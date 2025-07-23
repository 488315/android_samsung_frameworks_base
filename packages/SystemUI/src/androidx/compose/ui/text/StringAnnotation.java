package androidx.compose.ui.text;

import androidx.compose.ui.text.AnnotatedString;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class StringAnnotation implements AnnotatedString.Annotation {
    public final String value;

    private /* synthetic */ StringAnnotation(String str) {
        this.value = str;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ StringAnnotation m743boximpl(String str) {
        return new StringAnnotation(str);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof StringAnnotation) {
            return Intrinsics.areEqual(this.value, ((StringAnnotation) obj).value);
        }
        return false;
    }

    public final int hashCode() {
        return this.value.hashCode();
    }

    public final String toString() {
        return "StringAnnotation(value=" + this.value + ')';
    }
}
