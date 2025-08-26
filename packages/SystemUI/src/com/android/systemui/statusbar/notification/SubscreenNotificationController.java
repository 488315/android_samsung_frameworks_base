package com.android.systemui.statusbar.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.hardware.devicestate.DeviceState;
import android.hardware.devicestate.DeviceStateManager;
import android.os.Handler;
import android.os.UserManager;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierViewController$2$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.bixby2.controller.NotificationController;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.util.MediaFeatureFlag;
import com.android.systemui.plugins.aod.PluginAOD;
import com.android.systemui.plugins.clockpack.PluginClockPack;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.SubscreenDeviceModelParent;
import com.android.systemui.statusbar.notification.SubscreenNotificationDetailAdapter;
import com.android.systemui.statusbar.notification.SubscreenNotificationInfo;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
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
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.Utils;
import com.android.wm.shell.bubbles.Bubbles;
import com.samsung.android.view.SemWindowManager;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import kotlin.Function;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.text.StringsKt__StringsJVMKt;

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

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:159:0x02d4  */
        /* JADX WARN: Removed duplicated region for block: B:167:0x02e6  */
        /* JADX WARN: Removed duplicated region for block: B:183:0x0324  */
        /* JADX WARN: Removed duplicated region for block: B:61:0x011e  */
        /* JADX WARN: Removed duplicated region for block: B:88:0x01c5  */
        /* JADX WARN: Removed duplicated region for block: B:91:0x01cc  */
        /* JADX WARN: Removed duplicated region for block: B:94:0x01d5  */
        /* JADX WARN: Removed duplicated region for block: B:98:0x01eb  */
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
        */
        public final void onViewBound(NotificationEntry notificationEntry) {
            int detailAdapterAutoScrollCurrentPositionByReceive;
            SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter;
            SubscreenNotificationInfo subscreenNotificationInfo;
            SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter2;
            SubscreenNotificationListAdapter subscreenNotificationListAdapter;
            SubscreenNotificationListAdapter subscreenNotificationListAdapter2;
            SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter;
            Icon icon;
            SubscreenNotificationInfoManager subscreenNotificationInfoManager;
            NotificationEntry groupSummary;
            ?? r2;
            NotificationEntry groupSummary2;
            SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter3;
            SubscreenNotificationInfo subscreenNotificationInfo2;
            boolean z;
            NotificationEntry notificationEntry2;
            LinearLayout linearLayout;
            int childCount;
            String str;
            SubscreenSubRoomNotification subscreenSubRoomNotification;
            ?? r1 = SubscreenNotificationController.this.mDeviceModel;
            if (r1 != 0) {
                boolean z2 = r1.mIsReplyNotification;
                String str2 = notificationEntry.mKey;
                if (z2) {
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("entryViewBound parent - mIsReplyNotification :", str2, "S.S.N.");
                    return;
                }
                Log.d("S.S.N.", "entryViewBound parent :" + str2);
                boolean zIsSubScreen = r1.isSubScreen();
                if (r1.notiShowBlocked && zIsSubScreen) {
                    Log.d("S.S.N.", " entryViewBound : show notification is disabled. not showing List");
                    return;
                }
                if (zIsSubScreen) {
                    if (r1.isBubbleNotificationSuppressed$1(notificationEntry)) {
                        Log.d("S.S.N.", "entryViewBound parent - bubble is removed:" + str2);
                        r1.notifyListAdapterItemRemoved(notificationEntry);
                        r1.notifyGroupAdapterItemRemoved(notificationEntry);
                        r1.mMainListArrayHashMap.remove(str2);
                        return;
                    }
                    if (r1.checkBubbleLastHistoryReply(notificationEntry)) {
                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("entryViewBound parent - bubble Reply :", str2, "S.S.N.");
                        return;
                    }
                    NotificationEntry notificationEntry3 = null;
                    notificationEntry3 = null;
                    notificationEntry3 = null;
                    notificationEntry3 = null;
                    notificationEntry3 = null;
                    notificationEntry3 = null;
                    if (r1.mMainListArrayHashMap.containsKey(str2)) {
                        if (!notificationEntry.mSbn.isOngoing() && (subscreenSubRoomNotification = r1.mSubRoomNotification) != null) {
                            subscreenSubRoomNotification.updateNotificationState(notificationEntry, 0);
                        }
                        SubscreenSubRoomNotification subscreenSubRoomNotification2 = r1.mSubRoomNotification;
                        if (subscreenSubRoomNotification2 != null && subscreenSubRoomNotification2.mIsShownDetail && (subscreenNotificationInfo2 = (subscreenNotificationDetailAdapter3 = subscreenSubRoomNotification2.mNotificationDetailAdapter).mSelectNotificationInfo) != null && subscreenNotificationInfo2.mKey.equals(str2)) {
                            subscreenNotificationDetailAdapter3.mPrevSelectNotificationInfo = subscreenNotificationDetailAdapter3.mSelectNotificationInfo;
                            SubscreenNotificationInfo subscreenNotificationInfoCreateItemsData = subscreenNotificationDetailAdapter3.mNotificationInfoManager.createItemsData(notificationEntry.row);
                            subscreenNotificationDetailAdapter3.mSelectNotificationInfo = subscreenNotificationInfoCreateItemsData;
                            SubscreenNotificationInfo subscreenNotificationInfo3 = subscreenNotificationDetailAdapter3.mPrevSelectNotificationInfo;
                            if (subscreenNotificationInfo3 != null) {
                                ArrayList arrayList = subscreenNotificationInfo3.mMessageingStyleInfoArray;
                                ArrayList arrayList2 = subscreenNotificationInfoCreateItemsData.mMessageingStyleInfoArray;
                                if (arrayList.size() < arrayList2.size()) {
                                    Log.d("SubscreenNotificationDetailAdapter", "isItemUpdateCompleted - size is not max");
                                    subscreenNotificationDetailAdapter3.mPrevSelectNotificationInfo = null;
                                } else {
                                    if (arrayList.size() == arrayList2.size()) {
                                        int size = arrayList2.size() - 1;
                                        while (size >= 0) {
                                            SubscreenNotificationInfo.MessagingStyleInfo messagingStyleInfo = (SubscreenNotificationInfo.MessagingStyleInfo) arrayList.get(size);
                                            SubscreenNotificationInfo.MessagingStyleInfo messagingStyleInfo2 = (SubscreenNotificationInfo.MessagingStyleInfo) arrayList2.get(size);
                                            String str3 = messagingStyleInfo2.mContentText;
                                            if (str3 == null || (str = messagingStyleInfo.mContentText) == null || str3.equals(str)) {
                                                ArrayList arrayList3 = arrayList2;
                                                if (messagingStyleInfo2.mPostedTime != messagingStyleInfo.mPostedTime) {
                                                    Log.d("SubscreenNotificationDetailAdapter", "isItemUpdateCompleted - size is max - not match PostTime");
                                                } else {
                                                    size--;
                                                    arrayList2 = arrayList3;
                                                }
                                            } else {
                                                Log.d("SubscreenNotificationDetailAdapter", "isItemUpdateCompleted - size is max - not match text");
                                            }
                                        }
                                    }
                                    z = false;
                                    StringBuilder sb = new StringBuilder("updateSelectNotificationInfo - mIsSendedQuickReply : ");
                                    SubscreenNotificationDetailAdapter.ScrollInfo scrollInfo = subscreenNotificationDetailAdapter3.mScrollInfo;
                                    CarrierTextManager$$ExternalSyntheticOutline0.m(sb, scrollInfo.mIsSendedQuickReply, ", isItemUpdateCompleted() : ", z, "SubscreenNotificationDetailAdapter");
                                    if (z && subscreenNotificationDetailAdapter3.mSelectNotificationInfo.mIsMessagingStyle) {
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
                                                    scrollInfo.mPrevFirstHistoryView = subscreenParentDetailItemViewHolder.mContentLayout.getChildAt(0);
                                                    scrollInfo.mPrevLastHistoryView = subscreenParentDetailItemViewHolder.mContentLayout.getChildAt(childCount - 1);
                                                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) scrollInfo.mPrevFirstHistoryView.getLayoutParams();
                                                    scrollInfo.mPrevFirstHistoryViewBottomMargin = layoutParams.bottomMargin;
                                                    StringBuilder sb2 = new StringBuilder("setPrevFirstAndLastHistoryInfo - prevFirstHisotoryView params.bottomMargin :");
                                                    sb2.append(layoutParams.bottomMargin);
                                                    sb2.append(", mPrevBodyLayoutHeght :");
                                                    RecyclerView$$ExternalSyntheticOutline0.m(scrollInfo.mPrevBodyLayoutHeght, "SubscreenNotificationDetailAdapter", sb2);
                                                }
                                            } else {
                                                Log.d("SubscreenNotificationDetailAdapter", "setPrevFirstAndLastHistoryInfo - not SubscreenParentDetailItemViewHolder");
                                            }
                                            scrollInfo.mCompleteItemUpdateReason = 2;
                                        }
                                        notificationEntry2 = null;
                                        notificationEntry2 = null;
                                        subscreenNotificationDetailAdapter3.mPrevSelectNotificationInfo = null;
                                        if (subscreenNotificationDetailAdapter3.mIsShownReplyButtonWindow) {
                                            subscreenNotificationDetailAdapter3.mUpdatedInfo = true;
                                            subscreenNotificationDetailAdapter3.mDeviceModel.setSmartReplyResultValue(-1, null, null);
                                        } else {
                                            subscreenNotificationDetailAdapter3.notifyItemChanged(0);
                                            subscreenNotificationDetailAdapter3.mUpdatedInfo = false;
                                        }
                                    } else {
                                        notificationEntry2 = null;
                                    }
                                    if (!subscreenNotificationDetailAdapter3.mSelectNotificationInfo.mIsMessagingStyle) {
                                        subscreenNotificationDetailAdapter3.notifyItemChanged(0);
                                    }
                                    notificationEntry3 = notificationEntry2;
                                    if (subscreenNotificationDetailAdapter3.mSelectNotificationInfo.mIsMessagingStyle) {
                                        if (notificationEntry.row.needsRedaction()) {
                                            notificationEntry3 = notificationEntry2;
                                            if (!subscreenNotificationDetailAdapter3.mDeviceModel.isNotShwonNotificationState(subscreenNotificationDetailAdapter3.mSelectNotificationInfo.mRow.mEntry)) {
                                                SubscreenNotificationController subscreenNotificationController = (SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class);
                                                subscreenNotificationController.conversationNotificationManager.states.compute(str2, new ConversationNotificationManager$sam$java_util_function_BiFunction$0(new ConversationNotificationManager$$ExternalSyntheticLambda0()));
                                                ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                                                notificationEntry3 = notificationEntry2;
                                                if (expandableNotificationRow != null) {
                                                    subscreenNotificationController.conversationNotificationManager.getClass();
                                                    ConversationNotificationManager.resetBadgeUi(expandableNotificationRow);
                                                    notificationEntry3 = notificationEntry2;
                                                }
                                            }
                                        }
                                    }
                                }
                                z = true;
                                StringBuilder sb3 = new StringBuilder("updateSelectNotificationInfo - mIsSendedQuickReply : ");
                                SubscreenNotificationDetailAdapter.ScrollInfo scrollInfo2 = subscreenNotificationDetailAdapter3.mScrollInfo;
                                CarrierTextManager$$ExternalSyntheticOutline0.m(sb3, scrollInfo2.mIsSendedQuickReply, ", isItemUpdateCompleted() : ", z, "SubscreenNotificationDetailAdapter");
                                if (z) {
                                    notificationEntry2 = null;
                                    if (!subscreenNotificationDetailAdapter3.mSelectNotificationInfo.mIsMessagingStyle) {
                                    }
                                    notificationEntry3 = notificationEntry2;
                                    if (subscreenNotificationDetailAdapter3.mSelectNotificationInfo.mIsMessagingStyle) {
                                    }
                                }
                            } else {
                                z = false;
                                StringBuilder sb32 = new StringBuilder("updateSelectNotificationInfo - mIsSendedQuickReply : ");
                                SubscreenNotificationDetailAdapter.ScrollInfo scrollInfo22 = subscreenNotificationDetailAdapter3.mScrollInfo;
                                CarrierTextManager$$ExternalSyntheticOutline0.m(sb32, scrollInfo22.mIsSendedQuickReply, ", isItemUpdateCompleted() : ", z, "SubscreenNotificationDetailAdapter");
                                if (z) {
                                }
                            }
                        }
                        if (r1.isSupportRemoteView(notificationEntry) || notificationEntry.isOngoingActivity()) {
                            if (r1.isShownGroup()) {
                                SubscreenSubRoomNotification subscreenSubRoomNotification3 = r1.mSubRoomNotification;
                                if (subscreenSubRoomNotification3 == null || (subscreenNotificationGroupAdapter = subscreenSubRoomNotification3.mNotificationGroupAdapter) == null) {
                                    return;
                                }
                                subscreenNotificationGroupAdapter.notifyDataSetChanged();
                                return;
                            }
                            SubscreenSubRoomNotification subscreenSubRoomNotification4 = r1.mSubRoomNotification;
                            if (subscreenSubRoomNotification4 != null && (subscreenNotificationListAdapter2 = subscreenSubRoomNotification4.mNotificationListAdapter) != null) {
                                subscreenNotificationListAdapter2.mIsCustomNotificationUpdated = Boolean.TRUE;
                            }
                            if (subscreenSubRoomNotification4 == null || (subscreenNotificationListAdapter = subscreenSubRoomNotification4.mNotificationListAdapter) == null) {
                                return;
                            }
                            subscreenNotificationListAdapter.notifyDataSetChanged();
                            return;
                        }
                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("isUpdateEntry parent : ", str2, "S.S.N.");
                        SubscreenDeviceModelParent.MainListHashMapItem mainListHashMapItem = (SubscreenDeviceModelParent.MainListHashMapItem) r1.mMainListArrayHashMap.get(str2);
                        if (mainListHashMapItem == null) {
                            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("isUpdateEntry parent - oldEntry is null ", str2, "S.S.N.");
                        } else {
                            SubscreenNotificationInfo subscreenNotificationInfo4 = mainListHashMapItem.mInfo;
                            SubscreenSubRoomNotification subscreenSubRoomNotification5 = r1.mSubRoomNotification;
                            ?? CreateItemsData = (subscreenSubRoomNotification5 == null || (subscreenNotificationInfoManager = subscreenSubRoomNotification5.mNotificationInfoManager) == null) ? notificationEntry3 : subscreenNotificationInfoManager.createItemsData(notificationEntry.row);
                            if (notificationEntry.mSbn.getNotification().isGroupSummary()) {
                                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("isUpdateEntry parent - Group Sumarry: ", str2, "S.S.N.");
                            } else {
                                if (StringsKt__StringsJVMKt.equals(subscreenNotificationInfo4 != null ? subscreenNotificationInfo4.getTitle() : notificationEntry3, CreateItemsData != null ? CreateItemsData.getTitle() : notificationEntry3, false)) {
                                    if (StringsKt__StringsJVMKt.equals(subscreenNotificationInfo4 != null ? subscreenNotificationInfo4.mContent : notificationEntry3, CreateItemsData != null ? CreateItemsData.mContent : notificationEntry3, false)) {
                                        if (Intrinsics.areEqual(subscreenNotificationInfo4 != null ? Long.valueOf(subscreenNotificationInfo4.mWhen) : notificationEntry3, CreateItemsData != null ? Long.valueOf(CreateItemsData.mWhen) : notificationEntry3)) {
                                            if ((subscreenNotificationInfo4 != null ? subscreenNotificationInfo4.mLargeIcon : notificationEntry3) != null) {
                                                if (subscreenNotificationInfo4 != null && (icon = subscreenNotificationInfo4.mLargeIcon) != null) {
                                                    if (!icon.equals(CreateItemsData != null ? CreateItemsData.mLargeIcon : notificationEntry3)) {
                                                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("isUpdateEntry parent - large Icon: ", str2, "S.S.N.");
                                                        if (CreateItemsData != null) {
                                                            mainListHashMapItem.mEntry = notificationEntry;
                                                            mainListHashMapItem.mInfo = CreateItemsData;
                                                        }
                                                    }
                                                }
                                                ?? ValueOf = subscreenNotificationInfo4 != null ? Boolean.valueOf(subscreenNotificationInfo4.mIsMessagingStyle) : notificationEntry3;
                                                ValueOf.getClass();
                                                if (ValueOf.booleanValue()) {
                                                    ?? ValueOf2 = CreateItemsData != null ? Boolean.valueOf(CreateItemsData.mIsMessagingStyle) : notificationEntry3;
                                                    ValueOf2.getClass();
                                                    if (ValueOf2.booleanValue() && subscreenNotificationInfo4.mUnreadMessageCnt != CreateItemsData.mUnreadMessageCnt) {
                                                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("isUpdateEntry parent - unReadCount: ", str2, "S.S.N.");
                                                    }
                                                } else {
                                                    if ((CreateItemsData != null ? CreateItemsData.mContentView : notificationEntry3) != null) {
                                                    }
                                                }
                                            } else {
                                                if ((CreateItemsData != null ? CreateItemsData.mLargeIcon : notificationEntry3) == null) {
                                                }
                                            }
                                        } else {
                                            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("isUpdateEntry parent - when: ", str2, "S.S.N.");
                                            if (CreateItemsData != null) {
                                                mainListHashMapItem.mEntry = notificationEntry;
                                                mainListHashMapItem.mInfo = CreateItemsData;
                                            }
                                        }
                                    } else {
                                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("isUpdateEntry parent - text content: ", str2, "S.S.N.");
                                        if (CreateItemsData != null) {
                                            mainListHashMapItem.mEntry = notificationEntry;
                                            mainListHashMapItem.mInfo = CreateItemsData;
                                        }
                                    }
                                } else {
                                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("isUpdateEntry parent - text Title: ", str2, "S.S.N.");
                                    if (CreateItemsData != null) {
                                        mainListHashMapItem.mEntry = notificationEntry;
                                        mainListHashMapItem.mInfo = CreateItemsData;
                                    }
                                }
                            }
                        }
                        boolean zIsImportantConversation = notificationEntry.mRanking.getChannel().isImportantConversation();
                        if (!notificationEntry.rowIsChildInGroup() || r1.isShownGroup() || zIsImportantConversation) {
                            groupSummary = notificationEntry;
                            r2 = str2;
                        } else {
                            GroupMembershipManager groupMembershipManager = r1.mGroupMembershipManager;
                            groupSummary = groupMembershipManager != null ? ((GroupMembershipManagerImpl) groupMembershipManager).getGroupSummary(notificationEntry) : notificationEntry3;
                            groupSummary.getClass();
                            r2 = (groupMembershipManager == null || (groupSummary2 = ((GroupMembershipManagerImpl) groupMembershipManager).getGroupSummary(notificationEntry)) == null) ? notificationEntry3 : groupSummary2.mKey;
                            StringBuilder sb4 = new StringBuilder("updateMainListItem isChildGroup : ");
                            sb4.append(str2);
                            sb4.append(", addEntry : ");
                            sb4.append(groupSummary);
                            sb4.append(", key : ");
                            ExifInterface$$ExternalSyntheticOutline0.m(sb4, r2, "S.S.N.");
                        }
                        if (r2 != null) {
                            r1.mMainListUpdateItemHashMap.put(r2, groupSummary);
                        }
                    } else {
                        SubscreenSubRoomNotification subscreenSubRoomNotification6 = r1.mSubRoomNotification;
                        if (subscreenSubRoomNotification6 != null) {
                            subscreenSubRoomNotification6.updateNotificationState(notificationEntry, 0);
                        }
                        if (!notificationEntry.mSbn.getNotification().isGroupSummary()) {
                            r1.mMainListAddEntryHashMap.put(str2, notificationEntry);
                        }
                    }
                    boolean z3 = notificationEntry.mSbn.getNotification().fullScreenIntent != null;
                    if (!r1.showPopupEntryKeySet.contains(str2) && !z3) {
                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("return entryViewBound : ", str2, "S.S.N.");
                        return;
                    }
                    r1.showPopupEntryKeySet.remove(str2);
                    if (z3) {
                        if (ArraysKt___ArraysKt.indexOf(new String[]{"com.skt.prod.dialer", "com.samsung.android.incallui"}, notificationEntry.mSbn.getPackageName()) >= 0) {
                            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("return call Package : ", str2, "S.S.N.");
                            return;
                        }
                        String str4 = notificationEntry.mSbn.getNotification().category;
                        if ((!"call".equals(str4) || !notificationEntry.mSbn.getNotification().isStyle(Notification.CallStyle.class)) && !"alarm".equals(str4) && notificationEntry.mSbn.isClearable()) {
                            MediaSessions$H$$ExternalSyntheticOutline0.m("fullscreenIntent and this category is not supported in subscreen, so return : ", str2, ", category = ", str4, "S.S.N.");
                            return;
                        }
                    }
                    if (z3 && r1.mFullScreenIntentEntries.get(str2) == null) {
                        if (r1.launchFullscreenIntent(notificationEntry)) {
                            return;
                        }
                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("entryViewBound parent - put fullscreenIntent  :", str2, "S.S.N.");
                        r1.mFullScreenIntentEntries.put(str2, notificationEntry);
                    }
                    if (r1.mFullScreenIntentEntries.get(str2) == null && notificationEntry.mSbn.getNotification().isGroupSummary()) {
                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("entryViewBound GroupSummary :", str2, "S.S.N.");
                        return;
                    }
                    if (r1.isShownDetail()) {
                        SubscreenSubRoomNotification subscreenSubRoomNotification7 = r1.mSubRoomNotification;
                        if (((subscreenSubRoomNotification7 == null || (subscreenNotificationDetailAdapter2 = subscreenSubRoomNotification7.mNotificationDetailAdapter) == null) ? notificationEntry3 : subscreenNotificationDetailAdapter2.mSelectNotificationInfo) != null && r1.getTopActivityName().equals("com.android.systemui.subscreen.SubHomeActivity")) {
                            SubscreenSubRoomNotification subscreenSubRoomNotification8 = r1.mSubRoomNotification;
                            if (StringsKt__StringsJVMKt.equals((subscreenSubRoomNotification8 == null || (subscreenNotificationDetailAdapter = subscreenSubRoomNotification8.mNotificationDetailAdapter) == null || (subscreenNotificationInfo = subscreenNotificationDetailAdapter.mSelectNotificationInfo) == null) ? notificationEntry3 : subscreenNotificationInfo.mKey, str2, false)) {
                                SubscreenSubRoomNotification subscreenSubRoomNotification9 = r1.mSubRoomNotification;
                                ?? childAt2 = notificationEntry3;
                                if (subscreenSubRoomNotification9 != null) {
                                    SubscreenRecyclerView subscreenRecyclerView = subscreenSubRoomNotification9.mNotificationRecyclerView;
                                    childAt2 = notificationEntry3;
                                    if (subscreenRecyclerView != null) {
                                        childAt2 = subscreenRecyclerView.getChildAt(0);
                                    }
                                }
                                if (childAt2 != 0 && (detailAdapterAutoScrollCurrentPositionByReceive = r1.getDetailAdapterAutoScrollCurrentPositionByReceive(childAt2)) == 3) {
                                    KeyguardCarrierViewController$2$$ExternalSyntheticOutline0.m(detailAdapterAutoScrollCurrentPositionByReceive, "entryViewBound scrollCurrentPosition : ", " , key : ", str2, "S.S.N.");
                                    return;
                                }
                            }
                        }
                    }
                    if (r1.isCoverBriefAllowed(notificationEntry)) {
                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(" return entryViewBound isCoverBriefAllowed - ", str2, "S.S.N.");
                    } else {
                        r1.makeSubScreenNotification(notificationEntry);
                        r1.showSubscreenNotification();
                    }
                }
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

    static {
        new Companion(null);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0126  */
    /* JADX WARN: Type inference failed for: r0v7, types: [android.hardware.devicestate.DeviceStateManager$DeviceStateCallback, com.android.systemui.statusbar.notification.SubscreenNotificationController$mDeviceStateCallback$1] */
    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.systemui.statusbar.notification.SubscreenNotificationController$mRemoteInputCancelListener$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public SubscreenNotificationController(Context context, UserContextProvider userContextProvider, NotificationInterruptStateProvider notificationInterruptStateProvider, Lazy lazy, Lazy lazy2, Lazy lazy3, KeyguardUpdateMonitor keyguardUpdateMonitor, SettingsHelper settingsHelper, CommonNotifCollection commonNotifCollection, NotificationVisibilityProvider notificationVisibilityProvider, BindEventManager bindEventManager, NotificationController notificationController, UserManager userManager, ConversationNotificationManager conversationNotificationManager, Optional<Bubbles> optional, LogBuffer logBuffer, DebugModeFilterProvider debugModeFilterProvider, KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider, StatusBarStateController statusBarStateController, MediaFeatureFlag mediaFeatureFlag, NotifPipeline notifPipeline, NotificationStackScrollLayoutController notificationStackScrollLayoutController, NotificationRemoteInputManager notificationRemoteInputManager, FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper) {
        final SubscreenNotificationController subscreenNotificationController;
        Context context2;
        SubscreenDeviceModelParent subscreenDeviceModelParent;
        SubscreenDeviceModelParent subscreenDeviceModelCover;
        DeviceStateManager deviceStateManager;
        this.context = context;
        this.pluginAODManagerLazy = lazy2;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.settingsHelper = settingsHelper;
        this.notificationController = notificationController;
        this.conversationNotificationManager = conversationNotificationManager;
        this.bubblesOptional = optional;
        this.debugModeFilterProvider = debugModeFilterProvider;
        this.keyguardNotificationVisibilityProvider = keyguardNotificationVisibilityProvider;
        this.statusBarStateController = statusBarStateController;
        this.mediaFeatureFlag = mediaFeatureFlag;
        this.notifPipeline = notifPipeline;
        this.notificationStackScrollLayoutController = notificationStackScrollLayoutController;
        this.remoteInputManager = notificationRemoteInputManager;
        this.faceWidgetNotificationControllerWrapper = faceWidgetNotificationControllerWrapper;
        SubscreenDeviceModelCreater.Companion.getClass();
        if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_SEVENTH) {
            subscreenDeviceModelCover = new SubscreenDeviceModelB7(context, keyguardUpdateMonitor, settingsHelper, userContextProvider, this, lazy3, commonNotifCollection, logBuffer, notificationInterruptStateProvider, lazy, lazy2, notificationVisibilityProvider, bindEventManager, notificationController, userManager, conversationNotificationManager);
            subscreenNotificationController = this;
            context2 = context;
        } else if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH) {
            subscreenNotificationController = this;
            context2 = context;
            subscreenDeviceModelCover = new SubscreenDeviceModelB5(context2, keyguardUpdateMonitor, settingsHelper, userContextProvider, subscreenNotificationController, lazy3, commonNotifCollection, logBuffer, notificationInterruptStateProvider, lazy, lazy2, notificationVisibilityProvider, bindEventManager, notificationController, userManager, conversationNotificationManager);
        } else if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_SECOND) {
            subscreenNotificationController = this;
            context2 = context;
            subscreenDeviceModelCover = new SubscreenDeviceModelB4(context2, keyguardUpdateMonitor, settingsHelper, userContextProvider, subscreenNotificationController, lazy3, commonNotifCollection, logBuffer, notificationInterruptStateProvider, lazy, lazy2, notificationVisibilityProvider, bindEventManager, notificationController, userManager, conversationNotificationManager);
        } else {
            if (!NotiRune.NOTI_SUBSCREEN_CLEAR_COVER) {
                subscreenNotificationController = this;
                context2 = context;
                subscreenDeviceModelParent = null;
                subscreenNotificationController.mDeviceModel = subscreenDeviceModelParent;
                ?? r0 = new DeviceStateManager.DeviceStateCallback() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationController$mDeviceStateCallback$1
                    public final void onDeviceStateChanged(DeviceState deviceState) {
                        SubscreenDeviceModelParent subscreenDeviceModelParent2 = this.this$0.mDeviceModel;
                        if (subscreenDeviceModelParent2 != null) {
                            subscreenDeviceModelParent2.onStateChangedInDeviceStateCallback(deviceState);
                        }
                    }
                };
                subscreenNotificationController.mDeviceStateCallback = r0;
                ((NotifPipeline) commonNotifCollection).addCollectionListener(subscreenNotificationController);
                bindEventManager.listeners.addIfAbsent(subscreenNotificationController.new AnonymousClass1());
                SemWindowManager.getInstance().registerFoldStateListener(subscreenNotificationController, (Handler) null);
                deviceStateManager = (DeviceStateManager) context2.getSystemService(DeviceStateManager.class);
                if (deviceStateManager != null) {
                    deviceStateManager.registerCallback(context2.getMainExecutor(), (DeviceStateManager.DeviceStateCallback) r0);
                }
                if (subscreenDeviceModelParent != null) {
                    subscreenDeviceModelParent.initialize();
                }
                subscreenNotificationController.subscreenStateListenerList = new ArrayList();
                subscreenNotificationController.mRemoteInputCancelListener = new PendingIntent.CancelListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationController$mRemoteInputCancelListener$1
                    public final void onCanceled(PendingIntent pendingIntent) {
                        SubscreenDeviceModelParent subscreenDeviceModelParent2 = this.this$0.mDeviceModel;
                        if (subscreenDeviceModelParent2 != null) {
                            subscreenDeviceModelParent2.hideDetailNotificationAnimated(300, true);
                        }
                    }
                };
            }
            subscreenNotificationController = this;
            context2 = context;
            subscreenDeviceModelCover = new SubscreenDeviceModelCover(context2, keyguardUpdateMonitor, settingsHelper, userContextProvider, subscreenNotificationController, lazy3, commonNotifCollection, logBuffer, notificationInterruptStateProvider, lazy, lazy2, notificationVisibilityProvider, bindEventManager, notificationController, userManager, conversationNotificationManager);
        }
        subscreenDeviceModelParent = subscreenDeviceModelCover;
        subscreenNotificationController.mDeviceModel = subscreenDeviceModelParent;
        ?? r02 = new DeviceStateManager.DeviceStateCallback() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationController$mDeviceStateCallback$1
            public final void onDeviceStateChanged(DeviceState deviceState) {
                SubscreenDeviceModelParent subscreenDeviceModelParent2 = this.this$0.mDeviceModel;
                if (subscreenDeviceModelParent2 != null) {
                    subscreenDeviceModelParent2.onStateChangedInDeviceStateCallback(deviceState);
                }
            }
        };
        subscreenNotificationController.mDeviceStateCallback = r02;
        ((NotifPipeline) commonNotifCollection).addCollectionListener(subscreenNotificationController);
        bindEventManager.listeners.addIfAbsent(subscreenNotificationController.new AnonymousClass1());
        SemWindowManager.getInstance().registerFoldStateListener(subscreenNotificationController, (Handler) null);
        deviceStateManager = (DeviceStateManager) context2.getSystemService(DeviceStateManager.class);
        if (deviceStateManager != null) {
        }
        if (subscreenDeviceModelParent != null) {
        }
        subscreenNotificationController.subscreenStateListenerList = new ArrayList();
        subscreenNotificationController.mRemoteInputCancelListener = new PendingIntent.CancelListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationController$mRemoteInputCancelListener$1
            public final void onCanceled(PendingIntent pendingIntent) {
                SubscreenDeviceModelParent subscreenDeviceModelParent2 = this.this$0.mDeviceModel;
                if (subscreenDeviceModelParent2 != null) {
                    subscreenDeviceModelParent2.hideDetailNotificationAnimated(300, true);
                }
            }
        };
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
                ((SubscreenQuickReplyCoordinator.C10791) it.next()).onHideDetail(subscreenNotificationInfo.mKey);
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
                int iNotifyListAdapterItemRemoved = subscreenDeviceModelParent.notifyListAdapterItemRemoved(notificationEntry);
                int iNotifyGroupAdapterItemRemoved = subscreenDeviceModelParent.notifyGroupAdapterItemRemoved(notificationEntry);
                subscreenDeviceModelParent.mMainListArrayHashMap.remove(str);
                subscreenDeviceModelParent.mMainListAddEntryHashMap.remove(str);
                subscreenDeviceModelParent.mMainListUpdateItemHashMap.remove(str);
                StringBuilder sb = new StringBuilder("onEntryRemoved parent - remove List index : ");
                sb.append(iNotifyListAdapterItemRemoved);
                sb.append(", group index : ");
                RecyclerView$$ExternalSyntheticOutline0.m(iNotifyGroupAdapterItemRemoved, "S.S.N.", sb);
                if (iNotifyListAdapterItemRemoved >= 0) {
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
        Integer numValueOf = null;
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
                ((SubscreenQuickReplyCoordinator.C10791) obj).onReply(notificationEntry);
            }
        }
        SubscreenDeviceModelParent subscreenDeviceModelParent2 = this.mDeviceModel;
        if (subscreenDeviceModelParent2 != null) {
            subscreenDeviceModelParent2.mIsReplyNotification = true;
        }
        if (subscreenDeviceModelParent2 != null && (mainListHashMapItem = (SubscreenDeviceModelParent.MainListHashMapItem) subscreenDeviceModelParent2.mMainListArrayHashMap.get(str)) != null) {
            NotificationEntry notificationEntry2 = mainListHashMapItem.mEntry;
            Boolean boolValueOf = notificationEntry2 != null ? Boolean.valueOf(notificationEntry2.mRanking.canBubble()) : null;
            boolValueOf.getClass();
            if (boolValueOf.booleanValue()) {
                Log.d("S.S.N.", "hideDetailAdapterAfterBubbleReply parent - Entry  : " + notificationEntry2.mKey);
                if (subscreenDeviceModelParent2.isShownGroup()) {
                    SubscreenSubRoomNotification subscreenSubRoomNotification3 = subscreenDeviceModelParent2.mSubRoomNotification;
                    if (subscreenSubRoomNotification3 != null && (subscreenNotificationInfoManager = subscreenSubRoomNotification3.mNotificationInfoManager) != null) {
                        numValueOf = Integer.valueOf(subscreenNotificationInfoManager.removeGroupDataArrayItem(notificationEntry2));
                    }
                    if (numValueOf != null && numValueOf.intValue() >= 0 && (subscreenSubRoomNotification2 = subscreenDeviceModelParent2.mSubRoomNotification) != null && (subscreenNotificationGroupAdapter = subscreenSubRoomNotification2.mNotificationGroupAdapter) != null) {
                        subscreenNotificationGroupAdapter.notifyItemRemoved(numValueOf.intValue());
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
        return MediaDataManager.Companion.isMediaNotification(statusBarNotification2);
    }

    public final boolean useHistory(NotificationEntry notificationEntry) {
        NotificationManager notificationManager;
        if (notificationEntry == null) {
            return false;
        }
        List listSemGetNotificationHistoryForPackage = null;
        if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isNotificationHistoryEnabled() && (notificationManager = (NotificationManager) this.context.getSystemService(NotificationManager.class)) != null) {
            listSemGetNotificationHistoryForPackage = notificationManager.semGetNotificationHistoryForPackage(this.context.getPackageName(), this.context.getAttributionTag(), notificationEntry.mSbn.getUserId(), notificationEntry.mSbn.getPackageName(), notificationEntry.mSbn.getKey(), 1);
        }
        return (listSemGetNotificationHistoryForPackage != null ? listSemGetNotificationHistoryForPackage.size() : 0) > 0;
    }

    public final void onTableModeChanged(boolean z) {
    }
}
