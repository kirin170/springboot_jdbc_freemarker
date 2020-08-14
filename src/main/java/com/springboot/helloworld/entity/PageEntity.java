package com.springboot.helloworld.entity;

import lombok.Data;

import java.util.List;

@Data
public class PageEntity {
    /**
     * 当前页码
     */
    private int currentPage;
    /**
     * 每页显示条数
     */
    private int pageSize;
    /**
     * 数据库查询到的记录总条数
     */
    private int totalRecordSize;
    /**
     * 当前页的数据列表
     */
    private List<UserEntity> currentRecordList;
    /**
     * 关键字
     */
    private String keyword;


    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 页码开始索引
     */
    private int startPageIndex;
    /**
     * 页码结束索引
     */
    private int endPageIndex;


//    构造方法
    public PageEntity(int currentPage, int pageSize, String keyword, int totalRecordSize, List<UserEntity> recordList){
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.keyword = keyword;
        this.totalRecordSize = totalRecordSize;
        this.currentRecordList = recordList;

        /**
         * 计算总页数
         */
        totalPage = (totalRecordSize + pageSize - 1) / pageSize;
        /**
         * 计算beginPageIndex以及endPageIndex
         * 如果是10页以内，则页数为1~10
         * 大于10页，则显示10附近的10个页数，比如6~15、7~16......
         */
        if (totalPage <= 10){
            startPageIndex = 1;
            endPageIndex = totalPage;
        }else{
//            前4页+当前页+后5页
            startPageIndex = currentPage - 4;
            endPageIndex = currentPage + 5;
//            当前面页数不足4页时，显示前10个页面
            if (startPageIndex < 1){
                startPageIndex = 1;
                endPageIndex = 10;
            }
//            当后面页数不足5页时，显示后面10个页码
            if (endPageIndex > totalPage){
                startPageIndex = totalPage - 10 + 1;
                endPageIndex = totalPage;
            }
        }

    }
}
