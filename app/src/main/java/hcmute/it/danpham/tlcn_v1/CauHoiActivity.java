package hcmute.it.danpham.tlcn_v1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class CauHoiActivity extends AppCompatActivity {

    Button btnA,btnB,btnC,btnD;
    Integer causo;
    boolean cliked=false;
    TextView tvQuestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cau_hoi);



    }
}
