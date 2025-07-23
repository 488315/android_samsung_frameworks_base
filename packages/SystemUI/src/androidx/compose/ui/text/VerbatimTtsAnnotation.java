package androidx.compose.ui.text;

import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class VerbatimTtsAnnotation extends TtsAnnotation {
    public final String verbatim;

    public VerbatimTtsAnnotation(String str) {
        super(null);
        this.verbatim = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof VerbatimTtsAnnotation) {
            return Intrinsics.areEqual(this.verbatim, ((VerbatimTtsAnnotation) obj).verbatim);
        }
        return false;
    }

    public final int hashCode() {
        return this.verbatim.hashCode();
    }

    public final String toString() {
        return OpaqueKey$$ExternalSyntheticOutline0.m(new StringBuilder("VerbatimTtsAnnotation(verbatim="), this.verbatim, ')');
    }
}
