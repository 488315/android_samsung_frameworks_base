package androidx.compose.ui.text.input;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.AnnotatedString;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CommitTextCommand implements EditCommand {
    public final AnnotatedString annotatedString;
    public final int newCursorPosition;

    public CommitTextCommand(AnnotatedString annotatedString, int i) {
        this.annotatedString = annotatedString;
        this.newCursorPosition = i;
    }

    @Override // androidx.compose.ui.text.input.EditCommand
    public final void applyTo(EditingBuffer editingBuffer) {
        boolean hasComposition$ui_text_release = editingBuffer.hasComposition$ui_text_release();
        AnnotatedString annotatedString = this.annotatedString;
        if (hasComposition$ui_text_release) {
            editingBuffer.replace$ui_text_release(editingBuffer.compositionStart, editingBuffer.compositionEnd, annotatedString.text);
        } else {
            editingBuffer.replace$ui_text_release(editingBuffer.selectionStart, editingBuffer.selectionEnd, annotatedString.text);
        }
        int cursor$ui_text_release = editingBuffer.getCursor$ui_text_release();
        int i = this.newCursorPosition;
        int coerceIn = RangesKt___RangesKt.coerceIn(i > 0 ? (cursor$ui_text_release + i) - 1 : (cursor$ui_text_release + i) - annotatedString.text.length(), 0, editingBuffer.gapBuffer.getLength());
        editingBuffer.setSelection$ui_text_release(coerceIn, coerceIn);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CommitTextCommand)) {
            return false;
        }
        CommitTextCommand commitTextCommand = (CommitTextCommand) obj;
        return Intrinsics.areEqual(this.annotatedString.text, commitTextCommand.annotatedString.text) && this.newCursorPosition == commitTextCommand.newCursorPosition;
    }

    public final int hashCode() {
        return (this.annotatedString.text.hashCode() * 31) + this.newCursorPosition;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("CommitTextCommand(text='");
        sb.append(this.annotatedString.text);
        sb.append("', newCursorPosition=");
        return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.newCursorPosition, ')');
    }

    public CommitTextCommand(String str, int i) {
        this(new AnnotatedString(str, null, 2, null), i);
    }
}
