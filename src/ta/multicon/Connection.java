package ta.multicon;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class Connection extends Activity implements OnClickListener, OnItemClickListener
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect);

        Button btn = (Button)findViewById(R.id.btnConn);
        btn.setOnClickListener(this);
        btn = (Button)findViewById(R.id.btnClear);
        btn.setOnClickListener(this);
        
        ListView list = (ListView)findViewById(R.id.lstRecent);
        list.setOnItemClickListener(this);

        FileInputStream fis = null;
        ObjectInputStream ois = null;
		EditText tb = (EditText)findViewById(R.id.txtIp);
		boolean checkFirstConn = true;
		try
		{
			fis = openFileInput("ip.dat");
	        ois = new ObjectInputStream(fis);
			tb.setText(ois.readUTF());
			ois.close(); fis.close();
			checkFirstConn = false;
		}
		catch (FileNotFoundException e) { tb.setText("192.168."); }
		catch (StreamCorruptedException e) { tb.setText("192.168."); }
		catch (IOException e) { tb.setText("192.168."); }
		finally
		{
			if(checkFirstConn)
			{
				Toast.makeText(this, R.string.fir_msg, Toast.LENGTH_LONG).show();
			}
		}
		
		try
		{
			fis = openFileInput("recent.dat");
			ois = new ObjectInputStream(fis);
			
			int count = ois.readInt();
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
			for(int i = 0; i < count; i++)
				adapter.add(ois.readUTF());
			
			list.setAdapter(adapter);

			ois.close(); fis.close();
		}
		catch (FileNotFoundException e) { }
		catch (StreamCorruptedException e) { }
		catch (IOException e) { }
		
		ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		if(!cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting())
		{
			if(cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting())
			{
				new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.warning)).
					setMessage(getResources().getString(R.string.warn_msg)).
					setPositiveButton(getResources().getString(R.string.back), new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}
				}).show();
			}
			else
			{
				new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.err)).
					setMessage(getResources().getString(R.string.err_msg)).
					setPositiveButton(getResources().getString(R.string.back), new DialogInterface.OnClickListener()
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{
		EditText ip = (EditText)findViewById(R.id.txtIp);
		ip.setText(((TextView)arg1).getText());
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.btnConn:
			{
				EditText tb = (EditText)findViewById(R.id.txtIp);
				try
				{
					if(Client.Connect(this, tb.getText().toString(), false))
					{
						FileOutputStream fos = openFileOutput("ip.dat", Context.MODE_PRIVATE);
						ObjectOutputStream oos = new ObjectOutputStream(fos);
						oos.writeUTF(tb.getText().toString());
						oos.close(); fos.close();
						InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					    imm.hideSoftInputFromWindow(tb.getWindowToken(), 0);
					}
				} catch (Exception e) { }
		
		        int count = 0;
		        String[] recent = null;
				
				try
				{
			        FileInputStream fis = openFileInput("recent.dat");
			        ObjectInputStream ois = new ObjectInputStream(fis);
			        
			        boolean none = true;
			        count = ois.readInt();
			        recent = new String[count + 1];
			        for(int i = 0; i < count; i++)
			        {
			        	recent[i] = ois.readUTF();
			        	if(recent[i].contentEquals(tb.getText())) none = false;
			        }
					ois.close(); fis.close();
					
					if(none) { recent[count] = tb.getText().toString(); count++; }
				}
				catch (Exception e) { }
				finally
				{
					try
					{
						FileOutputStream fos = openFileOutput("recent.dat", Context.MODE_PRIVATE);
						ObjectOutputStream oos = new ObjectOutputStream(fos);
						oos.flush();
		
						if(count > 0)
						{
							oos.writeInt(count);
							for(int i = 0; i < count; i++) oos.writeUTF(recent[i]);
						}
						else
						{
							oos.writeInt(1); oos.writeUTF(tb.getText().toString());
						}
						
						oos.close(); fos.close();
					} catch (FileNotFoundException e) { } catch (IOException e) { }
				}
			}
			break;
		case R.id.btnClear:
			try
			{
				FileOutputStream fos = openFileOutput("recent.dat", Context.MODE_PRIVATE);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.flush();
				oos.writeInt(0);
				oos.close(); fos.close();
			} catch (FileNotFoundException e) { } catch (IOException e) { }
			
	        ListView list = (ListView)findViewById(R.id.lstRecent);
			ArrayAdapter<?> adapter = (ArrayAdapter<?>) list.getAdapter();
			if(adapter != null)
			{
				adapter.clear();
				adapter.notifyDataSetChanged();
			}
			
	        break;
		}
	}
}
