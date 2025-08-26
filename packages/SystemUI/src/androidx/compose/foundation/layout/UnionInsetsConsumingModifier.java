package androidx.compose.foundation.layout;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
final class UnionInsetsConsumingModifier extends InsetsConsumingModifier {
    public final WindowInsets insets;

    public UnionInsetsConsumingModifier(WindowInsets windowInsets) {
        super(null);
        this.insets = windowInsets;
    }

    @Override // androidx.compose.foundation.layout.InsetsConsumingModifier
    public final WindowInsets calculateInsets(WindowInsets windowInsets) {
        return new UnionInsets(this.insets, windowInsets);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof UnionInsetsConsumingModifier) {
            return Intrinsics.areEqual(((UnionInsetsConsumingModifier) obj).insets, this.insets);
        }
        return false;
    }

    public final int hashCode() {
        return this.insets.hashCode();
    }
}
