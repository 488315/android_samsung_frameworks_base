package com.android.systemui.statusbar.notification.collection.render;

import android.os.Trace;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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

    public final NodeSpecImpl buildNodeSpec(RootNodeController rootNodeController, List list) {
        NodeSpecImpl nodeSpecImpl;
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        SectionHeaderVisibilityProvider sectionHeaderVisibilityProvider = this.sectionHeaderVisibilityProvider;
        NotificationSectionsFeatureManager notificationSectionsFeatureManager = this.sectionsFeatureManager;
        if (isTagEnabled) {
            Trace.traceBegin(4096L, "NodeSpecBuilder.buildNodeSpec");
            try {
                nodeSpecImpl = new NodeSpecImpl(null, rootNodeController);
                List list2 = nodeSpecImpl.children;
                notificationSectionsFeatureManager.getClass();
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                boolean z = sectionHeaderVisibilityProvider.sectionHeadersVisible;
                new ArrayList();
                new LinkedHashMap();
                new LinkedHashMap();
                Iterator it = list.iterator();
                NotifSection notifSection = null;
                while (it.hasNext()) {
                    ListEntry listEntry = (ListEntry) it.next();
                    NotifSection section = listEntry.getSection();
                    Intrinsics.checkNotNull(section);
                    NodeController nodeController = section.headerController;
                    if (linkedHashSet.contains(section)) {
                        throw new RuntimeException("Section " + section.label + " has been duplicated");
                    }
                    if (!Intrinsics.areEqual(section, notifSection)) {
                        if (!Intrinsics.areEqual(nodeController, notifSection != null ? notifSection.headerController : null) && z && nodeController != null) {
                            ((ArrayList) list2).add(new NodeSpecImpl(nodeSpecImpl, nodeController));
                        }
                        linkedHashSet.add(notifSection);
                        notifSection = section;
                    }
                    ((ArrayList) list2).add(buildNotifNode(nodeSpecImpl, listEntry));
                }
            } finally {
                Trace.traceEnd(4096L);
            }
        } else {
            nodeSpecImpl = new NodeSpecImpl(null, rootNodeController);
            notificationSectionsFeatureManager.getClass();
            LinkedHashSet linkedHashSet2 = new LinkedHashSet();
            boolean z2 = sectionHeaderVisibilityProvider.sectionHeadersVisible;
            new ArrayList();
            new LinkedHashMap();
            new LinkedHashMap();
            Iterator it2 = list.iterator();
            NotifSection notifSection2 = null;
            while (it2.hasNext()) {
                ListEntry listEntry2 = (ListEntry) it2.next();
                NotifSection section2 = listEntry2.getSection();
                Intrinsics.checkNotNull(section2);
                if (linkedHashSet2.contains(section2)) {
                    throw new RuntimeException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder("Section "), section2.label, " has been duplicated"));
                }
                boolean areEqual = Intrinsics.areEqual(section2, notifSection2);
                List list3 = nodeSpecImpl.children;
                if (!areEqual) {
                    NodeController nodeController2 = notifSection2 != null ? notifSection2.headerController : null;
                    NodeController nodeController3 = section2.headerController;
                    if (!Intrinsics.areEqual(nodeController3, nodeController2) && z2 && nodeController3 != null) {
                        ((ArrayList) list3).add(new NodeSpecImpl(nodeSpecImpl, nodeController3));
                    }
                    linkedHashSet2.add(notifSection2);
                    notifSection2 = section2;
                }
                ((ArrayList) list3).add(buildNotifNode(nodeSpecImpl, listEntry2));
            }
        }
        return nodeSpecImpl;
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
        Iterator it = groupEntry.mUnmodifiableChildren.iterator();
        while (it.hasNext()) {
            ((ArrayList) nodeSpecImpl2.children).add(buildNotifNode(nodeSpecImpl2, (NotificationEntry) it.next()));
        }
        return nodeSpecImpl2;
    }
}
