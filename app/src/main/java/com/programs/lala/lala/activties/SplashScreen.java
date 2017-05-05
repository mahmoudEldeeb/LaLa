package com.programs.lala.lala.activties;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.programs.lala.lala.R;

import static android.R.attr.duration;
import static com.programs.lala.lala.fragments.login_frag.context;

public class SplashScreen extends AppCompatActivity {
      ImageView image0;
      Animation fade0;
      TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        textView = (TextView) findViewById(R.id.splash_text);

        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/Frijole-Regular.ttf");
        textView.setTypeface(type);
        image0 = (ImageView) findViewById(R.id.splash_image);
         fade0 = AnimationUtils.loadAnimation(this, R.anim.fade_in_enter);
        image0.startAnimation(fade0);

        fade0.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                startActivity(new Intent(SplashScreen.this, base_activity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                overridePendingTransition(R.anim.push_up_enter, R.anim.push_up_exit);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });



    }
}

