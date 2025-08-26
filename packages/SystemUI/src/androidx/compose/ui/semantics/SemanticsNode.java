package androidx.compose.ui.semantics;

import androidx.collection.MutableScatterMap;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import androidx.compose.ui.node.DelegatableNode;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.NodeCoordinator;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final class SemanticsNode {
    public SemanticsNode fakeNodeParent;
    public final int id;
    public boolean isFake;
    public final LayoutNode layoutNode;
    public final boolean mergingEnabled;
    public final Modifier.Node outerSemanticsNode;
    public final SemanticsConfiguration unmergedConfig;

    public SemanticsNode(Modifier.Node node, boolean z, LayoutNode layoutNode, SemanticsConfiguration semanticsConfiguration) {
        this.outerSemanticsNode = node;
        this.mergingEnabled = z;
        this.layoutNode = layoutNode;
        this.unmergedConfig = semanticsConfiguration;
        this.id = layoutNode.semanticsId;
    }

    public static /* synthetic */ List getChildren$ui_release$default(int i, SemanticsNode semanticsNode) {
        return semanticsNode.getChildren$ui_release((i & 1) != 0 ? !semanticsNode.mergingEnabled : false, (i & 2) == 0);
    }

    /* renamed from: fakeSemanticsNode-ypyhhiA, reason: not valid java name */
    public final SemanticsNode m717fakeSemanticsNodeypyhhiA(Role role, Function1 function1) {
        SemanticsConfiguration semanticsConfiguration = new SemanticsConfiguration();
        semanticsConfiguration.isMergingSemanticsOfDescendants = false;
        semanticsConfiguration.isClearingSemantics = false;
        function1.mo781invoke(semanticsConfiguration);
        SemanticsNode semanticsNode = new SemanticsNode(new SemanticsNode$fakeSemanticsNode$fakeNode$1(function1), false, new LayoutNode(true, this.id + (role != null ? 1000000000 : 2000000000)), semanticsConfiguration);
        semanticsNode.isFake = true;
        semanticsNode.fakeNodeParent = this;
        return semanticsNode;
    }

    public final void fillOneLayerOfSemanticsWrappers(LayoutNode layoutNode, List list) {
        MutableVector zSortedChildren = layoutNode.getZSortedChildren();
        Object[] objArr = zSortedChildren.content;
        int i = zSortedChildren.size;
        for (int i2 = 0; i2 < i; i2++) {
            LayoutNode layoutNode2 = (LayoutNode) objArr[i2];
            if (layoutNode2.isAttached() && !layoutNode2.isDeactivated) {
                if (layoutNode2.nodes.m665hasH91voCI$ui_release(8)) {
                    ((ArrayList) list).add(SemanticsNodeKt.SemanticsNode(layoutNode2, this.mergingEnabled));
                } else {
                    fillOneLayerOfSemanticsWrappers(layoutNode2, list);
                }
            }
        }
    }

    public final NodeCoordinator findCoordinatorToGetBounds$ui_release() {
        if (this.isFake) {
            SemanticsNode parent = getParent();
            if (parent != null) {
                return parent.findCoordinatorToGetBounds$ui_release();
            }
            return null;
        }
        DelegatableNode outerMergingSemantics = SemanticsNodeKt.getOuterMergingSemantics(this.layoutNode);
        if (outerMergingSemantics == null) {
            outerMergingSemantics = this.outerSemanticsNode;
        }
        return DelegatableNodeKt.m634requireCoordinator64DMado(outerMergingSemantics, 8);
    }

    public final void findOneLayerOfMergingSemanticsNodes(List list, List list2) {
        ArrayList arrayList = (ArrayList) list;
        unmergedChildren$ui_release(list, false);
        int size = arrayList.size();
        for (int size2 = arrayList.size(); size2 < size; size2++) {
            SemanticsNode semanticsNode = (SemanticsNode) arrayList.get(size2);
            if (semanticsNode.isMergingSemanticsOfDescendants()) {
                ((ArrayList) list2).add(semanticsNode);
            } else if (!semanticsNode.unmergedConfig.isClearingSemantics) {
                semanticsNode.findOneLayerOfMergingSemanticsNodes(list, list2);
            }
        }
    }

    public final Rect getBoundsInRoot() {
        Rect rectLocalBoundingBoxOf;
        NodeCoordinator nodeCoordinatorFindCoordinatorToGetBounds$ui_release = findCoordinatorToGetBounds$ui_release();
        if (nodeCoordinatorFindCoordinatorToGetBounds$ui_release != null) {
            if (!nodeCoordinatorFindCoordinatorToGetBounds$ui_release.getTail().isAttached) {
                nodeCoordinatorFindCoordinatorToGetBounds$ui_release = null;
            }
            if (nodeCoordinatorFindCoordinatorToGetBounds$ui_release != null && (rectLocalBoundingBoxOf = LayoutCoordinatesKt.findRootCoordinates(nodeCoordinatorFindCoordinatorToGetBounds$ui_release).localBoundingBoxOf(nodeCoordinatorFindCoordinatorToGetBounds$ui_release, true)) != null) {
                return rectLocalBoundingBoxOf;
            }
        }
        Rect.Companion.getClass();
        return Rect.Zero;
    }

    public final Rect getBoundsInWindow() {
        Rect rectBoundsInWindow;
        NodeCoordinator nodeCoordinatorFindCoordinatorToGetBounds$ui_release = findCoordinatorToGetBounds$ui_release();
        if (nodeCoordinatorFindCoordinatorToGetBounds$ui_release != null) {
            if (!nodeCoordinatorFindCoordinatorToGetBounds$ui_release.getTail().isAttached) {
                nodeCoordinatorFindCoordinatorToGetBounds$ui_release = null;
            }
            if (nodeCoordinatorFindCoordinatorToGetBounds$ui_release != null && (rectBoundsInWindow = LayoutCoordinatesKt.boundsInWindow(nodeCoordinatorFindCoordinatorToGetBounds$ui_release)) != null) {
                return rectBoundsInWindow;
            }
        }
        Rect.Companion.getClass();
        return Rect.Zero;
    }

    public final List getChildren$ui_release(boolean z, boolean z2) {
        if (!z && this.unmergedConfig.isClearingSemantics) {
            return EmptyList.INSTANCE;
        }
        ArrayList arrayList = new ArrayList();
        if (!isMergingSemanticsOfDescendants()) {
            return unmergedChildren$ui_release(arrayList, z2);
        }
        ArrayList arrayList2 = new ArrayList();
        findOneLayerOfMergingSemanticsNodes(arrayList, arrayList2);
        return arrayList2;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0063  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final SemanticsConfiguration getConfig() {
        boolean zIsMergingSemanticsOfDescendants = isMergingSemanticsOfDescendants();
        SemanticsConfiguration semanticsConfiguration = this.unmergedConfig;
        if (!zIsMergingSemanticsOfDescendants) {
            return semanticsConfiguration;
        }
        semanticsConfiguration.getClass();
        SemanticsConfiguration semanticsConfiguration2 = new SemanticsConfiguration();
        semanticsConfiguration2.isMergingSemanticsOfDescendants = semanticsConfiguration.isMergingSemanticsOfDescendants;
        semanticsConfiguration2.isClearingSemantics = semanticsConfiguration.isClearingSemantics;
        MutableScatterMap mutableScatterMap = semanticsConfiguration2.props;
        MutableScatterMap mutableScatterMap2 = semanticsConfiguration.props;
        mutableScatterMap.getClass();
        Object[] objArr = mutableScatterMap2.keys;
        Object[] objArr2 = mutableScatterMap2.values;
        long[] jArr = mutableScatterMap2.metadata;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i = 0;
            while (true) {
                long j = jArr[i];
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i2 = 8 - ((~(i - length)) >>> 31);
                    for (int i3 = 0; i3 < i2; i3++) {
                        if ((255 & j) < 128) {
                            int i4 = (i << 3) + i3;
                            mutableScatterMap.set(objArr[i4], objArr2[i4]);
                        }
                        j >>= 8;
                    }
                    if (i2 != 8) {
                        break;
                    }
                    if (i == length) {
                        break;
                    }
                    i++;
                }
            }
        }
        mergeConfig(new ArrayList(), semanticsConfiguration2);
        return semanticsConfiguration2;
    }

    public final SemanticsNode getParent() {
        LayoutNode parent$ui_release;
        SemanticsNode semanticsNode = this.fakeNodeParent;
        if (semanticsNode != null) {
            return semanticsNode;
        }
        LayoutNode layoutNode = this.layoutNode;
        boolean z = this.mergingEnabled;
        if (z) {
            parent$ui_release = layoutNode.getParent$ui_release();
            while (parent$ui_release != null) {
                SemanticsConfiguration semanticsConfiguration = parent$ui_release.getSemanticsConfiguration();
                if (semanticsConfiguration != null && semanticsConfiguration.isMergingSemanticsOfDescendants) {
                    break;
                }
                parent$ui_release = parent$ui_release.getParent$ui_release();
            }
            parent$ui_release = null;
        } else {
            parent$ui_release = null;
        }
        if (parent$ui_release == null) {
            LayoutNode parent$ui_release2 = layoutNode.getParent$ui_release();
            while (true) {
                if (parent$ui_release2 == null) {
                    parent$ui_release = null;
                    break;
                }
                if (parent$ui_release2.nodes.m665hasH91voCI$ui_release(8)) {
                    parent$ui_release = parent$ui_release2;
                    break;
                }
                parent$ui_release2 = parent$ui_release2.getParent$ui_release();
            }
        }
        if (parent$ui_release == null) {
            return null;
        }
        return SemanticsNodeKt.SemanticsNode(parent$ui_release, z);
    }

    public final boolean isMergingSemanticsOfDescendants() {
        return this.mergingEnabled && this.unmergedConfig.isMergingSemanticsOfDescendants;
    }

    public final boolean isUnmergedLeafNode$ui_release() {
        if (this.isFake || !getChildren$ui_release$default(4, this).isEmpty()) {
            return false;
        }
        LayoutNode parent$ui_release = this.layoutNode.getParent$ui_release();
        while (true) {
            if (parent$ui_release == null) {
                parent$ui_release = null;
                break;
            }
            SemanticsConfiguration semanticsConfiguration = parent$ui_release.getSemanticsConfiguration();
            if (semanticsConfiguration != null && semanticsConfiguration.isMergingSemanticsOfDescendants) {
                break;
            }
            parent$ui_release = parent$ui_release.getParent$ui_release();
        }
        return parent$ui_release == null;
    }

    public final void mergeConfig(List list, SemanticsConfiguration semanticsConfiguration) {
        int i;
        ArrayList arrayList;
        int i2;
        int i3;
        ArrayList arrayList2;
        int i4;
        int i5;
        if (this.unmergedConfig.isClearingSemantics) {
            return;
        }
        ArrayList arrayList3 = (ArrayList) list;
        int size = arrayList3.size();
        unmergedChildren$ui_release(list, false);
        int size2 = arrayList3.size();
        while (size < size2) {
            SemanticsNode semanticsNode = (SemanticsNode) arrayList3.get(size);
            if (semanticsNode.isMergingSemanticsOfDescendants()) {
                i = size2;
                arrayList = arrayList3;
                i2 = size;
            } else {
                SemanticsConfiguration semanticsConfiguration2 = semanticsNode.unmergedConfig;
                semanticsConfiguration.getClass();
                MutableScatterMap mutableScatterMap = semanticsConfiguration2.props;
                Object[] objArr = mutableScatterMap.keys;
                Object[] objArr2 = mutableScatterMap.values;
                long[] jArr = mutableScatterMap.metadata;
                int length = jArr.length - 2;
                if (length >= 0) {
                    int i6 = 0;
                    while (true) {
                        long j = jArr[i6];
                        if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i7 = 8;
                            int i8 = 8 - ((~(i6 - length)) >>> 31);
                            int i9 = 0;
                            while (i9 < i8) {
                                if ((j & 255) < 128) {
                                    int i10 = (i6 << 3) + i9;
                                    Object obj = objArr[i10];
                                    i5 = i7;
                                    Object obj2 = objArr2[i10];
                                    i3 = size2;
                                    SemanticsPropertyKey semanticsPropertyKey = (SemanticsPropertyKey) obj;
                                    arrayList2 = arrayList3;
                                    i4 = size;
                                    Object objInvoke = semanticsPropertyKey.mergePolicy.invoke(semanticsConfiguration.props.get(semanticsPropertyKey), obj2);
                                    if (objInvoke != null) {
                                        semanticsConfiguration.props.set(semanticsPropertyKey, objInvoke);
                                    }
                                } else {
                                    i3 = size2;
                                    arrayList2 = arrayList3;
                                    i4 = size;
                                    i5 = i7;
                                }
                                j >>= i5;
                                i9++;
                                size2 = i3;
                                arrayList3 = arrayList2;
                                i7 = i5;
                                size = i4;
                            }
                            i = size2;
                            arrayList = arrayList3;
                            i2 = size;
                            if (i8 != i7) {
                                break;
                            }
                        } else {
                            i = size2;
                            arrayList = arrayList3;
                            i2 = size;
                        }
                        if (i6 == length) {
                            break;
                        }
                        i6++;
                        size2 = i;
                        arrayList3 = arrayList;
                        size = i2;
                    }
                } else {
                    i = size2;
                    arrayList = arrayList3;
                    i2 = size;
                }
                semanticsNode.mergeConfig(list, semanticsConfiguration);
            }
            size = i2 + 1;
            size2 = i;
            arrayList3 = arrayList;
        }
    }

    public final List unmergedChildren$ui_release(List list, boolean z) {
        if (this.isFake) {
            return EmptyList.INSTANCE;
        }
        fillOneLayerOfSemanticsWrappers(this.layoutNode, list);
        if (z) {
            SemanticsProperties.INSTANCE.getClass();
            SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.Role;
            SemanticsConfiguration semanticsConfiguration = this.unmergedConfig;
            final Role role = (Role) SemanticsConfigurationKt.getOrNull(semanticsConfiguration, semanticsPropertyKey);
            if (role != null && semanticsConfiguration.isMergingSemanticsOfDescendants && !list.isEmpty()) {
                ((ArrayList) list).add(m717fakeSemanticsNodeypyhhiA(role, new Function1() { // from class: androidx.compose.ui.semantics.SemanticsNode$emitFakeNodes$fakeNode$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        SemanticsPropertiesKt.m719setRolekuIjeqM((SemanticsPropertyReceiver) obj, role.value);
                        return Unit.INSTANCE;
                    }
                }));
            }
            SemanticsPropertyKey semanticsPropertyKey2 = SemanticsProperties.ContentDescription;
            if (semanticsConfiguration.props.containsKey(semanticsPropertyKey2) && !list.isEmpty() && semanticsConfiguration.isMergingSemanticsOfDescendants) {
                List list2 = (List) SemanticsConfigurationKt.getOrNull(semanticsConfiguration, semanticsPropertyKey2);
                final String str = list2 != null ? (String) CollectionsKt___CollectionsKt.firstOrNull(list2) : null;
                if (str != null) {
                    ((ArrayList) list).add(0, m717fakeSemanticsNodeypyhhiA(null, new Function1() { // from class: androidx.compose.ui.semantics.SemanticsNode$emitFakeNodes$fakeNode$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            SemanticsPropertiesKt.setContentDescription((SemanticsPropertyReceiver) obj, str);
                            return Unit.INSTANCE;
                        }
                    }));
                }
            }
        }
        return list;
    }
}
