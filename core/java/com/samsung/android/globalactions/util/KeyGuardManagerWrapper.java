package com.samsung.android.globalactions.util;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.samsung.android.view.SemWindowManager;

/* loaded from: classes6.dex */
public class KeyGuardManagerWrapper {
    private static final String ACTION_SHOW_GLOBAL_ACTIONS =
            "android.intent.action.SHOW_GLOBAL_ACTIONS";
    private static final String TAG = "KeyguardManagerWrapper";
    private final Context mContext;
    private boolean mIsRegistered;
    private final KeyguardManager mKeyguardManager;
    private final LogWrapper mLogWrapper;

    public KeyGuardManagerWrapper(Context context, LogWrapper logWrapper) {
        this.mContext = context;
        this.mKeyguardManager =
                (KeyguardManager) this.mContext.getSystemService(Context.KEYGUARD_SERVICE);
        this.mLogWrapper = logWrapper;
    }

    public boolean isSecureKeyguard() {
        return this.mKeyguardManager.isDeviceSecure(ActivityManager.getCurrentUser())
                && this.mKeyguardManager.inKeyguardRestrictedInputMode();
    }

    public boolean isCurrentUserSecure() {
        return this.mKeyguardManager.isDeviceSecure(ActivityManager.getCurrentUser());
    }

    public void setPendingIntentAfterUnlock(String dissmissType) {
        this.mLogWrapper.i(TAG, "setPendingIntentAfterUnlock");
        if (this.mIsRegistered) {
            return;
        }
        Intent intent = new Intent("android.intent.action.SHOW_GLOBAL_ACTIONS");
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(this.mContext, 0, intent, 201326592);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtra("afterKeyguardGone", false);
        fillInIntent.putExtra("dismissType", dissmissType);
        this.mKeyguardManager.semSetPendingIntentAfterUnlock(pendingIntent, fillInIntent);
    }

    public void setPendingIntentAfterUnlockOnCover(String dissmissType, boolean oncover) {
        this.mLogWrapper.i(TAG, "setPendingIntentAfterUnlockOnCover");
        if (this.mIsRegistered) {
            return;
        }
        Intent intent = new Intent("android.intent.action.SHOW_GLOBAL_ACTIONS");
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(this.mContext, 0, intent, 201326592);
        Intent fillInIntent = new Intent();
        if (oncover && SemWindowManager.getInstance().isFolded()) {
            fillInIntent.putExtra("runOnCover", true);
            fillInIntent.putExtra("ignoreKeyguardState", true);
        } else {
            fillInIntent.putExtra("afterKeyguardGone", false);
            fillInIntent.putExtra("dismissType", dissmissType);
        }
        this.mKeyguardManager.semSetPendingIntentAfterUnlock(pendingIntent, fillInIntent);
    }

    public void setRegisterState(boolean state) {
        this.mIsRegistered = state;
    }
}
