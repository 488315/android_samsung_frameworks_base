package com.android.systemui.deviceentry.ui.binder;

import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
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
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

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

    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.deviceentry.ui.binder.LiftToRunFaceAuthBinder$listener$1] */
    public LiftToRunFaceAuthBinder(CoroutineScope coroutineScope, PackageManager packageManager, AsyncSensorManager asyncSensorManager, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardInteractor keyguardInteractor, PrimaryBouncerInteractor primaryBouncerInteractor, AlternateBouncerInteractor alternateBouncerInteractor, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, PowerInteractor powerInteractor) {
        this.scope = coroutineScope;
        this.packageManager = packageManager;
        this.asyncSensorManager = asyncSensorManager;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.deviceEntryFaceAuthInteractor = deviceEntryFaceAuthInteractor;
        final StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this.isListening = MutableStateFlow;
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.ui.binder.LiftToRunFaceAuthBinder$special$$inlined$filterNot$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.ui.binder.LiftToRunFaceAuthBinder$special$$inlined$filterNot$1$2$1 r0 = (com.android.systemui.deviceentry.ui.binder.LiftToRunFaceAuthBinder$special$$inlined$filterNot$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.ui.binder.LiftToRunFaceAuthBinder$special$$inlined$filterNot$1$2$1 r0 = new com.android.systemui.deviceentry.ui.binder.LiftToRunFaceAuthBinder$special$$inlined$filterNot$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L46
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        java.lang.Boolean r6 = (java.lang.Boolean) r6
                        boolean r6 = r6.booleanValue()
                        if (r6 != 0) goto L46
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L46
                        return r1
                    L46:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.ui.binder.LiftToRunFaceAuthBinder$special$$inlined$filterNot$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.listenForPickupSensor = FlowKt.combine(new Flow() { // from class: com.android.systemui.deviceentry.ui.binder.LiftToRunFaceAuthBinder$special$$inlined$map$1

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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.ui.binder.LiftToRunFaceAuthBinder$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.ui.binder.LiftToRunFaceAuthBinder$special$$inlined$map$1$2$1 r0 = (com.android.systemui.deviceentry.ui.binder.LiftToRunFaceAuthBinder$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.ui.binder.LiftToRunFaceAuthBinder$special$$inlined$map$1$2$1 r0 = new com.android.systemui.deviceentry.ui.binder.LiftToRunFaceAuthBinder$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L44
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        r5.getClass()
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L44
                        return r1
                    L44:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.ui.binder.LiftToRunFaceAuthBinder$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(primaryBouncerInteractor.isShowing, alternateBouncerInteractor.isVisible, new LiftToRunFaceAuthBinder$bouncerShowing$1(null)), new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(powerInteractor.isInteractive, keyguardInteractor.isKeyguardVisible, new LiftToRunFaceAuthBinder$onAwakeKeyguard$1(null)), new LiftToRunFaceAuthBinder$listenForPickupSensor$1(this, null));
        this.listener = new TriggerEventListener() { // from class: com.android.systemui.deviceentry.ui.binder.LiftToRunFaceAuthBinder$listener$1
            @Override // android.hardware.TriggerEventListener
            public final void onTrigger(TriggerEvent triggerEvent) {
                Assert.isMainThread();
                LiftToRunFaceAuthBinder.this.deviceEntryFaceAuthInteractor.onDeviceLifted();
                LiftToRunFaceAuthBinder.this.keyguardUpdateMonitor.requestActiveUnlock(ActiveUnlockConfig.ActiveUnlockRequestOrigin.WAKE, "KeyguardLiftController");
                LiftToRunFaceAuthBinder.this.isListening.updateState(null, Boolean.FALSE);
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
            BuildersKt.launch$default(this.scope, null, null, new LiftToRunFaceAuthBinder$init$1(this, null), 3);
        }
    }
}
