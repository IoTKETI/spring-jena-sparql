<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
  <head>
    <title>Snorql: A SPARQL Explorer</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/style.css" />
    <link rel="stylesheet" type="text/css" href="/resources/js/highlight/codemirror.css" />
    <link rel="stylesheet" type="text/css" href="/resources/js/highlight/elegant.css" />
    <script type="text/javascript" src="/resources/js/sparql.js"></script>
    <script type="text/javascript" src="/resources/js/namespaces.js"></script>
    <script type="text/javascript" src="/resources/js/snorql.js"></script>
    <script type="text/javascript" src="/resources/js/highlight/codemirror.js"></script>
    <script type="text/javascript" src="/resources/js/highlight/matchbrackets.js"></script>
    <script type="text/javascript" src="/resources/js/highlight/sparql.js"></script>
    <script>
    $(function(){
      snorql.entry()
//       CodeMirror.fromTextArea(document.getElementById("querytext"), {
//         mode: "application/x-sparql-query",
//         matchBrackets: true,
//         theme:'elegant'
//       });
    	
    });
    </script>

  </head>

  <body >
    <div id="header">
      <h1 id="title">protein details</h1>
      <ul>
        <li><a href="/entry/NX_O95406">/entry/NX_O95406</a></li>
        <li><a href="/entry/NX_O95406/medical">/entry/NX_O95406/medical</a></li>
      </ul>
    </div>



    <div class="section" style="margin-right: 12em">
      <h2>nextprot data</h2>
      <pre id="prefixestext"></pre>
      <form id="queryform" action="#" method="get"><div>
        <input type="hidden" name="output" value="json" id="jsonoutput" disabled="disabled" />
        <input type="hidden" name="stylesheet" value="" id="stylesheet" disabled="disabled" />
      </div></form>
      <div>
        Results: <div id="time"></div>
      </div>
    </div>

    <div class="section">
      <div id="result"><span></span></div>
    </div>

    <div id="footer">Powered by <a id="poweredby" href="#">Snorql</a></div>
  </body>
</html>