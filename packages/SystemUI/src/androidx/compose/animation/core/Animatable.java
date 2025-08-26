package androidx.compose.animation.core;

import androidx.compose.animation.core.AnimationVector;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import com.android.systemui.samsung.quicksetting.ui.tiles.QuickTileDrawerKt$ExpandButton$2$1$1$$ExternalSyntheticLambda0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes.dex */
public final class Animatable<T, V extends AnimationVector> {
    public final SpringSpec defaultSpringSpec;
    public final AnimationState internalState;
    public final MutableState isRunning$delegate;
    public Object lowerBound;
    public AnimationVector lowerBoundVector;
    public final MutatorMutex mutatorMutex;
    public final AnimationVector negativeInfinityBounds;
    public final AnimationVector positiveInfinityBounds;
    public final MutableState targetValue$delegate;
    public final TwoWayConverter typeConverter;
    public Object upperBound;
    public AnimationVector upperBoundVector;
    public final Object visibilityThreshold;

    /* renamed from: androidx.compose.animation.core.Animatable$snapTo$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function1 {
        final /* synthetic */ Object $targetValue;
        int label;
        final /* synthetic */ Animatable<Object, AnimationVector> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Animatable<Object, AnimationVector> animatable, Object obj, Continuation continuation) {
            super(1, continuation);
            this.this$0 = animatable;
            this.$targetValue = obj;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Continuation continuation) {
            return new AnonymousClass2(this.this$0, this.$targetValue, continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            return ((AnonymousClass2) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Animatable.access$endAnimation(this.this$0);
            Object objClampToBounds = this.this$0.clampToBounds(this.$targetValue);
            ((SnapshotMutableStateImpl) this.this$0.internalState.value$delegate).setValue(objClampToBounds);
            ((SnapshotMutableStateImpl) this.this$0.targetValue$delegate).setValue(objClampToBounds);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: androidx.compose.animation.core.Animatable$stop$2, reason: invalid class name and case insensitive filesystem */
    final class C06802 extends SuspendLambda implements Function1 {
        int label;
        final /* synthetic */ Animatable<Object, AnimationVector> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C06802(Animatable<Object, AnimationVector> animatable, Continuation continuation) {
            super(1, continuation);
            this.this$0 = animatable;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Continuation continuation) {
            return new C06802(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            return ((C06802) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Animatable.access$endAnimation(this.this$0);
            return Unit.INSTANCE;
        }
    }

    public Animatable(T t, TwoWayConverter<T, V> twoWayConverter, T t2, String str) {
        this.typeConverter = twoWayConverter;
        this.visibilityThreshold = t2;
        AnimationState animationState = new AnimationState(twoWayConverter, t, null, 0L, 0L, false, 60, null);
        this.internalState = animationState;
        this.isRunning$delegate = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
        this.targetValue$delegate = SnapshotStateKt.mutableStateOf$default(t);
        this.mutatorMutex = new MutatorMutex();
        this.defaultSpringSpec = new SpringSpec(0.0f, 0.0f, t2, 3, null);
        AnimationVector animationVector = animationState.velocityVector;
        boolean z = animationVector instanceof AnimationVector1D;
        AnimationVector animationVector2 = z ? AnimatableKt.negativeInfinityBounds1D : animationVector instanceof AnimationVector2D ? AnimatableKt.negativeInfinityBounds2D : animationVector instanceof AnimationVector3D ? AnimatableKt.negativeInfinityBounds3D : AnimatableKt.negativeInfinityBounds4D;
        this.negativeInfinityBounds = animationVector2;
        AnimationVector animationVector3 = z ? AnimatableKt.positiveInfinityBounds1D : animationVector instanceof AnimationVector2D ? AnimatableKt.positiveInfinityBounds2D : animationVector instanceof AnimationVector3D ? AnimatableKt.positiveInfinityBounds3D : AnimatableKt.positiveInfinityBounds4D;
        this.positiveInfinityBounds = animationVector3;
        this.lowerBoundVector = animationVector2;
        this.upperBoundVector = animationVector3;
    }

    public static final void access$endAnimation(Animatable animatable) {
        AnimationState animationState = animatable.internalState;
        animationState.velocityVector.reset$animation_core();
        animationState.lastFrameTimeNanos = Long.MIN_VALUE;
        ((SnapshotMutableStateImpl) animatable.isRunning$delegate).setValue(Boolean.FALSE);
    }

    public static Object animateTo$default(Animatable animatable, Object obj, AnimationSpec animationSpec, Object obj2, Function1 function1, Continuation continuation, int i) {
        AnimationSpec animationSpec2 = (i & 2) != 0 ? animatable.defaultSpringSpec : animationSpec;
        Object velocity = (i & 4) != 0 ? animatable.getVelocity() : obj2;
        Function1 function12 = (i & 8) != 0 ? null : function1;
        Object value = animatable.internalState.getValue();
        TwoWayConverter twoWayConverter = animatable.typeConverter;
        return MutatorMutex.mutate$default(animatable.mutatorMutex, new Animatable$runAnimation$2(animatable, velocity, new TargetBasedAnimation((AnimationSpec<Object>) animationSpec2, (TwoWayConverter<Object, AnimationVector>) twoWayConverter, value, obj, (AnimationVector) ((TwoWayConverterImpl) twoWayConverter).convertToVector.mo781invoke(velocity)), animatable.internalState.lastFrameTimeNanos, function12, null), continuation);
    }

    public final Object animateDecay(Object obj, DecayAnimationSpec decayAnimationSpec, QuickTileDrawerKt$ExpandButton$2$1$1$$ExternalSyntheticLambda0 quickTileDrawerKt$ExpandButton$2$1$1$$ExternalSyntheticLambda0, ContinuationImpl continuationImpl) {
        AnimationState animationState = this.internalState;
        Object value = animationState.getValue();
        TwoWayConverter twoWayConverter = this.typeConverter;
        return MutatorMutex.mutate$default(this.mutatorMutex, new Animatable$runAnimation$2(this, obj, new DecayAnimation((DecayAnimationSpec<Object>) decayAnimationSpec, (TwoWayConverter<Object, AnimationVector>) twoWayConverter, value, (AnimationVector) ((TwoWayConverterImpl) twoWayConverter).convertToVector.mo781invoke(obj)), animationState.lastFrameTimeNanos, quickTileDrawerKt$ExpandButton$2$1$1$$ExternalSyntheticLambda0, null), continuationImpl);
    }

    public final Object clampToBounds(Object obj) {
        if (!Intrinsics.areEqual(this.lowerBoundVector, this.negativeInfinityBounds) || !Intrinsics.areEqual(this.upperBoundVector, this.positiveInfinityBounds)) {
            TwoWayConverterImpl twoWayConverterImpl = (TwoWayConverterImpl) this.typeConverter;
            AnimationVector animationVector = (AnimationVector) twoWayConverterImpl.convertToVector.mo781invoke(obj);
            int size$animation_core = animationVector.getSize$animation_core();
            boolean z = false;
            for (int i = 0; i < size$animation_core; i++) {
                if (animationVector.get$animation_core(i) < this.lowerBoundVector.get$animation_core(i) || animationVector.get$animation_core(i) > this.upperBoundVector.get$animation_core(i)) {
                    animationVector.set$animation_core(RangesKt___RangesKt.coerceIn(animationVector.get$animation_core(i), this.lowerBoundVector.get$animation_core(i), this.upperBoundVector.get$animation_core(i)), i);
                    z = true;
                }
            }
            if (z) {
                return twoWayConverterImpl.convertFromVector.mo781invoke(animationVector);
            }
        }
        return obj;
    }

    public final Object getVelocity() {
        return ((TwoWayConverterImpl) this.typeConverter).convertFromVector.mo781invoke(this.internalState.velocityVector);
    }

    public final boolean isRunning() {
        return ((Boolean) ((SnapshotMutableStateImpl) this.isRunning$delegate).getValue()).booleanValue();
    }

    public final Object snapTo(Object obj, Continuation continuation) {
        Object objMutate$default = MutatorMutex.mutate$default(this.mutatorMutex, new AnonymousClass2(this, obj, null), continuation);
        return objMutate$default == CoroutineSingletons.COROUTINE_SUSPENDED ? objMutate$default : Unit.INSTANCE;
    }

    public final Object stop(SuspendLambda suspendLambda) {
        Object objMutate$default = MutatorMutex.mutate$default(this.mutatorMutex, new C06802(this, null), suspendLambda);
        return objMutate$default == CoroutineSingletons.COROUTINE_SUSPENDED ? objMutate$default : Unit.INSTANCE;
    }

    public final void updateBounds(Object obj, Object obj2) {
        TwoWayConverter twoWayConverter = this.typeConverter;
        AnimationVector animationVector = (AnimationVector) ((TwoWayConverterImpl) twoWayConverter).convertToVector.mo781invoke(obj);
        if (animationVector == null) {
            animationVector = this.negativeInfinityBounds;
        }
        AnimationVector animationVector2 = (AnimationVector) ((TwoWayConverterImpl) twoWayConverter).convertToVector.mo781invoke(obj2);
        if (animationVector2 == null) {
            animationVector2 = this.positiveInfinityBounds;
        }
        int size$animation_core = animationVector.getSize$animation_core();
        for (int i = 0; i < size$animation_core; i++) {
            if (animationVector.get$animation_core(i) > animationVector2.get$animation_core(i)) {
                PreconditionsKt.throwIllegalStateException("Lower bound must be no greater than upper bound on *all* dimensions. The provided lower bound: " + animationVector + " is greater than upper bound " + animationVector2 + " on index " + i);
            }
        }
        this.lowerBoundVector = animationVector;
        this.upperBoundVector = animationVector2;
        this.upperBound = obj2;
        this.lowerBound = obj;
        if (isRunning()) {
            return;
        }
        AnimationState animationState = this.internalState;
        Object objClampToBounds = clampToBounds(animationState.getValue());
        if (Intrinsics.areEqual(objClampToBounds, animationState.getValue())) {
            return;
        }
        ((SnapshotMutableStateImpl) animationState.value$delegate).setValue(objClampToBounds);
    }

    public /* synthetic */ Animatable(Object obj, TwoWayConverter twoWayConverter, Object obj2, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(obj, twoWayConverter, (i & 4) != 0 ? null : obj2, (i & 8) != 0 ? "Animatable" : str);
    }

    public /* synthetic */ Animatable(Object obj, TwoWayConverter twoWayConverter, Object obj2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(obj, twoWayConverter, (i & 4) != 0 ? null : obj2);
    }

    public /* synthetic */ Animatable(Object obj, TwoWayConverter twoWayConverter, Object obj2) {
        this(obj, twoWayConverter, obj2, "Animatable");
    }
}
