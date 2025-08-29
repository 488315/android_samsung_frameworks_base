package kotlinx.coroutines.flow.internal;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.collections.IndexedValue;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.intrinsics.UndispatchedKt;

/* loaded from: classes4.dex */
public abstract class CombineKt {

    /* renamed from: kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function0 $arrayFactory;
        final /* synthetic */ Flow[] $flows;
        final /* synthetic */ FlowCollector $this_combineInternal;
        final /* synthetic */ Function3 $transform;
        int I$0;
        int I$1;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        int label;

        /* renamed from: kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ Flow[] $flows;
            final /* synthetic */ int $i;
            final /* synthetic */ AtomicInteger $nonClosed;
            final /* synthetic */ Channel $resultChannel;
            int label;

            /* renamed from: kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2$1$1, reason: invalid class name and collision with other inner class name */
            public final class C06701 implements FlowCollector {
                public final /* synthetic */ int $i;
                public final /* synthetic */ Channel $resultChannel;

                public C06701(Channel channel, int i) {
                    this.$resultChannel = channel;
                    this.$i = i;
                }

                /* JADX WARN: Code restructure failed: missing block: B:20:0x0051, code lost:
                
                    if (kotlinx.coroutines.YieldKt.yield(r0) == r1) goto L21;
                 */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    CombineKt$combineInternal$2$1$1$emit$1 combineKt$combineInternal$2$1$1$emit$1;
                    if (continuation instanceof CombineKt$combineInternal$2$1$1$emit$1) {
                        combineKt$combineInternal$2$1$1$emit$1 = (CombineKt$combineInternal$2$1$1$emit$1) continuation;
                        int i = combineKt$combineInternal$2$1$1$emit$1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            combineKt$combineInternal$2$1$1$emit$1.label = i - Integer.MIN_VALUE;
                        } else {
                            combineKt$combineInternal$2$1$1$emit$1 = new CombineKt$combineInternal$2$1$1$emit$1(this, continuation);
                        }
                    }
                    Object obj2 = combineKt$combineInternal$2$1$1$emit$1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = combineKt$combineInternal$2$1$1$emit$1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        IndexedValue indexedValue = new IndexedValue(this.$i, obj);
                        combineKt$combineInternal$2$1$1$emit$1.label = 1;
                        if (this.$resultChannel.send(indexedValue, combineKt$combineInternal$2$1$1$emit$1) != coroutineSingletons) {
                        }
                        return coroutineSingletons;
                    }
                    if (i2 != 1) {
                        if (i2 != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                        return Unit.INSTANCE;
                    }
                    ResultKt.throwOnFailure(obj2);
                    combineKt$combineInternal$2$1$1$emit$1.label = 2;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(Flow[] flowArr, int i, AtomicInteger atomicInteger, Channel channel, Continuation continuation) {
                super(2, continuation);
                this.$flows = flowArr;
                this.$i = i;
                this.$nonClosed = atomicInteger;
                this.$resultChannel = channel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$flows, this.$i, this.$nonClosed, this.$resultChannel, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                AtomicInteger atomicInteger;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                try {
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        Flow[] flowArr = this.$flows;
                        int i2 = this.$i;
                        Flow flow = flowArr[i2];
                        C06701 c06701 = new C06701(this.$resultChannel, i2);
                        this.label = 1;
                        if (flow.collect(c06701, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                    }
                    if (atomicInteger.decrementAndGet() == 0) {
                        this.$resultChannel.close(null);
                    }
                    return Unit.INSTANCE;
                } finally {
                    if (this.$nonClosed.decrementAndGet() == 0) {
                        this.$resultChannel.close(null);
                    }
                }
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Flow[] flowArr, Function0 function0, Function3 function3, FlowCollector flowCollector, Continuation continuation) {
            super(2, continuation);
            this.$flows = flowArr;
            this.$arrayFactory = function0;
            this.$transform = function3;
            this.$this_combineInternal = flowCollector;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$flows, this.$arrayFactory, this.$transform, this.$this_combineInternal, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:43:0x0087, code lost:
        
            if (r7 != 0) goto L20;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Path cross not found for [B:20:0x0087, B:35:0x00ca], limit reached: 44 */
        /* JADX WARN: Path cross not found for [B:34:0x00c8, B:32:0x00b9], limit reached: 44 */
        /* JADX WARN: Path cross not found for [B:37:0x00d4, B:40:0x00eb], limit reached: 44 */
        /* JADX WARN: Removed duplicated region for block: B:25:0x00a4  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x00a7 A[LOOP:0: B:27:0x00a7->B:45:?, LOOP_START, PHI: r7 r10
          0x00a7: PHI (r7v3 int) = (r7v2 int), (r7v4 int) binds: [B:24:0x00a2, B:45:?] A[DONT_GENERATE, DONT_INLINE]
          0x00a7: PHI (r10v3 kotlin.collections.IndexedValue) = (r10v2 kotlin.collections.IndexedValue), (r10v10 kotlin.collections.IndexedValue) binds: [B:24:0x00a2, B:45:?] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Type inference failed for: r2v6, types: [int] */
        /* JADX WARN: Type inference failed for: r2v8, types: [int] */
        /* JADX WARN: Type inference failed for: r9v11, types: [kotlinx.coroutines.channels.Channel] */
        /* JADX WARN: Type inference failed for: r9v8, types: [kotlinx.coroutines.channels.Channel] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:38:0x00e8 -> B:20:0x0087). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:41:0x0104 -> B:20:0x0087). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            int length;
            Object[] objArr;
            BufferedChannel bufferedChannelChannel$default;
            byte[] bArr;
            byte b;
            Object objMo3472receiveCatchingJP2dKIU;
            IndexedValue indexedValue;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                length = this.$flows.length;
                if (length == 0) {
                    return Unit.INSTANCE;
                }
                objArr = new Object[length];
                Arrays.fill(objArr, 0, length, NullSurrogateKt.UNINITIALIZED);
                bufferedChannelChannel$default = ChannelKt.Channel$default(length, null, null, 6);
                AtomicInteger atomicInteger = new AtomicInteger(length);
                for (int i2 = 0; i2 < length; i2++) {
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.$flows, i2, atomicInteger, bufferedChannelChannel$default, null), 3);
                }
                bArr = new byte[length];
                b = 0;
            } else if (i == 1) {
                ?? r2 = this.I$1;
                length = this.I$0;
                byte[] bArr2 = (byte[]) this.L$2;
                ?? r9 = (Channel) this.L$1;
                Object[] objArr2 = (Object[]) this.L$0;
                ResultKt.throwOnFailure(obj);
                objMo3472receiveCatchingJP2dKIU = ((ChannelResult) obj).holder;
                bufferedChannelChannel$default = r9;
                b = r2;
                bArr = bArr2;
                objArr = objArr2;
                indexedValue = (IndexedValue) ChannelResult.m3478getOrNullimpl(objMo3472receiveCatchingJP2dKIU);
                if (indexedValue != null) {
                    return Unit.INSTANCE;
                }
                while (true) {
                    int i3 = indexedValue.index;
                    Object obj2 = objArr[i3];
                    objArr[i3] = indexedValue.value;
                    if (obj2 == NullSurrogateKt.UNINITIALIZED) {
                        length--;
                    }
                    if (bArr[i3] != b) {
                        bArr[i3] = b;
                        indexedValue = (IndexedValue) ChannelResult.m3478getOrNullimpl(bufferedChannelChannel$default.mo3474tryReceivePtdJZtk());
                        if (indexedValue != null) {
                        }
                    }
                    if (length == 0) {
                        Object[] objArr3 = (Object[]) this.$arrayFactory.invoke();
                        if (objArr3 == null) {
                            Function3 function3 = this.$transform;
                            FlowCollector flowCollector = this.$this_combineInternal;
                            this.L$0 = objArr;
                            this.L$1 = bufferedChannelChannel$default;
                            this.L$2 = bArr;
                            this.I$0 = length;
                            this.I$1 = b;
                            this.label = 2;
                            if (function3.invoke(flowCollector, objArr, this) != coroutineSingletons) {
                                break;
                            }
                        } else {
                            ArraysKt___ArraysJvmKt.copyInto$default(objArr, objArr3, 0, 0, 14);
                            Function3 function32 = this.$transform;
                            FlowCollector flowCollector2 = this.$this_combineInternal;
                            this.L$0 = objArr;
                            this.L$1 = bufferedChannelChannel$default;
                            this.L$2 = bArr;
                            this.I$0 = length;
                            this.I$1 = b;
                            this.label = 3;
                            if (function32.invoke(flowCollector2, objArr3, this) != coroutineSingletons) {
                                break;
                            }
                        }
                    }
                }
            } else {
                if (i != 2 && i != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ?? r22 = this.I$1;
                length = this.I$0;
                byte[] bArr3 = (byte[]) this.L$2;
                ?? r92 = (Channel) this.L$1;
                Object[] objArr4 = (Object[]) this.L$0;
                ResultKt.throwOnFailure(obj);
                bufferedChannelChannel$default = r92;
                b = r22;
                bArr = bArr3;
                objArr = objArr4;
            }
            b = (byte) (b + 1);
            this.L$0 = objArr;
            this.L$1 = bufferedChannelChannel$default;
            this.L$2 = bArr;
            this.I$0 = length;
            this.I$1 = b;
            this.label = 1;
            objMo3472receiveCatchingJP2dKIU = bufferedChannelChannel$default.mo3472receiveCatchingJP2dKIU(this);
            if (objMo3472receiveCatchingJP2dKIU != coroutineSingletons) {
                indexedValue = (IndexedValue) ChannelResult.m3478getOrNullimpl(objMo3472receiveCatchingJP2dKIU);
                if (indexedValue != null) {
                }
            }
            return coroutineSingletons;
        }
    }

    public static final Object combineInternal(Flow[] flowArr, Function0 function0, Function3 function3, FlowCollector flowCollector, Continuation continuation) {
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(flowArr, function0, function3, flowCollector, null);
        FlowCoroutine flowCoroutine = new FlowCoroutine(continuation.getContext(), continuation);
        Object objStartUndispatchedOrReturn = UndispatchedKt.startUndispatchedOrReturn(flowCoroutine, flowCoroutine, anonymousClass2);
        return objStartUndispatchedOrReturn == CoroutineSingletons.COROUTINE_SUSPENDED ? objStartUndispatchedOrReturn : Unit.INSTANCE;
    }
}
