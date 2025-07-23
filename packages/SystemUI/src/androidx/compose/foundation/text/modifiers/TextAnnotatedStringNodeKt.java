package androidx.compose.foundation.text.modifiers;

import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.AnnotatedStringKt;
import androidx.compose.ui.text.LinkAnnotation;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class TextAnnotatedStringNodeKt {
    public static final boolean hasLinks(AnnotatedString annotatedString) {
        int length = annotatedString.text.length();
        List list = annotatedString.annotations;
        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                AnnotatedString.Range range = (AnnotatedString.Range) list.get(i);
                if ((range.item instanceof LinkAnnotation) && AnnotatedStringKt.intersect(0, length, range.start, range.end)) {
                    return true;
                }
            }
        }
        return false;
    }
}
