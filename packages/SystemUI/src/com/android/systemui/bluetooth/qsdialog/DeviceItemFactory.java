package com.android.systemui.bluetooth.qsdialog;

import android.content.Context;
import android.util.Pair;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.systemui.R;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class DeviceItemFactory {
    public static final Companion Companion = new Companion(null);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static DeviceItem createDeviceItem$default(Companion companion, CachedBluetoothDevice cachedBluetoothDevice, DeviceItemType deviceItemType, String str, int i, String str2, boolean z) {
            companion.getClass();
            String name = cachedBluetoothDevice.getName();
            Pair drawableWithDescription = cachedBluetoothDevice.getDrawableWithDescription();
            return new DeviceItem(deviceItemType, cachedBluetoothDevice, name, str, new kotlin.Pair(drawableWithDescription.first, drawableWithDescription.second), Integer.valueOf(i), !cachedBluetoothDevice.isBusy(), str2, z, R.drawable.ic_settings_24dp);
        }

        private Companion() {
        }
    }

    public abstract DeviceItem create(Context context, CachedBluetoothDevice cachedBluetoothDevice);

    public abstract boolean isFilterMatched(Context context, CachedBluetoothDevice cachedBluetoothDevice, boolean z);
}
