package androidx.compose.foundation.lazy.layout;

import android.os.Trace;
import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.foundation.lazy.layout.AndroidPrefetchScheduler;
import androidx.compose.foundation.lazy.layout.LazyLayoutPrefetchState;
import androidx.compose.foundation.lazy.layout.LazyLayoutPrefetchState.NestedPrefetchScopeImpl;
import androidx.compose.ui.layout.SubcomposeLayoutState;
import androidx.compose.ui.node.TraversableNode;
import androidx.compose.ui.node.TraversableNode$Companion$TraverseDescendantsAction;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.IntSize;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.time.Duration;
import kotlin.time.MonotonicTimeSource;
import kotlin.time.TimeSource$Monotonic;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PrefetchHandleProvider {
    public final PrefetchScheduler executor;
    public final LazyLayoutItemContentFactory itemContentFactory;
    public final SubcomposeLayoutState subcomposeLayoutState;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class HandleAndRequestImpl implements LazyLayoutPrefetchState.PrefetchHandle, PrefetchRequest, LazyLayoutPrefetchState.LazyLayoutPrefetchResultScope {
        public long availableTimeNanos;
        public final long constraints;
        public long elapsedTimeNanos;
        public boolean hasResolvedNestedPrefetches;
        public final int index;
        public boolean isCanceled;
        public boolean isMeasured;
        public boolean isUrgent;
        public NestedPrefetchController nestedPrefetchController;
        public final Function1 onItemPrefetched;
        public SubcomposeLayoutState.PrecomposedSlotHandle precomposeHandle;
        public final PrefetchMetrics prefetchMetrics;
        public long startTime;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        final class NestedPrefetchController {
            public int requestIndex;
            public final List[] requestsByState;
            public int stateIndex;
            public final List states;

            public NestedPrefetchController(List<LazyLayoutPrefetchState> list) {
                this.states = list;
                this.requestsByState = new List[list.size()];
                if (list.isEmpty()) {
                    InlineClassHelperKt.throwIllegalArgumentException("NestedPrefetchController shouldn't be created with no states");
                }
            }
        }

        public /* synthetic */ HandleAndRequestImpl(PrefetchHandleProvider prefetchHandleProvider, int i, long j, PrefetchMetrics prefetchMetrics, Function1 function1, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, j, prefetchMetrics, function1);
        }

        @Override // androidx.compose.foundation.lazy.layout.LazyLayoutPrefetchState.PrefetchHandle
        public final void cancel() {
            if (this.isCanceled) {
                return;
            }
            this.isCanceled = true;
            SubcomposeLayoutState.PrecomposedSlotHandle precomposedSlotHandle = this.precomposeHandle;
            if (precomposedSlotHandle != null) {
                precomposedSlotHandle.dispose();
            }
            this.precomposeHandle = null;
        }

        public final boolean execute(AndroidPrefetchScheduler.PrefetchRequestScopeImpl prefetchRequestScopeImpl) {
            long j;
            long j2;
            long j3;
            List list;
            long j4;
            PrefetchHandleProvider prefetchHandleProvider = PrefetchHandleProvider.this;
            LazyLayoutItemProvider lazyLayoutItemProvider = (LazyLayoutItemProvider) prefetchHandleProvider.itemContentFactory.itemProvider.invoke();
            if (!this.isCanceled) {
                int itemCount = lazyLayoutItemProvider.getItemCount();
                int i = this.index;
                if (i >= 0 && i < itemCount) {
                    Object contentType = lazyLayoutItemProvider.getContentType(i);
                    long nanoTime = System.nanoTime();
                    long j5 = prefetchRequestScopeImpl.nextFrameTimeNs;
                    this.availableTimeNanos = Math.max(0L, j5 - nanoTime);
                    TimeSource$Monotonic.INSTANCE.getClass();
                    MonotonicTimeSource.INSTANCE.getClass();
                    this.startTime = MonotonicTimeSource.read();
                    this.elapsedTimeNanos = 0L;
                    boolean z = this.precomposeHandle != null;
                    PrefetchMetrics prefetchMetrics = this.prefetchMetrics;
                    if (z) {
                        j = j5;
                        j2 = 0;
                    } else {
                        j2 = 0;
                        long j6 = this.availableTimeNanos;
                        j = j5;
                        long j7 = prefetchMetrics.getAverage(contentType).compositionTimeNanos;
                        if ((!this.isUrgent || j6 <= 0) && j7 >= j6) {
                            return true;
                        }
                        Trace.beginSection("compose:lazy:prefetch:compose");
                        try {
                            if (this.precomposeHandle != null) {
                                InlineClassHelperKt.throwIllegalArgumentException("Request was already composed!");
                            }
                            Object key = lazyLayoutItemProvider.getKey(i);
                            this.precomposeHandle = prefetchHandleProvider.subcomposeLayoutState.getState().precompose(key, prefetchHandleProvider.itemContentFactory.getContent(i, key, contentType));
                            Unit unit = Unit.INSTANCE;
                            Trace.endSection();
                            updateElapsedAndAvailableTime();
                            long j8 = this.elapsedTimeNanos;
                            Averages averages = prefetchMetrics.overallAverage;
                            long j9 = averages.compositionTimeNanos;
                            if (j9 == 0) {
                                j4 = j8;
                            } else {
                                long j10 = 4;
                                j4 = (j8 / j10) + ((j9 / j10) * 3);
                            }
                            averages.compositionTimeNanos = j4;
                            Averages average = prefetchMetrics.getAverage(contentType);
                            long j11 = average.compositionTimeNanos;
                            if (j11 != 0) {
                                long j12 = 4;
                                j8 = (j8 / j12) + ((j11 / j12) * 3);
                            }
                            average.compositionTimeNanos = j8;
                        } finally {
                        }
                    }
                    if (!this.isUrgent) {
                        if (!this.hasResolvedNestedPrefetches) {
                            if (this.availableTimeNanos <= j2) {
                                return true;
                            }
                            Trace.beginSection("compose:lazy:prefetch:resolve-nested");
                            try {
                                this.nestedPrefetchController = resolveNestedPrefetchStates();
                                this.hasResolvedNestedPrefetches = true;
                                Unit unit2 = Unit.INSTANCE;
                            } finally {
                            }
                        }
                        NestedPrefetchController nestedPrefetchController = this.nestedPrefetchController;
                        if (nestedPrefetchController != null && nestedPrefetchController.stateIndex < nestedPrefetchController.states.size()) {
                            if (HandleAndRequestImpl.this.isCanceled) {
                                InlineClassHelperKt.throwIllegalStateException("Should not execute nested prefetch on canceled request");
                            }
                            Trace.beginSection("compose:lazy:prefetch:nested");
                            while (nestedPrefetchController.stateIndex < nestedPrefetchController.states.size()) {
                                try {
                                    if (nestedPrefetchController.requestsByState[nestedPrefetchController.stateIndex] == null) {
                                        long j13 = j2;
                                        if (Math.max(j13, j - System.nanoTime()) <= j13) {
                                            return true;
                                        }
                                        List[] listArr = nestedPrefetchController.requestsByState;
                                        int i2 = nestedPrefetchController.stateIndex;
                                        LazyLayoutPrefetchState lazyLayoutPrefetchState = (LazyLayoutPrefetchState) nestedPrefetchController.states.get(i2);
                                        Function1 function1 = lazyLayoutPrefetchState.onNestedPrefetch;
                                        if (function1 == null) {
                                            list = EmptyList.INSTANCE;
                                        } else {
                                            LazyLayoutPrefetchState.NestedPrefetchScopeImpl nestedPrefetchScopeImpl = lazyLayoutPrefetchState.new NestedPrefetchScopeImpl();
                                            function1.mo779invoke(nestedPrefetchScopeImpl);
                                            list = nestedPrefetchScopeImpl._requests;
                                        }
                                        listArr[i2] = list;
                                    }
                                    List list2 = nestedPrefetchController.requestsByState[nestedPrefetchController.stateIndex];
                                    list2.getClass();
                                    while (nestedPrefetchController.requestIndex < list2.size()) {
                                        if (((HandleAndRequestImpl) ((PrefetchRequest) list2.get(nestedPrefetchController.requestIndex))).execute(prefetchRequestScopeImpl)) {
                                            return true;
                                        }
                                        nestedPrefetchController.requestIndex++;
                                    }
                                    nestedPrefetchController.requestIndex = 0;
                                    nestedPrefetchController.stateIndex++;
                                    j2 = 0;
                                } finally {
                                }
                            }
                            Unit unit3 = Unit.INSTANCE;
                        }
                        updateElapsedAndAvailableTime();
                    }
                    if (!this.isMeasured) {
                        Constraints.Companion companion = Constraints.Companion;
                        long j14 = this.constraints;
                        int i3 = (int) (3 & j14);
                        int i4 = (((i3 & 2) >> 1) * 3) + ((i3 & 1) << 1);
                        int i5 = (((int) (j14 >> 33)) & ((1 << (i4 + 13)) - 1)) - 1;
                        if (!(((((1 << (18 - i4)) - 1) & ((int) (j14 >> (i4 + 46)))) - 1 == 0) | (i5 == 0))) {
                            long j15 = this.availableTimeNanos;
                            long j16 = prefetchMetrics.getAverage(contentType).measureTimeNanos;
                            if ((!this.isUrgent || j15 <= 0) && j16 >= j15) {
                                return true;
                            }
                            Trace.beginSection("compose:lazy:prefetch:measure");
                            try {
                                m174performMeasureBRTryo0(j14);
                                Unit unit4 = Unit.INSTANCE;
                                Trace.endSection();
                                updateElapsedAndAvailableTime();
                                long j17 = this.elapsedTimeNanos;
                                Averages averages2 = prefetchMetrics.overallAverage;
                                long j18 = averages2.measureTimeNanos;
                                if (j18 == 0) {
                                    j3 = j17;
                                } else {
                                    long j19 = 4;
                                    j3 = (j17 / j19) + ((j18 / j19) * 3);
                                }
                                averages2.measureTimeNanos = j3;
                                Averages average2 = prefetchMetrics.getAverage(contentType);
                                long j20 = average2.measureTimeNanos;
                                if (j20 != 0) {
                                    long j21 = 4;
                                    j17 = (j17 / j21) + ((j20 / j21) * 3);
                                }
                                average2.measureTimeNanos = j17;
                                Function1 function12 = this.onItemPrefetched;
                                if (function12 != null) {
                                    function12.mo779invoke(this);
                                    return false;
                                }
                            } finally {
                            }
                        }
                    }
                }
            }
            return false;
        }

        @Override // androidx.compose.foundation.lazy.layout.LazyLayoutPrefetchState.LazyLayoutPrefetchResultScope
        public final int getPlaceablesCount() {
            SubcomposeLayoutState.PrecomposedSlotHandle precomposedSlotHandle = this.precomposeHandle;
            if (precomposedSlotHandle != null) {
                return precomposedSlotHandle.getPlaceablesCount();
            }
            return 0;
        }

        @Override // androidx.compose.foundation.lazy.layout.LazyLayoutPrefetchState.LazyLayoutPrefetchResultScope
        /* renamed from: getSize-YEO4UFw */
        public final long mo171getSizeYEO4UFw(int i) {
            SubcomposeLayoutState.PrecomposedSlotHandle precomposedSlotHandle = this.precomposeHandle;
            if (precomposedSlotHandle != null) {
                return precomposedSlotHandle.mo620getSizeYEO4UFw(i);
            }
            IntSize.Companion.getClass();
            return 0L;
        }

        @Override // androidx.compose.foundation.lazy.layout.LazyLayoutPrefetchState.PrefetchHandle
        public final void markAsUrgent() {
            this.isUrgent = true;
        }

        /* renamed from: performMeasure-BRTryo0, reason: not valid java name */
        public final void m174performMeasureBRTryo0(long j) {
            if (this.isCanceled) {
                InlineClassHelperKt.throwIllegalArgumentException("Callers should check whether the request is still valid before calling performMeasure()");
            }
            if (this.isMeasured) {
                InlineClassHelperKt.throwIllegalArgumentException("Request was already measured!");
            }
            this.isMeasured = true;
            SubcomposeLayoutState.PrecomposedSlotHandle precomposedSlotHandle = this.precomposeHandle;
            if (precomposedSlotHandle == null) {
                InlineClassHelperKt.throwIllegalArgumentExceptionForNullCheck("performComposition() must be called before performMeasure()");
                throw new KotlinNothingValueException();
            }
            int placeablesCount = precomposedSlotHandle.getPlaceablesCount();
            for (int i = 0; i < placeablesCount; i++) {
                precomposedSlotHandle.mo621premeasure0kLqBqw(i, j);
            }
        }

        public final NestedPrefetchController resolveNestedPrefetchStates() {
            SubcomposeLayoutState.PrecomposedSlotHandle precomposedSlotHandle = this.precomposeHandle;
            if (precomposedSlotHandle == null) {
                InlineClassHelperKt.throwIllegalArgumentExceptionForNullCheck("Should precompose before resolving nested prefetch states");
                throw new KotlinNothingValueException();
            }
            final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            precomposedSlotHandle.traverseDescendants(new Function1() { // from class: androidx.compose.foundation.lazy.layout.PrefetchHandleProvider$HandleAndRequestImpl$resolveNestedPrefetchStates$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    T t;
                    LazyLayoutPrefetchState lazyLayoutPrefetchState = ((TraversablePrefetchStateNode) ((TraversableNode) obj)).prefetchState;
                    Ref$ObjectRef<List<LazyLayoutPrefetchState>> ref$ObjectRef2 = ref$ObjectRef;
                    List<LazyLayoutPrefetchState> list = ref$ObjectRef2.element;
                    if (list != null) {
                        list.add(lazyLayoutPrefetchState);
                        t = list;
                    } else {
                        t = CollectionsKt__CollectionsKt.mutableListOf(lazyLayoutPrefetchState);
                    }
                    ref$ObjectRef2.element = t;
                    return TraversableNode$Companion$TraverseDescendantsAction.SkipSubtreeAndContinueTraversal;
                }
            });
            List list = (List) ref$ObjectRef.element;
            if (list != null) {
                return new NestedPrefetchController(list);
            }
            return null;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("HandleAndRequestImpl { index = ");
            sb.append(this.index);
            sb.append(", constraints = ");
            sb.append((Object) Constraints.m824toStringimpl(this.constraints));
            sb.append(", isComposed = ");
            sb.append(this.precomposeHandle != null);
            sb.append(", isMeasured = ");
            sb.append(this.isMeasured);
            sb.append(", isCanceled = ");
            return MoveResult$$ExternalSyntheticOutline0.m(sb, this.isCanceled, " }");
        }

        public final void updateElapsedAndAvailableTime() {
            TimeSource$Monotonic.INSTANCE.getClass();
            MonotonicTimeSource.INSTANCE.getClass();
            long read = MonotonicTimeSource.read();
            long m3448minus6eNON_k = TimeSource$Monotonic.ValueTimeMark.m3448minus6eNON_k(read, this.startTime);
            long j = m3448minus6eNON_k >> 1;
            Duration.Companion companion = Duration.Companion;
            if ((((int) m3448minus6eNON_k) & 1) != 0) {
                j = j > 9223372036854L ? Long.MAX_VALUE : j < -9223372036854L ? Long.MIN_VALUE : j * 1000000;
            }
            this.elapsedTimeNanos = j;
            this.availableTimeNanos -= j;
            this.startTime = read;
        }

        private HandleAndRequestImpl(int i, long j, PrefetchMetrics prefetchMetrics, Function1 function1) {
            this.index = i;
            this.constraints = j;
            this.prefetchMetrics = prefetchMetrics;
            this.onItemPrefetched = function1;
            TimeSource$Monotonic.INSTANCE.getClass();
            MonotonicTimeSource.INSTANCE.getClass();
            this.startTime = MonotonicTimeSource.read();
        }
    }

    public PrefetchHandleProvider(LazyLayoutItemContentFactory lazyLayoutItemContentFactory, SubcomposeLayoutState subcomposeLayoutState, PrefetchScheduler prefetchScheduler) {
        this.itemContentFactory = lazyLayoutItemContentFactory;
        this.subcomposeLayoutState = subcomposeLayoutState;
        this.executor = prefetchScheduler;
    }
}
