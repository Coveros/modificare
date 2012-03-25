# Takes a directory and filename pattern as arguments and merges all data files
# matching that pattern.
mergeDataFiles <- function(directory="datasets/KauffmanThesisData/raw/", pattern="*.dat")
{
	# Create a list of all files in the directory that match the pattern.
	files <- list.files(path=directory,pattern=pattern,full.names=TRUE)

	# Will contain the attributes in all data frames.
	attributes <- c()

	# Create the list of the attributes in all files.
	for(file in files)
	{
		temp <- read.table(file)
		attributes <- union(attributes, names(temp))
	}

	# Make the data frame that will hold all data frames.  Create a row of
	# NA values.  Set the data frame attributes.
	dataFrame <- data.frame(t(rep(NA,length(attributes))))
	names(dataFrame) <- attributes

	#print(attributes)

	for(file in files)
	{
		temp <- read.table(file)
		diff <- setdiff(attributes, names(temp))
		
		for(column in diff)
		{
			temp[ncol(temp):ncol(temp)+1,column]<-NA
		}

		dataFrame <- rbind(dataFrame, temp)
	}

	# Remove the NA row.
	# TODO: This does not work.  However, since I am going to be subsetting the
	# data, and the NA row will never be selected, I will fix this later.
	dataFrame <- dataFrame[-1]

	return(dataFrame)
}
