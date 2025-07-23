package com.android.wm.shell.desktopmode;

import android.graphics.Point;
import android.graphics.Rect;
import com.android.systemui.util.SystemUIAnalytics;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class DesktopTaskPosition {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class BottomLeft extends DesktopTaskPosition {
        public static final BottomLeft INSTANCE = new BottomLeft();

        private BottomLeft() {
            super(null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof BottomLeft);
        }

        @Override // com.android.wm.shell.desktopmode.DesktopTaskPosition
        public final Point getTopLeftCoordinates(Rect rect, Rect rect2) {
            return new Point(rect.left, rect.bottom - rect2.height());
        }

        public final int hashCode() {
            return -1215550673;
        }

        @Override // com.android.wm.shell.desktopmode.DesktopTaskPosition
        public final DesktopTaskPosition next() {
            return TopRight.INSTANCE;
        }

        public final String toString() {
            return "BottomLeft";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class BottomRight extends DesktopTaskPosition {
        public static final BottomRight INSTANCE = new BottomRight();

        private BottomRight() {
            super(null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof BottomRight);
        }

        @Override // com.android.wm.shell.desktopmode.DesktopTaskPosition
        public final Point getTopLeftCoordinates(Rect rect, Rect rect2) {
            return new Point(rect.right - rect2.width(), rect.bottom - rect2.height());
        }

        public final int hashCode() {
            return 978295796;
        }

        @Override // com.android.wm.shell.desktopmode.DesktopTaskPosition
        public final DesktopTaskPosition next() {
            return TopLeft.INSTANCE;
        }

        public final String toString() {
            return "BottomRight";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Center extends DesktopTaskPosition {
        public static final Center INSTANCE = new Center();

        private Center() {
            super(null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Center);
        }

        @Override // com.android.wm.shell.desktopmode.DesktopTaskPosition
        public final Point getTopLeftCoordinates(Rect rect, Rect rect2) {
            return new Point((rect.width() - rect2.width()) / 2, (int) (((rect.height() - rect2.height()) * 0.375d) + rect.top));
        }

        public final int hashCode() {
            return 2013400370;
        }

        @Override // com.android.wm.shell.desktopmode.DesktopTaskPosition
        public final DesktopTaskPosition next() {
            return BottomRight.INSTANCE;
        }

        public final String toString() {
            return SystemUIAnalytics.DT_BOUNCER_POSITION_CENTER;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class TopLeft extends DesktopTaskPosition {
        public static final TopLeft INSTANCE = new TopLeft();

        private TopLeft() {
            super(null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof TopLeft);
        }

        @Override // com.android.wm.shell.desktopmode.DesktopTaskPosition
        public final Point getTopLeftCoordinates(Rect rect, Rect rect2) {
            return new Point(rect.left, rect.top);
        }

        public final int hashCode() {
            return 480509375;
        }

        @Override // com.android.wm.shell.desktopmode.DesktopTaskPosition
        public final DesktopTaskPosition next() {
            return BottomLeft.INSTANCE;
        }

        public final String toString() {
            return "TopLeft";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class TopRight extends DesktopTaskPosition {
        public static final TopRight INSTANCE = new TopRight();

        private TopRight() {
            super(null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof TopRight);
        }

        @Override // com.android.wm.shell.desktopmode.DesktopTaskPosition
        public final Point getTopLeftCoordinates(Rect rect, Rect rect2) {
            return new Point(rect.right - rect2.width(), rect.top);
        }

        public final int hashCode() {
            return 2016549732;
        }

        @Override // com.android.wm.shell.desktopmode.DesktopTaskPosition
        public final DesktopTaskPosition next() {
            return Center.INSTANCE;
        }

        public final String toString() {
            return "TopRight";
        }
    }

    public /* synthetic */ DesktopTaskPosition(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract Point getTopLeftCoordinates(Rect rect, Rect rect2);

    public abstract DesktopTaskPosition next();

    private DesktopTaskPosition() {
    }
}
