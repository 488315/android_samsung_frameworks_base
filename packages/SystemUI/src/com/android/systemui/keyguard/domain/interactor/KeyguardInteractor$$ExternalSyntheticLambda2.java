package com.android.systemui.keyguard.domain.interactor;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.scene.shared.model.Scenes;
import javax.inject.Provider;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.MutableSharedFlow;

/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardInteractor$$ExternalSyntheticLambda2 implements Function0 {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ KeyguardInteractor f$0;

    public /* synthetic */ KeyguardInteractor$$ExternalSyntheticLambda2(KeyguardInteractor keyguardInteractor) {
        this.f$0 = keyguardInteractor;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        KeyguardInteractor keyguardInteractor = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                SceneKey sceneKey = Scenes.Communal;
                final MutableSharedFlow transitionValueFlow = keyguardInteractor.keyguardTransitionInteractor.getTransitionValueFlow(KeyguardState.GONE);
                return FlowKt.distinctUntilChanged(FlowKt.combineTransform(FlowKt.distinctUntilChanged(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardInteractor$topClippingBounds$2$2(null), new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$topClippingBounds_delegate$lambda$6$$inlined$map$1

                    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$topClippingBounds_delegate$lambda$6$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$topClippingBounds_delegate$lambda$6$$inlined$map$1$2$1, reason: invalid class name */
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
                                Boolean boolValueOf = Boolean.valueOf(((Number) obj).floatValue() == 1.0f);
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
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object objCollect = transitionValueFlow.collect(new AnonymousClass2(flowCollector), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                })), ((KeyguardRepositoryImpl) keyguardInteractor.repository).topClippingBounds, new KeyguardInteractor$topClippingBounds$2$3(null)));
            default:
                return ((KeyguardRepositoryImpl) keyguardInteractor.repository).animateBottomAreaDozingTransitions;
        }
    }

    public /* synthetic */ KeyguardInteractor$$ExternalSyntheticLambda2(Provider provider, KeyguardInteractor keyguardInteractor) {
        this.f$0 = keyguardInteractor;
    }
}
