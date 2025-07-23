package com.android.systemui.statusbar.notification.collection.render;

import android.os.Trace;
import android.view.View;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeViewDiffer {
    public final ShadeViewDifferLogger logger;
    public final Map nodes;
    public final ShadeNode rootNode;

    public ShadeViewDiffer(NodeController nodeController, ShadeViewDifferLogger shadeViewDifferLogger) {
        this.logger = shadeViewDifferLogger;
        ShadeNode shadeNode = new ShadeNode(nodeController);
        this.rootNode = shadeNode;
        this.nodes = MapsKt__MapsKt.mutableMapOf(new Pair(nodeController, shadeNode));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void detachChildren$lambda$4$detachRecursively(Map map, ShadeViewDiffer shadeViewDiffer, ShadeNode shadeNode, Map map2) {
        int i;
        int i2;
        NodeSpec nodeSpec;
        NodeSpec nodeSpec2;
        ShadeNode shadeNode2 = shadeNode;
        int i3 = 0;
        int i4 = -1;
        int i5 = 1;
        LinkedHashMap linkedHashMap = (LinkedHashMap) map2;
        NodeSpec nodeSpec3 = (NodeSpec) linkedHashMap.get(shadeNode2.controller);
        NodeController nodeController = shadeNode2.controller;
        int childCount = nodeController.getChildCount() - 1;
        while (i4 < childCount) {
            ShadeNode shadeNode3 = (ShadeNode) ((LinkedHashMap) map).get(nodeController.getChildAt(childCount));
            if (shadeNode3 != null) {
                NodeController nodeController2 = shadeNode3.controller;
                NodeSpec nodeSpec4 = (NodeSpec) linkedHashMap.get(nodeController2);
                shadeViewDiffer.getClass();
                ShadeNode node = (nodeSpec4 == null || (nodeSpec2 = ((NodeSpecImpl) nodeSpec4).parent) == null) ? null : shadeViewDiffer.getNode(nodeSpec2);
                if (Intrinsics.areEqual(node, shadeNode2)) {
                    i = i4;
                    i2 = i5;
                    nodeSpec = nodeSpec3;
                } else {
                    int i6 = node == null ? i5 : i3;
                    i = i4;
                    if (i6 != 0) {
                        shadeViewDiffer.nodes.remove(nodeController2);
                    }
                    if (i6 != 0 && nodeSpec3 == null && nodeController2.offerToKeepInParentForAnimation()) {
                        String nodeLabel = nodeController2.getNodeLabel();
                        String nodeLabel2 = nodeController.getNodeLabel();
                        boolean z = i6 ^ i5;
                        ShadeViewDifferLogger shadeViewDifferLogger = shadeViewDiffer.logger;
                        shadeViewDifferLogger.getClass();
                        LogLevel logLevel = LogLevel.DEBUG;
                        ShadeViewDifferLogger$$ExternalSyntheticLambda0 shadeViewDifferLogger$$ExternalSyntheticLambda0 = new ShadeViewDifferLogger$$ExternalSyntheticLambda0(i3);
                        LogBuffer logBuffer = shadeViewDifferLogger.buffer;
                        nodeSpec = nodeSpec3;
                        LogMessage obtain = logBuffer.obtain("NotifViewManager", logLevel, shadeViewDifferLogger$$ExternalSyntheticLambda0, null);
                        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                        logMessageImpl.str1 = nodeLabel;
                        logMessageImpl.str2 = nodeLabel2;
                        logMessageImpl.bool1 = z;
                        i2 = 1;
                        logMessageImpl.bool2 = true;
                        logBuffer.commit(obtain);
                    } else {
                        i2 = i5;
                        nodeSpec = nodeSpec3;
                        boolean z2 = i6 ^ 1;
                        shadeViewDiffer.logger.logDetachingChild(nodeController2.getNodeLabel(), nodeController.getNodeLabel(), node != null ? node.controller.getNodeLabel() : null, z2, nodeSpec == null ? i2 : 0);
                        boolean isEnabled = Trace.isEnabled();
                        if (isEnabled) {
                            TraceUtilsKt.beginSlice("ShadeNode#" + Reflection.getOrCreateKotlinClass(nodeController.getClass()).getSimpleName() + "#removeChild");
                        }
                        try {
                            nodeController.removeChild(nodeController2, z2);
                            nodeController2.onViewRemoved();
                            Unit unit = Unit.INSTANCE;
                            shadeNode3.parent = null;
                        } finally {
                            if (isEnabled) {
                                TraceUtilsKt.endSlice();
                            }
                        }
                    }
                }
                if (nodeController2.getChildCount() > 0) {
                    detachChildren$lambda$4$detachRecursively(map, shadeViewDiffer, shadeNode3, map2);
                }
            } else {
                i = i4;
                i2 = i5;
                nodeSpec = nodeSpec3;
            }
            childCount--;
            shadeNode2 = shadeNode;
            i5 = i2;
            i4 = i;
            nodeSpec3 = nodeSpec;
            i3 = 0;
        }
    }

    public static void registerNodes(NodeSpec nodeSpec, Map map) {
        NodeSpecImpl nodeSpecImpl = (NodeSpecImpl) nodeSpec;
        if (map.containsKey(nodeSpecImpl.controller)) {
            throw new DuplicateNodeException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Node ", nodeSpecImpl.controller.getNodeLabel(), " appears more than once"));
        }
        map.put(nodeSpecImpl.controller, nodeSpec);
        if (((ArrayList) nodeSpecImpl.children).isEmpty()) {
            return;
        }
        ArrayList arrayList = (ArrayList) nodeSpecImpl.children;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            registerNodes((NodeSpec) obj, map);
        }
    }

    public final void applySpec(NodeSpecImpl nodeSpecImpl) {
        NodeController nodeController = nodeSpecImpl.controller;
        ShadeNode shadeNode = this.rootNode;
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("ShadeViewDiffer.applySpec");
        }
        try {
            Map treeToMap = treeToMap(nodeSpecImpl);
            if (Intrinsics.areEqual(nodeController, shadeNode.controller)) {
                detachChildren(shadeNode, treeToMap);
                attachChildren(shadeNode, treeToMap);
                Unit unit = Unit.INSTANCE;
                if (isEnabled) {
                    return;
                } else {
                    return;
                }
            }
            throw new IllegalArgumentException("Tree root " + nodeController.getNodeLabel() + " does not match own root at " + shadeNode.controller.getNodeLabel());
        } finally {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    public final void attachChildren(ShadeNode shadeNode, Map map) {
        boolean z;
        boolean z2;
        NodeController nodeController;
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("attachChildren");
        }
        try {
            NodeController nodeController2 = shadeNode.controller;
            Object obj = ((LinkedHashMap) map).get(nodeController2);
            try {
                if (obj == null) {
                    throw new IllegalStateException("Required value was null.");
                }
                Iterator it = ((ArrayList) ((NodeSpecImpl) ((NodeSpec) obj)).children).iterator();
                int i = 0;
                while (it.hasNext()) {
                    int i2 = i + 1;
                    NodeSpec nodeSpec = (NodeSpec) it.next();
                    View childAt = nodeController2.getChildAt(i);
                    ShadeNode node = getNode(nodeSpec);
                    NodeController nodeController3 = node.controller;
                    if (Intrinsics.areEqual(nodeController3.getView(), childAt)) {
                        z2 = isEnabled;
                        nodeController = nodeController2;
                    } else {
                        if (nodeController3.removeFromParentIfKeptForAnimation()) {
                            this.logger.logDetachingChild(nodeController3.getNodeLabel(), null, null, false, true);
                        }
                        ShadeNode shadeNode2 = node.parent;
                        ShadeViewDifferLogger shadeViewDifferLogger = this.logger;
                        if (shadeNode2 == null) {
                            String nodeLabel = nodeController3.getNodeLabel();
                            String nodeLabel2 = nodeController2.getNodeLabel();
                            shadeViewDifferLogger.getClass();
                            LogLevel logLevel = LogLevel.DEBUG;
                            z2 = isEnabled;
                            nodeController = nodeController2;
                            ShadeViewDifferLogger$$ExternalSyntheticLambda0 shadeViewDifferLogger$$ExternalSyntheticLambda0 = new ShadeViewDifferLogger$$ExternalSyntheticLambda0(4);
                            LogBuffer logBuffer = shadeViewDifferLogger.buffer;
                            LogMessage obtain = logBuffer.obtain("NotifViewManager", logLevel, shadeViewDifferLogger$$ExternalSyntheticLambda0, null);
                            ((LogMessageImpl) obtain).str1 = nodeLabel;
                            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                            logMessageImpl.str2 = nodeLabel2;
                            logMessageImpl.int1 = i;
                            logBuffer.commit(obtain);
                            shadeNode.addChildAt(node, i);
                            node.parent = shadeNode;
                        } else {
                            z2 = isEnabled;
                            nodeController = nodeController2;
                            if (!shadeNode2.equals(shadeNode)) {
                                String nodeLabel3 = nodeController3.getNodeLabel();
                                String nodeLabel4 = nodeController.getNodeLabel();
                                ShadeNode shadeNode3 = node.parent;
                                throw new IllegalStateException("Child " + nodeLabel3 + " should have parent " + nodeLabel4 + " but is actually " + (shadeNode3 != null ? shadeNode3.controller.getNodeLabel() : null));
                            }
                            String nodeLabel5 = nodeController3.getNodeLabel();
                            String nodeLabel6 = nodeController.getNodeLabel();
                            shadeViewDifferLogger.getClass();
                            LogLevel logLevel2 = LogLevel.DEBUG;
                            ShadeViewDifferLogger$$ExternalSyntheticLambda0 shadeViewDifferLogger$$ExternalSyntheticLambda02 = new ShadeViewDifferLogger$$ExternalSyntheticLambda0(3);
                            LogBuffer logBuffer2 = shadeViewDifferLogger.buffer;
                            LogMessage obtain2 = logBuffer2.obtain("NotifViewManager", logLevel2, shadeViewDifferLogger$$ExternalSyntheticLambda02, null);
                            ((LogMessageImpl) obtain2).str1 = nodeLabel5;
                            LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain2;
                            logMessageImpl2.str2 = nodeLabel6;
                            logMessageImpl2.int1 = i;
                            logBuffer2.commit(obtain2);
                            shadeNode.moveChildTo(node, i);
                        }
                    }
                    nodeController3.resetKeepInParentForAnimation();
                    if (!((ArrayList) ((NodeSpecImpl) nodeSpec).children).isEmpty()) {
                        attachChildren(node, map);
                    }
                    i = i2;
                    isEnabled = z2;
                    nodeController2 = nodeController;
                }
                boolean z3 = isEnabled;
                Unit unit = Unit.INSTANCE;
                if (z3) {
                    TraceUtilsKt.endSlice();
                }
            } catch (Throwable th) {
                th = th;
                if (z) {
                    TraceUtilsKt.endSlice();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            z = isEnabled;
        }
    }

    public final void detachChildren(ShadeNode shadeNode, Map map) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("detachChildren");
        }
        try {
            Collection values = ((LinkedHashMap) this.nodes).values();
            int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(values, 10));
            if (mapCapacity < 16) {
                mapCapacity = 16;
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity);
            for (Object obj : values) {
                linkedHashMap.put(((ShadeNode) obj).controller.getView(), obj);
            }
            detachChildren$lambda$4$detachRecursively(linkedHashMap, this, shadeNode, map);
            Unit unit = Unit.INSTANCE;
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        } catch (Throwable th) {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    public final ShadeNode getNode(NodeSpec nodeSpec) {
        ShadeNode shadeNode = (ShadeNode) ((LinkedHashMap) this.nodes).get(((NodeSpecImpl) nodeSpec).controller);
        if (shadeNode != null) {
            return shadeNode;
        }
        ShadeNode shadeNode2 = new ShadeNode(((NodeSpecImpl) nodeSpec).controller);
        this.nodes.put(shadeNode2.controller, shadeNode2);
        return shadeNode2;
    }

    public final Map treeToMap(NodeSpecImpl nodeSpecImpl) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        try {
            registerNodes(nodeSpecImpl, linkedHashMap);
            return linkedHashMap;
        } catch (DuplicateNodeException e) {
            ShadeViewDifferLogger shadeViewDifferLogger = this.logger;
            shadeViewDifferLogger.getClass();
            LogLevel logLevel = LogLevel.ERROR;
            ShadeViewDifferLogger$$ExternalSyntheticLambda0 shadeViewDifferLogger$$ExternalSyntheticLambda0 = new ShadeViewDifferLogger$$ExternalSyntheticLambda0(2);
            LogBuffer logBuffer = shadeViewDifferLogger.buffer;
            LogMessage obtain = logBuffer.obtain("NotifViewManager", logLevel, shadeViewDifferLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = e.toString();
            StringBuilder sb = new StringBuilder();
            NodeControllerKt.treeSpecToStrHelper(nodeSpecImpl, sb, "");
            logMessageImpl.str2 = sb.toString();
            logBuffer.commit(obtain);
            throw e;
        }
    }
}
