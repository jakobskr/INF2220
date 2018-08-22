import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Obligg4 {
  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.out.println("Wrong usage of program!\nCorrect usage is: java Obligg4 <NeedleFile>.txt <Hayfile>.txt");
      System.exit(0);
    }

    ArrayList<Integer> wordPos = new ArrayList<Integer>();
    File needleFile = new File (args[0]);
    File hayFile = new File(args[1]);
    Scanner filscan = new Scanner(needleFile);
    if (!filscan.hasNextLine()) {
      System.out.println("No Needle in " + args[0]);
      System.exit(0);
    }
    char[] needle = filscan.nextLine().toCharArray();
    for (int i = 0; i < needle.length; i++) {
      System.out.print(needle[i]);
    }
    System.out.println();
    filscan = new Scanner(hayFile);
    if (!filscan.hasNextLine()) {
      System.out.println("No haystack in " + args[1]);
      System.exit(0);
    }
    char[] hay = filscan.nextLine().toCharArray();
    System.out.println();
    for (int i = 0; i < hay.length; i++) {
      System.out.print(hay[i]);
    }

    /* calculate bad shift up to needle.length - 1 */

    System.out.println();
    wordPos = (horspool(needle, hay));
    if (wordPos.size() == 0) {
      System.out.println("The needle is not in the haystack");
    }
    else {
      System.out.println("The needle was found, printing  the locations and words");
      for (int i: wordPos) {
        System.out.print("\n" + i + " ");
        for (int j = 0;j <needle.length; j++) {
          System.out.print(hay[i + j]);
        }
      }
    }
  }

  static public ArrayList<Integer> horspool(char[] needle, char[] haystack){
    ArrayList<Integer> wordPos = new ArrayList<Integer>();
    if (needle.length > haystack.length ){
       System.out.println("The needle is longer than the haystack");
       System.exit(0);
    }
    int[] bad_shift = new int[256]; // 256
    int rest = 0;
    for (int i = 0; i < needle.length;i++ ) {
      if (needle[i] == '_') {
        rest = i;
      }
    }

    if (rest == 0) {
      rest = needle.length -1;
    }

    //setting the
    for(int i = 0; i < 256; i++){
      bad_shift[i] = rest;
    }

    int offset = 0, scan = 0;
    int last = needle.length - 1;
    for(int i = 0; i < last; i++){
      bad_shift[needle[i]] = last - i;
    }

    int maxoffset = haystack.length - needle.length;
    while(offset <= maxoffset){
      for(scan = last; needle[scan] == haystack[scan+offset] || needle[scan] == '_'; scan--){
        if(scan == 0){ // match found!
          wordPos.add(offset);
          break;
        }
      }
    if (scan == 0) {
      offset++;
    }
    else {
      offset += bad_shift[haystack[offset + last]];
    }
    }
    return wordPos;
  }
}
