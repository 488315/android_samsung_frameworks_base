package androidx.compose.foundation.text;

import androidx.compose.ui.text.AnnotatedString;

/* loaded from: classes.dex */
final class TextAnnotatorScope {
    public final AnnotatedString initialText;
    public AnnotatedString styledText;

    public TextAnnotatorScope(AnnotatedString annotatedString) {
        this.initialText = annotatedString;
        this.styledText = annotatedString;
    }
}
