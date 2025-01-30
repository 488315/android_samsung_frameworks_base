package com.android.systemui.unfold.util;

import android.os.Trace;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ATraceLoggerTransitionProgressListener implements UnfoldTransitionProgressProvider.TransitionProgressListener {
    public final String traceName;

    public ATraceLoggerTransitionProgressListener(String str) {
        this.traceName = str.concat("#FoldUnfoldTransitionInProgress");
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
