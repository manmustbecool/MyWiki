---
output:
  html_document:
    fig_caption: yes
    highlight: zenburn
    keep_md: yes
    number_sections: yes
    theme: spacelab
    toc: yes
---


## Gradient descents 


Linear regression by gradient descents

http://www.r-bloggers.com/linear-regression-by-gradient-descent/?goback=%2Egmp_70219%2Egde_70219_member_139405437

## Neural networks ##

http://www.ai-junkie.com/ann/evolved/nnt8.html

## K-Means ##

http://www.mattpeeples.net/kmeans.html


## Hidden Markov Models ##

http://web.stanford.edu/class/stats366/hmmR2.html

http://yunus.hacettepe.edu.tr/~iozkan/eco742/hmm.html


## Logistic Regression ##

https://rdatasmith.wordpress.com/2012/05/16/programming-problem-set-2-part-1-logistic-regression/


## Support Vector Machine (SVM) ##

  * package **e1071**

tune
http://rgm2.lab.nig.ac.jp/RGM2/func.php?rd_id=e1071:tune

svm
http://rgm2.lab.nig.ac.jp/RGM2/func.php?rd_id=e1071:svm

http://stackoverflow.com/questions/7390173/svm-equations-from-e1071-r-package

Recursive Feature Extraction (SVM-RFE)

http://stats.stackexchange.com/questions/10676/using-an-svm-for-feature-selection


## Singular Value Decomposition (SVD) ##

http://en.wikibooks.org/wiki/Data_Mining_Algorithms_In_R/Dimensionality_Reduction/Singular_Value_Decomposition

## Text mining

http://chengjun.github.io/en/2014/04/sentiment-analysis-with-machine-learning-in-R/

## Decision Tree

 * rpat
 
http://www.statmethods.net/advstats/cart.html

```r
# Classification Tree with rpart
library(rpart)

kyphosis[1:3,]
#   Kyphosis Age Number Start
# 1   absent  71      3     5
# 2   absent 158      3    14
# 3  present 128      4     5

# grow tree  
fit <- rpart(Kyphosis ~ Age + Number + Start, method="class", data=kyphosis) # classfication tree

printcp(fit) # display the results
plotcp(fit) # visualize cross-validation results
summary(fit) # detailed summary of splits

# plot tree
plot(fit, uniform=TRUE, main="Classification Tree for Kyphosis")
text(fit, use.n=TRUE, all=TRUE, cex=.8)

# create attractive postscript plot of tree
post(fit, file = "",
     title = "Classification Tree for Kyphosis")

# prune the tree
pfit<- prune(fit, cp=fit$cptable[which.min(fit$cptable[,"xerror"]),"CP"])

# plot the pruned tree
plot(pfit, uniform=TRUE,
     main="Pruned Classification Tree for Kyphosis")
text(pfit, use.n=TRUE, all=TRUE, cex=.8)
post(pfit,  file = "",
     title = "Pruned Classification Tree for Kyphosis")
```
 
 
## Association rule learning ##

 * arulesSequences

```r
# sample events 
# 322282,20100827,2,AA,BB
# 312980,20100622,3,CC,DD,EE
# 312246,20100218,7,FF,GG,HH,II,MM,OO,NN

t <- read_baskets(con=system.file("misc", "zaki.txt", package="arulesSequences"), 
         info=c("eventID","sequenceID","size"))

# get all eventId
transactionInfo(t)$eventID

# mine frequent sequences
s1 <- cspade(t, parameter=list(support = 0.4), 
           control=list(verbose = TRUE))
summary(s1)
as(s1, "data.frame")
```

## Time Series

http://www.statmethods.net/advstats/timeseries.html

http://a-little-book-of-r-for-time-series.readthedocs.org/en/latest/src/timeseries.html#arima-models

```r
prices <- c(1.1, 2.2, 3.3)
timestamps <-  c('2011-01-05 11:00', '2011-01-05 12:00', '2011-01-05 13:00')
stockprices <- data.frame(prices, timestamps)

# convert data.frame to time series
stockprices
# prices(numeric)    timestamps(character)
# 1.1         2011-01-05 11:00
# 2.2         2011-01-05 12:00
# 3.3         2011-01-05 13:00

require(xts)
stockpricesTs <- xts(stockprices$prices, order.by=as.POSIXct(stockprices$timestamps))
stockpricesTs

# select a time range
t1 <- stockpricesTs["2011-01-04T05:00/2011-01-05"]

```

### Moving Averages 

  * TTR

http://rss.acs.unt.edu/Rdoc/library/TTR/html/MovingAverages.html


### Arima 

```r
fit <- Arima(WWWusage,order=c(3,1,0))
plot(fit$x,col="red")
lines(fitted(fit),col="blue")
```

## Recommender system ##

  * recommenderlab


## Feature selection ##

  * caret

http://www.cybaea.net/Blogs/Data/Feature-selection-Using-the-caret-package.html


