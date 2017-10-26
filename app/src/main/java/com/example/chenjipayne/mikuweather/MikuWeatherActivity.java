package com.example.chenjipayne.mikuweather;

import android.app.Service;
import android.bluetooth.BluetoothClass;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Locale;

public class MikuWeatherActivity extends AppCompatActivity implements WeatherSearch.OnWeatherSearchListener{

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Calendar c = Calendar.getInstance();
    private int year = c.get(Calendar.YEAR);
    private int month = c.get(Calendar.MONTH);
    private int day = c.get(Calendar.DAY_OF_MONTH);
    private int hour = c.get(Calendar.HOUR_OF_DAY);
    private int minute = c.get(Calendar.MINUTE);
    private String week = String.valueOf(c.get(Calendar.DAY_OF_WEEK));

    private TextView tvcity,tvhumidity,tvwind,tvtemperature,tvdate,tvweather,tvgettime;
    private ImageView imweather;
    private ImageButton ibLocation;

    //final String tag = MainActivity.class.getSimpleName();
    private String cityString;
    private String weathername;
    private String temperature;
    private String wind;
    private String gettime;
    private String humidity;

    Weather weather = new Weather();

    /******************************************************************************************/
    MyService.MyBinder binder;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            System.out.print("--Service Connected--");
            binder = (MyService.MyBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            System.out.print("--Service Disconnected--");
        }
    };
    /******************************************************************************************/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.miku_weather);

        preferences = getSharedPreferences("city",MODE_PRIVATE);
        cityString = preferences.getString("city",null);

        ibLocation = (ImageButton) findViewById(R.id.ibLocation);
        tvcity = (TextView) findViewById(R.id.tvcity);
        tvhumidity = (TextView) findViewById(R.id.tvhumidity);
        tvwind = (TextView) findViewById(R.id.tvwind);
        tvtemperature = (TextView) findViewById(R.id.tvtemperature);
        tvdate = (TextView) findViewById(R.id.tvdate);
        tvweather = (TextView) findViewById(R.id.tvweather);
        tvgettime = (TextView) findViewById(R.id.tvgettime);
        imweather = (ImageView) findViewById(R.id.imweather);


        /******************************************************************************************/
        final Intent intent = new Intent(this, MyService.class);
        bindService(intent,conn, Service.BIND_AUTO_CREATE);

        unbindService(conn);
        /******************************************************************************************/




        WeatherSearchQuery weatherQuery = new WeatherSearchQuery(cityString, WeatherSearchQuery.WEATHER_TYPE_LIVE);
        WeatherSearch weatherSearch = new WeatherSearch(MikuWeatherActivity.this);
        weatherSearch.setQuery(weatherQuery);
        weatherSearch.setOnWeatherSearchListener(MikuWeatherActivity.this);
        weatherSearch.searchWeatherAsyn();
        Log.e("MainActivity", "==="+sHA1(getApplication()));

        xiahuaxian(tvwind);
        xiahuaxian(tvhumidity);

        ibLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MikuWeatherActivity.this,ResetCityActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == 2){
            String content = data.getStringExtra("data");
            cityString = content;
            WeatherSearchQuery weatherQuery = new WeatherSearchQuery(cityString, WeatherSearchQuery.WEATHER_TYPE_LIVE);
            WeatherSearch weatherSearch = new WeatherSearch(MikuWeatherActivity.this);
            weatherSearch.setQuery(weatherQuery);
            weatherSearch.setOnWeatherSearchListener(MikuWeatherActivity.this);
            weatherSearch.searchWeatherAsyn();
            editor = preferences.edit();
            editor.putString("city",cityString);
            editor.commit();
        }
    }


    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult localWeatherLiveResult, int i) {
        if (i == 1000) {
            LocalWeatherLive liveWeather = localWeatherLiveResult.getLiveResult();
            Log.e("MainActivity",liveWeather.getReportTime()+"发布");


            weathername=liveWeather.getWeather();
            temperature=liveWeather.getTemperature()+"°";
            humidity="湿度  :  "+liveWeather.getHumidity()+"%";
            wind="风势  :  "+liveWeather.getWindDirection() +"风 "+liveWeather.getWindPower()+"级";
            gettime=liveWeather.getReportTime()+"发布";

            weather.setCityname(cityString);
            weather.setWeathername(weathername);
            weather.setTemperature(temperature);
            weather.setHumidity(humidity);
            weather.setWind(wind);

            //此处传参************************************************************
            tvcity.setText(cityString);
            tvtemperature.setText(temperature);
            tvhumidity.setText(humidity);
            tvwind.setText(wind);
            tvgettime.setText(gettime);


            if("多云".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_wind);
                tvweather.setText("多云");
            }else if("阴".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_overcast);
                tvweather.setText("阴");
            }else if("晴".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_sun);
                tvweather.setText("晴");
            }else if("晴".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_fog);
                tvweather.setText("晴");
            }else if("小雪".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_snow);
                tvweather.setText("小雪");
            }else if("大雪".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_snow);
                tvweather.setText("大雪");
            }else if("中雪".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_snow);
                tvweather.setText("中雪");
            }else if("暴雪".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_snow);
                tvweather.setText("暴雪");
            }else if("阵雪".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_snow);
                tvweather.setText("阵雪");
            }else if("小雪-中雪".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_snow);
                tvweather.setText("小雪");
            }else if("中雪-大雪".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_snow);
                tvweather.setText("中雪");
            }else if("大雪-暴雪".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_snow);
                tvweather.setText("大雪");
            }else if("雨夹雪".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_rainsnow);
                tvweather.setText("雨夹雪");
            }else if("小雨".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_rain);
                tvweather.setText("小雨");
            }else if("中雨".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_rain);
                tvweather.setText("中雨");
            }else if("大雨".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_bigrain);
                tvweather.setText("大雨");
            }else if("暴雨".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_bigrain);
                tvweather.setText("暴雨");
            }else if("大暴雨".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_bigrain);
                tvweather.setText("大暴雨");
            }else if("特大暴雨".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_bigrain);
                tvweather.setText("特暴雨");
            }else if("小雨-中雨".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_rain);
                tvweather.setText("小雨");
            }else if("中雨-大雨".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_rain);
                tvweather.setText("中雨");
            }else if("大雨-暴雨".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_bigrain);
                tvweather.setText("大雨");
            }else if("暴雨-大暴雨".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_bigrain);
                tvweather.setText("大暴雨");
            }else if("大暴雨-特大暴雨".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_bigrain);
                tvweather.setText("特暴雨");
            }else if("冻雨".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_freezingrain);
                tvweather.setText("冻雨");
            }else if("阵雨".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_shower);
                tvweather.setText("阵雨");
            }else if("雷阵雨".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_thunderrain);
                tvweather.setText("雷阵雨");
            }else if("雷阵雨并伴有冰雹".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_thunderrainandhail);
                tvweather.setText("雷冰雨");
            }else if("弱高吹雪".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_snow);
                tvweather.setText("高吹雪");
            }else if("飑".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_squall);
                tvweather.setText("飑");
            }else if("龙卷风".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_tornado);
                tvweather.setText("龙卷风");
            }else if("霾".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_smog);
                tvweather.setText("霾");
            }else if("轻霾".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_smog);
                tvweather.setText("轻霾");
            }else if("沙尘暴".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_sandstorm);
                tvweather.setText("沙尘暴");
            }else if("强沙尘暴".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_sandstorm);
                tvweather.setText("大沙暴");
            }else if("扬沙".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_storm);
                tvweather.setText("扬沙");
            }else if("浮尘".equals(weathername)){
                imweather.setImageResource(R.drawable.tenki_storm);
                tvweather.setText("浮尘");
            }

            Log.e("MainActivity",liveWeather.getWeather()+" ="+liveWeather.getTemperature()+"°"+liveWeather.getWindDirection()
                    +"风     "+liveWeather.getWindPower()+"级"+" 湿度         "+liveWeather.getHumidity()+"%");
            if("1".equals(week)){
                week ="星期天";
            }else if("2".equals(week)){
                week ="星期一";
            }else if("3".equals(week)){
                week ="星期二";
            }else if("4".equals(week)){
                week ="星期三";
            }else if("5".equals(week)){
                week ="星期四";
            }else if("6".equals(week)){
                week ="星期五";
            }else if("7".equals(week)){
                week ="星期六";
            }
            tvdate.setText(week+" "+month+"/"+day);
        } else {
            Log.e("", "查询天气失败");
        }
    }

    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {

    }
    public String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void xiahuaxian(TextView textView){
        textView.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
        textView.getPaint().setAntiAlias(true);//抗锯齿
        textView.setText(Html.fromHtml(""+"content"+""));
    }
}
