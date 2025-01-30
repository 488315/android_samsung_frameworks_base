package com.android.settingslib.media;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ConnectionRecordManager {
    public static ConnectionRecordManager sInstance;
    public static final Object sInstanceSync = new Object();
    public String mLastSelectedDevice;

    public static ConnectionRecordManager getInstance() {
        synchronized (sInstanceSync) {
            if (sInstance == null) {
                sInstance = new ConnectionRecordManager();
            }
        }
        return sInstance;
    }
}
