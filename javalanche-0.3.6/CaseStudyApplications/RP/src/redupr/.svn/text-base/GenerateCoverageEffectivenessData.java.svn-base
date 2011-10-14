package redupr;

import java.util.ArrayList;
import java.util.Iterator;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/*
 * @author Gavilan T. Steinman 03/12/2008
 */
public class GenerateCoverageEffectivenessData {
       private SetCover s;
       private ArrayList<SingleTestSubset> testSubSets;
       private ArrayList<RequirementSubset> requirementSubSets;
       public GenerateCoverageEffectivenessData(SetCover s){
               this.s = s;
               this.testSubSets = new ArrayList<SingleTestSubset>();
               this.requirementSubSets = new ArrayList<RequirementSubset>();
               this.identifyTestSubsets();
               this.identifyRequirementSubsets();
       }
       private void identifyTestSubsets(){
               Iterator<SingleTestSubset> subsets = s.getTestSubsets().iterator();
               SingleTestSubset subset;
               while(subsets.hasNext()){
                       subset = subsets.next();
                       this.testSubSets.add(subset);
               }
       }
       private void identifyRequirementSubsets(){
               Iterator<RequirementSubset> subsets = s.getRequirementSubsetUniverse().iterator();
               RequirementSubset subset;
               while(subsets.hasNext()){
                       subset = subsets.next();
                       this.requirementSubSets.add(subset);
               }
       }
       public void saveMatrixData(String fileName){
               int[][] matrix = new int[this.testSubSets.size()+1][this.requirementSubSets.size()+1];
               PrintStream out;
               for(int i=0;i<this.testSubSets.size();i++){
                       for(int j=0;j<this.requirementSubSets.size();j++){
                               if(this.requirementSubSets.get(j)
                                               .containsSingleTest(this.testSubSets
                                                               .get(i).getTest())){
                                       matrix[i][j] = 1;
                                       matrix[i][matrix[0].length-1]++;
                                       matrix[matrix.length-1][j]++;
                               }
                       }
               }
               try {
                       out = new PrintStream(fileName);
                       for(int j=0;j<matrix[0].length;j++){
                               for(int i=0;i<matrix.length;i++){
                                       out.print(matrix[i][j]);
                                       if(i != matrix.length-1)out.print(" ");
                               }
                               out.println();
                       }
                       out.close();
               } catch (FileNotFoundException e) {
                       e.printStackTrace();
               }
       }
       public void saveTimingData(String fileName){
               PrintStream out;
               try {
                       out = new PrintStream(fileName);
                       out.println(
                                       "NameTTD"
                                       +"\t"+"Time"
                       );
                       for(int i=0;i<this.testSubSets.size();i++){
                               out.println(
                                               (this.testSubSets.get(i).getTest().getIndex())
                                               +"\t"+(this.testSubSets.get(i).getTest().getCost())
                               );
                       }
                       out.close();
               } catch (FileNotFoundException e) {
                       e.printStackTrace();
               }
       }
       
       /**
        * This method saves the setcover coverage data in the format
        * that is used by CE calculator.
        *
        * @author Adam M. Smith
        */
       
        public void saveCoverageData(String fileName){
               PrintStream out;
               try {
                        out = new PrintStream(fileName);
                        out.println("NameCD\tRequirements");
               			Iterator testSubsetsIterator = this.testSubSets.iterator();
               		  	while(testSubsetsIterator.hasNext())
               		  	{
               		  		SingleTestSubset currentSTS = (SingleTestSubset) testSubsetsIterator.next();
               		  		int currentSTSIndex = currentSTS.getTest().getIndex();
               		  		Iterator currentSTSRSIterator = currentSTS.getRequirementSubsetSet().iterator();
               		  		
               		  		while(currentSTSRSIterator.hasNext())
               		  		{
               		  			RequirementSubset currentRS = (RequirementSubset) currentSTSRSIterator.next();
               		  			int RSindex=currentRS.getIndex()+1;
               		  			out.println(currentSTSIndex +"\t"+RSindex);
               		  		}
               		  	}
               		   
               		   out.close();
               } catch (FileNotFoundException e) {
                       e.printStackTrace();
               }
       }
       
       public static void main(String args[]){
       
       //  String setCoverFile = args[0];
       //  String coverageFile = args[1];
       //  String timingFile = args[2];
         /*
         
         String setCoverFile = "data/diatoms/reduce/xmlSetCovers/GB-SetCover.xml";
         String coverageFile = "data/diatoms/reduce/xmlSetCovers/GBCoverage.dat";
         String timingFile = "data/diatoms/reduce/xmlSetCovers/GBTime.dat";
         
         SetCover sc;
         
         sc = SetCover.constructSetCoverFromXML(setCoverFile);
         
         GenerateCoverageEffectivenessData g = new GenerateCoverageEffectivenessData(sc);
         g.saveTimingData(timingFile);
         g.saveCoverageData(coverageFile);
         */
			/*
			//Note: taken from the testReduceUsingTallamGuptaExampleLA0
         // method of TestReduceUsingHarroldGuptaSoffa written
         // by Adam Smith.

         // make singletest
         SingleTest test0 = new SingleTest("test0",0);
         SingleTest test1 = new SingleTest("test1",1);
         SingleTest test2 = new SingleTest("test2",2);
         SingleTest test3 = new SingleTest("test3",3);
         SingleTest test4 = new SingleTest("test4",4);

         test0.setCost(42.12);
         test1.setCost(2.12);
         test2.setCost(4.32);
         test3.setCost(5.1);
         test4.setCost(3.33);

         // make requirementsubsets
         RequirementSubset req0 = new RequirementSubset("req0",0);
         RequirementSubset req1 = new RequirementSubset("req1",1);
         RequirementSubset req2 = new RequirementSubset("req2",2);
         RequirementSubset req3 = new RequirementSubset("req3",3);
         RequirementSubset req4 = new RequirementSubset("req4",4);
         RequirementSubset req5 = new RequirementSubset("req5",5);

         // make singletestsubset with singletests
         SingleTestSubset STS0 = new SingleTestSubset(test0);
         SingleTestSubset STS1 = new SingleTestSubset(test1);
         SingleTestSubset STS2 = new SingleTestSubset(test2);
         SingleTestSubset STS3 = new SingleTestSubset(test3);
         SingleTestSubset STS4 = new SingleTestSubset(test4);

         // add SingleTests to requirementSubsets
         req0.addCoveringTest(test0);
         req0.addCoveringTest(test1);
         req1.addCoveringTest(test0);
         req1.addCoveringTest(test2);
         req2.addCoveringTest(test0);
         req2.addCoveringTest(test3);
         req3.addCoveringTest(test1);
         req4.addCoveringTest(test2);
         req4.addCoveringTest(test4);
         req5.addCoveringTest(test3);

         // add requirementsubsets to STSs
         STS0.addRequirementSubset(req0);
         STS0.addRequirementSubset(req1);
         STS0.addRequirementSubset(req2);
         STS1.addRequirementSubset(req0);
         STS1.addRequirementSubset(req3);
         STS2.addRequirementSubset(req1);
         STS2.addRequirementSubset(req4);
         STS3.addRequirementSubset(req2);
         STS3.addRequirementSubset(req5);
         STS4.addRequirementSubset(req4);

         SetCover cover = new SetCover();
         // add requirementSubsets to the cover
         cover .addRequirementSubset(req0);
         cover.addRequirementSubset(req1);
         cover.addRequirementSubset(req2);
         cover.addRequirementSubset(req3);
         cover.addRequirementSubset(req4);
         cover.addRequirementSubset(req5);

         // add SingleTestSubset to cover

         cover.addSingleTestSubset(STS0);
         cover.addSingleTestSubset(STS1);
         cover.addSingleTestSubset(STS2);
         cover.addSingleTestSubset(STS3);
         cover.addSingleTestSubset(STS4);

         //Here is and example of this classes use
         GenerateCoverageEffectivenessData g = new GenerateCoverageEffectivenessData(cover);
         g.saveTimingData("timing.dat");
         g.saveMatrixData("matrix.dat");
         */
       }
}

