package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.NotificationChannel;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.SortBySectionTimeFlag;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.icon.ConversationIconManager;
import com.android.systemui.statusbar.notification.icon.IconManager;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifierImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@CoordinatorScope
/* loaded from: classes3.dex */
public final class ConversationCoordinator implements Coordinator {
    private static final String TAG = "ConversationCoordinator";
    private final ConversationIconManager conversationIconManager;
    private final HighPriorityProvider highPriorityProvider;
    private final NodeController peopleHeaderController;
    private final PeopleNotificationIdentifier peopleNotificationIdentifier;
    private static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    private final Map<NotificationEntry, NotificationEntry> promotedEntriesToSummaryOfSameChannel = new LinkedHashMap();
    private final OnBeforeRenderListListener onBeforeRenderListListener = new OnBeforeRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator$onBeforeRenderListListener$1
        /* JADX WARN: Removed duplicated region for block: B:16:0x004b  */
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onBeforeRenderList(List<PipelineEntry> list) {
            Map map = this.this$0.promotedEntriesToSummaryOfSameChannel;
            ArrayList arrayList = new ArrayList();
            for (Map.Entry entry : map.entrySet()) {
                NotificationEntry notificationEntry = (NotificationEntry) entry.getKey();
                NotificationEntry notificationEntry2 = (NotificationEntry) entry.getValue();
                PipelineEntry pipelineEntry = notificationEntry2.mAttachState.parent;
                String str = null;
                if (pipelineEntry != null && !pipelineEntry.equals(notificationEntry.mAttachState.parent) && pipelineEntry.getParent() != null) {
                    boolean z = pipelineEntry instanceof GroupEntry;
                    String str2 = notificationEntry2.mKey;
                    if (z) {
                        GroupEntry groupEntry = (GroupEntry) pipelineEntry;
                        if (Intrinsics.areEqual(groupEntry.mSummary, notificationEntry2)) {
                            List list2 = groupEntry.mUnmodifiableChildren;
                            if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                                Iterator it = list2.iterator();
                                while (it.hasNext()) {
                                    if (Intrinsics.areEqual(((NotificationEntry) it.next()).mRanking.getChannel(), notificationEntry2.mRanking.getChannel())) {
                                        break;
                                    }
                                }
                                str = str2;
                            }
                        }
                    } else {
                        str = str2;
                    }
                }
                if (str != null) {
                    arrayList.add(str);
                }
            }
            IconManager iconManager = (IconManager) this.this$0.conversationIconManager;
            iconManager.getClass();
            Set set = CollectionsKt___CollectionsKt.toSet(arrayList);
            boolean zAreEqual = Intrinsics.areEqual(iconManager.unimportantConversationKeys, set);
            iconManager.unimportantConversationKeys = set;
            if (!zAreEqual) {
                iconManager.recalculateForImportantConversationChange();
            }
            this.this$0.promotedEntriesToSummaryOfSameChannel.clear();
        }
    };
    private final ConversationCoordinator$notificationPromoter$1 notificationPromoter = new NotifPromoter() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator$notificationPromoter$1
        {
            super("ConversationCoordinator");
        }

        /* JADX WARN: Removed duplicated region for block: B:9:0x0016  */
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public boolean shouldPromoteToTopLevel(NotificationEntry notificationEntry) {
            boolean z;
            NotificationChannel channel = notificationEntry.mRanking.getChannel();
            if (channel != null) {
                z = channel.isImportantConversation() && !notificationEntry.isInsignificant();
            }
            if (z) {
                PipelineEntry pipelineEntry = notificationEntry.mAttachState.parent;
                GroupEntry groupEntry = pipelineEntry instanceof GroupEntry ? (GroupEntry) pipelineEntry : null;
                NotificationEntry notificationEntry2 = groupEntry != null ? groupEntry.mSummary : null;
                if (notificationEntry2 != null && Intrinsics.areEqual(notificationEntry.mRanking.getChannel(), notificationEntry2.mRanking.getChannel())) {
                    this.this$0.promotedEntriesToSummaryOfSameChannel.put(notificationEntry, notificationEntry2);
                }
            }
            return z;
        }
    };
    private final NotifSectioner priorityPeopleSectioner = new NotifSectioner() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator$priorityPeopleSectioner$1
        {
            super("Priority People", 10);
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(PipelineEntry pipelineEntry) {
            if (BundleUtil.Companion.isClassified(pipelineEntry)) {
                return false;
            }
            NotificationEntry representativeEntry = pipelineEntry.getRepresentativeEntry();
            return (representativeEntry == null || !representativeEntry.isInsignificant()) && this.this$0.getPeopleType(pipelineEntry) == 3;
        }
    };
    private final NotifSectioner peopleAlertingSectioner = new NotifSectioner() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator$peopleAlertingSectioner$1
        {
            super("People(alerting)", 11);
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public NotifComparator getComparator() {
            return null;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public NodeController getHeaderNodeController() {
            return this.this$0.conversationHeaderNodeController;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(PipelineEntry pipelineEntry) {
            if (BundleUtil.Companion.isClassified(pipelineEntry)) {
                return false;
            }
            NotificationEntry representativeEntry = pipelineEntry.getRepresentativeEntry();
            if (representativeEntry == null || !representativeEntry.isInsignificant()) {
                return this.this$0.highPriorityProvider.isHighPriorityConversation(pipelineEntry) || this.this$0.isConversation(pipelineEntry);
            }
            return false;
        }
    };
    private final NotifSectioner peopleSilentSectioner = new NotifSectioner() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator$peopleSilentSectioner$1
        {
            super("People(silent)", 11);
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public NotifComparator getComparator() {
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            int i = SortBySectionTimeFlag.$r8$clinit;
            throw new IllegalStateException("Legacy code path not supported when android.app.sort_section_by_time is enabled.");
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public NodeController getHeaderNodeController() {
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            int i = SortBySectionTimeFlag.$r8$clinit;
            throw new IllegalStateException("Legacy code path not supported when android.app.sort_section_by_time is enabled.");
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(PipelineEntry pipelineEntry) {
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            int i = SortBySectionTimeFlag.$r8$clinit;
            throw new IllegalStateException("Legacy code path not supported when android.app.sort_section_by_time is enabled.");
        }
    };
    private final NotifComparator notifComparator = new NotifComparator() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator$notifComparator$1
        {
            super("People");
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator, java.util.Comparator
        public int compare(PipelineEntry pipelineEntry, PipelineEntry pipelineEntry2) {
            return Intrinsics.compare(this.this$0.getPeopleType(pipelineEntry2), this.this$0.getPeopleType(pipelineEntry));
        }
    };
    private final NodeController conversationHeaderNodeController = null;

    final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator$notificationPromoter$1] */
    public ConversationCoordinator(PeopleNotificationIdentifier peopleNotificationIdentifier, ConversationIconManager conversationIconManager, HighPriorityProvider highPriorityProvider, NodeController nodeController) {
        this.peopleNotificationIdentifier = peopleNotificationIdentifier;
        this.conversationIconManager = conversationIconManager;
        this.highPriorityProvider = highPriorityProvider;
        this.peopleHeaderController = nodeController;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getPeopleType(PipelineEntry pipelineEntry) {
        NotificationEntry representativeEntry = pipelineEntry.getRepresentativeEntry();
        if (representativeEntry != null) {
            return ((PeopleNotificationIdentifierImpl) this.peopleNotificationIdentifier).getPeopleNotificationType(representativeEntry);
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isConversation(PipelineEntry pipelineEntry) {
        return getPeopleType(pipelineEntry) != 0;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addPromoter(this.notificationPromoter);
        notifPipeline.addOnBeforeRenderListListener(this.onBeforeRenderListListener);
    }

    public final NotifSectioner getPeopleAlertingSectioner() {
        return this.peopleAlertingSectioner;
    }

    public final NotifSectioner getPeopleSilentSectioner() {
        return this.peopleSilentSectioner;
    }

    public final NotifSectioner getPriorityPeopleSectioner() {
        return this.priorityPeopleSectioner;
    }
}
