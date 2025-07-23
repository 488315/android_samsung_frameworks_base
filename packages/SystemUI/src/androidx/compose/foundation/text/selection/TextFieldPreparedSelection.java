package androidx.compose.foundation.text.selection;

import androidx.compose.foundation.text.TextLayoutResultProxy;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.input.CommitTextCommand;
import androidx.compose.ui.text.input.EditCommand;
import androidx.compose.ui.text.input.OffsetMapping;
import androidx.compose.ui.text.input.SetSelectionCommand;
import androidx.compose.ui.text.input.TextFieldValue;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TextFieldPreparedSelection extends BaseTextPreparedSelection<TextFieldPreparedSelection> {
    public final TextFieldValue currentValue;
    public final TextLayoutResultProxy layoutResultProxy;

    public TextFieldPreparedSelection(TextFieldValue textFieldValue, OffsetMapping offsetMapping, TextLayoutResultProxy textLayoutResultProxy, TextPreparedSelectionState textPreparedSelectionState) {
        super(textFieldValue.annotatedString, textFieldValue.selection, textLayoutResultProxy != null ? textLayoutResultProxy.value : null, offsetMapping, textPreparedSelectionState, null);
        this.currentValue = textFieldValue;
        this.layoutResultProxy = textLayoutResultProxy;
    }

    public final List deleteIfSelectedOr(Function1 function1) {
        if (!TextRange.m747getCollapsedimpl(this.selection)) {
            return Arrays.asList(new CommitTextCommand("", 0), new SetSelectionCommand(TextRange.m750getMinimpl(this.selection), TextRange.m750getMinimpl(this.selection)));
        }
        EditCommand editCommand = (EditCommand) function1.mo779invoke(this);
        if (editCommand != null) {
            return Collections.singletonList(editCommand);
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x000f, code lost:
    
        if (r0 == null) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int jumpByPagesOffset(androidx.compose.foundation.text.TextLayoutResultProxy r8, int r9) {
        /*
            r7 = this;
            androidx.compose.ui.layout.LayoutCoordinates r0 = r8.innerTextFieldCoordinates
            if (r0 == 0) goto L11
            androidx.compose.ui.layout.LayoutCoordinates r1 = r8.decorationBoxCoordinates
            if (r1 == 0) goto Le
            r2 = 1
            androidx.compose.ui.geometry.Rect r0 = r1.localBoundingBoxOf(r0, r2)
            goto Lf
        Le:
            r0 = 0
        Lf:
            if (r0 != 0) goto L18
        L11:
            androidx.compose.ui.geometry.Rect$Companion r0 = androidx.compose.ui.geometry.Rect.Companion
            r0.getClass()
            androidx.compose.ui.geometry.Rect r0 = androidx.compose.ui.geometry.Rect.Zero
        L18:
            androidx.compose.ui.text.input.TextFieldValue r1 = r7.currentValue
            long r1 = r1.selection
            androidx.compose.ui.text.TextRange$Companion r3 = androidx.compose.ui.text.TextRange.Companion
            r3 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r1 = r1 & r3
            int r1 = (int) r1
            androidx.compose.ui.text.input.OffsetMapping r7 = r7.offsetMapping
            int r1 = r7.originalToTransformed(r1)
            androidx.compose.ui.text.TextLayoutResult r8 = r8.value
            androidx.compose.ui.geometry.Rect r1 = r8.getCursorRect(r1)
            long r5 = r0.m408getSizeNHjbRc()
            long r5 = r5 & r3
            int r0 = (int) r5
            float r0 = java.lang.Float.intBitsToFloat(r0)
            float r9 = (float) r9
            float r0 = r0 * r9
            float r9 = r1.top
            float r0 = r0 + r9
            float r9 = r1.left
            int r9 = java.lang.Float.floatToRawIntBits(r9)
            long r1 = (long) r9
            int r9 = java.lang.Float.floatToRawIntBits(r0)
            long r5 = (long) r9
            r9 = 32
            long r0 = r1 << r9
            long r2 = r5 & r3
            long r0 = r0 | r2
            androidx.compose.ui.geometry.Offset$Companion r9 = androidx.compose.ui.geometry.Offset.Companion
            androidx.compose.ui.text.MultiParagraph r8 = r8.multiParagraph
            int r8 = r8.m734getOffsetForPositionk4lQ0M(r0)
            int r7 = r7.transformedToOriginal(r8)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.selection.TextFieldPreparedSelection.jumpByPagesOffset(androidx.compose.foundation.text.TextLayoutResultProxy, int):int");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public TextFieldPreparedSelection(androidx.compose.ui.text.input.TextFieldValue r1, androidx.compose.ui.text.input.OffsetMapping r2, androidx.compose.foundation.text.TextLayoutResultProxy r3, androidx.compose.foundation.text.selection.TextPreparedSelectionState r4, int r5, kotlin.jvm.internal.DefaultConstructorMarker r6) {
        /*
            r0 = this;
            r6 = r5 & 2
            if (r6 == 0) goto Lb
            androidx.compose.ui.text.input.OffsetMapping$Companion r2 = androidx.compose.ui.text.input.OffsetMapping.Companion
            r2.getClass()
            androidx.compose.ui.text.input.OffsetMapping$Companion$Identity$1 r2 = androidx.compose.ui.text.input.OffsetMapping.Companion.Identity
        Lb:
            r5 = r5 & 8
            if (r5 == 0) goto L14
            androidx.compose.foundation.text.selection.TextPreparedSelectionState r4 = new androidx.compose.foundation.text.selection.TextPreparedSelectionState
            r4.<init>()
        L14:
            r0.<init>(r1, r2, r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.selection.TextFieldPreparedSelection.<init>(androidx.compose.ui.text.input.TextFieldValue, androidx.compose.ui.text.input.OffsetMapping, androidx.compose.foundation.text.TextLayoutResultProxy, androidx.compose.foundation.text.selection.TextPreparedSelectionState, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
