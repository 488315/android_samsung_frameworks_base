package com.android.systemui.statusbar.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.bixby2.controller.NotificationController;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.util.MediaFeatureFlag;
import com.android.systemui.plugins.aod.PluginAOD;
import com.android.systemui.plugins.clockpack.PluginClockPack;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.SubscreenDeviceModelParent;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.SubscreenQuickReplyCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.SubscreenQuickReplyCoordinator$registerSubscreenStateChangeListener$1;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManager;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.provider.DebugModeFilterProvider;
import com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.Utils;
import com.samsung.android.view.SemWindowManager;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import kotlin.Function;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

public final class SubscreenNotificationController implements NotifCollectionListener, SemWindowManager.FoldStateListener {
    public final Optional bubblesOptional;
    public final Context context;
    public final ConversationNotificationManager conversationNotificationManager;
    public final DebugModeFilterProvider debugModeFilterProvider;
    public final KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final SubscreenDeviceModelParent mDeviceModel;
    public final SubscreenNotificationController$mDeviceStateCallback$1 mDeviceStateCallback;
    public final SubscreenNotificationController$mRemoteInputCancelListener$1 mRemoteInputCancelListener;
    public final MediaFeatureFlag mediaFeatureFlag;
    public final NotifPipeline notifPipeline;
    public final NotificationController notificationController;
    public final Lazy pluginAODManagerLazy;
    public final NotificationRemoteInputManager remoteInputManager;
    public SubscreenNotificationReplyActivity replyActivity;
    private final SettingsHelper settingsHelper;
    public final StatusBarStateController statusBarStateController;
    public final List subscreenStateListenerList;

    /* renamed from: com.android.systemui.statusbar.notification.SubscreenNotificationController$1, reason: invalid class name */
    public final /* synthetic */ class AnonymousClass1 implements BindEventManager.Listener, FunctionAdapter {
        public AnonymousClass1() {
        }

        public final boolean equals(Object obj) {
            if ((obj instanceof BindEventManager.Listener) && (obj instanceof FunctionAdapter)) {
                return getFunctionDelegate().equals(((FunctionAdapter) obj).getFunctionDelegate());
            }
            return false;
        }

        @Override // kotlin.jvm.internal.FunctionAdapter
        public final Function getFunctionDelegate() {
            return new FunctionReferenceImpl(1, SubscreenNotificationController.this, SubscreenNotificationController.class, "onEntryViewBound", "onEntryViewBound(Lcom/android/systemui/statusbar/notification/collection/NotificationEntry;)V", 0);
        }

        public final int hashCode() {
            return getFunctionDelegate().hashCode();
        }

        /* JADX WARN: Code restructure failed: missing block: B:256:0x02e4, code lost:
        
            if ((r9 != null ? r9.mLargeIcon : r7) == null) goto L162;
         */
        /* JADX WARN: Code restructure failed: missing block: B:257:0x02f8, code lost:
        
            android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("isUpdateEntry parent - large Icon: ", r3, "S.S.N.");
         */
        /* JADX WARN: Code restructure failed: missing block: B:258:0x02fd, code lost:
        
            if (r9 == null) goto L190;
         */
        /* JADX WARN: Code restructure failed: missing block: B:259:0x02ff, code lost:
        
            r2.mEntry = r17;
            r2.mInfo = r9;
         */
        /* JADX WARN: Code restructure failed: missing block: B:267:0x02f6, code lost:
        
            if (r10.equals(r9 != null ? r9.mLargeIcon : r7) == false) goto L170;
         */
        /* JADX WARN: Code restructure failed: missing block: B:283:0x0362, code lost:
        
            if ((r9 != null ? r9.mContentView : r7) != null) goto L190;
         */
        /* JADX WARN: Code restructure failed: missing block: B:60:0x01e3, code lost:
        
            if (r2.mDeviceModel.isNotShwonNotificationState(r2.mSelectNotificationInfo.mRow.mEntry) == false) goto L92;
         */
        @Override // com.android.systemui.statusbar.notification.collection.inflation.BindEventManager.Listener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onViewBound(com.android.systemui.statusbar.notification.collection.NotificationEntry r17) {
            /*
                Method dump skipped, instructions count: 1274
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.SubscreenNotificationController.AnonymousClass1.onViewBound(com.android.systemui.statusbar.notification.collection.NotificationEntry):void");
        }
    }

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SubscreenNotificationController(android.content.Context r19, com.android.systemui.settings.UserContextProvider r20, com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider r21, dagger.Lazy r22, dagger.Lazy r23, dagger.Lazy r24, com.android.keyguard.KeyguardUpdateMonitor r25, com.android.systemui.util.SettingsHelper r26, com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection r27, com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider r28, com.android.systemui.statusbar.notification.collection.inflation.BindEventManager r29, com.android.systemui.bixby2.controller.NotificationController r30, android.os.UserManager r31, com.android.systemui.statusbar.notification.ConversationNotificationManager r32, java.util.Optional<com.android.wm.shell.bubbles.Bubbles> r33, com.android.systemui.log.LogBuffer r34, com.android.systemui.statusbar.notification.collection.provider.DebugModeFilterProvider r35, com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProvider r36, com.android.systemui.plugins.statusbar.StatusBarStateController r37, com.android.systemui.media.controls.util.MediaFeatureFlag r38, com.android.systemui.statusbar.notification.collection.NotifPipeline r39, com.android.systemui.statusbar.NotificationRemoteInputManager r40) {
        /*
            Method dump skipped, instructions count: 276
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.SubscreenNotificationController.<init>(android.content.Context, com.android.systemui.settings.UserContextProvider, com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider, dagger.Lazy, dagger.Lazy, dagger.Lazy, com.android.keyguard.KeyguardUpdateMonitor, com.android.systemui.util.SettingsHelper, com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection, com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider, com.android.systemui.statusbar.notification.collection.inflation.BindEventManager, com.android.systemui.bixby2.controller.NotificationController, android.os.UserManager, com.android.systemui.statusbar.notification.ConversationNotificationManager, java.util.Optional, com.android.systemui.log.LogBuffer, com.android.systemui.statusbar.notification.collection.provider.DebugModeFilterProvider, com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProvider, com.android.systemui.plugins.statusbar.StatusBarStateController, com.android.systemui.media.controls.util.MediaFeatureFlag, com.android.systemui.statusbar.notification.collection.NotifPipeline, com.android.systemui.statusbar.NotificationRemoteInputManager):void");
    }

    public final void hideDetailNotif() {
        NotificationEntry notificationEntry;
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter;
        SubscreenDeviceModelParent subscreenDeviceModelParent = this.mDeviceModel;
        SubscreenSubRoomNotification subRoomNotification = subscreenDeviceModelParent != null ? subscreenDeviceModelParent.getSubRoomNotification() : null;
        if (!(subRoomNotification instanceof SubscreenSubRoomNotification)) {
            subRoomNotification = null;
        }
        SubscreenNotificationInfo subscreenNotificationInfo = (subRoomNotification == null || (subscreenNotificationDetailAdapter = subRoomNotification.mNotificationDetailAdapter) == null) ? null : subscreenNotificationDetailAdapter.mSelectNotificationInfo;
        if (subscreenNotificationInfo != null) {
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("   hide recyclerview ", subscreenNotificationInfo.mKey, Reflection.getOrCreateKotlinClass(SubscreenQuickReplyCoordinator.class).getSimpleName());
            ExpandableNotificationRow expandableNotificationRow = subscreenNotificationInfo.mRow;
            if (expandableNotificationRow != null && (notificationEntry = expandableNotificationRow.mEntry) != null) {
                notificationEntry.mIsGhost = false;
            }
            PendingIntent pendingIntent = subscreenNotificationInfo.mRemoteInputActionIntent;
            if (pendingIntent != null) {
                pendingIntent.unregisterCancelListener(this.mRemoteInputCancelListener);
            }
            Iterator it = this.subscreenStateListenerList.iterator();
            while (it.hasNext()) {
                ((SubscreenQuickReplyCoordinator$registerSubscreenStateChangeListener$1) it.next()).onHideDetail(subscreenNotificationInfo.mKey);
            }
            SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter2 = subRoomNotification != null ? subRoomNotification.mNotificationDetailAdapter : null;
            if (subscreenNotificationDetailAdapter2 == null) {
                return;
            }
            subscreenNotificationDetailAdapter2.mSelectNotificationInfo = null;
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public final void onEntryAdded(NotificationEntry notificationEntry) {
        SubscreenDeviceModelParent subscreenDeviceModelParent = this.mDeviceModel;
        if (subscreenDeviceModelParent == null || !subscreenDeviceModelParent.isSubScreen()) {
            return;
        }
        StringBuilder sb = new StringBuilder("entryAdded parent : ");
        String str = notificationEntry.mKey;
        ExifInterface$$ExternalSyntheticOutline0.m(sb, str, "S.S.N.");
        if (subscreenDeviceModelParent.mIsReplyNotification) {
            subscreenDeviceModelParent.mIsReplyNotification = false;
        }
        if (subscreenDeviceModelParent.isProper(notificationEntry, false)) {
            Log.d("S.S.N.", "entryAdded - add popup key");
            subscreenDeviceModelParent.showPopupEntryKeySet.add(str);
            notificationEntry.interruption = true;
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
        NotificationActivityStarter notificationActivityStarter;
        StatusBarNotificationActivityStarter statusBarNotificationActivityStarter;
        NotificationEntry notificationEntry2;
        SubscreenDeviceModelParent subscreenDeviceModelParent = this.mDeviceModel;
        if (subscreenDeviceModelParent != null) {
            StringBuilder sb = new StringBuilder("onEntryRemoved parent : ");
            String str = notificationEntry.mKey;
            ExifInterface$$ExternalSyntheticOutline0.m(sb, str, "S.S.N.");
            subscreenDeviceModelParent.showPopupEntryKeySet.remove(str);
            if (NotiRune.NOTI_SUBSCREEN_PENDING_CALL_FULLSCRREN_INTENT && (notificationActivityStarter = subscreenDeviceModelParent.mNotificationActivityStarter) != null && (notificationEntry2 = (statusBarNotificationActivityStarter = (StatusBarNotificationActivityStarter) notificationActivityStarter).mPendingFullscreenEntry) != null && notificationEntry2.mKey.equals(str)) {
                statusBarNotificationActivityStarter.mPendingFullscreenEntry = null;
            }
            if (subscreenDeviceModelParent.isSubScreen()) {
                int notifyListAdapterItemRemoved = subscreenDeviceModelParent.notifyListAdapterItemRemoved(notificationEntry);
                int notifyGroupAdapterItemRemoved = subscreenDeviceModelParent.notifyGroupAdapterItemRemoved(notificationEntry);
                subscreenDeviceModelParent.mMainListArrayHashMap.remove(str);
                subscreenDeviceModelParent.mMainListAddEntryHashMap.remove(str);
                subscreenDeviceModelParent.mMainListUpdateItemHashMap.remove(str);
                StringBuilder sb2 = new StringBuilder("onEntryRemoved parent - remove List index : ");
                sb2.append(notifyListAdapterItemRemoved);
                sb2.append(", group index : ");
                RecyclerView$$ExternalSyntheticOutline0.m(notifyGroupAdapterItemRemoved, "S.S.N.", sb2);
                if (notifyListAdapterItemRemoved >= 0) {
                    subscreenDeviceModelParent.mIsNotificationRemoved = true;
                    subscreenDeviceModelParent.mMainListRemoveEntryHashMap.put(str, notificationEntry);
                }
                SubscreenSubRoomNotification subscreenSubRoomNotification = subscreenDeviceModelParent.mSubRoomNotification;
                if (subscreenSubRoomNotification != null) {
                    subscreenSubRoomNotification.updateNotificationState(notificationEntry, 1);
                }
                if (subscreenDeviceModelParent.mFullScreenIntentEntries.get(str) != null) {
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("REMOVE fullscreenIntent notification - ", str, "S.S.N.");
                }
                subscreenDeviceModelParent.dismissImmediately(notificationEntry);
                subscreenDeviceModelParent.removeSmartReplyHashMap(str);
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public final void onEntryUpdated(NotificationEntry notificationEntry) {
        SubscreenDeviceModelParent subscreenDeviceModelParent = this.mDeviceModel;
        if (subscreenDeviceModelParent == null || !subscreenDeviceModelParent.isSubScreen()) {
            return;
        }
        StringBuilder sb = new StringBuilder("entryUpdated parent : ");
        String str = notificationEntry.mKey;
        ExifInterface$$ExternalSyntheticOutline0.m(sb, str, "S.S.N.");
        if (subscreenDeviceModelParent.mIsReplyNotification) {
            subscreenDeviceModelParent.mIsReplyNotification = false;
        }
        notificationEntry.interruption = true;
        if (subscreenDeviceModelParent.isProper(notificationEntry, true)) {
            Log.d("S.S.N.", "entryUpdated - add popup key");
            subscreenDeviceModelParent.showPopupEntryKeySet.add(str);
        }
    }

    public final void onFoldStateChanged(boolean z) {
        SubscreenDeviceModelParent subscreenDeviceModelParent;
        if (NotiRune.NOTI_SUBSCREEN_GHOST_NOTIFICATION && !z && (subscreenDeviceModelParent = this.mDeviceModel) != null) {
            subscreenDeviceModelParent.hideDetailNotification();
        }
        SubscreenDeviceModelParent subscreenDeviceModelParent2 = this.mDeviceModel;
        if (subscreenDeviceModelParent2 != null) {
            subscreenDeviceModelParent2.foldStateChanged(z);
        }
    }

    public final void replyNotification(String str, String str2) {
        SubscreenDeviceModelParent.MainListHashMapItem mainListHashMapItem;
        SubscreenSubRoomNotification subscreenSubRoomNotification;
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter;
        SubscreenNotificationInfo subscreenNotificationInfo;
        String str3;
        SubscreenSubRoomNotification subscreenSubRoomNotification2;
        SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter;
        SubscreenNotificationInfoManager subscreenNotificationInfoManager;
        PendingIntent pendingIntent;
        ExpandableNotificationRow expandableNotificationRow;
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter2;
        SubscreenDeviceModelParent subscreenDeviceModelParent = this.mDeviceModel;
        Integer num = null;
        SubscreenSubRoomNotification subRoomNotification = subscreenDeviceModelParent != null ? subscreenDeviceModelParent.getSubRoomNotification() : null;
        if (!(subRoomNotification instanceof SubscreenSubRoomNotification)) {
            subRoomNotification = null;
        }
        SubscreenNotificationInfo subscreenNotificationInfo2 = (subRoomNotification == null || (subscreenNotificationDetailAdapter2 = subRoomNotification.mNotificationDetailAdapter) == null) ? null : subscreenNotificationDetailAdapter2.mSelectNotificationInfo;
        NotificationEntry notificationEntry = (subscreenNotificationInfo2 == null || (expandableNotificationRow = subscreenNotificationInfo2.mRow) == null) ? null : expandableNotificationRow.mEntry;
        if (NotiRune.NOTI_SUBSCREEN_GHOST_NOTIFICATION && useHistory(notificationEntry)) {
            if (notificationEntry != null && !notificationEntry.mIsGhost) {
                notificationEntry.mIsGhost = true;
                ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("   "), notificationEntry.mKey, " will be ghost ", Reflection.getOrCreateKotlinClass(SubscreenQuickReplyCoordinator.class).getSimpleName());
            }
            if (subscreenNotificationInfo2 != null && (pendingIntent = subscreenNotificationInfo2.mRemoteInputActionIntent) != null) {
                pendingIntent.addCancelListener(this.context.getMainExecutor(), this.mRemoteInputCancelListener);
            }
            Iterator it = ((ArrayList) this.subscreenStateListenerList).iterator();
            while (it.hasNext()) {
                ((SubscreenQuickReplyCoordinator$registerSubscreenStateChangeListener$1) it.next()).onReply(notificationEntry);
            }
        }
        SubscreenDeviceModelParent subscreenDeviceModelParent2 = this.mDeviceModel;
        if (subscreenDeviceModelParent2 != null) {
            subscreenDeviceModelParent2.mIsReplyNotification = true;
        }
        if (subscreenDeviceModelParent2 != null && (mainListHashMapItem = (SubscreenDeviceModelParent.MainListHashMapItem) subscreenDeviceModelParent2.mMainListArrayHashMap.get(str)) != null) {
            NotificationEntry notificationEntry2 = mainListHashMapItem.mEntry;
            Boolean valueOf = notificationEntry2 != null ? Boolean.valueOf(notificationEntry2.mRanking.canBubble()) : null;
            Intrinsics.checkNotNull(valueOf);
            if (valueOf.booleanValue()) {
                Log.d("S.S.N.", "hideDetailAdapterAfterBubbleReply parent - Entry  : " + notificationEntry2.mKey);
                if (subscreenDeviceModelParent2.isShownGroup()) {
                    SubscreenSubRoomNotification subscreenSubRoomNotification3 = subscreenDeviceModelParent2.mSubRoomNotification;
                    if (subscreenSubRoomNotification3 != null && (subscreenNotificationInfoManager = subscreenSubRoomNotification3.mNotificationInfoManager) != null) {
                        num = Integer.valueOf(subscreenNotificationInfoManager.removeGroupDataArrayItem(notificationEntry2));
                    }
                    if (num != null && num.intValue() >= 0 && (subscreenSubRoomNotification2 = subscreenDeviceModelParent2.mSubRoomNotification) != null && (subscreenNotificationGroupAdapter = subscreenSubRoomNotification2.mNotificationGroupAdapter) != null) {
                        subscreenNotificationGroupAdapter.notifyItemRemoved(num.intValue());
                    }
                } else {
                    subscreenDeviceModelParent2.notifyListAdapterItemRemoved(notificationEntry2);
                }
                if (NotiRune.NOTI_SUBSCREEN_GHOST_NOTIFICATION && subscreenDeviceModelParent2.isShownDetail() && (subscreenSubRoomNotification = subscreenDeviceModelParent2.mSubRoomNotification) != null && (subscreenNotificationDetailAdapter = subscreenSubRoomNotification.mNotificationDetailAdapter) != null && (subscreenNotificationInfo = subscreenNotificationDetailAdapter.mSelectNotificationInfo) != null && (str3 = subscreenNotificationInfo.mKey) != null && str3.equals(notificationEntry2.mKey)) {
                    if (subscreenDeviceModelParent2.mController.useHistory(notificationEntry2)) {
                        subscreenDeviceModelParent2.hideDetailNotification();
                    }
                    subscreenDeviceModelParent2.mIsUpdatedAllMainList = false;
                }
                subscreenDeviceModelParent2.mBubbleReplyEntry = notificationEntry2;
                subscreenDeviceModelParent2.removeMainHashItem(notificationEntry2);
            }
        }
        this.notificationController.replyNotification(str, str2);
    }

    public final void requestDozeState(int i, boolean z) {
        PluginAODManager pluginAODManager = (PluginAODManager) this.pluginAODManagerLazy.get();
        PluginAOD pluginAOD = pluginAODManager.mAODPlugin;
        if (pluginAOD != null) {
            pluginAOD.requestMODState(i, z);
            return;
        }
        PluginClockPack pluginClockPack = pluginAODManager.mClockPackPlugin;
        if (pluginClockPack != null) {
            pluginClockPack.requestMODState(i, z);
        }
    }

    public final boolean shouldFilterOut(NotificationEntry notificationEntry) {
        if (this.debugModeFilterProvider.allowedPackages.isEmpty() ? false : !r0.allowedPackages.contains(notificationEntry.mSbn.getPackageName())) {
            return true;
        }
        boolean z = this.keyguardUpdateMonitor.mDeviceProvisioned;
        if (!z) {
            StatusBarNotification statusBarNotification = notificationEntry.mSbn;
            if (z || statusBarNotification.getNotification().extras.getBoolean("android.allowDuringSetup")) {
                return true;
            }
        }
        if (!((NotificationLockscreenUserManagerImpl) ((NotificationLockscreenUserManager) Dependency.sDependency.getDependencyInner(NotificationLockscreenUserManager.class))).isCurrentProfile(notificationEntry.mSbn.getUserId()) || ((KeyguardNotificationVisibilityProviderImpl) this.keyguardNotificationVisibilityProvider).shouldHideNotification(notificationEntry)) {
            return true;
        }
        if ((this.statusBarStateController.isDozing() && notificationEntry.shouldSuppressVisualEffect(128)) || notificationEntry.shouldSuppressVisualEffect(256) || notificationEntry.mRanking.isSuspended()) {
            return true;
        }
        if (Utils.useQsMediaPlayer(this.mediaFeatureFlag.context)) {
            MediaDataManager.Companion companion = MediaDataManager.Companion;
            StatusBarNotification statusBarNotification2 = notificationEntry.mSbn;
            companion.getClass();
            if (statusBarNotification2.getNotification().isMediaNotification()) {
                return true;
            }
        }
        return false;
    }

    public final boolean useHistory(NotificationEntry notificationEntry) {
        NotificationManager notificationManager;
        if (notificationEntry == null) {
            return false;
        }
        List list = null;
        if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isNotificationHistoryEnabled() && (notificationManager = (NotificationManager) this.context.getSystemService(NotificationManager.class)) != null) {
            list = notificationManager.semGetNotificationHistoryForPackage(this.context.getPackageName(), this.context.getAttributionTag(), notificationEntry.mSbn.getUserId(), notificationEntry.mSbn.getPackageName(), notificationEntry.mSbn.getKey(), 1);
        }
        return (list != null ? list.size() : 0) > 0;
    }

    public final void onTableModeChanged(boolean z) {
    }
}
