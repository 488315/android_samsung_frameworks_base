package androidx.compose.ui.graphics;

import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.RoundRect;
import androidx.compose.ui.geometry.RoundRectKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class Outline {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Generic extends Outline {
        public final Path path;

        public Generic(Path path) {
            super(null);
            this.path = path;
        }

        @Override // androidx.compose.ui.graphics.Outline
        public final Rect getBounds() {
            return ((AndroidPath) this.path).getBounds();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Rectangle extends Outline {
        public final Rect rect;

        public Rectangle(Rect rect) {
            super(null);
            this.rect = rect;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof Rectangle) {
                return Intrinsics.areEqual(this.rect, ((Rectangle) obj).rect);
            }
            return false;
        }

        @Override // androidx.compose.ui.graphics.Outline
        public final Rect getBounds() {
            return this.rect;
        }

        public final int hashCode() {
            return this.rect.hashCode();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Rounded extends Outline {
        public final RoundRect roundRect;
        public final AndroidPath roundRectPath;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Rounded(RoundRect roundRect) {
            super(0 == true ? 1 : 0);
            AndroidPath androidPath = null;
            this.roundRect = roundRect;
            if (!RoundRectKt.isSimple(roundRect)) {
                androidPath = AndroidPath_androidKt.Path();
                Path.addRoundRect$default(androidPath, roundRect);
            }
            this.roundRectPath = androidPath;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof Rounded) {
                return Intrinsics.areEqual(this.roundRect, ((Rounded) obj).roundRect);
            }
            return false;
        }

        @Override // androidx.compose.ui.graphics.Outline
        public final Rect getBounds() {
            RoundRect roundRect = this.roundRect;
            return new Rect(roundRect.left, roundRect.top, roundRect.right, roundRect.bottom);
        }

        public final int hashCode() {
            return this.roundRect.hashCode();
        }
    }

    public /* synthetic */ Outline(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract Rect getBounds();

    private Outline() {
    }
}
