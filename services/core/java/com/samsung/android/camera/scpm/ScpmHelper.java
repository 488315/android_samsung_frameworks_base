package com.samsung.android.camera.scpm;

import android.content.Context;
import android.os.Build;

public final class ScpmHelper {
    public static final String APP_VERSION;
    public static final boolean DEBUG;
    public final Context mContext;
    public final ScpmReceiver.AnonymousClass1 mScpmCallback;
    public String mToken;

    static {
        String str = Build.TYPE;
        DEBUG = str.equals("eng") || str.equals("userdebug");
        APP_VERSION = Build.VERSION.RELEASE;
    }

    public ScpmHelper(Context context, ScpmReceiver.AnonymousClass1 anonymousClass1) {
        this.mContext = context;
        this.mScpmCallback = anonymousClass1;
    }
}
