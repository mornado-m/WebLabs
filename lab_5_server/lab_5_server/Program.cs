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
    class Program
    {
        private static Server server;

        static void Main(string[] args)
        {
            try
            {
                server = new Server();
                new Thread(server.Listen).Start();
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                throw;
            }
        }
    }
}
