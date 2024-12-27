package com.samsung.accessory.manager.authentication.cover;

import android.content.Context;
import android.content.Intent;

public final class CoverBroadcaster {
    public final Context mContext;
    public int mRealCoverType = 2;

    public CoverBroadcaster(Context context) {
        this.mContext = context;
    }

    public final void broadcastCoverAttachStatus(boolean z) {
        if (!z) {
            this.mRealCoverType = 2;
        }
        Intent intent = new Intent("com.samsung.android.intent.action.COVER_ATTACH_CHANGED");
        intent.putExtra("attach", z);
        intent.putExtra("real_cover_type", this.mRealCoverType);
        intent.setPackage("android");
        this.mContext.sendBroadcast(intent);
    }
}
