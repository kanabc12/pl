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
                                    <option value="0"></option>
                                    <c:forEach items="${teamList}" var="c">
                                        <option value="${c.id}">${c.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <label class="col-sm-1 control-label no-padding-right" for="homeTeam">
                                客场球队 </label>

                            <div class="col-sm-3">
                                <select class="col-sm-12 validate[funcCall[validCustTeam]]" id="customTeam" name="customTeam">
                                    <option value="0"></option>
                                    <c:forEach items="${teamList}" var="c">
                                        <option value="${c.id}">${c.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-sm-1"></div>
                            <div class="col-sm-2 no-padding-right">
                                <button class="btn btn-sm btn-info btn-block right" type="button" id="btnQuery">
                                    <i class="ace-icon fa fa-search"></i> 查询
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
        $("#leagueId").change(function () {
            var selectedValue = $(this).val();
            $.ajax({type: "post", url: "${ctx}/game/findTeamsByCountry", dataType: "json",
                data: {"countryId": selectedValue},
                success: function (data) {
                    $("#homeTeam").html("");
                    $("#homeTeam").append("<option value=0></option>");
                    $("#customTeam").html("");
                    $("#customTeam").append("<option value=0></option>");
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
        var customTeam = $("#customTeam").val();
        var homeTeam = $("#homeTeam").val();
        if((customTeam!=0)&&(homeTeam!=0)&&(customTeam==homeTeam)){
            return options.allrules.customTeam.alertText;
        }
    }
</script>
