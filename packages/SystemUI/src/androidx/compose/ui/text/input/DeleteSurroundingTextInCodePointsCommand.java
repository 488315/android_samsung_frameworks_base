package androidx.compose.ui.text.input;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.internal.InlineClassHelperKt;

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
        int length = 0;
        while (true) {
            if (i >= this.lengthAfterCursor) {
                break;
            }
            int i6 = length + 1;
            int i7 = editingBuffer.selectionEnd + i6;
            PartialGapBuffer partialGapBuffer = editingBuffer.gapBuffer;
            if (i7 >= partialGapBuffer.getLength()) {
                length = partialGapBuffer.getLength() - editingBuffer.selectionEnd;
                break;
            } else {
                length = (Character.isHighSurrogate(editingBuffer.get$ui_text_release((editingBuffer.selectionEnd + i6) + (-1))) && Character.isLowSurrogate(editingBuffer.get$ui_text_release(editingBuffer.selectionEnd + i6))) ? length + 2 : i6;
                i++;
            }
        }
        int i8 = editingBuffer.selectionEnd;
        editingBuffer.delete$ui_text_release(i8, length + i8);
        int i9 = editingBuffer.selectionStart;
        editingBuffer.delete$ui_text_release(i9 - i3, i9);
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
