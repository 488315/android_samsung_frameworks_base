package com.android.systemui.statusbar.notification.collection.render;

import android.os.Trace;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.statusbar.notification.NotificationSectionsFeatureManager;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import com.android.systemui.statusbar.notification.collection.provider.SectionHeaderVisibilityProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.collections.EmptySet;
import kotlin.jvm.internal.Intrinsics;

public final class NodeSpecBuilder {
    public final SectionHeaderVisibilityProvider sectionHeaderVisibilityProvider;
    public final NotificationSectionsFeatureManager sectionsFeatureManager;
    public final NotifViewBarn viewBarn;

    public NodeSpecBuilder(MediaContainerController mediaContainerController, NotificationSectionsFeatureManager notificationSectionsFeatureManager, SectionHeaderVisibilityProvider sectionHeaderVisibilityProvider, NotifViewBarn notifViewBarn, NodeSpecBuilderLogger nodeSpecBuilderLogger) {
        this.sectionsFeatureManager = notificationSectionsFeatureManager;
        this.sectionHeaderVisibilityProvider = sectionHeaderVisibilityProvider;
        this.viewBarn = notifViewBarn;
        EmptySet emptySet = EmptySet.INSTANCE;
    }

    public final NodeSpecImpl buildNodeSpec(NodeController nodeController, List list) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
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
                ListEntry listEntry = (ListEntry) it.next();
                NotifSection section = listEntry.getSection();
                Intrinsics.checkNotNull(section);
                NodeController nodeController2 = section.headerController;
                if (linkedHashSet.contains(section)) {
                    throw new RuntimeException("Section " + section.label + " has been duplicated");
                }
                if (!section.equals(notifSection)) {
                    if (!Intrinsics.areEqual(nodeController2, notifSection != null ? notifSection.headerController : null) && z && nodeController2 != null) {
                        ((ArrayList) nodeSpecImpl.children).add(new NodeSpecImpl(nodeSpecImpl, nodeController2));
                    }
                    linkedHashSet.add(notifSection);
                    notifSection = section;
                }
                ((ArrayList) nodeSpecImpl.children).add(buildNotifNode(nodeSpecImpl, listEntry));
            }
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            return nodeSpecImpl;
        } catch (Throwable th) {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    public final NodeSpecImpl buildNotifNode(NodeSpecImpl nodeSpecImpl, ListEntry listEntry) {
        boolean z = listEntry instanceof NotificationEntry;
        NotifViewBarn notifViewBarn = this.viewBarn;
        if (z) {
            return new NodeSpecImpl(nodeSpecImpl, notifViewBarn.requireNodeController(listEntry));
        }
        if (!(listEntry instanceof GroupEntry)) {
            throw new RuntimeException("Unexpected entry: " + listEntry);
        }
        GroupEntry groupEntry = (GroupEntry) listEntry;
        NotificationEntry notificationEntry = groupEntry.mSummary;
        if (notificationEntry == null) {
            throw new IllegalStateException("Required value was null.".toString());
        }
        NodeSpecImpl nodeSpecImpl2 = new NodeSpecImpl(nodeSpecImpl, notifViewBarn.requireNodeController(notificationEntry));
        for (NotificationEntry notificationEntry2 : groupEntry.mUnmodifiableChildren) {
            List list = nodeSpecImpl2.children;
            Intrinsics.checkNotNull(notificationEntry2);
            ((ArrayList) list).add(buildNotifNode(nodeSpecImpl2, notificationEntry2));
        }
        return nodeSpecImpl2;
    }
}
