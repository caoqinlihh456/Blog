package com.cql.admin.controller;


import com.cql.commons.exception.ServiceException;
import com.cql.commons.moudel.system.Result;
import com.cql.es.Mapper.EsResultMapper;
import com.cql.es.dao.GoodsESDao;
import com.cql.es.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController  //
@RequestMapping("/es")
@Validated
@Slf4j
public class ESController {

    @Autowired
    private GoodsESDao goodsESDao;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private EsResultMapper esResultMapper;


    @GetMapping("/add")
    public Result<Boolean> add() {
        List<GoodsES> list = new ArrayList<>();
        List<String> a = new ArrayList<>();
        a.add("中国");
        a.add("美国");
        a.add("日本");
        a.add("中国");
        a.add("日本");
        a.add("德国");
        a.add("菲律宾");
        a.add("日本");
        a.add("日本");
        a.add("日本");

        List<String> b = new ArrayList<>();
        b.add("iPhone12");
        b.add("iPhone11");
        b.add("iPhone10");
        b.add("iPhone9");
        b.add("小米6");
        b.add("小米10");
        b.add("华为30");
        b.add("华为20");
        b.add("红米1");
        b.add("贡米2");

        List<String> d = new ArrayList<>();
        d.add("iPhone");
        d.add("iPhone");
        d.add("iPhone");
        d.add("iPhone");
        d.add("小米");
        d.add("小米");
        d.add("华为");
        d.add("华为");
        d.add("小米");
        d.add("华为");

        List<Double> c = new ArrayList<>();
        c.add(112.1);
        c.add(1233.1);
        c.add(122.1);
        c.add(142.1);
        c.add(162.1);
        c.add(172.1);
        c.add(192.1);
        c.add(112.1);
        c.add(122.1);
        c.add(142.1);


        for (int i = 0; i < 10; i++) {
            GoodsES goodsES = new GoodsES();
            goodsES.setId(String.valueOf(i));
            goodsES.setPrice(c.get(i));
            goodsES.setBrand(d.get(i));
            goodsES.setName(a.get(i) + "至尊无敌超级" + b.get(i));
            list.add(goodsES);
        }
        goodsESDao.saveAll(list);
        return Result.genSuccessResult("操作成功", true);
    }

    @GetMapping("/deleteByIds")
    public Result<?> deleteByIds() {
         goodsESDao.deleteAll();
        return Result.genSuccessResult("操作成功", true);
    }

    @GetMapping("/queryById")
    public Result<?> queryById(String id) {
        Optional<GoodsES> goodsES = goodsESDao.findById(id);
        GoodsES es = goodsES.get();
        return Result.genSuccessResult("操作成功", es);
    }


    /*8
     * 组合查询
     * must(QueryBuilders) :   AND
     * mustNot(QueryBuilders): NOT
     * should:                  : OR
     */

    //单个关键字 模糊查询
//        FuzzyQueryBuilder name = QueryBuilders.fuzzyQuery("name", key);
//        Iterable<GoodsES> search = goodsESDao.search(name);
//
//        //组装条件
//        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(defaultQueryBuilder)
//                .withHighlightBuilder(highlightBuilder)
//                .withPageable(pageRequest)
//                .withSort(ageSortBuilder)
//                .withSort(scoreSortBuilder)
//                .build();
//        FieldSortBuilder ageSortBuilder = SortBuilders.fieldSort("date").order(SortOrder.ASC);
//        // 默认score是倒序
    @GetMapping("/query")
    public Result<?> query(String key, String brand) {

        //排序
        FieldSortBuilder sort = SortBuilders.fieldSort("price").order(SortOrder.ASC);
        //名称查询
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.fuzzyQuery("name", key))//商品名称模糊查询
                .must(QueryBuilders.termQuery("brand", brand));
        ;//品牌精确查询

        // 构建分页
        PageRequest pageRequest = PageRequest.of(0, 2);
        //高亮
        HighlightBuilder.Field field = new HighlightBuilder.Field("name").preTags("<font style='color:red'>").postTags("</font>");
        //组合查询
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder).withSort(sort).withPageable(pageRequest)
                .withHighlightFields(field).build();
        // 不带高亮的查询
//        AggregatedPage<GoodsES> search = (AggregatedPage<GoodsES>) goodsESDao.search(searchQuery);

        //高亮查询
        AggregatedPage<GoodsES> search = elasticsearchTemplate.queryForPage(searchQuery, GoodsES.class, esResultMapper);
        List<GoodsES> content = search.getContent();
        List<GoodsES> list = new ArrayList<>();
        return Result.genSuccessResult("操作成功", true);
    }

    @PostMapping("/query2")
    public Result<AggregatedPage<GoodsES>> query(@RequestBody QueryParameters queryParameters) {
        //获取query
        NativeSearchQuery searchQuery = assemblQuery(queryParameters);
        //高亮查询
        AggregatedPage<GoodsES> search = elasticsearchTemplate.queryForPage(searchQuery, GoodsES.class, esResultMapper);
        return Result.genSuccessResult("操作成功", search);
    }

    /**
     * 组装es查询
     * @return
     */
    private NativeSearchQuery assemblQuery(QueryParameters queryParameters) {
        List<Sort> sortList = queryParameters.getSortList();
        List<Condition> conditionList = queryParameters.getConditionList();
        // es 的分页从 0 开始
        int pageNo = queryParameters.getPageNo() - 1;
        int pageSize = queryParameters.getPageSize();

        //总组装query
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        //字段查询query
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //字段匹配=======================================================================================
        if(!CollectionUtils.isEmpty(conditionList)){
            for (Condition condition : conditionList) {
                Attribute attribute = condition.getAttribute();
                switch (condition.getName()){
                    // 精确查询 一般作为品牌 属性 精准匹配
                    case "eq":
                        boolQueryBuilder.must(QueryBuilders.termQuery(attribute.getName(), attribute.getValue()));
                        break;
                    // 模糊查询 关键字查询 且高亮显示
                    case "like":
                        boolQueryBuilder.must(QueryBuilders.fuzzyQuery(attribute.getName(), attribute.getValue()));
                        //设置高亮
                        builder.withHighlightFields(new HighlightBuilder.Field(attribute.getName()).preTags("<font style='color:red'>").postTags("</font>"));
                        break;
                    // 大于等于
                    case "gt":
                        boolQueryBuilder.must(QueryBuilders.rangeQuery(attribute.getName())
                                .from(attribute.getValue())
                                .includeLower(true) )  ;  // 包含上界
                       break;
                    // 小于等于
                    case "lt":
                        boolQueryBuilder.must(QueryBuilders.rangeQuery(attribute.getName())
                                .to(attribute.getValue())
                                .includeUpper(true));      // 包含下届
                        break;
                    default:
                        throw new ServiceException("es未匹配到合适的查询");
                }
            }
        }
        //排序===========================================================================================
        if(!CollectionUtils.isEmpty(sortList)){
            for (Sort sort : sortList) {
                switch (sort.getValue()){
                    // 正序
                    case "asc":
                        //排序query
                        builder.withSort(SortBuilders.fieldSort(sort.getName()).order(SortOrder.ASC));
                        break;
                    // 倒序
                    case "desc":
                        builder.withSort(SortBuilders.fieldSort(sort.getName()).order(SortOrder.DESC));
                        break;
                    default:
                        throw new ServiceException("es未匹配到合适的查询");
                }
            }
        }
        //=====================分页=================================================================
             //es 深度分页会报错     6000页
             // 1. 查询数据限制一下就好
        if(pageNo >= 0){
            builder.withPageable(PageRequest.of(pageNo,pageSize));
        }
        builder.withQuery(boolQueryBuilder);
        return builder.build();
    }


}
