package com.android.wm.shell.pip2.phone;

import android.content.Context;
import android.os.Handler;
import com.android.internal.jank.InteractionJankMonitor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PipInteractionHandler {
    public final Context mContext;
    public final Handler mHandler;
    public final InteractionJankMonitor mInteractionJankMonitor;

    public PipInteractionHandler(Context context, Handler handler, InteractionJankMonitor interactionJankMonitor) {
        this.mContext = context;
        this.mHandler = handler;
        this.mInteractionJankMonitor = interactionJankMonitor;
    }
}
