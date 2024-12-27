package com.android.server.media.projection;

import android.content.SharedPreferences;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
