package androidx.window.embedding;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.window.core.Bounds;
import androidx.window.layout.FoldingFeature;
import androidx.window.layout.HardwareFoldingFeature;
import androidx.window.layout.WindowLayoutInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class EmbeddingBounds {
    public static final EmbeddingBounds BOUNDS_EXPANDED;
    public static final Companion Companion = new Companion(null);
    public final Alignment alignment;
    public final Dimension height;
    public final Dimension width;

    public final class Alignment {
        public static final Alignment ALIGN_BOTTOM;
        public static final Alignment ALIGN_LEFT;
        public static final Alignment ALIGN_RIGHT;
        public static final Alignment ALIGN_TOP;
        public final int value;

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        static {
            new Companion(null);
            ALIGN_TOP = new Alignment(0);
            ALIGN_LEFT = new Alignment(1);
            ALIGN_BOTTOM = new Alignment(2);
            ALIGN_RIGHT = new Alignment(3);
        }

        public Alignment(int i) {
            this.value = i;
            if (i < 0 || i >= 4) {
                throw new IllegalArgumentException("Failed requirement.");
            }
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof Alignment) {
                return this.value == ((Alignment) obj).value;
            }
            return false;
        }

        public final int hashCode() {
            return this.value;
        }

        public final String toString() {
            int i = this.value;
            if (i == 0) {
                return "top";
            }
            if (i == 1) {
                return "left";
            }
            if (i == 2) {
                return "bottom";
            }
            if (i == 3) {
                return "right";
            }
            return "unknown position:" + i;
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static Bounds offset(Bounds bounds, int i, int i2) {
            return new Bounds(bounds.left + i, bounds.top + i2, bounds.right + i, bounds.bottom + i2);
        }

        private Companion() {
        }
    }

    public abstract class Dimension {
        public static final Companion Companion = new Companion(null);
        public static final Ratio DIMENSION_EXPANDED = new Ratio(1.0f);
        public static final EmbeddingBounds$Dimension$Companion$DIMENSION_HINGE$1 DIMENSION_HINGE = new Dimension() { // from class: androidx.window.embedding.EmbeddingBounds$Dimension$Companion$DIMENSION_HINGE$1
        };
        public final String description;

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        public final class Pixel extends Dimension {
            public final int value;

            public Pixel(int i) {
                super(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "dimension in pixel:"));
                this.value = i;
                if (i < 1) {
                    throw new IllegalArgumentException("Pixel value must be a positive integer.");
                }
            }
        }

        public final class Ratio extends Dimension {
            public final float value;

            public Ratio(float f) {
                super("dimension in ratio:" + f);
                this.value = f;
                if (f <= 0.0d || f > 1.0d) {
                    throw new IllegalArgumentException("Ratio must be in range (0.0, 1.0]");
                }
            }
        }

        public Dimension(String str) {
            this.description = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Dimension)) {
                return false;
            }
            return Intrinsics.areEqual(this.description, ((Dimension) obj).description);
        }

        public final int hashCode() {
            return this.description.hashCode();
        }

        public final String toString() {
            return this.description;
        }
    }

    static {
        Alignment alignment = Alignment.ALIGN_TOP;
        Dimension.Ratio ratio = Dimension.DIMENSION_EXPANDED;
        BOUNDS_EXPANDED = new EmbeddingBounds(alignment, ratio, ratio);
        EmbeddingBounds$Dimension$Companion$DIMENSION_HINGE$1 embeddingBounds$Dimension$Companion$DIMENSION_HINGE$1 = Dimension.DIMENSION_HINGE;
        new EmbeddingBounds(alignment, ratio, embeddingBounds$Dimension$Companion$DIMENSION_HINGE$1);
        new EmbeddingBounds(Alignment.ALIGN_LEFT, embeddingBounds$Dimension$Companion$DIMENSION_HINGE$1, ratio);
        new EmbeddingBounds(Alignment.ALIGN_BOTTOM, ratio, embeddingBounds$Dimension$Companion$DIMENSION_HINGE$1);
        new EmbeddingBounds(Alignment.ALIGN_RIGHT, embeddingBounds$Dimension$Companion$DIMENSION_HINGE$1, ratio);
    }

    public EmbeddingBounds(Alignment alignment, Dimension dimension, Dimension dimension2) {
        this.alignment = alignment;
        this.width = dimension;
        this.height = dimension2;
    }

    public static FoldingFeature getOnlyFoldingFeatureOrNull(WindowLayoutInfo windowLayoutInfo) {
        List list = windowLayoutInfo.displayFeatures;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (obj instanceof FoldingFeature) {
                arrayList.add(obj);
            }
        }
        if (arrayList.size() == 1) {
            return (FoldingFeature) arrayList.get(0);
        }
        return null;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EmbeddingBounds)) {
            return false;
        }
        EmbeddingBounds embeddingBounds = (EmbeddingBounds) obj;
        return Intrinsics.areEqual(this.alignment, embeddingBounds.alignment) && Intrinsics.areEqual(this.width, embeddingBounds.width) && Intrinsics.areEqual(this.height, embeddingBounds.height);
    }

    public final int hashCode() {
        return this.height.description.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.alignment.value * 31, 31, this.width.description);
    }

    public final boolean shouldUseFallbackDimensionForHeight$window_release(WindowLayoutInfo windowLayoutInfo) {
        if (Intrinsics.areEqual(this.height, Dimension.DIMENSION_HINGE)) {
            FoldingFeature onlyFoldingFeatureOrNull = getOnlyFoldingFeatureOrNull(windowLayoutInfo);
            if (!(onlyFoldingFeatureOrNull == null ? false : Intrinsics.areEqual(((HardwareFoldingFeature) onlyFoldingFeatureOrNull).getOrientation(), FoldingFeature.Orientation.HORIZONTAL)) || Arrays.asList(Alignment.ALIGN_LEFT, Alignment.ALIGN_RIGHT).contains(this.alignment)) {
                return true;
            }
        }
        return false;
    }

    public final boolean shouldUseFallbackDimensionForWidth$window_release(WindowLayoutInfo windowLayoutInfo) {
        if (Intrinsics.areEqual(this.width, Dimension.DIMENSION_HINGE)) {
            FoldingFeature onlyFoldingFeatureOrNull = getOnlyFoldingFeatureOrNull(windowLayoutInfo);
            if (!(onlyFoldingFeatureOrNull == null ? false : Intrinsics.areEqual(((HardwareFoldingFeature) onlyFoldingFeatureOrNull).getOrientation(), FoldingFeature.Orientation.VERTICAL)) || Arrays.asList(Alignment.ALIGN_TOP, Alignment.ALIGN_BOTTOM).contains(this.alignment)) {
                return true;
            }
        }
        return false;
    }

    public final String toString() {
        return "Bounds:{alignment=" + this.alignment + ", width=" + this.width + ", height=" + this.height + '}';
    }
}
