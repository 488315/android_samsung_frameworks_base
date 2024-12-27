package com.android.systemui.statusbar.notification;

import android.app.ActivityOptions;
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
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
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
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.statusbar.notification.SubscreenNotificationDetailAdapter;
import com.android.systemui.statusbar.notification.SubscreenSubRoomNotificaitonTouchManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.knox.accounts.Account;
import com.samsung.android.sivs.ai.sdkcommon.asr.SpeechRecognitionConst;
import com.sec.ims.presence.ServiceTuple;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SubscreenSubRoomNotification implements SubRoom {
    public static Context mContext;
    public static SubscreenSubRoomNotification sInstance;
    public View mMainView;
    public final SubscreenSubRoomNotificaitonAnimatorManager mNotificationAnimatorManager;
    public final SubscreenNotificationDetailAdapter mNotificationDetailAdapter;
    public final SubscreenNotificationGroupAdapter mNotificationGroupAdapter;
    public final SubscreenNotificationInfoManager mNotificationInfoManager;
    public final SubscreenNotificationListAdapter mNotificationListAdapter;
    public SubscreenRecyclerView mNotificationRecyclerView;
    public final SubscreenSubRoomNotificaitonTouchManager mNotificationTouchManager;
    public int mRecyclerViewFirstVisibleItemPosition;
    public String mRecyclerViewItemSelectKey;
    public int mRecyclerViewLastVisibleItemPosition;
    public final AnonymousClass3 mRemoteInputEmojiActionBroadcastReceiver;
    public final AnonymousClass4 mRemoteInputVoiceActionBroadcastReceiver;
    public SubRoom.StateChangeListener mStateChangeListener;
    public final SubscreenSubRoomNotificationTip mSubRoomNotificationTipAdapter;
    public final LinearLayout mSubscreenMainLayout;
    public final Vibrator mVibrator;
    public final ArrayList mUnreadNotificationList = new ArrayList();
    public boolean mIsInNotiRoom = false;
    public boolean mHasUnreadNoti = false;
    public boolean mIsShownDetail = false;
    public boolean mIsShownGroup = false;
    public boolean mIsScrollByMe = false;
    public final AnonymousClass1 mRecyclerViewScrollListener = new AnonymousClass1();
    public final AnonymousClass2 linearLayoutManager = new LinearLayoutManager(mContext, 1, false) { // from class: com.android.systemui.statusbar.notification.SubscreenSubRoomNotification.2
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
            } else if (i == 2) {
                scrollInfo.mCompleteItemUpdateReason = 0;
                SubscreenSubRoomNotification.getDeviceModel().updateContentScroll();
                Log.d("SubscreenSubRoomNotification", "onLayoutCompleted - isReceive : true");
                z = true;
                z2 = false;
            } else {
                z = false;
                z2 = false;
            }
            SubscreenSubRoomNotification.getDeviceModel().moveDetailAdapterContentScroll(getChildAt(0), z2, z, false);
            if (z) {
                Log.d("SubscreenSubRoomNotification", "onLayoutCompleted - ShowAiReply");
                SubscreenSubRoomNotification.getDeviceModel().showAIReply();
            }
        }
    };

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.notification.SubscreenSubRoomNotification$1, reason: invalid class name */
    public final class AnonymousClass1 extends RecyclerView.OnScrollListener {
        public AnonymousClass1() {
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
                        SubscreenSubRoomNotification.getDeviceModel().getClass();
                    }
                }, 300L);
            }
            subscreenSubRoomNotification.mNotificationInfoManager.getClass();
            boolean z = SubscreenNotificationInfoManager.getSubscreenNotificationInfoListSize() == 0;
            subscreenSubRoomNotification.mRecyclerViewFirstVisibleItemPosition = z ? -1 : ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            subscreenSubRoomNotification.mRecyclerViewLastVisibleItemPosition = z ? -1 : ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            if (subscreenSubRoomNotification.mIsShownDetail) {
                SubscreenSubRoomNotification.getDeviceModel().showAIReply();
            }
        }
    }

    /* renamed from: -$$Nest$mreturnRemoteInput, reason: not valid java name */
    public static void m2119$$Nest$mreturnRemoteInput(SubscreenSubRoomNotification subscreenSubRoomNotification, String str, String str2, String str3) {
        subscreenSubRoomNotification.getClass();
        if (ServiceTuple.BASIC_STATUS_OPEN.equals(str3)) {
            subscreenSubRoomNotification.mNotificationDetailAdapter.getClass();
            ((CentralSurfacesImpl) ((CentralSurfaces) Dependency.sDependency.getDependencyInner(CentralSurfaces.class))).checkRemoteInputRequest(str, str2);
            subscreenSubRoomNotification.mNotificationDetailAdapter.cleanAdapter();
            return;
        }
        if (!"send".equals(str3)) {
            if ("permissionCheck".equals(str3)) {
                subscreenSubRoomNotification.mNotificationDetailAdapter.mNeedToUnlock = true;
                return;
            } else {
                if (PopupUIUtil.EXTRA_SIM_CARD_TRAY_WATER_PROTECTION_POPUP_DISMISS.equals(str3)) {
                    subscreenSubRoomNotification.mNotificationDetailAdapter.cleanAdapter();
                    return;
                }
                return;
            }
        }
        ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).replyNotification(str, str2);
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter = subscreenSubRoomNotification.mNotificationDetailAdapter;
        if (subscreenNotificationDetailAdapter.mSelectHolder == null) {
            Log.d("SubscreenNotificationDetailAdapter", " remove notification, but selection holder is null.");
        } else if (subscreenNotificationDetailAdapter.mSvoiceEmojiClicked) {
            Log.d("SubscreenNotificationDetailAdapter", " hide notification after svoice/emoji reply");
            subscreenNotificationDetailAdapter.mSelectHolder.getClass();
            ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).mDeviceModel.hideDetailNotificationAnimated(300, true);
            subscreenNotificationDetailAdapter.mSelectNotificationInfo.mRow.mEntry.remoteInputText = "";
        } else {
            Log.d("SubscreenNotificationDetailAdapter", " remove notification by call back");
            SubscreenParentDetailItemViewHolder subscreenParentDetailItemViewHolder = subscreenNotificationDetailAdapter.mSelectHolder;
            subscreenParentDetailItemViewHolder.mAdapter.mNotificationInfoManager.removeNotification(subscreenParentDetailItemViewHolder.mInfo.mRow.mEntry);
            ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).mDeviceModel.hideDetailNotificationAnimated(300, true);
        }
        subscreenSubRoomNotification.mNotificationDetailAdapter.cleanAdapter();
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.notification.SubscreenSubRoomNotification$2] */
    private SubscreenSubRoomNotification() {
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.notification.SubscreenSubRoomNotification.3
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                Objects.toString(intent);
                if ("com.samsung.android.action.RETURN_REMOTE_INPUT".equals(intent.getAction())) {
                    SubscreenSubRoomNotification.m2119$$Nest$mreturnRemoteInput(SubscreenSubRoomNotification.this, intent.getStringExtra("key"), intent.getStringExtra("return"), intent.getStringExtra("state"));
                }
            }
        };
        BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.notification.SubscreenSubRoomNotification.4
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                Objects.toString(intent);
                if ("com.samsung.android.action.RETURN_REMOTE_INPUT_VOICE".equals(intent.getAction())) {
                    SubscreenSubRoomNotification.m2119$$Nest$mreturnRemoteInput(SubscreenSubRoomNotification.this, intent.getStringExtra("key"), intent.getStringExtra("return"), intent.getStringExtra("state"));
                }
            }
        };
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
        Context context = mContext;
        UserHandle userHandle = UserHandle.ALL;
        context.registerReceiverAsUser(broadcastReceiver, userHandle, new IntentFilter("com.samsung.android.action.RETURN_REMOTE_INPUT"), "com.samsung.android.permission.RETURN_REMOTE_INPUT", null, 2);
        mContext.registerReceiverAsUser(broadcastReceiver2, userHandle, new IntentFilter("com.samsung.android.action.RETURN_REMOTE_INPUT_VOICE"), "com.samsung.android.permission.RETURN_REMOTE_INPUT_VOICE", null, 2);
    }

    public static SubscreenDeviceModelParent getDeviceModel() {
        return ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).mDeviceModel;
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
            } else if (this.mNotificationInfoManager.mGroupDataArray.size() <= 1) {
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
        subscreenParentAdapter.mDeviceModel = ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).mDeviceModel;
        subscreenParentAdapter.mNotificationRecyclerView = this.mNotificationRecyclerView;
        subscreenParentAdapter.mNotificationInfoManager = this.mNotificationInfoManager;
        subscreenParentAdapter.mNotificationAnimatorManager = this.mNotificationAnimatorManager;
        subscreenParentAdapter.mSubRoomNotification = this;
        subscreenParentAdapter.mContext = mContext;
    }

    public final void initData() {
        Log.e("SubscreenSubRoomNotification", "initData");
        if (NotiRune.NOTI_SUBSCREEN_GHOST_NOTIFICATION && this.mIsShownDetail) {
            ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).hideDetailNotif();
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
        int m = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(subscreenSubRoomNotificaitonTouchManager.mContext);
        subscreenSubRoomNotificaitonTouchManager.mLayoutDirection = m;
        subscreenSubRoomNotificaitonTouchManager.mItemViewSwipeEnabled = true;
        SubscreenSubRoomNotificaitonTouchManager.AnonymousClass1 anonymousClass1 = subscreenSubRoomNotificaitonTouchManager.mSimpleItemTouchCallBack;
        if (m == 1) {
            anonymousClass1.mDefaultSwipeDirs = 4;
        } else {
            anonymousClass1.mDefaultSwipeDirs = 8;
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
        getDeviceModel().releaseSmartReply();
    }

    public final void initRecyclerView() {
        SubscreenRecyclerView subscreenRecyclerView = this.mNotificationRecyclerView;
        AnonymousClass1 anonymousClass1 = this.mRecyclerViewScrollListener;
        if (subscreenRecyclerView != null) {
            subscreenRecyclerView.setLayoutManager(null);
            this.mNotificationRecyclerView.setAdapter(null);
            SubscreenRecyclerView subscreenRecyclerView2 = this.mNotificationRecyclerView;
            SubscreenSubRoomNotificaitonTouchManager.AnonymousClass2 anonymousClass2 = this.mNotificationTouchManager.mOnItemTouchListener;
            subscreenRecyclerView2.mOnItemTouchListeners.remove(anonymousClass2);
            if (subscreenRecyclerView2.mInterceptingOnItemTouchListener == anonymousClass2) {
                subscreenRecyclerView2.mInterceptingOnItemTouchListener = null;
            }
            this.mNotificationRecyclerView.removeOnScrollListener(anonymousClass1);
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
        getDeviceModel().getClass();
        if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isCoverscreenShowNotificationTip()) {
            Log.d("SubscreenSubRoomNotification", "initTipData");
            this.mNotificationRecyclerView.setAdapter(this.mSubRoomNotificationTipAdapter);
        } else {
            setListAdpater();
        }
        SubscreenRecyclerView subscreenRecyclerView5 = this.mNotificationRecyclerView;
        subscreenRecyclerView5.mOnItemTouchListeners.add(this.mNotificationTouchManager.mOnItemTouchListener);
        this.mNotificationRecyclerView.addOnScrollListener(anonymousClass1);
        SubscreenRecyclerView subscreenRecyclerView6 = this.mNotificationRecyclerView;
        RecyclerView.ItemAnimator itemAnimator = subscreenRecyclerView6.mItemAnimator;
        if (itemAnimator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) itemAnimator).mSupportsChangeAnimations = false;
        }
        this.mNotificationTouchManager.mItemTouchHelper.attachToRecyclerView(subscreenRecyclerView6);
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
        this.mIsInNotiRoom = false;
        if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isCoverscreenShowNotificationTip()) {
            Log.d("SubscreenSubRoomNotification", "initTipData");
            this.mNotificationRecyclerView.setAdapter(this.mSubRoomNotificationTipAdapter);
        } else {
            initData();
        }
        SubscreenNotificationReplyActivity subscreenNotificationReplyActivity = ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).replyActivity;
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
        int size = subscreenNotificationInfoManager.mIsShownGroup ? subscreenNotificationInfoManager.mGroupDataArray.size() : SubscreenNotificationInfoManager.getSubscreenNotificationInfoListSize();
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                break;
            }
            if ((subscreenNotificationInfoManager.mIsShownGroup ? ((SubscreenNotificationInfo) subscreenNotificationInfoManager.mGroupDataArray.get(i2)).mKey : ((SubscreenNotificationInfo) SubscreenNotificationInfoManager.mSubscreenNotificationInfoList.get(i2)).mKey).equals(str)) {
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

    public final void setListAdpater() {
        if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isCoverscreenShowNotificationTip()) {
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
            SubscreenNotificationController subscreenNotificationController = (SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class);
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
        KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class);
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
                ExifInterface$$ExternalSyntheticOutline0.m(sb, subscreenNotificationInfo.mRemoteInputSignature, "SubscreenSubRoomNotification");
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
        if (notificationEntry != null && ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).shouldFilterOut(notificationEntry)) {
            Log.e("SubscreenSubRoomNotification", "updateNotificationState -  Filter out notification");
            return;
        }
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "updateNotificationState -  action = ", " list size = ");
        m.append(this.mUnreadNotificationList.size());
        Log.e("SubscreenSubRoomNotification", m.toString());
        if (i != 0) {
            if (i != 1) {
                if (i == 2) {
                    this.mUnreadNotificationList.clear();
                }
            } else if (notificationEntry != null) {
                this.mUnreadNotificationList.remove(notificationEntry.mKey);
            }
        } else if (notificationEntry != null) {
            Log.d("SubscreenSubRoomNotification", "updateNotificationState - mIsInNotiRoom = " + this.mIsInNotiRoom + " isOngoing = " + notificationEntry.mSbn.isOngoing() + " importance = " + notificationEntry.mRanking.getImportance() + " isGroupSummary = " + notificationEntry.mSbn.getNotification().isGroupSummary());
            if (!this.mIsInNotiRoom && !notificationEntry.mSbn.isOngoing() && (!((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isShowSilentNotificationOnLockscreen() ? notificationEntry.mRanking.getImportance() >= 3 : notificationEntry.mRanking.getImportance() >= 2) && !notificationEntry.mSbn.getNotification().isGroupSummary()) {
                ArrayList arrayList = this.mUnreadNotificationList;
                String str = notificationEntry.mKey;
                if (!arrayList.contains(str)) {
                    Log.d("SubscreenSubRoomNotification", "updateNotificationState - key is added");
                    this.mUnreadNotificationList.add(str);
                }
            }
        }
        boolean z = this.mHasUnreadNoti;
        boolean z2 = !this.mUnreadNotificationList.isEmpty();
        this.mHasUnreadNoti = z2;
        if (z != z2 && this.mStateChangeListener != null) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(SubRoom.EXTRA_HAS_UNREAD, z2);
            this.mStateChangeListener.onStateChanged(bundle);
        }
        StringBuilder m2 = RowView$$ExternalSyntheticOutline0.m("updateNotificationState - prevHasUnreadNoti = ", " mHasUnreadNoti = ", z);
        m2.append(this.mHasUnreadNoti);
        m2.append(" list size = ");
        m2.append(this.mUnreadNotificationList.size());
        Log.e("SubscreenSubRoomNotification", m2.toString());
        for (int i2 = 0; i2 < this.mUnreadNotificationList.size(); i2++) {
            Log.e("SubscreenSubRoomNotification", "updateNotificationState - mUnreadNotificationList = " + ((String) this.mUnreadNotificationList.get(i2)));
        }
    }
}
