package androidx.compose.foundation.gestures;

import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$LongRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DragGestureDetectorKt$detectDragGestures$9 extends RestrictedSuspendLambda implements Function2 {
    final /* synthetic */ Function2 $onDrag;
    final /* synthetic */ Function0 $onDragCancel;
    final /* synthetic */ Function1 $onDragEnd;
    final /* synthetic */ Function3 $onDragStart;
    final /* synthetic */ Orientation $orientationLock;
    final /* synthetic */ Ref$LongRef $overSlop;
    final /* synthetic */ Function0 $shouldAwaitTouchSlop;
    float F$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    Object L$4;
    Object L$5;
    Object L$6;
    boolean Z$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DragGestureDetectorKt$detectDragGestures$9(Function0 function0, Ref$LongRef ref$LongRef, Orientation orientation, Function3 function3, Function2 function2, Function0 function02, Function1 function1, Continuation continuation) {
        super(2, continuation);
        this.$shouldAwaitTouchSlop = function0;
        this.$overSlop = ref$LongRef;
        this.$orientationLock = orientation;
        this.$onDragStart = function3;
        this.$onDrag = function2;
        this.$onDragCancel = function02;
        this.$onDragEnd = function1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DragGestureDetectorKt$detectDragGestures$9 dragGestureDetectorKt$detectDragGestures$9 = new DragGestureDetectorKt$detectDragGestures$9(this.$shouldAwaitTouchSlop, this.$overSlop, this.$orientationLock, this.$onDragStart, this.$onDrag, this.$onDragCancel, this.$onDragEnd, continuation);
        dragGestureDetectorKt$detectDragGestures$9.L$0 = obj;
        return dragGestureDetectorKt$detectDragGestures$9;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DragGestureDetectorKt$detectDragGestures$9) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:122:0x04ad, code lost:
    
        if (androidx.compose.foundation.gestures.DragGestureDetectorKt.m73isPointerUpDmW0f2w(r4, r5) != false) goto L163;
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x0464, code lost:
    
        if (r8.awaitPointerEvent(r3, r22) == r1) goto L167;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x01e3, code lost:
    
        if (r13 == r1) goto L167;
     */
    /* JADX WARN: Code restructure failed: missing block: B:207:0x019b, code lost:
    
        if (r2 != false) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:214:0x018c, code lost:
    
        if (r9 == r1) goto L167;
     */
    /* JADX WARN: Code restructure failed: missing block: B:216:0x0168, code lost:
    
        if (r7 == r1) goto L167;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x04d4, code lost:
    
        if (r3 != r1) goto L168;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0556, code lost:
    
        if (r9 == 0.0f) goto L165;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x03b3, code lost:
    
        if (r3 == r1) goto L167;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0303, code lost:
    
        if (r3 == r1) goto L167;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Path cross not found for [B:35:0x050b, B:48:0x0533], limit reached: 217 */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0485  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x02c0  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x02b2  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x01b2  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x01b5  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0570  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0576  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x02cd A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x02df  */
    /* JADX WARN: Type inference failed for: r12v18, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r12v25 */
    /* JADX WARN: Type inference failed for: r12v26 */
    /* JADX WARN: Type inference failed for: r12v30, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r13v21, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r21v0 */
    /* JADX WARN: Type inference failed for: r21v1 */
    /* JADX WARN: Type inference failed for: r21v2, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v37, types: [kotlin.jvm.functions.Function3] */
    /* JADX WARN: Type inference failed for: r2v38, types: [kotlin.jvm.functions.Function2] */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v14, types: [androidx.compose.ui.input.pointer.PointerInputChange] */
    /* JADX WARN: Type inference failed for: r7v17 */
    /* JADX WARN: Type inference failed for: r7v29 */
    /* JADX WARN: Type inference failed for: r7v31, types: [androidx.compose.ui.input.pointer.PointerInputChange, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r7v33 */
    /* JADX WARN: Type inference failed for: r7v35 */
    /* JADX WARN: Type inference failed for: r7v37 */
    /* JADX WARN: Type inference failed for: r7v41 */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r7v50 */
    /* JADX WARN: Type inference failed for: r7v51 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:100:0x037c -> B:70:0x037e). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:101:0x0384 -> B:60:0x039c). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:104:0x0479 -> B:72:0x02c9). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:120:0x0417 -> B:59:0x0446). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:128:0x043d -> B:59:0x0446). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:130:0x0464 -> B:57:0x0468). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:175:0x01b2 -> B:169:0x02b0). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:176:0x01b5 -> B:140:0x01cc). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:180:0x02a4 -> B:137:0x02a8). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:29:0x04d4 -> B:7:0x04d7). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:89:0x0335 -> B:80:0x02eb). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r23) {
        /*
            Method dump skipped, instructions count: 1452
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.DragGestureDetectorKt$detectDragGestures$9.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
