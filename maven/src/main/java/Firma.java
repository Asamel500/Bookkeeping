public class Firma {
    private int id_firmy;
    private String nazwa,adress,telefon;
    public Firma(){

    }
    public Firma(int id_firmy, String nazwa, String adress, String telefon) {
        this.id_firmy = id_firmy;
        this.nazwa = nazwa;
        this.adress = adress;
        this.telefon = telefon;
    }

    public Firma(String nazwa, String adress, String telefon) {
        this.nazwa = nazwa;
        this.adress = adress;
        this.telefon = telefon;
    }

    public int getId_firmy() {
        return id_firmy;
    }

    public void setId_firmy(int id_firmy) {
        this.id_firmy = id_firmy;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}
