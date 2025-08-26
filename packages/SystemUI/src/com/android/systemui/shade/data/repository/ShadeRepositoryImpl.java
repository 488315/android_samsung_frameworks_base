package com.android.systemui.shade.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.shade.data.repository.ShadeRepositoryImpl$setCurrentFling$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ FlingInfo $info;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(FlingInfo flingInfo, Continuation continuation) {
            super(2, continuation);
            this.$info = flingInfo;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ShadeRepositoryImpl.this.new AnonymousClass1(this.$info, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SharedFlowImpl sharedFlowImpl = ShadeRepositoryImpl.this.currentFling;
                FlingInfo flingInfo = this.$info;
                this.label = 1;
                if (sharedFlowImpl.emit(flingInfo, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    static {
        new Companion(null);
    }

    public ShadeRepositoryImpl(CoroutineScope coroutineScope) {
        this.backgroundScope = coroutineScope;
        Float fValueOf = Float.valueOf(0.0f);
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(fValueOf);
        this._qsExpansion = stateFlowImplMutableStateFlow;
        this.qsExpansion = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(fValueOf);
        this._lockscreenShadeExpansion = stateFlowImplMutableStateFlow2;
        this.lockscreenShadeExpansion = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(fValueOf);
        this._udfpsTransitionToFullShadeProgress = stateFlowImplMutableStateFlow3;
        this.udfpsTransitionToFullShadeProgress = FlowKt.asStateFlow(stateFlowImplMutableStateFlow3);
        this.currentFling = SharedFlowKt.MutableSharedFlow$default(2, 0, BufferOverflow.DROP_OLDEST, 2);
        StateFlowImpl stateFlowImplMutableStateFlow4 = StateFlowKt.MutableStateFlow(fValueOf);
        this._legacyShadeExpansion = stateFlowImplMutableStateFlow4;
        this.legacyShadeExpansion = FlowKt.asStateFlow(stateFlowImplMutableStateFlow4);
        Boolean bool = Boolean.FALSE;
        StateFlowImpl stateFlowImplMutableStateFlow5 = StateFlowKt.MutableStateFlow(bool);
        this._legacyShadeTracking = stateFlowImplMutableStateFlow5;
        this.legacyShadeTracking = FlowKt.asStateFlow(stateFlowImplMutableStateFlow5);
        this.legacyLockscreenShadeTracking = StateFlowKt.MutableStateFlow(bool);
        StateFlowImpl stateFlowImplMutableStateFlow6 = StateFlowKt.MutableStateFlow(bool);
        this._legacyQsTracking = stateFlowImplMutableStateFlow6;
        this.legacyQsTracking = FlowKt.asStateFlow(stateFlowImplMutableStateFlow6);
        StateFlowImpl stateFlowImplMutableStateFlow7 = StateFlowKt.MutableStateFlow(bool);
        this._legacyExpandedOrAwaitingInputTransfer = stateFlowImplMutableStateFlow7;
        this.legacyExpandedOrAwaitingInputTransfer = FlowKt.asStateFlow(stateFlowImplMutableStateFlow7);
        StateFlowImpl stateFlowImplMutableStateFlow8 = StateFlowKt.MutableStateFlow(bool);
        this._legacyIsQsExpanded = stateFlowImplMutableStateFlow8;
        this.legacyIsQsExpanded = FlowKt.asStateFlow(stateFlowImplMutableStateFlow8);
        StateFlowImpl stateFlowImplMutableStateFlow9 = StateFlowKt.MutableStateFlow(bool);
        this._legacyExpandImmediate = stateFlowImplMutableStateFlow9;
        this.legacyExpandImmediate = FlowKt.asStateFlow(stateFlowImplMutableStateFlow9);
        StateFlowImpl stateFlowImplMutableStateFlow10 = StateFlowKt.MutableStateFlow(bool);
        this._legacyQsFullscreen = stateFlowImplMutableStateFlow10;
        this.legacyQsFullscreen = FlowKt.asStateFlow(stateFlowImplMutableStateFlow10);
        StateFlowImpl stateFlowImplMutableStateFlow11 = StateFlowKt.MutableStateFlow(bool);
        this._isShadeLayoutWide = stateFlowImplMutableStateFlow11;
        this.isShadeLayoutWide = FlowKt.asStateFlow(stateFlowImplMutableStateFlow11);
        StateFlowImpl stateFlowImplMutableStateFlow12 = StateFlowKt.MutableStateFlow(bool);
        this._legacyIsClosing = stateFlowImplMutableStateFlow12;
        this.legacyIsClosing = FlowKt.asStateFlow(stateFlowImplMutableStateFlow12);
    }

    public final void setCurrentFling(FlingInfo flingInfo) {
        BuildersKt.launch$default(this.backgroundScope, null, null, new AnonymousClass1(flingInfo, null), 3);
    }
}
