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
                <h1>赔率信息分析</h1>
            </div>
            <!-- /.page-header -->
            <div class="row">
                <div class="col-sm-12">
                    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
                    <div id="main" style="height:400px" class="col-sm-6"></div>
                </div>
            </div>
            <!-- /.row -->
        </div>
    </div>
</div>
<pl:contentFooter/>
<%@include file="/WEB-INF/jsp/common/import-echarts-js.jspf" %>
<script type="text/javascript">
    require.config({
        paths: {
            echarts: "${ctx}/static/comp/echarts"
        }
    });
    // 使用
    require(
            [
                'echarts',
                'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('main'));

                var option = {"legend":{"data":["主场胜","主场平","主场负","客场胜","客场平","客场负"],"orient":"vertical","x":"left"},"series":[{"data":[{"name":"主场胜","value":5},{"name":"主场平","value":3},{"name":"主场负","value":1},{"name":"客场胜","value":6},{"name":"客场平","value":2},{"name":"客场负","value":2}],"name":"场次","type":"pie"}],"title":{"text":"切尔西","x":"center"},"tooltip":{"formatter":"{a} <br/>{b} : {c} ({d}%)","trigger":"item"}};

                // 为echarts对象加载数据
                myChart.setOption(option);
            }
    );
</script>
