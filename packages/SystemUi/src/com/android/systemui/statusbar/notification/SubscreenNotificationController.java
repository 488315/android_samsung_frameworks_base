package com.android.systemui.statusbar.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.hardware.devicestate.DeviceStateManager;
import android.os.Handler;
import android.os.UserManager;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.AbstractC0689x6838b71d;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.wm.shell.bubbles.Bubbles;
import com.android.systemui.Dependency;
import com.android.systemui.ForegroundServiceController;
import com.android.systemui.NotiRune;
import com.android.systemui.bixby2.controller.NotificationController;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.keyguard.CustomizationProvider$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.media.controls.pipeline.MediaDataManagerKt;
import com.android.systemui.media.controls.util.MediaFeatureFlag;
import com.android.systemui.plugins.aod.PluginAOD;
import com.android.systemui.plugins.clockpack.PluginClockPack;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.ConversationNotificationManager;
import com.android.systemui.statusbar.notification.SubscreenDeviceModelParent;
import com.android.systemui.statusbar.notification.SubscreenNotificationDetailAdapter;
import com.android.systemui.statusbar.notification.SubscreenNotificationInfo;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda4;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.C2834x89868f76;
import com.android.systemui.statusbar.notification.collection.coordinator.SubscreenQuickReplyCoordinator;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManager;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.provider.DebugModeFilterProvider;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManagerImpl;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.Utils;
import com.samsung.android.view.SemWindowManager;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import kotlin.Function;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SubscreenNotificationController implements NotifCollectionListener, SemWindowManager.FoldStateListener {
    public final Optional bubblesOptional;
    public final Context context;
    public final ConversationNotificationManager conversationNotificationManager;
    public final DebugModeFilterProvider debugModeFilterProvider;
    public final ForegroundServiceController foregroundServiceController;
    public final KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final SubscreenDeviceModelParent mDeviceModel;
    public final SubscreenNotificationController$mDeviceStateCallback$1 mDeviceStateCallback;
    public final SubscreenNotificationController$mRemoteInputCancelListener$1 mRemoteInputCancelListener;
    public final MediaFeatureFlag mediaFeatureFlag;
    public final NotifPipeline notifPipeline;
    public final NotificationController notificationController;
    public final Lazy pluginAODManagerLazy;
    public SubscreenNotificationReplyActivity replyActivity;
    public final StatusBarStateController statusBarStateController;
    public final List subscreenStateListenerList;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.notification.SubscreenNotificationController$1 */
    public final /* synthetic */ class C27401 implements BindEventManager.Listener, FunctionAdapter {
        public C27401() {
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

        /* JADX WARN: Code restructure failed: missing block: B:264:0x02d0, code lost:
        
            if ((r7 != null ? r7.mLargeIcon : r6) == null) goto L160;
         */
        /* JADX WARN: Code restructure failed: missing block: B:292:0x0335, code lost:
        
            if ((r7 != null ? r7.mContentView : r6) != null) goto L191;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:123:0x033c  */
        /* JADX WARN: Removed duplicated region for block: B:267:0x02f0  */
        /* JADX WARN: Removed duplicated region for block: B:278:0x02f5  */
        /* JADX WARN: Removed duplicated region for block: B:51:0x01a9  */
        /* JADX WARN: Removed duplicated region for block: B:60:0x01e9  */
        /* JADX WARN: Removed duplicated region for block: B:62:0x01b1  */
        /* JADX WARN: Type inference failed for: r0v26 */
        /* JADX WARN: Type inference failed for: r0v27, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r0v34, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r0v53 */
        /* JADX WARN: Type inference failed for: r0v54, types: [java.lang.Boolean, java.lang.Object] */
        /* JADX WARN: Type inference failed for: r0v59 */
        /* JADX WARN: Type inference failed for: r0v60, types: [java.lang.Boolean, java.lang.Object] */
        /* JADX WARN: Type inference failed for: r0v65, types: [java.lang.Boolean] */
        /* JADX WARN: Type inference failed for: r0v67, types: [java.lang.Boolean] */
        /* JADX WARN: Type inference failed for: r0v73 */
        /* JADX WARN: Type inference failed for: r0v74, types: [java.lang.Object] */
        /* JADX WARN: Type inference failed for: r0v76 */
        /* JADX WARN: Type inference failed for: r0v77, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r0v80, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r14v2, types: [com.android.systemui.statusbar.notification.SubscreenDeviceModelParent] */
        /* JADX WARN: Type inference failed for: r4v37 */
        /* JADX WARN: Type inference failed for: r4v38, types: [java.lang.String, java.lang.StringBuilder] */
        /* JADX WARN: Type inference failed for: r4v57 */
        /* JADX WARN: Type inference failed for: r6v2, types: [android.view.View] */
        /* JADX WARN: Type inference failed for: r6v26 */
        /* JADX WARN: Type inference failed for: r6v27 */
        /* JADX WARN: Type inference failed for: r6v28 */
        /* JADX WARN: Type inference failed for: r7v17 */
        /* JADX WARN: Type inference failed for: r7v18, types: [com.android.systemui.statusbar.notification.SubscreenNotificationInfo] */
        /* JADX WARN: Type inference failed for: r7v20, types: [com.android.systemui.statusbar.notification.SubscreenNotificationInfo] */
        /* JADX WARN: Type inference failed for: r7v29, types: [com.android.systemui.statusbar.notification.SubscreenDeviceModelParent] */
        /* JADX WARN: Type inference failed for: r8v28, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r8v29, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r8v5 */
        /* JADX WARN: Type inference failed for: r8v6, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r8v8 */
        /* JADX WARN: Type inference failed for: r8v9, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r9v0 */
        /* JADX WARN: Type inference failed for: r9v1, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r9v11, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r9v12, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r9v2 */
        /* JADX WARN: Type inference failed for: r9v3, types: [java.lang.String] */
        @Override // com.android.systemui.statusbar.notification.collection.inflation.BindEventManager.Listener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onViewBound(NotificationEntry notificationEntry) {
            SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter;
            SubscreenNotificationInfo subscreenNotificationInfo;
            SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter2;
            boolean z;
            Icon icon;
            boolean z2;
            SubscreenNotificationInfoManager subscreenNotificationInfoManager;
            NotificationEntry notificationEntry2;
            ?? r0;
            NotificationEntry groupSummary;
            SubscreenNotificationListAdapter subscreenNotificationListAdapter;
            SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter;
            SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter3;
            SubscreenNotificationInfo subscreenNotificationInfo2;
            boolean z3;
            ?? r4;
            ExpandableNotificationRow expandableNotificationRow;
            LinearLayout linearLayout;
            int childCount;
            String str;
            ?? r14 = SubscreenNotificationController.this.mDeviceModel;
            if (r14 != 0) {
                boolean z4 = r14.mIsReplyNotification;
                String str2 = notificationEntry.mKey;
                if (z4) {
                    AbstractC0000x2c234b15.m3m("entryViewBound parent - mIsReplyNotification :", str2, "S.S.N.");
                    return;
                }
                Log.d("S.S.N.", "entryViewBound parent :" + str2);
                boolean isSubScreen = r14.isSubScreen();
                if (r14.notiShowBlocked && isSubScreen) {
                    Log.d("S.S.N.", " entryViewBound : show notification is disabled. not showing List");
                    return;
                }
                if (isSubScreen) {
                    if (r14.isBubbleNotificationSuppressed(notificationEntry)) {
                        Log.d("S.S.N.", "entryViewBound parent - bubble is removed:" + str2);
                        r14.notifyListAdapterItemRemoved(notificationEntry);
                        r14.notifyGroupAdapterItemRemoved(notificationEntry);
                        r14.removeMainHashItem(notificationEntry);
                        return;
                    }
                    if (r14.checkBubbleLastHistoryReply(notificationEntry)) {
                        AbstractC0000x2c234b15.m3m("entryViewBound parent - bubble Reply :", str2, "S.S.N.");
                        return;
                    }
                    SubscreenSubRoomNotification subscreenSubRoomNotification = r14.mSubRoomNotification;
                    if (subscreenSubRoomNotification != null) {
                        subscreenSubRoomNotification.updateNotificationState(notificationEntry, 0);
                    }
                    HashSet hashSet = r14.mNotiKeySet;
                    NotificationEntry notificationEntry3 = null;
                    notificationEntry3 = null;
                    notificationEntry3 = null;
                    notificationEntry3 = null;
                    notificationEntry3 = null;
                    notificationEntry3 = null;
                    if (hashSet.contains(str2)) {
                        SubscreenSubRoomNotification subscreenSubRoomNotification2 = r14.mSubRoomNotification;
                        if (subscreenSubRoomNotification2 != null && subscreenSubRoomNotification2.mIsShownDetail && (subscreenNotificationInfo2 = (subscreenNotificationDetailAdapter3 = subscreenSubRoomNotification2.mNotificationDetailAdapter).mSelectNotificationInfo) != null && subscreenNotificationInfo2.mKey.equals(str2)) {
                            subscreenNotificationDetailAdapter3.mPrevSelectNotificationInfo = subscreenNotificationDetailAdapter3.mSelectNotificationInfo;
                            SubscreenNotificationInfo createItemsData = subscreenNotificationDetailAdapter3.mNotificationInfoManager.createItemsData(notificationEntry.row);
                            subscreenNotificationDetailAdapter3.mSelectNotificationInfo = createItemsData;
                            SubscreenNotificationInfo subscreenNotificationInfo3 = subscreenNotificationDetailAdapter3.mPrevSelectNotificationInfo;
                            if (subscreenNotificationInfo3 != null) {
                                ArrayList arrayList = subscreenNotificationInfo3.mMessageingStyleInfoArray;
                                ArrayList arrayList2 = createItemsData.mMessageingStyleInfoArray;
                                if (arrayList.size() < arrayList2.size()) {
                                    Log.d("SubscreenNotificationDetailAdapter", "isItemUpdateCompleted - size is not max");
                                    subscreenNotificationDetailAdapter3.mPrevSelectNotificationInfo = null;
                                } else if (arrayList.size() == arrayList2.size()) {
                                    for (int size = arrayList2.size() - 1; size >= 0; size--) {
                                        SubscreenNotificationInfo.MessagingStyleInfo messagingStyleInfo = (SubscreenNotificationInfo.MessagingStyleInfo) arrayList.get(size);
                                        SubscreenNotificationInfo.MessagingStyleInfo messagingStyleInfo2 = (SubscreenNotificationInfo.MessagingStyleInfo) arrayList2.get(size);
                                        String str3 = messagingStyleInfo2.mContentText;
                                        if (str3 != null && (str = messagingStyleInfo.mContentText) != null && !str3.equals(str)) {
                                            Log.d("SubscreenNotificationDetailAdapter", "isItemUpdateCompleted - size is max - not match text");
                                        } else if (messagingStyleInfo2.mPostedTime != messagingStyleInfo.mPostedTime) {
                                            Log.d("SubscreenNotificationDetailAdapter", "isItemUpdateCompleted - size is max - not match PostTime");
                                        }
                                    }
                                }
                                z3 = true;
                                StringBuilder sb = new StringBuilder("updateSelectNotificationInfo - mIsSendedQuickReply : ");
                                SubscreenNotificationDetailAdapter.ScrollInfo scrollInfo = subscreenNotificationDetailAdapter3.mScrollInfo;
                                KeyguardCarrierViewController$$ExternalSyntheticOutline0.m63m(sb, scrollInfo.mIsSendedQuickReply, ", isItemUpdateCompleted() : ", z3, "SubscreenNotificationDetailAdapter");
                                if (z3 || !subscreenNotificationDetailAdapter3.mSelectNotificationInfo.mIsMessagingStyle) {
                                    r4 = 0;
                                } else {
                                    if (scrollInfo.mIsSendedQuickReply) {
                                        subscreenNotificationDetailAdapter3.dismissReplyButtons(false);
                                        scrollInfo.mIsSendedQuickReply = false;
                                        scrollInfo.mCompleteItemUpdateReason = 1;
                                    } else {
                                        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter4 = SubscreenNotificationDetailAdapter.this;
                                        View childAt = subscreenNotificationDetailAdapter4.mNotificationRecyclerView.getChildAt(0);
                                        RecyclerView recyclerView = subscreenNotificationDetailAdapter4.mNotificationRecyclerView;
                                        if (recyclerView == null || childAt == null) {
                                            Log.d("SubscreenNotificationDetailAdapter", "setPrevFirstAndLastHistoryInfo - value is null");
                                        } else if (recyclerView.getChildViewHolder(childAt) instanceof SubscreenParentDetailItemViewHolder) {
                                            SubscreenParentDetailItemViewHolder subscreenParentDetailItemViewHolder = (SubscreenParentDetailItemViewHolder) subscreenNotificationDetailAdapter4.mNotificationRecyclerView.getChildViewHolder(childAt);
                                            if (!(subscreenParentDetailItemViewHolder instanceof SubscreenNotificationDetailAdapter.TextViewHolder) && (linearLayout = subscreenParentDetailItemViewHolder.mContentLayout) != null && (childCount = linearLayout.getChildCount()) > 0) {
                                                scrollInfo.mPrevBodyLayoutHeght = subscreenParentDetailItemViewHolder.mBodyLayout.getHeight();
                                                scrollInfo.mPrevHistoryCount = childCount;
                                                LinearLayout linearLayout2 = subscreenParentDetailItemViewHolder.mContentLayout;
                                                scrollInfo.mPrevFirstHistoryView = linearLayout2.getChildAt(0);
                                                scrollInfo.mPrevLastHistoryView = linearLayout2.getChildAt(childCount - 1);
                                                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) scrollInfo.mPrevFirstHistoryView.getLayoutParams();
                                                scrollInfo.mPrevFirstHistoryViewBottomMargin = layoutParams.bottomMargin;
                                                StringBuilder sb2 = new StringBuilder("setPrevFirstAndLastHistoryInfo - prevFirstHisotoryView params.bottomMargin :");
                                                sb2.append(layoutParams.bottomMargin);
                                                sb2.append(", mPrevBodyLayoutHeght :");
                                                RecyclerView$$ExternalSyntheticOutline0.m46m(sb2, scrollInfo.mPrevBodyLayoutHeght, "SubscreenNotificationDetailAdapter");
                                            }
                                        } else {
                                            Log.d("SubscreenNotificationDetailAdapter", "setPrevFirstAndLastHistoryInfo - not SubscreenParentDetailItemViewHolder");
                                        }
                                        scrollInfo.mCompleteItemUpdateReason = 2;
                                    }
                                    r4 = 0;
                                    subscreenNotificationDetailAdapter3.mPrevSelectNotificationInfo = null;
                                }
                                if (subscreenNotificationDetailAdapter3.mIsShownReplyButtonWindow) {
                                    subscreenNotificationDetailAdapter3.notifyItemChanged(0);
                                    subscreenNotificationDetailAdapter3.mUpdatedInfo = false;
                                } else {
                                    subscreenNotificationDetailAdapter3.mUpdatedInfo = true;
                                    subscreenNotificationDetailAdapter3.mDeviceModel.setSmartReplyResultValue(-1, r4, r4);
                                }
                                if (subscreenNotificationDetailAdapter3.mSelectNotificationInfo.mIsMessagingStyle && (!notificationEntry.row.needsRedaction() || !subscreenNotificationDetailAdapter3.mDeviceModel.isNotShwonNotificationState(subscreenNotificationDetailAdapter3.mSelectNotificationInfo.mRow.mEntry))) {
                                    SubscreenNotificationController subscreenNotificationController = (SubscreenNotificationController) Dependency.get(SubscreenNotificationController.class);
                                    subscreenNotificationController.conversationNotificationManager.states.compute(notificationEntry.mKey, ConversationNotificationManager$resetCount$1.INSTANCE);
                                    expandableNotificationRow = notificationEntry.row;
                                    if (expandableNotificationRow != null) {
                                        subscreenNotificationController.conversationNotificationManager.getClass();
                                        ConversationNotificationManager.resetBadgeUi(expandableNotificationRow);
                                    }
                                }
                                notificationEntry3 = r4;
                            }
                            z3 = false;
                            StringBuilder sb3 = new StringBuilder("updateSelectNotificationInfo - mIsSendedQuickReply : ");
                            SubscreenNotificationDetailAdapter.ScrollInfo scrollInfo2 = subscreenNotificationDetailAdapter3.mScrollInfo;
                            KeyguardCarrierViewController$$ExternalSyntheticOutline0.m63m(sb3, scrollInfo2.mIsSendedQuickReply, ", isItemUpdateCompleted() : ", z3, "SubscreenNotificationDetailAdapter");
                            if (z3) {
                            }
                            r4 = 0;
                            if (subscreenNotificationDetailAdapter3.mIsShownReplyButtonWindow) {
                            }
                            if (subscreenNotificationDetailAdapter3.mSelectNotificationInfo.mIsMessagingStyle) {
                                SubscreenNotificationController subscreenNotificationController2 = (SubscreenNotificationController) Dependency.get(SubscreenNotificationController.class);
                                subscreenNotificationController2.conversationNotificationManager.states.compute(notificationEntry.mKey, ConversationNotificationManager$resetCount$1.INSTANCE);
                                expandableNotificationRow = notificationEntry.row;
                                if (expandableNotificationRow != null) {
                                }
                            }
                            notificationEntry3 = r4;
                        }
                        if (r14.isUpdatedRemoteView(notificationEntry.mSbn.getPackageName())) {
                            if (r14.isShownGroup()) {
                                SubscreenSubRoomNotification subscreenSubRoomNotification3 = r14.mSubRoomNotification;
                                if (subscreenSubRoomNotification3 == null || (subscreenNotificationGroupAdapter = subscreenSubRoomNotification3.mNotificationGroupAdapter) == null) {
                                    return;
                                }
                                subscreenNotificationGroupAdapter.notifyDataSetChanged();
                                return;
                            }
                            SubscreenSubRoomNotification subscreenSubRoomNotification4 = r14.mSubRoomNotification;
                            if (subscreenSubRoomNotification4 == null || (subscreenNotificationListAdapter = subscreenSubRoomNotification4.mNotificationListAdapter) == null) {
                                return;
                            }
                            subscreenNotificationListAdapter.notifyDataSetChanged();
                            return;
                        }
                        AbstractC0000x2c234b15.m3m("isUpdateEntry parent : ", str2, "S.S.N.");
                        SubscreenDeviceModelParent.MainListHashMapItem mainListHashMapItem = (SubscreenDeviceModelParent.MainListHashMapItem) r14.mMainListArrayHashMap.get(str2);
                        if (mainListHashMapItem == null) {
                            AbstractC0000x2c234b15.m3m("isUpdateEntry parent - oldEntry is null ", str2, "S.S.N.");
                        } else {
                            SubscreenNotificationInfo subscreenNotificationInfo4 = mainListHashMapItem.mInfo;
                            SubscreenSubRoomNotification subscreenSubRoomNotification5 = r14.mSubRoomNotification;
                            ?? createItemsData2 = (subscreenSubRoomNotification5 == null || (subscreenNotificationInfoManager = subscreenSubRoomNotification5.mNotificationInfoManager) == null) ? notificationEntry3 : subscreenNotificationInfoManager.createItemsData(notificationEntry.row);
                            if (notificationEntry.mSbn.getNotification().isGroupSummary()) {
                                AbstractC0000x2c234b15.m3m("isUpdateEntry parent - Group Sumarry: ", str2, "S.S.N.");
                            } else if (!StringsKt__StringsJVMKt.equals(subscreenNotificationInfo4 != null ? subscreenNotificationInfo4.getTitle() : notificationEntry3, createItemsData2 != null ? createItemsData2.getTitle() : notificationEntry3, false)) {
                                AbstractC0000x2c234b15.m3m("isUpdateEntry parent - text Title: ", str2, "S.S.N.");
                                if (createItemsData2 != null) {
                                    mainListHashMapItem.mEntry = notificationEntry;
                                    mainListHashMapItem.mInfo = createItemsData2;
                                }
                            } else if (!StringsKt__StringsJVMKt.equals(subscreenNotificationInfo4 != null ? subscreenNotificationInfo4.mContent : notificationEntry3, createItemsData2 != null ? createItemsData2.mContent : notificationEntry3, false)) {
                                AbstractC0000x2c234b15.m3m("isUpdateEntry parent - text content: ", str2, "S.S.N.");
                                if (createItemsData2 != null) {
                                    mainListHashMapItem.mEntry = notificationEntry;
                                    mainListHashMapItem.mInfo = createItemsData2;
                                }
                            } else if (Intrinsics.areEqual(subscreenNotificationInfo4 != null ? Long.valueOf(subscreenNotificationInfo4.mWhen) : notificationEntry3, createItemsData2 != null ? Long.valueOf(createItemsData2.mWhen) : notificationEntry3)) {
                                if ((subscreenNotificationInfo4 != null ? subscreenNotificationInfo4.mLargeIcon : notificationEntry3) == null) {
                                }
                                if (subscreenNotificationInfo4 != null && (icon = subscreenNotificationInfo4.mLargeIcon) != null) {
                                    if (!icon.equals(createItemsData2 != null ? createItemsData2.mLargeIcon : notificationEntry3)) {
                                        z = true;
                                        if (!z) {
                                            ?? valueOf = subscreenNotificationInfo4 != null ? Boolean.valueOf(subscreenNotificationInfo4.mIsMessagingStyle) : notificationEntry3;
                                            Intrinsics.checkNotNull(valueOf);
                                            if (valueOf.booleanValue()) {
                                                ?? valueOf2 = createItemsData2 != null ? Boolean.valueOf(createItemsData2.mIsMessagingStyle) : notificationEntry3;
                                                Intrinsics.checkNotNull(valueOf2);
                                                if (valueOf2.booleanValue()) {
                                                    if (subscreenNotificationInfo4.mUnreadMessageCnt != r14.mController.getUnreadCount(createItemsData2.mRow.mEntry)) {
                                                        AbstractC0000x2c234b15.m3m("isUpdateEntry parent - unReadCount: ", str2, "S.S.N.");
                                                    }
                                                }
                                            }
                                        }
                                        AbstractC0000x2c234b15.m3m("isUpdateEntry parent - large Icon: ", str2, "S.S.N.");
                                        if (createItemsData2 != null) {
                                            mainListHashMapItem.mEntry = notificationEntry;
                                            mainListHashMapItem.mInfo = createItemsData2;
                                        }
                                    }
                                }
                                z = false;
                                if (!z) {
                                }
                                AbstractC0000x2c234b15.m3m("isUpdateEntry parent - large Icon: ", str2, "S.S.N.");
                                if (createItemsData2 != null) {
                                }
                            } else {
                                AbstractC0000x2c234b15.m3m("isUpdateEntry parent - when: ", str2, "S.S.N.");
                                if (createItemsData2 != null) {
                                    mainListHashMapItem.mEntry = notificationEntry;
                                    mainListHashMapItem.mInfo = createItemsData2;
                                }
                            }
                            z2 = false;
                            if (z2) {
                                boolean isImportantConversation = notificationEntry.getChannel().isImportantConversation();
                                if (!notificationEntry.isChildInGroup() || r14.isShownGroup() || isImportantConversation) {
                                    notificationEntry2 = notificationEntry;
                                    r0 = str2;
                                } else {
                                    GroupMembershipManager groupMembershipManager = r14.mGroupMembershipManager;
                                    notificationEntry2 = groupMembershipManager != null ? ((GroupMembershipManagerImpl) groupMembershipManager).getGroupSummary(notificationEntry) : notificationEntry3;
                                    Intrinsics.checkNotNull(notificationEntry2);
                                    r0 = (groupMembershipManager == null || (groupSummary = ((GroupMembershipManagerImpl) groupMembershipManager).getGroupSummary(notificationEntry)) == null) ? notificationEntry3 : groupSummary.mKey;
                                    StringBuilder sb4 = new StringBuilder("updateMainListItem isChildGroup : ");
                                    sb4.append(str2);
                                    sb4.append(", addEntry : ");
                                    sb4.append(notificationEntry2);
                                    sb4.append(", key : ");
                                    ExifInterface$$ExternalSyntheticOutline0.m35m(sb4, r0, "S.S.N.");
                                }
                                if (r0 != null) {
                                    r14.mMainListUpdateItemHashMap.put(r0, notificationEntry2);
                                }
                            }
                        }
                        z2 = true;
                        if (z2) {
                        }
                    } else {
                        hashSet.add(str2);
                        if (!notificationEntry.mSbn.getNotification().isGroupSummary()) {
                            r14.mMainListAddEntryHashMap.put(str2, notificationEntry);
                        }
                    }
                    boolean z5 = notificationEntry.mSbn.getNotification().fullScreenIntent != null;
                    ArraySet arraySet = r14.showPopupEntryKeySet;
                    if (!arraySet.contains(str2) && !z5) {
                        AbstractC0000x2c234b15.m3m("return entryViewBound : ", str2, "S.S.N.");
                        return;
                    }
                    arraySet.remove(str2);
                    if (z5) {
                        if (ArraysKt___ArraysKt.contains(new String[]{"com.skt.prod.dialer", "com.samsung.android.incallui"}, notificationEntry.mSbn.getPackageName())) {
                            AbstractC0000x2c234b15.m3m("return call Package : ", str2, "S.S.N.");
                            return;
                        }
                        String str4 = notificationEntry.mSbn.getNotification().category;
                        if (!(Intrinsics.areEqual("call", str4) && notificationEntry.mSbn.getNotification().isStyle(Notification.CallStyle.class)) && !Intrinsics.areEqual("alarm", str4) && notificationEntry.mSbn.isClearable()) {
                            CustomizationProvider$$ExternalSyntheticOutline0.m135m("fullscreenIntent and this category is not supported in subscreen, so return : ", str2, ", category = ", str4, "S.S.N.");
                            return;
                        }
                    }
                    LinkedHashMap linkedHashMap = r14.mFullScreenIntentEntries;
                    if (z5 && linkedHashMap.get(str2) == null) {
                        if (r14.launchFullscreenIntent(notificationEntry)) {
                            return;
                        }
                        Log.d("S.S.N.", "entryViewBound parent - put fullscreenIntent  :" + str2);
                        linkedHashMap.put(str2, notificationEntry);
                    }
                    if (linkedHashMap.get(str2) == null && notificationEntry.mSbn.getNotification().isGroupSummary()) {
                        AbstractC0000x2c234b15.m3m("entryViewBound GroupSummary :", str2, "S.S.N.");
                        return;
                    }
                    if (r14.isShownDetail()) {
                        SubscreenSubRoomNotification subscreenSubRoomNotification6 = r14.mSubRoomNotification;
                        if (((subscreenSubRoomNotification6 == null || (subscreenNotificationDetailAdapter2 = subscreenSubRoomNotification6.mNotificationDetailAdapter) == null) ? notificationEntry3 : subscreenNotificationDetailAdapter2.mSelectNotificationInfo) != null && Intrinsics.areEqual(r14.getTopActivityName(), "com.android.systemui.subscreen.SubHomeActivity")) {
                            SubscreenSubRoomNotification subscreenSubRoomNotification7 = r14.mSubRoomNotification;
                            if (StringsKt__StringsJVMKt.equals((subscreenSubRoomNotification7 == null || (subscreenNotificationDetailAdapter = subscreenSubRoomNotification7.mNotificationDetailAdapter) == null || (subscreenNotificationInfo = subscreenNotificationDetailAdapter.mSelectNotificationInfo) == null) ? notificationEntry3 : subscreenNotificationInfo.mKey, str2, false)) {
                                SubscreenSubRoomNotification subscreenSubRoomNotification8 = r14.mSubRoomNotification;
                                ?? r6 = notificationEntry3;
                                if (subscreenSubRoomNotification8 != null) {
                                    SubscreenRecyclerView subscreenRecyclerView = subscreenSubRoomNotification8.mNotificationRecyclerView;
                                    r6 = notificationEntry3;
                                    if (subscreenRecyclerView != null) {
                                        r6 = subscreenRecyclerView.getChildAt(0);
                                    }
                                }
                                if (r6 != 0) {
                                    int detailAdapterAutoScrollCurrentPositionByReceive = r14.getDetailAdapterAutoScrollCurrentPositionByReceive(r6);
                                    if (detailAdapterAutoScrollCurrentPositionByReceive == 3) {
                                        Log.d("S.S.N.", "entryViewBound scrollCurrentPosition : " + detailAdapterAutoScrollCurrentPositionByReceive + " , key : " + str2);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                    if (r14.isCoverBriefAllowed(notificationEntry)) {
                        AbstractC0000x2c234b15.m3m(" return entryViewBound isCoverBriefAllowed - ", str2, "S.S.N.");
                    } else {
                        r14.makeSubScreenNotification(notificationEntry);
                        r14.showSubscreenNotification();
                    }
                }
            }
        }
    }

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
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x00f7  */
    /* JADX WARN: Type inference failed for: r1v2, types: [android.hardware.devicestate.DeviceStateManager$DeviceStateCallback, com.android.systemui.statusbar.notification.SubscreenNotificationController$mDeviceStateCallback$1] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.statusbar.notification.SubscreenNotificationController$mRemoteInputCancelListener$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public SubscreenNotificationController(Context context, UserContextProvider userContextProvider, NotificationInterruptStateProvider notificationInterruptStateProvider, Lazy lazy, Lazy lazy2, Lazy lazy3, KeyguardUpdateMonitor keyguardUpdateMonitor, SettingsHelper settingsHelper, CommonNotifCollection commonNotifCollection, NotificationVisibilityProvider notificationVisibilityProvider, BindEventManager bindEventManager, NotificationController notificationController, UserManager userManager, ConversationNotificationManager conversationNotificationManager, Optional<Bubbles> optional, LogBuffer logBuffer, DebugModeFilterProvider debugModeFilterProvider, KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider, StatusBarStateController statusBarStateController, ForegroundServiceController foregroundServiceController, MediaFeatureFlag mediaFeatureFlag, NotifPipeline notifPipeline) {
        SubscreenDeviceModelParent subscreenDeviceModelParent;
        SubscreenDeviceModelParent subscreenDeviceModelCover;
        DeviceStateManager deviceStateManager;
        this.context = context;
        this.pluginAODManagerLazy = lazy2;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.notificationController = notificationController;
        this.conversationNotificationManager = conversationNotificationManager;
        this.bubblesOptional = optional;
        this.debugModeFilterProvider = debugModeFilterProvider;
        this.keyguardNotificationVisibilityProvider = keyguardNotificationVisibilityProvider;
        this.statusBarStateController = statusBarStateController;
        this.foregroundServiceController = foregroundServiceController;
        this.mediaFeatureFlag = mediaFeatureFlag;
        this.notifPipeline = notifPipeline;
        SubscreenDeviceModelCreater.Companion.getClass();
        if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH) {
            subscreenDeviceModelCover = new SubscreenDeviceModelB5(context, keyguardUpdateMonitor, settingsHelper, userContextProvider, this, lazy3, commonNotifCollection, logBuffer, notificationInterruptStateProvider, lazy, lazy2, notificationVisibilityProvider, bindEventManager, notificationController, userManager, conversationNotificationManager);
        } else if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_SECOND) {
            subscreenDeviceModelCover = new SubscreenDeviceModelB4(context, keyguardUpdateMonitor, settingsHelper, userContextProvider, this, lazy3, commonNotifCollection, logBuffer, notificationInterruptStateProvider, lazy, lazy2, notificationVisibilityProvider, bindEventManager, notificationController, userManager, conversationNotificationManager);
        } else {
            if (!NotiRune.NOTI_SUBSCREEN_CLEAR_COVER) {
                subscreenDeviceModelParent = null;
                this.mDeviceModel = subscreenDeviceModelParent;
                ?? r1 = new DeviceStateManager.DeviceStateCallback() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationController$mDeviceStateCallback$1
                    public final void onStateChanged(int i) {
                        SubscreenDeviceModelParent subscreenDeviceModelParent2 = SubscreenNotificationController.this.mDeviceModel;
                        if (subscreenDeviceModelParent2 != null) {
                            subscreenDeviceModelParent2.onStateChangedInDeviceStateCallback(i);
                        }
                    }
                };
                this.mDeviceStateCallback = r1;
                ((NotifPipeline) commonNotifCollection).addCollectionListener(this);
                bindEventManager.listeners.addIfAbsent(new C27401());
                SemWindowManager.getInstance().registerFoldStateListener(this, (Handler) null);
                deviceStateManager = (DeviceStateManager) context.getSystemService(DeviceStateManager.class);
                if (deviceStateManager != null) {
                    deviceStateManager.registerCallback(context.getMainExecutor(), (DeviceStateManager.DeviceStateCallback) r1);
                }
                if (subscreenDeviceModelParent != null) {
                    subscreenDeviceModelParent.initialize();
                }
                this.subscreenStateListenerList = new ArrayList();
                this.mRemoteInputCancelListener = new PendingIntent.CancelListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationController$mRemoteInputCancelListener$1
                    public final void onCanceled(PendingIntent pendingIntent) {
                        SubscreenDeviceModelParent subscreenDeviceModelParent2 = SubscreenNotificationController.this.mDeviceModel;
                        if (subscreenDeviceModelParent2 != null) {
                            subscreenDeviceModelParent2.hideDetailNotificationAnimated(300, true);
                        }
                    }
                };
            }
            subscreenDeviceModelCover = new SubscreenDeviceModelCover(context, keyguardUpdateMonitor, settingsHelper, userContextProvider, this, lazy3, commonNotifCollection, logBuffer, notificationInterruptStateProvider, lazy, lazy2, notificationVisibilityProvider, bindEventManager, notificationController, userManager, conversationNotificationManager);
        }
        subscreenDeviceModelParent = subscreenDeviceModelCover;
        this.mDeviceModel = subscreenDeviceModelParent;
        ?? r12 = new DeviceStateManager.DeviceStateCallback() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationController$mDeviceStateCallback$1
            public final void onStateChanged(int i) {
                SubscreenDeviceModelParent subscreenDeviceModelParent2 = SubscreenNotificationController.this.mDeviceModel;
                if (subscreenDeviceModelParent2 != null) {
                    subscreenDeviceModelParent2.onStateChangedInDeviceStateCallback(i);
                }
            }
        };
        this.mDeviceStateCallback = r12;
        ((NotifPipeline) commonNotifCollection).addCollectionListener(this);
        bindEventManager.listeners.addIfAbsent(new C27401());
        SemWindowManager.getInstance().registerFoldStateListener(this, (Handler) null);
        deviceStateManager = (DeviceStateManager) context.getSystemService(DeviceStateManager.class);
        if (deviceStateManager != null) {
        }
        if (subscreenDeviceModelParent != null) {
        }
        this.subscreenStateListenerList = new ArrayList();
        this.mRemoteInputCancelListener = new PendingIntent.CancelListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationController$mRemoteInputCancelListener$1
            public final void onCanceled(PendingIntent pendingIntent) {
                SubscreenDeviceModelParent subscreenDeviceModelParent2 = SubscreenNotificationController.this.mDeviceModel;
                if (subscreenDeviceModelParent2 != null) {
                    subscreenDeviceModelParent2.hideDetailNotificationAnimated(300, true);
                }
            }
        };
    }

    public final int getUnreadCount(NotificationEntry notificationEntry) {
        Notification.Builder recoverBuilder = Notification.Builder.recoverBuilder(this.context, notificationEntry.mSbn.getNotification());
        ConversationNotificationManager conversationNotificationManager = this.conversationNotificationManager;
        Object compute = conversationNotificationManager.states.compute(notificationEntry.mKey, new ConversationNotificationManager$getUnreadCount$1(notificationEntry, conversationNotificationManager, recoverBuilder));
        Intrinsics.checkNotNull(compute);
        return ((ConversationNotificationManager.ConversationState) compute).unreadCount;
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
            AbstractC0000x2c234b15.m3m("   hide recyclerview ", subscreenNotificationInfo.mKey, Reflection.getOrCreateKotlinClass(SubscreenQuickReplyCoordinator.class).getSimpleName());
            ExpandableNotificationRow expandableNotificationRow = subscreenNotificationInfo.mRow;
            if (expandableNotificationRow != null && (notificationEntry = expandableNotificationRow.mEntry) != null) {
                notificationEntry.mIsGhost = false;
            }
            PendingIntent pendingIntent = subscreenNotificationInfo.mRemoteInputActionIntent;
            if (pendingIntent != null) {
                pendingIntent.unregisterCancelListener(this.mRemoteInputCancelListener);
            }
            Iterator it = ((ArrayList) this.subscreenStateListenerList).iterator();
            while (it.hasNext()) {
                C2834x89868f76 c2834x89868f76 = (C2834x89868f76) it.next();
                String str = subscreenNotificationInfo.mKey;
                c2834x89868f76.getClass();
                Log.d("SubscreenQuickReplyCoordinator", "   end extension - " + str);
                SubscreenQuickReplyCoordinator.SubscreenQuickReplyExtender subscreenQuickReplyExtender = c2834x89868f76.this$0.mQuickReplyExtender;
                if (str == null) {
                    str = "";
                }
                subscreenQuickReplyExtender.endLifetimeExtension(str);
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
        ExifInterface$$ExternalSyntheticOutline0.m35m(sb, str, "S.S.N.");
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
            ExifInterface$$ExternalSyntheticOutline0.m35m(sb, str, "S.S.N.");
            subscreenDeviceModelParent.mNotiKeySet.remove(str);
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
                RecyclerView$$ExternalSyntheticOutline0.m46m(sb2, notifyGroupAdapterItemRemoved, "S.S.N.");
                if (notifyListAdapterItemRemoved >= 0) {
                    subscreenDeviceModelParent.mIsNotificationRemoved = true;
                    subscreenDeviceModelParent.mMainListRemoveEntryHashMap.put(str, notificationEntry);
                }
                SubscreenSubRoomNotification subscreenSubRoomNotification = subscreenDeviceModelParent.mSubRoomNotification;
                if (subscreenSubRoomNotification != null) {
                    subscreenSubRoomNotification.updateNotificationState(notificationEntry, 1);
                }
                LinkedHashMap linkedHashMap = subscreenDeviceModelParent.mFullScreenIntentEntries;
                if (linkedHashMap.get(str) != null) {
                    Log.d("S.S.N.", "REMOVE fullscreenIntent notification - " + str);
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
        ExifInterface$$ExternalSyntheticOutline0.m35m(sb, str, "S.S.N.");
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
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter;
        SubscreenNotificationInfo subscreenNotificationInfo;
        String str3;
        SubscreenSubRoomNotification subscreenSubRoomNotification;
        SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter;
        SubscreenNotificationInfoManager subscreenNotificationInfoManager;
        StatusBarNotification statusBarNotification;
        Notification notification2;
        StatusBarNotification statusBarNotification2;
        Notification notification3;
        StatusBarNotification statusBarNotification3;
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
            if ((notificationEntry == null || notificationEntry.mIsGhost) ? false : true) {
                if (notificationEntry != null) {
                    notificationEntry.mIsGhost = true;
                }
                AbstractC0689x6838b71d.m68m("   ", notificationEntry != null ? notificationEntry.mKey : null, " will be ghost ", Reflection.getOrCreateKotlinClass(SubscreenQuickReplyCoordinator.class).getSimpleName());
            }
            if (subscreenNotificationInfo2 != null && (pendingIntent = subscreenNotificationInfo2.mRemoteInputActionIntent) != null) {
                pendingIntent.addCancelListener((Executor) Dependency.get(Dependency.MAIN_EXECUTOR), this.mRemoteInputCancelListener);
            }
            Iterator it = ((ArrayList) this.subscreenStateListenerList).iterator();
            while (it.hasNext()) {
                C2834x89868f76 c2834x89868f76 = (C2834x89868f76) it.next();
                c2834x89868f76.getClass();
                AbstractC0000x2c234b15.m3m("   subscreen quick reply - ", notificationEntry != null ? notificationEntry.mKey : null, "SubscreenQuickReplyCoordinator");
                Notification notification4 = (notificationEntry == null || (statusBarNotification3 = notificationEntry.mSbn) == null) ? null : statusBarNotification3.getNotification();
                if (notification4 != null) {
                    notification4.flags = ((notificationEntry == null || (statusBarNotification2 = notificationEntry.mSbn) == null || (notification3 = statusBarNotification2.getNotification()) == null) ? (notificationEntry == null || (statusBarNotification = notificationEntry.mSbn) == null || (notification2 = statusBarNotification.getNotification()) == null) ? null : Integer.valueOf(notification2.flags) : Integer.valueOf(notification3.flags | 8)).intValue();
                }
                NotifCollection$$ExternalSyntheticLambda4 notifCollection$$ExternalSyntheticLambda4 = c2834x89868f76.this$0.mNotifUpdater;
                if (notifCollection$$ExternalSyntheticLambda4 == null) {
                    notifCollection$$ExternalSyntheticLambda4 = null;
                }
                notifCollection$$ExternalSyntheticLambda4.onInternalNotificationUpdate("Extending lifetime of notification with subscreen quick reply", notificationEntry != null ? notificationEntry.mSbn : null);
            }
        }
        SubscreenDeviceModelParent subscreenDeviceModelParent2 = this.mDeviceModel;
        if (subscreenDeviceModelParent2 != null) {
            subscreenDeviceModelParent2.mIsReplyNotification = true;
        }
        if (subscreenDeviceModelParent2 != null && (mainListHashMapItem = (SubscreenDeviceModelParent.MainListHashMapItem) subscreenDeviceModelParent2.mMainListArrayHashMap.get(str)) != null) {
            NotificationEntry notificationEntry2 = mainListHashMapItem.mEntry;
            Boolean valueOf = notificationEntry2 != null ? Boolean.valueOf(notificationEntry2.canBubble()) : null;
            Intrinsics.checkNotNull(valueOf);
            if (valueOf.booleanValue()) {
                Log.d("S.S.N.", "hideDetailAdapterAfterBubbleReply parent - Entry  : " + notificationEntry2.mKey);
                if (subscreenDeviceModelParent2.isShownGroup()) {
                    SubscreenSubRoomNotification subscreenSubRoomNotification2 = subscreenDeviceModelParent2.mSubRoomNotification;
                    if (subscreenSubRoomNotification2 != null && (subscreenNotificationInfoManager = subscreenSubRoomNotification2.mNotificationInfoManager) != null) {
                        num = Integer.valueOf(subscreenNotificationInfoManager.removeGroupDataArrayItem(notificationEntry2));
                    }
                    if (num != null && num.intValue() >= 0 && (subscreenSubRoomNotification = subscreenDeviceModelParent2.mSubRoomNotification) != null && (subscreenNotificationGroupAdapter = subscreenSubRoomNotification.mNotificationGroupAdapter) != null) {
                        subscreenNotificationGroupAdapter.notifyItemRemoved(num.intValue());
                    }
                } else {
                    subscreenDeviceModelParent2.notifyListAdapterItemRemoved(notificationEntry2);
                }
                if (NotiRune.NOTI_SUBSCREEN_GHOST_NOTIFICATION && subscreenDeviceModelParent2.isShownDetail()) {
                    SubscreenSubRoomNotification subscreenSubRoomNotification3 = subscreenDeviceModelParent2.mSubRoomNotification;
                    if ((subscreenSubRoomNotification3 == null || (subscreenNotificationDetailAdapter = subscreenSubRoomNotification3.mNotificationDetailAdapter) == null || (subscreenNotificationInfo = subscreenNotificationDetailAdapter.mSelectNotificationInfo) == null || (str3 = subscreenNotificationInfo.mKey) == null || !str3.equals(notificationEntry2.mKey)) ? false : true) {
                        if (subscreenDeviceModelParent2.mController.useHistory(notificationEntry2)) {
                            subscreenDeviceModelParent2.hideDetailNotification();
                        }
                        subscreenDeviceModelParent2.mIsUpdatedAllMainList = false;
                    }
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

    /* JADX WARN: Code restructure failed: missing block: B:12:0x003a, code lost:
    
        if (((r0 || r5.mSbn.getNotification().extras.getBoolean("android.allowDuringSetup")) ? false : true) == false) goto L47;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean shouldFilterOut(NotificationEntry notificationEntry) {
        if (!(this.debugModeFilterProvider.allowedPackages.isEmpty() ? false : !r0.allowedPackages.contains(notificationEntry.mSbn.getPackageName()))) {
            boolean z = this.keyguardUpdateMonitor.mDeviceProvisioned;
            if (!z) {
            }
            if (((NotificationLockscreenUserManagerImpl) ((NotificationLockscreenUserManager) Dependency.get(NotificationLockscreenUserManager.class))).isCurrentProfile(notificationEntry.mSbn.getUserId()) && !((KeyguardNotificationVisibilityProviderImpl) this.keyguardNotificationVisibilityProvider).shouldHideNotification(notificationEntry) && ((!this.statusBarStateController.isDozing() || !notificationEntry.shouldSuppressVisualEffect(128)) && !notificationEntry.shouldSuppressVisualEffect(256) && !notificationEntry.mRanking.isSuspended())) {
                StatusBarNotification statusBarNotification = notificationEntry.mSbn;
                this.foregroundServiceController.getClass();
                if (!(ForegroundServiceController.isDisclosureNotification(statusBarNotification) && !this.foregroundServiceController.isDisclosureNeededForUser(statusBarNotification.getUserId()))) {
                    if (!Utils.useQsMediaPlayer(this.mediaFeatureFlag.context)) {
                        return false;
                    }
                    StatusBarNotification statusBarNotification2 = notificationEntry.mSbn;
                    String[] strArr = MediaDataManagerKt.ART_URIS;
                    if (!statusBarNotification2.getNotification().isMediaNotification()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public final boolean useHistory(NotificationEntry notificationEntry) {
        NotificationManager notificationManager;
        if (notificationEntry == null) {
            return false;
        }
        SettingsHelper settingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
        settingsHelper.getClass();
        List semGetNotificationHistoryForPackage = (!(NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH && settingsHelper.mItemLists.get("notification_history_enabled").getIntValue() == 1) || (notificationManager = (NotificationManager) this.context.getSystemService(NotificationManager.class)) == null) ? null : notificationManager.semGetNotificationHistoryForPackage(this.context.getPackageName(), this.context.getAttributionTag(), notificationEntry.mSbn.getUserId(), notificationEntry.mSbn.getPackageName(), notificationEntry.mSbn.getKey(), 1);
        return (semGetNotificationHistoryForPackage != null ? semGetNotificationHistoryForPackage.size() : 0) > 0;
    }

    public final void onTableModeChanged(boolean z) {
    }
}
