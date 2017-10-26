package com.example.chenjipayne.mikuweather;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {
    private int count;
    private boolean quit;
    private MyBinder binder = new MyBinder();

    @Override
    public IBinder onBind(Intent intent) {
        System.out.print("Service is Binded");
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.print("Service is Created");
        new Thread(){
            @Override
            public void run() {
                while (!quit){
                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e)
                    {

                    }
                    count++;
                }
            }
        }.start();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.print("Service is Unbinded");
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.quit = true;
        System.out.print("Service is Destroyed");
    }

    public class MyBinder extends Binder{
        public int getCount(){
            return count;
        }
    }
}
