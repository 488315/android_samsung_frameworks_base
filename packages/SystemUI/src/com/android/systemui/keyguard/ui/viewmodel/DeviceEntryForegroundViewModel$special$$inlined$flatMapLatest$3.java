package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlow;

public final class DeviceEntryForegroundViewModel$special$$inlined$flatMapLatest$3 extends SuspendLambda implements Function3 {
    final /* synthetic */ ConfigurationInteractor $configurationInteractor$inlined;
    final /* synthetic */ UdfpsOverlayInteractor $udfpsOverlayInteractor$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ DeviceEntryForegroundViewModel this$0;

    public DeviceEntryForegroundViewModel$special$$inlined$flatMapLatest$3(Continuation continuation, UdfpsOverlayInteractor udfpsOverlayInteractor, ConfigurationInteractor configurationInteractor, DeviceEntryForegroundViewModel deviceEntryForegroundViewModel) {
        super(3, continuation);
        this.$udfpsOverlayInteractor$inlined = udfpsOverlayInteractor;
        this.$configurationInteractor$inlined = configurationInteractor;
        this.this$0 = deviceEntryForegroundViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DeviceEntryForegroundViewModel$special$$inlined$flatMapLatest$3 deviceEntryForegroundViewModel$special$$inlined$flatMapLatest$3 = new DeviceEntryForegroundViewModel$special$$inlined$flatMapLatest$3((Continuation) obj3, this.$udfpsOverlayInteractor$inlined, this.$configurationInteractor$inlined, this.this$0);
        deviceEntryForegroundViewModel$special$$inlined$flatMapLatest$3.L$0 = (FlowCollector) obj;
        deviceEntryForegroundViewModel$special$$inlined$flatMapLatest$3.L$1 = obj2;
        return deviceEntryForegroundViewModel$special$$inlined$flatMapLatest$3.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flow;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                flow = this.$udfpsOverlayInteractor$inlined.iconPadding;
            } else {
                final StateFlow stateFlow = this.$configurationInteractor$inlined.scaleForResolution;
                final DeviceEntryForegroundViewModel deviceEntryForegroundViewModel = this.this$0;
                flow = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryForegroundViewModel$padding$lambda$5$$inlined$map$1

                    /* renamed from: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryForegroundViewModel$padding$lambda$5$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ DeviceEntryForegroundViewModel this$0;

                        /* renamed from: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryForegroundViewModel$padding$lambda$5$$inlined$map$1$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector, DeviceEntryForegroundViewModel deviceEntryForegroundViewModel) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = deviceEntryForegroundViewModel;
                        }

                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                            /*
                                r4 = this;
                                boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.DeviceEntryForegroundViewModel$padding$lambda$5$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.systemui.keyguard.ui.viewmodel.DeviceEntryForegroundViewModel$padding$lambda$5$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.DeviceEntryForegroundViewModel$padding$lambda$5$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.keyguard.ui.viewmodel.DeviceEntryForegroundViewModel$padding$lambda$5$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.DeviceEntryForegroundViewModel$padding$lambda$5$$inlined$map$1$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L5d
                            L27:
                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                r4.<init>(r5)
                                throw r4
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r6)
                                java.lang.Number r5 = (java.lang.Number) r5
                                float r5 = r5.floatValue()
                                com.android.systemui.keyguard.ui.viewmodel.DeviceEntryForegroundViewModel r6 = r4.this$0
                                android.content.Context r6 = r6.context
                                android.content.res.Resources r6 = r6.getResources()
                                r2 = 2131167041(0x7f070741, float:1.7948344E38)
                                int r6 = r6.getDimensionPixelSize(r2)
                                float r6 = (float) r6
                                float r6 = r6 * r5
                                int r5 = kotlin.math.MathKt__MathJVMKt.roundToInt(r6)
                                java.lang.Integer r6 = new java.lang.Integer
                                r6.<init>(r5)
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                java.lang.Object r4 = r4.emit(r6, r0)
                                if (r4 != r1) goto L5d
                                return r1
                            L5d:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryForegroundViewModel$padding$lambda$5$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2, deviceEntryForegroundViewModel), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
            }
            this.label = 1;
            if (FlowKt.emitAll(this, flow, flowCollector) == coroutineSingletons) {
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
