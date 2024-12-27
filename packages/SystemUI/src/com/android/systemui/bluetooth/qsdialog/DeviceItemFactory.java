package com.android.systemui.bluetooth.qsdialog;

import android.content.Context;
import android.media.AudioManager;
import android.util.Pair;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public abstract class DeviceItemFactory {
    public static final Companion Companion = new Companion(null);

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public static DeviceItem createDeviceItem(Context context, CachedBluetoothDevice cachedBluetoothDevice, DeviceItemType deviceItemType, String str, int i, String str2, boolean z) {
            String name = cachedBluetoothDevice.getName();
            Pair btClassDrawableWithDescription = BluetoothUtils.getBtClassDrawableWithDescription(context, cachedBluetoothDevice);
            return new DeviceItem(deviceItemType, cachedBluetoothDevice, name, str, new kotlin.Pair(btClassDrawableWithDescription.first, btClassDrawableWithDescription.second), Integer.valueOf(i), !cachedBluetoothDevice.isBusy(), str2, z);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public abstract DeviceItem create(Context context, CachedBluetoothDevice cachedBluetoothDevice);

    public abstract boolean isFilterMatched(Context context, CachedBluetoothDevice cachedBluetoothDevice, AudioManager audioManager);
}
