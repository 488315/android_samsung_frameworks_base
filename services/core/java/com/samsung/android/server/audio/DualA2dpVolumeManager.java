package com.samsung.android.server.audio;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.media.AudioManager;
import android.media.AudioSystem;
import android.os.Handler;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.display.DisplayPowerController2;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.utils.EventLogger;
import com.samsung.android.audio.AudioManagerHelper;
import com.samsung.android.audio.Rune;
import com.samsung.android.media.AudioParameter;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public class DualA2dpVolumeManager {
    public final int MAX_VOLUME;
    public Handler mBrokerHandler;
    public AudioSettingsHelper mSettingHelper;
    public static final float[] VOLUME_TABLE = {DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 0.007943f, 0.01122f, 0.015849f, 0.022387f, 0.031623f, 0.044668f, 0.063096f, 0.089125f, 0.125893f, 0.177828f, 0.251189f, 0.354813f, 0.501187f, 0.707946f, 1.0f};
    public static float[] FINE_VOLUME_TABLE = new float[FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__BIND_CROSS_PROFILE_SERVICE];
    public boolean mDualModeEnabled = false;
    public BluetoothDevice mActiveDevice = null;
    public boolean needVolumeChangedIntent = false;
    public PersistVolumeWorker mPersistVolumeWorker = new PersistVolumeWorker("", -1);
    public final EventLogger mEventLogger = new EventLogger(60, "Dual A2dp Event history");
    public final EventLogger mVolumeLogger = new EventLogger(30, "Dual A2dp Volume history");
    public int mMainVolume = -1;
    public int mMinVolume = 160;
    public final ArrayMap mConnectedDevicesVolume = new ArrayMap(2);
    public final ArrayMap mAbsVolSupported = new ArrayMap(2);
    public final ArrayMap mStoredAbsVolSupported = new ArrayMap();

    public DualA2dpVolumeManager(Context context, Handler handler, final int i) {
        this.MAX_VOLUME = i * 10;
        AudioExecutor.execute(new Runnable() { // from class: com.samsung.android.server.audio.DualA2dpVolumeManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                DualA2dpVolumeManager.this.lambda$new$0(i);
            }
        });
        this.mSettingHelper = AudioSettingsHelper.getInstance(context);
        this.mBrokerHandler = handler;
    }

    /* renamed from: makeFineMediaVolumeTable, reason: merged with bridge method [inline-methods] */
    public final void lambda$new$0(int i) {
        if (AudioSystem.checkAudioFlinger() != 0) {
            Log.e("AS.DualA2dpManager", "Cannot access audioserver");
            return;
        }
        String parameters = AudioSystem.getParameters(new AudioParameter.Builder().setParam("audioParam").setParam("l_volume_table").build().toString());
        if (TextUtils.isEmpty(parameters)) {
            Log.e("AS.DualA2dpManager", "cannot get volume tables. using default table");
            return;
        }
        float[] fArr = new float[i + 1];
        StringTokenizer stringTokenizer = new StringTokenizer(parameters, KnoxVpnFirewallHelper.DELIMITER);
        int i2 = 0;
        while (stringTokenizer.hasMoreTokens() && i2 <= i) {
            String nextToken = stringTokenizer.nextToken();
            int i3 = i2 + 1;
            try {
                fArr[i2] = Float.parseFloat(nextToken);
                i2 = i3;
            } catch (NumberFormatException unused) {
                Log.w("AS.DualA2dpManager", "invalid value " + nextToken);
                return;
            }
        }
        if (i2 <= i) {
            Log.e("AS.DualA2dpManager", "incorrect volume table, using default table");
            return;
        }
        int i4 = 0;
        while (i4 < i) {
            int i5 = i4 + 1;
            float f = (fArr[i5] - fArr[i4]) / 10.0f;
            for (int i6 = 0; i6 < 10; i6++) {
                FINE_VOLUME_TABLE[(i4 * 10) + i6] = fArr[i4] + (i6 * f);
            }
            i4 = i5;
        }
        FINE_VOLUME_TABLE[this.MAX_VOLUME] = 1.0f;
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x00a2 A[Catch: all -> 0x011a, TryCatch #0 {, blocks: (B:4:0x0003, B:8:0x0040, B:10:0x0048, B:11:0x005f, B:15:0x0059, B:16:0x0086, B:19:0x008c, B:21:0x0090, B:26:0x00a2, B:27:0x0098, B:28:0x00b9, B:30:0x00d1, B:32:0x00d9, B:33:0x0115, B:34:0x0118, B:39:0x010b), top: B:3:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void connectA2dpDevice(BluetoothDevice bluetoothDevice, int i, int i2, int i3, boolean z, BluetoothDevice bluetoothDevice2) {
        int semGetEarProtectLimit;
        synchronized (this.mConnectedDevicesVolume) {
            this.mEventLogger.enqueue(new EventLogger.StringEvent("connect :" + AudioManagerHelper.getAddressForLog(bluetoothDevice) + ",active:" + AudioManagerHelper.getAddressForLog(bluetoothDevice2) + ",state:" + i));
            String address = bluetoothDevice.getAddress();
            if (i == 2) {
                if (i2 == -1) {
                    if (this.mConnectedDevicesVolume.containsKey(bluetoothDevice)) {
                        i2 = ((Integer) this.mConnectedDevicesVolume.getOrDefault(bluetoothDevice, -1)).intValue();
                    } else {
                        i2 = this.mSettingHelper.getBTVolumeIndex(address);
                    }
                    this.mEventLogger.enqueue(new EventLogger.StringEvent("Restore vol:" + i2 + ",default:" + i3));
                    if (i2 == -1) {
                        i2 = i3;
                    }
                }
                if (Rune.SEC_AUDIO_SAFE_MEDIA_VOLUME && z) {
                    if (!Rune.SEC_AUDIO_VOLUME_MONITOR_PHASE_3 && !Rune.SEC_AUDIO_SAFE_MEDIA_VOLUME) {
                        semGetEarProtectLimit = 150;
                        if (i2 > semGetEarProtectLimit) {
                            Log.d("AS.DualA2dpManager", "Set safe media volume  : " + semGetEarProtectLimit);
                            i2 = semGetEarProtectLimit;
                        }
                    }
                    semGetEarProtectLimit = (AudioManager.semGetEarProtectLimit() - 1) * 10;
                    if (i2 > semGetEarProtectLimit) {
                    }
                }
                i2 = getValidIndex(i2);
                this.mConnectedDevicesVolume.put(bluetoothDevice, Integer.valueOf(i2));
                postSaveVolumeDB(address, i2);
                if (!this.mAbsVolSupported.containsKey(address) && this.mStoredAbsVolSupported.containsKey(address)) {
                    Boolean bool = (Boolean) this.mStoredAbsVolSupported.getOrDefault(address, Boolean.FALSE);
                    Log.d("AS.DualA2dpManager", "Set the stored AvrcpAbsoluteVolumeSupported addr=" + address + " supported=" + bool);
                    setAvrcpAbsoluteVolumeSupported(address, bool.booleanValue());
                }
            } else if (i == 0) {
                this.mConnectedDevicesVolume.remove(bluetoothDevice);
                this.mAbsVolSupported.remove(address);
            }
            updateMainVolume(i2);
        }
    }

    public boolean setActiveDevice(BluetoothDevice bluetoothDevice) {
        synchronized (this.mConnectedDevicesVolume) {
            this.mEventLogger.enqueue(new EventLogger.StringEvent("setActive:" + AudioManagerHelper.getAddressForLog(bluetoothDevice) + ",prev:" + AudioManagerHelper.getAddressForLog(this.mActiveDevice)).printLog("AS.DualA2dpManager"));
            boolean z = true;
            if (bluetoothDevice == null) {
                if (this.mActiveDevice == null) {
                    z = false;
                }
                this.mActiveDevice = null;
                return z;
            }
            if (bluetoothDevice.equals(this.mActiveDevice)) {
                z = false;
            }
            this.mActiveDevice = bluetoothDevice;
            return z;
        }
    }

    public void setA2dpDeviceVolume(BluetoothDevice bluetoothDevice, int i) {
        synchronized (this.mConnectedDevicesVolume) {
            this.mVolumeLogger.enqueue(new EventLogger.StringEvent("setVolume:" + AudioManagerHelper.getAddressForLog(bluetoothDevice) + ",idx:" + i));
            if (!this.mConnectedDevicesVolume.containsKey(bluetoothDevice)) {
                postSaveVolumeDB(bluetoothDevice.getAddress(), getValidIndex(i));
            } else {
                this.needVolumeChangedIntent = setVolume(bluetoothDevice, i);
                updateMainVolume(i);
            }
        }
    }

    public void updateIndividualA2dpVolumes(int i) {
        synchronized (this.mConnectedDevicesVolume) {
            this.mVolumeLogger.enqueue(new EventLogger.StringEvent("updateVolume:" + i + ",main:" + this.mMainVolume));
            if (!this.mDualModeEnabled) {
                BluetoothDevice bluetoothDevice = this.mActiveDevice;
                if (bluetoothDevice != null) {
                    setVolume(bluetoothDevice, i);
                    updateMainVolume(i);
                }
                return;
            }
            int i2 = i - this.mMainVolume;
            this.mMainVolume = -1;
            this.mMinVolume = this.MAX_VOLUME;
            int i3 = 0;
            for (int i4 = 0; i4 < this.mConnectedDevicesVolume.size(); i4++) {
                int intValue = ((Integer) this.mConnectedDevicesVolume.valueAt(i4)).intValue();
                if (intValue == this.MAX_VOLUME) {
                    i3++;
                }
                int i5 = intValue + i2;
                setVolume((BluetoothDevice) this.mConnectedDevicesVolume.keyAt(i4), i5);
                if (this.mMainVolume < i5) {
                    this.mMainVolume = i5;
                }
                if (this.mMinVolume > i5) {
                    this.mMinVolume = i5;
                }
                this.mMainVolume = getValidIndex(this.mMainVolume);
                this.mMinVolume = getValidIndex(this.mMinVolume);
            }
            this.needVolumeChangedIntent = i3 != this.mConnectedDevicesVolume.size();
        }
    }

    public boolean shouldVolumeChangedIntent() {
        return this.mDualModeEnabled && this.needVolumeChangedIntent;
    }

    public int getA2dpDeviceVolume(BluetoothDevice bluetoothDevice) {
        int intValue;
        synchronized (this.mConnectedDevicesVolume) {
            intValue = ((Integer) this.mConnectedDevicesVolume.getOrDefault(bluetoothDevice, -1)).intValue();
        }
        return intValue;
    }

    public ArrayMap getA2dpDevices() {
        ArrayMap arrayMap;
        synchronized (this.mConnectedDevicesVolume) {
            arrayMap = new ArrayMap();
            if (this.mDualModeEnabled) {
                arrayMap.putAll(this.mConnectedDevicesVolume);
            } else {
                int intValue = ((Integer) this.mConnectedDevicesVolume.getOrDefault(this.mActiveDevice, -1)).intValue();
                if (intValue != -1) {
                    arrayMap.put(this.mActiveDevice, Integer.valueOf(intValue));
                }
            }
        }
        return arrayMap;
    }

    public int getMainVolume() {
        synchronized (this.mConnectedDevicesVolume) {
            if (this.mDualModeEnabled) {
                return this.mMainVolume;
            }
            return ((Integer) this.mConnectedDevicesVolume.getOrDefault(this.mActiveDevice, -1)).intValue();
        }
    }

    public BluetoothDevice getActiveDevice() {
        BluetoothDevice bluetoothDevice;
        synchronized (this.mConnectedDevicesVolume) {
            bluetoothDevice = this.mActiveDevice;
        }
        return bluetoothDevice;
    }

    public void setDualA2dpMode(boolean z, BluetoothDevice bluetoothDevice) {
        synchronized (this.mConnectedDevicesVolume) {
            this.mDualModeEnabled = z;
            if (z) {
                updateMainVolume();
            } else {
                if (bluetoothDevice != null) {
                    this.mActiveDevice = bluetoothDevice;
                    Log.i("AS.DualA2dpManager", "mActiveDevice is changed by dual off address = " + this.mActiveDevice.getAddress());
                }
                this.mMainVolume = ((Integer) this.mConnectedDevicesVolume.getOrDefault(this.mActiveDevice, -1)).intValue();
            }
            this.mEventLogger.enqueue(new EventLogger.StringEvent("Dual:" + z + ",active:" + AudioManagerHelper.getAddressForLog(this.mActiveDevice) + ",vol:" + this.mMainVolume).printLog("AS.DualA2dpManager"));
        }
    }

    public boolean isDualA2dpMode() {
        return this.mDualModeEnabled;
    }

    public void setAvrcpAbsoluteVolumeSupported(String str, final boolean z) {
        this.mEventLogger.enqueue(new EventLogger.StringEvent("AVC:" + AudioManagerHelper.getAddressForLog(str) + ",support:" + z).printLog("AS.DualA2dpManager"));
        synchronized (this.mConnectedDevicesVolume) {
            if (TextUtils.isEmpty(str)) {
                this.mAbsVolSupported.replaceAll(new BiFunction() { // from class: com.samsung.android.server.audio.DualA2dpVolumeManager$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                        Boolean lambda$setAvrcpAbsoluteVolumeSupported$1;
                        lambda$setAvrcpAbsoluteVolumeSupported$1 = DualA2dpVolumeManager.lambda$setAvrcpAbsoluteVolumeSupported$1(z, (String) obj, (Boolean) obj2);
                        return lambda$setAvrcpAbsoluteVolumeSupported$1;
                    }
                });
            } else {
                this.mAbsVolSupported.put(str, Boolean.valueOf(z));
                this.mStoredAbsVolSupported.put(str, Boolean.valueOf(z));
            }
        }
    }

    public static /* synthetic */ Boolean lambda$setAvrcpAbsoluteVolumeSupported$1(boolean z, String str, Boolean bool) {
        return Boolean.valueOf(z);
    }

    public boolean isAvrcpAbsoluteVolumeSupportedForActiveDevice() {
        synchronized (this.mConnectedDevicesVolume) {
            BluetoothDevice bluetoothDevice = this.mActiveDevice;
            if (bluetoothDevice == null) {
                return this.mAbsVolSupported.values().stream().anyMatch(new Predicate() { // from class: com.samsung.android.server.audio.DualA2dpVolumeManager$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        boolean booleanValue;
                        booleanValue = ((Boolean) obj).booleanValue();
                        return booleanValue;
                    }
                });
            }
            Boolean bool = (Boolean) this.mAbsVolSupported.getOrDefault(bluetoothDevice.getAddress(), Boolean.FALSE);
            return bool != null && bool.booleanValue();
        }
    }

    public boolean skipSendingAVRCPVolume(int i) {
        synchronized (this.mConnectedDevicesVolume) {
            boolean z = false;
            if (this.mDualModeEnabled) {
                if ((i == -1 && this.mMainVolume <= 0) || (i == 1 && this.mMinVolume >= this.MAX_VOLUME)) {
                    z = true;
                }
                return z;
            }
            int intValue = ((Integer) this.mConnectedDevicesVolume.getOrDefault(this.mActiveDevice, -1)).intValue();
            if ((i == -1 && intValue <= 0) || (i == 1 && intValue >= this.MAX_VOLUME)) {
                z = true;
            }
            return z;
        }
    }

    public final void updateMainVolume() {
        synchronized (this.mConnectedDevicesVolume) {
            this.mMainVolume = -1;
            this.mMinVolume = this.MAX_VOLUME;
            for (int i = 0; i < this.mConnectedDevicesVolume.size(); i++) {
                int intValue = ((Integer) this.mConnectedDevicesVolume.valueAt(i)).intValue();
                if (this.mMainVolume < intValue) {
                    this.mMainVolume = intValue;
                }
                if (this.mMinVolume > intValue) {
                    this.mMinVolume = intValue;
                }
            }
        }
    }

    public final void updateMainVolume(int i) {
        int validIndex = getValidIndex(i);
        if (validIndex < this.mMinVolume) {
            this.mMinVolume = validIndex;
        }
        if (validIndex > this.mMainVolume) {
            this.mMainVolume = validIndex;
        } else {
            updateMainVolume();
        }
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("");
        printWriter.print("SEC_AUDIO_DUAL_A2DP_VOLUME=");
        printWriter.println(true);
        printWriter.print("  A2dp Devices: ");
        synchronized (this.mConnectedDevicesVolume) {
            for (int i = 0; i < this.mConnectedDevicesVolume.size(); i++) {
                if (i > 0) {
                    printWriter.print(", ");
                }
                printWriter.print(AudioManagerHelper.getAddressForLog(((BluetoothDevice) this.mConnectedDevicesVolume.keyAt(i)).getAddress()));
                printWriter.print(" : ");
                printWriter.print(((Integer) this.mConnectedDevicesVolume.valueAt(i)).intValue());
            }
            printWriter.println();
            printWriter.println("  AvrcpAbsoluteVolumeSupported");
            for (int i2 = 0; i2 < this.mAbsVolSupported.size(); i2++) {
                printWriter.print("  " + AudioManagerHelper.getAddressForLog((String) this.mAbsVolSupported.keyAt(i2)) + " : " + this.mAbsVolSupported.valueAt(i2));
                printWriter.println();
            }
            printWriter.println("  Stored AvrcpAbsoluteVolumeSupported");
            for (int i3 = 0; i3 < this.mStoredAbsVolSupported.size(); i3++) {
                printWriter.print("  " + AudioManagerHelper.getAddressForLog((String) this.mStoredAbsVolSupported.keyAt(i3)) + " : " + this.mStoredAbsVolSupported.valueAt(i3));
                printWriter.println();
            }
            printWriter.println("main fine volume : " + this.mMainVolume);
        }
        printWriter.print("  Active device: ");
        printWriter.println("" + AudioManagerHelper.getAddressForLog(getActiveDevice()));
        printWriter.print("  Dual Sound: ");
        printWriter.println("" + this.mDualModeEnabled);
        this.mEventLogger.dump(printWriter);
        printWriter.println("");
        this.mVolumeLogger.dump(printWriter);
    }

    static {
        int i = 0;
        while (i < 15) {
            float[] fArr = VOLUME_TABLE;
            int i2 = i + 1;
            float f = (fArr[i2] - fArr[i]) / 10.0f;
            for (int i3 = 0; i3 < 10; i3++) {
                FINE_VOLUME_TABLE[(i * 10) + i3] = VOLUME_TABLE[i] + (i3 * f);
            }
            i = i2;
        }
        FINE_VOLUME_TABLE[150] = 1.0f;
    }

    public final void postSaveVolumeDB(String str, int i) {
        if (str.equals(this.mPersistVolumeWorker.mAddress)) {
            this.mBrokerHandler.removeCallbacks(this.mPersistVolumeWorker);
            this.mPersistVolumeWorker.mVolume = i;
        } else {
            this.mPersistVolumeWorker = new PersistVolumeWorker(str, i);
        }
        this.mBrokerHandler.post(this.mPersistVolumeWorker);
    }

    public class PersistVolumeWorker implements Runnable {
        public String mAddress;
        public int mVolume;

        public PersistVolumeWorker(String str, int i) {
            this.mAddress = str;
            this.mVolume = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            DualA2dpVolumeManager.this.mSettingHelper.setBTVolumeIndex(this.mAddress, this.mVolume);
        }
    }

    public final boolean setVolume(BluetoothDevice bluetoothDevice, int i) {
        if (bluetoothDevice == null) {
            Log.e("AS.DualA2dpManager", "null device");
            return false;
        }
        int validIndex = getValidIndex(i);
        Integer num = (Integer) this.mConnectedDevicesVolume.put(bluetoothDevice, Integer.valueOf(validIndex));
        if (num != null && num.intValue() == validIndex) {
            return false;
        }
        postSaveVolumeDB(bluetoothDevice.getAddress(), validIndex);
        return true;
    }

    public final int getValidIndex(int i) {
        int i2 = this.MAX_VOLUME;
        if (i > i2) {
            return i2;
        }
        if (i < 0) {
            return 0;
        }
        return i;
    }
}
