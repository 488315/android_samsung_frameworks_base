package android.companion.virtual.audio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserManager;

/* loaded from: classes.dex */
final class UserRestrictionsDetector extends BroadcastReceiver {
    private static final String TAG = "UserRestrictionsDetector";
    private final Context mContext;
    private boolean mIsUnmuteMicDisallowed;
    private final Object mLock = new Object();
    private final UserManager mUserManager;
    private UserRestrictionsCallback mUserRestrictionsCallback;

    interface UserRestrictionsCallback {
        void onMicrophoneRestrictionChanged(boolean z);
    }

    UserRestrictionsDetector(Context context) {
        this.mContext = context;
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
    }

    boolean isUnmuteMicrophoneDisallowed() {
        return this.mUserManager.getUserRestrictions().getBoolean(UserManager.DISALLOW_UNMUTE_MICROPHONE);
    }

    void register(UserRestrictionsCallback userRestrictionsCallback) {
        this.mUserRestrictionsCallback = userRestrictionsCallback;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UserManager.ACTION_USER_RESTRICTIONS_CHANGED);
        this.mContext.registerReceiver(this, intentFilter);
        synchronized (this.mLock) {
            this.mIsUnmuteMicDisallowed = isUnmuteMicrophoneDisallowed();
        }
    }

    void unregister() {
        if (this.mUserRestrictionsCallback != null) {
            this.mUserRestrictionsCallback = null;
            this.mContext.unregisterReceiver(this);
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (UserManager.ACTION_USER_RESTRICTIONS_CHANGED.equals(intent.getAction())) {
            boolean zIsUnmuteMicrophoneDisallowed = isUnmuteMicrophoneDisallowed();
            synchronized (this.mLock) {
                if (zIsUnmuteMicrophoneDisallowed == this.mIsUnmuteMicDisallowed) {
                    return;
                }
                this.mIsUnmuteMicDisallowed = zIsUnmuteMicrophoneDisallowed;
                UserRestrictionsCallback userRestrictionsCallback = this.mUserRestrictionsCallback;
                if (userRestrictionsCallback != null) {
                    userRestrictionsCallback.onMicrophoneRestrictionChanged(zIsUnmuteMicrophoneDisallowed);
                }
            }
        }
    }
}
