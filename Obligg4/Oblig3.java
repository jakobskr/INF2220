import java.util.Scanner;
import java.io.File;


class Oblig3{
  public static void main(String[] args) {
    try{
      String pattern = readFile(args[0]);
      String source = readFile(args[1]);
      int numberOfMatches = find(pattern.toCharArray(), source.toCharArray());
      if(numberOfMatches == -1){
        System.exit(0);
      }
      else{
        System.out.println("Total number of matches: "+numberOfMatches);
      }
    }
    catch(Exception e){
      System.out.println("Error: ");
      e.printStackTrace();
      System.exit(0);
    }


  }

  public static String readFile(String filename) throws Exception{
    String returnString = "";
    File file = new File(filename);
    Scanner input = new Scanner(file);
    while(input.hasNextLine()){
      returnString = returnString.concat(input.nextLine());
    }
    return returnString;
  }

  public static int[] badCharShift(char pattern[]){
    int[] badCharShift = new int[256];
    int restOfCharacters = pattern.length;
    for(int i = 0; i < pattern.length; i++){
      if(pattern[i] == '_'){
        restOfCharacters = i;
      }
    }
    /*
     * Special case: '_' is the first letter in the pattern.
     * In this case we can skip the length of the pattern - 1.
     */
    if(restOfCharacters == 0){
      restOfCharacters  = pattern.length-1;
    }

    for(int i = 0; i < badCharShift.length; i++){
      badCharShift[i] = restOfCharacters;
    }

    // Replacing the predefined "skip-length" for characters matcing the pattern.
    int last = pattern.length - 1;
    for(int i = 0; i < last; i++){
      badCharShift[ (int)pattern[i] ] = last - i;
    }
    return badCharShift;
  }

  public static int find(char[] pattern, char[] source){
    if ( pattern.length > source.length ){
      System.out.println("error: the pattern is longer than the source!");
      return -1;
    }
    else if(pattern.length == 0 || source.length == 0){
      System.out.println("error: no source or no pattern!");
      return -1;
    }
    int[] badshift = badCharShift(pattern);
    int checkMatch;
    int numberOfMatches = 0;
    int offset = 0;
    int bounds = source.length - pattern.length;
    boolean foundMatch = false;

    while(offset <= bounds){
      for(checkMatch = (pattern.length-1); pattern[checkMatch] == source[checkMatch+offset] || pattern[checkMatch] == '_'; checkMatch--){
        //System.out.println("matching "+pattern[checkMatch] +" to "+ source[checkMatch+offset]);
        if(checkMatch == 0){
          numberOfMatches++;
          System.out.println("found pattern at offset "+offset+" in source:");
          System.out.print(" ");
          for(int i = offset; i < pattern.length+offset; i++){
            System.out.print(source[i]);
          }
          System.out.print("\n");
          foundMatch = true;
          break; //to avoid pattern[-1] in the for-loop.
        }
      }
      if(foundMatch){
        offset++;
        foundMatch = false;
      }
      else{
        //adds the amount corresponding with the letter at source  that was checked
        offset += badshift[source[offset + (pattern.length-1)]];
      }
    }
    return numberOfMatches;
  }
}
