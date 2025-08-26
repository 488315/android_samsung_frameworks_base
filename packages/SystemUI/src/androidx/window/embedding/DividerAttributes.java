package androidx.window.embedding;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import androidx.compose.animation.AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class DividerAttributes {
    public static final Companion Companion = new Companion(null);
    public static final DividerAttributes$Companion$NO_DIVIDER$1 NO_DIVIDER = new DividerAttributes() { // from class: androidx.window.embedding.DividerAttributes$Companion$NO_DIVIDER$1
        @Override // androidx.window.embedding.DividerAttributes
        public final String toString() {
            return "NO_DIVIDER";
        }
    };
    public final int color;
    public final int widthDp;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final void access$validateColor(Companion companion, int i) {
            companion.getClass();
            if ((i >>> 24) == 255) {
                return;
            }
            throw new IllegalArgumentException(("Divider color must be opaque. Got: " + Integer.toHexString(i)).toString());
        }

        public static final void access$validateWidth(Companion companion, int i) {
            companion.getClass();
            if (i != -1 && i < 0) {
                throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "widthDp must be greater than or equal to 0 or WIDTH_SYSTEM_DEFAULT. Got: ").toString());
            }
        }

        private Companion() {
        }
    }

    public abstract class DragRange {
        public static final DividerAttributes$DragRange$Companion$DRAG_RANGE_SYSTEM_DEFAULT$1 DRAG_RANGE_SYSTEM_DEFAULT;

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        public final class SplitRatioDragRange extends DragRange {
            public final float maxRatio;
            public final float minRatio;

            public SplitRatioDragRange(float f, float f2) {
                super(null);
                this.minRatio = f;
                this.maxRatio = f2;
                if (f <= 0.0d || f >= 1.0d) {
                    throw new IllegalArgumentException("minRatio must be in the interval (0.0, 1.0)");
                }
                if (f2 <= 0.0d || f2 >= 1.0d) {
                    throw new IllegalArgumentException("maxRatio must be in the interval (0.0, 1.0)");
                }
                if (f > f2) {
                    throw new IllegalArgumentException("minRatio must be less than or equal to maxRatio");
                }
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof SplitRatioDragRange)) {
                    return false;
                }
                SplitRatioDragRange splitRatioDragRange = (SplitRatioDragRange) obj;
                return this.minRatio == splitRatioDragRange.minRatio && this.maxRatio == splitRatioDragRange.maxRatio;
            }

            public final int hashCode() {
                return Float.hashCode(this.maxRatio) + (Float.hashCode(this.minRatio) * 31);
            }

            public final String toString() {
                StringBuilder sb = new StringBuilder("SplitRatioDragRange[");
                sb.append(this.minRatio);
                sb.append(", ");
                return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(sb, this.maxRatio, ']');
            }
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [androidx.window.embedding.DividerAttributes$DragRange$Companion$DRAG_RANGE_SYSTEM_DEFAULT$1] */
        static {
            new Companion(null);
            DRAG_RANGE_SYSTEM_DEFAULT = new DragRange() { // from class: androidx.window.embedding.DividerAttributes$DragRange$Companion$DRAG_RANGE_SYSTEM_DEFAULT$1
                public final String toString() {
                    return "DRAG_RANGE_SYSTEM_DEFAULT";
                }
            };
        }

        public /* synthetic */ DragRange(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private DragRange() {
        }
    }

    public final class DraggableDividerAttributes extends DividerAttributes {
        public final DragRange dragRange;

        public /* synthetic */ DraggableDividerAttributes(int i, int i2, DragRange dragRange, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, i2, dragRange);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DraggableDividerAttributes)) {
                return false;
            }
            DraggableDividerAttributes draggableDividerAttributes = (DraggableDividerAttributes) obj;
            if (this.widthDp == draggableDividerAttributes.widthDp) {
                if (this.color == draggableDividerAttributes.color && Intrinsics.areEqual(this.dragRange, draggableDividerAttributes.dragRange)) {
                    return true;
                }
            }
            return false;
        }

        public final int hashCode() {
            return this.dragRange.hashCode() + (((this.widthDp * 31) + this.color) * 31);
        }

        @Override // androidx.window.embedding.DividerAttributes
        public final String toString() {
            return "DividerAttributes{width=" + this.widthDp + ", color=" + this.color + ", primaryContainerDragRange=" + this.dragRange + '}';
        }

        public /* synthetic */ DraggableDividerAttributes(int i, int i2, DragRange dragRange, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this((i3 & 1) != 0 ? -1 : i, (i3 & 2) != 0 ? -16777216 : i2, (i3 & 4) != 0 ? DragRange.DRAG_RANGE_SYSTEM_DEFAULT : dragRange);
        }

        public final class Builder {
            public int color;
            public DragRange dragRange;
            public int widthDp;

            public Builder() {
                this.widthDp = -1;
                this.color = -16777216;
                this.dragRange = DragRange.DRAG_RANGE_SYSTEM_DEFAULT;
            }

            public Builder(DraggableDividerAttributes draggableDividerAttributes) {
                this();
                this.widthDp = draggableDividerAttributes.widthDp;
                this.dragRange = draggableDividerAttributes.dragRange;
                this.color = draggableDividerAttributes.color;
            }
        }

        private DraggableDividerAttributes(int i, int i2, DragRange dragRange) {
            super(i, i2, null);
            this.dragRange = dragRange;
        }
    }

    public final class FixedDividerAttributes extends DividerAttributes {
        public /* synthetic */ FixedDividerAttributes(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, i2);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof FixedDividerAttributes)) {
                return false;
            }
            FixedDividerAttributes fixedDividerAttributes = (FixedDividerAttributes) obj;
            if (this.widthDp == fixedDividerAttributes.widthDp) {
                if (this.color == fixedDividerAttributes.color) {
                    return true;
                }
            }
            return false;
        }

        public final int hashCode() {
            return (this.widthDp * 31) + this.color;
        }

        public /* synthetic */ FixedDividerAttributes(int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this((i3 & 1) != 0 ? -1 : i, (i3 & 2) != 0 ? -16777216 : i2);
        }

        public final class Builder {
            public int color;
            public int widthDp;

            public Builder() {
                this.widthDp = -1;
                this.color = -16777216;
            }

            public Builder(FixedDividerAttributes fixedDividerAttributes) {
                this();
                this.widthDp = fixedDividerAttributes.widthDp;
                this.color = fixedDividerAttributes.color;
            }
        }

        private FixedDividerAttributes(int i, int i2) {
            super(i, i2, null);
        }
    }

    public /* synthetic */ DividerAttributes(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("DividerAttributes{width=");
        sb.append(this.widthDp);
        sb.append(", color=");
        return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.color, '}');
    }

    private DividerAttributes(int i, int i2) {
        this.widthDp = i;
        this.color = i2;
    }

    public /* synthetic */ DividerAttributes(int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? -1 : i, (i3 & 2) != 0 ? -16777216 : i2);
    }
}
