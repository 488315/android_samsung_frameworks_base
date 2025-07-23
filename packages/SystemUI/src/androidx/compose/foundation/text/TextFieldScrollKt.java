package androidx.compose.foundation.text;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.input.TransformedText;
import androidx.compose.ui.unit.Density;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class TextFieldScrollKt {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        Rect rect;
        if (textLayoutResult != null) {
            rect = textLayoutResult.getCursorRect(transformedText.offsetMapping.originalToTransformed(i));
        } else {
            Rect.Companion.getClass();
            rect = Rect.Zero;
        }
        Rect rect2 = rect;
        int mo51roundToPx0680j_4 = density.mo51roundToPx0680j_4(TextFieldCursor_androidKt.DefaultCursorThickness);
        return Rect.copy$default(rect2, z ? (i2 - rect2.left) - mo51roundToPx0680j_4 : rect2.left, 0.0f, z ? i2 - rect2.left : mo51roundToPx0680j_4 + rect2.left, 0.0f, 10);
    }
}
