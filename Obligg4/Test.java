public class Test {
  public static void main(String[] args) {
    char[] needle,hay;
    needle = "abc".toCharArray();
    hay = "abcaacabc".toCharArray();

    for (int i: Obligg4.horspool(needle,hay)) {
      System.out.print("\n" + i + " ");
      for (int j = 0;j <needle.length; j++) {
        System.out.print(hay[i + j]);
      }
    }
    System.out.println();
    needle = "a_c".toCharArray();
    for (int i: Obligg4.horspool(needle,hay)) {
      System.out.print("\n" + i + " ");
      for (int j = 0;j <needle.length; j++) {
        System.out.print(hay[i + j]);
      }
    }
    System.out.println();
    needle = "b___".toCharArray();
    for (int i: Obligg4.horspool(needle,hay)) {
      System.out.print("\n" + i + " ");
      for (int j = 0;j <needle.length; j++) {
        System.out.print(hay[i + j]);
      }
    }
    System.out.println();

  }
}
