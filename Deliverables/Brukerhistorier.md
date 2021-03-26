### Brukerhistorier i prioritert rekkefølge:

#### 1. Vise et spillebrett:
- Som spiller ønsker jeg å se spillebrettet slik at jeg kan se tilstanden til det pågående spillet.
    - Akseptansekriterier:
        - Brettet har forventet størrelse
        - Objekter vises på brettet på tiltenkt sted
        - Objekter er intuitive
    - Arbeidsoppgaver:
        - Designe og implementere spillbrett med Tiled
        - Importere texture

#### 2. Vise brikke på spillebrett:
- Som spiller ønsker jeg å se brikkene på brettet slik at jeg kan planlegge mine trekk grundig og med ypperlig presisjon.
    - Akseptansekriterier:
        - Roboten skal være synlig på brettet
        - Roboten skal være plassert som forventet på brettet
    - Arbeidsoppgaver:
        - Implementere Player klasse
#### 3. Flytte brikke (vha taster e.l. for testing):
- Som spiller ønsker jeg å kunne flytte brikken min for å kunne utføre planen jeg har lagd
    - Akseptansekriterier:
        - Roboten kan ikke gå utenfor brettet
        - Roboten går i forventet retning
    - Arbeidsoppgaver:
        - Implementere movement logikk

#### 4. Robot besøker flagg:
- Som spiller må jeg kunne besøke flagg for å avansere i spillet
    - Akseptansekriterier:
        - Når en robot besøker flagg får den en indikasjon på at det flagget er plukket opp
        - Roboten vinner ved å besøke flaggene i riktig rekkefølge
    - Arbeidsoppgaver:
        - Boolean condition for ulike flagg

#### 5. Robot vinner ved å besøke flagg:
- Som spiller ønsker jeg å få en indikasjon på at jeg har vunnet slik at spillet avsluttes i henhold til reglene
    - Akseptansekriterier:
        - Når en robot besøker flagg oppdateres player texture
        - “Continue?” screen
    - Arbeidsoppgaver:
        - Logikk som sjekker om en spiller har alle flaggene
        - En skjerm som kommer opp når en spiller har vunnet som spør de andre spillerne om de vil fortsette

#### 6. Spille fra flere maskiner (vise brikker for alle spillere, flytte brikker for alle spillere):
- Som spiller ønsker jeg å spille fra flere maskiner slik at jeg kan konkurrere med andre via internett
    - Akseptansekriterier:
        - Når flere spillere kobler seg til samme server vises brikkene til alle spillerne på brettene til alle spillerne.
    - Når en spiller beveger seg på sitt brett oppdateres posisjonen til spilleren på alle andre sitt brett.
    - Arbeidsoppgaver:
    - Lage en server som håndterer alle tilkoblinger, og sender oppdateringer til alle tilkoblede enheter dersom det trengs.
    - Gjøre det mulig for spill-klassen å koble seg til serveren, og i tillegg sende oppdateringer til serveren hvis f.eks. spilleren beveger seg.

#### 7. Dele ut kort
- Som spiller ønsker jeg å få delt ut kort slik at jeg kan velge 5 kort
    - Akseptansekriterier
- spilleren kan se de mulige kortene
- spilleren kan velge ut x antall kort avhengig av skade
- kortene skal fjernes fra bunken etter der er delt ut

    - Arbeidsoppgaver:
        - implementere kortstokk med kort som beskrevet i manualen
        - spilleren må kunne “se” kortene. Foreløpig bare i terminalen
        - kort som deles ut må fjernes fra bunken

#### 8. Velge opptil 5 kort
- Som spiller ønsker jeg å kunne velge de beste av de utdelte kortene så jeg kan flytte meg der jeg ønsker  ...
    - Akseptansekriterier:
        - det er mulig å velg ut enkelte kort fra de utdelte kortene
        - disse kortene skal beholdes resten av runden
    - Arbeidsoppgaver:
        - kan velge og forkaste kort fra hånden
            - kort som ikke velges skal forkastes

#### 9. Bevege robot ut fra valgte kort
- Som spiller ønsker jeg at roboten min skal bevege seg ut fra kortene jeg valgte slik at den beveger seg som planlagt...
    - Akseptansekriterier:
        - Roboten beveger seg som forventet fra kortene
    - Arbeidsoppgaver:
        - må kunne velge ett eller flere kort
        - roboten utfører handlingen på kortet (gå, rygg, roter)
    
### 10. En Main menu for spillet
- Som spiller ønsker jeg en main menu som er lett å navigere og har intuitive knapper som gjør det som står.
    - Akspetansekriterier:
        - Tittelskjerm for spillet
        - Knapper som “Play & Host”, “Connect” og “Host”
        - Når musepekeren går over knappene skal de ha en animasjon som viser at knappen er trykkbar
        - Når en spiller trykker på “Play & Host” startes det en server og spilleren blir tilkoblet til serveren
        - Når en spiller trykker på “Connect” kobler de seg til en server som allerede er satt opp
        - Når en spiller trykker på “Host” setter de opp en server
    - Arbeidsoppgaver:
        - Lage en MenuScreen klasse som viser Main menu
        - En klasse som holder styr på hvilken skjerm som skal bli vist til enhver tid
        - Lage tittelskjerm og knapper i Photoshop
        - Gjøre knappene interaktive med hover 
        - Når brukeren trykker på skjermen og pekeren er over en av knappene skal det som står for hver sin knapp i akseptansekriteriene skje
### 11. End skjerm når en spiller vinner
- Som spiller ønsker jeg at når jeg vinner spillet skal det komme opp en skjerm hvor det står at jeg har vunnet og en knapp som starter spillet på nytt.
    - Akseptansekriterier:
        - En skjerm som det står “Victory” på kommer opp når en spiller har vunnet
        - En “Restart?” og “Exit” knapp vises på skjermen
        - Knappene skal ha hover, slik at når musepekeren går over knappen skjer det en intuitiv animasjon slik at brukeren skjønner at de kan trykke
        - Når en spiller trykker på “Restart?” skal det bli tatt tilbake til MenuScreen
        - Når en spiller trykker på “Exit” skal appen lukkes
    - Arbeidsoppgaver:
        - Lage en EndScreen klasse som viser en skjerm når en spiller har vunnet
        - Når en spiller har plukket opp det tredje flagget skal skjermen bli satt til EndScreen
        - Lage vinnerskjerm og knapper i Photoshop
        - Gjøre knappen interaktiv med hover
        - Når brukeren trykker på “Restart?” blir de sendt tilbake til menyen
### 12. Vegger er solide
- Som spiller ønsker jeg at veggene fungerer som forventet, 
  slik at jeg ikke kan gå gjennom dem.
    - Akseptansekriterier:
        - Når en spiller står på en brikke med en vegg, 
          kan den ikke bevege seg i retningen der veggen står.
    - Arbeidsoppgaver:
        - Sjekk om spilleren står på en tile med vegg. 
          Spilleren skal da ikke kunne bevege seg i den retningen veggen er, 
          men skal kunne gå langs veggen uten hindring 
 
### 13. Roboter kan ta skade (ikke implementert enda)
- Som spiller ønsker jeg at robotene på brettet kan ta skade fra brikken min og fra lasere. Jeg ønsker også at jeg kan ta skade fra andre spillere og fra lasere.
    - Akseptansekriterier:
        - Spilleren tar skade av å stå på et hull
        - Spilleren tar skade av å bli truffet av laser
        - Spilleren tar skade av å faller utenfor brettet 
    - Arbeidsoppgaver:
        - Sjekk om spiller befinner seg på felt med laser/hull eller utenfor brettet
        - Lage en hp counter for spilleren som går opp/ned ettersom spilleren tar skade
### 14. Belter og Gears
 - Som spiller ønsker jeg å kunne ta i bruk elementer på brettet, slik at jeg kan spille spillet som tiltenkt.
    - Akseptansekriterier: 
        - Spillere som stopper på en belte tile vil bli flyttet i retningen beltet peker, en eller to tiles avhengig av fargen på beltet. 
        - Spiller som stopper på en gear tile vil bli rotert mot høyre eller venstre avhengig av merkingen på gear
    - Arbeidsoppgaver:
        - Sjekk om spilleren befinner seg på et belte eller gear
        - Flytt spilleren uten å endre retning hvis spilleren står på belte
        - Roter spilleren hvis den står på gear
### 15. Vegger
- Som spiller ønsker jeg å bli stoppet av vegger, slik at jeg kan forholde meg til reglene i spillet og det aktuelle brettet.
    - Akseptansekriterier:
        - Spiller kan ikke gå gjennom vegger med kort eller piltaster/wasd
        - Spiller skal kunne gå langs vegger
    - Arbeidsoppgaver: 
        - Sjekke om spilleren er på en tile med vegg
        - Sjekke om spilleren prøver å gå til en tile med vegg
