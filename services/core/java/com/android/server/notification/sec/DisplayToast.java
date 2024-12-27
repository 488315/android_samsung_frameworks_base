package com.android.server.notification.sec;

import android.content.Context;

import java.text.SimpleDateFormat;

public final class DisplayToast {
    public static final DisplayToast sLogMsg;
    public Context mContext;
    public String mMessage;
    public String mPackageName;
    public int mUid;
    public SimpleDateFormat sdfNow;

    static {
        DisplayToast displayToast = new DisplayToast();
        displayToast.sdfNow = new SimpleDateFormat("yy-MM-dd_HH:mm:ss");
        sLogMsg = displayToast;
    }
}
