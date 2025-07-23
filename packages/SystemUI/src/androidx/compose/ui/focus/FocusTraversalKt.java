package androidx.compose.ui.focus;

import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.unit.LayoutDirection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class FocusTraversalKt {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[LayoutDirection.values().length];
            try {
                iArr[LayoutDirection.Ltr.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[LayoutDirection.Rtl.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[FocusStateImpl.values().length];
            try {
                iArr2[FocusStateImpl.Active.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[FocusStateImpl.ActiveParent.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[FocusStateImpl.Captured.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[FocusStateImpl.Inactive.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:77:0x0059, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.compose.ui.focus.FocusTargetNode findActiveFocusNode(androidx.compose.ui.focus.FocusTargetNode r9) {
        /*
            boolean r0 = androidx.compose.ui.ComposeUiFlags.isTrackFocusEnabled
            r1 = 0
            if (r0 == 0) goto L16
            androidx.compose.ui.node.Owner r9 = androidx.compose.ui.node.DelegatableNodeKt.requireOwner(r9)
            androidx.compose.ui.platform.AndroidComposeView r9 = (androidx.compose.ui.platform.AndroidComposeView) r9
            androidx.compose.ui.focus.FocusOwnerImpl r9 = r9.focusOwner
            androidx.compose.ui.focus.FocusTargetNode r9 = r9.activeFocusTargetNode
            if (r9 == 0) goto Lc5
            boolean r0 = r9.isAttached
            if (r0 == 0) goto Lc5
            return r9
        L16:
            androidx.compose.ui.focus.FocusStateImpl r0 = r9.getFocusState()
            int[] r2 = androidx.compose.ui.focus.FocusTraversalKt.WhenMappings.$EnumSwitchMapping$1
            int r0 = r0.ordinal()
            r0 = r2[r0]
            r2 = 1
            if (r0 == r2) goto Lc6
            r3 = 2
            if (r0 == r3) goto L36
            r2 = 3
            if (r0 == r2) goto Lc6
            r9 = 4
            if (r0 != r9) goto L30
            goto Lc5
        L30:
            kotlin.NoWhenBranchMatchedException r9 = new kotlin.NoWhenBranchMatchedException
            r9.<init>()
            throw r9
        L36:
            androidx.compose.ui.Modifier$Node r0 = r9.node
            boolean r0 = r0.isAttached
            if (r0 != 0) goto L42
            java.lang.String r0 = "visitChildren called on an unattached node"
            androidx.compose.ui.internal.InlineClassHelperKt.throwIllegalStateException(r0)
        L42:
            androidx.compose.runtime.collection.MutableVector r0 = new androidx.compose.runtime.collection.MutableVector
            r3 = 16
            androidx.compose.ui.Modifier$Node[] r4 = new androidx.compose.ui.Modifier.Node[r3]
            r5 = 0
            r0.<init>(r4, r5)
            androidx.compose.ui.Modifier$Node r9 = r9.node
            androidx.compose.ui.Modifier$Node r4 = r9.child
            if (r4 != 0) goto L56
            androidx.compose.ui.node.DelegatableNodeKt.access$addLayoutNodeChildren(r0, r9)
            goto L59
        L56:
            r0.add(r4)
        L59:
            int r9 = r0.size
            if (r9 == 0) goto Lc5
            int r9 = r9 + (-1)
            java.lang.Object r9 = r0.removeAt(r9)
            androidx.compose.ui.Modifier$Node r9 = (androidx.compose.ui.Modifier.Node) r9
            int r4 = r9.aggregateChildKindSet
            r4 = r4 & 1024(0x400, float:1.435E-42)
            if (r4 != 0) goto L6f
            androidx.compose.ui.node.DelegatableNodeKt.access$addLayoutNodeChildren(r0, r9)
            goto L59
        L6f:
            if (r9 == 0) goto L59
            int r4 = r9.kindSet
            r4 = r4 & 1024(0x400, float:1.435E-42)
            if (r4 == 0) goto Lc2
            r4 = r1
        L78:
            if (r9 == 0) goto L59
            boolean r6 = r9 instanceof androidx.compose.ui.focus.FocusTargetNode
            if (r6 == 0) goto L87
            androidx.compose.ui.focus.FocusTargetNode r9 = (androidx.compose.ui.focus.FocusTargetNode) r9
            androidx.compose.ui.focus.FocusTargetNode r9 = findActiveFocusNode(r9)
            if (r9 == 0) goto Lbd
            return r9
        L87:
            int r6 = r9.kindSet
            r6 = r6 & 1024(0x400, float:1.435E-42)
            if (r6 == 0) goto Lbd
            boolean r6 = r9 instanceof androidx.compose.ui.node.DelegatingNode
            if (r6 == 0) goto Lbd
            r6 = r9
            androidx.compose.ui.node.DelegatingNode r6 = (androidx.compose.ui.node.DelegatingNode) r6
            androidx.compose.ui.Modifier$Node r6 = r6.delegate
            r7 = r5
        L97:
            if (r6 == 0) goto Lba
            int r8 = r6.kindSet
            r8 = r8 & 1024(0x400, float:1.435E-42)
            if (r8 == 0) goto Lb7
            int r7 = r7 + 1
            if (r7 != r2) goto La5
            r9 = r6
            goto Lb7
        La5:
            if (r4 != 0) goto Lae
            androidx.compose.runtime.collection.MutableVector r4 = new androidx.compose.runtime.collection.MutableVector
            androidx.compose.ui.Modifier$Node[] r8 = new androidx.compose.ui.Modifier.Node[r3]
            r4.<init>(r8, r5)
        Lae:
            if (r9 == 0) goto Lb4
            r4.add(r9)
            r9 = r1
        Lb4:
            r4.add(r6)
        Lb7:
            androidx.compose.ui.Modifier$Node r6 = r6.child
            goto L97
        Lba:
            if (r7 != r2) goto Lbd
            goto L78
        Lbd:
            androidx.compose.ui.Modifier$Node r9 = androidx.compose.ui.node.DelegatableNodeKt.access$pop(r4)
            goto L78
        Lc2:
            androidx.compose.ui.Modifier$Node r9 = r9.child
            goto L6f
        Lc5:
            return r1
        Lc6:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.focus.FocusTraversalKt.findActiveFocusNode(androidx.compose.ui.focus.FocusTargetNode):androidx.compose.ui.focus.FocusTargetNode");
    }

    public static final Rect focusRect(FocusTargetNode focusTargetNode) {
        Rect localBoundingBoxOf;
        NodeCoordinator nodeCoordinator = focusTargetNode.coordinator;
        if (nodeCoordinator != null && (localBoundingBoxOf = LayoutCoordinatesKt.findRootCoordinates(nodeCoordinator).localBoundingBoxOf(nodeCoordinator, false)) != null) {
            return localBoundingBoxOf;
        }
        Rect.Companion.getClass();
        return Rect.Zero;
    }

    /* JADX WARN: Code restructure failed: missing block: B:68:0x0028, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.compose.ui.focus.FocusTargetNode getActiveChild(androidx.compose.ui.focus.FocusTargetNode r9) {
        /*
            androidx.compose.ui.Modifier$Node r0 = r9.node
            boolean r0 = r0.isAttached
            r1 = 0
            if (r0 != 0) goto L9
            goto Laa
        L9:
            if (r0 != 0) goto L11
            java.lang.String r0 = "visitChildren called on an unattached node"
            androidx.compose.ui.internal.InlineClassHelperKt.throwIllegalStateException(r0)
        L11:
            androidx.compose.runtime.collection.MutableVector r0 = new androidx.compose.runtime.collection.MutableVector
            r2 = 16
            androidx.compose.ui.Modifier$Node[] r3 = new androidx.compose.ui.Modifier.Node[r2]
            r4 = 0
            r0.<init>(r3, r4)
            androidx.compose.ui.Modifier$Node r9 = r9.node
            androidx.compose.ui.Modifier$Node r3 = r9.child
            if (r3 != 0) goto L25
            androidx.compose.ui.node.DelegatableNodeKt.access$addLayoutNodeChildren(r0, r9)
            goto L28
        L25:
            r0.add(r3)
        L28:
            int r9 = r0.size
            if (r9 == 0) goto Laa
            int r9 = r9 + (-1)
            java.lang.Object r9 = r0.removeAt(r9)
            androidx.compose.ui.Modifier$Node r9 = (androidx.compose.ui.Modifier.Node) r9
            int r3 = r9.aggregateChildKindSet
            r3 = r3 & 1024(0x400, float:1.435E-42)
            if (r3 != 0) goto L3e
            androidx.compose.ui.node.DelegatableNodeKt.access$addLayoutNodeChildren(r0, r9)
            goto L28
        L3e:
            if (r9 == 0) goto L28
            int r3 = r9.kindSet
            r3 = r3 & 1024(0x400, float:1.435E-42)
            if (r3 == 0) goto La7
            r3 = r1
        L47:
            if (r9 == 0) goto L28
            boolean r5 = r9 instanceof androidx.compose.ui.focus.FocusTargetNode
            r6 = 1
            if (r5 == 0) goto L6c
            androidx.compose.ui.focus.FocusTargetNode r9 = (androidx.compose.ui.focus.FocusTargetNode) r9
            androidx.compose.ui.Modifier$Node r5 = r9.node
            boolean r5 = r5.isAttached
            if (r5 == 0) goto La2
            androidx.compose.ui.focus.FocusStateImpl r5 = r9.getFocusState()
            int[] r7 = androidx.compose.ui.focus.FocusTraversalKt.WhenMappings.$EnumSwitchMapping$1
            int r5 = r5.ordinal()
            r5 = r7[r5]
            if (r5 == r6) goto L6b
            r6 = 2
            if (r5 == r6) goto L6b
            r6 = 3
            if (r5 == r6) goto L6b
            goto La2
        L6b:
            return r9
        L6c:
            int r5 = r9.kindSet
            r5 = r5 & 1024(0x400, float:1.435E-42)
            if (r5 == 0) goto La2
            boolean r5 = r9 instanceof androidx.compose.ui.node.DelegatingNode
            if (r5 == 0) goto La2
            r5 = r9
            androidx.compose.ui.node.DelegatingNode r5 = (androidx.compose.ui.node.DelegatingNode) r5
            androidx.compose.ui.Modifier$Node r5 = r5.delegate
            r7 = r4
        L7c:
            if (r5 == 0) goto L9f
            int r8 = r5.kindSet
            r8 = r8 & 1024(0x400, float:1.435E-42)
            if (r8 == 0) goto L9c
            int r7 = r7 + 1
            if (r7 != r6) goto L8a
            r9 = r5
            goto L9c
        L8a:
            if (r3 != 0) goto L93
            androidx.compose.runtime.collection.MutableVector r3 = new androidx.compose.runtime.collection.MutableVector
            androidx.compose.ui.Modifier$Node[] r8 = new androidx.compose.ui.Modifier.Node[r2]
            r3.<init>(r8, r4)
        L93:
            if (r9 == 0) goto L99
            r3.add(r9)
            r9 = r1
        L99:
            r3.add(r5)
        L9c:
            androidx.compose.ui.Modifier$Node r5 = r5.child
            goto L7c
        L9f:
            if (r7 != r6) goto La2
            goto L47
        La2:
            androidx.compose.ui.Modifier$Node r9 = androidx.compose.ui.node.DelegatableNodeKt.access$pop(r3)
            goto L47
        La7:
            androidx.compose.ui.Modifier$Node r9 = r9.child
            goto L3e
        Laa:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.focus.FocusTraversalKt.getActiveChild(androidx.compose.ui.focus.FocusTargetNode):androidx.compose.ui.focus.FocusTargetNode");
    }

    public static final boolean isEligibleForFocusSearch(FocusTargetNode focusTargetNode) {
        LayoutNode layoutNode;
        NodeCoordinator nodeCoordinator;
        LayoutNode layoutNode2;
        NodeCoordinator nodeCoordinator2 = focusTargetNode.coordinator;
        return (nodeCoordinator2 == null || (layoutNode = nodeCoordinator2.layoutNode) == null || !layoutNode.isPlaced() || (nodeCoordinator = focusTargetNode.coordinator) == null || (layoutNode2 = nodeCoordinator.layoutNode) == null || !layoutNode2.isAttached()) ? false : true;
    }
}
