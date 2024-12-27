package com.android.systemui.shade.data.repository;

import com.android.systemui.Flags;
import com.android.systemui.shade.shared.model.ShadeMode;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ShadeRepositoryImpl implements ShadeRepository {
    public final StateFlowImpl _currentFling;
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
    public final StateFlowImpl _shadeMode;
    public final StateFlowImpl _udfpsTransitionToFullShadeProgress;
    public final ReadonlyStateFlow currentFling;
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
    public final ReadonlyStateFlow shadeMode;
    public final ReadonlyStateFlow udfpsTransitionToFullShadeProgress;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public ShadeRepositoryImpl() {
        Float valueOf = Float.valueOf(0.0f);
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(valueOf);
        this._qsExpansion = MutableStateFlow;
        this.qsExpansion = FlowKt.asStateFlow(MutableStateFlow);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(valueOf);
        this._lockscreenShadeExpansion = MutableStateFlow2;
        this.lockscreenShadeExpansion = FlowKt.asStateFlow(MutableStateFlow2);
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(valueOf);
        this._udfpsTransitionToFullShadeProgress = MutableStateFlow3;
        FlowKt.asStateFlow(MutableStateFlow3);
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(null);
        this._currentFling = MutableStateFlow4;
        this.currentFling = FlowKt.asStateFlow(MutableStateFlow4);
        StateFlowImpl MutableStateFlow5 = StateFlowKt.MutableStateFlow(valueOf);
        this._legacyShadeExpansion = MutableStateFlow5;
        this.legacyShadeExpansion = FlowKt.asStateFlow(MutableStateFlow5);
        Boolean bool = Boolean.FALSE;
        StateFlowImpl MutableStateFlow6 = StateFlowKt.MutableStateFlow(bool);
        this._legacyShadeTracking = MutableStateFlow6;
        this.legacyShadeTracking = FlowKt.asStateFlow(MutableStateFlow6);
        this.legacyLockscreenShadeTracking = StateFlowKt.MutableStateFlow(bool);
        StateFlowImpl MutableStateFlow7 = StateFlowKt.MutableStateFlow(bool);
        this._legacyQsTracking = MutableStateFlow7;
        this.legacyQsTracking = FlowKt.asStateFlow(MutableStateFlow7);
        StateFlowImpl MutableStateFlow8 = StateFlowKt.MutableStateFlow(bool);
        this._legacyExpandedOrAwaitingInputTransfer = MutableStateFlow8;
        this.legacyExpandedOrAwaitingInputTransfer = FlowKt.asStateFlow(MutableStateFlow8);
        StateFlowImpl MutableStateFlow9 = StateFlowKt.MutableStateFlow(bool);
        this._legacyIsQsExpanded = MutableStateFlow9;
        this.legacyIsQsExpanded = FlowKt.asStateFlow(MutableStateFlow9);
        StateFlowImpl MutableStateFlow10 = StateFlowKt.MutableStateFlow(bool);
        this._legacyExpandImmediate = MutableStateFlow10;
        this.legacyExpandImmediate = FlowKt.asStateFlow(MutableStateFlow10);
        StateFlowImpl MutableStateFlow11 = StateFlowKt.MutableStateFlow(bool);
        this._legacyQsFullscreen = MutableStateFlow11;
        this.legacyQsFullscreen = FlowKt.asStateFlow(MutableStateFlow11);
        Flags.dualShade();
        StateFlowImpl MutableStateFlow12 = StateFlowKt.MutableStateFlow(ShadeMode.Single.INSTANCE);
        this._shadeMode = MutableStateFlow12;
        this.shadeMode = FlowKt.asStateFlow(MutableStateFlow12);
        StateFlowImpl MutableStateFlow13 = StateFlowKt.MutableStateFlow(bool);
        this._legacyIsClosing = MutableStateFlow13;
        this.legacyIsClosing = FlowKt.asStateFlow(MutableStateFlow13);
    }
}
