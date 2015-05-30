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
    jQuery(function ($) {
        $('#tree1').treeview({
            data: getTree("${ctx}/analyse/team/getTreeData"),
            showTags:true,
            onNodeSelected:function(event, data) {
                console.log(data);
            }
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
    ;
</script>
