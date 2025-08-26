package androidx.compose.foundation.text.selection;

import androidx.compose.foundation.text.selection.Selection;
import androidx.compose.ui.text.MultiParagraph;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextRange;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.jvm.functions.Function0;

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
                TextLayoutResult textLayoutResult = selectableInfo.textLayoutResult;
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
                SelectableInfo selectableInfo2 = selectableInfo;
                int iIntValue = ((Number) lazy.getValue()).intValue();
                int i5 = i;
                int i6 = i4;
                SingleSelectionLayout singleSelectionLayout2 = (SingleSelectionLayout) selectionLayout;
                boolean z2 = singleSelectionLayout2.isStartHandle;
                boolean z3 = singleSelectionLayout2.getCrossStatus() == CrossStatus.CROSSED;
                long jM746getWordBoundaryjx7JFs = selectableInfo2.textLayoutResult.m746getWordBoundaryjx7JFs(i5);
                TextRange.Companion companion = TextRange.Companion;
                int lineStart = (int) (jM746getWordBoundaryjx7JFs >> 32);
                TextLayoutResult textLayoutResult = selectableInfo2.textLayoutResult;
                int lineForOffset = textLayoutResult.multiParagraph.getLineForOffset(lineStart);
                MultiParagraph multiParagraph = textLayoutResult.multiParagraph;
                if (lineForOffset != iIntValue) {
                    int i7 = multiParagraph.lineCount;
                    lineStart = iIntValue >= i7 ? textLayoutResult.getLineStart(i7 - 1) : textLayoutResult.getLineStart(iIntValue);
                }
                int lineEnd = (int) (jM746getWordBoundaryjx7JFs & 4294967295L);
                if (multiParagraph.getLineForOffset(lineEnd) != iIntValue) {
                    int i8 = multiParagraph.lineCount;
                    lineEnd = iIntValue >= i8 ? multiParagraph.getLineEnd(i8 - 1, false) : multiParagraph.getLineEnd(iIntValue, false);
                }
                if (lineStart == i6) {
                    return selectableInfo2.anchorForOffset(lineEnd);
                }
                if (lineEnd == i6) {
                    return selectableInfo2.anchorForOffset(lineStart);
                }
                if (!(z3 ^ z2) ? i5 >= lineStart : i5 > lineEnd) {
                    lineStart = lineEnd;
                }
                return selectableInfo2.anchorForOffset(lineStart);
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
        long jM746getWordBoundaryjx7JFs = textLayoutResult.m746getWordBoundaryjx7JFs(i6);
        if (i5 != -1) {
            if (i != i5) {
                if (!(((i2 < i3 ? CrossStatus.NOT_CROSSED : i2 > i3 ? CrossStatus.CROSSED : CrossStatus.COLLAPSED) == CrossStatus.CROSSED) ^ z)) {
                }
            }
            return selectableInfo.anchorForOffset(i);
        }
        TextRange.Companion companion = TextRange.Companion;
        return (i6 == ((int) (jM746getWordBoundaryjx7JFs >> 32)) || i6 == ((int) (4294967295L & jM746getWordBoundaryjx7JFs))) ? (Selection.AnchorInfo) lazy2.getValue() : selectableInfo.anchorForOffset(i);
    }

    public static final Selection.AnchorInfo anchorOnBoundary(SelectableInfo selectableInfo, boolean z, boolean z2, int i, BoundaryFunction boundaryFunction) {
        long j;
        int i2 = z2 ? selectableInfo.rawStartHandleOffset : selectableInfo.rawEndHandleOffset;
        if (i != selectableInfo.slot) {
            return selectableInfo.anchorForOffset(i2);
        }
        long jMo234getBoundaryfzxv0v0 = boundaryFunction.mo234getBoundaryfzxv0v0(selectableInfo, i2);
        if (z ^ z2) {
            TextRange.Companion companion = TextRange.Companion;
            j = jMo234getBoundaryfzxv0v0 >> 32;
        } else {
            TextRange.Companion companion2 = TextRange.Companion;
            j = 4294967295L & jMo234getBoundaryfzxv0v0;
        }
        return selectableInfo.anchorForOffset((int) j);
    }

    public static final Selection.AnchorInfo changeOffset(Selection.AnchorInfo anchorInfo, SelectableInfo selectableInfo, int i) {
        return new Selection.AnchorInfo(selectableInfo.textLayoutResult.getBidiRunDirection(i), i, anchorInfo.selectableId);
    }
}
