package ta.multicon;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Presentation extends Activity implements OnClickListener
{
	Vibrator vib;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presentation);
        
        vib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        
        ImageButton btn;
        
        btn = (ImageButton)findViewById(R.id.btnF5);
        btn.setOnClickListener(this);
        btn = (ImageButton)findViewById(R.id.btnSf5);
        btn.setOnClickListener(this);

        btn = (ImageButton)findViewById(R.id.btnPrevSlide);
        btn.setOnClickListener(this);
        btn = (ImageButton)findViewById(R.id.btnNextSlide);
        btn.setOnClickListener(this);
    }

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.btnF5: Client.SendKey(this, 0x74); break;
		case R.id.btnSf5: Client.SendShiftKey(this, 0x74); break;
		
		case R.id.btnPrevSlide: Client.SendKey(this, 0x25); break;
		case R.id.btnNextSlide: Client.SendKey(this, 0x27); break;
		
		default: return;
		}

		vib.vibrate(50);
	}
}
