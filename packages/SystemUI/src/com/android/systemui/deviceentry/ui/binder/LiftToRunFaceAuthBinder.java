package com.android.systemui.deviceentry.ui.binder;

import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.keyguard.ActiveUnlockConfig;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.CoreStartable;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.util.Assert;
import com.android.systemui.util.sensors.AsyncSensorManager;
import java.io.PrintWriter;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class LiftToRunFaceAuthBinder implements CoreStartable {
    public final AsyncSensorManager asyncSensorManager;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 bouncerShowing;
    public final DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor;
    public final StateFlowImpl isListening;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 listenForPickupSensor;
    public final LiftToRunFaceAuthBinder$listener$1 listener;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 onAwakeKeyguard;
    public final PackageManager packageManager;
    public Sensor pickupSensor;
    public final CoroutineScope scope;
    public final LiftToRunFaceAuthBinder$special$$inlined$map$1 stoppedListening;

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.deviceentry.ui.binder.LiftToRunFaceAuthBinder$special$$inlined$map$1, kotlinx.coroutines.flow.Flow] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.deviceentry.ui.binder.LiftToRunFaceAuthBinder$listener$1] */
    public LiftToRunFaceAuthBinder(CoroutineScope coroutineScope, PackageManager packageManager, AsyncSensorManager asyncSensorManager, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardInteractor keyguardInteractor, PrimaryBouncerInteractor primaryBouncerInteractor, AlternateBouncerInteractor alternateBouncerInteractor, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, PowerInteractor powerInteractor) {
        this.scope = coroutineScope;
        this.packageManager = packageManager;
        this.asyncSensorManager = asyncSensorManager;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.deviceEntryFaceAuthInteractor = deviceEntryFaceAuthInteractor;
        final StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this.isListening = stateFlowImplMutableStateFlow;
        final Flow flow = new Flow() { // from class: com.android.systemui.deviceentry.ui.binder.LiftToRunFaceAuthBinder$special$$inlined$filterNot$1

            /* renamed from: com.android.systemui.deviceentry.ui.binder.LiftToRunFaceAuthBinder$special$$inlined$filterNot$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.ui.binder.LiftToRunFaceAuthBinder$special$$inlined$filterNot$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
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
                        if (!((Boolean) obj).booleanValue()) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
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
                Object objCollect = stateFlowImplMutableStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        ?? r1 = new Flow() { // from class: com.android.systemui.deviceentry.ui.binder.LiftToRunFaceAuthBinder$special$$inlined$map$1

            /* renamed from: com.android.systemui.deviceentry.ui.binder.LiftToRunFaceAuthBinder$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.ui.binder.LiftToRunFaceAuthBinder$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        ((Boolean) obj).getClass();
                        Unit unit = Unit.INSTANCE;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(unit, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.stoppedListening = r1;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(powerInteractor.isInteractive, keyguardInteractor.isKeyguardVisible, new LiftToRunFaceAuthBinder$onAwakeKeyguard$1(null));
        this.onAwakeKeyguard = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$12 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(primaryBouncerInteractor.isShowing, alternateBouncerInteractor.isVisible, new LiftToRunFaceAuthBinder$bouncerShowing$1(null));
        this.bouncerShowing = flowKt__ZipKt$combine$$inlined$unsafeFlow$12;
        this.listenForPickupSensor = FlowKt.combine(r1, flowKt__ZipKt$combine$$inlined$unsafeFlow$12, flowKt__ZipKt$combine$$inlined$unsafeFlow$1, new LiftToRunFaceAuthBinder$listenForPickupSensor$1(this, null));
        this.listener = new TriggerEventListener() { // from class: com.android.systemui.deviceentry.ui.binder.LiftToRunFaceAuthBinder$listener$1
            @Override // android.hardware.TriggerEventListener
            public final void onTrigger(TriggerEvent triggerEvent) {
                Assert.isMainThread();
                this.this$0.deviceEntryFaceAuthInteractor.onDeviceLifted();
                this.this$0.keyguardUpdateMonitor.requestActiveUnlock(ActiveUnlockConfig.ActiveUnlockRequestOrigin.WAKE, "KeyguardLiftController");
                this.this$0.isListening.updateState(null, Boolean.FALSE);
            }
        };
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("LiftToRunFaceAuthBinder:");
        printWriter.println("  pickupSensor: " + this.pickupSensor);
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("  isListening: ", this.isListening.getValue(), printWriter);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        if (this.packageManager.hasSystemFeature("android.hardware.biometrics.face")) {
            this.pickupSensor = this.asyncSensorManager.getDefaultSensor(25);
            CoroutineTracingKt.launchTraced$default(this.scope, null, null, new LiftToRunFaceAuthBinder$init$1(this, null), 7);
        }
    }
}
