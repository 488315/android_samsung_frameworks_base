package androidx.compose.foundation.text;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.input.TransformedText;
import androidx.compose.ui.unit.Density;

/* loaded from: classes.dex */
public abstract class TextFieldScrollKt {

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Orientation.values().length];
            try {
                iArr[Orientation.Vertical.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Orientation.Horizontal.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final Rect access$getCursorRectInScroller(Density density, int i, TransformedText transformedText, TextLayoutResult textLayoutResult, boolean z, int i2) {
        Rect cursorRect;
        if (textLayoutResult != null) {
            cursorRect = textLayoutResult.getCursorRect(transformedText.offsetMapping.originalToTransformed(i));
        } else {
            Rect.Companion.getClass();
            cursorRect = Rect.Zero;
        }
        Rect rect = cursorRect;
        int iMo52roundToPx0680j_4 = density.mo52roundToPx0680j_4(TextFieldCursor_androidKt.DefaultCursorThickness);
        return Rect.copy$default(rect, z ? (i2 - rect.left) - iMo52roundToPx0680j_4 : rect.left, 0.0f, z ? i2 - rect.left : iMo52roundToPx0680j_4 + rect.left, 0.0f, 10);
    }
}
