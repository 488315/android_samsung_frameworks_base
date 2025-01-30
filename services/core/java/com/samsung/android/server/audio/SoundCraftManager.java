package com.samsung.android.server.audio;

import android.bluetooth.BluetoothDevice;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Binder;
import android.provider.Settings;
import android.util.Log;
import com.android.internal.util.FrameworkStatsLog;
import com.samsung.android.server.audio.SoundCraftManager;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public class SoundCraftManager {
    public final ContentResolver mCr;

    enum Buds {
        BUDS2(Arrays.asList(Integer.valueOf(FrameworkStatsLog.f89x74364b3b), Integer.valueOf(FrameworkStatsLog.f80x6bfaed86), Integer.valueOf(FrameworkStatsLog.f79x6bfad700), Integer.valueOf(FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_SHELL), Integer.valueOf(FrameworkStatsLog.f90x54490b7), Integer.valueOf(FrameworkStatsLog.f108xb8c45718), Integer.valueOf(FrameworkStatsLog.f109x9a09c896), 320, 321, 322, 323, Integer.valueOf(FrameworkStatsLog.f58x60da79b1)), "com.samsung.accessory.berrymgr"),
        BUDS2_PRO(Arrays.asList(325, 326, Integer.valueOf(FrameworkStatsLog.TIF_TUNE_CHANGED), Integer.valueOf(FrameworkStatsLog.AUTO_ROTATE_REPORTED)), "com.samsung.accessory.zenithmgr"),
        BUDS_FE(Arrays.asList(330, 331), "com.samsung.accessory.pearlmgr"),
        BUDS3(Arrays.asList(Integer.valueOf(FrameworkStatsLog.DEVICE_ROTATED), 334, 335, 336, Integer.valueOf(FrameworkStatsLog.FACE_DOWN_REPORTED), 338, Integer.valueOf(FrameworkStatsLog.REBOOT_ESCROW_PREPARATION_REPORTED), 347, 348, 349, 350), "com.samsung.accessory.jellymgr"),
        BUDS3_PRO(Arrays.asList(Integer.valueOf(FrameworkStatsLog.REBOOT_ESCROW_LSKF_CAPTURE_REPORTED), Integer.valueOf(FrameworkStatsLog.REBOOT_ESCROW_REBOOT_REPORTED), Integer.valueOf(FrameworkStatsLog.BINDER_LATENCY_REPORTED), 343, 344, Integer.valueOf(FrameworkStatsLog.MAGNIFICATION_USAGE_REPORTED), Integer.valueOf(FrameworkStatsLog.MAGNIFICATION_MODE_WITH_IME_ON_REPORTED), Integer.valueOf(FrameworkStatsLog.INPUTDEVICE_REGISTERED), 352, Integer.valueOf(FrameworkStatsLog.AUTH_PROMPT_AUTHENTICATE_INVOKED), Integer.valueOf(FrameworkStatsLog.AUTH_MANAGER_CAN_AUTHENTICATE_INVOKED)), "com.samsung.accessory.paranmgr"),
        INVALID_BUDS(List.of(-1), "");

        private final List mDeviceIDs;
        private final String mPluginPackageName;

        Buds(List list, String str) {
            this.mPluginPackageName = str;
            this.mDeviceIDs = list;
        }

        public String getPluginPackageName() {
            return this.mPluginPackageName;
        }

        public boolean hasId(int i) {
            return this.mDeviceIDs.contains(Integer.valueOf(i));
        }

        public boolean isSoundCraftSupport() {
            int i = AbstractC31521.f1776xa1f35e57[ordinal()];
            return i == 1 || i == 2;
        }
    }

    /* renamed from: com.samsung.android.server.audio.SoundCraftManager$1 */
    public abstract /* synthetic */ class AbstractC31521 {

        /* renamed from: $SwitchMap$com$samsung$android$server$audio$SoundCraftManager$Buds */
        public static final /* synthetic */ int[] f1776xa1f35e57;

        static {
            int[] iArr = new int[Buds.values().length];
            f1776xa1f35e57 = iArr;
            try {
                iArr[Buds.BUDS3_PRO.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f1776xa1f35e57[Buds.BUDS3.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public SoundCraftManager(Context context) {
        this.mCr = context.getContentResolver();
    }

    public void setSoundCraftEnable(BluetoothDevice bluetoothDevice) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Buds buds = getBuds(getDeviceId(bluetoothDevice));
            if (bluetoothDevice == null) {
                Log.d("SoundCraftManager", "SoundCraft disabled, bt disconnected");
            } else if (buds != Buds.INVALID_BUDS) {
                r4 = buds.isSoundCraftSupport() ? 1 : 0;
                Log.d("SoundCraftManager", "SoundCraft support " + r4 + ", connected device is " + bluetoothDevice.getName());
            }
            Settings.System.putString(this.mCr, "buds_plugin_package_name", buds.getPluginPackageName());
            Settings.System.putInt(this.mCr, "buds_enable", r4);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Buds getBuds(final int i) {
        return (Buds) Arrays.stream(Buds.values()).filter(new Predicate() { // from class: com.samsung.android.server.audio.SoundCraftManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getBuds$0;
                lambda$getBuds$0 = SoundCraftManager.lambda$getBuds$0(i, (SoundCraftManager.Buds) obj);
                return lambda$getBuds$0;
            }
        }).findAny().orElse(Buds.INVALID_BUDS);
    }

    public static /* synthetic */ boolean lambda$getBuds$0(int i, Buds buds) {
        return buds.hasId(i);
    }

    public final int getDeviceId(BluetoothDevice bluetoothDevice) {
        byte[] semGetManufacturerData;
        if (bluetoothDevice == null || (semGetManufacturerData = bluetoothDevice.semGetManufacturerData()) == null || semGetManufacturerData.length <= 8) {
            return -1;
        }
        return new MfData(semGetManufacturerData).getDeviceIdToInt();
    }
}
