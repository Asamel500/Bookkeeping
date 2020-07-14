import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GlovnyFrame extends JFrame {
    JPanel menu, content;
    GlovnyFrame(User us){
        setBounds(300,200,700,500);
        setVisible(true);
        setLayout(new BorderLayout());
        switch (us.getDzial()){
            case "ksiengowosc":
                break;
            case "sprzedaz":
                break;
            default:
               break;

        }
        menu.setLayout(new GridLayout(9,0));
        content.setLayout(new FlowLayout());
        menu.setBounds(0,0,60,50);
        Border etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "Menu");
        menu.setBorder(titled);

        add(menu,BorderLayout.LINE_START);
        add(content,BorderLayout.CENTER);
    }
}
