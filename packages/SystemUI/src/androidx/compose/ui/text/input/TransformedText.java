package androidx.compose.ui.text.input;

import androidx.compose.ui.text.AnnotatedString;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TransformedText {
    public final OffsetMapping offsetMapping;
    public final AnnotatedString text;

    public TransformedText(AnnotatedString annotatedString, OffsetMapping offsetMapping) {
        this.text = annotatedString;
        this.offsetMapping = offsetMapping;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TransformedText)) {
            return false;
        }
        TransformedText transformedText = (TransformedText) obj;
        return Intrinsics.areEqual(this.text, transformedText.text) && Intrinsics.areEqual(this.offsetMapping, transformedText.offsetMapping);
    }

    public final int hashCode() {
        return this.offsetMapping.hashCode() + (this.text.hashCode() * 31);
    }

    public final String toString() {
        return "TransformedText(text=" + ((Object) this.text) + ", offsetMapping=" + this.offsetMapping + ')';
    }
}
