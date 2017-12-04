using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace lab_5_server
{
    class Session
    {
        protected internal int Id { get; private set; }
        protected internal NetworkStream Stream { get; private set; }

        private string login;
        private TcpClient client;
        private Server server;

        public Session(Server Server, TcpClient tcpClient, int ID)
        {
            Id = ID;
            client = tcpClient;
            server = Server;
            Server.AddConnection(this);
        }

        public void Run()
        {
            try
            {
                Stream = client.GetStream();

                login = GetMessage();
                if (login.Equals("Client"))
                {
                    login = "Client" + Id;
                }

                string message = login + " connected.";
                server.SendMessage(message);

                while (true)
                {
                    message = GetMessage();

                    if (message.Equals("disconnected"))
                    {
                        server.SendMessage(login + " disconnected.");
                        server.RemoveConnection(this.Id);
                        Close();
                        break;
                    }
                    if (!message.Equals(""))
                        server.SendMessage(login + ": " + message);
                }
            }
            catch (Exception e)
            {
                server.SendMessage(login + " disconnected.");
                Console.WriteLine(e.Message);
            }
        }
        
        private string GetMessage()
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

        protected internal void Close()
        {
            Stream?.Close();
            client?.Close();
        }
    }
}
