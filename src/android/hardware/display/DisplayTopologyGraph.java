package android.hardware.display;

import android.app.PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0;
import android.hardware.display.DisplayTopology;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class DisplayTopologyGraph extends Record {
    private final DisplayNode[] displayNodes;
    private final int primaryDisplayId;

    private /* synthetic */ boolean $record$equals(Object obj) {
        if (!(obj instanceof DisplayTopologyGraph)) {
            return false;
        }
        DisplayTopologyGraph displayTopologyGraph = (DisplayTopologyGraph) obj;
        return this.primaryDisplayId == displayTopologyGraph.primaryDisplayId && Objects.equals(this.displayNodes, displayTopologyGraph.displayNodes);
    }

    private /* synthetic */ Object[] $record$getFieldsAsObjects() {
        return new Object[]{Integer.valueOf(this.primaryDisplayId), this.displayNodes};
    }

    public DisplayTopologyGraph(int primaryDisplayId, DisplayNode[] displayNodes) {
        this.primaryDisplayId = primaryDisplayId;
        this.displayNodes = displayNodes;
    }

    public DisplayNode[] displayNodes() {
        return this.displayNodes;
    }

    @Override // java.lang.Record
    public final boolean equals(Object obj) {
        return $record$equals(obj);
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m(this.primaryDisplayId, this.displayNodes);
    }

    public int primaryDisplayId() {
        return this.primaryDisplayId;
    }

    @Override // java.lang.Record
    public final String toString() {
        return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m($record$getFieldsAsObjects(), DisplayTopologyGraph.class, "primaryDisplayId;displayNodes");
    }

    public static final class DisplayNode extends Record {
        private final AdjacentDisplay[] adjacentDisplays;
        private final int density;
        private final int displayId;

        private /* synthetic */ boolean $record$equals(Object obj) {
            if (!(obj instanceof DisplayNode)) {
                return false;
            }
            DisplayNode displayNode = (DisplayNode) obj;
            return this.displayId == displayNode.displayId && this.density == displayNode.density && Objects.equals(this.adjacentDisplays, displayNode.adjacentDisplays);
        }

        private /* synthetic */ Object[] $record$getFieldsAsObjects() {
            return new Object[]{Integer.valueOf(this.displayId), Integer.valueOf(this.density), this.adjacentDisplays};
        }

        public DisplayNode(int displayId, int density, AdjacentDisplay[] adjacentDisplays) {
            this.displayId = displayId;
            this.density = density;
            this.adjacentDisplays = adjacentDisplays;
        }

        public AdjacentDisplay[] adjacentDisplays() {
            return this.adjacentDisplays;
        }

        public int density() {
            return this.density;
        }

        public int displayId() {
            return this.displayId;
        }

        @Override // java.lang.Record
        public final boolean equals(Object obj) {
            return $record$equals(obj);
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m(this.displayId, this.density, this.adjacentDisplays);
        }

        @Override // java.lang.Record
        public final String toString() {
            return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m($record$getFieldsAsObjects(), DisplayNode.class, "displayId;density;adjacentDisplays");
        }
    }

    public static final class AdjacentDisplay extends Record {
        private final int displayId;
        private final float offsetDp;
        private final int position;

        private /* synthetic */ boolean $record$equals(Object obj) {
            if (!(obj instanceof AdjacentDisplay)) {
                return false;
            }
            AdjacentDisplay adjacentDisplay = (AdjacentDisplay) obj;
            return this.displayId == adjacentDisplay.displayId && this.position == adjacentDisplay.position && this.offsetDp == adjacentDisplay.offsetDp;
        }

        public AdjacentDisplay(int displayId, int position, float offsetDp) {
            this.displayId = displayId;
            this.position = position;
            this.offsetDp = offsetDp;
        }

        public int displayId() {
            return this.displayId;
        }

        @Override // java.lang.Record
        public final boolean equals(Object obj) {
            return $record$equals(obj);
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m(this.displayId, this.position, this.offsetDp);
        }

        public float offsetDp() {
            return this.offsetDp;
        }

        public int position() {
            return this.position;
        }

        @Override // java.lang.Record
        public String toString() {
            return "AdjacentDisplay{displayId=" + this.displayId + ", position=" + DisplayTopology.TreeNode.positionToString(this.position) + ", offsetDp=" + this.offsetDp + '}';
        }
    }
}
