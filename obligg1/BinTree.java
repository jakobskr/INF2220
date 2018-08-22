import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class BinTree {
  private BinNode rot = new BinNode();
  private int antNoder, avgDybde, maxDybde = 0;
  private int[] npdListe;
  char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

  private class BinNode {
    public BinNode() {
      //creates a node without data;
    }

    public BinNode(String n) {
      this.data = n;
    }
    private String data = null;
    private BinNode mindre,storre = null;
    private int dybde,hoyde;

    public void settData(String data) {
      this.data = data;
    }

    public void settInn(String n) {
      if (data.compareTo(n) > 0) {
        if (mindre == null) {
          mindre = new BinNode(n);
        }
        else {
          mindre.settInn(n);
        }
      }
      else if (data.compareTo(n) < 0) {
        if (storre == null) {
          storre = new BinNode(n);
        }
        else {
          storre.settInn(n);
        }
      }
    }

    public boolean finnOrd(String ord) {
      if (data.equalsIgnoreCase(ord)) {
        return true;
      }
      else if (data.compareTo(ord) > 0) {
        if (mindre != null) {
          return mindre.finnOrd(ord);
        }
        else {
          return false;
        }
      }
      else  {
        if (storre != null) {
          return storre.finnOrd(ord);
        }
        else {
          return false;
        }
      }
    }

    public String toString() {
      return data;
    }

    public void skrivUt() {
      System.out.println(data + "\n");
      if (mindre != null) {
        System.out.print("mindre ");
        mindre.skrivUt();
      }
      if (storre != null) {
        System.out.print("Storre ");
        storre.skrivUt();
      }
    }

    public void beregnDybde(int d) {
      this.dybde = d;
      if(mindre != null) {
        mindre.beregnDybde(dybde + 1);
      }
      if(storre != null) {
        storre.beregnDybde(dybde + 1);
      }
    }

    public int totalDybde() {
      int tempD = dybde;
      if (mindre != null) {
        tempD += mindre.totalDybde();
      }
      if (storre != null) {
        tempD += storre.totalDybde();
      }
      return tempD;
    }

    public int antallNoder() {
      int tempN = 1;
      if (mindre != null) {
        tempN += mindre.antallNoder();
      }
      if (storre != null) {
        tempN += storre.antallNoder();
      }
      return tempN;
    }

    public int maxDybde() {
      int temp = 0;
      this.hoyde = 0;
      if (mindre != null) {
        temp = mindre.maxDybde() + 1;
        if (hoyde < temp) {
          this.hoyde = temp;
        }
      }
      if (storre != null) {
        temp = storre.maxDybde() + 1;
        if (hoyde < temp) {
          this.hoyde = temp;
        }
      }
      return this.hoyde;
    }

    public String forsteOrd(){
      if (mindre != null) {
        return mindre.forsteOrd();
      }
      else return data;
    }

    public String sisteOrd(){
      if (storre != null) {
        return storre.sisteOrd();
      }
      else return data;
    }

    public void nodePerDybde(int[] list) {
      list[dybde] = list[dybde] + 1;
      if (mindre != null) {
        mindre.nodePerDybde(list);
      }
      if (storre != null) {
        storre.nodePerDybde(list);
      }
    }

  }//lukker node klassen

  public void lesFraFil(File fil) throws FileNotFoundException {
    Scanner filScan = new Scanner(fil);
    while(filScan.hasNextLine()) {
      settInn(filScan.nextLine());
    }
    filScan.close();
  }

  public void skrivUt() {
    rot.skrivUt();
  }

  public boolean finnOrd(String ord) {
    return rot.finnOrd(ord);
  }

  public void settInn(String n) {
    if (rot.data == null) {
      rot.settData(n);
    }
    else {
      rot.settInn(n);
    }
  }

  public void avgDybde() {
    antallNoder();
    avgDybde = rot.totalDybde() / antNoder;
    System.out.println("avg: " + avgDybde);
  }

  public void beregnDybde() {
    if(rot != null) {
      rot.beregnDybde(0);
    }
  }

  public void maxDybde() {
    maxDybde = rot.maxDybde();
    System.out.println("Dybden paa Treet: " + maxDybde);
  }

  public void forsteOrd() {
    String forste = rot.forsteOrd();
    System.out.println("minste ordet i treet " + forste);
  }

  public void antallNoder() {
    antNoder = rot.antallNoder();
    System.out.println("total antall Noder: " + antNoder);
  }

  public void sisteOrd() {
    String siste = rot.sisteOrd();
    System.out.println("storste ordet i treet " + siste);
  }

  public void nodePerDybde() {
    maxDybde();
    npdListe = new int[maxDybde + 1];
    rot.nodePerDybde(npdListe);
    for (Integer ndp: npdListe) {
      System.out.print(ndp + " -- ");
    }
  }

  public void menu() {
    String sokeOrd = "";
    Scanner scan = new Scanner(System.in);
    while(true) {
      System.out.println("Skriv inn ordet du onsker aa finne!");
      sokeOrd = scan.nextLine();
      if (sokeOrd.equalsIgnoreCase("q")) {
        System.out.println("programmet avslutter, skriver ut statistikk");
        beregnDybde();
        avgDybde();
        forsteOrd();
        sisteOrd();
        nodePerDybde();
        System.exit(0);
        }
      else if (sokeOrd == "") {
        System.out.println("du skrev ingenting.");
      }
      else{
        if (finnOrd(sokeOrd)) {
          System.out.println("ordet ble funnet");
        }
        else {
          System.out.println("fant ikke " + sokeOrd);
          finnVar(sokeOrd);
        }
      }
    }
  }

    public void finnVar(String sokeOrd) {
      ArrayList<String> ret = new ArrayList<String>();
      ArrayList<String> gen = genererOrd(sokeOrd);
      for (String s: gen) {
        if (finnOrd(s)) {
          System.out.println("mente du " + s);
        }
      }

    }

    public ArrayList<String> genererOrd(String sokeOrd) {
      ArrayList<String> ret = new ArrayList<String>();
      char[] temp = new char[sokeOrd.length()];

      //lager nye ord ved aa endre en bokstav
      for (int i = 0; i < temp.length; i++) {
        temp = sokeOrd.toCharArray();
        for (int j = 0; j < alphabet.length ; j++) {
          temp[i] = alphabet[j];
          if (!ret.contains(new String (temp))) {
            ret.add(new String(temp));
          }
        }
      }

      //lager nye ord ved aa fjerne en boksav
      for (int i = 0; i < temp.length; i++) {
        String newstr = sokeOrd.substring(0, i) + sokeOrd.substring(i + 1);
        if (!ret.contains(newstr)) {
          ret.add(newstr);
        }
      }

      //lager nye ord ved aa fjerne en boksav
      for (int i = 0;i < sokeOrd.length() + 1; i++) {
        if (i == 0) {
          temp = (" " + sokeOrd).toCharArray();
        }
        else if (i == sokeOrd.length()) {
          temp = (sokeOrd + " ").toCharArray();
        }
        else {
          temp = (sokeOrd.substring(0,i) + " " + sokeOrd.substring(i,sokeOrd.length())).toCharArray();
        }
        for (int j = 0; j < alphabet.length ; j++) {
          temp[i] = alphabet[j];
          if (!ret.contains(new String (temp))) {
            ret.add(new String(temp));
          }
        }
      }

      //lager nye ord ved switche plasser paa bokstaver
      for (int i = 0; (i + 1) < sokeOrd.length(); i++) {
        temp = sokeOrd.toCharArray();
        temp[i] = sokeOrd.charAt(i + 1);
        temp[i + 1] = sokeOrd.charAt(i);
        if (!ret.contains(new String (temp))) {
          ret.add(new String(temp));
        }
      }

      return ret;
    }

  static public void main(String[] args) throws Exception {
    BinTree tree = new BinTree();
    tree.lesFraFil(new File("dic.txt"));
    tree.menu();
  }
}//lukker BinTree
