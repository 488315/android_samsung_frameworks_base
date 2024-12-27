package com.android.systemui.statusbar.notification;

import android.app.Notification;
import android.content.Context;
import android.os.Handler;
import android.service.notification.NotificationListenerService;
import android.view.View;
import com.android.internal.widget.ConversationLayout;
import com.android.systemui.statusbar.notification.ConversationNotificationManager;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManager;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import kotlin.Function;
import kotlin.Pair;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.EmptySequence;
import kotlin.sequences.FilteringSequence;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt___SequencesKt;

public final class ConversationNotificationManager {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public final Handler mainHandler;
    public final CommonNotifCollection notifCollection;
    public final ConcurrentHashMap states = new ConcurrentHashMap();
    public boolean notifPanelCollapsed = true;

    /* renamed from: com.android.systemui.statusbar.notification.ConversationNotificationManager$2, reason: invalid class name */
    public final /* synthetic */ class AnonymousClass2 implements BindEventManager.Listener, FunctionAdapter {
        public AnonymousClass2() {
        }

        public final boolean equals(Object obj) {
            if ((obj instanceof BindEventManager.Listener) && (obj instanceof FunctionAdapter)) {
                return getFunctionDelegate().equals(((FunctionAdapter) obj).getFunctionDelegate());
            }
            return false;
        }

        @Override // kotlin.jvm.internal.FunctionAdapter
        public final Function getFunctionDelegate() {
            return new FunctionReferenceImpl(1, ConversationNotificationManager.this, ConversationNotificationManager.class, "onEntryViewBound", "onEntryViewBound(Lcom/android/systemui/statusbar/notification/collection/NotificationEntry;)V", 0);
        }

        public final int hashCode() {
            return getFunctionDelegate().hashCode();
        }

        @Override // com.android.systemui.statusbar.notification.collection.inflation.BindEventManager.Listener
        public final void onViewBound(NotificationEntry notificationEntry) {
            ConversationNotificationManager conversationNotificationManager = ConversationNotificationManager.this;
            conversationNotificationManager.getClass();
            if (notificationEntry.mRanking.isConversation()) {
                ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                if (expandableNotificationRow != null) {
                    expandableNotificationRow.mExpansionChangedListener = new ConversationNotificationManager$onEntryViewBound$1(notificationEntry, conversationNotificationManager);
                }
                ExpandableNotificationRow expandableNotificationRow2 = notificationEntry.row;
                boolean z = false;
                if (expandableNotificationRow2 != null && expandableNotificationRow2.isExpanded(false)) {
                    z = true;
                }
                ConversationNotificationManager.onEntryViewBound$updateCount(z, conversationNotificationManager, notificationEntry);
            }
        }
    }

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public final class ConversationState {

        /* renamed from: notification, reason: collision with root package name */
        public final Notification f98notification;
        public final int unreadCount;

        public ConversationState(int i, Notification notification2) {
            this.unreadCount = i;
            this.f98notification = notification2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ConversationState)) {
                return false;
            }
            ConversationState conversationState = (ConversationState) obj;
            return this.unreadCount == conversationState.unreadCount && Intrinsics.areEqual(this.f98notification, conversationState.f98notification);
        }

        public final int hashCode() {
            return this.f98notification.hashCode() + (Integer.hashCode(this.unreadCount) * 31);
        }

        public final String toString() {
            return "ConversationState(unreadCount=" + this.unreadCount + ", notification=" + this.f98notification + ")";
        }
    }

    static {
        new Companion(null);
    }

    public ConversationNotificationManager(BindEventManager bindEventManager, Context context, CommonNotifCollection commonNotifCollection, Handler handler) {
        this.context = context;
        this.notifCollection = commonNotifCollection;
        this.mainHandler = handler;
        ((NotifPipeline) commonNotifCollection).addCollectionListener(new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.ConversationNotificationManager.1
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
                ConversationNotificationManager.this.states.remove(notificationEntry.mKey);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
                Sequence asSequence;
                int i = ConversationNotificationManager.$r8$clinit;
                final ConversationNotificationManager conversationNotificationManager = ConversationNotificationManager.this;
                conversationNotificationManager.getClass();
                NotificationListenerService.Ranking ranking = new NotificationListenerService.Ranking();
                FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.mapNotNull(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(conversationNotificationManager.states.keySet()), new Function1() { // from class: com.android.systemui.statusbar.notification.ConversationNotificationManager$updateNotificationRanking$activeConversationEntries$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return ((NotifPipeline) ConversationNotificationManager.this.notifCollection).mNotifCollection.getEntry((String) obj);
                    }
                }));
                while (filteringSequence$iterator$1.hasNext()) {
                    NotificationEntry notificationEntry = (NotificationEntry) filteringSequence$iterator$1.next();
                    if (rankingMap.getRanking(notificationEntry.mSbn.getKey(), ranking) && ranking.isConversation()) {
                        final boolean isImportantConversation = ranking.getChannel().isImportantConversation();
                        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                        if (expandableNotificationRow != null) {
                            NotificationContentView[] notificationContentViewArr = expandableNotificationRow.mLayouts;
                            NotificationContentView[] notificationContentViewArr2 = (NotificationContentView[]) Arrays.copyOf(notificationContentViewArr, notificationContentViewArr.length);
                            if (notificationContentViewArr2 != null && (asSequence = ArraysKt___ArraysKt.asSequence(notificationContentViewArr2)) != null) {
                                FilteringSequence$iterator$1 filteringSequence$iterator$12 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.filterNot(SequencesKt___SequencesKt.mapNotNull(SequencesKt___SequencesKt.flatMap(asSequence, ConversationNotificationManager$updateNotificationRanking$1.INSTANCE), new Function1() { // from class: com.android.systemui.statusbar.notification.ConversationNotificationManager$updateNotificationRanking$2
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj) {
                                        ConversationLayout conversationLayout = (View) obj;
                                        if (conversationLayout instanceof ConversationLayout) {
                                            return conversationLayout;
                                        }
                                        return null;
                                    }
                                }), new Function1() { // from class: com.android.systemui.statusbar.notification.ConversationNotificationManager$updateNotificationRanking$3
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj) {
                                        return Boolean.valueOf(((ConversationLayout) obj).isImportantConversation() == isImportantConversation);
                                    }
                                }));
                                while (filteringSequence$iterator$12.hasNext()) {
                                    final ConversationLayout conversationLayout = (ConversationLayout) filteringSequence$iterator$12.next();
                                    if (isImportantConversation && notificationEntry.mIsMarkedForUserTriggeredMovement) {
                                        conversationNotificationManager.mainHandler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.notification.ConversationNotificationManager$updateNotificationRanking$4$1
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                conversationLayout.setIsImportantConversation(isImportantConversation, true);
                                            }
                                        }, 960L);
                                    } else {
                                        conversationLayout.setIsImportantConversation(isImportantConversation, false);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
        bindEventManager.listeners.addIfAbsent(new AnonymousClass2());
    }

    public static final void onEntryViewBound$updateCount(boolean z, ConversationNotificationManager conversationNotificationManager, NotificationEntry notificationEntry) {
        if (z) {
            if (conversationNotificationManager.notifPanelCollapsed) {
                ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                boolean z2 = false;
                if (expandableNotificationRow != null) {
                    if (!expandableNotificationRow.mIsPinned ? false : expandableNotificationRow.mExpandedWhenPinned) {
                        z2 = true;
                    }
                }
                if (!z2) {
                    return;
                }
            }
            conversationNotificationManager.states.compute(notificationEntry.mKey, ConversationNotificationManager$resetCount$1.INSTANCE);
            ExpandableNotificationRow expandableNotificationRow2 = notificationEntry.row;
            if (expandableNotificationRow2 != null) {
                resetBadgeUi(expandableNotificationRow2);
            }
        }
    }

    public static void resetBadgeUi(ExpandableNotificationRow expandableNotificationRow) {
        Sequence sequence;
        NotificationContentView[] notificationContentViewArr = expandableNotificationRow.mLayouts;
        NotificationContentView[] notificationContentViewArr2 = (NotificationContentView[]) Arrays.copyOf(notificationContentViewArr, notificationContentViewArr.length);
        if (notificationContentViewArr2 == null || (sequence = ArraysKt___ArraysKt.asSequence(notificationContentViewArr2)) == null) {
            sequence = EmptySequence.INSTANCE;
        }
        FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.mapNotNull(SequencesKt___SequencesKt.flatMap(sequence, new Function1() { // from class: com.android.systemui.statusbar.notification.ConversationNotificationManager$resetBadgeUi$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                NotificationContentView notificationContentView = (NotificationContentView) obj;
                notificationContentView.getClass();
                return ArraysKt___ArraysKt.asSequence(new View[]{notificationContentView.mContractedChild, notificationContentView.mHeadsUpChild, notificationContentView.mExpandedChild, notificationContentView.mSingleLineView});
            }
        }), new Function1() { // from class: com.android.systemui.statusbar.notification.ConversationNotificationManager$resetBadgeUi$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ConversationLayout conversationLayout = (View) obj;
                if (conversationLayout instanceof ConversationLayout) {
                    return conversationLayout;
                }
                return null;
            }
        }));
        while (filteringSequence$iterator$1.hasNext()) {
            ((ConversationLayout) filteringSequence$iterator$1.next()).setUnreadCount(0);
        }
    }

    public final void onNotificationPanelExpandStateChanged(boolean z) {
        this.notifPanelCollapsed = z;
        if (z) {
            return;
        }
        FilteringSequence mapNotNull = SequencesKt___SequencesKt.mapNotNull(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(this.states.entrySet()), new Function1() { // from class: com.android.systemui.statusbar.notification.ConversationNotificationManager$onNotificationPanelExpandStateChanged$expanded$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ExpandableNotificationRow expandableNotificationRow;
                String str = (String) ((Map.Entry) obj).getKey();
                NotificationEntry entry = ((NotifPipeline) ConversationNotificationManager.this.notifCollection).mNotifCollection.getEntry(str);
                if (entry == null || (expandableNotificationRow = entry.row) == null || !expandableNotificationRow.isExpanded(false)) {
                    return null;
                }
                return new Pair(str, entry);
            }
        });
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(mapNotNull);
        while (filteringSequence$iterator$1.hasNext()) {
            Pair pair = (Pair) filteringSequence$iterator$1.next();
            linkedHashMap.put(pair.component1(), pair.component2());
        }
        final Map optimizeReadOnlyMap = MapsKt__MapsKt.optimizeReadOnlyMap(linkedHashMap);
        this.states.replaceAll(new BiFunction() { // from class: com.android.systemui.statusbar.notification.ConversationNotificationManager$onNotificationPanelExpandStateChanged$1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                ConversationNotificationManager.ConversationState conversationState = (ConversationNotificationManager.ConversationState) obj2;
                return optimizeReadOnlyMap.containsKey((String) obj) ? new ConversationNotificationManager.ConversationState(0, conversationState.f98notification) : conversationState;
            }
        });
        FilteringSequence$iterator$1 filteringSequence$iterator$12 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.mapNotNull(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(optimizeReadOnlyMap.values()), new Function1() { // from class: com.android.systemui.statusbar.notification.ConversationNotificationManager$onNotificationPanelExpandStateChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ((NotificationEntry) obj).row;
            }
        }));
        while (filteringSequence$iterator$12.hasNext()) {
            resetBadgeUi((ExpandableNotificationRow) filteringSequence$iterator$12.next());
        }
    }
}
