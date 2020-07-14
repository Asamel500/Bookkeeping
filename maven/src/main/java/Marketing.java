import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Marketing extends JFrame {
    User user;
    JPanel menu, content;
    Marketing(User us) {
        user = us;
        setBounds(300, 200, 700, 500);
        menu = new MarketingMenu();
        content = new MarketingPanelHome();
        menu.setLayout(new GridLayout(9, 0));
        content.setLayout(new FlowLayout());
        menu.setBounds(0, 0, 60, 50);
        Border etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "Menu");
        menu.setBorder(titled);
        add(menu, BorderLayout.LINE_START);
        add(content, BorderLayout.CENTER);
        setVisible(true);
    }
    class MarketingMenu extends JPanel {
        MarketingMenu() {
            setLayout(new GridLayout(9, 0));
            JButton home = new JButton("Home");
            JButton Sklad = new JButton("Sklad");
            JButton DodajZamowienie = new JButton("Dodaj Zamowienie");
            JButton DodajFirmu = new JButton("Dodaj firnu");
            JButton Zamowienia = new JButton("Zamowienia");
            DodajZamowienie.addActionListener(new DodajZamowienieListner());
            DodajFirmu.addActionListener(new DodajFirmuListner());
            Zamowienia.addActionListener(new ZamowienieListner());
            home.addActionListener(new homeListner());
            Sklad.addActionListener(new SkladListner());
            add(home);
            add(DodajZamowienie);
            add(DodajFirmu);
            add(Sklad);
            add(Zamowienia);
        }
        class SkladListner implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().remove(content);
                content = new MarketingTabelSklad();
                getContentPane().add(content, BorderLayout.CENTER);
                getContentPane().validate();
            }
        }
        class homeListner implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().remove(content);
                content = new MarketingPanelHome();
                getContentPane().add(content, BorderLayout.CENTER);
                getContentPane().validate();
            }
        }

        class ZamowienieListner implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().remove(content);
                content = new MarketingPanelZamowienie();
                getContentPane().add(content, BorderLayout.CENTER);
                getContentPane().validate();
            }
        }

        class DodajFirmuListner implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().remove(content);
                content = new MarketingPanelDodajFirmu();
                getContentPane().add(content, BorderLayout.CENTER);
                getContentPane().validate();
            }
        }

        class DodajZamowienieListner implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().remove(content);
                content = new MarketingPanelDodajZamowiene();
                getContentPane().add(content, BorderLayout.CENTER);
                getContentPane().validate();
            }
        }
    }
    class MarketingPanelDodajZamowiene extends JPanel {
        JComboBox firma;
        JPanel panel;
        int iloscZamProdukcji;
        JComboBox[] kodProdukcji;
        JTextField[] iloscProdukcji, CenaProdukcji;
        JButton DodajNowyProdukt, Zapisz;

        MarketingPanelDodajZamowiene() {
            setLayout(new FlowLayout());
            kodProdukcji = new JComboBox[9];
            iloscProdukcji = new JTextField[9];
            CenaProdukcji = new JTextField[9];
            iloscZamProdukcji = 0;
            DbHelper dbHelper = new DbHelper();
            panel = new JPanel();
            panel.setLayout(new GridLayout(14, 0));
            panel.add(new JLabel("Podaj firmu"));
            panel.add(firma = new JComboBox(dbHelper.PodajNazwyFirm()));
            panel.add(DodajNowyProdukt = new JButton("Dodaj produkt"));
            panel.add(Zapisz = new JButton("Zapisz"));
            panel.add(new JLabel("Podaj kod ilosc i cenu zamovienia"));
            Zapisz.addActionListener(new ZapiszListner());
            DodajNowyProdukt.addActionListener(new DodajNowyProduktListner());
            add(panel);
            DodajNowyProdukt.doClick();
        }

        class ZapiszListner implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < iloscZamProdukcji; i++) {
                    DbHelper dbHelper = new DbHelper();
                    ArrayList<Firma> lfirm = dbHelper.PodajListuFirm();
                    if ((!(iloscProdukcji[i].getText().equals("")) && !(CenaProdukcji[i].getText().equals("")))) {
                        dbHelper.ZapiszZamovienie(new Zamovienie(Integer.parseInt(kodProdukcji[i].getSelectedItem().toString()),
                                        Integer.parseInt(iloscProdukcji[i].getText()),
                                        Double.parseDouble(CenaProdukcji[i].getText()), lfirm.get(firma.getSelectedIndex()).getId_firmy()),
                                user);
                    }
                }
            }
        }

        class DodajNowyProduktListner implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((iloscZamProdukcji == 0) || ((iloscZamProdukcji < 9))) {
                    DbHelper dbHelper = new DbHelper();
                    JPanel panelPr = new JPanel();
                    panelPr.setLayout(new GridLayout(0, 3));
                    kodProdukcji[iloscZamProdukcji] = new JComboBox(dbHelper.PodajKodList().toArray());
                    iloscProdukcji[iloscZamProdukcji] = new JTextField();
                    CenaProdukcji[iloscZamProdukcji] = new JTextField();
                    panelPr.add(kodProdukcji[iloscZamProdukcji]);
                    panelPr.add(iloscProdukcji[iloscZamProdukcji]);
                    panelPr.add(CenaProdukcji[iloscZamProdukcji]);
                    iloscZamProdukcji++;
                    panel.add(panelPr);
                    getContentPane().validate();
                }
            }
        }
    }
    class MarketingPanelDodajFirmu extends JPanel {
        JTextField nazwaFirmy, adresFirmy, telefonFirmy;

        MarketingPanelDodajFirmu() {
            setLayout(new FlowLayout());
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(7, 0));
            panel.add(new JLabel("Podaj nazwu firmy"));
            panel.add(nazwaFirmy = new JTextField());
            panel.add(new JLabel("Podaj adres firmy"));
            panel.add(adresFirmy = new JTextField());
            panel.add(new JLabel("Podaj telefon firmy"));
            panel.add(telefonFirmy = new JTextField());
            JButton ok = new JButton("ok");
            ok.addActionListener(new okListner());
            panel.add(ok);
            add(panel);
        }

        class okListner implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                DbHelper dbHelper = new DbHelper();
                dbHelper.ZapiszFirmu(new Firma(nazwaFirmy.getText(), adresFirmy.getText(), telefonFirmy.getText()));
            }
        }
    }
    class MarketingPanelHome extends JPanel {
        MarketingPanelHome() {
            setLayout(new FlowLayout());
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(9, 0));
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
    class MarketingPanelZamowienie extends JPanel {
        JTable ListaUserow;
        ArrayList<Zamovienie> lzamowien;
        MarketingPanelZamowienie() {
            setLayout(new BorderLayout());
            JPanel SouthPanel = new JPanel();
            SouthPanel.setLayout(new FlowLayout());
            DbHelper dbHelper = new DbHelper();
            lzamowien = dbHelper.PodajAktywneZamowienie();
            ListaUserow = new JTable(new ZamowienieTabelModel(lzamowien));
            JScrollPane scrollPane = new JScrollPane(ListaUserow);
            ListaUserow.addMouseListener( new MarketingTabelMouseListner());
            add(scrollPane, BorderLayout.CENTER);
        }


        public class MarketingTabelPanel extends JPanel {
            JTextField Kod,Ilosc,Cena;
            MarketingTabelPanel(final Zamovienie zamovienie) {
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
                JButton UsunZamowienie = new JButton("Usun zamowienie");
                JButton ZamkniZamowienie = new JButton("Zamkni zamowienie");
                JButton  ZmienichParametr = new JButton("Zmien parametry");
                ZmienichParametr.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(Marketing.this,"Parametry są rejestrowane");
                        zamovienie.setNumer(Integer.parseInt(Kod.getText()));
                        zamovienie.setIlosc(Integer.parseInt(Ilosc.getText()));
                        zamovienie.setCena(Double.parseDouble(Cena.getText()));
                        zamovienie.ZapiszNowyParametryDB();
                    }
                });
                UsunZamowienie.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(Marketing.this,"Zamowienie usuniente");
                        zamovienie.UsunZamowienie();
                        getContentPane().remove(content);
                        content = new MarketingPanelDodajZamowiene();
                        getContentPane().add(content, BorderLayout.CENTER);
                        getContentPane().validate();
                    }
                });
                end.add(UsunZamowienie);
                end.add(ZamkniZamowienie);
                end.add(ZmienichParametr);
                components.add(new JLabel("Firma"+zamovienie.PodajFirmu().getNazwa()));
                components.add(new JLabel("Kod zamowienia"));
                components.add(Kod = new JTextField(String.valueOf(zamovienie.getNumer())));
                components.add(new JLabel("Ilosc"));
                components.add(Ilosc = new JTextField(String.valueOf(zamovienie.getIlosc())));
                components.add(new JLabel("Cena"));
                components.add(Cena = new JTextField(String.valueOf(zamovienie.getCena())));
                Kod.addKeyListener(new NoLetterListner());
                Ilosc.addKeyListener(new NoLetterListner());
                Cena.addKeyListener(new NoLetterListner());
                if (zamovienie.CzyMozliwoZamkniochZamowienie()){
                    components.add(new JLabel("Zamówienie można zamknąć"));
                    ZamkniZamowienie.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(Marketing.this,"Zamówienie zostało zamknięte");
                            zamovienie.ZamkniZamowienie(user);
                            getContentPane().remove(content);
                            content = new MarketingPanelDodajZamowiene();
                            getContentPane().add(content, BorderLayout.CENTER);
                            getContentPane().validate();
                        }
                    });
                }else {
                    components.add(new JLabel("Zamówienie nie można zamknąć"));
                    ZamkniZamowienie.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(Marketing.this,"Nie można zamknąć zamówienie");
                        }
                    });
                }
                add(center,BorderLayout.CENTER);
                add(end,BorderLayout.LINE_END);

            }
        }

        public class ZamowienieTabelModel extends AbstractTableModel {
            ArrayList<Zamovienie> lzamowien;

            ZamowienieTabelModel(ArrayList<Zamovienie> lzamowien) {
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

            private class MarketingTabelMouseListner implements MouseListener {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int row = ListaUserow.rowAtPoint(e.getPoint());
                    int col = ListaUserow.columnAtPoint(e.getPoint());
                    if (row >= 0 && col >= 0) {
                        getContentPane().remove(content);
                        content = new MarketingTabelPanel(lzamowien.get(row));
                        getContentPane().add(content, BorderLayout.CENTER);
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
        }
    class NoLetterListner extends KeyAdapter {
        public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if ( ((c < '0') || (c > '9')) || (c==KeyEvent.VK_SPACE)) {
                e.consume();  // игнорим введенные буквы и пробел
            }

        }
    }
    public class MarketingTabelSklad extends JPanel{
        JTable ListaProduktow;
        ArrayList<Produkt> lprodukt;
        MarketingTabelSklad(){
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
    }
