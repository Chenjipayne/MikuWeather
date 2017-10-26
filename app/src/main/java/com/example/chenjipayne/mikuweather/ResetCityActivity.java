package com.example.chenjipayne.mikuweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ResetCityActivity extends AppCompatActivity {

    private Button btsave,bt1,bt2,bt3,bt4,bt5,bt6,bt7,bt8,bt9,bt10,bt11,bt12,bt13,bt14,bt15,bt16,bt17,bt18,bt19,bt20,bt21,bt22,bt23,bt24;
    private String content = "城市";
    public EditText cityname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_city);

        cityname = (EditText)findViewById(R.id.cityname);
        btsave = (Button)findViewById(R.id.btsave);
        bt1 = (Button)findViewById(R.id.button1);
        bt2 = (Button)findViewById(R.id.button2);
        bt3 = (Button)findViewById(R.id.button3);
        bt4 = (Button)findViewById(R.id.button4);
        bt5 = (Button)findViewById(R.id.button5);
        bt6 = (Button)findViewById(R.id.button6);
        bt7 = (Button)findViewById(R.id.button7);
        bt8 = (Button)findViewById(R.id.button8);
        bt9 = (Button)findViewById(R.id.button9);
        bt10 = (Button)findViewById(R.id.button10);
        bt11 = (Button)findViewById(R.id.button11);
        bt12 = (Button)findViewById(R.id.button12);
        bt13 = (Button)findViewById(R.id.button13);
        bt14 = (Button)findViewById(R.id.button14);
        bt15 = (Button)findViewById(R.id.button15);
        bt16 = (Button)findViewById(R.id.button16);
        bt17 = (Button)findViewById(R.id.button17);
        bt18 = (Button)findViewById(R.id.button18);
        bt19 = (Button)findViewById(R.id.button19);
        bt20 = (Button)findViewById(R.id.button20);
        bt21 = (Button)findViewById(R.id.button21);
        bt22 = (Button)findViewById(R.id.button22);
        bt23 = (Button)findViewById(R.id.button23);
        bt24 = (Button)findViewById(R.id.button24);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityname.setText("北京");
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityname.setText("天津");
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityname.setText("上海");
            }
        });

        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityname.setText("重庆");
            }
        });

        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityname.setText("郑州");
            }
        });

        bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityname.setText("武汉");
            }
        });

        bt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityname.setText("长沙");
            }
        });

        bt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityname.setText("南京");
            }
        });

        bt9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityname.setText("南昌");
            }
        });

        bt10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityname.setText("沈阳");
            }
        });

        bt11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityname.setText("长春");
            }
        });

        bt12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityname.setText("西安");
            }
        });

        bt13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityname.setText("成都");
            }
        });

        bt14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityname.setText("西宁");
            }
        });

        bt15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityname.setText("合肥");
            }
        });

        bt16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityname.setText("海口");
            }
        });

        bt17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityname.setText("广州");
            }
        });

        bt18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityname.setText("贵阳");
            }
        });

        bt19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityname.setText("杭州");
            }
        });

        bt20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityname.setText("福州");
            }
        });

        bt21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityname.setText("台北");
            }
        });

        bt22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityname.setText("昆明");
            }
        });

        bt23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityname.setText("南宁");
            }
        });

        bt24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityname.setText("兰州");
            }
        });

        btsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content = cityname.getText().toString();
                Intent data = new Intent();
                data.putExtra("data",content);
                setResult(2,data);
                //结束当前页面
                finish();
            }
        });

    }

}
