import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;

public class Ksiengowosc extends JFrame{
    JPanel menu, content;
    User user;
 Ksiengowosc(User us){
     super();
     user = us;
     setVisible(true);
     setBounds(300,200,700,500);
     menu = new KsiengowoscMenu();
     content = new KsiengowoscPanelHome();
     menu.setLayout(new GridLayout(9,0));
     content.setLayout(new FlowLayout());
     menu.setBounds(0,0,60,50);
     Border etched = BorderFactory.createEtchedBorder();
     Border titled = BorderFactory.createTitledBorder(etched, "Menu");
     menu.setBorder(titled);
     add(menu,BorderLayout.LINE_START);
     add(content,BorderLayout.CENTER);
 }
class  KsiengowoscPanelHome extends JPanel{
    KsiengowoscPanelHome(){
        setLayout(new FlowLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9,0));
        panel.add(new JLabel("Username"));
        JLabel username = new JLabel(user.getUsername());
        panel.add(username);
        panel.add(new JLabel("Pracownik"));
        JLabel name = new JLabel(user.getSureName());
        panel.add(name);
        panel.add(new JLabel("Dzial"));
        JLabel dzial = new JLabel(user.getDzial());
        panel.add(dzial);
        panel.add(new JLabel("Telefon"));
        JLabel telefon = new JLabel(user.getTelefon());
        panel.add(telefon);
        add(panel);
    }
}
class KsiengowoscMenu extends JPanel{
    KsiengowoscMenu() {
        setLayout(new GridLayout(9,0));
        JButton home = new JButton("Home");
        JButton ListaPrac = new JButton("Lista pracownikow");
        JButton DodajPrac = new JButton("Dodaj pracownika");
        JButton Sklad = new JButton("Sklad");
        JButton Zamowienia = new JButton("Zamowienia");
        add(home);
        home.addActionListener( new homeListner());
        add(ListaPrac);
        ListaPrac.addActionListener(new ListaPracListner());
        add(DodajPrac);
        DodajPrac.addActionListener(new DodajPracListner());
        add(Sklad);
        Sklad.addActionListener(new SkladListner());
        add(Zamowienia);
        Zamowienia.addActionListener(new ZamowoenieListner());
    }
    class SkladListner implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            getContentPane().remove(content);
            content = new KsiengowoscTabelSklad();
            getContentPane().add(content,BorderLayout.CENTER);
            getContentPane().validate();
        }
    }
    class ZamowoenieListner implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            getContentPane().remove(content);
            content = new KsiengowoscPanelZamowienie();
            getContentPane().add(content,BorderLayout.CENTER);
            getContentPane().validate();
        }
    }
    class homeListner implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            getContentPane().remove(content);
            content = new KsiengowoscPanelHome();
            getContentPane().add(content,BorderLayout.CENTER);
            getContentPane().validate();
        }
    }
    class ListaPracListner implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            getContentPane().remove(content);
            content = new KsiengowoscPanelPodajListuPracownikow();
            getContentPane().add(content,BorderLayout.CENTER);
            getContentPane().validate();
        }
    }
    class DodajPracListner implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            getContentPane().remove(content);
            content = new KsiengowoscPanelDodajPrac();
            getContentPane().add(content,BorderLayout.CENTER);
            getContentPane().validate();
            //   content.validate();
            //  content.repaint();
        }
    }
}
private class KsiengowoscPanelDodajPrac extends JPanel {
     JTextField Payment,NameSure,Username,Password,Telefon;
        JComboBox Dzial;
        JButton DPDodajPrac;
        String[] spiner = {"ksiengowosc","produkcja","marketing"};
     KsiengowoscPanelDodajPrac(){
         setLayout(new FlowLayout());
         JPanel panel = new JPanel();
         panel.setLayout(new GridLayout(9,0));
         panel.add(new JLabel("Podaj imie i nazwisko"));
         panel.add(NameSure = new JTextField());
         panel.add(new JLabel("Username"));
         panel.add(Username = new JTextField());
         panel.add(new JLabel("Podaj haslo"));
         panel.add(Password = new JTextField());
         panel.add(new JLabel("Dzial"));
         panel.add(Dzial = new JComboBox(spiner));
         panel.add(new JLabel("Podaj wynagrodzenie"));
         panel.add(Payment = new JTextField());
         panel.add(new JLabel("Podaj telefon"));
         panel.add(Telefon = new JTextField());
         Payment.addKeyListener(new NoLetterListner());
         Telefon.addKeyListener(new NoLetterListner());
         panel.add(DPDodajPrac = new JButton("Ok"));
         DPDodajPrac.addActionListener(new DPDodajPracListner());
         add(panel);
     }
        class NoLetterListner extends KeyAdapter {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ( ((c < '0') || (c > '9')) || (c==KeyEvent.VK_SPACE)) {
                    e.consume();  // игнорим введенные буквы и пробел
                }

            }
        }
        class DPDodajPracListner implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                User us = new User(NameSure.getText(),Username.getText(),Password.getText(),Dzial.getSelectedItem().toString(),Telefon.getText(),Double.parseDouble(Payment.getText()));
                DbHelper db = new DbHelper();
                db.ZapiszUsera(us);
            }
        }
    }
class KsiengowoscPanelPodajListuPracownikow extends JPanel {
     JTable ListaUserow;
    KsiengowoscPanelPodajListuPracownikow(){
        super();
        setLayout(new BorderLayout());
        DbHelper dbHelper = new DbHelper();
        ArrayList<User> luser = dbHelper.PodajListuUserow();
        ListaUserow = new JTable(new ListaPracTabelModel(luser));
        JScrollPane scrollPane = new JScrollPane(ListaUserow);
        ListaUserow.addMouseListener(new KsiengowoscTabelMouseListner(luser));
        add(scrollPane,BorderLayout.CENTER);
    }
    public class KsiengowoscTabelPanel extends JPanel{
        JTextField payment,Username,SureName,telefon;
        JComboBox dzial;
        User luser;
        KsiengowoscTabelPanel( User luser){
            String[] Dataspiner = {"ksiengowosc","produkcja","marketing"};
            this.luser=luser;
            setLayout(new BorderLayout());
            JPanel center = new JPanel();
            center.setLayout(new FlowLayout());
            JPanel components = new JPanel();
            center.add(components);
            components.setLayout(new GridLayout(9,0));
            JPanel end = new JPanel();
            Border etched = BorderFactory.createEtchedBorder();
            Border titled = BorderFactory.createTitledBorder(etched, "Menu");
            end.setBorder(titled);
            end.setLayout(new GridLayout(9,0));
            JButton UsunUsera = new JButton("Usun usera");
            UsunUsera.addActionListener(new UsunUseraListner());
            JButton ZobachHistorie = new JButton("Zobacz historię");
            ZobachHistorie.addActionListener(new ZobachHistorjeListner());
            JButton  ZmienichParametr = new JButton("Zmien parametry");
            ZobachHistorie.addActionListener(new ZobachHistorjeListner());
            ZmienichParametr.addActionListener(new ZmienichParametrListner());
            end.add(UsunUsera);
            if (!luser.getDzial().equals("ksiengowosc")) end.add(ZobachHistorie);
            end.add(ZmienichParametr);
            components.add(new JLabel("Imie nazwisko"));
            components.add(SureName = new JTextField(luser.getSureName()));
            components.add(new JLabel("Username"));
            components.add(Username = new JTextField(luser.getUsername()));
            components.add(new JLabel("Telefon"));
            components.add(telefon = new JTextField(luser.getTelefon()));
            components.add(new JLabel("Dzial"));
            components.add(dzial = new JComboBox(Dataspiner));
            dzial.setSelectedItem(luser.dzial);
            components.add(new JLabel("Wynagrodzenie"));
            components.add(payment = new JTextField(String.valueOf(luser.getPayment())));
            add(center,BorderLayout.CENTER);
            add(end,BorderLayout.LINE_END);
        }
        class ZmienichParametrListner implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                luser.setPayment(Double.parseDouble(payment.getText()));
                luser.setSureName(SureName.getText());
                luser.setDzial(dzial.getSelectedItem().toString());
                luser.setUsername(Username.getText());
                luser.setTelefon(telefon.getText());
                luser.ZmienichParametryDB();
                if ((luser.getId()==user.getId())&&!luser.getDzial().equals("ksiengowosc")){
                    Ksiengowosc.this.dispose();
                    JFrame frame = new Wejsce();
                }
                JOptionPane.showMessageDialog(Ksiengowosc.this,"Parametry są rejestrowane");
            }
        }
        class ZobachHistorjeUsera extends JPanel{
            ZobachHistorjeUsera(User us){
                setLayout(new BorderLayout());
                JPanel SouthPanel = new JPanel();
                SouthPanel.setLayout(new FlowLayout());
                DbHelper dbHelper = new DbHelper();
                JTable LogUsera = null;
                if (us.getDzial().equals("marketing")) LogUsera = new JTable(new ZobachHistorjaZamowieniaTabelModel(luser)); else LogUsera = new JTable(new ZobachHistorjaProdukcjaTabelModel(luser));
                JScrollPane scrollPane = new JScrollPane(LogUsera);
                add(scrollPane, BorderLayout.CENTER);
            }
            private class ZobachHistorjaZamowieniaTabelModel extends AbstractTableModel{
                User user;
                ArrayList<Zamovienie> lzamowien;
                ZobachHistorjaZamowieniaTabelModel(User user){
                    super();
                    DbHelper dbHelper = new DbHelper();
                    this.user=luser;
                    lzamowien = dbHelper.PodajZamowieniaPoUseru(user);
                }

                @Override
                public int getRowCount() {
                    return lzamowien.size();
                }

                @Override
                public int getColumnCount() {
                    return 5;
                }

                @Override
                public Object getValueAt(int r, int c) {
                    switch (c) {
                        case 0:
                            return lzamowien.get(r).getNumer();
                        case 1:
                            return lzamowien.get(r).getIlosc();
                        case 2:
                            return lzamowien.get(r).getCena();
                        case 3:
                             if(lzamowien.get(r).isAktywne()) return "Aktywne"; else return "Zamkniente";
                        case 4:
                            return lzamowien.get(r).PodajFirmu().getNazwa();
                    }
                    return null;
                }

                @Override
                public String getColumnName(int c) {
                    String result = "";
                    switch (c) {
                        case 0:
                            result = "kod produkci";
                            break;
                        case 1:
                            result = "Ilosc";
                            break;
                        case 2:
                            result = "Cena";
                            break;
                        case 3:
                            result = "Aktywne";
                            break;
                        case 4:
                            result = "Firma";
                            break;
                        default:
                            break;
                    }
                    return result;
                }
            }
            private class ZobachHistorjaProdukcjaTabelModel extends AbstractTableModel{
                User user;
                ArrayList<LProdukt> lProdukts;
                ZobachHistorjaProdukcjaTabelModel(User user){
                    super();
                    DbHelper dbHelper = new DbHelper();
                    this.user=luser;
                    lProdukts = dbHelper.PodajLogProdukcjiPoUseru(user);
                }

                @Override
                public int getRowCount() {
                    return lProdukts.size();
                }

                @Override
                public int getColumnCount() {
                    return 3;
                }

                @Override
                public Object getValueAt(int r, int c) {
                    switch (c) {
                        case 0:
                            return lProdukts.get(r).getNumer();
                        case 1:
                            return lProdukts.get(r).getIlosc();
                        case 2:
                            return new Date (lProdukts.get(r).getDate());
                    }
                    return null;
                }

                @Override
                public String getColumnName(int c) {
                    String result = "";
                    switch (c) {
                        case 0:
                            result = "kod produkci";
                            break;
                        case 1:
                            result = "Ilosc";
                            break;
                        case 2:
                            result = "Data";
                        default:
                            break;
                    }
                    return result;
                }
            }
        }
        class ZobachHistorjeListner implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().remove(content);
                content = new ZobachHistorjeUsera(luser);
                getContentPane().add(content,BorderLayout.CENTER);
                getContentPane().validate();
            }
        }
        class UsunUseraListner implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                user.UsunUsera();
                JOptionPane.showMessageDialog(Ksiengowosc.this,"User usunienty");
                if ((luser.getId()==user.getId())){
                    Ksiengowosc.this.dispose();
                    JFrame frame = new Wejsce();
                }
            }
        }
    }
    private class KsiengowoscTabelMouseListner implements MouseListener{
        ArrayList<User> luser;
        KsiengowoscTabelMouseListner(ArrayList<User> luser){
            this.luser = luser;
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            int row = ListaUserow.rowAtPoint(e.getPoint());
            int col = ListaUserow.columnAtPoint(e.getPoint());
            if (row >= 0 && col >= 0) {
                getContentPane().remove(content);
                content = new KsiengowoscTabelPanel(luser.get(row));
                getContentPane().add(content,BorderLayout.CENTER);
                getContentPane().validate();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
    private class ListaPracTabelModel extends AbstractTableModel{
        ArrayList<User> luser ;
        ListaPracTabelModel(ArrayList<User> luser){
            super();
            this.luser=luser;
        }

        @Override
        public int getRowCount() {
            return luser.size();
        }

        @Override
        public int getColumnCount() {
            return 5;
        }

        @Override
        public Object getValueAt(int r, int c) {
            switch (c) {
                case 0:
                    return luser.get(r).getSureName();
                case 1:
                    return luser.get(r).getTelefon();
                case 2:
                    return luser.get(r).getDzial();
                case 3:
                    return luser.get(r).getPayment();
                case 4:
                    return luser.get(r).getUsername();
            }
            return null;
        }

        @Override
        public String getColumnName(int c) {
            String result = "";
            switch (c) {
                case 0:
                    result = "Imie nazwisko";
                    break;
                case 1:
                    result = "Telefon";
                    break;
                case 2:
                    result = "Dzial";
                    break;
                case 3:
                    result = "Wynagroda";
                    break;
                case 4:
                    result = "Username";
                    break;
                default:
                    break;
            }
            return result;
        }
  }
 }
public static class KsiengowoscTabelSklad extends JPanel{
        JTable ListaProduktow;
        ArrayList<Produkt> lprodukt;
        KsiengowoscTabelSklad(){
            setLayout(new BorderLayout());
            JPanel SouthPanel = new JPanel();
            SouthPanel.setLayout(new FlowLayout());
            DbHelper dbHelper = new DbHelper();
            lprodukt = dbHelper.PodajGotowuProdukcju();
            ListaProduktow = new JTable(new SkladTabelModel(lprodukt));
            JScrollPane scrollPane = new JScrollPane(ListaProduktow);
            add(scrollPane, BorderLayout.CENTER);
        }
        public class SkladTabelModel extends AbstractTableModel {
            ArrayList<Produkt> lproudkt;

            SkladTabelModel(ArrayList<Produkt> lprodukt) {
                super();
                this.lproudkt = lprodukt;
            }

            @Override
            public int getRowCount() {
                return lproudkt.size();
            }

            @Override
            public int getColumnCount() {
                return 2;
            }

            @Override
            public Object getValueAt(int r, int c) {
                switch (c) {
                    case 0:
                        return lproudkt.get(r).getNumer();
                    case 1:
                        return lproudkt.get(r).getIlosc();
                }
                return null;
            }

            @Override
            public String getColumnName(int c) {
                String result = "";
                switch (c) {
                    case 0:
                        result = "Kod";
                        break;
                    case 1:
                        result = "Ilosc";
                        break;
                    default:
                        break;
                }
                return result;
            }
        }
    }
class KsiengowoscPanelZamowienie extends JPanel {
        JTable ListaUserow;
        ArrayList<Zamovienie> lzamowien;
        KsiengowoscPanelZamowienie() {
            setLayout(new BorderLayout());
            JPanel SouthPanel = new JPanel();
            SouthPanel.setLayout(new FlowLayout());
            DbHelper dbHelper = new DbHelper();
            lzamowien = dbHelper.PodajAktywneZamowienie();
            ListaUserow = new JTable(new KsiengowoscTabelModel(lzamowien));

            JScrollPane scrollPane = new JScrollPane(ListaUserow);
            add(scrollPane, BorderLayout.CENTER);
            ListaUserow.setEnabled(false);
        }

        public class KsiengowoscTabelModel extends AbstractTableModel {
            ArrayList<Zamovienie> lzamowien;

            KsiengowoscTabelModel(ArrayList<Zamovienie> lzamowien) {
                super();
                this.lzamowien = lzamowien;
            }

            @Override
            public int getRowCount() {
                return lzamowien.size();
            }

            @Override
            public int getColumnCount() {
                return 4;
            }

            @Override
            public Object getValueAt(int r, int c) {
                switch (c) {
                    case 0:
                        return lzamowien.get(r).PodajFirmu().getNazwa();
                    case 1:
                        return lzamowien.get(r).getNumer();
                    case 2:
                        return lzamowien.get(r).getIlosc();
                    case 3:
                        return lzamowien.get(r).getCena();
                }
                return null;
            }

            @Override
            public String getColumnName(int c) {
                String result = "";
                switch (c) {
                    case 0:
                        result = "Firma";
                        break;
                    case 1:
                        result = "Kod";
                        break;
                    case 2:
                        result = "Ilosc";
                        break;
                    case 3:
                        result = "Cena";
                        break;
                    default:
                        break;
                }
                return result;
            }
        }

    }
}

