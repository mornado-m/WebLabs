import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.JOptionPane;

public class Word {
    int x, y, offsetX, offsetY;
    Rectangle Border;
    public Label Text;
    Random rand;
    static boolean caught = false;

    public Word(String Name, int StartX, int StartY, Rectangle Border){
        x = StartX;
        y = StartY;
        Text = new Label(Name);
        Text.setFont(new Font("TimesRoman",Font.BOLD,25));
        Text.setLocation(x, y);

        this.Border = Border;
        rand = new Random();
        offsetX = rand.nextInt(21) - 10;
        offsetY = rand.nextInt(21) - 10;

        Text.addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!caught)
                if (!Text.getText().equals("Catch me!")){
                    synchronized (this){
                        caught = true;
                    }
                    JOptionPane.showMessageDialog(null, "You lose!");
                }
                else{
                    synchronized (this){
                        caught = true;
                    }
                    JOptionPane.showMessageDialog(null, "You win!");
                }
            }
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

    public void Move(){
        try{
            if (rand.nextInt(10) - 1 == -1){
                offsetX = rand.nextInt(21) - 10;
                offsetY = rand.nextInt(21) - 10;
            }
            if (x >= Border.width - Text.getText().length() * Text.getFont().getSize()) offsetX = -7;
            if (x <= Border.x + 10) offsetX = +7;
            if (y >= Border.height - 25) offsetY = -7;
            if (y <= Border.y + 10) offsetY = +7;

            x += offsetX;
            y += offsetY;
            Text.setBounds(x, y,
                    Text.getText().length() * Text.getFont().getSize() / 2 + 10,
                    Text.getFont().getSize());
            Thread.sleep(100);
        }
        catch (InterruptedException ee){

        }
    }

    public static void setCaught(boolean c){
        caught = c;
    }
}
