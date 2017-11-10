package hcmute.it.danpham.tlcn_v1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ConstraintLayout layoutMain;
    LinearLayout layoutCauHoi;
    Animation animationSlidetoLeft,animationSlidefromRight,animationSlidefromLeft;

    boolean clickLayoutMain=false;
    boolean clickDapAn=false;


    Button btnBatDau,btnA,btnB,btnC,btnD;
    TextView tvQuestion;
    int causo=1;
    String dapan="";
    String luachon="";


    String DATABASE_NAME="ALTPdb.sqlite";
    private static final String DB_PATH_SUFFIX="/databases/";
    SQLiteDatabase database=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //saoChepCSDL();

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnBatDau.setOnClickListener(this);
        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);
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


    //lần đầu chạy thì chạy 3 hàm này để kêt nối db
    private void saoChepCSDL(){

        File dbFile=getDatabasePath(DATABASE_NAME);

        //if(!dbFile.exists()){
            try{

                CopyDatabaseFromAsset();
                Toast.makeText(this,"copy thành công",Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                Toast.makeText(this,""+e.toString(),Toast.LENGTH_SHORT).show();
            }
       // }

    }

    private void CopyDatabaseFromAsset() {
        try{
            InputStream myInput=getAssets().open(DATABASE_NAME);
            String outFileName=layDuongDan();
            //kiem tra
            File f=new File(getApplicationInfo().dataDir+DB_PATH_SUFFIX);
            if(!f.exists()){
                f.mkdir();

            }

            OutputStream myOutput=new FileOutputStream(outFileName);
            //
            byte[] buffer=new byte[1024];
            int lenght;
            while((lenght=myInput.read(buffer))>0){
                myOutput.write(buffer,0,lenght);
            }
            //close stream
            myOutput.flush();
            myOutput.close();
            myInput.close();


        }catch (Exception ex){
            Log.e("",ex.toString());
        }
    }

    private String layDuongDan(){
        return getApplicationInfo().dataDir+DB_PATH_SUFFIX+DATABASE_NAME;
    }
    //

    private void hienThiCauHoi(){
        //mở csdl
        database=openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);

        Cursor cursor=database.rawQuery("select * from CauHoi where id="+causo,null);
        if(cursor.moveToNext()){
            tvQuestion.setText("Câu số "+causo+"\n"+cursor.getString(1));
            btnA.setText("A. "+cursor.getString(2));
            btnB.setText("B. "+cursor.getString(3));
            btnC.setText("C. "+cursor.getString(4));
            btnD.setText("D. "+cursor.getString(5));
            dapan=cursor.getString(6);

        }
        cursor.close();



    }
    private void xuLyChonDapAn(final View view){
        if(!clickDapAn) {
            clickDapAn=true;
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
                            if(luachon.equals(dapan)) {

                                if (!green) {
                                    green = true;
                                    view.setBackgroundResource(R.drawable.custom_right_result);
                                } else {
                                    view.setBackgroundResource(R.drawable.custom_btn_selected);
                                    green = false;
                                }
                            }
                            else
                            {

                                if (!green) {
                                    green = true;
                                    switch (dapan){
                                        case "A":
                                            btnA.setBackgroundResource(R.drawable.custom_right_result);
                                            break;
                                        case "B":
                                            btnB.setBackgroundResource(R.drawable.custom_right_result);
                                            break;
                                        case "C":
                                            btnC.setBackgroundResource(R.drawable.custom_right_result);
                                            break;
                                        case "D":
                                            btnD.setBackgroundResource(R.drawable.custom_right_result);
                                            break;


                                    }

                                } else {

                                    switch (dapan) {
                                        case "A":
                                            btnA.setBackgroundResource(R.drawable.custom_btn);
                                            break;
                                        case "B":
                                            btnB.setBackgroundResource(R.drawable.custom_btn);
                                            break;
                                        case "C":
                                            btnC.setBackgroundResource(R.drawable.custom_btn);
                                            break;
                                        case "D":
                                            btnD.setBackgroundResource(R.drawable.custom_btn);
                                            break;
                                    }
                                    green=false;
                                }
                            }
                        }

                        @Override
                        public void onFinish() {
                            layoutCauHoi.startAnimation(animationSlidetoLeft);
                            //
                            layoutCauHoi.setVisibility(View.GONE);
                            causo++;


                            layoutCauHoi.setVisibility(View.VISIBLE);
                            tvQuestion.setText("Câu số "+causo);
                            layoutCauHoi.startAnimation(animationSlidefromRight);
                            hienThiCauHoi();
                            btnA.setBackgroundResource(R.drawable.custom_btn);
                            btnB.setBackgroundResource(R.drawable.custom_btn);
                            btnC.setBackgroundResource(R.drawable.custom_btn);
                            btnD.setBackgroundResource(R.drawable.custom_btn);
                            clickDapAn=false;
                        }
                    }.start();
                }
            }.start();

        }
    }

    @Override
    public void onClick(final View view) {

        switch(view.getId())
        {

            case R.id.btnBatDau:
                //
                if(!clickLayoutMain) {

                    hienThiCauHoi();
                    clickLayoutMain=true;
                    layoutMain.startAnimation(animationSlidetoLeft);
                    layoutMain.setVisibility(View.GONE);
                    layoutCauHoi.setVisibility(View.VISIBLE);
                    layoutCauHoi.startAnimation(animationSlidefromRight);


                }
                break;
            case R.id.btnA:
                luachon="A";
                xuLyChonDapAn(btnA);

                break;
            case R.id.btnB:
                luachon="B";
                xuLyChonDapAn(btnB);
                break;
            case R.id.btnC:
                luachon="C";
                xuLyChonDapAn(btnC);
                break;
            case R.id.btnD:
                luachon="D";
                xuLyChonDapAn(btnD);
                break;
        }

    }



}
