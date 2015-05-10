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
                <h1>比赛信息录入</h1>
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
                                    <c:forEach items="${seasonList}" var="c">
                                        <option value="${c.id}">${c.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <label class="col-sm-1 control-label no-padding-right"
                                   for="round"> 赛季轮次 </label>

                            <div class="col-sm-3">
                                <select class="col-sm-12" id="round" name="round">
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
                            <label class="col-sm-1 control-label no-padding-right" for="homeTeam">
                                客场球队 </label>

                            <div class="col-sm-3">
                                <select class="col-sm-12 validate[funcCall[validCustTeam]]" id="customTeam" name="customTeam">
                                    <c:forEach items="${teamList}" var="c">
                                        <option value="${c.id}">${c.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <label class="col-sm-1 control-label no-padding-right"
                                   for="homeGoals"> 客队进球 </label>

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
                                    <input class="datetime-picker col-xs-12" id="planDate" type="text"
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
                        <div class="clearfix form-actions">
                            <div class="col-md-offset-5 col-md-9">
                                <button class="btn btn-info" type="submit">
                                    <i class="ace-icon fa fa-check bigger-110"></i> 保存
                                </button>

                                &nbsp; &nbsp; &nbsp;
                                <button class="btn" type="reset">
                                    <i class="ace-icon fa fa-undo bigger-110"></i> 重置
                                </button>
                            </div>
                        </div>
                    </form>
                    <!-- PAGE CONTENT ENDS -->
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->
        </div>
    </div>
</div>
<pl:contentFooter/>
<%@include file="/WEB-INF/jsp/common/import-datetimepicker-js.jspf" %>
<script type="text/javascript">
    jQuery(function ($) {
        $('.datetime-picker').datetimepicker({
            format: "YYYY-MM-DD HH:mm:00",
            locale: "zh-cn"
        });
        $("#country").change(function () {
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
        $("#myForm").validationEngine({
            promptPosition: "topRight",
            autoPositionUpdate: true,
            scroll: false
        });
    });
    function validCustTeam(field, rules, i, options){
        if($("#customTeam").val() ==$("#homeTeam").val()){
            return options.allrules.customTeam.alertText;
        }
    }
</script>
