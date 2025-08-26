package com.android.settingslib.media;

/* loaded from: classes.dex */
public class ConnectionRecordManager {
    public static ConnectionRecordManager sInstance;
    public static final Object sInstanceSync = new Object();
    public String mLastSelectedDevice;

    public static ConnectionRecordManager getInstance() {
        synchronized (sInstanceSync) {
            try {
                if (sInstance == null) {
                    sInstance = new ConnectionRecordManager();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return sInstance;
    }
}
