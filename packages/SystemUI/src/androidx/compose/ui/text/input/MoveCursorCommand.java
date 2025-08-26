package androidx.compose.ui.text.input;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import java.text.BreakIterator;

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
        String string = editingBuffer.gapBuffer.toString();
        int i3 = 0;
        int i4 = this.amount;
        if (i4 <= 0) {
            int i5 = -i4;
            while (i3 < i5) {
                BreakIterator characterInstance = BreakIterator.getCharacterInstance();
                characterInstance.setText(string);
                int iPreceding = characterInstance.preceding(i2);
                if (iPreceding == -1) {
                    break;
                }
                i3++;
                i2 = iPreceding;
            }
        } else {
            while (i3 < i4) {
                BreakIterator characterInstance2 = BreakIterator.getCharacterInstance();
                characterInstance2.setText(string);
                int iFollowing = characterInstance2.following(i2);
                if (iFollowing == -1) {
                    break;
                }
                i3++;
                i2 = iFollowing;
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
