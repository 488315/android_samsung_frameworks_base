package com.android.server.devicepolicy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.concurrent.Semaphore;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class UserUnlockedBlockingReceiver extends BroadcastReceiver {
    public final Semaphore mSemaphore = new Semaphore(0);
    public final int mUserId;

    public UserUnlockedBlockingReceiver(int i) {
        this.mUserId = i;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if ("android.intent.action.USER_UNLOCKED".equals(intent.getAction()) && intent.getIntExtra("android.intent.extra.user_handle", -10000) == this.mUserId) {
            this.mSemaphore.release();
        }
    }
}
