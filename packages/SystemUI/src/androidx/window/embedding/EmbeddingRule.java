package androidx.window.embedding;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class EmbeddingRule {
    public final String tag;

    public EmbeddingRule(String str) {
        this.tag = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EmbeddingRule)) {
            return false;
        }
        return Intrinsics.areEqual(this.tag, ((EmbeddingRule) obj).tag);
    }

    public final String getTag() {
        return this.tag;
    }

    public final int hashCode() {
        String str = this.tag;
        if (str != null) {
            return str.hashCode();
        }
        return 0;
    }
}
