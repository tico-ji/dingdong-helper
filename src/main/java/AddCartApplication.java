import java.util.HashMap;
import java.util.Map;
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
}
