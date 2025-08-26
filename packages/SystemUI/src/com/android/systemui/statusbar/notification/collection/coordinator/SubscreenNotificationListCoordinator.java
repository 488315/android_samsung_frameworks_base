package com.android.systemui.statusbar.notification.collection.coordinator;

import android.animation.Animator;
import android.app.NotificationChannel;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0;
import com.android.settingslib.SecNotificationBlockManager$$ExternalSyntheticOutline0;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.listener.PluginLockListener;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.SubscreenDeviceModelParent;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.notification.SubscreenNotificationGroupAdapter;
import com.android.systemui.statusbar.notification.SubscreenNotificationInfo;
import com.android.systemui.statusbar.notification.SubscreenNotificationInfoManager;
import com.android.systemui.statusbar.notification.SubscreenNotificationListAdapter;
import com.android.systemui.statusbar.notification.SubscreenNotificationListAdapter$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.SubscreenSubRoomNotification;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManagerImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.Bubbles;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.text.StringsKt__StringsJVMKt;

@CoordinatorScope
/* loaded from: classes3.dex */
public final class SubscreenNotificationListCoordinator implements Coordinator {
    public static final int $stable = 8;
    private final SubscreenNotificationListCoordinator$bubbleFilter$1 bubbleFilter;
    private final Optional<Bubbles> bubblesOptional;
    private final NotificationLockscreenUserManager lockscreenUserManager;
    private final SubscreenNotificationListCoordinator$pluginLockListener$1 pluginLockListener;
    private final PluginLockMediator pluginLockMediator;
    private int pluginLockMode;
    private final SysuiStatusBarStateController statusBarStateController;
    private final SubscreenNotificationController subscreenController;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.pluginlock.listener.PluginLockListener$State, com.android.systemui.statusbar.notification.collection.coordinator.SubscreenNotificationListCoordinator$pluginLockListener$1] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.statusbar.notification.collection.coordinator.SubscreenNotificationListCoordinator$bubbleFilter$1] */
    public SubscreenNotificationListCoordinator(SubscreenNotificationController subscreenNotificationController, SysuiStatusBarStateController sysuiStatusBarStateController, NotificationLockscreenUserManager notificationLockscreenUserManager, PluginLockMediator pluginLockMediator, Optional<Bubbles> optional) {
        this.subscreenController = subscreenNotificationController;
        this.statusBarStateController = sysuiStatusBarStateController;
        this.lockscreenUserManager = notificationLockscreenUserManager;
        this.pluginLockMediator = pluginLockMediator;
        this.bubblesOptional = optional;
        ?? r1 = new PluginLockListener.State() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SubscreenNotificationListCoordinator$pluginLockListener$1
            @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
            public void onViewModeChanged(int i) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onViewModeChanged mode: ", "SubscreenNotificationListCoordinator");
                this.this$0.pluginLockMode = i;
            }
        };
        this.pluginLockListener = r1;
        pluginLockMediator.registerStateCallback(r1);
        this.bubbleFilter = new NotifFilter() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SubscreenNotificationListCoordinator$bubbleFilter$1
            {
                super("SubscreenNotificationListCoordinator");
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
            public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
                SubscreenDeviceModelParent subscreenDeviceModelParent;
                if (!this.this$0.bubblesOptional.isPresent()) {
                    return false;
                }
                if (!((BubbleController.BubblesImpl) ((Bubbles) this.this$0.bubblesOptional.get())).isBubbleNotificationSuppressedFromShade(notificationEntry.mKey, notificationEntry.mSbn.getGroupKey()) || (subscreenDeviceModelParent = this.this$0.subscreenController.mDeviceModel) == null || !subscreenDeviceModelParent.mIsFolded || !notificationEntry.mRanking.canBubble()) {
                    return false;
                }
                NotificationEntry notificationEntry2 = subscreenDeviceModelParent.mBubbleReplyEntry;
                String str = notificationEntry.mKey;
                if (notificationEntry2 != null && str.equals(notificationEntry2.mKey)) {
                    Log.d("S.S.N.", "shouldFilterOutBubble parent - mBubbleReplyEntry key :".concat(str));
                    subscreenDeviceModelParent.mBubbleReplyEntry = null;
                    return false;
                }
                if (((SubscreenDeviceModelParent.MainListHashMapItem) subscreenDeviceModelParent.mMainListArrayHashMap.get(str)) == null) {
                    return false;
                }
                Log.d("S.S.N.", "shouldFilterOutBubble parent - remove Bubble Item :" + str);
                subscreenDeviceModelParent.notifyListAdapterItemRemoved(notificationEntry);
                subscreenDeviceModelParent.notifyGroupAdapterItemRemoved(notificationEntry);
                subscreenDeviceModelParent.mMainListArrayHashMap.remove(str);
                return false;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:408:0x0626  */
    /* JADX WARN: Removed duplicated region for block: B:428:0x0675  */
    /* JADX WARN: Removed duplicated region for block: B:432:0x068a A[LOOP:16: B:431:0x0688->B:432:0x068a, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:435:0x069c  */
    /* JADX WARN: Removed duplicated region for block: B:537:0x08cc  */
    /* JADX WARN: Removed duplicated region for block: B:624:0x079d A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onAfterRenderList(List<? extends PipelineEntry> list) {
        SubscreenSubRoomNotification subscreenSubRoomNotification;
        SubscreenNotificationInfoManager subscreenNotificationInfoManager;
        NotificationChildrenContainer notificationChildrenContainer;
        SubscreenSubRoomNotification subscreenSubRoomNotification2;
        SubscreenNotificationListAdapter subscreenNotificationListAdapter;
        SubscreenNotificationListAdapter subscreenNotificationListAdapter2;
        SubscreenNotificationListAdapter subscreenNotificationListAdapter3;
        boolean z;
        int i;
        Iterator it;
        int size;
        int i2;
        int i3;
        int i4;
        NotificationEntry notificationEntry;
        NotificationChannel channel;
        int i5;
        NotificationEntry groupSummary;
        NotificationEntry groupSummary2;
        ExpandableNotificationRow expandableNotificationRow;
        NotificationEntry notificationEntry2;
        NotificationChannel channel2;
        StatusBarNotification statusBarNotification;
        boolean z2;
        ExpandableNotificationRow expandableNotificationRow2;
        StatusBarNotification statusBarNotification2;
        SubscreenSubRoomNotification subscreenSubRoomNotification3;
        SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter;
        ExpandableNotificationRow expandableNotificationRow3;
        SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter2;
        SubscreenNotificationInfoManager subscreenNotificationInfoManager2;
        ArrayList arrayList;
        SubscreenNotificationInfoManager subscreenNotificationInfoManager3;
        SubscreenNotificationInfoManager subscreenNotificationInfoManager4;
        ArrayList arrayList2;
        SubscreenNotificationInfo subscreenNotificationInfo;
        NotificationEntry notificationEntry3;
        NotificationChildrenContainer notificationChildrenContainer2;
        List list2;
        SubscreenNotificationInfoManager subscreenNotificationInfoManager5;
        SubscreenNotificationInfoManager subscreenNotificationInfoManager6;
        NotificationChildrenContainer notificationChildrenContainer3;
        List list3;
        SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter3;
        SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter4;
        SubscreenNotificationInfoManager subscreenNotificationInfoManager7;
        ArrayList arrayList3;
        SubscreenNotificationInfoManager subscreenNotificationInfoManager8;
        SubscreenNotificationInfoManager subscreenNotificationInfoManager9;
        ArrayList arrayList4;
        SubscreenNotificationInfoManager subscreenNotificationInfoManager10;
        SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter5;
        SubscreenNotificationInfoManager subscreenNotificationInfoManager11;
        ArrayList arrayList5;
        SubscreenNotificationInfoManager subscreenNotificationInfoManager12;
        SubscreenNotificationInfoManager subscreenNotificationInfoManager13;
        ArrayList arrayList6;
        SubscreenNotificationInfoManager subscreenNotificationInfoManager14;
        StatusBarNotification statusBarNotification3;
        ExpandableNotificationRow expandableNotificationRow4;
        NotificationEntry notificationEntry4;
        SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter6;
        SubscreenDeviceModelParent subscreenDeviceModelParent = this.subscreenController.mDeviceModel;
        if (subscreenDeviceModelParent == null) {
            return;
        }
        boolean zIsSubScreen = subscreenDeviceModelParent.isSubScreen();
        int i6 = 0;
        boolean z3 = true;
        boolean z4 = ((StatusBarStateControllerImpl) this.statusBarStateController).mUpcomingState == 1;
        if (zIsSubScreen) {
            subscreenDeviceModelParent.setKeyguardStateWhenAddSubscreenNotificationInfoList(z4);
        }
        boolean z5 = subscreenDeviceModelParent.mIsCovered && !((NotificationLockscreenUserManagerImpl) this.lockscreenUserManager).mShowLockscreenNotifications;
        ArrayList arrayList7 = new ArrayList();
        if (!zIsSubScreen || this.pluginLockMode == 1 || z5) {
            ActionBarContextView$$ExternalSyntheticOutline0.m(KeyguardFMMViewController$$ExternalSyntheticOutline0.m("onAfterRenderList() isSubScreen = ", this.pluginLockMode, ", pluginLockMode = ", zIsSubScreen, ", doNotShowOnCover = "), z5, "SubscreenNotificationListCoordinator");
        } else {
            Iterator<T> it2 = list.iterator();
            while (it2.hasNext()) {
                NotificationEntry representativeEntry = ((PipelineEntry) it2.next()).getRepresentativeEntry();
                if (representativeEntry != null) {
                    boolean z6 = ((ArrayList) representativeEntry.mDismissInterceptors).size() > 0;
                    representativeEntry.row.getClass();
                    if (!z6) {
                        OngoingActivityDataHelper.INSTANCE.getClass();
                        OngoingActivityData ongoingActivityDataByKey = OngoingActivityDataHelper.getOngoingActivityDataByKey(representativeEntry.mKey);
                        if (!representativeEntry.row.isInsignificant() || representativeEntry.row.isInsignificantSummary()) {
                            if (ongoingActivityDataByKey == null || !ongoingActivityDataByKey.mIsMediaOngoingData) {
                                arrayList7.add(representativeEntry);
                            }
                        }
                    }
                }
            }
        }
        if (subscreenDeviceModelParent.isShownGroup() && (subscreenSubRoomNotification3 = subscreenDeviceModelParent.mSubRoomNotification) != null && (subscreenNotificationGroupAdapter = subscreenSubRoomNotification3.mNotificationGroupAdapter) != null) {
            boolean zIsInsignificantSummary = subscreenNotificationGroupAdapter.mSummaryInfo.mRow.isInsignificantSummary();
            if (subscreenNotificationGroupAdapter.mNotificationInfoManager.mGroupDataArray.size() > 1 || zIsInsignificantSummary) {
                if (subscreenNotificationGroupAdapter.mSummaryInfo != null) {
                    SubscreenDeviceModelParent subscreenDeviceModelParent2 = subscreenNotificationGroupAdapter.mDeviceModel;
                    SubscreenSubRoomNotification subscreenSubRoomNotification4 = subscreenDeviceModelParent2.mSubRoomNotification;
                    SubscreenNotificationInfo subscreenNotificationInfo2 = (subscreenSubRoomNotification4 == null || (subscreenNotificationGroupAdapter6 = subscreenSubRoomNotification4.mNotificationGroupAdapter) == null) ? null : subscreenNotificationGroupAdapter6.mSummaryInfo;
                    ArrayList arrayList8 = new ArrayList();
                    Iterator it3 = subscreenDeviceModelParent2.mMainListAddEntryHashMap.entrySet().iterator();
                    while (it3.hasNext()) {
                        Object value = ((Map.Entry) it3.next()).getValue();
                        Boolean boolValueOf = (subscreenNotificationInfo2 == null || (expandableNotificationRow4 = subscreenNotificationInfo2.mRow) == null || (notificationEntry4 = expandableNotificationRow4.mEntry) == null) ? null : Boolean.valueOf(SubscreenDeviceModelParent.isAutoGrouping(notificationEntry4, ((NotificationEntry) value).row.mEntry));
                        NotificationEntry notificationEntry5 = (NotificationEntry) value;
                        if (StringsKt__StringsJVMKt.equals((subscreenNotificationInfo2 == null || (statusBarNotification3 = subscreenNotificationInfo2.mSbn) == null) ? null : statusBarNotification3.getGroupKey(), notificationEntry5.mSbn.getGroupKey(), false) || Intrinsics.areEqual(boolValueOf, Boolean.TRUE)) {
                            String str = notificationEntry5.mKey;
                            arrayList8.add(str);
                            if (((SubscreenDeviceModelParent.MainListHashMapItem) subscreenDeviceModelParent2.mMainListArrayHashMap.get(str)) != null) {
                                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("updateGroupListArray parent - already group Item  - mainListItem != null : ", str, "S.S.N.");
                                subscreenDeviceModelParent2.mMainListUpdateItemHashMap.put(str, value);
                            } else {
                                SubscreenSubRoomNotification subscreenSubRoomNotification5 = subscreenDeviceModelParent2.mSubRoomNotification;
                                Integer numValueOf = (subscreenSubRoomNotification5 == null || (subscreenNotificationInfoManager14 = subscreenSubRoomNotification5.mNotificationInfoManager) == null) ? null : Integer.valueOf(subscreenNotificationInfoManager14.mGroupDataArray.size());
                                numValueOf.getClass();
                                int iIntValue = numValueOf.intValue();
                                int i7 = 0;
                                boolean z7 = false;
                                while (i7 < iIntValue) {
                                    SubscreenSubRoomNotification subscreenSubRoomNotification6 = subscreenDeviceModelParent2.mSubRoomNotification;
                                    SubscreenNotificationInfo subscreenNotificationInfo3 = (subscreenSubRoomNotification6 == null || (subscreenNotificationInfoManager13 = subscreenSubRoomNotification6.mNotificationInfoManager) == null || (arrayList6 = subscreenNotificationInfoManager13.mGroupDataArray) == null) ? null : (SubscreenNotificationInfo) arrayList6.get(i7);
                                    if (StringsKt__StringsJVMKt.equals(subscreenNotificationInfo3 != null ? subscreenNotificationInfo3.mKey : null, str, false)) {
                                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("updateGroupListArray parent - already Item - searchItem : ", str, "S.S.N.");
                                        subscreenDeviceModelParent2.mMainListUpdateItemHashMap.put(str, value);
                                        z7 = true;
                                    }
                                    i7++;
                                    z7 = z7;
                                }
                                SubscreenDeviceModelParent.putMainListArrayHashMap$default(subscreenDeviceModelParent2, notificationEntry5);
                                if (!z7) {
                                    SubscreenSubRoomNotification subscreenSubRoomNotification7 = subscreenDeviceModelParent2.mSubRoomNotification;
                                    SubscreenNotificationInfo subscreenNotificationInfoCreateItemsData = (subscreenSubRoomNotification7 == null || (subscreenNotificationInfoManager12 = subscreenSubRoomNotification7.mNotificationInfoManager) == null) ? null : subscreenNotificationInfoManager12.createItemsData(notificationEntry5.row);
                                    SubscreenSubRoomNotification subscreenSubRoomNotification8 = subscreenDeviceModelParent2.mSubRoomNotification;
                                    if (subscreenSubRoomNotification8 != null && (subscreenNotificationInfoManager11 = subscreenSubRoomNotification8.mNotificationInfoManager) != null && (arrayList5 = subscreenNotificationInfoManager11.mGroupDataArray) != null) {
                                        arrayList5.add(0, subscreenNotificationInfoCreateItemsData);
                                    }
                                    SubscreenSubRoomNotification subscreenSubRoomNotification9 = subscreenDeviceModelParent2.mSubRoomNotification;
                                    if (subscreenSubRoomNotification9 != null && (subscreenNotificationGroupAdapter5 = subscreenSubRoomNotification9.mNotificationGroupAdapter) != null) {
                                        subscreenNotificationGroupAdapter5.notifyItemInserted(0);
                                    }
                                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("updateGroupListArray parent - add Item  : ", str, "S.S.N.");
                                }
                            }
                        }
                    }
                    int size2 = arrayList8.size();
                    int i8 = 0;
                    while (i8 < size2) {
                        Object obj = arrayList8.get(i8);
                        i8++;
                        subscreenDeviceModelParent2.mMainListAddEntryHashMap.remove((String) obj);
                    }
                    SubscreenSubRoomNotification subscreenSubRoomNotification10 = subscreenDeviceModelParent2.mSubRoomNotification;
                    Integer numValueOf2 = (subscreenSubRoomNotification10 == null || (subscreenNotificationInfoManager10 = subscreenSubRoomNotification10.mNotificationInfoManager) == null) ? null : Integer.valueOf(subscreenNotificationInfoManager10.mGroupDataArray.size());
                    numValueOf2.getClass();
                    int iIntValue2 = numValueOf2.intValue();
                    int i9 = 0;
                    while (true) {
                        if (i9 >= iIntValue2) {
                            break;
                        }
                        SubscreenSubRoomNotification subscreenSubRoomNotification11 = subscreenDeviceModelParent2.mSubRoomNotification;
                        SubscreenNotificationInfo subscreenNotificationInfo4 = (subscreenSubRoomNotification11 == null || (subscreenNotificationInfoManager9 = subscreenSubRoomNotification11.mNotificationInfoManager) == null || (arrayList4 = subscreenNotificationInfoManager9.mGroupDataArray) == null) ? null : (SubscreenNotificationInfo) arrayList4.get(i9);
                        String str2 = subscreenNotificationInfo4 != null ? subscreenNotificationInfo4.mKey : null;
                        NotificationEntry notificationEntry6 = (NotificationEntry) subscreenDeviceModelParent2.mMainListUpdateItemHashMap.get(str2);
                        if (notificationEntry6 != null) {
                            SubscreenSubRoomNotification subscreenSubRoomNotification12 = subscreenDeviceModelParent2.mSubRoomNotification;
                            SubscreenNotificationInfo subscreenNotificationInfoCreateItemsData2 = (subscreenSubRoomNotification12 == null || (subscreenNotificationInfoManager8 = subscreenSubRoomNotification12.mNotificationInfoManager) == null) ? null : subscreenNotificationInfoManager8.createItemsData(notificationEntry6.row);
                            SubscreenSubRoomNotification subscreenSubRoomNotification13 = subscreenDeviceModelParent2.mSubRoomNotification;
                            if (subscreenSubRoomNotification13 != null && (subscreenNotificationInfoManager7 = subscreenSubRoomNotification13.mNotificationInfoManager) != null && (arrayList3 = subscreenNotificationInfoManager7.mGroupDataArray) != null) {
                            }
                            SubscreenSubRoomNotification subscreenSubRoomNotification14 = subscreenDeviceModelParent2.mSubRoomNotification;
                            if (subscreenSubRoomNotification14 != null && (subscreenNotificationGroupAdapter4 = subscreenSubRoomNotification14.mNotificationGroupAdapter) != null) {
                                subscreenNotificationGroupAdapter4.notifyItemChanged(i9 + (!subscreenDeviceModelParent2.isMainHeader() ? 1 : 0));
                            }
                            ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("updateGroupListArray parent - update Item  : "), notificationEntry6.mKey, "S.S.N.");
                            TypeIntrinsics.asMutableMap(subscreenDeviceModelParent2.mMainListUpdateItemHashMap).remove(str2);
                        } else {
                            i9++;
                        }
                    }
                    if (subscreenNotificationInfo2 != null && (expandableNotificationRow3 = subscreenNotificationInfo2.mRow) != null && expandableNotificationRow3.isInsignificantSummary()) {
                        SubscreenSubRoomNotification subscreenSubRoomNotification15 = subscreenDeviceModelParent2.mSubRoomNotification;
                        SubscreenNotificationInfo subscreenNotificationInfo5 = (subscreenSubRoomNotification15 == null || (subscreenNotificationGroupAdapter3 = subscreenSubRoomNotification15.mNotificationGroupAdapter) == null) ? null : subscreenNotificationGroupAdapter3.mSummaryInfo;
                        if (subscreenNotificationInfo5 != null) {
                            ExpandableNotificationRow expandableNotificationRow5 = subscreenNotificationInfo5.mRow;
                            Integer numValueOf3 = (expandableNotificationRow5 == null || (notificationChildrenContainer3 = expandableNotificationRow5.mChildrenContainer) == null || (list3 = notificationChildrenContainer3.mAttachedChildren) == null) ? null : Integer.valueOf(((ArrayList) list3).size());
                            SubscreenSubRoomNotification subscreenSubRoomNotification16 = subscreenDeviceModelParent2.mSubRoomNotification;
                            Integer numValueOf4 = (subscreenSubRoomNotification16 == null || (subscreenNotificationInfoManager6 = subscreenSubRoomNotification16.mNotificationInfoManager) == null) ? null : Integer.valueOf(subscreenNotificationInfoManager6.mGroupDataArray.size());
                            if (numValueOf3 != null && numValueOf4 != null && numValueOf3.intValue() > numValueOf4.intValue()) {
                                int iIntValue3 = numValueOf3.intValue() - numValueOf4.intValue();
                                int iIntValue4 = numValueOf3.intValue();
                                for (int i10 = 0; i10 < iIntValue4 && iIntValue3 != 0; i10++) {
                                    SubscreenSubRoomNotification subscreenSubRoomNotification17 = subscreenDeviceModelParent2.mSubRoomNotification;
                                    Integer numValueOf5 = (subscreenSubRoomNotification17 == null || (subscreenNotificationInfoManager5 = subscreenSubRoomNotification17.mNotificationInfoManager) == null) ? null : Integer.valueOf(subscreenNotificationInfoManager5.mGroupDataArray.size());
                                    numValueOf5.getClass();
                                    if (numValueOf5.intValue() >= 50) {
                                        break;
                                    }
                                    ExpandableNotificationRow expandableNotificationRow6 = subscreenNotificationInfo5.mRow;
                                    ExpandableNotificationRow expandableNotificationRow7 = (expandableNotificationRow6 == null || (notificationChildrenContainer2 = expandableNotificationRow6.mChildrenContainer) == null || (list2 = notificationChildrenContainer2.mAttachedChildren) == null) ? null : (ExpandableNotificationRow) ((ArrayList) list2).get(i10);
                                    String str3 = (expandableNotificationRow7 == null || (notificationEntry3 = expandableNotificationRow7.mEntry) == null) ? null : notificationEntry3.mKey;
                                    int iIntValue5 = numValueOf4.intValue();
                                    int i11 = 0;
                                    while (true) {
                                        if (i11 >= iIntValue5) {
                                            SubscreenSubRoomNotification subscreenSubRoomNotification18 = subscreenDeviceModelParent2.mSubRoomNotification;
                                            SubscreenNotificationInfo subscreenNotificationInfoCreateItemsData3 = (subscreenSubRoomNotification18 == null || (subscreenNotificationInfoManager3 = subscreenSubRoomNotification18.mNotificationInfoManager) == null) ? null : subscreenNotificationInfoManager3.createItemsData(expandableNotificationRow7);
                                            SubscreenSubRoomNotification subscreenSubRoomNotification19 = subscreenDeviceModelParent2.mSubRoomNotification;
                                            if (subscreenSubRoomNotification19 != null && (subscreenNotificationInfoManager2 = subscreenSubRoomNotification19.mNotificationInfoManager) != null && (arrayList = subscreenNotificationInfoManager2.mGroupDataArray) != null) {
                                                arrayList.add(0, subscreenNotificationInfoCreateItemsData3);
                                            }
                                            SubscreenSubRoomNotification subscreenSubRoomNotification20 = subscreenDeviceModelParent2.mSubRoomNotification;
                                            if (subscreenSubRoomNotification20 != null && (subscreenNotificationGroupAdapter2 = subscreenSubRoomNotification20.mNotificationGroupAdapter) != null) {
                                                subscreenNotificationGroupAdapter2.notifyItemInserted(0);
                                            }
                                            iIntValue3--;
                                        } else {
                                            SubscreenSubRoomNotification subscreenSubRoomNotification21 = subscreenDeviceModelParent2.mSubRoomNotification;
                                            if (StringsKt__StringsJVMKt.equals(str3, (subscreenSubRoomNotification21 == null || (subscreenNotificationInfoManager4 = subscreenSubRoomNotification21.mNotificationInfoManager) == null || (arrayList2 = subscreenNotificationInfoManager4.mGroupDataArray) == null || (subscreenNotificationInfo = (SubscreenNotificationInfo) arrayList2.get(i11)) == null) ? null : subscreenNotificationInfo.mKey, false)) {
                                                break;
                                            } else {
                                                i11++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (subscreenNotificationGroupAdapter.mNotificationInfoManager.mGroupDataArray.size() == 1) {
                subscreenNotificationGroupAdapter.mSubRoomNotification.mRecyclerViewItemSelectKey = ((SubscreenNotificationInfo) subscreenNotificationGroupAdapter.mNotificationInfoManager.mGroupDataArray.get(0)).mKey;
            }
        }
        ArrayList arrayList9 = new ArrayList();
        int size3 = arrayList7.size();
        int i12 = 0;
        while (i12 < size3) {
            Object obj2 = arrayList7.get(i12);
            i12++;
            NotificationEntry notificationEntry7 = (NotificationEntry) obj2;
            SubscreenSubRoomNotification subscreenSubRoomNotification22 = subscreenDeviceModelParent.mSubRoomNotification;
            if (subscreenSubRoomNotification22 != null) {
                arrayList9.add(subscreenSubRoomNotification22.mNotificationInfoManager.createItemsData(notificationEntry7.row));
            }
        }
        if (subscreenDeviceModelParent.notiShowBlocked || !subscreenDeviceModelParent.isSubScreen() || (subscreenSubRoomNotification = subscreenDeviceModelParent.mSubRoomNotification) == null || (subscreenNotificationInfoManager = subscreenSubRoomNotification.mNotificationInfoManager) == null) {
            return;
        }
        subscreenDeviceModelParent.bubbleReply = 0;
        if (subscreenDeviceModelParent.mIsChangedToFoldState && subscreenDeviceModelParent.mIsKeyguardStateWhenAddSubscreenNotificationInfoList) {
            Log.d("S.S.N.", "updateMainListArray - changeFoldState");
            subscreenDeviceModelParent.mMainListArrayHashMap.clear();
            subscreenDeviceModelParent.mMainListUpdateItemHashMap.clear();
            subscreenDeviceModelParent.mMainListAddEntryHashMap.clear();
            if (!subscreenDeviceModelParent.mIsNotificationRemoved) {
                subscreenDeviceModelParent.mMainListRemoveEntryHashMap.clear();
            }
            subscreenDeviceModelParent.mIsChangedToFoldState = false;
        }
        if (subscreenDeviceModelParent.mMainListArrayHashMap.isEmpty() || subscreenDeviceModelParent.mIsUpdatedAllMainList) {
            if (subscreenDeviceModelParent.mIsUpdatedAllMainList) {
                subscreenDeviceModelParent.mIsUpdatedAllMainList = false;
            }
            if (!subscreenDeviceModelParent.isShownGroup()) {
                subscreenNotificationInfoManager.clearAllRecyclerViewItem();
                subscreenDeviceModelParent.mMainListArrayHashMap.clear();
            }
            SubscreenNotificationInfoManager.mSubscreenNotificationInfoList.clear();
            KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0.m("updateMainListArray allMap - size : ", arrayList9.size(), ", isEmpty: ", subscreenDeviceModelParent.mMainListArrayHashMap.isEmpty(), "S.S.N.");
            int size4 = arrayList9.size();
            int i13 = 0;
            while (i13 < size4) {
                Object obj3 = arrayList9.get(i13);
                i13++;
                SubscreenNotificationInfo subscreenNotificationInfo6 = (SubscreenNotificationInfo) obj3;
                NotificationEntry notificationEntry8 = subscreenNotificationInfo6.mRow.mEntry;
                Log.d("S.S.N.", "updateMainListArray allMap - key : " + notificationEntry8.mKey + ", " + notificationEntry8);
                if (!subscreenDeviceModelParent.checkEntryConditionsForNonAddition(notificationEntry8, "allMap")) {
                    SubscreenNotificationInfoManager.mSubscreenNotificationInfoList.add(subscreenNotificationInfo6);
                    subscreenDeviceModelParent.putMainListArrayHashMap(notificationEntry8, subscreenNotificationInfo6);
                    LinkedHashMap linkedHashMap = subscreenDeviceModelParent.mMainListAddEntryHashMap;
                    String str4 = notificationEntry8.mKey;
                    if (((NotificationEntry) linkedHashMap.get(str4)) != null) {
                        subscreenDeviceModelParent.mMainListAddEntryHashMap.remove(str4);
                    }
                    if (subscreenNotificationInfo6.mGroupSummary && (notificationChildrenContainer = notificationEntry8.row.mChildrenContainer) != null && notificationChildrenContainer.getNotificationChildCount() > 0) {
                        int iMin = Math.min(notificationChildrenContainer.getNotificationChildCount(), 8);
                        for (int i14 = 0; i14 < iMin; i14++) {
                            NotificationEntry notificationEntry9 = ((ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer.mAttachedChildren).get(i14)).mEntry;
                            if (Intrinsics.areEqual(notificationEntry8.mSbn.getGroupKey(), notificationEntry9.mSbn.getGroupKey())) {
                                Log.d("S.S.N.", "updateMainListArray allMap child : " + notificationEntry9.mKey);
                                SubscreenDeviceModelParent.putMainListArrayHashMap$default(subscreenDeviceModelParent, notificationEntry9);
                            }
                        }
                    }
                }
            }
            SubscreenSubRoomNotification subscreenSubRoomNotification23 = subscreenDeviceModelParent.mSubRoomNotification;
            if (subscreenSubRoomNotification23 != null && subscreenSubRoomNotification23.mIsInNotiRoom) {
                subscreenSubRoomNotification23.mNotificationListAdapter.notifyDataSetChanged();
            }
        } else {
            int size5 = arrayList9.size();
            ArrayList arrayList10 = new ArrayList();
            ArrayList arrayList11 = new ArrayList();
            int i15 = 0;
            while (i15 < size5) {
                SubscreenNotificationInfo subscreenNotificationInfo7 = (SubscreenNotificationInfo) arrayList9.get(i15);
                NotificationEntry notificationEntry10 = subscreenNotificationInfo7.mRow.mEntry;
                SubscreenDeviceModelParent.MainListHashMapItem mainListHashMapItem = (SubscreenDeviceModelParent.MainListHashMapItem) subscreenDeviceModelParent.mMainListArrayHashMap.get(notificationEntry10.mKey);
                NotificationEntry notificationEntry11 = mainListHashMapItem != null ? mainListHashMapItem.mEntry : null;
                GroupMembershipManager groupMembershipManager = subscreenDeviceModelParent.mGroupMembershipManager;
                String str5 = notificationEntry10.mKey;
                if (notificationEntry11 != null) {
                    z = z3;
                    LinkedHashMap linkedHashMap2 = subscreenDeviceModelParent.mMainListUpdateItemHashMap;
                    String str6 = notificationEntry11.mKey;
                    NotificationEntry notificationEntry12 = (NotificationEntry) linkedHashMap2.get(str6);
                    if (notificationEntry12 != null) {
                        int subscreenNotificationInfoListSize = SubscreenNotificationInfoManager.getSubscreenNotificationInfoListSize();
                        int i16 = 0;
                        while (i16 < subscreenNotificationInfoListSize) {
                            ArrayList arrayList12 = SubscreenNotificationInfoManager.mSubscreenNotificationInfoList;
                            i = size5;
                            SubscreenNotificationInfo subscreenNotificationInfo8 = (SubscreenNotificationInfo) arrayList12.get(i16);
                            int i17 = subscreenNotificationInfoListSize;
                            NotificationEntry notificationEntry13 = notificationEntry12;
                            if (StringsKt__StringsJVMKt.equals(subscreenNotificationInfo8 != null ? subscreenNotificationInfo8.mKey : null, notificationEntry12.mKey, false)) {
                                arrayList12.set(i16, subscreenNotificationInfo7);
                                subscreenDeviceModelParent.notifyListAdapterItemChanged(i16);
                                subscreenDeviceModelParent.mMainListUpdateItemHashMap.remove(str6);
                                break;
                            } else {
                                i16++;
                                subscreenNotificationInfoListSize = i17;
                                size5 = i;
                                notificationEntry12 = notificationEntry13;
                            }
                        }
                        i = size5;
                        ArrayList arrayList13 = new ArrayList();
                        it = subscreenDeviceModelParent.mMainListAddEntryHashMap.entrySet().iterator();
                        while (it.hasNext()) {
                            NotificationEntry notificationEntry14 = (NotificationEntry) ((Map.Entry) it.next()).getValue();
                            if (notificationEntry14.rowIsChildInGroup()) {
                                String str7 = (groupMembershipManager == null || (groupSummary = ((GroupMembershipManagerImpl) groupMembershipManager).getGroupSummary(notificationEntry14)) == null) ? null : groupSummary.mKey;
                                if (str6.equals(str7)) {
                                    Iterator it4 = it;
                                    if (SubscreenNotificationInfoManager.mSubscreenNotificationInfoList != null) {
                                        i5 = 0;
                                        while (i5 < SubscreenNotificationInfoManager.getSubscreenNotificationInfoListSize()) {
                                            if (str7.equals(((SubscreenNotificationInfo) SubscreenNotificationInfoManager.mSubscreenNotificationInfoList.get(i5)).mRow.mEntry.mKey)) {
                                                break;
                                            } else {
                                                i5++;
                                            }
                                        }
                                        i5 = -1;
                                        if (i5 >= 0) {
                                            subscreenDeviceModelParent.notifyListAdapterItemChanged(i5);
                                        }
                                        arrayList13.add(notificationEntry14.mKey);
                                        SubscreenDeviceModelParent.putMainListArrayHashMap$default(subscreenDeviceModelParent, notificationEntry14);
                                        it = it4;
                                    } else {
                                        i5 = -1;
                                        if (i5 >= 0) {
                                        }
                                        arrayList13.add(notificationEntry14.mKey);
                                        SubscreenDeviceModelParent.putMainListArrayHashMap$default(subscreenDeviceModelParent, notificationEntry14);
                                        it = it4;
                                    }
                                }
                            }
                        }
                        size = arrayList13.size();
                        i2 = 0;
                        while (i2 < size) {
                            Object obj4 = arrayList13.get(i2);
                            i2++;
                            subscreenDeviceModelParent.mMainListAddEntryHashMap.remove((String) obj4);
                        }
                        if (!subscreenNotificationInfo7.mGroupSummary) {
                            Log.d("S.S.N.", "update - updateMainListArray parent Group child remove Item: " + str5);
                            int subscreenNotificationInfoListSize2 = SubscreenNotificationInfoManager.getSubscreenNotificationInfoListSize();
                            int i18 = 0;
                            boolean z8 = false;
                            boolean z9 = false;
                            while (i18 < subscreenNotificationInfoListSize2) {
                                SubscreenNotificationInfo subscreenNotificationInfo9 = (SubscreenNotificationInfo) SubscreenNotificationInfoManager.mSubscreenNotificationInfoList.get(i18);
                                boolean z10 = subscreenNotificationInfo9 != null ? subscreenNotificationInfo9.mGroupSummary : false;
                                if (z10) {
                                    i3 = subscreenNotificationInfoListSize2;
                                    i4 = i18;
                                } else {
                                    if (SubscreenDeviceModelParent.isAutoGrouping(notificationEntry10, subscreenNotificationInfo9.mRow.mEntry)) {
                                        int notificationChildCount = subscreenNotificationInfo7.mRow.mChildrenContainer.getNotificationChildCount();
                                        i3 = subscreenNotificationInfoListSize2;
                                        int i19 = 0;
                                        while (i19 < notificationChildCount) {
                                            int i20 = i18;
                                            int i21 = i19;
                                            if (((ExpandableNotificationRow) ((ArrayList) subscreenNotificationInfo7.mRow.mChildrenContainer.mAttachedChildren).get(i19)).mEntry.mKey.equals(subscreenNotificationInfo9.mKey) && subscreenNotificationInfo9.mRow.mEntry.rowIsChildInGroup()) {
                                                z9 = z;
                                            }
                                            i19 = i21 + 1;
                                            i18 = i20;
                                        }
                                        i4 = i18;
                                        if (!z9) {
                                            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("update - updateMainListArray parent Group child Auto Grouping - not Group Child ", str5, "S.S.N.");
                                        }
                                        i18 = i4 + 1;
                                        subscreenNotificationInfoListSize2 = i3;
                                    } else {
                                        i3 = subscreenNotificationInfoListSize2;
                                        i4 = i18;
                                    }
                                    String groupKey = notificationEntry10.mSbn.getGroupKey();
                                    StatusBarNotification statusBarNotification4 = subscreenNotificationInfo9.mSbn;
                                    if (groupKey.equals(statusBarNotification4 != null ? statusBarNotification4.getGroupKey() : null) || z9) {
                                        ExpandableNotificationRow expandableNotificationRow8 = subscreenNotificationInfo9.mRow;
                                        Boolean boolValueOf2 = (expandableNotificationRow8 == null || (notificationEntry = expandableNotificationRow8.mEntry) == null || (channel = notificationEntry.mRanking.getChannel()) == null) ? null : Boolean.valueOf(channel.isImportantConversation());
                                        boolValueOf2.getClass();
                                        if (boolValueOf2.booleanValue()) {
                                            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("update - updateMainListArray parent Group child important: ", subscreenNotificationInfo9.mKey, "S.S.N.");
                                        } else {
                                            arrayList11.add(Integer.valueOf(i4));
                                            z8 = z;
                                        }
                                    }
                                    i18 = i4 + 1;
                                    subscreenNotificationInfoListSize2 = i3;
                                }
                                if (z10) {
                                    if (str5.equals(subscreenNotificationInfo9.mKey)) {
                                        Log.d("S.S.N.", "update - updateMainListArray parent Group child already exists ".concat(str5));
                                        z8 = false;
                                    }
                                } else if (z9) {
                                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("update - updateMainListArray parent Group child Auto Grouping ", str5, "S.S.N.");
                                    z8 = false;
                                }
                                i18 = i4 + 1;
                                subscreenNotificationInfoListSize2 = i3;
                            }
                            if (z8) {
                                arrayList10.add(Integer.valueOf(i15));
                            }
                        }
                    } else {
                        i = size5;
                        ArrayList arrayList132 = new ArrayList();
                        it = subscreenDeviceModelParent.mMainListAddEntryHashMap.entrySet().iterator();
                        while (it.hasNext()) {
                        }
                        size = arrayList132.size();
                        i2 = 0;
                        while (i2 < size) {
                        }
                        if (!subscreenNotificationInfo7.mGroupSummary) {
                        }
                    }
                } else if (subscreenDeviceModelParent.checkEntryConditionsForNonAddition(notificationEntry10, "add Item")) {
                    i = size5;
                    z = z3;
                } else {
                    boolean zIsImportantConversation = notificationEntry10.mRanking.getChannel().isImportantConversation();
                    if (subscreenDeviceModelParent.isShownGroup() || zIsImportantConversation) {
                        z = z3;
                    } else {
                        int subscreenNotificationInfoListSize3 = SubscreenNotificationInfoManager.getSubscreenNotificationInfoListSize();
                        int i22 = i6;
                        while (true) {
                            z = z3;
                            if (i22 >= subscreenNotificationInfoListSize3) {
                                break;
                            }
                            SubscreenNotificationInfo subscreenNotificationInfo10 = (SubscreenNotificationInfo) SubscreenNotificationInfoManager.mSubscreenNotificationInfoList.get(i22);
                            if ((subscreenNotificationInfo10 != null ? subscreenNotificationInfo10.mGroupSummary : i6) != 0) {
                                if (notificationEntry10.mSbn.getGroupKey().equals((subscreenNotificationInfo10 == null || (statusBarNotification2 = subscreenNotificationInfo10.mSbn) == null) ? null : statusBarNotification2.getGroupKey())) {
                                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("updateMainListArray add Item - already group Item: ", str5, "S.S.N.");
                                    subscreenDeviceModelParent.mMainListAddEntryHashMap.remove(str5);
                                    NotificationChildrenContainer notificationChildrenContainer4 = (subscreenNotificationInfo10 == null || (expandableNotificationRow2 = subscreenNotificationInfo10.mRow) == null) ? null : expandableNotificationRow2.mChildrenContainer;
                                    if (notificationChildrenContainer4 == null || notificationChildrenContainer4.getNotificationChildCount() == 0) {
                                        Log.d("S.S.N.", "updateMainListArray add Item - remove old summary because it is empty: " + (subscreenNotificationInfo10 != null ? subscreenNotificationInfo10.mKey : null));
                                        arrayList11.add(Integer.valueOf(i22));
                                    } else {
                                        z2 = z;
                                    }
                                }
                            }
                            i22++;
                            z3 = z;
                            i6 = 0;
                        }
                        z2 = false;
                        if (!z2) {
                        }
                        i = size5;
                    }
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("updateMainListArray parent add Item: ", str5, "S.S.N.");
                    subscreenDeviceModelParent.putMainListArrayHashMap(notificationEntry10, subscreenNotificationInfo7);
                    arrayList10.add(Integer.valueOf(i15));
                    if (subscreenNotificationInfo7.mGroupSummary) {
                        Log.d("S.S.N.", "add - updateMainListArray parent Group child remove Item: " + str5);
                        int subscreenNotificationInfoListSize4 = SubscreenNotificationInfoManager.getSubscreenNotificationInfoListSize();
                        for (int i23 = 0; i23 < subscreenNotificationInfoListSize4; i23++) {
                            SubscreenNotificationInfo subscreenNotificationInfo11 = (SubscreenNotificationInfo) SubscreenNotificationInfoManager.mSubscreenNotificationInfoList.get(i23);
                            if (notificationEntry10.mSbn.getGroupKey().equals((subscreenNotificationInfo11 == null || (statusBarNotification = subscreenNotificationInfo11.mSbn) == null) ? null : statusBarNotification.getGroupKey())) {
                                Boolean boolValueOf3 = (subscreenNotificationInfo11 == null || (expandableNotificationRow = subscreenNotificationInfo11.mRow) == null || (notificationEntry2 = expandableNotificationRow.mEntry) == null || (channel2 = notificationEntry2.mRanking.getChannel()) == null) ? null : Boolean.valueOf(channel2.isImportantConversation());
                                boolValueOf3.getClass();
                                if (boolValueOf3.booleanValue()) {
                                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("add - updateMainListArray parent Group child important: ", subscreenNotificationInfo11.mKey, "S.S.N.");
                                } else {
                                    arrayList11.add(Integer.valueOf(i23));
                                }
                            }
                        }
                        ArrayList arrayList14 = new ArrayList();
                        Iterator it5 = subscreenDeviceModelParent.mMainListAddEntryHashMap.entrySet().iterator();
                        while (it5.hasNext()) {
                            NotificationEntry notificationEntry15 = (NotificationEntry) ((Map.Entry) it5.next()).getValue();
                            if (notificationEntry15.rowIsChildInGroup()) {
                                if (str5.equals((groupMembershipManager == null || (groupSummary2 = ((GroupMembershipManagerImpl) groupMembershipManager).getGroupSummary(notificationEntry15)) == null) ? null : groupSummary2.mKey)) {
                                    arrayList14.add(notificationEntry15.mKey);
                                    SubscreenDeviceModelParent.putMainListArrayHashMap$default(subscreenDeviceModelParent, notificationEntry15);
                                }
                            }
                        }
                        int size6 = arrayList14.size();
                        int i24 = 0;
                        while (i24 < size6) {
                            Object obj5 = arrayList14.get(i24);
                            i24++;
                            subscreenDeviceModelParent.mMainListAddEntryHashMap.remove((String) obj5);
                        }
                    }
                    LinkedHashMap linkedHashMap3 = subscreenDeviceModelParent.mMainListAddEntryHashMap;
                    if (linkedHashMap3 != null && linkedHashMap3.get(str5) != null) {
                        subscreenDeviceModelParent.mMainListAddEntryHashMap.remove(str5);
                    }
                    i = size5;
                }
                i15++;
                z3 = z;
                size5 = i;
                i6 = 0;
            }
            boolean z11 = z3;
            CollectionsKt__MutableCollectionsJVMKt.sort(arrayList11);
            if (SubscreenNotificationInfoManager.mSubscreenNotificationInfoList.size() > 0) {
                for (int size7 = arrayList11.size() - 1; -1 < size7; size7--) {
                    int iIntValue6 = ((Number) arrayList11.get(size7)).intValue();
                    ArrayList arrayList15 = SubscreenNotificationInfoManager.mSubscreenNotificationInfoList;
                    if (arrayList15.size() > iIntValue6) {
                        SubscreenNotificationInfo subscreenNotificationInfo12 = (SubscreenNotificationInfo) arrayList15.get(iIntValue6);
                        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(iIntValue6, "removeSubscreenNotificationInfoItem : ", " >>>>> currentThread : ");
                        sbM.append(Thread.currentThread());
                        Log.d("SubscreenNotificationInfoManager", sbM.toString());
                        arrayList15.remove(iIntValue6);
                        SubscreenSubRoomNotification subscreenSubRoomNotification24 = subscreenDeviceModelParent.mSubRoomNotification;
                        if (subscreenSubRoomNotification24 != null && (subscreenNotificationListAdapter3 = subscreenSubRoomNotification24.mNotificationListAdapter) != null) {
                            subscreenNotificationListAdapter3.notifyItemRemoved(subscreenDeviceModelParent.convertInfoIndexToAdapterPosition(iIntValue6));
                        }
                        SecNotificationBlockManager$$ExternalSyntheticOutline0.m(iIntValue6, "updateMainListArray parent remove Item: ", subscreenNotificationInfo12 != null ? subscreenNotificationInfo12.mKey : null, ", index : ", "S.S.N.");
                    }
                }
            }
            int size8 = arrayList10.size();
            int i25 = 0;
            while (i25 < size8) {
                Object obj6 = arrayList10.get(i25);
                i25++;
                int iIntValue7 = ((Number) obj6).intValue();
                SubscreenNotificationInfo subscreenNotificationInfo13 = (SubscreenNotificationInfo) arrayList9.get(iIntValue7);
                if (iIntValue7 > SubscreenNotificationInfoManager.getSubscreenNotificationInfoListSize()) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m(iIntValue7, "updateMainListArray parent add Item last position: ", "S.S.N.");
                    SubscreenNotificationInfoManager.mSubscreenNotificationInfoList.add(subscreenNotificationInfo13);
                } else {
                    SubscreenNotificationInfoManager.mSubscreenNotificationInfoList.add(iIntValue7, subscreenNotificationInfo13);
                }
                SubscreenSubRoomNotification subscreenSubRoomNotification25 = subscreenDeviceModelParent.mSubRoomNotification;
                if (subscreenSubRoomNotification25 != null && (subscreenNotificationListAdapter2 = subscreenSubRoomNotification25.mNotificationListAdapter) != null) {
                    subscreenNotificationListAdapter2.notifyItemInserted(subscreenDeviceModelParent.convertInfoIndexToAdapterPosition(iIntValue7));
                }
            }
            if (!subscreenDeviceModelParent.isShownGroup() && (subscreenSubRoomNotification2 = subscreenDeviceModelParent.mSubRoomNotification) != null && (subscreenNotificationListAdapter = subscreenSubRoomNotification2.mNotificationListAdapter) != null) {
                Animator animator = subscreenNotificationListAdapter.mFooterAnimator;
                if (animator != null) {
                    animator.cancel();
                    subscreenNotificationListAdapter.mFooterAnimator = null;
                }
                if (subscreenNotificationListAdapter.mFooterViewHolder != null) {
                    subscreenNotificationListAdapter.mNotificationInfoManager.getClass();
                    if (SubscreenNotificationInfoManager.getSubscreenNotificationInfoListSize() != 0) {
                        subscreenNotificationListAdapter.mNotificationInfoManager.getClass();
                        if (SubscreenNotificationInfoManager.checkRemoveNotification()) {
                            if (subscreenNotificationListAdapter.mFooterViewHolder.mClearAllLayout.getVisibility() == 8) {
                                subscreenNotificationListAdapter.mFooterAnimator = subscreenNotificationListAdapter.mNotificationAnimatorManager.alphaViewAnimated(subscreenNotificationListAdapter.mFooterViewHolder.mClearAllLayout, new SubscreenNotificationListAdapter$$ExternalSyntheticLambda0(subscreenNotificationListAdapter, 1), 300L, 0.0f, 1.0f);
                            } else {
                                subscreenNotificationListAdapter.mFooterViewHolder.mClearAllLayout.setAlpha(1.0f);
                                subscreenNotificationListAdapter.mFooterViewHolder.mClearAllLayout.setVisibility(0);
                            }
                            subscreenNotificationListAdapter.mFooterViewHolder.mClearAllLayout.setEnabled(z11);
                            subscreenNotificationListAdapter.mFooterViewHolder.mClearAllLayout.setAlpha(1.0f);
                        } else if (subscreenNotificationListAdapter.mFooterViewHolder.mClearAllLayout.getVisibility() == 0) {
                            subscreenNotificationListAdapter.mFooterAnimator = subscreenNotificationListAdapter.mNotificationAnimatorManager.alphaViewAnimated(subscreenNotificationListAdapter.mFooterViewHolder.mClearAllLayout, new SubscreenNotificationListAdapter$$ExternalSyntheticLambda0(subscreenNotificationListAdapter, 0), 300L, 1.0f, 0.0f);
                        } else {
                            subscreenNotificationListAdapter.mFooterViewHolder.mClearAllLayout.setAlpha(0.0f);
                            subscreenNotificationListAdapter.mFooterViewHolder.mClearAllLayout.setVisibility(8);
                        }
                    }
                }
            }
        }
        if (subscreenDeviceModelParent.bubbleReply == 0) {
            subscreenDeviceModelParent.mBubbleReplyEntry = null;
        }
        if (subscreenDeviceModelParent.mIsNotificationRemoved) {
            subscreenDeviceModelParent.mIsNotificationRemoved = false;
            subscreenDeviceModelParent.mMainListRemoveEntryHashMap.clear();
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addPreGroupFilter(this.bubbleFilter);
        notifPipeline.addOnAfterRenderListListener(new OnAfterRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SubscreenNotificationListCoordinator.attach.1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener
            public final void onAfterRenderList(List<? extends PipelineEntry> list) {
                SubscreenNotificationListCoordinator.this.onAfterRenderList(list);
            }
        });
    }
}
