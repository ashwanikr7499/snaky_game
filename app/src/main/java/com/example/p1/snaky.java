package com.example.p1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


public class snaky extends View implements  Runnable
{


    class OnSwipeTouchListener implements OnTouchListener
    {

        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener (Context ctx)
        {
            gestureDetector = new GestureDetector(getContext(), new GestureListener());
        }
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            return gestureDetector.onTouchEvent(event);
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener
        {

            private static final int SWIPE_THRESHOLD = 50;
            private static final int SWIPE_VELOCITY_THRESHOLD = 50;

            @Override
            public boolean onDown(MotionEvent e)
            {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
            {
                boolean result = false;
                try
                {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY))
                    {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD)
                        {
                            if (diffX > 0)
                                onSwipeRight();
                            else
                                onSwipeLeft();

                            result = true;
                        }
                    }
                    else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD)
                    {
                        if (diffY > 0)
                            onSwipeBottom();
                        else
                            onSwipeTop();

                        result = true;
                    }
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
                return result;
            }
        }

        public void onSwipeRight() {
        }

        public void onSwipeLeft() {
        }

        public void onSwipeTop() {
        }

        public void onSwipeBottom() {
        }
    }

    int parts,side,steptime=100;
    int xp[] = new int[1000],yp[] = new int[1000];
    int sx[][]=new int[10][1000],sy[][]=new int[10][1000];
    int stones[] = new int [10];
    int status; //1=just started,2 = running, 3=paused, 4 =game over, 5=winner,6=help
    int fx,fy;
    int score;
    Thread d;
    int levelsset;
    int level;
    int choice; //continue, main menu, new game,settings,help,quit
    int direction; //1=right, 2 = left, 3=up, 4=down
    private int screenWidth;
    private int screenHeight;
    private Handler handler;

    private void newGameSetup()
    {

        int i,j;
        parts=4;

        for(i = side*parts,j=0;j<3;j++,i-=side)
        {
            xp[j]=i;
            yp[j]=100;
        }


        score = 0;
        levelsset = 0;
        fx=300;
        fy=400;

        level=0;
        direction =1;

    }

    public snaky (Context context, AttributeSet attrs)
    {
        super(context,attrs);

        side = 20;
        status = 1;
        screenWidth = 720;
        screenHeight = 1000;
        choice = 1;

        Thread d = new Thread (snaky.this);
        d.start();
        //setOnClickListener(snaky.this);

        newGameSetup();
        status=2;


        setOnTouchListener(new OnSwipeTouchListener(getContext())
        {
            public void onSwipeTop()
            {
                //Toast.makeText(getContext(), "top", Toast.LENGTH_SHORT).show();
                keyPressed(3);
            }
            public void onSwipeRight()
            {
                //Toast.makeText(getContext(), "right", Toast.LENGTH_SHORT).show();
                keyPressed(1);
            }
            public void onSwipeLeft()
            {
                //Toast.makeText(getContext(), "left", Toast.LENGTH_SHORT).show();
                keyPressed(2);
            }
            public void onSwipeBottom()
            {
                //Toast.makeText(getContext(), "bottom", Toast.LENGTH_SHORT).show();
                keyPressed(4);
            }

        });
    }

    public void setlevels()
    {
        int i,j;


//stones for level 0
        stones[0]=10;
        for(i=0,j=160;i<stones[0];j+=side,i++)
        {
            sx[0][i]=j;
            sy[0][i]=260;
        }

//stones for level 1
        stones[1]=20;
        for(i=0,j=160;i<stones[1]/2;j+=side,i++)
        {
            sx[1][i]=j;
            sy[1][i]=260;
        }

        for(i=10,j=160;i<stones[1];j+=side,i++)
        {
            sx[1][i]=j;
            sy[1][i]=360;
        }

//stones for level 2
        stones[2]=0;
        for(i=0,j=0;j<screenHeight;j+=side,i++)
        {
            sx[2][i]=((screenWidth/2)/side)*side;
            sy[2][i]=j;
            stones[2]++;
        }
        for(j=0;j<screenWidth;j+=side,i++)
        {
            sx[2][i]=j;
            sy[2][i]=((screenWidth/2)/side)*side;
            stones[2]++;
        }

//level 3
        stones[3]=0;
        for(i=0,j=0;j<screenWidth;j+=side,i++)
        {
            sx[3][i]=j;
            sy[3][i]=0;
            stones[3]++;
        }
        for(j=0;j<screenHeight;j+=side,i++)
        {
            sx[3][i]=0;
            sy[3][i]=j;
            stones[3]++;
        }
        for(j=0;j<screenWidth;j+=side,i++)
        {
            sx[3][i]=j;
            sy[3][i]=screenHeight-side;
            stones[3]++;
        }

        for(j=0;j<screenHeight;j+=side,i++)
        {
            sx[3][i]=screenWidth-side;
            sy[3][i]=j;
            stones[3]++;
        }

//level 4
//stones for level 4
        stones[4]=0;
        for(i=0,j=120;j<380;j+=side,i++)
        {
            sx[4][i]=((screenWidth/2)/side)*side;
            sy[4][i]=j;
            stones[4]++;
        }
        for(j=120;j<380;j+=side,i++)
        {
            sx[4][i]=j;
            sy[4][i]=((screenWidth/2)/side)*side;
            stones[4]++;
        }

//level 5
//stones for level 5

        stones[5]=0;
        for(i=0,j=0;j<screenWidth;j+=side,i++)
        {
            sx[5][i]=j;
            sy[5][i]=((screenHeight/2)/side)*side;
            stones[5]++;
        }
        for(j=2*side;j<screenHeight-(2*side);j+=side,i++)
        {
            sx[5][i]=((screenWidth/3)/side)*side;
            sy[5][i]=j;
            stones[5]++;
        }
        for(j=2*side;j<screenHeight-(2*side);j+=side,i++)
        {
            sx[5][i]=((screenWidth*2/3)/side)*side;
            sy[5][i]=j;
            stones[5]++;
        }

//stones for level 6
        stones[6]=0;
        for(i=0,j=((screenWidth/3)/side)*side;j<screenWidth*2/3;j=j+side,i++)
        {
            sx[6][i]=j;
            sy[6][i]=((screenHeight/4)/side)*side;
            stones[6]++;
        }
        for(j=((screenWidth/3)/side)*side;j<screenWidth*2/3;j=j+side,i++)
        {
            sx[6][i]=j;
            sy[6][i]=((screenHeight*3/4)/side)*side;
            stones[6]++;
        }


        for(j=((screenWidth/3)/side)*side;j<screenWidth*2/3;j=j+side,i++)
        {
            sx[6][i]=j;
            sy[6][i]=((screenHeight/2)/side)*side;
            stones[6]++;
        }

        for(j=((screenHeight/4)/side)*side;j<screenHeight/2;j=j+side,i++)
        {
            sx[6][i]=((screenWidth/3)/side)*side;
            sy[6][i]=j;
            stones[6]++;
        }

        for(j=((screenHeight/2)/side)*side;j<screenHeight*3/4;j=j+side,i++)
        {
            sx[6][i]=((screenWidth*2/3)/side)*side;
            sy[6][i]=j;
            stones[6]++;
        }

//level 7
        stones[7]=0;
        for(i=0,j=120;j<380;j+=side,i++)
        {
            sx[7][i]=((screenWidth/2)/side)*side;
            sy[7][i]=j;
            stones[7]++;
        }
        for(j=120;j<380;j+=side,i++)
        {
            sx[7][i]=j;
            sy[7][i]=((screenWidth/2)/side)*side;
            stones[7]++;
        }

//left
        for(j=0;j<side*5;j+=side,i++)
        {
            sx[7][i]=j;
            sy[7][i]=0;
            stones[7]++;
        }
        for(j=0;j<side*5;j+=side,i++)
        {
            sx[7][i]=0;
            sy[7][i]=j;
            stones[7]++;
        }

//right
        for(j=0;j<side*5;j+=side,i++)
        {
            sx[7][i]=(screenWidth-side*5)+j;
            sy[7][i]=0;
            stones[7]++;
        }
        for(j=0;j<side*5;j+=side,i++)
        {
            sx[7][i]=screenWidth-side;
            sy[7][i]=j;
            stones[7]++;
        }

//left
        for(j=0;j<side*5;j+=side,i++)
        {
            sx[7][i]=j;
            sy[7][i]=screenHeight-side;
            stones[7]++;
        }
        for(j=0;j<side*5;j+=side,i++)
        {
            sx[7][i]=0;
            sy[7][i]=screenHeight-5*side+j;
            stones[7]++;
        }


//right bottom
        for(j=0;j<side*5;j+=side,i++)
        {
            sx[7][i]=(screenWidth-side*5)+j;
            sy[7][i]=screenHeight-side;
            stones[7]++;
        }
        for(j=0;j<side*5;j+=side,i++)
        {
            sx[7][i]=screenWidth-side;
            sy[7][i]=(screenHeight-side*5)+j;
            stones[7]++;
        }

//level 8
        stones[8]=0;
        int k;
        for(i=0,j=((screenWidth/4)/side)*side+side,k=((screenHeight/4)/side)*side+side;j<screenWidth*3/4;j+=side,k+=side,i++)
        {
            sx[8][i]=j;
            sy[8][i]=k;
            stones[8]++;
        }

        for(j=((screenWidth*3/4)/side)*side,k=((screenHeight/4)/side)*side+side;j>=screenWidth/4;j-=side,k+=side,i++)
        {
            sx[8][i]=j;
            sy[8][i]=k;
            stones[8]++;
        }

//stones for level 9
        stones[9]=0;
        i=0;
        for(k=1;k<=5;k+=2)
        {
            for(j=((screenWidth*k/7)/side)*side;j<=screenWidth*(k+1)/7-side;j+=side,i++)
            {
                sx[9][i]=j;
                sy[9][i]=((screenHeight/4)/side)*side;
                stones[9]++;
            }
        }
        for(k=1;k<=5;k+=2)
        {
            for(j=((screenWidth*k/7)/side)*side;j<=screenWidth*(k+1)/7-side;j+=side,i++)
            {
                sx[9][i]=j;
                sy[9][i]=((screenHeight*3/4)/side)*side;
                stones[9]++;
            }
        }

        for(j=((screenWidth/7)/side)*side;j<=screenWidth*5/7;j+=(2*((screenWidth/7)/side)*side+side))
        {
            for(k=((screenHeight/4)/side)*side;k<=((screenHeight*3/4)/side)*side;k+=side,i++)
            {
                sx[9][i]=j;
                sy[9][i]=k;
                stones[9]++;
            }
        }

        levelsset = 1;

    }




        @Override
    protected void onDraw(Canvas g) {
            int i;
            //setSize(screenWidth,screenHeight);
            Paint paint = new Paint();
            int colblack = 0xff000000;
            paint.setColor(colblack);
            g.drawRect(0, 0, screenWidth, screenHeight, paint);
            if (status == 2) //running
            {
                int colgreen = 0xff8000ff;
                Paint p2 = new Paint();
                p2.setColor(colgreen);
                g.drawOval(fx, fy, fx + side, fy + side, p2);
                int col3 = 0xffffffff;
                Paint p3 = new Paint();
                p3.setColor(col3);
                //g.setColor(new Color(0,255,255));
                for (i = 0; i < stones[level]; i++) {
                    g.drawRect(sx[level][i], sy[level][i], sx[level][i] + side, sy[level][i] + side, p3);
                }

                for (i = 0; i < parts; i++) {

                  int col4 = 0xffff0000;
                    Paint p4 = new Paint();
                    p4.setColor(col4);
                    g.drawRect(xp[i], yp[i], xp[i] + side, yp[i] + side, p4);

                }

                int col5 = 0xffffffff;
                Paint p4 = new Paint();
                p4.setColor(col5);
                p4.setTextSize(30);
                g.drawText("Level: " + (level + 1), screenWidth - 100, 20, p4);
                g.drawText("Score: " + score, screenWidth - 300, 20, p4);
            }
        }
   // public void n
    public void invalidate()
    {
        handler.post(new Runnable() {
            @Override
            public void run() {
                snaky.super.invalidate();
            }
        });
    }
    public void run()
    {
        if(levelsset == 0)
            setlevels();
        int i,f;
        while(true)
        {


            Log.d("Snaky","Running...");
            try{
                d.sleep(steptime);
            }catch(Exception e){}

            if(status == 2) //running
            {
                for(i = parts-1;i>=1;i--)
                {
                    xp[i]=xp[i-1];
                    yp[i] = yp[i-1];
                }
                switch(direction)
                { //direction of snak after rolling
                    case 1:
                        xp[0]=((xp[0]+side)>screenWidth-side)?0:xp[0]+side;
                        break;
                    case 2:
                        xp[0]=((xp[0]-side)<0)?(screenWidth/side)*side-side:xp[0]-side;
                        break;
                    case 3:
                        yp[0]=((yp[0]-side)<0)?(screenHeight/side)*side-side:yp[0]-side;
                        break;
                    case 4:
                        yp[0]=((yp[0]+side)>screenHeight-side)?0:yp[0]+side;
                        break;
                }
                if(xp[0]==fx && yp[0]==fy)
                {
                    score = score + 10*(level+1);
                    steptime-=5;

                    xp[parts]=xp[parts-1];
                    yp[parts]=yp[parts-1];
                    parts++;
                    if(parts==12)
                    {

                        try
                        {
                            d.sleep(2000);
                        }
                        catch(Exception e)
                        {
                        }
                        fx=screenWidth+1;
                        fy=screenHeight+1;
                        level++;

                        if(level==10)
                        {
                            status = 5; // winner

                            try
                            {
                                d.sleep(2000);
                            }catch(Exception e){}
                            invalidate();
                            return;
                        }
                        direction=1;
                        parts=4;
                        steptime=100;
                        int j;
                        for(i = side*parts,j=0;j<parts;j++,i-=side)
                        {
                            xp[j]=i;
                            yp[j]=100;
                        }
                        invalidate();
                        try
                        {
                            d.sleep(2000);
                        }catch(Exception e){}


                    }

                    int match;
                    do
                    {
                        fx= (int)Math.floor(Math.random()*1000%(screenWidth-side));
                        fy= (int)Math.floor(Math.random()*1000%(screenHeight-side));
                        fx = (fx%side==0)?fx:fx-(fx%side);
                        fy = (fy%side==0)?fy:fy-(fy%side);
                        fx =(fx==0)?fx=side:fx;
                        fy = (fy==0)?fx=side:fy;

                        match=0;
                        for(i=0;i<stones[level];i++)
                        {
                            if(fx==sx[level][i] && fy == sy[level][i])
                            {
                                match=1;
                                break;
                            }
                        }
                        for(i=0;i<parts;i++)
                        {
                            if(fx==xp[i] && fy == yp[i])
                            {
                                match=1;
                                break;
                            }
                        }
                    }while(match!=0);
                }
                for(i=1;i<parts;i++)
                {
                    if(xp[0]==xp[i] && yp[0] == yp[i])
                    {

                        status = 4;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(),"Game Over... Restart Application to play again",Toast.LENGTH_LONG).show();
                            }
                        });

                        //Toast.makeText(getContext(),"Game Over... Restart Application to play again",Toast.LENGTH_LONG).show();
                        try{
                            d.sleep(2000);}catch(Exception e){}
                        invalidate();
                        return;
                    }
                }

                for(i=0;i<stones[level];i++)
                {
                    if(xp[0]==sx[level][i] && yp[0] == sy[level][i])
                    {

                        try{
                            d.sleep(2000);}catch(Exception e){}
                        status = 4;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(),"Game Over... Restart Application to play again",Toast.LENGTH_LONG).show();
                            }
                        });
                        //Toast.makeText(getContext(),"Game Over... Restart Application to play again",Toast.LENGTH_LONG).show();
                        invalidate();
                        return;
                    }
                }
                invalidate();
            }
        }
    }
    public void keyPressed (int newdirection)
    {
                switch (newdirection)
                {
                    case 1:
                        if (!(direction == 2))
                            direction = 1;
                        break;
                    case 2:
                        if (!(direction == 1))
                            direction = 2;
                        break;
                    case 3:
                        if (!(direction == 4))
                            direction = 3;
                        break;
                    case 4:
                        if (!(direction == 3))
                            direction = 4;
                        break;

                }

            invalidate();
    }



    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
