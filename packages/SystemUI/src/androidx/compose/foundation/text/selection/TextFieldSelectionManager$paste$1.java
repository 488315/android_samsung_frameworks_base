package androidx.compose.foundation.text.selection;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class TextFieldSelectionManager$paste$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ TextFieldSelectionManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TextFieldSelectionManager$paste$1(TextFieldSelectionManager textFieldSelectionManager, Continuation continuation) {
        super(2, continuation);
        this.this$0 = textFieldSelectionManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TextFieldSelectionManager$paste$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TextFieldSelectionManager$paste$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0048, code lost:
    
        if (r7 == r0) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x004a, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x003b, code lost:
    
        if (r7 == r0) goto L22;
     */
    /* JADX WARN: Type inference failed for: r0v7, types: [kotlin.jvm.functions.Function1, kotlin.jvm.internal.Lambda] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            r6 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r6.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L1c
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r7)
            goto L4b
        L10:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L18:
            kotlin.ResultKt.throwOnFailure(r7)
            goto L3e
        L1c:
            kotlin.ResultKt.throwOnFailure(r7)
            androidx.compose.foundation.text.selection.TextFieldSelectionManager r7 = r6.this$0
            androidx.compose.ui.platform.Clipboard r7 = r7.clipboard
            if (r7 == 0) goto Ld2
            r6.label = r3
            androidx.compose.ui.platform.AndroidClipboard r7 = (androidx.compose.ui.platform.AndroidClipboard) r7
            androidx.compose.ui.platform.AndroidClipboardManager r7 = r7.androidClipboardManager
            android.content.ClipboardManager r7 = r7.clipboardManager
            android.content.ClipData r7 = r7.getPrimaryClip()
            if (r7 == 0) goto L3a
            androidx.compose.ui.platform.ClipEntry r1 = new androidx.compose.ui.platform.ClipEntry
            r1.<init>(r7)
            r7 = r1
            goto L3b
        L3a:
            r7 = 0
        L3b:
            if (r7 != r0) goto L3e
            goto L4a
        L3e:
            androidx.compose.ui.platform.ClipEntry r7 = (androidx.compose.ui.platform.ClipEntry) r7
            if (r7 == 0) goto Ld2
            r6.label = r2
            androidx.compose.ui.text.AnnotatedString r7 = androidx.compose.foundation.internal.ClipboardUtils_androidKt.readAnnotatedString(r7)
            if (r7 != r0) goto L4b
        L4a:
            return r0
        L4b:
            androidx.compose.ui.text.AnnotatedString r7 = (androidx.compose.ui.text.AnnotatedString) r7
            if (r7 != 0) goto L51
            goto Ld2
        L51:
            androidx.compose.foundation.text.selection.TextFieldSelectionManager r0 = r6.this$0
            androidx.compose.ui.text.input.TextFieldValue r0 = r0.getValue$foundation_release()
            androidx.compose.foundation.text.selection.TextFieldSelectionManager r1 = r6.this$0
            androidx.compose.ui.text.input.TextFieldValue r1 = r1.getValue$foundation_release()
            androidx.compose.ui.text.AnnotatedString r1 = r1.annotatedString
            java.lang.String r1 = r1.text
            int r1 = r1.length()
            androidx.compose.ui.text.AnnotatedString r0 = androidx.compose.ui.text.input.TextFieldValueKt.getTextBeforeSelection(r0, r1)
            androidx.compose.ui.text.AnnotatedString$Builder r1 = new androidx.compose.ui.text.AnnotatedString$Builder
            r1.<init>(r0)
            r1.append(r7)
            androidx.compose.ui.text.AnnotatedString r0 = r1.toAnnotatedString()
            androidx.compose.foundation.text.selection.TextFieldSelectionManager r1 = r6.this$0
            androidx.compose.ui.text.input.TextFieldValue r1 = r1.getValue$foundation_release()
            androidx.compose.foundation.text.selection.TextFieldSelectionManager r2 = r6.this$0
            androidx.compose.ui.text.input.TextFieldValue r2 = r2.getValue$foundation_release()
            androidx.compose.ui.text.AnnotatedString r2 = r2.annotatedString
            java.lang.String r2 = r2.text
            int r2 = r2.length()
            androidx.compose.ui.text.AnnotatedString r1 = androidx.compose.ui.text.input.TextFieldValueKt.getTextAfterSelection(r1, r2)
            androidx.compose.ui.text.AnnotatedString$Builder r2 = new androidx.compose.ui.text.AnnotatedString$Builder
            r2.<init>(r0)
            r2.append(r1)
            androidx.compose.ui.text.AnnotatedString r0 = r2.toAnnotatedString()
            androidx.compose.foundation.text.selection.TextFieldSelectionManager r1 = r6.this$0
            androidx.compose.ui.text.input.TextFieldValue r1 = r1.getValue$foundation_release()
            long r1 = r1.selection
            int r1 = androidx.compose.ui.text.TextRange.m750getMinimpl(r1)
            java.lang.String r7 = r7.text
            int r7 = r7.length()
            int r7 = r7 + r1
            androidx.compose.foundation.text.selection.TextFieldSelectionManager r1 = r6.this$0
            long r4 = androidx.compose.ui.text.TextRangeKt.TextRange(r7, r7)
            r1.getClass()
            androidx.compose.ui.text.input.TextFieldValue r7 = androidx.compose.foundation.text.selection.TextFieldSelectionManager.m237createTextFieldValueFDrldGo(r0, r4)
            androidx.compose.foundation.text.selection.TextFieldSelectionManager r0 = r6.this$0
            kotlin.jvm.internal.Lambda r0 = r0.onValueChange
            r0.mo779invoke(r7)
            androidx.compose.foundation.text.selection.TextFieldSelectionManager r7 = r6.this$0
            androidx.compose.foundation.text.HandleState r0 = androidx.compose.foundation.text.HandleState.None
            r7.setHandleState(r0)
            androidx.compose.foundation.text.selection.TextFieldSelectionManager r6 = r6.this$0
            androidx.compose.foundation.text.UndoManager r6 = r6.undoManager
            if (r6 == 0) goto Lcf
            r6.forceNextSnapshot = r3
        Lcf:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        Ld2:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.selection.TextFieldSelectionManager$paste$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
