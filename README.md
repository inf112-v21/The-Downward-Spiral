
# INF112 The Downward Spiral

## Deloppgave 1: Organiser teamet

Gruppenavn: The Downward Spiral (TDS)

#### Planlegge møter:
https://doodle.com/poll/52hwhvctgxubvsbg?utm_source=poll&utm_medium=link

Alle medlemmene av teamet har kunnskaper fra INF101 og INF102

### Roller:

- **Lag-leder:** Thomas
    - Lag-leder skal representere teamet som en helhet og dra alle løse tråder sammen, skal ha et overordnet ansvar for hva som skal gjøres og planlegges
    - Thomas fikk teamleder fordi han har vært med på gruppeprosjekter gjennom webapplikasjon jobb hvor han har fått en viss forståelse av hvordan man burde organisere et team.

- **Kundekontakt:** Simen
    -  Kundekontakten skal samle spørsmål fra gruppen og snakke med kunden, dette er bindeleddet mellom “gruppen” og kunden(e)
    - Simen fikk denne rollen fordi han er flink til å samle ideer og formulere gode spørsmål.

- **Programmeringsansvarlig:** Kristian
    - Programmeringsansvarlig er i teorien en lag-leder, men for utviklerne.
      Jobben hans er å være tilgjengelig på spørsmål angående kode, og å lede diskusjoner på hvordan man bestemt skal implementere noe

- **Utviklere:** Isabel og Sondre 
    - En utvikler skal klare å tenke abstrakt og konseptuelt for å kunne løse problemer gjennom kode og utvikle ideene til kunden til et fungerende produkt

## Deloppgave 2: Velg og tilpass en prosess for laget

###Prosjektmetodikk:

Vi har valgt å bruke Kanban som prosjektmetodikk, med innspill og elementer fra andre metoder.
Da kan vi fokusere på et mindre antall oppgaver om gangen og produsere raske resultater.
Prosjekt tavlen er essensiell i denne fremgangsmåten og må holdes oppdatert kontinuerlig.

Vi vil både arbeide individuelt og benytte parprogrammering.

### Møter og hyppighet:

Vi planlegger møtene fortløpende, bruker verktøy som doodle for å enklere få oversikt over hvilket tidspunkt som passer best for neste møte. Vi møtes også fast hver fredag på gruppetimer.

### Kommunikasjon mellom møter

Bruke discord hyppig, planlegge underveis når man ser omfanget av oppgavene og får en dypere forståelse.

### Arbeidsfordeling

Dette blir fordelt i trello og blir diskutert på møter underveis:

**Link to trello:** https://trello.com/b/D3Y3MkLV/tavle

Vi har organisert trello på følgende måte:
- Backlog fanen er for kort som ikke har en fullstendig beskrivelse og ikke er diskutert ferdig,
denne må bearbeides og få en ordentlig beskrivelse før den kan bli flyttet til "Todo" fanen
  
- Todo fanen er kort som er ferdig bearbeidet og man kan sette seg opp på kortet for å begynne å jobbe
  (eventuelt sette opp flere personer om man skal jobbe sammen)
  
- Needs Feedback fanen skal i teorien brukes for alle kommits som endrer fuksjonalitet eller utseende
her burde man lage en pull request og få andre på laget og helst Programmeringsansvarlig for å se over.
  
- Paused er for kort som har blitt påbegynt, men som man måtte avvente til senere av forskjellige grunner.

- Til slutt er done for kort som er helt ferdig og prosessen kan starte på nytt med et nytt kort.

### Deling og oppbevaring av felles dokumenter, diagram og kodebase

Vi bruker google docs for at det enkelt skal være mulig å skrive samtidig.

Google dokumentet skal være det mest oppdaterte, og så oppdaterer vi readme filen fortløpende når vi føler det er aktuelt, typisk før en innleveringsfrist .

Trello skal brukes for å holde styr på oppgaver.

Github skal brukes for alt av kode.

### Planlegge første tiden

Vi planlegger den første tiden ved å ha et møte hvor vi planlegger første fasen av hvilke trello kort som burde gjøres og hvem som gjør dette.
I starten blir det fort mye parprogrammering for at alle skal få en grunnleggende forståelse.
Vi kommer også til å bruke litt tid på at alle skal bli kjent med hjelpemidlene vi skal benytte.

## Deloppgave 3: Få oversikt over forventet produkt

### Målet:

Målet for applikasjonen er å ha et plattformuavhengig spill som gir brukeren en fornøyelig spillopplevelse og tilfredsstiller MVP-kravene (og kanskje litt til ;))

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
    
## Deloppgave 4: Kode

Viser til github repository for gruppen.

Vi har benyttet oss av manuell testing av kode og applikasjon for denne innleveringen.

For spillbrett og spiller har vi kjørt applikasjonen og bekreftet at det visuelt ser ut som forventet, samt oppfører seg i henhold til intensjonen.

### Oppsummering:

Vi kommuniserte bra med hverandre og fikk delegert oppgavene effektivt, her hjalp det at alle var aktiv på Discord.
Det viste seg å være enklerer å bestemme tidspunkt for neste møte under hvert møte i stede for å benytte doodle. Vi har
stort sett benyttet parprogrammering når vi har jobber med spillet, derfor er commitene på github 
Det var litt vanskelig å komme inn i en gruppe mentalitet og oppdatere Trello slik at alle har oversikt over hvem som gjør hva. 
Dette er noe vi håper vi kommer til å forbedre over tid. Det var ikke noe stort som vi ikke fikk til. 
Vi fant ut at Doodle ikke fungerte så bra som vi trodde det ville, det var mye lettere å bare planlegge møter via dialog siden vi er kun et team på fem stykker. 
Vi opplever at vi er kommet godt i gang med oppgaven og har en klar oversikt over arbeidsoppgavene fram mot neste innlevering. 
Som et team arbeider vi bra sammen og er klar over hvilke utfordringer vi må jobbe mer med. Fremover har vi tenkt å ha en “dev” branch som vi pusher til også merger vi den tilslutt med master før vi skal levere en obligatorisk oppgave.

## Møtereferat #1 (fredag 5. februar kl 10:15 - 12:15):

Vi begynte med å sette opp nyttige hjelpemidler som Trello, Doodle, Google Docs og ikke minst github for å gjøre samarbeid mer effektivt.

Vi fordelte også roller på grunnlag av kunnskap og innstilling. Vi kom også på et passende gruppenavn.

Planla nytt møte onsdag 10. februar kl 18:30.

## Møtereferat #2 (onsdag 10. februar kl 18:30 - 20:00):

Spillbrett og spiller klassen er implementert.

Vi har ryddet delvis opp i dokumentasjonen. Har valgt prosjektmetodikk og begynt på å skrive litt brukerhistorier som vi skal ta opp igjen på neste møte.

Planla nytt møte torsdag 11. februar kl 12:30.

## Møtereferat #3 (torsdag 11. februar kl 12:30 - 15:00):

Vi har arbeidet videre med brukerhistorier og dokumentasjon. Under møtet diskuterte vi hvilke utfordringer vi har hatt så langt og hvordan disse kan forbedres. Vi ble også enig om at Doodle er et hjelpemiddel som vi ikke behøver å bruke allikevel.

Neste møte blir på gruppetimen fredag 12. februar kl 10.15.

## Møtereferat #4 (fredag 12. februar kl 10:15 - 12:00):

issues / mangler:

Koden skal være plattformuavhengig, altså skal den fungere uavhengig av operativsystem.

Dokumentert kode
