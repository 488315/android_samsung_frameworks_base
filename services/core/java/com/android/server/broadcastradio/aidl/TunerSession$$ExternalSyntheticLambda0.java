package com.android.server.broadcastradio.aidl;

import android.hardware.radio.ITunerCallback;

public final /* synthetic */ class TunerSession$$ExternalSyntheticLambda0
        implements RadioModule.AidlCallbackRunnable {
    @Override // com.android.server.broadcastradio.aidl.RadioModule.AidlCallbackRunnable
    public final void run(ITunerCallback iTunerCallback, int i) {
        iTunerCallback.onBackgroundScanComplete();
    }
}
