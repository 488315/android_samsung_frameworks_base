package androidx.compose.ui.draw;

import androidx.collection.MutableObjectList;
import androidx.collection.ObjectListKt;
import androidx.compose.ui.graphics.GraphicsContext;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.internal.InlineClassHelperKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ScopedGraphicsContext implements GraphicsContext {
    public MutableObjectList allocatedGraphicsLayers;
    public GraphicsContext graphicsContext;

    @Override // androidx.compose.ui.graphics.GraphicsContext
    public final GraphicsLayer createGraphicsLayer() {
        GraphicsContext graphicsContext = this.graphicsContext;
        if (graphicsContext == null) {
            InlineClassHelperKt.throwIllegalStateException("GraphicsContext not provided");
        }
        GraphicsLayer createGraphicsLayer = graphicsContext.createGraphicsLayer();
        MutableObjectList mutableObjectList = this.allocatedGraphicsLayers;
        if (mutableObjectList != null) {
            mutableObjectList.add(createGraphicsLayer);
            return createGraphicsLayer;
        }
        Object[] objArr = ObjectListKt.EmptyArray;
        MutableObjectList mutableObjectList2 = new MutableObjectList(1);
        mutableObjectList2.add(createGraphicsLayer);
        this.allocatedGraphicsLayers = mutableObjectList2;
        return createGraphicsLayer;
    }

    @Override // androidx.compose.ui.graphics.GraphicsContext
    public final void releaseGraphicsLayer(GraphicsLayer graphicsLayer) {
        GraphicsContext graphicsContext = this.graphicsContext;
        if (graphicsContext != null) {
            graphicsContext.releaseGraphicsLayer(graphicsLayer);
        }
    }

    public final void releaseGraphicsLayers() {
        MutableObjectList mutableObjectList = this.allocatedGraphicsLayers;
        if (mutableObjectList != null) {
            Object[] objArr = mutableObjectList.content;
            int i = mutableObjectList._size;
            for (int i2 = 0; i2 < i; i2++) {
                releaseGraphicsLayer((GraphicsLayer) objArr[i2]);
            }
            mutableObjectList.clear();
        }
    }
}
