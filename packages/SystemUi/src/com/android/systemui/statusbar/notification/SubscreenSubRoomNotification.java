package com.android.systemui.statusbar.notification;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.statusbar.LockscreenNotificationInfo;
import com.android.systemui.statusbar.LockscreenNotificationManager;
import com.android.systemui.statusbar.notification.SubscreenDeviceModelParent;
import com.android.systemui.statusbar.notification.SubscreenNotificationDetailAdapter;
import com.android.systemui.statusbar.notification.SubscreenSubRoomNotificaitonTouchManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManagerImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.knox.accounts.Account;
import com.samsung.android.sivs.p049ai.sdkcommon.asr.SpeechRecognitionConst;
import com.sec.ims.presence.ServiceTuple;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SubscreenSubRoomNotification implements SubRoom {
    public static Context mContext;
    public static SubscreenSubRoomNotification sInstance;
    public View mMainView;
    public SubscreenSubRoomNotificaitonAnimatorManager mNotificationAnimatorManager;
    public SubscreenNotificationDetailAdapter mNotificationDetailAdapter;
    public SubscreenNotificationGroupAdapter mNotificationGroupAdapter;
    public SubscreenNotificationInfoManager mNotificationInfoManager;
    public SubscreenNotificationListAdapter mNotificationListAdapter;
    public SubscreenRecyclerView mNotificationRecyclerView;
    public SubscreenSubRoomNotificaitonTouchManager mNotificationTouchManager;
    public int mRecyclerViewFirstVisibleItemPosition;
    public String mRecyclerViewItemSelectKey;
    public int mRecyclerViewLastVisibleItemPosition;
    public final C27854 mRemoteInputEmojiActionBroadcastReceiver;
    public final C27865 mRemoteInputVoiceActionBroadcastReceiver;
    public SubRoom.StateChangeListener mStateChangeListener;
    public SubscreenSubRoomNotificationTip mSubRoomNotificationTipAdapter;
    public LinearLayout mSubscreenMainLayout;
    public Vibrator mVibrator;
    public final ArrayList mUnreadNotificationList = new ArrayList();
    public boolean mIsInNotiRoom = false;
    public boolean mHasUnreadNoti = false;
    public boolean mIsShownDetail = false;
    public boolean mIsShownGroup = false;
    public boolean mIsScrollByMe = false;
    public final C27821 mRecyclerViewScrollListener = new C27821();
    public final C27832 linearLayoutManager = new LinearLayoutManager(mContext, 1, false) { // from class: com.android.systemui.statusbar.notification.SubscreenSubRoomNotification.2
        @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
        public final void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                Log.e("SubscreenSubRoomNotification", "RecyclerView's item count : " + state.getItemCount() + " !meet a IndexOutOfBoundsException : " + e);
                StringBuilder sb = new StringBuilder("RecyclerView's list + ");
                sb.append(recycler.mUnmodifiableAttachedScrap);
                Log.e("SubscreenSubRoomNotification", sb.toString());
            }
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
        public final void onLayoutCompleted(RecyclerView.State state) {
            boolean z;
            super.onLayoutCompleted(state);
            SubscreenSubRoomNotification subscreenSubRoomNotification = SubscreenSubRoomNotification.this;
            if (!subscreenSubRoomNotification.mIsShownDetail) {
                SubscreenSubRoomNotification.getDeviceModel().updateContentScroll();
                return;
            }
            SubscreenNotificationDetailAdapter.ScrollInfo scrollInfo = subscreenSubRoomNotification.mNotificationDetailAdapter.mScrollInfo;
            int i = scrollInfo.mCompleteItemUpdateReason;
            boolean z2 = true;
            if (i == 1) {
                scrollInfo.mCompleteItemUpdateReason = 0;
                SubscreenSubRoomNotification.getDeviceModel().updateContentScroll();
                Log.d("SubscreenSubRoomNotification", "onLayoutCompleted - isQuickReply : true");
                z = false;
            } else {
                if (i == 2) {
                    scrollInfo.mCompleteItemUpdateReason = 0;
                    SubscreenSubRoomNotification.getDeviceModel().updateContentScroll();
                    Log.d("SubscreenSubRoomNotification", "onLayoutCompleted - isReceive : true");
                    z = true;
                    z2 = false;
                } else {
                    z = false;
                    z2 = false;
                }
            }
            SubscreenSubRoomNotification.getDeviceModel().moveDetailAdapterContentScroll(getChildAt(0), z2, z, false);
            if (z) {
                Log.d("SubscreenSubRoomNotification", "onLayoutCompleted - ShowAiReply");
                SubscreenSubRoomNotification.getDeviceModel().showAIReply();
            }
        }
    };
    public final C27843 mLockscreenNotiCallback = new LockscreenNotificationManager.Callback() { // from class: com.android.systemui.statusbar.notification.SubscreenSubRoomNotification.3
        /* JADX WARN: Code restructure failed: missing block: B:217:0x035c, code lost:
        
            if (r2 != false) goto L159;
         */
        /* JADX WARN: Code restructure failed: missing block: B:607:0x0953, code lost:
        
            if (r1.mIsInNotiRoom == true) goto L539;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:338:0x04f8  */
        /* JADX WARN: Removed duplicated region for block: B:363:0x053d A[LOOP:11: B:361:0x0537->B:363:0x053d, LOOP_END] */
        /* JADX WARN: Removed duplicated region for block: B:367:0x0553  */
        /* JADX WARN: Removed duplicated region for block: B:423:0x0604 A[SYNTHETIC] */
        /* JADX WARN: Type inference failed for: r5v3, types: [com.android.systemui.statusbar.notification.SubscreenNotificationListAdapter$$ExternalSyntheticLambda0] */
        /* JADX WARN: Type inference failed for: r7v15, types: [com.android.systemui.statusbar.notification.SubscreenNotificationListAdapter$$ExternalSyntheticLambda0] */
        @Override // com.android.systemui.statusbar.LockscreenNotificationManager.Callback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onNotificationInfoUpdated(ArrayList arrayList) {
            LinkedHashMap linkedHashMap;
            SubscreenNotificationListAdapter subscreenNotificationListAdapter;
            LinkedHashMap linkedHashMap2;
            NotificationChildrenContainer notificationChildrenContainer;
            ArrayList arrayList2;
            ArrayList arrayList3;
            SubscreenNotificationInfoManager subscreenNotificationInfoManager;
            SubscreenSubRoomNotification subscreenSubRoomNotification;
            final SubscreenNotificationListAdapter subscreenNotificationListAdapter2;
            ArrayList arrayList4;
            SubscreenNotificationListAdapter subscreenNotificationListAdapter3;
            ArrayList arrayList5;
            SubscreenNotificationListAdapter subscreenNotificationListAdapter4;
            ArrayList arrayList6;
            int i;
            LinkedHashMap linkedHashMap3;
            Iterator it;
            Iterator it2;
            ExpandableNotificationRow expandableNotificationRow;
            NotificationEntry notificationEntry;
            NotificationChannel channel;
            StatusBarNotification statusBarNotification;
            StatusBarNotification statusBarNotification2;
            Notification notification2;
            ArrayList arrayList7;
            SubscreenNotificationListAdapter subscreenNotificationListAdapter5;
            NotificationEntry groupSummary;
            SubscreenNotificationListAdapter subscreenNotificationListAdapter6;
            ArrayList arrayList8;
            NotificationEntry groupSummary2;
            ExpandableNotificationRow expandableNotificationRow2;
            NotificationEntry notificationEntry2;
            NotificationChannel channel2;
            StatusBarNotification statusBarNotification3;
            ArrayList arrayList9;
            boolean z;
            int i2;
            ExpandableNotificationRow expandableNotificationRow3;
            StatusBarNotification statusBarNotification4;
            StatusBarNotification statusBarNotification5;
            Notification notification3;
            ArrayList arrayList10;
            LinkedHashMap linkedHashMap4;
            SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter;
            SubscreenNotificationInfoManager subscreenNotificationInfoManager2;
            ArrayList arrayList11;
            SubscreenNotificationInfoManager subscreenNotificationInfoManager3;
            SubscreenNotificationInfoManager subscreenNotificationInfoManager4;
            ArrayList arrayList12;
            SubscreenNotificationInfoManager subscreenNotificationInfoManager5;
            SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter2;
            SubscreenNotificationInfoManager subscreenNotificationInfoManager6;
            ArrayList arrayList13;
            SubscreenNotificationInfoManager subscreenNotificationInfoManager7;
            SubscreenNotificationInfoManager subscreenNotificationInfoManager8;
            ArrayList arrayList14;
            SubscreenNotificationInfoManager subscreenNotificationInfoManager9;
            StatusBarNotification statusBarNotification6;
            SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter3;
            SubscreenSubRoomNotification subscreenSubRoomNotification2 = SubscreenSubRoomNotification.this;
            int i3 = 0;
            if (subscreenSubRoomNotification2.mIsShownGroup) {
                SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter4 = subscreenSubRoomNotification2.mNotificationGroupAdapter;
                if (subscreenNotificationGroupAdapter4.mNotificationInfoManager.getGroupDataArraySize() <= 1) {
                    if (subscreenNotificationGroupAdapter4.mNotificationInfoManager.getGroupDataArraySize() == 1) {
                        subscreenNotificationGroupAdapter4.mSubRoomNotification.mRecyclerViewItemSelectKey = ((SubscreenNotificationInfo) subscreenNotificationGroupAdapter4.mNotificationInfoManager.mGroupDataArray.get(0)).mKey;
                    }
                } else if (subscreenNotificationGroupAdapter4.mSummaryInfo != null) {
                    SubscreenDeviceModelParent subscreenDeviceModelParent = subscreenNotificationGroupAdapter4.mDeviceModel;
                    SubscreenSubRoomNotification subscreenSubRoomNotification3 = subscreenDeviceModelParent.mSubRoomNotification;
                    SubscreenNotificationInfo subscreenNotificationInfo = (subscreenSubRoomNotification3 == null || (subscreenNotificationGroupAdapter3 = subscreenSubRoomNotification3.mNotificationGroupAdapter) == null) ? null : subscreenNotificationGroupAdapter3.mSummaryInfo;
                    ArrayList arrayList15 = new ArrayList();
                    LinkedHashMap linkedHashMap5 = subscreenDeviceModelParent.mMainListAddEntryHashMap;
                    Iterator it3 = linkedHashMap5.entrySet().iterator();
                    while (true) {
                        boolean hasNext = it3.hasNext();
                        linkedHashMap4 = subscreenDeviceModelParent.mMainListUpdateItemHashMap;
                        if (!hasNext) {
                            break;
                        }
                        NotificationEntry notificationEntry3 = (NotificationEntry) ((Map.Entry) it3.next()).getValue();
                        if (StringsKt__StringsJVMKt.equals((subscreenNotificationInfo == null || (statusBarNotification6 = subscreenNotificationInfo.mSbn) == null) ? null : statusBarNotification6.getGroupKey(), notificationEntry3.mSbn.getGroupKey(), false)) {
                            String str = notificationEntry3.mKey;
                            arrayList15.add(str);
                            if (((SubscreenDeviceModelParent.MainListHashMapItem) subscreenDeviceModelParent.mMainListArrayHashMap.get(str)) != null) {
                                Log.d("S.S.N.", "updateGroupListArray parent - already group Item  - mainListItem != null : " + str);
                                linkedHashMap4.put(str, notificationEntry3);
                            } else {
                                SubscreenSubRoomNotification subscreenSubRoomNotification4 = subscreenDeviceModelParent.mSubRoomNotification;
                                Integer valueOf = (subscreenSubRoomNotification4 == null || (subscreenNotificationInfoManager9 = subscreenSubRoomNotification4.mNotificationInfoManager) == null) ? null : Integer.valueOf(subscreenNotificationInfoManager9.getGroupDataArraySize());
                                Intrinsics.checkNotNull(valueOf);
                                int intValue = valueOf.intValue();
                                int i4 = 0;
                                boolean z2 = false;
                                while (i4 < intValue) {
                                    SubscreenSubRoomNotification subscreenSubRoomNotification5 = subscreenDeviceModelParent.mSubRoomNotification;
                                    SubscreenNotificationInfo subscreenNotificationInfo2 = (subscreenSubRoomNotification5 == null || (subscreenNotificationInfoManager8 = subscreenSubRoomNotification5.mNotificationInfoManager) == null || (arrayList14 = subscreenNotificationInfoManager8.mGroupDataArray) == null) ? null : (SubscreenNotificationInfo) arrayList14.get(i4);
                                    if (StringsKt__StringsJVMKt.equals(subscreenNotificationInfo2 != null ? subscreenNotificationInfo2.mKey : null, str, false)) {
                                        Log.d("S.S.N.", "updateGroupListArray parent - already Item - searchItem : " + str);
                                        linkedHashMap4.put(str, notificationEntry3);
                                        z2 = true;
                                    }
                                    i4++;
                                    z2 = z2;
                                }
                                subscreenDeviceModelParent.putMainListArrayHashMap(notificationEntry3);
                                if (!z2) {
                                    SubscreenSubRoomNotification subscreenSubRoomNotification6 = subscreenDeviceModelParent.mSubRoomNotification;
                                    SubscreenNotificationInfo createItemsData = (subscreenSubRoomNotification6 == null || (subscreenNotificationInfoManager7 = subscreenSubRoomNotification6.mNotificationInfoManager) == null) ? null : subscreenNotificationInfoManager7.createItemsData(notificationEntry3.row);
                                    SubscreenSubRoomNotification subscreenSubRoomNotification7 = subscreenDeviceModelParent.mSubRoomNotification;
                                    if (subscreenSubRoomNotification7 != null && (subscreenNotificationInfoManager6 = subscreenSubRoomNotification7.mNotificationInfoManager) != null && (arrayList13 = subscreenNotificationInfoManager6.mGroupDataArray) != null) {
                                        arrayList13.add(0, createItemsData);
                                    }
                                    SubscreenSubRoomNotification subscreenSubRoomNotification8 = subscreenDeviceModelParent.mSubRoomNotification;
                                    if (subscreenSubRoomNotification8 != null && (subscreenNotificationGroupAdapter2 = subscreenSubRoomNotification8.mNotificationGroupAdapter) != null) {
                                        subscreenNotificationGroupAdapter2.notifyItemInserted(0);
                                    }
                                    AbstractC0000x2c234b15.m3m("updateGroupListArray parent - add Item  : ", str, "S.S.N.");
                                }
                            }
                        }
                    }
                    Iterator it4 = arrayList15.iterator();
                    while (it4.hasNext()) {
                        linkedHashMap5.remove((String) it4.next());
                    }
                    SubscreenSubRoomNotification subscreenSubRoomNotification9 = subscreenDeviceModelParent.mSubRoomNotification;
                    Integer valueOf2 = (subscreenSubRoomNotification9 == null || (subscreenNotificationInfoManager5 = subscreenSubRoomNotification9.mNotificationInfoManager) == null) ? null : Integer.valueOf(subscreenNotificationInfoManager5.getGroupDataArraySize());
                    Intrinsics.checkNotNull(valueOf2);
                    int intValue2 = valueOf2.intValue();
                    int i5 = 0;
                    while (true) {
                        if (i5 >= intValue2) {
                            break;
                        }
                        SubscreenSubRoomNotification subscreenSubRoomNotification10 = subscreenDeviceModelParent.mSubRoomNotification;
                        SubscreenNotificationInfo subscreenNotificationInfo3 = (subscreenSubRoomNotification10 == null || (subscreenNotificationInfoManager4 = subscreenSubRoomNotification10.mNotificationInfoManager) == null || (arrayList12 = subscreenNotificationInfoManager4.mGroupDataArray) == null) ? null : (SubscreenNotificationInfo) arrayList12.get(i5);
                        String str2 = subscreenNotificationInfo3 != null ? subscreenNotificationInfo3.mKey : null;
                        NotificationEntry notificationEntry4 = (NotificationEntry) linkedHashMap4.get(str2);
                        if (notificationEntry4 != null) {
                            SubscreenSubRoomNotification subscreenSubRoomNotification11 = subscreenDeviceModelParent.mSubRoomNotification;
                            SubscreenNotificationInfo createItemsData2 = (subscreenSubRoomNotification11 == null || (subscreenNotificationInfoManager3 = subscreenSubRoomNotification11.mNotificationInfoManager) == null) ? null : subscreenNotificationInfoManager3.createItemsData(notificationEntry4.row);
                            SubscreenSubRoomNotification subscreenSubRoomNotification12 = subscreenDeviceModelParent.mSubRoomNotification;
                            if (subscreenSubRoomNotification12 != null && (subscreenNotificationInfoManager2 = subscreenSubRoomNotification12.mNotificationInfoManager) != null && (arrayList11 = subscreenNotificationInfoManager2.mGroupDataArray) != null) {
                            }
                            SubscreenSubRoomNotification subscreenSubRoomNotification13 = subscreenDeviceModelParent.mSubRoomNotification;
                            if (subscreenSubRoomNotification13 != null && (subscreenNotificationGroupAdapter = subscreenSubRoomNotification13.mNotificationGroupAdapter) != null) {
                                subscreenNotificationGroupAdapter.notifyItemChanged(i5 + (!(subscreenDeviceModelParent instanceof SubscreenDeviceModelB5) ? 1 : 0));
                            }
                            Log.d("S.S.N.", "updateGroupListArray parent - update Item  : " + notificationEntry4.mKey);
                            TypeIntrinsics.asMutableMap(linkedHashMap4).remove(str2);
                        } else {
                            i5++;
                        }
                    }
                }
            }
            SubscreenDeviceModelParent deviceModel = SubscreenSubRoomNotification.getDeviceModel();
            if (!deviceModel.notiShowBlocked && deviceModel.isSubScreen()) {
                boolean z3 = deviceModel.mIsChangedToFoldState;
                LinkedHashMap linkedHashMap6 = deviceModel.mMainListUpdateItemHashMap;
                LinkedHashMap linkedHashMap7 = deviceModel.mMainListArrayHashMap;
                LinkedHashMap linkedHashMap8 = deviceModel.mMainListRemoveEntryHashMap;
                LinkedHashMap linkedHashMap9 = deviceModel.mMainListAddEntryHashMap;
                if (z3 && deviceModel.mIsKeyguardStateWhenAddLockscreenNotificationInfoArray) {
                    Log.d("S.S.N.", "updateMainListArray - changeFoldState");
                    linkedHashMap7.clear();
                    linkedHashMap6.clear();
                    linkedHashMap9.clear();
                    if (!deviceModel.mIsNotificationRemoved) {
                        linkedHashMap8.clear();
                    }
                    deviceModel.mIsChangedToFoldState = false;
                }
                if (linkedHashMap7.isEmpty() || deviceModel.mIsUpdatedAllMainList) {
                    LinkedHashMap linkedHashMap10 = linkedHashMap8;
                    if (deviceModel.mIsUpdatedAllMainList) {
                        deviceModel.mIsUpdatedAllMainList = false;
                    }
                    boolean isShownGroup = deviceModel.isShownGroup();
                    HashSet hashSet = deviceModel.mNotiKeySet;
                    if (!isShownGroup) {
                        SubscreenSubRoomNotification subscreenSubRoomNotification14 = deviceModel.mSubRoomNotification;
                        if (subscreenSubRoomNotification14 != null && (subscreenNotificationInfoManager = subscreenSubRoomNotification14.mNotificationInfoManager) != null) {
                            subscreenNotificationInfoManager.clearAllRecyclerViewItem();
                        }
                        linkedHashMap7.clear();
                        hashSet.clear();
                    }
                    int size = arrayList.size();
                    SubscreenSubRoomNotification subscreenSubRoomNotification15 = deviceModel.mSubRoomNotification;
                    if (subscreenSubRoomNotification15 != null && subscreenSubRoomNotification15.mNotificationInfoManager != null && (arrayList3 = SubscreenNotificationInfoManager.mLockscreenNotificationInfoArray) != null) {
                        arrayList3.clear();
                    }
                    Log.d("S.S.N.", "updateMainListArray allMap - size : " + size + ", isEmpty: " + linkedHashMap7.isEmpty());
                    Iterator it5 = arrayList.iterator();
                    while (it5.hasNext()) {
                        LockscreenNotificationInfo lockscreenNotificationInfo = (LockscreenNotificationInfo) it5.next();
                        Log.d("S.S.N.", "updateMainListArray allMap - key : " + lockscreenNotificationInfo.mKey + ", " + lockscreenNotificationInfo.mRow.mEntry);
                        if (deviceModel.checkBubbleLastHistoryReply(lockscreenNotificationInfo.mRow.mEntry)) {
                            AbstractC0000x2c234b15.m3m("updateMainListArray allMap - mBubbleReplyEntry: ", lockscreenNotificationInfo.mKey, "S.S.N.");
                        } else if (deviceModel.isBubbleNotificationSuppressed(lockscreenNotificationInfo.mRow.mEntry)) {
                            AbstractC0000x2c234b15.m3m("updateMainListArray allMap - isBubbleNotificationSuppressed: ", lockscreenNotificationInfo.mKey, "S.S.N.");
                        } else {
                            if (deviceModel.mIsNotificationRemoved) {
                                linkedHashMap2 = linkedHashMap10;
                                NotificationEntry notificationEntry5 = (NotificationEntry) linkedHashMap2.get(lockscreenNotificationInfo.mKey);
                                Log.d("S.S.N.", "updateMainListArray allMap - mIsNotificationRemoved - key :" + lockscreenNotificationInfo.mKey + " ,entry :" + notificationEntry5);
                                if (notificationEntry5 != null) {
                                    AbstractC0000x2c234b15.m3m("updateMainListArray allMap - mIsNotificationRemoved: ", lockscreenNotificationInfo.mKey, "S.S.N.");
                                    linkedHashMap2.remove(lockscreenNotificationInfo.mKey);
                                    linkedHashMap10 = linkedHashMap2;
                                }
                            } else {
                                linkedHashMap2 = linkedHashMap10;
                            }
                            SubscreenSubRoomNotification subscreenSubRoomNotification16 = deviceModel.mSubRoomNotification;
                            if (subscreenSubRoomNotification16 != null && subscreenSubRoomNotification16.mNotificationInfoManager != null && (arrayList2 = SubscreenNotificationInfoManager.mLockscreenNotificationInfoArray) != null) {
                                arrayList2.add(lockscreenNotificationInfo);
                            }
                            deviceModel.putMainListArrayHashMap(lockscreenNotificationInfo.mRow.mEntry);
                            hashSet.add(lockscreenNotificationInfo.mRow.mEntry.mKey);
                            if (((NotificationEntry) linkedHashMap9.get(lockscreenNotificationInfo.mKey)) != null) {
                                linkedHashMap9.remove(lockscreenNotificationInfo.mKey);
                            }
                            if (lockscreenNotificationInfo.mSbn.getNotification().isGroupSummary() && (notificationChildrenContainer = lockscreenNotificationInfo.mRow.mChildrenContainer) != null && notificationChildrenContainer.getNotificationChildCount() > 0) {
                                int min = Math.min(notificationChildrenContainer.getNotificationChildCount(), 8);
                                for (int i6 = 0; i6 < min; i6++) {
                                    NotificationEntry notificationEntry6 = ((ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer.mAttachedChildren).get(i6)).mEntry;
                                    if (Intrinsics.areEqual(lockscreenNotificationInfo.mSbn.getGroupKey(), notificationEntry6.mSbn.getGroupKey())) {
                                        StringBuilder sb = new StringBuilder("updateMainListArray allMap child : ");
                                        String str3 = notificationEntry6.mKey;
                                        sb.append(str3);
                                        Log.d("S.S.N.", sb.toString());
                                        deviceModel.putMainListArrayHashMap(notificationEntry6);
                                        hashSet.add(str3);
                                    }
                                }
                            }
                            linkedHashMap10 = linkedHashMap2;
                        }
                    }
                    linkedHashMap = linkedHashMap10;
                    SubscreenSubRoomNotification subscreenSubRoomNotification17 = deviceModel.mSubRoomNotification;
                    if ((subscreenSubRoomNotification17 != null ? Boolean.valueOf(subscreenSubRoomNotification17.mIsInNotiRoom) : null) != null) {
                        SubscreenSubRoomNotification subscreenSubRoomNotification18 = deviceModel.mSubRoomNotification;
                        boolean z4 = subscreenSubRoomNotification18 != null;
                        if (z4 && subscreenSubRoomNotification18 != null && (subscreenNotificationListAdapter = subscreenSubRoomNotification18.mNotificationListAdapter) != null) {
                            subscreenNotificationListAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    int size2 = arrayList.size();
                    ArrayList arrayList16 = new ArrayList();
                    ArrayList arrayList17 = new ArrayList();
                    int i7 = 0;
                    while (i3 < size2) {
                        LockscreenNotificationInfo lockscreenNotificationInfo2 = (LockscreenNotificationInfo) arrayList.get(i3);
                        SubscreenDeviceModelParent.MainListHashMapItem mainListHashMapItem = (SubscreenDeviceModelParent.MainListHashMapItem) linkedHashMap7.get(lockscreenNotificationInfo2.mKey);
                        NotificationEntry notificationEntry7 = mainListHashMapItem != null ? mainListHashMapItem.mEntry : null;
                        GroupMembershipManager groupMembershipManager = deviceModel.mGroupMembershipManager;
                        if (notificationEntry7 == null) {
                            if (deviceModel.mIsNotificationRemoved && ((NotificationEntry) linkedHashMap8.get(lockscreenNotificationInfo2.mKey)) != null) {
                                AbstractC0000x2c234b15.m3m("updateMainListArray add Item - mIsNotificationRemoved: ", lockscreenNotificationInfo2.mKey, "S.S.N.");
                                linkedHashMap8.remove(lockscreenNotificationInfo2.mKey);
                            } else if (deviceModel.checkBubbleLastHistoryReply(lockscreenNotificationInfo2.mRow.mEntry)) {
                                AbstractC0000x2c234b15.m3m("updateMainListArray add Item - mBubbleReplyEntry: ", lockscreenNotificationInfo2.mKey, "S.S.N.");
                                deviceModel.notifyListAdapterItemRemoved(lockscreenNotificationInfo2.mRow.mEntry);
                            } else if (deviceModel.isBubbleNotificationSuppressed(lockscreenNotificationInfo2.mRow.mEntry)) {
                                AbstractC0000x2c234b15.m3m("updateMainListArray add Item - isBubbleNotificationSuppressed: ", lockscreenNotificationInfo2.mKey, "S.S.N.");
                            } else {
                                boolean isImportantConversation = lockscreenNotificationInfo2.mRow.mEntry.getChannel().isImportantConversation();
                                if (deviceModel.isShownGroup() || isImportantConversation) {
                                    i = size2;
                                } else {
                                    SubscreenSubRoomNotification subscreenSubRoomNotification19 = deviceModel.mSubRoomNotification;
                                    Integer valueOf3 = (subscreenSubRoomNotification19 == null || subscreenSubRoomNotification19.mNotificationInfoManager == null) ? null : Integer.valueOf(SubscreenNotificationInfoManager.getNotificationInfoArraySize());
                                    Intrinsics.checkNotNull(valueOf3);
                                    int intValue3 = valueOf3.intValue();
                                    while (true) {
                                        if (i7 >= intValue3) {
                                            i = size2;
                                            break;
                                        }
                                        SubscreenSubRoomNotification subscreenSubRoomNotification20 = deviceModel.mSubRoomNotification;
                                        LockscreenNotificationInfo lockscreenNotificationInfo3 = (subscreenSubRoomNotification20 == null || subscreenSubRoomNotification20.mNotificationInfoManager == null || (arrayList10 = SubscreenNotificationInfoManager.mLockscreenNotificationInfoArray) == null) ? null : (LockscreenNotificationInfo) arrayList10.get(i7);
                                        i = size2;
                                        Boolean valueOf4 = (lockscreenNotificationInfo3 == null || (statusBarNotification5 = lockscreenNotificationInfo3.mSbn) == null || (notification3 = statusBarNotification5.getNotification()) == null) ? null : Boolean.valueOf(notification3.isGroupSummary());
                                        if (valueOf4 == null || !valueOf4.booleanValue()) {
                                            i2 = intValue3;
                                        } else {
                                            i2 = intValue3;
                                            if (lockscreenNotificationInfo2.mSbn.getGroupKey().equals((lockscreenNotificationInfo3 == null || (statusBarNotification4 = lockscreenNotificationInfo3.mSbn) == null) ? null : statusBarNotification4.getGroupKey())) {
                                                AbstractC0000x2c234b15.m3m("updateMainListArray add Item - already group Item: ", lockscreenNotificationInfo2.mKey, "S.S.N.");
                                                linkedHashMap9.remove(lockscreenNotificationInfo2.mKey);
                                                NotificationChildrenContainer notificationChildrenContainer2 = (lockscreenNotificationInfo3 == null || (expandableNotificationRow3 = lockscreenNotificationInfo3.mRow) == null) ? null : expandableNotificationRow3.mChildrenContainer;
                                                if (notificationChildrenContainer2 == null || notificationChildrenContainer2.getNotificationChildCount() == 0) {
                                                    Log.d("S.S.N.", "updateMainListArray add Item - remove old summary because it is empty: " + (lockscreenNotificationInfo3 != null ? lockscreenNotificationInfo3.mKey : null));
                                                    arrayList17.add(Integer.valueOf(i7));
                                                } else {
                                                    z = true;
                                                }
                                            }
                                        }
                                        i7++;
                                        size2 = i;
                                        intValue3 = i2;
                                    }
                                    z = false;
                                }
                                AbstractC0000x2c234b15.m3m("updateMainListArray parent add Item: ", lockscreenNotificationInfo2.mKey, "S.S.N.");
                                deviceModel.putMainListArrayHashMap(lockscreenNotificationInfo2.mRow.mEntry);
                                arrayList16.add(Integer.valueOf(i3));
                                if (lockscreenNotificationInfo2.mSbn.getNotification().isGroupSummary()) {
                                    AbstractC0000x2c234b15.m3m("add - updateMainListArray parent Group child remove Item: ", lockscreenNotificationInfo2.mKey, "S.S.N.");
                                    SubscreenSubRoomNotification subscreenSubRoomNotification21 = deviceModel.mSubRoomNotification;
                                    Integer valueOf5 = (subscreenSubRoomNotification21 == null || subscreenSubRoomNotification21.mNotificationInfoManager == null) ? null : Integer.valueOf(SubscreenNotificationInfoManager.getNotificationInfoArraySize());
                                    Intrinsics.checkNotNull(valueOf5);
                                    int intValue4 = valueOf5.intValue();
                                    int i8 = 0;
                                    while (i8 < intValue4) {
                                        SubscreenSubRoomNotification subscreenSubRoomNotification22 = deviceModel.mSubRoomNotification;
                                        LockscreenNotificationInfo lockscreenNotificationInfo4 = (subscreenSubRoomNotification22 == null || subscreenSubRoomNotification22.mNotificationInfoManager == null || (arrayList9 = SubscreenNotificationInfoManager.mLockscreenNotificationInfoArray) == null) ? null : (LockscreenNotificationInfo) arrayList9.get(i8);
                                        int i9 = intValue4;
                                        if (lockscreenNotificationInfo2.mSbn.getGroupKey().equals((lockscreenNotificationInfo4 == null || (statusBarNotification3 = lockscreenNotificationInfo4.mSbn) == null) ? null : statusBarNotification3.getGroupKey())) {
                                            Boolean valueOf6 = (lockscreenNotificationInfo4 == null || (expandableNotificationRow2 = lockscreenNotificationInfo4.mRow) == null || (notificationEntry2 = expandableNotificationRow2.mEntry) == null || (channel2 = notificationEntry2.getChannel()) == null) ? null : Boolean.valueOf(channel2.isImportantConversation());
                                            Intrinsics.checkNotNull(valueOf6);
                                            if (valueOf6.booleanValue()) {
                                                AbstractC0000x2c234b15.m3m("add - updateMainListArray parent Group child important: ", lockscreenNotificationInfo4.mKey, "S.S.N.");
                                            } else {
                                                arrayList17.add(Integer.valueOf(i8));
                                            }
                                        }
                                        i8++;
                                        intValue4 = i9;
                                    }
                                    ArrayList arrayList18 = new ArrayList();
                                    Iterator it6 = linkedHashMap9.entrySet().iterator();
                                    while (it6.hasNext()) {
                                        NotificationEntry notificationEntry8 = (NotificationEntry) ((Map.Entry) it6.next()).getValue();
                                        if (notificationEntry8.isChildInGroup()) {
                                            Iterator it7 = it6;
                                            if (lockscreenNotificationInfo2.mKey.equals((groupMembershipManager == null || (groupSummary2 = ((GroupMembershipManagerImpl) groupMembershipManager).getGroupSummary(notificationEntry8)) == null) ? null : groupSummary2.mKey)) {
                                                arrayList18.add(notificationEntry8.mKey);
                                                deviceModel.putMainListArrayHashMap(notificationEntry8);
                                            }
                                            it6 = it7;
                                        }
                                    }
                                    Iterator it8 = arrayList18.iterator();
                                    while (it8.hasNext()) {
                                        linkedHashMap9.remove((String) it8.next());
                                    }
                                }
                                if (linkedHashMap9 != null && linkedHashMap9.get(lockscreenNotificationInfo2.mKey) != null) {
                                    linkedHashMap9.remove(lockscreenNotificationInfo2.mKey);
                                }
                                linkedHashMap3 = linkedHashMap8;
                            }
                            i = size2;
                            linkedHashMap3 = linkedHashMap8;
                        } else {
                            i = size2;
                            String str4 = notificationEntry7.mKey;
                            NotificationEntry notificationEntry9 = (NotificationEntry) linkedHashMap6.get(str4);
                            if (notificationEntry9 != null) {
                                SubscreenSubRoomNotification subscreenSubRoomNotification23 = deviceModel.mSubRoomNotification;
                                Integer valueOf7 = (subscreenSubRoomNotification23 == null || subscreenSubRoomNotification23.mNotificationInfoManager == null) ? null : Integer.valueOf(SubscreenNotificationInfoManager.getNotificationInfoArraySize());
                                Intrinsics.checkNotNull(valueOf7);
                                int intValue5 = valueOf7.intValue();
                                int i10 = 0;
                                while (i10 < intValue5) {
                                    int i11 = intValue5;
                                    SubscreenSubRoomNotification subscreenSubRoomNotification24 = deviceModel.mSubRoomNotification;
                                    LockscreenNotificationInfo lockscreenNotificationInfo5 = (subscreenSubRoomNotification24 == null || subscreenSubRoomNotification24.mNotificationInfoManager == null || (arrayList8 = SubscreenNotificationInfoManager.mLockscreenNotificationInfoArray) == null) ? null : (LockscreenNotificationInfo) arrayList8.get(i10);
                                    linkedHashMap3 = linkedHashMap8;
                                    NotificationEntry notificationEntry10 = notificationEntry9;
                                    if (StringsKt__StringsJVMKt.equals(lockscreenNotificationInfo5 != null ? lockscreenNotificationInfo5.mKey : null, notificationEntry9.mKey, false)) {
                                        SubscreenSubRoomNotification subscreenSubRoomNotification25 = deviceModel.mSubRoomNotification;
                                        if (subscreenSubRoomNotification25 != null && (subscreenNotificationListAdapter6 = subscreenSubRoomNotification25.mNotificationListAdapter) != null) {
                                            subscreenNotificationListAdapter6.notifyItemChanged(i10);
                                        }
                                        linkedHashMap6.remove(str4);
                                        ArrayList arrayList19 = new ArrayList();
                                        it = linkedHashMap9.entrySet().iterator();
                                        while (it.hasNext()) {
                                            NotificationEntry notificationEntry11 = (NotificationEntry) ((Map.Entry) it.next()).getValue();
                                            if (notificationEntry11.isChildInGroup()) {
                                                if (str4.equals((groupMembershipManager == null || (groupSummary = ((GroupMembershipManagerImpl) groupMembershipManager).getGroupSummary(notificationEntry11)) == null) ? null : groupSummary.mKey)) {
                                                    SubscreenSubRoomNotification subscreenSubRoomNotification26 = deviceModel.mSubRoomNotification;
                                                    if (subscreenSubRoomNotification26 != null && (subscreenNotificationListAdapter5 = subscreenSubRoomNotification26.mNotificationListAdapter) != null) {
                                                        subscreenNotificationListAdapter5.notifyItemChanged(i3);
                                                    }
                                                    arrayList19.add(notificationEntry11.mKey);
                                                    deviceModel.putMainListArrayHashMap(notificationEntry11);
                                                }
                                            }
                                        }
                                        it2 = arrayList19.iterator();
                                        while (it2.hasNext()) {
                                            linkedHashMap9.remove((String) it2.next());
                                        }
                                        if (!lockscreenNotificationInfo2.mSbn.getNotification().isGroupSummary()) {
                                            AbstractC0000x2c234b15.m3m("update - updateMainListArray parent Group child remove Item: ", lockscreenNotificationInfo2.mKey, "S.S.N.");
                                            SubscreenSubRoomNotification subscreenSubRoomNotification27 = deviceModel.mSubRoomNotification;
                                            Integer valueOf8 = (subscreenSubRoomNotification27 == null || subscreenSubRoomNotification27.mNotificationInfoManager == null) ? null : Integer.valueOf(SubscreenNotificationInfoManager.getNotificationInfoArraySize());
                                            Intrinsics.checkNotNull(valueOf8);
                                            int intValue6 = valueOf8.intValue();
                                            boolean z5 = false;
                                            for (int i12 = 0; i12 < intValue6; i12++) {
                                                SubscreenSubRoomNotification subscreenSubRoomNotification28 = deviceModel.mSubRoomNotification;
                                                LockscreenNotificationInfo lockscreenNotificationInfo6 = (subscreenSubRoomNotification28 == null || subscreenSubRoomNotification28.mNotificationInfoManager == null || (arrayList7 = SubscreenNotificationInfoManager.mLockscreenNotificationInfoArray) == null) ? null : (LockscreenNotificationInfo) arrayList7.get(i12);
                                                Boolean valueOf9 = (lockscreenNotificationInfo6 == null || (statusBarNotification2 = lockscreenNotificationInfo6.mSbn) == null || (notification2 = statusBarNotification2.getNotification()) == null) ? null : Boolean.valueOf(notification2.isGroupSummary());
                                                Intrinsics.checkNotNull(valueOf9);
                                                if (!valueOf9.booleanValue()) {
                                                    if (lockscreenNotificationInfo2.mSbn.getGroupKey().equals((lockscreenNotificationInfo6 == null || (statusBarNotification = lockscreenNotificationInfo6.mSbn) == null) ? null : statusBarNotification.getGroupKey())) {
                                                        Boolean valueOf10 = (lockscreenNotificationInfo6 == null || (expandableNotificationRow = lockscreenNotificationInfo6.mRow) == null || (notificationEntry = expandableNotificationRow.mEntry) == null || (channel = notificationEntry.getChannel()) == null) ? null : Boolean.valueOf(channel.isImportantConversation());
                                                        Intrinsics.checkNotNull(valueOf10);
                                                        if (valueOf10.booleanValue()) {
                                                            AbstractC0000x2c234b15.m3m("update - updateMainListArray parent Group child important: ", lockscreenNotificationInfo6.mKey, "S.S.N.");
                                                        } else {
                                                            arrayList17.add(Integer.valueOf(i12));
                                                            z5 = true;
                                                        }
                                                    }
                                                }
                                            }
                                            if (z5) {
                                                arrayList16.add(Integer.valueOf(i3));
                                            }
                                        }
                                    } else {
                                        i10++;
                                        intValue5 = i11;
                                        linkedHashMap8 = linkedHashMap3;
                                        notificationEntry9 = notificationEntry10;
                                    }
                                }
                            }
                            linkedHashMap3 = linkedHashMap8;
                            ArrayList arrayList192 = new ArrayList();
                            it = linkedHashMap9.entrySet().iterator();
                            while (it.hasNext()) {
                            }
                            it2 = arrayList192.iterator();
                            while (it2.hasNext()) {
                            }
                            if (!lockscreenNotificationInfo2.mSbn.getNotification().isGroupSummary()) {
                            }
                        }
                        i3++;
                        i7 = 0;
                        size2 = i;
                        linkedHashMap8 = linkedHashMap3;
                    }
                    LinkedHashMap linkedHashMap11 = linkedHashMap8;
                    if (arrayList17.size() > 1) {
                        Collections.sort(arrayList17);
                    }
                    for (int size3 = arrayList17.size() - 1; -1 < size3; size3 += -1) {
                        int intValue7 = ((Number) arrayList17.get(size3)).intValue();
                        SubscreenSubRoomNotification subscreenSubRoomNotification29 = deviceModel.mSubRoomNotification;
                        LockscreenNotificationInfo lockscreenNotificationInfo7 = (subscreenSubRoomNotification29 == null || subscreenSubRoomNotification29.mNotificationInfoManager == null || (arrayList6 = SubscreenNotificationInfoManager.mLockscreenNotificationInfoArray) == null) ? null : (LockscreenNotificationInfo) arrayList6.get(intValue7);
                        SubscreenSubRoomNotification subscreenSubRoomNotification30 = deviceModel.mSubRoomNotification;
                        if (subscreenSubRoomNotification30 != null && subscreenSubRoomNotification30.mNotificationInfoManager != null) {
                            StringBuilder m1m = AbstractC0000x2c234b15.m1m("removeLockscreenNotificationInfoItem : ", intValue7, " >>>>> currentThread : ");
                            m1m.append(Thread.currentThread());
                            Log.d("SubscreenNotificationInfoManager", m1m.toString());
                            ArrayList arrayList20 = SubscreenNotificationInfoManager.mLockscreenNotificationInfoArray;
                            if (arrayList20 != null) {
                                arrayList20.remove(intValue7);
                            }
                        }
                        SubscreenSubRoomNotification subscreenSubRoomNotification31 = deviceModel.mSubRoomNotification;
                        if (subscreenSubRoomNotification31 != null && (subscreenNotificationListAdapter4 = subscreenSubRoomNotification31.mNotificationListAdapter) != null) {
                            subscreenNotificationListAdapter4.notifyItemRemoved(intValue7);
                        }
                        Log.d("S.S.N.", "updateMainListArray parent remove Item: " + (lockscreenNotificationInfo7 != null ? lockscreenNotificationInfo7.mKey : null) + ", index : " + intValue7);
                    }
                    Iterator it9 = arrayList16.iterator();
                    while (it9.hasNext()) {
                        Integer num = (Integer) it9.next();
                        LockscreenNotificationInfo lockscreenNotificationInfo8 = (LockscreenNotificationInfo) arrayList.get(num.intValue());
                        int intValue8 = num.intValue();
                        SubscreenSubRoomNotification subscreenSubRoomNotification32 = deviceModel.mSubRoomNotification;
                        Integer valueOf11 = (subscreenSubRoomNotification32 == null || subscreenSubRoomNotification32.mNotificationInfoManager == null) ? null : Integer.valueOf(SubscreenNotificationInfoManager.getNotificationInfoArraySize());
                        Intrinsics.checkNotNull(valueOf11);
                        if (intValue8 > valueOf11.intValue()) {
                            Log.d("S.S.N.", "updateMainListArray parent add Item last postion: " + num);
                            SubscreenSubRoomNotification subscreenSubRoomNotification33 = deviceModel.mSubRoomNotification;
                            if (subscreenSubRoomNotification33 != null && subscreenSubRoomNotification33.mNotificationInfoManager != null && (arrayList5 = SubscreenNotificationInfoManager.mLockscreenNotificationInfoArray) != null) {
                                arrayList5.add(lockscreenNotificationInfo8);
                            }
                        } else {
                            SubscreenSubRoomNotification subscreenSubRoomNotification34 = deviceModel.mSubRoomNotification;
                            if (subscreenSubRoomNotification34 != null && subscreenSubRoomNotification34.mNotificationInfoManager != null && (arrayList4 = SubscreenNotificationInfoManager.mLockscreenNotificationInfoArray) != null) {
                                arrayList4.add(num.intValue(), lockscreenNotificationInfo8);
                            }
                        }
                        SubscreenSubRoomNotification subscreenSubRoomNotification35 = deviceModel.mSubRoomNotification;
                        if (subscreenSubRoomNotification35 != null && (subscreenNotificationListAdapter3 = subscreenSubRoomNotification35.mNotificationListAdapter) != null) {
                            subscreenNotificationListAdapter3.notifyItemInserted(num.intValue());
                        }
                    }
                    if (!deviceModel.isShownGroup() && (subscreenSubRoomNotification = deviceModel.mSubRoomNotification) != null && (subscreenNotificationListAdapter2 = subscreenSubRoomNotification.mNotificationListAdapter) != null) {
                        Animator animator = subscreenNotificationListAdapter2.mFooterAnimator;
                        if (animator != null) {
                            animator.cancel();
                            subscreenNotificationListAdapter2.mFooterAnimator = null;
                        }
                        if (subscreenNotificationListAdapter2.mFooterViewHolder != null) {
                            subscreenNotificationListAdapter2.mNotificationInfoManager.getClass();
                            if (SubscreenNotificationInfoManager.getNotificationInfoArraySize() != 0) {
                                subscreenNotificationListAdapter2.mNotificationInfoManager.getClass();
                                if (SubscreenNotificationInfoManager.checkRemoveNotification()) {
                                    if (subscreenNotificationListAdapter2.mFooterViewHolder.mClearAllLayout.getVisibility() == 8) {
                                        final int i13 = 0;
                                        final int i14 = 1;
                                        subscreenNotificationListAdapter2.mFooterAnimator = subscreenNotificationListAdapter2.mNotificationAnimatorManager.alphaViewAnimated(subscreenNotificationListAdapter2.mFooterViewHolder.mClearAllLayout, new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationListAdapter$$ExternalSyntheticLambda0
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                switch (i14) {
                                                    case 0:
                                                        SubscreenNotificationListAdapter subscreenNotificationListAdapter7 = subscreenNotificationListAdapter2;
                                                        subscreenNotificationListAdapter7.mFooterViewHolder.mClearAllLayout.setVisibility(i13);
                                                        break;
                                                    default:
                                                        SubscreenNotificationListAdapter subscreenNotificationListAdapter8 = subscreenNotificationListAdapter2;
                                                        subscreenNotificationListAdapter8.mFooterViewHolder.mClearAllLayout.setVisibility(i13);
                                                        break;
                                                }
                                            }
                                        }, 300L, 0.0f, 1.0f);
                                    } else {
                                        subscreenNotificationListAdapter2.mFooterViewHolder.mClearAllLayout.setAlpha(1.0f);
                                        subscreenNotificationListAdapter2.mFooterViewHolder.mClearAllLayout.setVisibility(0);
                                    }
                                    subscreenNotificationListAdapter2.mFooterViewHolder.mClearAllLayout.setEnabled(true);
                                    subscreenNotificationListAdapter2.mFooterViewHolder.mClearAllLayout.setAlpha(1.0f);
                                }
                            }
                            if (subscreenNotificationListAdapter2.mFooterViewHolder.mClearAllLayout.getVisibility() == 0) {
                                final int i15 = 8;
                                final int i16 = 0;
                                subscreenNotificationListAdapter2.mFooterAnimator = subscreenNotificationListAdapter2.mNotificationAnimatorManager.alphaViewAnimated(subscreenNotificationListAdapter2.mFooterViewHolder.mClearAllLayout, new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationListAdapter$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        switch (i16) {
                                            case 0:
                                                SubscreenNotificationListAdapter subscreenNotificationListAdapter7 = subscreenNotificationListAdapter2;
                                                subscreenNotificationListAdapter7.mFooterViewHolder.mClearAllLayout.setVisibility(i15);
                                                break;
                                            default:
                                                SubscreenNotificationListAdapter subscreenNotificationListAdapter8 = subscreenNotificationListAdapter2;
                                                subscreenNotificationListAdapter8.mFooterViewHolder.mClearAllLayout.setVisibility(i15);
                                                break;
                                        }
                                    }
                                }, 300L, 1.0f, 0.0f);
                            } else {
                                subscreenNotificationListAdapter2.mFooterViewHolder.mClearAllLayout.setAlpha(0.0f);
                                subscreenNotificationListAdapter2.mFooterViewHolder.mClearAllLayout.setVisibility(8);
                            }
                        }
                    }
                    linkedHashMap = linkedHashMap11;
                }
                if (deviceModel.mIsNotificationRemoved) {
                    deviceModel.mIsNotificationRemoved = false;
                    linkedHashMap.clear();
                }
            }
        }

        @Override // com.android.systemui.statusbar.LockscreenNotificationManager.Callback
        public final void onNotificationTypeChanged(int i) {
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.notification.SubscreenSubRoomNotification$1 */
    public final class C27821 extends RecyclerView.OnScrollListener {
        public C27821() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public final void onScrolled(RecyclerView recyclerView, int i, int i2) {
            SubscreenSubRoomNotification subscreenSubRoomNotification = SubscreenSubRoomNotification.this;
            if (subscreenSubRoomNotification.mIsScrollByMe && recyclerView.mScrollState == 0) {
                subscreenSubRoomNotification.mIsScrollByMe = false;
                recyclerView.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenSubRoomNotification$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SubscreenSubRoomNotification.this.getClass();
                        SubscreenSubRoomNotification.getDeviceModel().enableGoToTopButton();
                    }
                }, 300L);
            }
            subscreenSubRoomNotification.mNotificationInfoManager.getClass();
            boolean z = SubscreenNotificationInfoManager.getNotificationInfoArraySize() == 0;
            subscreenSubRoomNotification.mRecyclerViewFirstVisibleItemPosition = z ? -1 : ((LinearLayoutManager) recyclerView.getLayoutManager$1()).findFirstVisibleItemPosition();
            subscreenSubRoomNotification.mRecyclerViewLastVisibleItemPosition = z ? -1 : ((LinearLayoutManager) recyclerView.getLayoutManager$1()).findLastVisibleItemPosition();
            if (subscreenSubRoomNotification.mIsShownDetail) {
                SubscreenSubRoomNotification.getDeviceModel().showAIReply();
            }
        }
    }

    /* renamed from: -$$Nest$mreturnRemoteInput, reason: not valid java name */
    public static void m1706$$Nest$mreturnRemoteInput(SubscreenSubRoomNotification subscreenSubRoomNotification, String str, String str2, String str3) {
        subscreenSubRoomNotification.getClass();
        if (ServiceTuple.BASIC_STATUS_OPEN.equals(str3)) {
            subscreenSubRoomNotification.mNotificationDetailAdapter.getClass();
            ((CentralSurfacesImpl) ((CentralSurfaces) Dependency.get(CentralSurfaces.class))).checkRemoteInputRequest(str, str2);
            subscreenSubRoomNotification.mNotificationDetailAdapter.cleanAdapter();
            return;
        }
        if (!"send".equals(str3)) {
            if ("permissionCheck".equals(str3)) {
                subscreenSubRoomNotification.mNotificationDetailAdapter.mNeedToUnlock = true;
                return;
            } else {
                if ("dismiss".equals(str3)) {
                    subscreenSubRoomNotification.mNotificationDetailAdapter.cleanAdapter();
                    return;
                }
                return;
            }
        }
        ((SubscreenNotificationController) Dependency.get(SubscreenNotificationController.class)).replyNotification(str, str2);
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter = subscreenSubRoomNotification.mNotificationDetailAdapter;
        if (subscreenNotificationDetailAdapter.mSelectHolder == null) {
            Log.d("SubscreenNotificationDetailAdapter", " remove notification, but selection holder is null.");
        } else if (subscreenNotificationDetailAdapter.mSvoiceEmojiClicked) {
            Log.d("SubscreenNotificationDetailAdapter", " hide notification after svoice/emoji reply");
            subscreenNotificationDetailAdapter.mSelectHolder.getClass();
            ((SubscreenNotificationController) Dependency.get(SubscreenNotificationController.class)).mDeviceModel.hideDetailNotificationAnimated(300, true);
            subscreenNotificationDetailAdapter.mSelectNotificationInfo.mRow.mEntry.remoteInputText = "";
        } else {
            Log.d("SubscreenNotificationDetailAdapter", " remove notification by call back");
            SubscreenParentDetailItemViewHolder subscreenParentDetailItemViewHolder = subscreenNotificationDetailAdapter.mSelectHolder;
            subscreenParentDetailItemViewHolder.mAdapter.mNotificationInfoManager.removeNotification(subscreenParentDetailItemViewHolder.mInfo.mRow.mEntry);
            ((SubscreenNotificationController) Dependency.get(SubscreenNotificationController.class)).mDeviceModel.hideDetailNotificationAnimated(300, true);
        }
        subscreenSubRoomNotification.mNotificationDetailAdapter.cleanAdapter();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.statusbar.notification.SubscreenSubRoomNotification$3] */
    /* JADX WARN: Type inference failed for: r0v3, types: [android.content.BroadcastReceiver, com.android.systemui.statusbar.notification.SubscreenSubRoomNotification$5] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.notification.SubscreenSubRoomNotification$2] */
    /* JADX WARN: Type inference failed for: r2v1, types: [android.content.BroadcastReceiver, com.android.systemui.statusbar.notification.SubscreenSubRoomNotification$4] */
    private SubscreenSubRoomNotification() {
        ?? r2 = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.notification.SubscreenSubRoomNotification.4
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                Objects.toString(intent);
                if ("com.samsung.android.action.RETURN_REMOTE_INPUT".equals(intent.getAction())) {
                    SubscreenSubRoomNotification.m1706$$Nest$mreturnRemoteInput(SubscreenSubRoomNotification.this, intent.getStringExtra("key"), intent.getStringExtra("return"), intent.getStringExtra("state"));
                }
            }
        };
        this.mRemoteInputEmojiActionBroadcastReceiver = r2;
        ?? r0 = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.notification.SubscreenSubRoomNotification.5
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                Objects.toString(intent);
                if ("com.samsung.android.action.RETURN_REMOTE_INPUT_VOICE".equals(intent.getAction())) {
                    SubscreenSubRoomNotification.m1706$$Nest$mreturnRemoteInput(SubscreenSubRoomNotification.this, intent.getStringExtra("key"), intent.getStringExtra("return"), intent.getStringExtra("state"));
                }
            }
        };
        this.mRemoteInputVoiceActionBroadcastReceiver = r0;
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.subscreen_notification_main, (ViewGroup) null);
        this.mMainView = inflate;
        this.mSubscreenMainLayout = (LinearLayout) inflate.findViewById(R.id.subscreen_main_layout);
        getDeviceModel().initMainHeaderView(this.mSubscreenMainLayout);
        if (SubscreenSubRoomNotificationTip.sInstance == null) {
            SubscreenSubRoomNotificationTip.sInstance = new SubscreenSubRoomNotificationTip();
        }
        this.mSubRoomNotificationTipAdapter = SubscreenSubRoomNotificationTip.sInstance;
        this.mNotificationListAdapter = SubscreenNotificationListAdapter.getInstance();
        this.mNotificationDetailAdapter = SubscreenNotificationDetailAdapter.getInstance();
        if (SubscreenNotificationGroupAdapter.sInstance == null) {
            SubscreenNotificationGroupAdapter.sInstance = new SubscreenNotificationGroupAdapter();
        }
        this.mNotificationGroupAdapter = SubscreenNotificationGroupAdapter.sInstance;
        this.mNotificationInfoManager = new SubscreenNotificationInfoManager(mContext, this.mNotificationListAdapter, this.mNotificationDetailAdapter, this.mNotificationGroupAdapter);
        Vibrator defaultVibrator = ((VibratorManager) mContext.getSystemService("vibrator_manager")).getDefaultVibrator();
        this.mVibrator = defaultVibrator;
        this.mNotificationTouchManager = new SubscreenSubRoomNotificaitonTouchManager(mContext, this.mNotificationInfoManager, defaultVibrator);
        this.mNotificationAnimatorManager = new SubscreenSubRoomNotificaitonAnimatorManager(this.mNotificationInfoManager, this.mVibrator);
        initRecyclerView();
        initAdapter(this.mSubRoomNotificationTipAdapter);
        initAdapter(this.mNotificationDetailAdapter);
        initAdapter(this.mNotificationListAdapter);
        initAdapter(this.mNotificationGroupAdapter);
        mContext.registerReceiverAsUser(r2, UserHandle.ALL, new IntentFilter("com.samsung.android.action.RETURN_REMOTE_INPUT"), "com.samsung.android.permission.RETURN_REMOTE_INPUT", null, 2);
        mContext.registerReceiverAsUser(r0, UserHandle.ALL, new IntentFilter("com.samsung.android.action.RETURN_REMOTE_INPUT_VOICE"), "com.samsung.android.permission.RETURN_REMOTE_INPUT_VOICE", null, 2);
    }

    public static SubscreenDeviceModelParent getDeviceModel() {
        return ((SubscreenNotificationController) Dependency.get(SubscreenNotificationController.class)).mDeviceModel;
    }

    public static SubscreenSubRoomNotification getInstance(Context context) {
        if (sInstance == null) {
            mContext = context;
            sInstance = new SubscreenSubRoomNotification();
        }
        return sInstance;
    }

    public final boolean getBixbyNotificationVisible(String str) {
        if (this.mRecyclerViewFirstVisibleItemPosition != -1 && this.mRecyclerViewLastVisibleItemPosition != -1) {
            for (int i = 0; i <= this.mNotificationRecyclerView.getChildCount(); i++) {
                View childAt = this.mNotificationRecyclerView.getChildAt(i);
                if (childAt != null) {
                    RecyclerView.ViewHolder childViewHolder = this.mNotificationRecyclerView.getChildViewHolder(childAt);
                    if (childViewHolder instanceof SubscreenParentItemViewHolder) {
                        if (((SubscreenParentItemViewHolder) childViewHolder).mInfo.mKey.equals(str)) {
                            return true;
                        }
                    } else if ((childViewHolder instanceof SubscreenParentDetailItemViewHolder) && ((SubscreenParentDetailItemViewHolder) childViewHolder).mInfo.mKey.equals(str)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final View getView(Context context) {
        return this.mMainView;
    }

    public final void hideDetailNotification() {
        if (this.mNotificationRecyclerView != null) {
            Log.e("SubscreenSubRoomNotification", "hideDetailNotification mIsShownGroup: " + this.mIsShownGroup);
            this.mIsShownDetail = false;
            if (!this.mIsShownGroup) {
                setListAdpater();
                getDeviceModel().updateMainHeaderViewVisibility(8);
            } else if (this.mNotificationInfoManager.getGroupDataArraySize() <= 1) {
                hideGroupNotification();
            } else {
                this.mNotificationRecyclerView.setAdapter(this.mNotificationGroupAdapter);
                this.mNotificationGroupAdapter.notifyDataSetChanged();
                getDeviceModel().initMainHeaderViewItems(mContext, this.mNotificationGroupAdapter.mSummaryInfo, false);
                getDeviceModel().updateMainHeaderViewVisibility(0);
            }
            scrollToSelectedPosition();
            this.mNotificationTouchManager.mItemViewSwipeEnabled = true;
            SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter = this.mNotificationDetailAdapter;
            subscreenNotificationDetailAdapter.mScrollInfo.mIsSendedQuickReply = false;
            subscreenNotificationDetailAdapter.dismissReplyButtons(true);
        }
    }

    public final void hideGroupNotification() {
        if (this.mNotificationRecyclerView == null || this.mNotificationGroupAdapter == null) {
            return;
        }
        Log.e("SubscreenSubRoomNotification", "hideGroupNotification");
        this.mIsShownGroup = false;
        this.mNotificationInfoManager.clearAllRecyclerViewItem();
        setListAdpater();
        this.mNotificationInfoManager.setShownGroup(this.mIsShownGroup);
        this.mNotificationInfoManager.mGroupDataArray.clear();
        scrollToSelectedPosition();
        getDeviceModel().updateMainHeaderViewVisibility(8);
    }

    public final void initAdapter(SubscreenParentAdapter subscreenParentAdapter) {
        subscreenParentAdapter.getClass();
        subscreenParentAdapter.mDeviceModel = ((SubscreenNotificationController) Dependency.get(SubscreenNotificationController.class)).mDeviceModel;
        subscreenParentAdapter.mNotificationRecyclerView = this.mNotificationRecyclerView;
        subscreenParentAdapter.mNotificationInfoManager = this.mNotificationInfoManager;
        subscreenParentAdapter.mNotificationAnimatorManager = this.mNotificationAnimatorManager;
        subscreenParentAdapter.mSubRoomNotification = this;
        subscreenParentAdapter.mContext = mContext;
    }

    public final void initData() {
        Log.e("SubscreenSubRoomNotification", "initData");
        if (NotiRune.NOTI_SUBSCREEN_GHOST_NOTIFICATION && this.mIsShownDetail) {
            ((SubscreenNotificationController) Dependency.get(SubscreenNotificationController.class)).hideDetailNotif();
        }
        SubscreenDeviceModelParent deviceModel = getDeviceModel();
        deviceModel.clearMainList();
        deviceModel.mController.notifPipeline.mShadeListBuilder.buildList();
        this.mIsShownGroup = false;
        this.mIsShownDetail = false;
        this.mNotificationInfoManager.setShownGroup(false);
        this.mNotificationInfoManager.clearAllRecyclerViewItem();
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter = this.mNotificationDetailAdapter;
        subscreenNotificationDetailAdapter.mScrollInfo.mIsSendedQuickReply = false;
        subscreenNotificationDetailAdapter.mSelectNotificationInfo = null;
        subscreenNotificationDetailAdapter.cleanAdapter();
        SubscreenSubRoomNotificaitonTouchManager subscreenSubRoomNotificaitonTouchManager = this.mNotificationTouchManager;
        int m11m = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m11m(subscreenSubRoomNotificaitonTouchManager.mContext);
        subscreenSubRoomNotificaitonTouchManager.mLayoutDirection = m11m;
        subscreenSubRoomNotificaitonTouchManager.mItemViewSwipeEnabled = true;
        SubscreenSubRoomNotificaitonTouchManager.C27801 c27801 = subscreenSubRoomNotificaitonTouchManager.mSimpleItemTouchCallBack;
        if (m11m == 1) {
            c27801.mDefaultSwipeDirs = 4;
        } else {
            c27801.mDefaultSwipeDirs = 8;
        }
        setListAdpater();
        this.mNotificationRecyclerView.scrollToPosition(0);
        this.mRecyclerViewFirstVisibleItemPosition = -1;
        this.mRecyclerViewLastVisibleItemPosition = -1;
        this.mNotificationDetailAdapter.dismissReplyButtons(true);
        getDeviceModel().updateMainHeaderViewVisibility(8);
        getDeviceModel().updateContentScroll();
        this.mSubscreenMainLayout.setAlpha(1.0f);
        this.mSubscreenMainLayout.setVisibility(0);
    }

    public final void initRecyclerView() {
        SubscreenRecyclerView subscreenRecyclerView = this.mNotificationRecyclerView;
        C27821 c27821 = this.mRecyclerViewScrollListener;
        if (subscreenRecyclerView != null) {
            subscreenRecyclerView.setLayoutManager(null);
            this.mNotificationRecyclerView.setAdapter(null);
            SubscreenRecyclerView subscreenRecyclerView2 = this.mNotificationRecyclerView;
            SubscreenSubRoomNotificaitonTouchManager.C27812 c27812 = this.mNotificationTouchManager.mOnItemTouchListener;
            subscreenRecyclerView2.mOnItemTouchListeners.remove(c27812);
            if (subscreenRecyclerView2.mInterceptingOnItemTouchListener == c27812) {
                subscreenRecyclerView2.mInterceptingOnItemTouchListener = null;
            }
            this.mNotificationRecyclerView.removeOnScrollListener(c27821);
            this.mNotificationRecyclerView = null;
        }
        SubscreenRecyclerView subscreenRecyclerView3 = (SubscreenRecyclerView) this.mMainView.findViewById(R.id.notification_list_recyclerview);
        this.mNotificationRecyclerView = subscreenRecyclerView3;
        subscreenRecyclerView3.setLayoutManager(this.linearLayoutManager);
        this.mNotificationRecyclerView.setNestedScrollingEnabled(false);
        SubscreenRecyclerView subscreenRecyclerView4 = this.mNotificationRecyclerView;
        subscreenRecyclerView4.mHasFixedSize = true;
        RecyclerView.Recycler recycler = subscreenRecyclerView4.mRecycler;
        recycler.mRequestedCacheMax = 7;
        recycler.updateViewCacheSize();
        getDeviceModel().enableGoToTopButton();
        SettingsHelper settingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
        settingsHelper.getClass();
        if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON && settingsHelper.mItemLists.get("cover_screen_show_notification_tip").getIntValue() == 1) {
            Log.d("SubscreenSubRoomNotification", "initTipData");
            this.mNotificationRecyclerView.setAdapter(this.mSubRoomNotificationTipAdapter);
        } else {
            setListAdpater();
        }
        this.mNotificationRecyclerView.mOnItemTouchListeners.add(this.mNotificationTouchManager.mOnItemTouchListener);
        this.mNotificationRecyclerView.addOnScrollListener(c27821);
        SubscreenRecyclerView subscreenRecyclerView5 = this.mNotificationRecyclerView;
        RecyclerView.ItemAnimator itemAnimator = subscreenRecyclerView5.mItemAnimator;
        if (itemAnimator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) itemAnimator).mSupportsChangeAnimations = false;
        }
        this.mNotificationTouchManager.mItemTouchHelper.attachToRecyclerView(subscreenRecyclerView5);
        getDeviceModel().setItemDecoration(this.mNotificationRecyclerView);
    }

    public final void notifyClockSubRoomRequest() {
        if (this.mStateChangeListener != null) {
            Log.e("SubscreenSubRoomNotification", "notifyClockSubRoomRequest");
            Bundle bundle = new Bundle();
            bundle.putString(SubRoom.EXTRA_FOCUS_REQUIRED, SubRoom.EXTRA_VALUE_CLOCK);
            this.mStateChangeListener.onStateChanged(bundle);
        }
    }

    public final void notifyNotificationSubRoomRequest() {
        if (this.mStateChangeListener != null) {
            Log.e("SubscreenSubRoomNotification", "notifyNotificationSubRoomRequest");
            Bundle bundle = new Bundle();
            bundle.putString(SubRoom.EXTRA_FOCUS_REQUIRED, SubRoom.EXTRA_VALUE_NOTIFICATION);
            this.mStateChangeListener.onStateChanged(bundle);
        }
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final void onCloseFinished() {
        Log.e("SubscreenSubRoomNotification", "onCloseFinished");
        boolean z = false;
        this.mIsInNotiRoom = false;
        SettingsHelper settingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
        settingsHelper.getClass();
        if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON && settingsHelper.mItemLists.get("cover_screen_show_notification_tip").getIntValue() == 1) {
            z = true;
        }
        if (z) {
            Log.d("SubscreenSubRoomNotification", "initTipData");
            this.mNotificationRecyclerView.setAdapter(this.mSubRoomNotificationTipAdapter);
        } else {
            initData();
        }
        SubscreenNotificationReplyActivity subscreenNotificationReplyActivity = ((SubscreenNotificationController) Dependency.get(SubscreenNotificationController.class)).replyActivity;
        if (subscreenNotificationReplyActivity != null) {
            subscreenNotificationReplyActivity.finish();
        }
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final void onOpenStarted() {
        Log.e("SubscreenSubRoomNotification", "onOpenStarted");
        if (this.mHasUnreadNoti) {
            SubscreenDeviceModelParent deviceModel = getDeviceModel();
            deviceModel.clearMainList();
            deviceModel.mController.notifPipeline.mShadeListBuilder.buildList();
        }
        this.mIsInNotiRoom = true;
        if (!this.mIsShownDetail) {
            this.mNotificationRecyclerView.scrollToPosition(0);
        }
        this.mNotificationRecyclerView.announceForAccessibility(mContext.getString(R.string.notification_info_package_block_text));
        updateNotificationState(null, 2);
        this.mNotificationListAdapter.notifyDataSetChanged();
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final void removeListener() {
        this.mStateChangeListener = null;
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final Bundle request(String str, Bundle bundle) {
        if (SubRoom.STATE_UNREAD_NOTIFICATION.equals(str)) {
            Bundle bundle2 = new Bundle();
            bundle2.putBoolean(SubRoom.EXTRA_HAS_UNREAD, this.mHasUnreadNoti);
            return bundle2;
        }
        if (SubRoom.STATE_POPUP_DISMISSED.equals(str)) {
            Log.d("SubscreenSubRoomNotification", "request() STATE_POPUP_DISMISSED ");
            this.mNotificationDetailAdapter.cleanAdapter();
        } else if (SubRoom.STATE_BIO_POPUP_CANCELED.equals(str)) {
            Log.d("SubscreenSubRoomNotification", "request() STATE_BIO_POPUP_CANCELED ");
            this.mNotificationDetailAdapter.cleanAdapter();
            getDeviceModel().initKeyguardActioninfo();
        }
        return bundle;
    }

    public final void scrollToSelectedPosition() {
        this.mIsScrollByMe = true;
        SubscreenNotificationInfoManager subscreenNotificationInfoManager = this.mNotificationInfoManager;
        String str = this.mRecyclerViewItemSelectKey;
        boolean z = subscreenNotificationInfoManager.mIsShownGroup;
        ArrayList arrayList = SubscreenNotificationInfoManager.mLockscreenNotificationInfoArray;
        ArrayList arrayList2 = subscreenNotificationInfoManager.mGroupDataArray;
        int i = 0;
        int size = z ? arrayList2.size() : arrayList != null ? arrayList.size() : 0;
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                break;
            }
            if ((subscreenNotificationInfoManager.mIsShownGroup ? ((SubscreenNotificationInfo) arrayList2.get(i2)).mKey : ((LockscreenNotificationInfo) arrayList.get(i2)).mKey).equals(str)) {
                SubscreenDeviceModelParent subscreenDeviceModelParent = subscreenNotificationInfoManager.mSubscreenNotificationController.mDeviceModel;
                subscreenDeviceModelParent.getClass();
                int i3 = 1 ^ (subscreenDeviceModelParent instanceof SubscreenDeviceModelB5 ? 1 : 0);
                if (subscreenNotificationInfoManager.mIsShownGroup) {
                    i2 += i3;
                }
                i = i2;
            } else {
                i2++;
            }
        }
        this.mNotificationRecyclerView.scrollToPosition(i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x001d, code lost:
    
        if (r0.mItemLists.get("cover_screen_show_notification_tip").getIntValue() == 1) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setListAdpater() {
        boolean z;
        SettingsHelper settingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
        settingsHelper.getClass();
        if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON) {
            z = true;
        }
        z = false;
        if (z) {
            return;
        }
        this.mNotificationRecyclerView.setAdapter(this.mNotificationListAdapter);
        this.mSubscreenMainLayout.setBackgroundColor(0);
        this.mNotificationListAdapter.notifyDataSetChanged();
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final void setListener(SubRoom.StateChangeListener stateChangeListener) {
        this.mStateChangeListener = stateChangeListener;
    }

    public final void showDetailNotification(SubscreenNotificationInfo subscreenNotificationInfo) {
        if (this.mNotificationRecyclerView == null || this.mNotificationDetailAdapter == null) {
            return;
        }
        Log.e("SubscreenSubRoomNotification", "showDetailNotification key : " + subscreenNotificationInfo.mKey);
        this.mNotificationDetailAdapter.dismissReplyButtons(false);
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter = this.mNotificationDetailAdapter;
        subscreenNotificationDetailAdapter.mSelectNotificationInfo = subscreenNotificationInfo;
        this.mNotificationRecyclerView.setAdapter(subscreenNotificationDetailAdapter);
        this.mNotificationDetailAdapter.notifyDataSetChanged();
        this.mNotificationTouchManager.mItemViewSwipeEnabled = false;
        this.mIsShownDetail = true;
        this.mRecyclerViewItemSelectKey = subscreenNotificationInfo.mKey;
        getDeviceModel().initSmartReplyStatus();
        getDeviceModel().initMainHeaderViewItems(mContext, subscreenNotificationInfo, false);
        getDeviceModel().updateMainHeaderViewVisibility(0);
        if (subscreenNotificationInfo.mIsMessagingStyle && (!subscreenNotificationInfo.mRow.needsRedaction() || !getDeviceModel().isNotShwonNotificationState(subscreenNotificationInfo.mRow.mEntry))) {
            SubscreenNotificationController subscreenNotificationController = (SubscreenNotificationController) Dependency.get(SubscreenNotificationController.class);
            NotificationEntry notificationEntry = subscreenNotificationInfo.mRow.mEntry;
            subscreenNotificationController.conversationNotificationManager.states.compute(notificationEntry.mKey, ConversationNotificationManager$resetCount$1.INSTANCE);
            ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
            if (expandableNotificationRow != null) {
                subscreenNotificationController.conversationNotificationManager.getClass();
                ConversationNotificationManager.resetBadgeUi(expandableNotificationRow);
            }
        }
        getDeviceModel().setDimOnMainBackground(this.mSubscreenMainLayout);
    }

    public final void startReplyActivity(int i, SubscreenNotificationInfo subscreenNotificationInfo) {
        Display display;
        Display[] displays = ((DisplayManager) mContext.getSystemService("display")).getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
        int length = displays.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                display = null;
                break;
            }
            display = displays[i2];
            if (display.getDisplayId() == 1) {
                break;
            } else {
                i2++;
            }
        }
        Log.d("SubscreenSubRoomNotification", "display: " + display);
        KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class);
        if (display != null) {
            String str = subscreenNotificationInfo.mKey;
            ActivityOptions makeBasic = ActivityOptions.makeBasic();
            makeBasic.setLaunchDisplayId(display.getDisplayId());
            Intent intent = new Intent();
            if (i == 1) {
                intent.setAction("samsung.honeyboard.honeyvoice.action.RECOGNIZE_SPEECH");
                intent.putExtra(SpeechRecognitionConst.Key.LOCALE, Locale.getDefault().toString());
                intent.putExtra("maxLength", subscreenNotificationInfo.mRemoteInputMaxLength);
                intent.putExtra("isSms", subscreenNotificationInfo.mRemoteInputIsSms);
                intent.putExtra(Account.SIGNATURE, subscreenNotificationInfo.mRemoteInputSignature);
                StringBuilder sb = new StringBuilder("voice started  ml : ");
                sb.append(subscreenNotificationInfo.mRemoteInputMaxLength);
                sb.append(" is : ");
                sb.append(subscreenNotificationInfo.mRemoteInputIsSms);
                sb.append(" s : ");
                ExifInterface$$ExternalSyntheticOutline0.m35m(sb, subscreenNotificationInfo.mRemoteInputSignature, "SubscreenSubRoomNotification");
            } else if (i == 2) {
                intent = new Intent("com.samsung.android.honeyboard.intent.action.COVER_EMOTICON", Uri.parse("honeyboard://cover-emoticon"));
                Log.d("SubscreenSubRoomNotification", "emoji started ");
            }
            Intent intent2 = intent;
            intent2.setFlags(276824064);
            intent2.putExtra("key", str);
            PendingIntent activityAsUser = PendingIntent.getActivityAsUser(mContext, 0, intent2, 201326592, makeBasic.toBundle(), UserHandle.CURRENT);
            Intent intent3 = new Intent();
            intent3.putExtra("runOnCover", true);
            intent3.putExtra("afterKeyguardGone", true);
            intent3.putExtra("ignoreKeyguardState", true);
            keyguardUpdateMonitor.dispatchStartSubscreenBiometric(intent3);
            SubRoom.StateChangeListener stateChangeListener = this.mStateChangeListener;
            if (stateChangeListener != null) {
                stateChangeListener.requestCoverPopup(activityAsUser, intent3);
            }
        }
    }

    public final void updateNotificationState(NotificationEntry notificationEntry, int i) {
        if (notificationEntry != null && ((SubscreenNotificationController) Dependency.get(SubscreenNotificationController.class)).shouldFilterOut(notificationEntry)) {
            Log.e("SubscreenSubRoomNotification", "updateNotificationState -  Filter out notification");
            return;
        }
        StringBuilder m1m = AbstractC0000x2c234b15.m1m("updateNotificationState -  action = ", i, " list size = ");
        ArrayList arrayList = this.mUnreadNotificationList;
        m1m.append(arrayList.size());
        Log.e("SubscreenSubRoomNotification", m1m.toString());
        if (i != 0) {
            if (i != 1) {
                if (i == 2) {
                    arrayList.clear();
                }
            } else if (notificationEntry != null) {
                arrayList.remove(notificationEntry.mKey);
            }
        } else if (notificationEntry != null) {
            Log.d("SubscreenSubRoomNotification", "updateNotificationState - mIsInNotiRoom = " + this.mIsInNotiRoom + " isOngoing = " + notificationEntry.mSbn.isOngoing() + " importance = " + notificationEntry.getImportance() + " isGroupSummary = " + notificationEntry.mSbn.getNotification().isGroupSummary());
            if (!this.mIsInNotiRoom && !notificationEntry.mSbn.isOngoing()) {
                if ((!(((SettingsHelper) Dependency.get(SettingsHelper.class)).mItemLists.get("lock_screen_show_silent_notifications").getIntValue() == 1) ? notificationEntry.getImportance() < 3 : notificationEntry.getImportance() < 2) && !notificationEntry.mSbn.getNotification().isGroupSummary()) {
                    String str = notificationEntry.mKey;
                    if (!arrayList.contains(str)) {
                        Log.d("SubscreenSubRoomNotification", "updateNotificationState - key is added");
                        arrayList.add(str);
                    }
                }
            }
        }
        boolean z = this.mHasUnreadNoti;
        boolean z2 = !arrayList.isEmpty();
        this.mHasUnreadNoti = z2;
        if (z != z2 && this.mStateChangeListener != null) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(SubRoom.EXTRA_HAS_UNREAD, z2);
            this.mStateChangeListener.onStateChanged(bundle);
        }
        StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("updateNotificationState - prevHasUnreadNoti = ", z, " mHasUnreadNoti = ");
        m49m.append(this.mHasUnreadNoti);
        m49m.append(" list size = ");
        m49m.append(arrayList.size());
        Log.e("SubscreenSubRoomNotification", m49m.toString());
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            Log.e("SubscreenSubRoomNotification", "updateNotificationState - mUnreadNotificationList = " + ((String) arrayList.get(i2)));
        }
    }
}
