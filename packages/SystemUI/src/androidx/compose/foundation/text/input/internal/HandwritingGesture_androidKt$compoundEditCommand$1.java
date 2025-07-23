package androidx.compose.foundation.text.input.internal;

import androidx.compose.ui.text.input.EditCommand;
import androidx.compose.ui.text.input.EditingBuffer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class HandwritingGesture_androidKt$compoundEditCommand$1 implements EditCommand {
    public final /* synthetic */ EditCommand[] $editCommands;

    public HandwritingGesture_androidKt$compoundEditCommand$1(EditCommand[] editCommandArr) {
        this.$editCommands = editCommandArr;
    }

    @Override // androidx.compose.ui.text.input.EditCommand
    public final void applyTo(EditingBuffer editingBuffer) {
        for (EditCommand editCommand : this.$editCommands) {
            editCommand.applyTo(editingBuffer);
        }
    }
}
