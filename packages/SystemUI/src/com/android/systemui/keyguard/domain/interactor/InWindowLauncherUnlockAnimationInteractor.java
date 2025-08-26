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
import kotlin.ResultKt;
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

/* loaded from: classes2.dex */
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
        final Flow flowIsInTransition = keyguardTransitionInteractor.isInTransition(Edge.Companion.create$default(companion, Scenes.Gone), Edge.Companion.create$default(companion, null, KeyguardState.GONE, 1));
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
                        Boolean boolValueOf = Boolean.valueOf(((Boolean) obj).booleanValue() && this.this$0.isLauncherUnderneath());
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
                Object objCollect = flowIsInTransition.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        Boolean bool = Boolean.FALSE;
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flow, coroutineScope, startedEagerly, bool);
        this.transitioningToGoneWithInWindowAnimation = readonlyStateFlowStateIn;
        this.shouldStartInWindowAnimation = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlowStateIn, ((KeyguardSurfaceBehindRepositoryImpl) ((KeyguardSurfaceBehindRepository) lazy.get())).isSurfaceRemoteAnimationTargetAvailable, new InWindowLauncherUnlockAnimationInteractor$shouldStartInWindowAnimation$1(null)), coroutineScope, startedEagerly, bool);
    }

    public final boolean isLauncherUnderneath() {
        ComponentName componentName;
        String className;
        String str = (String) this.repository.launcherActivityClass.getValue();
        if (str == null) {
            return false;
        }
        ActivityManager.RunningTaskInfo runningTask = this.activityManager.getRunningTask();
        Boolean boolValueOf = (runningTask == null || (componentName = runningTask.topActivity) == null || (className = componentName.getClassName()) == null) ? null : Boolean.valueOf(className.equals(str));
        if (boolValueOf != null) {
            return boolValueOf.booleanValue();
        }
        return false;
    }
}
