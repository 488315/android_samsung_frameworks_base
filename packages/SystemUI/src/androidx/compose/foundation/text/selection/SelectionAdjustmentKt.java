package androidx.compose.foundation.text.selection;

import androidx.compose.foundation.text.selection.Selection;
import androidx.compose.ui.text.MultiParagraph;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextRange;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SelectionAdjustmentKt {
    public static final Selection access$adjustToBoundaries(SelectionLayout selectionLayout, BoundaryFunction boundaryFunction) {
        SingleSelectionLayout singleSelectionLayout = (SingleSelectionLayout) selectionLayout;
        boolean z = singleSelectionLayout.getCrossStatus() == CrossStatus.CROSSED;
        SelectableInfo selectableInfo = singleSelectionLayout.info;
        return new Selection(anchorOnBoundary(selectableInfo, z, true, singleSelectionLayout.startSlot, boundaryFunction), anchorOnBoundary(selectableInfo, z, false, singleSelectionLayout.endSlot, boundaryFunction), z);
    }

    public static final Selection.AnchorInfo access$updateSelectionBoundary(final SelectionLayout selectionLayout, final SelectableInfo selectableInfo, Selection.AnchorInfo anchorInfo) {
        SingleSelectionLayout singleSelectionLayout = (SingleSelectionLayout) selectionLayout;
        boolean z = singleSelectionLayout.isStartHandle;
        final int i = z ? selectableInfo.rawStartHandleOffset : selectableInfo.rawEndHandleOffset;
        if ((z ? singleSelectionLayout.startSlot : singleSelectionLayout.endSlot) != selectableInfo.slot) {
            return selectableInfo.anchorForOffset(i);
        }
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        final Lazy lazy = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: androidx.compose.foundation.text.selection.SelectionAdjustmentKt$updateSelectionBoundary$currentRawLine$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                TextLayoutResult textLayoutResult = SelectableInfo.this.textLayoutResult;
                return Integer.valueOf(textLayoutResult.multiParagraph.getLineForOffset(i));
            }
        });
        int i2 = selectableInfo.rawStartHandleOffset;
        int i3 = selectableInfo.rawEndHandleOffset;
        final int i4 = z ? i3 : i2;
        Lazy lazy2 = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: androidx.compose.foundation.text.selection.SelectionAdjustmentKt$updateSelectionBoundary$anchorSnappedToWordBoundary$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SelectableInfo selectableInfo2 = SelectableInfo.this;
                int intValue = ((Number) lazy.getValue()).intValue();
                int i5 = i;
                int i6 = i4;
                SingleSelectionLayout singleSelectionLayout2 = (SingleSelectionLayout) selectionLayout;
                boolean z2 = singleSelectionLayout2.isStartHandle;
                boolean z3 = singleSelectionLayout2.getCrossStatus() == CrossStatus.CROSSED;
                long m744getWordBoundaryjx7JFs = selectableInfo2.textLayoutResult.m744getWordBoundaryjx7JFs(i5);
                TextRange.Companion companion = TextRange.Companion;
                int i7 = (int) (m744getWordBoundaryjx7JFs >> 32);
                TextLayoutResult textLayoutResult = selectableInfo2.textLayoutResult;
                int lineForOffset = textLayoutResult.multiParagraph.getLineForOffset(i7);
                MultiParagraph multiParagraph = textLayoutResult.multiParagraph;
                if (lineForOffset != intValue) {
                    int i8 = multiParagraph.lineCount;
                    i7 = intValue >= i8 ? textLayoutResult.getLineStart(i8 - 1) : textLayoutResult.getLineStart(intValue);
                }
                int i9 = (int) (m744getWordBoundaryjx7JFs & 4294967295L);
                if (multiParagraph.getLineForOffset(i9) != intValue) {
                    int i10 = multiParagraph.lineCount;
                    i9 = intValue >= i10 ? multiParagraph.getLineEnd(i10 - 1, false) : multiParagraph.getLineEnd(intValue, false);
                }
                if (i7 == i6) {
                    return selectableInfo2.anchorForOffset(i9);
                }
                if (i9 == i6) {
                    return selectableInfo2.anchorForOffset(i7);
                }
                if (!(z3 ^ z2) ? i5 >= i7 : i5 > i9) {
                    i7 = i9;
                }
                return selectableInfo2.anchorForOffset(i7);
            }
        });
        if (selectableInfo.selectableId != anchorInfo.selectableId) {
            return (Selection.AnchorInfo) lazy2.getValue();
        }
        int i5 = selectableInfo.rawPreviousHandleOffset;
        if (i == i5) {
            return anchorInfo;
        }
        TextLayoutResult textLayoutResult = selectableInfo.textLayoutResult;
        if (((Number) lazy.getValue()).intValue() != textLayoutResult.multiParagraph.getLineForOffset(i5)) {
            return (Selection.AnchorInfo) lazy2.getValue();
        }
        int i6 = anchorInfo.offset;
        long m744getWordBoundaryjx7JFs = textLayoutResult.m744getWordBoundaryjx7JFs(i6);
        if (i5 != -1) {
            if (i != i5) {
                if (!(((i2 < i3 ? CrossStatus.NOT_CROSSED : i2 > i3 ? CrossStatus.CROSSED : CrossStatus.COLLAPSED) == CrossStatus.CROSSED) ^ z)) {
                }
            }
            return selectableInfo.anchorForOffset(i);
        }
        TextRange.Companion companion = TextRange.Companion;
        return (i6 == ((int) (m744getWordBoundaryjx7JFs >> 32)) || i6 == ((int) (4294967295L & m744getWordBoundaryjx7JFs))) ? (Selection.AnchorInfo) lazy2.getValue() : selectableInfo.anchorForOffset(i);
    }

    public static final Selection.AnchorInfo anchorOnBoundary(SelectableInfo selectableInfo, boolean z, boolean z2, int i, BoundaryFunction boundaryFunction) {
        long j;
        int i2 = z2 ? selectableInfo.rawStartHandleOffset : selectableInfo.rawEndHandleOffset;
        if (i != selectableInfo.slot) {
            return selectableInfo.anchorForOffset(i2);
        }
        long mo233getBoundaryfzxv0v0 = boundaryFunction.mo233getBoundaryfzxv0v0(selectableInfo, i2);
        if (z ^ z2) {
            TextRange.Companion companion = TextRange.Companion;
            j = mo233getBoundaryfzxv0v0 >> 32;
        } else {
            TextRange.Companion companion2 = TextRange.Companion;
            j = 4294967295L & mo233getBoundaryfzxv0v0;
        }
        return selectableInfo.anchorForOffset((int) j);
    }

    public static final Selection.AnchorInfo changeOffset(Selection.AnchorInfo anchorInfo, SelectableInfo selectableInfo, int i) {
        return new Selection.AnchorInfo(selectableInfo.textLayoutResult.getBidiRunDirection(i), i, anchorInfo.selectableId);
    }
}
