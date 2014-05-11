package ta.multicon;

import android.app.TabActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class Main extends TabActivity implements OnTabChangeListener
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        TabHost tabHost = getTabHost();
        TabSpec connTab = tabHost.newTabSpec("connTab").setIndicator(getResources().getString(R.string.tab_conn));
        Intent connIntent = new Intent(Main.this, Connection.class);
        connTab.setContent(connIntent);
        tabHost.addTab(connTab);

        TabSpec mulTab = tabHost.newTabSpec("mulTab").setIndicator(getResources().getString(R.string.tab_multi));
        Intent mulIntent = new Intent(Main.this, Multimedia.class);
        mulTab.setContent(mulIntent);
        tabHost.addTab(mulTab);

        TabSpec presTab = tabHost.newTabSpec("presTab").setIndicator(getResources().getString(R.string.tab_pres));
        Intent presIntent = new Intent(Main.this, Presentation.class);
        presTab.setContent(presIntent);
        tabHost.addTab(presTab);

        TabSpec gpTab = tabHost.newTabSpec("gpTab").setIndicator(getResources().getString(R.string.tab_gamepad));
        Intent gpIntent = new Intent(Main.this, GamePad.class);
        gpTab.setContent(gpIntent);
        tabHost.addTab(gpTab);
        
        tabHost.setOnTabChangedListener(this);
    }
    
    @Override
    public void onBackPressed()
    {
    	Client.Disconnect(this);
    	super.onBackPressed();
    }

	@Override
	public void onTabChanged(String tabId)
	{
		if(tabId == "gpTab")
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
		else
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
	}
}