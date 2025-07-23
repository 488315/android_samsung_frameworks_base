package androidx.compose.ui.platform;

import android.graphics.Region;
import androidx.collection.IntObjectMapKt;
import androidx.collection.MutableIntObjectMap;
import androidx.collection.MutableScatterMap;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.MutableRect;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.semantics.AccessibilityAction;
import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.semantics.SemanticsActions;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsConfigurationKt;
import androidx.compose.ui.semantics.SemanticsNode;
import androidx.compose.ui.semantics.SemanticsNodeKt;
import androidx.compose.ui.semantics.SemanticsOwner;
import androidx.compose.ui.semantics.SemanticsProperties;
import androidx.compose.ui.semantics.SemanticsPropertyKey;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.viewinterop.AndroidViewHolder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SemanticsUtils_androidKt {
    public static final Rect DefaultFakeNodeBounds = new Rect(0.0f, 0.0f, 10.0f, 10.0f);

    public static final ScrollObservationScope findById(int i, List list) {
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            ArrayList arrayList = (ArrayList) list;
            if (((ScrollObservationScope) arrayList.get(i2)).semanticsNodeId == i) {
                return (ScrollObservationScope) arrayList.get(i2);
            }
        }
        return null;
    }

    public static final MutableIntObjectMap getAllUncoveredSemanticsNodesToIntObjectMap(SemanticsOwner semanticsOwner) {
        SemanticsNode unmergedRootSemanticsNode = semanticsOwner.getUnmergedRootSemanticsNode();
        LayoutNode layoutNode = unmergedRootSemanticsNode.layoutNode;
        if (!layoutNode.isPlaced() || !layoutNode.isAttached()) {
            return IntObjectMapKt.EmptyIntObjectMap;
        }
        MutableIntObjectMap mutableIntObjectMap = new MutableIntObjectMap(48);
        Rect boundsInRoot = unmergedRootSemanticsNode.getBoundsInRoot();
        getAllUncoveredSemanticsNodesToIntObjectMap$findAllSemanticNodesRecursive(new Region(Math.round(boundsInRoot.left), Math.round(boundsInRoot.top), Math.round(boundsInRoot.right), Math.round(boundsInRoot.bottom)), unmergedRootSemanticsNode, mutableIntObjectMap, unmergedRootSemanticsNode, new Region());
        return mutableIntObjectMap;
    }

    public static final void getAllUncoveredSemanticsNodesToIntObjectMap$findAllSemanticNodesRecursive(Region region, SemanticsNode semanticsNode, MutableIntObjectMap mutableIntObjectMap, SemanticsNode semanticsNode2, Region region2) {
        Rect rect;
        LayoutNode layoutNode;
        Object outerMergingSemantics;
        boolean isPlaced = semanticsNode2.layoutNode.isPlaced();
        LayoutNode layoutNode2 = semanticsNode2.layoutNode;
        boolean z = (isPlaced && layoutNode2.isAttached()) ? false : true;
        boolean isEmpty = region.isEmpty();
        int i = semanticsNode.id;
        int i2 = semanticsNode2.id;
        if (!isEmpty || i2 == i) {
            if (!z || semanticsNode2.isFake) {
                SemanticsConfiguration semanticsConfiguration = semanticsNode2.unmergedConfig;
                boolean z2 = semanticsConfiguration.isMergingSemanticsOfDescendants;
                Object obj = semanticsNode2.outerSemanticsNode;
                if (z2 && (outerMergingSemantics = SemanticsNodeKt.getOuterMergingSemantics(layoutNode2)) != null) {
                    obj = outerMergingSemantics;
                }
                Modifier.Node node = ((Modifier.Node) obj).node;
                SemanticsActions.INSTANCE.getClass();
                boolean z3 = SemanticsConfigurationKt.getOrNull(semanticsConfiguration, SemanticsActions.OnClick) != null;
                if (!node.node.isAttached) {
                    Rect.Companion.getClass();
                    rect = Rect.Zero;
                } else if (z3) {
                    NodeCoordinator m632requireCoordinator64DMado = DelegatableNodeKt.m632requireCoordinator64DMado(node, 8);
                    if (m632requireCoordinator64DMado.getTail().isAttached) {
                        LayoutCoordinates findRootCoordinates = LayoutCoordinatesKt.findRootCoordinates(m632requireCoordinator64DMado);
                        MutableRect mutableRect = m632requireCoordinator64DMado._rectCache;
                        if (mutableRect == null) {
                            mutableRect = new MutableRect(0.0f, 0.0f, 0.0f, 0.0f);
                            m632requireCoordinator64DMado._rectCache = mutableRect;
                        }
                        long m665calculateMinimumTouchTargetPaddingE7KxVPU = m632requireCoordinator64DMado.m665calculateMinimumTouchTargetPaddingE7KxVPU(m632requireCoordinator64DMado.m668getMinimumTouchTargetSizeNHjbRc());
                        int i3 = (int) (m665calculateMinimumTouchTargetPaddingE7KxVPU >> 32);
                        mutableRect.left = -Float.intBitsToFloat(i3);
                        int i4 = (int) (m665calculateMinimumTouchTargetPaddingE7KxVPU & 4294967295L);
                        mutableRect.top = -Float.intBitsToFloat(i4);
                        mutableRect.right = Float.intBitsToFloat(i3) + m632requireCoordinator64DMado.getMeasuredWidth();
                        mutableRect.bottom = Float.intBitsToFloat(i4) + m632requireCoordinator64DMado.getMeasuredHeight();
                        while (true) {
                            if (m632requireCoordinator64DMado == findRootCoordinates) {
                                rect = new Rect(mutableRect.left, mutableRect.top, mutableRect.right, mutableRect.bottom);
                                break;
                            }
                            m632requireCoordinator64DMado.rectInParent$ui_release(mutableRect, false, true);
                            if (mutableRect.isEmpty()) {
                                Rect.Companion.getClass();
                                rect = Rect.Zero;
                                break;
                            } else {
                                m632requireCoordinator64DMado = m632requireCoordinator64DMado.wrappedBy;
                                m632requireCoordinator64DMado.getClass();
                            }
                        }
                    } else {
                        Rect.Companion.getClass();
                        rect = Rect.Zero;
                    }
                } else {
                    NodeCoordinator m632requireCoordinator64DMado2 = DelegatableNodeKt.m632requireCoordinator64DMado(node, 8);
                    rect = LayoutCoordinatesKt.findRootCoordinates(m632requireCoordinator64DMado2).localBoundingBoxOf(m632requireCoordinator64DMado2, true);
                }
                int round = Math.round(rect.left);
                int round2 = Math.round(rect.top);
                int round3 = Math.round(rect.right);
                int round4 = Math.round(rect.bottom);
                region2.set(round, round2, round3, round4);
                if (i2 == i) {
                    i2 = -1;
                }
                if (!region2.op(region, Region.Op.INTERSECT)) {
                    if (semanticsNode2.isFake) {
                        SemanticsNode parent = semanticsNode2.getParent();
                        Rect boundsInRoot = (parent == null || (layoutNode = parent.layoutNode) == null || !layoutNode.isPlaced()) ? DefaultFakeNodeBounds : parent.getBoundsInRoot();
                        mutableIntObjectMap.set(i2, new SemanticsNodeWithAdjustedBounds(semanticsNode2, new android.graphics.Rect(Math.round(boundsInRoot.left), Math.round(boundsInRoot.top), Math.round(boundsInRoot.right), Math.round(boundsInRoot.bottom))));
                        return;
                    } else {
                        if (i2 == -1) {
                            mutableIntObjectMap.set(i2, new SemanticsNodeWithAdjustedBounds(semanticsNode2, region2.getBounds()));
                            return;
                        }
                        return;
                    }
                }
                mutableIntObjectMap.set(i2, new SemanticsNodeWithAdjustedBounds(semanticsNode2, region2.getBounds()));
                List children$ui_release$default = SemanticsNode.getChildren$ui_release$default(4, semanticsNode2);
                for (int size = children$ui_release$default.size() - 1; -1 < size; size--) {
                    SemanticsConfiguration config = ((SemanticsNode) children$ui_release$default.get(size)).getConfig();
                    SemanticsProperties.INSTANCE.getClass();
                    if (!config.props.containsKey(SemanticsProperties.LinkTestMarker)) {
                        getAllUncoveredSemanticsNodesToIntObjectMap$findAllSemanticNodesRecursive(region, semanticsNode, mutableIntObjectMap, (SemanticsNode) children$ui_release$default.get(size), region2);
                    }
                }
                if (isImportantForAccessibility(semanticsNode2)) {
                    region.op(round, round2, round3, round4, Region.Op.DIFFERENCE);
                }
            }
        }
    }

    public static final TextLayoutResult getTextLayoutResult(SemanticsConfiguration semanticsConfiguration) {
        Function1 function1;
        ArrayList arrayList = new ArrayList();
        SemanticsActions.INSTANCE.getClass();
        AccessibilityAction accessibilityAction = (AccessibilityAction) SemanticsConfigurationKt.getOrNull(semanticsConfiguration, SemanticsActions.GetTextLayoutResult);
        if (accessibilityAction == null || (function1 = (Function1) accessibilityAction.action) == null || !((Boolean) function1.mo779invoke(arrayList)).booleanValue()) {
            return null;
        }
        return (TextLayoutResult) arrayList.get(0);
    }

    public static final boolean isHidden(SemanticsNode semanticsNode) {
        NodeCoordinator findCoordinatorToGetBounds$ui_release = semanticsNode.findCoordinatorToGetBounds$ui_release();
        if (findCoordinatorToGetBounds$ui_release != null ? findCoordinatorToGetBounds$ui_release.isTransparent() : false) {
            return true;
        }
        SemanticsProperties semanticsProperties = SemanticsProperties.INSTANCE;
        semanticsProperties.getClass();
        SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.HideFromAccessibility;
        SemanticsConfiguration semanticsConfiguration = semanticsNode.unmergedConfig;
        if (semanticsConfiguration.props.containsKey(semanticsPropertyKey)) {
            return true;
        }
        semanticsProperties.getClass();
        return semanticsConfiguration.props.containsKey(SemanticsProperties.InvisibleToUser);
    }

    public static final boolean isImportantForAccessibility(SemanticsNode semanticsNode) {
        if (!isHidden(semanticsNode)) {
            SemanticsConfiguration semanticsConfiguration = semanticsNode.unmergedConfig;
            if (semanticsConfiguration.isMergingSemanticsOfDescendants) {
                return true;
            }
            MutableScatterMap mutableScatterMap = semanticsConfiguration.props;
            Object[] objArr = mutableScatterMap.keys;
            Object[] objArr2 = mutableScatterMap.values;
            long[] jArr = mutableScatterMap.metadata;
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
                                Object obj = objArr[i4];
                                Object obj2 = objArr2[i4];
                                if (((SemanticsPropertyKey) obj).isImportantForAccessibility) {
                                    return true;
                                }
                            }
                            j >>= 8;
                        }
                        if (i2 != 8) {
                            break;
                        }
                    }
                    if (i == length) {
                        break;
                    }
                    i++;
                }
            }
        }
        return false;
    }

    public static final AndroidViewHolder semanticsIdToView(AndroidViewsHandler androidViewsHandler, int i) {
        Object obj;
        Iterator it = androidViewsHandler.layoutNodeToHolder.entrySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (((LayoutNode) ((Map.Entry) obj).getKey()).semanticsId == i) {
                break;
            }
        }
        Map.Entry entry = (Map.Entry) obj;
        if (entry != null) {
            return (AndroidViewHolder) entry.getValue();
        }
        return null;
    }

    /* renamed from: toLegacyClassName-V4PA4sw, reason: not valid java name */
    public static final String m710toLegacyClassNameV4PA4sw(int i) {
        Role.Companion companion = Role.Companion;
        companion.getClass();
        if (i == 0) {
            return "android.widget.Button";
        }
        companion.getClass();
        if (i == Role.Checkbox) {
            return "android.widget.CheckBox";
        }
        companion.getClass();
        if (i == Role.RadioButton) {
            return "android.widget.RadioButton";
        }
        companion.getClass();
        if (i == Role.Image) {
            return "android.widget.ImageView";
        }
        companion.getClass();
        if (i == Role.DropdownList) {
            return "android.widget.Spinner";
        }
        companion.getClass();
        if (i == Role.ValuePicker) {
            return "android.widget.NumberPicker";
        }
        return null;
    }
}
