package com.leonardo_soares_santos.chatbotlss.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.leonardo_soares_santos.chatbotlss.R;

/**
 * Created by Leonardo Soares on 02/05/18.
 * ra 816114026
 */

public class Splash extends AppCompatActivity {
    private TextView tv;
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tv = (TextView) findViewById(R.id.tv);
        iv=(ImageView)findViewById(R.id.iv);
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.alpha);
        tv.startAnimation(myanim);
        iv.startAnimation(myanim);
        final Intent i = new Intent(this,LoginActivity.class);
        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(4000);
                }catch (InterruptedException e){

                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }


            }





        };
        timer.start();
    }
}
