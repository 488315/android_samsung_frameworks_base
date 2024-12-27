package com.android.server.broadcastradio.hal2;

import android.hardware.radio.ITunerCallback;
import com.android.server.broadcastradio.hal2.RadioModule;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class TunerSession$$ExternalSyntheticLambda1 implements RadioModule.AidlCallbackRunnable {
    @Override // com.android.server.broadcastradio.hal2.RadioModule.AidlCallbackRunnable
    public final void run(ITunerCallback iTunerCallback) {
        iTunerCallback.onBackgroundScanComplete();
    }
}
