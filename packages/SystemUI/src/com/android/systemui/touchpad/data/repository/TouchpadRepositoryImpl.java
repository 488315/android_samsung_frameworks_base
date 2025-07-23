package com.android.systemui.touchpad.data.repository;

import android.hardware.input.InputManager;
import com.android.systemui.inputdevice.data.repository.InputDeviceRepository;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class TouchpadRepositoryImpl implements TouchpadRepository {
    public final InputManager inputManager;
    public final Flow isAnyTouchpadConnected;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public TouchpadRepositoryImpl(CoroutineDispatcher coroutineDispatcher, InputManager inputManager, InputDeviceRepository inputDeviceRepository) {
        this.inputManager = inputManager;
        final ReadonlySharedFlow readonlySharedFlow = inputDeviceRepository.deviceChange;
        this.isAnyTouchpadConnected = FlowKt.flowOn(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.touchpad.data.repository.TouchpadRepositoryImpl$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.touchpad.data.repository.TouchpadRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ TouchpadRepositoryImpl this$0;

                /* renamed from: com.android.systemui.touchpad.data.repository.TouchpadRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, TouchpadRepositoryImpl touchpadRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = touchpadRepositoryImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.touchpad.data.repository.TouchpadRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.touchpad.data.repository.TouchpadRepositoryImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.touchpad.data.repository.TouchpadRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.touchpad.data.repository.TouchpadRepositoryImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.touchpad.data.repository.TouchpadRepositoryImpl$special$$inlined$map$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L84
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        kotlin.Pair r6 = (kotlin.Pair) r6
                        java.lang.Object r6 = r6.component1()
                        java.util.Collection r6 = (java.util.Collection) r6
                        java.lang.Iterable r6 = (java.lang.Iterable) r6
                        boolean r7 = r6 instanceof java.util.Collection
                        r2 = 0
                        if (r7 == 0) goto L4b
                        r7 = r6
                        java.util.Collection r7 = (java.util.Collection) r7
                        boolean r7 = r7.isEmpty()
                        if (r7 == 0) goto L4b
                        goto L75
                    L4b:
                        java.util.Iterator r6 = r6.iterator()
                    L4f:
                        boolean r7 = r6.hasNext()
                        if (r7 == 0) goto L75
                        java.lang.Object r7 = r6.next()
                        java.lang.Number r7 = (java.lang.Number) r7
                        int r7 = r7.intValue()
                        com.android.systemui.touchpad.data.repository.TouchpadRepositoryImpl r4 = r5.this$0
                        android.hardware.input.InputManager r4 = r4.inputManager
                        android.view.InputDevice r7 = r4.getInputDevice(r7)
                        if (r7 != 0) goto L6b
                        r7 = r2
                        goto L72
                    L6b:
                        r4 = 1048584(0x100008, float:1.469379E-39)
                        boolean r7 = r7.supportsSource(r4)
                    L72:
                        if (r7 == 0) goto L4f
                        r2 = r3
                    L75:
                        java.lang.Boolean r6 = java.lang.Boolean.valueOf(r2)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r6, r0)
                        if (r5 != r1) goto L84
                        return r1
                    L84:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.touchpad.data.repository.TouchpadRepositoryImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), coroutineDispatcher);
    }
}
