package com.android.systemui.statusbar.phone.ongoingactivity;

import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation$$ExternalSyntheticOutline0;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0;
import com.android.systemui.biometrics.AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper;
import com.android.systemui.media.MediaType;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.media.SecMediaPlayerData;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.panelresource.SecQSPanelResourceCommon;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener;
import com.android.systemui.statusbar.phone.IndicatorGardenPresenter;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment;
import com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView;
import com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CustomAnimationSet;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingChipAdapter;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallListener;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.unfold.updates.screen.ScreenStatusProvider;
import com.android.systemui.util.NotificationSAUtil;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.sec.ims.presence.ServiceTuple;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.math.MathKt__MathJVMKt;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class OngoingActivityController implements IOngoingObserver, CoreStartable, Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityStarter activityStarter;
    public boolean blockClickListener;
    public final BroadcastDispatcher broadcastDispatcher;
    public final OngoingActivityController$clearPipRunnable$1 clearPipRunnable;
    public final CommonNotifCollection commonNotifCollection;
    public final ConfigurationController configurationController;
    public final OngoingActivityController$configurationListener$1 configurationListener;
    public final OngoingActivityController$containerOnLayoutChangeListener$1 containerOnLayoutChangeListener;
    public MediaOngoingActivityInfo currentMediaOngoingActivityInfo;
    public final ConcurrentLinkedQueue delayedEntry;
    public final OngoingActivityController$dismissMediaRunnable$1 dismissMediaRunnable;
    public final FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper;
    public final HeadsUpManager headsUpManager;
    public final HistoryDumpList historyDump;
    public final IndicatorScaleGardener indicatorScaleGardener;
    public boolean isMediaPlaying;
    public boolean isMediaVisible;
    public boolean isNightMode;
    public boolean isScreenTurnedOn;
    public boolean isUpdateNotAllowed;
    public RecyclerView mCapsuleRecyclerView;
    public Configuration mConfig;
    public final Context mContext;
    public final Handler mHandler;
    public final IndicatorGardenPresenter mIndicatorGardenPresenter;
    public boolean mIsHeadsUpPinned;
    public boolean mIsPanelOpen;
    public int mLayoutMode;
    public LinearLayoutManager mLinearLayoutManager;
    public View mMediaCardView;
    public int mMediaChipColor;
    public CharSequence mMediaChipText;
    public String mMediaPackageName;
    public OngoingCardController mOngoingCardController;
    public OngoingChipAdapter mOngoingChipAdapter;
    public View mParentView;
    private SettingsHelper.OnChangedCallback mSettingChangedCallback;
    public View mStatusBar;
    public Integer mStatusBarState;
    public final MediaDataManager mediaDataManager;
    public final SecMediaHost mediaHost;
    public final OngoingActivityController$mediaPanelVisibilityListener$1 mediaPanelVisibilityListener;
    public final Handler mediaPauseTimerHandler;
    public final NotifCollection notifCollection;
    public final OngoingActivityController$notifListener$1 notifListener;
    public final NotificationIconAreaController notificationIconAreaController;
    public CollapsedStatusBarFragment.OngoingActivityListenerImpl ongoingActivityListener;
    public final OngoingCallController ongoingCallController;
    public final OngoingActivityController$ongoingCallListener$1 ongoingCallListener;
    public final OngoingActivityController$ongoingChipItemDecoration$1 ongoingChipItemDecoration;
    public final NotificationRemoteInputManager remoteInputManager;
    public final OngoingActivityController$removeAllMediaRunnable$1 removeAllMediaRunnable;
    public final OngoingActivityController$screenListener$1 screenListener;
    public final ScreenStatusProvider screenStatusProvider;
    private final SettingsHelper settingsHelper;
    public final StatusBarStateController statusBarStateController;
    public final OngoingActivityController$statusBarStateListener$1 statusBarStateListener;
    public final StatusBarWindowStateController statusBarWindowStateController;
    public final TaskStackChangeListeners taskStackChangeListeners;
    public final OngoingActivityController$userChangedListener$1 userChangedListener;
    public final NotificationLockscreenUserManager userManager;

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

    /* JADX WARN: Type inference failed for: r2v10, types: [com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$ongoingChipItemDecoration$1] */
    /* JADX WARN: Type inference failed for: r2v11, types: [com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$notifListener$1] */
    /* JADX WARN: Type inference failed for: r2v12, types: [com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$statusBarStateListener$1] */
    /* JADX WARN: Type inference failed for: r2v13, types: [com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$configurationListener$1] */
    /* JADX WARN: Type inference failed for: r2v14, types: [com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$ongoingCallListener$1] */
    /* JADX WARN: Type inference failed for: r2v15, types: [com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$screenListener$1] */
    /* JADX WARN: Type inference failed for: r2v16, types: [com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$mediaPanelVisibilityListener$1] */
    /* JADX WARN: Type inference failed for: r2v17, types: [com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$removeAllMediaRunnable$1] */
    /* JADX WARN: Type inference failed for: r2v18, types: [com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$dismissMediaRunnable$1] */
    /* JADX WARN: Type inference failed for: r2v6, types: [com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$clearPipRunnable$1] */
    /* JADX WARN: Type inference failed for: r2v8, types: [com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$containerOnLayoutChangeListener$1] */
    /* JADX WARN: Type inference failed for: r2v9, types: [com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$userChangedListener$1] */
    public OngoingActivityController(Context context, CommonNotifCollection commonNotifCollection, ActivityStarter activityStarter, StatusBarStateController statusBarStateController, IndicatorGardenPresenter indicatorGardenPresenter, HeadsUpManager headsUpManager, OngoingCallController ongoingCallController, ConfigurationController configurationController, BroadcastDispatcher broadcastDispatcher, TaskStackChangeListeners taskStackChangeListeners, IndicatorScaleGardener indicatorScaleGardener, NotifCollection notifCollection, NotificationLockscreenUserManager notificationLockscreenUserManager, NotificationRemoteInputManager notificationRemoteInputManager, StatusBarWindowStateController statusBarWindowStateController, DumpManager dumpManager, SecMediaHost secMediaHost, SettingsHelper settingsHelper, NotificationIconAreaController notificationIconAreaController, MediaDataManager mediaDataManager, ScreenStatusProvider screenStatusProvider, FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper) {
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
        this.mediaHost = secMediaHost;
        this.settingsHelper = settingsHelper;
        this.notificationIconAreaController = notificationIconAreaController;
        this.mediaDataManager = mediaDataManager;
        this.screenStatusProvider = screenStatusProvider;
        this.faceWidgetNotificationControllerWrapper = faceWidgetNotificationControllerWrapper;
        dumpManager.registerNormalDumpable(this);
        this.mConfig = new Configuration(context.getResources().getConfiguration());
        this.mMediaCardView = LayoutInflater.from(context).inflate(R.layout.sec_ongoing_card_item_layout, (ViewGroup) null, false);
        reinflateMediaFrame();
        SettingsHelper.OnChangedCallback onChangedCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController.1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                if (Intrinsics.areEqual(uri, Settings.System.getUriFor(SettingsHelper.INDEX_MEDIA_ONGOING))) {
                    OngoingActivityController ongoingActivityController = OngoingActivityController.this;
                    AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("updateMediaVisibility. isMediaVisible:", "MediaOngoingActivity", ongoingActivityController.isMediaVisible);
                    if (ongoingActivityController.isMediaVisible) {
                        NotificationManager notificationManager = (NotificationManager) ongoingActivityController.mContext.getSystemService(NotificationManager.class);
                        if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isMediaOngoingAllowed()) {
                            Log.i("MediaOngoingActivity", "updateMediaVisibility. nm.notifyAsUser");
                            if (notificationManager != null) {
                                notificationManager.notifyAsUser(null, 12030705, ongoingActivityController.getMediaDummyNotification(notificationManager), UserHandle.CURRENT);
                            }
                            ongoingActivityController.updateMediaChipData();
                            return;
                        }
                        Log.i("MediaOngoingActivity", "updateMediaVisibility. nm.cancel");
                        if (notificationManager != null) {
                            notificationManager.cancel(12030705);
                        }
                    }
                }
            }
        };
        this.mSettingChangedCallback = onChangedCallback;
        settingsHelper.registerCallback(onChangedCallback, Settings.System.getUriFor(SettingsHelper.INDEX_MEDIA_ONGOING));
        this.mLinearLayoutManager = new LinearLayoutManager(context);
        this.mStatusBarState = 0;
        Looper myLooper = Looper.myLooper();
        myLooper.getClass();
        this.mediaPauseTimerHandler = new Handler(myLooper);
        this.isScreenTurnedOn = true;
        this.delayedEntry = new ConcurrentLinkedQueue();
        this.isNightMode = context.getResources().getConfiguration().isNightModeActive();
        this.historyDump = new HistoryDumpList(100);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.clearPipRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$clearPipRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
                NotificationLockscreenUserManager notificationLockscreenUserManager2 = OngoingActivityController.this.userManager;
                ongoingActivityDataHelper.getClass();
                CopyOnWriteArrayList copyOnWriteArrayList = OngoingActivityDataHelper.pipEnabledComponentNameList;
                if (copyOnWriteArrayList.size() != 0) {
                    Log.i(OngoingActivityDataHelper.TAG, "clearPipEnabledComponentNameList");
                    copyOnWriteArrayList.clear();
                    OngoingActivityDataHelper.updateOngoingList(notificationLockscreenUserManager2);
                }
            }
        };
        this.mMediaChipText = "";
        this.mMediaChipColor = -1;
        this.mMediaPackageName = "";
        this.containerOnLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$containerOnLayoutChangeListener$1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                int i9;
                int i10;
                OngoingChipAdapter ongoingChipAdapter = OngoingActivityController.this.mOngoingChipAdapter;
                if (ongoingChipAdapter == null || (i10 = ongoingChipAdapter.enableMaxWidth) == (i9 = i3 - i)) {
                    return;
                }
                String m = ListImplementation$$ExternalSyntheticOutline0.m(i10, i9, "updateEnableMaxWidth : ", " -:> ");
                String str = ongoingChipAdapter.TAG;
                Log.d(str, m);
                ongoingChipAdapter.enableMaxWidth = i9;
                if (ongoingChipAdapter.marqueeState == OngoingChipAdapter.MarqueeState.INIT) {
                    ongoingChipAdapter.notifyDataSetChanged();
                } else {
                    Log.i(str, "updateEnableMaxWidth() set MarqueeState.WAIT_FINISH");
                    ongoingChipAdapter.marqueeState = OngoingChipAdapter.MarqueeState.WAIT_FINISH;
                }
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
        this.ongoingChipItemDecoration = new RecyclerView.ItemDecoration() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$ongoingChipItemDecoration$1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public final void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
                super.getItemOffsets(rect, view, recyclerView, state);
                recyclerView.getClass();
                if (RecyclerView.getChildAdapterPosition(view) != state.getItemCount() - 1) {
                    OngoingActivityController ongoingActivityController = OngoingActivityController.this;
                    int m = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(ongoingActivityController.mContext);
                    ongoingActivityController.mLayoutMode = m;
                    IndicatorScaleGardener indicatorScaleGardener2 = ongoingActivityController.indicatorScaleGardener;
                    if (m != 1) {
                        rect.left = (int) ((ongoingActivityController.mContext.getResources().getDimensionPixelOffset(R.dimen.ongoing_activity_chip_layer_offset) - ongoingActivityController.mContext.getResources().getDimensionPixelOffset(R.dimen.ongoing_activity_chip_min_width)) * indicatorScaleGardener2.getLatestScaleModel(ongoingActivityController.mContext).ratio);
                    } else {
                        rect.right = (int) ((ongoingActivityController.mContext.getResources().getDimensionPixelOffset(R.dimen.ongoing_activity_chip_layer_offset) - ongoingActivityController.mContext.getResources().getDimensionPixelOffset(R.dimen.ongoing_activity_chip_min_width)) * indicatorScaleGardener2.getLatestScaleModel(ongoingActivityController.mContext).ratio);
                    }
                }
            }
        };
        this.notifListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$notifListener$1
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryAdded(NotificationEntry notificationEntry) {
                onEntryUpdated(notificationEntry);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
                OngoingActivityDataHelper.INSTANCE.getClass();
                ConcurrentHashMap concurrentHashMap = OngoingActivityDataHelper.onlyShownNowbarItemMap;
                boolean containsKey = concurrentHashMap.containsKey(notificationEntry.mKey);
                String str = notificationEntry.mKey;
                if (containsKey) {
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("OnlyShownNowbar - onEntryRemoved() ", str, "{OngoingActivityController}");
                    String key = notificationEntry.mSbn.getKey();
                    Log.i(OngoingActivityDataHelper.TAG, "removeNowbarItemOnlyShownNowbar()");
                    concurrentHashMap.remove(key);
                    OngoingActivityDataHelper.notifyRemoveItemNowbarObservers(key);
                }
                if (notificationEntry.isOngoingActivity() || (NotiRune.NOTI_ONGOING_GEMINI_DEMO && OngoingActivityDataHelper.isExceptionalOngoingActivity(notificationEntry))) {
                    OngoingActivityController ongoingActivityController = OngoingActivityController.this;
                    MediaSessions$H$$ExternalSyntheticOutline0.m("onEntryRemoved() ", str, " is removed remove is ", ongoingActivityController.isUpdateNotAllowed ? "NOT allowed" : "allowed", "{OngoingActivityController}");
                    if (ongoingActivityController.isUpdateNotAllowed) {
                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("onEntryRemoved() add delayedEntry for removed. ", str, "{OngoingActivityController}");
                        ongoingActivityController.delayedEntry.add(new Pair(notificationEntry, Boolean.FALSE));
                    } else {
                        OngoingActivityController.access$removeOngoingActivityData(notificationEntry, ongoingActivityController);
                    }
                    HistoryDumpList historyDumpList = ongoingActivityController.historyDump;
                    historyDumpList.getClass();
                    historyDumpList.add(str + " is REMOVED ");
                }
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryUpdated(NotificationEntry notificationEntry) {
                Parcelable[] parcelableArray;
                OngoingActivityDataHelper.INSTANCE.getClass();
                int size = OngoingActivityDataHelper.hiddenOngoingActivityDataList.size() + OngoingActivityDataHelper.mOngoingActivityLists.size();
                OngoingActivityController ongoingActivityController = OngoingActivityController.this;
                if (size == 0) {
                    Log.i("{OngoingActivityController}", "onEntryUpdated and OA data is empty. So update latest task info");
                    Iterator<ActivityManager.RunningTaskInfo> it = ((ActivityManager) ongoingActivityController.mContext.getSystemService("activity")).getRunningTasks(1).iterator();
                    while (it.hasNext()) {
                        ComponentName componentName = it.next().baseActivity;
                        if (componentName != null) {
                            OngoingActivityDataHelper.INSTANCE.getClass();
                            OngoingActivityDataHelper.setBaseActivityComponentName(componentName, ongoingActivityController.userManager);
                        }
                    }
                }
                OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
                ongoingActivityDataHelper.getClass();
                boolean isOnlyShownNowbar = OngoingActivityDataHelper.isOnlyShownNowbar(notificationEntry);
                String str = notificationEntry.mKey;
                if (isOnlyShownNowbar) {
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("OnlyShownNowbar - onEntryUpdated() ", str, "{OngoingActivityController}");
                    Context context2 = ongoingActivityController.mContext;
                    OngoingActivityData ongoingActivityData = new OngoingActivityData(notificationEntry, notificationEntry.mSbn, context2);
                    OngoingActivityDataHelper.onlyShownNowbarItemMap.put(str, ongoingActivityData);
                    ongoingActivityDataHelper.getClass();
                    OngoingActivityDataHelper.createOngoingView(context2, ongoingActivityData);
                    OngoingActivityDataHelper.notifyUpdateItemNowbarObservers(OngoingActivityDataHelper.convertOngoingActivityData(ongoingActivityData));
                }
                if (notificationEntry.isOngoingActivity() || (NotiRune.NOTI_ONGOING_GEMINI_DEMO && OngoingActivityDataHelper.isExceptionalOngoingActivity(notificationEntry))) {
                    boolean z = OngoingActivityDataHelper.getOngoingActivityDataByKey(str) == null;
                    ExifInterface$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("onEntryUpdated() ", str, " is ", z ? "added" : "updated", " and update is "), ongoingActivityController.isUpdateNotAllowed ? "NOT allowed" : "allowed", "{OngoingActivityController}");
                    if (ongoingActivityController.isUpdateNotAllowed) {
                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("onEntryUpdated() add delayedEntry for update. ", str, "{OngoingActivityController}");
                        ongoingActivityController.delayedEntry.add(new Pair(notificationEntry, Boolean.TRUE));
                    } else {
                        ArrayList arrayList = new ArrayList();
                        Iterator it2 = ongoingActivityController.delayedEntry.iterator();
                        while (it2.hasNext()) {
                            Pair pair = (Pair) it2.next();
                            if (Intrinsics.areEqual(((NotificationEntry) pair.getFirst()).mKey, str)) {
                                arrayList.add(pair);
                            }
                        }
                        ongoingActivityController.delayedEntry.removeAll(CollectionsKt___CollectionsKt.toSet(arrayList));
                        OngoingActivityController.access$updateOngoingActivityData(notificationEntry, ongoingActivityController);
                        if (z) {
                            ongoingActivityController.startMarqueeAnimation();
                            NotificationSAUtil.sendOALog(SystemUIAnalytics.OAID_ONGOING_CHIP_GENERATED, notificationEntry);
                        }
                    }
                    HistoryDumpList historyDumpList = ongoingActivityController.historyDump;
                    historyDumpList.getClass();
                    historyDumpList.add(str + " is ADDED ");
                } else if (OngoingActivityDataHelper.getOngoingActivityDataByKey(str) != null) {
                    OngoingActivityController.access$removeOngoingActivityData(notificationEntry, ongoingActivityController);
                }
                if (!LsRune.LOCKUI_NOW_BAR_DEMO || (parcelableArray = notificationEntry.mSbn.getNotification().extras.getParcelableArray("android.messages")) == null) {
                    return;
                }
                List<Notification.MessagingStyle.Message> messagesFromBundleArray = Notification.MessagingStyle.Message.getMessagesFromBundleArray(parcelableArray);
                String obj = messagesFromBundleArray.size() > 0 ? ((Notification.MessagingStyle.Message) PreferenceGroupAdapter$$ExternalSyntheticOutline0.m(1, messagesFromBundleArray)).getText().toString() : "";
                Log.i("{OngoingActivityController}", "triggerTest " + obj);
                if (!obj.equals("morning")) {
                    if (obj.equalsIgnoreCase("7")) {
                        Context context3 = ongoingActivityController.mContext;
                        Intent intent = new Intent("com.google.android.samples.ambient.app.test.SIMULATE_GAMES");
                        intent.setPackage("com.google.android.samples.ambient.app.test");
                        context3.sendBroadcast(intent);
                    } else if (obj.equalsIgnoreCase("8")) {
                        Context context4 = ongoingActivityController.mContext;
                        Intent intent2 = new Intent("com.google.android.samples.ambient.app.test.END_WRITE_SESSION");
                        intent2.setPackage("com.google.android.samples.ambient.app.test");
                        context4.sendBroadcast(intent2);
                    } else if (obj.equalsIgnoreCase("1")) {
                        Context context5 = ongoingActivityController.mContext;
                        Intent intent3 = new Intent("order.complete.baemin");
                        intent3.setPackage("com.example.sanavditest");
                        context5.sendBroadcast(intent3);
                    } else if (obj.equalsIgnoreCase("2")) {
                        Context context6 = ongoingActivityController.mContext;
                        Intent intent4 = new Intent("start.delievery");
                        intent4.setPackage("com.example.sanavditest");
                        context6.sendBroadcast(intent4);
                    } else {
                        if (!obj.equalsIgnoreCase("3")) {
                            notificationEntry.mBlockVisible = false;
                            return;
                        }
                        Context context7 = ongoingActivityController.mContext;
                        Intent intent5 = new Intent("finish");
                        intent5.setPackage("com.example.sanavditest");
                        context7.sendBroadcast(intent5);
                    }
                }
                notificationEntry.mBlockVisible = true;
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
                        ongoingChipAdapter.getClass();
                        if (ongoingChipAdapter.getItemCount() > 0) {
                            RecyclerView recyclerView = ongoingActivityController.mCapsuleRecyclerView;
                            if (recyclerView != null) {
                                ongoingActivityController.mOngoingChipAdapter.getClass();
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
                        ongoingCardController.onDestroy(false);
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
                ongoingActivityController.updateParentViewVisibility(false);
                ongoingActivityController.killGhost();
            }
        };
        this.configurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$configurationListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                Log.i("{OngoingActivityController}", "onConfigChanged()!! - " + configuration);
                int i = OngoingActivityController.$r8$clinit;
                OngoingActivityController ongoingActivityController = OngoingActivityController.this;
                ongoingActivityController.updateParentViewVisibility(true);
                if (configuration != null) {
                    Configuration configuration2 = ongoingActivityController.mConfig;
                    boolean z = configuration2.fontScale == configuration.fontScale;
                    boolean areEqual = Intrinsics.areEqual(configuration2.getLocales().get(0), configuration.getLocales().get(0));
                    Configuration configuration3 = ongoingActivityController.mConfig;
                    boolean z2 = configuration3.densityDpi != configuration.densityDpi;
                    boolean z3 = configuration3.orientation != configuration.orientation;
                    if (!z || z2 || !areEqual || z3) {
                        ongoingActivityController.reinflateMediaFrame();
                        OngoingCardController ongoingCardController = ongoingActivityController.mOngoingCardController;
                        if ((ongoingCardController != null ? ongoingCardController.oaCardState : null) == OngoingCardController.OaCardState.DISPLAY) {
                            OngoingActivityDataHelper.INSTANCE.getClass();
                            OngoingActivityDataHelper.updateMediaProgressAndMarqueeStateIfNeeded(null, null);
                        }
                    }
                }
                if (MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(ongoingActivityController.mContext) != ongoingActivityController.mLayoutMode) {
                    RecyclerView recyclerView = ongoingActivityController.mCapsuleRecyclerView;
                    OngoingActivityController$ongoingChipItemDecoration$1 ongoingActivityController$ongoingChipItemDecoration$1 = ongoingActivityController.ongoingChipItemDecoration;
                    if (recyclerView != null) {
                        recyclerView.removeItemDecoration(ongoingActivityController$ongoingChipItemDecoration$1);
                    }
                    RecyclerView recyclerView2 = ongoingActivityController.mCapsuleRecyclerView;
                    if (recyclerView2 != null) {
                        recyclerView2.addItemDecoration(ongoingActivityController$ongoingChipItemDecoration$1);
                    }
                }
                configuration.getClass();
                boolean isNightModeActive = configuration.isNightModeActive();
                if (ongoingActivityController.isNightMode != isNightModeActive) {
                    OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
                    Context context2 = ongoingActivityController.mContext;
                    ongoingActivityDataHelper.getClass();
                    OngoingActivityDataHelper.updateOngoingActivityViews(context2, false, true);
                    ongoingActivityController.isNightMode = isNightModeActive;
                }
                ongoingActivityController.mConfig = new Configuration(configuration);
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                ViewGroup.LayoutParams layoutParams;
                OngoingActivityController ongoingActivityController = OngoingActivityController.this;
                float f = ongoingActivityController.indicatorScaleGardener.getLatestScaleModel(ongoingActivityController.mContext).ratio;
                RecyclerView recyclerView = ongoingActivityController.mCapsuleRecyclerView;
                if (recyclerView != null && (layoutParams = recyclerView.getLayoutParams()) != null) {
                    layoutParams.height = MathKt__MathJVMKt.roundToInt(ongoingActivityController.mContext.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_top_height) * f);
                }
                OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
                Context context2 = ongoingActivityController.mContext;
                ongoingActivityDataHelper.getClass();
                OngoingActivityDataHelper.updateOngoingActivityViews(context2, true, false);
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
                OngoingActivityController.this.update$8();
            }
        };
        this.screenListener = new ScreenStatusProvider.ScreenListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$screenListener$1
            @Override // com.android.systemui.unfold.updates.screen.ScreenStatusProvider.ScreenListener
            public final void onScreenTurnedOn() {
                OngoingActivityController.this.isScreenTurnedOn = true;
            }

            @Override // com.android.systemui.unfold.updates.screen.ScreenStatusProvider.ScreenListener
            public final void onScreenTurningOff() {
                Log.d("{OngoingActivityController}", " screen OFF ");
                OngoingActivityController ongoingActivityController = OngoingActivityController.this;
                ongoingActivityController.isScreenTurnedOn = false;
                OngoingCardController ongoingCardController = ongoingActivityController.mOngoingCardController;
                if (ongoingCardController != null) {
                    ongoingCardController.isScreenTurnedOn = false;
                }
                CollapsedStatusBarFragment.OngoingActivityListenerImpl ongoingActivityListenerImpl = ongoingActivityController.ongoingActivityListener;
                if (ongoingActivityListenerImpl != null) {
                    View view = ongoingActivityController.mParentView;
                    view.getClass();
                    Log.d("CollapsedStatusBarFragment", "cancelOngoingAnimation: -> Screen State Changed");
                    ChipAnimationController chipAnimationController = CollapsedStatusBarFragment.this.mChipAnimationController;
                    chipAnimationController.getClass();
                    BuildersKt.launch$default(chipAnimationController.coroutineScope, null, null, new ChipAnimationController$cancelChipAnimation$1(chipAnimationController, view, null), 3);
                }
            }

            @Override // com.android.systemui.unfold.updates.screen.ScreenStatusProvider.ScreenListener
            public final void onScreenTurningOn() {
            }
        };
        this.mediaPanelVisibilityListener = new SecMediaHost.MediaPanelVisibilityListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$mediaPanelVisibilityListener$1
            @Override // com.android.systemui.media.SecMediaHost.MediaPanelVisibilityListener
            public final void onMediaVisibilityChanged(boolean z) {
                OngoingActivityController ongoingActivityController = OngoingActivityController.this;
                Log.i("MediaOngoingActivity", "onMediaVisibilityChanged. isMediaVisible:" + ongoingActivityController.isMediaVisible + ", visible:" + z);
                if (ongoingActivityController.isMediaVisible == z) {
                    return;
                }
                NotificationManager notificationManager = (NotificationManager) ongoingActivityController.mContext.getSystemService(NotificationManager.class);
                if (z && ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isMediaOngoingAllowed()) {
                    Log.i("MediaOngoingActivity", "onMediaVisibilityChanged. nm.notifyAsUser");
                    if (notificationManager != null) {
                        notificationManager.notifyAsUser(null, 12030705, ongoingActivityController.getMediaDummyNotification(notificationManager), UserHandle.CURRENT);
                    }
                } else if (notificationManager != null) {
                    Log.i("MediaOngoingActivity", "onMediaVisibilityChanged. nm.cancel");
                    notificationManager.cancel(12030705);
                    ongoingActivityController.currentMediaOngoingActivityInfo = null;
                }
                ongoingActivityController.isMediaVisible = z;
            }
        };
        this.removeAllMediaRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$removeAllMediaRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                OngoingActivityDataHelper.INSTANCE.getClass();
                Log.i("MediaOngoingActivity", "60sec timer expired. removeAllMediaRunnable run media : " + OngoingActivityDataHelper.getMediaData());
                if (OngoingActivityDataHelper.getMediaData() != null) {
                    OngoingActivityController ongoingActivityController = OngoingActivityController.this;
                    SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) ongoingActivityController.mediaHost.mMediaPlayerData.get(MediaType.OA);
                    if (secMediaPlayerData != null) {
                        Iterator it = secMediaPlayerData.getMediaData().iterator();
                        while (it.hasNext()) {
                            ongoingActivityController.mediaDataManager.dismissMediaData((String) ((Map.Entry) it.next()).getKey(), 0L, true);
                        }
                    }
                }
            }
        };
        this.dismissMediaRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$dismissMediaRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                OngoingActivityDataHelper.INSTANCE.getClass();
                OngoingActivityData mediaData = OngoingActivityDataHelper.getMediaData();
                if (mediaData != null) {
                    OngoingActivityController ongoingActivityController = OngoingActivityController.this;
                    Log.i("MediaOngoingActivity", " dismissMediaRunnable dismiss!");
                    ongoingActivityController.notifCollection.dismissOngoingActivityNotification(mediaData.mNotiID);
                }
            }
        };
    }

    public static final ActivityManager.RunningTaskInfo access$getTaskInfo(OngoingActivityController ongoingActivityController, int i) {
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) ongoingActivityController.mContext.getSystemService("activity")).getRunningTasks(3);
        for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTasks) {
            if (runningTaskInfo.taskId == i) {
                return runningTaskInfo;
            }
        }
        Log.e("{OngoingActivityController}", "getTaskInfo find task " + i + " fail. taskInfoList.size:" + runningTasks.size());
        return null;
    }

    public static final void access$removeOngoingActivityData(NotificationEntry notificationEntry, OngoingActivityController ongoingActivityController) {
        boolean z;
        View view;
        OngoingCardController ongoingCardController = ongoingActivityController.mOngoingCardController;
        if (ongoingCardController == null) {
            OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
            StatusBarNotification statusBarNotification = notificationEntry.mSbn;
            ongoingActivityDataHelper.getClass();
            OngoingActivityDataHelper.removeOngoingActivityByKey(statusBarNotification.getKey());
            ongoingActivityController.updateParentViewVisibility(true);
            return;
        }
        String key = notificationEntry.mSbn.getKey();
        CardStackView cardStackView = ongoingCardController.mCardStackView;
        if (cardStackView.removingSbnId.equals(key)) {
            KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("Clear removingSbnId:", key, "{OngoingActivityCardStackView}");
            cardStackView.removingSbnId = "";
            cardStackView.isRunningSwipeDismissTopCardMove = false;
            cardStackView.isAnimating = false;
            z = true;
        } else {
            z = false;
        }
        if (z) {
            OngoingActivityDataHelper ongoingActivityDataHelper2 = OngoingActivityDataHelper.INSTANCE;
            StatusBarNotification statusBarNotification2 = notificationEntry.mSbn;
            ongoingActivityDataHelper2.getClass();
            OngoingActivityDataHelper.removeOngoingActivityByKey(statusBarNotification2.getKey());
            ongoingActivityController.updateParentViewVisibility(true);
            return;
        }
        OngoingActivityDataHelper ongoingActivityDataHelper3 = OngoingActivityDataHelper.INSTANCE;
        String key2 = notificationEntry.mSbn.getKey();
        ongoingActivityDataHelper3.getClass();
        Iterator it = OngoingActivityDataHelper.mOngoingActivityLists.iterator();
        int i = 0;
        while (true) {
            if (!it.hasNext()) {
                i = -1;
                break;
            }
            int i2 = i + 1;
            if (Intrinsics.areEqual(((OngoingActivityData) it.next()).mNotiID, key2)) {
                break;
            } else {
                i = i2;
            }
        }
        if (i != 0) {
            OngoingActivityDataHelper ongoingActivityDataHelper4 = OngoingActivityDataHelper.INSTANCE;
            StatusBarNotification statusBarNotification3 = notificationEntry.mSbn;
            ongoingActivityDataHelper4.getClass();
            OngoingActivityDataHelper.removeOngoingActivityByKey(statusBarNotification3.getKey());
            ongoingActivityController.updateParentViewVisibility(true);
            return;
        }
        Log.i("{OngoingActivityController}", "removeOngoingActivityData. position:" + i + ". Remove TOP card entry");
        final OngoingCardController ongoingCardController2 = ongoingActivityController.mOngoingCardController;
        if (ongoingCardController2 != null) {
            final OngoingActivityController$$ExternalSyntheticLambda0 ongoingActivityController$$ExternalSyntheticLambda0 = new OngoingActivityController$$ExternalSyntheticLambda0(notificationEntry, ongoingActivityController);
            ongoingCardController2.onAllowStateChanged(true);
            final CardStackView cardStackView2 = ongoingCardController2.mCardStackView;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    OngoingActivityController$$ExternalSyntheticLambda0.this.invoke();
                    Log.i("{OngoingExpandedPipController}", "runCardRemoveAnimation call onAllowStateChanged(false)");
                    ongoingCardController2.onAllowStateChanged(false);
                    return Unit.INSTANCE;
                }
            };
            cardStackView2.getClass();
            Log.i("{OngoingActivityCardStackView}", "removeTopCardAnimation()");
            OngoingActivityDataHelper.INSTANCE.getClass();
            if (OngoingActivityDataHelper.mOngoingActivityLists.size() != 0) {
                NotificationSAUtil.sendOALog(SystemUIAnalytics.OAID_ONGOING_SWIPE_TO_SEE_OTHER_ACTIVITY, OngoingActivityDataHelper.getDataByIndex(0).mNotificationEntry);
            }
            if (cardStackView2.gutsDisplay) {
                cardStackView2.disableTopCardGuts(true);
            }
            cardStackView2.isAnimating = true;
            CustomAnimationSet customAnimationSet = new CustomAnimationSet(new CardStackView$$ExternalSyntheticLambda0(cardStackView2, 3));
            customAnimationSet.additionalEndListener = function0;
            final View childAt = cardStackView2.getChildAt(cardStackView2.getTopViewIndex());
            if (childAt == null) {
                Log.e("{OngoingActivityCardStackView}", "removeAnimationSet get topView fail");
                function0.invoke();
                return;
            }
            Log.i("{OngoingActivityCardStackView}", "removeAnimationSet run()");
            OngoingCardAdapter ongoingCardAdapter = cardStackView2.adapter;
            ongoingCardAdapter.getClass();
            OngoingCardController$$ExternalSyntheticLambda0 ongoingCardController$$ExternalSyntheticLambda0 = ongoingCardAdapter.getMediaCardView;
            if (ongoingCardController$$ExternalSyntheticLambda0 == null) {
                Log.e("MediaOngoingActivity", "getMediaCard. lambda is not initialized");
                view = null;
            } else {
                view = (View) ongoingCardController$$ExternalSyntheticLambda0.mo779invoke(Unit.INSTANCE);
            }
            if (Intrinsics.areEqual(view, childAt)) {
                CardStackView.getBaseColorView(childAt).setBackground(cardStackView2.getContext().getResources().getDrawable(R.drawable.sec_ongoing_card_bg));
            }
            DynamicAnimation.AnonymousClass4 anonymousClass4 = DynamicAnimation.SCALE_X;
            customAnimationSet.add(childAt, anonymousClass4, 0.7f, 0.875f, 400.0f, 0L);
            DynamicAnimation.AnonymousClass5 anonymousClass5 = DynamicAnimation.SCALE_Y;
            customAnimationSet.add(childAt, anonymousClass5, 0.7f, 0.875f, 400.0f, 0L);
            final ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
            ofFloat.setDuration(250L);
            PathInterpolator pathInterpolator = CardStackView.cardRemoveAlphaInterpolator;
            ofFloat.setInterpolator(pathInterpolator);
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$removeTopCardAnimation$2$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float floatValue = ((Float) ofFloat.getAnimatedValue()).floatValue();
                    CardStackView cardStackView3 = cardStackView2;
                    View view2 = childAt;
                    CardStackView.Companion companion = CardStackView.Companion;
                    cardStackView3.getClass();
                    CardStackView.getBaseColorView(view2).setAlpha(floatValue);
                }
            });
            customAnimationSet.add(50L, ofFloat);
            View childAt2 = cardStackView2.getChildAt(cardStackView2.getTopViewIndex());
            final ViewGroup viewGroup = childAt2 != null ? (ViewGroup) childAt2.findViewWithTag("BottomCardShadowView") : null;
            final ValueAnimator ofFloat2 = ValueAnimator.ofFloat(1.0f, 0.0f);
            ofFloat2.setDuration(100L);
            ofFloat2.setInterpolator(pathInterpolator);
            ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$removeTopCardAnimation$2$2$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float floatValue = ((Float) ofFloat2.getAnimatedValue()).floatValue();
                    ViewGroup viewGroup2 = viewGroup;
                    if (viewGroup2 != null) {
                        viewGroup2.setAlpha(floatValue);
                    }
                }
            });
            customAnimationSet.add(0L, ofFloat2);
            if (cardStackView2.getTopViewIndex() > 0) {
                final View childAt3 = cardStackView2.getChildAt(cardStackView2.getTopViewIndex() - 1);
                CardStackView.ViewStatus viewStatus = (CardStackView.ViewStatus) cardStackView2.startViewStatusList.get(cardStackView2.getTopViewIndex());
                childAt3.setScaleX(0.92f);
                childAt3.setScaleY(0.92f);
                customAnimationSet.add(childAt3, DynamicAnimation.X, viewStatus.point.x, 0.8131728f, 200.0f, 250L);
                customAnimationSet.add(childAt3, DynamicAnimation.Y, viewStatus.point.y, 0.8131728f, 200.0f, 250L);
                customAnimationSet.add(childAt3, anonymousClass4, viewStatus.scaleX, 0.8131728f, 200.0f, 250L);
                customAnimationSet.add(childAt3, anonymousClass5, viewStatus.scaleY, 0.8131728f, 200.0f, 250L);
                final ValueAnimator ofFloat3 = ValueAnimator.ofFloat(0.0f, 1.0f);
                ofFloat3.setDuration(300L);
                ofFloat3.setInterpolator(pathInterpolator);
                ofFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$removeTopCardAnimation$2$3$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float floatValue = ((Float) ofFloat3.getAnimatedValue()).floatValue();
                        CardStackView cardStackView3 = cardStackView2;
                        View view2 = childAt3;
                        view2.getClass();
                        CardStackView.Companion companion = CardStackView.Companion;
                        cardStackView3.getClass();
                        CardStackView.getBaseColorView(view2).setAlpha(floatValue);
                    }
                });
                customAnimationSet.add(0L, ofFloat3);
            }
            customAnimationSet.start();
        }
    }

    public static final void access$updateOngoingActivityData(NotificationEntry notificationEntry, OngoingActivityController ongoingActivityController) {
        OngoingActivityData ongoingActivityData;
        Throwable th;
        int i;
        Integer num;
        Integer num2;
        ongoingActivityController.getClass();
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        Throwable th2 = null;
        boolean z = false;
        if (statusBarNotification.getNotification().getChannelId().equals("MediaOngoingActivity")) {
            String string = statusBarNotification.getNotification().extras.getString("android.title");
            if (string != null ? string.equals("MediaOngoingActivity") : false) {
                MediaOngoingActivityInfo mediaOngoingActivityInfo = new MediaOngoingActivityInfo("", 0, null, null);
                MediaOngoingActivityInfo mediaOngoingActivityInfo2 = ongoingActivityController.currentMediaOngoingActivityInfo;
                if (mediaOngoingActivityInfo2 != null) {
                    boolean z2 = ongoingActivityController.isMediaPlaying;
                    if (notificationEntry.mIsPlayingMediaOngoingActivity.booleanValue() != z2) {
                        notificationEntry.mIsPlayingMediaOngoingActivity = Boolean.valueOf(z2);
                    }
                    mediaOngoingActivityInfo = mediaOngoingActivityInfo2;
                }
                OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
                if (ongoingActivityController.mOngoingCardController == null && (num2 = ongoingActivityController.mStatusBarState) != null && num2.intValue() == 0) {
                    z = true;
                }
                ongoingActivityDataHelper.getClass();
                OngoingActivityData ongoingActivityData2 = new OngoingActivityData(mediaOngoingActivityInfo, notificationEntry);
                ongoingActivityData2.mNotificationEntry.mOnHideRawValueChangedListeners.addIfAbsent(OngoingActivityDataHelper.mOnHideRawValueChangedListener);
                if (!z) {
                    ongoingActivityData2.mNeedMarquee = Boolean.FALSE;
                }
                Log.i("MediaOngoingActivity", "updateOngoingActivity data: " + ongoingActivityData2);
                OngoingActivityDataHelper.pendingOngoingActivityDataList.put(ongoingActivityData2.mNotiID, ongoingActivityData2);
            }
        } else {
            OngoingActivityDataHelper ongoingActivityDataHelper2 = OngoingActivityDataHelper.INSTANCE;
            Context context = ongoingActivityController.mContext;
            boolean z3 = ongoingActivityController.mOngoingCardController == null && (num = ongoingActivityController.mStatusBarState) != null && num.intValue() == 0;
            ongoingActivityDataHelper2.getClass();
            if (NotiRune.NOTI_ONGOING_GEMINI_DEMO && OngoingActivityDataHelper.isExceptionalOngoingActivity(notificationEntry)) {
                ongoingActivityData = new OngoingActivityData(notificationEntry, context, 1, context.getColor(R.color.ongoing_gemini_primary_color), Icon.createWithResource(context, R.drawable.ongoing_gemini_app_icon));
                ArrayList arrayList = ongoingActivityData.mActions;
                if (arrayList != null) {
                    int size = arrayList.size();
                    int i2 = 0;
                    int i3 = 0;
                    while (i3 < size) {
                        Object obj = arrayList.get(i3);
                        i3++;
                        int i4 = i2 + 1;
                        if (i2 < 0) {
                            Throwable th3 = th2;
                            CollectionsKt__CollectionsKt.throwIndexOverflow();
                            throw th3;
                        }
                        Notification.Action action = (Notification.Action) obj;
                        if (i2 == 0) {
                            th = th2;
                            action = new Notification.Action.Builder(Icon.createWithResource(context, R.drawable.ongoing_btn_cancel), action.title, action.actionIntent).build();
                        } else if (i2 != 1) {
                            th = th2;
                        } else {
                            Bundle extras = action.getExtras();
                            boolean areEqual = Intrinsics.areEqual(extras != null ? extras.getString("LIVE_NOTIFICATION_ACTION_KEY") : th2, "ACTION_CONVERSATION_HOLD");
                            OngoingActivityDataHelper ongoingActivityDataHelper3 = OngoingActivityDataHelper.INSTANCE;
                            if (areEqual) {
                                ongoingActivityDataHelper3.getClass();
                                OngoingActivityDataHelper.geminiPlayStateChanged(true);
                                i = R.drawable.ic_ongoing_pause;
                            } else {
                                ongoingActivityDataHelper3.getClass();
                                OngoingActivityDataHelper.geminiPlayStateChanged(false);
                                i = R.drawable.ic_gemini_live;
                            }
                            th = th2;
                            action = new Notification.Action.Builder(Icon.createWithResource(context, i), action.title, action.actionIntent).build();
                        }
                        action.getClass();
                        arrayList.set(i2, action);
                        i2 = i4;
                        th2 = th;
                    }
                }
            } else {
                ongoingActivityData = new OngoingActivityData(notificationEntry, statusBarNotification, context);
            }
            NotificationEntry notificationEntry2 = ongoingActivityData.mNotificationEntry;
            notificationEntry2.mOnHideRawValueChangedListeners.addIfAbsent(OngoingActivityDataHelper.mOnHideRawValueChangedListener);
            OngoingActivityDataHelper.createOngoingView(context, ongoingActivityData);
            if (!z3) {
                ongoingActivityData.mNeedMarquee = Boolean.FALSE;
            }
            if (notificationEntry2.mIsRon.booleanValue()) {
                Notification notification2 = notificationEntry2.mSbn.getNotification();
                ongoingActivityData.mChipBackground = notification2.color;
                ongoingActivityData.mChipIcon = notification2.getSmallIcon();
                ongoingActivityData.mExpandedChipText = notification2.getShortCriticalText();
                ongoingActivityData.mNowbarPrimaryInfo = ongoingActivityData.mPrimaryInfo;
                ongoingActivityData.mNowbarSecondaryInfo = notification2.getShortCriticalText();
                Icon icon = (Icon) notification2.extras.getParcelable("android.largeIcon", Icon.class);
                if (icon == null) {
                    icon = notification2.getSmallIcon();
                }
                ongoingActivityData.mNowbarIcon = icon;
            }
            Log.i(OngoingActivityDataHelper.TAG, "updateOngoingActivity data: " + ongoingActivityData);
            OngoingActivityDataHelper.pendingOngoingActivityDataList.put(ongoingActivityData.mNotiID, ongoingActivityData);
        }
        ongoingActivityController.updateParentViewVisibility(true);
    }

    @Override // com.android.systemui.statusbar.phone.ongoingactivity.IOngoingObserver
    public final void add$1() {
        Log.d("{OngoingActivityController}", "add()");
        updateParentViewVisibility(true);
        updateAdapter();
    }

    public final boolean checkMediaChipViewState(CharSequence charSequence, String str, int i) {
        if (Intrinsics.areEqual(this.mMediaChipText, charSequence) && this.mMediaChipColor == i && Intrinsics.areEqual(this.mMediaPackageName, str)) {
            return true;
        }
        this.mMediaChipText = charSequence;
        this.mMediaChipColor = i;
        this.mMediaPackageName = str;
        return false;
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        OngoingActivityDataHelper ongoingActivityDataHelper;
        AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0.m(printWriter, "   =========================================================================   ", "   Live Notifications   ", "   =========================================================================   ", "   ");
        printWriter.println("   OngoingActivityController State ");
        printWriter.println("   =========================================================================   ");
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "   isUpdateNotAllowed : ", this.isUpdateNotAllowed);
        printWriter.println("   isNightMode : " + this.isNightMode);
        printWriter.println("   Delayed remove entry list");
        ConcurrentLinkedQueue concurrentLinkedQueue = this.delayedEntry;
        if (concurrentLinkedQueue != null) {
            Iterator it = concurrentLinkedQueue.iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                if (((Boolean) pair.getSecond()).booleanValue()) {
                    printWriter.println("        :update:" + ((NotificationEntry) pair.getFirst()).mSbn.getKey() + " ");
                } else {
                    printWriter.println("        :remove:" + ((NotificationEntry) pair.getFirst()).mSbn.getKey() + " ");
                }
            }
        }
        printWriter.println("   ");
        printWriter.println("   DataHelper State");
        OngoingActivityDataHelper.INSTANCE.getClass();
        printWriter.println("   =========================================================================   ");
        printWriter.println("   topComponent : " + OngoingActivityDataHelper.baseActivityComponentName);
        printWriter.println("   =========================================================================   ");
        printWriter.println("   Showing list");
        Iterator it2 = OngoingActivityDataHelper.mOngoingActivityLists.iterator();
        while (true) {
            boolean hasNext = it2.hasNext();
            ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
            if (!hasNext) {
                break;
            }
            OngoingActivityData ongoingActivityData = (OngoingActivityData) it2.next();
            ongoingActivityData.getClass();
            ongoingActivityDataHelper.getClass();
            OngoingActivityDataHelper.dataDump(ongoingActivityData, printWriter);
        }
        printWriter.println("   =========================================================================   ");
        printWriter.println("   Hidden list");
        Iterator it3 = OngoingActivityDataHelper.hiddenOngoingActivityDataList.iterator();
        while (it3.hasNext()) {
            OngoingActivityData ongoingActivityData2 = (OngoingActivityData) it3.next();
            ongoingActivityData2.getClass();
            ongoingActivityDataHelper.getClass();
            OngoingActivityDataHelper.dataDump(ongoingActivityData2, printWriter);
        }
        printWriter.println("   =========================================================================   ");
        printWriter.println("   Pending list");
        Iterator it4 = OngoingActivityDataHelper.pendingOngoingActivityDataList.entrySet().iterator();
        while (it4.hasNext()) {
            OngoingActivityData ongoingActivityData3 = (OngoingActivityData) ((Map.Entry) it4.next()).getValue();
            ongoingActivityDataHelper.getClass();
            OngoingActivityDataHelper.dataDump(ongoingActivityData3, printWriter);
        }
        printWriter.println("   =========================================================================   ");
        printWriter.println("   Ongoing Activity History ");
        printWriter.println("   ");
        HistoryDumpList historyDumpList = this.historyDump;
        if (historyDumpList != null) {
            Iterator<String> it5 = historyDumpList.iterator();
            while (it5.hasNext()) {
                it5.next();
                DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("       ", historyDumpList.poll(), printWriter);
            }
        }
    }

    public final Notification getMediaDummyNotification(NotificationManager notificationManager) {
        NotificationChannel notificationChannel = new NotificationChannel("MediaOngoingActivity", "MediaOngoingActivity", 2);
        notificationManager.createNotificationChannel(notificationChannel);
        Bundle bundle = new Bundle();
        bundle.putInt("android.ongoingActivityNoti.style", 1);
        return new Notification.Builder(this.mContext, notificationChannel.getId()).setSmallIcon(17304559).setContentTitle("MediaOngoingActivity").setChannelId(notificationChannel.getId()).setColor(16777215).addExtras(bundle).setVisibility(1).build();
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0135  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x00c0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initCapsuleLayout(android.view.View r22, android.view.View r23) {
        /*
            Method dump skipped, instructions count: 387
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController.initCapsuleLayout(android.view.View, android.view.View):void");
    }

    public final void killGhost() {
        OngoingActivityDataHelper ongoingActivityDataHelper;
        String str;
        OngoingActivityDataHelper ongoingActivityDataHelper2 = OngoingActivityDataHelper.INSTANCE;
        Collection allNotifs = ((NotifPipeline) this.commonNotifCollection).getAllNotifs();
        ongoingActivityDataHelper2.getClass();
        Iterator it = OngoingActivityDataHelper.mOngoingActivityLists.iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
            str = OngoingActivityDataHelper.TAG;
            if (!hasNext) {
                break;
            }
            OngoingActivityData ongoingActivityData = (OngoingActivityData) it.next();
            if (!allNotifs.contains(ongoingActivityData.mNotificationEntry)) {
                StringBuilder sb = new StringBuilder(" HIT ghost - ");
                String str2 = ongoingActivityData.mNotiID;
                sb.append(str2);
                sb.append(" will be killed from OL !!! ");
                Log.d(str, sb.toString());
                ongoingActivityDataHelper.getClass();
                OngoingActivityDataHelper.removeOngoingActivityByKey(str2);
            }
        }
        Iterator it2 = OngoingActivityDataHelper.hiddenOngoingActivityDataList.iterator();
        while (it2.hasNext()) {
            OngoingActivityData ongoingActivityData2 = (OngoingActivityData) it2.next();
            if (!allNotifs.contains(ongoingActivityData2.mNotificationEntry)) {
                StringBuilder sb2 = new StringBuilder(" HIT ghost - ");
                String str3 = ongoingActivityData2.mNotiID;
                sb2.append(str3);
                sb2.append(" will be killed from HL !!! ");
                Log.d(str, sb2.toString());
                ongoingActivityDataHelper.getClass();
                OngoingActivityDataHelper.removeOngoingActivityByKey(str3);
            }
        }
        for (Map.Entry entry : OngoingActivityDataHelper.pendingOngoingActivityDataList.entrySet()) {
            if (!allNotifs.contains(((OngoingActivityData) entry.getValue()).mNotificationEntry)) {
                Log.d(str, " HIT ghost - " + ((OngoingActivityData) entry.getValue()).mNotiID + " will be killed from PL !!! ");
                String str4 = ((OngoingActivityData) entry.getValue()).mNotiID;
                ongoingActivityDataHelper.getClass();
                OngoingActivityDataHelper.removeOngoingActivityByKey(str4);
            }
        }
    }

    public final void reinflateMediaFrame() {
        ViewGroup viewGroup = (ViewGroup) this.mMediaCardView.findViewById(R.id.stack_pip_layout);
        if (viewGroup != null) {
            ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
            OngoingActivityLayoutUtil ongoingActivityLayoutUtil = OngoingActivityLayoutUtil.INSTANCE;
            Context context = this.mContext;
            ongoingActivityLayoutUtil.getClass();
            layoutParams.width = OngoingActivityLayoutUtil.getOngoingCardWidth(context);
        }
        ViewGroup viewGroup2 = (ViewGroup) this.mMediaCardView.findViewById(R.id.stack_expand_contents);
        if (viewGroup2 != null) {
            Log.i("MediaOngoingActivity", "reinflate removeMediaFrame / addMediaFrame");
            viewGroup2.removeAllViews();
            MediaType mediaType = MediaType.OA;
            SecMediaHost secMediaHost = this.mediaHost;
            secMediaHost.removeMediaFrame(mediaType);
            secMediaHost.addMediaFrame(mediaType, viewGroup2);
            ViewGroup.LayoutParams layoutParams2 = viewGroup2.getLayoutParams();
            SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
            Context context2 = this.mContext;
            secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getClass();
            SecQSPanelResourceCommon.Companion.getClass();
            layoutParams2.height = SecQSPanelResourceCommon.Companion.dp(R.dimen.sec_qs_media_player_height_expanded, context2);
        }
    }

    @Override // com.android.systemui.statusbar.phone.ongoingactivity.IOngoingObserver
    public final void remove(int i) {
        OngoingChipAdapter ongoingChipAdapter;
        OngoingActivityDataHelper.INSTANCE.getClass();
        CopyOnWriteArrayList copyOnWriteArrayList = OngoingActivityDataHelper.mOngoingActivityLists;
        Log.i("{OngoingActivityController}", "remove position:" + i + ", oa data size:" + copyOnWriteArrayList.size());
        if ((i == 0 || copyOnWriteArrayList.size() <= 1) && (ongoingChipAdapter = this.mOngoingChipAdapter) != null) {
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
        ((HeadsUpManagerImpl) this.headsUpManager).addListener(new OnHeadsUpChangedListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$start$1
            @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
            public final void onHeadsUpPinnedModeChanged(boolean z) {
                OngoingCardController ongoingCardController;
                OngoingActivityController ongoingActivityController = OngoingActivityController.this;
                KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m(" onHeadsUpPinnedModeChanged - ", " : ", "{OngoingActivityController}", ongoingActivityController.mIsHeadsUpPinned, z);
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
            public final void onActivityPinned(int i, String str) {
                ComponentName componentName2;
                OngoingActivityDataHelper.INSTANCE.getClass();
                if (OngoingActivityDataHelper.hiddenOngoingActivityDataList.size() + OngoingActivityDataHelper.mOngoingActivityLists.size() == 0) {
                    Log.i("{OngoingActivityController}", "onActivityPinned but OA data is empty");
                    return;
                }
                OngoingActivityController ongoingActivityController = OngoingActivityController.this;
                ActivityManager.RunningTaskInfo access$getTaskInfo = OngoingActivityController.access$getTaskInfo(ongoingActivityController, i);
                Log.i("{OngoingActivityController}", "onActivityPinned packageName:" + str + ", baseActivity:" + (access$getTaskInfo != null ? access$getTaskInfo.baseActivity : null));
                if (access$getTaskInfo == null || (componentName2 = access$getTaskInfo.baseActivity) == null) {
                    return;
                }
                ongoingActivityController.mHandler.removeCallbacks(ongoingActivityController.clearPipRunnable);
                CopyOnWriteArrayList copyOnWriteArrayList = OngoingActivityDataHelper.pipEnabledComponentNameList;
                if (copyOnWriteArrayList.contains(componentName2)) {
                    return;
                }
                Log.i(OngoingActivityDataHelper.TAG, "updatePipEnabledComponentNameList add. name:" + componentName2);
                copyOnWriteArrayList.add(componentName2);
                OngoingActivityDataHelper.updateOngoingList(ongoingActivityController.userManager);
            }

            @Override // com.android.systemui.shared.system.TaskStackChangeListener
            public final void onActivityUnpinned() {
                Log.i("{OngoingActivityController}", "onActivityUnpinned clear pip request");
                OngoingActivityController ongoingActivityController = OngoingActivityController.this;
                ongoingActivityController.mHandler.removeCallbacks(ongoingActivityController.clearPipRunnable);
                ongoingActivityController.mHandler.postDelayed(ongoingActivityController.clearPipRunnable, 500L);
            }

            @Override // com.android.systemui.shared.system.TaskStackChangeListener
            public final void onTaskFocusChanged(int i, boolean z) {
                ComponentName componentName2;
                if (z) {
                    OngoingActivityDataHelper.INSTANCE.getClass();
                    if (OngoingActivityDataHelper.hiddenOngoingActivityDataList.size() + OngoingActivityDataHelper.mOngoingActivityLists.size() == 0) {
                        Log.i("{OngoingActivityController}", "onTaskFocusChanged but OA data is empty");
                        return;
                    }
                    OngoingActivityController ongoingActivityController = OngoingActivityController.this;
                    ActivityManager.RunningTaskInfo access$getTaskInfo = OngoingActivityController.access$getTaskInfo(ongoingActivityController, i);
                    Log.i("{OngoingActivityController}", "onTaskFocusChanged focused:" + z + ", baseActivity:" + (access$getTaskInfo != null ? access$getTaskInfo.baseActivity : null));
                    if (access$getTaskInfo != null && (componentName2 = access$getTaskInfo.baseActivity) != null) {
                        OngoingActivityDataHelper.setBaseActivityComponentName(componentName2, ongoingActivityController.userManager);
                        OngoingCardController ongoingCardController = ongoingActivityController.mOngoingCardController;
                        if (ongoingCardController != null) {
                            ongoingCardController.fadeOutCard();
                        }
                    }
                    ongoingActivityController.startMarqueeAnimation();
                }
            }
        });
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) this.mContext.getSystemService("activity")).getRunningTasks(1);
        List<ActivityManager.RunningTaskInfo> list = runningTasks;
        if (list == null || list.isEmpty() || (runningTaskInfo = runningTasks.get(0)) == null || (componentName = runningTaskInfo.baseActivity) == null) {
            return;
        }
        OngoingActivityDataHelper.INSTANCE.getClass();
        OngoingActivityDataHelper.setBaseActivityComponentName(componentName, this.userManager);
    }

    public final void startMarqueeAnimation() {
        this.mediaPauseTimerHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$startMarqueeAnimation$1
            @Override // java.lang.Runnable
            public final void run() {
                OngoingChipAdapter ongoingChipAdapter = OngoingActivityController.this.mOngoingChipAdapter;
                if (ongoingChipAdapter != null) {
                    ongoingChipAdapter.marqueeIfNeeded();
                }
            }
        });
    }

    @Override // com.android.systemui.statusbar.phone.ongoingactivity.IOngoingObserver
    public final void update(String str) {
        KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("update(", str, ")", "{OngoingActivityController}");
        updateParentViewVisibility(true);
        OngoingActivityDataHelper.INSTANCE.getClass();
        if (OngoingActivityDataHelper.mOngoingActivityLists.size() != 0) {
            String str2 = OngoingActivityDataHelper.getDataByIndex(0).mNotiID;
            Log.d("{OngoingActivityController}", "update check topItemKey:" + str2);
            if (Intrinsics.areEqual(str2, str)) {
                updateAdapter();
            }
        }
    }

    @Override // com.android.systemui.statusbar.phone.ongoingactivity.IOngoingObserver
    public final void update$8() {
        Log.d("{OngoingActivityController}", "update()");
        updateParentViewVisibility(true);
        updateAdapter();
    }

    public final void updateAdapter() {
        OngoingChipAdapter ongoingChipAdapter = this.mOngoingChipAdapter;
        if (ongoingChipAdapter != null) {
            ongoingChipAdapter.shouldShowChipOnly = this.ongoingCallController.hasOngoingCall();
        }
        OngoingChipAdapter ongoingChipAdapter2 = this.mOngoingChipAdapter;
        if (ongoingChipAdapter2 != null) {
            ongoingChipAdapter2.notifyDataSetChanged();
        }
    }

    public final void updateMediaChipData() {
        int i;
        int i2;
        boolean z;
        boolean z2;
        if (this.currentMediaOngoingActivityInfo == null) {
            return;
        }
        OngoingActivityDataHelper.INSTANCE.getClass();
        OngoingActivityData mediaData = OngoingActivityDataHelper.getMediaData();
        if (mediaData == null) {
            this.mMediaChipText = "";
            this.mMediaChipColor = -1;
            this.mMediaPackageName = "";
            final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            ref$ObjectRef.element = "";
            ConcurrentHashMap concurrentHashMap = OngoingActivityDataHelper.pendingOngoingActivityDataList;
            final Function2 function2 = new Function2() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$$ExternalSyntheticLambda1
                /* JADX WARN: Type inference failed for: r2v1, types: [T, java.lang.String] */
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ?? r2 = (String) obj;
                    int i3 = OngoingActivityController.$r8$clinit;
                    if (((OngoingActivityData) obj2).mIsMediaOngoingData) {
                        Ref$ObjectRef.this.element = r2;
                    }
                    return Unit.INSTANCE;
                }
            };
            concurrentHashMap.forEach(new BiConsumer() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$sam$java_util_function_BiConsumer$0
                @Override // java.util.function.BiConsumer
                public final /* synthetic */ void accept(Object obj, Object obj2) {
                    Function2.this.invoke(obj, obj2);
                }
            });
            Log.d("MediaOngoingActivity", "mediadata is not added yet. so we try to find it from pending list. and it is " + ref$ObjectRef.element);
            CharSequence charSequence = (CharSequence) ref$ObjectRef.element;
            if (charSequence == null || charSequence.length() == 0) {
                return;
            }
            Object remove = concurrentHashMap.remove(ref$ObjectRef.element);
            remove.getClass();
            NotificationEntry notificationEntry = ((OngoingActivityData) remove).mNotificationEntry;
            if (notificationEntry != null && notificationEntry.mIsPlayingMediaOngoingActivity.booleanValue() != (z2 = this.isMediaPlaying)) {
                notificationEntry.mIsPlayingMediaOngoingActivity = Boolean.valueOf(z2);
            }
            MediaOngoingActivityInfo mediaOngoingActivityInfo = this.currentMediaOngoingActivityInfo;
            mediaOngoingActivityInfo.getClass();
            OngoingActivityData ongoingActivityData = new OngoingActivityData(mediaOngoingActivityInfo, notificationEntry);
            concurrentHashMap.put(ref$ObjectRef.element, ongoingActivityData);
            CharSequence charSequence2 = ongoingActivityData.mExpandedChipText;
            if (charSequence2 == null) {
                charSequence2 = ongoingActivityData.mPrimaryInfo;
            }
            checkMediaChipViewState(charSequence2, ongoingActivityData.mPackageName, ongoingActivityData.mChipBackground);
            updateAdapter();
            return;
        }
        Boolean bool = mediaData.mNeedMarquee;
        NotificationEntry notificationEntry2 = mediaData.mNotificationEntry;
        if (notificationEntry2 != null && notificationEntry2.mIsPlayingMediaOngoingActivity.booleanValue() != (z = this.isMediaPlaying)) {
            notificationEntry2.mIsPlayingMediaOngoingActivity = Boolean.valueOf(z);
        }
        MediaOngoingActivityInfo mediaOngoingActivityInfo2 = this.currentMediaOngoingActivityInfo;
        mediaOngoingActivityInfo2.getClass();
        OngoingActivityData ongoingActivityData2 = new OngoingActivityData(mediaOngoingActivityInfo2, notificationEntry2);
        CopyOnWriteArrayList copyOnWriteArrayList = OngoingActivityDataHelper.hiddenOngoingActivityDataList;
        int i3 = 0;
        if (copyOnWriteArrayList != null) {
            Iterator it = copyOnWriteArrayList.iterator();
            i = -1;
            int i4 = 0;
            while (it.hasNext()) {
                if (((OngoingActivityData) it.next()).mIsMediaOngoingData) {
                    i = i4;
                }
                i4++;
            }
        } else {
            i = -1;
        }
        if (i > -1) {
            CopyOnWriteArrayList copyOnWriteArrayList2 = OngoingActivityDataHelper.hiddenOngoingActivityDataList;
            copyOnWriteArrayList2.remove(i);
            copyOnWriteArrayList2.add(i, ongoingActivityData2);
        } else {
            CopyOnWriteArrayList copyOnWriteArrayList3 = OngoingActivityDataHelper.mOngoingActivityLists;
            if (copyOnWriteArrayList3 != null) {
                Iterator it2 = copyOnWriteArrayList3.iterator();
                i2 = -1;
                while (it2.hasNext()) {
                    if (((OngoingActivityData) it2.next()).mIsMediaOngoingData) {
                        i2 = i3;
                    }
                    i3++;
                }
            } else {
                i2 = -1;
            }
            if (i2 > -1) {
                CopyOnWriteArrayList copyOnWriteArrayList4 = OngoingActivityDataHelper.mOngoingActivityLists;
                copyOnWriteArrayList4.remove(i2);
                copyOnWriteArrayList4.add(i2, ongoingActivityData2);
            }
        }
        CharSequence charSequence3 = ongoingActivityData2.mExpandedChipText;
        if (charSequence3 == null) {
            charSequence3 = ongoingActivityData2.mPrimaryInfo;
        }
        if (!checkMediaChipViewState(charSequence3, ongoingActivityData2.mPackageName, ongoingActivityData2.mChipBackground)) {
            Log.d("MediaOngoingActivity", "mediadata for chip view is updated. mNeedMarquee:true");
            ongoingActivityData2.mNeedMarquee = Boolean.TRUE;
            updateAdapter();
        } else {
            Log.d("MediaOngoingActivity", "mediadata for chip view is not updated. mNeedMarquee:(prev)" + bool);
            ongoingActivityData2.mNeedMarquee = bool;
        }
    }

    public final void updateParentViewVisibility(boolean z) {
        Log.d("{OngoingActivityController}", "updateParentViewVisibility() animate:" + z + " shouldVisible()");
        if (shouldVisible()) {
            CollapsedStatusBarFragment.OngoingActivityListenerImpl ongoingActivityListenerImpl = this.ongoingActivityListener;
            if (ongoingActivityListenerImpl != null) {
                this.mParentView.getClass();
                Log.d("CollapsedStatusBarFragment", "onShowActivityChip() animate:" + z);
                int i = CollapsedStatusBarFragment.$r8$clinit;
                CollapsedStatusBarFragment.this.updateStatusBarVisibilities(z);
                return;
            }
            return;
        }
        CollapsedStatusBarFragment.OngoingActivityListenerImpl ongoingActivityListenerImpl2 = this.ongoingActivityListener;
        if (ongoingActivityListenerImpl2 != null) {
            this.mParentView.getClass();
            Log.d("CollapsedStatusBarFragment", "onHideActivityChip() animate:" + z);
            int i2 = CollapsedStatusBarFragment.$r8$clinit;
            CollapsedStatusBarFragment.this.updateStatusBarVisibilities(z);
        }
    }
}
