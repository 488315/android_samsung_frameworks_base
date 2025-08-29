package androidx.compose.material3.internal;

import androidx.compose.foundation.MutatePriority;
import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PrimitiveSnapshotStateKt;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
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
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.sync.MutexImpl;

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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: androidx.compose.material3.internal.AnchoredDraggableState$anchoredDrag$1, reason: invalid class name and case insensitive filesystem */
    final class C07341 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ AnchoredDraggableState<Object> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C07341(AnchoredDraggableState<Object> anchoredDraggableState, Continuation continuation) {
            super(continuation);
            this.this$0 = anchoredDraggableState;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.anchoredDrag(null, null, this);
        }
    }

    /* renamed from: androidx.compose.material3.internal.AnchoredDraggableState$anchoredDrag$2, reason: invalid class name and case insensitive filesystem */
    final class C07352 extends SuspendLambda implements Function1 {
        final /* synthetic */ Function3 $block;
        int label;
        final /* synthetic */ AnchoredDraggableState<Object> this$0;

        /* renamed from: androidx.compose.material3.internal.AnchoredDraggableState$anchoredDrag$2$2, reason: invalid class name and collision with other inner class name */
        final class C00292 extends SuspendLambda implements Function2 {
            final /* synthetic */ Function3 $block;
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ AnchoredDraggableState<Object> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00292(Function3 function3, AnchoredDraggableState<Object> anchoredDraggableState, Continuation continuation) {
                super(2, continuation);
                this.$block = function3;
                this.this$0 = anchoredDraggableState;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C00292 c00292 = new C00292(this.$block, this.this$0, continuation);
                c00292.L$0 = obj;
                return c00292;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00292) create((DraggableAnchors) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
        public C07352(AnchoredDraggableState<Object> anchoredDraggableState, Function3 function3, Continuation continuation) {
            super(1, continuation);
            this.this$0 = anchoredDraggableState;
            this.$block = function3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Continuation continuation) {
            return new C07352(this.this$0, this.$block, continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            return ((C07352) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final AnchoredDraggableState<Object> anchoredDraggableState = this.this$0;
                Function0 function0 = new Function0() { // from class: androidx.compose.material3.internal.AnchoredDraggableState.anchoredDrag.2.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return anchoredDraggableState.getAnchors();
                    }
                };
                C00292 c00292 = new C00292(this.$block, this.this$0, null);
                this.label = 1;
                if (AnchoredDraggableKt.access$restartable(function0, c00292, this) == coroutineSingletons) {
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

    /* renamed from: androidx.compose.material3.internal.AnchoredDraggableState$anchoredDrag$3, reason: invalid class name */
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

    /* renamed from: androidx.compose.material3.internal.AnchoredDraggableState$anchoredDrag$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function1 {
        final /* synthetic */ Function4 $block;
        final /* synthetic */ Object $targetValue;
        int label;
        final /* synthetic */ AnchoredDraggableState<Object> this$0;

        /* renamed from: androidx.compose.material3.internal.AnchoredDraggableState$anchoredDrag$4$2, reason: invalid class name */
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
                anchoredDraggableState.setDragTarget(obj2);
                final AnchoredDraggableState<Object> anchoredDraggableState2 = this.this$0;
                Function0 function0 = new Function0() { // from class: androidx.compose.material3.internal.AnchoredDraggableState.anchoredDrag.4.1
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
            return Unit.INSTANCE;
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
                boolean zIsNaN = Float.isNaN(floatValue);
                MutableState mutableState = anchoredDraggableState.currentValue$delegate;
                return !zIsNaN ? anchoredDraggableState.computeTarget(floatValue, 0.0f, ((SnapshotMutableStateImpl) mutableState).getValue()) : ((SnapshotMutableStateImpl) mutableState).getValue();
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
                boolean zIsNaN = Float.isNaN(floatValue);
                MutableState mutableState = anchoredDraggableState.currentValue$delegate;
                if (zIsNaN) {
                    return ((SnapshotMutableStateImpl) mutableState).getValue();
                }
                Object value2 = ((SnapshotMutableStateImpl) mutableState).getValue();
                MapDraggableAnchors mapDraggableAnchors = (MapDraggableAnchors) anchoredDraggableState.getAnchors();
                float fPositionOf = mapDraggableAnchors.positionOf(value2);
                if (fPositionOf != floatValue && !Float.isNaN(fPositionOf)) {
                    if (fPositionOf < floatValue) {
                        Object objClosestAnchor = mapDraggableAnchors.closestAnchor(floatValue, true);
                        if (objClosestAnchor != null) {
                            return objClosestAnchor;
                        }
                    } else {
                        Object objClosestAnchor2 = mapDraggableAnchors.closestAnchor(floatValue, false);
                        if (objClosestAnchor2 != null) {
                            return objClosestAnchor2;
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
                float fPositionOf = ((MapDraggableAnchors) this.this$0.getAnchors()).positionOf(((SnapshotMutableStateImpl) this.this$0.currentValue$delegate).getValue());
                float fPositionOf2 = ((MapDraggableAnchors) this.this$0.getAnchors()).positionOf(this.this$0.closestValue$delegate.getValue()) - fPositionOf;
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
        this.anchors$delegate = SnapshotStateKt.mutableStateOf$default(new MapDraggableAnchors(MapsKt__MapsKt.emptyMap()));
        this.anchoredDragScope = new AnchoredDraggableState$anchoredDragScope$1(this);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object anchoredDrag(MutatePriority mutatePriority, Function3 function3, ContinuationImpl continuationImpl) {
        C07341 c07341;
        SnapshotMutableFloatStateImpl snapshotMutableFloatStateImpl;
        Object objClosestAnchor;
        if (continuationImpl instanceof C07341) {
            c07341 = (C07341) continuationImpl;
            int i = c07341.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c07341.label = i - Integer.MIN_VALUE;
            } else {
                c07341 = new C07341(this, continuationImpl);
            }
        }
        Object obj = c07341.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c07341.label;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                InternalMutatorMutex internalMutatorMutex = this.dragMutex;
                C07352 c07352 = new C07352(this, function3, null);
                c07341.L$0 = this;
                c07341.label = 1;
                internalMutatorMutex.getClass();
                if (CoroutineScopeKt.coroutineScope(new InternalMutatorMutex$mutate$2(mutatePriority, internalMutatorMutex, c07352, null), c07341) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                this = (AnchoredDraggableState) c07341.L$0;
                ResultKt.throwOnFailure(obj);
            }
            if (objClosestAnchor != null && Math.abs(snapshotMutableFloatStateImpl.getFloatValue() - ((MapDraggableAnchors) this.getAnchors()).positionOf(objClosestAnchor)) <= 0.5f && ((Boolean) this.confirmValueChange.mo781invoke(objClosestAnchor)).booleanValue()) {
                this.setCurrentValue(objClosestAnchor);
            }
            return Unit.INSTANCE;
        } finally {
            DraggableAnchors anchors = this.getAnchors();
            snapshotMutableFloatStateImpl = (SnapshotMutableFloatStateImpl) this.offset$delegate;
            objClosestAnchor = ((MapDraggableAnchors) anchors).closestAnchor(snapshotMutableFloatStateImpl.getFloatValue());
            if (objClosestAnchor != null && Math.abs(snapshotMutableFloatStateImpl.getFloatValue() - ((MapDraggableAnchors) this.getAnchors()).positionOf(objClosestAnchor)) <= 0.5f && ((Boolean) this.confirmValueChange.mo781invoke(objClosestAnchor)).booleanValue()) {
                this.setCurrentValue(objClosestAnchor);
            }
        }
    }

    public final Object computeTarget(float f, float f2, Object obj) {
        MapDraggableAnchors mapDraggableAnchors = (MapDraggableAnchors) getAnchors();
        float fPositionOf = mapDraggableAnchors.positionOf(obj);
        float fFloatValue = ((Number) this.velocityThreshold.invoke()).floatValue();
        if (fPositionOf != f && !Float.isNaN(fPositionOf)) {
            Function1 function1 = this.positionalThreshold;
            if (fPositionOf < f) {
                if (f2 >= fFloatValue) {
                    Object objClosestAnchor = mapDraggableAnchors.closestAnchor(f, true);
                    objClosestAnchor.getClass();
                    return objClosestAnchor;
                }
                Object objClosestAnchor2 = mapDraggableAnchors.closestAnchor(f, true);
                objClosestAnchor2.getClass();
                if (f >= Math.abs(Math.abs(((Number) function1.mo781invoke(Float.valueOf(Math.abs(mapDraggableAnchors.positionOf(objClosestAnchor2) - fPositionOf)))).floatValue()) + fPositionOf)) {
                    return objClosestAnchor2;
                }
            } else {
                if (f2 <= (-fFloatValue)) {
                    Object objClosestAnchor3 = mapDraggableAnchors.closestAnchor(f, false);
                    objClosestAnchor3.getClass();
                    return objClosestAnchor3;
                }
                Object objClosestAnchor4 = mapDraggableAnchors.closestAnchor(f, false);
                objClosestAnchor4.getClass();
                float fAbs = Math.abs(fPositionOf - Math.abs(((Number) function1.mo781invoke(Float.valueOf(Math.abs(fPositionOf - mapDraggableAnchors.positionOf(objClosestAnchor4))))).floatValue()));
                if (f >= 0.0f ? f <= fAbs : Math.abs(f) >= fAbs) {
                    return objClosestAnchor4;
                }
            }
        }
        return obj;
    }

    public final float dispatchRawDelta(float f) {
        float fNewOffsetForDelta$material3_release = newOffsetForDelta$material3_release(f);
        MutableFloatState mutableFloatState = this.offset$delegate;
        SnapshotMutableFloatStateImpl snapshotMutableFloatStateImpl = (SnapshotMutableFloatStateImpl) mutableFloatState;
        float floatValue = Float.isNaN(snapshotMutableFloatStateImpl.getFloatValue()) ? 0.0f : snapshotMutableFloatStateImpl.getFloatValue();
        ((SnapshotMutableFloatStateImpl) mutableFloatState).setFloatValue(fNewOffsetForDelta$material3_release);
        return fNewOffsetForDelta$material3_release - floatValue;
    }

    public final DraggableAnchors getAnchors() {
        return (DraggableAnchors) ((SnapshotMutableStateImpl) this.anchors$delegate).getValue();
    }

    public final float newOffsetForDelta$material3_release(float f) {
        MutableFloatState mutableFloatState = this.offset$delegate;
        float floatValue = (Float.isNaN(((SnapshotMutableFloatStateImpl) mutableFloatState).getFloatValue()) ? 0.0f : ((SnapshotMutableFloatStateImpl) mutableFloatState).getFloatValue()) + f;
        float fMinAnchor = ((MapDraggableAnchors) getAnchors()).minAnchor();
        Float fM3449maxOrNull = CollectionsKt___CollectionsKt.m3449maxOrNull((Iterable) ((MapDraggableAnchors) getAnchors()).anchors.values());
        return RangesKt___RangesKt.coerceIn(floatValue, fMinAnchor, fM3449maxOrNull != null ? fM3449maxOrNull.floatValue() : Float.NaN);
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
        Function0 function0 = new Function0(this) { // from class: androidx.compose.material3.internal.AnchoredDraggableState.trySnapTo.1
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
                float fPositionOf = ((MapDraggableAnchors) anchoredDraggableState.getAnchors()).positionOf(obj2);
                if (!Float.isNaN(fPositionOf)) {
                    anchoredDraggableState$anchoredDragScope$1.getClass();
                    int i = AnchoredDraggableState.$r8$clinit;
                    AnchoredDraggableState anchoredDraggableState2 = anchoredDraggableState$anchoredDragScope$1.this$0;
                    ((SnapshotMutableFloatStateImpl) anchoredDraggableState2.offset$delegate).setFloatValue(fPositionOf);
                    ((SnapshotMutableFloatStateImpl) anchoredDraggableState2.lastVelocity$delegate).setFloatValue(0.0f);
                    anchoredDraggableState.setDragTarget(null);
                }
                anchoredDraggableState.setCurrentValue(obj2);
                return Unit.INSTANCE;
            }
        };
        MutexImpl mutexImpl = this.dragMutex.mutex;
        boolean zTryLock = mutexImpl.tryLock();
        if (!zTryLock) {
            return zTryLock;
        }
        try {
            function0.invoke();
            return zTryLock;
        } finally {
            mutexImpl.unlock(null);
        }
    }

    public /* synthetic */ AnchoredDraggableState(Object obj, Function1 function1, Function0 function0, Function0 function02, Function1 function12, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(obj, function1, function0, function02, (i & 16) != 0 ? new Function1() { // from class: androidx.compose.material3.internal.AnchoredDraggableState.1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj2) {
                return Boolean.TRUE;
            }
        } : function12);
    }

    public /* synthetic */ AnchoredDraggableState(Object obj, DraggableAnchors draggableAnchors, Function1 function1, Function0 function0, Function0 function02, Function1 function12, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(obj, draggableAnchors, function1, function0, function02, (i & 32) != 0 ? new Function1() { // from class: androidx.compose.material3.internal.AnchoredDraggableState.2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj2) {
                return Boolean.TRUE;
            }
        } : function12);
    }

    public AnchoredDraggableState(T t, DraggableAnchors<T> draggableAnchors, Function1 function1, Function0 function0, Function0 function02, Function1 function12) {
        this(t, function1, function0, function02, function12);
        ((SnapshotMutableStateImpl) this.anchors$delegate).setValue(draggableAnchors);
        trySnapTo(t);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object anchoredDrag(Object obj, MutatePriority mutatePriority, Function4 function4, ContinuationImpl continuationImpl) {
        AnonymousClass3 anonymousClass3;
        SnapshotMutableFloatStateImpl snapshotMutableFloatStateImpl;
        Object objClosestAnchor;
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
                if (((MapDraggableAnchors) getAnchors()).anchors.containsKey(obj)) {
                    InternalMutatorMutex internalMutatorMutex = this.dragMutex;
                    AnonymousClass4 anonymousClass4 = new AnonymousClass4(this, obj, function4, null);
                    anonymousClass3.L$0 = this;
                    anonymousClass3.label = 1;
                    internalMutatorMutex.getClass();
                    if (CoroutineScopeKt.coroutineScope(new InternalMutatorMutex$mutate$2(mutatePriority, internalMutatorMutex, anonymousClass4, null), anonymousClass3) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    setCurrentValue(obj);
                    return Unit.INSTANCE;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                this = (AnchoredDraggableState) anonymousClass3.L$0;
                ResultKt.throwOnFailure(obj2);
            }
            if (objClosestAnchor != null && Math.abs(snapshotMutableFloatStateImpl.getFloatValue() - ((MapDraggableAnchors) this.getAnchors()).positionOf(objClosestAnchor)) <= 0.5f && ((Boolean) this.confirmValueChange.mo781invoke(objClosestAnchor)).booleanValue()) {
                this.setCurrentValue(objClosestAnchor);
            }
            return Unit.INSTANCE;
        } finally {
            this.setDragTarget(null);
            DraggableAnchors anchors = this.getAnchors();
            snapshotMutableFloatStateImpl = (SnapshotMutableFloatStateImpl) this.offset$delegate;
            objClosestAnchor = ((MapDraggableAnchors) anchors).closestAnchor(snapshotMutableFloatStateImpl.getFloatValue());
            if (objClosestAnchor != null && Math.abs(snapshotMutableFloatStateImpl.getFloatValue() - ((MapDraggableAnchors) this.getAnchors()).positionOf(objClosestAnchor)) <= 0.5f && ((Boolean) this.confirmValueChange.mo781invoke(objClosestAnchor)).booleanValue()) {
                this.setCurrentValue(objClosestAnchor);
            }
        }
    }
}
