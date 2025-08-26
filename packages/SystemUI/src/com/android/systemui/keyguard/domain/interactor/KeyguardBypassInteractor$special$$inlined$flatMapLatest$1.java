package com.android.systemui.keyguard.domain.interactor;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.CombineKt;

/* loaded from: classes2.dex */
public final class KeyguardBypassInteractor$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ AlternateBouncerInteractor $alternateBouncerInteractor$inlined;
    final /* synthetic */ KeyguardQuickAffordanceInteractor $keyguardQuickAffordanceInteractor$inlined;
    final /* synthetic */ PulseExpansionInteractor $pulseExpansionInteractor$inlined;
    final /* synthetic */ SceneInteractor $sceneInteractor$inlined;
    final /* synthetic */ ShadeInteractor $shadeInteractor$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardBypassInteractor$special$$inlined$flatMapLatest$1(Continuation continuation, SceneInteractor sceneInteractor, AlternateBouncerInteractor alternateBouncerInteractor, KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor, PulseExpansionInteractor pulseExpansionInteractor, ShadeInteractor shadeInteractor) {
        super(3, continuation);
        this.$sceneInteractor$inlined = sceneInteractor;
        this.$alternateBouncerInteractor$inlined = alternateBouncerInteractor;
        this.$keyguardQuickAffordanceInteractor$inlined = keyguardQuickAffordanceInteractor;
        this.$pulseExpansionInteractor$inlined = pulseExpansionInteractor;
        this.$shadeInteractor$inlined = shadeInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardBypassInteractor$special$$inlined$flatMapLatest$1 keyguardBypassInteractor$special$$inlined$flatMapLatest$1 = new KeyguardBypassInteractor$special$$inlined$flatMapLatest$1((Continuation) obj3, this.$sceneInteractor$inlined, this.$alternateBouncerInteractor$inlined, this.$keyguardQuickAffordanceInteractor$inlined, this.$pulseExpansionInteractor$inlined, this.$shadeInteractor$inlined);
        keyguardBypassInteractor$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        keyguardBypassInteractor$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return keyguardBypassInteractor$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                final StateFlow stateFlow = this.$sceneInteractor$inlined.currentOverlays;
                Flow flow = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor$canBypass$lambda$3$$inlined$map$1

                    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor$canBypass$lambda$3$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor$canBypass$lambda$3$$inlined$map$1$2$1, reason: invalid class name */
                        public final class AnonymousClass1 extends ContinuationImpl {
                            Object L$0;
                            int label;
                            /* synthetic */ Object result;

                            public AnonymousClass1(Continuation continuation) {
                                super(continuation);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Object invokeSuspend(Object obj) {
                                this.result = obj;
                                this.label |= Integer.MIN_VALUE;
                                return AnonymousClass2.this.emit(null, this);
                            }
                        }

                        public AnonymousClass2(FlowCollector flowCollector) {
                            this.$this_unsafeFlow = flowCollector;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object emit(Object obj, Continuation continuation) {
                            AnonymousClass1 anonymousClass1;
                            if (continuation instanceof AnonymousClass1) {
                                anonymousClass1 = (AnonymousClass1) continuation;
                                int i = anonymousClass1.label;
                                if ((i & Integer.MIN_VALUE) != 0) {
                                    anonymousClass1.label = i - Integer.MIN_VALUE;
                                } else {
                                    anonymousClass1 = new AnonymousClass1(continuation);
                                }
                            }
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i2 = anonymousClass1.label;
                            if (i2 == 0) {
                                ResultKt.throwOnFailure(obj2);
                                Boolean boolValueOf = Boolean.valueOf(((Set) obj).contains(Overlays.Bouncer));
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i2 != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        Object objCollect = stateFlow.collect(new AnonymousClass2(flowCollector2), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
                StateFlow stateFlow2 = this.$alternateBouncerInteractor$inlined.isVisible;
                final StateFlow stateFlow3 = this.$sceneInteractor$inlined.currentScene;
                final Flow[] flowArr = {flow, stateFlow2, new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor$canBypass$lambda$3$$inlined$map$2

                    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor$canBypass$lambda$3$$inlined$map$2$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor$canBypass$lambda$3$$inlined$map$2$2$1, reason: invalid class name */
                        public final class AnonymousClass1 extends ContinuationImpl {
                            Object L$0;
                            int label;
                            /* synthetic */ Object result;

                            public AnonymousClass1(Continuation continuation) {
                                super(continuation);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Object invokeSuspend(Object obj) {
                                this.result = obj;
                                this.label |= Integer.MIN_VALUE;
                                return AnonymousClass2.this.emit(null, this);
                            }
                        }

                        public AnonymousClass2(FlowCollector flowCollector) {
                            this.$this_unsafeFlow = flowCollector;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object emit(Object obj, Continuation continuation) {
                            AnonymousClass1 anonymousClass1;
                            if (continuation instanceof AnonymousClass1) {
                                anonymousClass1 = (AnonymousClass1) continuation;
                                int i = anonymousClass1.label;
                                if ((i & Integer.MIN_VALUE) != 0) {
                                    anonymousClass1.label = i - Integer.MIN_VALUE;
                                } else {
                                    anonymousClass1 = new AnonymousClass1(continuation);
                                }
                            }
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i2 = anonymousClass1.label;
                            if (i2 == 0) {
                                ResultKt.throwOnFailure(obj2);
                                Boolean boolValueOf = Boolean.valueOf(Intrinsics.areEqual((SceneKey) obj, Scenes.Lockscreen));
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i2 != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        Object objCollect = stateFlow3.collect(new AnonymousClass2(flowCollector2), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                }, this.$keyguardQuickAffordanceInteractor$inlined.launchingAffordance, this.$pulseExpansionInteractor$inlined.isPulseExpanding, ((ShadeInteractorImpl) this.$shadeInteractor$inlined).baseShadeInteractor.isQsExpanded()};
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor$canBypass$lambda$3$$inlined$combine$1

                    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor$canBypass$lambda$3$$inlined$combine$1$3, reason: invalid class name */
                    public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                        private /* synthetic */ Object L$0;
                        /* synthetic */ Object L$1;
                        int label;

                        public AnonymousClass3(Continuation continuation) {
                            super(3, continuation);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3);
                            anonymousClass3.L$0 = (FlowCollector) obj;
                            anonymousClass3.L$1 = (Object[]) obj2;
                            return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i = this.label;
                            if (i == 0) {
                                ResultKt.throwOnFailure(obj);
                                FlowCollector flowCollector = (FlowCollector) this.L$0;
                                Object[] objArr = (Object[]) this.L$1;
                                boolean z = false;
                                Object obj2 = objArr[0];
                                Object obj3 = objArr[1];
                                Object obj4 = objArr[2];
                                Object obj5 = objArr[3];
                                Object obj6 = objArr[4];
                                boolean zBooleanValue = ((Boolean) objArr[5]).booleanValue();
                                boolean zBooleanValue2 = ((Boolean) obj6).booleanValue();
                                boolean zBooleanValue3 = ((Boolean) obj5).booleanValue();
                                boolean zBooleanValue4 = ((Boolean) obj4).booleanValue();
                                boolean zBooleanValue5 = ((Boolean) obj3).booleanValue();
                                if (((Boolean) obj2).booleanValue() || zBooleanValue5 || (zBooleanValue4 && !zBooleanValue3 && !zBooleanValue2 && !zBooleanValue)) {
                                    z = true;
                                }
                                Boolean boolValueOf = Boolean.valueOf(z);
                                this.label = 1;
                                if (flowCollector.emit(boolValueOf, this) == coroutineSingletons) {
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

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        final Flow[] flowArr2 = flowArr;
                        Object objCombineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor$canBypass$lambda$3$$inlined$combine$1.2
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                return new Object[flowArr2.length];
                            }
                        }, new AnonymousClass3(null), flowCollector2, continuation);
                        return objCombineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? objCombineInternal : Unit.INSTANCE;
                    }
                };
            } else {
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, this) == coroutineSingletons) {
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
