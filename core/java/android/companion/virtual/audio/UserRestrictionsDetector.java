package android.companion.virtual.audio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.UserManager;

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
        Bundle bundle = this.mUserManager.getUserRestrictions();
        return bundle.getBoolean(UserManager.DISALLOW_UNMUTE_MICROPHONE);
    }

    void register(UserRestrictionsCallback callback) {
        this.mUserRestrictionsCallback = callback;
        IntentFilter filter = new IntentFilter();
        filter.addAction(UserManager.ACTION_USER_RESTRICTIONS_CHANGED);
        this.mContext.registerReceiver(this, filter);
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
        String action = intent.getAction();
        if (UserManager.ACTION_USER_RESTRICTIONS_CHANGED.equals(action)) {
            boolean isUnmuteMicDisallowed = isUnmuteMicrophoneDisallowed();
            synchronized (this.mLock) {
                if (isUnmuteMicDisallowed == this.mIsUnmuteMicDisallowed) {
                    return;
                }
                this.mIsUnmuteMicDisallowed = isUnmuteMicDisallowed;
                if (this.mUserRestrictionsCallback != null) {
                    this.mUserRestrictionsCallback.onMicrophoneRestrictionChanged(
                            isUnmuteMicDisallowed);
                }
            }
        }
    }
}
