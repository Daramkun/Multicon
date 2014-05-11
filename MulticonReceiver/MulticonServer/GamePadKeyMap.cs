using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using MulticonReceiver.Properties;

namespace MulticonReceiver
{
	public partial class GamePadKeyMap : Form
	{
		int selectedItem = 0;

		public int SelectedItem
		{
			get
			{
				return selectedItem;
			}
			set
			{
				selectedItem = value;
				label2.Text = lstKeys.Items [ value ].SubItems [ 0 ].Text;
			}
		}

		public GamePadKeyMap ()
		{
			InitializeComponent ();
		}

		private void GamePadKeyMap_FormClosing ( object sender, FormClosingEventArgs e )
		{
			Settings.Default.Save ();
		}

		private void GamePadKeyMap_Shown ( object sender, EventArgs e )
		{
			lstKeys.Items [ 0 ].SubItems.Add ( ( ( Keys ) Settings.Default.Up ).ToString () );
			lstKeys.Items [ 1 ].SubItems.Add ( ( ( Keys ) Settings.Default.Down ).ToString () );
			lstKeys.Items [ 2 ].SubItems.Add ( ( ( Keys ) Settings.Default.Left ).ToString () );
			lstKeys.Items [ 3 ].SubItems.Add ( ( ( Keys ) Settings.Default.Right ).ToString () );
			lstKeys.Items [ 4 ].SubItems.Add ( ( ( Keys ) Settings.Default.A ).ToString () );
			lstKeys.Items [ 5 ].SubItems.Add ( ( ( Keys ) Settings.Default.B ).ToString () );
			lstKeys.Items [ 6 ].SubItems.Add ( ( ( Keys ) Settings.Default.X ).ToString () );
			lstKeys.Items [ 7 ].SubItems.Add ( ( ( Keys ) Settings.Default.Y ).ToString () );
			lstKeys.Items [ 8 ].SubItems.Add ( ( ( Keys ) Settings.Default.Start ).ToString () );
			lstKeys.Items [ 9 ].SubItems.Add ( ( ( Keys ) Settings.Default.Select ).ToString () );

			SelectedItem = 0;
		}

		private void textBox1_KeyDown ( object sender, KeyEventArgs e )
		{
			lstKeys.Items [ SelectedItem ].SubItems [ 1 ].Text = e.KeyCode.ToString ();
			switch ( SelectedItem )
			{
				case 0: Settings.Default.Up = ( byte ) e.KeyCode; break;
				case 1: Settings.Default.Down = ( byte ) e.KeyCode; break;
				case 2: Settings.Default.Left = ( byte ) e.KeyCode; break;
				case 3: Settings.Default.Right = ( byte ) e.KeyCode; break;
				case 4: Settings.Default.A = ( byte ) e.KeyCode; break;
				case 5: Settings.Default.B = ( byte ) e.KeyCode; break;
				case 6: Settings.Default.X = ( byte ) e.KeyCode; break;
				case 7: Settings.Default.Y = ( byte ) e.KeyCode; break;
				case 8: Settings.Default.Start = ( byte ) e.KeyCode; break;
				case 9: Settings.Default.Select = ( byte ) e.KeyCode; break;
			}
		}

		private void btnUp_Click ( object sender, EventArgs e )
		{
			SelectedItem = 0;
		}

		private void btnDown_Click ( object sender, EventArgs e )
		{
			SelectedItem = 1;
		}

		private void btnLeft_Click ( object sender, EventArgs e )
		{
			SelectedItem = 2;
		}

		private void btnRight_Click ( object sender, EventArgs e )
		{
			SelectedItem = 3;
		}

		private void btnA_Click ( object sender, EventArgs e )
		{
			SelectedItem = 4;
		}

		private void btnB_Click ( object sender, EventArgs e )
		{
			SelectedItem = 5;
		}

		private void btnX_Click ( object sender, EventArgs e )
		{
			SelectedItem = 6;
		}

		private void btnY_Click ( object sender, EventArgs e )
		{
			SelectedItem = 7;
		}

		private void btnStart_Click ( object sender, EventArgs e )
		{
			SelectedItem = 8;
		}

		private void btnSelect_Click ( object sender, EventArgs e )
		{
			SelectedItem = 9;
		}
	}
}
