package com.tianditu.nantong;

import android.app.Application;

import org.xutils.x;

/**
 * Created by chenjie on 2016/11/21.
 */
public class NTApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
        // 开启debug会影响性能
    }
}
