package androidx.compose.ui.node;

import androidx.compose.runtime.CompositionLocalMap;
import androidx.compose.runtime.CompositionLocalMapKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.runtime.internal.PersistentCompositionLocalHashMap;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.platform.ViewConfiguration;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public interface ComposeUiNode {
    public static final Companion Companion = Companion.$$INSTANCE;

    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final Function0 Constructor;
        public static final Function2 SetCompositeKeyHash;
        public static final Function2 SetMeasurePolicy;
        public static final Function2 SetModifier;
        public static final Function2 SetResolvedCompositionLocals;
        public static final Function0 VirtualConstructor;

        static {
            LayoutNode.Companion.getClass();
            Constructor = LayoutNode.Constructor;
            VirtualConstructor = new Function0() { // from class: androidx.compose.ui.node.ComposeUiNode$Companion$VirtualConstructor$1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return new LayoutNode(true, 0, 2, null);
                }
            };
            SetModifier = new Function2() { // from class: androidx.compose.ui.node.ComposeUiNode$Companion$SetModifier$1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((LayoutNode) ((ComposeUiNode) obj)).setModifier((Modifier) obj2);
                    return Unit.INSTANCE;
                }
            };
            SetResolvedCompositionLocals = new Function2() { // from class: androidx.compose.ui.node.ComposeUiNode$Companion$SetResolvedCompositionLocals$1
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r0v1 */
                /* JADX WARN: Type inference failed for: r0v10 */
                /* JADX WARN: Type inference failed for: r0v11 */
                /* JADX WARN: Type inference failed for: r0v12 */
                /* JADX WARN: Type inference failed for: r0v13 */
                /* JADX WARN: Type inference failed for: r0v14 */
                /* JADX WARN: Type inference failed for: r0v2, types: [androidx.compose.ui.Modifier$Node] */
                /* JADX WARN: Type inference failed for: r0v6 */
                /* JADX WARN: Type inference failed for: r0v7, types: [androidx.compose.ui.Modifier$Node] */
                /* JADX WARN: Type inference failed for: r0v8, types: [java.lang.Object] */
                /* JADX WARN: Type inference failed for: r0v9 */
                /* JADX WARN: Type inference failed for: r1v1 */
                /* JADX WARN: Type inference failed for: r1v10 */
                /* JADX WARN: Type inference failed for: r1v11 */
                /* JADX WARN: Type inference failed for: r1v12 */
                /* JADX WARN: Type inference failed for: r1v2 */
                /* JADX WARN: Type inference failed for: r1v3 */
                /* JADX WARN: Type inference failed for: r1v4, types: [androidx.compose.runtime.collection.MutableVector] */
                /* JADX WARN: Type inference failed for: r1v5 */
                /* JADX WARN: Type inference failed for: r1v6 */
                /* JADX WARN: Type inference failed for: r1v7, types: [androidx.compose.runtime.collection.MutableVector] */
                /* JADX WARN: Type inference failed for: r1v9 */
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    CompositionLocalMap compositionLocalMap = (CompositionLocalMap) obj2;
                    LayoutNode layoutNode = (LayoutNode) ((ComposeUiNode) obj);
                    layoutNode.compositionLocalMap = compositionLocalMap;
                    StaticProvidableCompositionLocal staticProvidableCompositionLocal = CompositionLocalsKt.LocalDensity;
                    PersistentCompositionLocalHashMap persistentCompositionLocalHashMap = (PersistentCompositionLocalHashMap) compositionLocalMap;
                    persistentCompositionLocalHashMap.getClass();
                    layoutNode.setDensity$1((Density) CompositionLocalMapKt.read(persistentCompositionLocalHashMap, staticProvidableCompositionLocal));
                    LayoutDirection layoutDirection = (LayoutDirection) CompositionLocalMapKt.read(persistentCompositionLocalHashMap, CompositionLocalsKt.LocalLayoutDirection);
                    LayoutDirection layoutDirection2 = layoutNode.layoutDirection;
                    NodeChain nodeChain = layoutNode.nodes;
                    if (layoutDirection2 != layoutDirection) {
                        layoutNode.layoutDirection = layoutDirection;
                        layoutNode.invalidateMeasurements$ui_release();
                        LayoutNode parent$ui_release = layoutNode.getParent$ui_release();
                        if (parent$ui_release != null) {
                            parent$ui_release.invalidateLayer$ui_release();
                        }
                        layoutNode.invalidateLayers$ui_release();
                        for (Modifier.Node node = nodeChain.head; node != null; node = node.child) {
                            node.onLayoutDirectionChange();
                        }
                    }
                    layoutNode.setViewConfiguration((ViewConfiguration) CompositionLocalMapKt.read(persistentCompositionLocalHashMap, CompositionLocalsKt.LocalViewConfiguration));
                    Modifier.Node node2 = nodeChain.head;
                    if ((node2.aggregateChildKindSet & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0) {
                        while (node2 != null) {
                            if ((node2.kindSet & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0) {
                                DelegatingNode delegatingNodeAccess$pop = node2;
                                ?? mutableVector = 0;
                                while (delegatingNodeAccess$pop != 0) {
                                    if (delegatingNodeAccess$pop instanceof CompositionLocalConsumerModifierNode) {
                                        Modifier.Node node3 = ((Modifier.Node) ((CompositionLocalConsumerModifierNode) delegatingNodeAccess$pop)).node;
                                        if (node3.isAttached) {
                                            NodeKindKt.autoInvalidateUpdatedNode(node3);
                                        } else {
                                            node3.updatedNodeAwaitingAttachForInvalidation = true;
                                        }
                                    } else if ((delegatingNodeAccess$pop.kindSet & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0 && (delegatingNodeAccess$pop instanceof DelegatingNode)) {
                                        Modifier.Node node4 = delegatingNodeAccess$pop.delegate;
                                        int i = 0;
                                        delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                                        mutableVector = mutableVector;
                                        while (node4 != null) {
                                            if ((node4.kindSet & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0) {
                                                i++;
                                                mutableVector = mutableVector;
                                                if (i == 1) {
                                                    delegatingNodeAccess$pop = node4;
                                                } else {
                                                    if (mutableVector == 0) {
                                                        mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                                    }
                                                    if (delegatingNodeAccess$pop != 0) {
                                                        mutableVector.add(delegatingNodeAccess$pop);
                                                        delegatingNodeAccess$pop = 0;
                                                    }
                                                    mutableVector.add(node4);
                                                }
                                            }
                                            node4 = node4.child;
                                            delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                                            mutableVector = mutableVector;
                                        }
                                        if (i == 1) {
                                        }
                                    }
                                    delegatingNodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector);
                                }
                            }
                            if ((node2.aggregateChildKindSet & NetworkAnalyticsConstants.DataPoints.FLAG_UID) == 0) {
                                break;
                            }
                            node2 = node2.child;
                        }
                    }
                    return Unit.INSTANCE;
                }
            };
            SetMeasurePolicy = new Function2() { // from class: androidx.compose.ui.node.ComposeUiNode$Companion$SetMeasurePolicy$1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((LayoutNode) ((ComposeUiNode) obj)).setMeasurePolicy((MeasurePolicy) obj2);
                    return Unit.INSTANCE;
                }
            };
            SetCompositeKeyHash = new Function2() { // from class: androidx.compose.ui.node.ComposeUiNode$Companion$SetCompositeKeyHash$1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    ((ComposeUiNode) obj).getClass();
                    return Unit.INSTANCE;
                }
            };
        }

        private Companion() {
        }
    }
}
