using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace lab_5_server
{
    class Server
    {
        private static TcpListener _tcpListener;
        private int _clientIdCount = 0;
        private List<Session> _clients = new List<Session>();

        public List<Session> Clients => _clients;

        protected internal void AddConnection(Session Session)
        {
            _clients.Add(Session);
        }

        protected internal void RemoveConnection(int id)
        {
            Session offclient = _clients.FirstOrDefault(c => c.Id == id);

            if (offclient != null)
                _clients.Remove(offclient);
        }
        
        protected internal void Listen()
        {
            try
            {
                _tcpListener = new TcpListener(Dns.Resolve(Dns.GetHostName()).AddressList[0], 3345);
                _tcpListener.Start();
                Console.WriteLine("Server started...");

                while (true)
                {
                    TcpClient tcpClient = _tcpListener.AcceptTcpClient();

                    Session Session = new Session(this, tcpClient, ++_clientIdCount);
                    new Thread(Session.Run).Start();
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                Disconnect();
            }
        }
        
        protected internal void SendMessage(string message)
        {
            byte[] data = Encoding.Unicode.GetBytes(message);
            _clients.ForEach(elem => elem.Stream.Write(data, 0, data.Length));
        }
        
        protected internal void Disconnect()
        {
            _tcpListener.Stop(); 
            _clients.ForEach(elem => elem.Close());
            Environment.Exit(0); 
        }
    }
}
