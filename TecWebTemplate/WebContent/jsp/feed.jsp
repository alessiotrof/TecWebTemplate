<?xml version='1.0' encoding='UTF-8'?>


<!-- pagina per la gestione di errori -->
<%@ page errorPage="../error/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="false"%>

<!-- import di classi Java -->
<%@ page import="it.tecwebtemplate.beans.Feed"%>
<%@ page import="it.tecwebtemplate.beans.FeedDB"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>

<!-- librerie di terze parti -->
<!-- n.d. -->

<!-- creazione Java Bean con scope di applicazione -->
<jsp:useBean id="feedDB" class="it.tecwebtemplate.beans.FeedDB"  scope = "application"/>
<%
application.setAttribute("FeedDb", feedDB);
%>

<%

// QUESTO E' IMPORTANTISSIMO AFFINCHE' L'INTERPRETE JAVASCRIPT RICONOSCA IL CONTENUTO COME XML!!!!!!
response.setHeader("Content-Type","application/xml");

%>
<!-- segue quindi il codice.... questa volta NON HTML!!!!! ... restituito al client -->

<rss version='2.0'>
	<channel>
		<title><![CDATA[HomePage - LASTAMPA.it]]></title>
		<link>http://www.lastampa.it/redazione/</link>
		<description><![CDATA[lastampa.it - I titoli della Home Page]]></description>
		<lastBuildDate><![CDATA[Mon, 16 May 2011 15:58:35 +0200]]></lastBuildDate>
		<docs></docs>
		<managingEditor></managingEditor>

		<webMaster>teleservizi s.r.l.</webMaster>
		<language>it-IT</language>
		<image>
  			<url>http://www.lastampa.it/common/images/lastampatop.gif</url> 
  			<title>lastampa.it</title> 
  			<link>http://www.lastampa.it/redazione/</link> 
  			<width></width> 
  			<height></height> 
  		</image>

<%

	// recupero il tipo di categoria cercata dai parametri della richiesta
	String category = request.getParameter("category");
	
	// recupero i feed corrispondenti da database
	List<Feed> someFeeds = feedDB.find(category);
	
	// li stampo su out
	for ( Feed feed : someFeeds ) {
%>
		<item>
			<title><![CDATA[<%=feed.getTitle()%>]]></title>
			<description><![CDATA[<%=feed.getDescription()%>]]></description>
			<author><![CDATA[<%=feed.getAuthor()%>]]></author>
			<category><![CDATA[<%=feed.getCategory()%>]]></category>
			<pubDate><![CDATA[<%=feed.getPubDate()%>]]></pubDate>
			<link><![CDATA[<%=feed.getLink()%>]]></link>
<%
			if ( feed.getImageUrl() != null && feed.getImageUrl().length() > 0 ) {
%>		
				<enclosure url='<%=feed.getImageUrl()%>' type='image/jpeg' />
<% 
			}
%>		
		</item>
<% 
	}
%>

	</channel>
</rss>
