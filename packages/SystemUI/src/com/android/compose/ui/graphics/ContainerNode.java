package com.android.compose.ui.graphics;

import androidx.compose.foundation.BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.snapshots.SnapshotStateList;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.ClipOp;
import androidx.compose.ui.graphics.Path;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.graphics.layer.GraphicsLayerKt;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DrawModifierNode;
import androidx.compose.ui.node.LayoutAwareModifierNode;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import com.android.compose.ui.graphics.DrawInContainerNode;

/* loaded from: classes.dex */
public final class ContainerNode extends Modifier.Node implements LayoutAwareModifierNode, DrawModifierNode {
    public ContainerState state;

    public ContainerNode(ContainerState containerState) {
        this.state = containerState;
    }

    @Override // androidx.compose.ui.node.DrawModifierNode
    public final void draw(LayoutNodeDrawScope layoutNodeDrawScope) {
        layoutNodeDrawScope.drawContent();
        SnapshotStateList snapshotStateList = this.state.renderers;
        int size = snapshotStateList.size();
        for (int i = 0; i < size; i++) {
            DrawInContainerNode.LayerWithRenderer layerWithRenderer = (DrawInContainerNode.LayerWithRenderer) snapshotStateList.get(i);
            DrawInContainerNode drawInContainerNode = DrawInContainerNode.this;
            if (((Boolean) drawInContainerNode.enabled.invoke()).booleanValue()) {
                long jM402minusMKHz9U = Offset.m402minusMKHz9U(((Offset) ((SnapshotMutableStateImpl) drawInContainerNode.lastOffsetInWindow$delegate).getValue()).packedValue, ((Offset) ((SnapshotMutableStateImpl) drawInContainerNode.state.lastOffsetInWindow$delegate).getValue()).packedValue);
                float fIntBitsToFloat = Float.intBitsToFloat((int) (jM402minusMKHz9U >> 32));
                float fIntBitsToFloat2 = Float.intBitsToFloat((int) (jM402minusMKHz9U & 4294967295L));
                Path path = (Path) drawInContainerNode.clipPath.invoke(layoutNodeDrawScope.getLayoutDirection(), DelegatableNodeKt.requireLayoutNode(drawInContainerNode).density);
                CanvasDrawScope canvasDrawScope = layoutNodeDrawScope.canvasDrawScope;
                GraphicsLayer graphicsLayer = layerWithRenderer.layer;
                if (path != null) {
                    ClipOp.Companion.getClass();
                    int i2 = ClipOp.Intersect;
                    CanvasDrawScope$drawContext$1 canvasDrawScope$drawContext$1 = canvasDrawScope.drawContext;
                    long jM528getSizeNHjbRc = canvasDrawScope$drawContext$1.m528getSizeNHjbRc();
                    canvasDrawScope$drawContext$1.getCanvas().save();
                    try {
                        ((CanvasDrawScope$drawContext$1) canvasDrawScope$drawContext$1.transform.$this_asDrawTransform).getCanvas().mo425clipPathmtrdDE(path, i2);
                        canvasDrawScope.drawContext.transform.translate(fIntBitsToFloat, fIntBitsToFloat2);
                        try {
                            GraphicsLayerKt.drawLayer(layoutNodeDrawScope, graphicsLayer);
                        } finally {
                        }
                    } finally {
                        BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(canvasDrawScope$drawContext$1, jM528getSizeNHjbRc);
                    }
                } else {
                    canvasDrawScope.drawContext.transform.translate(fIntBitsToFloat, fIntBitsToFloat2);
                    try {
                        GraphicsLayerKt.drawLayer(layoutNodeDrawScope, graphicsLayer);
                    } finally {
                    }
                }
            }
        }
    }

    @Override // androidx.compose.ui.node.LayoutAwareModifierNode
    public final void onPlaced(LayoutCoordinates layoutCoordinates) {
        ContainerState containerState = this.state;
        long jPositionInWindow = LayoutCoordinatesKt.positionInWindow(layoutCoordinates);
        ((SnapshotMutableStateImpl) containerState.lastOffsetInWindow$delegate).setValue(Offset.m395boximpl(jPositionInWindow));
    }
}
