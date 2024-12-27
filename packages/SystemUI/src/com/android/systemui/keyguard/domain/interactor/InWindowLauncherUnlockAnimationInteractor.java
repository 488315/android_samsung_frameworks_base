package com.android.systemui.keyguard.domain.interactor;

import android.app.ActivityManager;
import android.content.ComponentName;
import com.android.systemui.keyguard.data.repository.InWindowLauncherUnlockAnimationRepository;
import com.android.systemui.keyguard.data.repository.KeyguardSurfaceBehindRepository;
import com.android.systemui.keyguard.data.repository.KeyguardSurfaceBehindRepositoryImpl;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import dagger.Lazy;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;

public final class InWindowLauncherUnlockAnimationInteractor {
    public final ActivityManagerWrapper activityManager;
    public final InWindowLauncherUnlockAnimationRepository repository;
    public final ReadonlyStateFlow shouldStartInWindowAnimation;
    public final ReadonlyStateFlow startedUnlockAnimation;
    public final ReadonlyStateFlow transitioningToGoneWithInWindowAnimation;

    public InWindowLauncherUnlockAnimationInteractor(InWindowLauncherUnlockAnimationRepository inWindowLauncherUnlockAnimationRepository, CoroutineScope coroutineScope, KeyguardTransitionInteractor keyguardTransitionInteractor, Lazy lazy, ActivityManagerWrapper activityManagerWrapper) {
        this.repository = inWindowLauncherUnlockAnimationRepository;
        this.activityManager = activityManagerWrapper;
        this.startedUnlockAnimation = FlowKt.asStateFlow(inWindowLauncherUnlockAnimationRepository.startedUnlockAnimation);
        Edge.Companion companion = Edge.Companion;
        final Flow isInTransition = keyguardTransitionInteractor.isInTransition(Edge.Companion.create$default(companion, Scenes.Gone), Edge.Companion.create$default(companion, null, KeyguardState.GONE, 1));
        Flow flow = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.InWindowLauncherUnlockAnimationInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.domain.interactor.InWindowLauncherUnlockAnimationInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ InWindowLauncherUnlockAnimationInteractor this$0;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.InWindowLauncherUnlockAnimationInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, InWindowLauncherUnlockAnimationInteractor inWindowLauncherUnlockAnimationInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = inWindowLauncherUnlockAnimationInteractor;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.InWindowLauncherUnlockAnimationInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.InWindowLauncherUnlockAnimationInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.InWindowLauncherUnlockAnimationInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.InWindowLauncherUnlockAnimationInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.InWindowLauncherUnlockAnimationInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L54
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        boolean r5 = r5.booleanValue()
                        if (r5 == 0) goto L44
                        com.android.systemui.keyguard.domain.interactor.InWindowLauncherUnlockAnimationInteractor r5 = r4.this$0
                        boolean r5 = r5.isLauncherUnderneath()
                        if (r5 == 0) goto L44
                        r5 = r3
                        goto L45
                    L44:
                        r5 = 0
                    L45:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L54
                        return r1
                    L54:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.InWindowLauncherUnlockAnimationInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        Boolean bool = Boolean.FALSE;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(flow, coroutineScope, startedEagerly, bool);
        this.transitioningToGoneWithInWindowAnimation = stateIn;
        this.shouldStartInWindowAnimation = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateIn, ((KeyguardSurfaceBehindRepositoryImpl) ((KeyguardSurfaceBehindRepository) lazy.get())).isSurfaceRemoteAnimationTargetAvailable, new InWindowLauncherUnlockAnimationInteractor$shouldStartInWindowAnimation$1(null)), coroutineScope, startedEagerly, bool);
    }

    public final boolean isLauncherUnderneath() {
        ComponentName componentName;
        String className;
        String str = (String) this.repository.launcherActivityClass.getValue();
        if (str != null) {
            ActivityManager.RunningTaskInfo runningTask = this.activityManager.getRunningTask();
            Boolean valueOf = (runningTask == null || (componentName = runningTask.topActivity) == null || (className = componentName.getClassName()) == null) ? null : Boolean.valueOf(className.equals(str));
            if (valueOf != null) {
                return valueOf.booleanValue();
            }
        }
        return false;
    }
}
