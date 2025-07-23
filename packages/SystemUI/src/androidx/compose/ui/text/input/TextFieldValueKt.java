package androidx.compose.ui.text.input;

import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.TextRange;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class TextFieldValueKt {
    public static final AnnotatedString getSelectedText(TextFieldValue textFieldValue) {
        AnnotatedString annotatedString = textFieldValue.annotatedString;
        annotatedString.getClass();
        long j = textFieldValue.selection;
        return annotatedString.subSequence(TextRange.m750getMinimpl(j), TextRange.m749getMaximpl(j));
    }

    public static final AnnotatedString getTextAfterSelection(TextFieldValue textFieldValue, int i) {
        AnnotatedString annotatedString = textFieldValue.annotatedString;
        long j = textFieldValue.selection;
        return annotatedString.subSequence(TextRange.m749getMaximpl(j), Math.min(TextRange.m749getMaximpl(j) + i, textFieldValue.annotatedString.text.length()));
    }

    public static final AnnotatedString getTextBeforeSelection(TextFieldValue textFieldValue, int i) {
        AnnotatedString annotatedString = textFieldValue.annotatedString;
        long j = textFieldValue.selection;
        return annotatedString.subSequence(Math.max(0, TextRange.m750getMinimpl(j) - i), TextRange.m750getMinimpl(j));
    }
}
