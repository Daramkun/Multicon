package ta.multicon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePadView extends SurfaceView implements SurfaceHolder.Callback
{
	float circleX, circleY, circleLastX, circleLastY;
	int circleIndex = -1;
	boolean[] isClicked;
	boolean[] isLastClicked;

	public GamePadView(Context context)
	{
		super(context);
		isClicked = new boolean[12];
		isLastClicked = new boolean[12];
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height)
	{
		drawScreen();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		drawScreen();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		int action = event.getAction();
		
		switch(event.getAction() & MotionEvent.ACTION_MASK)
		{
		case MotionEvent.ACTION_DOWN:
			touchDown(event.getPointerId(0), event.getX(), event.getY(), event.getPressure());
			break;
		case MotionEvent.ACTION_POINTER_1_DOWN:
		case MotionEvent.ACTION_POINTER_2_DOWN:
			{
				int index = (action & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT;
				int id = event.getPointerId(index);
				touchDown(id, event.getX(id), event.getY(id), event.getPressure(id));
			}
			break;
			
		case MotionEvent.ACTION_MOVE:
			for(int i = 0; i < event.getPointerCount(); i++)
			{
				int id = event.findPointerIndex(i);
				if(id == -1) continue;
				
				touchMove(id, event.getX(id), event.getY(id), event.getPressure(id));
			}
			break;
			
		case MotionEvent.ACTION_UP:
			touchUp(event.getPointerId(0), event.getX(), event.getY(), event.getPressure());
		case MotionEvent.ACTION_POINTER_1_UP:
		case MotionEvent.ACTION_POINTER_2_UP:
			{
				int index = (action & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT;
				int id = event.getPointerId(index);
				touchUp(id, event.getX(id), event.getY(id), event.getPressure(id));
			}
			break;
		}
		
		drawScreen();
		
		return true;
	}
	
	private void touchesButtons(float x, float y, float pressure)
	{
		float range = (pressure * 50) + 50;
		
		///////////////////////////////// A /////////////////////////////////
		if(Math.sqrt(Math.pow(x - 620, 2) + Math.pow(y - 230, 2)) <= range)
		{
			if(!isLastClicked[4]) Client.SendGamePadKey(getContext(), true, 4);
			isLastClicked[4] = isClicked[4] = true;
		}
		else if(isLastClicked[4])
		{
			Client.SendGamePadKey(getContext(), false, 4);
			isLastClicked[4] = isClicked[4] = false;
		}

		///////////////////////////////// B /////////////////////////////////
		if(Math.sqrt(Math.pow(x - 730, 2) + Math.pow(y - 180, 2)) <= range)
		{
			if(!isLastClicked[5]) Client.SendGamePadKey(getContext(), true, 5);
			isLastClicked[5] = isClicked[5] = true;
		}
		else if(isLastClicked[5])
		{
			Client.SendGamePadKey(getContext(), false, 5);
			isLastClicked[5] = isClicked[5] = false;
		}

		///////////////////////////////// X /////////////////////////////////
		if(Math.sqrt(Math.pow(x - 560, 2) + Math.pow(y - 130, 2)) <= range)
		{
			if(!isLastClicked[6]) Client.SendGamePadKey(getContext(), true, 6);
			isLastClicked[6] = isClicked[6] = true;
		}
		else if(isLastClicked[6]) 
		{
			Client.SendGamePadKey(getContext(), false, 6);
			isLastClicked[6] = isClicked[6] = false;
		}

		///////////////////////////////// Y /////////////////////////////////
		if(Math.sqrt(Math.pow(x - 670, 2) + Math.pow(y - 80, 2)) <= range)
		{
			if(!isLastClicked[7]) Client.SendGamePadKey(getContext(), true, 7);
			isLastClicked[7] = isClicked[7] = true;
		}
		else if(isLastClicked[7])
		{
			Client.SendGamePadKey(getContext(), false, 7);
			isLastClicked[7] = isClicked[7] = false;
		}

		///////////////////////////////// Select /////////////////////////////////
		if(x <= 350 && 250 <= x && y <= 240 && 180 <= y)
		{
			if(!isLastClicked[8]) Client.SendGamePadKey(getContext(), true, 8);
			isLastClicked[8] = isClicked[8] = true;
		}
		else if(isLastClicked[8])
		{
			Client.SendGamePadKey(getContext(), false, 8);
			isLastClicked[8] = isClicked[8] = false;
		}

		///////////////////////////////// Start /////////////////////////////////
		if(x <= 480 && 380 <= x && y <= 240 && 180 <= y)
		{
			if(!isLastClicked[9]) Client.SendGamePadKey(getContext(), true, 9);
			isLastClicked[9] = isClicked[9] = true;
		}
		else if(isLastClicked[9])
		{
			Client.SendGamePadKey(getContext(), false, 9);
			isLastClicked[9] = isClicked[9] = false;
		}
	}
	
	private void touchDown(int id, float x, float y, float pressure)
	{
		if(circleIndex == -1)
		{
			if(Math.sqrt(Math.pow(x - 130, 2) + Math.pow(y - 130, 2)) <= 80)
			{
				circleIndex = id;
				circleLastX = x; circleLastY = y;
			}
		}

		touchesButtons(x, y, pressure);
	}
	
	private void touchMove(int id, float x, float y, float pressure)
	{
		if(circleIndex == id)
		{
			circleX += x - circleLastX;
			circleY += y - circleLastY;
			
			if(Math.sqrt(Math.pow(circleX, 2) + Math.pow(circleY, 2)) > 40)
			{
				if(Math.sqrt(Math.pow(circleX, 2) + Math.pow(circleY, 2)) > 50)
		 		{
					circleX -= x - circleLastX;
					circleY -= y - circleLastY;
				}
				
				float angle = (float)Math.atan2(circleY, circleX) * 180 / 3.141592f + 180;

				///////////////////////////////// Left move /////////////////////////////////
				if((angle >= 0 && angle <= 55) || (angle >= 305 && angle <= 360))
				{
					if(!isLastClicked[2]) Client.SendGamePadKey(getContext(), true, 2);
					isClicked[2] = true; isLastClicked[2] = true;
				}
				else
				{
					if(isLastClicked[2]) Client.SendGamePadKey(getContext(), false, 2);
					isLastClicked[2] = false;
				}
				
				///////////////////////////////// Up move /////////////////////////////////
				if(angle >= 35 && angle <= 145)
				{
					if(!isLastClicked[0]) Client.SendGamePadKey(getContext(), true, 0);
					isClicked[0] = true; isLastClicked[0] = true;
				}
				else
				{
					if(isLastClicked[0]) Client.SendGamePadKey(getContext(), false, 0);
					isLastClicked[0] = false;
				}
				
				///////////////////////////////// Right move /////////////////////////////////
				if(angle >= 125 && angle <= 235)
				{
					if(!isLastClicked[3]) Client.SendGamePadKey(getContext(), true, 3);
					isClicked[3] = true; isLastClicked[3] = true;
				}
				else
				{
					if(isLastClicked[3]) Client.SendGamePadKey(getContext(), false, 3);
					isLastClicked[3] = false;
				}
				
				///////////////////////////////// Down move /////////////////////////////////
				if(angle >= 215 && angle <= 325)
				{
					if(!isLastClicked[1]) Client.SendGamePadKey(getContext(), true, 1);
					isClicked[1] = true; isLastClicked[1] = true;
				}
				else
				{
					if(isLastClicked[1]) Client.SendGamePadKey(getContext(), false, 1);
					isLastClicked[1] = false;
				}
			}
			else
			{
				if(isLastClicked[0]) Client.SendGamePadKey(getContext(), false, 0);
				if(isLastClicked[1]) Client.SendGamePadKey(getContext(), false, 1);
				if(isLastClicked[2]) Client.SendGamePadKey(getContext(), false, 2);
				if(isLastClicked[3]) Client.SendGamePadKey(getContext(), false, 3);
				isLastClicked[0] = false;
				isLastClicked[1] = false;
				isLastClicked[2] = false;
				isLastClicked[3] = false;
			}
			
			circleLastX = x; circleLastY = y;
		}

		touchesButtons(x, y, pressure);
	}
	
	private void touchUp(int id, float x, float y, float pressure)
	{
		if(circleIndex == id)
		{
			circleIndex = -1;
			circleX = circleY = 0;
			
			if(isLastClicked[0]) Client.SendGamePadKey(getContext(), false, 0);
			isLastClicked[0] = isClicked[0] = false;
			if(isLastClicked[1]) Client.SendGamePadKey(getContext(), false, 1);
			isLastClicked[1] = isClicked[1] = false;
			if(isLastClicked[2]) Client.SendGamePadKey(getContext(), false, 2);
			isLastClicked[2] = isClicked[2] = false;
			if(isLastClicked[3]) Client.SendGamePadKey(getContext(), false, 3);
			isLastClicked[3] = isClicked[3] = false;
		}
		else
		{
			if(isLastClicked[4]) Client.SendGamePadKey(getContext(), false, 4);
			isLastClicked[4] = isClicked[4] = false;
			if(isLastClicked[5]) Client.SendGamePadKey(getContext(), false, 5);
			isLastClicked[5] = isClicked[5] = false;
			if(isLastClicked[6]) Client.SendGamePadKey(getContext(), false, 6);
			isLastClicked[6] = isClicked[6] = false;
			if(isLastClicked[7]) Client.SendGamePadKey(getContext(), false, 7);
			isLastClicked[7] = isClicked[7] = false;
			if(isLastClicked[8]) Client.SendGamePadKey(getContext(), false, 8);
			isLastClicked[8] = isClicked[8] = false;
			if(isLastClicked[9]) Client.SendGamePadKey(getContext(), false, 9);
			isLastClicked[9] = isClicked[9] = false;	
		}
	}
	
	private void drawScreen()
	{
		Canvas canvas = getHolder().lockCanvas();
		draw(canvas);
		getHolder().unlockCanvasAndPost(canvas);
	}
	
	@Override
	public void draw(Canvas canvas)
	{
		Paint paint = new Paint();
		
		paint.setDither(false);

		// Screen clear
		paint.setColor(0xff000000);
		canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
		
		// Joystick Back
		paint.setColor(0xffaaaaaa);
		canvas.drawCircle(130, 130, 90, paint);

		// Joystick Bar
		paint.setColor(0xffeeeeee);
		canvas.drawCircle(130 + circleX, 130 + circleY, 80, paint);
		paint.setColor((circleIndex != -1) ? 0xffaaaaaa : 0xffcccccc);
		canvas.drawCircle(130 + circleX, 130 + circleY, 60, paint);
		
		// Button A, B
		paint.setColor(0xffeeeeee);
		canvas.drawCircle(620, 230, 50, paint);
		paint.setColor((isClicked[4]) ? 0xffaaaaaa : 0xffcccccc);
		canvas.drawCircle(620, 230, 40, paint);
		paint.setColor(0xffeeeeee);
		canvas.drawCircle(730, 180, 50, paint);
		paint.setColor((isClicked[5]) ? 0xffaaaaaa : 0xffcccccc);
		canvas.drawCircle(730, 180, 40, paint);

		// Button X, Y
		paint.setColor(0xffeeeeee);
		canvas.drawCircle(560, 130, 50, paint);
		paint.setColor((isClicked[6]) ? 0xffaaaaaa : 0xffcccccc);
		canvas.drawCircle(560, 130, 40, paint);
		paint.setColor(0xffeeeeee);
		canvas.drawCircle(670, 80, 50, paint);
		paint.setColor((isClicked[7]) ? 0xffaaaaaa : 0xffcccccc);
		canvas.drawCircle(670, 80, 40, paint);
		
		// Button Select
		paint.setColor(0xffeeeeee);
		canvas.drawRect(250, 180, 350, 240, paint);
		paint.setColor((isClicked[8]) ? 0xffaaaaaa : 0xffcccccc);
		canvas.drawRect(260, 190, 340, 230, paint);

		// Button Start
		paint.setColor(0xffeeeeee);
		canvas.drawRect(380, 180, 480, 240, paint);
		paint.setColor((isClicked[9]) ? 0xffaaaaaa : 0xffcccccc);
		canvas.drawRect(390, 190, 470, 230, paint);
	}
}
