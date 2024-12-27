package com.android.server.devicepolicy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.concurrent.Semaphore;

public final class UserUnlockedBlockingReceiver extends BroadcastReceiver {
    public final Semaphore mSemaphore = new Semaphore(0);
    public final int mUserId;

    public UserUnlockedBlockingReceiver(int i) {
        this.mUserId = i;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if ("android.intent.action.USER_UNLOCKED".equals(intent.getAction())
                && intent.getIntExtra("android.intent.extra.user_handle", -10000) == this.mUserId) {
            this.mSemaphore.release();
        }
    }
}
