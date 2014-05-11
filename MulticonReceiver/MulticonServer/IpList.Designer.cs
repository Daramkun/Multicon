namespace MulticonReceiver
{
	partial class IpList
	{
		/// <summary>
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.IContainer components = null;

		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		/// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
		protected override void Dispose ( bool disposing )
		{
			if ( disposing && ( components != null ) )
			{
				components.Dispose ();
			}
			base.Dispose ( disposing );
		}

		#region Windows Form Designer generated code

		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent ()
		{
			this.components = new System.ComponentModel.Container();
			System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(IpList));
			this.label1 = new System.Windows.Forms.Label();
			this.lstIp = new System.Windows.Forms.ListBox();
			this.notifyMenu = new System.Windows.Forms.ContextMenuStrip(this.components);
			this.exitToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
			this.tmrState = new System.Windows.Forms.Timer(this.components);
			this.setGamePadKeyMappingToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
			this.toolStripMenuItem1 = new System.Windows.Forms.ToolStripSeparator();
			this.notifyMenu.SuspendLayout();
			this.SuspendLayout();
			// 
			// label1
			// 
			this.label1.AutoSize = true;
			this.label1.Location = new System.Drawing.Point(22, 38);
			this.label1.Name = "label1";
			this.label1.Size = new System.Drawing.Size(28, 12);
			this.label1.TabIndex = 0;
			this.label1.Text = "IP : ";
			// 
			// lstIp
			// 
			this.lstIp.FormattingEnabled = true;
			this.lstIp.ItemHeight = 12;
			this.lstIp.Location = new System.Drawing.Point(56, 12);
			this.lstIp.Name = "lstIp";
			this.lstIp.Size = new System.Drawing.Size(229, 64);
			this.lstIp.TabIndex = 1;
			// 
			// notifyMenu
			// 
			this.notifyMenu.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.setGamePadKeyMappingToolStripMenuItem,
            this.toolStripMenuItem1,
            this.exitToolStripMenuItem});
			this.notifyMenu.Name = "notifyMenu";
			this.notifyMenu.Size = new System.Drawing.Size(221, 76);
			// 
			// exitToolStripMenuItem
			// 
			this.exitToolStripMenuItem.Name = "exitToolStripMenuItem";
			this.exitToolStripMenuItem.Size = new System.Drawing.Size(220, 22);
			this.exitToolStripMenuItem.Text = "E&xit";
			// 
			// tmrState
			// 
			this.tmrState.Enabled = true;
			// 
			// setGamePadKeyMappingToolStripMenuItem
			// 
			this.setGamePadKeyMappingToolStripMenuItem.Name = "setGamePadKeyMappingToolStripMenuItem";
			this.setGamePadKeyMappingToolStripMenuItem.Size = new System.Drawing.Size(220, 22);
			this.setGamePadKeyMappingToolStripMenuItem.Text = "Set GamePad key mapping";
			// 
			// toolStripMenuItem1
			// 
			this.toolStripMenuItem1.Name = "toolStripMenuItem1";
			this.toolStripMenuItem1.Size = new System.Drawing.Size(217, 6);
			// 
			// IpList
			// 
			this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.ClientSize = new System.Drawing.Size(310, 91);
			this.Controls.Add(this.lstIp);
			this.Controls.Add(this.label1);
			this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedToolWindow;
			this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
			this.Name = "IpList";
			this.StartPosition = System.Windows.Forms.FormStartPosition.Manual;
			this.Text = "Multicon Receiver IP list";
			this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.Main_FormClosing);
			this.Load += new System.EventHandler(this.Main_Load);
			this.notifyMenu.ResumeLayout(false);
			this.ResumeLayout(false);
			this.PerformLayout();

		}

		#endregion

		private System.Windows.Forms.Label label1;
		private System.Windows.Forms.ListBox lstIp;
		private System.Windows.Forms.Timer tmrState;
		internal System.Windows.Forms.ContextMenuStrip notifyMenu;
		internal System.Windows.Forms.ToolStripMenuItem exitToolStripMenuItem;
		private System.Windows.Forms.ToolStripSeparator toolStripMenuItem1;
		internal System.Windows.Forms.ToolStripMenuItem setGamePadKeyMappingToolStripMenuItem;
	}
}

