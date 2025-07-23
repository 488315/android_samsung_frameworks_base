package androidx.compose.foundation.lazy.layout;

import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class LazyLayoutItemAnimation$animateAppearance$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ GraphicsLayer $layer;
    final /* synthetic */ boolean $shouldResetValue;
    final /* synthetic */ FiniteAnimationSpec<Float> $spec;
    int label;
    final /* synthetic */ LazyLayoutItemAnimation this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LazyLayoutItemAnimation$animateAppearance$2(boolean z, LazyLayoutItemAnimation lazyLayoutItemAnimation, FiniteAnimationSpec<Float> finiteAnimationSpec, GraphicsLayer graphicsLayer, Continuation continuation) {
        super(2, continuation);
        this.$shouldResetValue = z;
        this.this$0 = lazyLayoutItemAnimation;
        this.$spec = finiteAnimationSpec;
        this.$layer = graphicsLayer;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new LazyLayoutItemAnimation$animateAppearance$2(this.$shouldResetValue, this.this$0, this.$spec, this.$layer, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LazyLayoutItemAnimation$animateAppearance$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x005b, code lost:
    
        if (androidx.compose.animation.core.Animatable.animateTo$default(r4, r5, r6, null, r8, r9, 4) == r0) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x005d, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0039, code lost:
    
        if (r12.snapTo(r1, r11) == r0) goto L26;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r11.label
            r2 = 0
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L22
            if (r1 == r4) goto L1e
            if (r1 != r3) goto L16
            kotlin.ResultKt.throwOnFailure(r12)     // Catch: java.lang.Throwable -> L12
            r9 = r11
            goto L5e
        L12:
            r0 = move-exception
            r12 = r0
            r9 = r11
            goto L74
        L16:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L1e:
            kotlin.ResultKt.throwOnFailure(r12)     // Catch: java.lang.Throwable -> L12
            goto L3c
        L22:
            kotlin.ResultKt.throwOnFailure(r12)
            boolean r12 = r11.$shouldResetValue     // Catch: java.lang.Throwable -> L6b
            if (r12 == 0) goto L3c
            androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation r12 = r11.this$0     // Catch: java.lang.Throwable -> L12
            androidx.compose.animation.core.Animatable r12 = r12.visibilityAnimation     // Catch: java.lang.Throwable -> L12
            java.lang.Float r1 = new java.lang.Float     // Catch: java.lang.Throwable -> L12
            r5 = 0
            r1.<init>(r5)     // Catch: java.lang.Throwable -> L12
            r11.label = r4     // Catch: java.lang.Throwable -> L12
            java.lang.Object r12 = r12.snapTo(r1, r11)     // Catch: java.lang.Throwable -> L12
            if (r12 != r0) goto L3c
            goto L5d
        L3c:
            androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation r12 = r11.this$0     // Catch: java.lang.Throwable -> L6b
            androidx.compose.animation.core.Animatable r4 = r12.visibilityAnimation     // Catch: java.lang.Throwable -> L6b
            java.lang.Float r5 = new java.lang.Float     // Catch: java.lang.Throwable -> L70
            r12 = 1065353216(0x3f800000, float:1.0)
            r5.<init>(r12)     // Catch: java.lang.Throwable -> L70
            androidx.compose.animation.core.FiniteAnimationSpec<java.lang.Float> r6 = r11.$spec     // Catch: java.lang.Throwable -> L6b
            androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation$animateAppearance$2$1 r8 = new androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation$animateAppearance$2$1     // Catch: java.lang.Throwable -> L6b
            androidx.compose.ui.graphics.layer.GraphicsLayer r12 = r11.$layer     // Catch: java.lang.Throwable -> L6b
            androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation r1 = r11.this$0     // Catch: java.lang.Throwable -> L6b
            r8.<init>()     // Catch: java.lang.Throwable -> L6b
            r11.label = r3     // Catch: java.lang.Throwable -> L6b
            r7 = 0
            r10 = 4
            r9 = r11
            java.lang.Object r11 = androidx.compose.animation.core.Animatable.animateTo$default(r4, r5, r6, r7, r8, r9, r10)     // Catch: java.lang.Throwable -> L68
            if (r11 != r0) goto L5e
        L5d:
            return r0
        L5e:
            androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation r11 = r9.this$0
            androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation$Companion r12 = androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation.Companion
            r11.setAppearanceAnimationInProgress(r2)
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        L68:
            r0 = move-exception
        L69:
            r12 = r0
            goto L74
        L6b:
            r0 = move-exception
            r9 = r11
            goto L69
        L6e:
            r12 = r11
            goto L74
        L70:
            r0 = move-exception
            r9 = r11
            r11 = r0
            goto L6e
        L74:
            androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation r11 = r9.this$0
            androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation$Companion r0 = androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation.Companion
            r11.setAppearanceAnimationInProgress(r2)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation$animateAppearance$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
