package androidx.compose.foundation.gestures;

import androidx.compose.foundation.MutatePriority;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ScrollExtensionsKt {
    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object animateScrollBy(androidx.compose.foundation.gestures.ScrollableState r5, float r6, androidx.compose.animation.core.FiniteAnimationSpec r7, kotlin.coroutines.Continuation r8) {
        /*
            boolean r0 = r8 instanceof androidx.compose.foundation.gestures.ScrollExtensionsKt$animateScrollBy$1
            if (r0 == 0) goto L13
            r0 = r8
            androidx.compose.foundation.gestures.ScrollExtensionsKt$animateScrollBy$1 r0 = (androidx.compose.foundation.gestures.ScrollExtensionsKt$animateScrollBy$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.foundation.gestures.ScrollExtensionsKt$animateScrollBy$1 r0 = new androidx.compose.foundation.gestures.ScrollExtensionsKt$animateScrollBy$1
            r0.<init>(r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r5 = r0.L$0
            kotlin.jvm.internal.Ref$FloatRef r5 = (kotlin.jvm.internal.Ref$FloatRef) r5
            kotlin.ResultKt.throwOnFailure(r8)
            goto L4f
        L2b:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L33:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlin.jvm.internal.Ref$FloatRef r8 = new kotlin.jvm.internal.Ref$FloatRef
            r8.<init>()
            androidx.compose.foundation.gestures.ScrollExtensionsKt$animateScrollBy$2 r2 = new androidx.compose.foundation.gestures.ScrollExtensionsKt$animateScrollBy$2
            r4 = 0
            r2.<init>(r6, r7, r8, r4)
            r0.L$0 = r8
            r0.label = r3
            androidx.compose.foundation.MutatePriority r6 = androidx.compose.foundation.MutatePriority.Default
            java.lang.Object r5 = r5.scroll(r6, r2, r0)
            if (r5 != r1) goto L4e
            return r1
        L4e:
            r5 = r8
        L4f:
            float r5 = r5.element
            java.lang.Float r6 = new java.lang.Float
            r6.<init>(r5)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.ScrollExtensionsKt.animateScrollBy(androidx.compose.foundation.gestures.ScrollableState, float, androidx.compose.animation.core.FiniteAnimationSpec, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object scrollBy(androidx.compose.foundation.gestures.ScrollableState r5, float r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            boolean r0 = r7 instanceof androidx.compose.foundation.gestures.ScrollExtensionsKt$scrollBy$1
            if (r0 == 0) goto L13
            r0 = r7
            androidx.compose.foundation.gestures.ScrollExtensionsKt$scrollBy$1 r0 = (androidx.compose.foundation.gestures.ScrollExtensionsKt$scrollBy$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.foundation.gestures.ScrollExtensionsKt$scrollBy$1 r0 = new androidx.compose.foundation.gestures.ScrollExtensionsKt$scrollBy$1
            r0.<init>(r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r5 = r0.L$0
            kotlin.jvm.internal.Ref$FloatRef r5 = (kotlin.jvm.internal.Ref$FloatRef) r5
            kotlin.ResultKt.throwOnFailure(r7)
            goto L4f
        L2b:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L33:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlin.jvm.internal.Ref$FloatRef r7 = new kotlin.jvm.internal.Ref$FloatRef
            r7.<init>()
            androidx.compose.foundation.gestures.ScrollExtensionsKt$scrollBy$2 r2 = new androidx.compose.foundation.gestures.ScrollExtensionsKt$scrollBy$2
            r4 = 0
            r2.<init>(r7, r6, r4)
            r0.L$0 = r7
            r0.label = r3
            androidx.compose.foundation.MutatePriority r6 = androidx.compose.foundation.MutatePriority.Default
            java.lang.Object r5 = r5.scroll(r6, r2, r0)
            if (r5 != r1) goto L4e
            return r1
        L4e:
            r5 = r7
        L4f:
            float r5 = r5.element
            java.lang.Float r6 = new java.lang.Float
            r6.<init>(r5)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.ScrollExtensionsKt.scrollBy(androidx.compose.foundation.gestures.ScrollableState, float, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public static Object stopScroll$default(ScrollableState scrollableState, Continuation continuation) {
        Object scroll = scrollableState.scroll(MutatePriority.Default, new ScrollExtensionsKt$stopScroll$2(null), continuation);
        return scroll == CoroutineSingletons.COROUTINE_SUSPENDED ? scroll : Unit.INSTANCE;
    }
}
