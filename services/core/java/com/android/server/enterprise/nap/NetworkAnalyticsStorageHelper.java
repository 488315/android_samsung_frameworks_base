package com.android.server.enterprise.nap;

import android.content.ContentValues;
import android.content.Context;
import com.android.server.enterprise.storage.EdmStorageProvider;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class NetworkAnalyticsStorageHelper {
    public static NetworkAnalyticsStorageHelper mDefaultHelper;
    public static EdmStorageProvider mEDM;
    public static Object mSynObj = new Object();
    public Context mContext;

    public NetworkAnalyticsStorageHelper(Context context) {
        this.mContext = null;
        synchronized (mSynObj) {
            if (mEDM == null) {
                this.mContext = context;
                mEDM = new EdmStorageProvider(context);
            }
        }
    }

    public static synchronized NetworkAnalyticsStorageHelper getInstance(Context context) {
        NetworkAnalyticsStorageHelper networkAnalyticsStorageHelper;
        synchronized (NetworkAnalyticsStorageHelper.class) {
            if (mDefaultHelper == null) {
                mDefaultHelper = new NetworkAnalyticsStorageHelper(context);
            }
            networkAnalyticsStorageHelper = mDefaultHelper;
        }
        return networkAnalyticsStorageHelper;
    }

    public boolean putDataByFields(String str, String[] strArr, String[] strArr2, ContentValues contentValues) {
        return mEDM.putDataByFields(str, strArr, strArr2, contentValues);
    }

    public boolean deleteDataByFields(String str, String[] strArr, String[] strArr2) {
        return mEDM.deleteDataByFields(str, strArr, strArr2);
    }

    public ArrayList getDataByFields(String str, String[] strArr, String[] strArr2, String[] strArr3) {
        return mEDM.getDataByFields(str, strArr, strArr2, strArr3);
    }

    public int updateFields(String str, ContentValues contentValues, ContentValues contentValues2) {
        return mEDM.update(str, contentValues, contentValues2);
    }
}
