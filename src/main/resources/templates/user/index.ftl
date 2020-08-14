<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>测试</title>
<#--引入bootstrap-->

    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.5.1/jquery.js"></script>
    <script src="https://cdn.bootcss.com/jquery.serializeJSON/2.9.0/jquery.serializejson.min.js"></script>
    <script src="https://cdn.bootcss.com/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://cdn.bootcss.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</head>
<body>
<#--搜索关键字-->
    <div class="input-group mb-3" style="width: 90%;margin: 20px 0 0 20px;float: left;">
        <input type="text" class="form-control" id="search_id" placeholder="请输入关键字" aria-label="关键字" aria-describedby="basic-addon2">
        <div class="input-group-append">
            <button class="btn btn-outline-secondary" type="button" type="submit"  onclick="searchId()">搜索</button>
        </div>
    </div>
<#--插入模态视图-->
    <button type="button" class="btn btn-primary" style="float: left;margin:20px 20px 0;" data-toggle="modal" data-target="#infoModel" data-id="insertCommit">插入</button>
    <div class="modal fade" id="infoModel" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">New message</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form>
                        <div class="form-group">
                            <label for="recipient-title" class="col-form-label">标题：</label>
                            <input type="text" class="form-control" id="recipient-title" name="title">
                        </div>
                        <div class="form-group">
                            <label for="recipient-name" class="col-form-label">名称：</label>
                            <input type="text" class="form-control" id="recipient-name" name="name">
                        </div>
                        <div class="form-group">
                            <label for="recipient-wxnum" class="col-form-label">微信号：</label>
                            <input type="text" class="form-control" id="recipient-wxnum" name="wxNumber">
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" id="commitBtn" onclick="commitEvent(this)">提交</button>
                </div>
            </div>
        </div>
    </div>

    <#--数据展示-->
    <div class="table-responsive">
        <table class="table">
            <thead>
            <tr>
                <th scope="col" style="width: 10%;"> ID </th>
                <th scope="col" style="width: 30%;">标题</th>
                <th scope="col" style="width: 20%;">姓名</th>
                <th scope="col" style="width: 20%;">微信</th>
                <th scope="col" style="width: 20%;">操作</th>
            </tr>
            </thead>
            <tbody>
            <#if userList?? && (userList?size > 0)>
                <#list userList as row>
                    <tr>
                        <th scope="row">${row.id}</th>
                        <td>${row.title}</td>
                        <td>${row.name}</td>
                        <td>${row.wxNumber}</td>
                    <td>
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#infoModel" data-id="alterCommit" data-param='{"id":"${row.id}","title":"${row.title}","name":"${row.name}","wxNumber":"${row.wxNumber}"}'>修改</button>
                        <button class="btn btn-danger" onclick="deleteRow(${row.id})">删除</button>
                    </td>
                    </tr>
                </#list>

            <#else>
                <p>${message}</p>
            </#if>
            </tbody>
        </table>
    </div>
<#if userList?? && (userList?size > 0)>
    <#--分页-->
    <div class="col-md-12 column">
        <ul class="pagination">
        <#--lte  less than or equal 小于等于-->
            <#if currentPage lte 1>
                <li class="disabled">
                    <a class="page-link" href="#"><<</a>
                </li>
            <#else>
                <li>
                    <a class="page-link" href="${pageUrl}page=${currentPage - 1}&keyword=${keyword}"><<</a>
                </li>
            </#if>

            <#list 1..totalPage as index>
                <#if currentPage == index>
                    <li class="page-item active">
                        <a class="page-link" href="#">${index}</a>
                    </li>
                <#else>
                    <li>
                        <a class="page-link" href="${pageUrl}page=${index}&keyword=${keyword}">${index}</a>
                    </li>
                </#if>
            </#list>
        <#--gte  greater than or equal  大于等于-->
            <#if currentPage gte totalPage>
                <li class="disabled">
                    <a class="page-link" href="#">>></a>
                </li>
            <#else>
                <li>
                    <a class="page-link" href="${pageUrl}page=${currentPage + 1}&keyword=${keyword}">>></a>
                </li>
            </#if>
        </ul>
    </div>
</#if>
</body>

<script type="text/javascript">
    $(function () {
        //显示模态视图
        $('#infoModel').on('show.bs.modal', function (event) {
            //获取当前点击按钮，用于判断是插入还是修改
            const button = $(event.relatedTarget);
            const btnId = button.data('id');
            //用于填充表单
            const modal = $(this);

            if (btnId === "insertCommit"){
                //插入
                modal.find("#recipient-title").val("");
                modal.find("#recipient-name").val("");
                modal.find("#recipient-wxnum").val("");

            } else if (btnId === "alterCommit"){
            //    修改
                const info = button.data("param");
                console.log(info);

                modal.find("#recipient-title").val(info.title);
                modal.find("#recipient-name").val(info.name);
                modal.find("#recipient-wxnum").val(info.wxNumber);

                //传rowid用于修改数据
                $("#commitBtn").attr("data-rowId", info.id);
            }

            //提交按钮加上id用于区分是插入提交还是修改提交
            $("#commitBtn").attr("data-id", btnId);
        });

        //模态视图提交内容
        commitEvent = function (event) {
            const btnId = $(event).attr("data-id");
            if (btnId === "insertCommit") {
                insertEvent();
            }else if (btnId === "alterCommit"){
                const rowId = $(event).attr("data-rowId");
                updateEvent(rowId);
            }

        };

        //    插入
        insertEvent = function () {
            //将form表单内容转为json串
            const jsonStr = JSON.stringify($('form').serializeJSON());
            console.log(jsonStr);
        //    通过ajax请求接口
            $.ajax({
                type: "POST",
                url: "/insertUser",
                contentType: "application/json",//数据请求格式
                dataType : 'json',//数据返回格式
                data: jsonStr,
                success: function(result){
                    console.log(result);
                    // $('#requestResult').append('success===' + result);
                    $('#infoModel').modal('hide');
                    //刷新页面
                    window.location.reload();
                },
                error:function(error){
                    console.log(error);
                    // $('#requestResult').append('error===' + error);
                    $('#infoModel').modal('hide');
                }
            });
        };
        //    修改
        updateEvent = function (rowId) {
            const object = $('form').serializeJSON();
            const paramInfo = {
                "title": object.title,
                "name": object.name,
                "wxNumber": object.wxNumber,
                "id": rowId
            };
            const jsonStr = JSON.stringify(paramInfo);
            console.log(jsonStr);

            $.ajax({
                type: "POST",
                url: "/updateUser",
                contentType: "application/json",
                dataType: "json",
                data: jsonStr,
                success: function(result){
                    console.log(result);
                    const json = JSON.parse(result);
                    //    关闭模态框
                    $('#infoModel').modal('hide');
                    //刷新页面
                    window.location.reload();

                },
                error:function(result){
                    alert("error");
                    $('#infoModel').modal('hide');
                }
            });
        }
        
        //    删除
        deleteRow = function (rowId) {
            const param = {
                "uid": rowId,
            };
            const jsonStr = JSON.stringify(param);
            $.ajax({
                type: "POST",
                url: "/deleteUser",
                contentType: "application/json",
                dataType: "json",
                data: jsonStr,
                success: function(result){
                    console.log(result);
                    //刷新页面
                    window.location.reload();
                },
                error:function(result){
                    alert("error");
                }
            });
        }

        //    搜索
        searchId = function () {
            var keyword = $("#search_id").val();
            window.location.href = "/index?keyword=" + keyword;
        }


    });
</script>

</html>