import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Wejsce extends JFrame {
    static  JFrame frame;
    JTextField name;
    JTextField password;
    User User;
    Wejsce(){
        super();
        setBounds(500,300,300,200);
        setLayout(new GridLayout(5,0));
        name = new JTextField();
        password = new JTextField();
        JButton accept = new JButton("Ok");
        getContentPane().add(new JLabel("Username"));
        getContentPane().add(name);
        getContentPane().add(new JLabel("Password"));
        getContentPane().add(password);
        getContentPane().add(accept);
        accept.addActionListener(new AceptListner());
        setVisible(true);
    }
    public class AceptListner implements ActionListener{
        public void actionPerformed(ActionEvent e) {
        DbHelper dbHelper = new DbHelper();
        User = dbHelper.PodajUsera(name.getText());
        if (User.getPassword().equals(password.getText())){
           switch (User.getDzial()){
               case "ksiengowosc":
                   frame.dispose();
                   frame = new Ksiengowosc(User);
                   break;
               case "produkcja":
                   frame.dispose();
                   frame = new Produkcja(User);
                   break;
               case "marketing":
                   frame.dispose();
                   frame = new Marketing(User);
                   break;
           }
        }

        }
    }
    public static void main(String[] args) {
     frame = new Wejsce();
       // new DbHelper().PodajKodList();
    }
}
