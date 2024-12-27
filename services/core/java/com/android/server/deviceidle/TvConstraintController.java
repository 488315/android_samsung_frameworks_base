package com.android.server.deviceidle;

import android.content.Context;
import android.os.Handler;
import com.android.server.DeviceIdleInternal;
import com.android.server.LocalServices;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class TvConstraintController implements ConstraintController {
    public final BluetoothConstraint mBluetoothConstraint;
    public final DeviceIdleInternal mDeviceIdleService;

    public TvConstraintController(Context context, Handler handler) {
        DeviceIdleInternal deviceIdleInternal = (DeviceIdleInternal) LocalServices.getService(DeviceIdleInternal.class);
        this.mDeviceIdleService = deviceIdleInternal;
        this.mBluetoothConstraint = context.getPackageManager().hasSystemFeature("android.hardware.bluetooth") ? new BluetoothConstraint(context, handler, deviceIdleInternal) : null;
    }

    public final void start() {
        BluetoothConstraint bluetoothConstraint = this.mBluetoothConstraint;
        if (bluetoothConstraint != null) {
            this.mDeviceIdleService.registerDeviceIdleConstraint(bluetoothConstraint, "bluetooth", 1);
        }
    }

    public final void stop() {
        BluetoothConstraint bluetoothConstraint = this.mBluetoothConstraint;
        if (bluetoothConstraint != null) {
            this.mDeviceIdleService.unregisterDeviceIdleConstraint(bluetoothConstraint);
        }
    }
}
