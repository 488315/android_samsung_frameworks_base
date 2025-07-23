package com.samsung.android.globalactions.util;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.samsung.android.view.SemWindowManager;

/* loaded from: classes6.dex */
public class KeyGuardManagerWrapper {
    private static final String ACTION_SHOW_GLOBAL_ACTIONS = "android.intent.action.SHOW_GLOBAL_ACTIONS";
    private static final String TAG = "KeyguardManagerWrapper";
    private final Context mContext;
    private boolean mIsRegistered;
    private final KeyguardManager mKeyguardManager;
    private final LogWrapper mLogWrapper;

    public KeyGuardManagerWrapper(Context context, LogWrapper logWrapper) {
        this.mContext = context;
        this.mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        this.mLogWrapper = logWrapper;
    }

    public boolean isSecureKeyguard() {
        return this.mKeyguardManager.isDeviceSecure(ActivityManager.getCurrentUser()) && this.mKeyguardManager.inKeyguardRestrictedInputMode();
    }

    public boolean isCurrentUserSecure() {
        return this.mKeyguardManager.isDeviceSecure(ActivityManager.getCurrentUser());
    }

    public void setPendingIntentAfterUnlock(String str) {
        this.mLogWrapper.i(TAG, "setPendingIntentAfterUnlock");
        if (this.mIsRegistered) {
            return;
        }
        PendingIntent broadcast = PendingIntent.getBroadcast(this.mContext, 0, new Intent("android.intent.action.SHOW_GLOBAL_ACTIONS"), 201326592);
        Intent intent = new Intent();
        intent.putExtra("afterKeyguardGone", false);
        intent.putExtra("dismissType", str);
        this.mKeyguardManager.semSetPendingIntentAfterUnlock(broadcast, intent);
    }

    public void setPendingIntentAfterUnlockOnCover(String str, boolean z) {
        this.mLogWrapper.i(TAG, "setPendingIntentAfterUnlockOnCover");
        if (this.mIsRegistered) {
            return;
        }
        PendingIntent broadcast = PendingIntent.getBroadcast(this.mContext, 0, new Intent("android.intent.action.SHOW_GLOBAL_ACTIONS"), 201326592);
        Intent intent = new Intent();
        if (z && SemWindowManager.getInstance().isFolded()) {
            intent.putExtra("runOnCover", true);
            intent.putExtra("ignoreKeyguardState", true);
        } else {
            intent.putExtra("afterKeyguardGone", false);
            intent.putExtra("dismissType", str);
        }
        this.mKeyguardManager.semSetPendingIntentAfterUnlock(broadcast, intent);
    }

    public void setRegisterState(boolean z) {
        this.mIsRegistered = z;
    }
}
