package com.android.systemui.deviceentry.domain.interactor;

import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DeviceEntryInteractor$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ DeviceEntryFaceAuthInteractor $faceAuthInteractor$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ DeviceEntryInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryInteractor$special$$inlined$flatMapLatest$1(Continuation continuation, DeviceEntryInteractor deviceEntryInteractor, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor) {
        super(3, continuation);
        this.this$0 = deviceEntryInteractor;
        this.$faceAuthInteractor$inlined = deviceEntryFaceAuthInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DeviceEntryInteractor$special$$inlined$flatMapLatest$1 deviceEntryInteractor$special$$inlined$flatMapLatest$1 = new DeviceEntryInteractor$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0, this.$faceAuthInteractor$inlined);
        deviceEntryInteractor$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        deviceEntryInteractor$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return deviceEntryInteractor$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flow;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Triple triple = (Triple) this.L$1;
            boolean booleanValue = ((Boolean) triple.component1()).booleanValue();
            boolean booleanValue2 = ((Boolean) triple.component2()).booleanValue();
            final boolean booleanValue3 = ((Boolean) triple.component3()).booleanValue();
            if (booleanValue || booleanValue2 || booleanValue3) {
                Flow flow2 = this.this$0.biometricSettingsInteractor.authenticationFlags;
                StateFlow isLockedOut = this.$faceAuthInteractor$inlined.isLockedOut();
                DeviceEntryInteractor deviceEntryInteractor = this.this$0;
                final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 combine = FlowKt.combine(flow2, isLockedOut, deviceEntryInteractor.fingerprintAuthInteractor.isLockedOut, deviceEntryInteractor.trustInteractor.isTrustAgentCurrentlyAllowed, DeviceEntryInteractor$deviceEntryRestrictionReason$1$2.INSTANCE);
                final DeviceEntryInteractor deviceEntryInteractor2 = this.this$0;
                final DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor = this.$faceAuthInteractor$inlined;
                flow = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$deviceEntryRestrictionReason$lambda$6$$inlined$map$1

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                    /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$deviceEntryRestrictionReason$lambda$6$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ DeviceEntryFaceAuthInteractor $faceAuthInteractor$inlined;
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ boolean $trustEnabled$inlined;
                        public final /* synthetic */ DeviceEntryInteractor this$0;

                        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                        /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$deviceEntryRestrictionReason$lambda$6$$inlined$map$1$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector, DeviceEntryInteractor deviceEntryInteractor, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, boolean z) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = deviceEntryInteractor;
                            this.$faceAuthInteractor$inlined = deviceEntryFaceAuthInteractor;
                            this.$trustEnabled$inlined = z;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                        /* JADX WARN: Removed duplicated region for block: B:22:0x00e3 A[RETURN] */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
                            /*
                                Method dump skipped, instructions count: 231
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$deviceEntryRestrictionReason$lambda$6$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2, deviceEntryInteractor2, deviceEntryFaceAuthInteractor, booleanValue3), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
            } else {
                flow = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
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
