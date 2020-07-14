import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class DbHelper {
    private Connection conn = null;

    DbHelper() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/dbmeble?useSSL=false", "asamel", "12345678");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User PodajUsera(String name) {
        try {
            String query = ("SELECT * FROM `userlist` WHERE `username` = '" + name + "'");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            User us = new User();
            while (rs.next()) {
                us.setDzial(rs.getString("dzial"));
                us.setTelefon(rs.getString("Telefon"));
                us.setSureName(rs.getString("SureName"));
                us.setPassword(rs.getString("password"));
                us.setUsername(rs.getString("username"));
                us.setId(rs.getInt("id"));
                us.setPayment(rs.getDouble("payment"));
            }
            st.close();
            return us;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String[] PodajNazwyFirm(){
        DbHelper dbHelper = new DbHelper();
        ArrayList<Firma> lfirma = dbHelper.PodajListuFirm();
        String[] ListaFirm  = new String[lfirma.size()];
        for (int i=0;i<lfirma.size();i++){
            ListaFirm[i] = lfirma.get(i).getNazwa();
        }
        return ListaFirm;
    }
    public ArrayList<Firma> PodajListuFirm(){
        try {
            ArrayList<Firma> lfirm = new ArrayList<>();
            String query = ("SELECT * FROM `Lista_Firm`");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Firma fr  = new Firma();
                fr.setId_firmy(rs.getInt("id_firmy"));
                fr.setTelefon(rs.getString("telefon"));
                fr.setAdress(rs.getString("Adres"));
                fr.setNazwa(rs.getString("Nazwa"));
                lfirm.add(fr);
            }
            st.close();
            return lfirm;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Zamovienie> PodajZamowienia(){
        try {
            ArrayList<Zamovienie> lzamowien = new ArrayList<Zamovienie>();
            String query = ("SELECT * FROM `Lista_zamowienia`");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Zamovienie zamovienie = new Zamovienie();
                zamovienie.setCena(rs.getInt("cena"));
                zamovienie.setAktywne(rs.getBoolean("aktywne"));
                zamovienie.setIlosc(rs.getInt("ilosc"));
                zamovienie.setNumer(rs.getInt("kod_produkci"));
                zamovienie.setId_firmy(rs.getInt("id_zam"));
                lzamowien.add(zamovienie);
            }
            st.close();
            return lzamowien;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<User> PodajListuUserow() {
        try {
            ArrayList<User> luser = new ArrayList<User>();
            String query = ("SELECT * FROM `userlist`");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                User us = new User();
                us.setDzial(rs.getString("dzial"));
                us.setTelefon(rs.getString("Telefon"));
                us.setSureName(rs.getString("SureName"));
                us.setPassword(rs.getString("password"));
                us.setUsername(rs.getString("username"));
                us.setId(rs.getInt("id"));
                us.setPayment(rs.getDouble("payment"));
                luser.add(us);
            }
            st.close();
            return luser;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void UsunUsera(String useername){
        try {
            PreparedStatement preparedStmt = conn.prepareStatement("DELETE FROM `userlist` WHERE `username` ='"+useername+"'");
            preparedStmt.execute();
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String> PodajKodList() {
        try {
            String query = ("SELECT * FROM `Lista_kod_produkcji`");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            ArrayList<String> listaKodow = new ArrayList<String>();
            while (rs.next()) {
                listaKodow.add(rs.getString("kod_produkci"));
            }
            st.close();
            return listaKodow;
        } catch (SQLException e ){
            e.printStackTrace();
            return null;
        }
    }

    public void ZapiszUsera(User us) {
        try {
            String query ="INSERT INTO `userlist`(`username`, `password`, `SureName`, `Telefon`, `dzial`, `payment`) VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, us.username);
            preparedStmt.setString (2, us.password);
            preparedStmt.setString (3, us.SureName);
            preparedStmt.setString (4, us.telefon);
            preparedStmt.setString (5, us.dzial);
            preparedStmt.setDouble (6, us.payment);
            preparedStmt.execute();
            preparedStmt.close();
        } catch (SQLException e) {
        e.printStackTrace();
        }
    }
    public  void DodajNowyKodProdukt(String kod){
        try {
            ArrayList list = PodajKodList();
            int CzyJeKod = list.indexOf(kod);
            if (CzyJeKod== -1) {
                String query = "INSERT INTO `Lista_kod_produkcji`( `kod_produkci`) VALUES (?)";
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString(1, kod);
                preparedStmt.execute();
                preparedStmt.close();
                query ="INSERT INTO `Sklad`(`kod_produkci`, `ilosc`) VALUES (?,?)";
                preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString (1, kod);
                preparedStmt.setInt    (2, 0);
                preparedStmt.execute();
                preparedStmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void ZapiszProdukt(User us, Produkt produkt) {
        try {
            String query ="UPDATE `Sklad` set ilosc=ilosc+? WHERE kod_produkci = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, produkt.getIlosc());
            preparedStmt.setInt (2, produkt.getNumer());
            preparedStmt.execute();
            preparedStmt.close();
            query ="INSERT INTO `log_produkcja`(`kod_produkci`, `ilosc`, `Data`, `id_us`) VALUES (?,?,?,?)";
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, produkt.getNumer());
            preparedStmt.setInt (2, produkt.getIlosc());
            preparedStmt.setLong (3,  new Date().getTime());
            preparedStmt.setInt (4, us.getId());
            preparedStmt.execute();
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ZapiszZamovienie(Zamovienie zamovienie,User us) {
        try {
            String query ="INSERT INTO `Lista_zamowienia`(`kod_produkci`, `ilosc`, `cena`, `id_us`, `id_firmy`, `aktywne`) VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, zamovienie.getNumer());
            preparedStmt.setInt (2, zamovienie.getIlosc());
            preparedStmt.setDouble (3, zamovienie.getCena());
            preparedStmt.setInt (4, us.getId());
            preparedStmt.setInt (5, zamovienie.getId_firmy());
            preparedStmt.setBoolean (6, zamovienie.isAktywne());
            preparedStmt.execute();
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ZapiszFirmu(Firma firma) {
        try {
            if (!Arrays.asList(PodajNazwyFirm()).contains(firma.getNazwa())) {
                String query = "INSERT INTO `Lista_Firm`(`Nazwa`, `Adres`, `telefon`) VALUES (?,?,?)";
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString(1, firma.getNazwa());
                preparedStmt.setString(2, firma.getAdress());
                preparedStmt.setString(3, firma.getTelefon());
                preparedStmt.execute();
                preparedStmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Firma PodajFirmuID(int id_firmy) {
        try {
            String query = ("SELECT * FROM `Lista_Firm` WHERE `id_firmy` = "+String.valueOf(id_firmy));
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            Firma firma = new Firma();
            while (rs.next()) {
                firma.setAdress(rs.getString("Adres"));
                firma.setNazwa(rs.getString("Nazwa"));
                firma.setId_firmy(rs.getInt("id_firmy"));
                firma.setTelefon(rs.getString("telefon"));
            }
            st.close();
            return firma;
        } catch (SQLException e ){
            e.printStackTrace();
            return null;
        }
    }
    public Produkt PodajProduktPoKodu(int Kod){
        try {
            Produkt produkt = new Produkt();
            String query = ("SELECT * FROM `Sklad` WHERE kod_produkci = "+ Kod);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {

                produkt.setIlosc(rs.getInt("ilosc"));
                produkt.setNumer(rs.getInt("kod_produkci"));
                produkt.setId(rs.getInt("id_zap"));
            }
            st.close();
            return produkt;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Produkt> PodajGotowuProdukcju(){
        try {
            ArrayList<Produkt> lprodukt = new ArrayList<Produkt>();
            String query = ("SELECT * FROM `Sklad`");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Produkt produkt = new Produkt();
                produkt.setIlosc(rs.getInt("ilosc"));
                produkt.setNumer(rs.getInt("kod_produkci"));
                produkt.setId(rs.getInt("id_zap"));
                lprodukt.add(produkt);
            }
            st.close();
            return lprodukt;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Zamovienie> PodajAktywneZamowienie() {
        try {
            ArrayList<Zamovienie> lzamowien = new ArrayList<Zamovienie>();
            String query = ("SELECT * FROM `Lista_zamowienia` WHERE aktywne = 1");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Zamovienie zamovienie = new Zamovienie();
                zamovienie.setCena(rs.getInt("cena"));
                zamovienie.setAktywne(rs.getBoolean("aktywne"));
                zamovienie.setIlosc(rs.getInt("ilosc"));
                zamovienie.setNumer(rs.getInt("kod_produkci"));
                zamovienie.setId_firmy(rs.getInt("id_firmy"));
                zamovienie.setId(rs.getInt("id_zam"));
                lzamowien.add(zamovienie);
            }
            st.close();
            return lzamowien;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void ZamkniZamowieniePoId(User us , Zamovienie zamovienie) {
        try {
                String query = "UPDATE `Lista_zamowienia` SET `aktywne` = 0 WHERE `id_zam` = ?";
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setInt(1,zamovienie.getId());
                preparedStmt.execute();
            preparedStmt.close();
            query ="UPDATE `Sklad` set ilosc=ilosc-? WHERE kod_produkci = ?";
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, zamovienie.getIlosc());
            preparedStmt.setInt (2, zamovienie.getNumer());
            preparedStmt.execute();
            preparedStmt.close();
            query = "INSERT INTO `Log_zamkniecia_zamowienia`(`id_us`, `id_zam`, `Data`) VALUES (?,?,?)";
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, zamovienie.getId());
            preparedStmt.setInt (2, us.getId());
            preparedStmt.setLong (3, new Date().getTime());
            preparedStmt.execute();
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void UsunZamowienie(int id) {
        try {
        String query = "DELETE FROM `Lista_zamowienia` WHERE id_zam = ?";
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1,id );
        preparedStmt.execute();
        preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ZapiszNowyParametryPoId(Zamovienie zamovienie) {
        try {
        String query ="UPDATE `Lista_zamowienia` SET `kod_produkci`=?,`ilosc`=?,`cena`=? WHERE `id_zam` = ?";
        PreparedStatement  preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt (1, zamovienie.getNumer());
        preparedStmt.setInt (2, zamovienie.getIlosc());
        preparedStmt.setDouble(3,  zamovienie.getCena());
        preparedStmt.setInt(4,zamovienie.getId());
        preparedStmt.execute();
        preparedStmt.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }

    public void ZmienParametryUseraPoId(User us) {
        try {
            String query ="UPDATE `userlist` SET `username`=?,`password`=?,`SureName`=?,`Telefon`=?,`dzial`=?,`payment`=? WHERE `id`=?";
            PreparedStatement  preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, us.getUsername());
            preparedStmt.setString (2, us.getPassword());
            preparedStmt.setString (3, us.getSureName());
            preparedStmt.setString (4, us.getTelefon());
            preparedStmt.setString (5, us.getDzial());
            preparedStmt.setDouble (6, us.getPayment());
            preparedStmt.setInt (7, us.getId());
            preparedStmt.execute();
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Zamovienie> PodajZamowieniaPoUseru(User user) {
        try {
            ArrayList<Zamovienie> lzamowien = new ArrayList<Zamovienie>();
            String query = ("SELECT * FROM `Lista_zamowienia` WHERE id_us = "+ user.getId());
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Zamovienie zamovienie = new Zamovienie();
                zamovienie.setCena(rs.getInt("cena"));
                zamovienie.setAktywne(rs.getBoolean("aktywne"));
                zamovienie.setIlosc(rs.getInt("ilosc"));
                zamovienie.setNumer(rs.getInt("kod_produkci"));
                zamovienie.setId_firmy(rs.getInt("id_firmy"));
                zamovienie.setId(rs.getInt("id_zam"));
                lzamowien.add(zamovienie);
            }
            st.close();
            return lzamowien;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<LProdukt> PodajLogProdukcjiPoUseru(User user) {
        try {
            ArrayList<LProdukt> lprodukt = new ArrayList<LProdukt>();
            String query = ("SELECT * FROM `log_produkcja` WHERE id_us="+user.getId());
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                LProdukt produkt = new LProdukt();
                produkt.setIlosc(rs.getInt("ilosc"));
                produkt.setNumer(rs.getInt("kod_produkci"));
                produkt.setId(rs.getInt("id_zap"));
                produkt.setDate(rs.getLong("Data"));
                lprodukt.add(produkt);
            }
            st.close();
            return lprodukt;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}