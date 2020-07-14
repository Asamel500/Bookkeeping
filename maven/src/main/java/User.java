public class User {
    int id ;
    String SureName, username,password,dzial,telefon;
    double payment;
    User(){

    };

    public void setId(int id) {
        this.id = id;
    }

    public void setSureName(String sureName) {
        SureName = sureName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public double getPayment() {
        return payment;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public void setDzial(String dzial) {
        this.dzial = dzial;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getUsername() {

        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDzial() {
        return dzial;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getSureName() {

        return SureName;
    }

    public int getId() {

        return id;
    }

    public User(String sureName, String username, String password, String dzial, String telefon,double payment) {
        SureName = sureName;
        this.username = username;
        this.password = password;
        this.dzial = dzial;
        this.telefon = telefon;
        this.payment = payment;
    }

    User(int xid, String xusername, String xpassword, String xSureName, String xdzial, String xtelefon,double payment){
        id = xid;
        dzial = xdzial;
        telefon = xtelefon;
        username = xusername;
        this.payment=payment;
        password = xpassword;
        SureName = xSureName;
    };

    public void UsunUsera() {
    DbHelper dbHelper = new DbHelper();
    dbHelper.UsunUsera(this.username);
    }

    public void ZmienichParametryDB() {
    DbHelper dbHelper =new DbHelper();
    dbHelper.ZmienParametryUseraPoId(this);
    }
}
