using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Net;
using System.Net.Sockets;
using System.IO;
using System.Runtime.InteropServices;
using System.Diagnostics;
using System.Threading;

namespace MulticonReceiver
{
	public partial class IpList : Form
	{
		public IpList ()
		{
			InitializeComponent ();
		}

		private void Main_Load ( object sender, EventArgs e )
		{
			Left = Screen.PrimaryScreen.WorkingArea.Width - Right;
			Top = Screen.PrimaryScreen.WorkingArea.Height - Bottom;

			IPHostEntry he = Dns.GetHostEntry ( Dns.GetHostName () );
			foreach ( IPAddress i in he.AddressList )
				if ( i.AddressFamily == AddressFamily.InterNetwork )
					lstIp.Items.Add ( i.ToString () );
		}

		private void Main_FormClosing ( object sender, FormClosingEventArgs e )
		{
			if ( Program.run == true )
				e.Cancel = true;
			this.Hide ();
		}
	}
}
