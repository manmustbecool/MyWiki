---
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

  *  **e1071**

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


```r
rdmTweets <- list(
"Text Mining Tutorial http://t.co/jPHHLEGm",
"He likes dogs r Singapore http://t.co/GPA0TyG5",
"RDataMining: Easier Parallel Computing in R with snowfall and sfCluster http://t.co/BPcinvzK",
"RDataMining: Tutorial: Parallel computing using R package snowfall http://t.co/CHBCyr76",
"handling big data: Interacting with Data using the filehash Package for R http://t.co/7RB3sChx"
)

df <- as.data.frame(do.call(rbind, rdmTweets))
names(df) <- c('text')

library(tm)
# ---- build a corpus
myCorpus <- Corpus(VectorSource(df$text)) # VectorSource specifies the text source

# ---- Transforming Text

myCorpus <- tm_map(myCorpus, content_transformer(tolower)) #  to lower case
myCorpus <- tm_map(myCorpus, removePunctuation) # remove punctuation
myCorpus <- tm_map(myCorpus, removeNumbers) # remove numbers
myStopwords <- c(stopwords('english'), "available", "dogs") # add two additional stopwords
myStopwords <- setdiff(myStopwords, c("r", "big")) # remove 'r' and 'big' from stopwords
myCorpus <- tm_map(myCorpus, removeWords, myStopwords) # remove stopwords

# ---- Stemming Words

dictCorpus <- myCorpus # keep a copy as a dictionary for stem completion
#library("SnowballC") # for stemDocument
myCorpus <- tm_map(myCorpus, stemDocument) # stem words
inspect(myCorpus)
# myCorpus <- tm_map(myCorpus, stemCompletion, dictionary=dictCorpus) # stem completion
# the following stem completion works in tm v0.6 
tm_map(myCorpus, content_transformer(function(x, d)
  paste(stemCompletion(strsplit(stemDocument(x), ' ')[[1]], d), collapse = ' ')), dictCorpus)
inspect(myCorpus)
inspect(dictCorpus)

# ---- Building a Document-Term Matrix

myDtm <- TermDocumentMatrix(myCorpus, control = list(wordLengths = c(1, Inf)))
inspect(myDtm)
#                  Docs
# Terms             1 2 3 4 5
#   big             0 0 0 0 1
#   comput          0 0 1 1 0
#   data            0 0 0 0 2  
#   ...

# Based on the above matrix, many data mining tasks can be done, for example, clustering, classification and association analysis.

# ----- Frequent Terms and Associations

findFreqTerms(myDtm, lowfreq=2)

# which words are associated with "r"?
findAssocs(myDtm, 'r', 0.30)
```


## Decision Tree

 * rpart
 
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

## Random forest

http://trevorstephens.com/post/73770963794/titanic-getting-started-with-r-part-5-random
 
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


