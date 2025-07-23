package androidx.compose.foundation.text;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.RectKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.AndroidPaint;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.input.OffsetMapping;
import androidx.compose.ui.text.input.TextFieldValue;
import androidx.compose.ui.text.input.TextInputSession;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TextFieldDelegate {
    public static final Companion Companion = new Companion(null);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: drawHighlight-Le-punE, reason: not valid java name */
        public static void m204drawHighlightLepunE(Canvas canvas, long j, OffsetMapping offsetMapping, TextLayoutResult textLayoutResult, AndroidPaint androidPaint) {
            int originalToTransformed = offsetMapping.originalToTransformed(TextRange.m750getMinimpl(j));
            int originalToTransformed2 = offsetMapping.originalToTransformed(TextRange.m749getMaximpl(j));
            if (originalToTransformed != originalToTransformed2) {
                canvas.drawPath(textLayoutResult.getPathForRange(originalToTransformed, originalToTransformed2), androidPaint);
            }
        }

        public static void notifyFocusedRect$foundation_release(TextFieldValue textFieldValue, TextDelegate textDelegate, TextLayoutResult textLayoutResult, LayoutCoordinates layoutCoordinates, TextInputSession textInputSession, boolean z, OffsetMapping offsetMapping) {
            long computeSizeForDefaultText;
            Rect rect;
            if (z) {
                int originalToTransformed = offsetMapping.originalToTransformed(TextRange.m749getMaximpl(textFieldValue.selection));
                if (originalToTransformed < textLayoutResult.layoutInput.text.text.length()) {
                    rect = textLayoutResult.getBoundingBox(originalToTransformed);
                } else if (originalToTransformed != 0) {
                    rect = textLayoutResult.getBoundingBox(originalToTransformed - 1);
                } else {
                    computeSizeForDefaultText = TextFieldDelegateKt.computeSizeForDefaultText(textDelegate.style, textDelegate.density, textDelegate.fontFamilyResolver, TextFieldDelegateKt.EmptyTextReplacement, 1);
                    rect = new Rect(0.0f, 0.0f, 1.0f, (int) (computeSizeForDefaultText & 4294967295L));
                }
                float f = rect.left;
                long floatToRawIntBits = Float.floatToRawIntBits(f);
                float f2 = rect.top;
                Offset.Companion companion = Offset.Companion;
                long mo613localToRootMKHz9U = layoutCoordinates.mo613localToRootMKHz9U((floatToRawIntBits << 32) | (Float.floatToRawIntBits(f2) & 4294967295L));
                float intBitsToFloat = Float.intBitsToFloat((int) (mo613localToRootMKHz9U >> 32));
                float intBitsToFloat2 = Float.intBitsToFloat((int) (mo613localToRootMKHz9U & 4294967295L));
                long floatToRawIntBits2 = (Float.floatToRawIntBits(intBitsToFloat2) & 4294967295L) | (Float.floatToRawIntBits(intBitsToFloat) << 32);
                float f3 = rect.right - f;
                float f4 = rect.bottom - f2;
                Size.Companion companion2 = Size.Companion;
                Rect m411Recttz77jQw = RectKt.m411Recttz77jQw(floatToRawIntBits2, (Float.floatToRawIntBits(f3) << 32) | (Float.floatToRawIntBits(f4) & 4294967295L));
                if (Intrinsics.areEqual((TextInputSession) textInputSession.textInputService._currentInputSession.get(), textInputSession)) {
                    textInputSession.platformTextInputService.notifyFocusedRect(m411Recttz77jQw);
                }
            }
        }

        private Companion() {
        }
    }
}
