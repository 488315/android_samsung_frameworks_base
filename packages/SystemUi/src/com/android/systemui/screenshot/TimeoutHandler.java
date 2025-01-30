package com.android.systemui.screenshot;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.accessibility.AccessibilityManager;
import com.samsung.systemui.splugins.edgelightingplus.PluginEdgeLightingPlus;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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

    public final void resetTimeout() {
        removeMessages(2);
        sendMessageDelayed(obtainMessage(2), ((AccessibilityManager) this.mContext.getSystemService("accessibility")).getRecommendedTimeoutMillis(this.mDefaultTimeout, 4));
    }
}
