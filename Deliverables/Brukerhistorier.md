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