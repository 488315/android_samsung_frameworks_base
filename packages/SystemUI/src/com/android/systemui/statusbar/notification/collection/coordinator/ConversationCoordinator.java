package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.Flags;
import android.app.NotificationChannel;
import com.android.systemui.NotiRune;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
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
        public final void onBeforeRenderList(List<ListEntry> list) {
            Map map;
            ConversationIconManager conversationIconManager;
            Map map2;
            map = ConversationCoordinator.this.promotedEntriesToSummaryOfSameChannel;
            ArrayList arrayList = new ArrayList();
            for (Map.Entry entry : map.entrySet()) {
                NotificationEntry notificationEntry = (NotificationEntry) entry.getKey();
                NotificationEntry notificationEntry2 = (NotificationEntry) entry.getValue();
                GroupEntry parent = notificationEntry2.getParent();
                String str = null;
                if (parent != null && !parent.equals(notificationEntry.getParent()) && parent.getParent() != null && Intrinsics.areEqual(parent.mSummary, notificationEntry2)) {
                    List list2 = parent.mUnmodifiableChildren;
                    if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                        Iterator it = list2.iterator();
                        while (it.hasNext()) {
                            if (Intrinsics.areEqual(((NotificationEntry) it.next()).mRanking.getChannel(), notificationEntry2.mRanking.getChannel())) {
                                break;
                            }
                        }
                    }
                    str = notificationEntry2.mKey;
                }
                if (str != null) {
                    arrayList.add(str);
                }
            }
            conversationIconManager = ConversationCoordinator.this.conversationIconManager;
            IconManager iconManager = (IconManager) conversationIconManager;
            iconManager.getClass();
            Set set = CollectionsKt___CollectionsKt.toSet(arrayList);
            boolean z = !Intrinsics.areEqual(iconManager.unimportantConversationKeys, set);
            iconManager.unimportantConversationKeys = set;
            if (z) {
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

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter
        public boolean shouldPromoteToTopLevel(NotificationEntry notificationEntry) {
            Map map;
            NotificationChannel channel = notificationEntry.mRanking.getChannel();
            boolean z = false;
            if (channel != null && channel.isImportantConversation()) {
                z = true;
            }
            if (z) {
                GroupEntry parent = notificationEntry.getParent();
                NotificationEntry notificationEntry2 = parent != null ? parent.mSummary : null;
                if (notificationEntry2 != null && Intrinsics.areEqual(notificationEntry.mRanking.getChannel(), notificationEntry2.mRanking.getChannel())) {
                    map = ConversationCoordinator.this.promotedEntriesToSummaryOfSameChannel;
                    map.put(notificationEntry, notificationEntry2);
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
        public boolean isInSection(ListEntry listEntry) {
            int peopleType;
            NotificationEntry representativeEntry;
            if (NotiRune.NOTI_INSIGNIFICANT && (representativeEntry = listEntry.getRepresentativeEntry()) != null && representativeEntry.isInsignificant()) {
                return false;
            }
            peopleType = ConversationCoordinator.this.getPeopleType(listEntry);
            return peopleType == 3;
        }
    };
    private final NotifSectioner peopleAlertingSectioner = new NotifSectioner() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator$peopleAlertingSectioner$1
        {
            super("People(alerting)", 11);
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public NotifComparator getComparator() {
            NotifComparator notifComparator;
            if (Flags.sortSectionByTime()) {
                return null;
            }
            notifComparator = ConversationCoordinator.this.notifComparator;
            return notifComparator;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public NodeController getHeaderNodeController() {
            NodeController nodeController;
            nodeController = ConversationCoordinator.this.conversationHeaderNodeController;
            return nodeController;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(ListEntry listEntry) {
            HighPriorityProvider highPriorityProvider;
            HighPriorityProvider highPriorityProvider2;
            boolean isConversation;
            NotificationEntry representativeEntry;
            if (NotiRune.NOTI_INSIGNIFICANT && (representativeEntry = listEntry.getRepresentativeEntry()) != null && representativeEntry.isInsignificant()) {
                return false;
            }
            if (!Flags.sortSectionByTime()) {
                highPriorityProvider = ConversationCoordinator.this.highPriorityProvider;
                return highPriorityProvider.isHighPriorityConversation(listEntry);
            }
            highPriorityProvider2 = ConversationCoordinator.this.highPriorityProvider;
            if (!highPriorityProvider2.isHighPriorityConversation(listEntry)) {
                isConversation = ConversationCoordinator.this.isConversation(listEntry);
                if (!isConversation) {
                    return false;
                }
            }
            return true;
        }
    };
    private final NotifSectioner peopleSilentSectioner = new NotifSectioner() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator$peopleSilentSectioner$1
        {
            super("People(silent)", 11);
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public NotifComparator getComparator() {
            NotifComparator notifComparator;
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            int i = SortBySectionTimeFlag.$r8$clinit;
            if (!(!Flags.sortSectionByTime())) {
                throw new IllegalStateException("Legacy code path not supported when android.app.sort_section_by_time is enabled.".toString());
            }
            notifComparator = ConversationCoordinator.this.notifComparator;
            return notifComparator;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public NodeController getHeaderNodeController() {
            NodeController nodeController;
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            int i = SortBySectionTimeFlag.$r8$clinit;
            if (!(!Flags.sortSectionByTime())) {
                throw new IllegalStateException("Legacy code path not supported when android.app.sort_section_by_time is enabled.".toString());
            }
            nodeController = ConversationCoordinator.this.conversationHeaderNodeController;
            return nodeController;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(ListEntry listEntry) {
            boolean isConversation;
            NotificationEntry representativeEntry;
            if (NotiRune.NOTI_INSIGNIFICANT && (representativeEntry = listEntry.getRepresentativeEntry()) != null && representativeEntry.isInsignificant()) {
                return false;
            }
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            int i = SortBySectionTimeFlag.$r8$clinit;
            if (!(!Flags.sortSectionByTime())) {
                throw new IllegalStateException("Legacy code path not supported when android.app.sort_section_by_time is enabled.".toString());
            }
            isConversation = ConversationCoordinator.this.isConversation(listEntry);
            return isConversation;
        }
    };
    private final NotifComparator notifComparator = new NotifComparator() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator$notifComparator$1
        {
            super("People");
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator, java.util.Comparator
        public int compare(ListEntry listEntry, ListEntry listEntry2) {
            int peopleType;
            int peopleType2;
            peopleType = ConversationCoordinator.this.getPeopleType(listEntry);
            peopleType2 = ConversationCoordinator.this.getPeopleType(listEntry2);
            return Intrinsics.compare(peopleType2, peopleType);
        }
    };
    private final NodeController conversationHeaderNodeController = null;

    final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ConversationCoordinator(PeopleNotificationIdentifier peopleNotificationIdentifier, ConversationIconManager conversationIconManager, HighPriorityProvider highPriorityProvider, NodeController nodeController) {
        this.peopleNotificationIdentifier = peopleNotificationIdentifier;
        this.conversationIconManager = conversationIconManager;
        this.highPriorityProvider = highPriorityProvider;
        this.peopleHeaderController = nodeController;
    }

    public final int getPeopleType(ListEntry listEntry) {
        NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
        if (representativeEntry != null) {
            return ((PeopleNotificationIdentifierImpl) this.peopleNotificationIdentifier).getPeopleNotificationType(representativeEntry);
        }
        return 0;
    }

    public final boolean isConversation(ListEntry listEntry) {
        return getPeopleType(listEntry) != 0;
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
