package kotlin.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "kotlin.collections.SlidingWindowKt$windowedIterator$1", m277f = "SlidingWindow.kt", m278l = {34, 40, 49, 55, 58}, m279m = "invokeSuspend")
/* loaded from: classes3.dex */
public final class SlidingWindowKt$windowedIterator$1 extends RestrictedSuspendLambda implements Function2 {
    final /* synthetic */ Iterator<Object> $iterator;
    final /* synthetic */ boolean $partialWindows;
    final /* synthetic */ boolean $reuseBuffer;
    final /* synthetic */ int $size;
    final /* synthetic */ int $step;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SlidingWindowKt$windowedIterator$1(int i, int i2, Iterator<Object> it, boolean z, boolean z2, Continuation<? super SlidingWindowKt$windowedIterator$1> continuation) {
        super(2, continuation);
        this.$size = i;
        this.$step = i2;
        this.$iterator = it;
        this.$reuseBuffer = z;
        this.$partialWindows = z2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SlidingWindowKt$windowedIterator$1 slidingWindowKt$windowedIterator$1 = new SlidingWindowKt$windowedIterator$1(this.$size, this.$step, this.$iterator, this.$reuseBuffer, this.$partialWindows, continuation);
        slidingWindowKt$windowedIterator$1.L$0 = obj;
        return slidingWindowKt$windowedIterator$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SlidingWindowKt$windowedIterator$1) create((SequenceScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x00d7 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:106:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x007f  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x0189 -> B:12:0x018c). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:55:0x0155 -> B:30:0x0158). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:79:0x00a1 -> B:67:0x00a4). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        int i;
        RingBuffer ringBuffer;
        SequenceScope sequenceScope;
        Iterator<Object> it;
        ArrayList arrayList;
        SequenceScope sequenceScope2;
        int i2;
        Iterator<Object> it2;
        RingBuffer ringBuffer2;
        SequenceScope sequenceScope3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = this.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj);
            SequenceScope sequenceScope4 = (SequenceScope) this.L$0;
            int i4 = this.$size;
            int i5 = i4 <= 1024 ? i4 : 1024;
            i = this.$step - i4;
            if (i < 0) {
                ringBuffer = new RingBuffer(i5);
                sequenceScope = sequenceScope4;
                it = this.$iterator;
                while (it.hasNext()) {
                }
                if (this.$partialWindows) {
                }
                return Unit.INSTANCE;
            }
            arrayList = new ArrayList(i5);
            sequenceScope2 = sequenceScope4;
            i2 = 0;
            it2 = this.$iterator;
            while (it2.hasNext()) {
            }
            if (!arrayList.isEmpty()) {
                this.L$0 = null;
                this.L$1 = null;
                this.L$2 = null;
                this.label = 2;
                if (sequenceScope2.yield(arrayList, this) == coroutineSingletons) {
                }
            }
            return Unit.INSTANCE;
        }
        if (i3 == 1) {
            int i6 = this.I$0;
            it2 = (Iterator) this.L$2;
            arrayList = (ArrayList) this.L$1;
            sequenceScope2 = (SequenceScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            i = i6;
            if (this.$reuseBuffer) {
                arrayList = new ArrayList(this.$size);
            } else {
                arrayList.clear();
            }
            i2 = i;
            while (it2.hasNext()) {
                Object next = it2.next();
                if (i2 > 0) {
                    i2--;
                } else {
                    arrayList.add(next);
                    if (arrayList.size() == this.$size) {
                        this.L$0 = sequenceScope2;
                        this.L$1 = arrayList;
                        this.L$2 = it2;
                        this.I$0 = i;
                        this.label = 1;
                        if (sequenceScope2.yield(arrayList, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        if (this.$reuseBuffer) {
                        }
                        i2 = i;
                        while (it2.hasNext()) {
                        }
                    }
                }
            }
            if ((!arrayList.isEmpty()) && (this.$partialWindows || arrayList.size() == this.$size)) {
                this.L$0 = null;
                this.L$1 = null;
                this.L$2 = null;
                this.label = 2;
                if (sequenceScope2.yield(arrayList, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
            return Unit.INSTANCE;
        }
        if (i3 != 2) {
            if (i3 == 3) {
                it = (Iterator) this.L$2;
                ringBuffer = (RingBuffer) this.L$1;
                sequenceScope = (SequenceScope) this.L$0;
                ResultKt.throwOnFailure(obj);
                ringBuffer.removeFirst(this.$step);
                while (it.hasNext()) {
                    Object next2 = it.next();
                    int size = ringBuffer.getSize();
                    int i7 = ringBuffer.capacity;
                    if (size == i7) {
                        throw new IllegalStateException("ring buffer is full");
                    }
                    Object[] objArr = ringBuffer.buffer;
                    int i8 = ringBuffer.startIndex;
                    int i9 = ringBuffer.size;
                    objArr[(i8 + i9) % i7] = next2;
                    ringBuffer.size = i9 + 1;
                    int size2 = ringBuffer.getSize();
                    int i10 = ringBuffer.capacity;
                    if (size2 == i10) {
                        int i11 = ringBuffer.size;
                        int i12 = this.$size;
                        if (i11 >= i12) {
                            Object arrayList2 = this.$reuseBuffer ? ringBuffer : new ArrayList(ringBuffer);
                            this.L$0 = sequenceScope;
                            this.L$1 = ringBuffer;
                            this.L$2 = it;
                            this.label = 3;
                            if (sequenceScope.yield(arrayList2, this) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            ringBuffer.removeFirst(this.$step);
                            while (it.hasNext()) {
                            }
                        } else {
                            int i13 = i10 + (i10 >> 1) + 1;
                            if (i13 <= i12) {
                                i12 = i13;
                            }
                            ringBuffer = new RingBuffer(ringBuffer.startIndex == 0 ? Arrays.copyOf(ringBuffer.buffer, i12) : ringBuffer.toArray(new Object[i12]), ringBuffer.size);
                        }
                    }
                }
                if (this.$partialWindows) {
                    ringBuffer2 = ringBuffer;
                    sequenceScope3 = sequenceScope;
                    if (ringBuffer2.size <= this.$step) {
                    }
                }
                return Unit.INSTANCE;
            }
            if (i3 == 4) {
                ringBuffer2 = (RingBuffer) this.L$1;
                sequenceScope3 = (SequenceScope) this.L$0;
                ResultKt.throwOnFailure(obj);
                ringBuffer2.removeFirst(this.$step);
                if (ringBuffer2.size <= this.$step) {
                    Object arrayList3 = this.$reuseBuffer ? ringBuffer2 : new ArrayList(ringBuffer2);
                    this.L$0 = sequenceScope3;
                    this.L$1 = ringBuffer2;
                    this.L$2 = null;
                    this.label = 4;
                    if (sequenceScope3.yield(arrayList3, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    ringBuffer2.removeFirst(this.$step);
                    if (ringBuffer2.size <= this.$step) {
                        if (!ringBuffer2.isEmpty()) {
                            this.L$0 = null;
                            this.L$1 = null;
                            this.L$2 = null;
                            this.label = 5;
                            if (sequenceScope3.yield(ringBuffer2, this) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }
            } else if (i3 != 5) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
        ResultKt.throwOnFailure(obj);
        return Unit.INSTANCE;
    }
}
