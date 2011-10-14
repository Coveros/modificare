
# Define all of the code as a function so that it can
# easily be executed inside of an R session.


# The following commands read in txt files that
# are needed to produce the graphs

srcDataFile = "data/src.txt"
testDataFile= "data/test.txt"
fullDataFile = "data/full.txt"
ccnHistoDataFile = "data/ccn_histo.txt"
ncssHistoDataFile = "data/ncss_histo.txt"
javadocHistoDataFile  = "data/javadoc_histo.txt"

x11()


# FUNCTION:
# This function creates multihist graphs for NCSS and ANJOPF
# and outputs them into graphs/NCSS_multihist.eps and
# graphs/ANOJPF_multihist.eps

Create_Histo = function()
{

ccnHisto = read.table(file=ccnHistoDataFile, header=T);
ncssHisto = read.table(file=ncssHistoDataFile, header=T);
javadocHisto = read.table(file=javadocHistoDataFile, header=T);


# Create NCSS multihist graph
barplot2(as.matrix(ncssHisto), 
       	  	beside = TRUE,
       	  	col = c("grey25", "grey75"), 
		las=2, 
		font.main = 4,
		main="NCSS_src v. NCSS_test",  
)
box()
# create legend
histolegend = c("src", "test")
par(xpd = TRUE);
par(usr=c(0,1,0,1))
legend(x=.375, y=1.075, histolegend,
        bty="n", horiz=TRUE, fill = c("grey25", "grey75"))
# save graph
SaveGraphicWeb("graphs/NCSS_multihist.eps")



# Create ANJOPF multihist graph
barplot2(as.matrix(javadocHisto), 
       	  	beside = TRUE,
       	  	col = c("grey25", "grey75"), 
		las=2, 
		font.main = 4,
		main="ANOJPF_src v. ANOJPF_test",  
)
box()
# create legend
histolegend = c("src", "test")
par(xpd = TRUE);
par(usr=c(0,1,0,1))
legend(x=.375, y=1.075, histolegend,
        bty="n", horiz=TRUE, fill = c("grey25", "grey75"))
#save graph
SaveGraphicWeb("graphs/ANOJPF_multihist.eps")

}

# FUNCTION:
# This function creates scatterplot graphs 
# and outputs them into graphs/

Create_Scatter_Plots = function(linearRegression)
{
src = read.table(file=srcDataFile, header=T);
test = read.table(file=testDataFile, header=T);
full = read.table(file=fullDataFile, header = T);

plot(test$noo,test$notc, xlab="Number of Oracles (test)",  
			 ylab="Number of Test Cases (test)", 
			 main="NOO_test vs. NOTC_test")

if( linearRegression == TRUE)
{
myline.fit <- lm(test$notc~test$noo)
abline(myline.fit)
#linear regression output
capture.output(summary(myline.fit), 
file = "graphs/NOO_test_vs_NOTC_test_Statistics.txt")
} 
# save graph
SaveGraphicWeb("graphs/NOO_test_vs_NOTC_test.eps")



plot(test$notc,src$ncss, xlab="Number of Test Cases (test)", 
			 ylab="Number of Non-Commented Source Statements (src)",
			 main="NOTC_test vs. NCSS_src")

if( linearRegression == TRUE)
{
myline.fit <- lm(src$ncss~test$notc)
abline(myline.fit)
#linear regression output
capture.output(summary(myline.fit), 
file = "graphs/NOTC_test_vs_NCSS_src_Statistics.txt")
}
# save graph
SaveGraphicWeb("graphs/NOTC_test_vs_NCSS_src.eps")



plot(src$ccn, test$notc,
	      xlab="Cyclomatic Complexity Number (src)",
	      ylab="Number of Test Cases (test)",
	      main="CCN_src vs. NOTC_test")

if( linearRegression == TRUE)
{
myline.fit <- lm(test$notc~src$ccn)
abline(myline.fit)
#linear regression output
capture.output(summary(myline.fit), 
file = "graphs/CCN_src_vs_NOTC_test_Statistics.txt")
}
# save graph
SaveGraphicWeb("graphs/CCN_src_vs_NOTC_test.eps")

      	       

}

# FUNCTION:
# This function creates bar graphs 
# and outputs them into graphs/

Create_Bars = function() 
{
src = read.table(file=srcDataFile, header=T);
test = read.table(file=testDataFile, header=T);
full = read.table(file=fullDataFile, header = T);



barplot(full$ncss, 
	names.arg=sub("[_].*","",full$name), 
	main="NCSS_full", 
	xlab=NULL,
	ylab="Non-Commented Source Statements", 
	las=2, 
	plot=TRUE)
box()
# save graph
SaveGraphicWeb("graphs/barNCSS_full.eps")

barplot(src$ncss, 
	names.arg=sub("[_].*","",src$name), 
	main="NCSS_src", 
	xlab=NULL,
	ylab="Non-Commented Source Statements", 
	las=2, 
	plot=TRUE)
box()
# save graph
SaveGraphicWeb("graphs/barNCSS_src.eps")

barplot(test$ncss, 
	names.arg=sub("[_].*","",test$name), 
	main="NCSS_test", 
	xlab=NULL,
	ylab="Non-Commented Source Statements", 
	las=2, 
	plot=TRUE)
box()
# save graph
SaveGraphicWeb("graphs/barNCSS_test.eps")

barplot(src$ccn, 
	names.arg=sub("[_].*","",src$name), 
	main="CCN_src", 
	xlab=NULL,
	ylab="Cyclomatic Complexity Number", 
	las=2, 
	plot=TRUE)
box()
# save graph
SaveGraphicWeb("graphs/barCCN_src.eps")

barplot(test$notc, 
	names.arg=sub("[_].*","",test$name), 
	main="NOTC_test", 
	xlab=NULL,
	ylab="Number of Test Cases", 
	las=2, 
	plot=TRUE)
box()
# save graph
SaveGraphicWeb("graphs/barNOTC_test.eps")

barplot(test$noo, 
	names.arg=sub("[_].*","",test$name), 
	main="NOO_test", 
	xlab=NULL,
	ylab="Number of Oracles", 
	las=2, 
	plot=TRUE)
box()
# save graph
SaveGraphicWeb("graphs/barNOO_test.eps")

barplot(full$anojpf, 
	names.arg=sub("[_].*","",full$name), 
	main="ANOJPF_full", 
	xlab=NULL,
	ylab="Average JavaDocs per Function", 
	las=2, 
	plot=TRUE)
box()
# save graph
SaveGraphicWeb("graphs/barANOJPF_full.eps")


barplot(src$anojpf, 
	names.arg=sub("[_].*","",src$name), 
	main="ANOJPF_src", 
	xlab=NULL,
	ylab="Average JavaDocs per Function", 
	las=2, 
	plot=TRUE)
box()
# save graph
SaveGraphicWeb("graphs/barANOJPF_src.eps")

barplot(test$anojpf, 
	names.arg=sub("[_].*","",test$name), 
	main="ANOJPF_test", 
	xlab=NULL,
	ylab="Average JavaDocs per Function", 
	las=2, 
	plot=TRUE)
box()
# save graph
SaveGraphicWeb("graphs/barANOJPF_test.eps")
}

# FUNCTION: 
# This function will save the current working graph to the
# file that is specified.  Note that the X11 device that is
# currently labelled as (ACTIVE) will be saved.

SaveGraphicWeb = function(fileName)
{

   dev.copy(postscript, file=fileName, height=6,  
width=6,horizontal=F,onefile=F)
   dev.off()

}
