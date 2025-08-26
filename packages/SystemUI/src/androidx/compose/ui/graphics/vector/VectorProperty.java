package androidx.compose.ui.graphics.vector;

import androidx.compose.ui.graphics.Brush;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public abstract class VectorProperty<T> {

    public final class Fill extends VectorProperty<Brush> {
        public static final Fill INSTANCE = new Fill();

        private Fill() {
            super(null);
        }
    }

    public final class FillAlpha extends VectorProperty<Float> {
        public static final FillAlpha INSTANCE = new FillAlpha();

        private FillAlpha() {
            super(null);
        }
    }

    public final class PathData extends VectorProperty<List<? extends PathNode>> {
        public static final PathData INSTANCE = new PathData();

        private PathData() {
            super(null);
        }
    }

    public final class PivotX extends VectorProperty<Float> {
        public static final PivotX INSTANCE = new PivotX();

        private PivotX() {
            super(null);
        }
    }

    public final class PivotY extends VectorProperty<Float> {
        public static final PivotY INSTANCE = new PivotY();

        private PivotY() {
            super(null);
        }
    }

    public final class Rotation extends VectorProperty<Float> {
        public static final Rotation INSTANCE = new Rotation();

        private Rotation() {
            super(null);
        }
    }

    public final class ScaleX extends VectorProperty<Float> {
        public static final ScaleX INSTANCE = new ScaleX();

        private ScaleX() {
            super(null);
        }
    }

    public final class ScaleY extends VectorProperty<Float> {
        public static final ScaleY INSTANCE = new ScaleY();

        private ScaleY() {
            super(null);
        }
    }

    public final class Stroke extends VectorProperty<Brush> {
        public static final Stroke INSTANCE = new Stroke();

        private Stroke() {
            super(null);
        }
    }

    public final class StrokeAlpha extends VectorProperty<Float> {
        public static final StrokeAlpha INSTANCE = new StrokeAlpha();

        private StrokeAlpha() {
            super(null);
        }
    }

    public final class StrokeLineWidth extends VectorProperty<Float> {
        public static final StrokeLineWidth INSTANCE = new StrokeLineWidth();

        private StrokeLineWidth() {
            super(null);
        }
    }

    public final class TranslateX extends VectorProperty<Float> {
        public static final TranslateX INSTANCE = new TranslateX();

        private TranslateX() {
            super(null);
        }
    }

    public final class TranslateY extends VectorProperty<Float> {
        public static final TranslateY INSTANCE = new TranslateY();

        private TranslateY() {
            super(null);
        }
    }

    public final class TrimPathEnd extends VectorProperty<Float> {
        public static final TrimPathEnd INSTANCE = new TrimPathEnd();

        private TrimPathEnd() {
            super(null);
        }
    }

    public final class TrimPathOffset extends VectorProperty<Float> {
        public static final TrimPathOffset INSTANCE = new TrimPathOffset();

        private TrimPathOffset() {
            super(null);
        }
    }

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
