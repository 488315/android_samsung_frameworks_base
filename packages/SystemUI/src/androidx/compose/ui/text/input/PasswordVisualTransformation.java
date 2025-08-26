package androidx.compose.ui.text.input;

import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.input.OffsetMapping;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsJVMKt;

/* loaded from: classes.dex */
public final class PasswordVisualTransformation implements VisualTransformation {
    public final char mask;

    public PasswordVisualTransformation() {
        this((char) 0, 1, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof PasswordVisualTransformation) {
            return this.mask == ((PasswordVisualTransformation) obj).mask;
        }
        return false;
    }

    @Override // androidx.compose.ui.text.input.VisualTransformation
    public final TransformedText filter(AnnotatedString annotatedString) {
        AnnotatedString annotatedString2 = new AnnotatedString(StringsKt__StringsJVMKt.repeat(annotatedString.text.length(), String.valueOf(this.mask)), null, 2, null);
        OffsetMapping.Companion.getClass();
        return new TransformedText(annotatedString2, OffsetMapping.Companion.Identity);
    }

    public final int hashCode() {
        return Character.hashCode(this.mask);
    }

    public PasswordVisualTransformation(char c) {
        this.mask = c;
    }

    public /* synthetic */ PasswordVisualTransformation(char c, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? (char) 8226 : c);
    }
}
