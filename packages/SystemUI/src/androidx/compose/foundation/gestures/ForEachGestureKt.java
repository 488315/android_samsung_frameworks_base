package androidx.compose.foundation.gestures;

import androidx.compose.ui.input.pointer.PointerInputScope;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ForEachGestureKt {
    /* JADX WARN: Removed duplicated region for block: B:12:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0064 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x0062 -> B:10:0x0065). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object awaitAllPointersUp(androidx.compose.ui.input.pointer.AwaitPointerEventScope r8, androidx.compose.ui.input.pointer.PointerEventPass r9, kotlin.coroutines.jvm.internal.BaseContinuationImpl r10) {
        /*
            boolean r0 = r10 instanceof androidx.compose.foundation.gestures.ForEachGestureKt$awaitAllPointersUp$3
            if (r0 == 0) goto L13
            r0 = r10
            androidx.compose.foundation.gestures.ForEachGestureKt$awaitAllPointersUp$3 r0 = (androidx.compose.foundation.gestures.ForEachGestureKt$awaitAllPointersUp$3) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.foundation.gestures.ForEachGestureKt$awaitAllPointersUp$3 r0 = new androidx.compose.foundation.gestures.ForEachGestureKt$awaitAllPointersUp$3
            r0.<init>(r10)
        L18:
            java.lang.Object r10 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L3b
            if (r2 != r3) goto L33
            java.lang.Object r8 = r0.L$1
            androidx.compose.ui.input.pointer.PointerEventPass r8 = (androidx.compose.ui.input.pointer.PointerEventPass) r8
            java.lang.Object r9 = r0.L$0
            androidx.compose.ui.input.pointer.AwaitPointerEventScope r9 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r9
            kotlin.ResultKt.throwOnFailure(r10)
            r7 = r9
            r9 = r8
            r8 = r7
            goto L65
        L33:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L3b:
            kotlin.ResultKt.throwOnFailure(r10)
            androidx.compose.ui.input.pointer.PointerEvent r10 = r8.getCurrentEvent()
            java.util.List r10 = r10.changes
            r2 = r10
            java.util.Collection r2 = (java.util.Collection) r2
            int r2 = r2.size()
            r5 = r4
        L4c:
            if (r5 >= r2) goto L84
            java.lang.Object r6 = r10.get(r5)
            androidx.compose.ui.input.pointer.PointerInputChange r6 = (androidx.compose.ui.input.pointer.PointerInputChange) r6
            boolean r6 = r6.pressed
            if (r6 == 0) goto L81
        L58:
            r0.L$0 = r8
            r0.L$1 = r9
            r0.label = r3
            java.lang.Object r10 = r8.awaitPointerEvent(r9, r0)
            if (r10 != r1) goto L65
            return r1
        L65:
            androidx.compose.ui.input.pointer.PointerEvent r10 = (androidx.compose.ui.input.pointer.PointerEvent) r10
            java.util.List r10 = r10.changes
            r2 = r10
            java.util.Collection r2 = (java.util.Collection) r2
            int r2 = r2.size()
            r5 = r4
        L71:
            if (r5 >= r2) goto L84
            java.lang.Object r6 = r10.get(r5)
            androidx.compose.ui.input.pointer.PointerInputChange r6 = (androidx.compose.ui.input.pointer.PointerInputChange) r6
            boolean r6 = r6.pressed
            if (r6 == 0) goto L7e
            goto L58
        L7e:
            int r5 = r5 + 1
            goto L71
        L81:
            int r5 = r5 + 1
            goto L4c
        L84:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.ForEachGestureKt.awaitAllPointersUp(androidx.compose.ui.input.pointer.AwaitPointerEventScope, androidx.compose.ui.input.pointer.PointerEventPass, kotlin.coroutines.jvm.internal.BaseContinuationImpl):java.lang.Object");
    }

    public static final Object awaitEachGesture(PointerInputScope pointerInputScope, Function2 function2, Continuation continuation) {
        Object awaitPointerEventScope = pointerInputScope.awaitPointerEventScope(new ForEachGestureKt$awaitEachGesture$2(continuation.getContext(), function2, null), continuation);
        return awaitPointerEventScope == CoroutineSingletons.COROUTINE_SUSPENDED ? awaitPointerEventScope : Unit.INSTANCE;
    }
}
