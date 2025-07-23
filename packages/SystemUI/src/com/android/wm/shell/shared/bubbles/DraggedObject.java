package com.android.wm.shell.shared.bubbles;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface DraggedObject {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
