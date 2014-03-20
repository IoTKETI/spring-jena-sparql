<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="dec" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <meta name="description" content="">
    	<meta name="author" content="Evalet Olivier">
       
      <title><dec:title default="sparqling nextprot" /></title>
      
<!--       <link href="/resources/css/bootstrap.min.css" rel="stylesheet"> -->
<!--       <link href="/resources/css/bootstrap-responsive.min.css" rel="stylesheet" /> -->


		  <!--[if lt IE 9]>
      		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    	<![endif]-->
    	
	    <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
	    <script src="/resources/js/bootstrap.min.js" type="text/javascript"></script>

  		<dec:head />
    </head>
    <body class="snorql">
			<dec:body />
	</body>
</html>