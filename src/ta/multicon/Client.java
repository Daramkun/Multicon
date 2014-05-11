package ta.multicon;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class Client
{
	static Socket socket;
	static InetAddress sendAddress;
	
	public static boolean Connect(Context context, String ip, boolean isBluetooth)
	{
		if(!isBluetooth)
		{
			try
			{
				socket = new Socket();
				socket.connect(new InetSocketAddress(InetAddress.getByName(ip), 5670), 5000);
				
				new AlertDialog.Builder(context).setIcon(R.drawable.icon).setTitle(R.string.success)
				.setMessage(R.string.suc_msg).setPositiveButton(context.getResources().getString(R.string.back), new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}
				}).show();
			}
			catch (Exception ex) 
			{
				socket = null;
				
				new AlertDialog.Builder(context).setIcon(R.drawable.icon).setTitle(R.string.err)
				.setMessage(R.string.connfail).setPositiveButton(context.getResources().getString(R.string.back), new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}
				}).show();
				
				return false;
			}
		}
		else
		{
			
		}
		
		return true;
	}
	
	public static void Disconnect(Context context)
	{
		if(socket == null) return;
		try
		{
			socket.close();
			Toast.makeText(context, context.getResources().getText(R.string.disconnected), Toast.LENGTH_SHORT).show();
		}
		catch (IOException e) { e.printStackTrace(); }
		socket = null;
	}
	
	public static boolean IsConnected()
	{
		return (socket != null);
	}
	
	public static void SendKey(Context context, int key)
	{
		if(socket == null) return;
		try
		{
			byte[] data = {0, (byte)key, 0, 0};
			socket.getOutputStream().write(data);
		}
		catch (IOException e)
		{
			socket = null;
			new AlertDialog.Builder(context).setIcon(R.drawable.icon).setTitle(R.string.err)
			.setMessage(R.string.disconn).setPositiveButton(context.getResources().getString(R.string.back), new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					dialog.dismiss();
				}
			}).show();
		}
	}
	
	public static void SendShiftKey(Context context, int key)
	{
		if(socket == null) return;
		try
		{
			byte[] data = {1, (byte)0x10, (byte)key, 0};
			socket.getOutputStream().write(data);
		}
		catch (IOException e)
		{
			socket = null;
			new AlertDialog.Builder(context).setIcon(R.drawable.icon).setTitle(R.string.err)
			.setMessage(R.string.disconn).setPositiveButton(context.getResources().getString(R.string.back), new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					dialog.dismiss();
				}
			}).show();
		}
	}
	
	public static void SendGamePadKey(Context context, boolean isPressed, int key)
	{
		if(socket == null) return;
		try
		{
			byte[] data = {2, 0, (byte)key, (byte)((isPressed) ? 0 : 1)};
			socket.getOutputStream().write(data);
		}
		catch (IOException e)
		{
			socket = null;
			new AlertDialog.Builder(context).setIcon(R.drawable.icon).setTitle(R.string.err)
			.setMessage(R.string.disconn).setPositiveButton(context.getResources().getString(R.string.back), new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					dialog.dismiss();
				}
			}).show();
		}
	}
	
	public static void SendGamePadKeyForDriver(Context context, boolean isPressed, int key)
	{
		if(socket == null) return;
		try
		{
			byte[] data = {2, 1, (byte)key, (byte)((isPressed) ? 0 : 1)};
			socket.getOutputStream().write(data);
		}
		catch (IOException e)
		{
			socket = null;
			new AlertDialog.Builder(context).setIcon(R.drawable.icon).setTitle(R.string.err)
			.setMessage(R.string.disconn).setPositiveButton(context.getResources().getString(R.string.back), new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					dialog.dismiss();
				}
			}).show();
		}
	}
}