package androidx.compose.runtime;

import androidx.collection.MutableScatterSet;
import androidx.collection.ScatterSet;
import androidx.compose.runtime.collection.ScatterSetWrapper;
import androidx.compose.runtime.snapshots.ObserverHandle;
import androidx.compose.runtime.snapshots.ReaderKind;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.runtime.snapshots.SnapshotKt;
import androidx.compose.runtime.snapshots.StateObjectImpl;
import java.util.Collection;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes.dex */
final class SnapshotStateKt__SnapshotFlowKt$snapshotFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function0 $block;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    Object L$4;
    Object L$5;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SnapshotStateKt__SnapshotFlowKt$snapshotFlow$1(Function0 function0, Continuation continuation) {
        super(2, continuation);
        this.$block = function0;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SnapshotStateKt__SnapshotFlowKt$snapshotFlow$1 snapshotStateKt__SnapshotFlowKt$snapshotFlow$1 = new SnapshotStateKt__SnapshotFlowKt$snapshotFlow$1(this.$block, continuation);
        snapshotStateKt__SnapshotFlowKt$snapshotFlow$1.L$0 = obj;
        return snapshotStateKt__SnapshotFlowKt$snapshotFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SnapshotStateKt__SnapshotFlowKt$snapshotFlow$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Path cross not found for [B:42:0x0117, B:53:0x0149], limit reached: 107 */
    /* JADX WARN: Path cross not found for [B:63:0x016d, B:74:0x01b2], limit reached: 107 */
    /* JADX WARN: Path cross not found for [B:74:0x01b2, B:63:0x016d], limit reached: 107 */
    /* JADX WARN: Removed duplicated region for block: B:103:0x016b A[EDGE_INSN: B:103:0x016b->B:62:0x016b BREAK  A[LOOP:0: B:36:0x00f1->B:83:0x01c6], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00f3 A[Catch: all -> 0x005a, TryCatch #3 {all -> 0x005a, blocks: (B:35:0x00ef, B:37:0x00f3, B:40:0x0100, B:42:0x0117, B:44:0x0125, B:46:0x012f, B:60:0x015e, B:63:0x016d, B:67:0x018a, B:69:0x0193, B:79:0x01bb, B:80:0x01be, B:50:0x0140, B:55:0x014d, B:15:0x0052, B:64:0x017f, B:66:0x0187, B:76:0x01b6, B:77:0x01b9, B:65:0x0183), top: B:97:0x0052, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0158  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x016d A[Catch: all -> 0x005a, TRY_LEAVE, TryCatch #3 {all -> 0x005a, blocks: (B:35:0x00ef, B:37:0x00f3, B:40:0x0100, B:42:0x0117, B:44:0x0125, B:46:0x012f, B:60:0x015e, B:63:0x016d, B:67:0x018a, B:69:0x0193, B:79:0x01bb, B:80:0x01be, B:50:0x0140, B:55:0x014d, B:15:0x0052, B:64:0x017f, B:66:0x0187, B:76:0x01b6, B:77:0x01b9, B:65:0x0183), top: B:97:0x0052, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x01c6 A[LOOP:0: B:36:0x00f1->B:83:0x01c6, LOOP_END] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        ObserverHandle observerHandleRegisterApplyObserver;
        FlowCollector flowCollector;
        final MutableScatterSet mutableScatterSet;
        Function1 function1;
        final Channel channelChannel$default;
        Snapshot snapshotTakeNestedSnapshot;
        Snapshot snapshotMakeCurrent;
        Object obj2;
        ObserverHandle observerHandle;
        FlowCollector flowCollector2;
        Object objReceive;
        MutableScatterSet mutableScatterSet2;
        Function1 function12;
        Channel channel;
        Object obj3;
        int i;
        Set set;
        int i2;
        int i3;
        int i4;
        int i5 = 3;
        int i6 = 1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i7 = this.label;
        int i8 = 2;
        int i9 = 0;
        try {
            if (i7 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                mutableScatterSet = new MutableScatterSet(0, 1, null);
                function1 = new Function1() { // from class: androidx.compose.runtime.SnapshotStateKt__SnapshotFlowKt$snapshotFlow$1$readObserver$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj4) {
                        if (obj4 instanceof StateObjectImpl) {
                            int i10 = ReaderKind.$r8$clinit;
                            ((StateObjectImpl) obj4).m352recordReadInh_f27i8$runtime_release(4);
                        }
                        mutableScatterSet.add(obj4);
                        return Unit.INSTANCE;
                    }
                };
                channelChannel$default = ChannelKt.Channel$default(Integer.MAX_VALUE, null, null, 6);
                Snapshot.Companion companion = Snapshot.Companion;
                Function2 function2 = new Function2() { // from class: androidx.compose.runtime.SnapshotStateKt__SnapshotFlowKt$snapshotFlow$1$unregisterApplyObserver$1
                    {
                        super(2);
                    }

                    /* JADX WARN: Removed duplicated region for block: B:20:0x005e  */
                    @Override // kotlin.jvm.functions.Function2
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj4, Object obj5) {
                        Set set2 = (Set) obj4;
                        if (set2 instanceof ScatterSetWrapper) {
                            ScatterSet scatterSet = ((ScatterSetWrapper) set2).set;
                            Object[] objArr = scatterSet.elements;
                            long[] jArr = scatterSet.metadata;
                            int length = jArr.length - 2;
                            if (length >= 0) {
                                int i10 = 0;
                                loop0: while (true) {
                                    long j = jArr[i10];
                                    if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                                        int i11 = 8 - ((~(i10 - length)) >>> 31);
                                        for (int i12 = 0; i12 < i11; i12++) {
                                            if ((255 & j) < 128) {
                                                Object obj6 = objArr[(i10 << 3) + i12];
                                                if (!(obj6 instanceof StateObjectImpl)) {
                                                    break loop0;
                                                }
                                                int i13 = ReaderKind.$r8$clinit;
                                                if (((StateObjectImpl) obj6).m351isReadInh_f27i8$runtime_release(4)) {
                                                    break loop0;
                                                }
                                            }
                                            j >>= 8;
                                        }
                                        if (i11 != 8) {
                                            break;
                                        }
                                        if (i10 == length) {
                                            break;
                                        }
                                        i10++;
                                    }
                                }
                            }
                        } else {
                            Set set3 = set2;
                            if (!(set3 instanceof Collection) || !set3.isEmpty()) {
                                for (Object obj7 : set3) {
                                    if (obj7 instanceof StateObjectImpl) {
                                        int i14 = ReaderKind.$r8$clinit;
                                        if (((StateObjectImpl) obj7).m351isReadInh_f27i8$runtime_release(4)) {
                                        }
                                    }
                                    channelChannel$default.mo3475trySendJP2dKIU(set2);
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                };
                companion.getClass();
                observerHandleRegisterApplyObserver = Snapshot.Companion.registerApplyObserver(function2);
                snapshotTakeNestedSnapshot = SnapshotKt.currentSnapshot().takeNestedSnapshot(function1);
                Function0 function0 = this.$block;
                try {
                    snapshotMakeCurrent = snapshotTakeNestedSnapshot.makeCurrent();
                    try {
                        Object objInvoke = function0.invoke();
                        snapshotTakeNestedSnapshot.dispose();
                        this.L$0 = flowCollector;
                        this.L$1 = mutableScatterSet;
                        this.L$2 = function1;
                        this.L$3 = channelChannel$default;
                        this.L$4 = observerHandleRegisterApplyObserver;
                        this.L$5 = objInvoke;
                        this.label = 1;
                        if (flowCollector.emit(objInvoke, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        obj2 = objInvoke;
                    } finally {
                    }
                } finally {
                }
            } else if (i7 == 1) {
                obj2 = this.L$5;
                observerHandleRegisterApplyObserver = (ObserverHandle) this.L$4;
                channelChannel$default = (Channel) this.L$3;
                function1 = (Function1) this.L$2;
                mutableScatterSet = (MutableScatterSet) this.L$1;
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            } else if (i7 == 2) {
                i = this.I$0;
                obj3 = this.L$5;
                observerHandle = (ObserverHandle) this.L$4;
                channel = (Channel) this.L$3;
                function12 = (Function1) this.L$2;
                mutableScatterSet2 = (MutableScatterSet) this.L$1;
                FlowCollector flowCollector3 = (FlowCollector) this.L$0;
                try {
                    ResultKt.throwOnFailure(obj);
                    flowCollector2 = flowCollector3;
                    objReceive = obj;
                    set = (Set) objReceive;
                    while (true) {
                        if (i != 0) {
                            StaticProvidableCompositionLocal staticProvidableCompositionLocal = SnapshotStateKt__SnapshotFlowKt.LocalCollectAsStateCoroutineContext;
                            Object[] objArr = mutableScatterSet2.elements;
                            long[] jArr = mutableScatterSet2.metadata;
                            int length = jArr.length - i8;
                            if (length >= 0) {
                                i3 = i6;
                                int i10 = i9;
                                while (true) {
                                    long j = jArr[i10];
                                    i2 = i5;
                                    int i11 = i10;
                                    if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                                        int i12 = 8 - ((~(i11 - length)) >>> 31);
                                        int i13 = 0;
                                        while (i13 < i12) {
                                            if ((j & 255) < 128) {
                                                i4 = i13;
                                                if (set.contains(objArr[(i11 << 3) + i13])) {
                                                    break;
                                                }
                                            } else {
                                                i4 = i13;
                                            }
                                            j >>= 8;
                                            i13 = i4 + 1;
                                        }
                                        if (i12 != 8) {
                                            break;
                                        }
                                    }
                                    if (i11 == length) {
                                        break;
                                    }
                                    i10 = i11 + 1;
                                    i5 = i2;
                                }
                            } else {
                                i2 = i5;
                                i3 = i6;
                            }
                            i = 0;
                            set = (Set) ChannelResult.m3478getOrNullimpl(channel.mo3474tryReceivePtdJZtk());
                            if (set == null) {
                                break;
                            }
                            i6 = i3;
                            i5 = i2;
                            i8 = 2;
                            i9 = 0;
                        } else {
                            i2 = i5;
                            i3 = i6;
                        }
                        i = i3;
                        set = (Set) ChannelResult.m3478getOrNullimpl(channel.mo3474tryReceivePtdJZtk());
                        if (set == null) {
                        }
                    }
                    if (i != 0) {
                        mutableScatterSet2.clear();
                        Snapshot.Companion.getClass();
                        snapshotTakeNestedSnapshot = SnapshotKt.currentSnapshot().takeNestedSnapshot(function12);
                        Function0 function02 = this.$block;
                        try {
                            try {
                                Object objInvoke2 = function02.invoke();
                                snapshotTakeNestedSnapshot.dispose();
                                if (!Intrinsics.areEqual(objInvoke2, obj3)) {
                                    this.L$0 = flowCollector2;
                                    this.L$1 = mutableScatterSet2;
                                    this.L$2 = function12;
                                    this.L$3 = channel;
                                    this.L$4 = observerHandle;
                                    this.L$5 = objInvoke2;
                                    i5 = i2;
                                    this.label = i5;
                                    if (flowCollector2.emit(objInvoke2, this) != coroutineSingletons) {
                                        obj2 = objInvoke2;
                                        observerHandleRegisterApplyObserver = observerHandle;
                                        channelChannel$default = channel;
                                        function1 = function12;
                                        mutableScatterSet = mutableScatterSet2;
                                        flowCollector = flowCollector2;
                                    }
                                    return coroutineSingletons;
                                }
                                i6 = i3;
                                i8 = 2;
                                i9 = 0;
                            } finally {
                            }
                            snapshotMakeCurrent = snapshotTakeNestedSnapshot.makeCurrent();
                        } finally {
                        }
                    }
                    i5 = i2;
                    obj2 = obj3;
                    observerHandleRegisterApplyObserver = observerHandle;
                    channelChannel$default = channel;
                    function1 = function12;
                    mutableScatterSet = mutableScatterSet2;
                    flowCollector = flowCollector2;
                    i6 = i3;
                    i8 = 2;
                    i9 = 0;
                } catch (Throwable th) {
                    th = th;
                    observerHandleRegisterApplyObserver = observerHandle;
                    observerHandleRegisterApplyObserver.dispose();
                    throw th;
                }
            } else {
                if (i7 != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                obj2 = this.L$5;
                observerHandleRegisterApplyObserver = (ObserverHandle) this.L$4;
                channelChannel$default = (Channel) this.L$3;
                function1 = (Function1) this.L$2;
                mutableScatterSet = (MutableScatterSet) this.L$1;
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
                i3 = 1;
                i6 = i3;
                i8 = 2;
                i9 = 0;
            }
            this.L$0 = flowCollector;
            this.L$1 = mutableScatterSet;
            this.L$2 = function1;
            this.L$3 = channelChannel$default;
            this.L$4 = observerHandleRegisterApplyObserver;
            this.L$5 = obj2;
            this.I$0 = i9;
            this.label = i8;
            objReceive = channelChannel$default.receive(this);
            if (objReceive != coroutineSingletons) {
                flowCollector2 = flowCollector;
                mutableScatterSet2 = mutableScatterSet;
                function12 = function1;
                channel = channelChannel$default;
                observerHandle = observerHandleRegisterApplyObserver;
                obj3 = obj2;
                i = i9;
                set = (Set) objReceive;
                while (true) {
                    if (i != 0) {
                    }
                    i = i3;
                    set = (Set) ChannelResult.m3478getOrNullimpl(channel.mo3474tryReceivePtdJZtk());
                    if (set == null) {
                    }
                    i6 = i3;
                    i5 = i2;
                    i8 = 2;
                    i9 = 0;
                }
                if (i != 0) {
                }
                i5 = i2;
                obj2 = obj3;
                observerHandleRegisterApplyObserver = observerHandle;
                channelChannel$default = channel;
                function1 = function12;
                mutableScatterSet = mutableScatterSet2;
                flowCollector = flowCollector2;
                i6 = i3;
                i8 = 2;
                i9 = 0;
                this.L$0 = flowCollector;
                this.L$1 = mutableScatterSet;
                this.L$2 = function1;
                this.L$3 = channelChannel$default;
                this.L$4 = observerHandleRegisterApplyObserver;
                this.L$5 = obj2;
                this.I$0 = i9;
                this.label = i8;
                objReceive = channelChannel$default.receive(this);
                if (objReceive != coroutineSingletons) {
                }
            }
            return coroutineSingletons;
        } catch (Throwable th2) {
            th = th2;
            observerHandleRegisterApplyObserver.dispose();
            throw th;
        }
    }
}
