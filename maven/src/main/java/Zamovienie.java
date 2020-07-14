public class Zamovienie extends Produkt {
    double cena;
    boolean aktywne;
    int id_firmy;
Zamovienie(){
    super();
}
    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public int getId_firmy() {
        return id_firmy;
    }

    public void setId_firmy(int id_firmy) {
        this.id_firmy = id_firmy;
    }

    public boolean isAktywne() {

        return aktywne;
    }
    public Firma PodajFirmu(){
        DbHelper dbHelper = new DbHelper();
        return  dbHelper.PodajFirmuID(id_firmy);
    }
    public boolean CzyMozliwoZamkniochZamowienie(){
    DbHelper dbHelper =new DbHelper();
    Produkt produkt = dbHelper.PodajProduktPoKodu(getNumer());
    if (produkt.getIlosc()>=getIlosc())
        return true; else return false;
    }

    public void setAktywne(boolean aktywne) {
        this.aktywne = aktywne;
    }

    public Zamovienie(int numer, int ilosc, double cena,int id_firmy) {
        super(numer, ilosc);
        this.cena = cena;
        aktywne = true;
        this.id_firmy = id_firmy;
    }
    public Zamovienie(int numer, int ilosc, double cena,boolean aktywne,int id_firmy) {
        super(numer, ilosc);
        this.cena = cena;
        this.aktywne = aktywne;
        this.id_firmy = id_firmy;
    }

    public Zamovienie(int id, int numer, int ilosc, double cena,int id_firmy) {
        super(id, numer, ilosc);
        this.cena = cena;
        aktywne = true;
        this.id_firmy = id_firmy;
    }

    public void ZamkniZamowienie(User us) {
    DbHelper dbHelper = new DbHelper();
    dbHelper.ZamkniZamowieniePoId(us,this);
    }

    public void UsunZamowienie() {
    DbHelper dbHelper = new DbHelper();
    dbHelper.UsunZamowienie(getId());
    }

    public void ZapiszNowyParametryDB() {
    DbHelper dbHelper = new DbHelper();
    dbHelper.ZapiszNowyParametryPoId(this);
    
    }
}
