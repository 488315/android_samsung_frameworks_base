package com.android.systemui.deviceentry.domain.interactor;

import com.android.systemui.util.kotlin.FlowKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class BiometricMessageInteractor$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ DeviceEntryFingerprintAuthInteractor $fingerprintAuthInteractor$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ BiometricMessageInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BiometricMessageInteractor$special$$inlined$flatMapLatest$1(Continuation continuation, DeviceEntryFingerprintAuthInteractor deviceEntryFingerprintAuthInteractor, BiometricMessageInteractor biometricMessageInteractor) {
        super(3, continuation);
        this.$fingerprintAuthInteractor$inlined = deviceEntryFingerprintAuthInteractor;
        this.this$0 = biometricMessageInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        BiometricMessageInteractor$special$$inlined$flatMapLatest$1 biometricMessageInteractor$special$$inlined$flatMapLatest$1 = new BiometricMessageInteractor$special$$inlined$flatMapLatest$1((Continuation) obj3, this.$fingerprintAuthInteractor$inlined, this.this$0);
        biometricMessageInteractor$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        biometricMessageInteractor$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return biometricMessageInteractor$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            final boolean booleanValue = ((Boolean) this.L$1).booleanValue();
            final Flow sample = FlowKt.sample(this.$fingerprintAuthInteractor$inlined.fingerprintFailure, this.this$0.biometricSettingsInteractor.fingerprintAuthCurrentlyAllowed);
            final Flow flow = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$fingerprintFailMessage$lambda$9$$inlined$filter$1

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$fingerprintFailMessage$lambda$9$$inlined$filter$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                    /* renamed from: com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$fingerprintFailMessage$lambda$9$$inlined$filter$1$2$1, reason: invalid class name */
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
                            boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$fingerprintFailMessage$lambda$9$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r6
                            com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$fingerprintFailMessage$lambda$9$$inlined$filter$1$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$fingerprintFailMessage$lambda$9$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$fingerprintFailMessage$lambda$9$$inlined$filter$1$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$fingerprintFailMessage$lambda$9$$inlined$filter$1$2$1
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
                            if (r6 == 0) goto L46
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                            java.lang.Object r4 = r4.emit(r5, r0)
                            if (r4 != r1) goto L46
                            return r1
                        L46:
                            kotlin.Unit r4 = kotlin.Unit.INSTANCE
                            return r4
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$fingerprintFailMessage$lambda$9$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            };
            final BiometricMessageInteractor biometricMessageInteractor = this.this$0;
            Flow flow2 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$fingerprintFailMessage$lambda$9$$inlined$map$1

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$fingerprintFailMessage$lambda$9$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ boolean $isUdfps$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ BiometricMessageInteractor this$0;

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                    /* renamed from: com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$fingerprintFailMessage$lambda$9$$inlined$map$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, boolean z, BiometricMessageInteractor biometricMessageInteractor) {
                        this.$this_unsafeFlow = flowCollector;
                        this.$isUdfps$inlined = z;
                        this.this$0 = biometricMessageInteractor;
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
                            boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$fingerprintFailMessage$lambda$9$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r6
                            com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$fingerprintFailMessage$lambda$9$$inlined$map$1$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$fingerprintFailMessage$lambda$9$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$fingerprintFailMessage$lambda$9$$inlined$map$1$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$fingerprintFailMessage$lambda$9$$inlined$map$1$2$1
                            r0.<init>(r6)
                        L18:
                            java.lang.Object r6 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r6)
                            goto L60
                        L27:
                            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                            r4.<init>(r5)
                            throw r4
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r6)
                            java.lang.Boolean r5 = (java.lang.Boolean) r5
                            r5.getClass()
                            com.android.systemui.deviceentry.shared.model.FingerprintFailureMessage r5 = new com.android.systemui.deviceentry.shared.model.FingerprintFailureMessage
                            boolean r6 = r4.$isUdfps$inlined
                            com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor r2 = r4.this$0
                            if (r6 == 0) goto L49
                            android.content.res.Resources r6 = r2.resources
                            r2 = 17040779(0x104058b, float:2.4248548E-38)
                            java.lang.String r6 = r6.getString(r2)
                            goto L52
                        L49:
                            android.content.res.Resources r6 = r2.resources
                            r2 = 17040766(0x104057e, float:2.4248511E-38)
                            java.lang.String r6 = r6.getString(r2)
                        L52:
                            r5.<init>(r6)
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                            java.lang.Object r4 = r4.emit(r5, r0)
                            if (r4 != r1) goto L60
                            return r1
                        L60:
                            kotlin.Unit r4 = kotlin.Unit.INSTANCE
                            return r4
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$fingerprintFailMessage$lambda$9$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2, booleanValue, biometricMessageInteractor), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (kotlinx.coroutines.flow.FlowKt.emitAll(this, flow2, flowCollector) == coroutineSingletons) {
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
