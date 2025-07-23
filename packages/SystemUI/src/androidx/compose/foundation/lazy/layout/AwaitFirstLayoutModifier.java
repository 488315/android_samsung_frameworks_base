package androidx.compose.foundation.lazy.layout;

import androidx.compose.ui.layout.OnGloballyPositionedModifier;
import kotlin.coroutines.SafeContinuation;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AwaitFirstLayoutModifier implements OnGloballyPositionedModifier {
    public SafeContinuation continuation;
    public boolean wasPositioned;

    /* JADX WARN: Removed duplicated region for block: B:11:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object waitForFirstLayout(kotlin.coroutines.jvm.internal.ContinuationImpl r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof androidx.compose.foundation.lazy.layout.AwaitFirstLayoutModifier$waitForFirstLayout$1
            if (r0 == 0) goto L13
            r0 = r5
            androidx.compose.foundation.lazy.layout.AwaitFirstLayoutModifier$waitForFirstLayout$1 r0 = (androidx.compose.foundation.lazy.layout.AwaitFirstLayoutModifier$waitForFirstLayout$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.foundation.lazy.layout.AwaitFirstLayoutModifier$waitForFirstLayout$1 r0 = new androidx.compose.foundation.lazy.layout.AwaitFirstLayoutModifier$waitForFirstLayout$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L37
            if (r2 != r3) goto L2f
            java.lang.Object r4 = r0.L$1
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
            java.lang.Object r0 = r0.L$0
            androidx.compose.foundation.lazy.layout.AwaitFirstLayoutModifier r0 = (androidx.compose.foundation.lazy.layout.AwaitFirstLayoutModifier) r0
            kotlin.ResultKt.throwOnFailure(r5)
            goto L59
        L2f:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L37:
            kotlin.ResultKt.throwOnFailure(r5)
            boolean r5 = r4.wasPositioned
            if (r5 != 0) goto L62
            kotlin.coroutines.SafeContinuation r5 = r4.continuation
            r0.L$0 = r4
            r0.L$1 = r5
            r0.label = r3
            kotlin.coroutines.SafeContinuation r2 = new kotlin.coroutines.SafeContinuation
            kotlin.coroutines.Continuation r0 = kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.intercepted(r0)
            r2.<init>(r0)
            r4.continuation = r2
            java.lang.Object r4 = r2.getOrThrow()
            if (r4 != r1) goto L58
            return r1
        L58:
            r4 = r5
        L59:
            if (r4 == 0) goto L62
            int r5 = kotlin.Result.$r8$clinit
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            r4.resumeWith(r5)
        L62:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.lazy.layout.AwaitFirstLayoutModifier.waitForFirstLayout(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
