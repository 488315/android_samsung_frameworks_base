package com.android.systemui.statusbar.notification.collection.coordinator;

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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener
        public final void onBeforeRenderList(List<PipelineEntry> list) {
            Map map;
            ConversationIconManager conversationIconManager;
            Map map2;
            map = ConversationCoordinator.this.promotedEntriesToSummaryOfSameChannel;
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
                            }
                        }
                    }
                    str = str2;
                }
                if (str != null) {
                    arrayList.add(str);
                }
            }
            conversationIconManager = ConversationCoordinator.this.conversationIconManager;
            IconManager iconManager = (IconManager) conversationIconManager;
            iconManager.getClass();
            Set set = CollectionsKt___CollectionsKt.toSet(arrayList);
            boolean areEqual = Intrinsics.areEqual(iconManager.unimportantConversationKeys, set);
            iconManager.unimportantConversationKeys = set;
            if (!areEqual) {
                iconManager.recalculateForImportantConversationChange();
            }
            map2 = ConversationCoordinator.this.promotedEntriesToSummaryOfSameChannel;
            map2.clear();
        }
    };
    private final ConversationCoordinator$notificationPromoter$1 notificationPromoter = new NotifPromoter() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator$notificationPromoter$1
        {
            super("ConversationCoordinator");
        }

        /* JADX WARN: Code restructure failed: missing block: B:6:0x0013, code lost:
        
            if (r5.isInsignificant() == false) goto L10;
         */
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean shouldPromoteToTopLevel(com.android.systemui.statusbar.notification.collection.NotificationEntry r5) {
            /*
                r4 = this;
                android.service.notification.NotificationListenerService$Ranking r0 = r5.mRanking
                android.app.NotificationChannel r0 = r0.getChannel()
                if (r0 == 0) goto L16
                boolean r0 = r0.isImportantConversation()
                r1 = 1
                if (r0 != r1) goto L16
                boolean r0 = r5.isInsignificant()
                if (r0 != 0) goto L16
                goto L17
            L16:
                r1 = 0
            L17:
                if (r1 == 0) goto L47
                com.android.systemui.statusbar.notification.collection.ListAttachState r0 = r5.mAttachState
                com.android.systemui.statusbar.notification.collection.PipelineEntry r0 = r0.parent
                boolean r2 = r0 instanceof com.android.systemui.statusbar.notification.collection.GroupEntry
                r3 = 0
                if (r2 == 0) goto L25
                com.android.systemui.statusbar.notification.collection.GroupEntry r0 = (com.android.systemui.statusbar.notification.collection.GroupEntry) r0
                goto L26
            L25:
                r0 = r3
            L26:
                if (r0 == 0) goto L2a
                com.android.systemui.statusbar.notification.collection.NotificationEntry r3 = r0.mSummary
            L2a:
                if (r3 == 0) goto L47
                android.service.notification.NotificationListenerService$Ranking r0 = r5.mRanking
                android.app.NotificationChannel r0 = r0.getChannel()
                android.service.notification.NotificationListenerService$Ranking r2 = r3.mRanking
                android.app.NotificationChannel r2 = r2.getChannel()
                boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r2)
                if (r0 == 0) goto L47
                com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator r4 = com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator.this
                java.util.Map r4 = com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator.access$getPromotedEntriesToSummaryOfSameChannel$p(r4)
                r4.put(r5, r3)
            L47:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator$notificationPromoter$1.shouldPromoteToTopLevel(com.android.systemui.statusbar.notification.collection.NotificationEntry):boolean");
        }
    };
    private final NotifSectioner priorityPeopleSectioner = new NotifSectioner() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator$priorityPeopleSectioner$1
        {
            super("Priority People", 10);
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(PipelineEntry pipelineEntry) {
            int peopleType;
            if (BundleUtil.Companion.isClassified(pipelineEntry)) {
                return false;
            }
            NotificationEntry representativeEntry = pipelineEntry.getRepresentativeEntry();
            if (representativeEntry != null && representativeEntry.isInsignificant()) {
                return false;
            }
            peopleType = ConversationCoordinator.this.getPeopleType(pipelineEntry);
            return peopleType == 3;
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
            NodeController nodeController;
            nodeController = ConversationCoordinator.this.conversationHeaderNodeController;
            return nodeController;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(PipelineEntry pipelineEntry) {
            HighPriorityProvider highPriorityProvider;
            boolean isConversation;
            if (BundleUtil.Companion.isClassified(pipelineEntry)) {
                return false;
            }
            NotificationEntry representativeEntry = pipelineEntry.getRepresentativeEntry();
            if (representativeEntry != null && representativeEntry.isInsignificant()) {
                return false;
            }
            highPriorityProvider = ConversationCoordinator.this.highPriorityProvider;
            if (highPriorityProvider.isHighPriorityConversation(pipelineEntry)) {
                return true;
            }
            isConversation = ConversationCoordinator.this.isConversation(pipelineEntry);
            return isConversation;
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
            int peopleType;
            int peopleType2;
            peopleType = ConversationCoordinator.this.getPeopleType(pipelineEntry);
            peopleType2 = ConversationCoordinator.this.getPeopleType(pipelineEntry2);
            return Intrinsics.compare(peopleType2, peopleType);
        }
    };
    private final NodeController conversationHeaderNodeController = null;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
