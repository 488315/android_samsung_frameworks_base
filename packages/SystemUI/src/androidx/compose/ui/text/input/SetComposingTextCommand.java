package androidx.compose.ui.text.input;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.AnnotatedString;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SetComposingTextCommand implements EditCommand {
    public final AnnotatedString annotatedString;
    public final int newCursorPosition;

    public SetComposingTextCommand(AnnotatedString annotatedString, int i) {
        this.annotatedString = annotatedString;
        this.newCursorPosition = i;
    }

    @Override // androidx.compose.ui.text.input.EditCommand
    public final void applyTo(EditingBuffer editingBuffer) {
        boolean hasComposition$ui_text_release = editingBuffer.hasComposition$ui_text_release();
        AnnotatedString annotatedString = this.annotatedString;
        if (hasComposition$ui_text_release) {
            int i = editingBuffer.compositionStart;
            editingBuffer.replace$ui_text_release(i, editingBuffer.compositionEnd, annotatedString.text);
            if (annotatedString.text.length() > 0) {
                editingBuffer.setComposition$ui_text_release(i, annotatedString.text.length() + i);
            }
        } else {
            int i2 = editingBuffer.selectionStart;
            editingBuffer.replace$ui_text_release(i2, editingBuffer.selectionEnd, annotatedString.text);
            if (annotatedString.text.length() > 0) {
                editingBuffer.setComposition$ui_text_release(i2, annotatedString.text.length() + i2);
            }
        }
        int cursor$ui_text_release = editingBuffer.getCursor$ui_text_release();
        int i3 = this.newCursorPosition;
        int coerceIn = RangesKt___RangesKt.coerceIn(i3 > 0 ? (cursor$ui_text_release + i3) - 1 : (cursor$ui_text_release + i3) - annotatedString.text.length(), 0, editingBuffer.gapBuffer.getLength());
        editingBuffer.setSelection$ui_text_release(coerceIn, coerceIn);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SetComposingTextCommand)) {
            return false;
        }
        SetComposingTextCommand setComposingTextCommand = (SetComposingTextCommand) obj;
        return Intrinsics.areEqual(this.annotatedString.text, setComposingTextCommand.annotatedString.text) && this.newCursorPosition == setComposingTextCommand.newCursorPosition;
    }

    public final int hashCode() {
        return (this.annotatedString.text.hashCode() * 31) + this.newCursorPosition;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SetComposingTextCommand(text='");
        sb.append(this.annotatedString.text);
        sb.append("', newCursorPosition=");
        return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.newCursorPosition, ')');
    }

    public SetComposingTextCommand(String str, int i) {
        this(new AnnotatedString(str, null, 2, null), i);
    }
}
