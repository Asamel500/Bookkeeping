public class Produkt {
    private int id;
    private int numer;
    private int ilosc;

    public Produkt(int numer, int ilosc) {
        this.numer = numer;
        this.ilosc = ilosc;
    }

    public Produkt(int id, int numer, int ilosc) {
        this.id = id;
        this.numer = numer;
        this.ilosc = ilosc;
    }

    public Produkt() {

    }

    public int getId() {

        return id;

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumer() {
        return numer;
    }

    public void setNumer(int numer) {
        this.numer = numer;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

}
