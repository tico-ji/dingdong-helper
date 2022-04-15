import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author ：Tico
 * @date ：Created in 2022/4/15 16:05
 * @description：扩展api
 * @modified By：
 * @version:
 */
public class ExtApi extends Api{
    /**
     *  检查库存
     */
    public static boolean checkStock(String categoryId){
        HttpRequest httpRequest = HttpUtil.createGet("https://maicai.api.ddxq.mobi/guide-service/homeApi/categoriesNewDetail");
        httpRequest.addHeaders(UserConfig.getHeaders());
        Map<String, String> request = UserConfig.getBody();
        request.put("category_id",categoryId);
        request.put("version_control","new");
        httpRequest.formStr(request);
        String body = httpRequest.execute().body();
        JSONObject object = JSONUtil.parseObj(body);
        JSONArray cateList = object.getJSONObject("data").getJSONArray("cate");
        String categoryName = object.getJSONObject("data").getStr("category_name");
        System.out.println(now() + "正在检查类目 <" + categoryName + "> 库存...");
        AtomicReference<Boolean> result = new AtomicReference<>(true);
        boolean flag = false;
        /**
         *  类目分两种不同的格式,以下根据cateList是否为空分别做出判断
         */
        if(Objects.nonNull(cateList)){
            flag = true;
            cateList.stream().filter(Objects::nonNull).forEach(foo -> {
                JSONArray cateProducts = ((JSONObject) foo).getJSONArray("products");
                cateProducts.stream().filter(Objects::nonNull).forEach(fooo -> {
                    String name = ((JSONObject) fooo).getStr("name");
                    Integer stock = ((JSONObject) fooo).getInt("stock_number");
//                    System.out.println("商品 <" + name + "> id:" + ((JSONObject) fooo).getStr("id") + " 库存:" + stock);
                    if(stock > 0){
                        System.out.println(now() + categoryName + " 到货提醒:" + name + " 库存:" + stock);
                        result.set(false);
                        addCart(((JSONObject) fooo).getStr("id"),name);
                    }
                });

            });
        }else {
            JSONArray products = object.getJSONObject("data").getJSONArray("products");
            products.stream().filter(Objects::nonNull).forEach(fooo -> {
                String name = ((JSONObject) fooo).getStr("name");
                Integer stock = ((JSONObject) fooo).getInt("stock_number");
//                System.out.println("商品 <" + name + "> id:" + ((JSONObject) fooo).getStr("id") + " 库存:" + stock);
                if(stock > 0){
                    System.out.println(now() + categoryName + " 到货提醒:" + name + " 库存:" + stock);
                    result.set(false);
                    addCart(((JSONObject) fooo).getStr("id"),name);
                }
            });
            flag = true;
        }
        if(!flag){
            System.out.println(now() + "类目 <" + categoryName + "> 数据结构为解析有误.请检查......");
        }
        return result.get();
    }

    private static final String productDetailUrl = "https://maicai.api.ddxq.mobi/guide-service/productApi/productDetail/info";
    private static final String addCartUrl_POST = "https://maicai.api.ddxq.mobi/cart/add";

    /**
     *  根据白名单添加至购物车
     */
    public static void addCart(String pid,String name){
        if(!ExtUserConfig.pidSet.contains(pid)){
            System.out.println(now() + name + " 不在购物清单之内,跳过...");
            return;
        }
        HttpRequest httpRequest = HttpUtil.createGet(productDetailUrl);
        httpRequest.addHeaders(UserConfig.getHeaders());
        Map<String, String> request = UserConfig.getBody();
        request.put("product_id",pid);
        request.put("id",pid);
        httpRequest.formStr(request);
        String body = httpRequest.execute().body();
        JSONObject object = JSONUtil.parseObj(body);
        JSONArray sizeArray = object.getJSONObject("data").getJSONObject("detail").getJSONArray("sizes");
        String sizeId = null;
        if(Objects.isNull(sizeArray) || sizeArray.size() == 0){
            //没有型号,直接添加
        }else{
            //有型号,获取第一个
            sizeId = ((JSONObject)((JSONObject)sizeArray.get(0)).getJSONArray("values").get(0)).getStr("id");
        }
        HttpRequest addCartReq = HttpUtil.createPost(addCartUrl_POST);
        addCartReq.addHeaders(UserConfig.getHeaders());
        Map<String, Object> addCartQuery = UserConfig.getBody();
        addCartQuery.put("products", JSONUtil.toJsonStr(Arrays.asList(new FooProduct(pid,sizeId))));
        addCartQuery.put("is_filter","1");
        addCartQuery.put("is_load","1");
        addCartQuery.put("add_scene","6");
        addCartQuery.put("filter_stock","1");
        addCartQuery.put("showData","true");
        addCartQuery.put("showMsg","false");
        request.put("ab_config", "{\"key_onion\":\"C\"}");
        addCartReq.form(addCartQuery);
        String addResultBody = addCartReq.execute().body();
        JSONObject addResult = JSONUtil.parseObj(addResultBody);
        if(Objects.nonNull(addResult) && addResult.getBool("success")){
            System.out.println(now() + "<" + name + "> 添加购物车成功!");
            play();
            ExtUserConfig.pidSet.remove(pid);
        }else {
            System.out.println(now() + "<" + name + "> 添加购物车失败: " + addResultBody);
        }
    }

    /**
     *  检查是否有运力
     */
    public static boolean getMultiReserveTimePre(){
        HttpRequest httpRequest = HttpUtil.createGet("https://maicai.api.ddxq.mobi/orderFlashSale/check");
        httpRequest.addHeaders(UserConfig.getHeaders());
        Map<String, String> request = UserConfig.getBody();
        httpRequest.formStr(request);

        String body = httpRequest.execute().body();
        JSONObject object = JSONUtil.parseObj(body);
        boolean result = object.getBool("success");
        System.out.println(now() + "运力检查结果:" + result + " msg:" + object.getStr("msg"));
        return result;
    }



    static class FooProduct{
        public String id;
        public Integer count = 1;
        public List<Size> sizes = new ArrayList<>();

        class Size{
            public String id;

            public Size(String id) {
                this.id = id;
            }
        }

        public FooProduct(String pid, String sizeId){
            this.id = pid;
            if(Objects.nonNull(sizeId) && sizeId.length() > 0){
                this.sizes.add(new Size(sizeId));
            }
        }
    }
}
