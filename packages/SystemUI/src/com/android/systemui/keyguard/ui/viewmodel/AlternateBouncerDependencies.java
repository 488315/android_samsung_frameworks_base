package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.ui.SwipeUpAnywhereGestureHandler;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.TouchHandlingViewLogger;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.statusbar.gesture.TapGestureDetector;
import dagger.Lazy;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AlternateBouncerDependencies {
    public final TouchHandlingViewLogger logger;
    public final AlternateBouncerMessageAreaViewModel messageAreaViewModel;
    public final PowerInteractor powerInteractor;
    public final SwipeUpAnywhereGestureHandler swipeUpAnywhereGestureHandler;
    public final TapGestureDetector tapGestureDetector;
    public final Lazy udfpsAccessibilityOverlayViewModel;
    public final AlternateBouncerUdfpsIconViewModel udfpsIconViewModel;
    public final AlternateBouncerViewModel viewModel;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public AlternateBouncerDependencies(AlternateBouncerViewModel alternateBouncerViewModel, SwipeUpAnywhereGestureHandler swipeUpAnywhereGestureHandler, TapGestureDetector tapGestureDetector, AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel, Lazy lazy, AlternateBouncerMessageAreaViewModel alternateBouncerMessageAreaViewModel, PowerInteractor powerInteractor, LogBuffer logBuffer) {
        this.viewModel = alternateBouncerViewModel;
        this.swipeUpAnywhereGestureHandler = swipeUpAnywhereGestureHandler;
        this.tapGestureDetector = tapGestureDetector;
        this.udfpsIconViewModel = alternateBouncerUdfpsIconViewModel;
        this.udfpsAccessibilityOverlayViewModel = lazy;
        this.messageAreaViewModel = alternateBouncerMessageAreaViewModel;
        this.powerInteractor = powerInteractor;
        this.logger = new TouchHandlingViewLogger(logBuffer, "AlternateBouncer");
    }
}
