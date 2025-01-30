package com.android.systemui.dagger;

import android.content.Context;
import android.os.Handler;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NightDisplayListenerModule {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder {
        public final Handler mBgHandler;
        public final Context mContext;
        public int mUserId = 0;

        public Builder(Context context, Handler handler) {
            this.mContext = context;
            this.mBgHandler = handler;
        }
    }
}
