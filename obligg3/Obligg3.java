import java.util.Random;
import java.util.Arrays;

public class Obligg3 {
  public static void main(String[] args) {
    Random random = new Random();
    double tid = 0.0;
    int[] a = {1};
    int[] a2;
    int kjoringer = 5;
    double[] radixTider = new double[kjoringer];
    double[] quickTider = new double[kjoringer];
    String[] utskrift= new String[kjoringer];

    for (int i = 100; i <= 10000000; i = i * 10) {
        for (int b = 0;b < kjoringer ;b++ ) {
          a = new int[i];
          a2 = new int[i];
          for (int j = 0;j < i;j++) {
            a[j] = random.nextInt(i);
            a2[j] = a[j];
          }
          radixTider[b] = VenstreRadix.vRadixMulti2(a);
          long tt = System.nanoTime();
          Arrays.sort(a2);
          quickTider[b] = (System.nanoTime() -tt)/1000000.0;
        }

        System.out.println("\nMedian of "+ kjoringer + " runs of " + i + " sorted numbers");
        Arrays.sort(radixTider);
        System.out.println("radix sort : " + radixTider[5/2]);
        Arrays.sort(quickTider);
        System.out.println("Quick sort: " + quickTider[5/2]);
        System.out.println("Speed up: " + quickTider[5/2] / radixTider[5/2]);
    }
  }

}
