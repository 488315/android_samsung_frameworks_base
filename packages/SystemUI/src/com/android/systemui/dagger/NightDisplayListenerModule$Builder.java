package com.android.systemui.dagger;

import android.content.Context;
import android.os.Handler;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NightDisplayListenerModule$Builder {
    public final Handler mBgHandler;
    public final Context mContext;
    public int mUserId = 0;

    public NightDisplayListenerModule$Builder(Context context, Handler handler) {
        this.mContext = context;
        this.mBgHandler = handler;
    }
}
