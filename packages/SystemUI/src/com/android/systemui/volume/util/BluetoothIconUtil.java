package com.android.systemui.volume.util;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.samsung.android.bluetooth.SmepTag;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Result;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BluetoothIconUtil {
    public static final BluetoothIconUtil INSTANCE = new BluetoothIconUtil();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SamsungStandard {
        public static final Companion Companion = Companion.$$INSTANCE;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion {
            public static final /* synthetic */ Companion $$INSTANCE = new Companion();
            public static final short GALAXY_BUDS = 5379;
            public static final short GALAXY_BUDS_LIVE = 5380;
            public static final short GALAXY_BUDS3 = 5381;
            public static final short AI_SPEAKER_GALAXY_HOME_MINI = 10242;
            public static final short SAMSUNG_MUSIC_FRAME = 1799;

            private Companion() {
            }
        }
    }

    private BluetoothIconUtil() {
    }

    public static int getDeviceId(BluetoothDevice bluetoothDevice) {
        byte[] semGetManufacturerData = bluetoothDevice.semGetManufacturerData();
        if (semGetManufacturerData == null || semGetManufacturerData.length <= 8) {
            return -1;
        }
        byte[] bArr = new MfData(semGetManufacturerData).mDeviceId;
        return ((bArr[0] & 255) << 8) + (bArr[1] & 255);
    }

    public static Drawable getServerIconDrawable(Context context, String str) {
        try {
            int i = Result.$r8$clinit;
            BluetoothUtils.AnonymousClass2 anonymousClass2 = BluetoothUtils.mOnInitCallback;
            CachedBluetoothDevice findDevice = LocalBluetoothManager.getInstance(context, anonymousClass2).mCachedDeviceManager.findDevice(LocalBluetoothManager.getInstance(context, anonymousClass2).mLocalAdapter.mAdapter.getRemoteDevice(str));
            if (findDevice == null || !isNextBudsModel(findDevice.mDevice)) {
                return null;
            }
            return findDevice.getIconDrawableForSolid();
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(new Result.Failure(th));
            if (m3422exceptionOrNullimpl == null) {
                return null;
            }
            m3422exceptionOrNullimpl.printStackTrace();
            return null;
        }
    }

    public static Drawable getServerIconDrawableWithDevice(Context context, BluetoothDevice bluetoothDevice) {
        boolean z;
        try {
            z = isNextBudsModel(bluetoothDevice);
            if (!z) {
                return null;
            }
            try {
                CachedBluetoothDevice findDevice = LocalBluetoothManager.getInstance(context, BluetoothUtils.mOnInitCallback).mCachedDeviceManager.findDevice(bluetoothDevice);
                if (findDevice == null) {
                    return null;
                }
                INSTANCE.getClass();
                return findDevice.getIconDrawableForSolid();
            } catch (Exception unused) {
                String name = bluetoothDevice.getName();
                short semIconIndex = semIconIndex(bluetoothDevice);
                StringBuilder sb = new StringBuilder("getServerIconDrawableWithDevice isNextBuds = ");
                sb.append(z);
                sb.append(" name = ");
                sb.append(name);
                sb.append(" iconIndex = ");
                TooltipPopup$$ExternalSyntheticOutline0.m(semIconIndex, "BluetoothIconUtil", sb);
                return null;
            }
        } catch (Exception unused2) {
            z = false;
        }
    }

    public static final boolean isBuds(BluetoothDevice bluetoothDevice) {
        SamsungStandard.Companion.getClass();
        List asList = Arrays.asList(Short.valueOf(SamsungStandard.Companion.GALAXY_BUDS), Short.valueOf(SamsungStandard.Companion.GALAXY_BUDS_LIVE));
        if ((asList instanceof Collection) && asList.isEmpty()) {
            return false;
        }
        Iterator it = asList.iterator();
        while (it.hasNext()) {
            short shortValue = ((Number) it.next()).shortValue();
            INSTANCE.getClass();
            if (shortValue == semIconIndex(bluetoothDevice)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isBuds3(BluetoothDevice bluetoothDevice) {
        INSTANCE.getClass();
        short semIconIndex = semIconIndex(bluetoothDevice);
        SamsungStandard.Companion.getClass();
        return semIconIndex == SamsungStandard.Companion.GALAXY_BUDS3;
    }

    public static final boolean isHomeMini(BluetoothDevice bluetoothDevice) {
        INSTANCE.getClass();
        short semIconIndex = semIconIndex(bluetoothDevice);
        SamsungStandard.Companion.getClass();
        return semIconIndex == SamsungStandard.Companion.AI_SPEAKER_GALAXY_HOME_MINI;
    }

    public static final boolean isMusicFrame(BluetoothDevice bluetoothDevice) {
        INSTANCE.getClass();
        short semIconIndex = semIconIndex(bluetoothDevice);
        SamsungStandard.Companion.getClass();
        return semIconIndex == SamsungStandard.Companion.SAMSUNG_MUSIC_FRAME;
    }

    public static boolean isNextBudsModel(BluetoothDevice bluetoothDevice) {
        boolean z;
        int tag = SmepTag.SUPPORTED_FEATURES.getTag();
        byte[] semGetMetadata = bluetoothDevice.semGetMetadata(new byte[]{(byte) tag, (byte) (tag >> 8)});
        if (semGetMetadata == null) {
            semGetMetadata = null;
        }
        if (semGetMetadata != null) {
            z = !(semGetMetadata.length == 0);
        } else {
            z = false;
        }
        if (z) {
            SamsungStandard.Companion.getClass();
            List asList = Arrays.asList(Short.valueOf(SamsungStandard.Companion.GALAXY_BUDS_LIVE), Short.valueOf(SamsungStandard.Companion.GALAXY_BUDS3));
            if (!(asList instanceof Collection) || !asList.isEmpty()) {
                Iterator it = asList.iterator();
                while (it.hasNext()) {
                    short shortValue = ((Number) it.next()).shortValue();
                    INSTANCE.getClass();
                    if (shortValue == semIconIndex(bluetoothDevice)) {
                        break;
                    }
                }
            }
            if (getDeviceId(bluetoothDevice) > 354) {
                return true;
            }
        }
        return false;
    }

    public static short semIconIndex(BluetoothDevice bluetoothDevice) {
        byte[] semGetManufacturerDeviceIconIndex = bluetoothDevice.semGetManufacturerDeviceIconIndex();
        if (semGetManufacturerDeviceIconIndex == null) {
            return (short) -1;
        }
        return (short) (semGetManufacturerDeviceIconIndex[1] | (semGetManufacturerDeviceIconIndex[0] << 8));
    }
}
