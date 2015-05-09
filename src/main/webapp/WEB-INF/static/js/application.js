//自己扩展的jquery函数
//压缩时请把编码改成ANSI
$.app = {
    initDatetimePicker : function() {
        //初始化 datetime picker
        $('.date:not(.custom)').each(function() {
            var $date = $(this);
            alert("aaa")
            if($date.attr("initialized") == "true") {
                return;
            }
            var pickDate = $(this).find("[data-format]").data("format").toLowerCase().indexOf("yyyy-mm-dd") != -1;
            var pickTime = $(this).find("[data-format]").data("format").toLowerCase().indexOf("hh:mm:ss") != -1;
            $date.datetimepicker({
                pickDate : pickDate,
                pickTime : pickTime,
                maskInput: true
//                language:"zh-CN"
            }).on('changeDate', function(ev) {
                    if(pickTime == false) {
                        $(this).data("datetimepicker").hide();
                    }
                });
            $date.find(":input").click(function() {$date.find(".fa-calendar,.fa-time,.fa-date").click();});
            $date.attr("initialized", true);
        });
    }
};

/*$(function () {
    $.ajaxSetup({ cache: true });
    $(".table").each(function() {
        $.table.initTable($(this));
    });
    $.app.initDatetimePicker();
    $.layout = top.$.layout;
    $.tabs = top.$.tabs;
    $.menus = top.$.menus;
    $("[data-toggle='tooltip']").each(function() {
        $(this).tooltip({delay:300});
    });
    $(document).ajaxError(function(event, request, settings) {
        $.app.waitingOver();
        if(request.status == 0) {// 中断的不处理
            return;
        }
        top.$.app.alert({
            title : "网络故障/系统故障",
            //<refresh>中间的按钮在ajax方式中删除不显示
            message : request.responseText.replace(/(<refresh>.*<\/refresh>)/g, "")
        });
    });
});*/

