package androidx.compose.ui.text.input;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import java.text.BreakIterator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MoveCursorCommand implements EditCommand {
    public final int amount;

    public MoveCursorCommand(int i) {
        this.amount = i;
    }

    @Override // androidx.compose.ui.text.input.EditCommand
    public final void applyTo(EditingBuffer editingBuffer) {
        if (editingBuffer.getCursor$ui_text_release() == -1) {
            int i = editingBuffer.selectionStart;
            editingBuffer.setSelection$ui_text_release(i, i);
        }
        int i2 = editingBuffer.selectionStart;
        String partialGapBuffer = editingBuffer.gapBuffer.toString();
        int i3 = 0;
        int i4 = this.amount;
        if (i4 <= 0) {
            int i5 = -i4;
            while (i3 < i5) {
                BreakIterator characterInstance = BreakIterator.getCharacterInstance();
                characterInstance.setText(partialGapBuffer);
                int preceding = characterInstance.preceding(i2);
                if (preceding == -1) {
                    break;
                }
                i3++;
                i2 = preceding;
            }
        } else {
            while (i3 < i4) {
                BreakIterator characterInstance2 = BreakIterator.getCharacterInstance();
                characterInstance2.setText(partialGapBuffer);
                int following = characterInstance2.following(i2);
                if (following == -1) {
                    break;
                }
                i3++;
                i2 = following;
            }
        }
        editingBuffer.setSelection$ui_text_release(i2, i2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof MoveCursorCommand) {
            return this.amount == ((MoveCursorCommand) obj).amount;
        }
        return false;
    }

    public final int hashCode() {
        return this.amount;
    }

    public final String toString() {
        return BackEventCompat$$ExternalSyntheticOutline0.m(new StringBuilder("MoveCursorCommand(amount="), this.amount, ')');
    }
}
