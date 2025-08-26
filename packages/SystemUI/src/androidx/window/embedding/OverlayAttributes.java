package androidx.window.embedding;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class OverlayAttributes {
    public final EmbeddingBounds bounds;

    /* JADX WARN: Multi-variable type inference failed */
    public OverlayAttributes() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OverlayAttributes)) {
            return false;
        }
        return Intrinsics.areEqual(this.bounds, ((OverlayAttributes) obj).bounds);
    }

    public final int hashCode() {
        return this.bounds.hashCode();
    }

    public final String toString() {
        return "OverlayAttributes: {bounds=" + this.bounds + '}';
    }

    public OverlayAttributes(EmbeddingBounds embeddingBounds) {
        this.bounds = embeddingBounds;
    }

    public /* synthetic */ OverlayAttributes(EmbeddingBounds embeddingBounds, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? EmbeddingBounds.BOUNDS_EXPANDED : embeddingBounds);
    }
}
