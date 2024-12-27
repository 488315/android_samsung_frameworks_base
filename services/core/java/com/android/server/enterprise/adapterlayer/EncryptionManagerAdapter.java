package com.android.server.enterprise.adapterlayer;

import android.content.Context;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
