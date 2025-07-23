package com.android.systemui.statusbar.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.ArraySet;
import android.util.Log;
import android.view.WindowManager;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.bixby2.controller.NotificationController;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper;
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
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManagerImpl;
import com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SubscreenNotificationController implements NotifCollectionListener, SemWindowManager.FoldStateListener {
    public final Optional bubblesOptional;
    public final Context context;
    public final ConversationNotificationManager conversationNotificationManager;
    public final DebugModeFilterProvider debugModeFilterProvider;
    public final FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper;
    public final KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final SubscreenDeviceModelParent mDeviceModel;
    public final SubscreenNotificationController$mDeviceStateCallback$1 mDeviceStateCallback;
    public final SubscreenNotificationController$mRemoteInputCancelListener$1 mRemoteInputCancelListener;
    public final MediaFeatureFlag mediaFeatureFlag;
    public final NotifPipeline notifPipeline;
    public final NotificationController notificationController;
    public final NotificationStackScrollLayoutController notificationStackScrollLayoutController;
    public boolean panelExpanded;
    public final Lazy pluginAODManagerLazy;
    public final NotificationRemoteInputManager remoteInputManager;
    public SubscreenNotificationReplyActivity replyActivity;
    private final SettingsHelper settingsHelper;
    public final StatusBarStateController statusBarStateController;
    public final List subscreenStateListenerList;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.notification.SubscreenNotificationController$1, reason: invalid class name */
    public final /* synthetic */ class AnonymousClass1 implements BindEventManager.Listener, FunctionAdapter {
        public AnonymousClass1() {
        }

        public final boolean equals(Object obj) {
            if ((obj instanceof BindEventManager.Listener) && (obj instanceof FunctionAdapter)) {
                return Intrinsics.areEqual(getFunctionDelegate(), ((FunctionAdapter) obj).getFunctionDelegate());
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

        /* JADX WARN: Code restructure failed: missing block: B:242:0x02d2, code lost:
        
            if ((r9 != null ? r9.mLargeIcon : r7) == null) goto L159;
         */
        /* JADX WARN: Code restructure failed: missing block: B:243:0x02e6, code lost:
        
            android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("isUpdateEntry parent - large Icon: ", r3, "S.S.N.");
         */
        /* JADX WARN: Code restructure failed: missing block: B:244:0x02eb, code lost:
        
            if (r9 == null) goto L187;
         */
        /* JADX WARN: Code restructure failed: missing block: B:245:0x02ed, code lost:
        
            r2.mEntry = r17;
            r2.mInfo = r9;
         */
        /* JADX WARN: Code restructure failed: missing block: B:253:0x02e4, code lost:
        
            if (r10.equals(r9 != null ? r9.mLargeIcon : r7) == false) goto L167;
         */
        /* JADX WARN: Code restructure failed: missing block: B:269:0x032a, code lost:
        
            if ((r9 != null ? r9.mContentView : r7) != null) goto L187;
         */
        /* JADX WARN: Code restructure failed: missing block: B:62:0x01e9, code lost:
        
            if (r2.mDeviceModel.isNotShwonNotificationState(r2.mSelectNotificationInfo.mRow.mEntry) == false) goto L98;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:56:0x01cc  */
        /* JADX WARN: Removed duplicated region for block: B:59:0x01d5  */
        /* JADX WARN: Type inference failed for: r10v22, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r10v23, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r10v3 */
        /* JADX WARN: Type inference failed for: r10v4, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r10v6 */
        /* JADX WARN: Type inference failed for: r10v7, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r11v0 */
        /* JADX WARN: Type inference failed for: r11v1, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r11v11, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r11v12, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r11v2 */
        /* JADX WARN: Type inference failed for: r11v3, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.statusbar.notification.SubscreenDeviceModelParent] */
        /* JADX WARN: Type inference failed for: r2v29 */
        /* JADX WARN: Type inference failed for: r2v30, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r2v37, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r2v64 */
        /* JADX WARN: Type inference failed for: r2v65, types: [java.lang.Boolean, java.lang.Object] */
        /* JADX WARN: Type inference failed for: r2v70 */
        /* JADX WARN: Type inference failed for: r2v71, types: [java.lang.Boolean, java.lang.Object] */
        /* JADX WARN: Type inference failed for: r2v76, types: [java.lang.Boolean] */
        /* JADX WARN: Type inference failed for: r2v78, types: [java.lang.Boolean] */
        /* JADX WARN: Type inference failed for: r2v83 */
        /* JADX WARN: Type inference failed for: r2v84, types: [java.lang.Object] */
        /* JADX WARN: Type inference failed for: r2v86 */
        /* JADX WARN: Type inference failed for: r2v87, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r2v90, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r7v2, types: [android.view.View] */
        /* JADX WARN: Type inference failed for: r7v43 */
        /* JADX WARN: Type inference failed for: r7v44 */
        /* JADX WARN: Type inference failed for: r7v45 */
        /* JADX WARN: Type inference failed for: r9v3 */
        /* JADX WARN: Type inference failed for: r9v4, types: [com.android.systemui.statusbar.notification.SubscreenNotificationInfo] */
        /* JADX WARN: Type inference failed for: r9v6, types: [com.android.systemui.statusbar.notification.SubscreenNotificationInfo] */
        @Override // com.android.systemui.statusbar.notification.collection.inflation.BindEventManager.Listener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onViewBound(com.android.systemui.statusbar.notification.collection.NotificationEntry r17) {
            /*
                Method dump skipped, instructions count: 1243
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.SubscreenNotificationController.AnonymousClass1.onViewBound(com.android.systemui.statusbar.notification.collection.NotificationEntry):void");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0126  */
    /* JADX WARN: Type inference failed for: r0v7, types: [android.hardware.devicestate.DeviceStateManager$DeviceStateCallback, com.android.systemui.statusbar.notification.SubscreenNotificationController$mDeviceStateCallback$1] */
    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.systemui.statusbar.notification.SubscreenNotificationController$mRemoteInputCancelListener$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SubscreenNotificationController(android.content.Context r18, com.android.systemui.settings.UserContextProvider r19, com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider r20, dagger.Lazy r21, dagger.Lazy r22, dagger.Lazy r23, com.android.keyguard.KeyguardUpdateMonitor r24, com.android.systemui.util.SettingsHelper r25, com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection r26, com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider r27, com.android.systemui.statusbar.notification.collection.inflation.BindEventManager r28, com.android.systemui.bixby2.controller.NotificationController r29, android.os.UserManager r30, com.android.systemui.statusbar.notification.ConversationNotificationManager r31, java.util.Optional<com.android.wm.shell.bubbles.Bubbles> r32, com.android.systemui.log.LogBuffer r33, com.android.systemui.statusbar.notification.collection.provider.DebugModeFilterProvider r34, com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProvider r35, com.android.systemui.plugins.statusbar.StatusBarStateController r36, com.android.systemui.media.controls.util.MediaFeatureFlag r37, com.android.systemui.statusbar.notification.collection.NotifPipeline r38, com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r39, com.android.systemui.statusbar.NotificationRemoteInputManager r40, com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper r41) {
        /*
            Method dump skipped, instructions count: 312
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.SubscreenNotificationController.<init>(android.content.Context, com.android.systemui.settings.UserContextProvider, com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider, dagger.Lazy, dagger.Lazy, dagger.Lazy, com.android.keyguard.KeyguardUpdateMonitor, com.android.systemui.util.SettingsHelper, com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection, com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider, com.android.systemui.statusbar.notification.collection.inflation.BindEventManager, com.android.systemui.bixby2.controller.NotificationController, android.os.UserManager, com.android.systemui.statusbar.notification.ConversationNotificationManager, java.util.Optional, com.android.systemui.log.LogBuffer, com.android.systemui.statusbar.notification.collection.provider.DebugModeFilterProvider, com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProvider, com.android.systemui.plugins.statusbar.StatusBarStateController, com.android.systemui.media.controls.util.MediaFeatureFlag, com.android.systemui.statusbar.notification.collection.NotifPipeline, com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController, com.android.systemui.statusbar.NotificationRemoteInputManager, com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper):void");
    }

    public final int getSubScreenCardWidth(Context context) {
        SubscreenDeviceModelParent subscreenDeviceModelParent = this.mDeviceModel;
        if (subscreenDeviceModelParent == null) {
            return 0;
        }
        int i = subscreenDeviceModelParent.largeSubScreenCardWidth;
        if (i > 0) {
            return i;
        }
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_list_horizontal_margin_b5);
        WindowManager windowManager = subscreenDeviceModelParent.mWindowManager;
        if (windowManager == null) {
            windowManager = (WindowManager) context.getSystemService("window");
        }
        return windowManager.getCurrentWindowMetrics().getBounds().width() - (dimensionPixelSize * 2);
    }

    public final void hideDetailNotif() {
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter;
        NotificationEntry notificationEntry;
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter2;
        SubscreenDeviceModelParent subscreenDeviceModelParent = this.mDeviceModel;
        SubscreenSubRoomNotification subRoomNotification = subscreenDeviceModelParent != null ? subscreenDeviceModelParent.getSubRoomNotification() : null;
        if (subRoomNotification == null) {
            subRoomNotification = null;
        }
        SubscreenNotificationInfo subscreenNotificationInfo = (subRoomNotification == null || (subscreenNotificationDetailAdapter2 = subRoomNotification.mNotificationDetailAdapter) == null) ? null : subscreenNotificationDetailAdapter2.mSelectNotificationInfo;
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
            if (subRoomNotification == null || (subscreenNotificationDetailAdapter = subRoomNotification.mNotificationDetailAdapter) == null) {
                return;
            }
            subscreenNotificationDetailAdapter.mSelectNotificationInfo = null;
        }
    }

    public final boolean isZenMode() {
        return ((ZenModeControllerImpl) this.notificationStackScrollLayoutController.mZenModeController).mZenMode == 1;
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public final void onEntryAdded(NotificationEntry notificationEntry) {
        SubscreenDeviceModelParent subscreenDeviceModelParent = this.mDeviceModel;
        if (subscreenDeviceModelParent == null || !subscreenDeviceModelParent.isSubScreen()) {
            return;
        }
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("entryAdded parent : ", notificationEntry.mKey, "S.S.N.");
        if (subscreenDeviceModelParent.mIsReplyNotification) {
            subscreenDeviceModelParent.mIsReplyNotification = false;
        }
        if (subscreenDeviceModelParent.isProper(notificationEntry, false)) {
            Log.d("S.S.N.", "entryAdded - add popup key");
            subscreenDeviceModelParent.showPopupEntryKeySet.add(notificationEntry.mKey);
            notificationEntry.interruption = true;
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
        SubscreenNotificationListAdapter subscreenNotificationListAdapter;
        SubscreenNotificationListAdapter subscreenNotificationListAdapter2;
        NotificationActivityStarter notificationActivityStarter;
        StatusBarNotificationActivityStarter statusBarNotificationActivityStarter;
        NotificationEntry notificationEntry2;
        SubscreenDeviceModelParent subscreenDeviceModelParent = this.mDeviceModel;
        if (subscreenDeviceModelParent != null) {
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("onEntryRemoved parent : ", notificationEntry.mKey, "S.S.N.");
            ArraySet arraySet = subscreenDeviceModelParent.showPopupEntryKeySet;
            String str = notificationEntry.mKey;
            arraySet.remove(str);
            if (NotiRune.NOTI_SUBSCREEN_PENDING_CALL_FULLSCRREN_INTENT && (notificationActivityStarter = subscreenDeviceModelParent.mNotificationActivityStarter) != null && (notificationEntry2 = (statusBarNotificationActivityStarter = (StatusBarNotificationActivityStarter) notificationActivityStarter).mPendingFullscreenEntry) != null && notificationEntry2.mKey.equals(str)) {
                statusBarNotificationActivityStarter.mPendingFullscreenEntry = null;
            }
            if (subscreenDeviceModelParent.isSubScreen()) {
                int notifyListAdapterItemRemoved = subscreenDeviceModelParent.notifyListAdapterItemRemoved(notificationEntry);
                int notifyGroupAdapterItemRemoved = subscreenDeviceModelParent.notifyGroupAdapterItemRemoved(notificationEntry);
                subscreenDeviceModelParent.mMainListArrayHashMap.remove(str);
                subscreenDeviceModelParent.mMainListAddEntryHashMap.remove(str);
                subscreenDeviceModelParent.mMainListUpdateItemHashMap.remove(str);
                StringBuilder sb = new StringBuilder("onEntryRemoved parent - remove List index : ");
                sb.append(notifyListAdapterItemRemoved);
                sb.append(", group index : ");
                RecyclerView$$ExternalSyntheticOutline0.m(notifyGroupAdapterItemRemoved, "S.S.N.", sb);
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
                SubscreenSubRoomNotification subscreenSubRoomNotification2 = subscreenDeviceModelParent.mSubRoomNotification;
                if (subscreenSubRoomNotification2 != null && subscreenSubRoomNotification2.mIsInNotiRoom && notificationEntry.isOngoingActivity()) {
                    GroupMembershipManager groupMembershipManager = subscreenDeviceModelParent.mGroupMembershipManager;
                    if ((groupMembershipManager != null ? ((GroupMembershipManagerImpl) groupMembershipManager).getGroupSummary(notificationEntry) : null) != null) {
                        SubscreenSubRoomNotification subscreenSubRoomNotification3 = subscreenDeviceModelParent.mSubRoomNotification;
                        if (subscreenSubRoomNotification3 != null && (subscreenNotificationListAdapter2 = subscreenSubRoomNotification3.mNotificationListAdapter) != null) {
                            subscreenNotificationListAdapter2.mIsCustomNotificationUpdated = Boolean.TRUE;
                        }
                        if (subscreenSubRoomNotification3 == null || (subscreenNotificationListAdapter = subscreenSubRoomNotification3.mNotificationListAdapter) == null) {
                            return;
                        }
                        subscreenNotificationListAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public final void onEntryUpdated(NotificationEntry notificationEntry) {
        SubscreenDeviceModelParent subscreenDeviceModelParent = this.mDeviceModel;
        if (subscreenDeviceModelParent == null || !subscreenDeviceModelParent.isSubScreen()) {
            return;
        }
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("entryUpdated parent : ", notificationEntry.mKey, "S.S.N.");
        if (subscreenDeviceModelParent.mIsReplyNotification) {
            subscreenDeviceModelParent.mIsReplyNotification = false;
        }
        notificationEntry.interruption = true;
        if (subscreenDeviceModelParent.isProper(notificationEntry, true)) {
            Log.d("S.S.N.", "entryUpdated - add popup key");
            subscreenDeviceModelParent.showPopupEntryKeySet.add(notificationEntry.mKey);
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
        if (subRoomNotification == null) {
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
            ArrayList arrayList = (ArrayList) this.subscreenStateListenerList;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((SubscreenQuickReplyCoordinator$registerSubscreenStateChangeListener$1) obj).onReply(notificationEntry);
            }
        }
        SubscreenDeviceModelParent subscreenDeviceModelParent2 = this.mDeviceModel;
        if (subscreenDeviceModelParent2 != null) {
            subscreenDeviceModelParent2.mIsReplyNotification = true;
        }
        if (subscreenDeviceModelParent2 != null && (mainListHashMapItem = (SubscreenDeviceModelParent.MainListHashMapItem) subscreenDeviceModelParent2.mMainListArrayHashMap.get(str)) != null) {
            NotificationEntry notificationEntry2 = mainListHashMapItem.mEntry;
            Boolean valueOf = notificationEntry2 != null ? Boolean.valueOf(notificationEntry2.mRanking.canBubble()) : null;
            valueOf.getClass();
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
                boolean z = NotiRune.NOTI_SUBSCREEN_GHOST_NOTIFICATION;
                String str4 = notificationEntry2.mKey;
                if (z && subscreenDeviceModelParent2.isShownDetail() && (subscreenSubRoomNotification = subscreenDeviceModelParent2.mSubRoomNotification) != null && (subscreenNotificationDetailAdapter = subscreenSubRoomNotification.mNotificationDetailAdapter) != null && (subscreenNotificationInfo = subscreenNotificationDetailAdapter.mSelectNotificationInfo) != null && (str3 = subscreenNotificationInfo.mKey) != null && str3.equals(str4)) {
                    if (subscreenDeviceModelParent2.mController.useHistory(notificationEntry2)) {
                        subscreenDeviceModelParent2.hideDetailNotification();
                    }
                    subscreenDeviceModelParent2.mIsUpdatedAllMainList = false;
                }
                subscreenDeviceModelParent2.mBubbleReplyEntry = notificationEntry2;
                subscreenDeviceModelParent2.mMainListArrayHashMap.remove(str4);
            }
        }
        SubscreenDeviceModelParent subscreenDeviceModelParent3 = this.mDeviceModel;
        if (subscreenDeviceModelParent3 != null) {
            subscreenDeviceModelParent3.hideSmartReplyErrorMessage();
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
        if (this.debugModeFilterProvider.shouldFilterOut(notificationEntry)) {
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
        if (!Utils.useQsMediaPlayer(this.mediaFeatureFlag.context)) {
            return false;
        }
        MediaDataManager.Companion companion = MediaDataManager.Companion;
        StatusBarNotification statusBarNotification2 = notificationEntry.mSbn;
        companion.getClass();
        return statusBarNotification2.getNotification().isMediaNotification();
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
