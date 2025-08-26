package androidx.compose.foundation.lazy.layout;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
final class LazyLayoutItemAnimation$animateDisappearance$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ GraphicsLayer $layer;
    final /* synthetic */ FiniteAnimationSpec<Float> $spec;
    int label;
    final /* synthetic */ LazyLayoutItemAnimation this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LazyLayoutItemAnimation$animateDisappearance$1(LazyLayoutItemAnimation lazyLayoutItemAnimation, FiniteAnimationSpec<Float> finiteAnimationSpec, GraphicsLayer graphicsLayer, Continuation continuation) {
        super(2, continuation);
        this.this$0 = lazyLayoutItemAnimation;
        this.$spec = finiteAnimationSpec;
        this.$layer = graphicsLayer;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new LazyLayoutItemAnimation$animateDisappearance$1(this.this$0, this.$spec, this.$layer, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LazyLayoutItemAnimation$animateDisappearance$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        LazyLayoutItemAnimation$animateDisappearance$1 lazyLayoutItemAnimation$animateDisappearance$1;
        Animatable animatable;
        Throwable th;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            try {
                animatable = this.this$0.visibilityAnimation;
                try {
                } catch (Throwable th2) {
                    th = th2;
                    lazyLayoutItemAnimation$animateDisappearance$1 = this;
                }
            } catch (Throwable th3) {
                th = th3;
                lazyLayoutItemAnimation$animateDisappearance$1 = this;
                th = th;
                LazyLayoutItemAnimation lazyLayoutItemAnimation = lazyLayoutItemAnimation$animateDisappearance$1.this$0;
                LazyLayoutItemAnimation.Companion companion = LazyLayoutItemAnimation.Companion;
                lazyLayoutItemAnimation.setDisappearanceAnimationInProgress(false);
                throw th;
            }
            try {
                Float f = new Float(0.0f);
                FiniteAnimationSpec<Float> finiteAnimationSpec = this.$spec;
                final GraphicsLayer graphicsLayer = this.$layer;
                final LazyLayoutItemAnimation lazyLayoutItemAnimation2 = this.this$0;
                Function1 function1 = new Function1() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation$animateDisappearance$1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        graphicsLayer.setAlpha(((Number) ((Animatable) obj2).internalState.getValue()).floatValue());
                        lazyLayoutItemAnimation2.onLayerPropertyChanged.invoke();
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                lazyLayoutItemAnimation$animateDisappearance$1 = this;
                try {
                    if (Animatable.animateTo$default(animatable, f, finiteAnimationSpec, null, function1, lazyLayoutItemAnimation$animateDisappearance$1, 4) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    th = th;
                    LazyLayoutItemAnimation lazyLayoutItemAnimation3 = lazyLayoutItemAnimation$animateDisappearance$1.this$0;
                    LazyLayoutItemAnimation.Companion companion2 = LazyLayoutItemAnimation.Companion;
                    lazyLayoutItemAnimation3.setDisappearanceAnimationInProgress(false);
                    throw th;
                }
            } catch (Throwable th5) {
                th = th5;
                lazyLayoutItemAnimation$animateDisappearance$1 = this;
                th = th;
                LazyLayoutItemAnimation lazyLayoutItemAnimation32 = lazyLayoutItemAnimation$animateDisappearance$1.this$0;
                LazyLayoutItemAnimation.Companion companion22 = LazyLayoutItemAnimation.Companion;
                lazyLayoutItemAnimation32.setDisappearanceAnimationInProgress(false);
                throw th;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            try {
                ResultKt.throwOnFailure(obj);
                lazyLayoutItemAnimation$animateDisappearance$1 = this;
            } catch (Throwable th6) {
                th = th6;
                lazyLayoutItemAnimation$animateDisappearance$1 = this;
                LazyLayoutItemAnimation lazyLayoutItemAnimation322 = lazyLayoutItemAnimation$animateDisappearance$1.this$0;
                LazyLayoutItemAnimation.Companion companion222 = LazyLayoutItemAnimation.Companion;
                lazyLayoutItemAnimation322.setDisappearanceAnimationInProgress(false);
                throw th;
            }
        }
        try {
            try {
                ((SnapshotMutableStateImpl) lazyLayoutItemAnimation$animateDisappearance$1.this$0.isDisappearanceAnimationFinished$delegate).setValue(Boolean.TRUE);
                lazyLayoutItemAnimation$animateDisappearance$1.this$0.setDisappearanceAnimationInProgress(false);
                return Unit.INSTANCE;
            } catch (Throwable th7) {
                th = th7;
                th = th;
                LazyLayoutItemAnimation lazyLayoutItemAnimation3222 = lazyLayoutItemAnimation$animateDisappearance$1.this$0;
                LazyLayoutItemAnimation.Companion companion2222 = LazyLayoutItemAnimation.Companion;
                lazyLayoutItemAnimation3222.setDisappearanceAnimationInProgress(false);
                throw th;
            }
        } catch (Throwable th8) {
            th = th8;
        }
    }
}
