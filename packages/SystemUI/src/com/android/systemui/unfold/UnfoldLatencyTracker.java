package com.android.systemui.unfold;

import android.content.ContentResolver;
import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import android.os.Trace;
import com.android.internal.util.LatencyTracker;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.unfold.util.ScaleAwareTransitionProgressProvider;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlin.jvm.internal.Intrinsics;

public final class UnfoldLatencyTracker implements ScreenLifecycle.Observer, UnfoldTransitionProgressProvider.TransitionProgressListener {
    public final ContentResolver contentResolver;
    public final Context context;
    public final DeviceStateManager deviceStateManager;
    public final FoldStateListener foldStateListener;
    public Boolean folded;
    public Boolean isTransitionEnabled;
    public final LatencyTracker latencyTracker;
    public final ScreenLifecycle screenLifecycle;
    public final Optional transitionProgressProvider;
    public final Executor uiBgExecutor;
    public boolean unfoldInProgress;

    public final class FoldStateListener extends DeviceStateManager.FoldStateListener {
        public FoldStateListener(final UnfoldLatencyTracker unfoldLatencyTracker, Context context) {
            super(context, new Consumer() { // from class: com.android.systemui.unfold.UnfoldLatencyTracker.FoldStateListener.1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    Boolean bool = (Boolean) obj;
                    UnfoldLatencyTracker unfoldLatencyTracker2 = UnfoldLatencyTracker.this;
                    Intrinsics.checkNotNull(bool);
                    boolean booleanValue = bool.booleanValue();
                    Boolean bool2 = unfoldLatencyTracker2.folded;
                    if (Intrinsics.areEqual(bool2, bool)) {
                        return;
                    }
                    unfoldLatencyTracker2.folded = bool;
                    if (bool2 == null || booleanValue) {
                        return;
                    }
                    boolean z = false;
                    if (!unfoldLatencyTracker2.unfoldInProgress) {
                        unfoldLatencyTracker2.unfoldInProgress = true;
                        unfoldLatencyTracker2.latencyTracker.onActionStart(13);
                        Trace.asyncTraceBegin(4096L, "Switch displays during unfold", 0);
                    }
                    if (unfoldLatencyTracker2.transitionProgressProvider.isPresent()) {
                        ScaleAwareTransitionProgressProvider.Companion companion = ScaleAwareTransitionProgressProvider.Companion;
                        ContentResolver contentResolver = unfoldLatencyTracker2.contentResolver;
                        companion.getClass();
                        if (ScaleAwareTransitionProgressProvider.Companion.areAnimationsEnabled(contentResolver)) {
                            z = true;
                        }
                    }
                    unfoldLatencyTracker2.isTransitionEnabled = Boolean.valueOf(z);
                }
            });
        }
    }

    public UnfoldLatencyTracker(LatencyTracker latencyTracker, DeviceStateManager deviceStateManager, Optional<UnfoldTransitionProgressProvider> optional, Executor executor, Context context, ContentResolver contentResolver, ScreenLifecycle screenLifecycle) {
        this.latencyTracker = latencyTracker;
        this.deviceStateManager = deviceStateManager;
        this.transitionProgressProvider = optional;
        this.uiBgExecutor = executor;
        this.context = context;
        this.contentResolver = contentResolver;
        this.screenLifecycle = screenLifecycle;
        this.foldStateListener = new FoldStateListener(this, context);
    }

    @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
    public final void onScreenTurnedOn() {
        Boolean bool = this.folded;
        Boolean bool2 = Boolean.FALSE;
        if (Intrinsics.areEqual(bool, bool2) && Intrinsics.areEqual(this.isTransitionEnabled, bool2) && this.unfoldInProgress) {
            this.unfoldInProgress = false;
            this.latencyTracker.onActionEnd(13);
            Trace.endAsyncSection("Switch displays during unfold", 0);
        }
    }

    @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
    public final void onTransitionStarted() {
        if (Intrinsics.areEqual(this.folded, Boolean.FALSE) && Intrinsics.areEqual(this.isTransitionEnabled, Boolean.TRUE) && this.unfoldInProgress) {
            this.unfoldInProgress = false;
            this.latencyTracker.onActionEnd(13);
            Trace.endAsyncSection("Switch displays during unfold", 0);
        }
    }
}
