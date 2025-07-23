package com.android.systemui.unfold.util;

import android.os.Trace;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ATraceLoggerTransitionProgressListener implements UnfoldTransitionProgressProvider.TransitionProgressListener {
    public final String traceName;

    public ATraceLoggerTransitionProgressListener(String str, String str2) {
        this.traceName = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, str2, "#FoldUnfoldTransitionInProgress");
    }

    @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
    public final void onTransitionFinished() {
        Trace.endAsyncSection(this.traceName, 0);
    }

    @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
    public final void onTransitionProgress(float f) {
        Trace.setCounter(this.traceName, (long) (f * 100));
    }

    @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
    public final void onTransitionStarted() {
        Trace.beginAsyncSection(this.traceName, 0);
    }
}
