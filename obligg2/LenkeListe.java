import java.util.Iterator;
@SuppressWarnings("unchecked")

public class LenkeListe<T> implements Liste<T> {

  protected Node foran;
  protected Node bak;
  protected Node temp;
  protected int storrelsen;

  public LenkeListe() {
    foran = new Node();
    bak = new Node();
    foran.settNeste(bak);
    bak.settForrige(foran);
  }

  @Override
  //storrelsen er en teller som blir oker/minsker med settInn/fjern
  public int storrelse() {
    return storrelsen;
  }

  @Override
  //returnerer false vis listen er tom
  public boolean erTom() {
    if (foran.hentNeste() == bak) {
      return true;
    }
    else {
      return false;
    }
  }

  @Override
  //setter et element fremst i listen
  public void settInn(T element) {

    //setter at det forste elementet havner i midten
    if (foran.hentNeste() == bak) {
      Node temp = new Node(element);
      temp.settNeste(bak);
      bak.settForrige(temp);
      foran.settNeste(temp);
      temp.settForrige(foran);
      temp = null;
      storrelsen++;
    }

    else {
      Node temp = new Node(element);
      temp.settNeste(foran.hentNeste());
      foran.hentNeste().settForrige(temp);
      foran.settNeste(temp);
      temp.settForrige(foran);
      temp = null;
      storrelsen++;
    }
  }

  @Override
  //fjerner det forste elementet i listen
  public T fjern() {
    if (! (foran.hentNeste() == bak)) {
      T verdi;
      verdi = foran.hentNeste().hentInnhold();
      foran.settNeste(foran.hentNeste().hentNeste());
      storrelsen--;
      return verdi;
    }

    else {
      return null;
    }
  }

  public Iterator<T> iterator() {
    return new ListeIterator();
  }

  //lagde en beholder klasse for aa holde paa nodene
  protected class Node {
    private T innhold;
    Node neste, forrige;

    //konstruktoren som blir brukt for de vanlige nodene
    public Node(T element) {
      innhold = element;
      //neste = null;
    }

    //en tom konstruktor som blir brukt for foran og bak nodenene
    public Node() {
    }

    //setter den neste noden til noe nytt;
    public void settNeste(Node node) {
      neste = node;
    }

    //setter den forrige noden til noe nytt
    public void settForrige(Node node) {
      forrige = node;
    }

    //henter neste node
    public Node hentNeste() {
      return neste;
    }

    //henter noden foran
    public Node hentForrige(){
      return forrige;
    }

    //henter det som er i noden
    public T hentInnhold() {
      return innhold;
    }
  }

  //iteratoren til Lenkelisten
  protected class ListeIterator implements Iterator<T> {
    Node denneNode = foran;

    @Override
    public T next() {
      denneNode = denneNode.hentNeste();
      return denneNode.hentInnhold();
    }

    @Override
    //sjekker om det er flere elementer i lenkelisten
    public boolean hasNext() {
      if (denneNode.hentNeste() == bak) {
        return false;
      }

      else {
        return true;
      }

    }

    public void remove() {

    }
  }

}
