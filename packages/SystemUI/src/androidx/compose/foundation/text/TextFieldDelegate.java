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

/* loaded from: classes.dex */
public final class TextFieldDelegate {
    public static final Companion Companion = new Companion(null);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: drawHighlight-Le-punE, reason: not valid java name */
        public static void m205drawHighlightLepunE(Canvas canvas, long j, OffsetMapping offsetMapping, TextLayoutResult textLayoutResult, AndroidPaint androidPaint) {
            int iOriginalToTransformed = offsetMapping.originalToTransformed(TextRange.m752getMinimpl(j));
            int iOriginalToTransformed2 = offsetMapping.originalToTransformed(TextRange.m751getMaximpl(j));
            if (iOriginalToTransformed != iOriginalToTransformed2) {
                canvas.drawPath(textLayoutResult.getPathForRange(iOriginalToTransformed, iOriginalToTransformed2), androidPaint);
            }
        }

        public static void notifyFocusedRect$foundation_release(TextFieldValue textFieldValue, TextDelegate textDelegate, TextLayoutResult textLayoutResult, LayoutCoordinates layoutCoordinates, TextInputSession textInputSession, boolean z, OffsetMapping offsetMapping) {
            if (z) {
                int iOriginalToTransformed = offsetMapping.originalToTransformed(TextRange.m751getMaximpl(textFieldValue.selection));
                Rect boundingBox = iOriginalToTransformed < textLayoutResult.layoutInput.text.text.length() ? textLayoutResult.getBoundingBox(iOriginalToTransformed) : iOriginalToTransformed != 0 ? textLayoutResult.getBoundingBox(iOriginalToTransformed - 1) : new Rect(0.0f, 0.0f, 1.0f, (int) (TextFieldDelegateKt.computeSizeForDefaultText(textDelegate.style, textDelegate.density, textDelegate.fontFamilyResolver, TextFieldDelegateKt.EmptyTextReplacement, 1) & 4294967295L));
                long jFloatToRawIntBits = Float.floatToRawIntBits(boundingBox.left);
                float f = boundingBox.top;
                Offset.Companion companion = Offset.Companion;
                long jMo615localToRootMKHz9U = layoutCoordinates.mo615localToRootMKHz9U((jFloatToRawIntBits << 32) | (Float.floatToRawIntBits(f) & 4294967295L));
                long jFloatToRawIntBits2 = (Float.floatToRawIntBits(boundingBox.right - r6) << 32) | (Float.floatToRawIntBits(boundingBox.bottom - f) & 4294967295L);
                Size.Companion companion2 = Size.Companion;
                Rect rectM413Recttz77jQw = RectKt.m413Recttz77jQw((Float.floatToRawIntBits(Float.intBitsToFloat((int) (jMo615localToRootMKHz9U & 4294967295L))) & 4294967295L) | (Float.floatToRawIntBits(Float.intBitsToFloat((int) (jMo615localToRootMKHz9U >> 32))) << 32), jFloatToRawIntBits2);
                if (Intrinsics.areEqual((TextInputSession) textInputSession.textInputService._currentInputSession.get(), textInputSession)) {
                    textInputSession.platformTextInputService.notifyFocusedRect(rectM413Recttz77jQw);
                }
            }
        }

        private Companion() {
        }
    }
}
