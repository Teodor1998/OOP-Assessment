
                        ##############################
                        #           README           #
                        #                            #
                        #   Nume Proiect: Tema POO   #
                        #  Creat de: Apostol Teodor  #
                        #        Grupa: 322CC        #
                        #    Deadline: 13.01.2018    #
                        #                            #
                        ##############################



	Schema proiectului:
    PROIECT
    |   src
    |   | 
    |   | //Pentru Mecanism (Task 1)
    |   | Gestiune.java
    |   | Produs.java
    |   | ProdusComandat.java
    |   | Factura.java
    |   | IMagazin.java
    |   | Magazin.java
    |   | MarketFactory.java
    |   | MiniMarket.java
    |   | MediumMarket.java
    |   | HyperMarket.java
    |   | 
    |   | 
    |   | //Pentru GUI (Task 2)
    |   | Main.java
    |   | Login.java
    |   | MakeAccount.java
    |   | LoadFiles.java
    |   | Principala.java
    |   | ProductManage.java
    |   | Statistici.java
    |   | ProduseOrdonate.java
    |   | AdaugareProdus.java
    |   | PretTara.java
    |   | StergeProdus.java
    |   | EditareProdus.java
    |   | EditTara.java
    |   | CautaProdus.java
    |   O--O
    |   
    |   accounts.txt
    |   
    |   produse.txt
    |   taxe.txt
    |   facturi.txt
    |   out.txt
    |   
    |   //Imagini (trebuie sa fie copiate in bin)
    |   //OBS: Eclipse nu mi le copiaza la build in bin
    |   //Se poate sa necesite o copiere manuala in bin
    |   
    |   app_icon.png
    |   main_background.jpg
    |   produse_background.jpg
    |   register_background.jpg
    |   start_background.jpg
    |   tari_background.jpg
    O--O
	
	##########
	# Task 1 #
 	##########

	Pentru realizarea acestui task am implementat clasele si
specificatiile din scheletul prezentat in cerinta.
	
Clasa Produs:
- Implementeaza metodele din schelet si toString()
- Voi explica modul in care e utilizata mai tarziu

Clasa ProdusComandat:
- Implementeaza metodele din schelet si toString()
- Voi explica modul in care e utilizata mai tarziu

Clasa Factura:
- Contine un vector de elemente ProdusComandat
- Parcurgand acest vector calculeaza Totalurile, rotunjindu-le
	Math.round()
- Totalurile cu texe se folosesc de metoda getTaxe():
- Logica: pret_produs = ((pret*taxa)/100 + pret)*cantitate

Clasa MarketFactory:
- Clasa pentru implementarea design pattern-ului FactoryPattern
- Metoda createMarket(tip_magazin) creeaza si returneaza o instanta
	a clasei tip_magazin, derivata din Magazin

Clasa Magazin:
- Clasa ce contine metoda abstracta calculScutiriTaxe():
	fiind specifica pentru fiecare subclasa
- Metodele pentru calculul totalurilor sunt implementate cu ajutorul
	metodelor pentru calculul totalurilor din obiectele Factura
- Metoda toString() Realizeaza o afisare conform cerintei pentru
	magazinul curent, ulterior afisarile magazinelor fiind puse
	una dupa cealalta

Clasele MiniMarket si HyperMarket
- Mostenesc clasa Magazin si implementeaza metoda calculScutiriTaxe()

Clasa MediumMarket
- Mosteneste clasa Magazin si implementeaza metoda calculScutiriTaxe()
- De asemenea implementeaza si o metoda statica totalCategorie()
	de care m-am folosit la Taskul 2

Clasa Gestiune
- Este implementata folosind SingletonPattern
- Are constructorul private
- Metoda getInstance() va fi folosita pentru obtinerea unei referinte
	catre instanta (unica) a clasei
- Metoda replaceInstance() va fi folosita pentru inlocuirea instantei
	curente (la taskul 2 vom avea obtiunea de a reincarca
	fisierele, construind un alt obiect dar pastrand caracteristica
	de SingleTon Pattern)
- Metoda getCategorii() intoarce multimea categoriilor produselor
	existente
- Metoda sortProduse() va sorta lista de produse in functie de 
	parametrul primit:
	- 1 = sortare dupa nume
	- 2 = sortare dupa tari
- Metoda citireProduse() va citi produsul aflat la adresa absoluta
	salvata in variabila produse_file
	Functia scaneaza linie cu linie, iar fiecare linie scanata va
	fi si ea la randul ei scanata pentru a obtine informatiile
	din fisier
- Metoda parsingTaxe() va citi fisierul aflat la adresa absoluta
	salvata in taxe_file
	Metoda realizeaza citirea pe acelasi principiu ca metoda
	citireProduse()
	Doar ca aceasta construieste 3 liste pe care la sfarsit
	le va introduce in dictionarul taxe
- Metoda tari_in_taxe() va citi prima linie a fisierului taxe_file
	si va intoarce o lista cu numele tarile existente.
- Metoda parsingFacturi() va citi fisierul aflat la adresa absoluta
	facturi_file.
	Scaneaza linie cu linie si in functie de continutul liniei
	decide daca urmatoarele linii descriu o factura noua sau un
	magazin nou sau pur si simplu o linie goala. Apoi urmeaza
	prelucrarea inputului si crearea Listei de magazine
- Metoda getMaxFactura() intoarce factura ce are totalul fara taxe
	cel mai mare.

	Metodele pentru acest task sunt apelate din GUI unde se afla
si functia main().


	##########
	# Task 2 #
 	##########


Clasa Main
- Este clasa "de comanda". Aplicatia functioneaza pe baza unui singur
	JFrame si a mai multor JPanel-uri si JDialog-s.
- Am folosit CardLayout(): Astfel, in constructor am instantiat toate
	JPanel-urile pe care le voi folosi, le adaug la Panoul
	principal (contentPane) si trimit ca parametru constructorilor
	panel-ul principal si layout-ul pentru a putea naviga din orice
	punct printre Panel-uri.

Clasa Login
- Este primul panel care apare la rularea aplicatiei. Aceasta are doua
	campuri (pentru utilizator si parola) si doua butoane.
- Butonul inregistrare ne trimite la MakeAccount (panel), unde
	se va putea realiza inregistrarea.
- Butonul autentificare ne trimite la Principala (panel), aceasta
	fiind pagina principala a aplicatiei.
- Autentificarea se face prin citirea fisierului accounts.txt si
	verificarea daca perechea (user, password) se gaseste in file.

Clasa MakeAccount
- Este un panel in care sunt prezente 3 campuri:
	1. Utilizator
	2. Parola
	3. Rescrierea Parolei (pentru corectitudine)
- Odata apasat butonul pentru inregistrare, perechea (user, password)
	va fi scrisa in fisierul accounts.txt (pentru a fi valabila
	si la alte rulari ale aplicatiei)

Clasa Principala
- Este un panel ce reprezinta pagina principala a aplicatiei
- De aici utilizatorul are acces la toate functionalitatile
- Primul lucru care trebuie facut de aici este importarea Fisierelor,
	Fara acest lucru aplicatia nu permite accesul altundeva
- Dupa citirea fisierelor butonul "Importare Date", utilizatorul
	poate trece mai departe la gestiunea produselor sau la
	statistici.

Clasa LoadFiles
- Este un panel cu ajutorul caruia se importeaza datele in aplicatie
- Butonul de gestionare (crearea obiectului Gestiune) este blocat
	deoarece acest lucru nu ar fi posibil fara citirea fisierelor
- Fiecare din cele 3 butoane disponibile initial, deschide un
	jFileChoser cu ajutorul caruia putem naviga prin ierarhia
	de fisiere si selecta un fisier .txt
- Dupa ce toate fisierele au fost incarcate, butonul Gestiune devine
	disponibil si astfel aplicatia va avea un obiect de tip
	Gestiune pe baza caruia va rula mai departe
- Butonul de Back ne intoarce in pagina principala fara a altera
	obiectul Gestiune (existent sau nu)
- De asemenea Panelul poate fi reaccesat in vederea importarii altor
	fisiere in aceeasi sesiune de rulare

Clasa ProductManage
- Ofera posibilitatea de : - a crea un nou produs
			   - a edita un produs
			   - a sterge un produs
			   - a afisa produsele
			   - a cauta un produs

Am considerat ca exista un singur produs cu acelasi nume.
(Adica nu exista produse care sa aiba acelasi nume si sa fie in
categorii diferite)

Crearea produselor functioneaza pe un numar variabil de tari.
Se cere in panel-ul AdaugareProdus introducerea numelui si categoriei,
cu mentiunea ca daca categoria scrisa nu exista apare un mesaj in care
sune enumerate toate categoriile existente. Apoi este lansata o
fereastra de dialog ce se auto-apeleaza in vederea introducerii
pretului aferent fiecarei tari din care poate veni produsul.

Editarea, Stergerea si Cautarea de produse trimit o notificare
in cazul in care produsul cautat este inexistent.

Clasa Statistici afiseaza intr-un JtextPane needitabil toate
informatiile cerute, prin apelarea unor metode din clasele definite
pentru Taskul 1.


Observatii tema:

Timpul alocat efectuarii temei a fost de aproximativ 36 de ore.
Tema a fost realizata in Eclipse Oxygen
