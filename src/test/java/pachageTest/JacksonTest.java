package pachageTest;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import pojo.Product;
import org.junit.Test;
public class JacksonTest {
    /**
    * jackson实现对象和json,集合和json之间的互相转换
    *
    */

    private ObjectMapper objectMapper = new ObjectMapper();

    @org.junit.Test
    public void test(){
        String jsonArr = "[{\"brand\":\"联想\",\"type\":\"电脑\",\"color\":\"白色\",\"price\":\"3000\"},"+
                "{\"brand\":\"小米\",\"type\":\"手机\",\"color\":\"黑色\",\"price\":\"2500\"},"+
                "{\"brand\":\"华为\",\"type\":\"手机\",\"color\":\"白色\",\"price\":\"2000\"},"+
                "{\"brand\":\"戴尔\",\"type\":\"电脑\",\"color\":\"蓝色\",\"price\":\"4000\"},"+
                "{\"brand\":\"苹果\",\"type\":\"手机\",\"color\":\"红色\",\"price\":\"5000\"}]";

        try {
            //json转集合
            List<Product> plist = objectMapper.readValue(jsonArr, new TypeReference<List<Product>>(){});
            System.out.println(plist);

            //json转对象
            //ObjectNode objectNode = mapper.readValue(json, ObjectNode.class);
            Product p = objectMapper.readValue("{\"brand\":\"小米\",\"type\":\"手机\",\"color\":\"黑色\",\"price\":\"2500\"}", Product.class);
            System.out.println(p);

            //对象转json
            String json_p = objectMapper.writeValueAsString(new Product("小米","手机","黑色",2500));
            System.out.println(json_p);

            //集合转json
            String json_list = objectMapper.writeValueAsString(plist);
            System.out.println(json_list);

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @org.junit.Test
    public void testNode(){
        String json = "{\"brand\":\"小米\",\"type\":\"手机\",\"color\":\"黑色\",\"price\":\"2500\"}";

        ObjectMapper mapper = new ObjectMapper();
        try {
            //反序列化都是ObjectMapper.read相关方法
            //第一种方式，如果有对应的javaBean接收，可以这样，直接做
            Product people = mapper.readValue(json, Product.class);
            System.out.println(people);

            //第二种方式，如果没有对应的javaBean接收，可以用ObjectNode接收
            ObjectNode objectNode = mapper.readValue(json, ObjectNode.class);
            //jackson中json对象就是一个个node

            //查找node和值得几种方式区别
        /*
         * 1.findValue   从当前节点开始查询子孙节点，只要有匹配的节点就返回该节点否则返回null
         * 2.get         仅查询当前节点是否有匹配的节点
         * 3.with        仅查询当前节点是否有匹配的节点，若有，则该节点必须是ObjectNode，若无，则自动创建一个
         * 4.withArray   仅查询当前节点是否有匹配的节点，若有，则该节点必须是ArrayNode，若无，则自动创建一个
         */
            objectNode.findValue("女友");
            objectNode.get("pets");
            objectNode.with("others");
            objectNode.withArray("pets");


            //ObjectNode节点数据的添加
            //因为objectNode没有"myNode"节点，
            // 所有当调用objectNode.with("myNode")自动就添加了该节点，然后我们可以往该节点里面添加数据
            ObjectNode myNode = objectNode.with("myNode");
            myNode.put("key1", "value1");
            myNode.put("key2", 19.9);


            //ArrayNode节点数据的添加
            ArrayNode myArrayNode = objectNode.withArray("myArrayNode");
            myArrayNode.add("");
            //添加一个ObjectNode，然后又往这个ObjectNode里面放东西。
            ObjectNode jsonNodes = myArrayNode.addObject();
            //。。。。等等方法。
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


