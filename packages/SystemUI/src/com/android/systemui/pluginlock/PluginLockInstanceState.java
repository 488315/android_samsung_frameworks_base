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
import java.util.ArrayList;

/* loaded from: classes2.dex */
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
        this.mGson = new GsonBuilder().disableHtmlEscaping().create();
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
                if (dbData == null || dbData.isEmpty()) {
                    this.mUtils.addDump(TAG, "initInstanceData() strData:" + dbData);
                    PluginLockInstanceData pluginLockInstanceData = new PluginLockInstanceData();
                    PluginLockInstanceData.Data data = new PluginLockInstanceData.Data();
                    this.mData = data;
                    int i = this.mMode;
                    if (i == 1) {
                        this.mAllowedNumber = 10;
                    } else if (i == 2) {
                        this.mAllowedNumber = 10000;
                    }
                    data.setPackageName(this.mPackageName);
                    this.mData.setNumber(Integer.valueOf(this.mAllowedNumber));
                    pluginLockInstanceData.addData(this.mData);
                    updateDb(pluginLockInstanceData);
                } else {
                    PluginLockInstanceData pluginLockInstanceData2 = (PluginLockInstanceData) this.mGson.fromJson(dbData, PluginLockInstanceData.class);
                    this.mUtils.addDump(TAG, "initInstanceData() instanceData:" + pluginLockInstanceData2.getData(this.mPackageName));
                    if (pluginLockInstanceData2.contain(this.mPackageName)) {
                        PluginLockInstanceData.Data data2 = pluginLockInstanceData2.getData(this.mPackageName);
                        this.mData = data2;
                        if (data2 != null) {
                            this.mAllowedNumber = data2.getNumber().intValue();
                        }
                    } else {
                        this.mData = new PluginLockInstanceData.Data();
                        int size = pluginLockInstanceData2.getDataList().size();
                        int i2 = this.mMode;
                        if (i2 == 1) {
                            this.mAllowedNumber = (size * 10) + 10;
                        } else if (i2 == 2) {
                            this.mAllowedNumber = (size * 10) + 10000;
                        }
                        this.mData.setPackageName(this.mPackageName);
                        this.mData.setNumber(Integer.valueOf(this.mAllowedNumber));
                        pluginLockInstanceData2.addData(this.mData);
                        updateDb(pluginLockInstanceData2);
                    }
                }
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
        int iIntValue = ((PluginLockInstanceData) this.mGson.fromJson(getDbData(), PluginLockInstanceData.class)).getVersion().intValue();
        ListPopupWindow$$ExternalSyntheticOutline0.m(iIntValue, "getDataVersion() ", TAG);
        return iIntValue;
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
        PluginLockInstanceData pluginLockInstanceData = (PluginLockInstanceData) this.mGson.fromJson(getDbData(), PluginLockInstanceData.class);
        boolean z = false;
        if (pluginLockInstanceData != null) {
            ArrayList<PluginLockInstanceData.Data> dataList = pluginLockInstanceData.getDataList();
            int size = dataList.size();
            int i2 = 0;
            while (i2 < size) {
                PluginLockInstanceData.Data data = dataList.get(i2);
                i2++;
                PluginLockInstanceData.Data data2 = data;
                Long timeStamps = LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION ? data2.getTimeStamps(i) : data2.getTimeStamp();
                if (timeStamps != null && timeStamps.longValue() != 0 && data2.isEnabled(i)) {
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
        ArrayList<PluginLockInstanceData.Data> dataList = ((PluginLockInstanceData) this.mGson.fromJson(getDbData(), PluginLockInstanceData.class)).getDataList();
        int size = dataList.size();
        boolean z = false;
        long jLongValue = 0;
        int i = 0;
        while (i < size) {
            PluginLockInstanceData.Data data = dataList.get(i);
            i++;
            PluginLockInstanceData.Data data2 = data;
            if (data2.getTimeStamp() != null && jLongValue < data2.getTimeStamp().longValue()) {
                jLongValue = data2.getTimeStamp().longValue();
            }
        }
        if (jLongValue > 0 && this.mData.getTimeStamp() != null && this.mData.getTimeStamp().longValue() >= jLongValue) {
            z = true;
        }
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

    public void updateDb() {
        if (this.mIsDestroyed) {
            return;
        }
        synchronized (mLock) {
            try {
                String dbData = getDbData();
                if (dbData == null || dbData.isEmpty()) {
                    PluginLockInstanceData pluginLockInstanceData = new PluginLockInstanceData();
                    PluginLockInstanceData.Data data = new PluginLockInstanceData.Data();
                    this.mData = data;
                    int i = this.mMode;
                    if (i == 1) {
                        this.mAllowedNumber = 10;
                    } else if (i == 2) {
                        this.mAllowedNumber = 10000;
                    }
                    data.setPackageName(this.mPackageName);
                    this.mData.setNumber(Integer.valueOf(this.mAllowedNumber));
                    pluginLockInstanceData.addData(this.mData);
                    dbData = this.mGson.toJson(pluginLockInstanceData);
                }
                PluginLockInstanceData pluginLockInstanceData2 = (PluginLockInstanceData) this.mGson.fromJson(dbData, PluginLockInstanceData.class);
                PluginLockInstanceData.Data data2 = pluginLockInstanceData2.getData(this.mPackageName);
                if (data2 != null) {
                    data2.setNumber(this.mData.getNumber());
                    data2.setTimeStamp(this.mData.getTimeStamp());
                    data2.setTimeStampList(this.mData.getTimeStamps());
                    data2.setRecoverData(this.mData.getRecoverData());
                    data2.setWhich(this.mData.getWhich());
                }
                updateDb(pluginLockInstanceData2);
            } catch (Throwable th) {
                throw th;
            }
        }
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
        ArrayList<PluginLockInstanceData.Data> dataList = ((PluginLockInstanceData) this.mGson.fromJson(getDbData(), PluginLockInstanceData.class)).getDataList();
        int size = dataList.size();
        boolean z = false;
        long jLongValue = 0;
        int i2 = 0;
        while (i2 < size) {
            PluginLockInstanceData.Data data = dataList.get(i2);
            i2++;
            PluginLockInstanceData.Data data2 = data;
            Long timeStamps = LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION ? data2.getTimeStamps(i) : data2.getTimeStamp();
            if (timeStamps != null && jLongValue < timeStamps.longValue() && data2.isEnabled(i)) {
                jLongValue = timeStamps.longValue();
            }
        }
        Long timeStamps2 = LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION ? this.mData.getTimeStamps(i) : this.mData.getTimeStamp();
        if (jLongValue > 0 && timeStamps2 != null && timeStamps2.longValue() >= jLongValue && this.mData.isEnabled(i)) {
            z = true;
        }
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
