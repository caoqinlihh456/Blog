package com.cql.admin.controller;


import com.cql.commons.moudel.system.Result;
import com.es.dao.UserESDao;
import com.es.entity.UserES;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Slf4j
@RequestMapping("/es")
public class ESController {


//    @Autowired
//    private RestHighLevelClient client;
//
//    @Autowired
//    private HttpServletRequest httpServletRequest;
//
//    /**
//     * 添加索引
//     *
//     * @param key
//     * @return
//     * @throws IOException
//     */
//    @GetMapping("/addIndex")
//    public Result addIndex(String key) throws IOException {
//        //1.使用client获取操作索引对象
//        IndicesClient indices = client.indices();
//        //2.具体操作获取返回值
//        //2.1 设置索引名称
//        CreateIndexRequest createIndexRequest = new CreateIndexRequest(key);
//        CreateIndexResponse createIndexResponse = indices.create(createIndexRequest, RequestOptions.DEFAULT);
//        //3.根据返回值判断结果
//        System.out.println(createIndexResponse.isAcknowledged());
//        return Result.genSuccessResult("操作成功", createIndexResponse.isAcknowledged());
//    }
//
//
//    /**
//     * 添加索引以及映射mapper
//     *
//     * @param key
//     * @return
//     * @throws IOException
//     */
//    @GetMapping("/addIndexAndMapper")
//    public Result addIndexAndMapper(String key) throws IOException {
//        //1.使用client获取操作索引对象
//        IndicesClient indices = client.indices();
//        //2.具体操作获取返回值
//        //2.具体操作，获取返回值
//        CreateIndexRequest createIndexRequest = new CreateIndexRequest(key);
//        //2.1 设置mappings   版本不同  type    不同  类型不同
//        String mapping = " {\n" + "   \"user\":{          \n" + "      \"properties\": {       \n" + "        \"name\":{              \n" + "          \"type\": \"string\"     \n" + "        },\n" + "        \"password\":{\n" + "          \"type\": \"string\"\n" + "        },\n" + "        \"id\":{\n" + "          \"type\": \"long\"\n" + "        }\n" + "      }}\n" + "\n" + "} ";
//        createIndexRequest.mapping(mapping, XContentType.JSON);
//
//        CreateIndexResponse createIndexResponse = indices.create(createIndexRequest, RequestOptions.DEFAULT);
//        return Result.genSuccessResult("操作成功", createIndexResponse.isAcknowledged());
//    }
//
//
//    /**
//     * 删除索引
//     *
//     * @param key
//     * @return
//     * @throws IOException
//     */
//    @GetMapping("/deleteIndex")
//    public Result deleteIndex(String key) throws IOException {
//        IndicesClient indices = client.indices();
//        DeleteIndexRequest deleteRequest = new DeleteIndexRequest(key);
//        AcknowledgedResponse delete = indices.delete(deleteRequest, RequestOptions.DEFAULT);
//        System.out.println(delete.isAcknowledged());
//        return Result.genSuccessResult("操作成功", delete.isAcknowledged());
//    }
//
//
//    /**
//     * 索引是否存在
//     *
//     * @param key
//     * @return
//     * @throws IOException
//     */
//    @GetMapping("/existIndex")
//    public Result existIndex(String key) throws IOException {
//        IndicesClient indices = client.indices();
//        GetIndexRequest getIndexRequest = new GetIndexRequest(key);
//        boolean exists = indices.exists(getIndexRequest, RequestOptions.DEFAULT);
//        System.out.println(exists);
//        return Result.genSuccessResult("操作成功", exists);
//    }
//
//
//    /**
//     * 添加数据
//     *
//     * @param key
//     * @return
//     * @throws IOException
//     */
//    @GetMapping("/addUser")
//    public Result addUser() throws IOException {
//        User user = new User();
//        user.setName("cql");
//        user.setPassword("123");
//        user.setId(1L);
//        IndexRequest request = new IndexRequest("user").id("1").source(JSONObject.toJSONString(user), XContentType.JSON);
//        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
//        System.out.println(response.getId());
//        return Result.genSuccessResult("操作成功", true);
//    }

    @Autowired
    private UserESDao userESDao;


    @GetMapping("/addUser")
    public Result addUser() {
        UserES user = new UserES();
        user.setName("cql");
        user.setDesc("我是Java程序员");
        user.setSex("男");
        user.setId("1");
        userESDao.save(user);
        return Result.genSuccessResult("操作成功", true);
    }

}
