---
title: "R basic"
---

date: "r format(Sys.time(), '%d %B, %Y')"

# Basic #

## Installing R in Ubuntu ##

```bash
sudo apt-get update
sudo apt-get install r-base r-base-dev
```

## data types ##

The atomic data types are "logical", "integer", "numeric" (synonym "double"), "complex", "character" and "raw".


```r
# Formatting Decimal
x <- 1.224554
round(x, digits=2)

#  string is considered to be a character vector in R
str = 'string' # str[1] # This evaluates to 'string'.
substr(str, 1, 2) # == 'st'
strsplit('0-0-1', '-') # Evaluates to list('0', '0', '1')
paste('hello', 'world', sep = ' ') # == hello world

```

## Data initialization ##

```r
# initialize a list
my_ls <- vector('list', 50) # vector(mode="list", length=50)
my_ls[[3]] <- c(1,2,3) # assign a value

# create an arithmetic sequence (numeric)
se = seq(8, 3) # 8,7,6,5,4,3
se <- seq(3, 8, by=2) #  3, 5, 7

# create an array 
ar <- array(3, 5) # [3,3,3,3,3]
ar <- array(c(3,5), 5) # [3,5,3,5,3,5,3,5,3,5]

# Initialize a matrix
mr <- matrix(ncol=50, nrow=50)

# Initialize a dataframe
df <- data.frame(matrix(ncol=50, nrow=1))
...
df <- df[-1,]

# 
prices <- c(1.1, 2.2, 3.3)
timestamps <- c('2011-01-05 11:00', '2011-01-05 12:00', '2011-01-05 13:00')
stockpricesDf <- data.frame(prices, timestamps)

```

## Common Vector operations ##
A Vector is a sequence of data elements of atomic data types (Numeric, Integer, etc.).

```r
# select a subset
v <- c(1,2,3,4)
y <- v[v<3]
y <- which(v<3)

# select every (n+1)th element
n=2
v <- v[seq(1, length(a), n)]

# append a value
v <- c(1, 2)
v <- c(v, 3) #or v <- append(v, 3)

# delete a value
v <- c("a", "b", "c", "d")
v <- v[!v=="c"]

# delete subset of values
v <- v[!v %in% c("c", "d")]

# find all duplicated entries
v[duplicated(v)] # or remove all duplicated, v[!duplicated(v)]

# find all unique entries
unique(x)

# calculate frequency of occurrence 
a = c(1,1,1,1,1,2,3,4,5,5,5,5,,6,7,7,7,7)
table(a) # frequency table
max(table(a)) # highest frequency (not the actual value )

# Set operations
union(x, y)
intersect(x, y) 
setdiff(x, y) # (asymmetric!) difference, setdiff(y, x) for y difference to x
setequal(x, y) 

# Return the sum of all the values
sum(v)
sum(v==1) # count the number of value 1

# convert one character vector into one string
paste(x, sep="", collapse="") 

```


## Common Factor operations ##
```R
# Factor to Numeric vector
f <- factor(c("10", "11", "12"))
v <- as.numeric(as.character(f))
```


## Common Matrix operations ##
A Matrix is a collection of elements with same data types in a two-dimensional rectangular layout.

```R
# Convert a vector to a matrix
v <- seq(1, 6)
m <- array(v, c(3,2)) # or m <- matrix(v, nrow=3, ncol=2)
m
      [,2]  [,3]
[1,]    1    4
[2,]    2    5
[3,]    3    6

# Matrix Transpose
m <- t(m)
```


## Common Data Frame operations ##

A Data Frame likes a table. It is a list of column vectors of equal length. Different columns can be of various types. . I.e., one column might be a numerical variable, another might be a factor.

```r
# Get and Set Row and Column Names for Data Frames
rownames(df)   
colnames(df) 
rownames(df) <- value

# Get number of rows and columns
nrow(df)
ncol(df)

# Select a subset of the data frame
sub_df <- df[c(1,2,5), ] # select by row indexes
sub_df <- df[, c(1,2,5)] # select by column indexes

sub_df <- df[rownames(df)=='user1', ] # select by row names
sub_df <- df[, c('start','end')] # select by column names

sub_df <- df[df$age>40, ] # filter rows by values

# find duplicated rows
df[duplicated(df),] # or remove, df[!duplicated(df),]

# remove duplicated rows by a column
df[!duplicated(df[,c('firstname')]),]

# find unique rows
unique(df)

# row frequency count
# http://rwiki.sciviews.org/doku.php?id=tips:data-frames:count_and_extract_unique_rows
aggregate(rep(1, nrow(df)), by=as.list(df), FUN=sum)

# sample rows 
df[sample(nrow(df), 3), ]

# apply a Function to a Data Frame Split by Factors
by(df[, 1:2], df[,3], summary)

# add a row or column
row <- c(1,2,3)
df <- rbind(df, row);

column <- c(1,2,3)
df <- cbind(df, column);

# create a new column with merged values of two columns
df$new_column = paste(df$V1, df$V2, sep="_")

# delete rows
df <- df[-c(1:8),] # deleting rows by index

# delete columns by names 
df <- df[, !(colnames(df) %in% c("TotalDuration") )]

# sort by column index
df[order(-df[,4], df[,1]), ]

# sort according a specified order or vector
target <- c("b", "c", "a", "d")
df[match(target, df$name),]

# ----- join two data frames
merge(x=df1, y=df2, by="CustomerId")             # default all=FALSE. natural join, a special case of an inner join
merge(x=df1, y=df2, by="CustomerId", all = TRUE) # (full) outer join
merge(x=df1, y=df2, by="CustomerId", all.x=TRUE) # left (outer) join
merge(x=df1, y=df2, by="CustomerId", all.y=TRUE) # right (outer) join
merge(x=df1, y=df2, by=NULL)                     # cross join

merge(df1, df2, by = c("k1","k2")) 

# ------ group by with data table
library(data.table)
dt <- data.table(df) # convert the data frame to data table
dt[, list(mean=mean(age), sd=sd(age)), by=company] # summarize
dt[, list(ages=list(age)), by=company] # list
dt[, count:=.N, by=list(a,b)] # row frequency count by group. .N will give you the number of number of rows in each group.

expr <- parse(text = paste("by=", colnames(dt)2)) # dynamic column names
dt[, list(ages=list(age)), eval(expr)]


#  ------------ Handle NA, NaN, null, blank ---------------------

# NULL is an Object meaning the variable contains no object.

# NA is a Value meaning missing data
# is.na() returns TRUE for both NA and NaN

# NaN means not a number
# is.nan() returns False for NA and Ture for NaN

# Convert all Na's in data frames to other values
df[is.na(df)] <- value

# Remove rows with NAs in data frames
df[complete.cases(df),]   # remove all rows containing NAs
df[complete.cases(df[,2:3]),] # remove rows based on some columns

# Remove rows with null value in a specific column
df <- df[!is.null(df[,1]),]

# Remove rows with Blank value in a specific column
df <- df[!(df[,1]==""),]

# Remove columns where All values are NA
df <- df[,colSums(is.na(df))<nrow(df)]

```

## Common List operations ##

A List is an ordered collection of objects.

``` r
# convert a list to a data frame
my_df <- as.data.frame(do.call(rbind, my_ls))

# select a subset from a list
v <- v[v>5]

# append a list
l1 <- append(l1, l2) # each elements of l2 will be an element of l1
l1 <- append(l1, list(l2)) # the l2 will be one element of l1
```


## Common functions ##
``` r
-------- apply --------
http://nsaunders.wordpress.com/2010/08/20/a-brief-introduction-to-apply-in-r/

----- Sample --------

# Sample 30 from 1 to 300
sample(300, 30)
sort(sample(300, 30)) # sorted result

# get replicable sample 
set.seed(123) # sample results are same everytime for same seed
sample(300, 30)


```

## Plot  ##

http://personality-project.org/r/r.plottingdates.html

Producing Simple Graphs with R : 
http://www.harding.edu/fmccown/r/

``` r
# plot a line of a set of points without showing the points
points(x, y,  type="o", pch=46)

# x is a vector that you wish to find the CDF for
plot(ecdf(x))

# Sorting a boxplot based on median value
bymedian <- with(InsectSprays, reorder(spray, -count, median))
boxplot(count ~ bymedian, data = InsectSprays,
          xlab = "spray", ylab = "count", varwidth = TRUE,
          col = "lightgray")

# plot with multiple Y scales
par(oma=c(2,2,2,2)) # all sides have 2 lines of space margin
plot(y~x,data=t, ...) # first plot
par(new=T) # tell R to draw over the first plot
plot(y2~x,data=t, ...) # do second plot. 
axis(4,pretty(range(t$y2)),ylab='y2') # draw second axis on the right
mtext("second Y", side=4, line=2.3, adj=0.5, cex=1) # add test label on the right 

```
*Hmisc*
http://rgm2.lab.nig.ac.jp/RGM2/func.php?rd_id=Hmisc:Ecdf

*Histograms in R*
http://msenux.redwoods.edu/math/R/hist.php

*heatmap-or-plot-for-a-correlation-matrix*
http://stackoverflow.com/questions/15887212/heatmap-or-plot-for-a-correlation-matrix


## Parallel R in Windows ##
http://decisionstats.com/2010/09/24/parallel-programming-using-r-in-windows/
``` r
require(doSNOW)
cl<-makeCluster(2) # I have two cores
registerDoSNOW(cl)

# create a function to run in each itteration of the loop
check <-function(n) {
   for(i in 1:1000){
     sme <- matrix(rnorm(100), 10,10)
     solve(sme)
     }
 }

times <- 10   # times to run the loop
system.time(x <- foreach(j=1:times ) %dopar% check(j))
system.time(for(j in 1:times ) x <- check(j))
stopCluster(cl)
```


## SQL query ##

 * JDBC
```r
library(RJDBC) 
postgres <- JDBC( "org.postgresql.Driver", "C:/.../postgresql-8.4-703.jdbc4.jar")
conn <- dbConnect(postgres, "jdbc:postgresql://*.*.*.*:5432/xxdb", user="postgres", password="postgres")
myDf <- dbGetQuery(conn, "select * from tableX")
```
 * ODBC
```r
library(RODBC)
conn <- odbcDriverConnect('Driver=SQL Server; Server=10.x.x.x\\SQLEXPRESS; Database=xxx;')
myDF <- sqlQuery(conn, "select * from [xxx].[dbo].[myTable]")
```


# Others #

**pakcages overview** http://cran.r-project.org/web/views/

**QuickR**
http://www.statmethods.net/index.html


## Call R from Java ##

 * **RCaller** 
 
http://www.mhsatman.com/rcaller.php

 * **Rserve** 
 
An R server listens and processes incoming requests of clients. It passes commands to R which were sent by clients.

 * **JRI**
 
Java/R Interface

It use uses the JNI and is part of rJava. http://www.rforge.net/JRI/

Install rJava in R `install.packages("rJava")`

JRI can be found be the R library directory `C:\Program Files\R\R-2.15.0\library\rJava\jri `

Run example in the Windows CMD. (i386 for 32bit)
``` bash
set R_HOME= C:\Program Files\R\R-2.15.0
# The environment variable R_HOME has to be set globally for the system, not only for the current user (restart of the system!) 
set path= %PATH%;C:\Program Files\R\R-2.15.0\bin\i386;C:\Program Files\R\R-2.15.0\library\rJava\jri
cd C:\Program Files\R\R-2.15.0\library\rJava\jri\
java -cp JRI.jar;examples rtest
```

Development in Eclipse Java project

``` bash
 build path > add external class folder : C:\Program Files\R\R-2.15.0\library\rJava\jri
```

In Linux,
``` bash
# sudo R CMD javareconf

sudo apt-get install r-cran-rjava

export JAVA_HOME=/usr/lib/jvm/java-6-sun
export R_HOME=/usr/lib/R
# export PATH=$PATH:/usr/lib/R/site-library/rJava/jri

cd /usr/lib/R/site-library/rJava/jri
java -cp JRI.jar:examples rtest

# sudo cp /usr/lib/R/site-library/rJava/jri libjri.so  /usr/lib
```

JRI sample:

``` java
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;
public class RTest {
 public static void main(String[] args) {
  Rengine engine = new Rengine(new String[]{"--no-save"}, false, null);
  engine.assign("a", new int[]{81});
  REXP result = engine.eval("sqrt(a)");
  System.out.println(result.asDouble());
  engine.end();
 }
}
```

 * **Renjin** 
 
 A new JVM-based R language interpreter.
 http://code.google.com/p/renjin/ http://stdioe.blogspot.ie/2011/09/embedding-r-in-java-applications-using.html


## Call Java from R ##
  * **rJava**
  
 http://www.rforge.net/rJava/

