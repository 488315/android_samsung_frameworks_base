package androidx.compose.ui.graphics;

import android.view.ViewGroup;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.graphics.layer.GraphicsLayerV29;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
final class AndroidGraphicsContext implements GraphicsContext {
    public final Object lock = new Object();
    public final ViewGroup ownerView;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    final class UniqueDrawingIdApi29 {
        public static final /* synthetic */ int $r8$clinit = 0;

        static {
            new UniqueDrawingIdApi29();
        }

        private UniqueDrawingIdApi29() {
        }
    }

    static {
        new Companion(null);
    }

    public AndroidGraphicsContext(ViewGroup viewGroup) {
        this.ownerView = viewGroup;
    }

    @Override // androidx.compose.ui.graphics.GraphicsContext
    public final GraphicsLayer createGraphicsLayer() {
        GraphicsLayer graphicsLayer;
        synchronized (this.lock) {
            ViewGroup viewGroup = this.ownerView;
            int i = UniqueDrawingIdApi29.$r8$clinit;
            graphicsLayer = new GraphicsLayer(new GraphicsLayerV29(viewGroup.getUniqueDrawingId(), null, null, 6, null), null);
        }
        return graphicsLayer;
    }

    @Override // androidx.compose.ui.graphics.GraphicsContext
    public final void releaseGraphicsLayer(GraphicsLayer graphicsLayer) {
        synchronized (this.lock) {
            if (!graphicsLayer.isReleased) {
                graphicsLayer.isReleased = true;
                graphicsLayer.discardContentIfReleasedAndHaveNoParentLayerUsages();
            }
            Unit unit = Unit.INSTANCE;
        }
    }
}
