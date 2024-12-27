package com.android.server.autofill;

import android.os.ICancellationSignal;
import android.os.RemoteException;
import android.util.Slog;

public final /* synthetic */ class RemoteAugmentedAutofillService$$ExternalSyntheticLambda4
        implements Runnable {
    public final /* synthetic */ ICancellationSignal f$0;

    @Override // java.lang.Runnable
    public final void run() {
        ICancellationSignal iCancellationSignal = this.f$0;
        int i = RemoteAugmentedAutofillService.$r8$clinit;
        try {
            iCancellationSignal.cancel();
        } catch (RemoteException e) {
            Slog.e("RemoteAugmentedAutofillService", "Error requesting a cancellation", e);
        }
    }
}
