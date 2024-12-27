package com.android.systemui.statusbar.phone.ongoingactivity;

import android.app.ActivityManager;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Icon;
import android.os.Handler;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPatternView$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.CoreStartable;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.bixby2.controller.MWBixbyController$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.IndicatorGardenPresenter;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.TouchInterceptFrameLayout;
import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment;
import com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingChipAdapter;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallListener;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.util.Assert;
import com.android.systemui.util.NotificationSAUtil;
import com.android.systemui.util.SystemUIAnalytics;
import com.sec.ims.presence.ServiceTuple;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class OngoingActivityController implements IOngoingObserver, CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityStarter activityStarter;
    public final BroadcastDispatcher broadcastDispatcher;
    public final CommonNotifCollection commonNotifCollection;
    public final ConfigurationController configurationController;
    public final OngoingActivityController$configurationListener$1 configurationListener;
    public final OngoingActivityController$containerOnLayoutChangeListener$1 containerOnLayoutChangeListener;
    public final ConcurrentHashMap delayedEntry;
    public final HeadsUpManager headsUpManager;
    public final IndicatorScaleGardener indicatorScaleGardener;
    public boolean isNightMode;
    public boolean isUpdateNotAllowed;
    public RecyclerView mCapsuleRecyclerView;
    public final Context mContext;
    public final IndicatorGardenPresenter mIndicatorGardenPresenter;
    public boolean mIsHeadsUpPinned;
    public boolean mIsOngoingCall;
    public boolean mIsPanelOpen;
    public int mLayoutMode;
    public LinearLayoutManager mLinearLayoutManager;
    public OngoingCardController mOngoingCardController;
    public OngoingChipAdapter mOngoingChipAdapter;
    public View mParentView;
    public View mStatusBar;
    public Integer mStatusBarState = 0;
    public final NotifCollection notifCollection;
    public final OngoingActivityController$notifListener$1 notifListener;
    public CollapsedStatusBarFragment.OngoingActivityListenerImpl ongoingActivityListener;
    public final OngoingCallController ongoingCallController;
    public final OngoingActivityController$ongoingCallListener$1 ongoingCallListener;
    public final NotificationRemoteInputManager remoteInputManager;
    public final StatusBarStateController statusBarStateController;
    public final OngoingActivityController$statusBarStateListener$1 statusBarStateListener;
    public final StatusBarWindowStateController statusBarWindowStateController;
    public final TaskStackChangeListeners taskStackChangeListeners;
    public final OngoingActivityController$userChangedListener$1 userChangedListener;
    public final NotificationLockscreenUserManager userManager;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$containerOnLayoutChangeListener$1] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$userChangedListener$1] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$notifListener$1] */
    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$statusBarStateListener$1] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$configurationListener$1] */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$ongoingCallListener$1] */
    public OngoingActivityController(Context context, CommonNotifCollection commonNotifCollection, ActivityStarter activityStarter, StatusBarStateController statusBarStateController, IndicatorGardenPresenter indicatorGardenPresenter, HeadsUpManager headsUpManager, OngoingCallController ongoingCallController, ConfigurationController configurationController, BroadcastDispatcher broadcastDispatcher, TaskStackChangeListeners taskStackChangeListeners, IndicatorScaleGardener indicatorScaleGardener, NotifCollection notifCollection, NotificationLockscreenUserManager notificationLockscreenUserManager, NotificationRemoteInputManager notificationRemoteInputManager, StatusBarWindowStateController statusBarWindowStateController) {
        this.mContext = context;
        this.commonNotifCollection = commonNotifCollection;
        this.activityStarter = activityStarter;
        this.statusBarStateController = statusBarStateController;
        this.mIndicatorGardenPresenter = indicatorGardenPresenter;
        this.headsUpManager = headsUpManager;
        this.ongoingCallController = ongoingCallController;
        this.configurationController = configurationController;
        this.broadcastDispatcher = broadcastDispatcher;
        this.taskStackChangeListeners = taskStackChangeListeners;
        this.indicatorScaleGardener = indicatorScaleGardener;
        this.notifCollection = notifCollection;
        this.userManager = notificationLockscreenUserManager;
        this.remoteInputManager = notificationRemoteInputManager;
        this.statusBarWindowStateController = statusBarWindowStateController;
        this.mLinearLayoutManager = new LinearLayoutManager(context);
        new HashMap();
        this.delayedEntry = new ConcurrentHashMap();
        this.isNightMode = context.getResources().getConfiguration().isNightModeActive();
        this.containerOnLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$containerOnLayoutChangeListener$1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                int i9;
                OngoingChipAdapter ongoingChipAdapter = OngoingActivityController.this.mOngoingChipAdapter;
                if (ongoingChipAdapter == null || ongoingChipAdapter.enableMaxWidth == (i9 = i3 - i)) {
                    return;
                }
                ongoingChipAdapter.enableMaxWidth = i9;
                ongoingChipAdapter.notifyDataSetChanged();
            }
        };
        this.userChangedListener = new NotificationLockscreenUserManager.UserChangedListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$userChangedListener$1
            @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager.UserChangedListener
            public final void onCurrentProfilesChanged(SparseArray sparseArray) {
                OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
                NotificationLockscreenUserManager notificationLockscreenUserManager2 = OngoingActivityController.this.userManager;
                ongoingActivityDataHelper.getClass();
                OngoingActivityDataHelper.updateOngoingList(notificationLockscreenUserManager2);
            }
        };
        this.notifListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$notifListener$1
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryAdded(NotificationEntry notificationEntry) {
                onEntryUpdated(notificationEntry);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
                boolean equals = "com.samsung.android.incallui".equals(notificationEntry.mSbn.getPackageName());
                OngoingActivityController ongoingActivityController = OngoingActivityController.this;
                if (equals) {
                    ongoingActivityController.mIsOngoingCall = false;
                }
                if (!notificationEntry.isOngoingAcitivty()) {
                    if (!NotiRune.NOTI_ONGOING_GEMINI_DEMO) {
                        return;
                    }
                    OngoingActivityDataHelper.INSTANCE.getClass();
                    if (!OngoingActivityDataHelper.isExceptionalOngoingActivity(notificationEntry)) {
                        return;
                    }
                }
                boolean isGroupSummary = notificationEntry.mSbn.getNotification().isGroupSummary();
                String str = notificationEntry.mKey;
                if (isGroupSummary) {
                    KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("onEntryRemoved() return - key:", str, " is group summary", "{OngoingActivityController}");
                    return;
                }
                MWBixbyController$$ExternalSyntheticOutline0.m("onEntryRemoved() ", str, " is and remove is ", ongoingActivityController.isUpdateNotAllowed ? "NOT allowed" : "allowed", "{OngoingActivityController}");
                if (ongoingActivityController.isUpdateNotAllowed) {
                    ongoingActivityController.delayedEntry.put(str, new Pair(notificationEntry, Boolean.FALSE));
                } else {
                    OngoingActivityController.access$removeOngoingActivityData(ongoingActivityController, notificationEntry);
                }
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryUpdated(NotificationEntry notificationEntry) {
                boolean equals = "com.samsung.android.incallui".equals(notificationEntry.mSbn.getPackageName());
                OngoingActivityController ongoingActivityController = OngoingActivityController.this;
                if (equals) {
                    ongoingActivityController.mIsOngoingCall = notificationEntry.mSbn.getNotification().when > 0;
                }
                if (!notificationEntry.isOngoingAcitivty()) {
                    if (!NotiRune.NOTI_ONGOING_GEMINI_DEMO) {
                        return;
                    }
                    OngoingActivityDataHelper.INSTANCE.getClass();
                    if (!OngoingActivityDataHelper.isExceptionalOngoingActivity(notificationEntry)) {
                        return;
                    }
                }
                boolean isGroupSummary = notificationEntry.mSbn.getNotification().isGroupSummary();
                String str = notificationEntry.mKey;
                if (isGroupSummary) {
                    KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("onEntryUpdated() return - key:", str, " is group summary", "{OngoingActivityController}");
                    return;
                }
                OngoingActivityDataHelper.INSTANCE.getClass();
                boolean z = OngoingActivityDataHelper.getOngoingActivityDataByKey(str) == null;
                ExifInterface$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("onEntryUpdated() ", str, " is ", z ? "added" : "updated", " and update is "), ongoingActivityController.isUpdateNotAllowed ? "NOT allowed" : "allowed", "{OngoingActivityController}");
                if (ongoingActivityController.isUpdateNotAllowed) {
                    ongoingActivityController.delayedEntry.put(str, new Pair(notificationEntry, Boolean.TRUE));
                } else {
                    OngoingActivityController.access$updateOngoingActivityData(ongoingActivityController, notificationEntry);
                }
                if (z) {
                    ongoingActivityController.startMarqueeAnimation();
                    NotificationSAUtil.sendOALog(ongoingActivityController.mContext, SystemUIAnalytics.OAID_ONGOING_CHIP_GENERATED, notificationEntry);
                }
            }
        };
        this.statusBarStateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$statusBarStateListener$1
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onExpandedChanged(boolean z) {
                RecyclerView.ViewHolder viewHolder;
                View childAt;
                Log.d("{OngoingActivityController}", "onExpandedChanged : shade is ".concat(z ? ServiceTuple.BASIC_STATUS_OPEN : "close"));
                OngoingActivityController ongoingActivityController = OngoingActivityController.this;
                if (ongoingActivityController.mIsPanelOpen != z) {
                    ongoingActivityController.mIsPanelOpen = z;
                    if (z) {
                        OngoingChipAdapter ongoingChipAdapter = ongoingActivityController.mOngoingChipAdapter;
                        Intrinsics.checkNotNull(ongoingChipAdapter);
                        if (ongoingChipAdapter.getItemCount() > 0) {
                            RecyclerView recyclerView = ongoingActivityController.mCapsuleRecyclerView;
                            if (recyclerView != null) {
                                Intrinsics.checkNotNull(ongoingActivityController.mOngoingChipAdapter);
                                viewHolder = recyclerView.findViewHolderForPosition(r2.getItemCount() - 1, false);
                            } else {
                                viewHolder = null;
                            }
                            OngoingChipAdapter.ChipViewHolder chipViewHolder = viewHolder instanceof OngoingChipAdapter.ChipViewHolder ? (OngoingChipAdapter.ChipViewHolder) viewHolder : null;
                            if (chipViewHolder != null && (childAt = chipViewHolder.mExpandedInfo.getChildAt(0)) != null) {
                                childAt.setSelected(false);
                            }
                        }
                        OngoingCardController ongoingCardController = ongoingActivityController.mOngoingCardController;
                        if (ongoingCardController != null) {
                            ongoingCardController.fadeOutCard();
                        }
                    }
                }
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStateChanged(int i) {
                OngoingActivityController ongoingActivityController = OngoingActivityController.this;
                Integer num = ongoingActivityController.mStatusBarState;
                if (num != null && num.intValue() == i) {
                    return;
                }
                ongoingActivityController.mStatusBarState = Integer.valueOf(i);
                if (i != 0) {
                    OngoingCardController ongoingCardController = ongoingActivityController.mOngoingCardController;
                    if (ongoingCardController != null) {
                        ongoingCardController.onDestroy();
                    }
                } else {
                    OngoingChipAdapter ongoingChipAdapter = ongoingActivityController.mOngoingChipAdapter;
                    if (ongoingChipAdapter != null) {
                        ongoingChipAdapter.isKeyguardGoneNow = true;
                    }
                    if (ongoingChipAdapter != null) {
                        ongoingChipAdapter.notifyDataSetChanged();
                    }
                }
                ongoingActivityController.updateParentViewVisibility(true);
            }
        };
        this.configurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$configurationListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                Log.i("{OngoingActivityController}", "onConfigChanged()!! - " + configuration);
                int i = OngoingActivityController.$r8$clinit;
                OngoingActivityController ongoingActivityController = OngoingActivityController.this;
                ongoingActivityController.updateParentViewVisibility(true);
                if (MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(ongoingActivityController.mContext) != ongoingActivityController.mLayoutMode) {
                    RecyclerView recyclerView = ongoingActivityController.mCapsuleRecyclerView;
                    int size = recyclerView != null ? recyclerView.mItemDecorations.size() : 0;
                    for (int i2 = 0; i2 < size; i2++) {
                        RecyclerView recyclerView2 = ongoingActivityController.mCapsuleRecyclerView;
                        if (recyclerView2 != null) {
                            recyclerView2.removeItemDecorationAt(i2);
                        }
                    }
                    RecyclerView recyclerView3 = ongoingActivityController.mCapsuleRecyclerView;
                    if (recyclerView3 != null) {
                        recyclerView3.addItemDecoration(new OngoingActivityController$addDecoratorForRecyclerView$1(ongoingActivityController));
                    }
                }
                Intrinsics.checkNotNull(configuration);
                boolean isNightModeActive = configuration.isNightModeActive();
                if (ongoingActivityController.isNightMode != isNightModeActive) {
                    OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
                    Context context2 = ongoingActivityController.mContext;
                    ongoingActivityDataHelper.getClass();
                    OngoingActivityDataHelper.updateOngoingActivityViews(context2, OngoingActivityDataHelper.hiddenOngoingActivityDataList, false, true);
                    OngoingActivityDataHelper.updateOngoingActivityViews(context2, OngoingActivityDataHelper.mOngoingActivityLists, false, true);
                    ongoingActivityController.isNightMode = isNightModeActive;
                }
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                OngoingActivityController ongoingActivityController = OngoingActivityController.this;
                float f = ongoingActivityController.indicatorScaleGardener.getLatestScaleModel(ongoingActivityController.mContext).ratio;
                RecyclerView recyclerView = ongoingActivityController.mCapsuleRecyclerView;
                ViewGroup.LayoutParams layoutParams = recyclerView != null ? recyclerView.getLayoutParams() : null;
                if (layoutParams != null) {
                    layoutParams.height = MathKt__MathJVMKt.roundToInt(ongoingActivityController.mContext.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_top_height) * f);
                }
                OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
                Context context2 = ongoingActivityController.mContext;
                ongoingActivityDataHelper.getClass();
                OngoingActivityDataHelper.updateOngoingActivityViews(context2, OngoingActivityDataHelper.hiddenOngoingActivityDataList, true, false);
                OngoingActivityDataHelper.updateOngoingActivityViews(context2, OngoingActivityDataHelper.mOngoingActivityLists, true, false);
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDisplayDeviceTypeChanged() {
                if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
                    onDensityOrFontScaleChanged();
                    OngoingActivityController ongoingActivityController = OngoingActivityController.this;
                    View view = ongoingActivityController.mParentView;
                    if (view == null) {
                        view = null;
                    }
                    View view2 = ongoingActivityController.mStatusBar;
                    ongoingActivityController.initCapsuleLayout(view, view2 != null ? view2 : null);
                }
            }
        };
        this.ongoingCallListener = new OngoingCallListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$ongoingCallListener$1
            @Override // com.android.systemui.statusbar.phone.ongoingcall.OngoingCallListener
            public final void onOngoingCallStateChanged() {
                Log.d("{OngoingActivityController}", "onOngoingCallStateChanged() animate:true");
                OngoingActivityController.this.update$6();
            }
        };
    }

    public static final void access$removeOngoingActivityData(OngoingActivityController ongoingActivityController, NotificationEntry notificationEntry) {
        OngoingCardController ongoingCardController = ongoingActivityController.mOngoingCardController;
        if (ongoingCardController != null) {
            String key = notificationEntry.mSbn.getKey();
            CardStackView cardStackView = ongoingCardController.mCardStackView;
            if (cardStackView.removingSbnId.equals(key)) {
                Log.i("{OngoingActivityCardStackView}", "Clear removingSbnId:".concat(key));
                cardStackView.removingSbnId = "";
                cardStackView.isRunningSwipeDismissTopCardMove = false;
                cardStackView.isAnimating = false;
            }
        }
        OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        ongoingActivityDataHelper.getClass();
        OngoingActivityDataHelper.removeOngoingActivityByKey(statusBarNotification.getKey());
        ongoingActivityController.updateParentViewVisibility(true);
    }

    public static final void access$updateOngoingActivityData(OngoingActivityController ongoingActivityController, NotificationEntry notificationEntry) {
        OngoingActivityData ongoingActivityData;
        Pair pair;
        Integer num;
        ongoingActivityController.getClass();
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
        Context context = ongoingActivityController.mContext;
        boolean z = ongoingActivityController.mOngoingCardController == null && (num = ongoingActivityController.mStatusBarState) != null && num.intValue() == 0;
        ongoingActivityDataHelper.getClass();
        OngoingActivityData ongoingActivityData2 = null;
        if (NotiRune.NOTI_ONGOING_GEMINI_DEMO && OngoingActivityDataHelper.isExceptionalOngoingActivity(notificationEntry)) {
            ongoingActivityData = new OngoingActivityData(notificationEntry, context, 0, context.getColor(R.color.ongoing_gemini_primary_color), Icon.createWithResource(context, R.drawable.ongoing_gemini_app_icon));
            ArrayList arrayList = ongoingActivityData.mActions;
            if (arrayList != null) {
                int i = 0;
                for (Object obj : arrayList) {
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                        throw null;
                    }
                    Notification.Action action = (Notification.Action) obj;
                    if (i == 0) {
                        action = new Notification.Action.Builder(Icon.createWithResource(context, R.drawable.ongoing_btn_cancel), "End Live mode", action.actionIntent).build();
                    } else if (i == 1) {
                        String str = ongoingActivityData.mPrimaryInfo;
                        action = new Notification.Action.Builder(Icon.createWithResource(context, (str == null || !str.startsWith("Listening")) ? R.drawable.ic_ongoing_play : R.drawable.ic_ongoing_pause), "Play and Pause", action.actionIntent).build();
                    }
                    Intrinsics.checkNotNull(action);
                    arrayList.set(i, action);
                    i = i2;
                }
            }
        } else {
            ongoingActivityData = new OngoingActivityData(notificationEntry, statusBarNotification, context);
        }
        OngoingActivityLayoutCreatorImpl ongoingActivityLayoutCreatorImpl = new OngoingActivityLayoutCreatorImpl(context);
        RemoteViews remoteViews = ongoingActivityData.mCustomNowBarView;
        String str2 = ongoingActivityData.mNotiID;
        if (remoteViews != null) {
            RemoteViews remoteViews2 = new RemoteViews(ongoingActivityLayoutCreatorImpl.context.getPackageName(), R.layout.ongoing_nowbar_view);
            RemoteViews remoteViews3 = ongoingActivityData.mCustomNowBarView;
            if (remoteViews3 != null) {
                remoteViews2.addView(R.id.ongoing_activity_nowbar_custom_content, remoteViews3);
                remoteViews2.setViewVisibility(R.id.ongoing_activity_nowbar_custom_content, 0);
                Log.i("{OngoingActivityLayoutCreator}", "createNowBarView: use full custom view (" + str2 + ")");
                remoteViews2.removeAllViewsExceptId(R.id.ongoing_activity_nowbar_view, R.id.ongoing_activity_nowbar_custom_content);
            }
            ongoingActivityData.mOngoingNowbarView = remoteViews2;
        }
        RemoteViews createExpandView = ongoingActivityLayoutCreatorImpl.createExpandView(ongoingActivityData);
        createExpandView.addFlags(1);
        ongoingActivityData.mOngoingExpandView = createExpandView;
        RemoteViews createCollapsedView = ongoingActivityLayoutCreatorImpl.createCollapsedView(ongoingActivityData);
        createCollapsedView.addFlags(1);
        ongoingActivityData.mOngoingCollapsedView = createCollapsedView;
        if (!z) {
            ongoingActivityData.mIsNew = Boolean.FALSE;
        }
        String str3 = OngoingActivityDataHelper.TAG;
        Log.i(str3, "updateOngoingActivity data: " + ongoingActivityData);
        int size = OngoingActivityDataHelper.mOngoingActivityLists.size();
        ComponentName componentName = OngoingActivityDataHelper.baseActivityComponentName;
        StringBuilder m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(size, "addOrUpdateOngoingActivityInternal() mNotiID = ", str2, ", oa size = ", ", baseActivityComponentName = ");
        m.append(componentName);
        Log.i(str3, m.toString());
        OngoingActivityData ongoingActivityData3 = null;
        for (OngoingActivityData ongoingActivityData4 : OngoingActivityDataHelper.hiddenOngoingActivityDataList) {
            if (ongoingActivityData4.mNotiID.equals(str2)) {
                ongoingActivityData3 = ongoingActivityData4;
            }
        }
        if (ongoingActivityData3 != null) {
            LinkedList linkedList = OngoingActivityDataHelper.hiddenOngoingActivityDataList;
            int indexOf = linkedList.indexOf(ongoingActivityData3);
            if (indexOf != -1) {
                linkedList.remove(indexOf);
                linkedList.add(indexOf, ongoingActivityData);
            } else {
                Log.e(str3, "hiddenOngoingActivityDataList get index fail");
            }
            Boolean bool = Boolean.FALSE;
            pair = new Pair(bool, bool);
        } else {
            for (OngoingActivityData ongoingActivityData5 : OngoingActivityDataHelper.mOngoingActivityLists) {
                if (ongoingActivityData5.mNotiID.equals(str2)) {
                    ongoingActivityData2 = ongoingActivityData5;
                }
            }
            if (ongoingActivityData2 != null) {
                LinkedList linkedList2 = OngoingActivityDataHelper.mOngoingActivityLists;
                int indexOf2 = linkedList2.indexOf(ongoingActivityData2);
                if (indexOf2 != -1) {
                    linkedList2.remove(indexOf2);
                    linkedList2.add(indexOf2, ongoingActivityData);
                    pair = new Pair(Boolean.TRUE, Boolean.FALSE);
                } else {
                    Log.e(str3, "mOngoingActivityLists get index fail");
                    Boolean bool2 = Boolean.FALSE;
                    pair = new Pair(bool2, bool2);
                }
            } else if (OngoingActivityDataHelper.shouldHide(ongoingActivityController.userManager, ongoingActivityData)) {
                OngoingActivityDataHelper.hiddenOngoingActivityDataList.add(ongoingActivityData);
                Boolean bool3 = Boolean.FALSE;
                pair = new Pair(bool3, bool3);
            } else {
                OngoingActivityDataHelper.mOngoingActivityLists.addFirst(ongoingActivityData);
                Boolean bool4 = Boolean.TRUE;
                pair = new Pair(bool4, bool4);
            }
        }
        if (((Boolean) pair.getFirst()).booleanValue()) {
            if (((Boolean) pair.getSecond()).booleanValue()) {
                Iterator it = ((ArrayList) OngoingActivityDataHelper.observers).iterator();
                while (it.hasNext()) {
                    ((IOngoingObserver) it.next()).add$2();
                }
            } else {
                OngoingActivityDataHelper.notifyUpdateObservers();
            }
        }
        OngoingActivityDataHelper.notifyUpdateItemNowbarObservers(OngoingActivityDataHelper.convertOngoingActivityData(ongoingActivityData));
        ongoingActivityController.updateParentViewVisibility(true);
    }

    @Override // com.android.systemui.statusbar.phone.ongoingactivity.IOngoingObserver
    public final void add$2() {
        Log.d("{OngoingActivityController}", "add()");
        updateParentViewVisibility(true);
        updateAdapter();
    }

    public final void initCapsuleLayout(View view, View view2) {
        ViewGroup viewGroup;
        ViewGroup viewGroup2;
        Log.i("{OngoingActivityController}", "initCapsuleLayout() " + this.mOngoingChipAdapter + " : " + this.mCapsuleRecyclerView);
        View view3 = this.mParentView;
        OngoingActivityController$userChangedListener$1 ongoingActivityController$userChangedListener$1 = this.userChangedListener;
        NotificationLockscreenUserManager notificationLockscreenUserManager = this.userManager;
        OngoingActivityController$containerOnLayoutChangeListener$1 ongoingActivityController$containerOnLayoutChangeListener$1 = this.containerOnLayoutChangeListener;
        ConfigurationController configurationController = this.configurationController;
        OngoingActivityController$configurationListener$1 ongoingActivityController$configurationListener$1 = this.configurationListener;
        OngoingActivityController$ongoingCallListener$1 ongoingActivityController$ongoingCallListener$1 = this.ongoingCallListener;
        OngoingCallController ongoingCallController = this.ongoingCallController;
        OngoingActivityController$statusBarStateListener$1 ongoingActivityController$statusBarStateListener$1 = this.statusBarStateListener;
        StatusBarStateController statusBarStateController = this.statusBarStateController;
        CommonNotifCollection commonNotifCollection = this.commonNotifCollection;
        OngoingActivityController$notifListener$1 ongoingActivityController$notifListener$1 = this.notifListener;
        if (view3 != null || this.mStatusBar != null) {
            NotifCollection notifCollection = ((NotifPipeline) commonNotifCollection).mNotifCollection;
            notifCollection.getClass();
            Assert.isMainThread();
            notifCollection.mNotifCollectionListeners.remove(ongoingActivityController$notifListener$1);
            OngoingActivityDataHelper.INSTANCE.getClass();
            ((ArrayList) OngoingActivityDataHelper.observers).remove(this);
            statusBarStateController.removeCallback(ongoingActivityController$statusBarStateListener$1);
            ongoingCallController.removeCallback((OngoingCallListener) ongoingActivityController$ongoingCallListener$1);
            ((ConfigurationControllerImpl) configurationController).removeCallback(ongoingActivityController$configurationListener$1);
            View view4 = this.mStatusBar;
            if (view4 != null && (viewGroup = (ViewGroup) view4.findViewById(R.id.samsung_notification_indicator_container)) != null) {
                viewGroup.removeOnLayoutChangeListener(ongoingActivityController$containerOnLayoutChangeListener$1);
            }
            ((ArrayList) ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).mListeners).remove(ongoingActivityController$userChangedListener$1);
        }
        this.mParentView = view;
        TouchInterceptFrameLayout touchInterceptFrameLayout = view instanceof TouchInterceptFrameLayout ? (TouchInterceptFrameLayout) view : null;
        if (touchInterceptFrameLayout != null) {
            touchInterceptFrameLayout.customClickListener = new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$initCapsuleLayout$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view5) {
                    OngoingActivityDataHelper.INSTANCE.getClass();
                    LinkedList linkedList = OngoingActivityDataHelper.mOngoingActivityLists;
                    Log.d("{OngoingActivityController}", "OnClick Ongoing Chip! dataSize : " + linkedList.size());
                    if (linkedList.size() > 0) {
                        Log.d("{OngoingActivityController}", " top Data is " + OngoingActivityDataHelper.getDataByIndex(0));
                    }
                    OngoingActivityController ongoingActivityController = OngoingActivityController.this;
                    if (ongoingActivityController.mOngoingCardController != null) {
                        Log.i("{OngoingActivityController}", "createCardController mOngoingCardController != null. return");
                    } else {
                        Context context = ongoingActivityController.mContext;
                        RecyclerView recyclerView = ongoingActivityController.mCapsuleRecyclerView;
                        Intrinsics.checkNotNull(recyclerView);
                        ongoingActivityController.mOngoingCardController = new OngoingCardController(context, ongoingActivityController.activityStarter, recyclerView, new OngoingActivityController$createCardController$1(ongoingActivityController), ongoingActivityController.mIndicatorGardenPresenter, ongoingActivityController.indicatorScaleGardener, ongoingActivityController.configurationController, ongoingActivityController.broadcastDispatcher, ongoingActivityController.notifCollection, ongoingActivityController.remoteInputManager, ongoingActivityController.statusBarWindowStateController);
                        OngoingChipAdapter ongoingChipAdapter = ongoingActivityController.mOngoingChipAdapter;
                        if (ongoingChipAdapter != null) {
                            ongoingChipAdapter.notifyDataSetChanged();
                        }
                        OngoingChipAdapter ongoingChipAdapter2 = ongoingActivityController.mOngoingChipAdapter;
                        if (ongoingChipAdapter2 != null) {
                            ongoingChipAdapter2.marqueePair = null;
                        }
                    }
                    if (view5 != null) {
                        view5.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(1));
                    }
                }
            };
        }
        this.mStatusBar = view2;
        if (touchInterceptFrameLayout != null) {
            touchInterceptFrameLayout.touchForwardView = view2;
        }
        Intrinsics.checkNotNull(view);
        this.mCapsuleRecyclerView = (RecyclerView) view.findViewById(R.id.capsule_recyclerview);
        this.mOngoingChipAdapter = new OngoingChipAdapter(this.mContext, this.indicatorScaleGardener);
        RecyclerView recyclerView = this.mCapsuleRecyclerView;
        if (recyclerView != null) {
            recyclerView.setFocusable(false);
        }
        RecyclerView recyclerView2 = this.mCapsuleRecyclerView;
        if (recyclerView2 != null) {
            recyclerView2.setAdapter(this.mOngoingChipAdapter);
        }
        RecyclerView recyclerView3 = this.mCapsuleRecyclerView;
        if (recyclerView3 != null) {
            recyclerView3.mHasFixedSize = true;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.mContext);
        this.mLinearLayoutManager = linearLayoutManager;
        linearLayoutManager.setOrientation(0);
        this.mLinearLayoutManager.setStackFromEnd(true);
        LinearLayoutManager linearLayoutManager2 = this.mLinearLayoutManager;
        linearLayoutManager2.assertNotInLayoutOrScroll(null);
        if (true != linearLayoutManager2.mReverseLayout) {
            linearLayoutManager2.mReverseLayout = true;
            linearLayoutManager2.requestLayout();
        }
        RecyclerView recyclerView4 = this.mCapsuleRecyclerView;
        if (recyclerView4 != null) {
            recyclerView4.setLayoutManager(this.mLinearLayoutManager);
        }
        RecyclerView recyclerView5 = this.mCapsuleRecyclerView;
        if (recyclerView5 != null) {
            recyclerView5.addItemDecoration(new OngoingActivityController$addDecoratorForRecyclerView$1(this));
        }
        ((NotifPipeline) commonNotifCollection).addCollectionListener(ongoingActivityController$notifListener$1);
        OngoingActivityDataHelper.INSTANCE.getClass();
        ((ArrayList) OngoingActivityDataHelper.observers).add(this);
        statusBarStateController.addCallback(ongoingActivityController$statusBarStateListener$1);
        ongoingCallController.addCallback((OngoingCallListener) ongoingActivityController$ongoingCallListener$1);
        ((ConfigurationControllerImpl) configurationController).addCallback(ongoingActivityController$configurationListener$1);
        View view5 = this.mStatusBar;
        if (view5 != null && (viewGroup2 = (ViewGroup) view5.findViewById(R.id.samsung_notification_indicator_container)) != null) {
            viewGroup2.addOnLayoutChangeListener(ongoingActivityController$containerOnLayoutChangeListener$1);
        }
        ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).addUserChangedListener(ongoingActivityController$userChangedListener$1);
        OngoingActivityDataHelper.updateOngoingList(notificationLockscreenUserManager);
    }

    @Override // com.android.systemui.statusbar.phone.ongoingactivity.IOngoingObserver
    public final void remove(int i) {
        KeyguardSecPatternView$$ExternalSyntheticOutline0.m(i, "position = ", "{OngoingActivityController}");
        OngoingChipAdapter ongoingChipAdapter = this.mOngoingChipAdapter;
        if (ongoingChipAdapter != null) {
            ongoingChipAdapter.notifyDataSetChanged();
        }
    }

    public final boolean shouldVisible() {
        Integer num;
        OngoingActivityDataHelper.INSTANCE.getClass();
        return OngoingActivityDataHelper.mOngoingActivityLists.size() > 0 && (num = this.mStatusBarState) != null && num.intValue() == 0 && !this.mIsHeadsUpPinned;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        ActivityManager.RunningTaskInfo runningTaskInfo;
        ComponentName componentName;
        ((BaseHeadsUpManager) this.headsUpManager).addListener(new OnHeadsUpChangedListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$start$1
            @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
            public final void onHeadsUpPinnedModeChanged(boolean z) {
                OngoingCardController ongoingCardController;
                OngoingActivityController ongoingActivityController = OngoingActivityController.this;
                EmergencyButtonController$$ExternalSyntheticOutline0.m(" onHeadsUpPinnedModeChanged - ", " : ", "{OngoingActivityController}", ongoingActivityController.mIsHeadsUpPinned, z);
                if (ongoingActivityController.mIsHeadsUpPinned != z) {
                    ongoingActivityController.mIsHeadsUpPinned = z;
                    if (z && (ongoingCardController = ongoingActivityController.mOngoingCardController) != null) {
                        ongoingCardController.fadeOutCard();
                    }
                    ongoingActivityController.updateParentViewVisibility(true);
                }
            }
        });
        this.taskStackChangeListeners.registerTaskStackListener(new TaskStackChangeListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$start$2
            @Override // com.android.systemui.shared.system.TaskStackChangeListener
            public final void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo2) {
                ComponentName componentName2;
                OngoingActivityController ongoingActivityController = OngoingActivityController.this;
                if (runningTaskInfo2 != null && (componentName2 = runningTaskInfo2.baseActivity) != null) {
                    OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
                    NotificationLockscreenUserManager notificationLockscreenUserManager = ongoingActivityController.userManager;
                    ongoingActivityDataHelper.getClass();
                    if (!componentName2.equals(OngoingActivityDataHelper.baseActivityComponentName)) {
                        OngoingActivityDataHelper.baseActivityComponentName = componentName2;
                        OngoingActivityDataHelper.updateOngoingList(notificationLockscreenUserManager);
                    }
                }
                ongoingActivityController.startMarqueeAnimation();
            }
        });
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) this.mContext.getSystemService("activity")).getRunningTasks(1);
        if (runningTasks == null || (runningTaskInfo = runningTasks.get(0)) == null || (componentName = runningTaskInfo.baseActivity) == null) {
            return;
        }
        OngoingActivityDataHelper.INSTANCE.getClass();
        if (componentName.equals(OngoingActivityDataHelper.baseActivityComponentName)) {
            return;
        }
        OngoingActivityDataHelper.baseActivityComponentName = componentName;
        OngoingActivityDataHelper.updateOngoingList(this.userManager);
    }

    public final void startMarqueeAnimation() {
        new Handler().post(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$startMarqueeAnimation$1
            @Override // java.lang.Runnable
            public final void run() {
                OngoingChipAdapter ongoingChipAdapter = OngoingActivityController.this.mOngoingChipAdapter;
                if (ongoingChipAdapter != null) {
                    Pair pair = ongoingChipAdapter.marqueePair;
                    if (pair != null && (Intrinsics.areEqual(((OngoingActivityData) pair.getSecond()).mIsNew, Boolean.TRUE) || ongoingChipAdapter.isKeyguardGoneNow)) {
                        ((TextView) pair.getFirst()).setEllipsize(TextUtils.TruncateAt.MARQUEE);
                        ((TextView) pair.getFirst()).setMarqueeRepeatLimit(1);
                        ((TextView) pair.getFirst()).setSelected(true);
                        ((OngoingActivityData) pair.getSecond()).mIsNew = Boolean.FALSE;
                    }
                    ongoingChipAdapter.isKeyguardGoneNow = false;
                    ongoingChipAdapter.marqueePair = null;
                }
            }
        });
    }

    @Override // com.android.systemui.statusbar.phone.ongoingactivity.IOngoingObserver
    public final void update$6() {
        Log.d("{OngoingActivityController}", "update()");
        updateParentViewVisibility(true);
        updateAdapter();
    }

    public final void updateAdapter() {
        OngoingChipAdapter ongoingChipAdapter = this.mOngoingChipAdapter;
        if (ongoingChipAdapter != null) {
            ongoingChipAdapter.shouldShowChipOnly = this.mIsOngoingCall && this.ongoingCallController.hasOngoingCall();
        }
        OngoingChipAdapter ongoingChipAdapter2 = this.mOngoingChipAdapter;
        if (ongoingChipAdapter2 != null) {
            ongoingChipAdapter2.notifyDataSetChanged();
        }
    }

    public final void updateParentViewVisibility(boolean z) {
        Log.d("{OngoingActivityController}", "updateParentViewVisibility() animate:" + z);
        if (shouldVisible()) {
            CollapsedStatusBarFragment.OngoingActivityListenerImpl ongoingActivityListenerImpl = this.ongoingActivityListener;
            if (ongoingActivityListenerImpl != null) {
                Intrinsics.checkNotNull(this.mParentView);
                Log.d("CollapsedStatusBarFragment", "onShowActivityChip() animate:" + z);
                int i = CollapsedStatusBarFragment.$r8$clinit;
                CollapsedStatusBarFragment.this.updateStatusBarVisibilities(z);
                return;
            }
            return;
        }
        CollapsedStatusBarFragment.OngoingActivityListenerImpl ongoingActivityListenerImpl2 = this.ongoingActivityListener;
        if (ongoingActivityListenerImpl2 != null) {
            Intrinsics.checkNotNull(this.mParentView);
            Log.d("CollapsedStatusBarFragment", "onHideActivityChip() animate:" + z);
            int i2 = CollapsedStatusBarFragment.$r8$clinit;
            CollapsedStatusBarFragment.this.updateStatusBarVisibilities(z);
        }
    }
}
