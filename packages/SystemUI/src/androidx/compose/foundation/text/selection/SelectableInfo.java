package androidx.compose.foundation.text.selection;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.selection.Selection;
import androidx.compose.ui.text.TextLayoutResult;

/* loaded from: classes.dex */
public final class SelectableInfo {
    public final int rawEndHandleOffset;
    public final int rawPreviousHandleOffset;
    public final int rawStartHandleOffset;
    public final long selectableId;
    public final int slot;
    public final TextLayoutResult textLayoutResult;

    public SelectableInfo(long j, int i, int i2, int i3, int i4, TextLayoutResult textLayoutResult) {
        this.selectableId = j;
        this.slot = i;
        this.rawStartHandleOffset = i2;
        this.rawEndHandleOffset = i3;
        this.rawPreviousHandleOffset = i4;
        this.textLayoutResult = textLayoutResult;
    }

    public final Selection.AnchorInfo anchorForOffset(int i) {
        return new Selection.AnchorInfo(SelectionLayoutKt.getTextDirectionForOffset(this.textLayoutResult, i), i, this.selectableId);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SelectionInfo(id=");
        sb.append(this.selectableId);
        sb.append(", range=(");
        int i = this.rawStartHandleOffset;
        sb.append(i);
        sb.append('-');
        TextLayoutResult textLayoutResult = this.textLayoutResult;
        sb.append(SelectionLayoutKt.getTextDirectionForOffset(textLayoutResult, i));
        sb.append(',');
        int i2 = this.rawEndHandleOffset;
        sb.append(i2);
        sb.append('-');
        sb.append(SelectionLayoutKt.getTextDirectionForOffset(textLayoutResult, i2));
        sb.append("), prevOffset=");
        return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.rawPreviousHandleOffset, ')');
    }
}
