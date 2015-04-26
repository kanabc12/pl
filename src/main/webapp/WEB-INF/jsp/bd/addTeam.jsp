<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<pl:contentHeader title="足彩统计分析系统" />
<%@include file="/WEB-INF/jsp/common/import-calendar-css.jspf"%>
<pl:navbar />
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<script type="text/javascript">
		try {
			ace.settings.check('main-container', 'fixed')
		} catch (e) {
		}
	</script>
	<pl:sidebar />
	<div class="main-content">
		<pl:search />
		<div class="page-content">
			<pl:setting />
			<div class="page-header">
				<h1>球队信息录入</h1>
			</div>
			<!-- /.page-header -->
			<c:if test="${result eq '1'}">
				<div class="alert alert-block alert-success">
					<button type="button" class="close" data-dismiss="alert">
						<i class="ace-icon fa fa-times"></i>
					</button>
					<i class="ace-icon fa fa-check green"></i> <strong class="green">
						保存成功 </strong>
				</div>
			</c:if>
			<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->
					<form class="form-horizontal" role="form"
						action="${ctx}/bd/team/saveTeam" method="post">
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="name">
								球队名称 </label>
							<div class="col-sm-9">
								<input type="text" id="name" placeholder="请输入球队名称"
									class="col-xs-10 col-sm-5" name="name" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="nameAbbr"> 球队英文缩写 </label>

							<div class="col-sm-9">
								<input type="text" id="nameAbbr" placeholder="请输入球队英文缩写"
									class="col-xs-10 col-sm-5" name="nameAbbr" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="country"> 所属国家 </label>
							<div class="col-sm-9">
								<select class="col-xs-10 col-sm-5" id="country" name="contry">
									<c:forEach items="${countryList}" var="c">
										<option value="${c.id}">${c.name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="coach"> 主教练 </label>

							<div class="col-sm-9">
								<input type="text" id="coach" placeholder="请输入主教练姓名"
									class="col-xs-10 col-sm-5" name="coach" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="type">
								球队性质 </label>
							<div class="col-sm-9">
								<select class="col-xs-10 col-sm-5" id="type" name="type">
									<option value="1">俱乐部</option>
									<option value="2">国家队</option>
								</select>
							</div>
						</div>
						<div class="clearfix form-actions">
							<div class="col-md-offset-3 col-md-9">
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
<pl:contentFooter />

<script type="text/javascript">
	jQuery(function($) {
	});
</script>
