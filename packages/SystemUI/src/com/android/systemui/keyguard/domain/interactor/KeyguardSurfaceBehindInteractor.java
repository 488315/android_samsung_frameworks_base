package com.android.systemui.keyguard.domain.interactor;

import android.content.Context;
import com.android.systemui.keyguard.data.repository.KeyguardSurfaceBehindRepository;
import com.android.systemui.keyguard.data.repository.KeyguardSurfaceBehindRepositoryImpl;
import com.android.systemui.statusbar.notification.data.repository.NotificationLaunchAnimationRepository;
import com.android.systemui.statusbar.notification.domain.interactor.NotificationLaunchAnimationInteractor;
import dagger.Lazy;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardSurfaceBehindInteractor {
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isAnimatingSurface;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 isNotificationLaunchAnimationRunningOnKeyguard;
    public final KeyguardSurfaceBehindRepository repository;
    public final Flow viewParams;

    public KeyguardSurfaceBehindInteractor(KeyguardSurfaceBehindRepository keyguardSurfaceBehindRepository, Context context, KeyguardTransitionInteractor keyguardTransitionInteractor, Lazy lazy, SwipeToDismissInteractor swipeToDismissInteractor, NotificationLaunchAnimationInteractor notificationLaunchAnimationInteractor) {
        this.repository = keyguardSurfaceBehindRepository;
        NotificationLaunchAnimationRepository notificationLaunchAnimationRepository = notificationLaunchAnimationInteractor.repository;
        this.viewParams = FlowKt.distinctUntilChanged(FlowKt.combine(keyguardTransitionInteractor.startedKeyguardTransitionStep, keyguardTransitionInteractor.currentKeyguardState, notificationLaunchAnimationRepository.isLaunchAnimationRunning, new KeyguardSurfaceBehindInteractor$viewParams$1(lazy, context, swipeToDismissInteractor, null)));
        KeyguardSurfaceBehindInteractor$isNotificationLaunchAnimationRunningOnKeyguard$2 keyguardSurfaceBehindInteractor$isNotificationLaunchAnimationRunningOnKeyguard$2 = KeyguardSurfaceBehindInteractor$isNotificationLaunchAnimationRunningOnKeyguard$2.INSTANCE;
        final Flow sample = com.android.systemui.util.kotlin.FlowKt.sample(notificationLaunchAnimationRepository.isLaunchAnimationRunning, keyguardTransitionInteractor.finishedKeyguardState, keyguardSurfaceBehindInteractor$isNotificationLaunchAnimationRunningOnKeyguard$2);
        this.isAnimatingSurface = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(((KeyguardSurfaceBehindRepositoryImpl) keyguardSurfaceBehindRepository).isAnimatingSurface, new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardSurfaceBehindInteractor$isNotificationLaunchAnimationRunningOnKeyguard$4(null), new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardSurfaceBehindInteractor$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardSurfaceBehindInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardSurfaceBehindInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.KeyguardSurfaceBehindInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardSurfaceBehindInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.KeyguardSurfaceBehindInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardSurfaceBehindInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L5c
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlin.Pair r5 = (kotlin.Pair) r5
                        java.lang.Object r6 = r5.component1()
                        java.lang.Boolean r6 = (java.lang.Boolean) r6
                        boolean r6 = r6.booleanValue()
                        java.lang.Object r5 = r5.component2()
                        com.android.systemui.keyguard.shared.model.KeyguardState r5 = (com.android.systemui.keyguard.shared.model.KeyguardState) r5
                        if (r6 == 0) goto L4c
                        com.android.systemui.keyguard.shared.model.KeyguardState r6 = com.android.systemui.keyguard.shared.model.KeyguardState.GONE
                        if (r5 == r6) goto L4c
                        r5 = r3
                        goto L4d
                    L4c:
                        r5 = 0
                    L4d:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L5c
                        return r1
                    L5c:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardSurfaceBehindInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), new KeyguardSurfaceBehindInteractor$isAnimatingSurface$1(null));
    }
}
