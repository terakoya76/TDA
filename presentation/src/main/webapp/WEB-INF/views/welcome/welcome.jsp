<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:if test="${!empty param.message}">
	<div class="message">
		<fmt:message key="${param.message }" />
	</div>
</c:if>

<div id="search-button">
	<a href="#" style="font-size: 14px;"><span class="ui-icon ui-icon-search button-icon"> </span> Buscar paciente</a>
</div>

<div class="filter-container" style="display:none;" id="filter-containter">
	<jsp:include page="/WEB-INF/views/welcome/filter.jsp" flush="true"/>
</div>

<div class="table-container">
	<table class="list-table">
	<thead>
		<tr>
			<th>
				<fmt:message key="welcome.name" />
			</th>
			<th>
				<div class="welcome-forms">
					<fmt:message key="welcome.forms" />
				</div>
			</th>
			<th>
				<div class="welcome-location">
					<fmt:message key="welcome.location" />
				</div>
			</th>
			<th>
				<fmt:message key="welcome.options" />
			</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${patientList}" var="patientintrain">
			<tr>
				<td>${patientintrain.patient.firstName} ${patientintrain.patient.lastName}</td>
				<td>
					<c:choose>
						<c:when test="${patientintrain.socialworkerform != null}">
							<img src="${pageContext.request.contextPath}/themes/default/image/ok.png" /> <a href="${pageContext.request.contextPath}/patient/${patientintrain.patient.id}/socialworker/${patientintrain.socialworkerform.id}/edit" class="trigger">Formulario Trabajador Social</a> <div class="tooltip">Editar Formulario de Trabajador Social</div> <br/>
						</c:when>
						<c:otherwise>
			  				<img src="${pageContext.request.contextPath}/themes/default/image/edit.gif" /><a href="${pageContext.request.contextPath}/patient/${patientintrain.patient.id}/socialworker/new" class="trigger">Formulario Trabajador Social</a> <span class="tooltip">Cargar Formulario de Trabajador Social</span> <br/>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${patientintrain.padiatricianform != null}">
							<img src="${pageContext.request.contextPath}/themes/default/image/ok.png" /> <a href="${pageContext.request.contextPath}/patient/${patientintrain.patient.id}/pediatrician/${patientintrain.padiatricianform.id}/edit" class="trigger">Formulario Pediatra</a> <span class="tooltip">Editar Formulario de Pediatra</span>  <br/>
						</c:when>
						<c:otherwise>
							<img src="${pageContext.request.contextPath}/themes/default/image/edit.gif" /> <a href="${pageContext.request.contextPath}/patient/${patientintrain.patient.id}/pediatrician/new" class="trigger">Formulario Pediatra</a>  <span class="tooltip">Cargar Formulario de Pediatra</span>  <br/>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${patientintrain.nurseform != null}">
							<img src="${pageContext.request.contextPath}/themes/default/image/ok.png" /> <a href="${pageContext.request.contextPath}/patient/${patientintrain.patient.id}/nurse/${patientintrain.nurseform.id}/edit">Formulario Enfermero</a> <br/>
						</c:when>
						<c:otherwise>
							<img src="${pageContext.request.contextPath}/themes/default/image/edit.gif" /> <a href="${pageContext.request.contextPath}/patient/${patientintrain.patient.id}/nurse/new">Formulario Enfermeno</a> <br/>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${patientintrain.dentistform != null}">
							<img src="${pageContext.request.contextPath}/themes/default/image/ok.png" /> <a href="${pageContext.request.contextPath}/patient/${patientintrain.patient.id}/dentist/${patientintrain.dentistform.id}/edit">Formulario Dentista</a> <br/>
						</c:when>
						<c:otherwise>
							<img src="${pageContext.request.contextPath}/themes/default/image/edit.gif" /> <a href="${pageContext.request.contextPath}/patient/${patientintrain.patient.id}/dentist/new">Formulario Dentista</a> <br/>
						</c:otherwise>
					</c:choose>
					</td>
				<td>
					<c:choose>
						<c:when test="${patientintrain.user != null}">
							${patientintrain.user.username}
						</c:when>
						<c:otherwise>
							<fmt:message key="welcome.state.waiting" />
						</c:otherwise>
					</c:choose>
				</td>
				<td align="center"><a class="button-text fg-button button-add ui-state-default ui-corner-all" href="#" onClick="confirmDownInTrain(${patientintrain.patient.id});"><span class="ui-icon ui-icon-arrowreturnthick-1-s button-icon"></span> Bajar </a></td>
			</tr>
		</c:forEach>
	</tbody>

	<tfoot>
		<tr>
		  <td colspan="3">
			<jsp:include page="/WEB-INF/views/paginator/paginator.jsp" flush="true"/>
		  </td>
		</tr>
	</tfoot>
</table>

<script language='javascript' type='text/javascript'>
	$(document).ready(function(){
		$('.trigger').tooltip({
			// place tooltip on the right edge
			position: "center right",
	
			// a little tweaking of the position
			//offset: [-2, 10],
	
			// use the built-in fadeIn/fadeOut effect
			effect: "fade",
	
			// custom opacity setting
			opacity: 0.7
		});
	});
</script>