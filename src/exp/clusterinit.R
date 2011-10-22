
## function to load in cluster libraries and start a cluster of all
# the new dell machines in alden 101/103
AllNewDellClusterInit <- function()
{
 
  cl <- makeSOCKcluster(c("aldenv100", "aldenv100", "aldenv101",
        "aldenv101", "aldenv102", "aldenv102", "aldenv103",
        "aldenv103", "aldenv104", "aldenv104", "aldenv105",
        "aldenv105", "aldenv106", "aldenv106", "aldenv107",
        "aldenv107", "aldenv122", "aldenv122", "aldenv123", 
        "aldenv123", "aldenv151", "aldenv151", "aldenv152",
        "aldenv152", "aldenv153", "aldenv153", "aldenv154",
        "aldenv154", "aldenv184", "aldenv184", "aldenv185",
        "aldenv185", "aldenv186", "aldenv186", "aldenv187",
        "aldenv187", "aldenv188", "aldenv188", "aldenv189", 
        "aldenv189"))
        
  return(cl)
}

Alden101DellClusterInit <- function()
{  
  cl <- makeSOCKcluster(c("aldenv151", "aldenv151", "aldenv152",
        "aldenv152", "aldenv153", "aldenv153", "aldenv154",
        "aldenv154", "aldenv184", "aldenv184", "aldenv185",
        "aldenv185", "aldenv186", "aldenv186", "aldenv187",
        "aldenv187", "aldenv188", "aldenv188", "aldenv189",
        "aldenv189", "aldenv122", "aldenv122", "aldenv123",
        "aldenv123"))
  
  return(cl)
}

Alden103DellClusterInit <- function(){
  cl <- makeSOCKcluster(c("aldenv100", "aldenv100", "aldenv101",
        "aldenv101", "aldenv102", "aldenv102", "aldenv103",
        "aldenv103", "aldenv104", "aldenv104", "aldenv105",
        "aldenv105", "aldenv106", "aldenv106", "aldenv107",
        "aldenv107"))
        
  return(cl)
}
