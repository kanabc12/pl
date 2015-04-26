<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf" %>
<pl:contentHeader title="足彩统计分析系统"/>
<pl:navbar/>
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
    <script type="text/javascript">
        try {
            ace.settings.check('main-container', 'fixed')
        } catch (e) {
        }
    </script>
    <pl:sidebar/>
    <div class="main-content">
        <pl:search/>
        <div class="page-content">
            <pl:setting/>
            <div class="page-header">
                <h1>球队信息查询</h1>
            </div>
            <!-- /.page-header -->
            <div class="row">
                <div class="col-xs-12">
                    <!-- PAGE CONTENT BEGINS -->
                    <form class="form-horizontal" role="form"
                          action="" method="post">
                        <div class="form-group">
                            <label class="col-xs-1 control-label " for="name">
                                球队名称 :</label>
                            <input type="text" id="name" placeholder="请输入球队名称"
                                   class="col-xs-2 " name="name"/>
                            <label class="col-xs-1 control-label"
                                   for="contry"> 所属国家: </label>
                            <select class="col-xs-2" id="contry" name="contry">
                                <option value="0"></option>
                                <c:forEach items="${countryList}" var="c">
                                    <option value="${c.id}">${c.name}</option>
                                </c:forEach>
                            </select>
                            <label class="col-xs-1 control-label" for="type">
                                球队性质: </label>
                            <select class="col-xs-2" id="type" name="type">
                                <option value="0"></option>
                                <option value="1">俱乐部</option>
                                <option value="2">国家队</option>
                            </select>
                            <button class="btn btn-info right" type="button" id="btnQuery">
                                <i class="ace-icon fa fa-search"></i> 查询
                            </button>
                        </div>
                    </form>
                    <table id="grid-table"></table>
                    <div id="grid-pager"></div>
                    <!-- PAGE CONTENT ENDS -->
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->
        </div>
    </div>
</div>
<pl:contentFooter/>
<%@include file="/WEB-INF/jsp/common/import-jqGrid-js.jspf" %>
<script type="text/javascript">
    jQuery(function ($) {
        gritInit();
        $("#btnQuery").click(function () {
            jQuery("#grid-table").jqGrid('setGridParam',{
                url: "${ctx}/bd/team/showEdit",
                datatype: "json",
                mtype: "POST",
                postData: {name: $("#name").val(),
                    contry: $("#contry").val(),
                    type: $("#type").val()}
            }).trigger("reloadGrid");
        });
    });
    function gritInit() {
        jQuery("#grid-table").jqGrid({
            height: "auto",//高度，表格高度。可为数值、百分比或'auto'
            autowidth: true,
            caption: "查询结果",
            colNames: [ '球队名称', '英文简写', '所属国家', '主教练', '建队日期', '球队性质' ],
            colModel: [
                {name: 'name',width: 55},
                {name: 'nameAbbr', width: 90},
                {name: 'contryName', width: 100},
                {name: 'coach',  width: 80, align: "right"},
                {name: 'buildDate', index: 'tax',datefmt:"yyyy-mm-dd", width: 80, align: "right"},
                {name: 'type',width: 80, align: "right"}
            ],
            rowNum: 10,
            rowList: [ 10, 20, 30 ],
            rownumbers: true,
            viewrecords: true,
            pager : $("#grid-pager")
        });
    }
</script>
