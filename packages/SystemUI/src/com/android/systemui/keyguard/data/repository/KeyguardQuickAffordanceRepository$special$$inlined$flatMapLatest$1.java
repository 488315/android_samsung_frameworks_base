package com.android.systemui.keyguard.data.repository;

import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceSelectionManager;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardQuickAffordanceRepository$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ KeyguardQuickAffordanceRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardQuickAffordanceRepository$special$$inlined$flatMapLatest$1(Continuation continuation, KeyguardQuickAffordanceRepository keyguardQuickAffordanceRepository) {
        super(3, continuation);
        this.this$0 = keyguardQuickAffordanceRepository;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardQuickAffordanceRepository$special$$inlined$flatMapLatest$1 keyguardQuickAffordanceRepository$special$$inlined$flatMapLatest$1 = new KeyguardQuickAffordanceRepository$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        keyguardQuickAffordanceRepository$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        keyguardQuickAffordanceRepository$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return keyguardQuickAffordanceRepository$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            final Flow mo1960getSelections = ((KeyguardQuickAffordanceSelectionManager) this.L$1).mo1960getSelections();
            final KeyguardQuickAffordanceRepository keyguardQuickAffordanceRepository = this.this$0;
            Flow flow = new Flow() { // from class: com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$selections$lambda$4$$inlined$map$1

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$selections$lambda$4$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ KeyguardQuickAffordanceRepository this$0;

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                    /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$selections$lambda$4$$inlined$map$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, KeyguardQuickAffordanceRepository keyguardQuickAffordanceRepository) {
                        this.$this_unsafeFlow = flowCollector;
                        this.this$0 = keyguardQuickAffordanceRepository;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r10, kotlin.coroutines.Continuation r11) {
                        /*
                            r9 = this;
                            boolean r0 = r11 instanceof com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$selections$lambda$4$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r11
                            com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$selections$lambda$4$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$selections$lambda$4$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$selections$lambda$4$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$selections$lambda$4$$inlined$map$1$2$1
                            r0.<init>(r11)
                        L18:
                            java.lang.Object r11 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r11)
                            goto L9a
                        L27:
                            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                            r9.<init>(r10)
                            throw r9
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r11)
                            java.util.Map r10 = (java.util.Map) r10
                            java.util.LinkedHashMap r11 = new java.util.LinkedHashMap
                            int r2 = r10.size()
                            int r2 = kotlin.collections.MapsKt__MapsJVMKt.mapCapacity(r2)
                            r11.<init>(r2)
                            java.util.Set r10 = r10.entrySet()
                            java.lang.Iterable r10 = (java.lang.Iterable) r10
                            java.util.Iterator r10 = r10.iterator()
                        L4b:
                            boolean r2 = r10.hasNext()
                            if (r2 == 0) goto L8f
                            java.lang.Object r2 = r10.next()
                            java.util.Map$Entry r2 = (java.util.Map.Entry) r2
                            java.lang.Object r4 = r2.getKey()
                            java.lang.Object r2 = r2.getValue()
                            java.util.List r2 = (java.util.List) r2
                            com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository r5 = r9.this$0
                            java.util.Set r5 = r5.configs
                            java.lang.Iterable r5 = (java.lang.Iterable) r5
                            java.util.ArrayList r6 = new java.util.ArrayList
                            r6.<init>()
                            java.util.Iterator r5 = r5.iterator()
                        L70:
                            boolean r7 = r5.hasNext()
                            if (r7 == 0) goto L8b
                            java.lang.Object r7 = r5.next()
                            r8 = r7
                            com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig r8 = (com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig) r8
                            java.lang.String r8 = r8.getKey()
                            boolean r8 = r2.contains(r8)
                            if (r8 == 0) goto L70
                            r6.add(r7)
                            goto L70
                        L8b:
                            r11.put(r4, r6)
                            goto L4b
                        L8f:
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r9 = r9.$this_unsafeFlow
                            java.lang.Object r9 = r9.emit(r11, r0)
                            if (r9 != r1) goto L9a
                            return r1
                        L9a:
                            kotlin.Unit r9 = kotlin.Unit.INSTANCE
                            return r9
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$selections$lambda$4$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2, keyguardQuickAffordanceRepository), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            };
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
