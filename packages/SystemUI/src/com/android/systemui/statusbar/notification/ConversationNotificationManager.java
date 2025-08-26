package com.android.systemui.statusbar.notification;

import android.app.Notification;
import android.content.Context;
import android.os.Handler;
import android.service.notification.NotificationListenerService;
import com.android.internal.widget.ConversationLayout;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManager;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Function;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.EmptySequence;
import kotlin.sequences.FilteringSequence;
import kotlin.sequences.FilteringSequence.AnonymousClass1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt___SequencesKt;

/* loaded from: classes3.dex */
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
                return Intrinsics.areEqual(getFunctionDelegate(), ((FunctionAdapter) obj).getFunctionDelegate());
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
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class ConversationState {

        /* renamed from: notification, reason: collision with root package name */
        public final Notification f134notification;
        public final int unreadCount;

        public ConversationState(int i, Notification notification2) {
            this.unreadCount = i;
            this.f134notification = notification2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ConversationState)) {
                return false;
            }
            ConversationState conversationState = (ConversationState) obj;
            return this.unreadCount == conversationState.unreadCount && Intrinsics.areEqual(this.f134notification, conversationState.f134notification);
        }

        public final int hashCode() {
            return this.f134notification.hashCode() + (Integer.hashCode(this.unreadCount) * 31);
        }

        public final String toString() {
            return "ConversationState(unreadCount=" + this.unreadCount + ", notification=" + this.f134notification + ")";
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
                Sequence sequenceAsSequence;
                int i = ConversationNotificationManager.$r8$clinit;
                ConversationNotificationManager conversationNotificationManager = ConversationNotificationManager.this;
                conversationNotificationManager.getClass();
                NotificationListenerService.Ranking ranking = new NotificationListenerService.Ranking();
                FilteringSequence.AnonymousClass1 anonymousClass1 = SequencesKt___SequencesKt.mapNotNull(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(conversationNotificationManager.states.keySet()), new ConversationNotificationManager$$ExternalSyntheticLambda4(conversationNotificationManager, 1)).new AnonymousClass1();
                while (anonymousClass1.hasNext()) {
                    NotificationEntry notificationEntry = (NotificationEntry) anonymousClass1.next();
                    if (rankingMap.getRanking(notificationEntry.mSbn.getKey(), ranking) && ranking.isConversation()) {
                        final boolean zIsImportantConversation = ranking.getChannel().isImportantConversation();
                        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                        if (expandableNotificationRow != null) {
                            NotificationContentView[] notificationContentViewArr = expandableNotificationRow.mLayouts;
                            NotificationContentView[] notificationContentViewArr2 = (NotificationContentView[]) Arrays.copyOf(notificationContentViewArr, notificationContentViewArr.length);
                            if (notificationContentViewArr2 != null && (sequenceAsSequence = ArraysKt___ArraysKt.asSequence(notificationContentViewArr2)) != null) {
                                FilteringSequence.AnonymousClass1 anonymousClass12 = SequencesKt___SequencesKt.filterNot(SequencesKt___SequencesKt.mapNotNull(SequencesKt___SequencesKt.flatMap(sequenceAsSequence, ConversationNotificationManager$updateNotificationRanking$1.INSTANCE), new ConversationNotificationManager$$ExternalSyntheticLambda1(3)), new Function1() { // from class: com.android.systemui.statusbar.notification.ConversationNotificationManager$$ExternalSyntheticLambda9
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj) {
                                        int i2 = ConversationNotificationManager.$r8$clinit;
                                        return Boolean.valueOf(((ConversationLayout) obj).isImportantConversation() == zIsImportantConversation);
                                    }
                                }).new AnonymousClass1();
                                while (anonymousClass12.hasNext()) {
                                    final ConversationLayout conversationLayout = (ConversationLayout) anonymousClass12.next();
                                    if (zIsImportantConversation && notificationEntry.mIsMarkedForUserTriggeredMovement) {
                                        conversationNotificationManager.mainHandler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.notification.ConversationNotificationManager$updateNotificationRanking$4$1
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                conversationLayout.setIsImportantConversation(zIsImportantConversation, true);
                                            }
                                        }, 960L);
                                    } else {
                                        conversationLayout.setIsImportantConversation(zIsImportantConversation, false);
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
                if (expandableNotificationRow == null) {
                    return;
                }
                if (!(!expandableNotificationRow.mPinnedStatus.isPinned() ? false : expandableNotificationRow.mExpandedWhenPinned)) {
                    return;
                }
            }
            conversationNotificationManager.states.compute(notificationEntry.mKey, new ConversationNotificationManager$sam$java_util_function_BiFunction$0(new ConversationNotificationManager$$ExternalSyntheticLambda0()));
            ExpandableNotificationRow expandableNotificationRow2 = notificationEntry.row;
            if (expandableNotificationRow2 != null) {
                resetBadgeUi(expandableNotificationRow2);
            }
        }
    }

    public static void resetBadgeUi(ExpandableNotificationRow expandableNotificationRow) {
        Sequence sequenceAsSequence;
        NotificationContentView[] notificationContentViewArr = expandableNotificationRow.mLayouts;
        NotificationContentView[] notificationContentViewArr2 = (NotificationContentView[]) Arrays.copyOf(notificationContentViewArr, notificationContentViewArr.length);
        if (notificationContentViewArr2 == null || (sequenceAsSequence = ArraysKt___ArraysKt.asSequence(notificationContentViewArr2)) == null) {
            sequenceAsSequence = EmptySequence.INSTANCE;
        }
        FilteringSequence.AnonymousClass1 anonymousClass1 = SequencesKt___SequencesKt.mapNotNull(SequencesKt___SequencesKt.flatMap(sequenceAsSequence, new ConversationNotificationManager$$ExternalSyntheticLambda1(0)), new ConversationNotificationManager$$ExternalSyntheticLambda1(1)).new AnonymousClass1();
        while (anonymousClass1.hasNext()) {
            ((ConversationLayout) anonymousClass1.next()).setUnreadCount(0);
        }
    }
}
