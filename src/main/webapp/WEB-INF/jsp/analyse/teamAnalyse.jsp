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
                <h1>球队基本信息分析</h1>
            </div>
            <!-- /.page-header -->
            <div class="row">
                <div class="col-sm-12">
                    <!-- #section:plugins/fuelux.treeview -->
                    <div class="row">
                        <div class="col-sm-3">
                            <div id="tree1"></div>
                        </div>
                        <div class="col-sm-9 hide" id="rightArea">
                            <div class="row">
                                <label class="col-sm-1 control-label no-padding-right"
                                       for="seasonId"> 所属赛季 </label>
                                <div class="col-sm-3">
                                    <select class="col-sm-12" id="seasonId" name="seasonId">
                                        <c:forEach items="${seasonList}" var="c">
                                            <option value="${c.id}">${c.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="row" style="margin-top: 10px;">
                                <div class="col-sm-5">
                                    <div  id="teamWDF" style="height:600px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.row -->
    </div>
</div>
</div>
<pl:contentFooter/>
<%@include file="/WEB-INF/jsp/common/import-echarts-all-js.jspf" %>
<%@include file="/WEB-INF/jsp/common/import-tree-js.jspf" %>
<script type="text/javascript">
    var teamWDFPie = echarts.init(document.getElementById("teamWDF"));
    jQuery(function ($) {
        $('#tree1').treeview({
            data: getTree("${ctx}/analyse/team/getTreeData"),
            showTags:true,
            onNodeSelected:function(event, data) {
                $("#rightArea").removeClass("hide");
                loadPie(data.text,$("#seasonId").val());
            }
        });
        $("#seasonId").change(function(){
            var seasonId = $(this).children('option:selected').val();
            var nodeText =  $('#tree1').treeview('getSelected')[0].text;
            loadPie(nodeText,seasonId);
        });
    });
    function getTree(url) {
        var retdata = [];
        $.ajax({
            url: url,
            data: 'id=0',
            type: 'POST',
            dataType: 'json',
            async: false,
            success: function (data) {
                if (data != null)
                    retdata = data.data;
            },
            error: function (response) {
                //console.log(response);
            }
        });
        return retdata;
    }
    //查询
    function loadPie(text,seasonId) {
        teamWDFPie.clear();
        teamWDFPie.showLoading({text: '正在努力的读取数据中...'});
        $.ajax({
            url:"${ctx}/analyse/team/getTeamWDF",
            data:{
                seasonId:seasonId,
                teamName:text
            },
            type: 'POST',
            dataType: 'json',
            success:function(data){
                if(data!= null){
                    teamWDFPie.setOption(data, true);
                    teamWDFPie.hideLoading();
                }
            }
        });
    }
</script>
