using System;
using System.Collections.Generic;
using System.Windows.Forms;
using System.Runtime.InteropServices;
using System.Net.Sockets;
using System.Threading;
using System.Net;
using MulticonReceiver.Properties;

namespace MulticonReceiver
{
	static class Program
	{
		[DllImport ( "user32.dll" )]
		private static extern void keybd_event ( byte bVk, byte bScan, int dwFlags, int dwExtraInfo );

		private const byte VK_VOLUME_MUTE = 0xAD;
		private const byte VK_VOLUME_DOWN = 0xAE;
		private const byte VK_VOLUME_UP = 0xAF;

		private const byte VK_MEDIA_NEXT_TRACK = 0xB0;
		private const byte VK_MEDIA_PREV_TRACK = 0xB1;
		private const byte VK_MEDIA_STOP = 0xB2;
		private const byte VK_MEDIA_PLAY_PAUSE = 0xB3;

		private const int VK_LEFT = 0x25;
		private const int VK_RIGHT = 0x27;

		private const int VK_F5 = 0x74;
		private const int VK_SHIFT = 0x10;

		private const int VK_RETURN = 0xD;
		private const int VK_SPACE = 0x20;
		
		static Socket socket, client;
		static Thread thread;
		static NotifyIcon ni;
		internal static bool run = true;

		static string stateMessage = "";

		static MulticonReceiver.IpList main;
		static MulticonReceiver.GamePadKeyMap map;

		static Socket multijoy;
		static IPEndPoint joyLocal;

		[STAThread]
		static void Main ()
		{
			Application.EnableVisualStyles ();

			socket = new Socket ( AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp );
			socket.Bind ( new IPEndPoint ( IPAddress.Any, 5670 ) );
			socket.Listen ( 5 );

			thread = new Thread ( Threading );
			thread.Start ();

			multijoy = new Socket ( AddressFamily.InterNetwork, SocketType.Dgram, ProtocolType.Udp );
			joyLocal = new IPEndPoint ( IPAddress.Loopback, 22567 );

			main = new MulticonReceiver.IpList ();
			map = new GamePadKeyMap ();

			ni = new NotifyIcon ();
			ni.Icon = Resources.MyIcon;
			ni.Text = "Multicon Receiver v2.0";
			ni.Visible = true;
			ni.ContextMenuStrip = main.notifyMenu;
			ni.MouseDoubleClick += new MouseEventHandler ( ( object sender, MouseEventArgs e ) => { main.Show (); } );

			main.exitToolStripMenuItem.Click += new EventHandler ( ( object sender, EventArgs e ) => { run = false; } );
			main.setGamePadKeyMappingToolStripMenuItem.Click += new EventHandler ( ( object sender, EventArgs e ) => { map.Show (); } );

			ni.ShowBalloonTip ( 5000, "Multicon Receiver v2.0", "Multicon Receiver is running...", ToolTipIcon.Info );

			while ( run )
			{
				ni.Text = "Multicon Receiver v2.0" + Environment.NewLine + stateMessage;
				Application.DoEvents ();
			}

			try
			{
				if ( client != null ) client.Close ();
				socket.Close ();
			}
			catch { }

			ni.Visible = false;
			Application.DoEvents ();
		}

		static void Threading ()
		{
			while ( true )
			{
				stateMessage = "Waitting...";
				try
				{
					client = socket.Accept ();
				}
				catch { stateMessage = "Server is Down."; break; }
				stateMessage = "Connected!";
				ni.ShowBalloonTip ( 5000, "Multicon Receiver v2.0", "Connected to Device.", ToolTipIcon.Info );

				while ( true )
				{
					byte [] data = new byte [ 4 ];
					try
					{
						int count = client.Receive ( data, 4, SocketFlags.None );
						if ( count == 0 )
						{
							ni.ShowBalloonTip ( 5000, "Multicon Receiver v2.0", "Connection Lost.", ToolTipIcon.Error );
							client = null; break;
						}
					}
					catch { client = null; break; }

					switch ( data [ 0 ] )
					{
							// 일반키
						case 0:
							switch ( data [ 1 ] )
							{
								case VK_MEDIA_NEXT_TRACK: stateMessage = "Next Track Key Pressed."; break;
								case VK_MEDIA_PLAY_PAUSE: stateMessage = "Play or Pause Key Pressed."; break;
								case VK_MEDIA_PREV_TRACK: stateMessage = "Previous Track Key Pressed."; break;
								case VK_MEDIA_STOP: stateMessage = "Media Stop Key Pressed."; break;
								case VK_VOLUME_DOWN: stateMessage = "Volume Down Key Pressed."; break;
								case VK_VOLUME_MUTE: stateMessage = "Mute Key Pressed."; break;
								case VK_VOLUME_UP: stateMessage = "Volume Up Key Pressed."; break;
								case VK_LEFT: stateMessage = "Left Key Pressed."; break;
								case VK_RIGHT: stateMessage = "Right Key Pressed."; break;
								case VK_F5: stateMessage = "F5 Key Pressed."; break;
								case VK_RETURN: stateMessage = "Enter Key Pressed."; break;
								case VK_SPACE: stateMessage = "Space Bar Pressed."; break;
								default: continue;
							}
							keybd_event ( data [ 1 ], 0, 0, 0 );
							keybd_event ( data [ 1 ], 0, 2, 0 );
							break;
							// 조합키
						case 1:
							switch ( data [ 1 ] )
							{
								case VK_SHIFT:
									switch ( data [ 2 ] )
									{
										case VK_F5: stateMessage = "Shift + F5 Key Pressed."; break;
										default: continue;
									}
									break;
								default: continue;
							}
							keybd_event ( data [ 1 ], 0, 0, 0 );
							keybd_event ( data [ 2 ], 0, 0, 0 );
							keybd_event ( data [ 2 ], 0, 2, 0 );
							keybd_event ( data [ 1 ], 0, 2, 0 );
							break;
							// 게임패드키
							// 패킷 구조 : 0 1 2 3 
						case 2:
							switch ( data [ 1 ] )
							{ 
									// 키맵핑
								case 0:
									switch ( data [ 3 ] )
									{ 
											// 눌렸다
										case 0:
											keybd_event ( GetKey ( data [ 2 ] ), 0, 0, 0 );
											break;
											// 떼였다
										case 1:
											keybd_event ( GetKey ( data [ 2 ] ), 0, 2, 0 );
											break;
									}
									break;
									// 드라이버 커넥션
								case 1:
									multijoy.SendTo ( data, SocketFlags.None, joyLocal );
									break;
							}
							break;
					}
				}
			}
		}

		public static object MouseEvenrArgs { get; set; }

		static byte GetKey ( byte data )
		{
			switch ( data )
			{
				case 0: return Settings.Default.Up;
				case 1: return Settings.Default.Down;
				case 2: return Settings.Default.Left;
				case 3: return Settings.Default.Right;
				case 4: return Settings.Default.A;
				case 5: return Settings.Default.B;
				case 6: return Settings.Default.X;
				case 7: return Settings.Default.Y;
				case 8: return Settings.Default.Select;
				case 9: return Settings.Default.Start;
				case 10: return Settings.Default.L;
				case 11: return Settings.Default.R;
				default:
					return 255;
			}
		}
	}
}
