package com.android.wm.shell.shared.bubbles;

/* loaded from: classes3.dex */
public interface DraggedObject {

    public final class Bubble implements DraggedObject {
        public final BubbleBarLocation initialLocation;

        public Bubble(BubbleBarLocation bubbleBarLocation) {
            this.initialLocation = bubbleBarLocation;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Bubble) && this.initialLocation == ((Bubble) obj).initialLocation;
        }

        @Override // com.android.wm.shell.shared.bubbles.DraggedObject
        public final BubbleBarLocation getInitialLocation() {
            return this.initialLocation;
        }

        public final int hashCode() {
            return this.initialLocation.hashCode();
        }

        public final String toString() {
            return "Bubble(initialLocation=" + this.initialLocation + ")";
        }
    }

    public final class BubbleBar implements DraggedObject {
        public final BubbleBarLocation initialLocation;

        public BubbleBar(BubbleBarLocation bubbleBarLocation) {
            this.initialLocation = bubbleBarLocation;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof BubbleBar) && this.initialLocation == ((BubbleBar) obj).initialLocation;
        }

        @Override // com.android.wm.shell.shared.bubbles.DraggedObject
        public final BubbleBarLocation getInitialLocation() {
            return this.initialLocation;
        }

        public final int hashCode() {
            return this.initialLocation.hashCode();
        }

        public final String toString() {
            return "BubbleBar(initialLocation=" + this.initialLocation + ")";
        }
    }

    public final class ExpandedView implements DraggedObject {
        public final BubbleBarLocation initialLocation;

        public ExpandedView(BubbleBarLocation bubbleBarLocation) {
            this.initialLocation = bubbleBarLocation;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ExpandedView) && this.initialLocation == ((ExpandedView) obj).initialLocation;
        }

        @Override // com.android.wm.shell.shared.bubbles.DraggedObject
        public final BubbleBarLocation getInitialLocation() {
            return this.initialLocation;
        }

        public final int hashCode() {
            return this.initialLocation.hashCode();
        }

        public final String toString() {
            return "ExpandedView(initialLocation=" + this.initialLocation + ")";
        }
    }

    BubbleBarLocation getInitialLocation();
}
