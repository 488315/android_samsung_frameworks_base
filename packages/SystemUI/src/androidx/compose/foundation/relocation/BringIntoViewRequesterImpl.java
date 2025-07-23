package androidx.compose.foundation.relocation;

import androidx.compose.runtime.collection.MutableVector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class BringIntoViewRequesterImpl implements BringIntoViewRequester {
    public final MutableVector nodes = new MutableVector(new BringIntoViewRequesterNode[16], 0);

    /* JADX WARN: Removed duplicated region for block: B:12:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:13:0x0063 -> B:10:0x0066). Please report as a decompilation issue!!! */
    @Override // androidx.compose.foundation.relocation.BringIntoViewRequester
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object bringIntoView(androidx.compose.ui.geometry.Rect r8, kotlin.coroutines.jvm.internal.ContinuationImpl r9) {
        /*
            r7 = this;
            boolean r0 = r9 instanceof androidx.compose.foundation.relocation.BringIntoViewRequesterImpl$bringIntoView$1
            if (r0 == 0) goto L13
            r0 = r9
            androidx.compose.foundation.relocation.BringIntoViewRequesterImpl$bringIntoView$1 r0 = (androidx.compose.foundation.relocation.BringIntoViewRequesterImpl$bringIntoView$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.foundation.relocation.BringIntoViewRequesterImpl$bringIntoView$1 r0 = new androidx.compose.foundation.relocation.BringIntoViewRequesterImpl$bringIntoView$1
            r0.<init>(r7, r9)
        L18:
            java.lang.Object r9 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3c
            if (r2 != r3) goto L34
            int r7 = r0.I$1
            int r8 = r0.I$0
            java.lang.Object r2 = r0.L$1
            java.lang.Object[] r2 = (java.lang.Object[]) r2
            java.lang.Object r4 = r0.L$0
            androidx.compose.ui.geometry.Rect r4 = (androidx.compose.ui.geometry.Rect) r4
            kotlin.ResultKt.throwOnFailure(r9)
            r9 = r4
            goto L66
        L34:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L3c:
            kotlin.ResultKt.throwOnFailure(r9)
            androidx.compose.runtime.collection.MutableVector r7 = r7.nodes
            java.lang.Object[] r9 = r7.content
            int r7 = r7.size
            r2 = 0
            r6 = r9
            r9 = r8
            r8 = r2
            r2 = r6
        L4a:
            if (r8 >= r7) goto L68
            r4 = r2[r8]
            androidx.compose.foundation.relocation.BringIntoViewRequesterNode r4 = (androidx.compose.foundation.relocation.BringIntoViewRequesterNode) r4
            androidx.compose.foundation.relocation.BringIntoViewRequesterImpl$bringIntoView$2$1 r5 = new androidx.compose.foundation.relocation.BringIntoViewRequesterImpl$bringIntoView$2$1
            r5.<init>()
            r0.L$0 = r9
            r0.L$1 = r2
            r0.I$0 = r8
            r0.I$1 = r7
            r0.label = r3
            java.lang.Object r4 = androidx.compose.ui.relocation.BringIntoViewModifierNodeKt.bringIntoView(r4, r5, r0)
            if (r4 != r1) goto L66
            return r1
        L66:
            int r8 = r8 + r3
            goto L4a
        L68:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.relocation.BringIntoViewRequesterImpl.bringIntoView(androidx.compose.ui.geometry.Rect, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
