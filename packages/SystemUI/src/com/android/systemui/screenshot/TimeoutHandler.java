package com.android.systemui.screenshot;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.samsung.systemui.splugins.edgelightingplus.PluginEdgeLightingPlus;

public final class TimeoutHandler extends Handler {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    public int mDefaultTimeout;
    public Runnable mOnTimeout;

    public TimeoutHandler(Context context) {
        super(Looper.getMainLooper());
        this.mDefaultTimeout = PluginEdgeLightingPlus.VERSION;
        this.mContext = context;
        this.mOnTimeout = new TimeoutHandler$$ExternalSyntheticLambda0();
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        if (message.what != 2) {
            return;
        }
        this.mOnTimeout.run();
    }
}
