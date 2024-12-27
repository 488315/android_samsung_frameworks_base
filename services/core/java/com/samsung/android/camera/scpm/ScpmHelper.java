package com.samsung.android.camera.scpm;

import android.content.Context;
import android.os.Build;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
