package com.android.systemui.power.domain.interactor;

import com.android.systemui.camera.CameraGestureHelper;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.power.data.repository.PowerRepository;
import com.android.systemui.power.data.repository.PowerRepositoryImpl;
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.statusbar.phone.ScreenOffAnimation;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.inject.Provider;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.DistinctFlowImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class PowerInteractor {
    public static final PowerInteractor$$ExternalSyntheticLambda0 checkEquivalentUnlessEmitDuplicatesUnderTest;
    public final Provider cameraGestureHelper;
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
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        checkEquivalentUnlessEmitDuplicatesUnderTest = new PowerInteractor$$ExternalSyntheticLambda0();
    }

    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.systemui.power.domain.interactor.PowerInteractor$special$$inlined$map$2] */
    public PowerInteractor(PowerRepository powerRepository, FalsingCollector falsingCollector, ScreenOffAnimationController screenOffAnimationController, StatusBarStateController statusBarStateController, Provider provider) {
        this.repository = powerRepository;
        this.falsingCollector = falsingCollector;
        this.screenOffAnimationController = screenOffAnimationController;
        this.statusBarStateController = statusBarStateController;
        this.cameraGestureHelper = provider;
        PowerRepositoryImpl powerRepositoryImpl = (PowerRepositoryImpl) powerRepository;
        this.isInteractive = powerRepositoryImpl.isInteractive;
        final ReadonlyStateFlow readonlyStateFlow = powerRepositoryImpl.wakefulness;
        this.detailedWakefulness = readonlyStateFlow;
        final DistinctFlowImpl distinctFlowImplDistinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.power.domain.interactor.PowerInteractor$special$$inlined$map$1

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
                        Boolean boolValueOf = Boolean.valueOf(((WakefulnessModel) obj).isAwake());
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, checkEquivalentUnlessEmitDuplicatesUnderTest);
        this.isAwake = distinctFlowImplDistinctUntilChanged;
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
                        Boolean boolValueOf = Boolean.valueOf(!((Boolean) obj).booleanValue());
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
                Object objCollect = distinctFlowImplDistinctUntilChanged.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.screenPowerState = powerRepositoryImpl.screenPowerState;
        FlowKt.asStateFlow(powerRepositoryImpl.dozeScreenState);
    }

    public static void onUserTouch$default(PowerInteractor powerInteractor) {
        PowerRepositoryImpl powerRepositoryImpl = (PowerRepositoryImpl) powerInteractor.repository;
        powerRepositoryImpl.manager.userActivity(powerRepositoryImpl.systemClock.uptimeMillis(), 2, 0);
    }

    public final boolean isPowerButtonGestureSuppressed() {
        CameraGestureHelper cameraGestureHelper = (CameraGestureHelper) this.cameraGestureHelper.get();
        return (cameraGestureHelper == null || cameraGestureHelper.canCameraGestureBeLaunched(this.statusBarStateController.getState())) ? false : true;
    }

    public final void wakeUpForFullScreenIntent() {
        PowerRepositoryImpl powerRepositoryImpl = (PowerRepositoryImpl) this.repository;
        if (((WakefulnessModel) powerRepositoryImpl.wakefulness.$$delegate_0.getValue()).isAsleep() || this.statusBarStateController.isDozing()) {
            powerRepositoryImpl.wakeUp(2, "full_screen_intent");
        }
    }

    public final void wakeUpIfDozing(int i, String str) {
        if (this.statusBarStateController.isDozing()) {
            List list = this.screenOffAnimationController.animations;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    if (((ScreenOffAnimation) it.next()).isAnimationPlaying()) {
                        return;
                    }
                }
            }
            ((PowerRepositoryImpl) this.repository).wakeUp(i, str);
            this.falsingCollector.onScreenOnFromTouch();
        }
    }
}
