using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace ClientApp
{
    class Client
    {
        private TcpClient client;
        private NetworkStream Stream;
        private string login;

        public Client(string log)
        {
            client = new TcpClient(Dns.GetHostName(), 3345);
        }

        public void Run()
        {
            Stream = client.GetStream();



            while (true)
            {
                try
                {

                }
                catch (Exception e)
                {
                    Console.WriteLine(e);
                    throw;
                }
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
    }
}
