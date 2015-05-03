<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf" %>
<pl:contentHeader title="足彩统计分析系统"/>
<%@include file="/WEB-INF/jsp/common/import-datetime-css.jspf" %>
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
                <h1>球队信息查询</h1>
            </div>
            <!-- /.page-header -->
            <div class="row">
                <div class="col-xs-12">
                    <!-- PAGE CONTENT BEGINS -->
                    <form class="form-horizontal" role="form"
                          action="" method="post">
                        <div class="form-group">
                            <label class="col-xs-1 control-label " for="name">
                                球队名称 :</label>
                            <input type="text" id="name" placeholder="请输入球队名称"
                                   class="col-xs-2 " name="name"/>
                            <label class="col-xs-1 control-label"
                                   for="contry"> 所属国家: </label>
                            <select class="col-xs-2" id="contry" name="contry">
                                <option value="0"></option>
                                <c:forEach items="${countryList}" var="c">
                                    <option value="${c.id}">${c.name}</option>
                                </c:forEach>
                            </select>
                            <label class="col-xs-1 control-label" for="type">
                                球队性质: </label>
                            <select class="col-xs-2" id="type" name="type">
                                <option value="0"></option>
                                <option value="1">俱乐部</option>
                                <option value="2">国家队</option>
                            </select>
                            <button class="btn btn-info right" type="button" id="btnQuery">
                                <i class="ace-icon fa fa-search"></i> 查询
                            </button>
                        </div>
                    </form>
                    <table id="grid-table"></table>
                    <div id="grid-pager"></div>
                    <!-- PAGE CONTENT ENDS -->
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->
        </div>
    </div>
</div>
<pl:contentFooter/>
<%@include file="/WEB-INF/jsp/common/import-datetime-js.jspf" %>
<%@include file="/WEB-INF/jsp/common/import-jqGrid-js.jspf" %>
<script type="text/javascript">
var  countryStr = "${countryStr}"
jQuery(function ($) {
    gritInit();
    $(window).on('resize.jqGrid', function () {
        $("#grid-table").jqGrid('setGridWidth', $(".page-content").width());
    });
    jQuery("#grid-table").navGrid("#grid-pager",
            { 	//navbar options
                edit: true,
                editicon: 'ace-icon fa fa-pencil blue',
                add: true,
                addicon: 'ace-icon fa fa-plus-circle purple',
                del: true,
                delicon: 'ace-icon fa fa-trash-o red',
                search: true,
                searchicon: 'ace-icon fa fa-search orange',
                refresh: true,
                refreshicon: 'ace-icon fa fa-refresh green',
                view: true,
                viewicon: 'ace-icon fa fa-search-plus grey'
            },
            {
                //edit record form
                //closeAfterEdit: true,
                //width: 700,
                recreateForm: true,
                beforeShowForm: function (e) {
                    var form = $(e[0]);
                    form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
                    style_edit_form(form);
                }
            },
            {
                //new record form
                //width: 700,
                closeAfterAdd: true,
                recreateForm: true,
                viewPagerButtons: false,
                beforeShowForm: function (e) {
                    var form = $(e[0]);
                    form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar')
                            .wrapInner('<div class="widget-header" />')
                    style_edit_form(form);
                }
            },
            {
                //delete record form
                recreateForm: true,
                beforeShowForm: function (e) {
                    var form = $(e[0]);
                    if (form.data('styled')) return false;

                    form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
                    style_delete_form(form);

                    form.data('styled', true);
                },
                onClick: function (e) {
                    alert(1);
                }
            },
            {
                //search form
                recreateForm: true,
                afterShowSearch: function (e) {
                    var form = $(e[0]);
                    form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
                    style_search_form(form);
                },
                afterRedraw: function () {
                    style_search_filters($(this));
                },
                multipleSearch: true
                /**
                 multipleGroup:true,
                 showQuery: true
                 */
            },
            {
                //view record form
                recreateForm: true,
                beforeShowForm: function (e) {
                    var form = $(e[0]);
                    form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
                }
            }
    );
    $("#btnQuery").click(function () {
        jQuery("#grid-table").jqGrid('setGridParam', {
            url: "${ctx}/bd/team/showEdit",
            datatype: "json",
            mtype: "POST",
            jsonReader: {
                id: "id"
            },
            postData: {name: $("#name").val(),
                contry: $("#contry").val(),
                type: $("#type").val()}
        }).trigger("reloadGrid");
    });
});
function style_edit_form(form) {
    //enable datepicker on "sdate" field and switches for "stock" field
    form.find('input[name=buildDate]').datepicker({format: 'yyyy-mm-dd', autoclose: true, language: "zh-CN"});
    //don't wrap inside a label element, the checkbox value won't be submitted (POST'ed)
    //.addClass('ace ace-switch ace-switch-5').wrap('<label class="inline" />').after('<span class="lbl"></span>');

    //update buttons classes
    var buttons = form.next().find('.EditButton .fm-button');
    buttons.addClass('btn btn-sm').find('[class*="-icon"]').hide();//ui-icon, s-icon
    buttons.eq(0).addClass('btn-primary').prepend('<i class="ace-icon fa fa-check"></i>');
    buttons.eq(1).prepend('<i class="ace-icon fa fa-times"></i>')

    buttons = form.next().find('.navButton a');
    buttons.find('.ui-icon').hide();
    buttons.eq(0).append('<i class="ace-icon fa fa-chevron-left"></i>');
    buttons.eq(1).append('<i class="ace-icon fa fa-chevron-right"></i>');
}
function gritInit() {
    jQuery("#grid-table").jqGrid({
        height: "auto",//高度，表格高度。可为数值、百分比或'auto'
        autowidth: true,
        shrinkToFit:true,
        caption: "查询结果",
        colNames: ['球队名称','英文名称','英文简写', '所属国家', '主教练', '建队日期', '球队性质', "编辑"],
        loadtext: "正在加载...",
        editurl: "${ctx}/bd/team/editTeam",//nothing is saved
        colModel: [
            {name: 'name', width: 30, editable: true},
            {name: 'nameEn', width: 80, editable: true},
            {name: 'nameAbbr', width: 30, editable: true},
            {name: 'contry', width: 30, editable: true,edittype:"select",formatter: "select", formatoptions:{value:countryStr}, editoptions: {dataUrl:"${ctx}/bd/team/ShowCountry"}},
            {name: 'coach', width: 50, align: "right", editable: true},
            {name: 'buildDate', index: 'buildDate', editable: true, width: 80, align: "right", unformat: pickDate},
            {name: 'type', width: 40, align: "right", editable: true, edittype:"select",formatter: "select", editoptions: {value: "1:俱乐部;2:国家队"}},
            {name: 'myac', index: '', width: 80, fixed: true, sortable: false, resize: false, align: "center",
                formatter: 'actions',
                formatoptions: {
                    keys: true,
                    delOptions: {recreateForm: true, beforeShowForm: beforeDeleteCallback}
                    //editformbutton:true, editOptions:{recreateForm: true, beforeShowForm:beforeEditCallback}
                }
            }
        ],
        altRows: true,
        multiselect: true,
        multiboxonly: true,
        rowNum: 10,
        rowList: [ 10, 20, 30 ],
        rownumbers: true,
        viewrecords: true,
        pager: $("#grid-pager"),
        loadComplete: function () {
            var table = this;
            setTimeout(function () {
                styleCheckbox(table);

                updateActionIcons(table);
                updatePagerIcons(table);
                enableTooltips(table);
            }, 0);
        }
    });
}
function updatePagerIcons(table) {
    var replacement =
    {
        'ui-icon-seek-first': 'ace-icon fa fa-angle-double-left bigger-140',
        'ui-icon-seek-prev': 'ace-icon fa fa-angle-left bigger-140',
        'ui-icon-seek-next': 'ace-icon fa fa-angle-right bigger-140',
        'ui-icon-seek-end': 'ace-icon fa fa-angle-double-right bigger-140'
    };
    $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function () {
        var icon = $(this);
        var $class = $.trim(icon.attr('class').replace('ui-icon', ''));

        if ($class in replacement) icon.attr('class', 'ui-icon ' + replacement[$class]);
    })
}
//it causes some flicker when reloading or navigating grid
//it may be possible to have some custom formatter to do this as the grid is being created to prevent this
//or go back to default browser checkbox styles for the grid
function styleCheckbox(table) {
    /**
     $(table).find('input:checkbox').addClass('ace')
     .wrap('<label />')
     .after('<span class="lbl align-top" />')


     $('.ui-jqgrid-labels th[id*="_cb"]:first-child')
     .find('input.cbox[type=checkbox]').addClass('ace')
     .wrap('<label />').after('<span class="lbl align-top" />');
     */
}
//unlike navButtons icons, action icons in rows seem to be hard-coded
//you can change them like this in here if you want
function updateActionIcons(table) {
    /**
     var replacement =
     {
         'ui-ace-icon fa fa-pencil' : 'ace-icon fa fa-pencil blue',
         'ui-ace-icon fa fa-trash-o' : 'ace-icon fa fa-trash-o red',
         'ui-icon-disk' : 'ace-icon fa fa-check green',
         'ui-icon-cancel' : 'ace-icon fa fa-times red'
     };
     $(table).find('.ui-pg-div span.ui-icon').each(function(){
						var icon = $(this);
						var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
						if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
					})
     */
}
function enableTooltips(table) {
    $('.navtable .ui-pg-button').tooltip({container: 'body'});
    $(table).find('.ui-pg-div').tooltip({container: 'body'});
}
function style_delete_form(form) {
    var buttons = form.next().find('.EditButton .fm-button');
    buttons.addClass('btn btn-sm btn-white btn-round').find('[class*="-icon"]').hide();//ui-icon, s-icon
    buttons.eq(0).addClass('btn-danger').prepend('<i class="ace-icon fa fa-trash-o"></i>');
    buttons.eq(1).addClass('btn-default').prepend('<i class="ace-icon fa fa-times"></i>')
}
function style_search_filters(form) {
    form.find('.delete-rule').val('X');
    form.find('.add-rule').addClass('btn btn-xs btn-primary');
    form.find('.add-group').addClass('btn btn-xs btn-success');
    form.find('.delete-group').addClass('btn btn-xs btn-danger');
}
function style_search_form(form) {
    var dialog = form.closest('.ui-jqdialog');
    var buttons = dialog.find('.EditTable')
    buttons.find('.EditButton a[id*="_reset"]').addClass('btn btn-sm btn-info').find('.ui-icon').attr('class', 'ace-icon fa fa-retweet');
    buttons.find('.EditButton a[id*="_query"]').addClass('btn btn-sm btn-inverse').find('.ui-icon').attr('class', 'ace-icon fa fa-comment-o');
    buttons.find('.EditButton a[id*="_search"]').addClass('btn btn-sm btn-purple').find('.ui-icon').attr('class', 'ace-icon fa fa-search');
}
function beforeDeleteCallback(e) {
    var form = $(e[0]);
    if (form.data('styled')) return false;
    form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
    style_delete_form(form);
    form.data('styled', true);
}
//enable datepicker
function pickDate(cellvalue, options, cell) {
    setTimeout(function () {
        $(cell).find('input[type=text]')
                .datepicker({format: 'yyyy-mm-dd', autoclose: true, language: "zh-CN"});
    }, 0);
}
</script>
