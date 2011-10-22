## Convert a KM fault matrix logical format, remove summary column
makeLogFM <- function(FM)
{
  FM <- as.matrix(FM)
  somevar<- apply(FM[-nrow(FM), -ncol(FM)], 2, as.logical) 
  return(somevar)
}

## (N) APFD Calculator
APFD <- function(Ord, lFM)
{
  #Number of Tests
  FbyT <- dim(lFM)
  
  reveal <- apply(lFM[,Ord], 1, function(x) {which(x)[1]})
  # Removes NA values before computing reveal.
  # reveal <- apply(lFM[,Ord][ , !apply(is.na(lFM[,Ord]), 2, all)], 1,
  # function(x) {which(x)[1]})

  # calculate p
  pval <- sum(!is.na(reveal)) / FbyT[1]

  # Calculate p - (SigmaReveal / nm)  
  fitscore <- pval - (sum(reveal, na.rm=TRUE) / (FbyT[1] * FbyT[2])) +
              (pval / (2 * FbyT[2]))
  
  return(fitscore)
}

# Computes the requirements covered by an ordering or reduction.
ReqsCovered <- function(Ord, lFM)
{
    # logical vector that will hold the requirements covered
    Reqs <- c()

    for(i in 1:nrow(lFM[,Ord]))
        Reqs <- c(Reqs,(TRUE %in% lFM[,Ord][i,]))


    return(Reqs)
}
