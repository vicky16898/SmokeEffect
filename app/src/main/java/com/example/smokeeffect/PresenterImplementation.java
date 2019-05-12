package com.example.smokeeffect;

import android.content.Context;
import android.util.Log;

public class PresenterImplementation implements Presenter {
    private Listener listener;
    private Context context;
    public PresenterImplementation(Context context){
        super();
        this.context = context;
        listener = new DrawingView(context);
    }
    @Override
    public void onClick() {
        Log.d("BUTTON RESPONSE", "RECEIVED");
        listener.clickHandle();
    }
}
