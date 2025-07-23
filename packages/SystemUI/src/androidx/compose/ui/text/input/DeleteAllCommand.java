package androidx.compose.ui.text.input;

import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DeleteAllCommand implements EditCommand {
    @Override // androidx.compose.ui.text.input.EditCommand
    public final void applyTo(EditingBuffer editingBuffer) {
        editingBuffer.replace$ui_text_release(0, editingBuffer.gapBuffer.getLength(), "");
    }

    public final boolean equals(Object obj) {
        return obj instanceof DeleteAllCommand;
    }

    public final int hashCode() {
        return Reflection.getOrCreateKotlinClass(DeleteAllCommand.class).hashCode();
    }

    public final String toString() {
        return "DeleteAllCommand()";
    }
}
