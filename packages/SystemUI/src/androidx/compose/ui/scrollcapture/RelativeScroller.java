package androidx.compose.ui.scrollcapture;

import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class RelativeScroller {
    public float scrollAmount;
    public final Function2 scrollBy;
    public final int viewportSize;

    public RelativeScroller(int i, Function2 function2) {
        this.viewportSize = i;
        this.scrollBy = function2;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object scrollBy(float r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof androidx.compose.ui.scrollcapture.RelativeScroller$scrollBy$1
            if (r0 == 0) goto L13
            r0 = r6
            androidx.compose.ui.scrollcapture.RelativeScroller$scrollBy$1 r0 = (androidx.compose.ui.scrollcapture.RelativeScroller$scrollBy$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.ui.scrollcapture.RelativeScroller$scrollBy$1 r0 = new androidx.compose.ui.scrollcapture.RelativeScroller$scrollBy$1
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r4 = r0.L$0
            androidx.compose.ui.scrollcapture.RelativeScroller r4 = (androidx.compose.ui.scrollcapture.RelativeScroller) r4
            kotlin.ResultKt.throwOnFailure(r6)
            goto L48
        L2b:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L33:
            kotlin.ResultKt.throwOnFailure(r6)
            java.lang.Float r6 = new java.lang.Float
            r6.<init>(r5)
            r0.L$0 = r4
            r0.label = r3
            kotlin.jvm.functions.Function2 r5 = r4.scrollBy
            java.lang.Object r6 = r5.invoke(r6, r0)
            if (r6 != r1) goto L48
            return r1
        L48:
            java.lang.Number r6 = (java.lang.Number) r6
            float r5 = r6.floatValue()
            float r6 = r4.scrollAmount
            float r6 = r6 + r5
            r4.scrollAmount = r6
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.scrollcapture.RelativeScroller.scrollBy(float, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
