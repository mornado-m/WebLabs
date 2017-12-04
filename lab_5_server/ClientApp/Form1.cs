using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace ClientApp
{
    public partial class MainForm : Form
    {
        private LoginInit loginInit;

        private string login;

        private TcpClient client;
        private NetworkStream Stream;

        public MainForm()
        {
            InitializeComponent();
            loginInit = new LoginInit();

            client = new TcpClient(Dns.GetHostName(), 3345);
        }

        private void MainForm_Load(object sender, EventArgs e)
        {
            login = loginInit.ShowDialog() == DialogResult.OK ? loginInit.Login : "Client";
            this.Text = login;

            Stream = client.GetStream();
            
            SendMessage(login);
            SendButton.Click += (o, args) =>
            {
                try
                {
                    SendMessage(MessageText.Text);
                    MessageText.Text = "";
                }
                catch
                {
                    Close();
                }
            };
            new Thread(RunRecieving).Start();
        }

        private void RunRecieving()
        {
            try
            {
                while (true)
                {
                    var message = ReceiveMessage();
                    if (!message.Equals(""))
                        ChatTextBox.Invoke((Action) (() =>
                        {
                            ChatTextBox.Text += message + Environment.NewLine;
                        }));
                }
            }
            catch
            {
                CloseConnection();
            }
        }

        private void SendMessage(string message)
        {
            byte[] data = Encoding.Unicode.GetBytes(message);
            Stream.Write(data, 0, data.Length);
        }

        private string ReceiveMessage()
        {
            byte[] data = new byte[256];
            string res = "";
            do
            {
                var bytes = Stream.Read(data, 0, data.Length);
                res += Encoding.Unicode.GetString(data, 0, bytes);
            } while (Stream.DataAvailable);

            return res;
        }

        private void CloseConnection()
        {
            Stream?.Close();
            client?.Close();
            Environment.Exit(0);
        }

        private void MainForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            SendMessage("disconnected");
            CloseConnection();
        }
    }
}
