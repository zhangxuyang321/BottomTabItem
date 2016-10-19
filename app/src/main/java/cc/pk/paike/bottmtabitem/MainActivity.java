package cc.pk.paike.bottmtabitem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.xyz.tabitem.BottmTabItem;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private BottmTabItem tab1;
    private BottmTabItem tab2;
    private BottmTabItem tab3;
    private BottmTabItem tab4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottmTabItem tab1 = (BottmTabItem) findViewById(R.id.tab1);
        tab2 = (BottmTabItem) findViewById(R.id.tab2);
        tab3 = (BottmTabItem) findViewById(R.id.tab3);
        tab4 = (BottmTabItem) findViewById(R.id.tab4);

        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        tab4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab1:
                changeState(0);
                break;
            case R.id.tab2:
                changeState(1);
                break;
            case R.id.tab3:
                changeState(2);
                break;
            case R.id.tab4:
                changeState(3);
                break;
        }
    }
    private void changeState(int flag) {
        tab1.setSelectState(0 == flag);//true 选中状态 false未选中状态
        tab2.setSelectState(1 == flag);
        tab3.setSelectState(2 == flag);
        tab4.setSelectState(3 == flag);
    }
}
