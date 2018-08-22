//import java.util.Random;

public class VenstreRadix {
  static int NUM_BIT = 8;
  final static int MIN_NUM = 16; // mellom 16 og 60, kvikksort bruker 47
  static int ANTALL_KALL = 0;
  static boolean aTOb = true;

  public static double vRadixMulti2 (int[] a) {
    long tt = System.nanoTime();
    NUM_BIT = 8;
    int [] b = new int [a.length];
    int max = 0;
    for(int i = 0; i < a.length; i++){
      if(a[i] > max)
        max = a[i];
    }
    int numbit = 0;
    while (max >= (1<<numbit)) {
      numbit++;
    }

    if (NUM_BIT > numbit) {
      NUM_BIT = numbit ;
    }
    venstreRadix2(a, b, 0, a.length, numbit , NUM_BIT);
    System.arraycopy(b, 0, a, 0, a.length);
    testSort(a);
    double tid = (System.nanoTime() -tt)/1000000.0;
    return tid;
  }

  static public void venstreRadix2 ( int [] a, int [] b, int left, int right, int shift, int maskLen) {
    int mask = (1 << maskLen) - 1;
    int[] count = new int[mask + 1];
    for (int i = left; i < right; i++ ) {
      count[(a[i]>>shift) & mask]++;
    }
    int acumVal = left, j = 0;
    for(int i = 0; i <= mask; i++){
      j = count[i];
      count[i] = acumVal;
      acumVal += j;
    }
    for(int i = left; i < right; i++){
      b[count[a[i] >> shift & mask]++] = a[i];
    }
    if (shift <= 0) {
      return;
    }
    shift = (shift - NUM_BIT >= 0) ? (shift - NUM_BIT) : 0;

    for (int i = left; i < right ;i++ ) {
      a[i] = b[i];
    }
    //left er startindexen til sifferverdien
    //count[i] er sluttindexen til sifferverdien
    for(int i = 0; i <= mask; i++){
      if (count[i] - left <= 1) {
        continue;
      }
      if(count[i] - left <= MIN_NUM && count[i] - left >= 2){
        insertSort(b, left, count[i]);

      }
      else{
        venstreRadix2(a, b, left, count[i], shift , NUM_BIT);
      }
      left = count[i];
    }
  }//lukker

  //tatt fra forelesning sliden i inf2220,
  //endret selv til å bare sortere en del av arrayer.
  static void insertSort(int[] a, int left, int right){
    int i, t;
    for(int k = left; k < right - 1; k++){
      if(a[k] > a[k + 1]){
        t = a[k + 1];
        i = k;
        while(i >= left && a[i] > t){
          a[i + 1] = a[i];
          i--;
        }
        a[i + 1] = t;
      }
    }
  }//insertSort end

//tatt fra apendix a.
static public void testSort(int [] a){
  for (int i = 0; i< a.length-1;i++) {
    if (a[i] > a[i+1]){
      System.out.println("SorteringsFEIL på: "+
      i +" a["+i+"]:"+a[i]+" > a["+(i+1)+"]:"+a[i+1]);
      return;
      }
    }
  }
}
