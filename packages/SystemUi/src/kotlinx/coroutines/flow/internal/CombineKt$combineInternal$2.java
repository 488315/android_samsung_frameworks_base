package kotlinx.coroutines.flow.internal;

import com.samsung.android.nexus.video.VideoPlayer;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.collections.IndexedValue;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.YieldKt;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2", m277f = "Combine.kt", m278l = {57, 79, 82}, m279m = "invokeSuspend")
/* loaded from: classes3.dex */
final class CombineKt$combineInternal$2 extends SuspendLambda implements Function2 {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2$1", m277f = "Combine.kt", m278l = {34}, m279m = "invokeSuspend")
    /* renamed from: kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2$1 */
    final class C48561 extends SuspendLambda implements Function2 {
        final /* synthetic */ Flow[] $flows;

        /* renamed from: $i */
        final /* synthetic */ int f669$i;
        final /* synthetic */ AtomicInteger $nonClosed;
        final /* synthetic */ Channel $resultChannel;
        int label;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2$1$1, reason: invalid class name */
        public final class AnonymousClass1 implements FlowCollector {

            /* renamed from: $i */
            public final /* synthetic */ int f670$i;
            public final /* synthetic */ Channel $resultChannel;

            public AnonymousClass1(Channel channel, int i) {
                this.$resultChannel = channel;
                this.f670$i = i;
            }

            /* JADX WARN: Removed duplicated region for block: B:19:0x0053 A[RETURN] */
            /* JADX WARN: Removed duplicated region for block: B:20:0x0036  */
            /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object emit(Object obj, Continuation continuation) {
                CombineKt$combineInternal$2$1$1$emit$1 combineKt$combineInternal$2$1$1$emit$1;
                CoroutineSingletons coroutineSingletons;
                int i;
                if (continuation instanceof CombineKt$combineInternal$2$1$1$emit$1) {
                    combineKt$combineInternal$2$1$1$emit$1 = (CombineKt$combineInternal$2$1$1$emit$1) continuation;
                    int i2 = combineKt$combineInternal$2$1$1$emit$1.label;
                    if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                        combineKt$combineInternal$2$1$1$emit$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                        Object obj2 = combineKt$combineInternal$2$1$1$emit$1.result;
                        coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        i = combineKt$combineInternal$2$1$1$emit$1.label;
                        if (i != 0) {
                            ResultKt.throwOnFailure(obj2);
                            IndexedValue indexedValue = new IndexedValue(this.f670$i, obj);
                            combineKt$combineInternal$2$1$1$emit$1.label = 1;
                            if (this.$resultChannel.send(indexedValue, combineKt$combineInternal$2$1$1$emit$1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        } else {
                            if (i != 1) {
                                if (i != 2) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                                return Unit.INSTANCE;
                            }
                            ResultKt.throwOnFailure(obj2);
                        }
                        combineKt$combineInternal$2$1$1$emit$1.label = 2;
                        if (YieldKt.yield(combineKt$combineInternal$2$1$1$emit$1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        return Unit.INSTANCE;
                    }
                }
                combineKt$combineInternal$2$1$1$emit$1 = new CombineKt$combineInternal$2$1$1$emit$1(this, continuation);
                Object obj22 = combineKt$combineInternal$2$1$1$emit$1.result;
                coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = combineKt$combineInternal$2$1$1$emit$1.label;
                if (i != 0) {
                }
                combineKt$combineInternal$2$1$1$emit$1.label = 2;
                if (YieldKt.yield(combineKt$combineInternal$2$1$1$emit$1) == coroutineSingletons) {
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C48561(Flow[] flowArr, int i, AtomicInteger atomicInteger, Channel channel, Continuation<? super C48561> continuation) {
            super(2, continuation);
            this.$flows = flowArr;
            this.f669$i = i;
            this.$nonClosed = atomicInteger;
            this.$resultChannel = channel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C48561(this.$flows, this.f669$i, this.$nonClosed, this.$resultChannel, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C48561) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                    int i2 = this.f669$i;
                    Flow flow = flowArr[i2];
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$resultChannel, i2);
                    this.label = 1;
                    if (flow.collect(anonymousClass1, this) == coroutineSingletons) {
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
    public CombineKt$combineInternal$2(Flow[] flowArr, Function0 function0, Function3 function3, FlowCollector flowCollector, Continuation<? super CombineKt$combineInternal$2> continuation) {
        super(2, continuation);
        this.$flows = flowArr;
        this.$arrayFactory = function0;
        this.$transform = function3;
        this.$this_combineInternal = flowCollector;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CombineKt$combineInternal$2 combineKt$combineInternal$2 = new CombineKt$combineInternal$2(this.$flows, this.$arrayFactory, this.$transform, this.$this_combineInternal, continuation);
        combineKt$combineInternal$2.L$0 = obj;
        return combineKt$combineInternal$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CombineKt$combineInternal$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x012e, code lost:
    
        r10 = r2;
        r2 = r8;
        r8 = r5;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x00a7 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00ba A[LOOP:0: B:22:0x00ba->B:45:?, LOOP_START, PHI: r7 r11
      0x00ba: PHI (r7v3 int) = (r7v2 int), (r7v4 int) binds: [B:19:0x00b5, B:45:?] A[DONT_GENERATE, DONT_INLINE]
      0x00ba: PHI (r11v4 kotlin.collections.IndexedValue) = (r11v3 kotlin.collections.IndexedValue), (r11v14 kotlin.collections.IndexedValue) binds: [B:19:0x00b5, B:45:?] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        int length;
        Object[] objArr;
        Channel Channel$default;
        byte[] bArr;
        int i;
        Object[] objArr2;
        Object obj2;
        byte[] bArr2;
        int i2;
        IndexedValue indexedValue;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = this.label;
        Object obj3 = null;
        int i4 = 2;
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            length = this.$flows.length;
            if (length == 0) {
                return Unit.INSTANCE;
            }
            objArr = new Object[length];
            Arrays.fill(objArr, 0, length, NullSurrogateKt.UNINITIALIZED);
            Channel$default = ChannelKt.Channel$default(length, null, 6);
            AtomicInteger atomicInteger = new AtomicInteger(length);
            int i5 = 0;
            while (i5 < length) {
                BuildersKt.launch$default(coroutineScope, null, null, new C48561(this.$flows, i5, atomicInteger, Channel$default, null), 3);
                i5++;
                atomicInteger = atomicInteger;
            }
            bArr = new byte[length];
            i = 0;
            byte b = (byte) (i + 1);
            this.L$0 = objArr;
            this.L$1 = Channel$default;
            this.L$2 = bArr;
            this.I$0 = length;
            this.I$1 = b;
            this.label = 1;
            obj2 = Channel$default.mo2870receiveCatchingJP2dKIU(this);
            if (obj2 != coroutineSingletons) {
            }
        } else if (i3 == 1) {
            i2 = this.I$1;
            length = this.I$0;
            bArr2 = (byte[]) this.L$2;
            Channel$default = (Channel) this.L$1;
            Object[] objArr3 = (Object[]) this.L$0;
            ResultKt.throwOnFailure(obj);
            obj2 = ((ChannelResult) obj).holder;
            objArr2 = objArr3;
            ChannelResult.Companion companion = ChannelResult.Companion;
            if (obj2 instanceof ChannelResult.Failed) {
            }
            indexedValue = (IndexedValue) obj2;
            if (indexedValue != null) {
            }
        } else {
            if (i3 != 2 && i3 != 3) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            int i6 = this.I$1;
            length = this.I$0;
            byte[] bArr3 = (byte[]) this.L$2;
            Channel$default = (Channel) this.L$1;
            Object[] objArr4 = (Object[]) this.L$0;
            ResultKt.throwOnFailure(obj);
            i = i6;
            bArr = bArr3;
            objArr = objArr4;
            obj3 = null;
            i4 = 2;
            byte b2 = (byte) (i + 1);
            this.L$0 = objArr;
            this.L$1 = Channel$default;
            this.L$2 = bArr;
            this.I$0 = length;
            this.I$1 = b2;
            this.label = 1;
            obj2 = Channel$default.mo2870receiveCatchingJP2dKIU(this);
            if (obj2 != coroutineSingletons) {
                return coroutineSingletons;
            }
            objArr2 = objArr;
            bArr2 = bArr;
            i2 = b2;
            ChannelResult.Companion companion2 = ChannelResult.Companion;
            if (obj2 instanceof ChannelResult.Failed) {
                obj2 = obj3;
            }
            indexedValue = (IndexedValue) obj2;
            if (indexedValue != null) {
                return Unit.INSTANCE;
            }
            do {
                int i7 = indexedValue.index;
                Object obj4 = objArr2[i7];
                objArr2[i7] = indexedValue.value;
                if (obj4 == NullSurrogateKt.UNINITIALIZED) {
                    length--;
                }
                if (bArr2[i7] == i2) {
                    break;
                }
                bArr2[i7] = (byte) i2;
                Object mo2871tryReceivePtdJZtk = Channel$default.mo2871tryReceivePtdJZtk();
                if (mo2871tryReceivePtdJZtk instanceof ChannelResult.Failed) {
                    mo2871tryReceivePtdJZtk = obj3;
                }
                indexedValue = (IndexedValue) mo2871tryReceivePtdJZtk;
            } while (indexedValue != null);
            if (length == 0) {
                Object[] objArr5 = (Object[]) this.$arrayFactory.invoke();
                if (objArr5 == null) {
                    Function3 function3 = this.$transform;
                    FlowCollector flowCollector = this.$this_combineInternal;
                    this.L$0 = objArr2;
                    this.L$1 = Channel$default;
                    this.L$2 = bArr2;
                    this.I$0 = length;
                    this.I$1 = i2;
                    this.label = i4;
                    if (function3.invoke(flowCollector, objArr2, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    Object[] objArr6 = objArr2;
                    ArraysKt___ArraysJvmKt.copyInto$default(objArr2, objArr5, 0, 0, 0, 14);
                    Function3 function32 = this.$transform;
                    FlowCollector flowCollector2 = this.$this_combineInternal;
                    this.L$0 = objArr6;
                    this.L$1 = Channel$default;
                    this.L$2 = bArr2;
                    this.I$0 = length;
                    this.I$1 = i2;
                    this.label = 3;
                    if (function32.invoke(flowCollector2, objArr5, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    i = i2;
                    bArr = bArr2;
                    objArr = objArr2;
                    obj3 = null;
                    i4 = 2;
                    byte b22 = (byte) (i + 1);
                    this.L$0 = objArr;
                    this.L$1 = Channel$default;
                    this.L$2 = bArr;
                    this.I$0 = length;
                    this.I$1 = b22;
                    this.label = 1;
                    obj2 = Channel$default.mo2870receiveCatchingJP2dKIU(this);
                    if (obj2 != coroutineSingletons) {
                    }
                }
            }
            i = i2;
            bArr = bArr2;
            objArr = objArr2;
            obj3 = null;
            i4 = 2;
            byte b222 = (byte) (i + 1);
            this.L$0 = objArr;
            this.L$1 = Channel$default;
            this.L$2 = bArr;
            this.I$0 = length;
            this.I$1 = b222;
            this.label = 1;
            obj2 = Channel$default.mo2870receiveCatchingJP2dKIU(this);
            if (obj2 != coroutineSingletons) {
            }
        }
    }
}
