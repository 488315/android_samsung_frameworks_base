package androidx.compose.ui.text.input;

import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.TextRange;

/* loaded from: classes.dex */
public abstract class TextFieldValueKt {
    public static final AnnotatedString getSelectedText(TextFieldValue textFieldValue) {
        AnnotatedString annotatedString = textFieldValue.annotatedString;
        annotatedString.getClass();
        long j = textFieldValue.selection;
        return annotatedString.subSequence(TextRange.m752getMinimpl(j), TextRange.m751getMaximpl(j));
    }

    public static final AnnotatedString getTextAfterSelection(TextFieldValue textFieldValue, int i) {
        AnnotatedString annotatedString = textFieldValue.annotatedString;
        long j = textFieldValue.selection;
        return annotatedString.subSequence(TextRange.m751getMaximpl(j), Math.min(TextRange.m751getMaximpl(j) + i, textFieldValue.annotatedString.text.length()));
    }

    public static final AnnotatedString getTextBeforeSelection(TextFieldValue textFieldValue, int i) {
        AnnotatedString annotatedString = textFieldValue.annotatedString;
        long j = textFieldValue.selection;
        return annotatedString.subSequence(Math.max(0, TextRange.m752getMinimpl(j) - i), TextRange.m752getMinimpl(j));
    }
}
