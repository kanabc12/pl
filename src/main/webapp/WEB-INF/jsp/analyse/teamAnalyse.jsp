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
                        <div class="col-sm-9" id="rightArea">
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
                            <div class="row">
                                <div class="col-xs-12 col-sm-12 widget-container-col ui-sortable">
                                    <div class="widget-box ui-sortable-handle">
                                        <div class="widget-header">
                                            <h5 class="widget-title">球队胜平负分析</h5>

                                            <!-- #section:custom/widget-box.toolbar -->
                                            <div class="widget-toolbar">
                                                <a href="#" data-action="fullscreen" class="orange2">
                                                    <i class="ace-icon fa fa-expand"></i>
                                                </a>

                                                <a href="#" data-action="reload">
                                                    <i class="ace-icon fa fa-refresh"></i>
                                                </a>

                                                <a href="#" data-action="collapse">
                                                    <i class="ace-icon fa fa-chevron-up"></i>
                                                </a>

                                                <a href="#" data-action="close">
                                                    <i class="ace-icon fa fa-times"></i>
                                                </a>
                                            </div>
                                        </div>
                                        <div class="widget-body">
                                            <div class="widget-main">
                                                <div id="teamWDF" style="height:400px;"></div>
                                            </div>
                                        </div>
                                    </div>
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
<%@include file="/WEB-INF/jsp/common/import-echarts-js.jspf" %>
<%@include file="/WEB-INF/jsp/common/import-tree-js.jspf" %>
<script type="text/javascript">
    var teamWDFPie;
    var eCharts;
    require.config({
        paths: {
            'echarts': "${ctx}/static/comp/echarts",
            'echarts/chart/pie':"${ctx}/static/comp/echarts/chart/pie"
        }
    });
    require(
            [
                'echarts',
                'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
            ],drawPie
    );
    jQuery(function ($) {
        $('#tree1').treeview({
            data: getTree("${ctx}/analyse/team/getTreeData"),
            showTags: true,
            onNodeSelected: function (event, data) {
                if(data.nodes.length==0&& typeof(data.parentId) !="undefined"){
                    $("#rightArea").removeClass("hide");
                    loadPie(data.text, $("#seasonId").val());
                }
            }
        });
        $("#seasonId").change(function () {
            var seasonId = $(this).children('option:selected').val();
            var nodeText = $('#tree1').treeview('getSelected')[0].text;
            loadPie(nodeText, seasonId);
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
    function loadPie(text, seasonId) {
        teamWDFPie.clear();
        teamWDFPie.showLoading({text: '正在努力的读取数据中1...'});
        $.ajax({
            url: "${ctx}/analyse/team/getTeamWDF",
            data: {
                seasonId: seasonId,
                teamName: text
            },
            type: 'POST',
            dataType: 'json',
            success: function (data) {
                if (data != null) {
                    teamWDFPie.setOption(data,true);
                    teamWDFPie.hideLoading();
                }
            }
        });
    }
    function drawPie(ec){
        eCharts = ec;
        teamWDFPie = eCharts.init(document.getElementById('teamWDF'));
        teamWDFPie.clear();
        teamWDFPie.showLoading({text: '正在努力的读取数据中...'});
        var option ={
            "legend": {
                "data": [
                    "主场胜",
                    "主场平",
                    "主场负",
                    "客场胜",
                    "客场平",
                    "客场负"
                ],
                "orient": "vertical",
                "x": "left"
            },
            "series": [
                {
                    "data": [
                        {
                            "name": "主场胜",
                            "value": 12
                        },
                        {
                            "name": "主场平",
                            "value": 3
                        },
                        {
                            "name": "主场负",
                            "value": 4
                        },
                        {
                            "name": "客场胜",
                            "value": 6
                        },
                        {
                            "name": "客场平",
                            "value": 7
                        },
                        {
                            "name": "客场负",
                            "value": 6
                        }
                    ],
                    "name": "场次",
                    "type": "pie"
                }
            ],
            "title": {
                "text": "切尔西",
                "x": "center"
            },
            "tooltip": {
                "formatter": "{a} <br/>{b} : {c} ({d}%)",
                "trigger": "item"
            }
        };
        teamWDFPie.setOption(option); //先把可选项注入myChart中
        teamWDFPie.hideLoading();
    }
</script>
