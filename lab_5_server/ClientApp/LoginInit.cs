using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace ClientApp
{
    public partial class LoginInit : Form
    {
        public string Login;

        public LoginInit()
        {
            InitializeComponent();
        }

        private void OKButton_Click(object sender, EventArgs e)
        {
            Login = LoginValue.Text;

            this.Visible = false;
            DialogResult = DialogResult.OK;
        }
    }
}
