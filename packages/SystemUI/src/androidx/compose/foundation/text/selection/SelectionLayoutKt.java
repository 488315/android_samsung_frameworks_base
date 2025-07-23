package androidx.compose.foundation.text.selection;

import androidx.compose.ui.text.MultiParagraph;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.style.ResolvedTextDirection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SelectionLayoutKt {
    public static final ResolvedTextDirection getTextDirectionForOffset(TextLayoutResult textLayoutResult, int i) {
        if (textLayoutResult.layoutInput.text.length() != 0) {
            MultiParagraph multiParagraph = textLayoutResult.multiParagraph;
            int lineForOffset = multiParagraph.getLineForOffset(i);
            if ((i != 0 && lineForOffset == multiParagraph.getLineForOffset(i - 1)) || (i != textLayoutResult.layoutInput.text.text.length() && lineForOffset == multiParagraph.getLineForOffset(i + 1))) {
                return textLayoutResult.getBidiRunDirection(i);
            }
        }
        return textLayoutResult.getParagraphDirection(i);
    }
}
