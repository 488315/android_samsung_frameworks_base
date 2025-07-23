package com.android.wm.shell.shared.bubbles;

import android.graphics.Rect;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface DragZone {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Bubble implements DragZone {
        public final Rect bounds;
        public final Rect dropTarget;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Left extends Bubble {
            public final Rect bounds;
            public final Rect dropTarget;

            public Left(Rect rect, Rect rect2) {
                super(rect, rect2, null);
                this.bounds = rect;
                this.dropTarget = rect2;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Left)) {
                    return false;
                }
                Left left = (Left) obj;
                return Intrinsics.areEqual(this.bounds, left.bounds) && Intrinsics.areEqual(this.dropTarget, left.dropTarget);
            }

            @Override // com.android.wm.shell.shared.bubbles.DragZone.Bubble, com.android.wm.shell.shared.bubbles.DragZone
            public final Rect getBounds() {
                return this.bounds;
            }

            @Override // com.android.wm.shell.shared.bubbles.DragZone.Bubble, com.android.wm.shell.shared.bubbles.DragZone
            public final Rect getDropTarget() {
                return this.dropTarget;
            }

            public final int hashCode() {
                return this.dropTarget.hashCode() + (this.bounds.hashCode() * 31);
            }

            public final String toString() {
                return "Left(bounds=" + this.bounds + ", dropTarget=" + this.dropTarget + ")";
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Right extends Bubble {
            public final Rect bounds;
            public final Rect dropTarget;

            public Right(Rect rect, Rect rect2) {
                super(rect, rect2, null);
                this.bounds = rect;
                this.dropTarget = rect2;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Right)) {
                    return false;
                }
                Right right = (Right) obj;
                return Intrinsics.areEqual(this.bounds, right.bounds) && Intrinsics.areEqual(this.dropTarget, right.dropTarget);
            }

            @Override // com.android.wm.shell.shared.bubbles.DragZone.Bubble, com.android.wm.shell.shared.bubbles.DragZone
            public final Rect getBounds() {
                return this.bounds;
            }

            @Override // com.android.wm.shell.shared.bubbles.DragZone.Bubble, com.android.wm.shell.shared.bubbles.DragZone
            public final Rect getDropTarget() {
                return this.dropTarget;
            }

            public final int hashCode() {
                return this.dropTarget.hashCode() + (this.bounds.hashCode() * 31);
            }

            public final String toString() {
                return "Right(bounds=" + this.bounds + ", dropTarget=" + this.dropTarget + ")";
            }
        }

        public /* synthetic */ Bubble(Rect rect, Rect rect2, DefaultConstructorMarker defaultConstructorMarker) {
            this(rect, rect2);
        }

        @Override // com.android.wm.shell.shared.bubbles.DragZone
        public Rect getBounds() {
            return this.bounds;
        }

        @Override // com.android.wm.shell.shared.bubbles.DragZone
        public Rect getDropTarget() {
            return this.dropTarget;
        }

        private Bubble(Rect rect, Rect rect2) {
            this.bounds = rect;
            this.dropTarget = rect2;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Dismiss implements DragZone {
        public final Rect bounds;

        public Dismiss(Rect rect) {
            this.bounds = rect;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Dismiss) && Intrinsics.areEqual(this.bounds, ((Dismiss) obj).bounds);
        }

        @Override // com.android.wm.shell.shared.bubbles.DragZone
        public final Rect getBounds() {
            return this.bounds;
        }

        @Override // com.android.wm.shell.shared.bubbles.DragZone
        public final Rect getDropTarget() {
            return null;
        }

        public final int hashCode() {
            return this.bounds.hashCode();
        }

        public final String toString() {
            return "Dismiss(bounds=" + this.bounds + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class FullScreen implements DragZone {
        public final Rect bounds;
        public final Rect dropTarget;

        public FullScreen(Rect rect, Rect rect2) {
            this.bounds = rect;
            this.dropTarget = rect2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof FullScreen)) {
                return false;
            }
            FullScreen fullScreen = (FullScreen) obj;
            return Intrinsics.areEqual(this.bounds, fullScreen.bounds) && Intrinsics.areEqual(this.dropTarget, fullScreen.dropTarget);
        }

        @Override // com.android.wm.shell.shared.bubbles.DragZone
        public final Rect getBounds() {
            return this.bounds;
        }

        @Override // com.android.wm.shell.shared.bubbles.DragZone
        public final Rect getDropTarget() {
            return this.dropTarget;
        }

        public final int hashCode() {
            return this.dropTarget.hashCode() + (this.bounds.hashCode() * 31);
        }

        public final String toString() {
            return "FullScreen(bounds=" + this.bounds + ", dropTarget=" + this.dropTarget + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Split implements DragZone {
        public final Rect bounds;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Bottom extends Split {
            public final Rect bounds;

            public Bottom(Rect rect) {
                super(rect, null);
                this.bounds = rect;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof Bottom) && Intrinsics.areEqual(this.bounds, ((Bottom) obj).bounds);
            }

            @Override // com.android.wm.shell.shared.bubbles.DragZone.Split, com.android.wm.shell.shared.bubbles.DragZone
            public final Rect getBounds() {
                return this.bounds;
            }

            public final int hashCode() {
                return this.bounds.hashCode();
            }

            public final String toString() {
                return "Bottom(bounds=" + this.bounds + ")";
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Left extends Split {
            public final Rect bounds;

            public Left(Rect rect) {
                super(rect, null);
                this.bounds = rect;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof Left) && Intrinsics.areEqual(this.bounds, ((Left) obj).bounds);
            }

            @Override // com.android.wm.shell.shared.bubbles.DragZone.Split, com.android.wm.shell.shared.bubbles.DragZone
            public final Rect getBounds() {
                return this.bounds;
            }

            public final int hashCode() {
                return this.bounds.hashCode();
            }

            public final String toString() {
                return "Left(bounds=" + this.bounds + ")";
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Right extends Split {
            public final Rect bounds;

            public Right(Rect rect) {
                super(rect, null);
                this.bounds = rect;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof Right) && Intrinsics.areEqual(this.bounds, ((Right) obj).bounds);
            }

            @Override // com.android.wm.shell.shared.bubbles.DragZone.Split, com.android.wm.shell.shared.bubbles.DragZone
            public final Rect getBounds() {
                return this.bounds;
            }

            public final int hashCode() {
                return this.bounds.hashCode();
            }

            public final String toString() {
                return "Right(bounds=" + this.bounds + ")";
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Top extends Split {
            public final Rect bounds;

            public Top(Rect rect) {
                super(rect, null);
                this.bounds = rect;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof Top) && Intrinsics.areEqual(this.bounds, ((Top) obj).bounds);
            }

            @Override // com.android.wm.shell.shared.bubbles.DragZone.Split, com.android.wm.shell.shared.bubbles.DragZone
            public final Rect getBounds() {
                return this.bounds;
            }

            public final int hashCode() {
                return this.bounds.hashCode();
            }

            public final String toString() {
                return "Top(bounds=" + this.bounds + ")";
            }
        }

        public /* synthetic */ Split(Rect rect, DefaultConstructorMarker defaultConstructorMarker) {
            this(rect);
        }

        @Override // com.android.wm.shell.shared.bubbles.DragZone
        public Rect getBounds() {
            return this.bounds;
        }

        @Override // com.android.wm.shell.shared.bubbles.DragZone
        public final Rect getDropTarget() {
            return null;
        }

        private Split(Rect rect) {
            this.bounds = rect;
        }
    }

    Rect getBounds();

    Rect getDropTarget();
}
