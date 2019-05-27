<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="article/nutritionist/edit.do" modelAttribute="article">
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:textbox code="article.title" path="title" />
	
	<acme:textarea code="article.description" path="description" />

	<p style="color:blue;"><spring:message code="article.info.tags"/></p>
	<acme:textarea code="article.tags" path="tags"/>

	<br />
	<acme:submit name="save" code="article.save"/>	
	<jstl:if test="${article.id != 0}">
		<acme:submit name="delete" code="article.delete"/>	
	</jstl:if>
	<acme:cancel url="article/nutritionist/list.do" code="article.cancel"/>
	<br />
</form:form>
