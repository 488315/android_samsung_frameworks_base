package com.android.systemui.keyguard.domain.interactor;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.scene.shared.model.Scenes;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardWakeDirectlyToGoneInteractor$setOrCancelAlarmFromWakefulness$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ KeyguardWakeDirectlyToGoneInteractor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            DistinctFlowImpl distinctUntilChangedBy$FlowKt__DistinctKt = FlowKt__DistinctKt.distinctUntilChangedBy$FlowKt__DistinctKt(this.this$0.powerInteractor.detailedWakefulness, new KeyguardWakeDirectlyToGoneInteractor$setOrCancelAlarmFromWakefulness$1$$ExternalSyntheticLambda0(0), FlowKt__DistinctKt.defaultAreEquivalent);
            KeyguardTransitionInteractor keyguardTransitionInteractor = this.this$0.transitionInteractor;
            SceneKey sceneKey = Scenes.Communal;
            final KeyguardState keyguardState = KeyguardState.GONE;
            keyguardTransitionInteractor.getClass();
            keyguardState.checkValidState();
            final ReadonlyStateFlow readonlyStateFlow = keyguardTransitionInteractor.currentKeyguardState;
            Flow sample = FlowKt.sample(distinctUntilChangedBy$FlowKt__DistinctKt, kotlinx.coroutines.flow.FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isCurrentlyIn$$inlined$map$1

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                            boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isCurrentlyIn$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r6
                            com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isCurrentlyIn$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isCurrentlyIn$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isCurrentlyIn$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isCurrentlyIn$$inlined$map$1$2$1
                            r0.<init>(r6)
                        L18:
                            java.lang.Object r6 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r6)
                            goto L4a
                        L27:
                            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                            r4.<init>(r5)
                            throw r4
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r6)
                            com.android.systemui.keyguard.shared.model.KeyguardState r5 = (com.android.systemui.keyguard.shared.model.KeyguardState) r5
                            com.android.systemui.keyguard.shared.model.KeyguardState r6 = r4.$stateWithoutSceneContainer$inlined
                            if (r5 != r6) goto L3a
                            r5 = r3
                            goto L3b
                        L3a:
                            r5 = 0
                        L3b:
                            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                            java.lang.Object r4 = r4.emit(r5, r0)
                            if (r4 != r1) goto L4a
                            return r1
                        L4a:
                            kotlin.Unit r4 = kotlin.Unit.INSTANCE
                            return r4
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isCurrentlyIn$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, keyguardState), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            }), AnonymousClass4.INSTANCE);
            final KeyguardWakeDirectlyToGoneInteractor keyguardWakeDirectlyToGoneInteractor = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$setOrCancelAlarmFromWakefulness$1.5
                /* JADX WARN: Code restructure failed: missing block: B:20:0x0079, code lost:
                
                    if (r11.lockPatternUtils.isSecure(r12) != false) goto L20;
                 */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r12, kotlin.coroutines.Continuation r13) {
                    /*
                        r11 = this;
                        kotlin.Pair r12 = (kotlin.Pair) r12
                        java.lang.Object r13 = r12.component1()
                        com.android.systemui.power.shared.model.WakefulnessModel r13 = (com.android.systemui.power.shared.model.WakefulnessModel) r13
                        java.lang.Object r12 = r12.component2()
                        java.lang.Boolean r12 = (java.lang.Boolean) r12
                        boolean r12 = r12.booleanValue()
                        boolean r0 = r13.isAwake()
                        com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor r11 = com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor.this
                        r11.isAwake = r0
                        if (r0 != 0) goto L88
                        if (r12 == 0) goto L88
                        com.android.systemui.user.domain.interactor.SelectedUserInteractor r12 = r11.selectedUserInteractor
                        int r0 = r12.getSelectedUserId()
                        java.lang.String r1 = "lock_screen_lock_after_timeout"
                        r2 = 5000(0x1388, float:7.006E-42)
                        com.android.systemui.util.settings.SecureSettings r3 = r11.secureSettings
                        int r1 = r3.getIntForUser(r1, r2, r0)
                        long r1 = (long) r1
                        com.android.internal.widget.LockPatternUtils r3 = r11.lockPatternUtils
                        android.app.admin.DevicePolicyManager r3 = r3.getDevicePolicyManager()
                        r4 = 0
                        long r5 = r3.getMaximumTimeToLock(r4, r0)
                        r7 = 0
                        int r3 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
                        if (r3 > 0) goto L41
                        goto L59
                    L41:
                        java.lang.String r3 = "screen_off_timeout"
                        r9 = 30000(0x7530, float:4.2039E-41)
                        com.android.systemui.util.settings.SystemSettings r10 = r11.systemSettings
                        int r0 = r10.getIntForUser(r3, r9, r0)
                        long r9 = (long) r0
                        long r9 = java.lang.Math.max(r9, r7)
                        long r5 = r5 - r9
                        long r0 = java.lang.Math.min(r5, r1)
                        long r1 = java.lang.Math.max(r0, r7)
                    L59:
                        com.android.systemui.power.shared.model.WakeSleepReason r0 = com.android.systemui.power.shared.model.WakeSleepReason.TIMEOUT
                        com.android.systemui.power.shared.model.WakeSleepReason r13 = r13.lastSleepReason
                        if (r13 != r0) goto L63
                        int r0 = (r1 > r7 ? 1 : (r1 == r7 ? 0 : -1))
                        if (r0 > 0) goto L7c
                    L63:
                        com.android.systemui.power.shared.model.WakeSleepReason r0 = com.android.systemui.power.shared.model.WakeSleepReason.POWER_BUTTON
                        if (r13 != r0) goto L90
                        int r12 = r12.getSelectedUserId()
                        com.android.internal.widget.LockPatternUtils r13 = r11.lockPatternUtils
                        boolean r13 = r13.getPowerButtonInstantlyLocks(r12)
                        if (r13 != 0) goto L90
                        com.android.internal.widget.LockPatternUtils r13 = r11.lockPatternUtils
                        boolean r12 = r13.isSecure(r12)
                        if (r12 != 0) goto L7c
                        goto L90
                    L7c:
                        com.android.systemui.keyguard.data.repository.KeyguardRepository r11 = r11.repository
                        com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl r11 = (com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl) r11
                        java.lang.Boolean r12 = java.lang.Boolean.TRUE
                        kotlinx.coroutines.flow.StateFlowImpl r11 = r11._canIgnoreAuthAndReturnToGone
                        r11.updateState(r4, r12)
                        goto L90
                    L88:
                        if (r0 == 0) goto L90
                        int r12 = r11.timeoutCounter
                        int r12 = r12 + 1
                        r11.timeoutCounter = r12
                    L90:
                        kotlin.Unit r11 = kotlin.Unit.INSTANCE
                        return r11
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$setOrCancelAlarmFromWakefulness$1.AnonymousClass5.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            };
            this.label = 1;
            if (sample.collect(flowCollector, this) == coroutineSingletons) {
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
