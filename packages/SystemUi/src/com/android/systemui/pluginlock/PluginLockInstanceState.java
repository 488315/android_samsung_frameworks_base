package com.android.systemui.pluginlock;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.pluginlock.PluginLockInstanceData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PluginLockInstanceState {
    public static String mDbCacheData;
    public static final Object mLock = new Object();
    public int mAllowedNumber;
    public final Context mContext;
    public final ContentResolver mCr;
    public PluginLockInstanceData.Data mData;
    public final Gson mGson;
    public PluginLock mInstance;
    public boolean mIsDestroyed = false;
    public final int mMode;
    public String mPackageName;
    public long mTimeStamp;
    public final PluginLockUtils mUtils;

    public PluginLockInstanceState(PluginLock pluginLock, Context context, PluginLockUtils pluginLockUtils) {
        String str;
        this.mMode = 1;
        Log.d("PluginLockInstanceState", "PluginLockInstanceState: " + pluginLock);
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
                str = this.mPackageName + ":" + serviceType;
            }
            this.mPackageName = str;
            Log.d("PluginLockInstanceState", "PluginLockInstanceState mPackageName[" + this.mPackageName + "]");
        } catch (Throwable th) {
            Log.d("PluginLockInstanceState", "PluginLockInstanceState Throwable " + th.getMessage());
        }
        if (this.mInstance.getVersion() >= 1100) {
            int mode = this.mInstance.getBasicManager().getMode();
            this.mMode = mode;
            if (this.mContext != null) {
                synchronized (mLock) {
                    String dbData = getDbData();
                    Log.d("PluginLockInstanceState", "initInstanceData list = " + dbData);
                    if (dbData != null && !dbData.isEmpty()) {
                        PluginLockInstanceData pluginLockInstanceData = (PluginLockInstanceData) this.mGson.fromJson(PluginLockInstanceData.class, dbData);
                        this.mUtils.addDump("PluginLockInstanceState", "initInstanceData() instanceData:" + pluginLockInstanceData.getData(this.mPackageName));
                        if (pluginLockInstanceData.contain(this.mPackageName)) {
                            PluginLockInstanceData.Data data = pluginLockInstanceData.getData(this.mPackageName);
                            this.mData = data;
                            if (data != null) {
                                this.mAllowedNumber = data.getNumber().intValue();
                            }
                        } else {
                            this.mData = new PluginLockInstanceData.Data();
                            int size = pluginLockInstanceData.getDataList().size();
                            if (mode == 1) {
                                this.mAllowedNumber = (size * 10) + 10;
                            } else if (mode == 2) {
                                this.mAllowedNumber = (size * 10) + 10000;
                            }
                            this.mData.setPackageName(this.mPackageName);
                            this.mData.setNumber(Integer.valueOf(this.mAllowedNumber));
                            pluginLockInstanceData.addData(this.mData);
                            updateDb(pluginLockInstanceData);
                        }
                    }
                    this.mUtils.addDump("PluginLockInstanceState", "initInstanceData() strData:" + dbData);
                    PluginLockInstanceData pluginLockInstanceData2 = new PluginLockInstanceData();
                    PluginLockInstanceData.Data data2 = new PluginLockInstanceData.Data();
                    this.mData = data2;
                    if (mode == 1) {
                        this.mAllowedNumber = 10;
                    } else if (mode == 2) {
                        this.mAllowedNumber = 10000;
                    }
                    data2.setPackageName(this.mPackageName);
                    this.mData.setNumber(Integer.valueOf(this.mAllowedNumber));
                    pluginLockInstanceData2.addData(this.mData);
                    updateDb(pluginLockInstanceData2);
                }
                RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("initInstanceData setAllowedNumber "), this.mAllowedNumber, "PluginLockInstanceState");
                this.mInstance.getBasicManager().setAllowedNumber(this.mAllowedNumber);
            }
        }
        RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("mMode = "), this.mMode, "PluginLockInstanceState");
    }

    public final void destroy() {
        ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("destroy() "), this.mPackageName, "PluginLockInstanceState");
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

    public final String getDbData() {
        String str = mDbCacheData;
        return (str == null || str.isEmpty()) ? Settings.Secure.getString(this.mCr, "key_plugin_lock_instance_data") : mDbCacheData;
    }

    public final PluginLockInstanceData.Data.RecoverData getRecoverData() {
        PluginLockInstanceData.Data data = this.mData;
        if (data != null) {
            return data.getRecoverData();
        }
        return null;
    }

    public final boolean hasEnabledPlugin(int i) {
        PluginLockInstanceData pluginLockInstanceData = (PluginLockInstanceData) this.mGson.fromJson(PluginLockInstanceData.class, getDbData());
        boolean z = false;
        if (pluginLockInstanceData != null) {
            Iterator it = pluginLockInstanceData.getDataList().iterator();
            while (it.hasNext()) {
                PluginLockInstanceData.Data data = (PluginLockInstanceData.Data) it.next();
                Long timeStamps = LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION ? data.getTimeStamps(i) : data.getTimeStamp();
                if (timeStamps != null && timeStamps.longValue() != 0 && data.isEnabled(i)) {
                    z = true;
                }
            }
        }
        return z;
    }

    public final boolean isEnabledOtherScreen(int i) {
        if (i == 0) {
            return this.mData.isEnabled(1);
        }
        if (i == 1) {
            return this.mData.isEnabled(0);
        }
        return false;
    }

    public final boolean isRecentInstance() {
        Iterator it = ((PluginLockInstanceData) this.mGson.fromJson(PluginLockInstanceData.class, getDbData())).getDataList().iterator();
        long j = 0;
        while (it.hasNext()) {
            PluginLockInstanceData.Data data = (PluginLockInstanceData.Data) it.next();
            if (data.getTimeStamp() != null && j < data.getTimeStamp().longValue()) {
                j = data.getTimeStamp().longValue();
            }
        }
        boolean z = j > 0 && this.mData.getTimeStamp() != null && this.mData.getTimeStamp().longValue() >= j;
        if (z) {
            ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("isRecentInstance() true, "), this.mPackageName, "PluginLockInstanceState");
        }
        return z;
    }

    public final void setStateData(int i, boolean z) {
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

    public final void setTimeStamp(boolean z) {
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

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        PluginLockInstanceData.Data data = this.mData;
        sb.append(data != null ? data.toString() : "null");
        sb.append(", instance[");
        sb.append(this.mInstance);
        sb.append("]");
        return sb.toString();
    }

    public final void updateDb() {
        if (this.mIsDestroyed) {
            return;
        }
        synchronized (mLock) {
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
            PluginLockInstanceData pluginLockInstanceData2 = (PluginLockInstanceData) this.mGson.fromJson(PluginLockInstanceData.class, dbData);
            PluginLockInstanceData.Data data2 = pluginLockInstanceData2.getData(this.mPackageName);
            if (data2 != null) {
                data2.setNumber(this.mData.getNumber());
                data2.setTimeStamp(this.mData.getTimeStamp());
                data2.setTimeStampList(this.mData.getTimeStamps());
                data2.setRecoverData(this.mData.getRecoverData());
                data2.setWhich(this.mData.getWhich());
            }
            updateDb(pluginLockInstanceData2);
        }
    }

    public final boolean isRecentInstance(int i) {
        Iterator it = ((PluginLockInstanceData) this.mGson.fromJson(PluginLockInstanceData.class, getDbData())).getDataList().iterator();
        long j = 0;
        while (it.hasNext()) {
            PluginLockInstanceData.Data data = (PluginLockInstanceData.Data) it.next();
            Long timeStamps = LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION ? data.getTimeStamps(i) : data.getTimeStamp();
            if (timeStamps != null && j < timeStamps.longValue() && data.isEnabled(i)) {
                j = timeStamps.longValue();
            }
        }
        Long timeStamps2 = LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION ? this.mData.getTimeStamps(i) : this.mData.getTimeStamp();
        boolean z = j > 0 && timeStamps2 != null && timeStamps2.longValue() >= j && this.mData.isEnabled(i);
        if (z) {
            ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("isRecentInstance() true, "), this.mPackageName, "PluginLockInstanceState");
        }
        return z;
    }

    public final void updateDb(PluginLockInstanceData pluginLockInstanceData) {
        Log.d("PluginLockInstanceState", "update instance data: " + pluginLockInstanceData);
        if (pluginLockInstanceData.getVersion().intValue() < 3) {
            pluginLockInstanceData.setVersion();
        }
        String json = this.mGson.toJson(pluginLockInstanceData);
        mDbCacheData = json;
        Settings.Secure.putString(this.mCr, "key_plugin_lock_instance_data", json);
    }
}
