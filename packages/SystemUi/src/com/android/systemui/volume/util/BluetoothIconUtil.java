package com.android.systemui.volume.util;

import android.bluetooth.BluetoothDevice;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BluetoothIconUtil {
    public static final BluetoothIconUtil INSTANCE = new BluetoothIconUtil();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SamsungStandard {
        public static final Companion Companion = Companion.$$INSTANCE;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public static boolean isSameDeviceIconType(BluetoothDevice bluetoothDevice, ArrayList arrayList) {
        byte[] semGetManufacturerDeviceIconIndex = bluetoothDevice.semGetManufacturerDeviceIconIndex();
        if (semGetManufacturerDeviceIconIndex == null) {
            return false;
        }
        final short s = (short) (semGetManufacturerDeviceIconIndex[1] | (semGetManufacturerDeviceIconIndex[0] << 8));
        return !arrayList.stream().filter(new Predicate() { // from class: com.android.systemui.volume.util.BluetoothIconUtil$isSameDeviceIconType$1$1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                Short sh = (Short) obj;
                return sh != null && sh.shortValue() == s;
            }
        }).findFirst().isEmpty();
    }
}
