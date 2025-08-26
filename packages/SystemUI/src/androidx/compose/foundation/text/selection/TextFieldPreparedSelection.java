package androidx.compose.foundation.text.selection;

import androidx.compose.foundation.text.TextLayoutResultProxy;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.text.TextLayoutResult;
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
import kotlin.jvm.internal.DefaultConstructorMarker;

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
        if (!TextRange.m749getCollapsedimpl(this.selection)) {
            return Arrays.asList(new CommitTextCommand("", 0), new SetSelectionCommand(TextRange.m752getMinimpl(this.selection), TextRange.m752getMinimpl(this.selection)));
        }
        EditCommand editCommand = (EditCommand) function1.mo781invoke(this);
        if (editCommand != null) {
            return Collections.singletonList(editCommand);
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0011  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int jumpByPagesOffset(TextLayoutResultProxy textLayoutResultProxy, int i) {
        Rect rectLocalBoundingBoxOf;
        LayoutCoordinates layoutCoordinates = textLayoutResultProxy.innerTextFieldCoordinates;
        if (layoutCoordinates == null) {
            Rect.Companion.getClass();
            rectLocalBoundingBoxOf = Rect.Zero;
        } else {
            LayoutCoordinates layoutCoordinates2 = textLayoutResultProxy.decorationBoxCoordinates;
            rectLocalBoundingBoxOf = layoutCoordinates2 != null ? layoutCoordinates2.localBoundingBoxOf(layoutCoordinates, true) : null;
            if (rectLocalBoundingBoxOf == null) {
            }
        }
        long j = this.currentValue.selection;
        TextRange.Companion companion = TextRange.Companion;
        OffsetMapping offsetMapping = this.offsetMapping;
        int iOriginalToTransformed = offsetMapping.originalToTransformed((int) (j & 4294967295L));
        TextLayoutResult textLayoutResult = textLayoutResultProxy.value;
        Rect cursorRect = textLayoutResult.getCursorRect(iOriginalToTransformed);
        float fIntBitsToFloat = (Float.intBitsToFloat((int) (rectLocalBoundingBoxOf.m410getSizeNHjbRc() & 4294967295L)) * i) + cursorRect.top;
        long jFloatToRawIntBits = Float.floatToRawIntBits(cursorRect.left);
        long jFloatToRawIntBits2 = Float.floatToRawIntBits(fIntBitsToFloat);
        Offset.Companion companion2 = Offset.Companion;
        return offsetMapping.transformedToOriginal(textLayoutResult.multiParagraph.m736getOffsetForPositionk4lQ0M((jFloatToRawIntBits << 32) | (jFloatToRawIntBits2 & 4294967295L)));
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public TextFieldPreparedSelection(TextFieldValue textFieldValue, OffsetMapping offsetMapping, TextLayoutResultProxy textLayoutResultProxy, TextPreparedSelectionState textPreparedSelectionState, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 2) != 0) {
            OffsetMapping.Companion.getClass();
            offsetMapping = OffsetMapping.Companion.Identity;
        }
        this(textFieldValue, offsetMapping, textLayoutResultProxy, (i & 8) != 0 ? new TextPreparedSelectionState() : textPreparedSelectionState);
    }
}
