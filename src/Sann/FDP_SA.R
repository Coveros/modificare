require(plyr)
## Experiment Generator for the SANN alg
SANN_Multi_Rep <- function(fFM, NF="NF_FS", AF="AF_kirklin",
                  CF="CF_kirklin", Trials=10, itLimit=25, RandSeed=100, Par=FALSE)
{
    set.seed(RandSeed)
    SANN_Multi_Rep_real(fFM, NF, AF, CF, Trials, itLimit, RandSeed, Par)
}
SANN_Multi_Rep_real <- function(fFM, NF="NF_FS", AF="AF_kirklin",
                       CF="CF_kirklin", Trials=10, itLimit=25, RandSeed=100,
                       Par=FALSE)
{
  #set.seed(RandSeed)
  
    ParamMatrix <- expand.grid(fFM=fFM, NF=NF, AF=AF, CF=CF,
                               Trials=Trials, itLimit=itLimit, stringsAsFactors=FALSE,
                               KEEP.OUT.ATTRS=FALSE)
      
  print(c("Configurations to be executed: ", ParamMatrix))
  
    resDF <- adply(ParamMatrix, 1, function(pDF)
    {
    pList <- list(lFM=makeLogFM(read.table(pDF$fFM)), NF=pDF$NF,
                  AF=pDF$AF, CF=pDF$CF, itLimit=pDF$itLimit)
    
    batchDF <- ldply(runif(pDF$Trials, 1, 1000), function(tSeed,
               argList)
    {
      argList$Seed <- tSeed
      
      rTime <- system.time(expOut <- do.call("SANN_real",
               args=argList))[[3]]
      
      expOut$Ord <- unname(expOut$Ord)
      expOut$Runtime <- rTime
      
      theReturn <- data.frame(rbind(unlist(expOut)),
                   stringsAsFactors=FALSE)
    
      return(theReturn)
    }, argList=pList, .parallel=Par)
    
    return(cbind(batchDF, fFM=pDF$fFM))
    }, .progress="text")

    return(resDF)
}

## Bare-Bones simulated annealing algorithm as described in the
## Kirklin thesis
SANN <- function(lFM, NF="NF_FS", AF="AF_kirklin", CF="CF_kirklin",
                 itLimit=25,Seed=100)
{
    set.seed(Seed)
    SANN_real(lFM, NF, AF, CF, itLimit, Seed)
}
SANN_real <- function(lFM, NF="NF_FS", AF="AF_kirklin",
             CF="CF_kirklin", itLimit=25, Seed=100)
{    
    #set.seed(Seed)

    nT <- ncol(lFM)
    initialSequence <- sample.int(nT)
    currentTemperature <- 1

    # Set maximum iterations (per Kirklin), iterations since improve
    itLimit <- 25
    itSinceImprovement <- 0
    totalIterations <- 0

    # set the current neighbor to the initial sequence, and calc
    # fitness
    CurrOrd <- initialSequence
    CurrOrdFit <- APFD(CurrOrd, lFM)

    while(itSinceImprovement < itLimit) 
    {
        # Pull a neighbor using the neighbor generator, evaluate
        NearOrd <- do.call(NF, list(CurrOrd))
        NearOrdFit <- APFD(NearOrd, lFM)
        # Random chance of accepting inferior ordering
        AcceptChance <- runif(1)

        # Check if the neighboring ordering offers an improvement 
        #    in fitness
        if(CurrOrdFit < NearOrdFit) 
        {
            # Move to the new ordering, update the fitness        
            CurrOrd <- NearOrd
            CurrOrdFit <- numeric()
            CurrOrdFit <- APFD(CurrOrd, lFM)

            # reset the improvement counter
            itSinceImprovement <- 0
      
        } else if(AcceptChance < do.call(AF,
                  args=list(DeltaFitness=abs(NearOrdFit - 
                  CurrOrdFit), Temperature=currentTemperature)))
        {
            itSinceImprovement <- itSinceImprovement + 1
            CurrOrd <- NearOrd
            CurrOrdFit <- APFD(CurrOrd, lFM)
        
        } else
        {
            itSinceImprovement <- itSinceImprovement + 1
        }

        # At each iteration the temperature should change
        currentTemperature <- do.call(CF, list(currentTemperature))
        totalIterations <- totalIterations + 1    
    }

    return(list(Ord=CurrOrd, Fit=CurrOrdFit, Pri="SANN", Conf=NF, 
             TotalIt=totalIterations,Seed=Seed,
             Temp=currentTemperature))
}
