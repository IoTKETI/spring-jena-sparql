<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en" class="snorql">
  <head>
    <title>SPARQL Explorer</title>
    <link rel="stylesheet" type="text/css" href="/css/sparql.css" />
    <script src="/js/sparql.js"></script>
    <script src="js/app.js"></script>
  </head>

  <body onLoad="start()">
    <div id="header">
      <h1 id="title">SPARQL Explorer</h1>
    </div>
<!--
    <div class="section" style="float: right; width: 6em">
      <h2>Browse:</h2>
      <ul>
        <li><a href="?classes">Classes</a></li>
        <li><a href="?properties">Properties</a></li>
      </ul>
    </div>

    <div class="section" style="margin-right: 10em">
-->
    <div class="section">
      <h2>SPARQL:</h2>
      <ul>
        <li><a href="http://rdftest:9000/rest/entry/NX_O95406/overview">http://rdftest:9000/rest/entry/NX_O95406/overview</a></li>
        <li><a href="http://rdftest:9000/rest/entry/annotations/NX_O95406/Function_Annotation">http://rdftest:9000/rest/entry/annotations/NX_O95406/Function_Annotation</a></li>
        <li><a href="http://swissprot.isb-sib.ch/wiki/display/cal/Amos%27+search+examples">Amos' search examples</a></li>
      </ul>
      <br/>
      <pre id="prefixestext"></pre>
      <form id="queryform" action="#" method="get">
        <input type="hidden" name="default-graph-uri" value="" id="defaultgraph" disabled="disabled" />
        <input type="hidden" name="query" value="" id="query" />
        <input type="hidden" name="output" value="json" id="jsonoutput" disabled="disabled" />
        <input type="hidden" name="stylesheet" value="" id="stylesheet" disabled="disabled" />
      </form>
      <div>
        <textarea name="query" rows="9" id="querytext"></textarea>
        Results:
        <select id="selectoutput" onChange="outputChanged(this.value)">
          <option selected="selected" value="browse">Browse</option>
          <option value="json">as JSON</option>
          <option value="xml">as XML</option>
          <option value="xslt">as XML+XSLT</option>
        </select>
        <span id="xsltcontainer"><span id="xsltinput">
          XSLT stylesheet URL:
          <input id="xsltstylesheet" type="text" value="xml-to-html.xsl" size="20" />
        </span></span>
        <input type="button" value="Go!" onClick="submitQuery()" />
        <input type="button" value="Reset" onClick="resetQuery()" />
      </div>
    </div>

    <div class="section">
      <div id="time"></div>
      <div id="result"><span></span></div>
    </div>

    <div id="footer">nextprot</div>
  </body>
</html>