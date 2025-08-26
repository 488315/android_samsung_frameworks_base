package com.android.systemui.statusbar.phone;

import android.util.Log;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import com.android.systemui.bouncer.data.repository.KeyguardBouncerRepository;
import com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.StatusBarState;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.power.shared.model.ScreenPowerState;
import com.android.systemui.scrim.ScrimView;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import com.android.systemui.statusbar.phone.QSScrimViewSwitch;
import kotlin.NoWhenBranchMatchedException;
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

/* loaded from: classes3.dex */
public final class QSScrimViewSwitch {
    public final StateFlowImpl _scrimBehindAlpha;
    public int currentVisibility;
    public final ScrimView scrimBehind;
    public final ReadonlyStateFlow scrimBehindAlpha;
    public final ScrimView scrimInFront;
    public int visibility;

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
            int iIntValue = ((Number) obj).intValue();
            boolean zBooleanValue = ((Boolean) obj2).booleanValue();
            AnonymousClass2 anonymousClass2 = QSScrimViewSwitch.this.new AnonymousClass2((Continuation) obj3);
            anonymousClass2.I$0 = iIntValue;
            anonymousClass2.Z$0 = zBooleanValue;
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
            int iIntValue = num.intValue();
            StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(qSScrimViewSwitch.currentVisibility, iIntValue, "set visibility[", " => ", "] vis: ");
            sbM.append(i);
            sbM.append(" | pbs: ");
            sbM.append(z);
            Log.d("QSScrimViewSwitch", sbM.toString());
            qSScrimViewSwitch.currentVisibility = iIntValue;
            qSScrimViewSwitch.scrimBehind.setVisibility(iIntValue);
            qSScrimViewSwitch.scrimInFront.setVisibility(iIntValue);
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
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(Float.valueOf(0.0f));
        this._scrimBehindAlpha = stateFlowImplMutableStateFlow;
        ReadonlyStateFlow readonlyStateFlowAsStateFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        this.scrimBehindAlpha = readonlyStateFlowAsStateFlow;
        ShadeRepositoryImpl shadeRepositoryImpl = (ShadeRepositoryImpl) shadeRepository;
        final Flow[] flowArr = {((KeyguardRepositoryImpl) keyguardRepository).statusBarState, keyguardTransitionInteractor.currentKeyguardState, keyguardTransitionInteractor.transitionState, powerInteractor.screenPowerState, shadeRepositoryImpl.lockscreenShadeExpansion, shadeRepositoryImpl.legacyShadeExpansion, shadeRepositoryImpl.qsExpansion, readonlyStateFlowAsStateFlow};
        FlowKt.launchIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.phone.QSScrimViewSwitch$special$$inlined$combine$1

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

                /* JADX WARN: Removed duplicated region for block: B:17:0x0077  */
                /* JADX WARN: Removed duplicated region for block: B:54:0x00eb  */
                /* JADX WARN: Removed duplicated region for block: B:60:0x0166  */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invokeSuspend(Object obj) {
                    Integer num;
                    int iIntValue;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        Object[] objArr = (Object[]) this.L$1;
                        Object obj2 = objArr[0];
                        Object obj3 = objArr[1];
                        Object obj4 = objArr[2];
                        Object obj5 = objArr[3];
                        Object obj6 = objArr[4];
                        Object obj7 = objArr[5];
                        Object obj8 = objArr[6];
                        float fFloatValue = ((Number) objArr[7]).floatValue();
                        float fFloatValue2 = ((Number) obj8).floatValue();
                        float fFloatValue3 = ((Number) obj7).floatValue();
                        float fFloatValue4 = ((Number) obj6).floatValue();
                        ScreenPowerState screenPowerState = (ScreenPowerState) obj5;
                        TransitionStep transitionStep = (TransitionStep) obj4;
                        KeyguardState keyguardState = (KeyguardState) obj3;
                        StatusBarState statusBarState = (StatusBarState) obj2;
                        int i2 = QSScrimViewSwitch.WhenMappings.$EnumSwitchMapping$0[statusBarState.ordinal()];
                        if (i2 == 1) {
                            if (keyguardState == KeyguardState.DOZING) {
                                num = new Integer(0);
                            } else if (fFloatValue4 > 0.0f || fFloatValue2 > 0.0f) {
                                num = new Integer(8);
                            } else if (fFloatValue4 == 0.0f && fFloatValue2 == 0.0f && fFloatValue == 0.0f) {
                                num = new Integer(0);
                            }
                            if (num != null) {
                            }
                        } else if (i2 == 2) {
                            if (keyguardState == KeyguardState.DOZING) {
                                num = new Integer(0);
                            } else if (fFloatValue3 > 0.0f) {
                                if (screenPowerState == ScreenPowerState.SCREEN_TURNING_ON) {
                                    num = new Integer(0);
                                } else if (transitionStep.transitionState == TransitionState.FINISHED) {
                                    num = new Integer(8);
                                }
                            } else if (fFloatValue3 == 0.0f && fFloatValue == 0.0f) {
                                num = new Integer(0);
                            }
                            if (num != null) {
                            }
                        } else {
                            if (i2 != 3) {
                                throw new NoWhenBranchMatchedException();
                            }
                            num = (fFloatValue4 > 0.0f || fFloatValue2 > 0.0f) ? new Integer(8) : null;
                            if (num != null) {
                                Integer num2 = this.this$0.visibility != num.intValue() ? num : null;
                                if (num2 != null) {
                                    int iIntValue2 = num2.intValue();
                                    Log.d("QSScrimViewSwitch", this.this$0.visibility + " => " + iIntValue2 + ": sb: " + statusBarState + " | cks: " + keyguardState + " | ts: " + transitionStep.transitionState + " | sps: " + screenPowerState + " | lsse: " + fFloatValue4 + " | lse: " + fFloatValue3 + " | qe: " + fFloatValue2 + " | sba: " + fFloatValue);
                                    this.this$0.visibility = iIntValue2;
                                    iIntValue = num2.intValue();
                                } else {
                                    iIntValue = this.this$0.visibility;
                                }
                                Integer num3 = new Integer(iIntValue);
                                this.label = 1;
                                if (flowCollector.emit(num3, this) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            }
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

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                final Flow[] flowArr2 = flowArr;
                Object objCombineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.statusbar.phone.QSScrimViewSwitch$special$$inlined$combine$1.2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr2.length];
                    }
                }, new AnonymousClass3(null, this), flowCollector, continuation);
                return objCombineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? objCombineInternal : Unit.INSTANCE;
            }
        }), ((KeyguardBouncerRepositoryImpl) keyguardBouncerRepository).primaryBouncerShow, new AnonymousClass2(null)), coroutineScope);
    }
}
