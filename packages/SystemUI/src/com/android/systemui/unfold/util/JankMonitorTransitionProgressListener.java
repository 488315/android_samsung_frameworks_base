package com.android.systemui.unfold.util;

import android.view.View;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import java.util.function.Supplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class JankMonitorTransitionProgressListener implements UnfoldTransitionProgressProvider.TransitionProgressListener {
    public final Supplier attachedViewProvider;
    public final InteractionJankMonitor interactionJankMonitor = InteractionJankMonitor.getInstance();

    public JankMonitorTransitionProgressListener(Supplier<View> supplier) {
        this.attachedViewProvider = supplier;
    }

    @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
    public final void onTransitionFinished() {
        this.interactionJankMonitor.end(44);
    }

    @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
    public final void onTransitionStarted() {
        this.interactionJankMonitor.begin((View) this.attachedViewProvider.get(), 44);
    }
}
