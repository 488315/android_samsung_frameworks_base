package androidx.compose.foundation.gestures;

import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerInputScope;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class TapGestureDetectorKt {
    public static final Function3 NoPressGesture = new TapGestureDetectorKt$NoPressGesture$1(null);

    /* JADX WARN: Removed duplicated region for block: B:12:0x0050 A[LOOP:0: B:11:0x004e->B:12:0x0050, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0040 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x003e -> B:10:0x0041). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$consumeUntilUp(androidx.compose.ui.input.pointer.AwaitPointerEventScope r8, kotlin.coroutines.jvm.internal.BaseContinuationImpl r9) {
        /*
            boolean r0 = r9 instanceof androidx.compose.foundation.gestures.TapGestureDetectorKt$consumeUntilUp$1
            if (r0 == 0) goto L13
            r0 = r9
            androidx.compose.foundation.gestures.TapGestureDetectorKt$consumeUntilUp$1 r0 = (androidx.compose.foundation.gestures.TapGestureDetectorKt$consumeUntilUp$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.foundation.gestures.TapGestureDetectorKt$consumeUntilUp$1 r0 = new androidx.compose.foundation.gestures.TapGestureDetectorKt$consumeUntilUp$1
            r0.<init>(r9)
        L18:
            java.lang.Object r9 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r8 = r0.L$0
            androidx.compose.ui.input.pointer.AwaitPointerEventScope r8 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r8
            kotlin.ResultKt.throwOnFailure(r9)
            goto L41
        L2b:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L33:
            kotlin.ResultKt.throwOnFailure(r9)
        L36:
            r0.L$0 = r8
            r0.label = r3
            java.lang.Object r9 = androidx.compose.ui.input.pointer.AwaitPointerEventScope.awaitPointerEvent$default(r8, r0)
            if (r9 != r1) goto L41
            return r1
        L41:
            androidx.compose.ui.input.pointer.PointerEvent r9 = (androidx.compose.ui.input.pointer.PointerEvent) r9
            java.util.List r2 = r9.changes
            r4 = r2
            java.util.Collection r4 = (java.util.Collection) r4
            int r4 = r4.size()
            r5 = 0
            r6 = r5
        L4e:
            if (r6 >= r4) goto L5c
            java.lang.Object r7 = r2.get(r6)
            androidx.compose.ui.input.pointer.PointerInputChange r7 = (androidx.compose.ui.input.pointer.PointerInputChange) r7
            r7.consume()
            int r6 = r6 + 1
            goto L4e
        L5c:
            java.util.List r9 = r9.changes
            r2 = r9
            java.util.Collection r2 = (java.util.Collection) r2
            int r2 = r2.size()
        L65:
            if (r5 >= r2) goto L75
            java.lang.Object r4 = r9.get(r5)
            androidx.compose.ui.input.pointer.PointerInputChange r4 = (androidx.compose.ui.input.pointer.PointerInputChange) r4
            boolean r4 = r4.pressed
            if (r4 == 0) goto L72
            goto L36
        L72:
            int r5 = r5 + 1
            goto L65
        L75:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.TapGestureDetectorKt.access$consumeUntilUp(androidx.compose.ui.input.pointer.AwaitPointerEventScope, kotlin.coroutines.jvm.internal.BaseContinuationImpl):java.lang.Object");
    }

    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    /* JADX WARN: Removed duplicated region for block: B:12:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0083 A[SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x004c -> B:10:0x004f). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object awaitFirstDown(androidx.compose.ui.input.pointer.AwaitPointerEventScope r10, boolean r11, androidx.compose.ui.input.pointer.PointerEventPass r12, kotlin.coroutines.Continuation r13) {
        /*
            boolean r0 = r13 instanceof androidx.compose.foundation.gestures.TapGestureDetectorKt$awaitFirstDown$2
            if (r0 == 0) goto L13
            r0 = r13
            androidx.compose.foundation.gestures.TapGestureDetectorKt$awaitFirstDown$2 r0 = (androidx.compose.foundation.gestures.TapGestureDetectorKt$awaitFirstDown$2) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.foundation.gestures.TapGestureDetectorKt$awaitFirstDown$2 r0 = new androidx.compose.foundation.gestures.TapGestureDetectorKt$awaitFirstDown$2
            r0.<init>(r13)
        L18:
            java.lang.Object r13 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3d
            if (r2 != r3) goto L35
            boolean r10 = r0.Z$0
            java.lang.Object r11 = r0.L$1
            androidx.compose.ui.input.pointer.PointerEventPass r11 = (androidx.compose.ui.input.pointer.PointerEventPass) r11
            java.lang.Object r12 = r0.L$0
            androidx.compose.ui.input.pointer.AwaitPointerEventScope r12 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r12
            kotlin.ResultKt.throwOnFailure(r13)
            r9 = r11
            r11 = r10
            r10 = r12
            r12 = r9
            goto L4f
        L35:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L3d:
            kotlin.ResultKt.throwOnFailure(r13)
        L40:
            r0.L$0 = r10
            r0.L$1 = r12
            r0.Z$0 = r11
            r0.label = r3
            java.lang.Object r13 = r10.awaitPointerEvent(r12, r0)
            if (r13 != r1) goto L4f
            return r1
        L4f:
            androidx.compose.ui.input.pointer.PointerEvent r13 = (androidx.compose.ui.input.pointer.PointerEvent) r13
            java.util.List r2 = r13.changes
            r4 = r2
            java.util.Collection r4 = (java.util.Collection) r4
            int r4 = r4.size()
            r5 = 0
            r6 = r5
        L5c:
            if (r6 >= r4) goto L83
            java.lang.Object r7 = r2.get(r6)
            androidx.compose.ui.input.pointer.PointerInputChange r7 = (androidx.compose.ui.input.pointer.PointerInputChange) r7
            if (r11 == 0) goto L78
            boolean r8 = r7.isConsumed()
            if (r8 != 0) goto L76
            boolean r8 = r7.previousPressed
            if (r8 != 0) goto L76
            boolean r7 = r7.pressed
            if (r7 == 0) goto L76
            r7 = r3
            goto L7c
        L76:
            r7 = r5
            goto L7c
        L78:
            boolean r7 = androidx.compose.ui.input.pointer.PointerEventKt.changedToDownIgnoreConsumed(r7)
        L7c:
            if (r7 != 0) goto L80
            r2 = r5
            goto L84
        L80:
            int r6 = r6 + 1
            goto L5c
        L83:
            r2 = r3
        L84:
            if (r2 == 0) goto L40
            java.util.List r10 = r13.changes
            java.lang.Object r10 = r10.get(r5)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.TapGestureDetectorKt.awaitFirstDown(androidx.compose.ui.input.pointer.AwaitPointerEventScope, boolean, androidx.compose.ui.input.pointer.PointerEventPass, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static /* synthetic */ Object awaitFirstDown$default(AwaitPointerEventScope awaitPointerEventScope, PointerEventPass pointerEventPass, Continuation continuation, int i) {
        boolean z = (i & 1) != 0;
        if ((i & 2) != 0) {
            pointerEventPass = PointerEventPass.Main;
        }
        return awaitFirstDown(awaitPointerEventScope, z, pointerEventPass, continuation);
    }

    public static final Object detectTapAndPress(PointerInputScope pointerInputScope, Function3 function3, Function1 function1, Continuation continuation) {
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new TapGestureDetectorKt$detectTapAndPress$2(pointerInputScope, function3, function1, new PressGestureScopeImpl(pointerInputScope), null), continuation);
        return coroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? coroutineScope : Unit.INSTANCE;
    }

    public static final Object detectTapGestures(PointerInputScope pointerInputScope, Function3 function3, Function1 function1, Function1 function12, Function1 function13, Continuation continuation) {
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new TapGestureDetectorKt$detectTapGestures$2(pointerInputScope, function3, function12, function1, function13, null), continuation);
        return coroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? coroutineScope : Unit.INSTANCE;
    }

    public static /* synthetic */ Object detectTapGestures$default(PointerInputScope pointerInputScope, Function1 function1, Function1 function12, Function3 function3, Function1 function13, Continuation continuation, int i) {
        Function1 function14;
        Function3 function32;
        Function1 function15;
        if ((i & 1) != 0) {
            function1 = null;
        }
        if ((i & 2) != 0) {
            function12 = null;
        }
        if ((i & 4) != 0) {
            function3 = NoPressGesture;
        }
        if ((i & 8) != 0) {
            Function1 function16 = function12;
            function14 = function1;
            function32 = function3;
            function15 = function16;
            function13 = null;
        } else {
            Function1 function17 = function12;
            function14 = function1;
            function32 = function3;
            function15 = function17;
        }
        return detectTapGestures(pointerInputScope, function32, function14, function15, function13, continuation);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0021  */
    /* JADX WARN: Type inference failed for: r2v1, types: [T, androidx.compose.foundation.gestures.LongPressResult$Canceled] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object waitForLongPress(androidx.compose.ui.input.pointer.AwaitPointerEventScope r7, androidx.compose.ui.input.pointer.PointerEventPass r8, kotlin.coroutines.jvm.internal.BaseContinuationImpl r9) {
        /*
            boolean r0 = r9 instanceof androidx.compose.foundation.gestures.TapGestureDetectorKt$waitForLongPress$1
            if (r0 == 0) goto L13
            r0 = r9
            androidx.compose.foundation.gestures.TapGestureDetectorKt$waitForLongPress$1 r0 = (androidx.compose.foundation.gestures.TapGestureDetectorKt$waitForLongPress$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.foundation.gestures.TapGestureDetectorKt$waitForLongPress$1 r0 = new androidx.compose.foundation.gestures.TapGestureDetectorKt$waitForLongPress$1
            r0.<init>(r9)
        L18:
            java.lang.Object r9 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r7 = r0.L$0
            kotlin.jvm.internal.Ref$ObjectRef r7 = (kotlin.jvm.internal.Ref$ObjectRef) r7
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> L5c
            goto L59
        L2b:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L33:
            kotlin.ResultKt.throwOnFailure(r9)
            kotlin.jvm.internal.Ref$ObjectRef r9 = new kotlin.jvm.internal.Ref$ObjectRef
            r9.<init>()
            androidx.compose.foundation.gestures.LongPressResult$Canceled r2 = androidx.compose.foundation.gestures.LongPressResult.Canceled.INSTANCE
            r9.element = r2
            androidx.compose.ui.platform.ViewConfiguration r2 = r7.getViewConfiguration()     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> L5c
            long r4 = r2.getLongPressTimeoutMillis()     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> L5c
            androidx.compose.foundation.gestures.TapGestureDetectorKt$waitForLongPress$2 r2 = new androidx.compose.foundation.gestures.TapGestureDetectorKt$waitForLongPress$2     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> L5c
            r6 = 0
            r2.<init>(r8, r9, r6)     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> L5c
            r0.L$0 = r9     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> L5c
            r0.label = r3     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> L5c
            java.lang.Object r7 = r7.withTimeout(r4, r2, r0)     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> L5c
            if (r7 != r1) goto L58
            return r1
        L58:
            r7 = r9
        L59:
            T r7 = r7.element
            return r7
        L5c:
            androidx.compose.foundation.gestures.LongPressResult$Success r7 = androidx.compose.foundation.gestures.LongPressResult.Success.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.TapGestureDetectorKt.waitForLongPress(androidx.compose.ui.input.pointer.AwaitPointerEventScope, androidx.compose.ui.input.pointer.PointerEventPass, kotlin.coroutines.jvm.internal.BaseContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x00ae, code lost:
    
        if (r15 == r1) goto L36;
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:32:0x00ae -> B:11:0x0032). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object waitForUpOrCancellation(androidx.compose.ui.input.pointer.AwaitPointerEventScope r13, androidx.compose.ui.input.pointer.PointerEventPass r14, kotlin.coroutines.jvm.internal.BaseContinuationImpl r15) {
        /*
            Method dump skipped, instructions count: 218
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.TapGestureDetectorKt.waitForUpOrCancellation(androidx.compose.ui.input.pointer.AwaitPointerEventScope, androidx.compose.ui.input.pointer.PointerEventPass, kotlin.coroutines.jvm.internal.BaseContinuationImpl):java.lang.Object");
    }
}
