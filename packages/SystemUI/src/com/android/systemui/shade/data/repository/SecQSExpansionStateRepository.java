package com.android.systemui.shade.data.repository;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SecQSExpansionStateRepository {
    public final StateFlowImpl _isCustomizerShowing;
    public final StateFlowImpl _isDetailClosing;
    public final StateFlowImpl _isDetailOpening;
    public final StateFlowImpl _isDetailShowing;
    public final StateFlowImpl _panelTransitionEnabled;
    public final StateFlowImpl _panelTransitionState;
    public boolean currentExpanded;
    public boolean debugExpanded;
    public final ReadonlyStateFlow expanded;
    public final ReadonlyStateFlow isCustomizerShowing;
    public final ReadonlyStateFlow isDetailClosing;
    public final ReadonlyStateFlow isDetailOpening;
    public final ReadonlyStateFlow isDetailShowing;
    public final ReadonlyStateFlow panelTransitionEnabled;
    public final ReadonlyStateFlow panelTransitionState;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
            boolean booleanValue = ((Boolean) obj).booleanValue();
            boolean booleanValue2 = ((Boolean) obj2).booleanValue();
            boolean booleanValue3 = ((Boolean) obj3).booleanValue();
            boolean booleanValue4 = ((Boolean) obj4).booleanValue();
            boolean booleanValue5 = ((Boolean) obj5).booleanValue();
            AnonymousClass1 anonymousClass1 = SecQSExpansionStateRepository.this.new AnonymousClass1(this.$notify, (Continuation) obj6);
            anonymousClass1.Z$0 = booleanValue;
            anonymousClass1.Z$1 = booleanValue2;
            anonymousClass1.Z$2 = booleanValue3;
            anonymousClass1.Z$3 = booleanValue4;
            anonymousClass1.Z$4 = booleanValue5;
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
            boolean z6 = false;
            if (!z2 && !z3 && !z4 && !z5 && z) {
                z6 = true;
            }
            Boolean valueOf = Boolean.valueOf(z6);
            SecQSExpansionStateRepository secQSExpansionStateRepository = SecQSExpansionStateRepository.this;
            if (secQSExpansionStateRepository.currentExpanded == z6) {
                valueOf = null;
            }
            if (valueOf == null) {
                return null;
            }
            Function1 function1 = this.$notify;
            boolean booleanValue = valueOf.booleanValue();
            StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("notify expanded[", " => ", "] expanded: ", secQSExpansionStateRepository.currentExpanded, booleanValue);
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, z, " | CS: ", z2, " | DO: ");
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, z3, " | DS: ", z4, " | DC: ");
            ActionBarContextView$$ExternalSyntheticOutline0.m(m, z5, "SecQSExpansionStateRepository");
            secQSExpansionStateRepository.currentExpanded = booleanValue;
            function1.invoke(Boolean.valueOf(booleanValue));
            return Unit.INSTANCE;
        }
    }

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

    public SecQSExpansionStateRepository(CoroutineScope coroutineScope, ShadeRepository shadeRepository, Function1 function1) {
        Boolean bool = Boolean.FALSE;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._isCustomizerShowing = MutableStateFlow;
        ReadonlyStateFlow asStateFlow = FlowKt.asStateFlow(MutableStateFlow);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(bool);
        this._isDetailOpening = MutableStateFlow2;
        ReadonlyStateFlow asStateFlow2 = FlowKt.asStateFlow(MutableStateFlow2);
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(bool);
        this._isDetailShowing = MutableStateFlow3;
        ReadonlyStateFlow asStateFlow3 = FlowKt.asStateFlow(MutableStateFlow3);
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(bool);
        this._isDetailClosing = MutableStateFlow4;
        ReadonlyStateFlow asStateFlow4 = FlowKt.asStateFlow(MutableStateFlow4);
        StateFlowImpl MutableStateFlow5 = StateFlowKt.MutableStateFlow(bool);
        this._panelTransitionEnabled = MutableStateFlow5;
        ReadonlyStateFlow asStateFlow5 = FlowKt.asStateFlow(MutableStateFlow5);
        StateFlowImpl MutableStateFlow6 = StateFlowKt.MutableStateFlow(3);
        this._panelTransitionState = MutableStateFlow6;
        ShadeRepositoryImpl shadeRepositoryImpl = (ShadeRepositoryImpl) shadeRepository;
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.combine(asStateFlow5, FlowKt.asStateFlow(MutableStateFlow6), shadeRepositoryImpl.lockscreenShadeExpansion, shadeRepositoryImpl.qsExpansion, new SecQSExpansionStateRepository$expanded$1(this, null)));
        SharingStarted.Companion.getClass();
        FlowKt.launchIn(FlowKt.distinctUntilChanged(FlowKt.combine(FlowKt.stateIn(distinctUntilChanged, coroutineScope, SharingStarted.Companion.Eagerly, bool), asStateFlow, asStateFlow2, asStateFlow3, asStateFlow4, new AnonymousClass1(function1, null))), coroutineScope);
    }
}
