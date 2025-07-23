package androidx.compose.foundation.gestures;

import androidx.compose.foundation.MutatePriority;
import androidx.compose.foundation.MutatorMutex;
import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PrimitiveSnapshotStateKt;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import com.android.systemui.qs.panels.ui.compose.selection.QSDragAnchor;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.sync.MutexImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AnchoredDraggableState<T> {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnchoredDraggableState$anchoredDragScope$1 anchoredDragScope;
    public final MutableState anchors$delegate;
    public final Function1 confirmValueChange;
    public final MutableState currentValue$delegate;
    public final MutatorMutex dragMutex;
    public final MutableState dragTarget$delegate;
    public final MutableFloatState lastVelocity$delegate;
    public final MutableFloatState offset$delegate;
    public final MutableState settledValue$delegate;
    public final State targetValue$delegate;

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

    public AnchoredDraggableState(T t) {
        this.confirmValueChange = new Function1() { // from class: androidx.compose.foundation.gestures.AnchoredDraggableState$confirmValueChange$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final /* bridge */ /* synthetic */ Object mo779invoke(Object obj) {
                return Boolean.TRUE;
            }
        };
        this.dragMutex = new MutatorMutex();
        this.currentValue$delegate = SnapshotStateKt.mutableStateOf$default(t);
        this.settledValue$delegate = SnapshotStateKt.mutableStateOf$default(t);
        this.targetValue$delegate = SnapshotStateKt.derivedStateOf(new Function0(this) { // from class: androidx.compose.foundation.gestures.AnchoredDraggableState$targetValue$2
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
                boolean isNaN = Float.isNaN(((SnapshotMutableFloatStateImpl) anchoredDraggableState.offset$delegate).getFloatValue());
                MutableState mutableState = anchoredDraggableState.currentValue$delegate;
                if (isNaN) {
                    return ((SnapshotMutableStateImpl) mutableState).getValue();
                }
                Object closestAnchor = ((DefaultDraggableAnchors) anchoredDraggableState.getAnchors()).closestAnchor(((SnapshotMutableFloatStateImpl) anchoredDraggableState.offset$delegate).getFloatValue());
                return closestAnchor == null ? ((SnapshotMutableStateImpl) mutableState).getValue() : closestAnchor;
            }
        });
        this.offset$delegate = PrimitiveSnapshotStateKt.mutableFloatStateOf(Float.NaN);
        SnapshotStateKt.derivedStateOf(SnapshotStateKt.structuralEqualityPolicy(), new Function0(this) { // from class: androidx.compose.foundation.gestures.AnchoredDraggableState$progress$2
            final /* synthetic */ AnchoredDraggableState<Object> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                float positionOf = ((DefaultDraggableAnchors) this.this$0.getAnchors()).positionOf(((SnapshotMutableStateImpl) this.this$0.settledValue$delegate).getValue());
                float positionOf2 = ((DefaultDraggableAnchors) this.this$0.getAnchors()).positionOf(this.this$0.targetValue$delegate.getValue()) - positionOf;
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
        Function1 function1 = AnchoredDraggableKt.AlwaysDrag;
        this.anchors$delegate = SnapshotStateKt.mutableStateOf$default(new DefaultDraggableAnchors(EmptyList.INSTANCE, new float[0]));
        this.anchoredDragScope = new AnchoredDraggableState$anchoredDragScope$1(this);
    }

    public static Object anchoredDrag$default(AnchoredDraggableState anchoredDraggableState, Function3 function3, ContinuationImpl continuationImpl) {
        MutatePriority mutatePriority = MutatePriority.Default;
        anchoredDraggableState.getClass();
        Object mutate = anchoredDraggableState.dragMutex.mutate(mutatePriority, new AnchoredDraggableState$anchoredDrag$2(anchoredDraggableState, function3, null), continuationImpl);
        return mutate == CoroutineSingletons.COROUTINE_SUSPENDED ? mutate : Unit.INSTANCE;
    }

    public static void updateAnchors$default(AnchoredDraggableState anchoredDraggableState, DraggableAnchors draggableAnchors) {
        Object value;
        boolean isNaN = Float.isNaN(((SnapshotMutableFloatStateImpl) anchoredDraggableState.offset$delegate).getFloatValue());
        State state = anchoredDraggableState.targetValue$delegate;
        if (isNaN) {
            value = state.getValue();
        } else {
            value = ((DefaultDraggableAnchors) draggableAnchors).closestAnchor(((SnapshotMutableFloatStateImpl) anchoredDraggableState.offset$delegate).getFloatValue());
            if (value == null) {
                value = state.getValue();
            }
        }
        if (Intrinsics.areEqual(anchoredDraggableState.getAnchors(), draggableAnchors)) {
            return;
        }
        ((SnapshotMutableStateImpl) anchoredDraggableState.anchors$delegate).setValue(draggableAnchors);
        if (anchoredDraggableState.trySnapTo(value)) {
            return;
        }
        ((SnapshotMutableStateImpl) anchoredDraggableState.dragTarget$delegate).setValue(value);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0022  */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v7, types: [androidx.compose.runtime.SnapshotMutableStateImpl] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object anchoredDrag(java.lang.Object r6, androidx.compose.foundation.MutatePriority r7, kotlin.jvm.functions.Function4 r8, kotlin.coroutines.jvm.internal.ContinuationImpl r9) {
        /*
            r5 = this;
            boolean r0 = r9 instanceof androidx.compose.foundation.gestures.AnchoredDraggableState$anchoredDrag$3
            if (r0 == 0) goto L13
            r0 = r9
            androidx.compose.foundation.gestures.AnchoredDraggableState$anchoredDrag$3 r0 = (androidx.compose.foundation.gestures.AnchoredDraggableState$anchoredDrag$3) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.foundation.gestures.AnchoredDraggableState$anchoredDrag$3 r0 = new androidx.compose.foundation.gestures.AnchoredDraggableState$anchoredDrag$3
            r0.<init>(r5, r9)
        L18:
            java.lang.Object r9 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L36
            if (r2 != r3) goto L2e
            java.lang.Object r5 = r0.L$0
            androidx.compose.foundation.gestures.AnchoredDraggableState r5 = (androidx.compose.foundation.gestures.AnchoredDraggableState) r5
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Throwable -> L2c
            goto L5a
        L2c:
            r6 = move-exception
            goto L62
        L2e:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L36:
            kotlin.ResultKt.throwOnFailure(r9)
            androidx.compose.foundation.gestures.DraggableAnchors r9 = r5.getAnchors()
            androidx.compose.foundation.gestures.DefaultDraggableAnchors r9 = (androidx.compose.foundation.gestures.DefaultDraggableAnchors) r9
            java.util.List r9 = r9.keys
            int r9 = r9.indexOf(r6)
            r2 = -1
            if (r9 == r2) goto L6a
            androidx.compose.foundation.MutatorMutex r9 = r5.dragMutex     // Catch: java.lang.Throwable -> L2c
            androidx.compose.foundation.gestures.AnchoredDraggableState$anchoredDrag$4 r2 = new androidx.compose.foundation.gestures.AnchoredDraggableState$anchoredDrag$4     // Catch: java.lang.Throwable -> L2c
            r2.<init>(r5, r6, r8, r4)     // Catch: java.lang.Throwable -> L2c
            r0.L$0 = r5     // Catch: java.lang.Throwable -> L2c
            r0.label = r3     // Catch: java.lang.Throwable -> L2c
            java.lang.Object r6 = r9.mutate(r7, r2, r0)     // Catch: java.lang.Throwable -> L2c
            if (r6 != r1) goto L5a
            return r1
        L5a:
            androidx.compose.runtime.MutableState r5 = r5.dragTarget$delegate
            androidx.compose.runtime.SnapshotMutableStateImpl r5 = (androidx.compose.runtime.SnapshotMutableStateImpl) r5
            r5.setValue(r4)
            goto L82
        L62:
            androidx.compose.runtime.MutableState r5 = r5.dragTarget$delegate
            androidx.compose.runtime.SnapshotMutableStateImpl r5 = (androidx.compose.runtime.SnapshotMutableStateImpl) r5
            r5.setValue(r4)
            throw r6
        L6a:
            kotlin.jvm.functions.Function1 r7 = r5.confirmValueChange
            java.lang.Object r7 = r7.mo779invoke(r6)
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r7 = r7.booleanValue()
            if (r7 == 0) goto L82
            androidx.compose.runtime.MutableState r7 = r5.settledValue$delegate
            androidx.compose.runtime.SnapshotMutableStateImpl r7 = (androidx.compose.runtime.SnapshotMutableStateImpl) r7
            r7.setValue(r6)
            r5.setCurrentValue(r6)
        L82:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.AnchoredDraggableState.anchoredDrag(java.lang.Object, androidx.compose.foundation.MutatePriority, kotlin.jvm.functions.Function4, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final DraggableAnchors getAnchors() {
        return (DraggableAnchors) ((SnapshotMutableStateImpl) this.anchors$delegate).getValue();
    }

    public final float newOffsetForDelta$foundation_release(float f) {
        SnapshotMutableFloatStateImpl snapshotMutableFloatStateImpl = (SnapshotMutableFloatStateImpl) this.offset$delegate;
        return RangesKt___RangesKt.coerceIn((Float.isNaN(snapshotMutableFloatStateImpl.getFloatValue()) ? 0.0f : snapshotMutableFloatStateImpl.getFloatValue()) + f, ((DefaultDraggableAnchors) getAnchors()).minPosition(), ((DefaultDraggableAnchors) getAnchors()).maxPosition());
    }

    public final float progress(QSDragAnchor qSDragAnchor, QSDragAnchor qSDragAnchor2) {
        float positionOf = ((DefaultDraggableAnchors) getAnchors()).positionOf(qSDragAnchor);
        float positionOf2 = ((DefaultDraggableAnchors) getAnchors()).positionOf(qSDragAnchor2);
        float coerceIn = (RangesKt___RangesKt.coerceIn(((SnapshotMutableFloatStateImpl) this.offset$delegate).getFloatValue(), Math.min(positionOf, positionOf2), Math.max(positionOf, positionOf2)) - positionOf) / (positionOf2 - positionOf);
        if (Float.isNaN(coerceIn)) {
            return 1.0f;
        }
        if (coerceIn < 1.0E-6f) {
            return 0.0f;
        }
        if (coerceIn > 0.999999f) {
            return 1.0f;
        }
        return Math.abs(coerceIn);
    }

    public final float requireOffset() {
        MutableFloatState mutableFloatState = this.offset$delegate;
        if (Float.isNaN(((SnapshotMutableFloatStateImpl) mutableFloatState).getFloatValue())) {
            InlineClassHelperKt.throwIllegalStateException("The offset was read before being initialized. Did you access the offset in a phase before layout, like effects or composition?");
        }
        return ((SnapshotMutableFloatStateImpl) mutableFloatState).getFloatValue();
    }

    public final void setCurrentValue(Object obj) {
        ((SnapshotMutableStateImpl) this.currentValue$delegate).setValue(obj);
    }

    public final boolean trySnapTo(Object obj) {
        MutatorMutex mutatorMutex = this.dragMutex;
        MutexImpl mutexImpl = mutatorMutex.mutex;
        MutexImpl mutexImpl2 = mutatorMutex.mutex;
        boolean tryLock = mutexImpl.tryLock();
        if (!tryLock) {
            return tryLock;
        }
        try {
            AnchoredDraggableState$anchoredDragScope$1 anchoredDraggableState$anchoredDragScope$1 = this.anchoredDragScope;
            float positionOf = ((DefaultDraggableAnchors) getAnchors()).positionOf(obj);
            if (!Float.isNaN(positionOf)) {
                anchoredDraggableState$anchoredDragScope$1.dragTo(positionOf, 0.0f);
                ((SnapshotMutableStateImpl) this.dragTarget$delegate).setValue(null);
            }
            setCurrentValue(obj);
            ((SnapshotMutableStateImpl) this.settledValue$delegate).setValue(obj);
            mutexImpl2.unlock(null);
            return tryLock;
        } catch (Throwable th) {
            mutexImpl2.unlock(null);
            throw th;
        }
    }

    public AnchoredDraggableState(T t, DraggableAnchors<T> draggableAnchors) {
        this(t);
        ((SnapshotMutableStateImpl) this.anchors$delegate).setValue(draggableAnchors);
        trySnapTo(t);
    }

    public AnchoredDraggableState(T t, Function1 function1) {
        this(t);
        this.confirmValueChange = function1;
    }

    public /* synthetic */ AnchoredDraggableState(Object obj, DraggableAnchors draggableAnchors, Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(obj, draggableAnchors, (i & 4) != 0 ? new Function1() { // from class: androidx.compose.foundation.gestures.AnchoredDraggableState.1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final /* bridge */ /* synthetic */ Object mo779invoke(Object obj2) {
                return Boolean.TRUE;
            }
        } : function1);
    }

    public AnchoredDraggableState(T t, DraggableAnchors<T> draggableAnchors, Function1 function1) {
        this(t, function1);
        ((SnapshotMutableStateImpl) this.anchors$delegate).setValue(draggableAnchors);
        trySnapTo(t);
    }
}
