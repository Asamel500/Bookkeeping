import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Produkcja extends JFrame {
    User user;
    JPanel menu,content;
    Produkcja(User us){
        user = us;
        setVisible(true);
        setBounds(300,200,700,500);
        menu = new ProdukcjaMenu();
        content = new ProdukcjaPanelHome();
        menu.setLayout(new GridLayout(9,0));
        content.setLayout(new FlowLayout());
        menu.setBounds(0,0,60,50);
        Border etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "Menu");
        menu.setBorder(titled);
        add(menu,BorderLayout.LINE_START);
        add(content,BorderLayout.CENTER);
    }
    class ProdukcjaMenu extends JPanel{
        ProdukcjaMenu() {
            setLayout(new GridLayout(8,0));
            JButton home = new JButton("Home");
            JButton ZapiszProdukt = new JButton("Zapisz Produkt");
            JButton DodajKod = new JButton("Dodaj nowy kod");
            JButton Sklad = new JButton("Sklad");
            JButton Zamowienia = new JButton("Zamowienia");
            add(home);
            home.addActionListener(new homeListner());
            add(ZapiszProdukt);
            ZapiszProdukt.addActionListener(new ZapiszProduktListner());
            add(DodajKod);
            DodajKod.addActionListener(new DodajKodListner());
            add(Sklad);
            Sklad.addActionListener(new SkladListner());
            add(Zamowienia);
            Zamowienia.addActionListener(new ZamowienieListner());
        }
        class SkladListner implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().remove(content);
                content = new Ksiengowosc.KsiengowoscTabelSklad();
                getContentPane().add(content,BorderLayout.CENTER);
                getContentPane().validate();
            }
        }
        class ZamowienieListner implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().remove(content);
                content = new ProdukcjaPanelZamowienie();
                getContentPane().add(content,BorderLayout.CENTER);
                getContentPane().validate();
            }
        }
        class homeListner implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().remove(content);
                content = new ProdukcjaPanelHome();
                getContentPane().add(content,BorderLayout.CENTER);
                getContentPane().validate();
            }
        }
        class ZapiszProduktListner implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().remove(content);
                content = new ProdukcjaPanelDodajProdukt();
                getContentPane().add(content,BorderLayout.CENTER);
                getContentPane().validate();
            }
        }
        class DodajKodListner implements ActionListener{
            JTextField NowyKod;
            JFrame frame;
            @Override
            public void actionPerformed(ActionEvent e) {
                frame =new JFrame();
                frame.setVisible(true);
                frame. setBounds(500,300,300,200);
                frame.setLayout(new GridLayout(3,0));
                frame.add(new JLabel("Podaj nowy kod"));
                frame.add(NowyKod = new JTextField());
                JButton Ok = new JButton("Ok");
                Ok.addActionListener(new OkListner());
                frame.add(Ok);
            }
        class OkListner implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                DbHelper dbHelper = new DbHelper();
                dbHelper.DodajNowyKodProdukt(NowyKod.getText());
                frame.dispose();
            }
        }
        }
    }
    class ProdukcjaPanelDodajProdukt extends JPanel{
        JTextField IloscProdukcji;
        JComboBox KodProdukci;
        JButton DodajProdukt;
        ProdukcjaPanelDodajProdukt(){
            super();
            DbHelper dbHelper =new DbHelper();

            setLayout(new FlowLayout());
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(9,0));
            panel.add(new JLabel("Podaj kod produkcji"));
            panel.add(KodProdukci = new JComboBox(dbHelper.PodajKodList().toArray()));
            panel.add(new JLabel("Podaj ilosc wyrobjonej produkcji"));
            panel.add(IloscProdukcji = new JTextField());
            panel.add(DodajProdukt = new JButton("Ok"));
            DodajProdukt.addActionListener(new DodajProduktListner());
            add(panel);
        }
        class DodajProduktListner implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                DbHelper dbHelper = new DbHelper();
                dbHelper.ZapiszProdukt(user,new Produkt(Integer.parseInt(KodProdukci.getSelectedItem().toString()),Integer.parseInt(IloscProdukcji.getText())));
            }
        }
    }
    class  ProdukcjaPanelHome extends JPanel{
        ProdukcjaPanelHome(){
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
    class  ProdukcjaPanelZamowienie extends JPanel {
        ProdukcjaPanelZamowienie() {
            setLayout(new BorderLayout());
            JPanel SouthPanel = new JPanel();
            SouthPanel.setLayout(new FlowLayout());
            DbHelper dbHelper = new DbHelper();
            ArrayList<Zamovienie> luser = dbHelper.PodajAktywneZamowienie();
            JTable ListaUserow = new JTable(new ProdukcjaTabelModel(luser));
            JScrollPane scrollPane = new JScrollPane(ListaUserow);
            // add(ListaUserow,BorderLayout.CENTER);
            add(scrollPane, BorderLayout.CENTER);
            add(SouthPanel, BorderLayout.SOUTH);
        }
        public class ProdukcjaTabelModel extends AbstractTableModel {
            ArrayList<Zamovienie> lzamowien ;
            ProdukcjaTabelModel(ArrayList<Zamovienie> lzamowien){
                super();
                this.lzamowien=lzamowien;
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