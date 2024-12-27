package com.android.server.broadcastradio.hal2;

import android.hardware.radio.ITunerCallback;

public final /* synthetic */ class TunerSession$$ExternalSyntheticLambda1
        implements RadioModule.AidlCallbackRunnable {
    @Override // com.android.server.broadcastradio.hal2.RadioModule.AidlCallbackRunnable
    public final void run(ITunerCallback iTunerCallback) {
        iTunerCallback.onBackgroundScanComplete();
    }
}
