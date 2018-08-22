class MultiVRadix{
  final static int NUM_BIT = 2;
  final static int MIN_NUM = 30;

  public static double VRadixMulti(int[] a){
    long systemTime = System.nanoTime();
    int[] b = new int[a.length];

    // a) Find max value of a[]
    int max = 0;
    for(int i = 0; i < a.length; i++){
      if(a[i] > max)
        max = a[i];
    }

    // b) Decide highest leftmost bit == 1
    int numberOfBitsInMax = NUM_BIT;
    while(max >= (1 << numberOfBitsInMax))
      numberOfBitsInMax++;
    //System.out.println("max: " + max);
    //System.out.println("Number of bits in max: " + numberOfBitsInMax);

    // c) Call on VenstreRadix
    int shift = numberOfBitsInMax - NUM_BIT;
    System.out.println("SHIFT: " + shift);

    VenstreRadix(a, b, 0, a.length - 1, numberOfBitsInMax, NUM_BIT);
    System.arraycopy(a, 0, b, 0, a.length);
    double tid = (System.nanoTime() - systemTime)/1000000.0;//nano to milli
    //testSort(a);
    return tid;
  }

  static void VenstreRadix(int[] a, int[] b, int left, int right, int shift , int maskLen){
    int mask = (1 << maskLen) - 1;
    int[] count = new int[mask + 1];

    // b) Count frequency
    for(int i = left; i < right; i++){
      count[(a[i] >> shift) & mask]++;
    }

    // e) Count accumulated values
    int acumVal = 0, j = 0;
    for(int i = 0; i <= mask; i++){
      j = count[i];
      count[i] = acumVal;
      acumVal += j;
    }

    // f) Move from a to b
    for(int i = 0; i < a.length; i++){
      b[ count[a[i] >> shift & mask]++] = a[i];
    }

    // System.arraycopy(from, startindex, to, startindex, size);
    // 1010 ->  0110
    // -------------
    // 0110 ->  1010
    // 1110     1011
    // -------------
    // 1011 ->  1110
    // 1101     1101

    // g) Call on recursive on next number
    // VenstreRadix(from, to, startindex, sluttindex, shift, masklen);
    //Math.min(shift, NUM_BIT)
    for(int i = 0; i < mask ; i++){
      if(count[i] != 0 || count[i] > count[i+1]){
        // if(count[i] < MIN_NUM){
        //   insertSort(a);
        // }
        // System.out.println("---------------");
        // for(int k = 0; k < b.length; k++){
        //   System.out.println("b[" + k + "]: "+ b[k]);
        // }
        //System.out.println("H: " + (shift - NUM_BIT));
        VenstreRadix(b, a, count[i] + 1, count[i+1] - 1, shift - NUM_BIT, Math.min(shift, NUM_BIT));
      }
    }
  }

  static void insertSort(int[] a){
    int i, t, max = a.length - 1;
    for(int k = 0; k < max; k++){
      if(a[k] > a[k+1]){
        t = a[k+1];
        i = k;

        while(i >= 0 && a[i] > t){
          a[i + 1] = a[i];
          i--;
        }
        a[i + 1] = t;
      }
    }
  }

  static void testSort(int[] a){
    for(int i = 0; i < a.length-1; i++){
      if(a[i] > a[i + 1]){
        System.out.println("FEIL: " +
          i + "a[" + i + "]: " + a[i] + " > a[" + (i + 1) + "]: " + a[i + 1]);
        return;
      }
    }
  }
}
