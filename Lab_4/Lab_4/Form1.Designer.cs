namespace Lab_4
{
    partial class MainForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.RestartButton = new System.Windows.Forms.Button();
            this.TryCounterLabel = new System.Windows.Forms.Label();
            this.TryCounterValue = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // RestartButton
            // 
            this.RestartButton.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.RestartButton.Location = new System.Drawing.Point(274, 12);
            this.RestartButton.MinimumSize = new System.Drawing.Size(125, 60);
            this.RestartButton.Name = "RestartButton";
            this.RestartButton.Size = new System.Drawing.Size(126, 61);
            this.RestartButton.TabIndex = 0;
            this.RestartButton.Text = "Restart";
            this.RestartButton.UseVisualStyleBackColor = true;
            this.RestartButton.Visible = false;
            this.RestartButton.Click += new System.EventHandler(this.RestartButton_Click);
            // 
            // TryCounterLabel
            // 
            this.TryCounterLabel.AutoSize = true;
            this.TryCounterLabel.Location = new System.Drawing.Point(13, 13);
            this.TryCounterLabel.Name = "TryCounterLabel";
            this.TryCounterLabel.Size = new System.Drawing.Size(44, 19);
            this.TryCounterLabel.TabIndex = 1;
            this.TryCounterLabel.Text = "Try #";
            // 
            // TryCounterValue
            // 
            this.TryCounterValue.AutoSize = true;
            this.TryCounterValue.Location = new System.Drawing.Point(63, 13);
            this.TryCounterValue.Name = "TryCounterValue";
            this.TryCounterValue.Size = new System.Drawing.Size(18, 19);
            this.TryCounterValue.TabIndex = 2;
            this.TryCounterValue.Text = "1";
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 19F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(696, 518);
            this.Controls.Add(this.TryCounterValue);
            this.Controls.Add(this.TryCounterLabel);
            this.Controls.Add(this.RestartButton);
            this.Font = new System.Drawing.Font("Times New Roman", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(204)));
            this.Margin = new System.Windows.Forms.Padding(3, 4, 3, 4);
            this.MinimumSize = new System.Drawing.Size(600, 400);
            this.Name = "MainForm";
            this.ShowIcon = false;
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Threads mini-game";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.MainForm_FormClosing);
            this.Shown += new System.EventHandler(this.MainForm_Shown);
            this.ResizeEnd += new System.EventHandler(this.MainForm_ResizeEnd);
            this.Click += new System.EventHandler(this.MainForm_Click);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button RestartButton;
        private System.Windows.Forms.Label TryCounterLabel;
        private System.Windows.Forms.Label TryCounterValue;
    }
}

