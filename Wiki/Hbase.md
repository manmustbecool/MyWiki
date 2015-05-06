

## Hbase 

```bash
cd /opt/hbase-0.94.26/bin/
./hbase shell
```

## phoenix

start sql
```bash
cd /opt/phoenix-3.3.0-src/bin/
./sqlline.py localhost

```

http://phoenix.apache.org/faq.html
```bash
? # help
!tables # list tables
!describe <tablename> # describe table
```

sql command line is similar to SQLLine 
```bash
create table test (mykey integer not null primary key, mycolumn varchar);
upsert into test values (1,'Hello');
upsert into test values (2,'World!');
select * from test;
```

load CSV file
```bash
head /opt/phoenix-3.3.0-src/examples/WEB_STAT.csv
./psql.py localhost -t 'WEB_STAT' ../examples/WEB_STAT.csv

```