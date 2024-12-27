package com.android.systemui.statusbar.phone;

import android.util.Log;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import com.android.systemui.bouncer.data.repository.KeyguardBouncerRepository;
import com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.StatusBarState;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.scrim.ScrimView;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QSScrimViewSwitch {
    public final StateFlowImpl _scrimBehindAlpha;
    public int currentVisibility;
    public final ScrimView scrimBehind;
    public final ReadonlyStateFlow scrimBehindAlpha;
    public final ScrimView scrimInFront;
    public int visibility;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.phone.QSScrimViewSwitch$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function3 {
        /* synthetic */ int I$0;
        /* synthetic */ boolean Z$0;
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            int intValue = ((Number) obj).intValue();
            boolean booleanValue = ((Boolean) obj2).booleanValue();
            AnonymousClass2 anonymousClass2 = QSScrimViewSwitch.this.new AnonymousClass2((Continuation) obj3);
            anonymousClass2.I$0 = intValue;
            anonymousClass2.Z$0 = booleanValue;
            return anonymousClass2.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            int i = this.I$0;
            boolean z = this.Z$0;
            Integer num = new Integer(z ? 0 : i);
            if (QSScrimViewSwitch.this.currentVisibility == num.intValue()) {
                num = null;
            }
            if (num == null) {
                return null;
            }
            QSScrimViewSwitch qSScrimViewSwitch = QSScrimViewSwitch.this;
            int intValue = num.intValue();
            StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(qSScrimViewSwitch.currentVisibility, intValue, "set visibility[", " => ", "] vis: ");
            m.append(i);
            m.append(" | pbs: ");
            m.append(z);
            Log.d("QSScrimViewSwitch", m.toString());
            qSScrimViewSwitch.currentVisibility = intValue;
            qSScrimViewSwitch.scrimBehind.setVisibility(intValue);
            qSScrimViewSwitch.scrimInFront.setVisibility(intValue);
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[StatusBarState.values().length];
            try {
                iArr[StatusBarState.KEYGUARD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[StatusBarState.SHADE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[StatusBarState.SHADE_LOCKED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    public QSScrimViewSwitch(KeyguardBouncerRepository keyguardBouncerRepository, KeyguardRepository keyguardRepository, KeyguardTransitionInteractor keyguardTransitionInteractor, PowerInteractor powerInteractor, ShadeRepository shadeRepository, CoroutineScope coroutineScope, ScrimView scrimView, ScrimView scrimView2) {
        this.scrimBehind = scrimView;
        this.scrimInFront = scrimView2;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Float.valueOf(0.0f));
        this._scrimBehindAlpha = MutableStateFlow;
        ReadonlyStateFlow asStateFlow = FlowKt.asStateFlow(MutableStateFlow);
        ShadeRepositoryImpl shadeRepositoryImpl = (ShadeRepositoryImpl) shadeRepository;
        final Flow[] flowArr = {((KeyguardRepositoryImpl) keyguardRepository).statusBarState, keyguardTransitionInteractor.currentKeyguardState, keyguardTransitionInteractor.transitionState, powerInteractor.screenPowerState, shadeRepositoryImpl.lockscreenShadeExpansion, shadeRepositoryImpl.legacyShadeExpansion, shadeRepositoryImpl.qsExpansion, asStateFlow};
        FlowKt.launchIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.phone.QSScrimViewSwitch$special$$inlined$combine$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.statusbar.phone.QSScrimViewSwitch$special$$inlined$combine$1$3, reason: invalid class name */
            public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;
                final /* synthetic */ QSScrimViewSwitch this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(Continuation continuation, QSScrimViewSwitch qSScrimViewSwitch) {
                    super(3, continuation);
                    this.this$0 = qSScrimViewSwitch;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3, this.this$0);
                    anonymousClass3.L$0 = (FlowCollector) obj;
                    anonymousClass3.L$1 = (Object[]) obj2;
                    return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Removed duplicated region for block: B:20:0x00de  */
                /* JADX WARN: Removed duplicated region for block: B:27:0x016a A[RETURN] */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invokeSuspend(java.lang.Object r19) {
                    /*
                        Method dump skipped, instructions count: 366
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.QSScrimViewSwitch$special$$inlined$combine$1.AnonymousClass3.invokeSuspend(java.lang.Object):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                final Flow[] flowArr2 = flowArr;
                Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.statusbar.phone.QSScrimViewSwitch$special$$inlined$combine$1.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr2.length];
                    }
                }, new AnonymousClass3(null, this), flowCollector, continuation);
                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
            }
        }), ((KeyguardBouncerRepositoryImpl) keyguardBouncerRepository).primaryBouncerShow, new AnonymousClass2(null)), coroutineScope);
    }
}
