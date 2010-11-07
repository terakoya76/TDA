<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title" ignore="true" /></title>
<script type="text/javascript" src="${pageContext.request.contextPath}/<spring:theme code="js.base-var"/>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/<spring:theme code="js.jquery"/>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/<spring:theme code="js.jqueryui"/>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/<spring:theme code="js.tabbed-menu"/>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/<spring:theme code="js.tabbed-menu"/>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/<spring:theme code="js.chat"/>"></script>

<link  rel="stylesheet" href='${pageContext.request.contextPath}/<spring:theme code="css.chat"/>' type="text/css"/>
<link  rel="stylesheet" href='${pageContext.request.contextPath}/<spring:theme code="css.screen"/>' type="text/css"/>
<!--[if lte IE 7]>
<link  rel="stylesheet" href='${pageContext.request.contextPath}/<spring:theme code="css.screen_ie"/>' type="text/css"/>
<![endif]-->

<link  rel="stylesheet" href='${pageContext.request.contextPath}/<spring:theme code="layout.css"/>' type="text/css"/>
<link  rel="stylesheet" href='${pageContext.request.contextPath}/<spring:theme code="list.css"/>' type="text/css"/>
<link  rel="stylesheet" href='${pageContext.request.contextPath}/<spring:theme code="paginator.css"/>' type="text/css"/>	
<link  rel="stylesheet" href='${pageContext.request.contextPath}/<spring:theme code="form.css"/>' type="text/css"/>
<link  rel="stylesheet" href='${pageContext.request.contextPath}/<spring:theme code="jqueryui.css"/>' type="text/css"/>
<link  rel="stylesheet" href='${pageContext.request.contextPath}/<spring:theme code="tabbed-menu.css"/>' type="text/css"/>
</head>
<body>
<div class="main-container ">
	<div class="header-container">
		<tiles:insertAttribute name="header" />
	</div>
	<div class="menu-body-container">
		<div class="menu-container">
			<tiles:insertAttribute name="menu" />
		</div>
		<div class="body-container">
			<tiles:insertAttribute name="body" />
		</div>
	</div>
	<div class="footer-container">
		<tiles:insertAttribute name="footer" />
	</div>
</div>

<div id="dialog" title="Confirmacion">
  Seguro que desea eliminar?
</div>

<script type="text/javascript">
  $(document).ready(function() {
    $("#dialog").dialog({
      autoOpen: false,
      modal: true
    });
  });

  $(".confirmLink").click(function(e) {
    e.preventDefault();
    var targetUrl = $(this).attr("href");

    $("#dialog").dialog({
      buttons : {
        "Aceptar" : function() {
          window.location.href = targetUrl;
        },
        "Cancelar" : function() {
          $(this).dialog("close");
        }
      }
    });

    $("#dialog").dialog("open");
  });
</script>
</body>
</html>