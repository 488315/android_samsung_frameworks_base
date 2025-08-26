package androidx.window.embedding;

import kotlin.jvm.internal.Intrinsics;

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
