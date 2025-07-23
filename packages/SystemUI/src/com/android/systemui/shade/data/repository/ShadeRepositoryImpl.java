package com.android.systemui.shade.data.repository;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeRepositoryImpl implements ShadeRepository {
    public final StateFlowImpl _isShadeLayoutWide;
    public final StateFlowImpl _legacyExpandImmediate;
    public final StateFlowImpl _legacyExpandedOrAwaitingInputTransfer;
    public final StateFlowImpl _legacyIsClosing;
    public final StateFlowImpl _legacyIsQsExpanded;
    public final StateFlowImpl _legacyQsFullscreen;
    public final StateFlowImpl _legacyQsTracking;
    public final StateFlowImpl _legacyShadeExpansion;
    public final StateFlowImpl _legacyShadeTracking;
    public final StateFlowImpl _lockscreenShadeExpansion;
    public final StateFlowImpl _qsExpansion;
    public final StateFlowImpl _udfpsTransitionToFullShadeProgress;
    public final CoroutineScope backgroundScope;
    public final SharedFlowImpl currentFling;
    public final ReadonlyStateFlow isShadeLayoutWide;
    public final ReadonlyStateFlow legacyExpandImmediate;
    public final ReadonlyStateFlow legacyExpandedOrAwaitingInputTransfer;
    public final ReadonlyStateFlow legacyIsClosing;
    public final ReadonlyStateFlow legacyIsQsExpanded;
    public final StateFlowImpl legacyLockscreenShadeTracking;
    public final ReadonlyStateFlow legacyQsFullscreen;
    public final ReadonlyStateFlow legacyQsTracking;
    public final ReadonlyStateFlow legacyShadeExpansion;
    public final ReadonlyStateFlow legacyShadeTracking;
    public final ReadonlyStateFlow lockscreenShadeExpansion;
    public final ReadonlyStateFlow qsExpansion;
    public final ReadonlyStateFlow udfpsTransitionToFullShadeProgress;

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

    public ShadeRepositoryImpl(CoroutineScope coroutineScope) {
        this.backgroundScope = coroutineScope;
        Float valueOf = Float.valueOf(0.0f);
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(valueOf);
        this._qsExpansion = MutableStateFlow;
        this.qsExpansion = FlowKt.asStateFlow(MutableStateFlow);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(valueOf);
        this._lockscreenShadeExpansion = MutableStateFlow2;
        this.lockscreenShadeExpansion = FlowKt.asStateFlow(MutableStateFlow2);
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(valueOf);
        this._udfpsTransitionToFullShadeProgress = MutableStateFlow3;
        this.udfpsTransitionToFullShadeProgress = FlowKt.asStateFlow(MutableStateFlow3);
        this.currentFling = SharedFlowKt.MutableSharedFlow$default(2, 0, BufferOverflow.DROP_OLDEST, 2);
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(valueOf);
        this._legacyShadeExpansion = MutableStateFlow4;
        this.legacyShadeExpansion = FlowKt.asStateFlow(MutableStateFlow4);
        Boolean bool = Boolean.FALSE;
        StateFlowImpl MutableStateFlow5 = StateFlowKt.MutableStateFlow(bool);
        this._legacyShadeTracking = MutableStateFlow5;
        this.legacyShadeTracking = FlowKt.asStateFlow(MutableStateFlow5);
        this.legacyLockscreenShadeTracking = StateFlowKt.MutableStateFlow(bool);
        StateFlowImpl MutableStateFlow6 = StateFlowKt.MutableStateFlow(bool);
        this._legacyQsTracking = MutableStateFlow6;
        this.legacyQsTracking = FlowKt.asStateFlow(MutableStateFlow6);
        StateFlowImpl MutableStateFlow7 = StateFlowKt.MutableStateFlow(bool);
        this._legacyExpandedOrAwaitingInputTransfer = MutableStateFlow7;
        this.legacyExpandedOrAwaitingInputTransfer = FlowKt.asStateFlow(MutableStateFlow7);
        StateFlowImpl MutableStateFlow8 = StateFlowKt.MutableStateFlow(bool);
        this._legacyIsQsExpanded = MutableStateFlow8;
        this.legacyIsQsExpanded = FlowKt.asStateFlow(MutableStateFlow8);
        StateFlowImpl MutableStateFlow9 = StateFlowKt.MutableStateFlow(bool);
        this._legacyExpandImmediate = MutableStateFlow9;
        this.legacyExpandImmediate = FlowKt.asStateFlow(MutableStateFlow9);
        StateFlowImpl MutableStateFlow10 = StateFlowKt.MutableStateFlow(bool);
        this._legacyQsFullscreen = MutableStateFlow10;
        this.legacyQsFullscreen = FlowKt.asStateFlow(MutableStateFlow10);
        StateFlowImpl MutableStateFlow11 = StateFlowKt.MutableStateFlow(bool);
        this._isShadeLayoutWide = MutableStateFlow11;
        this.isShadeLayoutWide = FlowKt.asStateFlow(MutableStateFlow11);
        StateFlowImpl MutableStateFlow12 = StateFlowKt.MutableStateFlow(bool);
        this._legacyIsClosing = MutableStateFlow12;
        this.legacyIsClosing = FlowKt.asStateFlow(MutableStateFlow12);
    }

    public final void setCurrentFling(FlingInfo flingInfo) {
        BuildersKt.launch$default(this.backgroundScope, null, null, new ShadeRepositoryImpl$setCurrentFling$1(this, flingInfo, null), 3);
    }
}
