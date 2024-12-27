package com.android.systemui.unfold.util;

import android.os.Trace;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class ATraceLoggerTransitionProgressListener implements UnfoldTransitionProgressProvider.TransitionProgressListener {
    public final String traceName;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        ATraceLoggerTransitionProgressListener create(String str);
    }

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
