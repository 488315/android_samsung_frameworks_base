package com.android.server.media.projection;

import android.content.SharedPreferences;

public final class MediaProjectionSessionIdGenerator {
    public static MediaProjectionSessionIdGenerator sInstance;
    public static final Object sInstanceLock = new Object();
    public final Object mSessionIdLock = new Object();
    public final SharedPreferences mSharedPreferences;

    public MediaProjectionSessionIdGenerator(SharedPreferences sharedPreferences) {
        this.mSharedPreferences = sharedPreferences;
    }

    public final int getCurrentSessionId() {
        int i;
        synchronized (this.mSessionIdLock) {
            i = this.mSharedPreferences.getInt("media_projection_session_id_key", 0);
        }
        return i;
    }
}
