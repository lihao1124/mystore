package com.bj.glz.firsttry.manager;

import android.content.Context;
import android.content.Intent;

import com.bj.glz.firsttry.activity.MainActivity;

/**
 * Created by lihao on 2017/10/30.
 */

public class IntentManager {

    public static void startMainActivity(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
