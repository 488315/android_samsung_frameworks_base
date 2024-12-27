package com.android.server.enterprise.nap;

import android.content.Context;

import com.android.server.enterprise.storage.EdmStorageProvider;

public final class NetworkAnalyticsStorageHelper {
    public static NetworkAnalyticsStorageHelper mDefaultHelper;
    public static EdmStorageProvider mEDM;
    public static final Object mSynObj = new Object();

    public static synchronized NetworkAnalyticsStorageHelper getInstance(Context context) {
        NetworkAnalyticsStorageHelper networkAnalyticsStorageHelper;
        synchronized (NetworkAnalyticsStorageHelper.class) {
            if (mDefaultHelper == null) {
                NetworkAnalyticsStorageHelper networkAnalyticsStorageHelper2 =
                        new NetworkAnalyticsStorageHelper();
                synchronized (mSynObj) {
                    try {
                        if (mEDM == null) {
                            mEDM = new EdmStorageProvider(context);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                mDefaultHelper = networkAnalyticsStorageHelper2;
            }
            networkAnalyticsStorageHelper = mDefaultHelper;
        }
        return networkAnalyticsStorageHelper;
    }
}
