<%@tag pageEncoding="UTF-8" %>
<!-- #section:basics/sidebar -->
<div id="sidebar" class="sidebar responsive">
<script type="text/javascript">
    try {
        ace.settings.check('sidebar', 'fixed')
    } catch (e) {
    }
</script>

<div class="sidebar-shortcuts" id="sidebar-shortcuts">
    <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
        <button class="btn btn-success">
            <i class="ace-icon fa fa-signal"></i>
        </button>

        <button class="btn btn-info">
            <i class="ace-icon fa fa-pencil"></i>
        </button>

        <!-- #section:basics/sidebar.layout.shortcuts -->
        <button class="btn btn-warning">
            <i class="ace-icon fa fa-users"></i>
        </button>

        <button class="btn btn-danger">
            <i class="ace-icon fa fa-cogs"></i>
        </button>

        <!-- /section:basics/sidebar.layout.shortcuts -->
    </div>

    <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
        <span class="btn btn-success"></span>

        <span class="btn btn-info"></span>

        <span class="btn btn-warning"></span>

        <span class="btn btn-danger"></span>
    </div>
</div>
<!-- /.sidebar-shortcuts -->

<ul class="nav nav-list">
<li class="active">
    <a href="${ctx}/">
        <i class="menu-icon fa fa-tachometer"></i>
        <span class="menu-text"> 首页 </span>
    </a>

    <b class="arrow"></b>
</li>

<li class="">
    <a href="#" class="dropdown-toggle">
        <i class="menu-icon fa fa-desktop"></i>
        <span class="menu-text"> 基本信息 </span>

        <b class="arrow fa fa-angle-down"></b>
    </a>

    <b class="arrow"></b>

    <ul class="submenu">
        <li class="">
            <a href="#" class="dropdown-toggle">
                <i class="menu-icon fa fa-caret-right"></i>
					球队信息
                <b class="arrow fa fa-angle-down"></b>
            </a>

            <b class="arrow"></b>

            <ul class="submenu">
                <li class="">
                    <a href="${ctx}/bd/team/showAddTeam">
                        <i class="menu-icon fa fa-caret-right"></i>
                        球队信息录入
                    </a>

                    <b class="arrow"></b>
                </li>

                <li class="">
                    <a href="${ctx}/bd/team/showEdit">
                        <i class="menu-icon fa fa-caret-right"></i>
                        球队信息编辑
                    </a>

                    <b class="arrow"></b>
                </li>

                <li class="">
                    <a href="mobile-menu-2.html">
                        <i class="menu-icon fa fa-caret-right"></i>
                        Mobile Menu 2
                    </a>

                    <b class="arrow"></b>
                </li>

                <li class="">
                    <a href="mobile-menu-3.html">
                        <i class="menu-icon fa fa-caret-right"></i>
                        Mobile Menu 3
                    </a>

                    <b class="arrow"></b>
                </li>
            </ul>
        </li>

        <li class="">
            <a href="typography.html">
                <i class="menu-icon fa fa-caret-right"></i>
                Typography
            </a>

            <b class="arrow"></b>
        </li>

        <li class="">
            <a href="elements.html">
                <i class="menu-icon fa fa-caret-right"></i>
                Elements
            </a>

            <b class="arrow"></b>
        </li>

        <li class="">
            <a href="buttons.html">
                <i class="menu-icon fa fa-caret-right"></i>
                Buttons &amp; Icons
            </a>

            <b class="arrow"></b>
        </li>

        <li class="">
            <a href="treeview.html">
                <i class="menu-icon fa fa-caret-right"></i>
                Treeview
            </a>

            <b class="arrow"></b>
        </li>

        <li class="">
            <a href="jquery-ui.html">
                <i class="menu-icon fa fa-caret-right"></i>
                jQuery UI
            </a>

            <b class="arrow"></b>
        </li>

        <li class="">
            <a href="nestable-list.html">
                <i class="menu-icon fa fa-caret-right"></i>
                Nestable Lists
            </a>

            <b class="arrow"></b>
        </li>

        <li class="">
            <a href="#" class="dropdown-toggle">
                <i class="menu-icon fa fa-caret-right"></i>

                Three Level Menu
                <b class="arrow fa fa-angle-down"></b>
            </a>

            <b class="arrow"></b>

            <ul class="submenu">
                <li class="">
                    <a href="#">
                        <i class="menu-icon fa fa-leaf"></i>
                        Item #1
                    </a>

                    <b class="arrow"></b>
                </li>

                <li class="">
                    <a href="#" class="dropdown-toggle">
                        <i class="menu-icon fa fa-pencil"></i>

                        4th level
                        <b class="arrow fa fa-angle-down"></b>
                    </a>

                    <b class="arrow"></b>

                    <ul class="submenu">
                        <li class="">
                            <a href="#">
                                <i class="menu-icon fa fa-plus"></i>
                                Add Product
                            </a>

                            <b class="arrow"></b>
                        </li>

                        <li class="">
                            <a href="#">
                                <i class="menu-icon fa fa-eye"></i>
                                View Products
                            </a>

                            <b class="arrow"></b>
                        </li>
                    </ul>
                </li>
            </ul>
        </li>
    </ul>
</li>

<li class="">
    <a href="#" class="dropdown-toggle">
        <i class="menu-icon fa fa-list"></i>
        <span class="menu-text"> 数据导入 </span>

        <b class="arrow fa fa-angle-down"></b>
    </a>

    <b class="arrow"></b>

    <ul class="submenu">
        <li class="">
            <a href="${ctx}/excel/import">
                <i class="menu-icon fa fa-caret-right"></i>
                比分&赔率导入一
            </a>

            <b class="arrow"></b>
        </li>

        <li class="">
            <a href="${ctx}/excel/merge/import">
                <i class="menu-icon fa fa-caret-right"></i>
                比分&赔率导入二
            </a>
            <b class="arrow"></b>
        </li>
        <li class="">
            <a href="${ctx}/excel/schedule/import">
                <i class="menu-icon fa fa-caret-right"></i>
                联赛赛程导入一
            </a>
            <b class="arrow"></b>
        </li>
        <li class="">
            <a href="${ctx}/excel/merge/schedule/import">
                <i class="menu-icon fa fa-caret-right"></i>
                联赛赛程导入二
            </a>
            <b class="arrow"></b>
        </li>
    </ul>
</li>

<li class="">
    <a href="#" class="dropdown-toggle">
        <i class="menu-icon fa fa-pencil-square-o"></i>
        <span class="menu-text"> 赛事信息录入 </span>

        <b class="arrow fa fa-angle-down"></b>
    </a>

    <b class="arrow"></b>

    <ul class="submenu">
        <li class="">
            <a href="${ctx}/game/gameInputInit">
                <i class="menu-icon fa fa-caret-right"></i>
                单场比赛结果录入
            </a>

            <b class="arrow"></b>
        </li>

        <li class="">
            <a href="${ctx}/game/leagueGameInputInit">
                <i class="menu-icon fa fa-caret-right"></i>
                联赛结果录入
            </a>

            <b class="arrow"></b>
        </li>


        <li class="">
            <a href="${ctx}/odd/addOdd">
                <i class="menu-icon fa fa-caret-right"></i>
                赔率信息录入
            </a>

            <b class="arrow"></b>
        </li>

        <li class="">
            <a href="wysiwyg.html">
                <i class="menu-icon fa fa-caret-right"></i>
                Wysiwyg &amp; Markdown
            </a>

            <b class="arrow"></b>
        </li>

        <li class="">
            <a href="dropzone.html">
                <i class="menu-icon fa fa-caret-right"></i>
                Dropzone File Upload
            </a>

            <b class="arrow"></b>
        </li>
    </ul>
</li>

<li class="">
    <a href="#" class="dropdown-toggle">
        <i class="menu-icon fa  fa-folder"></i>
        <span class="menu-text"> 统计分析 </span>

        <b class="arrow fa fa-angle-down"></b>
    </a>

    <b class="arrow"></b>

    <ul class="submenu">
        <li class="">
            <a href="${ctx}/analyse/team/show">
                <i class="menu-icon fa fa-caret-right"></i>
                球队基本数据分析
            </a>

            <b class="arrow"></b>
        </li>

        <li class="">
            <a href="${ctx}/odd/addOdd">
                <i class="menu-icon fa fa-caret-right"></i>
                赔率信息录入
            </a>

            <b class="arrow"></b>
        </li>

        <li class="">
            <a href="wysiwyg.html">
                <i class="menu-icon fa fa-caret-right"></i>
                Wysiwyg &amp; Markdown
            </a>

            <b class="arrow"></b>
        </li>

        <li class="">
            <a href="dropzone.html">
                <i class="menu-icon fa fa-caret-right"></i>
                Dropzone File Upload
            </a>

            <b class="arrow"></b>
        </li>
    </ul>
</li>

<li class="">
    <a href="widgets.html">
        <i class="menu-icon fa fa-list-alt"></i>
        <span class="menu-text"> Widgets </span>
    </a>

    <b class="arrow"></b>
</li>

<li class="">
    <a href="calendar.html">
        <i class="menu-icon fa fa-calendar"></i>

							<span class="menu-text">
								Calendar

								<!-- #section:basics/sidebar.layout.badge -->
								<span class="badge badge-transparent tooltip-error" title="2 Important Events">
									<i class="ace-icon fa fa-exclamation-triangle red bigger-130"></i>
								</span>

								<!-- /section:basics/sidebar.layout.badge -->
							</span>
    </a>

    <b class="arrow"></b>
</li>

<li class="">
    <a href="gallery.html">
        <i class="menu-icon fa fa-picture-o"></i>
        <span class="menu-text"> Gallery </span>
    </a>

    <b class="arrow"></b>
</li>

<li class="">
    <a href="#" class="dropdown-toggle">
        <i class="menu-icon fa fa-tag"></i>
        <span class="menu-text"> More Pages </span>

        <b class="arrow fa fa-angle-down"></b>
    </a>

    <b class="arrow"></b>

    <ul class="submenu">
        <li class="">
            <a href="profile.html">
                <i class="menu-icon fa fa-caret-right"></i>
                User Profile
            </a>

            <b class="arrow"></b>
        </li>

        <li class="">
            <a href="inbox.html">
                <i class="menu-icon fa fa-caret-right"></i>
                Inbox
            </a>

            <b class="arrow"></b>
        </li>

        <li class="">
            <a href="pricing.html">
                <i class="menu-icon fa fa-caret-right"></i>
                Pricing Tables
            </a>

            <b class="arrow"></b>
        </li>

        <li class="">
            <a href="invoice.html">
                <i class="menu-icon fa fa-caret-right"></i>
                Invoice
            </a>

            <b class="arrow"></b>
        </li>

        <li class="">
            <a href="timeline.html">
                <i class="menu-icon fa fa-caret-right"></i>
                Timeline
            </a>

            <b class="arrow"></b>
        </li>

        <li class="">
            <a href="login.html">
                <i class="menu-icon fa fa-caret-right"></i>
                Login &amp; Register
            </a>

            <b class="arrow"></b>
        </li>
    </ul>
</li>

<li class="">
    <a href="#" class="dropdown-toggle">
        <i class="menu-icon fa fa-file-o"></i>

							<span class="menu-text">
								Other Pages

								<!-- #section:basics/sidebar.layout.badge -->
								<span class="badge badge-primary">5</span>

								<!-- /section:basics/sidebar.layout.badge -->
							</span>

        <b class="arrow fa fa-angle-down"></b>
    </a>

    <b class="arrow"></b>

    <ul class="submenu">
        <li class="">
            <a href="faq.html">
                <i class="menu-icon fa fa-caret-right"></i>
                FAQ
            </a>

            <b class="arrow"></b>
        </li>

        <li class="">
            <a href="error-404.html">
                <i class="menu-icon fa fa-caret-right"></i>
                Error 404
            </a>

            <b class="arrow"></b>
        </li>

        <li class="">
            <a href="error-500.html">
                <i class="menu-icon fa fa-caret-right"></i>
                Error 500
            </a>

            <b class="arrow"></b>
        </li>

        <li class="">
            <a href="grid.html">
                <i class="menu-icon fa fa-caret-right"></i>
                Grid
            </a>

            <b class="arrow"></b>
        </li>

        <li class="">
            <a href="blank.html">
                <i class="menu-icon fa fa-caret-right"></i>
                Blank Page
            </a>

            <b class="arrow"></b>
        </li>
    </ul>
</li>
</ul>
<!-- /.nav-list -->

<!-- #section:basics/sidebar.layout.minimize -->
<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
    <i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left"
       data-icon2="ace-icon fa fa-angle-double-right"></i>
</div>

<!-- /section:basics/sidebar.layout.minimize -->
<script type="text/javascript">
    try {
        ace.settings.check('sidebar', 'collapsed')
    } catch (e) {
    }
</script>
</div>