package com.android.server.deviceidle;

import android.content.Context;
import android.os.Handler;

import com.android.server.DeviceIdleInternal;
import com.android.server.LocalServices;

public final class TvConstraintController implements ConstraintController {
    public final BluetoothConstraint mBluetoothConstraint;
    public final DeviceIdleInternal mDeviceIdleService;

    public TvConstraintController(Context context, Handler handler) {
        DeviceIdleInternal deviceIdleInternal =
                (DeviceIdleInternal) LocalServices.getService(DeviceIdleInternal.class);
        this.mDeviceIdleService = deviceIdleInternal;
        this.mBluetoothConstraint =
                context.getPackageManager().hasSystemFeature("android.hardware.bluetooth")
                        ? new BluetoothConstraint(context, handler, deviceIdleInternal)
                        : null;
    }

    public final void start() {
        BluetoothConstraint bluetoothConstraint = this.mBluetoothConstraint;
        if (bluetoothConstraint != null) {
            this.mDeviceIdleService.registerDeviceIdleConstraint(
                    bluetoothConstraint, "bluetooth", 1);
        }
    }

    public final void stop() {
        BluetoothConstraint bluetoothConstraint = this.mBluetoothConstraint;
        if (bluetoothConstraint != null) {
            this.mDeviceIdleService.unregisterDeviceIdleConstraint(bluetoothConstraint);
        }
    }
}
