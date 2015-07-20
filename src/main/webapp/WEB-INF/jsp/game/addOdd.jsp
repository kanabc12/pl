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
        <tbody id="gameResult">
        </tbody>
    </table>
</div>
<div class="col-sm-12">
    <ul id="page1"></ul>
</div>
<div id="dialog-message" class="hide">
    <form class="form-horizontal" id="oddForm">
        <div>
            <label class="control-label no-padding-right" for="addDate">
                更新时间 </label>
            <input class="datetime-picker input-sm" id="addDate" type="text" style="z-index: 1000;!important"
                   data-format="yyyy-mm-dd hh:mm:00" name="addDate"/>
            <label class=" control-label no-padding-right" for="leagueName">
                赛事名称 </label>
            <input class="input-sm" type="text" name="leagueName" id="leagueName" readonly="true">
        </div>
        <div style="margin-top: 30px;">
            <label class=" control-label no-padding-right" for="winOdd">
                主胜赔率 </label>
            <input class="input-sm validate[required,custom[number]]" type="text" placeholder="请输入主胜赔率" id="winOdd"
                   name="winOdd">
            <label class=" control-label no-padding-right" for="seasonName">
                赛季名称 </label>
            <input class="input-sm" type="text" placeholder="请输入主负赔率" id="seasonName" name="seasonName" readonly="true">
        </div>
        <div style="margin-top: 30px;">
            <label class="control-label no-padding-right" for="drawOdd">
                主平赔率 </label>
            <input class="input-sm validate[required,custom[number]]" type="text" placeholder="请输入主平赔率" id="drawOdd"
                   name="drawOdd">
            <label class=" control-label no-padding-right" for="homeTeamName">
                主队名称 </label>
            <input class="input-sm" type="text" name="homeTeamName" id="homeTeamName" readonly="true">
        </div>
        <div style="margin-top: 30px;">
            <label class=" control-label no-padding-right" for="loseOdd">
                主负赔率 </label>
            <input class="input-sm validate[required,custom[number]]" type="text" placeholder="请输入主负赔率" id="loseOdd"
                   name="loseOdd">
            <label class="control-label no-padding-right" for="customTeamName">
                客队名称 </label>
            <input class="input-sm" type="text" name="customTeamName" id="customTeamName" readonly="true">
        </div>
        <div style="margin-top: 30px;">
            <label class=" control-label no-padding-right" for="companyId">
                博彩公司 </label>
            <select id="companyId" name="companyId" style="width: 159px;">
                <option value="1">威廉希尔</option>
                <option value="2">立博</option>
            </select>
            <label class=" control-label no-padding-right" for="planDate">
                比赛时间 </label>
            <input class="input-sm" type="text" placeholder="请输入主负赔率" id="planDate" name="planDate" readonly="true">
        </div>
        <div style="margin-top: 30px;">
            <label class=" control-label no-padding-right" for="companyId">
                赔率性质 </label>
            <select id="oddType" name="oddType" style="width: 159px;">
                <option value="1">初始赔率</option>
                <option value="3">瞬时赔率</option>
                <option value="2">最终赔率</option>
            </select>
            <label class=" control-label no-padding-right" for="gameRound">
                赛事轮次 </label>
            <input class="input-sm" type="text" name="gameRound" id="gameRound" readonly="true">
        </div>
    </form>

</div>
<div id="oddResult" class="row hide">
    <div class="col-sm-12">
        <div class="col-xs-12 col-sm-6 widget-container-col ui-sortable" style="min-height: 263px;">
            <div class="widget-box widget-color-blue ui-sortable-handle" style="opacity: 1; z-index: 0;">
                <!-- #section:custom/widget-box.options -->
                <div class="widget-header">
                    <h5 class="widget-title bigger lighter">
                        <i class="ace-icon fa fa-table"></i>
                        威廉赔率
                    </h5>
                </div>
                <!-- /section:custom/widget-box.options -->
                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <table class="table table-striped table-bordered table-hover">
                            <thead class="thin-border-bottom">
                            <tr>
                                <th>序号</th>
                                <th>
                                    胜赔
                                </th>

                                <th>
                                    平赔
                                </th>
                                <th>负赔</th>
                                <th>赔率性质</th>
                                <th>更新时间</th>
                            </tr>
                            </thead>
                            <tbody id="wOdds">
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-12 col-sm-6 widget-container-col ui-sortable" style="min-height: 263px;">
            <div class="widget-box widget-color-blue ui-sortable-handle" style="opacity: 1; z-index: 0;">
                <div class="widget-header">
                    <h5 class="widget-title bigger lighter">
                        <i class="ace-icon fa fa-table"></i>
                        立博赔率
                    </h5>
                </div>
                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <table class="table table-striped table-bordered table-hover">
                            <thead class="thin-border-bottom">
                            <tr>
                                <th>序号</th>
                                <th>
                                    胜赔
                                </th>
                                <th>
                                    平赔
                                </th>
                                <th>负赔</th>
                                <th>赔率性质</th>
                                <th>更新时间</th>
                            </tr>
                            </thead>
                            <tbody id="lOdds">
                            </tbody>
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
</div>
</div>
<pl:contentFooter/>
<%@include file="/WEB-INF/jsp/common/import-datetimepicker-js.jspf" %>
<%@include file="/WEB-INF/jsp/common/import-bootstrap-paginator-js.jspf" %>
<script type="text/javascript">
jQuery(function ($) {
    $.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
        _title: function (title) {
            var $title = this.options.title || '&nbsp;'
            if (("title_html" in this.options) && this.options.title_html == true)
                title.html($title);
            else title.text($title);
        }
    }));
    $('.datetime-picker').datetimepicker({
        format: "YYYY-MM-DD HH:mm:ss",
        locale: "zh-cn"
    });
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
    $("#oddForm").validationEngine({
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
                        $("#gameResult").html("");
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
                                            $("#gameResult").html("");
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
    row.append('<td style="vertical-align: middle;"><a href = "${ctx}/bd/team/showTeam?teamId=' + item.homeTeam + '">' + item.homeTeamName + '</a></td>');
    if (typeof(item.resultStr) == "undefined") {
        row.append('<td style="vertical-align: middle;">未赛</td>');
    } else {
        row.append('<td style="vertical-align: middle;">' + item.resultStr + '</td>');
    }
    row.append('<td style="vertical-align: middle;"><a href = "${ctx}/bd/team/showTeam?teamId=' + item.customTeam + '">' + item.customTeamName + '</a></td>');
    if (typeof(item.round) == "undefined") {
        row.append('<td style="vertical-align: middle;">未知</td>');
    } else {
        row.append('<td style="vertical-align: middle;">' + item.round + '</td>');
    }
    if (item.gameStatus == 1) {
        row.append('<td style="vertical-align: middle;">已赛</td>');
    } else if (item.gameStatus == 0) {
        row.append('<td style="vertical-align: middle;">未赛</td>');
    } else {
        row.append('<td style="vertical-align: middle;">推迟</td>');
    }
    row.append('<td ><a href="javascript:void(0)"  class="btn btn-app btn-primary no-radius btn-xs" title="录入赔率" onclick="showOddAdd(' + item.id + ')"><i class="ace-icon fa fa-plus"></i></a>&nbsp;<a href="javascript:void(0)"  class="btn  btn-app btn-purple no-radius btn-xs" title="查看赔率" onclick="showGameOdd(' + item.id + ')"><i class="ace-icon fa fa-search"></i><span class="label label-inverse arrowed-in" id="span' + item.id + '">' + item.oddCounts + '</span></a></td>');
    $("#gameResult").append(row);
}
function showOddAdd(gameId) {
    $.ajax({
        url: "${ctx}/game/getResultById",
        type: "post",
        dataType: "json",
        data: {
            gameId: gameId
        },
        success: function (data) {
            if (data != null) {
                fillFormData(data);
                var dialog = $("#dialog-message").removeClass('hide').dialog({
                    modal: true,
                    height: 480,
                    width: 490,
                    title: "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='ace-icon fa fa-plus'></i>添加赔率</h4></div>",
                    title_html: true,
                    zIndex: 10,
                    close: function (event, ui) {
                        clearFormData();
                    },
                    buttons: [
                        {
                            text: "取消",
                            "class": "btn btn-xs",
                            click: function () {
                                clearFormData();
                                $("#dialog-message").dialog("close");
                            }
                        },
                        {
                            text: "保存",
                            "class": "btn btn-primary btn-xs",
                            click: function () {
                                if ($("#oddForm").validationEngine("validate")) {
                                    $.ajax({
                                        url: "${ctx}/odd/saveOdd",
                                        type: "post",
                                        dataType: "json",
                                        data: {
                                            gemeId: gameId,
                                            winOdd: $("#winOdd").val(),
                                            drawOdd: $("#drawOdd").val(),
                                            loseOdd: $("#loseOdd").val(),
                                            companyId: $("#companyId").val(),
                                            addDate: $("#addDate").val(),
                                            type: $("#oddType").val()
                                        },
                                        success: function (data) {
                                            if (data != null) {
                                                if (data == 1) {
                                                    var oddCount = $("#span" + gameId).text();
                                                    var newOddCount = parseInt(oddCount) + 1;
                                                    $("#span" + gameId).text(newOddCount);
                                                    clearFormData();
                                                    $("#dialog-message").dialog("close");
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    ]
                });
            }
        }
    });
}
function clearFormData() {
    $('#oddForm')[0].reset()
}

function fillFormData(data) {
    $("#leagueName").val(data.leagueName);
    if (typeof(data.round) == "undefined") {
        $("#gameRound").val("未知");
    } else {
        $("#gameRound").val(data.round);
    }
    $("#homeTeamName").val(data.homeTeamName);
    $("#customTeamName").val(data.customTeamName);
    $("#planDate").val(data.planDate);
    $("#seasonName").val(data.seasonName);
}
function showGameOdd(gameId) {
    $.ajax({
        url: "${ctx}/odd/getOddsByGameId",
        type: "post",
        dataType: "json",
        data: {
            gameId: gameId
        },
        success: function (data) {
            if (data != null) {
                var wOddObj = $("#wOdds");
                var lOddObj = $("#lOdds");
                $("#wOdds").html("");
                $("#lOdds").html("");
                for (var i = 0; i < data.wOdds.length; i++) {
                    fillOddResult(data.wOdds[i], wOddObj, i + 1);
                }
                for (var i = 0; i < data.lOdds.length; i++) {
                    fillOddResult(data.lOdds[i], lOddObj, i + 1);
                }
                var dialog = $("#oddResult").removeClass('hide').dialog({
                    modal: true,
                    height: 500,
                    width: 900,
                    title: "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='ace-icon fa fa-search'></i>查看赔率</h4></div>",
                    title_html: true,
                    zIndex: 10
                });
            }
        }
    });

    function fillOddResult(item, obj, lineNumber) {
        var row = $("<tr></tr>");
        row.append('<td class="center" width="5%" style="vertical-align: middle;">' + lineNumber + '</td>');
        row.append('<td style="vertical-align: middle;">' + item.winOdd + '</td>');
        row.append('<td style="vertical-align: middle;">' + item.drawOdd + '</td>');
        row.append('<td style="vertical-align: middle;">' + item.loseOdd + '</td>');
        if (item.type == 1) {
            row.append('<td style="vertical-align: middle;">初始赔率</td>');
        } else if (item.type == 2) {
            row.append('<td style="vertical-align: middle;">最终赔率 </td>');
        } else {
            row.append('<td style="vertical-align: middle;">瞬时赔率</td>');
        }
        row.append('<td style="vertical-align: middle;">' + item.addDate + '</td>');
        obj.append(row);
    }
}
</script>
