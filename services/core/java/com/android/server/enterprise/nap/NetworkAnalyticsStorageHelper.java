package com.android.server.enterprise.nap;

import android.content.Context;

import com.android.server.enterprise.storage.EdmStorageProvider;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
