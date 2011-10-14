# Remove one test case neighborhood generator.
# Returns a columns bound matrix of orderings with NA values for removed test
# cases.
NG_RO <- function(Ordering)
{
    Neighborhood <- vapply(c(1:length(Ordering)), function(x)
        {
            # Initialize the neighbor to the Ordering
            Neighbor <- Ordering
            # Replace the removed test case with NA.
            Neighbor[x] <- NA

            return(Neighbor)
        }, FUN.VALUE=Ordering)

    return(Neighborhood)
}

# First swap neighbor generator
#  Returns column bound matrix of orderings
NG_FS <- function(Ordering) 
{
  ## Laply -> array of neighboring orderings by ROWS
  Neighborhood <- vapply(c(2:length(Ordering)), function(x) 
		{
			# Initialize the neighbor to the Ordering
			Neighbor <- Ordering
			# Swap the first position with the target
			Neighbor[c(1,x)] <- Neighbor[c(x,1)]

			return(Neighbor)
		}, FUN.VALUE=Ordering)

	return(Neighborhood)
}

## Last swap neighborhood generator
#  Returns column bound matrix of orderings
NG_LS <- function(Ordering)
{
	LastPosn <- length(Ordering) 
	
	Neighborhood <- vapply(c(1:(LastPosn - 1)), function(x)
	{
		# Initialize the neighbor to the Ordering
		Neighbor <- Ordering
		# Swap the last position with the target
		Neighbor[c(x, LastPosn)] <- Neighbor[c(LastPosn, x)]

		return(Neighbor)
	}, FUN.VALUE=Ordering)

	return(Neighborhood)
}

# FrontLoadedGenSANN - swaps test cases to produce a neighbor according to
#  the front loaded neighbor generator described in the kirklin thesis
NG_FLS <- function(Ordering) 
{
	# random number for acceptance probability of an elements
	rand <- runif(1)
	# Determine one swap position
	Posn1 <- as.integer(runif(1, 1, (length(Ordering) - 1)))
	# Initiate the second posn to the last case
	Posn2 <- length(Ordering)
	
	probdenom<-numeric()

	# Iterate through each case, testing the acceptance prob
	for(Test in 1:length(Ordering))
	{
		probdenom <- (1/(2^Test))
		if(rand >= probdenom)
		{
			# Accept this test as the second position, break
			Posn2 <- Test
			break
		}
	}
	# Ensure the possibility of swapping with the last element
	if(Posn1 >= Posn2)
	{
		Posn1 <- Posn1 + 1
	}

	# Call the InOrdSwap to perform the swap
	Neighbor <-	InOrdSwap(Ordering, Posn1, Posn2)

	# Return the Result
	return(Neighbor)
}

# BackLoadGenSANN - swaps test cases to produce a neighbor according to
#  the front loaded neighbor generator described in the kirklin thesis
NG_BLS <- function(Ordering) 
{
	# random number for acceptance probability of an elements
	rand <- runif(1)
	# Determine one swap position
	Posn1 <- as.integer(runif(1, 1, length(Ordering) - 1))
	# Initiate the second posn to the first test case
	Posn2 <- 1

	# Iterate through each case, testing the acceptance prob
	for(Test in length(Ordering):1)
	{
		if(rand >= (1 / (2^(length(Ordering)-Test))))
		{
			# Accept this test as the second position, break
			Posn2 <- Test
			break
		}
	}
	# Ensure the possibility of swapping with the last element
	if(Posn1 >= Posn2)
	{
		Posn1 <- Posn1 + 1
	}

	# Call the InOrdSwap to perform the swap
	Neighbor <- InOrdSwap(Ordering, Posn1, Posn2)

	# Return the Result
	return(Neighbor)
}
