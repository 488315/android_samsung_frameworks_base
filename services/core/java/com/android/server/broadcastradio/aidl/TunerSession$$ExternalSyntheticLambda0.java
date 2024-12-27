package com.android.server.broadcastradio.aidl;

import android.hardware.radio.ITunerCallback;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class TunerSession$$ExternalSyntheticLambda0
        implements RadioModule.AidlCallbackRunnable {
    @Override // com.android.server.broadcastradio.aidl.RadioModule.AidlCallbackRunnable
    public final void run(ITunerCallback iTunerCallback, int i) {
        iTunerCallback.onBackgroundScanComplete();
    }
}
