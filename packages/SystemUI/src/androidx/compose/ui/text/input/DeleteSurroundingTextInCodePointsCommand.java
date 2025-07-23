package androidx.compose.ui.text.input;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.internal.InlineClassHelperKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DeleteSurroundingTextInCodePointsCommand implements EditCommand {
    public final int lengthAfterCursor;
    public final int lengthBeforeCursor;

    public DeleteSurroundingTextInCodePointsCommand(int i, int i2) {
        this.lengthBeforeCursor = i;
        this.lengthAfterCursor = i2;
        if (i >= 0 && i2 >= 0) {
            return;
        }
        InlineClassHelperKt.throwIllegalArgumentException("Expected lengthBeforeCursor and lengthAfterCursor to be non-negative, were " + i + " and " + i2 + " respectively.");
    }

    @Override // androidx.compose.ui.text.input.EditCommand
    public final void applyTo(EditingBuffer editingBuffer) {
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i2 < this.lengthBeforeCursor) {
                int i4 = i3 + 1;
                int i5 = editingBuffer.selectionStart;
                if (i5 <= i4) {
                    i3 = i5;
                    break;
                } else {
                    i3 = (Character.isHighSurrogate(editingBuffer.get$ui_text_release((i5 - i4) + (-1))) && Character.isLowSurrogate(editingBuffer.get$ui_text_release(editingBuffer.selectionStart - i4))) ? i3 + 2 : i4;
                    i2++;
                }
            } else {
                break;
            }
        }
        int i6 = 0;
        while (true) {
            if (i >= this.lengthAfterCursor) {
                break;
            }
            int i7 = i6 + 1;
            int i8 = editingBuffer.selectionEnd + i7;
            PartialGapBuffer partialGapBuffer = editingBuffer.gapBuffer;
            if (i8 >= partialGapBuffer.getLength()) {
                i6 = partialGapBuffer.getLength() - editingBuffer.selectionEnd;
                break;
            } else {
                i6 = (Character.isHighSurrogate(editingBuffer.get$ui_text_release((editingBuffer.selectionEnd + i7) + (-1))) && Character.isLowSurrogate(editingBuffer.get$ui_text_release(editingBuffer.selectionEnd + i7))) ? i6 + 2 : i7;
                i++;
            }
        }
        int i9 = editingBuffer.selectionEnd;
        editingBuffer.delete$ui_text_release(i9, i6 + i9);
        int i10 = editingBuffer.selectionStart;
        editingBuffer.delete$ui_text_release(i10 - i3, i10);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DeleteSurroundingTextInCodePointsCommand)) {
            return false;
        }
        DeleteSurroundingTextInCodePointsCommand deleteSurroundingTextInCodePointsCommand = (DeleteSurroundingTextInCodePointsCommand) obj;
        return this.lengthBeforeCursor == deleteSurroundingTextInCodePointsCommand.lengthBeforeCursor && this.lengthAfterCursor == deleteSurroundingTextInCodePointsCommand.lengthAfterCursor;
    }

    public final int hashCode() {
        return (this.lengthBeforeCursor * 31) + this.lengthAfterCursor;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("DeleteSurroundingTextInCodePointsCommand(lengthBeforeCursor=");
        sb.append(this.lengthBeforeCursor);
        sb.append(", lengthAfterCursor=");
        return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.lengthAfterCursor, ')');
    }
}
