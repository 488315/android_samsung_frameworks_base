package com.android.systemui.flags;

import com.android.systemui.flags.ConditionalRestarter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class ConditionalRestarter$scheduleRestart$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ConditionalRestarter this$0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.flags.ConditionalRestarter$scheduleRestart$1$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function3 {
        private /* synthetic */ Object L$0;
        /* synthetic */ boolean Z$0;
        int label;
        final /* synthetic */ ConditionalRestarter this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(ConditionalRestarter conditionalRestarter, Continuation continuation) {
            super(3, continuation);
            this.this$0 = conditionalRestarter;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            boolean booleanValue = ((Boolean) obj2).booleanValue();
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.this$0, (Continuation) obj3);
            anonymousClass3.L$0 = (FlowCollector) obj;
            anonymousClass3.Z$0 = booleanValue;
            return anonymousClass3.invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:14:0x004e A[RETURN] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r7) {
            /*
                r6 = this;
                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r1 = r6.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L20
                if (r1 == r3) goto L18
                if (r1 != r2) goto L10
                kotlin.ResultKt.throwOnFailure(r7)
                goto L4f
            L10:
                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                r6.<init>(r7)
                throw r6
            L18:
                java.lang.Object r1 = r6.L$0
                kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
                kotlin.ResultKt.throwOnFailure(r7)
                goto L41
            L20:
                kotlin.ResultKt.throwOnFailure(r7)
                java.lang.Object r7 = r6.L$0
                r1 = r7
                kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
                boolean r7 = r6.Z$0
                if (r7 == 0) goto L4f
                java.util.concurrent.TimeUnit r7 = java.util.concurrent.TimeUnit.SECONDS
                com.android.systemui.flags.ConditionalRestarter r4 = r6.this$0
                long r4 = r4.restartDelaySec
                long r4 = r7.toMillis(r4)
                r6.L$0 = r1
                r6.label = r3
                java.lang.Object r7 = kotlinx.coroutines.DelayKt.delay(r4, r6)
                if (r7 != r0) goto L41
                return r0
            L41:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                r3 = 0
                r6.L$0 = r3
                r6.label = r2
                java.lang.Object r6 = r1.emit(r7, r6)
                if (r6 != r0) goto L4f
                return r0
            L4f:
                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.flags.ConditionalRestarter$scheduleRestart$1.AnonymousClass3.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConditionalRestarter$scheduleRestart$1(ConditionalRestarter conditionalRestarter, Continuation continuation) {
        super(2, continuation);
        this.this$0 = conditionalRestarter;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ConditionalRestarter$scheduleRestart$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ConditionalRestarter$scheduleRestart$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Set set = this.this$0.conditions;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set, 10));
            Iterator it = set.iterator();
            while (it.hasNext()) {
                arrayList.add(((ConditionalRestarter.Condition) it.next()).getCanRestartNow());
            }
            final Flow[] flowArr = (Flow[]) CollectionsKt___CollectionsKt.toList(arrayList).toArray(new Flow[0]);
            ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(new Flow() { // from class: com.android.systemui.flags.ConditionalRestarter$scheduleRestart$1$invokeSuspend$$inlined$combine$1

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.flags.ConditionalRestarter$scheduleRestart$1$invokeSuspend$$inlined$combine$1$3, reason: invalid class name */
                public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                    private /* synthetic */ Object L$0;
                    /* synthetic */ Object L$1;
                    int label;

                    public AnonymousClass3(Continuation continuation) {
                        super(3, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3);
                        anonymousClass3.L$0 = (FlowCollector) obj;
                        anonymousClass3.L$1 = (Object[]) obj2;
                        return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            FlowCollector flowCollector = (FlowCollector) this.L$0;
                            Boolean[] boolArr = (Boolean[]) ((Object[]) this.L$1);
                            int length = boolArr.length;
                            boolean z = false;
                            int i2 = 0;
                            while (true) {
                                if (i2 >= length) {
                                    z = true;
                                    break;
                                }
                                if (!boolArr[i2].booleanValue()) {
                                    break;
                                }
                                i2++;
                            }
                            Boolean valueOf = Boolean.valueOf(z);
                            this.label = 1;
                            if (flowCollector.emit(valueOf, this) == coroutineSingletons) {
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

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    final Flow[] flowArr2 = flowArr;
                    Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.flags.ConditionalRestarter$scheduleRestart$1$invokeSuspend$$inlined$combine$1.2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return new Boolean[flowArr2.length];
                        }
                    }, new AnonymousClass3(null), flowCollector, continuation);
                    return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
                }
            }, new AnonymousClass3(this.this$0, null));
            this.label = 1;
            if (FlowKt.first(transformLatest, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        ConditionalRestarter conditionalRestarter = this.this$0;
        conditionalRestarter.systemExitRestarter.restartSystemUI(conditionalRestarter.pendingReason);
        return Unit.INSTANCE;
    }
}
