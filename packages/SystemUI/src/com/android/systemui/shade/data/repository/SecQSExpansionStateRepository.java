package com.android.systemui.shade.data.repository;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class SecQSExpansionStateRepository {
    public final StateFlowImpl _isCustomizerShowing;
    public final StateFlowImpl _isDetailClosing;
    public final StateFlowImpl _isDetailOpening;
    public final StateFlowImpl _isDetailShowing;
    public final StateFlowImpl _panelTransitionEnabled;
    public final StateFlowImpl _panelTransitionState;
    public boolean debugExpanded;
    public final ReadonlyStateFlow expanded;
    public boolean expandedWithCustomizerOrDetail;
    public final ReadonlyStateFlow isCustomizerShowing;
    public final ReadonlyStateFlow isDetailClosing;
    public final ReadonlyStateFlow isDetailOpening;
    public final ReadonlyStateFlow isDetailShowing;
    public final ReadonlyStateFlow panelTransitionEnabled;
    public final ReadonlyStateFlow panelTransitionState;

    /* renamed from: com.android.systemui.shade.data.repository.SecQSExpansionStateRepository$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function6 {
        final /* synthetic */ Function1 $notify;
        /* synthetic */ boolean Z$0;
        /* synthetic */ boolean Z$1;
        /* synthetic */ boolean Z$2;
        /* synthetic */ boolean Z$3;
        /* synthetic */ boolean Z$4;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Function1 function1, Continuation continuation) {
            super(6, continuation);
            this.$notify = function1;
        }

        @Override // kotlin.jvm.functions.Function6
        public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
            boolean zBooleanValue = ((Boolean) obj).booleanValue();
            boolean zBooleanValue2 = ((Boolean) obj2).booleanValue();
            boolean zBooleanValue3 = ((Boolean) obj3).booleanValue();
            boolean zBooleanValue4 = ((Boolean) obj4).booleanValue();
            boolean zBooleanValue5 = ((Boolean) obj5).booleanValue();
            AnonymousClass1 anonymousClass1 = SecQSExpansionStateRepository.this.new AnonymousClass1(this.$notify, (Continuation) obj6);
            anonymousClass1.Z$0 = zBooleanValue;
            anonymousClass1.Z$1 = zBooleanValue2;
            anonymousClass1.Z$2 = zBooleanValue3;
            anonymousClass1.Z$3 = zBooleanValue4;
            anonymousClass1.Z$4 = zBooleanValue5;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            boolean z = this.Z$0;
            boolean z2 = this.Z$1;
            boolean z3 = this.Z$2;
            boolean z4 = this.Z$3;
            boolean z5 = this.Z$4;
            StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("expanded: ", " | CS: ", " | DO: ", z, z2);
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, z3, " | DS: ", z4, " | DC: ");
            ActionBarContextView$$ExternalSyntheticOutline0.m(sbM, z5, "SecQSExpansionStateRepository");
            boolean z6 = (z2 || z3 || z4 || z5) ? false : z;
            Boolean boolValueOf = Boolean.valueOf(z6);
            SecQSExpansionStateRepository secQSExpansionStateRepository = SecQSExpansionStateRepository.this;
            if (secQSExpansionStateRepository.expandedWithCustomizerOrDetail == z6) {
                boolValueOf = null;
            }
            if (boolValueOf == null) {
                return null;
            }
            Function1 function1 = this.$notify;
            boolean zBooleanValue = boolValueOf.booleanValue();
            StringBuilder sbM2 = EmergencyButtonController$$ExternalSyntheticOutline0.m("notify expanded[", " => ", "] expanded: ", secQSExpansionStateRepository.expandedWithCustomizerOrDetail, zBooleanValue);
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM2, z, " | CS: ", z2, " | DO: ");
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM2, z3, " | DS: ", z4, " | DC: ");
            ActionBarContextView$$ExternalSyntheticOutline0.m(sbM2, z5, "SecQSExpansionStateRepository");
            secQSExpansionStateRepository.expandedWithCustomizerOrDetail = zBooleanValue;
            function1.mo781invoke(Boolean.valueOf(zBooleanValue));
            return Unit.INSTANCE;
        }
    }

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

    public SecQSExpansionStateRepository(CoroutineScope coroutineScope, ShadeRepository shadeRepository, Function1 function1) {
        Boolean bool = Boolean.FALSE;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._isCustomizerShowing = stateFlowImplMutableStateFlow;
        ReadonlyStateFlow readonlyStateFlowAsStateFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        this.isCustomizerShowing = readonlyStateFlowAsStateFlow;
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(bool);
        this._isDetailOpening = stateFlowImplMutableStateFlow2;
        ReadonlyStateFlow readonlyStateFlowAsStateFlow2 = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        this.isDetailOpening = readonlyStateFlowAsStateFlow2;
        StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(bool);
        this._isDetailShowing = stateFlowImplMutableStateFlow3;
        ReadonlyStateFlow readonlyStateFlowAsStateFlow3 = FlowKt.asStateFlow(stateFlowImplMutableStateFlow3);
        this.isDetailShowing = readonlyStateFlowAsStateFlow3;
        StateFlowImpl stateFlowImplMutableStateFlow4 = StateFlowKt.MutableStateFlow(bool);
        this._isDetailClosing = stateFlowImplMutableStateFlow4;
        ReadonlyStateFlow readonlyStateFlowAsStateFlow4 = FlowKt.asStateFlow(stateFlowImplMutableStateFlow4);
        this.isDetailClosing = readonlyStateFlowAsStateFlow4;
        StateFlowImpl stateFlowImplMutableStateFlow5 = StateFlowKt.MutableStateFlow(bool);
        this._panelTransitionEnabled = stateFlowImplMutableStateFlow5;
        ReadonlyStateFlow readonlyStateFlowAsStateFlow5 = FlowKt.asStateFlow(stateFlowImplMutableStateFlow5);
        this.panelTransitionEnabled = readonlyStateFlowAsStateFlow5;
        StateFlowImpl stateFlowImplMutableStateFlow6 = StateFlowKt.MutableStateFlow(3);
        this._panelTransitionState = stateFlowImplMutableStateFlow6;
        ReadonlyStateFlow readonlyStateFlowAsStateFlow6 = FlowKt.asStateFlow(stateFlowImplMutableStateFlow6);
        this.panelTransitionState = readonlyStateFlowAsStateFlow6;
        ShadeRepositoryImpl shadeRepositoryImpl = (ShadeRepositoryImpl) shadeRepository;
        Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.combine(readonlyStateFlowAsStateFlow5, readonlyStateFlowAsStateFlow6, shadeRepositoryImpl.lockscreenShadeExpansion, shadeRepositoryImpl.qsExpansion, new SecQSExpansionStateRepository$expanded$1(this, null)));
        SharingStarted.Companion.getClass();
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flowDistinctUntilChanged, coroutineScope, SharingStarted.Companion.Eagerly, bool);
        this.expanded = readonlyStateFlowStateIn;
        FlowKt.launchIn(FlowKt.distinctUntilChanged(FlowKt.combine(readonlyStateFlowStateIn, readonlyStateFlowAsStateFlow, readonlyStateFlowAsStateFlow2, readonlyStateFlowAsStateFlow3, readonlyStateFlowAsStateFlow4, new AnonymousClass1(function1, null))), coroutineScope);
    }
}
