# Takes in the aggregate data frame and adds four columns to each row containing
# the expense scores of the fault localization techniques.
# TODO: Remove references to column numbers.
computeFaultLocalizationResults <- function(dataFrame)
{
	dataFrame$Tarantula <- apply(dataFrame,1,
		function(row)
		{
			# Extract the reduction from the row.
			reduction <- as.numeric(row["Ord"])
			for(i in 1:268)
			{
				name <- paste("Ord",i,sep="")
				reduction <- c(reduction, as.numeric(row[name]))
			}
			reduction <- reduction[!is.na(reduction)]
			# print(reduction)

			# Read in the matrix and store information about it.
			filename <- as.character(row["fFM"])
			matrix<-makeLogFM(read.table(filename))‭
			numStatements <- nrow(matrix)
			numTests <- ncol(matrix)

			# Choose the faulty statement based on the application.

			# Choose the pass/fail information based on the application.

			# Generate the list of lives test cases.
			
			#matrixName <- strsplit(filename, split="/")[[1]][3]
			#splitMatrixName <- strsplit(matrixName, split="_")
			#criteria <- splitMatrixName[[1]][length(splitMatrixName[[1]])]
			#if(criteria == "FDM.dat")
		#		return("fault")
		#	else if(criteria == "Coverage.dat")
		#		return(splitMatrixName[[1]][3])
		})

	#return(dataFrame)
}
