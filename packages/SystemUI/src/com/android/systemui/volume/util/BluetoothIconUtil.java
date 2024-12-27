package com.android.systemui.volume.util;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.samsung.android.bluetooth.SmepTag;
import com.samsung.android.settingslib.bluetooth.scsp.ScspUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Result;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BluetoothIconUtil {
    public static final BluetoothIconUtil INSTANCE = new BluetoothIconUtil();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SamsungStandard {
        public static final Companion Companion = Companion.$$INSTANCE;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Companion {
            public static final /* synthetic */ Companion $$INSTANCE = new Companion();
            public static final short GALAXY_BUDS = 5379;
            public static final short GALAXY_BUDS_LIVE = 5380;
            public static final short GALAXY_BUDS3 = 5381;
            public static final short AI_SPEAKER_GALAXY_HOME_MINI = 10242;

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

    public static Drawable getIconDrawable(Context context, String str) {
        BluetoothUtils.AnonymousClass2 anonymousClass2 = BluetoothUtils.mOnInitCallback;
        CachedBluetoothDevice findDevice = LocalBluetoothManager.getInstance(context, anonymousClass2).mCachedDeviceManager.findDevice(LocalBluetoothManager.getInstance(context, anonymousClass2).mLocalAdapter.mAdapter.getRemoteDevice(str));
        if (findDevice != null) {
            return findDevice.getIconDrawable(true);
        }
        return null;
    }

    public static Drawable getServerIconDrawable(Context context, String str) {
        try {
            int i = Result.$r8$clinit;
            BluetoothUtils.AnonymousClass2 anonymousClass2 = BluetoothUtils.mOnInitCallback;
            CachedBluetoothDevice findDevice = LocalBluetoothManager.getInstance(context, anonymousClass2).mCachedDeviceManager.findDevice(LocalBluetoothManager.getInstance(context, anonymousClass2).mLocalAdapter.mAdapter.getRemoteDevice(str));
            if (findDevice == null || !isNextBudsModel(findDevice.mDevice)) {
                return null;
            }
            return getServerIconDrawable(context, findDevice, 0);
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(new Result.Failure(th));
            if (m2527exceptionOrNullimpl != null) {
                m2527exceptionOrNullimpl.printStackTrace();
            }
            return null;
        }
    }

    public static Drawable getServerIconDrawableWithDevice(Context context, BluetoothDevice bluetoothDevice, int i) {
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
                return getServerIconDrawable(context, findDevice, i);
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
        List listOf = CollectionsKt__CollectionsKt.listOf(Short.valueOf(SamsungStandard.Companion.GALAXY_BUDS), Short.valueOf(SamsungStandard.Companion.GALAXY_BUDS_LIVE));
        if ((listOf instanceof Collection) && listOf.isEmpty()) {
            return false;
        }
        Iterator it = listOf.iterator();
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

    public static boolean isBudsSeries(BluetoothDevice bluetoothDevice) {
        int tag = SmepTag.SUPPORTED_FEATURES.getTag();
        byte[] semGetMetadata = bluetoothDevice.semGetMetadata(new byte[]{(byte) tag, (byte) (tag >> 8)});
        if (!(semGetMetadata instanceof byte[])) {
            semGetMetadata = null;
        }
        if (semGetMetadata != null) {
            return !(semGetMetadata.length == 0);
        }
        return false;
    }

    public static final boolean isHomeMini(BluetoothDevice bluetoothDevice) {
        INSTANCE.getClass();
        short semIconIndex = semIconIndex(bluetoothDevice);
        SamsungStandard.Companion.getClass();
        return semIconIndex == SamsungStandard.Companion.AI_SPEAKER_GALAXY_HOME_MINI;
    }

    public static boolean isNextBudsModel(BluetoothDevice bluetoothDevice) {
        if (isBudsSeries(bluetoothDevice)) {
            SamsungStandard.Companion.getClass();
            List listOf = CollectionsKt__CollectionsKt.listOf(Short.valueOf(SamsungStandard.Companion.GALAXY_BUDS), Short.valueOf(SamsungStandard.Companion.GALAXY_BUDS_LIVE), Short.valueOf(SamsungStandard.Companion.GALAXY_BUDS3));
            if (!(listOf instanceof Collection) || !listOf.isEmpty()) {
                Iterator it = listOf.iterator();
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

    public static Drawable getServerIconDrawable(Context context, CachedBluetoothDevice cachedBluetoothDevice, int i) {
        String resourcePath = cachedBluetoothDevice.getResourcePath();
        BluetoothIconServerUtils bluetoothIconServerUtils = BluetoothIconServerUtils.INSTANCE;
        Intrinsics.checkNotNull(resourcePath);
        bluetoothIconServerUtils.getClass();
        String str = BluetoothIconServerUtils.FILE_EXTENSION_SVG;
        if (i == 0) {
            StringBuilder m = EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(resourcePath);
            m.append(BluetoothIconServerUtils.FILE_NAME_LEFT);
            m.append(str);
            return ScspUtils.getIcon(context, m.toString());
        }
        if (i == 1) {
            StringBuilder m2 = EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(resourcePath);
            m2.append(BluetoothIconServerUtils.FILE_NAME_RIGHT);
            m2.append(str);
            return ScspUtils.getIcon(context, m2.toString());
        }
        if (i == 2) {
            StringBuilder m3 = EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(resourcePath);
            m3.append(BluetoothIconServerUtils.FILE_NAME_PAIR);
            m3.append(str);
            return ScspUtils.getIcon(context, m3.toString());
        }
        if (i != 3) {
            return null;
        }
        StringBuilder m4 = EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(resourcePath);
        m4.append(BluetoothIconServerUtils.FILE_NAME_CASE);
        m4.append(str);
        return ScspUtils.getIcon(context, m4.toString());
    }
}
