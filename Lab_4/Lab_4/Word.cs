using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Lab_4
{
    //Код класу для роботи із окремим словом 
    //в окремому потоці
    public class Word
    {
        private Label _text;
        private Random _rand;
        public int Width;
        public int Height;
        public bool Resume;
        public Thread WordThread;

        public Word(Label text, int borderWidth, int borderHeight)
        {
            _text = text;
            Width = borderWidth;
            Height = borderHeight;
            Resume = true;
            _rand = new Random();
            WordThread = new Thread(Move);
            WordThread.Start();
        }

        private void Move()
        {
            while (Resume)
            {
                _text.Invoke((Action) (() =>
                {
                    int OffsetX = _rand.Next(-_rand.Next(5, 30), _rand.Next(5, 30));
                    int OffsetY = _rand.Next(-_rand.Next(5, 30), _rand.Next(5, 30));
                    if (_text.Location.X < 50)
                        OffsetX = 20;
                    if (_text.Location.X > Width - 100)
                        OffsetX = -20;
                    if (_text.Location.Y < 50)
                        OffsetY = 20;
                    if (_text.Location.Y > Height - 50)
                        OffsetY = -20;
                    _text.Location = new Point(_text.Location.X + OffsetX,
                        _text.Location.Y + OffsetY);
                }));
                Thread.Sleep(140);
            }
        }
    }
}
