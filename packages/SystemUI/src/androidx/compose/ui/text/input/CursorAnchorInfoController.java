package androidx.compose.ui.text.input;

import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.EditorBoundsInfo;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.graphics.AndroidMatrixConversions_androidKt;
import androidx.compose.ui.graphics.Matrix;
import androidx.compose.ui.graphics.RectHelper_androidKt;
import androidx.compose.ui.input.pointer.MatrixPositionCalculator;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.compose.ui.text.MultiParagraph;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.TextRangeKt;
import androidx.compose.ui.text.style.ResolvedTextDirection;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes.dex */
public final class CursorAnchorInfoController {
    public Rect decorationBoxBounds;
    public boolean hasPendingImmediateRequest;
    public boolean includeCharacterBounds;
    public boolean includeEditorBounds;
    public boolean includeInsertionMarker;
    public boolean includeLineBounds;
    public Rect innerTextFieldBounds;
    public final InputMethodManager inputMethodManager;
    public boolean monitorEnabled;
    public OffsetMapping offsetMapping;
    public final MatrixPositionCalculator rootPositionCalculator;
    public TextFieldValue textFieldValue;
    public TextLayoutResult textLayoutResult;
    public final Object lock = new Object();
    public Lambda textFieldToRootTransform = new Function1() { // from class: androidx.compose.ui.text.input.CursorAnchorInfoController$textFieldToRootTransform$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final /* synthetic */ Object mo781invoke(Object obj) {
            float[] fArr = ((Matrix) obj).values;
            return Unit.INSTANCE;
        }
    };
    public final CursorAnchorInfo.Builder builder = new CursorAnchorInfo.Builder();
    public final float[] matrix = Matrix.m483constructorimpl$default();
    public final android.graphics.Matrix androidMatrix = new android.graphics.Matrix();

    public CursorAnchorInfoController(MatrixPositionCalculator matrixPositionCalculator, InputMethodManager inputMethodManager) {
        this.rootPositionCalculator = matrixPositionCalculator;
        this.inputMethodManager = inputMethodManager;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0169  */
    /* JADX WARN: Type inference failed for: r3v4, types: [kotlin.jvm.functions.Function1, kotlin.jvm.internal.Lambda] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateCursorAnchorInfo() {
        boolean z;
        InputMethodManagerImpl inputMethodManagerImpl;
        int lineForVerticalPosition;
        int lineForVerticalPosition2;
        int i;
        InputMethodManagerImpl inputMethodManagerImpl2 = (InputMethodManagerImpl) this.inputMethodManager;
        if (((android.view.inputmethod.InputMethodManager) inputMethodManagerImpl2.imm$delegate.getValue()).isActive(inputMethodManagerImpl2.view)) {
            ?? r3 = this.textFieldToRootTransform;
            float[] fArr = this.matrix;
            r3.mo781invoke(Matrix.m482boximpl(fArr));
            ((AndroidComposeView) this.rootPositionCalculator).m694localToScreen58bKbWc(fArr);
            AndroidMatrixConversions_androidKt.m434setFromEL8BTi8(this.androidMatrix, fArr);
            CursorAnchorInfo.Builder builder = this.builder;
            TextFieldValue textFieldValue = this.textFieldValue;
            textFieldValue.getClass();
            OffsetMapping offsetMapping = this.offsetMapping;
            offsetMapping.getClass();
            TextLayoutResult textLayoutResult = this.textLayoutResult;
            textLayoutResult.getClass();
            android.graphics.Matrix matrix = this.androidMatrix;
            Rect rect = this.innerTextFieldBounds;
            rect.getClass();
            Rect rect2 = this.decorationBoxBounds;
            rect2.getClass();
            boolean z2 = this.includeInsertionMarker;
            boolean z3 = this.includeCharacterBounds;
            boolean z4 = this.includeEditorBounds;
            boolean z5 = this.includeLineBounds;
            builder.reset();
            builder.setMatrix(matrix);
            long j = textFieldValue.selection;
            int iM752getMinimpl = TextRange.m752getMinimpl(j);
            builder.setSelectionRange(iM752getMinimpl, TextRange.m751getMaximpl(j));
            if (!z2 || iM752getMinimpl < 0) {
                z = z5;
            } else {
                int iOriginalToTransformed = offsetMapping.originalToTransformed(iM752getMinimpl);
                Rect cursorRect = textLayoutResult.getCursorRect(iOriginalToTransformed);
                float fCoerceIn = RangesKt___RangesKt.coerceIn(cursorRect.left, 0.0f, (int) (textLayoutResult.size >> 32));
                boolean zContainsInclusive = CursorAnchorInfoBuilder_androidKt.containsInclusive(rect, fCoerceIn, cursorRect.top);
                boolean zContainsInclusive2 = CursorAnchorInfoBuilder_androidKt.containsInclusive(rect, fCoerceIn, cursorRect.bottom);
                boolean z6 = textLayoutResult.getBidiRunDirection(iOriginalToTransformed) == ResolvedTextDirection.Rtl;
                int i2 = (zContainsInclusive || zContainsInclusive2) ? 1 : 0;
                if (!zContainsInclusive || !zContainsInclusive2) {
                    i2 |= 2;
                }
                if (z6) {
                    i2 |= 4;
                }
                int i3 = i2;
                float f = cursorRect.top;
                float f2 = cursorRect.bottom;
                z = z5;
                builder.setInsertionMarkerLocation(fCoerceIn, f, f2, f2, i3);
            }
            MultiParagraph multiParagraph = textLayoutResult.multiParagraph;
            if (z3) {
                TextRange textRange = textFieldValue.composition;
                int iM752getMinimpl2 = textRange != null ? TextRange.m752getMinimpl(textRange.packedValue) : -1;
                int iM751getMaximpl = textRange != null ? TextRange.m751getMaximpl(textRange.packedValue) : -1;
                if (iM752getMinimpl2 < 0 || iM752getMinimpl2 >= iM751getMaximpl) {
                    inputMethodManagerImpl = inputMethodManagerImpl2;
                } else {
                    builder.setComposingText(iM752getMinimpl2, textFieldValue.annotatedString.text.subSequence(iM752getMinimpl2, iM751getMaximpl));
                    int iOriginalToTransformed2 = offsetMapping.originalToTransformed(iM752getMinimpl2);
                    int iOriginalToTransformed3 = offsetMapping.originalToTransformed(iM751getMaximpl);
                    float[] fArr2 = new float[(iOriginalToTransformed3 - iOriginalToTransformed2) * 4];
                    inputMethodManagerImpl = inputMethodManagerImpl2;
                    multiParagraph.m735fillBoundingBoxes8ffj60Q(TextRangeKt.TextRange(iOriginalToTransformed2, iOriginalToTransformed3), fArr2);
                    while (iM752getMinimpl2 < iM751getMaximpl) {
                        int iOriginalToTransformed4 = offsetMapping.originalToTransformed(iM752getMinimpl2);
                        int i4 = (iOriginalToTransformed4 - iOriginalToTransformed2) * 4;
                        int i5 = iOriginalToTransformed2;
                        OffsetMapping offsetMapping2 = offsetMapping;
                        Rect rect3 = new Rect(fArr2[i4], fArr2[i4 + 1], fArr2[i4 + 2], fArr2[i4 + 3]);
                        boolean zOverlaps = rect.overlaps(rect3);
                        if (CursorAnchorInfoBuilder_androidKt.containsInclusive(rect, rect3.left, rect3.top)) {
                            i = zOverlaps;
                            if (!CursorAnchorInfoBuilder_androidKt.containsInclusive(rect, rect3.right, rect3.bottom)) {
                                i = (zOverlaps ? 1 : 0) | 2;
                            }
                        }
                        if (textLayoutResult.getBidiRunDirection(iOriginalToTransformed4) == ResolvedTextDirection.Rtl) {
                            i = (i == true ? 1 : 0) | 4;
                        }
                        MultiParagraph multiParagraph2 = multiParagraph;
                        int i6 = iM752getMinimpl2;
                        builder.addCharacterBounds(i6, rect3.left, rect3.top, rect3.right, rect3.bottom, i);
                        iM752getMinimpl2 = i6 + 1;
                        multiParagraph = multiParagraph2;
                        fArr2 = fArr2;
                        iM751getMaximpl = iM751getMaximpl;
                        offsetMapping = offsetMapping2;
                        iOriginalToTransformed2 = i5;
                    }
                }
            }
            MultiParagraph multiParagraph3 = multiParagraph;
            if (z4) {
                int i7 = CursorAnchorInfoApi33Helper.$r8$clinit;
                builder.setEditorBoundsInfo(new EditorBoundsInfo.Builder().setEditorBounds(RectHelper_androidKt.toAndroidRectF(rect2)).setHandwritingBounds(RectHelper_androidKt.toAndroidRectF(rect2)).build());
            }
            if (z) {
                int i8 = CursorAnchorInfoApi34Helper.$r8$clinit;
                if (!rect.isEmpty() && (lineForVerticalPosition = multiParagraph3.getLineForVerticalPosition(rect.top)) <= (lineForVerticalPosition2 = multiParagraph3.getLineForVerticalPosition(rect.bottom))) {
                    while (true) {
                        builder.addVisibleLineBounds(textLayoutResult.getLineLeft(lineForVerticalPosition), multiParagraph3.getLineTop(lineForVerticalPosition), textLayoutResult.getLineRight(lineForVerticalPosition), multiParagraph3.getLineBottom(lineForVerticalPosition));
                        if (lineForVerticalPosition == lineForVerticalPosition2) {
                            break;
                        } else {
                            lineForVerticalPosition++;
                        }
                    }
                }
            }
            InputMethodManagerImpl inputMethodManagerImpl3 = inputMethodManagerImpl;
            ((android.view.inputmethod.InputMethodManager) inputMethodManagerImpl3.imm$delegate.getValue()).updateCursorAnchorInfo(inputMethodManagerImpl3.view, builder.build());
            this.hasPendingImmediateRequest = false;
        }
    }
}
