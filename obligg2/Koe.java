//First in first out liste
public class Koe<T> extends LenkeListe<T> {

  //Setter ett element bakerst i listen
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

    //her setter den inn elementet bakerst i listen
    else {
      Node temp = new Node(element);
      temp.settForrige(bak.hentForrige());
      bak.hentForrige().settNeste(temp);
      bak.settForrige(temp);
      temp.settNeste(bak);
      temp = null;
      storrelsen++;
    }
  }

  //fjerner et element fra foran i listen
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


}
