package com.android.systemui.dagger;

import android.content.Context;
import android.os.Handler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class NightDisplayListenerModule$Builder {
    public final Handler mBgHandler;
    public final Context mContext;
    public int mUserId = 0;

    public NightDisplayListenerModule$Builder(Context context, Handler handler) {
        this.mContext = context;
        this.mBgHandler = handler;
    }
}
