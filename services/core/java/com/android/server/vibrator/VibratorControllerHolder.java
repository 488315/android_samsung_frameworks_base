package com.android.server.vibrator;

import android.frameworks.vibrator.IVibratorController;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;

public final class VibratorControllerHolder implements IBinder.DeathRecipient {
    public IVibratorController mVibratorController;

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        Slog.wtf("VibratorControllerHolder", "binderDied() called unexpectedly.");
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied(IBinder iBinder) {
        IVibratorController iVibratorController = this.mVibratorController;
        if (iVibratorController == null
                || iBinder != ((IVibratorController.Stub.Proxy) iVibratorController).asBinder()) {
            return;
        }
        setVibratorController(null);
    }

    public final void setVibratorController(IVibratorController iVibratorController) {
        try {
            IVibratorController iVibratorController2 = this.mVibratorController;
            if (iVibratorController2 != null) {
                ((IVibratorController.Stub.Proxy) iVibratorController2)
                        .asBinder()
                        .unlinkToDeath(this, 0);
            }
            this.mVibratorController = iVibratorController;
            if (iVibratorController != null) {
                ((IVibratorController.Stub.Proxy) iVibratorController)
                        .asBinder()
                        .linkToDeath(this, 0);
            }
        } catch (RemoteException e) {
            Slog.wtf("VibratorControllerHolder", "Failed to set IVibratorController: " + this, e);
        }
    }
}
