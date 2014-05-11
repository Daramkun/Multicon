package ta.multicon;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GamePad extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamepad);
        
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        
        LinearLayout ll = (LinearLayout)findViewById(R.id.gamepadLayout);
        TextView tv = new TextView(this);
        
        if(!(dm.widthPixels == 480 && dm.heightPixels == 800) && !(dm.widthPixels == 800 && dm.heightPixels == 480))
        	tv.setText(getResources().getString(R.string.gamepad_invaild));
        else tv.setText(getResources().getString(R.string.gamepad_vaild));
        ll.addView(tv);
        GamePadView gpv = new GamePadView(this);
        ll.addView(gpv);
	}
}
