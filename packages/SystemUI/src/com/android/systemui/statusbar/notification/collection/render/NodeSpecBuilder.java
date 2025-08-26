package com.android.systemui.statusbar.notification.collection.render;

import android.os.Trace;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.statusbar.notification.NotificationSectionsFeatureManager;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import com.android.systemui.statusbar.notification.collection.provider.SectionHeaderVisibilityProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.collections.EmptySet;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class NodeSpecBuilder {
    public final EmptySet lastSections = EmptySet.INSTANCE;
    public final SectionHeaderVisibilityProvider sectionHeaderVisibilityProvider;
    public final NotificationSectionsFeatureManager sectionsFeatureManager;
    public final NotifViewBarn viewBarn;

    public NodeSpecBuilder(MediaContainerController mediaContainerController, NotificationSectionsFeatureManager notificationSectionsFeatureManager, SectionHeaderVisibilityProvider sectionHeaderVisibilityProvider, NotifViewBarn notifViewBarn, NodeSpecBuilderLogger nodeSpecBuilderLogger) {
        this.sectionsFeatureManager = notificationSectionsFeatureManager;
        this.sectionHeaderVisibilityProvider = sectionHeaderVisibilityProvider;
        this.viewBarn = notifViewBarn;
    }

    public final NodeSpecImpl buildNodeSpec(NodeController nodeController, List list) {
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("NodeSpecBuilder.buildNodeSpec");
        }
        try {
            NodeSpecImpl nodeSpecImpl = new NodeSpecImpl(null, nodeController);
            this.sectionsFeatureManager.getClass();
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            boolean z = this.sectionHeaderVisibilityProvider.sectionHeadersVisible;
            new ArrayList();
            new LinkedHashMap();
            new LinkedHashMap();
            Iterator it = list.iterator();
            NotifSection notifSection = null;
            while (it.hasNext()) {
                PipelineEntry pipelineEntry = (PipelineEntry) it.next();
                NotifSection notifSection2 = pipelineEntry.mAttachState.section;
                notifSection2.getClass();
                NodeController nodeController2 = notifSection2.headerController;
                if (linkedHashSet.contains(notifSection2)) {
                    throw new RuntimeException("Section " + notifSection2.label + " has been duplicated");
                }
                if (!notifSection2.equals(notifSection)) {
                    if (!Intrinsics.areEqual(nodeController2, notifSection != null ? notifSection.headerController : null) && z && nodeController2 != null) {
                        ((ArrayList) nodeSpecImpl.children).add(new NodeSpecImpl(nodeSpecImpl, nodeController2));
                    }
                    linkedHashSet.add(notifSection);
                    notifSection = notifSection2;
                }
                ((ArrayList) nodeSpecImpl.children).add(buildNotifNode(nodeSpecImpl, pipelineEntry));
            }
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            return nodeSpecImpl;
        } catch (Throwable th) {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    public final NodeSpecImpl buildNotifNode(NodeSpecImpl nodeSpecImpl, PipelineEntry pipelineEntry) {
        boolean z = pipelineEntry instanceof NotificationEntry;
        NotifViewBarn notifViewBarn = this.viewBarn;
        if (z) {
            return new NodeSpecImpl(nodeSpecImpl, notifViewBarn.requireNodeController(pipelineEntry));
        }
        if (!(pipelineEntry instanceof GroupEntry)) {
            throw new RuntimeException("Unexpected entry: " + pipelineEntry);
        }
        GroupEntry groupEntry = (GroupEntry) pipelineEntry;
        NotificationEntry notificationEntry = groupEntry.mSummary;
        if (notificationEntry == null) {
            throw new IllegalStateException("Required value was null.");
        }
        NodeSpecImpl nodeSpecImpl2 = new NodeSpecImpl(nodeSpecImpl, notifViewBarn.requireNodeController(notificationEntry));
        for (NotificationEntry notificationEntry2 : groupEntry.mUnmodifiableChildren) {
            List list = nodeSpecImpl2.children;
            notificationEntry2.getClass();
            ((ArrayList) list).add(buildNotifNode(nodeSpecImpl2, notificationEntry2));
        }
        return nodeSpecImpl2;
    }
}
