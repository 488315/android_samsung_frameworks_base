package com.samsung.android.server.audio;

import android.bluetooth.BluetoothDevice;
import android.content.ContentValues;
import android.content.Context;
import android.media.AudioSystem;
import android.os.Handler;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.utils.EventLogger;

import com.samsung.android.audio.AudioManagerHelper;
import com.samsung.android.media.AudioParameter;

import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.function.BiFunction;

public final class DualA2dpVolumeManager {
    public final int MAX_VOLUME;
    public final Handler mBrokerHandler;
    public final AudioSettingsHelper mSettingHelper;
    public static final float[] VOLUME_TABLE = {
        FullScreenMagnificationGestureHandler.MAX_SCALE,
        0.007943f,
        0.01122f,
        0.015849f,
        0.022387f,
        0.031623f,
        0.044668f,
        0.063096f,
        0.089125f,
        0.125893f,
        0.177828f,
        0.251189f,
        0.354813f,
        0.501187f,
        0.707946f,
        1.0f
    };
    public static final float[] FINE_VOLUME_TABLE =
            new float[FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__BIND_CROSS_PROFILE_SERVICE];
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

    public final class PersistVolumeWorker implements Runnable {
        public final String mAddress;
        public int mVolume;

        public PersistVolumeWorker(String str, int i) {
            this.mAddress = str;
            this.mVolume = i;
        }

        @Override // java.lang.Runnable
        public final void run() {
            AudioSettingsHelper audioSettingsHelper = DualA2dpVolumeManager.this.mSettingHelper;
            String str = this.mAddress;
            int i = this.mVolume;
            audioSettingsHelper.getClass();
            ContentValues contentValues = new ContentValues();
            contentValues.put("_addr", str);
            contentValues.put("_index", Integer.valueOf(i));
            audioSettingsHelper.set(contentValues, "device_addr", "_addr='" + str + "'");
        }
    }

    static {
        int i = 0;
        while (i < 15) {
            float[] fArr = VOLUME_TABLE;
            int i2 = i + 1;
            float f = (fArr[i2] - fArr[i]) / 10.0f;
            for (int i3 = 0; i3 < 10; i3++) {
                FINE_VOLUME_TABLE[(i * 10) + i3] = (i3 * f) + VOLUME_TABLE[i];
            }
            i = i2;
        }
        FINE_VOLUME_TABLE[150] = 1.0f;
    }

    public DualA2dpVolumeManager(Context context, Handler handler, final int i) {
        this.MAX_VOLUME = i * 10;
        AudioExecutor.execute(
                new Runnable() { // from class:
                                 // com.samsung.android.server.audio.DualA2dpVolumeManager$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        DualA2dpVolumeManager dualA2dpVolumeManager = DualA2dpVolumeManager.this;
                        int i2 = i;
                        dualA2dpVolumeManager.getClass();
                        if (AudioSystem.checkAudioFlinger() != 0) {
                            Log.e("AS.DualA2dpManager", "Cannot access audioserver");
                            return;
                        }
                        String parameters =
                                AudioSystem.getParameters(
                                        new AudioParameter.Builder()
                                                .setParam("audioParam")
                                                .setParam("l_volume_table")
                                                .build()
                                                .toString());
                        if (TextUtils.isEmpty(parameters)) {
                            Log.e(
                                    "AS.DualA2dpManager",
                                    "cannot get volume tables. using default table");
                            return;
                        }
                        float[] fArr = new float[i2 + 1];
                        StringTokenizer stringTokenizer = new StringTokenizer(parameters, ";");
                        int i3 = 0;
                        while (stringTokenizer.hasMoreTokens() && i3 <= i2) {
                            String nextToken = stringTokenizer.nextToken();
                            int i4 = i3 + 1;
                            try {
                                fArr[i3] = Float.parseFloat(nextToken);
                                i3 = i4;
                            } catch (NumberFormatException unused) {
                                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                                        "invalid value ", nextToken, "AS.DualA2dpManager");
                                return;
                            }
                        }
                        if (i3 <= i2) {
                            Log.e(
                                    "AS.DualA2dpManager",
                                    "incorrect volume table, using default table");
                            return;
                        }
                        int i5 = 0;
                        while (true) {
                            float[] fArr2 = DualA2dpVolumeManager.FINE_VOLUME_TABLE;
                            if (i5 >= i2) {
                                fArr2[dualA2dpVolumeManager.MAX_VOLUME] = 1.0f;
                                return;
                            }
                            int i6 = i5 + 1;
                            float f = (fArr[i6] - fArr[i5]) / 10.0f;
                            for (int i7 = 0; i7 < 10; i7++) {
                                fArr2[(i5 * 10) + i7] = (i7 * f) + fArr[i5];
                            }
                            i5 = i6;
                        }
                    }
                });
        this.mSettingHelper = AudioSettingsHelper.getInstance(context);
        this.mBrokerHandler = handler;
    }

    public final void dump(PrintWriter printWriter) {
        BluetoothDevice bluetoothDevice;
        printWriter.println("");
        printWriter.print("SEC_AUDIO_DUAL_A2DP_VOLUME=");
        printWriter.println(true);
        printWriter.print("  A2dp Devices: ");
        synchronized (this.mConnectedDevicesVolume) {
            for (int i = 0; i < this.mConnectedDevicesVolume.size(); i++) {
                try {
                    if (i > 0) {
                        printWriter.print(", ");
                    }
                    printWriter.print(
                            AudioManagerHelper.getAddressForLog(
                                    ((BluetoothDevice) this.mConnectedDevicesVolume.keyAt(i))
                                            .getAddress()));
                    printWriter.print(" : ");
                    printWriter.print(
                            ((Integer) this.mConnectedDevicesVolume.valueAt(i)).intValue());
                } catch (Throwable th) {
                    throw th;
                }
            }
            printWriter.println();
            printWriter.println("  AvrcpAbsoluteVolumeSupported");
            for (int i2 = 0; i2 < this.mAbsVolSupported.size(); i2++) {
                printWriter.print(
                        "  "
                                + AudioManagerHelper.getAddressForLog(
                                        (String) this.mAbsVolSupported.keyAt(i2))
                                + " : "
                                + this.mAbsVolSupported.valueAt(i2));
                printWriter.println();
            }
            printWriter.println("  Stored AvrcpAbsoluteVolumeSupported");
            for (int i3 = 0; i3 < this.mStoredAbsVolSupported.size(); i3++) {
                printWriter.print(
                        "  "
                                + AudioManagerHelper.getAddressForLog(
                                        (String) this.mStoredAbsVolSupported.keyAt(i3))
                                + " : "
                                + this.mStoredAbsVolSupported.valueAt(i3));
                printWriter.println();
            }
            printWriter.println("main fine volume : " + this.mMainVolume);
        }
        printWriter.print("  Active device: ");
        synchronized (this.mConnectedDevicesVolume) {
            bluetoothDevice = this.mActiveDevice;
        }
        printWriter.println("" + AudioManagerHelper.getAddressForLog(bluetoothDevice));
        printWriter.print("  Dual Sound: ");
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                new StringBuilder(""), this.mDualModeEnabled, printWriter);
        this.mEventLogger.dump(printWriter);
        printWriter.println("");
        this.mVolumeLogger.dump(printWriter);
    }

    public final int getMainVolume() {
        synchronized (this.mConnectedDevicesVolume) {
            try {
                if (this.mDualModeEnabled) {
                    return this.mMainVolume;
                }
                return ((Integer) this.mConnectedDevicesVolume.getOrDefault(this.mActiveDevice, -1))
                        .intValue();
            } catch (Throwable th) {
                throw th;
            }
        }
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

    public final boolean isAvrcpAbsoluteVolumeSupportedForActiveDevice() {
        synchronized (this.mConnectedDevicesVolume) {
            try {
                BluetoothDevice bluetoothDevice = this.mActiveDevice;
                if (bluetoothDevice == null) {
                    return this.mAbsVolSupported.values().stream()
                            .anyMatch(new DualA2dpVolumeManager$$ExternalSyntheticLambda1());
                }
                Boolean bool =
                        (Boolean)
                                this.mAbsVolSupported.getOrDefault(
                                        bluetoothDevice.getAddress(), Boolean.FALSE);
                return bool != null && bool.booleanValue();
            } finally {
            }
        }
    }

    public final void postSaveVolumeDB(int i, String str) {
        boolean equals = str.equals(this.mPersistVolumeWorker.mAddress);
        Handler handler = this.mBrokerHandler;
        if (equals) {
            handler.removeCallbacks(this.mPersistVolumeWorker);
            this.mPersistVolumeWorker.mVolume = i;
        } else {
            this.mPersistVolumeWorker = new PersistVolumeWorker(str, i);
        }
        handler.post(this.mPersistVolumeWorker);
    }

    public final boolean setActiveDevice(BluetoothDevice bluetoothDevice) {
        synchronized (this.mConnectedDevicesVolume) {
            try {
                EventLogger eventLogger = this.mEventLogger;
                EventLogger.StringEvent stringEvent =
                        new EventLogger.StringEvent(
                                "setActive:"
                                        + AudioManagerHelper.getAddressForLog(bluetoothDevice)
                                        + ",prev:"
                                        + AudioManagerHelper.getAddressForLog(this.mActiveDevice));
                stringEvent.printLog(0, "AS.DualA2dpManager");
                eventLogger.enqueue(stringEvent);
                if (bluetoothDevice == null) {
                    boolean z = this.mActiveDevice != null;
                    this.mActiveDevice = null;
                    return z;
                }
                boolean equals = true ^ bluetoothDevice.equals(this.mActiveDevice);
                this.mActiveDevice = bluetoothDevice;
                return equals;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setAvrcpAbsoluteVolumeSupported(String str, final boolean z) {
        EventLogger eventLogger = this.mEventLogger;
        EventLogger.StringEvent stringEvent =
                new EventLogger.StringEvent(
                        "AVC:" + AudioManagerHelper.getAddressForLog(str) + ",support:" + z);
        stringEvent.printLog(0, "AS.DualA2dpManager");
        eventLogger.enqueue(stringEvent);
        synchronized (this.mConnectedDevicesVolume) {
            try {
                if (TextUtils.isEmpty(str)) {
                    this.mAbsVolSupported.replaceAll(
                            new BiFunction() { // from class:
                                               // com.samsung.android.server.audio.DualA2dpVolumeManager$$ExternalSyntheticLambda2
                                @Override // java.util.function.BiFunction
                                public final Object apply(Object obj, Object obj2) {
                                    return Boolean.valueOf(z);
                                }
                            });
                } else {
                    this.mAbsVolSupported.put(str, Boolean.valueOf(z));
                    this.mStoredAbsVolSupported.put(str, Boolean.valueOf(z));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setDualA2dpMode(BluetoothDevice bluetoothDevice, boolean z) {
        synchronized (this.mConnectedDevicesVolume) {
            try {
                this.mDualModeEnabled = z;
                if (z) {
                    updateMainVolume();
                } else {
                    if (bluetoothDevice != null) {
                        this.mActiveDevice = bluetoothDevice;
                        Log.i(
                                "AS.DualA2dpManager",
                                "mActiveDevice is changed by dual off address = "
                                        + this.mActiveDevice.getAddress());
                    }
                    this.mMainVolume =
                            ((Integer)
                                            this.mConnectedDevicesVolume.getOrDefault(
                                                    this.mActiveDevice, -1))
                                    .intValue();
                }
                EventLogger eventLogger = this.mEventLogger;
                EventLogger.StringEvent stringEvent =
                        new EventLogger.StringEvent(
                                "Dual:"
                                        + z
                                        + ",active:"
                                        + AudioManagerHelper.getAddressForLog(this.mActiveDevice)
                                        + ",vol:"
                                        + this.mMainVolume);
                stringEvent.printLog(0, "AS.DualA2dpManager");
                eventLogger.enqueue(stringEvent);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean setVolume(BluetoothDevice bluetoothDevice, int i) {
        if (bluetoothDevice == null) {
            Log.e("AS.DualA2dpManager", "null device");
            return false;
        }
        int validIndex = getValidIndex(i);
        Integer num =
                (Integer)
                        this.mConnectedDevicesVolume.put(
                                bluetoothDevice, Integer.valueOf(validIndex));
        if (num != null && num.intValue() == validIndex) {
            return false;
        }
        postSaveVolumeDB(validIndex, bluetoothDevice.getAddress());
        return true;
    }

    public final void updateMainVolume() {
        synchronized (this.mConnectedDevicesVolume) {
            try {
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
            } catch (Throwable th) {
                throw th;
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
}
