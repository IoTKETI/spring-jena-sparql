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
      CodeMirror.fromTextArea(document.getElementById("querytext"), {
        mode: "application/x-sparql-query",
        matchBrackets: true,
        theme:'elegant'
      });
      snorql.start()
    	
    });
    </script>

  </head>

  <body >
    <div id="header">
      <h1 id="title">Snorql: A SPARQL Explorer</h1>
      <ul>
        <li><a href="/rdf/entry/NX_O95406/overview">/rdf/entry/NX_O95406/overview</a></li>
        <li><a href="/rdf/entry/annotations/NX_O95406/Function_Annotation">/rdf/entry/annotations/NX_O95406/Function_Annotation</a></li>
        <li><a href="http://swissprot.isb-sib.ch/wiki/display/cal/Amos%27+search+examples">Amos' search examples</a></li>
      </ul>
    </div>

    <div class="section" style="float: right; width: 8em">
      <h2>Browse:</h2>
      <ul>
        <li><a class="graph-link" href="?browse=superclasses">Super Classes</a></li>
        <li><a class="graph-link" href="?browse=classes">Classes</a></li>
        <li><a class="graph-link" href="?browse=properties">Properties</a></li>
        <li id="browse-named-graphs-link"><a href="?browse=graphs">Named Graphs</a></li>
      </ul>
    </div>

    <div id="default-graph-section" class="section" style="margin-right: 12em">
      <h2 style="display: inline">GRAPH:</h2>
      <p style="display: inline">
        Default graph.
        <a href="?browse=graphs">List named graphs</a>
      </p>
    </div>

    <div id="named-graph-section" class="section" style="margin-right: 12em">
      <h2 style="display: inline">GRAPH:</h2>
      <p style="display: inline">
        <span id="selected-named-graph">Named graph goes here</span>.
        <a href="javascript:snorql.switchToDefaultGraph()">Switch back to default graph</a>
      </p>
    </div>

    <div class="section" style="margin-right: 12em">
      <h2>SPARQL:</h2>
      <pre id="prefixestext"></pre>
      <form id="queryform" action="#" method="get"><div>
        <input type="hidden" name="query" value="" id="query" />
        <input type="hidden" name="output" value="json" id="jsonoutput" disabled="disabled" />
        <input type="hidden" name="stylesheet" value="" id="stylesheet" disabled="disabled" />
        <input type="hidden" name="graph" value="" id="graph-uri" disabled="disabled" />
      </div></form>
      <div>
        <textarea name="query" rows="1" cols="80" id="querytext"></textarea>
        Results: <div id="time"></div>
        <select id="selectoutput" onchange="snorql.updateOutputMode()">
          <option selected="selected" value="browse">Browse</option>
          <option value="json">as JSON</option>
          <option value="xml">as XML</option>
          <option value="xslt">as XML+XSLT</option>
        </select>
        <span id="xsltcontainer"><span id="xsltinput">
          XSLT stylesheet URL:
          <input id="xsltstylesheet" type="text" value="snorql/xml-to-html.xsl" size="30" />
        </span></span>
        <input type="button" value="Go!" onclick="snorql.submitQuery()" />
        <input type="button" value="Reset" onclick="snorql.resetQuery()" />
      </div>
    </div>

    <div class="section">
      <div id="result"><span></span></div>
    </div>

    <div id="footer">Powered by <a id="poweredby" href="#">Snorql</a></div>
  </body>
</html>