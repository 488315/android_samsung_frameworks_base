package com.android.systemui.communal.ui.compose.extensions;

import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerInputScope;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public abstract class PointerInputScopeExtKt {
    /* JADX WARN: Removed duplicated region for block: B:12:0x0058 A[LOOP:0: B:11:0x0056->B:12:0x0058, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x0049 -> B:10:0x004c). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$consumeUntilUp(androidx.compose.ui.input.pointer.AwaitPointerEventScope r9, androidx.compose.ui.input.pointer.PointerEventPass r10, kotlin.coroutines.Continuation r11) {
        /*
            boolean r0 = r11 instanceof com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt$consumeUntilUp$1
            if (r0 == 0) goto L13
            r0 = r11
            com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt$consumeUntilUp$1 r0 = (com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt$consumeUntilUp$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt$consumeUntilUp$1 r0 = new com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt$consumeUntilUp$1
            r0.<init>(r11)
        L18:
            java.lang.Object r11 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3a
            if (r2 != r3) goto L32
            java.lang.Object r9 = r0.L$1
            androidx.compose.ui.input.pointer.PointerEventPass r9 = (androidx.compose.ui.input.pointer.PointerEventPass) r9
            java.lang.Object r10 = r0.L$0
            androidx.compose.ui.input.pointer.AwaitPointerEventScope r10 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r10
            kotlin.ResultKt.throwOnFailure(r11)
            r8 = r10
            r10 = r9
            r9 = r8
            goto L4c
        L32:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L3a:
            kotlin.ResultKt.throwOnFailure(r11)
        L3d:
            r0.L$0 = r9
            r0.L$1 = r10
            r0.label = r3
            androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl$PointerEventHandlerCoroutine r9 = (androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) r9
            java.lang.Object r11 = r9.awaitPointerEvent(r10, r0)
            if (r11 != r1) goto L4c
            goto L7c
        L4c:
            androidx.compose.ui.input.pointer.PointerEvent r11 = (androidx.compose.ui.input.pointer.PointerEvent) r11
            java.util.List r2 = r11.changes
            int r4 = r2.size()
            r5 = 0
            r6 = r5
        L56:
            if (r6 >= r4) goto L64
            java.lang.Object r7 = r2.get(r6)
            androidx.compose.ui.input.pointer.PointerInputChange r7 = (androidx.compose.ui.input.pointer.PointerInputChange) r7
            r7.consume()
            int r6 = r6 + 1
            goto L56
        L64:
            java.util.List r11 = r11.changes
            int r2 = r11.size()
        L6a:
            if (r5 >= r2) goto L7a
            java.lang.Object r4 = r11.get(r5)
            androidx.compose.ui.input.pointer.PointerInputChange r4 = (androidx.compose.ui.input.pointer.PointerInputChange) r4
            boolean r4 = r4.pressed
            if (r4 == 0) goto L77
            goto L3d
        L77:
            int r5 = r5 + 1
            goto L6a
        L7a:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
        L7c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt.access$consumeUntilUp(androidx.compose.ui.input.pointer.AwaitPointerEventScope, androidx.compose.ui.input.pointer.PointerEventPass, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static Object detectLongPressGesture$default(PointerInputScope pointerInputScope, Function1 function1, Continuation continuation) {
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new PointerInputScopeExtKt$detectLongPressGesture$2(pointerInputScope, PointerEventPass.Initial, function1, null), continuation);
        return coroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? coroutineScope : Unit.INSTANCE;
    }

    public static Object observeTaps$default(PointerInputScope pointerInputScope, boolean z, Function1 function1, Continuation continuation, int i) {
        PointerEventPass pointerEventPass = PointerEventPass.Initial;
        if ((i & 2) != 0) {
            z = false;
        }
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new PointerInputScopeExtKt$observeTaps$2(function1, pointerInputScope, pointerEventPass, z, null), continuation);
        return coroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? coroutineScope : Unit.INSTANCE;
    }
}
