package com.android.systemui.unfold.util;

import android.view.View;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
