package androidx.compose.ui.text.input;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes.dex */
public final class SetSelectionCommand implements EditCommand {
    public final int end;
    public final int start;

    public SetSelectionCommand(int i, int i2) {
        this.start = i;
        this.end = i2;
    }

    @Override // androidx.compose.ui.text.input.EditCommand
    public final void applyTo(EditingBuffer editingBuffer) {
        int iCoerceIn = RangesKt___RangesKt.coerceIn(this.start, 0, editingBuffer.gapBuffer.getLength());
        int iCoerceIn2 = RangesKt___RangesKt.coerceIn(this.end, 0, editingBuffer.gapBuffer.getLength());
        if (iCoerceIn < iCoerceIn2) {
            editingBuffer.setSelection$ui_text_release(iCoerceIn, iCoerceIn2);
        } else {
            editingBuffer.setSelection$ui_text_release(iCoerceIn2, iCoerceIn);
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SetSelectionCommand)) {
            return false;
        }
        SetSelectionCommand setSelectionCommand = (SetSelectionCommand) obj;
        return this.start == setSelectionCommand.start && this.end == setSelectionCommand.end;
    }

    public final int hashCode() {
        return (this.start * 31) + this.end;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SetSelectionCommand(start=");
        sb.append(this.start);
        sb.append(", end=");
        return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.end, ')');
    }
}
