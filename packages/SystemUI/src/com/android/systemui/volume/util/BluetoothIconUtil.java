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

/* loaded from: classes3.dex */
public final class BluetoothIconUtil {
    public static final BluetoothIconUtil INSTANCE = new BluetoothIconUtil();

    @Retention(RetentionPolicy.RUNTIME)
    public @interface SamsungStandard {
        public static final Companion Companion = Companion.$$INSTANCE;

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
        byte[] bArrSemGetManufacturerData = bluetoothDevice.semGetManufacturerData();
        if (bArrSemGetManufacturerData == null || bArrSemGetManufacturerData.length <= 8) {
            return -1;
        }
        byte[] bArr = new MfData(bArrSemGetManufacturerData).mDeviceId;
        return ((bArr[0] & 255) << 8) + (bArr[1] & 255);
    }

    public static Drawable getServerIconDrawable(Context context, String str) {
        try {
            int i = Result.$r8$clinit;
            BluetoothUtils.AnonymousClass2 anonymousClass2 = BluetoothUtils.mOnInitCallback;
            CachedBluetoothDevice cachedBluetoothDeviceFindDevice = LocalBluetoothManager.getInstance(context, anonymousClass2).mCachedDeviceManager.findDevice(LocalBluetoothManager.getInstance(context, anonymousClass2).mLocalAdapter.mAdapter.getRemoteDevice(str));
            if (cachedBluetoothDeviceFindDevice == null || !isNextBudsModel(cachedBluetoothDeviceFindDevice.mDevice)) {
                return null;
            }
            return cachedBluetoothDeviceFindDevice.getIconDrawableForSolid();
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            Throwable thM3441exceptionOrNullimpl = Result.m3441exceptionOrNullimpl(new Result.Failure(th));
            if (thM3441exceptionOrNullimpl == null) {
                return null;
            }
            thM3441exceptionOrNullimpl.printStackTrace();
            return null;
        }
    }

    public static Drawable getServerIconDrawableWithDevice(Context context, BluetoothDevice bluetoothDevice) {
        boolean zIsNextBudsModel;
        try {
            zIsNextBudsModel = isNextBudsModel(bluetoothDevice);
            if (!zIsNextBudsModel) {
                return null;
            }
            try {
                CachedBluetoothDevice cachedBluetoothDeviceFindDevice = LocalBluetoothManager.getInstance(context, BluetoothUtils.mOnInitCallback).mCachedDeviceManager.findDevice(bluetoothDevice);
                if (cachedBluetoothDeviceFindDevice == null) {
                    return null;
                }
                INSTANCE.getClass();
                return cachedBluetoothDeviceFindDevice.getIconDrawableForSolid();
            } catch (Exception unused) {
                String name = bluetoothDevice.getName();
                short sSemIconIndex = semIconIndex(bluetoothDevice);
                StringBuilder sb = new StringBuilder("getServerIconDrawableWithDevice isNextBuds = ");
                sb.append(zIsNextBudsModel);
                sb.append(" name = ");
                sb.append(name);
                sb.append(" iconIndex = ");
                TooltipPopup$$ExternalSyntheticOutline0.m(sSemIconIndex, "BluetoothIconUtil", sb);
                return null;
            }
        } catch (Exception unused2) {
            zIsNextBudsModel = false;
        }
    }

    public static final boolean isBuds(BluetoothDevice bluetoothDevice) {
        SamsungStandard.Companion.getClass();
        List listAsList = Arrays.asList(Short.valueOf(SamsungStandard.Companion.GALAXY_BUDS), Short.valueOf(SamsungStandard.Companion.GALAXY_BUDS_LIVE));
        if ((listAsList instanceof Collection) && listAsList.isEmpty()) {
            return false;
        }
        Iterator it = listAsList.iterator();
        while (it.hasNext()) {
            short sShortValue = ((Number) it.next()).shortValue();
            INSTANCE.getClass();
            if (sShortValue == semIconIndex(bluetoothDevice)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isBuds3(BluetoothDevice bluetoothDevice) {
        INSTANCE.getClass();
        short sSemIconIndex = semIconIndex(bluetoothDevice);
        SamsungStandard.Companion.getClass();
        return sSemIconIndex == SamsungStandard.Companion.GALAXY_BUDS3;
    }

    public static final boolean isHomeMini(BluetoothDevice bluetoothDevice) {
        INSTANCE.getClass();
        short sSemIconIndex = semIconIndex(bluetoothDevice);
        SamsungStandard.Companion.getClass();
        return sSemIconIndex == SamsungStandard.Companion.AI_SPEAKER_GALAXY_HOME_MINI;
    }

    public static final boolean isMusicFrame(BluetoothDevice bluetoothDevice) {
        INSTANCE.getClass();
        short sSemIconIndex = semIconIndex(bluetoothDevice);
        SamsungStandard.Companion.getClass();
        return sSemIconIndex == SamsungStandard.Companion.SAMSUNG_MUSIC_FRAME;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0079 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isNextBudsModel(BluetoothDevice bluetoothDevice) {
        boolean z;
        int tag = SmepTag.SUPPORTED_FEATURES.getTag();
        byte[] bArrSemGetMetadata = bluetoothDevice.semGetMetadata(new byte[]{(byte) tag, (byte) (tag >> 8)});
        if (bArrSemGetMetadata == null) {
            bArrSemGetMetadata = null;
        }
        if (bArrSemGetMetadata != null) {
            z = !(bArrSemGetMetadata.length == 0);
        } else {
            z = false;
        }
        if (z) {
            SamsungStandard.Companion.getClass();
            List listAsList = Arrays.asList(Short.valueOf(SamsungStandard.Companion.GALAXY_BUDS_LIVE), Short.valueOf(SamsungStandard.Companion.GALAXY_BUDS3));
            if (!(listAsList instanceof Collection) || !listAsList.isEmpty()) {
                Iterator it = listAsList.iterator();
                while (it.hasNext()) {
                    short sShortValue = ((Number) it.next()).shortValue();
                    INSTANCE.getClass();
                    if (sShortValue == semIconIndex(bluetoothDevice)) {
                        break;
                    }
                }
                if (getDeviceId(bluetoothDevice) <= 354) {
                    return true;
                }
            } else if (getDeviceId(bluetoothDevice) <= 354) {
                break;
            }
        }
        return false;
    }

    public static short semIconIndex(BluetoothDevice bluetoothDevice) {
        byte[] bArrSemGetManufacturerDeviceIconIndex = bluetoothDevice.semGetManufacturerDeviceIconIndex();
        if (bArrSemGetManufacturerDeviceIconIndex == null) {
            return (short) -1;
        }
        return (short) (bArrSemGetManufacturerDeviceIconIndex[1] | (bArrSemGetManufacturerDeviceIconIndex[0] << 8));
    }
}
