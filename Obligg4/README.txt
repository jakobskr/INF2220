1.
  Koden er for det meste en rett frem implementasjon av Boyer-Moore-Horsepool algoritme, men noen få forskjeller.
  Den første er hvordan 'Bad Character Shift' blir laget, som er endret for å ta høyde for wildcard.
  Tankegangen var at hvert eneste tegn i høystakken som ikke er i nålen, egentlig er med i nålen i form av wildcarded.
  For eksempel med en høystakk som "detteergoy" og en nål "ee_" så kan høystakken tolkes som "_e__ee____".

  Den andre er if-testen som sjekker om nåla og høystakken matcher, som sier at om nålas tegn er "_" så er testen true
  uavhengig av av hva tegnet i høystakken er.

2.
  javac *.java

3.
  Oblig3.java

4.
  at innfilen bare inneholder ASCII-tegn, og at man ikke skal lese alle newline-tegn mellom to gyldige tegn som et enkelt mellomrom.

5.
  Ingen som jeg kommer på. Mulig at filinnlesningen kan se på som noe spessielt, sto lite om akuratt hvordan filene skal leses.

6.
  Alt virker.

7.
  Forelesnings slidsene om Takstalgoritme fra 26. oktober var til stor hjelp.
