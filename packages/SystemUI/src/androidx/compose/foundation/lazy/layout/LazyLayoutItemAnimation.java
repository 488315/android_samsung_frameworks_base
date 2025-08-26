package androidx.compose.foundation.lazy.layout;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.animation.core.VectorConvertersKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.graphics.GraphicsContext;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.unit.IntOffset;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FloatCompanionObject;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
public final class LazyLayoutItemAnimation {
    public static final Companion Companion = new Companion(null);
    public static final long NotInitialized;
    public final CoroutineScope coroutineScope;
    public FiniteAnimationSpec fadeInSpec;
    public FiniteAnimationSpec fadeOutSpec;
    public long finalOffset;
    public final GraphicsContext graphicsContext;
    public final MutableState isAppearanceAnimationInProgress$delegate;
    public final MutableState isDisappearanceAnimationFinished$delegate;
    public final MutableState isDisappearanceAnimationInProgress$delegate;
    public final MutableState isPlacementAnimationInProgress$delegate;
    public boolean isRunningMovingAwayAnimation;
    public GraphicsLayer layer;
    public long lookaheadOffset;
    public final Function0 onLayerPropertyChanged;
    public final MutableState placementDelta$delegate;
    public final Animatable placementDeltaAnimation;
    public FiniteAnimationSpec placementSpec;
    public long rawOffset;
    public final Animatable visibilityAnimation;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation$animateAppearance$1, reason: invalid class name and case insensitive filesystem */
    final class C07101 extends SuspendLambda implements Function2 {
        int label;

        public C07101(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LazyLayoutItemAnimation.this.new C07101(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C07101) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Animatable animatable = LazyLayoutItemAnimation.this.visibilityAnimation;
                Float f = new Float(1.0f);
                this.label = 1;
                if (animatable.snapTo(f, this) == coroutineSingletons) {
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

    /* renamed from: androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation$animateAppearance$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ GraphicsLayer $layer;
        final /* synthetic */ boolean $shouldResetValue;
        final /* synthetic */ FiniteAnimationSpec<Float> $spec;
        int label;
        final /* synthetic */ LazyLayoutItemAnimation this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(boolean z, LazyLayoutItemAnimation lazyLayoutItemAnimation, FiniteAnimationSpec<Float> finiteAnimationSpec, GraphicsLayer graphicsLayer, Continuation continuation) {
            super(2, continuation);
            this.$shouldResetValue = z;
            this.this$0 = lazyLayoutItemAnimation;
            this.$spec = finiteAnimationSpec;
            this.$layer = graphicsLayer;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$shouldResetValue, this.this$0, this.$spec, this.$layer, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Can't wrap try/catch for region: R(12:0|2|(1:40)|(1:(1:(4:6|7|27|28)(2:10|11))(1:12))(6:14|43|15|(3:17|(0)|26)|36|37)|20|38|21|22|23|41|24|(1:(0))) */
        /* JADX WARN: Can't wrap try/catch for region: R(12:0|2|40|(1:(1:(4:6|7|27|28)(2:10|11))(1:12))(6:14|43|15|(3:17|(0)|26)|36|37)|20|38|21|22|23|41|24|(1:(0))) */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x005b, code lost:
        
            if (androidx.compose.animation.core.Animatable.animateTo$default(r4, r5, r6, null, r8, r9, 4) == r0) goto L26;
         */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x0068, code lost:
        
            r0 = th;
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x006e, code lost:
        
            r12 = r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:34:0x0070, code lost:
        
            r0 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x0071, code lost:
        
            r9 = r11;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) throws Throwable {
            Throwable th;
            AnonymousClass2 anonymousClass2;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            try {
            } catch (Throwable th2) {
                th = th2;
                anonymousClass2 = this;
            }
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                try {
                    if (this.$shouldResetValue) {
                        Animatable animatable = this.this$0.visibilityAnimation;
                        Float f = new Float(0.0f);
                        this.label = 1;
                        if (animatable.snapTo(f, this) != coroutineSingletons) {
                        }
                        return coroutineSingletons;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    anonymousClass2 = this;
                    th = th;
                    LazyLayoutItemAnimation lazyLayoutItemAnimation = anonymousClass2.this$0;
                    Companion companion = LazyLayoutItemAnimation.Companion;
                    lazyLayoutItemAnimation.setAppearanceAnimationInProgress(false);
                    throw th;
                }
                LazyLayoutItemAnimation lazyLayoutItemAnimation2 = anonymousClass2.this$0;
                Companion companion2 = LazyLayoutItemAnimation.Companion;
                lazyLayoutItemAnimation2.setAppearanceAnimationInProgress(false);
                throw th;
            }
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                anonymousClass2 = this;
                LazyLayoutItemAnimation lazyLayoutItemAnimation3 = anonymousClass2.this$0;
                Companion companion3 = LazyLayoutItemAnimation.Companion;
                lazyLayoutItemAnimation3.setAppearanceAnimationInProgress(false);
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            Animatable animatable2 = this.this$0.visibilityAnimation;
            Float f2 = new Float(1.0f);
            FiniteAnimationSpec<Float> finiteAnimationSpec = this.$spec;
            final GraphicsLayer graphicsLayer = this.$layer;
            final LazyLayoutItemAnimation lazyLayoutItemAnimation4 = this.this$0;
            Function1 function1 = new Function1() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation.animateAppearance.2.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    graphicsLayer.setAlpha(((Number) ((Animatable) obj2).internalState.getValue()).floatValue());
                    lazyLayoutItemAnimation4.onLayerPropertyChanged.invoke();
                    return Unit.INSTANCE;
                }
            };
            this.label = 2;
            anonymousClass2 = this;
        }
    }

    /* renamed from: androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation$cancelPlacementAnimation$1, reason: invalid class name and case insensitive filesystem */
    final class C07111 extends SuspendLambda implements Function2 {
        int label;

        public C07111(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LazyLayoutItemAnimation.this.new C07111(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C07111) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Animatable animatable = LazyLayoutItemAnimation.this.placementDeltaAnimation;
                IntOffset.Companion.getClass();
                IntOffset intOffsetM849boximpl = IntOffset.m849boximpl(0L);
                this.label = 1;
                if (animatable.snapTo(intOffsetM849boximpl, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            LazyLayoutItemAnimation lazyLayoutItemAnimation = LazyLayoutItemAnimation.this;
            IntOffset.Companion.getClass();
            Companion companion = LazyLayoutItemAnimation.Companion;
            lazyLayoutItemAnimation.m167setPlacementDeltagyyYBs(0L);
            LazyLayoutItemAnimation.this.setPlacementAnimationInProgress(false);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation$release$1, reason: invalid class name and case insensitive filesystem */
    final class C07121 extends SuspendLambda implements Function2 {
        int label;

        public C07121(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LazyLayoutItemAnimation.this.new C07121(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C07121) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Animatable animatable = LazyLayoutItemAnimation.this.placementDeltaAnimation;
                this.label = 1;
                if (animatable.stop(this) == coroutineSingletons) {
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

    /* renamed from: androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation$release$2, reason: invalid class name and case insensitive filesystem */
    final class C07132 extends SuspendLambda implements Function2 {
        int label;

        public C07132(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LazyLayoutItemAnimation.this.new C07132(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C07132) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Animatable animatable = LazyLayoutItemAnimation.this.visibilityAnimation;
                this.label = 1;
                if (animatable.stop(this) == coroutineSingletons) {
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

    /* renamed from: androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation$release$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass3(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LazyLayoutItemAnimation.this.new AnonymousClass3(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Animatable animatable = LazyLayoutItemAnimation.this.visibilityAnimation;
                this.label = 1;
                if (animatable.stop(this) == coroutineSingletons) {
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
        long j = Integer.MAX_VALUE;
        IntOffset.Companion companion = IntOffset.Companion;
        NotInitialized = (j & 4294967295L) | (j << 32);
    }

    public LazyLayoutItemAnimation(CoroutineScope coroutineScope, GraphicsContext graphicsContext, Function0 function0) {
        this.coroutineScope = coroutineScope;
        this.graphicsContext = graphicsContext;
        this.onLayerPropertyChanged = function0;
        Boolean bool = Boolean.FALSE;
        this.isPlacementAnimationInProgress$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.isAppearanceAnimationInProgress$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.isDisappearanceAnimationInProgress$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.isDisappearanceAnimationFinished$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        long j = NotInitialized;
        this.rawOffset = j;
        IntOffset.Companion.getClass();
        this.finalOffset = 0L;
        this.layer = graphicsContext != null ? graphicsContext.createGraphicsLayer() : null;
        this.placementDeltaAnimation = new Animatable(IntOffset.m849boximpl(0L), VectorConvertersKt.IntOffsetToVector, null, null, 12, null);
        Float fValueOf = Float.valueOf(1.0f);
        FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
        this.visibilityAnimation = new Animatable(fValueOf, VectorConvertersKt.FloatToVector, null, null, 12, null);
        this.placementDelta$delegate = SnapshotStateKt.mutableStateOf$default(IntOffset.m849boximpl(0L));
        this.lookaheadOffset = j;
    }

    public final void animateAppearance() {
        GraphicsLayer graphicsLayer = this.layer;
        FiniteAnimationSpec finiteAnimationSpec = this.fadeInSpec;
        boolean zBooleanValue = ((Boolean) ((SnapshotMutableStateImpl) this.isAppearanceAnimationInProgress$delegate).getValue()).booleanValue();
        CoroutineScope coroutineScope = this.coroutineScope;
        if (zBooleanValue || finiteAnimationSpec == null || graphicsLayer == null) {
            if (isDisappearanceAnimationInProgress()) {
                if (graphicsLayer != null) {
                    graphicsLayer.setAlpha(1.0f);
                }
                BuildersKt.launch$default(coroutineScope, null, null, new C07101(null), 3);
                return;
            }
            return;
        }
        setAppearanceAnimationInProgress(true);
        boolean zIsDisappearanceAnimationInProgress = isDisappearanceAnimationInProgress();
        boolean z = !zIsDisappearanceAnimationInProgress;
        if (!zIsDisappearanceAnimationInProgress) {
            graphicsLayer.setAlpha(0.0f);
        }
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(z, this, finiteAnimationSpec, graphicsLayer, null), 3);
    }

    public final void cancelPlacementAnimation() {
        if (((Boolean) ((SnapshotMutableStateImpl) this.isPlacementAnimationInProgress$delegate).getValue()).booleanValue()) {
            BuildersKt.launch$default(this.coroutineScope, null, null, new C07111(null), 3);
        }
    }

    public final boolean isDisappearanceAnimationInProgress() {
        return ((Boolean) ((SnapshotMutableStateImpl) this.isDisappearanceAnimationInProgress$delegate).getValue()).booleanValue();
    }

    public final void release() {
        GraphicsContext graphicsContext;
        boolean zBooleanValue = ((Boolean) ((SnapshotMutableStateImpl) this.isPlacementAnimationInProgress$delegate).getValue()).booleanValue();
        CoroutineScope coroutineScope = this.coroutineScope;
        if (zBooleanValue) {
            setPlacementAnimationInProgress(false);
            BuildersKt.launch$default(coroutineScope, null, null, new C07121(null), 3);
        }
        if (((Boolean) ((SnapshotMutableStateImpl) this.isAppearanceAnimationInProgress$delegate).getValue()).booleanValue()) {
            setAppearanceAnimationInProgress(false);
            BuildersKt.launch$default(coroutineScope, null, null, new C07132(null), 3);
        }
        if (isDisappearanceAnimationInProgress()) {
            setDisappearanceAnimationInProgress(false);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(null), 3);
        }
        this.isRunningMovingAwayAnimation = false;
        IntOffset.Companion.getClass();
        m167setPlacementDeltagyyYBs(0L);
        this.rawOffset = NotInitialized;
        GraphicsLayer graphicsLayer = this.layer;
        if (graphicsLayer != null && (graphicsContext = this.graphicsContext) != null) {
            graphicsContext.releaseGraphicsLayer(graphicsLayer);
        }
        this.layer = null;
        this.fadeInSpec = null;
        this.fadeOutSpec = null;
        this.placementSpec = null;
    }

    public final void setAppearanceAnimationInProgress(boolean z) {
        ((SnapshotMutableStateImpl) this.isAppearanceAnimationInProgress$delegate).setValue(Boolean.valueOf(z));
    }

    public final void setDisappearanceAnimationInProgress(boolean z) {
        ((SnapshotMutableStateImpl) this.isDisappearanceAnimationInProgress$delegate).setValue(Boolean.valueOf(z));
    }

    public final void setPlacementAnimationInProgress(boolean z) {
        ((SnapshotMutableStateImpl) this.isPlacementAnimationInProgress$delegate).setValue(Boolean.valueOf(z));
    }

    /* renamed from: setPlacementDelta--gyyYBs, reason: not valid java name */
    public final void m167setPlacementDeltagyyYBs(long j) {
        ((SnapshotMutableStateImpl) this.placementDelta$delegate).setValue(IntOffset.m849boximpl(j));
    }

    public /* synthetic */ LazyLayoutItemAnimation(CoroutineScope coroutineScope, GraphicsContext graphicsContext, Function0 function0, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(coroutineScope, (i & 2) != 0 ? null : graphicsContext, (i & 4) != 0 ? new Function0() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation.1
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Unit.INSTANCE;
            }
        } : function0);
    }
}
