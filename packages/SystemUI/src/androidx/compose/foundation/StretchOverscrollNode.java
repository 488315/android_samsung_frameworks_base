package androidx.compose.foundation;

import android.graphics.Canvas;
import android.graphics.RenderNode;
import android.widget.EdgeEffect;
import androidx.compose.ui.node.DelegatableNode;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.DrawModifierNode;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class StretchOverscrollNode extends DelegatingNode implements DrawModifierNode {
    public RenderNode _renderNode;
    public final EdgeEffectWrapper edgeEffectWrapper;
    public final AndroidEdgeEffectOverscrollEffect overscrollEffect;

    public StretchOverscrollNode(DelegatableNode delegatableNode, AndroidEdgeEffectOverscrollEffect androidEdgeEffectOverscrollEffect, EdgeEffectWrapper edgeEffectWrapper) {
        this.overscrollEffect = androidEdgeEffectOverscrollEffect;
        this.edgeEffectWrapper = edgeEffectWrapper;
        delegate(delegatableNode);
    }

    public static boolean drawWithRotation(float f, EdgeEffect edgeEffect, Canvas canvas) {
        if (f == 0.0f) {
            return edgeEffect.draw(canvas);
        }
        int save = canvas.save();
        canvas.rotate(f);
        boolean draw = edgeEffect.draw(canvas);
        canvas.restoreToCount(save);
        return draw;
    }

    /* JADX WARN: Removed duplicated region for block: B:118:0x0294  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x02ae  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x02f3  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x030d  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0353  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x0358  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x035d  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x035a  */
    @Override // androidx.compose.ui.node.DrawModifierNode
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void draw(androidx.compose.ui.node.LayoutNodeDrawScope r26) {
        /*
            Method dump skipped, instructions count: 1026
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.StretchOverscrollNode.draw(androidx.compose.ui.node.LayoutNodeDrawScope):void");
    }

    public final RenderNode getRenderNode() {
        RenderNode renderNode = this._renderNode;
        if (renderNode != null) {
            return renderNode;
        }
        RenderNode renderNode2 = new RenderNode("AndroidEdgeEffectOverscrollEffect");
        this._renderNode = renderNode2;
        return renderNode2;
    }
}
