package com.android.systemui.power.domain.interactor;

import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.power.data.repository.PowerRepository;
import com.android.systemui.power.data.repository.PowerRepositoryImpl;
import com.android.systemui.power.shared.model.ScreenPowerState;
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.statusbar.phone.ScreenOffAnimation;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.flow.DistinctFlowImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__DistinctKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

public final class PowerInteractor {
    public static final Function2 checkEquivalentUnlessEmitDuplicatesUnderTest;
    public static boolean emitDuplicateWakefulnessValue;
    public final ReadonlyStateFlow detailedWakefulness;
    public final FalsingCollector falsingCollector;
    public final PowerInteractor$special$$inlined$map$2 isAsleep;
    public final DistinctFlowImpl isAwake;
    public final Flow isInteractive;
    public final PowerRepository repository;
    public final ScreenOffAnimationController screenOffAnimationController;
    public final ReadonlyStateFlow screenPowerState;
    public final StatusBarStateController statusBarStateController;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        checkEquivalentUnlessEmitDuplicatesUnderTest = new Function2() { // from class: com.android.systemui.power.domain.interactor.PowerInteractor$Companion$checkEquivalentUnlessEmitDuplicatesUnderTest$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                boolean booleanValue = ((Boolean) obj).booleanValue();
                boolean booleanValue2 = ((Boolean) obj2).booleanValue();
                boolean z = false;
                if (PowerInteractor.emitDuplicateWakefulnessValue) {
                    PowerInteractor.emitDuplicateWakefulnessValue = false;
                } else if (booleanValue == booleanValue2) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        };
    }

    public PowerInteractor(PowerRepository powerRepository, FalsingCollector falsingCollector, ScreenOffAnimationController screenOffAnimationController, StatusBarStateController statusBarStateController) {
        this.repository = powerRepository;
        this.falsingCollector = falsingCollector;
        this.screenOffAnimationController = screenOffAnimationController;
        this.statusBarStateController = statusBarStateController;
        PowerRepositoryImpl powerRepositoryImpl = (PowerRepositoryImpl) powerRepository;
        this.isInteractive = powerRepositoryImpl.isInteractive;
        final ReadonlyStateFlow readonlyStateFlow = powerRepositoryImpl.wakefulness;
        this.detailedWakefulness = readonlyStateFlow;
        Flow flow = new Flow() { // from class: com.android.systemui.power.domain.interactor.PowerInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.power.domain.interactor.PowerInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.power.domain.interactor.PowerInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.power.domain.interactor.PowerInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.power.domain.interactor.PowerInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.power.domain.interactor.PowerInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.power.domain.interactor.PowerInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.power.domain.interactor.PowerInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L47
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.power.shared.model.WakefulnessModel r5 = (com.android.systemui.power.shared.model.WakefulnessModel) r5
                        boolean r5 = r5.isAwake()
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L47
                        return r1
                    L47:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.power.domain.interactor.PowerInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        Function2 function2 = checkEquivalentUnlessEmitDuplicatesUnderTest;
        Function1 function1 = FlowKt__DistinctKt.defaultKeySelector;
        TypeIntrinsics.beforeCheckcastToFunctionOfArity(2, function2);
        final DistinctFlowImpl distinctUntilChangedBy$FlowKt__DistinctKt = FlowKt__DistinctKt.distinctUntilChangedBy$FlowKt__DistinctKt(flow, function1, function2);
        this.isAwake = distinctUntilChangedBy$FlowKt__DistinctKt;
        this.isAsleep = new Flow() { // from class: com.android.systemui.power.domain.interactor.PowerInteractor$special$$inlined$map$2

            /* renamed from: com.android.systemui.power.domain.interactor.PowerInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.power.domain.interactor.PowerInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.power.domain.interactor.PowerInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.power.domain.interactor.PowerInteractor$special$$inlined$map$2$2$1 r0 = (com.android.systemui.power.domain.interactor.PowerInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.power.domain.interactor.PowerInteractor$special$$inlined$map$2$2$1 r0 = new com.android.systemui.power.domain.interactor.PowerInteractor$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L48
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        boolean r5 = r5.booleanValue()
                        r5 = r5 ^ r3
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.power.domain.interactor.PowerInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.screenPowerState = powerRepositoryImpl.screenPowerState;
    }

    public static void onUserTouch$default(PowerInteractor powerInteractor) {
        PowerRepositoryImpl powerRepositoryImpl = (PowerRepositoryImpl) powerInteractor.repository;
        powerRepositoryImpl.manager.userActivity(powerRepositoryImpl.systemClock.uptimeMillis(), 2, 0);
    }

    public final void onScreenPowerStateUpdated(ScreenPowerState screenPowerState) {
        ((PowerRepositoryImpl) this.repository)._screenPowerState.updateState(null, screenPowerState);
    }

    public final void wakeUpForFullScreenIntent(String str) {
        if ("com.android.cts.verifier".equals(str)) {
            PowerRepositoryImpl powerRepositoryImpl = (PowerRepositoryImpl) this.repository;
            if (((WakefulnessModel) powerRepositoryImpl.wakefulness.$$delegate_0.getValue()).isAsleep() || this.statusBarStateController.isDozing()) {
                powerRepositoryImpl.wakeUp(2, "full_screen_intent");
            }
        }
    }

    public final void wakeUpIfDozing(int i, String str) {
        if (this.statusBarStateController.isDozing()) {
            List list = this.screenOffAnimationController.animations;
            if (!(list instanceof Collection) || !((ArrayList) list).isEmpty()) {
                Iterator it = ((ArrayList) list).iterator();
                while (it.hasNext()) {
                    if (!(!((ScreenOffAnimation) it.next()).isAnimationPlaying())) {
                        return;
                    }
                }
            }
            ((PowerRepositoryImpl) this.repository).wakeUp(i, str);
            this.falsingCollector.onScreenOnFromTouch();
        }
    }
}
