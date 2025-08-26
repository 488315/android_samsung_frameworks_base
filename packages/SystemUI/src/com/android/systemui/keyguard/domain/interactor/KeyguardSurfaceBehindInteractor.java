package com.android.systemui.keyguard.domain.interactor;

import android.content.Context;
import com.android.systemui.keyguard.data.repository.KeyguardSurfaceBehindRepository;
import com.android.systemui.keyguard.data.repository.KeyguardSurfaceBehindRepositoryImpl;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.statusbar.notification.domain.interactor.NotificationLaunchAnimationInteractor;
import dagger.Lazy;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* loaded from: classes2.dex */
public final class KeyguardSurfaceBehindInteractor {
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isAnimatingSurface;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 isNotificationLaunchAnimationRunningOnKeyguard;
    public final KeyguardSurfaceBehindRepository repository;
    public final Flow viewParams;

    public KeyguardSurfaceBehindInteractor(KeyguardSurfaceBehindRepository keyguardSurfaceBehindRepository, Context context, KeyguardTransitionInteractor keyguardTransitionInteractor, Lazy lazy, SwipeToDismissInteractor swipeToDismissInteractor, NotificationLaunchAnimationInteractor notificationLaunchAnimationInteractor) {
        this.repository = keyguardSurfaceBehindRepository;
        Edge.Companion companion = Edge.Companion;
        Edge.StateToContent stateToContentCreate$default = Edge.Companion.create$default(companion, Scenes.Gone);
        KeyguardState keyguardState = KeyguardState.GONE;
        this.viewParams = FlowKt.distinctUntilChanged(FlowKt.combine(keyguardTransitionInteractor.isInTransition(stateToContentCreate$default, Edge.Companion.create$default(companion, null, keyguardState, 1)), keyguardTransitionInteractor.isFinishedIn(keyguardState), notificationLaunchAnimationInteractor.repository.isLaunchAnimationRunning, new KeyguardSurfaceBehindInteractor$viewParams$1(lazy, context, swipeToDismissInteractor, null)));
        final Flow flowSample = com.android.systemui.util.kotlin.FlowKt.sample(notificationLaunchAnimationInteractor.repository.isLaunchAnimationRunning, FlowKt.distinctUntilChanged(keyguardTransitionInteractor.isFinishedIn$1(keyguardState)), KeyguardSurfaceBehindInteractor$isNotificationLaunchAnimationRunningOnKeyguard$3.INSTANCE);
        FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardSurfaceBehindInteractor$isNotificationLaunchAnimationRunningOnKeyguard$5(null), new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardSurfaceBehindInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardSurfaceBehindInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardSurfaceBehindInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        Pair pair = (Pair) obj;
                        Boolean boolValueOf = Boolean.valueOf(((Boolean) pair.component1()).booleanValue() && !((Boolean) pair.component2()).booleanValue());
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
                Object objCollect = flowSample.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.isNotificationLaunchAnimationRunningOnKeyguard = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
        this.isAnimatingSurface = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(((KeyguardSurfaceBehindRepositoryImpl) keyguardSurfaceBehindRepository).isAnimatingSurface, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, new KeyguardSurfaceBehindInteractor$isAnimatingSurface$1(null));
    }
}
