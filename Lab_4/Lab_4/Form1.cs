using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Lab_4
{
    public partial class MainForm : Form
    {
        private List<Label> _words;
        private List<Word> _wordsControllers;
        private Word _winWord;
        private Random _rand;
        private int _tryCounter = 1;

        public MainForm()
        {            
            InitializeComponent();
            _rand = new Random();
            _words = new List<Label>();
            _wordsControllers = new List<Word>();
            for (int i = 0; i < 10; ++i)
            {
                _words.Add(new Label());
                if (i < 9)
                {
                    _words[i].Text = "word" + i;
                    _words[i].Size = new Size(50, 20);
                    _words[i].Click += (sender, args) =>
                    {
                        _tryCounter++;
                        TryCounterValue.Text = _tryCounter.ToString();
                    };
                }
                else
                {
                    _words[i].Text = "Catch me!";
                    _words[i].Size = new Size(70, 20);
                    _words[i].Click += (sender, args) =>
                    {
                        _wordsControllers.ForEach(elem => elem.Resume = false);
                        MessageBox.Show("You win!");
                        RestartButton.Visible = true;
                    };
                }
                _words[i].Parent = this;
                _words[i].Location = new Point(_rand.Next(50, Width - 100), _rand.Next(50, Height - 50));
                _words[i].Visible = true;
                _words[i].LocationChanged += (sender, args) => this.Update();
            }
        }

        private void MainForm_Shown(object sender, EventArgs e)
        {
            foreach (var word in _words)
            {
                _wordsControllers.Add(new Word(word, Width, Height));
            }
        }

        private void MainForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            _wordsControllers.ForEach(elem => elem.Resume = false);
        }

        private void RestartButton_Click(object sender, EventArgs e)
        {
            _wordsControllers.RemoveAll(elem => true);
            _tryCounter = 1;
            TryCounterValue.Text = _tryCounter.ToString();
            _words.ForEach(elem => _wordsControllers.Add(new Word(elem, Width, Height)));
            RestartButton.Visible = false;
        }

        private void MainForm_ResizeEnd(object sender, EventArgs e)
        {
            if (RestartButton.Visible) return;
            _wordsControllers.ForEach(elem =>
            {
                elem.Width = Width;
                elem.Height = Height;
            });
        }

        private void MainForm_Click(object sender, EventArgs e)
        {
            if (RestartButton.Visible) return;
            _tryCounter++;
            TryCounterValue.Text = _tryCounter.ToString();
        }
    }
}
