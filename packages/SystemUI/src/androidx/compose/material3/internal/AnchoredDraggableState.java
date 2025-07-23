package androidx.compose.material3.internal;

import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PrimitiveSnapshotStateKt;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.sync.MutexImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AnchoredDraggableState<T> {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnchoredDraggableState$anchoredDragScope$1 anchoredDragScope;
    public final MutableState anchors$delegate;
    public final Function0 animationSpec;
    public final State closestValue$delegate;
    public final Function1 confirmValueChange;
    public final MutableState currentValue$delegate;
    public final InternalMutatorMutex dragMutex;
    public final MutableState dragTarget$delegate;
    public final AnchoredDraggableState$draggableState$1 draggableState;
    public final MutableFloatState lastVelocity$delegate;
    public final MutableFloatState offset$delegate;
    public final Function1 positionalThreshold;
    public final State targetValue$delegate;
    public final Function0 velocityThreshold;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public AnchoredDraggableState(T t, Function1 function1, Function0 function0, Function0 function02, Function1 function12) {
        this.positionalThreshold = function1;
        this.velocityThreshold = function0;
        this.animationSpec = function02;
        this.confirmValueChange = function12;
        this.dragMutex = new InternalMutatorMutex();
        this.draggableState = new AnchoredDraggableState$draggableState$1(this);
        this.currentValue$delegate = SnapshotStateKt.mutableStateOf$default(t);
        this.targetValue$delegate = SnapshotStateKt.derivedStateOf(new Function0(this) { // from class: androidx.compose.material3.internal.AnchoredDraggableState$targetValue$2
            final /* synthetic */ AnchoredDraggableState<Object> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object value = ((SnapshotMutableStateImpl) this.this$0.dragTarget$delegate).getValue();
                if (value != null) {
                    return value;
                }
                AnchoredDraggableState<Object> anchoredDraggableState = this.this$0;
                float floatValue = ((SnapshotMutableFloatStateImpl) anchoredDraggableState.offset$delegate).getFloatValue();
                boolean isNaN = Float.isNaN(floatValue);
                MutableState mutableState = anchoredDraggableState.currentValue$delegate;
                return !isNaN ? anchoredDraggableState.computeTarget(floatValue, 0.0f, ((SnapshotMutableStateImpl) mutableState).getValue()) : ((SnapshotMutableStateImpl) mutableState).getValue();
            }
        });
        this.closestValue$delegate = SnapshotStateKt.derivedStateOf(new Function0(this) { // from class: androidx.compose.material3.internal.AnchoredDraggableState$closestValue$2
            final /* synthetic */ AnchoredDraggableState<Object> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object value = ((SnapshotMutableStateImpl) this.this$0.dragTarget$delegate).getValue();
                if (value != null) {
                    return value;
                }
                AnchoredDraggableState<Object> anchoredDraggableState = this.this$0;
                float floatValue = ((SnapshotMutableFloatStateImpl) anchoredDraggableState.offset$delegate).getFloatValue();
                boolean isNaN = Float.isNaN(floatValue);
                MutableState mutableState = anchoredDraggableState.currentValue$delegate;
                if (isNaN) {
                    return ((SnapshotMutableStateImpl) mutableState).getValue();
                }
                Object value2 = ((SnapshotMutableStateImpl) mutableState).getValue();
                MapDraggableAnchors mapDraggableAnchors = (MapDraggableAnchors) anchoredDraggableState.getAnchors();
                float positionOf = mapDraggableAnchors.positionOf(value2);
                if (positionOf != floatValue && !Float.isNaN(positionOf)) {
                    if (positionOf < floatValue) {
                        Object closestAnchor = mapDraggableAnchors.closestAnchor(floatValue, true);
                        if (closestAnchor != null) {
                            return closestAnchor;
                        }
                    } else {
                        Object closestAnchor2 = mapDraggableAnchors.closestAnchor(floatValue, false);
                        if (closestAnchor2 != null) {
                            return closestAnchor2;
                        }
                    }
                }
                return value2;
            }
        });
        this.offset$delegate = PrimitiveSnapshotStateKt.mutableFloatStateOf(Float.NaN);
        SnapshotStateKt.derivedStateOf(SnapshotStateKt.structuralEqualityPolicy(), new Function0(this) { // from class: androidx.compose.material3.internal.AnchoredDraggableState$progress$2
            final /* synthetic */ AnchoredDraggableState<Object> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                float positionOf = ((MapDraggableAnchors) this.this$0.getAnchors()).positionOf(((SnapshotMutableStateImpl) this.this$0.currentValue$delegate).getValue());
                float positionOf2 = ((MapDraggableAnchors) this.this$0.getAnchors()).positionOf(this.this$0.closestValue$delegate.getValue()) - positionOf;
                float abs = Math.abs(positionOf2);
                float f = 1.0f;
                if (!Float.isNaN(abs) && abs > 1.0E-6f) {
                    float requireOffset = (this.this$0.requireOffset() - positionOf) / positionOf2;
                    if (requireOffset < 1.0E-6f) {
                        f = 0.0f;
                    } else if (requireOffset <= 0.999999f) {
                        f = requireOffset;
                    }
                }
                return Float.valueOf(f);
            }
        });
        this.lastVelocity$delegate = PrimitiveSnapshotStateKt.mutableFloatStateOf(0.0f);
        this.dragTarget$delegate = SnapshotStateKt.mutableStateOf$default(null);
        this.anchors$delegate = SnapshotStateKt.mutableStateOf$default(new MapDraggableAnchors(MapsKt__MapsKt.emptyMap()));
        this.anchoredDragScope = new AnchoredDraggableState$anchoredDragScope$1(this);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object anchoredDrag(androidx.compose.foundation.MutatePriority r7, kotlin.jvm.functions.Function3 r8, kotlin.coroutines.jvm.internal.ContinuationImpl r9) {
        /*
            r6 = this;
            boolean r0 = r9 instanceof androidx.compose.material3.internal.AnchoredDraggableState$anchoredDrag$1
            if (r0 == 0) goto L13
            r0 = r9
            androidx.compose.material3.internal.AnchoredDraggableState$anchoredDrag$1 r0 = (androidx.compose.material3.internal.AnchoredDraggableState$anchoredDrag$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.material3.internal.AnchoredDraggableState$anchoredDrag$1 r0 = new androidx.compose.material3.internal.AnchoredDraggableState$anchoredDrag$1
            r0.<init>(r6, r9)
        L18:
            java.lang.Object r9 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1056964608(0x3f000000, float:0.5)
            r4 = 1
            if (r2 == 0) goto L37
            if (r2 != r4) goto L2f
            java.lang.Object r6 = r0.L$0
            androidx.compose.material3.internal.AnchoredDraggableState r6 = (androidx.compose.material3.internal.AnchoredDraggableState) r6
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Throwable -> L2d
            goto L55
        L2d:
            r7 = move-exception
            goto L94
        L2f:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L37:
            kotlin.ResultKt.throwOnFailure(r9)
            androidx.compose.material3.internal.InternalMutatorMutex r9 = r6.dragMutex     // Catch: java.lang.Throwable -> L2d
            androidx.compose.material3.internal.AnchoredDraggableState$anchoredDrag$2 r2 = new androidx.compose.material3.internal.AnchoredDraggableState$anchoredDrag$2     // Catch: java.lang.Throwable -> L2d
            r5 = 0
            r2.<init>(r6, r8, r5)     // Catch: java.lang.Throwable -> L2d
            r0.L$0 = r6     // Catch: java.lang.Throwable -> L2d
            r0.label = r4     // Catch: java.lang.Throwable -> L2d
            r9.getClass()     // Catch: java.lang.Throwable -> L2d
            androidx.compose.material3.internal.InternalMutatorMutex$mutate$2 r8 = new androidx.compose.material3.internal.InternalMutatorMutex$mutate$2     // Catch: java.lang.Throwable -> L2d
            r8.<init>(r7, r9, r2, r5)     // Catch: java.lang.Throwable -> L2d
            java.lang.Object r7 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r8, r0)     // Catch: java.lang.Throwable -> L2d
            if (r7 != r1) goto L55
            return r1
        L55:
            androidx.compose.material3.internal.DraggableAnchors r7 = r6.getAnchors()
            androidx.compose.runtime.MutableFloatState r8 = r6.offset$delegate
            androidx.compose.runtime.SnapshotMutableFloatStateImpl r8 = (androidx.compose.runtime.SnapshotMutableFloatStateImpl) r8
            float r9 = r8.getFloatValue()
            androidx.compose.material3.internal.MapDraggableAnchors r7 = (androidx.compose.material3.internal.MapDraggableAnchors) r7
            java.lang.Object r7 = r7.closestAnchor(r9)
            if (r7 == 0) goto L91
            float r8 = r8.getFloatValue()
            androidx.compose.material3.internal.DraggableAnchors r9 = r6.getAnchors()
            androidx.compose.material3.internal.MapDraggableAnchors r9 = (androidx.compose.material3.internal.MapDraggableAnchors) r9
            float r9 = r9.positionOf(r7)
            float r8 = r8 - r9
            float r8 = java.lang.Math.abs(r8)
            int r8 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r8 > 0) goto L91
            kotlin.jvm.functions.Function1 r8 = r6.confirmValueChange
            java.lang.Object r8 = r8.mo779invoke(r7)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L91
            r6.setCurrentValue(r7)
        L91:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        L94:
            androidx.compose.material3.internal.DraggableAnchors r8 = r6.getAnchors()
            androidx.compose.runtime.MutableFloatState r9 = r6.offset$delegate
            androidx.compose.runtime.SnapshotMutableFloatStateImpl r9 = (androidx.compose.runtime.SnapshotMutableFloatStateImpl) r9
            float r0 = r9.getFloatValue()
            androidx.compose.material3.internal.MapDraggableAnchors r8 = (androidx.compose.material3.internal.MapDraggableAnchors) r8
            java.lang.Object r8 = r8.closestAnchor(r0)
            if (r8 == 0) goto Ld0
            float r9 = r9.getFloatValue()
            androidx.compose.material3.internal.DraggableAnchors r0 = r6.getAnchors()
            androidx.compose.material3.internal.MapDraggableAnchors r0 = (androidx.compose.material3.internal.MapDraggableAnchors) r0
            float r0 = r0.positionOf(r8)
            float r9 = r9 - r0
            float r9 = java.lang.Math.abs(r9)
            int r9 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r9 > 0) goto Ld0
            kotlin.jvm.functions.Function1 r9 = r6.confirmValueChange
            java.lang.Object r9 = r9.mo779invoke(r8)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 == 0) goto Ld0
            r6.setCurrentValue(r8)
        Ld0:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.internal.AnchoredDraggableState.anchoredDrag(androidx.compose.foundation.MutatePriority, kotlin.jvm.functions.Function3, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final Object computeTarget(float f, float f2, Object obj) {
        MapDraggableAnchors mapDraggableAnchors = (MapDraggableAnchors) getAnchors();
        float positionOf = mapDraggableAnchors.positionOf(obj);
        float floatValue = ((Number) this.velocityThreshold.invoke()).floatValue();
        if (positionOf == f) {
            return obj;
        }
        if (!Float.isNaN(positionOf)) {
            Function1 function1 = this.positionalThreshold;
            if (positionOf < f) {
                if (f2 >= floatValue) {
                    Object closestAnchor = mapDraggableAnchors.closestAnchor(f, true);
                    closestAnchor.getClass();
                    return closestAnchor;
                }
                Object closestAnchor2 = mapDraggableAnchors.closestAnchor(f, true);
                closestAnchor2.getClass();
                if (f >= Math.abs(Math.abs(((Number) function1.mo779invoke(Float.valueOf(Math.abs(mapDraggableAnchors.positionOf(closestAnchor2) - positionOf)))).floatValue()) + positionOf)) {
                    return closestAnchor2;
                }
            } else {
                if (f2 <= (-floatValue)) {
                    Object closestAnchor3 = mapDraggableAnchors.closestAnchor(f, false);
                    closestAnchor3.getClass();
                    return closestAnchor3;
                }
                Object closestAnchor4 = mapDraggableAnchors.closestAnchor(f, false);
                closestAnchor4.getClass();
                float abs = Math.abs(positionOf - Math.abs(((Number) function1.mo779invoke(Float.valueOf(Math.abs(positionOf - mapDraggableAnchors.positionOf(closestAnchor4))))).floatValue()));
                if (f >= 0.0f ? f <= abs : Math.abs(f) >= abs) {
                    return closestAnchor4;
                }
            }
        }
        return obj;
    }

    public final float dispatchRawDelta(float f) {
        float newOffsetForDelta$material3_release = newOffsetForDelta$material3_release(f);
        MutableFloatState mutableFloatState = this.offset$delegate;
        SnapshotMutableFloatStateImpl snapshotMutableFloatStateImpl = (SnapshotMutableFloatStateImpl) mutableFloatState;
        float floatValue = Float.isNaN(snapshotMutableFloatStateImpl.getFloatValue()) ? 0.0f : snapshotMutableFloatStateImpl.getFloatValue();
        ((SnapshotMutableFloatStateImpl) mutableFloatState).setFloatValue(newOffsetForDelta$material3_release);
        return newOffsetForDelta$material3_release - floatValue;
    }

    public final DraggableAnchors getAnchors() {
        return (DraggableAnchors) ((SnapshotMutableStateImpl) this.anchors$delegate).getValue();
    }

    public final float newOffsetForDelta$material3_release(float f) {
        MutableFloatState mutableFloatState = this.offset$delegate;
        float floatValue = (Float.isNaN(((SnapshotMutableFloatStateImpl) mutableFloatState).getFloatValue()) ? 0.0f : ((SnapshotMutableFloatStateImpl) mutableFloatState).getFloatValue()) + f;
        float minAnchor = ((MapDraggableAnchors) getAnchors()).minAnchor();
        Float m3430maxOrNull = CollectionsKt___CollectionsKt.m3430maxOrNull((Iterable) ((MapDraggableAnchors) getAnchors()).anchors.values());
        return RangesKt___RangesKt.coerceIn(floatValue, minAnchor, m3430maxOrNull != null ? m3430maxOrNull.floatValue() : Float.NaN);
    }

    public final float requireOffset() {
        MutableFloatState mutableFloatState = this.offset$delegate;
        if (Float.isNaN(((SnapshotMutableFloatStateImpl) mutableFloatState).getFloatValue())) {
            throw new IllegalStateException("The offset was read before being initialized. Did you access the offset in a phase before layout, like effects or composition?");
        }
        return ((SnapshotMutableFloatStateImpl) mutableFloatState).getFloatValue();
    }

    public final void setCurrentValue(Object obj) {
        ((SnapshotMutableStateImpl) this.currentValue$delegate).setValue(obj);
    }

    public final void setDragTarget(Object obj) {
        ((SnapshotMutableStateImpl) this.dragTarget$delegate).setValue(obj);
    }

    public final boolean trySnapTo(final Object obj) {
        Function0 function0 = new Function0(this) { // from class: androidx.compose.material3.internal.AnchoredDraggableState$trySnapTo$1
            final /* synthetic */ AnchoredDraggableState<Object> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                AnchoredDraggableState<Object> anchoredDraggableState = this.this$0;
                AnchoredDraggableState$anchoredDragScope$1 anchoredDraggableState$anchoredDragScope$1 = anchoredDraggableState.anchoredDragScope;
                Object obj2 = obj;
                float positionOf = ((MapDraggableAnchors) anchoredDraggableState.getAnchors()).positionOf(obj2);
                if (!Float.isNaN(positionOf)) {
                    anchoredDraggableState$anchoredDragScope$1.getClass();
                    int i = AnchoredDraggableState.$r8$clinit;
                    AnchoredDraggableState anchoredDraggableState2 = anchoredDraggableState$anchoredDragScope$1.this$0;
                    ((SnapshotMutableFloatStateImpl) anchoredDraggableState2.offset$delegate).setFloatValue(positionOf);
                    ((SnapshotMutableFloatStateImpl) anchoredDraggableState2.lastVelocity$delegate).setFloatValue(0.0f);
                    anchoredDraggableState.setDragTarget(null);
                }
                anchoredDraggableState.setCurrentValue(obj2);
                return Unit.INSTANCE;
            }
        };
        MutexImpl mutexImpl = this.dragMutex.mutex;
        boolean tryLock = mutexImpl.tryLock();
        if (!tryLock) {
            return tryLock;
        }
        try {
            function0.invoke();
            return tryLock;
        } finally {
            mutexImpl.unlock(null);
        }
    }

    public /* synthetic */ AnchoredDraggableState(Object obj, Function1 function1, Function0 function0, Function0 function02, Function1 function12, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(obj, function1, function0, function02, (i & 16) != 0 ? new Function1() { // from class: androidx.compose.material3.internal.AnchoredDraggableState.1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final /* bridge */ /* synthetic */ Object mo779invoke(Object obj2) {
                return Boolean.TRUE;
            }
        } : function12);
    }

    public /* synthetic */ AnchoredDraggableState(Object obj, DraggableAnchors draggableAnchors, Function1 function1, Function0 function0, Function0 function02, Function1 function12, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(obj, draggableAnchors, function1, function0, function02, (i & 32) != 0 ? new Function1() { // from class: androidx.compose.material3.internal.AnchoredDraggableState.2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final /* bridge */ /* synthetic */ Object mo779invoke(Object obj2) {
                return Boolean.TRUE;
            }
        } : function12);
    }

    public AnchoredDraggableState(T t, DraggableAnchors<T> draggableAnchors, Function1 function1, Function0 function0, Function0 function02, Function1 function12) {
        this(t, function1, function0, function02, function12);
        ((SnapshotMutableStateImpl) this.anchors$delegate).setValue(draggableAnchors);
        trySnapTo(t);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object anchoredDrag(java.lang.Object r7, androidx.compose.foundation.MutatePriority r8, kotlin.jvm.functions.Function4 r9, kotlin.coroutines.jvm.internal.ContinuationImpl r10) {
        /*
            r6 = this;
            boolean r0 = r10 instanceof androidx.compose.material3.internal.AnchoredDraggableState$anchoredDrag$3
            if (r0 == 0) goto L13
            r0 = r10
            androidx.compose.material3.internal.AnchoredDraggableState$anchoredDrag$3 r0 = (androidx.compose.material3.internal.AnchoredDraggableState$anchoredDrag$3) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.material3.internal.AnchoredDraggableState$anchoredDrag$3 r0 = new androidx.compose.material3.internal.AnchoredDraggableState$anchoredDrag$3
            r0.<init>(r6, r10)
        L18:
            java.lang.Object r10 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 1056964608(0x3f000000, float:0.5)
            r5 = 1
            if (r2 == 0) goto L38
            if (r2 != r5) goto L30
            java.lang.Object r6 = r0.L$0
            androidx.compose.material3.internal.AnchoredDraggableState r6 = (androidx.compose.material3.internal.AnchoredDraggableState) r6
            kotlin.ResultKt.throwOnFailure(r10)     // Catch: java.lang.Throwable -> L2e
            goto L63
        L2e:
            r7 = move-exception
            goto La3
        L30:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L38:
            kotlin.ResultKt.throwOnFailure(r10)
            androidx.compose.material3.internal.DraggableAnchors r10 = r6.getAnchors()
            androidx.compose.material3.internal.MapDraggableAnchors r10 = (androidx.compose.material3.internal.MapDraggableAnchors) r10
            java.util.Map r10 = r10.anchors
            boolean r10 = r10.containsKey(r7)
            if (r10 == 0) goto Le3
            androidx.compose.material3.internal.InternalMutatorMutex r10 = r6.dragMutex     // Catch: java.lang.Throwable -> L2e
            androidx.compose.material3.internal.AnchoredDraggableState$anchoredDrag$4 r2 = new androidx.compose.material3.internal.AnchoredDraggableState$anchoredDrag$4     // Catch: java.lang.Throwable -> L2e
            r2.<init>(r6, r7, r9, r3)     // Catch: java.lang.Throwable -> L2e
            r0.L$0 = r6     // Catch: java.lang.Throwable -> L2e
            r0.label = r5     // Catch: java.lang.Throwable -> L2e
            r10.getClass()     // Catch: java.lang.Throwable -> L2e
            androidx.compose.material3.internal.InternalMutatorMutex$mutate$2 r7 = new androidx.compose.material3.internal.InternalMutatorMutex$mutate$2     // Catch: java.lang.Throwable -> L2e
            r7.<init>(r8, r10, r2, r3)     // Catch: java.lang.Throwable -> L2e
            java.lang.Object r7 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r7, r0)     // Catch: java.lang.Throwable -> L2e
            if (r7 != r1) goto L63
            return r1
        L63:
            r6.setDragTarget(r3)
            androidx.compose.material3.internal.DraggableAnchors r7 = r6.getAnchors()
            androidx.compose.runtime.MutableFloatState r8 = r6.offset$delegate
            androidx.compose.runtime.SnapshotMutableFloatStateImpl r8 = (androidx.compose.runtime.SnapshotMutableFloatStateImpl) r8
            float r9 = r8.getFloatValue()
            androidx.compose.material3.internal.MapDraggableAnchors r7 = (androidx.compose.material3.internal.MapDraggableAnchors) r7
            java.lang.Object r7 = r7.closestAnchor(r9)
            if (r7 == 0) goto Le6
            float r8 = r8.getFloatValue()
            androidx.compose.material3.internal.DraggableAnchors r9 = r6.getAnchors()
            androidx.compose.material3.internal.MapDraggableAnchors r9 = (androidx.compose.material3.internal.MapDraggableAnchors) r9
            float r9 = r9.positionOf(r7)
            float r8 = r8 - r9
            float r8 = java.lang.Math.abs(r8)
            int r8 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r8 > 0) goto Le6
            kotlin.jvm.functions.Function1 r8 = r6.confirmValueChange
            java.lang.Object r8 = r8.mo779invoke(r7)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto Le6
            r6.setCurrentValue(r7)
            goto Le6
        La3:
            r6.setDragTarget(r3)
            androidx.compose.material3.internal.DraggableAnchors r8 = r6.getAnchors()
            androidx.compose.runtime.MutableFloatState r9 = r6.offset$delegate
            androidx.compose.runtime.SnapshotMutableFloatStateImpl r9 = (androidx.compose.runtime.SnapshotMutableFloatStateImpl) r9
            float r10 = r9.getFloatValue()
            androidx.compose.material3.internal.MapDraggableAnchors r8 = (androidx.compose.material3.internal.MapDraggableAnchors) r8
            java.lang.Object r8 = r8.closestAnchor(r10)
            if (r8 == 0) goto Le2
            float r9 = r9.getFloatValue()
            androidx.compose.material3.internal.DraggableAnchors r10 = r6.getAnchors()
            androidx.compose.material3.internal.MapDraggableAnchors r10 = (androidx.compose.material3.internal.MapDraggableAnchors) r10
            float r10 = r10.positionOf(r8)
            float r9 = r9 - r10
            float r9 = java.lang.Math.abs(r9)
            int r9 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
            if (r9 > 0) goto Le2
            kotlin.jvm.functions.Function1 r9 = r6.confirmValueChange
            java.lang.Object r9 = r9.mo779invoke(r8)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 == 0) goto Le2
            r6.setCurrentValue(r8)
        Le2:
            throw r7
        Le3:
            r6.setCurrentValue(r7)
        Le6:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.internal.AnchoredDraggableState.anchoredDrag(java.lang.Object, androidx.compose.foundation.MutatePriority, kotlin.jvm.functions.Function4, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
