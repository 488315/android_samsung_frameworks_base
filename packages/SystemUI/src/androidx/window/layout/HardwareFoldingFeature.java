package androidx.window.layout;

import androidx.window.core.Bounds;
import androidx.window.layout.FoldingFeature;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class HardwareFoldingFeature implements FoldingFeature {
    public static final Companion Companion = new Companion(null);
    public final Bounds featureBounds;
    public final FoldingFeature.State state;
    public final Type type;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class Type {
        public static final Companion Companion = new Companion(null);
        public static final Type FOLD = new Type("FOLD");
        public static final Type HINGE = new Type("HINGE");
        public final String description;

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        private Type(String str) {
            this.description = str;
        }

        public final String toString() {
            return this.description;
        }
    }

    public HardwareFoldingFeature(Bounds bounds, Type type, FoldingFeature.State state) {
        this.featureBounds = bounds;
        this.type = type;
        this.state = state;
        Companion.getClass();
        if (bounds.getWidth() == 0 && bounds.getHeight() == 0) {
            throw new IllegalArgumentException("Bounds must be non zero");
        }
        if (bounds.left != 0 && bounds.top != 0) {
            throw new IllegalArgumentException("Bounding rectangle must start at the top or left window edge for folding features");
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!HardwareFoldingFeature.class.equals(obj != null ? obj.getClass() : null)) {
            return false;
        }
        HardwareFoldingFeature hardwareFoldingFeature = (HardwareFoldingFeature) obj;
        if (Intrinsics.areEqual(this.featureBounds, hardwareFoldingFeature.featureBounds) && Intrinsics.areEqual(this.type, hardwareFoldingFeature.type)) {
            return Intrinsics.areEqual(this.state, hardwareFoldingFeature.state);
        }
        return false;
    }

    public final FoldingFeature.Orientation getOrientation() {
        Bounds bounds = this.featureBounds;
        return bounds.getWidth() > bounds.getHeight() ? FoldingFeature.Orientation.HORIZONTAL : FoldingFeature.Orientation.VERTICAL;
    }

    public final int hashCode() {
        return this.state.hashCode() + ((this.type.hashCode() + (this.featureBounds.hashCode() * 31)) * 31);
    }

    public final String toString() {
        return "HardwareFoldingFeature { " + this.featureBounds + ", type=" + this.type + ", state=" + this.state + " }";
    }
}
