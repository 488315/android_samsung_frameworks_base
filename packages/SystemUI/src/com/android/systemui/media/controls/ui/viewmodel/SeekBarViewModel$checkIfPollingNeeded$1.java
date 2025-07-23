package com.android.systemui.media.controls.ui.viewmodel;

import android.os.Trace;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SeekBarViewModel$checkIfPollingNeeded$1 implements Runnable {
    public final /* synthetic */ Runnable $cancelPolling;
    public final /* synthetic */ int $traceCookie;

    public SeekBarViewModel$checkIfPollingNeeded$1(Runnable runnable, int i) {
        this.$cancelPolling = runnable;
        this.$traceCookie = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.$cancelPolling.run();
        Trace.endAsyncSection("SeekBarPollingPosition", this.$traceCookie);
    }
}
