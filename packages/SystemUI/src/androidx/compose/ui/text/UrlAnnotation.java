package androidx.compose.ui.text;

import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.AnnotatedString;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class UrlAnnotation implements AnnotatedString.Annotation {
    public final String url;

    public UrlAnnotation(String str) {
        this.url = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof UrlAnnotation) {
            return Intrinsics.areEqual(this.url, ((UrlAnnotation) obj).url);
        }
        return false;
    }

    public final int hashCode() {
        return this.url.hashCode();
    }

    public final String toString() {
        return OpaqueKey$$ExternalSyntheticOutline0.m(new StringBuilder("UrlAnnotation(url="), this.url, ')');
    }
}
