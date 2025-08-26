package androidx.compose.ui.node;

import android.view.View;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.compose.ui.platform.DrawChildContainer;
import androidx.compose.ui.platform.GraphicsLayerOwnerLayer;
import androidx.compose.ui.platform.RenderNodeLayer;
import androidx.compose.ui.platform.ViewLayer;
import androidx.compose.ui.platform.ViewLayerContainer;
import androidx.compose.ui.platform.WeakCache;
import java.lang.ref.Reference;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public interface Owner {
    public static final Companion Companion = Companion.$$INSTANCE;

    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }
    }

    public interface OnLayoutCompletedListener {
        void onLayoutComplete();
    }

    static OwnedLayer createLayer$default(Owner owner, Function2 function2, Function0 function0, GraphicsLayer graphicsLayer, boolean z, int i) {
        Reference referencePoll;
        MutableVector mutableVector;
        Object obj = null;
        if ((i & 4) != 0) {
            graphicsLayer = null;
        }
        if ((i & 8) != 0) {
            z = false;
        }
        AndroidComposeView androidComposeView = (AndroidComposeView) owner;
        if (graphicsLayer != null) {
            return new GraphicsLayerOwnerLayer(graphicsLayer, null, androidComposeView, function2, function0);
        }
        if (z) {
            if (androidComposeView.isHardwareAccelerated() && androidComposeView.isRenderNodeCompatible) {
                try {
                    return new RenderNodeLayer(androidComposeView, function2, function0);
                } catch (Throwable unused) {
                    androidComposeView.isRenderNodeCompatible = false;
                }
            }
            if (androidComposeView.viewLayersContainer == null) {
                ViewLayer.Companion.getClass();
                if (!ViewLayer.hasRetrievedMethod) {
                    ViewLayer.Companion.updateDisplayList(new View(androidComposeView.getContext()));
                }
                DrawChildContainer drawChildContainer = ViewLayer.shouldUseDispatchDraw ? new DrawChildContainer(androidComposeView.getContext()) : new ViewLayerContainer(androidComposeView.getContext());
                androidComposeView.viewLayersContainer = drawChildContainer;
                androidComposeView.addView(drawChildContainer, -1);
            }
            DrawChildContainer drawChildContainer2 = androidComposeView.viewLayersContainer;
            drawChildContainer2.getClass();
            return new ViewLayer(androidComposeView, drawChildContainer2, function2, function0);
        }
        WeakCache weakCache = androidComposeView.layerCache;
        do {
            referencePoll = weakCache.referenceQueue.poll();
            mutableVector = weakCache.values;
            if (referencePoll != null) {
                mutableVector.remove(referencePoll);
            }
        } while (referencePoll != null);
        while (true) {
            int i2 = mutableVector.size;
            if (i2 == 0) {
                break;
            }
            Object obj2 = ((Reference) mutableVector.removeAt(i2 - 1)).get();
            if (obj2 != null) {
                obj = obj2;
                break;
            }
        }
        OwnedLayer ownedLayer = (OwnedLayer) obj;
        if (ownedLayer == null) {
            return new GraphicsLayerOwnerLayer(androidComposeView.graphicsContext.createGraphicsLayer(), androidComposeView.graphicsContext, androidComposeView, function2, function0);
        }
        ownedLayer.reuseLayer(function2, function0);
        return ownedLayer;
    }
}
