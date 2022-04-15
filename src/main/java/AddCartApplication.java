import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author ：Tico
 * @date ：Created in 2022/4/14 03:31
 * @description：
 * @modified By：
 * @version:
 */
public class AddCartApplication {


    public static void main(String[] args) {

    }

    /**
     *  启动库存监控并添加到购物车
     */
    private static void stockMonitor(){
        System.out.println(Api.now() + "库存监控启动,本次监控目录:".concat(ExtUserConfig.categoryMap.keySet().stream().map(foo -> ExtUserConfig.categoryMap.get(foo)).collect(Collectors.joining(","))));
        AtomicReference<Integer> i = new AtomicReference<>(0);
        while(true){
            i.getAndSet(i.get() + 1);
            ExtUserConfig.categoryMap.keySet().stream().forEach(cateId -> {
                ExtApi.checkStock(cateId);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            System.out.println(ExtApi.now() + "第" + i.get() + "轮循环完毕...5秒后开始下一轮...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  备份购物车
     */
    private static void createCartSnapshot(){
        System.out.println(ExtApi.createCartSnapshot());;
    }

    /**
     *  恢复购物车
     */
    private static void recoverCartBySnapshot(String backup){
        Map<String,String> backupMap = JSONUtil.parseObj(backup,true).toBean(HashMap.class);
        if(CollectionUtil.isNotEmpty(backupMap.keySet())){
            backupMap.keySet().stream().filter(Objects::nonNull).forEach(fooKey -> {
                ExtApi.addCart(fooKey,backupMap.get(fooKey));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
