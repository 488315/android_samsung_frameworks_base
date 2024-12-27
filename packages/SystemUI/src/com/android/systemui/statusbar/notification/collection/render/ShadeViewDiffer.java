package com.android.systemui.statusbar.notification.collection.render;

import android.os.Trace;
import android.view.View;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceUtilsKt;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
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
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public static final void detachChildren$lambda$4$detachRecursively(Map map, ShadeViewDiffer shadeViewDiffer, ShadeNode shadeNode, Map map2) {
        LinkedHashMap linkedHashMap;
        boolean z;
        int i;
        NodeSpec nodeSpec;
        ShadeNode shadeNode2 = shadeNode;
        int i2 = -1;
        boolean z2 = true;
        LinkedHashMap linkedHashMap2 = (LinkedHashMap) map2;
        NodeSpec nodeSpec2 = (NodeSpec) linkedHashMap2.get(shadeNode2.controller);
        NodeController nodeController = shadeNode2.controller;
        int childCount = nodeController.getChildCount() - 1;
        while (i2 < childCount) {
            ShadeNode shadeNode3 = (ShadeNode) ((LinkedHashMap) map).get(nodeController.getChildAt(childCount));
            if (shadeNode3 != null) {
                NodeController nodeController2 = shadeNode3.controller;
                NodeSpec nodeSpec3 = (NodeSpec) linkedHashMap2.get(nodeController2);
                shadeViewDiffer.getClass();
                ShadeNode node = (nodeSpec3 == null || (nodeSpec = ((NodeSpecImpl) nodeSpec3).parent) == null) ? null : shadeViewDiffer.getNode(nodeSpec);
                if (Intrinsics.areEqual(node, shadeNode2)) {
                    linkedHashMap = linkedHashMap2;
                    z = z2;
                } else {
                    boolean z3 = node == null ? z2 : false;
                    if (z3) {
                        shadeViewDiffer.nodes.remove(nodeController2);
                    }
                    if (z3 && nodeSpec2 == null && nodeController2.offerToKeepInParentForAnimation()) {
                        String nodeLabel = nodeController2.getNodeLabel();
                        String nodeLabel2 = nodeController.getNodeLabel();
                        boolean z4 = z3 ^ z2;
                        ShadeViewDifferLogger shadeViewDifferLogger = shadeViewDiffer.logger;
                        shadeViewDifferLogger.getClass();
                        LogLevel logLevel = LogLevel.DEBUG;
                        ShadeViewDifferLogger$logSkipDetachingChild$2 shadeViewDifferLogger$logSkipDetachingChild$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.render.ShadeViewDifferLogger$logSkipDetachingChild$2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                LogMessage logMessage = (LogMessage) obj;
                                String str1 = logMessage.getStr1();
                                String str2 = logMessage.getStr2();
                                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Skip detaching ", str1, " from ", str2, " isTransfer="), logMessage.getBool1(), " isParentRemoved=", logMessage.getBool2());
                            }
                        };
                        LogBuffer logBuffer = shadeViewDifferLogger.buffer;
                        linkedHashMap = linkedHashMap2;
                        LogMessage obtain = logBuffer.obtain("NotifViewManager", logLevel, shadeViewDifferLogger$logSkipDetachingChild$2, null);
                        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                        logMessageImpl.str1 = nodeLabel;
                        logMessageImpl.str2 = nodeLabel2;
                        logMessageImpl.bool1 = z4;
                        z = true;
                        logMessageImpl.bool2 = true;
                        logBuffer.commit(obtain);
                    } else {
                        linkedHashMap = linkedHashMap2;
                        z = z2;
                        boolean z5 = !z3;
                        shadeViewDiffer.logger.logDetachingChild(nodeController2.getNodeLabel(), nodeController.getNodeLabel(), node != null ? node.controller.getNodeLabel() : null, z5, nodeSpec2 == null ? z : false);
                        boolean isEnabled = Trace.isEnabled();
                        if (isEnabled) {
                            TraceUtilsKt.beginSlice("ShadeNode#removeChild");
                        }
                        try {
                            nodeController.removeChild(nodeController2, z5);
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
                i = -1;
            } else {
                linkedHashMap = linkedHashMap2;
                z = z2;
                i = i2;
            }
            childCount += i;
            i2 = i;
            z2 = z;
            linkedHashMap2 = linkedHashMap;
            shadeNode2 = shadeNode;
        }
    }

    public static void registerNodes(NodeSpec nodeSpec, Map map) {
        NodeSpecImpl nodeSpecImpl = (NodeSpecImpl) nodeSpec;
        if (map.containsKey(nodeSpecImpl.controller)) {
            throw new DuplicateNodeException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Node ", nodeSpecImpl.controller.getNodeLabel(), " appears more than once"));
        }
        map.put(nodeSpecImpl.controller, nodeSpec);
        if (!((ArrayList) nodeSpecImpl.children).isEmpty()) {
            Iterator it = ((ArrayList) nodeSpecImpl.children).iterator();
            while (it.hasNext()) {
                registerNodes((NodeSpec) it.next(), map);
            }
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
        Iterator it;
        int i;
        boolean isEnabled;
        boolean isEnabled2 = Trace.isEnabled();
        if (isEnabled2) {
            TraceUtilsKt.beginSlice("attachChildren");
        }
        try {
            NodeController nodeController = shadeNode.controller;
            Object obj = ((LinkedHashMap) map).get(nodeController);
            if (obj == null) {
                throw new IllegalStateException("Required value was null.".toString());
            }
            Iterator it2 = ((ArrayList) ((NodeSpecImpl) ((NodeSpec) obj)).children).iterator();
            int i2 = 0;
            while (it2.hasNext()) {
                int i3 = i2 + 1;
                NodeSpec nodeSpec = (NodeSpec) it2.next();
                View childAt = nodeController.getChildAt(i2);
                ShadeNode node = getNode(nodeSpec);
                NodeController nodeController2 = node.controller;
                if (Intrinsics.areEqual(nodeController2.getView(), childAt)) {
                    it = it2;
                    i = i3;
                } else {
                    if (nodeController2.removeFromParentIfKeptForAnimation()) {
                        this.logger.logDetachingChild(nodeController2.getNodeLabel(), null, null, false, true);
                    }
                    ShadeNode shadeNode2 = node.parent;
                    ShadeViewDifferLogger shadeViewDifferLogger = this.logger;
                    if (shadeNode2 == null) {
                        String nodeLabel = nodeController2.getNodeLabel();
                        String nodeLabel2 = nodeController.getNodeLabel();
                        shadeViewDifferLogger.getClass();
                        LogLevel logLevel = LogLevel.DEBUG;
                        it = it2;
                        ShadeViewDifferLogger$logAttachingChild$2 shadeViewDifferLogger$logAttachingChild$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.render.ShadeViewDifferLogger$logAttachingChild$2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj2) {
                                LogMessage logMessage = (LogMessage) obj2;
                                String str1 = logMessage.getStr1();
                                String str2 = logMessage.getStr2();
                                int int1 = logMessage.getInt1();
                                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Attaching view ", str1, " to ", str2, " at index ");
                                m.append(int1);
                                return m.toString();
                            }
                        };
                        LogBuffer logBuffer = shadeViewDifferLogger.buffer;
                        i = i3;
                        LogMessage obtain = logBuffer.obtain("NotifViewManager", logLevel, shadeViewDifferLogger$logAttachingChild$2, null);
                        ((LogMessageImpl) obtain).str1 = nodeLabel;
                        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                        logMessageImpl.str2 = nodeLabel2;
                        logMessageImpl.int1 = i2;
                        logBuffer.commit(obtain);
                        isEnabled = Trace.isEnabled();
                        if (isEnabled) {
                            TraceUtilsKt.beginSlice("ShadeNode#addChildAt");
                        }
                        try {
                            nodeController.addChildAt(nodeController2, i2);
                            nodeController2.onViewAdded();
                            Unit unit = Unit.INSTANCE;
                            if (isEnabled) {
                                TraceUtilsKt.endSlice();
                            }
                            node.parent = shadeNode;
                        } finally {
                        }
                    } else {
                        it = it2;
                        i = i3;
                        if (!shadeNode2.equals(shadeNode)) {
                            String nodeLabel3 = nodeController2.getNodeLabel();
                            String nodeLabel4 = nodeController.getNodeLabel();
                            ShadeNode shadeNode3 = node.parent;
                            throw new IllegalStateException("Child " + nodeLabel3 + " should have parent " + nodeLabel4 + " but is actually " + (shadeNode3 != null ? shadeNode3.controller.getNodeLabel() : null));
                        }
                        String nodeLabel5 = nodeController2.getNodeLabel();
                        String nodeLabel6 = nodeController.getNodeLabel();
                        shadeViewDifferLogger.getClass();
                        LogLevel logLevel2 = LogLevel.DEBUG;
                        ShadeViewDifferLogger$logMovingChild$2 shadeViewDifferLogger$logMovingChild$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.render.ShadeViewDifferLogger$logMovingChild$2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj2) {
                                LogMessage logMessage = (LogMessage) obj2;
                                String str1 = logMessage.getStr1();
                                String str2 = logMessage.getStr2();
                                int int1 = logMessage.getInt1();
                                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Moving child view ", str1, " in ", str2, " to index ");
                                m.append(int1);
                                return m.toString();
                            }
                        };
                        LogBuffer logBuffer2 = shadeViewDifferLogger.buffer;
                        LogMessage obtain2 = logBuffer2.obtain("NotifViewManager", logLevel2, shadeViewDifferLogger$logMovingChild$2, null);
                        ((LogMessageImpl) obtain2).str1 = nodeLabel5;
                        LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain2;
                        logMessageImpl2.str2 = nodeLabel6;
                        logMessageImpl2.int1 = i2;
                        logBuffer2.commit(obtain2);
                        isEnabled = Trace.isEnabled();
                        if (isEnabled) {
                            TraceUtilsKt.beginSlice("ShadeNode#moveChildTo");
                        }
                        try {
                            nodeController.moveChildTo(nodeController2, i2);
                            Unit unit2 = Unit.INSTANCE;
                            if (isEnabled) {
                                TraceUtilsKt.endSlice();
                            }
                        } finally {
                        }
                    }
                }
                nodeController2.resetKeepInParentForAnimation();
                if (!((ArrayList) ((NodeSpecImpl) nodeSpec).children).isEmpty()) {
                    attachChildren(node, map);
                }
                it2 = it;
                i2 = i;
            }
            Unit unit3 = Unit.INSTANCE;
            if (isEnabled2) {
                TraceUtilsKt.endSlice();
            }
        } catch (Throwable th) {
            if (isEnabled2) {
                TraceUtilsKt.endSlice();
            }
            throw th;
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
            ShadeViewDifferLogger$logDuplicateNodeInTree$2 shadeViewDifferLogger$logDuplicateNodeInTree$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.render.ShadeViewDifferLogger$logDuplicateNodeInTree$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(logMessage.getStr1(), " when mapping tree: ", logMessage.getStr2());
                }
            };
            LogBuffer logBuffer = shadeViewDifferLogger.buffer;
            LogMessage obtain = logBuffer.obtain("NotifViewManager", logLevel, shadeViewDifferLogger$logDuplicateNodeInTree$2, null);
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
