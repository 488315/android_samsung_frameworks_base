package com.android.server.autofill;

import android.os.ICancellationSignal;
import android.os.RemoteException;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
