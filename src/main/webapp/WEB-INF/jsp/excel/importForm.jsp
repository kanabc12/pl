<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf" %>
<pl:contentHeader title="足彩统计分析系统"/>
<pl:navbar/>
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
                <h1>导入Excel比分以及赔率信息</h1>
            </div>
            <!-- /.page-header -->
            <div class="row">
                <div class="col-xs-12">
                    <!-- PAGE CONTENT BEGINS -->
                    <form class="form-horizontal" role="form" id="form"
                          action="${ctx}/excel/import" method="post" enctype="multipart/form-data">
                        <pl:showMessage/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right"
                                   for="file"> 导入Excel </label>
                            <div class="col-sm-4">
                                <input type="file" id="file"
                                       class="col-xs-10 col-sm-5 validate[required]" name="file"/>
                            </div>
                        </div>

                        <div class="clearfix form-actions">
                            <div class="col-md-offset-3 col-md-9">
                                <button class="btn btn-info" type="submit">
                                    <i class="ace-icon fa fa-upload bigger-110"></i> 导入
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
<script type="text/javascript">
    jQuery(function ($) {
        $('#file').ace_file_input({
            no_file: '请上传xlsx格式的Excel文件 ...',
            btn_choose: '选择',
            btn_change: '改变',
            droppable: false,
            onchange: null,
            thumbnail: false, //| true | large
            whitelist:'xlsx'
            //blacklist:'exe|php'
            //onchange:''
            //
        });
        var validationEngine = $("form").validationEngine({
            promptPosition : "topRight",
            autoPositionUpdate:true,
            scroll:false
        });
    });
</script>