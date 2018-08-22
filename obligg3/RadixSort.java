import java.util.Arrays;

public class RadixSort{
  final static int MILLION = 1000000;
  final static int DIGIT_BIT_SIZE = 2; //9 // Finn selv ut om 6-13 er kanskje bedre
  final static int INSERTION_LIMIT = 10;//30; // mellom 16 og 60, kvikksort bruker 47
  static boolean doDebug = true;
  int numBits = 0;

  /*
  ██      ███████ ███████ ████████     ██████   █████  ██████  ██ ██   ██
  ██      ██      ██         ██        ██   ██ ██   ██ ██   ██ ██  ██ ██
  ██      █████   █████      ██        ██████  ███████ ██   ██ ██   ███
  ██      ██      ██         ██        ██   ██ ██   ██ ██   ██ ██  ██ ██
  ███████ ███████ ██         ██        ██   ██ ██   ██ ██████  ██ ██   ██
  */

  public void debug(String s){
    if (doDebug) System.out.println(s);
  }

  //Sorterer a[] stigede. Antar 0 ≤ a[i] < 2^32
  double leftRadixMulti(int[] a) {
    double startTime = System.nanoTime();

    //Finding max number
    int max=0;
    for (int i=0; i<a.length; i++)
      if (a[i] > max) max = a[i];
    //Finding numBits: Mest venstre bit (max's antall bit)
    for (numBits=0; (max >> numBits) > 0; numBits++);

    //Debug info
    int numDigits = numBits / DIGIT_BIT_SIZE;
    int finalDigitSize = numBits % DIGIT_BIT_SIZE;
    debug("Max number b"+bin(max)+" uses "+numBits+" bits");
    debug("Using "+numDigits+" digits of "+DIGIT_BIT_SIZE+" bits + "+finalDigitSize);
    debug("A in: "+binArr(a));
    debug("________________________________________________________________________");

    //Recursive call leftRadix(inputArr, ouputArr, arrIndexLeft, arrIndexRight, digitBitIndexStart, digitBitSize)
    int[] b = new int[a.length];
    leftRadix(a, b, 0, a.length, numBits, DIGIT_BIT_SIZE); //Right args?
    double endTime = System.nanoTime();

    debug("Finished leftRadix recursion");
    debug("a:"+binArr(a));

    testSort(a);
    return (endTime-startTime)/MILLION; //Div by one million to nano-->milli
  }

  // Sorter a[left..right] på siffer med start i bit: digStartBit, og med lengde: digLen bit,
  // Plasserer sortert versjon av a[] i output-array b[].
  void leftRadix (int[] a, int[] b, int left, int right, int digStartBit, int digLen){
    int mask = (1<<digLen) -1; //Make a sequence of ones, digLen long.
    int[] count = new int [mask+1];

    //Find digit length of final digit
    int shift = digStartBit - digLen;
    if (shift <= 0){
      digLen = shift + digLen; //Necessary?
      shift = 0;
    }
    debug("Doing radixSort for a["+left+".."+right+"]:"+(right-left)+", on digit starting at "+shift+" with len "+digLen);

    //d) count[]=count the frequency of each radix value in a[]
    for (int i=left; i<right; i++){
      int digit = (a[i] >> shift) & mask;
      //System.out.print(dec(a[i]) +": "+bin(a[i]) +" >> "+dec(shift)+" & "+dig(mask)+" = "+dig(digit));
      count[digit]++;
      //System.out.println("\tcount["+dig(digit)+"]: "+dec(count[digit]));
    }

    //e) add up in count
    int nextFreeIndex = left;
    for (int i=0; i < count.length; i++){
      int valueCount = count[i];
      //System.out.println("Value "+dig(i)+", which there are "+dec(count[i])+" of, starts at "+dec(nextFreeIndex));
      count[i] = nextFreeIndex;
      nextFreeIndex += valueCount;
    }

    //f: Move sorted numbers in a[left..right] to b
    for (int i=left; i<right; i++){
      int digitValue = (a[i]>>shift) & mask;
      int sortedIndex = count[digitValue]++;
      b[sortedIndex] = a[i];
    }

    //Kopier forandringer fra b[] til a[]
    //System.out.println("A in: "+binArr(a));
    for(int i=left; i<right; i++){
      a[i] = b[i];
    }
    //System.out.println("B out:"+binArr(b));

    //Set digStartBit to start index of next digit
    digStartBit -= digLen;
    if (digStartBit <= 0) return; //We're finished.
    //System.out.println("count: "+Arrays.toString(count));

    //TODO: Comments
    int prev = 0;
    for (int i=0; i<count.length; i++){
      int n = right-left;
      if (n < INSERTION_LIMIT){ //small enough to insertionSort
        if (n > 1) insertionSort(a, prev, count[i]);
      }
      else { //Big enough to radixSort
        leftRadix(a, b, prev, count[i], digStartBit, digLen);
      }
      prev = count[i];
    }
  }

  /*
  ██████  ██████  ██ ███    ██ ████████ ███████
  ██   ██ ██   ██ ██ ████   ██    ██    ██
  ██████  ██████  ██ ██ ██  ██    ██    ███████
  ██      ██   ██ ██ ██  ██ ██    ██         ██
  ██      ██   ██ ██ ██   ████    ██    ███████
  */

  public boolean testSort(int [] a){
    for (int i = 0; i< a.length-1;i++) {
      if (a[i] > a[i+1]){
        System.out.println("[!] Incorrect sort:" +
        " a["+i+"]:"+a[i]+" > a["+(i+1)+"]:"+a[i+1]);
        return false;
      }
    }

    debug("Successfully sorted!");
    return true;
  }


  //Represent a single digit as binary
  String dig(int n){
    String bin = Integer.toBinaryString(n);
    String bits = bin;
    for (int i=0; i<DIGIT_BIT_SIZE-bin.length(); i++){
      bits = "0"+bits;
    }
    return  bits ;
  }

  //Represent a binary number, divided into digits
  String bin(int n){
    String bin = Integer.toBinaryString(n);

    String bits = bin;
    for (int i=0; i<numBits-bin.length(); i++){
      bits = "0"+bits;
    }

    String s = "";
    for (int i=0; i<bits.length(); i++){
      if (i!=0 && i%DIGIT_BIT_SIZE==0){
        s += " ";
      }
      s += bits.charAt(i);
    }
    return  s ;
  }

  //String represention for array of binary numbers
  String binArr(int[] arr){
    String s = "[";
    for (Integer i : arr){
      s += dec(i)+"="+bin(i)+", ";
    }
    return s.substring(0, s.length()-2) + "] "+arr;
  }

  //Colorize decimal number to easily keep track on number types
  String dec(int n){
    return  n + "";
  }

  /*
  ███    ██  ██████  ███    ██       ██████   █████  ██████  ██ ██   ██
  ████   ██ ██    ██ ████   ██       ██   ██ ██   ██ ██   ██ ██  ██ ██
  ██ ██  ██ ██    ██ ██ ██  ██ █████ ██████  ███████ ██   ██ ██   ███
  ██  ██ ██ ██    ██ ██  ██ ██       ██   ██ ██   ██ ██   ██ ██  ██ ██
  ██   ████  ██████  ██   ████       ██   ██ ██   ██ ██████  ██ ██   ██
  */

  double quickSort(int[] a){
    double startTime = System.nanoTime();
    Arrays.sort(a);
    double endTime = System.nanoTime();
    testSort(a);
    return (endTime-startTime)/MILLION; //Div by one million to nano-->milli
  }

  void insertionSort(int[] a){
    insertionSort(a, 0, a.length);
  }

  void insertionSort(int[] a, int minIndex, int maxIndex){
    int i,j,temp;
    for (i=(minIndex+1); i < maxIndex; i++){
      for (j=i; j>minIndex; j--){
        if (a[j] < a[j-1]){ //Switch around
          temp = a[j];
          a[j] = a[j-1];
          a[j-1] = temp;
        }
      }
    }
  }

}
