package com.android.systemui.keyguard.domain.interactor;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.pluginlock.PluginLockInstancePolicy;
import com.android.systemui.power.shared.model.WakeSleepReason;
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.kotlin.FlowKt;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.DistinctFlowImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__DistinctKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
final class KeyguardWakeDirectlyToGoneInteractor$setOrCancelAlarmFromWakefulness$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ KeyguardWakeDirectlyToGoneInteractor this$0;

    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$setOrCancelAlarmFromWakefulness$1$4, reason: invalid class name */
    final /* synthetic */ class AnonymousClass4 extends AdaptedFunctionReference implements Function3 {
        public static final AnonymousClass4 INSTANCE = new AnonymousClass4();

        public AnonymousClass4() {
            super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Boolean bool = (Boolean) obj2;
            bool.booleanValue();
            return new Pair((WakefulnessModel) obj, bool);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardWakeDirectlyToGoneInteractor$setOrCancelAlarmFromWakefulness$1(KeyguardWakeDirectlyToGoneInteractor keyguardWakeDirectlyToGoneInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardWakeDirectlyToGoneInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyguardWakeDirectlyToGoneInteractor$setOrCancelAlarmFromWakefulness$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardWakeDirectlyToGoneInteractor$setOrCancelAlarmFromWakefulness$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            DistinctFlowImpl distinctFlowImplDistinctUntilChangedBy$FlowKt__DistinctKt = FlowKt__DistinctKt.distinctUntilChangedBy$FlowKt__DistinctKt(this.this$0.powerInteractor.detailedWakefulness, new KeyguardWakeDirectlyToGoneInteractor$setOrCancelAlarmFromWakefulness$1$$ExternalSyntheticLambda0(0), FlowKt__DistinctKt.defaultAreEquivalent);
            KeyguardTransitionInteractor keyguardTransitionInteractor = this.this$0.transitionInteractor;
            SceneKey sceneKey = Scenes.Communal;
            final KeyguardState keyguardState = KeyguardState.GONE;
            keyguardTransitionInteractor.getClass();
            keyguardState.checkValidState();
            final ReadonlyStateFlow readonlyStateFlow = keyguardTransitionInteractor.currentKeyguardState;
            Flow flowSample = FlowKt.sample(distinctFlowImplDistinctUntilChangedBy$FlowKt__DistinctKt, kotlinx.coroutines.flow.FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isCurrentlyIn$$inlined$map$1

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isCurrentlyIn$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ KeyguardState $stateWithoutSceneContainer$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isCurrentlyIn$$inlined$map$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, KeyguardState keyguardState) {
                        this.$this_unsafeFlow = flowCollector;
                        this.$stateWithoutSceneContainer$inlined = keyguardState;
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
                            Boolean boolValueOf = Boolean.valueOf(((KeyguardState) obj) == this.$stateWithoutSceneContainer$inlined);
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
                    Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, keyguardState), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            }), AnonymousClass4.INSTANCE);
            final KeyguardWakeDirectlyToGoneInteractor keyguardWakeDirectlyToGoneInteractor = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$setOrCancelAlarmFromWakefulness$1.5
                /* JADX WARN: Removed duplicated region for block: B:20:0x007d  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj2, Continuation continuation) {
                    Pair pair = (Pair) obj2;
                    WakefulnessModel wakefulnessModel = (WakefulnessModel) pair.component1();
                    boolean zBooleanValue = ((Boolean) pair.component2()).booleanValue();
                    boolean zIsAwake = wakefulnessModel.isAwake();
                    KeyguardWakeDirectlyToGoneInteractor keyguardWakeDirectlyToGoneInteractor2 = keyguardWakeDirectlyToGoneInteractor;
                    keyguardWakeDirectlyToGoneInteractor2.isAwake = zIsAwake;
                    if (!zIsAwake && zBooleanValue) {
                        SelectedUserInteractor selectedUserInteractor = keyguardWakeDirectlyToGoneInteractor2.selectedUserInteractor;
                        int selectedUserId = selectedUserInteractor.getSelectedUserId();
                        long intForUser = keyguardWakeDirectlyToGoneInteractor2.secureSettings.getIntForUser("lock_screen_lock_after_timeout", 5000, selectedUserId);
                        long maximumTimeToLock = keyguardWakeDirectlyToGoneInteractor2.lockPatternUtils.getDevicePolicyManager().getMaximumTimeToLock(null, selectedUserId);
                        if (maximumTimeToLock > 0) {
                            intForUser = Math.max(Math.min(maximumTimeToLock - Math.max(keyguardWakeDirectlyToGoneInteractor2.systemSettings.getIntForUser("screen_off_timeout", PluginLockInstancePolicy.DISABLED_BY_SUB_USER, selectedUserId), 0L), intForUser), 0L);
                        }
                        WakeSleepReason wakeSleepReason = WakeSleepReason.TIMEOUT;
                        WakeSleepReason wakeSleepReason2 = wakefulnessModel.lastSleepReason;
                        if (wakeSleepReason2 != wakeSleepReason || intForUser <= 0) {
                            if (wakeSleepReason2 == WakeSleepReason.POWER_BUTTON) {
                                int selectedUserId2 = selectedUserInteractor.getSelectedUserId();
                                if (!keyguardWakeDirectlyToGoneInteractor2.lockPatternUtils.getPowerButtonInstantlyLocks(selectedUserId2) && keyguardWakeDirectlyToGoneInteractor2.lockPatternUtils.isSecure(selectedUserId2)) {
                                    ((KeyguardRepositoryImpl) keyguardWakeDirectlyToGoneInteractor2.repository)._canIgnoreAuthAndReturnToGone.updateState(null, Boolean.TRUE);
                                }
                            }
                        }
                    } else if (zIsAwake) {
                        keyguardWakeDirectlyToGoneInteractor2.timeoutCounter++;
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (flowSample.collect(flowCollector, this) == coroutineSingletons) {
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
