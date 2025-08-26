package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.ArrayMap;
import android.util.KeyValueListParser;
import android.util.Log;
import com.android.settingslib.bluetooth.HearingDeviceLocalDataManager;
import com.android.settingslib.utils.ThreadUtils;
import com.google.common.util.concurrent.ListeningExecutorService;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

/* loaded from: classes.dex */
public class HearingDeviceLocalDataManager {
    public static final Object sLock = new Object();
    public final Context mContext;
    public AmbientVolumeUiController mListener;
    public ListeningExecutorService mListenerExecutor;
    public final SettingsObserver mSettingsObserver;
    public final Map mAddrToDataMap = new HashMap();
    public boolean mIsStarted = false;

    public final class Data extends Record {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final int ambient;
        public final boolean ambientControlExpanded;
        public final int groupAmbient;

        public /* synthetic */ Data(int i) {
            this();
        }

        @Override // java.lang.Record
        public final boolean equals(Object obj) {
            if (!(obj instanceof Data)) {
                return false;
            }
            Data data = (Data) obj;
            return this.ambientControlExpanded == data.ambientControlExpanded && this.ambient == data.ambient && this.groupAmbient == data.groupAmbient;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            boolean z = this.ambientControlExpanded;
            int i = this.ambient;
            return (((Boolean.hashCode(z) * 31) + i) * 31) + this.groupAmbient;
        }

        public final String toSettingsFormat() {
            String string;
            if (this.ambient != Integer.MIN_VALUE) {
                string = ",ambient=" + this.ambient;
            } else {
                string = "";
            }
            if (this.groupAmbient != Integer.MIN_VALUE) {
                StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(string, ",group_ambient=");
                sbM.append(this.groupAmbient);
                string = sbM.toString();
            }
            StringBuilder sbM2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(string, ",control_expanded=");
            sbM2.append(this.ambientControlExpanded);
            return sbM2.toString();
        }

        @Override // java.lang.Record
        public final String toString() {
            Object[] objArr = {Integer.valueOf(this.ambient), Integer.valueOf(this.groupAmbient), Boolean.valueOf(this.ambientControlExpanded)};
            String[] strArrSplit = "ambient;groupAmbient;ambientControlExpanded".length() == 0 ? new String[0] : "ambient;groupAmbient;ambientControlExpanded".split(";");
            StringBuilder sb = new StringBuilder();
            sb.append(Data.class.getSimpleName());
            sb.append("[");
            for (int i = 0; i < strArrSplit.length; i++) {
                sb.append(strArrSplit[i]);
                sb.append("=");
                sb.append(objArr[i]);
                if (i != strArrSplit.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            return sb.toString();
        }

        public Data(int i, int i2, boolean z) {
            this.ambient = i;
            this.groupAmbient = i2;
            this.ambientControlExpanded = z;
        }

        private Data() {
            this(Integer.MIN_VALUE, Integer.MIN_VALUE, false);
        }

        public final class Builder {
            public int mAmbient;
            public boolean mAmbientControlExpanded;
            public int mGroupAmbient;

            public Builder() {
                int i = Data.$r8$clinit;
                this.mAmbient = Integer.MIN_VALUE;
                this.mGroupAmbient = Integer.MIN_VALUE;
                this.mAmbientControlExpanded = false;
            }

            public Builder(Data data) {
                this.mAmbient = data.ambient;
                this.mGroupAmbient = data.groupAmbient;
                this.mAmbientControlExpanded = data.ambientControlExpanded;
            }
        }
    }

    public final class SettingsObserver extends ContentObserver {
        public final Uri mAmbientVolumeUri;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.mAmbientVolumeUri = Settings.Global.getUriFor("hearing_device_local_ambient_volume");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (this.mAmbientVolumeUri.equals(uri)) {
                Objects.toString(HearingDeviceLocalDataManager.this);
                HearingDeviceLocalDataManager.this.getLocalDataFromSettings();
            }
        }
    }

    public HearingDeviceLocalDataManager(Context context) {
        this.mContext = context;
        if (ThreadUtils.sMainThreadHandler == null) {
            ThreadUtils.sMainThreadHandler = new Handler(Looper.getMainLooper());
        }
        this.mSettingsObserver = new SettingsObserver(ThreadUtils.sMainThreadHandler);
    }

    public final synchronized void flush() {
        if (this.mIsStarted) {
            putAmbientVolumeSettings();
        }
    }

    public final Data get(BluetoothDevice bluetoothDevice) {
        Data data;
        int i = 0;
        if (!this.mIsStarted) {
            Log.w("HearingDeviceDataMgr", "Manager is not started. Please call start() first.");
            return new Data(i);
        }
        synchronized (sLock) {
            data = (Data) ((HashMap) this.mAddrToDataMap).getOrDefault(bluetoothDevice.getAnonymizedAddress(), new Data(i));
        }
        return data;
    }

    public final void getLocalDataFromSettings() {
        synchronized (sLock) {
            Map fromSettings = parseFromSettings();
            final Map map = this.mAddrToDataMap;
            ((ArrayMap) fromSettings).forEach(new BiConsumer() { // from class: com.android.settingslib.bluetooth.HearingDeviceLocalDataManager$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    HearingDeviceLocalDataManager hearingDeviceLocalDataManager = this.f$0;
                    Map map2 = map;
                    String str = (String) obj;
                    HearingDeviceLocalDataManager.Data data = (HearingDeviceLocalDataManager.Data) obj2;
                    hearingDeviceLocalDataManager.getClass();
                    HearingDeviceLocalDataManager.Data data2 = (HearingDeviceLocalDataManager.Data) ((HashMap) map2).get(str);
                    if ((data2 == null || !data2.equals(data)) && hearingDeviceLocalDataManager.mListener != null) {
                        hearingDeviceLocalDataManager.mListenerExecutor.execute(new HearingDeviceLocalDataManager$$ExternalSyntheticLambda1(hearingDeviceLocalDataManager, str, data, 0));
                    }
                }
            });
            ((HashMap) this.mAddrToDataMap).clear();
            ((HashMap) this.mAddrToDataMap).putAll(fromSettings);
        }
    }

    public final Map parseFromSettings() {
        String stringForUser = Settings.Global.getStringForUser(this.mContext.getContentResolver(), "hearing_device_local_ambient_volume", 0);
        ArrayMap arrayMap = new ArrayMap();
        if (stringForUser != null && !stringForUser.isEmpty()) {
            for (String str : stringForUser.split(";")) {
                KeyValueListParser keyValueListParser = new KeyValueListParser(',');
                keyValueListParser.setString(str);
                String string = keyValueListParser.getString("addr", "");
                if (!string.isEmpty()) {
                    Data.Builder builder = new Data.Builder();
                    int i = Data.$r8$clinit;
                    builder.mAmbient = keyValueListParser.getInt("ambient", Integer.MIN_VALUE);
                    builder.mGroupAmbient = keyValueListParser.getInt("group_ambient", Integer.MIN_VALUE);
                    builder.mAmbientControlExpanded = keyValueListParser.getBoolean("control_expanded", false);
                    arrayMap.put(string, new Data(builder.mAmbient, builder.mGroupAmbient, builder.mAmbientControlExpanded));
                }
            }
        }
        return arrayMap;
    }

    public final void put(BluetoothDevice bluetoothDevice, Data data) {
        ListeningExecutorService listeningExecutorService;
        if (bluetoothDevice == null) {
            return;
        }
        synchronized (sLock) {
            try {
                String anonymizedAddress = bluetoothDevice.getAnonymizedAddress();
                ((HashMap) this.mAddrToDataMap).put(anonymizedAddress, data);
                if (this.mListener != null && (listeningExecutorService = this.mListenerExecutor) != null) {
                    listeningExecutorService.execute(new HearingDeviceLocalDataManager$$ExternalSyntheticLambda1(this, anonymizedAddress, data, 1));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void putAmbientVolumeSettings() {
        synchronized (sLock) {
            try {
                final StringBuilder sb = new StringBuilder();
                for (Map.Entry entry : ((HashMap) this.mAddrToDataMap).entrySet()) {
                    sb.append("addr");
                    sb.append("=");
                    sb.append((String) entry.getKey());
                    sb.append(((Data) entry.getValue()).toSettingsFormat());
                    sb.append(";");
                }
                ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.settingslib.bluetooth.HearingDeviceLocalDataManager$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        Settings.Global.putStringForUser(this.f$0.mContext.getContentResolver(), "hearing_device_local_ambient_volume", sb.toString(), 0);
                    }
                });
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final synchronized void start() {
        if (this.mIsStarted) {
            return;
        }
        this.mIsStarted = true;
        getLocalDataFromSettings();
        SettingsObserver settingsObserver = this.mSettingsObserver;
        this.mContext.getContentResolver().registerContentObserver(settingsObserver.mAmbientVolumeUri, false, settingsObserver, 0);
    }

    public final void updateAmbient(BluetoothDevice bluetoothDevice, int i) {
        if (!this.mIsStarted) {
            Log.w("HearingDeviceDataMgr", "Manager is not started. Please call start() first.");
            return;
        }
        if (bluetoothDevice == null) {
            return;
        }
        synchronized (sLock) {
            try {
                Data data = get(bluetoothDevice);
                if (i == data.ambient) {
                    return;
                }
                Data.Builder builder = new Data.Builder(data);
                builder.mAmbient = i;
                put(bluetoothDevice, new Data(builder.mAmbient, builder.mGroupAmbient, builder.mAmbientControlExpanded));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateAmbientControlExpanded(BluetoothDevice bluetoothDevice, boolean z) {
        if (!this.mIsStarted) {
            Log.w("HearingDeviceDataMgr", "Manager is not started. Please call start() first.");
            return;
        }
        if (bluetoothDevice == null) {
            return;
        }
        synchronized (sLock) {
            try {
                Data data = get(bluetoothDevice);
                if (z == data.ambientControlExpanded) {
                    return;
                }
                Data.Builder builder = new Data.Builder(data);
                builder.mAmbientControlExpanded = z;
                put(bluetoothDevice, new Data(builder.mAmbient, builder.mGroupAmbient, builder.mAmbientControlExpanded));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateGroupAmbient(BluetoothDevice bluetoothDevice, int i) {
        if (!this.mIsStarted) {
            Log.w("HearingDeviceDataMgr", "Manager is not started. Please call start() first.");
            return;
        }
        if (bluetoothDevice == null) {
            return;
        }
        synchronized (sLock) {
            try {
                Data data = get(bluetoothDevice);
                if (i == data.groupAmbient) {
                    return;
                }
                Data.Builder builder = new Data.Builder(data);
                builder.mGroupAmbient = i;
                put(bluetoothDevice, new Data(builder.mAmbient, builder.mGroupAmbient, builder.mAmbientControlExpanded));
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
