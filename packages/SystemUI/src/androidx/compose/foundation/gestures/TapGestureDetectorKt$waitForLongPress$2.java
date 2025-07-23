package androidx.compose.foundation.gestures;

import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerEventPass;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class TapGestureDetectorKt$waitForLongPress$2 extends RestrictedSuspendLambda implements Function2 {
    final /* synthetic */ PointerEventPass $pass;
    final /* synthetic */ Ref$ObjectRef<LongPressResult> $result;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TapGestureDetectorKt$waitForLongPress$2(PointerEventPass pointerEventPass, Ref$ObjectRef<LongPressResult> ref$ObjectRef, Continuation continuation) {
        super(2, continuation);
        this.$pass = pointerEventPass;
        this.$result = ref$ObjectRef;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TapGestureDetectorKt$waitForLongPress$2 tapGestureDetectorKt$waitForLongPress$2 = new TapGestureDetectorKt$waitForLongPress$2(this.$pass, this.$result, continuation);
        tapGestureDetectorKt$waitForLongPress$2.L$0 = obj;
        return tapGestureDetectorKt$waitForLongPress$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TapGestureDetectorKt$waitForLongPress$2) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x00cb, code lost:
    
        r13.$result.element = androidx.compose.foundation.gestures.LongPressResult.Canceled.INSTANCE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0058, code lost:
    
        r5 = r14.getMotionEvent$ui_release();
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x005c, code lost:
    
        if (r5 == null) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x005e, code lost:
    
        r5 = r5.getClassification();
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0064, code lost:
    
        if (r5 != 2) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0066, code lost:
    
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0069, code lost:
    
        if (r5 == false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x006b, code lost:
    
        r13.$result.element = androidx.compose.foundation.gestures.LongPressResult.Success.INSTANCE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0073, code lost:
    
        r14 = r14.changes;
        r5 = r14.size();
        r6 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x007d, code lost:
    
        if (r6 >= r5) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x007f, code lost:
    
        r7 = (androidx.compose.ui.input.pointer.PointerInputChange) r14.get(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0089, code lost:
    
        if (r7.isConsumed() != false) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0097, code lost:
    
        if (androidx.compose.ui.input.pointer.PointerEventKt.m589isOutOfBoundsjwHxaWs(r7, r1.mo585getSizeYbymL2g(), r1.mo584getExtendedTouchPaddingNHjbRc()) == false) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x009a, code lost:
    
        r6 = r6 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x009d, code lost:
    
        r13.$result.element = androidx.compose.foundation.gestures.LongPressResult.Canceled.INSTANCE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00a4, code lost:
    
        r14 = androidx.compose.ui.input.pointer.PointerEventPass.Final;
        r13.L$0 = r1;
        r13.label = 2;
        r14 = r1.awaitPointerEvent(r14, r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00ae, code lost:
    
        if (r14 != r0) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0068, code lost:
    
        r5 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0063, code lost:
    
        r5 = 0;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00dc A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v1, types: [T, androidx.compose.foundation.gestures.LongPressResult$Released] */
    /* JADX WARN: Type inference failed for: r14v11, types: [T, androidx.compose.foundation.gestures.LongPressResult$Canceled] */
    /* JADX WARN: Type inference failed for: r14v12, types: [T, androidx.compose.foundation.gestures.LongPressResult$Success] */
    /* JADX WARN: Type inference failed for: r14v19, types: [T, androidx.compose.foundation.gestures.LongPressResult$Canceled] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:39:0x00ae -> B:6:0x00b1). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r14) {
        /*
            Method dump skipped, instructions count: 240
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.TapGestureDetectorKt$waitForLongPress$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
