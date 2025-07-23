package androidx.compose.ui.node;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LayoutNodeLayoutDelegateKt {
    public static final boolean isOutMostLookaheadRoot(LayoutNode layoutNode) {
        if (layoutNode.lookaheadRoot == null) {
            return false;
        }
        LayoutNode parent$ui_release = layoutNode.getParent$ui_release();
        return (parent$ui_release != null ? parent$ui_release.lookaheadRoot : null) == null || layoutNode.layoutDelegate.detachedFromParentLookaheadPass;
    }
}
