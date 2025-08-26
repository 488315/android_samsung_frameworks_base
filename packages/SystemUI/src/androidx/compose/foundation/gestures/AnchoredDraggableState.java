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
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.sync.MutexImpl;

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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: androidx.compose.foundation.gestures.AnchoredDraggableState$anchoredDrag$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function1 {
        final /* synthetic */ Function3 $block;
        int label;
        final /* synthetic */ AnchoredDraggableState<Object> this$0;

        /* renamed from: androidx.compose.foundation.gestures.AnchoredDraggableState$anchoredDrag$2$2, reason: invalid class name and collision with other inner class name */
        final class C00062 extends SuspendLambda implements Function2 {
            final /* synthetic */ Function3 $block;
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ AnchoredDraggableState<Object> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00062(Function3 function3, AnchoredDraggableState<Object> anchoredDraggableState, Continuation continuation) {
                super(2, continuation);
                this.$block = function3;
                this.this$0 = anchoredDraggableState;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C00062 c00062 = new C00062(this.$block, this.this$0, continuation);
                c00062.L$0 = obj;
                return c00062;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00062) create((DraggableAnchors) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    DraggableAnchors draggableAnchors = (DraggableAnchors) this.L$0;
                    Function3 function3 = this.$block;
                    AnchoredDraggableState$anchoredDragScope$1 anchoredDraggableState$anchoredDragScope$1 = this.this$0.anchoredDragScope;
                    this.label = 1;
                    if (function3.invoke(anchoredDraggableState$anchoredDragScope$1, draggableAnchors, this) == coroutineSingletons) {
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

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(AnchoredDraggableState<Object> anchoredDraggableState, Function3 function3, Continuation continuation) {
            super(1, continuation);
            this.this$0 = anchoredDraggableState;
            this.$block = function3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Continuation continuation) {
            return new AnonymousClass2(this.this$0, this.$block, continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            return ((AnonymousClass2) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final AnchoredDraggableState<Object> anchoredDraggableState = this.this$0;
                Function0 function0 = new Function0() { // from class: androidx.compose.foundation.gestures.AnchoredDraggableState.anchoredDrag.2.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return anchoredDraggableState.getAnchors();
                    }
                };
                C00062 c00062 = new C00062(this.$block, this.this$0, null);
                this.label = 1;
                if (AnchoredDraggableKt.access$restartable(function0, c00062, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            Object objClosestAnchor = ((DefaultDraggableAnchors) this.this$0.getAnchors()).closestAnchor(((SnapshotMutableFloatStateImpl) this.this$0.offset$delegate).getFloatValue());
            if (objClosestAnchor != null) {
                if (Math.abs(((SnapshotMutableFloatStateImpl) this.this$0.offset$delegate).getFloatValue() - ((DefaultDraggableAnchors) this.this$0.getAnchors()).positionOf(objClosestAnchor)) < 0.5f && ((Boolean) this.this$0.confirmValueChange.mo781invoke(objClosestAnchor)).booleanValue()) {
                    ((SnapshotMutableStateImpl) this.this$0.settledValue$delegate).setValue(objClosestAnchor);
                    this.this$0.setCurrentValue(objClosestAnchor);
                }
            }
            return Unit.INSTANCE;
        }
    }

    /* renamed from: androidx.compose.foundation.gestures.AnchoredDraggableState$anchoredDrag$3, reason: invalid class name */
    final class AnonymousClass3 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ AnchoredDraggableState<Object> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(AnchoredDraggableState<Object> anchoredDraggableState, Continuation continuation) {
            super(continuation);
            this.this$0 = anchoredDraggableState;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.anchoredDrag(null, null, null, this);
        }
    }

    /* renamed from: androidx.compose.foundation.gestures.AnchoredDraggableState$anchoredDrag$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function1 {
        final /* synthetic */ Function4 $block;
        final /* synthetic */ Object $targetValue;
        int label;
        final /* synthetic */ AnchoredDraggableState<Object> this$0;

        /* renamed from: androidx.compose.foundation.gestures.AnchoredDraggableState$anchoredDrag$4$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ Function4 $block;
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ AnchoredDraggableState<Object> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(Function4 function4, AnchoredDraggableState<Object> anchoredDraggableState, Continuation continuation) {
                super(2, continuation);
                this.$block = function4;
                this.this$0 = anchoredDraggableState;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$block, this.this$0, continuation);
                anonymousClass2.L$0 = obj;
                return anonymousClass2;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass2) create((Pair) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Pair pair = (Pair) this.L$0;
                    DraggableAnchors draggableAnchors = (DraggableAnchors) pair.component1();
                    Object objComponent2 = pair.component2();
                    Function4 function4 = this.$block;
                    AnchoredDraggableState$anchoredDragScope$1 anchoredDraggableState$anchoredDragScope$1 = this.this$0.anchoredDragScope;
                    this.label = 1;
                    if (function4.invoke(anchoredDraggableState$anchoredDragScope$1, draggableAnchors, objComponent2, this) == coroutineSingletons) {
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

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(AnchoredDraggableState<Object> anchoredDraggableState, Object obj, Function4 function4, Continuation continuation) {
            super(1, continuation);
            this.this$0 = anchoredDraggableState;
            this.$targetValue = obj;
            this.$block = function4;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Continuation continuation) {
            return new AnonymousClass4(this.this$0, this.$targetValue, this.$block, continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            return ((AnonymousClass4) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                AnchoredDraggableState<Object> anchoredDraggableState = this.this$0;
                Object obj2 = this.$targetValue;
                int i2 = AnchoredDraggableState.$r8$clinit;
                ((SnapshotMutableStateImpl) anchoredDraggableState.dragTarget$delegate).setValue(obj2);
                final AnchoredDraggableState<Object> anchoredDraggableState2 = this.this$0;
                Function0 function0 = new Function0() { // from class: androidx.compose.foundation.gestures.AnchoredDraggableState.anchoredDrag.4.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Pair(anchoredDraggableState2.getAnchors(), anchoredDraggableState2.targetValue$delegate.getValue());
                    }
                };
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$block, this.this$0, null);
                this.label = 1;
                if (AnchoredDraggableKt.access$restartable(function0, anonymousClass2, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            if (((Boolean) this.this$0.confirmValueChange.mo781invoke(this.$targetValue)).booleanValue()) {
                float fPositionOf = ((DefaultDraggableAnchors) this.this$0.getAnchors()).positionOf(this.$targetValue);
                AnchoredDraggableState<Object> anchoredDraggableState3 = this.this$0;
                anchoredDraggableState3.anchoredDragScope.dragTo(fPositionOf, ((SnapshotMutableFloatStateImpl) anchoredDraggableState3.lastVelocity$delegate).getFloatValue());
                AnchoredDraggableState<Object> anchoredDraggableState4 = this.this$0;
                ((SnapshotMutableStateImpl) anchoredDraggableState4.settledValue$delegate).setValue(this.$targetValue);
                this.this$0.setCurrentValue(this.$targetValue);
            }
            return Unit.INSTANCE;
        }
    }

    static {
        new Companion(null);
    }

    public AnchoredDraggableState(T t) {
        this.confirmValueChange = new Function1() { // from class: androidx.compose.foundation.gestures.AnchoredDraggableState$confirmValueChange$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
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
                boolean zIsNaN = Float.isNaN(((SnapshotMutableFloatStateImpl) anchoredDraggableState.offset$delegate).getFloatValue());
                MutableState mutableState = anchoredDraggableState.currentValue$delegate;
                if (zIsNaN) {
                    return ((SnapshotMutableStateImpl) mutableState).getValue();
                }
                Object objClosestAnchor = ((DefaultDraggableAnchors) anchoredDraggableState.getAnchors()).closestAnchor(((SnapshotMutableFloatStateImpl) anchoredDraggableState.offset$delegate).getFloatValue());
                return objClosestAnchor == null ? ((SnapshotMutableStateImpl) mutableState).getValue() : objClosestAnchor;
            }
        });
        this.offset$delegate = PrimitiveSnapshotStateKt.mutableFloatStateOf(Float.NaN);
        SnapshotStateKt.derivedStateOf(SnapshotStateKt.structuralEqualityPolicy(), new Function0(this) { // from class: androidx.compose.foundation.gestures.AnchoredDraggableState.progress.2
            final /* synthetic */ AnchoredDraggableState<Object> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                float fPositionOf = ((DefaultDraggableAnchors) this.this$0.getAnchors()).positionOf(((SnapshotMutableStateImpl) this.this$0.settledValue$delegate).getValue());
                float fPositionOf2 = ((DefaultDraggableAnchors) this.this$0.getAnchors()).positionOf(this.this$0.targetValue$delegate.getValue()) - fPositionOf;
                float fAbs = Math.abs(fPositionOf2);
                float f = 1.0f;
                if (!Float.isNaN(fAbs) && fAbs > 1.0E-6f) {
                    float fRequireOffset = (this.this$0.requireOffset() - fPositionOf) / fPositionOf2;
                    if (fRequireOffset < 1.0E-6f) {
                        f = 0.0f;
                    } else if (fRequireOffset <= 0.999999f) {
                        f = fRequireOffset;
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
        Object objMutate = anchoredDraggableState.dragMutex.mutate(mutatePriority, new AnonymousClass2(anchoredDraggableState, function3, null), continuationImpl);
        return objMutate == CoroutineSingletons.COROUTINE_SUSPENDED ? objMutate : Unit.INSTANCE;
    }

    public static void updateAnchors$default(AnchoredDraggableState anchoredDraggableState, DraggableAnchors draggableAnchors) {
        Object value;
        boolean zIsNaN = Float.isNaN(((SnapshotMutableFloatStateImpl) anchoredDraggableState.offset$delegate).getFloatValue());
        State state = anchoredDraggableState.targetValue$delegate;
        if (zIsNaN) {
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
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v7, types: [androidx.compose.runtime.SnapshotMutableStateImpl] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object anchoredDrag(Object obj, MutatePriority mutatePriority, Function4 function4, ContinuationImpl continuationImpl) {
        AnonymousClass3 anonymousClass3;
        ?? r5;
        if (continuationImpl instanceof AnonymousClass3) {
            anonymousClass3 = (AnonymousClass3) continuationImpl;
            int i = anonymousClass3.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass3.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass3 = new AnonymousClass3(this, continuationImpl);
            }
        }
        Object obj2 = anonymousClass3.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass3.label;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj2);
                if (((DefaultDraggableAnchors) getAnchors()).keys.indexOf(obj) == -1) {
                    if (((Boolean) this.confirmValueChange.mo781invoke(obj)).booleanValue()) {
                        ((SnapshotMutableStateImpl) this.settledValue$delegate).setValue(obj);
                        setCurrentValue(obj);
                    }
                    return Unit.INSTANCE;
                }
                MutatorMutex mutatorMutex = this.dragMutex;
                AnonymousClass4 anonymousClass4 = new AnonymousClass4(this, obj, function4, null);
                anonymousClass3.L$0 = this;
                anonymousClass3.label = 1;
                this = this;
                if (mutatorMutex.mutate(mutatePriority, anonymousClass4, anonymousClass3) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                boolean z = (AnchoredDraggableState<T>) ((AnchoredDraggableState) anonymousClass3.L$0);
                ResultKt.throwOnFailure(obj2);
                r5 = z;
            }
            return Unit.INSTANCE;
        } finally {
            ((SnapshotMutableStateImpl) this.dragTarget$delegate).setValue(null);
        }
    }

    public final DraggableAnchors getAnchors() {
        return (DraggableAnchors) ((SnapshotMutableStateImpl) this.anchors$delegate).getValue();
    }

    public final float newOffsetForDelta$foundation_release(float f) {
        SnapshotMutableFloatStateImpl snapshotMutableFloatStateImpl = (SnapshotMutableFloatStateImpl) this.offset$delegate;
        return RangesKt___RangesKt.coerceIn((Float.isNaN(snapshotMutableFloatStateImpl.getFloatValue()) ? 0.0f : snapshotMutableFloatStateImpl.getFloatValue()) + f, ((DefaultDraggableAnchors) getAnchors()).minPosition(), ((DefaultDraggableAnchors) getAnchors()).maxPosition());
    }

    public final float progress(QSDragAnchor qSDragAnchor, QSDragAnchor qSDragAnchor2) {
        float fPositionOf = ((DefaultDraggableAnchors) getAnchors()).positionOf(qSDragAnchor);
        float fPositionOf2 = ((DefaultDraggableAnchors) getAnchors()).positionOf(qSDragAnchor2);
        float fCoerceIn = (RangesKt___RangesKt.coerceIn(((SnapshotMutableFloatStateImpl) this.offset$delegate).getFloatValue(), Math.min(fPositionOf, fPositionOf2), Math.max(fPositionOf, fPositionOf2)) - fPositionOf) / (fPositionOf2 - fPositionOf);
        if (Float.isNaN(fCoerceIn)) {
            return 1.0f;
        }
        if (fCoerceIn < 1.0E-6f) {
            return 0.0f;
        }
        if (fCoerceIn > 0.999999f) {
            return 1.0f;
        }
        return Math.abs(fCoerceIn);
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
        boolean zTryLock = mutexImpl.tryLock();
        if (!zTryLock) {
            return zTryLock;
        }
        try {
            AnchoredDraggableState$anchoredDragScope$1 anchoredDraggableState$anchoredDragScope$1 = this.anchoredDragScope;
            float fPositionOf = ((DefaultDraggableAnchors) getAnchors()).positionOf(obj);
            if (!Float.isNaN(fPositionOf)) {
                anchoredDraggableState$anchoredDragScope$1.dragTo(fPositionOf, 0.0f);
                ((SnapshotMutableStateImpl) this.dragTarget$delegate).setValue(null);
            }
            setCurrentValue(obj);
            ((SnapshotMutableStateImpl) this.settledValue$delegate).setValue(obj);
            mutexImpl2.unlock(null);
            return zTryLock;
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
            public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj2) {
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
