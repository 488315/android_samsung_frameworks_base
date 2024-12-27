package com.android.server.notification.sec;

import android.content.Context;

import java.text.SimpleDateFormat;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
