<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf" %>
<pl:contentHeader title="足彩统计分析系统"/>
<%@include file="/WEB-INF/jsp/common/import-datetimepicker-css.jspf" %>
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
                <h1>赔率信息录入</h1>
            </div>
            <!-- /.page-header -->
            <div class="row">
                <div class="col-sm-12">
                    <!-- PAGE CONTENT BEGINS -->
                    <form class="form-horizontal" role="form" id="myForm"
                          action="${ctx}/game/saveGame" method="post">
                        <pl:showMessage/>
                        <div class="form-group">
                            <label class="col-sm-1 control-label no-padding-right"
                                   for="leagueId"> 所属联赛 </label>

                            <div class="col-sm-3">
                                <select class="col-sm-12" id="leagueId" name="leagueId">
                                    <c:forEach items="${leagueList}" var="c">
                                        <option value="${c.id}">${c.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <label class="col-sm-1 control-label no-padding-right"
                                   for="seasonId"> 所属赛季 </label>

                            <div class="col-sm-3">
                                <select class="col-sm-12" id="seasonId" name="seasonId">
                                    <option value="0"></option>
                                    <c:forEach items="${seasonList}" var="c">
                                        <option value="${c.id}">${c.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <label class="col-sm-1 control-label no-padding-right"
                                   for="round"> 赛季轮次 </label>

                            <div class="col-sm-3">
                                <select class="col-sm-12" id="round" name="round">
                                    <option value="0"></option>
                                    <c:forEach var="i" begin="1" end="38" step="1">
                                        <option value="${i}">第${i}轮</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-1 control-label no-padding-right" for="homeTeam">
                                主场球队 </label>

                            <div class="col-sm-3">
                                <select class="col-sm-12" id="homeTeam" name="homeTeam">
                                    <option value="0"></option>
                                    <c:forEach items="${teamList}" var="c">
                                        <option value="${c.id}">${c.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <label class="col-sm-1 control-label no-padding-right" for="homeTeam">
                                客场球队 </label>

                            <div class="col-sm-3">
                                <select class="col-sm-12 validate[funcCall[validCustTeam]]" id="customTeam"
                                        name="customTeam">
                                    <option value="0"></option>
                                    <c:forEach items="${teamList}" var="c">
                                        <option value="${c.id}">${c.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-sm-1"></div>
                            <div class="col-sm-2 no-padding-right">
                                <button class="btn btn-sm btn-info btn-block right" style="width: 262px;" type="button"
                                        id="btnQuery">
                                    <i class="ace-icon fa fa-search"></i> 查询
                                </button>
                            </div>
                        </div>
                    </form>
                    <!-- PAGE CONTENT ENDS -->
                </div>
                <!-- /.col -->
                <div class="col-sm-12">
                    <table id="sample-table-1" class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th class="center" width="5%">
                                序号
                            </th>
                            <th width="15%">
                                比赛时间
                            </th>
                            <th>赛季</th>
                            <th>联赛</th>
                            <th>主队</th>
                            <th>比分</th>
                            <th>客队</th>
                            <th>轮次</th>
                            <th>状态</th>
                            <th class="hidden-480">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
                <div class="col-sm-12">
                    <ul id="page1"></ul>
                </div>
                <div id="dialog-message" class="hide">
                    <p>
                        This is the default dialog which is useful for displaying information. The dialog window can be moved, resized and closed with the 'x' icon.
                    </p>

                    <div class="hr hr-12 hr-double"></div>

                    <p>
                        Currently using
                        <b>36% of your storage space</b>.
                    </p>
                </div>
            </div>
            <!-- /.row -->
        </div>
    </div>
</div>
<pl:contentFooter/>
<%@include file="/WEB-INF/jsp/common/import-datetimepicker-js.jspf" %>
<%@include file="/WEB-INF/jsp/common/import-bootstrap-paginator-js.jspf" %>
<script type="text/javascript">
    jQuery(function ($) {
        $.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
            _title: function(title) {
                var $title = this.options.title || '&nbsp;'
                if( ("title_html" in this.options) && this.options.title_html == true )
                    title.html($title);
                else title.text($title);
            }
        }));
        $("#leagueId").change(function () {
            var selectedValue = $(this).val();
            $.ajax({type: "post", url: "${ctx}/game/findTeamsByCountry", dataType: "json",
                data: {"countryId": selectedValue},
                success: function (data) {
                    $("#homeTeam").html("");
                    $("#homeTeam").append("<option value=\"0\"></option>");
                    $("#customTeam").html("");
                    $("#customTeam").append("<option value=\"0\"></option>");
                    $.each(data, function (i, item) {
                        $("#homeTeam").append("<option value=" + item.id + ">" + item.name + "</option>");
                        $("#customTeam").append("<option value=" + item.id + ">" + item.name + "</option>");
                    });
                }
            });
        });
        $("#myForm").validationEngine({
            promptPosition: "topRight",
            autoPositionUpdate: true,
            scroll: false
        });
        $("#btnQuery").click(function () {
            if ($("#myForm").validationEngine("validate")) {
                $.ajax({
                    url: "${ctx}/odd/queryResult",
                    type: "post",
                    dataType: "json",
                    data: $("#myForm").serialize(),
                    success: function (data) {
                        if (data != null) {
                            var lineNumber = 1;
                            $("tbody").html("");
                            $.each(data.list, function (index, item) {
                                reloadTable(item, lineNumber);
                                lineNumber++;
                            });
                            var pageCount = data.totalPage;//总页数
                            var currentPage = data.toPage;//当前页
                            //分页功能
                            var options = {
                                bootstrapMajorVersion: 3,
                                currentPage: currentPage,
                                totalPages: pageCount,
                                numberOfPages: 10,
                                itemTexts: function (type, page, current) {
                                    switch (type) {
                                        case "first":
                                            return "首页";
                                        case "prev":
                                            return "上一页";
                                        case "next":
                                            return "下一页";
                                        case "last":
                                            return "末页";
                                        case "page":
                                            return page;
                                    }
                                },
                                tooltipTitles: function (type, page, current) {
                                    switch (type) {
                                        case "first":
                                            return "前往首页";
                                        case "prev":
                                            return "前往上一页";
                                        case "next":
                                            return "前往下一页";
                                        case "last":
                                            return "前往末页";
                                        case "page":
                                            return (page === current) ? "当前是第" + page + "页" : "前往第" + page + "页 ";
                                    }
                                },//点击事件，用于通过Ajax来刷新整个list列表
                                onPageClicked: function (event, originalEvent, type, page) {
                                    $.ajax({
                                        url: "${ctx}/odd/queryResult?page=" + page,
                                        type: "post",
                                        dataType: "json",
                                        data: $("#myForm").serialize(),
                                        success: function (data) {
                                            if (data != null) {
                                                var lineNumber = 1;
                                                $("tbody").html("");
                                                $.each(data.list, function (index, item) {
                                                    reloadTable(item, lineNumber);
                                                    lineNumber++;
                                                });
                                            }
                                        }
                                    });
                                }
                            };
                            $('#page1').bootstrapPaginator(options);
                        }
                    }
                });
            }
        });
    });
    function validCustTeam(field, rules, i, options) {
        var customTeam = $("#customTeam").val();
        var homeTeam = $("#homeTeam").val();
        if ((customTeam != 0) && (homeTeam != 0) && (customTeam == homeTeam)) {
            return options.allrules.customTeam.alertText;
        }
    }
    function reloadTable(item, lineNumber) {
        var row = $("<tr></tr>");
        row.append('<td class="center" width="5%" style="vertical-align: middle;">' + lineNumber + '</td>');
        row.append('<td style="vertical-align: middle;">' + item.actualTime + '</td>');
        row.append('<td style="vertical-align: middle;">' + item.seasonName + '</td>');
        row.append('<td style="vertical-align: middle;">' + item.leagueName + '</td>');
        row.append('<td style="vertical-align: middle;"><a href = "${ctx}/bd/team/showTeam?teamId='+item.homeTeam+'">' + item.homeTeamName + '</a></td>');
        row.append('<td style="vertical-align: middle;">' + item.resultStr + '</td>');
        row.append('<td style="vertical-align: middle;"><a href = "${ctx}/bd/team/showTeam?teamId='+item.customTeam+'">' + item.customTeamName + '</a></td>');
        if(typeof(item.round) =="undefined"){
            row.append('<td style="vertical-align: middle;">未知</td>');
        }else{
            row.append('<td style="vertical-align: middle;">' + item.round + '</td>');
        }
        if (item.gameStatus == 1) {
            row.append('<td style="vertical-align: middle;">已赛</td>');
        } else if (item.gameStatus == 0) {
            row.append('<td style="vertical-align: middle;">未赛</td>');
        } else {
            row.append('<td style="vertical-align: middle;">推迟</td>');
        }
        row.append('<td ><a href="javascript:void(0)"  class="btn btn-app btn-primary no-radius btn-xs" title="录入赔率" onclick="showOddAdd('+item.id+')"><i class="ace-icon fa fa-plus"></i></a>&nbsp;<a href="javascript:void(0)"  class="btn  btn-app btn-purple no-radius btn-xs" title="查看赔率"><i class="ace-icon fa fa-search"></i><span class="label label-inverse arrowed-in">' + item.oddCounts + '</span></a></td>');
        $("tbody").append(row);
    }
    function showOddAdd(gameId){
        var dialog = $( "#dialog-message" ).removeClass('hide').dialog({
            modal: true,
            height: 330,
            width:300,
            title: "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='ace-icon fa fa-plus'></i>添加赔率</h4></div>",
            title_html: true,
            buttons: [
                {
                    text: "Cancel",
                    "class" : "btn btn-xs",
                    click: function() {
                        $( this ).dialog( "close" );
                    }
                },
                {
                    text: "OK",
                    "class" : "btn btn-primary btn-xs",
                    click: function() {
                        $( this ).dialog( "close" );
                    }
                }
            ]
        });
    }
</script>
