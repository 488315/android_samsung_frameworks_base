package androidx.compose.ui.text.input;

import java.text.BreakIterator;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes.dex */
public final class BackspaceCommand implements EditCommand {
    @Override // androidx.compose.ui.text.input.EditCommand
    public final void applyTo(EditingBuffer editingBuffer) {
        if (editingBuffer.hasComposition$ui_text_release()) {
            editingBuffer.delete$ui_text_release(editingBuffer.compositionStart, editingBuffer.compositionEnd);
            return;
        }
        if (editingBuffer.getCursor$ui_text_release() == -1) {
            int i = editingBuffer.selectionStart;
            int i2 = editingBuffer.selectionEnd;
            editingBuffer.setSelection$ui_text_release(i, i);
            editingBuffer.delete$ui_text_release(i, i2);
            return;
        }
        if (editingBuffer.getCursor$ui_text_release() == 0) {
            return;
        }
        String string = editingBuffer.gapBuffer.toString();
        int cursor$ui_text_release = editingBuffer.getCursor$ui_text_release();
        BreakIterator characterInstance = BreakIterator.getCharacterInstance();
        characterInstance.setText(string);
        editingBuffer.delete$ui_text_release(characterInstance.preceding(cursor$ui_text_release), editingBuffer.getCursor$ui_text_release());
    }

    public final boolean equals(Object obj) {
        return obj instanceof BackspaceCommand;
    }

    public final int hashCode() {
        return Reflection.getOrCreateKotlinClass(BackspaceCommand.class).hashCode();
    }

    public final String toString() {
        return "BackspaceCommand()";
    }
}
