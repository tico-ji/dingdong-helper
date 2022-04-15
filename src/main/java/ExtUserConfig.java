import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author ：Tico
 * @date ：Created in 2022/4/15 16:58
 * @description：扩展配置
 * @modified By：
 * @version:
 */
public class ExtUserConfig extends UserConfig{

    /**
     *  添加购物车白名单
     */
    public static Set<String> pidSet = new HashSet<>();

    /**
     *  库存监控类目白名单
     */
    public static Map<String,String> categoryMap = new HashMap<>();

    static {
        categoryMap.put("5fb564538858c1fb7f61a89a","有机供港菜");
        categoryMap.put("61f3954a5c27c8000102d4a9","解春馋");
        categoryMap.put("60bb9443fa37ebfc4a03bd14","崇明蔬菜");
        categoryMap.put("5fe6d3d9d4ddcfe5de0602b0","叶子菜");
        categoryMap.put("61c1aaf9405e670001895419","黑猪肉");
        categoryMap.put("6118be7fbea1d20936da26e9","平价猪肉");
        categoryMap.put("5fb564518858c1fb7f61a7ae","猪肉日日鲜");
        categoryMap.put("6114fb082f1c61559939b150","鲜奶日日鲜");
        categoryMap.put("5fb5644b8858c1fb7f61a5ce","鲜奶");
        
        
        
        pidSet.add("5d36b90010abf132987a16d9");
        pidSet.add("5d40213a10abf132932b5dad");
        pidSet.add("5dca64d47cdbf013070f2d0f");
        pidSet.add("61504618a3ac91b21d03eb44");
        pidSet.add("61dd57f3ca91e8c8ab3dee83");
        pidSet.add("615042e3043a31c205ca8471");
        pidSet.add("615043e00de13069f5b05fd0");
        pidSet.add("61503ddd72c9e7cab7abf2a2");
        pidSet.add("6150424ce8fe47d486e453ec");
        pidSet.add("61503f30530ee2c2910da379");
        pidSet.add("61504b81d219eb9940a94e08");
        pidSet.add("61504b810bc43ce8f123e5c4");
        pidSet.add("61b2fcbe11328ef1849bf0cd");
        pidSet.add("61b94a980fc245471cb63d48");
        pidSet.add("5c8a24922b12be5f0053295a");
        pidSet.add("5c8a23187b37a12d484ad46d");
        pidSet.add("5cc32d65ca2034756a47d15f");
        pidSet.add("6202586bd66708a57189b060");
        pidSet.add("6191c355f0a826b0a6ddc85d");
        pidSet.add("5dc00c0ab0055a0b492a5a14");
        pidSet.add("6229db809675ba7699cec4a3");
        pidSet.add("5eddaac0b0055a4b917fd473");
        pidSet.add("5ec2724cb0055a0b5a3d7698");
        pidSet.add("5ea10ceeb0055a0b55181e1b");
        pidSet.add("58bf57df916edfa34cc575c8");
        pidSet.add("5f1a8bd710abf12dc20c51a3");
        pidSet.add("5f1a8e78b0055a4b7f492798");
        pidSet.add("5f1a9004b0055a4b917fd480");
        pidSet.add("5f1a934a7cdbf006dd055fa7");
        pidSet.add("5f1a94bd10abf12dda5dd40f");
        pidSet.add("5f1a95ad7cdbf055c679dc1a");
        pidSet.add("5eddaac0b0055a4b917fd473");
        pidSet.add("5df8a51e7cdbf0131056574e");
        pidSet.add("620257979173096686e94ffe");
        pidSet.add("5e85a0b2b0055a0b4d3567a3");
        pidSet.add("602282da516db2578089f276");
        pidSet.add("60473bca47987eb75d42290f");
        pidSet.add("6066aa5218392178e9bd4171");
        pidSet.add("602282da516db2578089f276");

        System.out.println("本次添加商品购物车白名单数量:" + pidSet.size());
    }
}
