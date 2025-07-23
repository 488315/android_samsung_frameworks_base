package androidx.compose.ui.graphics.vector;

import androidx.compose.ui.graphics.Brush;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class VectorProperty<T> {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Fill extends VectorProperty<Brush> {
        public static final Fill INSTANCE = new Fill();

        private Fill() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class FillAlpha extends VectorProperty<Float> {
        public static final FillAlpha INSTANCE = new FillAlpha();

        private FillAlpha() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PathData extends VectorProperty<List<? extends PathNode>> {
        public static final PathData INSTANCE = new PathData();

        private PathData() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PivotX extends VectorProperty<Float> {
        public static final PivotX INSTANCE = new PivotX();

        private PivotX() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PivotY extends VectorProperty<Float> {
        public static final PivotY INSTANCE = new PivotY();

        private PivotY() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Rotation extends VectorProperty<Float> {
        public static final Rotation INSTANCE = new Rotation();

        private Rotation() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ScaleX extends VectorProperty<Float> {
        public static final ScaleX INSTANCE = new ScaleX();

        private ScaleX() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ScaleY extends VectorProperty<Float> {
        public static final ScaleY INSTANCE = new ScaleY();

        private ScaleY() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Stroke extends VectorProperty<Brush> {
        public static final Stroke INSTANCE = new Stroke();

        private Stroke() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class StrokeAlpha extends VectorProperty<Float> {
        public static final StrokeAlpha INSTANCE = new StrokeAlpha();

        private StrokeAlpha() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class StrokeLineWidth extends VectorProperty<Float> {
        public static final StrokeLineWidth INSTANCE = new StrokeLineWidth();

        private StrokeLineWidth() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class TranslateX extends VectorProperty<Float> {
        public static final TranslateX INSTANCE = new TranslateX();

        private TranslateX() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class TranslateY extends VectorProperty<Float> {
        public static final TranslateY INSTANCE = new TranslateY();

        private TranslateY() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class TrimPathEnd extends VectorProperty<Float> {
        public static final TrimPathEnd INSTANCE = new TrimPathEnd();

        private TrimPathEnd() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class TrimPathOffset extends VectorProperty<Float> {
        public static final TrimPathOffset INSTANCE = new TrimPathOffset();

        private TrimPathOffset() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class TrimPathStart extends VectorProperty<Float> {
        public static final TrimPathStart INSTANCE = new TrimPathStart();

        private TrimPathStart() {
            super(null);
        }
    }

    public /* synthetic */ VectorProperty(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private VectorProperty() {
    }
}
