package ta.multicon;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class Multimedia extends Activity implements OnClickListener
{
	Vibrator vib;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multimedia);
        
        vib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        
        ImageButton btn;
        btn = (ImageButton)findViewById(R.id.btnPrev);
        btn.setOnClickListener(this);
        btn = (ImageButton)findViewById(R.id.btnPlay);
        btn.setOnClickListener(this);
        btn = (ImageButton)findViewById(R.id.btnStop);
        btn.setOnClickListener(this);
        btn = (ImageButton)findViewById(R.id.btnNext);
        btn.setOnClickListener(this);
        
        btn = (ImageButton)findViewById(R.id.btnVd);
        btn.setOnClickListener(this);
        btn = (ImageButton)findViewById(R.id.btnVm);
        btn.setOnClickListener(this);
        btn = (ImageButton)findViewById(R.id.btnVu);
        btn.setOnClickListener(this);
        
        Button btn2;
        btn2 = (Button)findViewById(R.id.btnEnter);
        btn2.setOnClickListener(this);
        btn2 = (Button)findViewById(R.id.btnSpace);
        btn2.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.btnVm: Client.SendKey(this, 0xAD); break;
		case R.id.btnVd: Client.SendKey(this, 0xAE); break;
		case R.id.btnVu: Client.SendKey(this, 0xAF); break;

		case R.id.btnNext: Client.SendKey(this, 0xB0); break;
		case R.id.btnPrev: Client.SendKey(this, 0xB1); break;
		case R.id.btnStop: Client.SendKey(this, 0xB2); break;
		case R.id.btnPlay: Client.SendKey(this, 0xB3); break;
		
		case R.id.btnEnter: Client.SendKey(this, 0xD); break;
		case R.id.btnSpace: Client.SendKey(this, 0x20); break;
		
		default: return;
		}
		
		vib.vibrate(50);
	}
}
