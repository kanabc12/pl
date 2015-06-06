<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf" %>
<pl:contentHeader title="足彩统计分析系统"/>
<%@include file="/WEB-INF/jsp/common/import-bootstrap-table-css.jspf" %>
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
                                <div class="col-xs-12 col-sm-12 widget-container-col ui-sortable">
                                    <div class="widget-box ui-sortable-handle">
                                        <div class="widget-header">
                                            <h5 class="widget-title">赛季选择</h5>
                                            <div class="widget-toolbar">
                                                <a href="#" data-action="collapse">
                                                    <i class="ace-icon fa fa-chevron-up"></i>
                                                </a>
                                            </div>
                                        </div>
                                        <div class="widget-body">
                                            <div class="widget-main">
                                                <div class="row">
                                                    <label class="col-sm-1 control-label no-padding-right"
                                                           for="seasonId"> 所属赛季</label>
                                                    <select class="col-sm-6 " id="seasonId" name="seasonId">
                                                        <c:forEach items="${seasonList}" var="c">
                                                            <option value="${c.id}">${c.name}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-12 col-sm-12 widget-container-col ui-sortable">
                                    <div class="widget-box ui-sortable-handle">
                                        <div class="widget-header">
                                            <h5 class="widget-title">球队胜平负分析</h5>
                                            <div class="widget-toolbar">
                                                <a href="#" data-action="fullscreen" class="orange2">
                                                    <i class="ace-icon fa fa-expand"></i>
                                                </a>
                                                <a href="#" data-action="collapse">
                                                    <i class="ace-icon fa fa-chevron-up"></i>
                                                </a>
                                            </div>
                                        </div>
                                        <div class="widget-body">
                                            <div class="widget-main">
                                                <div id="teamWDF" style="height:350px;"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-12 col-sm-12 widget-container-col ui-sortable">
                                    <div class="widget-box ui-sortable-handle">
                                        <div class="widget-header">
                                            <h5 class="widget-title">球队胜平负按月分析</h5>
                                            <div class="widget-toolbar">
                                                <a href="#" data-action="fullscreen" class="orange2">
                                                    <i class="ace-icon fa fa-expand"></i>
                                                </a>
                                                <a href="#" data-action="collapse">
                                                    <i class="ace-icon fa fa-chevron-up"></i>
                                                </a>
                                            </div>
                                        </div>
                                        <div class="widget-body">
                                            <div class="widget-main">
                                                <div id="teamWDFMonth" style="height:350px;"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="gameResult" class="row hide">
                                <div class="col-sm-12">
                                    <table id="gameTable" data-toggle="table" data-height="300"  data-row-style="rowStyle">
                                        <thead>
                                        <tr>
                                            <th data-field="seasonName">赛季</th>
                                            <th data-field="leagueName">联赛</th>
                                            <th data-field="homeTeamName">主队</th>
                                            <th data-field="resultStr">比分</th>
                                            <th data-field="customTeamName">客队</th>
                                            <th data-field="round" data-formatter="roundFormatter">轮次</th>
                                            <th data-field="gameStatus"  data-formatter="statusFormatter">状态</th>
                                            <th data-field="actualTime">比赛时间</th>
                                        </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.row -->
    </div>
    <pl:footer/>
</div>
<pl:contentFooter/>
<%@include file="/WEB-INF/jsp/common/import-bootstrap-table-js.jspf" %>
<%@include file="/WEB-INF/jsp/common/import-echarts-js.jspf" %>
<%@include file="/WEB-INF/jsp/common/import-tree-js.jspf" %>
<script type="text/javascript">
    var teamWDFPie;
    var teamWDFBar;
    var eCharts;
    require.config({
        paths: {
            'echarts': "${ctx}/static/comp/echarts",
            'echarts/chart/pie':"${ctx}/static/comp/echarts/chart/pie",
            'echarts/chart/bar':"${ctx}/static/comp/echarts/chart/bar"
        }
    });
    require(
            [
                'echarts',
                'echarts/chart/pie', // 使用柱状图就加载bar模块，按需加载
                'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
            ],DrawCharts
    );
    jQuery(function ($) {
        $.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
            _title: function (title) {
                var $title = this.options.title || '&nbsp;'
                if (("title_html" in this.options) && this.options.title_html == true)
                    title.html($title);
                else title.text($title);
            }
        }));
        $('#tree1').treeview({
            data: getTree("${ctx}/analyse/team/getTreeData"),
            showTags: true,
            onNodeSelected: function (event, data) {
                if(data.nodes.length==0&& typeof(data.parentId) !="undefined"){
                    loadPie(data.text, $("#seasonId").val());
//                    loadBar(data.text, $("#seasonId").val());
                }
            }
        });
        $("#seasonId").change(function () {
            var seasonId = $(this).children('option:selected').val();
            var nodeText = "";
            if($('#tree1').treeview('getSelected').length>0){
                nodeText = $('#tree1').treeview('getSelected')[0].text;
            }  else{
                nodeText = "切尔西";
            }
            loadPie(nodeText, seasonId);
//            loadBar(nodeText, seasonId);
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
    //加载饼状图
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
    //加载柱状图
    function loadBar(text, seasonId) {
        teamWDFBar.clear();
        teamWDFBar.showLoading({text: '正在努力的读取数据中1...'});
        $.ajax({
            url: "${ctx}/analyse/team/getTeamWDFAndYM",
            data: {
                seasonId: seasonId,
                teamName: text
            },
            type: 'POST',
            dataType: 'json',
            success: function (data) {
                if (data != null) {
                    console.log(data)
                    teamWDFBar.setOption($.parseJSON(data),true);
//                    teamWDFBar.hideLoading();
                }
            }
        });
    }
    function DrawPieEChart(ec){
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
        var ecConfig = require('echarts/config');
        //ECharts图表的click事件监听
        teamWDFPie.on("click",eConsole);
        teamWDFPie.setOption(option); //先把可选项注入myChart中
        teamWDFPie.hideLoading();
    }
    ///将画多个图表的进行函数封装
    function DrawCharts(ec) {
        DrawPieEChart(ec);
        DrawColumnEChart(ec);
    }
    //创建ECharts柱状图图表
    function DrawColumnEChart(ec) {
        //--- 柱状图 ---
        teamWDFBar= ec.init(document.getElementById('teamWDFMonth'));
        //图表显示提示信息
        teamWDFBar.showLoading({
            text: "图表数据正在努力加载..."
        });
        teamWDFBar.hideLoading();
        teamWDFBar.setOption({
            title : {
                text: '未来一周气温变化',
                subtext: '纯属虚构'
            },
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:['最高气温','最低气温']
            },
            toolbox: {
                show : true,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {show: true, type: ['line', 'bar']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : false,
                    data : ['周一','周二','周三','周四','周五','周六','周日']
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    axisLabel : {
                        formatter: '{value} °C'
                    }
                }
            ],
            series : [
                {
                    name:'最高气温',
                    type:'line',
                    data:[11, 11, 15, 13, 12, 13, 10],
                    markPoint : {
                        data : [
                            {type : 'max', name: '最大值'},
                            {type : 'min', name: '最小值'}
                        ]
                    },
                    markLine : {
                        data : [
                            {type : 'average', name: '平均值'}
                        ]
                    }
                },
                {
                    name:'最低气温',
                    type:'line',
                    data:[1, -2, 2, 5, 3, 2, 0],
                    markPoint : {
                        data : [
                            {name : '周最低', value : -2, xAxis: 1, yAxis: -1.5}
                        ]
                    },
                    markLine : {
                        data : [
                            {type : 'average', name : '平均值'}
                        ]
                    }
                }
            ]
        });

        var ecConfig = require('echarts/config');
        //ECharts图表的click事件监听
        teamWDFBar.on("click",eConsole);
    }
    function eConsole(param) {
//        var mes = '【' + param.type + '】';
//        if (typeof param.seriesIndex != 'undefined') {
//            mes += '  seriesIndex : ' + param.seriesIndex;
//            mes += '  dataIndex : ' + param.dataIndex;
//        }
//        if (param.type == 'hover') {
//            document.getElementById('hover-console').innerHTML = 'Event Console : ' + mes;
//        }
//        else {
//            console.log(mes);
//        }
        $("#gameResult").removeClass('hide').dialog({
            modal: true,
            height: 430,
            width: 800,
            title: "<div class='widget-header widget-header-small'><h4 class='smaller'>查询结果</h4></div>",
            title_html: true,
            zIndex: 10,
            buttons: [
                {
                    text: "关闭",
                    "class": "btn btn-xs",
                    click: function () {
                        $("#gameResult").dialog("close");
                    }
                }
            ]
        });
        $("#gameTable").bootstrapTable('load', randomData(param.dataIndex));
    }

function roundFormatter(value ,row){
    if (typeof(value) == "undefined") {
        return "未知";
    } else {
        return value;
    }
}
    function statusFormatter(value ,row){
        if (value == 1) {
            return "已赛";
        } else if (value == 0) {
            return "未赛";
        } else {
            return "推迟";
        }
    }
function randomData(resultType){
    var  rows = [];
    var nodeText = "";
    if($('#tree1').treeview('getSelected').length>0){
        nodeText = $('#tree1').treeview('getSelected')[0].text;
    }  else{
        nodeText = "切尔西";
    }
    var seasonId = $("#seasonId").val();
    $.ajax({
        url: "${ctx}/analyse/team/getTeamWDFDetail",
        data:{
            teamName:nodeText,
            seasonId:seasonId,
            resultType:resultType
        },
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (data != null)
                rows = data;
        },
        error: function (response) {
            //console.log(response);
        }
    });
    return rows;
}
</script>
