package com.samsung.accessory.manager.authentication.cover;

import android.content.Context;
import android.content.Intent;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
