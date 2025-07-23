package androidx.compose.foundation.gestures;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.PointerEvent;
import androidx.compose.ui.input.pointer.PointerId;
import androidx.compose.ui.input.pointer.PointerInputChange;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.PointerType;
import androidx.compose.ui.platform.ViewConfiguration;
import androidx.compose.ui.unit.Dp;
import com.samsung.sesl.compose.foundation.BasicSwitchKt$$ExternalSyntheticLambda1;
import com.samsung.sesl.compose.foundation.BasicSwitchKt$SeslBasicSwitch$4$2$1$$ExternalSyntheticLambda1;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$LongRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class DragGestureDetectorKt {
    public static final float mouseToTouchSlopRatio;

    static {
        Dp.Companion companion = Dp.Companion;
        mouseToTouchSlopRatio = ((float) 0.125d) / 18;
    }

    /* JADX WARN: Code restructure failed: missing block: B:44:0x00d0, code lost:
    
        if (androidx.compose.ui.geometry.Offset.m396equalsimpl0(r8, 0) == false) goto L47;
     */
    /* JADX WARN: Removed duplicated region for block: B:12:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0065 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x008f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0026  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:38:0x0066 -> B:10:0x006b). Please report as a decompilation issue!!! */
    /* renamed from: awaitDragOrCancellation-rnUCldI, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object m66awaitDragOrCancellationrnUCldI(androidx.compose.ui.input.pointer.AwaitPointerEventScope r17, long r18, kotlin.coroutines.jvm.internal.ContinuationImpl r20) {
        /*
            Method dump skipped, instructions count: 223
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.DragGestureDetectorKt.m66awaitDragOrCancellationrnUCldI(androidx.compose.ui.input.pointer.AwaitPointerEventScope, long, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x019c  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00f4 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:59:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0029  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:49:0x018f -> B:11:0x0195). Please report as a decompilation issue!!! */
    /* renamed from: awaitHorizontalPointerSlopOrCancellation-gDDlDlE, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object m67awaitHorizontalPointerSlopOrCancellationgDDlDlE(androidx.compose.ui.input.pointer.AwaitPointerEventScope r19, long r20, int r22, kotlin.jvm.functions.Function2 r23, kotlin.coroutines.jvm.internal.BaseContinuationImpl r24) {
        /*
            Method dump skipped, instructions count: 419
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.DragGestureDetectorKt.m67awaitHorizontalPointerSlopOrCancellationgDDlDlE(androidx.compose.ui.input.pointer.AwaitPointerEventScope, long, int, kotlin.jvm.functions.Function2, kotlin.coroutines.jvm.internal.BaseContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x01a2  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x013d  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00fa A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:58:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0029  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:48:0x0195 -> B:11:0x019b). Please report as a decompilation issue!!! */
    /* renamed from: awaitHorizontalTouchSlopOrCancellation-jO51t88, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object m68awaitHorizontalTouchSlopOrCancellationjO51t88(androidx.compose.ui.input.pointer.AwaitPointerEventScope r19, long r20, com.android.compose.gesture.NestedDraggableNode$detectDrags$2$$ExternalSyntheticLambda0 r22, kotlin.coroutines.jvm.internal.BaseContinuationImpl r23) {
        /*
            Method dump skipped, instructions count: 425
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.DragGestureDetectorKt.m68awaitHorizontalTouchSlopOrCancellationjO51t88(androidx.compose.ui.input.pointer.AwaitPointerEventScope, long, com.android.compose.gesture.NestedDraggableNode$detectDrags$2$$ExternalSyntheticLambda0, kotlin.coroutines.jvm.internal.BaseContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:14:0x00a7 A[Catch: PointerEventTimeoutCancellationException -> 0x00b0, TRY_LEAVE, TryCatch #0 {PointerEventTimeoutCancellationException -> 0x00b0, blocks: (B:11:0x0030, B:12:0x00a3, B:14:0x00a7, B:34:0x0089), top: B:7:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0022  */
    /* JADX WARN: Type inference failed for: r10v3, types: [kotlin.jvm.internal.Ref$ObjectRef] */
    /* JADX WARN: Type inference failed for: r10v7 */
    /* JADX WARN: Type inference failed for: r10v8 */
    /* JADX WARN: Type inference failed for: r11v3, types: [T, androidx.compose.ui.input.pointer.PointerInputChange, java.lang.Object] */
    /* renamed from: awaitLongPressOrCancellation-rnUCldI, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object m69awaitLongPressOrCancellationrnUCldI(androidx.compose.ui.input.pointer.AwaitPointerEventScope r9, long r10, kotlin.coroutines.jvm.internal.BaseContinuationImpl r12) {
        /*
            boolean r0 = r12 instanceof androidx.compose.foundation.gestures.DragGestureDetectorKt$awaitLongPressOrCancellation$1
            if (r0 == 0) goto L13
            r0 = r12
            androidx.compose.foundation.gestures.DragGestureDetectorKt$awaitLongPressOrCancellation$1 r0 = (androidx.compose.foundation.gestures.DragGestureDetectorKt$awaitLongPressOrCancellation$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.foundation.gestures.DragGestureDetectorKt$awaitLongPressOrCancellation$1 r0 = new androidx.compose.foundation.gestures.DragGestureDetectorKt$awaitLongPressOrCancellation$1
            r0.<init>(r12)
        L18:
            java.lang.Object r12 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L3d
            if (r2 != r3) goto L35
            java.lang.Object r9 = r0.L$2
            kotlin.jvm.internal.Ref$BooleanRef r9 = (kotlin.jvm.internal.Ref$BooleanRef) r9
            java.lang.Object r10 = r0.L$1
            kotlin.jvm.internal.Ref$ObjectRef r10 = (kotlin.jvm.internal.Ref$ObjectRef) r10
            java.lang.Object r11 = r0.L$0
            androidx.compose.ui.input.pointer.PointerInputChange r11 = (androidx.compose.ui.input.pointer.PointerInputChange) r11
            kotlin.ResultKt.throwOnFailure(r12)     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> Lb0
            goto La3
        L35:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L3d:
            kotlin.ResultKt.throwOnFailure(r12)
            androidx.compose.ui.input.pointer.PointerEvent r12 = r9.getCurrentEvent()
            boolean r12 = m73isPointerUpDmW0f2w(r12, r10)
            if (r12 == 0) goto L4b
            goto Laf
        L4b:
            androidx.compose.ui.input.pointer.PointerEvent r12 = r9.getCurrentEvent()
            java.util.List r12 = r12.changes
            r2 = r12
            java.util.Collection r2 = (java.util.Collection) r2
            int r2 = r2.size()
            r5 = 0
        L59:
            if (r5 >= r2) goto L6e
            java.lang.Object r6 = r12.get(r5)
            r7 = r6
            androidx.compose.ui.input.pointer.PointerInputChange r7 = (androidx.compose.ui.input.pointer.PointerInputChange) r7
            long r7 = r7.id
            boolean r7 = androidx.compose.ui.input.pointer.PointerId.m591equalsimpl0(r7, r10)
            if (r7 == 0) goto L6b
            goto L6f
        L6b:
            int r5 = r5 + 1
            goto L59
        L6e:
            r6 = r4
        L6f:
            r11 = r6
            androidx.compose.ui.input.pointer.PointerInputChange r11 = (androidx.compose.ui.input.pointer.PointerInputChange) r11
            if (r11 != 0) goto L75
            goto Laf
        L75:
            kotlin.jvm.internal.Ref$ObjectRef r10 = new kotlin.jvm.internal.Ref$ObjectRef
            r10.<init>()
            kotlin.jvm.internal.Ref$ObjectRef r12 = new kotlin.jvm.internal.Ref$ObjectRef
            r12.<init>()
            r12.element = r11
            androidx.compose.ui.platform.ViewConfiguration r2 = r9.getViewConfiguration()
            long r5 = r2.getLongPressTimeoutMillis()
            kotlin.jvm.internal.Ref$BooleanRef r2 = new kotlin.jvm.internal.Ref$BooleanRef     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> Lb0
            r2.<init>()     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> Lb0
            androidx.compose.foundation.gestures.DragGestureDetectorKt$awaitLongPressOrCancellation$2 r7 = new androidx.compose.foundation.gestures.DragGestureDetectorKt$awaitLongPressOrCancellation$2     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> Lb0
            r7.<init>(r2, r12, r10, r4)     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> Lb0
            r0.L$0 = r11     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> Lb0
            r0.L$1 = r10     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> Lb0
            r0.L$2 = r2     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> Lb0
            r0.label = r3     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> Lb0
            java.lang.Object r9 = r9.withTimeout(r5, r7, r0)     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> Lb0
            if (r9 != r1) goto La2
            return r1
        La2:
            r9 = r2
        La3:
            boolean r9 = r9.element     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> Lb0
            if (r9 == 0) goto Laf
            T r9 = r10.element     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> Lb0
            androidx.compose.ui.input.pointer.PointerInputChange r9 = (androidx.compose.ui.input.pointer.PointerInputChange) r9     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> Lb0
            if (r9 != 0) goto Lae
            return r11
        Lae:
            return r9
        Laf:
            return r4
        Lb0:
            T r9 = r10.element
            androidx.compose.ui.input.pointer.PointerInputChange r9 = (androidx.compose.ui.input.pointer.PointerInputChange) r9
            if (r9 != 0) goto Lb7
            goto Lb8
        Lb7:
            r11 = r9
        Lb8:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.DragGestureDetectorKt.m69awaitLongPressOrCancellationrnUCldI(androidx.compose.ui.input.pointer.AwaitPointerEventScope, long, kotlin.coroutines.jvm.internal.BaseContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x01a5  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x013d  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00fa A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:58:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0029  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:48:0x0198 -> B:11:0x019e). Please report as a decompilation issue!!! */
    /* renamed from: awaitVerticalTouchSlopOrCancellation-jO51t88, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object m70awaitVerticalTouchSlopOrCancellationjO51t88(androidx.compose.ui.input.pointer.AwaitPointerEventScope r19, long r20, com.android.compose.gesture.NestedDraggableNode$detectDrags$2$$ExternalSyntheticLambda0 r22, kotlin.coroutines.jvm.internal.BaseContinuationImpl r23) {
        /*
            Method dump skipped, instructions count: 428
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.DragGestureDetectorKt.m70awaitVerticalTouchSlopOrCancellationjO51t88(androidx.compose.ui.input.pointer.AwaitPointerEventScope, long, com.android.compose.gesture.NestedDraggableNode$detectDrags$2$$ExternalSyntheticLambda0, kotlin.coroutines.jvm.internal.BaseContinuationImpl):java.lang.Object");
    }

    public static final Object detectDragGestures(PointerInputScope pointerInputScope, final Function1 function1, final Function0 function0, Function0 function02, Function2 function2, Continuation continuation) {
        Object awaitEachGesture = ForEachGestureKt.awaitEachGesture(pointerInputScope, new DragGestureDetectorKt$detectDragGestures$9(new Function0() { // from class: androidx.compose.foundation.gestures.DragGestureDetectorKt$detectDragGestures$7
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Boolean.TRUE;
            }
        }, new Ref$LongRef(), null, new Function3() { // from class: androidx.compose.foundation.gestures.DragGestureDetectorKt$detectDragGestures$5
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                long j = ((Offset) obj3).packedValue;
                Function1.this.mo779invoke(Offset.m393boximpl(((PointerInputChange) obj2).position));
                return Unit.INSTANCE;
            }
        }, function2, function02, new Function1() { // from class: androidx.compose.foundation.gestures.DragGestureDetectorKt$detectDragGestures$6
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Function0.this.invoke();
                return Unit.INSTANCE;
            }
        }, null), continuation);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (awaitEachGesture != coroutineSingletons) {
            awaitEachGesture = Unit.INSTANCE;
        }
        return awaitEachGesture == coroutineSingletons ? awaitEachGesture : Unit.INSTANCE;
    }

    public static /* synthetic */ Object detectDragGestures$default(PointerInputScope pointerInputScope, Function1 function1, Function0 function0, Function2 function2, Continuation continuation, int i) {
        if ((i & 1) != 0) {
            function1 = new Function1() { // from class: androidx.compose.foundation.gestures.DragGestureDetectorKt$detectDragGestures$2
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final /* synthetic */ Object mo779invoke(Object obj) {
                    long j = ((Offset) obj).packedValue;
                    return Unit.INSTANCE;
                }
            };
        }
        Function1 function12 = function1;
        if ((i & 2) != 0) {
            function0 = new Function0() { // from class: androidx.compose.foundation.gestures.DragGestureDetectorKt$detectDragGestures$3
                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ Object invoke() {
                    return Unit.INSTANCE;
                }
            };
        }
        return detectDragGestures(pointerInputScope, function12, function0, new Function0() { // from class: androidx.compose.foundation.gestures.DragGestureDetectorKt$detectDragGestures$4
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Unit.INSTANCE;
            }
        }, function2, continuation);
    }

    public static final Object detectDragGesturesAfterLongPress(PointerInputScope pointerInputScope, Function1 function1, Function0 function0, Function0 function02, Function2 function2, Continuation continuation) {
        Object awaitEachGesture = ForEachGestureKt.awaitEachGesture(pointerInputScope, new DragGestureDetectorKt$detectDragGesturesAfterLongPress$5(function1, function0, function02, function2, null), continuation);
        return awaitEachGesture == CoroutineSingletons.COROUTINE_SUSPENDED ? awaitEachGesture : Unit.INSTANCE;
    }

    public static final Object detectHorizontalDragGestures(PointerInputScope pointerInputScope, Function1 function1, Function0 function0, Function0 function02, Function2 function2, Continuation continuation) {
        Object awaitEachGesture = ForEachGestureKt.awaitEachGesture(pointerInputScope, new DragGestureDetectorKt$detectHorizontalDragGestures$5(function1, function2, function0, function02, null), continuation);
        return awaitEachGesture == CoroutineSingletons.COROUTINE_SUSPENDED ? awaitEachGesture : Unit.INSTANCE;
    }

    public static /* synthetic */ Object detectHorizontalDragGestures$default(PointerInputScope pointerInputScope, BasicSwitchKt$$ExternalSyntheticLambda1 basicSwitchKt$$ExternalSyntheticLambda1, BasicSwitchKt$SeslBasicSwitch$4$2$1$$ExternalSyntheticLambda1 basicSwitchKt$SeslBasicSwitch$4$2$1$$ExternalSyntheticLambda1, Function2 function2, Continuation continuation, int i) {
        Function1 function1 = basicSwitchKt$$ExternalSyntheticLambda1;
        if ((i & 1) != 0) {
            function1 = new Function1() { // from class: androidx.compose.foundation.gestures.DragGestureDetectorKt$detectHorizontalDragGestures$2
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final /* synthetic */ Object mo779invoke(Object obj) {
                    long j = ((Offset) obj).packedValue;
                    return Unit.INSTANCE;
                }
            };
        }
        Function1 function12 = function1;
        Function0 function0 = basicSwitchKt$SeslBasicSwitch$4$2$1$$ExternalSyntheticLambda1;
        if ((i & 2) != 0) {
            function0 = new Function0() { // from class: androidx.compose.foundation.gestures.DragGestureDetectorKt$detectHorizontalDragGestures$3
                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ Object invoke() {
                    return Unit.INSTANCE;
                }
            };
        }
        return detectHorizontalDragGestures(pointerInputScope, function12, function0, new Function0() { // from class: androidx.compose.foundation.gestures.DragGestureDetectorKt$detectHorizontalDragGestures$4
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Unit.INSTANCE;
            }
        }, function2, continuation);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0048 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x0046 -> B:10:0x0049). Please report as a decompilation issue!!! */
    /* renamed from: drag-jO51t88, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object m71dragjO51t88(androidx.compose.ui.input.pointer.AwaitPointerEventScope r4, long r5, kotlin.jvm.functions.Function1 r7, kotlin.coroutines.jvm.internal.BaseContinuationImpl r8) {
        /*
            boolean r0 = r8 instanceof androidx.compose.foundation.gestures.DragGestureDetectorKt$drag$1
            if (r0 == 0) goto L13
            r0 = r8
            androidx.compose.foundation.gestures.DragGestureDetectorKt$drag$1 r0 = (androidx.compose.foundation.gestures.DragGestureDetectorKt$drag$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.foundation.gestures.DragGestureDetectorKt$drag$1 r0 = new androidx.compose.foundation.gestures.DragGestureDetectorKt$drag$1
            r0.<init>(r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L39
            if (r2 != r3) goto L31
            java.lang.Object r4 = r0.L$1
            kotlin.jvm.functions.Function1 r4 = (kotlin.jvm.functions.Function1) r4
            java.lang.Object r5 = r0.L$0
            androidx.compose.ui.input.pointer.AwaitPointerEventScope r5 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r5
            kotlin.ResultKt.throwOnFailure(r8)
            r7 = r4
            r4 = r5
            goto L49
        L31:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L39:
            kotlin.ResultKt.throwOnFailure(r8)
        L3c:
            r0.L$0 = r4
            r0.L$1 = r7
            r0.label = r3
            java.lang.Object r8 = m66awaitDragOrCancellationrnUCldI(r4, r5, r0)
            if (r8 != r1) goto L49
            return r1
        L49:
            androidx.compose.ui.input.pointer.PointerInputChange r8 = (androidx.compose.ui.input.pointer.PointerInputChange) r8
            if (r8 != 0) goto L50
            java.lang.Boolean r4 = java.lang.Boolean.FALSE
            return r4
        L50:
            boolean r5 = androidx.compose.ui.input.pointer.PointerEventKt.changedToUpIgnoreConsumed(r8)
            if (r5 == 0) goto L59
            java.lang.Boolean r4 = java.lang.Boolean.TRUE
            return r4
        L59:
            r7.mo779invoke(r8)
            long r5 = r8.id
            goto L3c
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.DragGestureDetectorKt.m71dragjO51t88(androidx.compose.ui.input.pointer.AwaitPointerEventScope, long, kotlin.jvm.functions.Function1, kotlin.coroutines.jvm.internal.BaseContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x010b, code lost:
    
        if (r0 == 0.0f) goto L56;
     */
    /* JADX WARN: Path cross not found for [B:39:0x00c3, B:51:0x00e9], limit reached: 69 */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0080 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00b2 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:34:0x0081 -> B:10:0x0087). Please report as a decompilation issue!!! */
    /* renamed from: horizontalDrag-jO51t88, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object m72horizontalDragjO51t88(androidx.compose.ui.input.pointer.AwaitPointerEventScope r17, long r18, kotlin.jvm.functions.Function1 r20, kotlin.coroutines.jvm.internal.BaseContinuationImpl r21) {
        /*
            Method dump skipped, instructions count: 314
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.DragGestureDetectorKt.m72horizontalDragjO51t88(androidx.compose.ui.input.pointer.AwaitPointerEventScope, long, kotlin.jvm.functions.Function1, kotlin.coroutines.jvm.internal.BaseContinuationImpl):java.lang.Object");
    }

    /* renamed from: isPointerUp-DmW0f2w, reason: not valid java name */
    public static final boolean m73isPointerUpDmW0f2w(PointerEvent pointerEvent, long j) {
        Object obj;
        List list = pointerEvent.changes;
        int size = list.size();
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= size) {
                obj = null;
                break;
            }
            obj = list.get(i);
            if (PointerId.m591equalsimpl0(((PointerInputChange) obj).id, j)) {
                break;
            }
            i++;
        }
        PointerInputChange pointerInputChange = (PointerInputChange) obj;
        if (pointerInputChange != null && pointerInputChange.pressed) {
            z = true;
        }
        return true ^ z;
    }

    /* renamed from: pointerSlop-E8SPZFQ, reason: not valid java name */
    public static final float m74pointerSlopE8SPZFQ(ViewConfiguration viewConfiguration, int i) {
        PointerType.Companion.getClass();
        return i == PointerType.Mouse ? viewConfiguration.getTouchSlop() * mouseToTouchSlopRatio : viewConfiguration.getTouchSlop();
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x010b, code lost:
    
        if (r0 == 0.0f) goto L56;
     */
    /* JADX WARN: Path cross not found for [B:39:0x00c3, B:51:0x00e9], limit reached: 69 */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0080 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00b2 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:34:0x0081 -> B:10:0x0087). Please report as a decompilation issue!!! */
    /* renamed from: verticalDrag-jO51t88, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object m75verticalDragjO51t88(androidx.compose.ui.input.pointer.AwaitPointerEventScope r17, long r18, com.android.compose.gesture.NestedDraggableNode$detectDrags$2$$ExternalSyntheticLambda1 r20, kotlin.coroutines.jvm.internal.BaseContinuationImpl r21) {
        /*
            Method dump skipped, instructions count: 314
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.DragGestureDetectorKt.m75verticalDragjO51t88(androidx.compose.ui.input.pointer.AwaitPointerEventScope, long, com.android.compose.gesture.NestedDraggableNode$detectDrags$2$$ExternalSyntheticLambda1, kotlin.coroutines.jvm.internal.BaseContinuationImpl):java.lang.Object");
    }
}
