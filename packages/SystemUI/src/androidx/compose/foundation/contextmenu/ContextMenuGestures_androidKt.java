package androidx.compose.foundation.contextmenu;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ContextMenuGestures_androidKt {
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0040 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0049  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:13:0x003e -> B:10:0x0041). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$awaitFirstRightClickDown(androidx.compose.ui.input.pointer.AwaitPointerEventScope r9, kotlin.coroutines.jvm.internal.BaseContinuationImpl r10) {
        /*
            boolean r0 = r10 instanceof androidx.compose.foundation.contextmenu.ContextMenuGestures_androidKt$awaitFirstRightClickDown$1
            if (r0 == 0) goto L13
            r0 = r10
            androidx.compose.foundation.contextmenu.ContextMenuGestures_androidKt$awaitFirstRightClickDown$1 r0 = (androidx.compose.foundation.contextmenu.ContextMenuGestures_androidKt$awaitFirstRightClickDown$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.foundation.contextmenu.ContextMenuGestures_androidKt$awaitFirstRightClickDown$1 r0 = new androidx.compose.foundation.contextmenu.ContextMenuGestures_androidKt$awaitFirstRightClickDown$1
            r0.<init>(r10)
        L18:
            java.lang.Object r10 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r9 = r0.L$0
            androidx.compose.ui.input.pointer.AwaitPointerEventScope r9 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r9
            kotlin.ResultKt.throwOnFailure(r10)
            goto L41
        L2b:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L33:
            kotlin.ResultKt.throwOnFailure(r10)
        L36:
            r0.L$0 = r9
            r0.label = r3
            java.lang.Object r10 = androidx.compose.ui.input.pointer.AwaitPointerEventScope.awaitPointerEvent$default(r9, r0)
            if (r10 != r1) goto L41
            return r1
        L41:
            androidx.compose.ui.input.pointer.PointerEvent r10 = (androidx.compose.ui.input.pointer.PointerEvent) r10
            int r2 = r10.buttons
            r2 = r2 & 66
            if (r2 == 0) goto L36
            java.util.List r2 = r10.changes
            r4 = r2
            java.util.Collection r4 = (java.util.Collection) r4
            int r4 = r4.size()
            r5 = 0
            r6 = r5
        L54:
            if (r6 >= r4) goto L6d
            java.lang.Object r7 = r2.get(r6)
            androidx.compose.ui.input.pointer.PointerInputChange r7 = (androidx.compose.ui.input.pointer.PointerInputChange) r7
            boolean r8 = r7.isConsumed()
            if (r8 != 0) goto L36
            boolean r8 = r7.previousPressed
            if (r8 != 0) goto L36
            boolean r7 = r7.pressed
            if (r7 == 0) goto L36
            int r6 = r6 + 1
            goto L54
        L6d:
            java.util.List r9 = r10.changes
            java.lang.Object r9 = r9.get(r5)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.contextmenu.ContextMenuGestures_androidKt.access$awaitFirstRightClickDown(androidx.compose.ui.input.pointer.AwaitPointerEventScope, kotlin.coroutines.jvm.internal.BaseContinuationImpl):java.lang.Object");
    }
}
