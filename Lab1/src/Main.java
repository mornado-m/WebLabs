import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Main extends Applet {
    int WordsCount = 10;
    Word[] words;
    Thread[] Threads;
    Random rand;
    Button Start;

    public void init(){
        setBackground(Color.white);
        setForeground(Color.black);

        Start = new Button("Start");
        Start.setFont(new Font("TimesRoman",Font.BOLD,25));
        Start.setLocation(335, 235);
        add(Start);
        Start.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Word.setCaught(false);
            }
        });

        words = new Word[WordsCount];
        Threads = new Thread[WordsCount];
        rand = new Random();

        for (int i = 0; i < WordsCount - 1; ++i){
            words[i] = new Word("word"+(i+1),
                    rand.nextInt(630)+20,
                    rand.nextInt(430)+20,
                    new Rectangle(10,10,680,480));
            add(words[i].Text);
            Threads[i] = new Thread(new myThread(words[i]));
        }
        words[WordsCount - 1] = new Word("Catch me!", 335, 235, new Rectangle(10,10,680,480));
        add(words[WordsCount - 1].Text);
        setLayout(null);

        Threads[WordsCount - 1] = new Thread(new myThread(words[WordsCount - 1]));
        for (int i = 0; i < WordsCount; ++i){
            Threads[i].start();
        }
    }

    public void start(){

    }

    public void stop(){
    }

    public void paint(Graphics g){
        g.drawRect(10,10,680,480);
        /*g.drawLine(words[WordsCount - 1].Text.getX(), words[WordsCount - 1].Text.getY(),
                words[0].Text.getX(), words[0].Text.getY());*/

    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
