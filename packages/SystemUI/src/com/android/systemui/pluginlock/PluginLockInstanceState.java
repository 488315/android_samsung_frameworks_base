package com.android.systemui.pluginlock;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.pluginlock.PluginLockInstanceData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import java.util.Iterator;

public class PluginLockInstanceState {
    private static final int ALLOWED_NUMBER_BASE_BASIC = 10;
    private static final int ALLOWED_NUMBER_BASE_DYNAMIC = 10000;
    private static final int DEFAULT_SERVICE_TYPE = 0;
    private static final String KEY_PLUGIN_LOCK_INSTANCE_DATA = "key_plugin_lock_instance_data";
    private static final int PLUGIN_LOCK_MODE_BASIC = 1;
    private static final int PLUGIN_LOCK_MODE_DYNAMIC = 2;
    private static final String SERVICE_TYPE_SEPARATOR = ":";
    private static final String TAG = "PluginLockInstanceState";
    public static String mDbCacheData;
    public static final Object mLock = new Object();
    private int mAllowedNumber;
    private Context mContext;
    private final ContentResolver mCr;
    private PluginLockInstanceData.Data mData;
    private final Gson mGson;
    private PluginLock mInstance;
    private boolean mIsDestroyed = false;
    private int mMode;
    private String mPackageName;
    private long mTimeStamp;
    private final PluginLockUtils mUtils;

    public PluginLockInstanceState(PluginLock pluginLock, Context context, PluginLockUtils pluginLockUtils) {
        String str;
        this.mMode = 1;
        Log.d(TAG, "PluginLockInstanceState: " + pluginLock);
        this.mInstance = pluginLock;
        this.mContext = context;
        this.mCr = context.getContentResolver();
        this.mPackageName = context.getPackageName();
        this.mTimeStamp = 0L;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.escapeHtmlChars = false;
        this.mGson = gsonBuilder.create();
        this.mUtils = pluginLockUtils;
        try {
            int serviceType = pluginLock.getBasicManager().getServiceType();
            if (serviceType == 0) {
                str = this.mPackageName;
            } else {
                str = this.mPackageName + SERVICE_TYPE_SEPARATOR + serviceType;
            }
            this.mPackageName = str;
            Log.d(TAG, "PluginLockInstanceState mPackageName[" + this.mPackageName + "]");
        } catch (Throwable th) {
            Log.d(TAG, "PluginLockInstanceState Throwable " + th.getMessage());
        }
        if (this.mInstance.getVersion() >= 1100) {
            this.mMode = this.mInstance.getBasicManager().getMode();
            initInstanceData();
        }
        RecyclerView$$ExternalSyntheticOutline0.m(this.mMode, TAG, new StringBuilder("mMode = "));
    }

    private String getDbData() {
        String str = mDbCacheData;
        return (str == null || str.isEmpty()) ? Settings.Secure.getString(this.mCr, KEY_PLUGIN_LOCK_INSTANCE_DATA) : mDbCacheData;
    }

    private void initInstanceData() {
        if (this.mContext == null) {
            return;
        }
        synchronized (mLock) {
            try {
                String dbData = getDbData();
                Log.d(TAG, "initInstanceData list = " + dbData);
                if (dbData != null && !dbData.isEmpty()) {
                    PluginLockInstanceData pluginLockInstanceData = (PluginLockInstanceData) this.mGson.fromJson(PluginLockInstanceData.class, dbData);
                    this.mUtils.addDump(TAG, "initInstanceData() instanceData:" + pluginLockInstanceData.getData(this.mPackageName));
                    if (pluginLockInstanceData.contain(this.mPackageName)) {
                        PluginLockInstanceData.Data data = pluginLockInstanceData.getData(this.mPackageName);
                        this.mData = data;
                        if (data != null) {
                            this.mAllowedNumber = data.getNumber().intValue();
                        }
                    } else {
                        this.mData = new PluginLockInstanceData.Data();
                        int size = pluginLockInstanceData.getDataList().size();
                        int i = this.mMode;
                        if (i == 1) {
                            this.mAllowedNumber = (size * 10) + 10;
                        } else if (i == 2) {
                            this.mAllowedNumber = (size * 10) + 10000;
                        }
                        this.mData.setPackageName(this.mPackageName);
                        this.mData.setNumber(Integer.valueOf(this.mAllowedNumber));
                        pluginLockInstanceData.addData(this.mData);
                        updateDb(pluginLockInstanceData);
                    }
                }
                this.mUtils.addDump(TAG, "initInstanceData() strData:" + dbData);
                PluginLockInstanceData pluginLockInstanceData2 = new PluginLockInstanceData();
                PluginLockInstanceData.Data data2 = new PluginLockInstanceData.Data();
                this.mData = data2;
                int i2 = this.mMode;
                if (i2 == 1) {
                    this.mAllowedNumber = 10;
                } else if (i2 == 2) {
                    this.mAllowedNumber = 10000;
                }
                data2.setPackageName(this.mPackageName);
                this.mData.setNumber(Integer.valueOf(this.mAllowedNumber));
                pluginLockInstanceData2.addData(this.mData);
                updateDb(pluginLockInstanceData2);
            } catch (Throwable th) {
                throw th;
            }
        }
        RecyclerView$$ExternalSyntheticOutline0.m(this.mAllowedNumber, TAG, new StringBuilder("initInstanceData setAllowedNumber "));
        this.mInstance.getBasicManager().setAllowedNumber(this.mAllowedNumber);
    }

    public void destroy() {
        ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("destroy() "), this.mPackageName, TAG);
        PluginLock pluginLock = this.mInstance;
        if (pluginLock != null) {
            if (pluginLock.getBasicManager() != null) {
                this.mInstance.getBasicManager().setCallback(null);
                this.mInstance.getBasicManager().setPanelView(null);
            }
            this.mInstance.onDestroy();
            this.mInstance = null;
        }
        this.mPackageName = null;
        this.mTimeStamp = 0L;
        this.mIsDestroyed = true;
    }

    public int getAllowedNumber() {
        return this.mAllowedNumber;
    }

    public PluginLockInstanceData.Data getData() {
        return this.mData;
    }

    public int getDataVersion() {
        int intValue = ((PluginLockInstanceData) this.mGson.fromJson(PluginLockInstanceData.class, getDbData())).getVersion().intValue();
        ListPopupWindow$$ExternalSyntheticOutline0.m(intValue, "getDataVersion() ", TAG);
        return intValue;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public Context getPluginLockContext() {
        return this.mContext;
    }

    public PluginLock getPluginLockInstance() {
        return this.mInstance;
    }

    public long getPluginLockTimeStamp() {
        return this.mTimeStamp;
    }

    public PluginLockInstanceData.Data.RecoverData getRecoverData() {
        PluginLockInstanceData.Data data = this.mData;
        if (data != null) {
            return data.getRecoverData();
        }
        return null;
    }

    public boolean hasEnabledPlugin(int i) {
        PluginLockInstanceData pluginLockInstanceData = (PluginLockInstanceData) this.mGson.fromJson(PluginLockInstanceData.class, getDbData());
        boolean z = false;
        if (pluginLockInstanceData != null) {
            Iterator<PluginLockInstanceData.Data> it = pluginLockInstanceData.getDataList().iterator();
            while (it.hasNext()) {
                PluginLockInstanceData.Data next = it.next();
                Long timeStamps = LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION ? next.getTimeStamps(i) : next.getTimeStamp();
                if (timeStamps != null && timeStamps.longValue() != 0 && next.isEnabled(i)) {
                    z = true;
                }
            }
        }
        return z;
    }

    public boolean isEnabled(int i) {
        return this.mData.isEnabled(i);
    }

    public boolean isEnabledOtherScreen(int i) {
        if (i == 0) {
            return this.mData.isEnabled(1);
        }
        if (i == 1) {
            return this.mData.isEnabled(0);
        }
        return false;
    }

    public boolean isModeDynamic() {
        return this.mMode == 2;
    }

    public boolean isRecentInstance() {
        Iterator<PluginLockInstanceData.Data> it = ((PluginLockInstanceData) this.mGson.fromJson(PluginLockInstanceData.class, getDbData())).getDataList().iterator();
        long j = 0;
        while (it.hasNext()) {
            PluginLockInstanceData.Data next = it.next();
            if (next.getTimeStamp() != null && j < next.getTimeStamp().longValue()) {
                j = next.getTimeStamp().longValue();
            }
        }
        boolean z = j > 0 && this.mData.getTimeStamp() != null && this.mData.getTimeStamp().longValue() >= j;
        if (z) {
            ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("isRecentInstance() true, "), this.mPackageName, TAG);
        }
        return z;
    }

    public void reset(PluginLock pluginLock, Context context) {
        this.mInstance = pluginLock;
        this.mContext = context;
        this.mTimeStamp = 0L;
        this.mData.setTimeStamp(null);
        updateDb();
    }

    public void resetStateData() {
        PluginLockInstanceData.Data data;
        if (this.mIsDestroyed || (data = this.mData) == null) {
            return;
        }
        data.setTimeStamp(null);
        this.mData.setWhich(0);
        updateDb();
    }

    public void setStateData(int i, boolean z) {
        if (this.mIsDestroyed) {
            return;
        }
        if (z) {
            this.mTimeStamp = System.currentTimeMillis();
        } else {
            this.mTimeStamp = 0L;
        }
        PluginLockInstanceData.Data data = this.mData;
        if (data != null) {
            data.setTimeStamp(i, Long.valueOf(this.mTimeStamp));
            this.mData.setScreen(i, z);
            updateDb();
        }
    }

    public void setTimeStamp(boolean z) {
        if (this.mIsDestroyed) {
            return;
        }
        if (z) {
            this.mTimeStamp = System.currentTimeMillis();
        } else {
            this.mTimeStamp = 0L;
        }
        PluginLockInstanceData.Data data = this.mData;
        if (data != null) {
            data.setTimeStamp(Long.valueOf(this.mTimeStamp));
            updateDb();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        PluginLockInstanceData.Data data = this.mData;
        sb.append(data != null ? data.toString() : "null");
        sb.append(", instance[");
        sb.append(this.mInstance);
        sb.append("]");
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0062 A[Catch: all -> 0x0015, TryCatch #0 {all -> 0x0015, blocks: (B:8:0x0008, B:10:0x000e, B:13:0x0050, B:15:0x0062, B:16:0x008f, B:17:0x0092, B:20:0x0018, B:22:0x0029, B:23:0x0035, B:26:0x0031), top: B:7:0x0008 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateDb() {
        /*
            r5 = this;
            boolean r0 = r5.mIsDestroyed
            if (r0 == 0) goto L5
            return
        L5:
            java.lang.Object r0 = com.android.systemui.pluginlock.PluginLockInstanceState.mLock
            monitor-enter(r0)
            java.lang.String r1 = r5.getDbData()     // Catch: java.lang.Throwable -> L15
            if (r1 == 0) goto L18
            boolean r2 = r1.isEmpty()     // Catch: java.lang.Throwable -> L15
            if (r2 == 0) goto L50
            goto L18
        L15:
            r5 = move-exception
            goto L94
        L18:
            com.android.systemui.pluginlock.PluginLockInstanceData r1 = new com.android.systemui.pluginlock.PluginLockInstanceData     // Catch: java.lang.Throwable -> L15
            r1.<init>()     // Catch: java.lang.Throwable -> L15
            com.android.systemui.pluginlock.PluginLockInstanceData$Data r2 = new com.android.systemui.pluginlock.PluginLockInstanceData$Data     // Catch: java.lang.Throwable -> L15
            r2.<init>()     // Catch: java.lang.Throwable -> L15
            r5.mData = r2     // Catch: java.lang.Throwable -> L15
            int r3 = r5.mMode     // Catch: java.lang.Throwable -> L15
            r4 = 1
            if (r3 != r4) goto L2e
            r3 = 10
            r5.mAllowedNumber = r3     // Catch: java.lang.Throwable -> L15
            goto L35
        L2e:
            r4 = 2
            if (r3 != r4) goto L35
            r3 = 10000(0x2710, float:1.4013E-41)
            r5.mAllowedNumber = r3     // Catch: java.lang.Throwable -> L15
        L35:
            java.lang.String r3 = r5.mPackageName     // Catch: java.lang.Throwable -> L15
            r2.setPackageName(r3)     // Catch: java.lang.Throwable -> L15
            com.android.systemui.pluginlock.PluginLockInstanceData$Data r2 = r5.mData     // Catch: java.lang.Throwable -> L15
            int r3 = r5.mAllowedNumber     // Catch: java.lang.Throwable -> L15
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch: java.lang.Throwable -> L15
            r2.setNumber(r3)     // Catch: java.lang.Throwable -> L15
            com.android.systemui.pluginlock.PluginLockInstanceData$Data r2 = r5.mData     // Catch: java.lang.Throwable -> L15
            r1.addData(r2)     // Catch: java.lang.Throwable -> L15
            com.google.gson.Gson r2 = r5.mGson     // Catch: java.lang.Throwable -> L15
            java.lang.String r1 = r2.toJson(r1)     // Catch: java.lang.Throwable -> L15
        L50:
            com.google.gson.Gson r2 = r5.mGson     // Catch: java.lang.Throwable -> L15
            java.lang.Class<com.android.systemui.pluginlock.PluginLockInstanceData> r3 = com.android.systemui.pluginlock.PluginLockInstanceData.class
            java.lang.Object r1 = r2.fromJson(r3, r1)     // Catch: java.lang.Throwable -> L15
            com.android.systemui.pluginlock.PluginLockInstanceData r1 = (com.android.systemui.pluginlock.PluginLockInstanceData) r1     // Catch: java.lang.Throwable -> L15
            java.lang.String r2 = r5.mPackageName     // Catch: java.lang.Throwable -> L15
            com.android.systemui.pluginlock.PluginLockInstanceData$Data r2 = r1.getData(r2)     // Catch: java.lang.Throwable -> L15
            if (r2 == 0) goto L8f
            com.android.systemui.pluginlock.PluginLockInstanceData$Data r3 = r5.mData     // Catch: java.lang.Throwable -> L15
            java.lang.Integer r3 = r3.getNumber()     // Catch: java.lang.Throwable -> L15
            r2.setNumber(r3)     // Catch: java.lang.Throwable -> L15
            com.android.systemui.pluginlock.PluginLockInstanceData$Data r3 = r5.mData     // Catch: java.lang.Throwable -> L15
            java.lang.Long r3 = r3.getTimeStamp()     // Catch: java.lang.Throwable -> L15
            r2.setTimeStamp(r3)     // Catch: java.lang.Throwable -> L15
            com.android.systemui.pluginlock.PluginLockInstanceData$Data r3 = r5.mData     // Catch: java.lang.Throwable -> L15
            java.util.List r3 = r3.getTimeStamps()     // Catch: java.lang.Throwable -> L15
            r2.setTimeStampList(r3)     // Catch: java.lang.Throwable -> L15
            com.android.systemui.pluginlock.PluginLockInstanceData$Data r3 = r5.mData     // Catch: java.lang.Throwable -> L15
            com.android.systemui.pluginlock.PluginLockInstanceData$Data$RecoverData r3 = r3.getRecoverData()     // Catch: java.lang.Throwable -> L15
            r2.setRecoverData(r3)     // Catch: java.lang.Throwable -> L15
            com.android.systemui.pluginlock.PluginLockInstanceData$Data r3 = r5.mData     // Catch: java.lang.Throwable -> L15
            int r3 = r3.getWhich()     // Catch: java.lang.Throwable -> L15
            r2.setWhich(r3)     // Catch: java.lang.Throwable -> L15
        L8f:
            r5.updateDb(r1)     // Catch: java.lang.Throwable -> L15
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L15
            return
        L94:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L15
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.pluginlock.PluginLockInstanceState.updateDb():void");
    }

    public void setStateData(int i, long j) {
        PluginLockInstanceData.Data data;
        if (this.mIsDestroyed || (data = this.mData) == null) {
            return;
        }
        data.setTimeStamp(i, Long.valueOf(j));
        this.mData.setScreen(i, true);
        updateDb();
    }

    public boolean isRecentInstance(int i) {
        Iterator<PluginLockInstanceData.Data> it = ((PluginLockInstanceData) this.mGson.fromJson(PluginLockInstanceData.class, getDbData())).getDataList().iterator();
        long j = 0;
        while (it.hasNext()) {
            PluginLockInstanceData.Data next = it.next();
            Long timeStamps = LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION ? next.getTimeStamps(i) : next.getTimeStamp();
            if (timeStamps != null && j < timeStamps.longValue() && next.isEnabled(i)) {
                j = timeStamps.longValue();
            }
        }
        Long timeStamps2 = LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION ? this.mData.getTimeStamps(i) : this.mData.getTimeStamp();
        boolean z = j > 0 && timeStamps2 != null && timeStamps2.longValue() >= j && this.mData.isEnabled(i);
        if (z) {
            ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("isRecentInstance() true, "), this.mPackageName, TAG);
        }
        return z;
    }

    private void updateDb(PluginLockInstanceData pluginLockInstanceData) {
        Log.d(TAG, "update instance data: " + pluginLockInstanceData);
        if (pluginLockInstanceData.getVersion().intValue() < 3) {
            pluginLockInstanceData.setVersion(3);
        }
        String json = this.mGson.toJson(pluginLockInstanceData);
        mDbCacheData = json;
        Settings.Secure.putString(this.mCr, KEY_PLUGIN_LOCK_INSTANCE_DATA, json);
    }
}
