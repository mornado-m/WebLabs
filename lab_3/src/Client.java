import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

public class Client extends JFrame {
    private JPanel LoginPanel;
    private JTextField Login;
    private JButton LoginButton;

    private JPanel OutputPanel;
    private JPanel InputPanel;
    private JTextArea OutputField;
    private JTextField InputField;
    private volatile boolean needSend = false;
    private String message;

    class Session extends Thread {
        Socket socket;
        String log;

        public Session(Socket s, String l) {
            socket = s;
            log = l;
        }

        private class Receiver extends Thread {
            private BufferedReader in;

            public Receiver(BufferedReader in) {
                this.in = in;
            }

            @Override
            public void run() {
                try {
                    while (true) {
                        String str = in.readLine();

                        OutputField.append("\n");
                        OutputField.append(str);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(),
                        Charset.forName("UTF-8")));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                out.println(log);

                new Receiver(in).start();

                while (true) {
                    if (needSend) {
                        out.println(message);
                        needSend = false;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Client();
            }
        });
    }

    public Client() {
        this.setSize(500, 700);
        this.setMinimumSize(new Dimension(500, 700));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        InitLogin();

        this.pack();
        this.setVisible(true);
    }

    void InitComponents() {
        getContentPane().setLayout(new BorderLayout());

        OutputPanel = new JPanel();
        add(OutputPanel, BorderLayout.CENTER);
        InputPanel = new JPanel();
        InputPanel.setBackground(Color.WHITE);
        add(InputPanel, BorderLayout.SOUTH);

        OutputPanel.setLayout(new BoxLayout(OutputPanel, BoxLayout.Y_AXIS));
        InputPanel.setLayout(new BoxLayout(InputPanel, BoxLayout.Y_AXIS));

        OutputField = new JTextArea();
        OutputField.setEnabled(false);
        OutputField.setDisabledTextColor(Color.black);
        OutputField.setAlignmentX(CENTER_ALIGNMENT);
        OutputPanel.add(OutputField);

        JLabel mess = new JLabel("Message:");
        mess.setAlignmentX(TOP_ALIGNMENT);
        InputPanel.add(mess);
        InputField = new JTextField();
        InputField.setAlignmentX(CENTER_ALIGNMENT);
        InputPanel.add(InputField);
        InputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    while (true) {
                        if (!needSend) {
                            message = InputField.getText();
                            InputField.setText("");
                            needSend = true;
                            break;
                        }
                    }
                }
            }
        });
    }

    void InitLogin() {
        getContentPane().setLayout(new BorderLayout());

        LoginPanel = new JPanel();
        add(LoginPanel, BorderLayout.SOUTH);

        LoginPanel.setLayout(new BoxLayout(LoginPanel, BoxLayout.Y_AXIS));

        JLabel log = new JLabel("Login");
        log.setAlignmentX(CENTER_ALIGNMENT);
        LoginPanel.add(log);

        Login = new JTextField();
        Login.setAlignmentX(CENTER_ALIGNMENT);
        LoginPanel.add(Login);

        LoginButton = new JButton("Start");
        LoginButton.setAlignmentX(CENTER_ALIGNMENT);
        LoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String log = Login.getText();
                if (log != "") {
                    LoginPanel.removeAll();
                    InitComponents();
                    InitSession(log);
                }
            }
        });

        LoginPanel.add(LoginButton);
    }

    void InitSession(String log) {
        try {
            Socket clientSocket = new Socket("localhost", 3345);
            Session ses = new Session(clientSocket, log);
            ses.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
