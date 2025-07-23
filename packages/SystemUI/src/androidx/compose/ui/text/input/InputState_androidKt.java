package androidx.compose.ui.text.input;

import android.view.inputmethod.ExtractedText;
import androidx.compose.ui.text.TextRange;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class InputState_androidKt {
    public static final ExtractedText toExtractedText(TextFieldValue textFieldValue) {
        ExtractedText extractedText = new ExtractedText();
        String str = textFieldValue.annotatedString.text;
        extractedText.text = str;
        extractedText.startOffset = 0;
        extractedText.partialEndOffset = str.length();
        extractedText.partialStartOffset = -1;
        long j = textFieldValue.selection;
        extractedText.selectionStart = TextRange.m750getMinimpl(j);
        extractedText.selectionEnd = TextRange.m749getMaximpl(j);
        extractedText.flags = !StringsKt__StringsKt.contains$default(textFieldValue.annotatedString.text, '\n') ? 1 : 0;
        return extractedText;
    }
}
