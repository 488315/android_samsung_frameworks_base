package com.android.systemui.screenshot.ui.viewmodel;

import android.view.accessibility.AccessibilityManager;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class ScreenshotViewModel {
    public final StateFlowImpl _actions;
    public final StateFlowImpl _animationState;
    public final StateFlowImpl _badge;
    public final StateFlowImpl _isAnimating;
    public final StateFlowImpl _preview;
    public final StateFlowImpl _previewAction;
    public final StateFlowImpl _scrollableRect;
    public final StateFlowImpl _scrollingScrim;
    public final AccessibilityManager accessibilityManager;
    public final StateFlowImpl actions;
    public final StateFlowImpl animationState;
    public final StateFlowImpl badge;
    public final StateFlowImpl isAnimating;
    public final StateFlowImpl preview;
    public final StateFlowImpl previewAction;
    public final StateFlowImpl scrollableRect;
    public final StateFlowImpl scrollingScrim;

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

    public ScreenshotViewModel(AccessibilityManager accessibilityManager) {
        this.accessibilityManager = accessibilityManager;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._preview = stateFlowImplMutableStateFlow;
        this.preview = stateFlowImplMutableStateFlow;
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(null);
        this._scrollingScrim = stateFlowImplMutableStateFlow2;
        this.scrollingScrim = stateFlowImplMutableStateFlow2;
        StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(null);
        this._badge = stateFlowImplMutableStateFlow3;
        this.badge = stateFlowImplMutableStateFlow3;
        StateFlowImpl stateFlowImplMutableStateFlow4 = StateFlowKt.MutableStateFlow(null);
        this._previewAction = stateFlowImplMutableStateFlow4;
        this.previewAction = stateFlowImplMutableStateFlow4;
        StateFlowImpl stateFlowImplMutableStateFlow5 = StateFlowKt.MutableStateFlow(EmptyList.INSTANCE);
        this._actions = stateFlowImplMutableStateFlow5;
        this.actions = stateFlowImplMutableStateFlow5;
        StateFlowImpl stateFlowImplMutableStateFlow6 = StateFlowKt.MutableStateFlow(AnimationState.NOT_STARTED);
        this._animationState = stateFlowImplMutableStateFlow6;
        this.animationState = stateFlowImplMutableStateFlow6;
        StateFlowImpl stateFlowImplMutableStateFlow7 = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._isAnimating = stateFlowImplMutableStateFlow7;
        this.isAnimating = stateFlowImplMutableStateFlow7;
        StateFlowImpl stateFlowImplMutableStateFlow8 = StateFlowKt.MutableStateFlow(null);
        this._scrollableRect = stateFlowImplMutableStateFlow8;
        this.scrollableRect = stateFlowImplMutableStateFlow8;
    }
}
