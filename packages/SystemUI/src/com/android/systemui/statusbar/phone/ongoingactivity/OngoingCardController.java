package com.android.systemui.statusbar.phone.ongoingactivity;

import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.window.OnBackInvokedCallback;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.recyclerview.widget.RecyclerView;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.IndicatorGardenPresenter;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment;
import com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView;
import com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CustomAnimationSet;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController;
import com.android.systemui.statusbar.phone.ongoingactivity.animation.ViewPropertyCapture;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.statusbar.window.StatusBarWindowStateListener;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.NotificationSAUtil;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.view.SemWindowManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;

/* loaded from: classes3.dex */
public final class OngoingCardController implements View.OnTouchListener, IOngoingObserver, ConfigurationController.ConfigurationListener {
    public final ActivityStarter activityStarter;
    public final BroadcastDispatcher broadcastDispatcher;
    public final AnonymousClass8 broadcastReceiver;
    public final ConfigurationController configurationController;
    public Boolean foldState;
    public OngoingActivityController$$ExternalSyntheticLambda2 getMediaCardView;
    public final IndicatorGardenPresenter indicatorGardenPresenter;
    public OngoingActivityController$$ExternalSyntheticLambda2 isMediaPlaying;
    public boolean isOrientationChanged;
    public boolean isScreenTurnedOn;
    public boolean isWatchSelfValidationProc;
    public final View mCapsule;
    public final CardStackView mCardStackView;
    public final Context mContext;
    public final ViewGroup mExpandedView;
    public final AnonymousClass9 mFoldStateListener;
    public Integer mInitOrientation;
    public final BackKeyConsumerViewGroup mParentView;
    public boolean mProcDestroy;
    public final WindowManager mWindowManager;
    public final WindowManager.LayoutParams mWindowParams;
    public final Handler mainUIHandler;
    public final MediaDataManager mediaDataManager;
    public final SecMediaHost mediaHost;
    public final NotifCollection notifCollection;
    public OaCardState oaCardState;
    public final AnonymousClass7 onBackInvokedCallback;
    public final ArrayList onStateEventListeners;
    public final OngoingCardController$selfDestroyRunnable$1 selfDestroyRunnable;
    public final Handler selfValidationHandler;
    public OngoingActivityController$$ExternalSyntheticLambda2 setMediaCardView;
    public int statusBarVisibility;
    public final StatusBarWindowStateController statusBarWindowStateController;
    public final OngoingCardController$statusBarWindowStateListener$1 statusBarWindowStateListener;

    /* renamed from: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$6, reason: invalid class name */
    public final class AnonymousClass6 {
        public AnonymousClass6() {
        }

        public static void onChange(int i, int i2) {
            SuggestionsAdapter$$ExternalSyntheticOutline0.m(i, i2, "remainingCardsCount :: ", ", totalCardsCount :: ", "StackView");
        }
    }

    public final class BackKeyConsumerViewGroup extends FrameLayout {
        public BackKeyConsumerViewGroup(Context context) {
            super(context);
        }

        @Override // android.view.ViewGroup, android.view.View
        public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
            if (keyEvent != null && keyEvent.getKeyCode() == 4 && OngoingCardController.this.mCardStackView.isReadyCollapseAnimation()) {
                OngoingCardController.this.collapseAnimation();
            }
            return super.dispatchKeyEvent(keyEvent);
        }

        @Override // android.view.ViewGroup, android.view.View
        public final void onAttachedToWindow() {
            getViewRootImpl().getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, OngoingCardController.this.onBackInvokedCallback);
            super.onAttachedToWindow();
        }

        @Override // android.view.ViewGroup, android.view.View
        public final void onDetachedFromWindow() {
            getViewRootImpl().getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(OngoingCardController.this.onBackInvokedCallback);
            super.onDetachedFromWindow();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class OaCardState {
        public static final /* synthetic */ OaCardState[] $VALUES;
        public static final OaCardState COLLAPSE;
        public static final OaCardState DISPLAY;
        public static final OaCardState EXPAND;
        public static final OaCardState FADEOUT;
        public static final OaCardState INIT;

        static {
            OaCardState oaCardState = new OaCardState("INIT", 0);
            INIT = oaCardState;
            OaCardState oaCardState2 = new OaCardState("EXPAND", 1);
            EXPAND = oaCardState2;
            OaCardState oaCardState3 = new OaCardState("DISPLAY", 2);
            DISPLAY = oaCardState3;
            OaCardState oaCardState4 = new OaCardState("COLLAPSE", 3);
            COLLAPSE = oaCardState4;
            OaCardState oaCardState5 = new OaCardState("FADEOUT", 4);
            FADEOUT = oaCardState5;
            OaCardState[] oaCardStateArr = {oaCardState, oaCardState2, oaCardState3, oaCardState4, oaCardState5};
            $VALUES = oaCardStateArr;
            EnumEntriesKt.enumEntries(oaCardStateArr);
        }

        private OaCardState(String str, int i) {
        }

        public static OaCardState valueOf(String str) {
            return (OaCardState) Enum.valueOf(OaCardState.class, str);
        }

        public static OaCardState[] values() {
            return (OaCardState[]) $VALUES.clone();
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$collapseAnimation$2, reason: invalid class name */
    public final class AnonymousClass2 implements ViewPropertyCapture.ViewPropertyObserver {
        public AnonymousClass2() {
        }
    }

    /* JADX WARN: Type inference failed for: r10v0, types: [com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$statusBarWindowStateListener$1, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r10v1, types: [android.content.BroadcastReceiver, com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$8] */
    /* JADX WARN: Type inference failed for: r1v26, types: [com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$7] */
    /* JADX WARN: Type inference failed for: r1v28, types: [com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$selfDestroyRunnable$1] */
    /* JADX WARN: Type inference failed for: r2v7, types: [com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$9, com.samsung.android.view.SemWindowManager$FoldStateListener] */
    public OngoingCardController(Context context, ActivityStarter activityStarter, View view, IndicatorGardenPresenter indicatorGardenPresenter, IndicatorScaleGardener indicatorScaleGardener, ConfigurationController configurationController, BroadcastDispatcher broadcastDispatcher, NotifCollection notifCollection, NotificationRemoteInputManager notificationRemoteInputManager, StatusBarWindowStateController statusBarWindowStateController, Function1 function1, SecMediaHost secMediaHost, MediaDataManager mediaDataManager, FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper) {
        this.mContext = context;
        this.activityStarter = activityStarter;
        this.mCapsule = view;
        this.indicatorGardenPresenter = indicatorGardenPresenter;
        this.configurationController = configurationController;
        this.broadcastDispatcher = broadcastDispatcher;
        this.notifCollection = notifCollection;
        this.statusBarWindowStateController = statusBarWindowStateController;
        this.mediaHost = secMediaHost;
        this.mediaDataManager = mediaDataManager;
        ?? r10 = new StatusBarWindowStateListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$statusBarWindowStateListener$1
            @Override // com.android.systemui.statusbar.window.StatusBarWindowStateListener
            public final void onStatusBarWindowStateChanged(int i) {
                this.this$0.statusBarVisibility = i;
            }
        };
        this.statusBarWindowStateListener = r10;
        this.onStateEventListeners = new ArrayList();
        this.oaCardState = OaCardState.INIT;
        this.isScreenTurnedOn = true;
        Looper looperMyLooper = Looper.myLooper();
        looperMyLooper.getClass();
        this.selfValidationHandler = new Handler(looperMyLooper);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        this.mWindowManager = windowManager;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2226, 262688, -2);
        this.mWindowParams = layoutParams;
        layoutParams.setTitle("OngoingCard");
        layoutParams.gravity = 51;
        layoutParams.x = 0;
        layoutParams.y = 0;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.width = -1;
        layoutParams.height = -1;
        layoutParams.privateFlags |= 16;
        BackKeyConsumerViewGroup backKeyConsumerViewGroup = new BackKeyConsumerViewGroup(context);
        this.mParentView = backKeyConsumerViewGroup;
        ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.sec_ongoing_card_root_layout, backKeyConsumerViewGroup);
        this.mExpandedView = viewGroup;
        viewGroup.setOnTouchListener(this);
        View viewFindViewById = viewGroup.findViewById(R.id.card_stack_view);
        viewFindViewById.getClass();
        CardStackView cardStackView = (CardStackView) viewFindViewById;
        this.mCardStackView = cardStackView;
        OngoingCardAdapter ongoingCardAdapter = new OngoingCardAdapter(context, indicatorScaleGardener, notificationRemoteInputManager, secMediaHost, faceWidgetNotificationControllerWrapper);
        ongoingCardAdapter.getMediaCardView = new OngoingCardController$$ExternalSyntheticLambda0(this, 0);
        OngoingCardAdapter ongoingCardAdapter2 = cardStackView.adapter;
        if (ongoingCardAdapter2 != null) {
            ongoingCardAdapter2.unregisterDataSetObserver(cardStackView.dataObserver);
        }
        OngoingCardAdapter ongoingCardAdapter3 = cardStackView.adapter;
        if (ongoingCardAdapter3 != null) {
            ongoingCardAdapter3.registerDataSetObserver(cardStackView.dataObserver);
        }
        cardStackView.adapter = ongoingCardAdapter;
        cardStackView.dismiss = new OngoingCardController$$ExternalSyntheticLambda0(this, 1);
        cardStackView.isMediaPlaying = new OngoingCardController$$ExternalSyntheticLambda0(this, 2);
        cardStackView.indicatorScaleGardener = indicatorScaleGardener;
        cardStackView.collapseBackCall = new OngoingCardController$$ExternalSyntheticLambda0(this, 3);
        cardStackView.onChangeListener = new AnonymousClass6();
        this.mInitOrientation = Integer.valueOf(context.getResources().getConfiguration().orientation);
        updateCardViewLayout();
        OngoingActivityDataHelper.INSTANCE.getClass();
        ((ArrayList) OngoingActivityDataHelper.observers).add(this);
        windowManager.getClass();
        windowManager.addView(backKeyConsumerViewGroup, layoutParams);
        this.onBackInvokedCallback = new OnBackInvokedCallback() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController.7
            @Override // android.window.OnBackInvokedCallback
            public final void onBackInvoked() {
                if (OngoingCardController.this.mCardStackView.isReadyCollapseAnimation()) {
                    OngoingCardController.this.collapseAnimation();
                }
            }
        };
        ?? r102 = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController.8
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                boolean z = OngoingCardController.this.mProcDestroy;
                if (z) {
                    AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("BroadcastReceiver onReceive: discard by mProcDestroy:", "{OngoingExpandedPipController}", z);
                    return;
                }
                boolean zEquals = StringsKt__StringsJVMKt.equals(intent != null ? intent.getAction() : null, "com.android.systemui.edgelighting.start", false);
                boolean zEquals2 = StringsKt__StringsJVMKt.equals(intent != null ? intent.getStringExtra("reason") : null, "homekey", false);
                boolean zIsReadyCollapseAnimation = OngoingCardController.this.mCardStackView.isReadyCollapseAnimation();
                OngoingCardController ongoingCardController = OngoingCardController.this;
                if ((zEquals2 | zEquals) && (zIsReadyCollapseAnimation & ongoingCardController.isScreenTurnedOn)) {
                    ongoingCardController.collapseAnimation();
                }
            }
        };
        this.broadcastReceiver = r102;
        ?? r2 = new SemWindowManager.FoldStateListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController.9
            public final void onFoldStateChanged(boolean z) throws Exception {
                Log.i("{OngoingExpandedPipController}", "onFoldStateChanged start foldState:" + OngoingCardController.this.foldState + ", p0:" + z);
                Boolean bool = OngoingCardController.this.foldState;
                if (bool == null || bool.equals(Boolean.valueOf(z))) {
                    OngoingCardController.this.foldState = Boolean.valueOf(z);
                } else {
                    OngoingCardController.this.foldState = Boolean.valueOf(z);
                    Log.i("{OngoingExpandedPipController}", "onFoldStateChanged call onDestroy");
                    OngoingCardController.this.onDestroy(false);
                }
            }

            public final void onTableModeChanged(boolean z) {
            }
        };
        this.mFoldStateListener = r2;
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, r102, KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("com.android.systemui.edgelighting.start", PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS), null, null, 0, null, 60);
        ((ConfigurationControllerImpl) configurationController).addCallback(this);
        SemWindowManager.getInstance().registerFoldStateListener((SemWindowManager.FoldStateListener) r2, (Handler) Dependency.sDependency.getDependencyInner(Dependency.MAIN_HANDLER));
        OngoingActivityDataHelper.cardIsShown = true;
        ((HashSet) statusBarWindowStateController.listeners).add(r10);
        this.selfDestroyRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$selfDestroyRunnable$1
            @Override // java.lang.Runnable
            public final void run() throws Exception {
                OngoingCardController ongoingCardController = this.this$0;
                if (!ongoingCardController.isWatchSelfValidationProc) {
                    Log.i("{OngoingExpandedPipController}", "selfDestroyRunnable: discard..");
                    return;
                }
                ongoingCardController.isWatchSelfValidationProc = false;
                OngoingCardController.OaCardState oaCardState = ongoingCardController.oaCardState;
                if (oaCardState == OngoingCardController.OaCardState.EXPAND) {
                    Log.e("{OngoingExpandedPipController}", "selfDestroyRunnable: request fadeOut");
                    this.this$0.fadeOutCard();
                    return;
                }
                if (oaCardState != OngoingCardController.OaCardState.FADEOUT && oaCardState != OngoingCardController.OaCardState.COLLAPSE) {
                    Log.e("{OngoingExpandedPipController}", "selfDestroyRunnable: discard.. oaCardState:" + oaCardState);
                    return;
                }
                Log.e("{OngoingExpandedPipController}", "selfDestroyRunnable: remove window. mParentView.isAttachedToWindow:" + ongoingCardController.mParentView.isAttachedToWindow() + ", mParentView.parent != null:" + (this.this$0.mParentView.getParent() != null));
                OngoingCardController ongoingCardController2 = this.this$0;
                WindowManager windowManager2 = ongoingCardController2.mWindowManager;
                if (windowManager2 != null) {
                    windowManager2.removeViewImmediate(ongoingCardController2.mParentView);
                }
            }
        };
        this.mainUIHandler = new Handler(Looper.getMainLooper());
    }

    @Override // com.android.systemui.statusbar.phone.ongoingactivity.IOngoingObserver
    public final void add$1() throws Exception {
        View childAt;
        final CardStackView cardStackView = this.mCardStackView;
        cardStackView.getClass();
        Log.i("{OngoingActivityCardStackView}", "addItem()");
        OngoingCardAdapter ongoingCardAdapter = cardStackView.adapter;
        if (ongoingCardAdapter != null) {
            if (cardStackView.getChildCount() == cardStackView.stackMaxSize && (childAt = cardStackView.getChildAt(0)) != null) {
                cardStackView.removeView(childAt);
            }
            OngoingCardAdapter ongoingCardAdapter2 = cardStackView.adapter;
            if (ongoingCardAdapter2 != null) {
                int iMin = Math.min(ongoingCardAdapter2.getCount() - cardStackView.currentIndex, cardStackView.stackMaxSize);
                cardStackView.getChildCount();
                cardStackView.addItem(iMin - 1, cardStackView.getChildCount(), ongoingCardAdapter2.getView(0, null, cardStackView), true);
            }
            cardStackView.updateItem$1();
            cardStackView.requestLayout();
            if (cardStackView.onChangeListener != null) {
                AnonymousClass6.onChange(ongoingCardAdapter.getCount(), ongoingCardAdapter.getCount());
            }
        }
        View childAt2 = cardStackView.getChildAt(cardStackView.getChildCount() - 1);
        if (childAt2 != null) {
            childAt2.setScaleX(1.0f);
            childAt2.setScaleY(1.0f);
            childAt2.setX(0.0f);
            childAt2.setY(0.0f);
        }
        View childAt3 = cardStackView.getChildAt(cardStackView.getChildCount() - 1);
        if (childAt3 != null) {
            childAt3.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView.addItem.3
                public AnonymousClass3() {
                }

                @Override // java.lang.Runnable
                public final void run() {
                    CardStackView.showBottomCardShadowIfNeeded$default(CardStackView.this);
                }
            });
        }
        cardStackView.startViewStatusList = cardStackView.getStartViewStatusList();
        cardStackView.endViewStatusList = cardStackView.getEndViewStatusList();
    }

    public final void collapseAnimation() {
        Log.d("{OngoingExpandedPipController}", "collapseAnimation called. card state:{" + this.oaCardState + "}. " + Debug.getCallers(3));
        OaCardState oaCardState = this.oaCardState;
        OaCardState oaCardState2 = OaCardState.COLLAPSE;
        if (oaCardState == oaCardState2 || oaCardState == OaCardState.FADEOUT) {
            Log.i("{OngoingExpandedPipController}", "collapseAnimation discard");
            return;
        }
        Log.d("{OngoingExpandedPipController}", "watchSelfValidation start");
        this.isWatchSelfValidationProc = true;
        this.selfValidationHandler.removeCallbacks(this.selfDestroyRunnable);
        this.selfValidationHandler.postDelayed(this.selfDestroyRunnable, 4000L);
        setCardState(oaCardState2);
        RecyclerView.Adapter adapter = ((RecyclerView) this.mCapsule).mAdapter;
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        onChangeCapsuleVisibility(4, new AnonymousClass2());
        OngoingActivityDataHelper.INSTANCE.getClass();
        if (OngoingActivityDataHelper.mOngoingActivityLists.size() != 0) {
            NotificationSAUtil.sendOALog(SystemUIAnalytics.OAID_ONGOING_CLOSD_EXPAND_VIEW, OngoingActivityDataHelper.getDataByIndex(0).mNotificationEntry);
        }
    }

    public final void fadeOutCard() throws Exception {
        OaCardState oaCardState;
        Log.i("{OngoingExpandedPipController}", "fadeOutCard - card state:" + this.oaCardState + ". " + Debug.getCallers(3));
        OaCardState oaCardState2 = this.oaCardState;
        if (oaCardState2 == OaCardState.COLLAPSE || oaCardState2 == (oaCardState = OaCardState.FADEOUT)) {
            return;
        }
        setCardState(oaCardState);
        if (this.mCardStackView.isReadyCollapseAnimation()) {
            this.mCardStackView.animate().alpha(0.0f).setDuration(100L).withEndAction(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController.fadeOutCard.1
                @Override // java.lang.Runnable
                public final void run() throws Exception {
                    OngoingCardController.this.onDestroy(false);
                }
            }).start();
        } else {
            onDestroy(false);
        }
    }

    public final PointF getCardStackLocationOnScreen() throws Resources.NotFoundException {
        float dimension;
        int marginStart;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mCardStackView.getLayoutParams();
        if (this.mCardStackView.getChildCount() <= 0) {
            return new PointF(0.0f, 0.0f);
        }
        OngoingActivityLayoutUtil ongoingActivityLayoutUtil = OngoingActivityLayoutUtil.INSTANCE;
        Context context = this.mContext;
        ongoingActivityLayoutUtil.getClass();
        if ((context.getResources().getConfiguration().smallestScreenWidthDp > 600 || DeviceState.isTablet()) && this.mContext.getResources().getConfiguration().orientation == 2) {
            dimension = this.mContext.getResources().getDimension(R.dimen.ongoing_activity_card_tablet_landscape_left_margin);
            marginStart = marginLayoutParams.getMarginStart();
        } else {
            dimension = (this.mExpandedView.getWidth() - this.mCardStackView.getWidth()) / 2.0f;
            marginStart = marginLayoutParams.getMarginStart();
        }
        return new PointF(dimension - marginStart, this.mContext.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_view_top_margin));
    }

    public final void onAllowStateChanged(boolean z) {
        ArrayList arrayList = this.onStateEventListeners;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            OngoingActivityController ongoingActivityController = ((OngoingActivityController$createCardController$2) obj).this$0;
            KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0.m("onAllowStateChanged : delayedEntry size = ", ongoingActivityController.delayedEntry.size(), " : ", z, "{OngoingActivityController}");
            if (ongoingActivityController.isUpdateNotAllowed != z) {
                ongoingActivityController.isUpdateNotAllowed = z;
                OngoingActivityDataHelper.INSTANCE.getClass();
                OngoingActivityDataHelper.isUpdateNotAllowed = z;
                if (!z) {
                    Iterator it = ongoingActivityController.delayedEntry.iterator();
                    while (it.hasNext()) {
                        Pair pair = (Pair) it.next();
                        if (((Boolean) pair.getSecond()).booleanValue()) {
                            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("onAllowStateChanged update delayedEntry  = ", ((NotificationEntry) pair.getFirst()).mKey, "{OngoingActivityController}");
                            OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
                            String str = ((NotificationEntry) pair.getFirst()).mKey;
                            ongoingActivityDataHelper.getClass();
                            boolean z2 = OngoingActivityDataHelper.getOngoingActivityDataByKey(str) == null;
                            OngoingActivityController.access$updateOngoingActivityData((NotificationEntry) pair.getFirst(), ongoingActivityController);
                            if (((NotificationEntry) pair.getFirst()).mAttachState.parent != null) {
                                OngoingActivityDataHelper.onAsyncInflationFinished((NotificationEntry) pair.getFirst(), ongoingActivityController.userManager);
                            }
                            if (z2) {
                                ongoingActivityController.startMarqueeAnimation();
                                NotificationSAUtil.sendOALog(SystemUIAnalytics.OAID_ONGOING_CHIP_GENERATED, (NotificationEntry) pair.getFirst());
                            }
                        } else {
                            Log.d("{OngoingActivityController}", "onAllowStateChanged remove delayedEntry  = " + ((NotificationEntry) pair.getFirst()).mKey);
                            OngoingActivityController.access$removeOngoingActivityData((NotificationEntry) pair.getFirst(), ongoingActivityController);
                        }
                    }
                    ongoingActivityController.delayedEntry.clear();
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0051  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onChangeCapsuleVisibility(int i, AnonymousClass2 anonymousClass2) {
        int i2;
        AnonymousClass2 anonymousClass22;
        CollapsedStatusBarFragment.OngoingActivityListenerImpl ongoingActivityListenerImpl;
        ArrayList arrayList = this.onStateEventListeners;
        int size = arrayList.size();
        int i3 = 0;
        while (i3 < size) {
            Object obj = arrayList.get(i3);
            i3++;
            OngoingActivityController ongoingActivityController = ((OngoingActivityController$createCardController$2) obj).this$0;
            if (i == 0 && (ongoingActivityListenerImpl = ongoingActivityController.ongoingActivityListener) != null) {
                CollapsedStatusBarFragment.this.mNotificationIconAreaController.setAnimationsEnabled(false);
            }
            CollapsedStatusBarFragment.OngoingActivityListenerImpl ongoingActivityListenerImpl2 = ongoingActivityController.ongoingActivityListener;
            if (ongoingActivityListenerImpl2 != null) {
                RecyclerView recyclerView = ongoingActivityController.mCapsuleRecyclerView;
                recyclerView.getClass();
                ChipAnimationController chipAnimationController = CollapsedStatusBarFragment.this.mChipAnimationController;
                chipAnimationController.getClass();
                Log.d("{ChipAnimationController}", "animateNotificationIconArea visibility:" + i);
                if (recyclerView.getVisibility() == i) {
                    i2 = i;
                    anonymousClass22 = anonymousClass2;
                } else if (i == 0) {
                    recyclerView.setVisibility(0);
                    i2 = i;
                    anonymousClass22 = anonymousClass2;
                } else {
                    boolean z = i != 8;
                    i2 = i;
                    anonymousClass22 = anonymousClass2;
                    chipAnimationController.startChipTransitionAnimation(z, recyclerView, i2, z ? ChipAnimationController.CARD_TRANSITION_COLLAPSE_INTERPOLATOR : ChipAnimationController.CARD_TRANSITION_EXPAND_INTERPOLATOR, anonymousClass22);
                }
            }
            i = i2;
            anonymousClass2 = anonymousClass22;
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        this.mCardStackView.setVisibility(4);
        this.mCardStackView.setTranslationY(this.indicatorGardenPresenter.cachedGardenModel.totalHeight);
        WindowManager.LayoutParams layoutParams = this.mWindowParams;
        if (layoutParams != null) {
            Rect appBounds = this.mContext.getResources().getConfiguration().windowConfiguration.getAppBounds();
            layoutParams.width = appBounds != null ? appBounds.width() : 0;
        }
        WindowManager.LayoutParams layoutParams2 = this.mWindowParams;
        if (layoutParams2 != null) {
            Rect appBounds2 = this.mContext.getResources().getConfiguration().windowConfiguration.getAppBounds();
            layoutParams2.height = appBounds2 != null ? appBounds2.height() : 0;
        }
        BackKeyConsumerViewGroup backKeyConsumerViewGroup = this.mParentView;
        if (backKeyConsumerViewGroup != null && backKeyConsumerViewGroup.isAttachedToWindow()) {
            WindowManager windowManager = this.mWindowManager;
            if (windowManager != null) {
                windowManager.removeViewImmediate(this.mParentView);
            }
            WindowManager windowManager2 = this.mWindowManager;
            if (windowManager2 != null) {
                windowManager2.addView(this.mParentView, this.mWindowParams);
            }
            this.mParentView.forceLayout();
        }
        this.mExpandedView.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController.onConfigChanged.1
            @Override // java.lang.Runnable
            public final void run() throws Resources.NotFoundException {
                CardStackView cardStackView = OngoingCardController.this.mCardStackView;
                cardStackView.getClass();
                OngoingActivityDataHelper.INSTANCE.getClass();
                if (OngoingActivityDataHelper.mOngoingActivityLists.size() >= cardStackView.getChildCount()) {
                    int childCount = cardStackView.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        OngoingActivityDataHelper.INSTANCE.getClass();
                        OngoingActivityData dataByIndex = OngoingActivityDataHelper.getDataByIndex(i);
                        if (dataByIndex.mProgress > -1 && dataByIndex.mProgressSegments.length > 0) {
                            ImageView imageView = (ImageView) cardStackView.findViewWithTag("tintedProgress");
                            float f = dataByIndex.mProgress;
                            int i2 = dataByIndex.mProgressMax;
                            if (i2 > 0) {
                                f = (f * 100) / i2;
                            }
                            imageView.setImageBitmap(new OngoingSeekBarCreator(cardStackView.getContext(), dataByIndex.mProgressSegments, f, dataByIndex.mProgressSegmentIcon, dataByIndex.mProgressColor).makeImage(OngoingType.OA));
                        }
                    }
                }
                OngoingCardController.this.mCardStackView.updateBottomCardShadow();
                OngoingCardController ongoingCardController = OngoingCardController.this;
                PointF cardStackLocationOnScreen = ongoingCardController.getCardStackLocationOnScreen();
                View viewFindViewById = ongoingCardController.mExpandedView.findViewById(R.id.ongoing_card_background);
                viewFindViewById.getClass();
                if (ongoingCardController.mCardStackView.getLayoutDirection() == 1) {
                    viewFindViewById.setX((ongoingCardController.mCapsule.getResources().getDisplayMetrics().widthPixels - ongoingCardController.mCardStackView.getWidth()) - cardStackLocationOnScreen.x);
                } else {
                    viewFindViewById.setX(cardStackLocationOnScreen.x);
                }
                viewFindViewById.setY(cardStackLocationOnScreen.y);
                ongoingCardController.mCardStackView.setVisibility(0);
            }
        }, 16L);
    }

    public final void onDestroy(boolean z) throws Exception {
        RecyclerView.Adapter adapter;
        if (!z && (adapter = ((RecyclerView) this.mCapsule).mAdapter) != null) {
            adapter.notifyDataSetChanged();
        }
        OngoingCardAdapter ongoingCardAdapter = this.mCardStackView.adapter;
        View detachedMediaView = ongoingCardAdapter != null ? ongoingCardAdapter.getDetachedMediaView() : null;
        if (detachedMediaView == null) {
            Log.e("MediaOngoingActivity", "initPositionMediaCard cannot find media card");
        } else {
            detachedMediaView.setX(0.0f);
            detachedMediaView.setY(0.0f);
            detachedMediaView.setTranslationX(0.0f);
            detachedMediaView.setTranslationY(0.0f);
        }
        this.broadcastDispatcher.unregisterReceiver(this.broadcastReceiver);
        ((ConfigurationControllerImpl) this.configurationController).removeCallback(this);
        SemWindowManager.getInstance().unregisterFoldStateListener(this.mFoldStateListener);
        ArrayList arrayList = this.onStateEventListeners;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            OngoingActivityController$createCardController$2 ongoingActivityController$createCardController$2 = (OngoingActivityController$createCardController$2) obj;
            ongoingActivityController$createCardController$2.getClass();
            Log.d("{OngoingActivityController}", "onClosed() Card!");
            OngoingActivityController ongoingActivityController = ongoingActivityController$createCardController$2.this$0;
            int size2 = ongoingActivityController.delayedEntry.size();
            NotificationLockscreenUserManager notificationLockscreenUserManager = ongoingActivityController.userManager;
            if (size2 != 0) {
                Iterator it = ongoingActivityController.delayedEntry.iterator();
                while (it.hasNext()) {
                    Pair pair = (Pair) it.next();
                    if (((Boolean) pair.getSecond()).booleanValue()) {
                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("onClosed: update delayedEntry  = ", ((NotificationEntry) pair.getFirst()).mKey, "{OngoingActivityController}");
                        OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
                        String str = ((NotificationEntry) pair.getFirst()).mKey;
                        ongoingActivityDataHelper.getClass();
                        boolean z2 = OngoingActivityDataHelper.getOngoingActivityDataByKey(str) == null;
                        OngoingActivityController.access$updateOngoingActivityData((NotificationEntry) pair.getFirst(), ongoingActivityController);
                        if (((NotificationEntry) pair.getFirst()).mAttachState.parent != null) {
                            OngoingActivityDataHelper.onAsyncInflationFinished((NotificationEntry) pair.getFirst(), notificationLockscreenUserManager);
                        }
                        if (z2) {
                            ongoingActivityController.startMarqueeAnimation();
                            NotificationSAUtil.sendOALog(SystemUIAnalytics.OAID_ONGOING_CHIP_GENERATED, (NotificationEntry) pair.getFirst());
                        }
                    } else {
                        Log.d("{OngoingActivityController}", "onClosed: remove delayedEntry  = " + ((NotificationEntry) pair.getFirst()).mKey);
                        OngoingActivityController.access$removeOngoingActivityData((NotificationEntry) pair.getFirst(), ongoingActivityController);
                    }
                }
                ongoingActivityController.delayedEntry.clear();
            }
            OngoingActivityDataHelper.INSTANCE.getClass();
            LinkedList<OngoingActivityData> linkedList = new LinkedList();
            linkedList.addAll(OngoingActivityDataHelper.hiddenOngoingActivityDataList);
            boolean z3 = false;
            for (OngoingActivityData ongoingActivityData : linkedList) {
                OngoingActivityDataHelper.INSTANCE.getClass();
                if (!OngoingActivityDataHelper.shouldHide(notificationLockscreenUserManager, ongoingActivityData)) {
                    OngoingActivityDataHelper.hiddenOngoingActivityDataList.remove(ongoingActivityData);
                    String str2 = ongoingActivityData.mNotiID;
                    Iterator it2 = OngoingActivityDataHelper.mOngoingActivityLists.iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            if (((OngoingActivityData) it2.next()).mNotiID.equals(str2)) {
                                break;
                            }
                        } else {
                            OngoingActivityDataHelper.mOngoingActivityLists.addFirst(ongoingActivityData);
                            z3 = true;
                            break;
                        }
                    }
                }
            }
            if (z3) {
                OngoingActivityDataHelper.notifyUpdateObservers();
            }
            ongoingActivityController.updateParentViewVisibility(false);
            ongoingActivityController.startMarqueeAnimation();
            ongoingActivityController.mOngoingCardController = null;
            ongoingActivityController.isUpdateNotAllowed = false;
            OngoingActivityDataHelper.INSTANCE.getClass();
            OngoingActivityDataHelper.isUpdateNotAllowed = false;
            ongoingActivityController.killGhost();
        }
        OngoingActivityDataHelper.INSTANCE.getClass();
        OngoingActivityDataHelper.cardIsShown = false;
        onChangeCapsuleVisibility(0, null);
        EmergencyButtonController$$ExternalSyntheticOutline0.m("onDestroy() mParentView.isAttachedToWindow = ", "{OngoingExpandedPipController}", this.mParentView.isAttachedToWindow());
        BackKeyConsumerViewGroup backKeyConsumerViewGroup = this.mParentView;
        if (backKeyConsumerViewGroup == null) {
            Log.e("{OngoingExpandedPipController}", "onDestroy() mParentView is null");
        } else if (backKeyConsumerViewGroup.isAttachedToWindow() || this.mParentView.getParent() != null) {
            Log.d("{OngoingExpandedPipController}", "onDestroy() removeViewImmediate");
            WindowManager windowManager = this.mWindowManager;
            if (windowManager != null) {
                windowManager.removeViewImmediate(this.mParentView);
            }
            Log.d("{OngoingExpandedPipController}", "watchSelfValidation stop");
            this.isWatchSelfValidationProc = false;
            this.selfValidationHandler.removeCallbacks(this.selfDestroyRunnable);
        } else {
            KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("onDestroy() do not remove view. mParentView.isAttachedToWindow:", ", mParentView.parent != null?:", "{OngoingExpandedPipController}", this.mParentView.isAttachedToWindow(), this.mParentView.getParent() != null);
        }
        ((HashSet) this.statusBarWindowStateController.listeners).remove(this.statusBarWindowStateListener);
        Boolean bool = Boolean.FALSE;
        OngoingActivityDataHelper.updateMediaProgressAndMarqueeStateIfNeeded(bool, bool);
        ((ArrayList) OngoingActivityDataHelper.observers).remove(this);
        this.mProcDestroy = true;
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onOrientationChanged(int i) {
        this.isOrientationChanged = true;
        Integer num = this.mInitOrientation;
        if (num != null && num.intValue() == i) {
            return;
        }
        this.mInitOrientation = Integer.valueOf(i);
        updateCardViewLayout();
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        if ((motionEvent.getAction() == 1 || motionEvent.getAction() == 4) && this.mCardStackView.isReadyCollapseAnimation()) {
            int[] iArr = new int[2];
            this.mCardStackView.getLocationOnScreen(iArr);
            WindowManager.LayoutParams layoutParams = this.mWindowParams;
            layoutParams.getClass();
            layoutParams.height = this.mCardStackView.getHeight() + iArr[1];
            WindowManager windowManager = this.mWindowManager;
            if (windowManager != null) {
                windowManager.updateViewLayout(this.mParentView, this.mWindowParams);
            }
            collapseAnimation();
        }
        return true;
    }

    @Override // com.android.systemui.statusbar.phone.ongoingactivity.IOngoingObserver
    public final void remove(int i) throws Exception {
        OngoingActivityDataHelper.INSTANCE.getClass();
        int size = OngoingActivityDataHelper.mOngoingActivityLists.size();
        SuggestionsAdapter$$ExternalSyntheticOutline0.m(size, i, "remove view datasize:", ", position:", "{OngoingExpandedPipController}");
        if (size == 0) {
            onDestroy(false);
        } else {
            this.mCardStackView.removeItem(i);
        }
    }

    public final void runCardRemoveAnimation(Function0 function0) throws Exception {
        View view;
        onAllowStateChanged(true);
        final CardStackView cardStackView = this.mCardStackView;
        final OngoingCardController$$ExternalSyntheticLambda4 ongoingCardController$$ExternalSyntheticLambda4 = new OngoingCardController$$ExternalSyntheticLambda4(function0, this);
        cardStackView.getClass();
        Log.i("{OngoingActivityCardStackView}", "removeTopCardAnimation()");
        OngoingActivityDataHelper.INSTANCE.getClass();
        if (OngoingActivityDataHelper.mOngoingActivityLists.size() != 0) {
            NotificationSAUtil.sendOALog(SystemUIAnalytics.OAID_ONGOING_SWIPE_TO_SEE_OTHER_ACTIVITY, OngoingActivityDataHelper.getDataByIndex(0).mNotificationEntry);
        }
        if (cardStackView.gutsDisplay) {
            cardStackView.disableTopCardGuts(true);
        }
        cardStackView.isRunningRemoveTopCardAnimation = true;
        cardStackView.isAnimating = true;
        CustomAnimationSet customAnimationSet = new CustomAnimationSet(new CardStackView$$ExternalSyntheticLambda0(cardStackView, 3));
        customAnimationSet.additionalEndListener = new Function0() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                CardStackView.Companion companion = CardStackView.Companion;
                ongoingCardController$$ExternalSyntheticLambda4.invoke();
                CardStackView cardStackView2 = cardStackView;
                int topViewIndex = cardStackView2.getTopViewIndex();
                if (topViewIndex >= 0) {
                    int i = 0;
                    while (true) {
                        CardStackView.ViewStatus viewStatus = (CardStackView.ViewStatus) cardStackView2.startViewStatusList.get(i);
                        View childAt = cardStackView2.getChildAt(i);
                        childAt.setScaleX(viewStatus.scaleX);
                        childAt.setScaleY(viewStatus.scaleY);
                        if (i == topViewIndex) {
                            break;
                        }
                        i++;
                    }
                }
                return Unit.INSTANCE;
            }
        };
        final View childAt = cardStackView.getChildAt(cardStackView.getTopViewIndex());
        if (childAt == null) {
            Log.e("{OngoingActivityCardStackView}", "removeAnimationSet get topView fail");
            ongoingCardController$$ExternalSyntheticLambda4.invoke();
            return;
        }
        Log.i("{OngoingActivityCardStackView}", "removeAnimationSet run()");
        OngoingCardAdapter ongoingCardAdapter = cardStackView.adapter;
        ongoingCardAdapter.getClass();
        OngoingCardController$$ExternalSyntheticLambda0 ongoingCardController$$ExternalSyntheticLambda0 = ongoingCardAdapter.getMediaCardView;
        if (ongoingCardController$$ExternalSyntheticLambda0 == null) {
            Log.e("MediaOngoingActivity", "getMediaCard. lambda is not initialized");
            view = null;
        } else {
            view = (View) ongoingCardController$$ExternalSyntheticLambda0.mo781invoke(Unit.INSTANCE);
        }
        if (Intrinsics.areEqual(view, childAt)) {
            CardStackView.getBaseColorView(childAt).setBackground(cardStackView.getContext().getResources().getDrawable(R.drawable.sec_ongoing_card_bg));
        }
        DynamicAnimation.AnonymousClass4 anonymousClass4 = DynamicAnimation.SCALE_X;
        customAnimationSet.add(childAt, anonymousClass4, 0.7f, 0.875f, 400.0f, (96 & 32) != 0 ? 0L : 250L, null);
        DynamicAnimation.AnonymousClass5 anonymousClass5 = DynamicAnimation.SCALE_Y;
        customAnimationSet.add(childAt, anonymousClass5, 0.7f, 0.875f, 400.0f, (96 & 32) != 0 ? 0L : 250L, null);
        final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        valueAnimatorOfFloat.setDuration(250L);
        PathInterpolator pathInterpolator = CardStackView.cardRemoveAlphaInterpolator;
        valueAnimatorOfFloat.setInterpolator(pathInterpolator);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$removeTopCardAnimation$3$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fFloatValue = ((Float) valueAnimatorOfFloat.getAnimatedValue()).floatValue();
                CardStackView cardStackView2 = cardStackView;
                View view2 = childAt;
                CardStackView.Companion companion = CardStackView.Companion;
                cardStackView2.getClass();
                CardStackView.getBaseColorView(view2).setAlpha(fFloatValue);
            }
        });
        customAnimationSet.add(50L, valueAnimatorOfFloat);
        View childAt2 = cardStackView.getChildAt(cardStackView.getTopViewIndex());
        ViewGroup viewGroup = childAt2 != null ? (ViewGroup) childAt2.findViewWithTag("BottomCardShadowView") : null;
        if (viewGroup != null) {
            viewGroup.setVisibility(4);
        }
        if (cardStackView.getTopViewIndex() > 0) {
            final View childAt3 = cardStackView.getChildAt(cardStackView.getTopViewIndex() - 1);
            CardStackView.ViewStatus viewStatus = (CardStackView.ViewStatus) cardStackView.startViewStatusList.get(cardStackView.getTopViewIndex());
            childAt3.setScaleX(0.92f);
            childAt3.setScaleY(0.92f);
            customAnimationSet.add(childAt3, DynamicAnimation.X, viewStatus.point.x, 0.8131728f, 200.0f, (96 & 32) != 0 ? 0L : 250L, null);
            customAnimationSet.add(childAt3, DynamicAnimation.Y, viewStatus.point.y, 0.8131728f, 200.0f, (96 & 32) != 0 ? 0L : 250L, null);
            customAnimationSet.add(childAt3, anonymousClass4, viewStatus.scaleX, 0.8131728f, 200.0f, (96 & 32) != 0 ? 0L : 250L, null);
            customAnimationSet.add(childAt3, anonymousClass5, viewStatus.scaleY, 0.8131728f, 200.0f, (96 & 32) != 0 ? 0L : 250L, null);
            final ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimatorOfFloat2.setDuration(300L);
            valueAnimatorOfFloat2.setInterpolator(pathInterpolator);
            valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$removeTopCardAnimation$3$2$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float fFloatValue = ((Float) valueAnimatorOfFloat2.getAnimatedValue()).floatValue();
                    CardStackView cardStackView2 = cardStackView;
                    View view2 = childAt3;
                    view2.getClass();
                    CardStackView.Companion companion = CardStackView.Companion;
                    cardStackView2.getClass();
                    CardStackView.getBaseColorView(view2).setAlpha(fFloatValue);
                }
            });
            customAnimationSet.add(0L, valueAnimatorOfFloat2);
        }
        customAnimationSet.start();
    }

    public final void setCardState(OaCardState oaCardState) {
        Log.d("{OngoingExpandedPipController}", " setCardState. " + this.oaCardState + " --> " + oaCardState);
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(" DEBUG - ", Debug.getCallers(3), "{OngoingExpandedPipController}");
        this.oaCardState = oaCardState;
    }

    public final void startAnimation(View view, float f, float f2, float f3, float f4, PathInterpolator pathInterpolator, long j, final Function0 function0) {
        Log.d("{OngoingExpandedPipController}", "startAnimation for card expand/collapse view translation");
        view.setTranslationX(f);
        view.setTranslationY(f2);
        view.animate().x(f3).y(f4).setInterpolator(pathInterpolator).setDuration(j).withEndAction(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardControllerKt$sam$java_lang_Runnable$0
            @Override // java.lang.Runnable
            public final /* synthetic */ void run() {
                function0.invoke();
            }
        }).start();
        onAllowStateChanged(true);
    }

    @Override // com.android.systemui.statusbar.phone.ongoingactivity.IOngoingObserver
    public final void update(String str) throws Exception {
        String str2;
        CardStackView cardStackView = this.mCardStackView;
        cardStackView.getClass();
        Log.i("{OngoingActivityCardStackView}", "updateItem() " + str);
        OngoingCardAdapter ongoingCardAdapter = cardStackView.adapter;
        if (ongoingCardAdapter != null) {
            OngoingActivityDataHelper.INSTANCE.getClass();
            int iMin = Math.min(OngoingActivityDataHelper.mOngoingActivityLists.size(), cardStackView.stackMaxSize);
            int childCount = cardStackView.getChildCount();
            int i = 0;
            while (true) {
                if (i >= childCount) {
                    break;
                }
                int i2 = (iMin - i) - 1;
                if (i2 < 0) {
                    break;
                }
                OngoingActivityDataHelper.INSTANCE.getClass();
                OngoingActivityData dataByIndex = OngoingActivityDataHelper.getDataByIndex(i2);
                if (dataByIndex == null || (str2 = dataByIndex.mNotiID) == null || !str2.equals(str)) {
                    i++;
                } else if (dataByIndex.mIsMediaOngoingData) {
                    cardStackView.removeView(cardStackView.getChildAt(i));
                    cardStackView.addView(ongoingCardAdapter.getDetachedMediaView(), i);
                    ongoingCardAdapter.inflateDummyChipView(dataByIndex);
                } else {
                    ongoingCardAdapter.bindView(cardStackView.getChildAt(i), i2);
                }
            }
            cardStackView.updateItemBg();
            if (cardStackView.onChangeListener != null) {
                AnonymousClass6.onChange(ongoingCardAdapter.getCount(), ongoingCardAdapter.getCount());
            }
        }
        OngoingActivityDataHelper.INSTANCE.getClass();
        OngoingActivityDataHelper.updateTopIndex();
        cardStackView.setCardClickable();
    }

    @Override // com.android.systemui.statusbar.phone.ongoingactivity.IOngoingObserver
    public final void update$1$1() throws Exception {
        this.mCardStackView.updateItem$1();
        this.mCardStackView.requestLayout();
    }

    public final void updateCardViewLayout() {
        View viewFindViewById = this.mExpandedView.findViewById(R.id.ongoing_card_background);
        ViewGroup.LayoutParams layoutParams = viewFindViewById != null ? viewFindViewById.getLayoutParams() : null;
        if (layoutParams != null) {
            OngoingActivityLayoutUtil ongoingActivityLayoutUtil = OngoingActivityLayoutUtil.INSTANCE;
            Context context = this.mContext;
            ongoingActivityLayoutUtil.getClass();
            layoutParams.width = OngoingActivityLayoutUtil.getOngoingCardWidth(context);
        }
        if (viewFindViewById != null) {
            viewFindViewById.setLayoutParams(layoutParams);
        }
        OngoingActivityDataHelper.INSTANCE.getClass();
        if (OngoingActivityDataHelper.mOngoingActivityLists.size() != 0) {
            OngoingActivityLayoutUtil ongoingActivityLayoutUtil2 = OngoingActivityLayoutUtil.INSTANCE;
            Context context2 = this.mContext;
            viewFindViewById.getClass();
            OngoingActivityData dataByIndex = OngoingActivityDataHelper.getDataByIndex(0);
            OngoingType ongoingType = OngoingType.OA;
            ongoingActivityLayoutUtil2.getClass();
            OngoingActivityLayoutUtil.updateNowbarSports(context2, viewFindViewById, dataByIndex, ongoingType);
        }
    }
}
