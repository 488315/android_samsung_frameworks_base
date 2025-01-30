package com.android.systemui.statusbar.notification.collection.coordinator;

import android.util.IndentingPrintWriter;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.provider.SectionHeaderVisibilityProvider;
import com.android.systemui.statusbar.notification.collection.provider.SeenNotificationsProviderImpl;
import com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.settings.SecureSettings;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class KeyguardCoordinator implements Coordinator, Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long SEEN_TIMEOUT;
    public final CoroutineDispatcher bgDispatcher;
    public final DumpManager dumpManager;
    public final HeadsUpManager headsUpManager;
    public final KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider;
    public final KeyguardRepository keyguardRepository;
    public final KeyguardTransitionRepository keyguardTransitionRepository;
    public final KeyguardCoordinatorLogger logger;
    public final CoroutineScope scope;
    public final SectionHeaderVisibilityProvider sectionHeaderVisibilityProvider;
    public final SecureSettings secureSettings;
    public final SeenNotificationsProviderImpl seenNotifsProvider;
    public final StatusBarStateController statusBarStateController;
    public boolean unseenFilterEnabled;
    public final KeyguardCoordinator$unseenNotifFilter$1 unseenNotifFilter;
    public final Set unseenNotifications = new LinkedHashSet();
    public final KeyguardCoordinator$collectionListener$1 collectionListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$collectionListener$1
        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public final void onEntryAdded(NotificationEntry notificationEntry) {
            KeyguardCoordinator keyguardCoordinator = KeyguardCoordinator.this;
            if (((KeyguardStateControllerImpl) ((KeyguardRepositoryImpl) keyguardCoordinator.keyguardRepository).keyguardStateController).mShowing || !keyguardCoordinator.statusBarStateController.isExpanded()) {
                String str = notificationEntry.mKey;
                KeyguardCoordinatorLogger keyguardCoordinatorLogger = keyguardCoordinator.logger;
                keyguardCoordinatorLogger.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                KeyguardCoordinatorLogger$logUnseenAdded$2 keyguardCoordinatorLogger$logUnseenAdded$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinatorLogger$logUnseenAdded$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return KeyAttributes$$ExternalSyntheticOutline0.m21m("Unseen notif added: ", ((LogMessage) obj).getStr1());
                    }
                };
                LogBuffer logBuffer = keyguardCoordinatorLogger.buffer;
                LogMessage obtain = logBuffer.obtain("KeyguardCoordinator", logLevel, keyguardCoordinatorLogger$logUnseenAdded$2, null);
                obtain.setStr1(str);
                logBuffer.commit(obtain);
                keyguardCoordinator.unseenNotifications.add(notificationEntry);
            }
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
            KeyguardCoordinator keyguardCoordinator = KeyguardCoordinator.this;
            if (keyguardCoordinator.unseenNotifications.remove(notificationEntry)) {
                KeyguardCoordinatorLogger keyguardCoordinatorLogger = keyguardCoordinator.logger;
                String str = notificationEntry.mKey;
                keyguardCoordinatorLogger.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                KeyguardCoordinatorLogger$logUnseenRemoved$2 keyguardCoordinatorLogger$logUnseenRemoved$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinatorLogger$logUnseenRemoved$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return KeyAttributes$$ExternalSyntheticOutline0.m21m("Unseen notif removed: ", ((LogMessage) obj).getStr1());
                    }
                };
                LogBuffer logBuffer = keyguardCoordinatorLogger.buffer;
                LogMessage obtain = logBuffer.obtain("KeyguardCoordinator", logLevel, keyguardCoordinatorLogger$logUnseenRemoved$2, null);
                obtain.setStr1(str);
                logBuffer.commit(obtain);
            }
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public final void onEntryUpdated(NotificationEntry notificationEntry) {
            KeyguardCoordinator keyguardCoordinator = KeyguardCoordinator.this;
            if (((KeyguardStateControllerImpl) ((KeyguardRepositoryImpl) keyguardCoordinator.keyguardRepository).keyguardStateController).mShowing || !keyguardCoordinator.statusBarStateController.isExpanded()) {
                String str = notificationEntry.mKey;
                KeyguardCoordinatorLogger keyguardCoordinatorLogger = keyguardCoordinator.logger;
                keyguardCoordinatorLogger.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                KeyguardCoordinatorLogger$logUnseenUpdated$2 keyguardCoordinatorLogger$logUnseenUpdated$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinatorLogger$logUnseenUpdated$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return KeyAttributes$$ExternalSyntheticOutline0.m21m("Unseen notif updated: ", ((LogMessage) obj).getStr1());
                    }
                };
                LogBuffer logBuffer = keyguardCoordinatorLogger.buffer;
                LogMessage obtain = logBuffer.obtain("KeyguardCoordinator", logLevel, keyguardCoordinatorLogger$logUnseenUpdated$2, null);
                obtain.setStr1(str);
                logBuffer.commit(obtain);
                keyguardCoordinator.unseenNotifications.add(notificationEntry);
            }
        }
    };
    public final KeyguardCoordinator$notifFilter$1 notifFilter = new NotifFilter() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$notifFilter$1
        {
            super("KeyguardCoordinator");
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public final boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            return ((KeyguardNotificationVisibilityProviderImpl) KeyguardCoordinator.this.keyguardNotificationVisibilityProvider).shouldHideNotification(notificationEntry);
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        Duration.Companion companion = Duration.Companion;
        SEEN_TIMEOUT = DurationKt.toDuration(5, DurationUnit.SECONDS);
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$collectionListener$1] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$unseenNotifFilter$1] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$notifFilter$1] */
    public KeyguardCoordinator(CoroutineDispatcher coroutineDispatcher, DumpManager dumpManager, HeadsUpManager headsUpManager, KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider, KeyguardRepository keyguardRepository, KeyguardTransitionRepository keyguardTransitionRepository, KeyguardCoordinatorLogger keyguardCoordinatorLogger, NotifPipelineFlags notifPipelineFlags, CoroutineScope coroutineScope, SectionHeaderVisibilityProvider sectionHeaderVisibilityProvider, SecureSettings secureSettings, SeenNotificationsProviderImpl seenNotificationsProviderImpl, StatusBarStateController statusBarStateController) {
        this.bgDispatcher = coroutineDispatcher;
        this.dumpManager = dumpManager;
        this.headsUpManager = headsUpManager;
        this.keyguardNotificationVisibilityProvider = keyguardNotificationVisibilityProvider;
        this.keyguardRepository = keyguardRepository;
        this.keyguardTransitionRepository = keyguardTransitionRepository;
        this.logger = keyguardCoordinatorLogger;
        this.scope = coroutineScope;
        this.sectionHeaderVisibilityProvider = sectionHeaderVisibilityProvider;
        this.secureSettings = secureSettings;
        this.seenNotifsProvider = seenNotificationsProviderImpl;
        this.statusBarStateController = statusBarStateController;
        final String str = "KeyguardCoordinator-unseen";
        this.unseenNotifFilter = new NotifFilter(str) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$unseenNotifFilter$1
            public boolean hasFilteredAnyNotifs;

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable
            public final void onCleanup() {
                KeyguardCoordinator keyguardCoordinator = KeyguardCoordinator.this;
                KeyguardCoordinatorLogger keyguardCoordinatorLogger2 = keyguardCoordinator.logger;
                boolean z = this.hasFilteredAnyNotifs;
                keyguardCoordinatorLogger2.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                KeyguardCoordinatorLogger$logProviderHasFilteredOutSeenNotifs$2 keyguardCoordinatorLogger$logProviderHasFilteredOutSeenNotifs$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinatorLogger$logProviderHasFilteredOutSeenNotifs$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return AbstractC0866xb1ce8deb.m86m("UI showing unseen filter treatment: ", ((LogMessage) obj).getBool1());
                    }
                };
                LogBuffer logBuffer = keyguardCoordinatorLogger2.buffer;
                LogMessage obtain = logBuffer.obtain("KeyguardCoordinator", logLevel, keyguardCoordinatorLogger$logProviderHasFilteredOutSeenNotifs$2, null);
                obtain.setBool1(z);
                logBuffer.commit(obtain);
                keyguardCoordinator.seenNotifsProvider.hasFilteredOutSeenNotifications = this.hasFilteredAnyNotifs;
                this.hasFilteredAnyNotifs = false;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
            public final boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
                boolean z;
                KeyguardCoordinator keyguardCoordinator = KeyguardCoordinator.this;
                boolean z2 = true;
                if (keyguardCoordinator.unseenFilterEnabled && ((KeyguardStateControllerImpl) ((KeyguardRepositoryImpl) keyguardCoordinator.keyguardRepository).keyguardStateController).mShowing && !keyguardCoordinator.unseenNotifications.contains(notificationEntry)) {
                    GroupEntry parent = notificationEntry.getParent();
                    if (!Intrinsics.areEqual(parent != null ? parent.mSummary : null, notificationEntry)) {
                        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                        if (!((expandableNotificationRow == null ? false : expandableNotificationRow.mEntry.mSbn.getNotification().isMediaNotification()) || notificationEntry.mSbn.isOngoing())) {
                            z = true;
                            if (!this.hasFilteredAnyNotifs && !z) {
                                z2 = false;
                            }
                            this.hasFilteredAnyNotifs = z2;
                            return z;
                        }
                    }
                }
                z = false;
                if (!this.hasFilteredAnyNotifs) {
                    z2 = false;
                }
                this.hasFilteredAnyNotifs = z2;
                return z;
            }
        };
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public final void attach(NotifPipeline notifPipeline) {
        notifPipeline.addFinalizeFilter(this.notifFilter);
        ((KeyguardNotificationVisibilityProviderImpl) this.keyguardNotificationVisibilityProvider).onStateChangedListeners.addIfAbsent(new Consumer() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$attach$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                String str = (String) obj;
                KeyguardCoordinator keyguardCoordinator = KeyguardCoordinator.this;
                int i = KeyguardCoordinator.$r8$clinit;
                boolean z = false;
                boolean z2 = keyguardCoordinator.statusBarStateController.getState() == 1;
                SectionHeaderVisibilityProvider sectionHeaderVisibilityProvider = keyguardCoordinator.sectionHeaderVisibilityProvider;
                boolean z3 = sectionHeaderVisibilityProvider.neverShowSectionHeaders;
                if (!z2 && !z3) {
                    z = true;
                }
                sectionHeaderVisibilityProvider.sectionHeadersVisible = z;
                keyguardCoordinator.notifFilter.invalidateList(str);
            }
        });
        boolean z = false;
        boolean z2 = this.statusBarStateController.getState() == 1;
        SectionHeaderVisibilityProvider sectionHeaderVisibilityProvider = this.sectionHeaderVisibilityProvider;
        boolean z3 = sectionHeaderVisibilityProvider.neverShowSectionHeaders;
        if (!z2 && !z3) {
            z = true;
        }
        sectionHeaderVisibilityProvider.sectionHeadersVisible = z;
        notifPipeline.addFinalizeFilter(this.unseenNotifFilter);
        notifPipeline.addCollectionListener(this.collectionListener);
        KeyguardCoordinator$attachUnseenFilter$1 keyguardCoordinator$attachUnseenFilter$1 = new KeyguardCoordinator$attachUnseenFilter$1(this, null);
        CoroutineScope coroutineScope = this.scope;
        BuildersKt.launch$default(coroutineScope, null, null, keyguardCoordinator$attachUnseenFilter$1, 3);
        BuildersKt.launch$default(coroutineScope, null, null, new KeyguardCoordinator$attachUnseenFilter$2(this, null), 3);
        this.dumpManager.registerDumpable(this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.println("seenNotifsProvider.hasFilteredOutSeenNotifications=" + this.seenNotifsProvider.hasFilteredOutSeenNotifications);
        asIndenting.println("unseen notifications:");
        asIndenting.increaseIndent();
        Iterator it = this.unseenNotifications.iterator();
        while (it.hasNext()) {
            asIndenting.println(((NotificationEntry) it.next()).mKey);
        }
        asIndenting.decreaseIndent();
    }

    /* renamed from: getUnseenNotifFilter$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
    public static /* synthetic */ void m203x93a8fda4() {
    }
}
