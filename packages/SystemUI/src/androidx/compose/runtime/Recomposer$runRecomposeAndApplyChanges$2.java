package androidx.compose.runtime;

import androidx.collection.MutableScatterSet;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class Recomposer$runRecomposeAndApplyChanges$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    Object L$4;
    Object L$5;
    Object L$6;
    Object L$7;
    Object L$8;
    int label;
    final /* synthetic */ Recomposer this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Recomposer$runRecomposeAndApplyChanges$2(Recomposer recomposer, Continuation continuation) {
        super(3, continuation);
        this.this$0 = recomposer;
    }

    public static final void access$invokeSuspend$clearRecompositionState(Recomposer recomposer, List list, List list2, List list3, MutableScatterSet mutableScatterSet, MutableScatterSet mutableScatterSet2, MutableScatterSet mutableScatterSet3, MutableScatterSet mutableScatterSet4) {
        char c;
        long j;
        long j2;
        synchronized (recomposer.stateLock) {
            try {
                list.clear();
                list2.clear();
                int size = list3.size();
                for (int i = 0; i < size; i++) {
                    CompositionImpl compositionImpl = (CompositionImpl) ((ControlledComposition) list3.get(i));
                    compositionImpl.abandonChanges();
                    recomposer.recordFailedCompositionLocked(compositionImpl);
                }
                list3.clear();
                Object[] objArr = mutableScatterSet.elements;
                long[] jArr = mutableScatterSet.metadata;
                int length = jArr.length - 2;
                if (length >= 0) {
                    int i2 = 0;
                    j = 255;
                    while (true) {
                        long j3 = jArr[i2];
                        c = 7;
                        j2 = -9187201950435737472L;
                        if ((((~j3) << 7) & j3 & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i3 = 8 - ((~(i2 - length)) >>> 31);
                            for (int i4 = 0; i4 < i3; i4++) {
                                if ((j3 & 255) < 128) {
                                    CompositionImpl compositionImpl2 = (CompositionImpl) ((ControlledComposition) objArr[(i2 << 3) + i4]);
                                    compositionImpl2.abandonChanges();
                                    recomposer.recordFailedCompositionLocked(compositionImpl2);
                                }
                                j3 >>= 8;
                            }
                            if (i3 != 8) {
                                break;
                            }
                        }
                        if (i2 == length) {
                            break;
                        } else {
                            i2++;
                        }
                    }
                } else {
                    c = 7;
                    j = 255;
                    j2 = -9187201950435737472L;
                }
                mutableScatterSet.clear();
                Object[] objArr2 = mutableScatterSet2.elements;
                long[] jArr2 = mutableScatterSet2.metadata;
                int length2 = jArr2.length - 2;
                if (length2 >= 0) {
                    int i5 = 0;
                    while (true) {
                        long j4 = jArr2[i5];
                        if ((((~j4) << c) & j4 & j2) != j2) {
                            int i6 = 8 - ((~(i5 - length2)) >>> 31);
                            for (int i7 = 0; i7 < i6; i7++) {
                                if ((j4 & j) < 128) {
                                    ((CompositionImpl) ((ControlledComposition) objArr2[(i5 << 3) + i7])).changesApplied();
                                }
                                j4 >>= 8;
                            }
                            if (i6 != 8) {
                                break;
                            }
                        }
                        if (i5 == length2) {
                            break;
                        } else {
                            i5++;
                        }
                    }
                }
                mutableScatterSet2.clear();
                mutableScatterSet3.clear();
                Object[] objArr3 = mutableScatterSet4.elements;
                long[] jArr3 = mutableScatterSet4.metadata;
                int length3 = jArr3.length - 2;
                if (length3 >= 0) {
                    int i8 = 0;
                    while (true) {
                        long j5 = jArr3[i8];
                        if ((((~j5) << c) & j5 & j2) != j2) {
                            int i9 = 8 - ((~(i8 - length3)) >>> 31);
                            for (int i10 = 0; i10 < i9; i10++) {
                                if ((j5 & j) < 128) {
                                    CompositionImpl compositionImpl3 = (CompositionImpl) ((ControlledComposition) objArr3[(i8 << 3) + i10]);
                                    compositionImpl3.abandonChanges();
                                    recomposer.recordFailedCompositionLocked(compositionImpl3);
                                }
                                j5 >>= 8;
                            }
                            if (i9 != 8) {
                                break;
                            }
                        }
                        if (i8 == length3) {
                            break;
                        } else {
                            i8++;
                        }
                    }
                }
                mutableScatterSet4.clear();
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static final void access$invokeSuspend$fillToInsert(List list, Recomposer recomposer) {
        list.clear();
        synchronized (recomposer.stateLock) {
            try {
                ArrayList arrayList = (ArrayList) recomposer.movableContentAwaitingInsert;
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    list.add((MovableContentStateReference) arrayList.get(i));
                }
                ((ArrayList) recomposer.movableContentAwaitingInsert).clear();
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        Recomposer$runRecomposeAndApplyChanges$2 recomposer$runRecomposeAndApplyChanges$2 = new Recomposer$runRecomposeAndApplyChanges$2(this.this$0, (Continuation) obj3);
        recomposer$runRecomposeAndApplyChanges$2.L$0 = (MonotonicFrameClock) obj2;
        return recomposer$runRecomposeAndApplyChanges$2.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:59:0x0154, code lost:
    
        if (r3.withFrameNanos(r17, r0) == r2) goto L42;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00c0 A[DONT_GENERATE] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0121  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0221  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:57:0x0154 -> B:6:0x0157). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:58:0x0221 -> B:34:0x00bb). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r29) {
        /*
            Method dump skipped, instructions count: 567
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.Recomposer$runRecomposeAndApplyChanges$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
