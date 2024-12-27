package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.ui.SwipeUpAnywhereGestureHandler;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.statusbar.gesture.TapGestureDetector;
import dagger.Lazy;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AlternateBouncerDependencies {
    public final AlternateBouncerMessageAreaViewModel messageAreaViewModel;
    public final PowerInteractor powerInteractor;
    public final SwipeUpAnywhereGestureHandler swipeUpAnywhereGestureHandler;
    public final TapGestureDetector tapGestureDetector;
    public final Lazy udfpsAccessibilityOverlayViewModel;
    public final AlternateBouncerUdfpsIconViewModel udfpsIconViewModel;
    public final AlternateBouncerViewModel viewModel;

    public AlternateBouncerDependencies(AlternateBouncerViewModel alternateBouncerViewModel, SwipeUpAnywhereGestureHandler swipeUpAnywhereGestureHandler, TapGestureDetector tapGestureDetector, AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel, Lazy lazy, AlternateBouncerMessageAreaViewModel alternateBouncerMessageAreaViewModel, PowerInteractor powerInteractor) {
        this.viewModel = alternateBouncerViewModel;
        this.swipeUpAnywhereGestureHandler = swipeUpAnywhereGestureHandler;
        this.tapGestureDetector = tapGestureDetector;
        this.udfpsIconViewModel = alternateBouncerUdfpsIconViewModel;
        this.udfpsAccessibilityOverlayViewModel = lazy;
        this.messageAreaViewModel = alternateBouncerMessageAreaViewModel;
        this.powerInteractor = powerInteractor;
    }
}
