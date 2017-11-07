package hcmute.it.danpham.tlcn_v1;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ConstraintLayout layoutMain;
    LinearLayout layoutCauHoi;
    Animation animationSlidetoLeft,animationSlidefromRight,animationSlidefromLeft;

    boolean clickLayoutMain=false;
    boolean clickDapAn=false;


    Button btnBatDau,btnA,btnB,btnC,btnD;
    TextView tvQuestion;
    int causo=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnBatDau.setOnClickListener(this);
        btnA.setOnClickListener(this);

    }

    private void addControls() {
        tvQuestion= (TextView) findViewById(R.id.tvQuestion);
        tvQuestion.setText("Câu số "+causo);

        btnBatDau= (Button) findViewById(R.id.btnBatDau);
        btnA= (Button) findViewById(R.id.btnA);
        btnB= (Button) findViewById(R.id.btnB);
        btnC= (Button) findViewById(R.id.btnC);
        btnD= (Button) findViewById(R.id.btnD);

        layoutMain = (ConstraintLayout) findViewById(R.id.layoutMain);
        layoutCauHoi= (LinearLayout) findViewById(R.id.layoutCauHoi);
        animationSlidetoLeft=  AnimationUtils.loadAnimation(this, R.anim.slide_to_left);
        animationSlidefromRight=AnimationUtils.loadAnimation(this,R.anim.slide_from_right);
        animationSlidefromLeft=AnimationUtils.loadAnimation(this,R.anim.slide_from_left);




    }

    @Override
    public void onClick(final View view) {
        switch(view.getId())
        {

            case R.id.btnBatDau:
                //
                if(!clickLayoutMain) {
                    clickLayoutMain=true;
                    layoutMain.startAnimation(animationSlidetoLeft);
                    layoutMain.setVisibility(View.GONE);
                    layoutCauHoi.setVisibility(View.VISIBLE);
                    layoutCauHoi.startAnimation(animationSlidefromRight);


                }
                break;
            case R.id.btnA:
                if(!clickDapAn) {
                    view.setBackgroundResource(R.drawable.custom_btn_selected);
                    new CountDownTimer(4000, 4000) {
                        @Override
                        public void onTick(long l) {

                        }

                        @Override
                        public void onFinish() {
                            new CountDownTimer(5000, 500) {
                                boolean green=false;
                                @Override

                                public void onTick(long l) {
                                    if (!green){
                                        green=true;
                                        view.setBackgroundResource(R.drawable.custom_right_result);
                                    }
                                    else    {
                                        view.setBackgroundResource(R.drawable.custom_btn_selected);
                                        green=false;
                                    }
                                }

                                @Override
                                public void onFinish() {
                                    layoutCauHoi.startAnimation(animationSlidetoLeft);
                                    //
                                    layoutCauHoi.setVisibility(View.GONE);
                                    causo++;
                                    tvQuestion.setText("Câu số " + causo);

                                    layoutCauHoi.setVisibility(View.VISIBLE);

                                    layoutCauHoi.startAnimation(animationSlidefromRight);
                                    btnA.setBackgroundResource(R.drawable.custom_btn);
                                }
                            }.start();
                        }
                    }.start();

                }
                break;
        }

    }



}
