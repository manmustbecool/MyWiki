---
title: "Solr"
output: html_document
---

# Enterprise Search and Solr framework

Paper: Enterprise Search with Development for Network Management System

[PDF paper](http://manmustbecool.github.io/MyWiki/papers/Enterprise%20search%20with%20development%20for%20network%20management%20system.pdf) 
or 
[IEEE xplore link](http://ieeexplore.ieee.org/xpl/articleDetails.jsp?arnumber=7207254&amp;punumber%3D7194705%26filter%3DAND%28p_IS_Number%3A7207183%29%26pageNumber%3D3)


# Top 10 performance tips apache solr

http://myjeeva.com/top-10-performance-tips-apache-solr.html

# Add UUID field on indexing
http://wiki.apache.org/solr/UniqueKey

in the schema.xml
```xml
<fields>
    <field name="uuid" type="uuid" indexed="true" stored="true" required="true" />
</fields>

<uniqueKey>uuid</uniqueKey>

<types>
    <fieldType name="uuid" class="solr.UUIDField" indexed="true" />
</types>
```

in the Solrconfig.xml

updateRequestProcessorChain allows you to configure a processing chain that processes a document before indexing
```xml
<requestHandler name="/dataimport"  class="org.apache.solr.handler.dataimport.DataImportHandler">  
    .........
    <lst name="defaults">
        <str name="config">data-config.xml</str>
        <str name="update.chain">uuid</str>
    </lst>
</requestHandler>

<updateRequestProcessorChain name="uuid">
  <processor class="solr.UUIDUpdateProcessorFactory">
      <str name="fieldName">uuid</str>
  </processor>
  <processor class="solr.RunUpdateProcessorFactory" />
</updateRequestProcessorChain>
```