package androidx.compose.foundation.text;

import androidx.compose.ui.input.key.KeyEvent;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final /* synthetic */ class TextFieldKeyInputKt$textFieldKeyInput$2$1$1 extends FunctionReferenceImpl implements Function1 {
    public TextFieldKeyInputKt$textFieldKeyInput$2$1$1(Object obj) {
        super(1, obj, TextFieldKeyInput.class, "process", "process-ZmokQxo(Landroid/view/KeyEvent;)Z", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final /* synthetic */ Object mo779invoke(Object obj) {
        return m206invokeZmokQxo(((KeyEvent) obj).nativeKeyEvent);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0082  */
    /* renamed from: invoke-ZmokQxo, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Boolean m206invokeZmokQxo(android.view.KeyEvent r10) {
        /*
            r9 = this;
            java.lang.Object r9 = r9.receiver
            androidx.compose.foundation.text.TextFieldKeyInput r9 = (androidx.compose.foundation.text.TextFieldKeyInput) r9
            r9.getClass()
            int r0 = r10.getAction()
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L6c
            int r0 = r10.getUnicodeChar()
            boolean r0 = java.lang.Character.isISOControl(r0)
            if (r0 != 0) goto L6c
            androidx.compose.foundation.text.DeadKeyCombiner r0 = r9.keyCombiner
            r0.getClass()
            int r3 = r10.getUnicodeChar()
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r4 & r3
            if (r4 == 0) goto L33
            r4 = 2147483647(0x7fffffff, float:NaN)
            r3 = r3 & r4
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r0.deadKeyCode = r3
            r4 = r1
            goto L53
        L33:
            java.lang.Integer r4 = r0.deadKeyCode
            if (r4 == 0) goto L4f
            r0.deadKeyCode = r1
            int r0 = r4.intValue()
            int r0 = android.view.KeyCharacterMap.getDeadChar(r0, r3)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r0)
            if (r0 != 0) goto L48
            r4 = r1
        L48:
            if (r4 != 0) goto L53
            java.lang.Integer r4 = java.lang.Integer.valueOf(r3)
            goto L53
        L4f:
            java.lang.Integer r4 = java.lang.Integer.valueOf(r3)
        L53:
            if (r4 == 0) goto L6c
            int r0 = r4.intValue()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.StringBuilder r0 = r3.appendCodePoint(r0)
            java.lang.String r0 = r0.toString()
            androidx.compose.ui.text.input.CommitTextCommand r3 = new androidx.compose.ui.text.input.CommitTextCommand
            r3.<init>(r0, r2)
            goto L6d
        L6c:
            r3 = r1
        L6d:
            androidx.compose.foundation.text.selection.TextPreparedSelectionState r0 = r9.preparedSelectionState
            boolean r4 = r9.editable
            r5 = 0
            if (r3 == 0) goto L82
            if (r4 == 0) goto L80
            java.util.List r10 = java.util.Collections.singletonList(r3)
            r9.apply(r10)
            r0.cachedX = r1
            goto Lea
        L80:
            r2 = r5
            goto Lea
        L82:
            int r1 = androidx.compose.ui.input.key.KeyEvent_androidKt.m579getTypeZmokQxo(r10)
            androidx.compose.ui.input.key.KeyEventType$Companion r3 = androidx.compose.ui.input.key.KeyEventType.Companion
            r3.getClass()
            int r3 = androidx.compose.ui.input.key.KeyEventType.KeyDown
            if (r1 != r3) goto L80
            androidx.compose.foundation.text.KeyMapping r1 = r9.keyMapping
            androidx.compose.foundation.text.KeyCommand r10 = r1.mo198mapZmokQxo(r10)
            if (r10 == 0) goto L80
            boolean r1 = r10.getEditsText()
            if (r1 == 0) goto La0
            if (r4 != 0) goto La0
            goto L80
        La0:
            kotlin.jvm.internal.Ref$BooleanRef r1 = new kotlin.jvm.internal.Ref$BooleanRef
            r1.<init>()
            r1.element = r2
            androidx.compose.foundation.text.TextFieldKeyInput$process$2 r3 = new androidx.compose.foundation.text.TextFieldKeyInput$process$2
            r3.<init>()
            androidx.compose.foundation.text.selection.TextFieldPreparedSelection r10 = new androidx.compose.foundation.text.selection.TextFieldPreparedSelection
            androidx.compose.foundation.text.LegacyTextFieldState r4 = r9.state
            androidx.compose.foundation.text.TextLayoutResultProxy r4 = r4.getLayoutResult()
            androidx.compose.ui.text.input.OffsetMapping r5 = r9.offsetMapping
            androidx.compose.ui.text.input.TextFieldValue r6 = r9.value
            r10.<init>(r6, r5, r4, r0)
            r3.mo779invoke(r10)
            long r3 = r10.selection
            long r7 = r6.selection
            boolean r0 = androidx.compose.ui.text.TextRange.m746equalsimpl0(r3, r7)
            if (r0 == 0) goto Ld2
            androidx.compose.ui.text.AnnotatedString r0 = r10.annotatedString
            androidx.compose.ui.text.AnnotatedString r3 = r6.annotatedString
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r3)
            if (r0 != 0) goto Le2
        Ld2:
            long r3 = r10.selection
            androidx.compose.ui.text.AnnotatedString r0 = r10.annotatedString
            r5 = 4
            androidx.compose.ui.text.input.TextFieldValue r10 = r10.currentValue
            androidx.compose.ui.text.input.TextFieldValue r10 = androidx.compose.ui.text.input.TextFieldValue.m778copy3r_uNRQ$default(r10, r0, r3, r5)
            kotlin.jvm.functions.Function1 r0 = r9.onValueChange
            r0.mo779invoke(r10)
        Le2:
            androidx.compose.foundation.text.UndoManager r9 = r9.undoManager
            if (r9 == 0) goto Le8
            r9.forceNextSnapshot = r2
        Le8:
            boolean r2 = r1.element
        Lea:
            java.lang.Boolean r9 = java.lang.Boolean.valueOf(r2)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.TextFieldKeyInputKt$textFieldKeyInput$2$1$1.m206invokeZmokQxo(android.view.KeyEvent):java.lang.Boolean");
    }
}
