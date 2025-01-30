package com.android.systemui.statusbar.notification.collection.render;

import android.os.Trace;
import android.view.View;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ShadeViewDiffer {
    public final ShadeViewDifferLogger logger;
    public final Map nodes;
    public final ShadeNode rootNode;

    public ShadeViewDiffer(NodeController nodeController, ShadeViewDifferLogger shadeViewDifferLogger) {
        this.logger = shadeViewDifferLogger;
        ShadeNode shadeNode = new ShadeNode(nodeController);
        this.rootNode = shadeNode;
        Pair[] pairArr = {new Pair(nodeController, shadeNode)};
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(1));
        MapsKt__MapsKt.putAll(linkedHashMap, pairArr);
        this.nodes = linkedHashMap;
    }

    public static final void detachChildren$lambda$4$detachRecursively(Map map, ShadeViewDiffer shadeViewDiffer, ShadeNode shadeNode, Map map2) {
        LinkedHashMap linkedHashMap;
        boolean z;
        NodeSpec nodeSpec;
        ShadeNode shadeNode2 = shadeNode;
        LinkedHashMap linkedHashMap2 = (LinkedHashMap) map2;
        NodeSpec nodeSpec2 = (NodeSpec) linkedHashMap2.get(shadeNode2.controller);
        NodeController nodeController = shadeNode2.controller;
        boolean z2 = true;
        int childCount = nodeController.getChildCount() - 1;
        while (-1 < childCount) {
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
                        String label = shadeNode3.getLabel();
                        String label2 = shadeNode.getLabel();
                        ShadeViewDifferLogger shadeViewDifferLogger = shadeViewDiffer.logger;
                        shadeViewDifferLogger.getClass();
                        LogLevel logLevel = LogLevel.DEBUG;
                        ShadeViewDifferLogger$logSkipDetachingChild$2 shadeViewDifferLogger$logSkipDetachingChild$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.render.ShadeViewDifferLogger$logSkipDetachingChild$2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                LogMessage logMessage = (LogMessage) obj;
                                String str1 = logMessage.getStr1();
                                String str2 = logMessage.getStr2();
                                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m74m(AbstractC0866xb1ce8deb.m87m("Skip detaching ", str1, " from ", str2, " isTransfer="), logMessage.getBool1(), " isParentRemoved=", logMessage.getBool2());
                            }
                        };
                        LogBuffer logBuffer = shadeViewDifferLogger.buffer;
                        linkedHashMap = linkedHashMap2;
                        LogMessage obtain = logBuffer.obtain("NotifViewManager", logLevel, shadeViewDifferLogger$logSkipDetachingChild$2, null);
                        obtain.setStr1(label);
                        obtain.setStr2(label2);
                        obtain.setBool1(!z3);
                        z = true;
                        obtain.setBool2(true);
                        logBuffer.commit(obtain);
                    } else {
                        linkedHashMap = linkedHashMap2;
                        z = z2;
                        boolean z4 = !z3;
                        shadeViewDiffer.logger.logDetachingChild(shadeNode3.getLabel(), shadeNode.getLabel(), node != null ? node.getLabel() : null, z4, nodeSpec2 == null ? z : false);
                        nodeController.removeChild(nodeController2, z4);
                        nodeController2.onViewRemoved();
                        shadeNode3.parent = null;
                    }
                }
                if (nodeController2.getChildCount() > 0) {
                    detachChildren$lambda$4$detachRecursively(map, shadeViewDiffer, shadeNode3, map2);
                }
            } else {
                linkedHashMap = linkedHashMap2;
                z = z2;
            }
            childCount--;
            shadeNode2 = shadeNode;
            z2 = z;
            linkedHashMap2 = linkedHashMap;
        }
    }

    public static void registerNodes(NodeSpec nodeSpec, Map map) {
        NodeSpecImpl nodeSpecImpl = (NodeSpecImpl) nodeSpec;
        if (map.containsKey(nodeSpecImpl.controller)) {
            throw new DuplicateNodeException(PathParser$$ExternalSyntheticOutline0.m29m("Node ", nodeSpecImpl.controller.getNodeLabel(), " appears more than once"));
        }
        map.put(nodeSpecImpl.controller, nodeSpec);
        ArrayList arrayList = (ArrayList) nodeSpecImpl.children;
        if (!arrayList.isEmpty()) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                registerNodes((NodeSpec) it.next(), map);
            }
        }
    }

    public final void applySpec(NodeSpecImpl nodeSpecImpl) {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        NodeController nodeController = nodeSpecImpl.controller;
        ShadeNode shadeNode = this.rootNode;
        if (!isTagEnabled) {
            Map treeToMap = treeToMap(nodeSpecImpl);
            if (!Intrinsics.areEqual(nodeController, shadeNode.controller)) {
                throw new IllegalArgumentException(FontProvider$$ExternalSyntheticOutline0.m32m("Tree root ", nodeController.getNodeLabel(), " does not match own root at ", shadeNode.getLabel()));
            }
            detachChildren(shadeNode, treeToMap);
            attachChildren(shadeNode, treeToMap);
            return;
        }
        Trace.traceBegin(4096L, "ShadeViewDiffer.applySpec");
        try {
            Map treeToMap2 = treeToMap(nodeSpecImpl);
            if (Intrinsics.areEqual(nodeController, shadeNode.controller)) {
                detachChildren(shadeNode, treeToMap2);
                attachChildren(shadeNode, treeToMap2);
                Unit unit = Unit.INSTANCE;
            } else {
                throw new IllegalArgumentException("Tree root " + nodeController.getNodeLabel() + " does not match own root at " + shadeNode.getLabel());
            }
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    public final void attachChildren(ShadeNode shadeNode, Map map) {
        int i;
        NodeSpec nodeSpec;
        long j;
        NodeController nodeController;
        NodeController nodeController2;
        int i2;
        NodeSpec nodeSpec2;
        ShadeViewDiffer shadeViewDiffer = this;
        long j2 = 4096;
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        int i3 = 0;
        ShadeViewDifferLogger shadeViewDifferLogger = shadeViewDiffer.logger;
        if (!isTagEnabled) {
            Object obj = ((LinkedHashMap) map).get(shadeNode.controller);
            if (obj == null) {
                throw new IllegalStateException("Required value was null.".toString());
            }
            Iterator it = ((ArrayList) ((NodeSpecImpl) ((NodeSpec) obj)).children).iterator();
            while (it.hasNext()) {
                int i4 = i3 + 1;
                NodeSpec nodeSpec3 = (NodeSpec) it.next();
                NodeController nodeController3 = shadeNode.controller;
                View childAt = nodeController3.getChildAt(i3);
                ShadeNode node = shadeViewDiffer.getNode(nodeSpec3);
                NodeController nodeController4 = node.controller;
                Iterator it2 = it;
                if (Intrinsics.areEqual(nodeController4.getView(), childAt)) {
                    i = i4;
                    nodeSpec = nodeSpec3;
                } else {
                    if (nodeController4.removeFromParentIfKeptForAnimation()) {
                        shadeViewDiffer.logger.logDetachingChild(node.getLabel(), null, null, false, true);
                    }
                    ShadeNode shadeNode2 = node.parent;
                    if (shadeNode2 == null) {
                        String label = node.getLabel();
                        String label2 = shadeNode.getLabel();
                        shadeViewDifferLogger.getClass();
                        i = i4;
                        LogLevel logLevel = LogLevel.DEBUG;
                        ShadeViewDifferLogger$logAttachingChild$2 shadeViewDifferLogger$logAttachingChild$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.render.ShadeViewDifferLogger$logAttachingChild$2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj2) {
                                LogMessage logMessage = (LogMessage) obj2;
                                String str1 = logMessage.getStr1();
                                String str2 = logMessage.getStr2();
                                int int1 = logMessage.getInt1();
                                StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("Attaching view ", str1, " to ", str2, " at index ");
                                m87m.append(int1);
                                return m87m.toString();
                            }
                        };
                        LogBuffer logBuffer = shadeViewDifferLogger.buffer;
                        nodeSpec = nodeSpec3;
                        LogMessage obtain = logBuffer.obtain("NotifViewManager", logLevel, shadeViewDifferLogger$logAttachingChild$2, null);
                        obtain.setStr1(label);
                        obtain.setStr2(label2);
                        obtain.setInt1(i3);
                        logBuffer.commit(obtain);
                        nodeController3.addChildAt(nodeController4, i3);
                        nodeController4.onViewAdded();
                        node.parent = shadeNode;
                    } else {
                        i = i4;
                        nodeSpec = nodeSpec3;
                        if (!Intrinsics.areEqual(shadeNode2, shadeNode)) {
                            String label3 = node.getLabel();
                            String label4 = shadeNode.getLabel();
                            ShadeNode shadeNode3 = node.parent;
                            String label5 = shadeNode3 != null ? shadeNode3.getLabel() : null;
                            StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("Child ", label3, " should have parent ", label4, " but is actually ");
                            m87m.append(label5);
                            throw new IllegalStateException(m87m.toString());
                        }
                        String label6 = node.getLabel();
                        String label7 = shadeNode.getLabel();
                        shadeViewDifferLogger.getClass();
                        LogLevel logLevel2 = LogLevel.DEBUG;
                        ShadeViewDifferLogger$logMovingChild$2 shadeViewDifferLogger$logMovingChild$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.render.ShadeViewDifferLogger$logMovingChild$2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj2) {
                                LogMessage logMessage = (LogMessage) obj2;
                                String str1 = logMessage.getStr1();
                                String str2 = logMessage.getStr2();
                                int int1 = logMessage.getInt1();
                                StringBuilder m87m2 = AbstractC0866xb1ce8deb.m87m("Moving child view ", str1, " in ", str2, " to index ");
                                m87m2.append(int1);
                                return m87m2.toString();
                            }
                        };
                        LogBuffer logBuffer2 = shadeViewDifferLogger.buffer;
                        LogMessage obtain2 = logBuffer2.obtain("NotifViewManager", logLevel2, shadeViewDifferLogger$logMovingChild$2, null);
                        obtain2.setStr1(label6);
                        obtain2.setStr2(label7);
                        obtain2.setInt1(i3);
                        logBuffer2.commit(obtain2);
                        nodeController3.moveChildTo(nodeController4, i3);
                        nodeController4.onViewMoved();
                    }
                }
                nodeController4.resetKeepInParentForAnimation();
                if (!((ArrayList) ((NodeSpecImpl) nodeSpec).children).isEmpty()) {
                    shadeViewDiffer = this;
                    shadeViewDiffer.attachChildren(node, map);
                } else {
                    shadeViewDiffer = this;
                }
                it = it2;
                i3 = i;
            }
            return;
        }
        Trace.traceBegin(4096L, "attachChildren");
        try {
            nodeController = shadeNode.controller;
            nodeController2 = shadeNode.controller;
        } catch (Throwable th) {
            th = th;
            j = j2;
        }
        try {
            Object obj2 = ((LinkedHashMap) map).get(nodeController);
            if (obj2 == null) {
                throw new IllegalStateException("Required value was null.".toString());
            }
            Iterator it3 = ((ArrayList) ((NodeSpecImpl) ((NodeSpec) obj2)).children).iterator();
            while (it3.hasNext()) {
                int i5 = i3 + 1;
                NodeSpec nodeSpec4 = (NodeSpec) it3.next();
                View childAt2 = nodeController2.getChildAt(i3);
                ShadeNode node2 = shadeViewDiffer.getNode(nodeSpec4);
                NodeController nodeController5 = node2.controller;
                Iterator it4 = it3;
                if (Intrinsics.areEqual(nodeController5.getView(), childAt2)) {
                    i2 = i5;
                    nodeSpec2 = nodeSpec4;
                } else {
                    if (nodeController5.removeFromParentIfKeptForAnimation()) {
                        shadeViewDiffer.logger.logDetachingChild(node2.getLabel(), null, null, false, true);
                    }
                    ShadeNode shadeNode4 = node2.parent;
                    if (shadeNode4 == null) {
                        String label8 = node2.getLabel();
                        String label9 = shadeNode.getLabel();
                        shadeViewDifferLogger.getClass();
                        i2 = i5;
                        LogLevel logLevel3 = LogLevel.DEBUG;
                        ShadeViewDifferLogger$logAttachingChild$2 shadeViewDifferLogger$logAttachingChild$22 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.render.ShadeViewDifferLogger$logAttachingChild$2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj22) {
                                LogMessage logMessage = (LogMessage) obj22;
                                String str1 = logMessage.getStr1();
                                String str2 = logMessage.getStr2();
                                int int1 = logMessage.getInt1();
                                StringBuilder m87m2 = AbstractC0866xb1ce8deb.m87m("Attaching view ", str1, " to ", str2, " at index ");
                                m87m2.append(int1);
                                return m87m2.toString();
                            }
                        };
                        LogBuffer logBuffer3 = shadeViewDifferLogger.buffer;
                        nodeSpec2 = nodeSpec4;
                        LogMessage obtain3 = logBuffer3.obtain("NotifViewManager", logLevel3, shadeViewDifferLogger$logAttachingChild$22, null);
                        obtain3.setStr1(label8);
                        obtain3.setStr2(label9);
                        obtain3.setInt1(i3);
                        logBuffer3.commit(obtain3);
                        nodeController2.addChildAt(nodeController5, i3);
                        nodeController5.onViewAdded();
                        node2.parent = shadeNode;
                    } else {
                        i2 = i5;
                        nodeSpec2 = nodeSpec4;
                        if (!Intrinsics.areEqual(shadeNode4, shadeNode)) {
                            String label10 = node2.getLabel();
                            String label11 = shadeNode.getLabel();
                            ShadeNode shadeNode5 = node2.parent;
                            throw new IllegalStateException("Child " + label10 + " should have parent " + label11 + " but is actually " + (shadeNode5 != null ? shadeNode5.getLabel() : null));
                        }
                        String label12 = node2.getLabel();
                        String label13 = shadeNode.getLabel();
                        shadeViewDifferLogger.getClass();
                        LogLevel logLevel4 = LogLevel.DEBUG;
                        ShadeViewDifferLogger$logMovingChild$2 shadeViewDifferLogger$logMovingChild$22 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.render.ShadeViewDifferLogger$logMovingChild$2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj22) {
                                LogMessage logMessage = (LogMessage) obj22;
                                String str1 = logMessage.getStr1();
                                String str2 = logMessage.getStr2();
                                int int1 = logMessage.getInt1();
                                StringBuilder m87m2 = AbstractC0866xb1ce8deb.m87m("Moving child view ", str1, " in ", str2, " to index ");
                                m87m2.append(int1);
                                return m87m2.toString();
                            }
                        };
                        LogBuffer logBuffer4 = shadeViewDifferLogger.buffer;
                        LogMessage obtain4 = logBuffer4.obtain("NotifViewManager", logLevel4, shadeViewDifferLogger$logMovingChild$22, null);
                        obtain4.setStr1(label12);
                        obtain4.setStr2(label13);
                        obtain4.setInt1(i3);
                        logBuffer4.commit(obtain4);
                        nodeController2.moveChildTo(nodeController5, i3);
                        nodeController5.onViewMoved();
                    }
                }
                nodeController5.resetKeepInParentForAnimation();
                if (!((ArrayList) ((NodeSpecImpl) nodeSpec2).children).isEmpty()) {
                    shadeViewDiffer = this;
                    shadeViewDiffer.attachChildren(node2, map);
                } else {
                    shadeViewDiffer = this;
                }
                j2 = 4096;
                it3 = it4;
                i3 = i2;
            }
            Unit unit = Unit.INSTANCE;
            Trace.traceEnd(4096L);
        } catch (Throwable th2) {
            th = th2;
            j = 4096;
            Trace.traceEnd(j);
            throw th;
        }
    }

    public final void detachChildren(ShadeNode shadeNode, Map map) {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        Map map2 = this.nodes;
        if (!isTagEnabled) {
            Collection values = ((LinkedHashMap) map2).values();
            int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(values, 10));
            LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity >= 16 ? mapCapacity : 16);
            for (Object obj : values) {
                linkedHashMap.put(((ShadeNode) obj).controller.getView(), obj);
            }
            detachChildren$lambda$4$detachRecursively(linkedHashMap, this, shadeNode, map);
            return;
        }
        Trace.traceBegin(4096L, "detachChildren");
        try {
            Collection values2 = ((LinkedHashMap) map2).values();
            int mapCapacity2 = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(values2, 10));
            if (mapCapacity2 >= 16) {
                r4 = mapCapacity2;
            }
            LinkedHashMap linkedHashMap2 = new LinkedHashMap(r4);
            for (Object obj2 : values2) {
                linkedHashMap2.put(((ShadeNode) obj2).controller.getView(), obj2);
            }
            detachChildren$lambda$4$detachRecursively(linkedHashMap2, this, shadeNode, map);
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    public final ShadeNode getNode(NodeSpec nodeSpec) {
        Map map = this.nodes;
        ShadeNode shadeNode = (ShadeNode) ((LinkedHashMap) map).get(((NodeSpecImpl) nodeSpec).controller);
        if (shadeNode != null) {
            return shadeNode;
        }
        ShadeNode shadeNode2 = new ShadeNode(((NodeSpecImpl) nodeSpec).controller);
        map.put(shadeNode2.controller, shadeNode2);
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
                    return AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(logMessage.getStr1(), " when mapping tree: ", logMessage.getStr2());
                }
            };
            LogBuffer logBuffer = shadeViewDifferLogger.buffer;
            LogMessage obtain = logBuffer.obtain("NotifViewManager", logLevel, shadeViewDifferLogger$logDuplicateNodeInTree$2, null);
            obtain.setStr1(e.toString());
            StringBuilder sb = new StringBuilder();
            NodeControllerKt.treeSpecToStrHelper(nodeSpecImpl, sb, "");
            obtain.setStr2(sb.toString());
            logBuffer.commit(obtain);
            throw e;
        }
    }
}
