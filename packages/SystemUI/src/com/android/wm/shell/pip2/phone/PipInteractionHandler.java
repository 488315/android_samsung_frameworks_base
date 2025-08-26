package com.android.wm.shell.pip2.phone;

import android.content.Context;
import android.os.Handler;
import com.android.internal.jank.InteractionJankMonitor;

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
