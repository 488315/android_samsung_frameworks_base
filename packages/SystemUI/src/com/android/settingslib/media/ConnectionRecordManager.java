package com.android.settingslib.media;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
