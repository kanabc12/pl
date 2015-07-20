<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf" %>
<pl:contentHeader title="足彩统计分析系统"/>
<%@include file="/WEB-INF/jsp/common/import-datetimepicker-css.jspf" %>
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
                <h1>联赛结果录入</h1>
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
                                    <option value="0">请选择---</option>
                                    <c:forEach items="${seasonList}" var="c">
                                        <option value="${c.id}">${c.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <label class="col-sm-1 control-label no-padding-right"
                                   for="round"> 赛季轮次 </label>

                            <div class="col-sm-3">
                                <select class="col-sm-12" id="round" name="round">
                                    <option value="0">请选择---</option>
                                    <c:forEach var="i" begin="1" end="38" step="1">
                                        <option value="${i}">第${i}轮</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-1 control-label no-padding-right"
                                   for="gameStatus"> 赛季状态 </label>
                            <div class="col-sm-3">
                                <select class="col-sm-12" id="gameStatus" name="gameStatus">
                                        <option value="">请选择---</option>
                                        <option value="0">未赛</option>
                                        <option value="1">已赛</option>
                                        <option value="2">推迟</option>
                                </select>
                            </div>
                            <label class="col-sm-1 control-label no-padding-right"
                                   for="planDate"> 原定时间 </label>

                            <div class="col-sm-3">
                                <div class="col-sm-12 input-group">
                                    <input class="datetime-picker col-xs-12" id="planDate" type="text"
                                           name="planDate"/>
																<span class="input-group-addon">
																	<i class="fa fa-calendar bigger-110"></i>
																</span>
                                </div>
                            </div>
                            <div class="col-md-offset-1 col-sm-3">
                                <button class="btn btn-sm btn-info btn-block right"  type="button"
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
                    <table id="gameTable"
                           data-toggle="table"
                           data-height="449"
                           data-row-style="rowStyle"
                           data-locale="zh-CN"
                           data-page-list="[10,20,50]"
                           data-id-field="id"
                           data-pagination="true" >
                        <thead>
                        <tr>
                            <th data-field="seasonName">赛季</th>
                            <th data-field="leagueName">联赛</th>
                            <th data-field="homeTeamName">主队</th>
                            <th data-field="resultStr">比分</th>
                            <th data-field="customTeamName">客队</th>
                            <th data-field="round" data-formatter="roundFormatter">轮次</th>
                            <th data-field="gameStatus"  data-formatter="statusFormatter">状态</th>
                            <th data-field="planDate">计划时间</th>
                            <th  align="center" data-formatter="operateFormatter" data-events="operateEvents">操作</th>
                        </tr>
                        </thead>
                    </table>
                </div>
                <div class="modal fade" id="myModal">
                    <div class="modal-dialog" style="width: 900px;">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" onclick="cancelModal()" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title">联赛结果录入</h4>
                            </div>
                            <div class="modal-body">
                                <form class="form-horizontal" role="form" id="myFormInput"
                                      action="${ctx}/game/saveGame" method="post">
                                    <pl:showMessage/>
                                    <input type="hidden" name="id"  id="id">
                                    <input type="hidden" name="gameStatus"  id="gameStatusInput">
                                    <div class="form-group">
                                        <label class="col-sm-1 control-label no-padding-right"
                                               for="leagueId"> 所属联赛 </label>

                                        <div class="col-sm-3">
                                            <select class="col-sm-12" id="leagueIdInput" name="leagueId">
                                                <c:forEach items="${leagueList}" var="c">
                                                    <option value="${c.id}">${c.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label class="col-sm-1 control-label no-padding-right"
                                               for="seasonId"> 所属赛季 </label>

                                        <div class="col-sm-3">
                                            <select class="col-sm-12" id="seasonIdInput" name="seasonId">
                                                <c:forEach items="${seasonList}" var="c">
                                                    <option value="${c.id}">${c.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label class="col-sm-1 control-label no-padding-right"
                                               for="round"> 赛季轮次 </label>

                                        <div class="col-sm-3">
                                            <select class="col-sm-12" id="roundInput" name="round">
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
                                                <c:forEach items="${teamList}" var="c">
                                                    <option value="${c.id}">${c.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label class="col-sm-1 control-label no-padding-right"
                                               for="homeGoals"> 主队进球 </label>

                                        <div class="col-sm-3">
                                            <input type="text" id="homeGoals" placeholder="请填写主队进球数"
                                                   class="col-sm-12 validate[required,custom[integer]]" name="homeGoals" />
                                        </div>
                                        <label class="col-sm-1 control-label no-padding-right"
                                               for="homeHalfGoals"> 半场进球 </label>

                                        <div class="col-sm-3">
                                            <input type="text" id="homeHalfGoals" placeholder="请填写主队半场进球"
                                                   class="col-sm-12 validate[required,custom[integer]]" name="homeHalfGoals"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-1 control-label no-padding-right" for="customTeam">
                                            客场球队 </label>

                                        <div class="col-sm-3">
                                            <select class="col-sm-12 validate[funcCall[validCustTeam]]" id="customTeam" name="customTeam">
                                                <c:forEach items="${teamList}" var="c">
                                                    <option value="${c.id}">${c.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label class="col-sm-1 control-label no-padding-right"
                                               for="customGoals"> 客队进球 </label>

                                        <div class="col-sm-3">
                                            <input type="text" id="customGoals" placeholder="请填写客队进球数"
                                                   class="col-sm-12 validate[required,custom[integer]]" name="customGoals"/>
                                        </div>
                                        <label class="col-sm-1 control-label no-padding-right"
                                               for="customHalfGoals"> 半场进球 </label>

                                        <div class="col-sm-3">
                                            <input type="text" id="customHalfGoals" placeholder="请填写客队半场进球"
                                                   class="col-sm-12 validate[required,custom[integer]]" name="customHalfGoals"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-1 control-label no-padding-right"
                                               for="gameCity"> 比赛城市 </label>

                                        <div class="col-sm-3">
                                            <input type="text" id="gameCity" placeholder="请输入比赛城市名"
                                                   class="col-sm-12 validate[required,custom[chinese]]" name="gameCity"/>
                                        </div>
                                        <label class="col-sm-1 control-label no-padding-right"
                                               for="planDate"> 原定时间 </label>

                                        <div class="col-sm-3">
                                            <div class="col-sm-12 input-group">
                                                <input class="datetime-picker col-xs-12" id="planDateInput" type="text"
                                                       name="planDate"/>
																<span class="input-group-addon">
																	<i class="fa fa-calendar bigger-110"></i>
																</span>
                                            </div>
                                        </div>
                                        <label class="col-sm-1 control-label no-padding-right"
                                               for="actualTime"> 开球时间 </label>

                                        <div class="col-sm-3">
                                            <div class="col-sm-12 input-group">
                                                <input class="datetime-picker col-xs-12" id="actualTime" type="text"
                                                       data-format="yyyy-mm-dd hh:mm:ss" name="actualTime"/>
																<span class="input-group-addon">
																	<i class="fa fa-calendar bigger-110"></i>
																</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-1 control-label no-padding-right"
                                               for="leagueId"> 比赛总结 </label>

                                        <div class="col-sm-11">
                                            <textarea class="form-control" id="gameSummarize" placeholder="请输入比赛简要总结" name="gameSummarize"></textarea>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default"  onclick="cancelModal()">关闭</button>
                                <button type="button" id="btnAction" class="btn btn-primary" onclick="btnSaveResult()">保存</button>
                            </div>
                        </div><!-- /.modal-content -->
                    </div><!-- /.modal-dialog -->
                </div><!-- /.modal -->
            </div>
            <!-- /.row -->
        </div>
    </div>
</div>
<pl:contentFooter/>
<%@include file="/WEB-INF/jsp/common/import-bootstrap-table-js.jspf" %>
<%@include file="/WEB-INF/jsp/common/import-datetimepicker-js.jspf" %>
<script type="text/javascript">
    var currentRowNumber;
    jQuery(function ($) {
        $('.datetime-picker').datetimepicker({
            format: "YYYY-MM-DD HH:mm:00",
            locale: "zh-cn"
        });
        $("#btnQuery").click(function (){
            $("#gameTable").bootstrapTable('load', ajaxRequest());
        });
        $("#myFormInput").validationEngine({
            promptPosition: "topRight",
            autoPositionUpdate: true,
            scroll: false
        });
        $("#leagueId").change(function () {
            var selectedValue = $(this).val();
            $.ajax({type: "post", url: "${ctx}/game/findTeamsByCountry", dataType: "json",
                data: {"countryId": selectedValue},
                success: function (data) {
                    $("#homeTeam").html("");
                    $("#customTeam").html("");
                    $.each(data, function (i, item) {
                        $("#homeTeam").append("<option value=" + item.id + ">" + item.name + "</option>");
                        $("#customTeam").append("<option value=" + item.id + ">" + item.name + "</option>");
                    });
                }
            });
        });
    });
    function operateFormatter(value, row, index) {
        if(row.gameStatus==1){
            return [
                '<span style="display:block;width:auto;margin:0 auto;">',
                '<a class="like" href="javascript:void(0)" title="查看信息">',
                '<i class="ace-icon fa fa-search"></i>',
                '</a>  ',
                '&nbsp;&nbsp;',
                '<a class="modify" href="javascript:void(0)" title="修改信息">',
                '<i class="ace-icon fa fa-pencil-square-o"></i>',
                '</a>',
                 '</span>'
            ].join('');
        }else if(row.gameStatus==0){
            return [
                '<span style="display:block;width:auto;margin:0 auto;">',
                '<a class="like" href="javascript:void(0)" title="录入信息">',
                '<i class="ace-icon fa fa-pencil-square-o"></i>',
                '</a>  ',
                '</span>'
            ].join('');
        }

    }

    function ajaxRequest(){
        var  rows = [];
        $.ajax({
            type: "POST",
            url:"${ctx}/game/findLeagueGame",
            data:$('#myForm').serialize(),// 你的formid
            async: false,
            dataType: 'json',
            error: function(request) {
                alert("Connection error");
            },
            success: function(data) {
                if (data != null)
                    rows = data;
            }
        });
        return rows;
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
    window.operateEvents = {
        'click .like': function (e, value, row, index) {
            $("#roundInput").val(row.round);
            $("#planDateInput").val(row.planDate);
            $("#leagueIdInput").val(row.leagueId);
            $("#seasonIdInput").val(row.seasonId);
            $("#homeTeam").val(row.homeTeam);
            $("#customTeam").val(row.customTeam);
            $("#id").val(row.id);
            $("#gameStatusInput").val(row.gameStatus);
            if(row.gameStatus==1){
                $("#homeGoals").val(row.homeGoals);
                $("#homeHalfGoals").val(row.homeHalfGoals);
                $("#customGoals").val(row.customGoals);
                $("#customHalfGoals").val(row.customHalfGoals);
                $("#gameCity").val(row.gameCity);
                $("#actualTime").val(row.actualTime);
                $("#gameSummarize").val(row.gameSummarize);
                $("#btnAction").addClass("hide");
            }
            currentRowNumber = index;
            $("#myModal").modal({backdrop: 'static'});
            $("#myModal").on('shown.bs.modal', function(){
                var $this = $(this);
                var $modal_dialog = $this.find('.modal-dialog');
                var m_top = ( $(document).height() - $modal_dialog.height() )/2;
                $modal_dialog.css({'margin': m_top +'px auto'});
            });
        },
        'click .modify': function (e, value, row, index) {
            $("#roundInput").val(row.round);
            $("#planDateInput").val(row.planDate);
            $("#leagueIdInput").val(row.leagueId);
            $("#seasonIdInput").val(row.seasonId);
            $("#homeTeam").val(row.homeTeam);
            $("#customTeam").val(row.customTeam);
            $("#id").val(row.id);
            $("#homeGoals").val(row.homeGoals);
            $("#homeHalfGoals").val(row.homeHalfGoals);
            $("#customGoals").val(row.customGoals);
            $("#customHalfGoals").val(row.customHalfGoals);
            $("#gameCity").val(row.gameCity);
            $("#actualTime").val(row.actualTime);
            $("#gameStatusInput").val(row.gameStatus);
            $("#gameSummarize").val(row.gameSummarize);
            currentRowNumber = index;
            $("#myModal").modal({backdrop: 'static'});
            $("#myModal").on('shown.bs.modal', function(){
                var $this = $(this);
                var $modal_dialog = $this.find('.modal-dialog');
                var m_top = ( $(document).height() - $modal_dialog.height() )/2;
                $modal_dialog.css({'margin': m_top +'px auto'});
            });
        }
    };
    function btnSaveResult(){
        if($("#myFormInput").validationEngine("validate")){
            $.ajax({
                type: "POST",
                url:"${ctx}/game/updateGameResult",
                data:$('#myFormInput').serialize(),// 你的formid
                async: false,
                dataType: 'json',
                error: function(request) {
                    alert("操作失败");
                },
                success: function(data) {
                    if (data != null){
                        $("#gameTable").bootstrapTable('updateRow', {
                            index: currentRowNumber,
                            row: data
                        });
                        cancelModal();
                    }else{
                        alert("保存失败");
                    }
                }
            });
        }
    }

    function cancelModal(){
        $("#homeGoals").val("");
        $("#homeHalfGoals").val("");
        $("#customGoals").val("");
        $("#customHalfGoals").val("");
        $("#actualTime").val("");
        $("#gameSummarize").val("");
        $("#myFormInput").validationEngine("hideAll");
        $("#btnAction").removeClass("hide");
        $('#myModal').modal('hide');
    }
    function validCustTeam(field, rules, i, options){
        if($("#customTeam").val() ==$("#homeTeam").val()){
            return options.allrules.customTeam.alertText;
        }
    }
</script>
