package com.android.server.enterprise.adapterlayer;

import android.content.Context;

public final class EncryptionManagerAdapter {
    public static Context mContext;
    public static EncryptionManagerAdapter mInstance;

    public static synchronized EncryptionManagerAdapter getInstance(Context context) {
        EncryptionManagerAdapter encryptionManagerAdapter;
        synchronized (EncryptionManagerAdapter.class) {
            try {
                if (mInstance == null) {
                    mContext = context;
                    mInstance = new EncryptionManagerAdapter();
                }
                encryptionManagerAdapter = mInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return encryptionManagerAdapter;
    }
}
