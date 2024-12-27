package com.android.server.biometrics.sensors;

import android.hardware.fingerprint.IUdfpsOverlayController;
import android.hardware.fingerprint.IUdfpsOverlayControllerCallback;
import android.os.RemoteException;
import android.util.Slog;

import java.util.Optional;

public final class SensorOverlays {
    public final Optional mUdfpsOverlayController = Optional.ofNullable(null);

    public final void hide(int i) {
        if (this.mUdfpsOverlayController.isPresent()) {
            try {
                ((IUdfpsOverlayController) this.mUdfpsOverlayController.get()).hideUdfpsOverlay(i);
            } catch (RemoteException e) {
                Slog.e("SensorOverlays", "Remote exception when hiding the UDFPS overlay", e);
            }
        }
    }

    public final void show(int i, int i2, final AcquisitionClient acquisitionClient) {
        if (this.mUdfpsOverlayController.isPresent()) {
            try {
                ((IUdfpsOverlayController) this.mUdfpsOverlayController.get())
                        .showUdfpsOverlay(
                                acquisitionClient.mRequestId,
                                i,
                                i2,
                                new IUdfpsOverlayControllerCallback
                                        .Stub() { // from class:
                                                  // com.android.server.biometrics.sensors.SensorOverlays.1
                                    public final void onUserCanceled() {
                                        AcquisitionClient.this.onUserCanceled();
                                    }
                                });
            } catch (RemoteException e) {
                Slog.e("SensorOverlays", "Remote exception when showing the UDFPS overlay", e);
            }
        }
    }
}
