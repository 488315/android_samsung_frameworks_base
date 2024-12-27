package com.android.systemui.statusbar.phone.ongoingactivity;

import android.animation.ArgbEvaluator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Handler;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.WindowManager;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.window.OnBackInvokedCallback;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.recyclerview.widget.RecyclerView;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.IndicatorGardenPresenter;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment;
import com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView;
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
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$IntRef;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class OngoingCardController implements View.OnTouchListener, IOngoingObserver, ConfigurationController.ConfigurationListener {
    public final ActivityStarter activityStarter;
    public final BroadcastDispatcher broadcastDispatcher;
    public final AnonymousClass5 broadcastReceiver;
    public final ConfigurationController configurationController;
    public final IndicatorGardenPresenter indicatorGardenPresenter;
    public boolean isOrientationChanged;
    public final View mCapsule;
    public final CardStackView mCardStackView;
    public final Context mContext;
    public final ViewGroup mExpandedView;
    public Integer mInitOrientation;
    public final BackKeyConsumerViewGroup mParentView;
    public final OnStateEventListener mStateListener;
    public final WindowManager mWindowManager;
    public final WindowManager.LayoutParams mWindowParams;
    public final NotifCollection notifCollection;
    public final AnonymousClass4 onBackInvokedCallback;
    public int statusBarVisibility;
    public final StatusBarWindowStateController statusBarWindowStateController;
    public final OngoingCardController$statusBarWindowStateListener$1 statusBarWindowStateListener;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$3, reason: invalid class name */
    public final class AnonymousClass3 {
        public AnonymousClass3() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface OnStateEventListener {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v0, types: [com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$statusBarWindowStateListener$1, com.android.systemui.statusbar.window.StatusBarWindowStateListener] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$4] */
    /* JADX WARN: Type inference failed for: r6v11, types: [android.content.BroadcastReceiver, com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$5] */
    public OngoingCardController(Context context, ActivityStarter activityStarter, View view, OnStateEventListener onStateEventListener, IndicatorGardenPresenter indicatorGardenPresenter, IndicatorScaleGardener indicatorScaleGardener, ConfigurationController configurationController, BroadcastDispatcher broadcastDispatcher, NotifCollection notifCollection, NotificationRemoteInputManager notificationRemoteInputManager, StatusBarWindowStateController statusBarWindowStateController) {
        this.mContext = context;
        this.activityStarter = activityStarter;
        this.mCapsule = view;
        this.mStateListener = onStateEventListener;
        this.indicatorGardenPresenter = indicatorGardenPresenter;
        this.configurationController = configurationController;
        this.broadcastDispatcher = broadcastDispatcher;
        this.notifCollection = notifCollection;
        this.statusBarWindowStateController = statusBarWindowStateController;
        ?? r13 = new StatusBarWindowStateListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$statusBarWindowStateListener$1
            @Override // com.android.systemui.statusbar.window.StatusBarWindowStateListener
            public final void onStatusBarWindowStateChanged(int i) {
                OngoingCardController.this.statusBarVisibility = i;
            }
        };
        this.statusBarWindowStateListener = r13;
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
        View findViewById = viewGroup.findViewById(R.id.card_stack_view);
        Intrinsics.checkNotNull(findViewById);
        CardStackView cardStackView = (CardStackView) findViewById;
        this.mCardStackView = cardStackView;
        OngoingCardAdapter ongoingCardAdapter = new OngoingCardAdapter(context, indicatorScaleGardener, notificationRemoteInputManager);
        OngoingCardAdapter ongoingCardAdapter2 = cardStackView.adapter;
        if (ongoingCardAdapter2 != null) {
            ongoingCardAdapter2.unregisterDataSetObserver(cardStackView.dataObserver);
        }
        OngoingCardAdapter ongoingCardAdapter3 = cardStackView.adapter;
        if (ongoingCardAdapter3 != null) {
            ongoingCardAdapter3.registerDataSetObserver(cardStackView.dataObserver);
        }
        cardStackView.adapter = ongoingCardAdapter;
        cardStackView.dismiss = new Function1() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController.1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                String str = (String) obj;
                Log.i("{OngoingExpandedPipController}", "Swipe L/R dismiss. sbnId= " + str + "}");
                OngoingCardController.this.notifCollection.dismissOngoingActivityNotification(str);
                return Unit.INSTANCE;
            }
        };
        cardStackView.collapseBackCall = new Function1() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController.2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                OngoingCardController.this.collapseAnimation();
                return Unit.INSTANCE;
            }
        };
        cardStackView.onChangeListener = new AnonymousClass3();
        this.mInitOrientation = Integer.valueOf(context.getResources().getConfiguration().orientation);
        updateCardViewLayout();
        OngoingActivityDataHelper.INSTANCE.getClass();
        ((ArrayList) OngoingActivityDataHelper.observers).add(this);
        windowManager.addView(backKeyConsumerViewGroup, layoutParams);
        this.onBackInvokedCallback = new OnBackInvokedCallback() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController.4
            @Override // android.window.OnBackInvokedCallback
            public final void onBackInvoked() {
                if (OngoingCardController.this.mCardStackView.isReadyCollapseAnimation()) {
                    OngoingCardController.this.collapseAnimation();
                }
            }
        };
        ?? r6 = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController.5
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                OngoingCardController ongoingCardController = OngoingCardController.this;
                CardStackView cardStackView2 = ongoingCardController.mCardStackView;
                if ((!cardStackView2.isRunningExpandAnimation) && (cardStackView2.isRunningCollapseAnimation ^ true)) {
                    ongoingCardController.collapseAnimation();
                }
            }
        };
        this.broadcastReceiver = r6;
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, r6, KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("com.android.systemui.edgelighting.start", PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS), null, null, 0, null, 60);
        ((ConfigurationControllerImpl) configurationController).addCallback(this);
        SemWindowManager.getInstance().registerFoldStateListener(new OngoingCardController$getFoldStateListener$1(this), (Handler) Dependency.sDependency.getDependencyInner(Dependency.MAIN_HANDLER));
        cardStackView.setVisibility(4);
        int width = view.getWidth();
        int height = view.getHeight();
        Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$expandAnimation$1$1
            @Override // java.lang.Runnable
            public final void run() {
                ((OngoingActivityController$createCardController$1) OngoingCardController.this.mStateListener).getClass();
                OngoingCardController.this.mCapsule.setVisibility(8);
                OngoingCardController.this.mCardStackView.setVisibility(0);
                OngoingCardController ongoingCardController = OngoingCardController.this;
                View findViewById2 = ongoingCardController.mExpandedView.findViewById(R.id.ongoing_card_background);
                Intrinsics.checkNotNull(findViewById2);
                final OngoingCardController ongoingCardController2 = OngoingCardController.this;
                Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$expandAnimation$1$1.1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        ((OngoingActivityController$createCardController$1) OngoingCardController.this.mStateListener).onAllowStateChanged(false);
                        return Unit.INSTANCE;
                    }
                };
                PointF capsuleLocationOnScreen = ongoingCardController.getCapsuleLocationOnScreen();
                PointF cardStackLocationOnScreen = ongoingCardController.getCardStackLocationOnScreen();
                if (ongoingCardController.mCardStackView.getLayoutDirection() == 1) {
                    capsuleLocationOnScreen.x = (capsuleLocationOnScreen.x - ongoingCardController.mCapsule.getResources().getDisplayMetrics().widthPixels) + ongoingCardController.mCapsule.getWidth();
                }
                float f = capsuleLocationOnScreen.x;
                float f2 = capsuleLocationOnScreen.y;
                float f3 = cardStackLocationOnScreen.x;
                float f4 = cardStackLocationOnScreen.y + ongoingCardController.indicatorGardenPresenter.cachedGardenModel.totalHeight;
                CardStackView.Companion.getClass();
                ongoingCardController.startAnimation(findViewById2, f, f2, f3, f4, CardStackView.expandTranslationInterpolator, 300L, function0);
                CollapsedStatusBarFragment.OngoingActivityListenerImpl ongoingActivityListenerImpl = ((OngoingActivityController$createCardController$1) ongoingCardController.mStateListener).this$0.ongoingActivityListener;
                if (ongoingActivityListenerImpl != null) {
                    ongoingActivityListenerImpl.onNudgeClockRequired();
                }
            }
        };
        if (cardStackView.getChildCount() == 0) {
            cardStackView.decorView = viewGroup;
            cardStackView.pendingWidth = width;
            cardStackView.pendingHeight = height;
            cardStackView.pendingOnStartListener = runnable;
            cardStackView.pendingAnimation = true;
        }
        OngoingActivityDataHelper.cardIsShown = true;
        statusBarWindowStateController.addListener(r13);
    }

    @Override // com.android.systemui.statusbar.phone.ongoingactivity.IOngoingObserver
    public final void add$2() {
        CardStackView cardStackView = this.mCardStackView;
        cardStackView.getClass();
        Log.i("{OngoingActivityCardStackView}", "addItem()");
        cardStackView.rebuildAllItems();
    }

    public final void collapseAnimation() {
        this.mCapsule.setVisibility(4);
        RecyclerView.Adapter adapter = ((RecyclerView) this.mCapsule).mAdapter;
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        new Handler().post(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$collapseAnimation$2
            @Override // java.lang.Runnable
            public final void run() {
                final int i;
                Scene scene;
                final OngoingCardController ongoingCardController = OngoingCardController.this;
                final CardStackView cardStackView = ongoingCardController.mCardStackView;
                final Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$collapseAnimation$2.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((OngoingActivityController$createCardController$1) OngoingCardController.this.mStateListener).onClosed();
                        OngoingCardController ongoingCardController2 = OngoingCardController.this;
                        View findViewById = ongoingCardController2.mExpandedView.findViewById(R.id.ongoing_card_background);
                        Intrinsics.checkNotNull(findViewById);
                        final OngoingCardController ongoingCardController3 = OngoingCardController.this;
                        Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController.collapseAnimation.2.1.1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                int childCount = OngoingCardController.this.mCardStackView.getChildCount();
                                for (int i2 = 0; i2 < childCount; i2++) {
                                    View childAt = OngoingCardController.this.mCardStackView.getChildAt(i2);
                                    View findViewById2 = childAt.findViewById(R.id.stack_pip_layout);
                                    if (findViewById2 != null) {
                                        findViewById2.setBackground(null);
                                    }
                                    if (OngoingCardController.this.mCardStackView.getChildCount() - 1 == i2) {
                                        View findViewById3 = childAt.findViewById(R.id.pip_dummy_chip_layout);
                                        Intrinsics.checkNotNull(findViewById3);
                                        ViewPropertyAnimator duration = findViewById3.animate().alpha(0.0f).setDuration(100L);
                                        final OngoingCardController ongoingCardController4 = OngoingCardController.this;
                                        duration.withEndAction(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController.collapseAnimation.2.1.1.1
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                OngoingCardController.this.onDestroy();
                                            }
                                        }).start();
                                    } else {
                                        childAt.setVisibility(8);
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        PointF cardStackLocationOnScreen = ongoingCardController2.getCardStackLocationOnScreen();
                        PointF capsuleLocationOnScreen = ongoingCardController2.getCapsuleLocationOnScreen();
                        if (ongoingCardController2.statusBarVisibility != 0) {
                            capsuleLocationOnScreen.y -= ongoingCardController2.indicatorGardenPresenter.cachedGardenModel.totalHeight;
                        }
                        if (ongoingCardController2.isOrientationChanged) {
                            ongoingCardController2.isOrientationChanged = false;
                            ongoingCardController2.mCardStackView.setTranslationY(0.0f);
                        }
                        if (ongoingCardController2.mCardStackView.getLayoutDirection() == 1) {
                            cardStackLocationOnScreen.x = -cardStackLocationOnScreen.x;
                            capsuleLocationOnScreen.x = (capsuleLocationOnScreen.x - ongoingCardController2.mContext.getResources().getDisplayMetrics().widthPixels) - (cardStackLocationOnScreen.x * 2);
                        }
                        float f = cardStackLocationOnScreen.x;
                        float f2 = ongoingCardController2.indicatorGardenPresenter.cachedGardenModel.totalHeight + cardStackLocationOnScreen.y;
                        float f3 = capsuleLocationOnScreen.x;
                        float f4 = capsuleLocationOnScreen.y;
                        CardStackView.Companion.getClass();
                        ongoingCardController2.startAnimation(findViewById, f, f2, f3, f4, CardStackView.collapseTranslationInterpolator, 500L, function0);
                        CollapsedStatusBarFragment.OngoingActivityListenerImpl ongoingActivityListenerImpl = ((OngoingActivityController$createCardController$1) ongoingCardController2.mStateListener).this$0.ongoingActivityListener;
                        if (ongoingActivityListenerImpl != null) {
                            ongoingActivityListenerImpl.onNudgeClockRequired();
                        }
                    }
                };
                final OngoingCardController ongoingCardController2 = OngoingCardController.this;
                final Runnable runnable2 = new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$collapseAnimation$2.2
                    @Override // java.lang.Runnable
                    public final void run() {
                        OngoingCardController.this.mExpandedView.setOnTouchListener(null);
                        OngoingCardController.this.mExpandedView.setOnKeyListener(null);
                        CollapsedStatusBarFragment.OngoingActivityListenerImpl ongoingActivityListenerImpl = ((OngoingActivityController$createCardController$1) OngoingCardController.this.mStateListener).this$0.ongoingActivityListener;
                        if (ongoingActivityListenerImpl != null) {
                            CollapsedStatusBarFragment.this.mNotificationIconAreaController.setAnimationsEnabled(false);
                        }
                        ((RecyclerView) OngoingCardController.this.mCapsule).setVisibility(0);
                        OngoingCardController.this.mCardStackView.setVisibility(4);
                        ((OngoingActivityController$createCardController$1) OngoingCardController.this.mStateListener).onAllowStateChanged(false);
                    }
                };
                int width = ((RecyclerView) OngoingCardController.this.mCapsule).getWidth();
                int height = ((RecyclerView) OngoingCardController.this.mCapsule).getHeight();
                if (cardStackView.gutsDisplay) {
                    cardStackView.disableTopCardGuts(true);
                }
                cardStackView.pendingWidth = width;
                cardStackView.pendingHeight = height;
                final TransitionSet transitionSet = new TransitionSet();
                transitionSet.addTransition(new ChangeBounds());
                int i2 = 0;
                transitionSet.setOrdering(0);
                long j = 500;
                transitionSet.setDuration(500L);
                transitionSet.setInterpolator((TimeInterpolator) CardStackView.collapseRootInterpolator);
                transitionSet.addListener((Transition.TransitionListener) new TransitionListenerAdapter() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$collapseAnimation$1
                    @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                    public final void onTransitionEnd(Transition transition) {
                        runnable2.run();
                        cardStackView.isRunningCollapseAnimation = false;
                    }

                    @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                    public final void onTransitionStart(Transition transition) {
                        runnable.run();
                        cardStackView.isRunningCollapseAnimation = true;
                    }
                });
                cardStackView.sceneList.clear();
                int childCount = cardStackView.getChildCount();
                for (int i3 = 0; i3 < childCount; i3++) {
                    ViewGroup viewGroup = (ViewGroup) cardStackView.getChildAt(i3);
                    cardStackView.sceneList.add(new Pair(new Scene(viewGroup, viewGroup.findViewById(R.id.stack_pip_layout)), new Scene(viewGroup, viewGroup.findViewById(R.id.stack_pip_layout))));
                }
                Iterator it = cardStackView.sceneList.iterator();
                int i4 = 0;
                while (it.hasNext()) {
                    Object next = it.next();
                    int i5 = i4 + 1;
                    if (i4 < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                        throw null;
                    }
                    Scene scene2 = (Scene) ((Pair) next).getFirst();
                    cardStackView.getCardBg();
                    int topViewIndex = cardStackView.getTopViewIndex() - i4;
                    OngoingActivityDataHelper.INSTANCE.getClass();
                    int size = OngoingActivityDataHelper.mOngoingActivityLists.size();
                    if (size == 0) {
                        Log.i("{OngoingActivityCardStackView}", "getChipBg : ongoingActivity is null ##");
                        i = i2;
                    } else {
                        if (topViewIndex < 0 || topViewIndex >= size) {
                            topViewIndex = i2;
                        }
                        i = OngoingActivityDataHelper.getDataByIndex(topViewIndex).mChipBackground | (-16777216);
                    }
                    if (i4 != cardStackView.getTopViewIndex()) {
                        scene = scene2;
                    } else {
                        final View childAt = cardStackView.getChildAt(i4);
                        final int cardBg = cardStackView.getCardBg();
                        Pair pair = new Pair(Long.valueOf(j), CardStackView.collapseRootInterpolator);
                        long longValue = ((Number) pair.component1()).longValue();
                        PathInterpolator pathInterpolator = (PathInterpolator) pair.component2();
                        Intrinsics.checkNotNull(childAt);
                        cardStackView.changeCardBg(childAt, cardBg);
                        final ValueAnimator ofInt = ValueAnimator.ofInt(i2, 1);
                        ofInt.setDuration(longValue);
                        ofInt.setInterpolator(pathInterpolator);
                        final ArgbEvaluator argbEvaluator = new ArgbEvaluator();
                        scene = scene2;
                        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$colorTransition$1$1
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                int intValue = ((Integer) argbEvaluator.evaluate(ofInt.getAnimatedFraction(), Integer.valueOf(cardBg), Integer.valueOf(i))).intValue();
                                CardStackView cardStackView2 = cardStackView;
                                View view = childAt;
                                CardStackView.Companion companion = CardStackView.Companion;
                                cardStackView2.changeCardBg(view, intValue);
                            }
                        });
                        ofInt.start();
                    }
                    final Ref$IntRef ref$IntRef = new Ref$IntRef();
                    ref$IntRef.element = cardStackView.pendingWidth;
                    if (cardStackView.getChildCount() > 1) {
                        ref$IntRef.element = cardStackView.pendingWidth - cardStackView.getContext().getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_layer_offset);
                    }
                    View findViewById = scene.getSceneRoot().findViewById(R.id.pip_dummy_chip_layout);
                    Intrinsics.checkNotNull(findViewById);
                    CardStackView.changeLayoutSize(findViewById, ref$IntRef.element, cardStackView.pendingHeight);
                    final Scene scene3 = scene;
                    final int i6 = i4;
                    Scene scene4 = scene;
                    scene4.setEnterAction(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$collapseAnimation$2$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ViewPropertyAnimator animate;
                            ViewPropertyAnimator translationY;
                            ViewPropertyAnimator scaleX;
                            ViewPropertyAnimator scaleY;
                            ViewPropertyAnimator alpha;
                            ViewPropertyAnimator duration;
                            ViewPropertyAnimator interpolator;
                            ViewGroup viewGroup2 = (ViewGroup) scene3.getSceneRoot().findViewById(R.id.stack_expand_contents);
                            if (viewGroup2 != null) {
                                viewGroup2.setVisibility(4);
                            }
                            ViewGroup viewGroup3 = (ViewGroup) scene3.getSceneRoot().findViewById(R.id.stack_pip_layout);
                            if (viewGroup3 != null) {
                                int i7 = i6;
                                CardStackView cardStackView2 = cardStackView;
                                TransitionSet transitionSet2 = transitionSet;
                                Ref$IntRef ref$IntRef2 = ref$IntRef;
                                CardStackView.Companion companion = CardStackView.Companion;
                                if (i7 != cardStackView2.getTopViewIndex() && (animate = viewGroup3.animate()) != null && (translationY = animate.translationY(0.0f)) != null && (scaleX = translationY.scaleX(1.0f)) != null && (scaleY = scaleX.scaleY(1.0f)) != null && (alpha = scaleY.alpha(0.0f)) != null && (duration = alpha.setDuration(300L)) != null && (interpolator = duration.setInterpolator(transitionSet2.getInterpolator())) != null) {
                                    interpolator.start();
                                }
                                CardStackView.changeLayoutSize(viewGroup3, ref$IntRef2.element, cardStackView2.pendingHeight);
                            }
                            View findViewById2 = scene3.getSceneRoot().findViewById(R.id.pip_dummy_chip_layout);
                            Intrinsics.checkNotNull(findViewById2);
                            CardStackView cardStackView3 = cardStackView;
                            findViewById2.setVisibility(0);
                            SpringAnimation springAnimation = new SpringAnimation(findViewById2, DynamicAnimation.SCALE_X, 1.0f);
                            springAnimation.mSpring.setStiffness(200.0f);
                            springAnimation.mSpring.setDampingRatio(0.8131728f);
                            springAnimation.start();
                            SpringAnimation springAnimation2 = new SpringAnimation(findViewById2, DynamicAnimation.SCALE_Y, 1.0f);
                            springAnimation2.mSpring.setStiffness(200.0f);
                            springAnimation2.mSpring.setDampingRatio(0.8131728f);
                            springAnimation2.start();
                            CardStackView.Companion companion2 = CardStackView.Companion;
                            cardStackView3.getClass();
                            CardStackView.changeLayoutMargin(findViewById2, 0);
                            View findViewById3 = scene3.getSceneRoot().findViewById(R.id.dummy_capsule_item_top_layout);
                            Intrinsics.checkNotNull(findViewById3);
                            View findViewById4 = scene3.getSceneRoot().findViewById(R.id.dummy_capsule_item_app_icon);
                            Intrinsics.checkNotNull(findViewById4);
                            View findViewById5 = scene3.getSceneRoot().findViewById(R.id.dummy_capsule_item_noti_expanded_info);
                            Intrinsics.checkNotNull(findViewById5);
                            View findViewById6 = scene3.getSceneRoot().findViewById(R.id.dummy_capsule_remote_container);
                            Intrinsics.checkNotNull(findViewById6);
                            if (ref$IntRef.element < cardStackView.getContext().getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_chip_min_width)) {
                                int dimensionPixelSize = cardStackView.getContext().getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_padding_side);
                                findViewById3.setPadding(dimensionPixelSize, 0, dimensionPixelSize, 0);
                                FrameLayout frameLayout = (FrameLayout) findViewById5;
                                frameLayout.removeAllViews();
                                frameLayout.setVisibility(8);
                            } else {
                                ViewPropertyAnimator alpha2 = findViewById5.animate().alpha(1.0f);
                                CardStackView.Companion.getClass();
                                PathInterpolator pathInterpolator2 = CardStackView.alphaInterpolator;
                                alpha2.setInterpolator(pathInterpolator2).setDuration(500L).start();
                                findViewById6.setAlpha(0.0f);
                                findViewById6.animate().alpha(1.0f).setInterpolator(pathInterpolator2).setDuration(500L).start();
                            }
                            findViewById3.setVisibility(0);
                            findViewById3.setAlpha(0.0f);
                            ViewPropertyAnimator alpha3 = findViewById3.animate().alpha(1.0f);
                            CardStackView.Companion.getClass();
                            PathInterpolator pathInterpolator3 = CardStackView.alphaInterpolator;
                            alpha3.setInterpolator(pathInterpolator3).setDuration(500L).start();
                            findViewById4.setAlpha(0.0f);
                            findViewById4.animate().alpha(1.0f).setInterpolator(pathInterpolator3).setDuration(500L).start();
                        }
                    });
                    cardStackView.isRunningCollapseAnimation = true;
                    TransitionManager.go(scene4, transitionSet);
                    i4 = i5;
                    i2 = 0;
                    j = 500;
                }
            }
        });
    }

    public final void fadeOutCard() {
        if (this.mCardStackView.isReadyCollapseAnimation()) {
            this.mCardStackView.animate().alpha(0.0f).setDuration(100L).withEndAction(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$fadeOutCard$1
                @Override // java.lang.Runnable
                public final void run() {
                    OngoingCardController.this.onDestroy();
                }
            }).start();
        } else {
            onDestroy();
        }
    }

    public final PointF getCapsuleLocationOnScreen() {
        this.mCapsule.getLocationOnScreen(new int[2]);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mCardStackView.getLayoutParams();
        return new PointF(r0[0] - marginLayoutParams.getMarginStart(), r0[1] - marginLayoutParams.topMargin);
    }

    public final PointF getCardStackLocationOnScreen() {
        return this.mCardStackView.getChildCount() > 0 ? new PointF(((this.mExpandedView.getWidth() - this.mCardStackView.getWidth()) / 2.0f) - ((ViewGroup.MarginLayoutParams) this.mCardStackView.getLayoutParams()).getMarginStart(), this.mContext.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_view_top_margin)) : new PointF(0.0f, 0.0f);
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
        this.mExpandedView.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$onConfigChanged$1
            @Override // java.lang.Runnable
            public final void run() {
                OngoingCardController.this.mCardStackView.updateBottomCardShadow();
                OngoingCardController ongoingCardController = OngoingCardController.this;
                PointF cardStackLocationOnScreen = ongoingCardController.getCardStackLocationOnScreen();
                View findViewById = ongoingCardController.mExpandedView.findViewById(R.id.ongoing_card_background);
                Intrinsics.checkNotNull(findViewById);
                findViewById.setX(cardStackLocationOnScreen.x);
                findViewById.setY(cardStackLocationOnScreen.y);
                ongoingCardController.mCardStackView.setVisibility(0);
            }
        }, 16L);
    }

    public final void onDestroy() {
        this.broadcastDispatcher.unregisterReceiver(this.broadcastReceiver);
        ((ConfigurationControllerImpl) this.configurationController).removeCallback(this);
        SemWindowManager.getInstance().unregisterFoldStateListener(new OngoingCardController$getFoldStateListener$1(this));
        ((OngoingActivityController$createCardController$1) this.mStateListener).onClosed();
        Log.d("{OngoingExpandedPipController}", "onDestroy()");
        OngoingActivityDataHelper.INSTANCE.getClass();
        if (OngoingActivityDataHelper.mOngoingActivityLists.size() != 0) {
            NotificationSAUtil.sendOALog(this.mContext, SystemUIAnalytics.OAID_ONGOING_CLOSD_EXPAND_VIEW, OngoingActivityDataHelper.getDataByIndex(0).mNotificationEntry);
        }
        OngoingActivityDataHelper.cardIsShown = false;
        this.mCapsule.setVisibility(0);
        BackKeyConsumerViewGroup backKeyConsumerViewGroup = this.mParentView;
        if (backKeyConsumerViewGroup != null && backKeyConsumerViewGroup.isAttachedToWindow()) {
            Log.d("{OngoingExpandedPipController}", "onDestroy() removeViewImmediate");
            WindowManager windowManager = this.mWindowManager;
            if (windowManager != null) {
                windowManager.removeViewImmediate(this.mParentView);
            }
        }
        StatusBarWindowStateController statusBarWindowStateController = this.statusBarWindowStateController;
        ((HashSet) statusBarWindowStateController.listeners).remove(this.statusBarWindowStateListener);
        ((ArrayList) OngoingActivityDataHelper.observers).remove(this);
        OngoingActivityController ongoingActivityController = ((OngoingActivityController$createCardController$1) this.mStateListener).this$0;
        CollapsedStatusBarFragment.OngoingActivityListenerImpl ongoingActivityListenerImpl = ongoingActivityController.ongoingActivityListener;
        if (ongoingActivityListenerImpl != null) {
            CollapsedStatusBarFragment.this.mNotificationIconAreaController.setAnimationsEnabled(true);
        }
        ongoingActivityController.isUpdateNotAllowed = false;
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
            collapseAnimation();
        }
        return true;
    }

    @Override // com.android.systemui.statusbar.phone.ongoingactivity.IOngoingObserver
    public final void remove(int i) {
        OngoingActivityDataHelper.INSTANCE.getClass();
        int size = OngoingActivityDataHelper.mOngoingActivityLists.size();
        ListPopupWindow$$ExternalSyntheticOutline0.m(size, "remove view and datasize : ", "{OngoingExpandedPipController}");
        if (size == 0) {
            onDestroy();
        } else {
            this.mCardStackView.rebuildAllItems();
        }
    }

    public final void startAnimation(View view, float f, float f2, float f3, float f4, PathInterpolator pathInterpolator, long j, final Function0 function0) {
        view.setTranslationX(f);
        view.setTranslationY(f2);
        view.animate().x(f3).y(f4).setInterpolator(pathInterpolator).setDuration(j).withEndAction(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardControllerKt$sam$java_lang_Runnable$0
            @Override // java.lang.Runnable
            public final /* synthetic */ void run() {
                Function0.this.invoke();
            }
        }).start();
        ((OngoingActivityController$createCardController$1) this.mStateListener).onAllowStateChanged(true);
    }

    @Override // com.android.systemui.statusbar.phone.ongoingactivity.IOngoingObserver
    public final void update$6() {
        this.mCardStackView.updateItem();
    }

    public final void updateCardViewLayout() {
        View findViewById = this.mExpandedView.findViewById(R.id.ongoing_card_background);
        ViewGroup.LayoutParams layoutParams = findViewById != null ? findViewById.getLayoutParams() : null;
        float f = (!BasicRune.BASIC_FOLDABLE_TYPE_FOLD || DeviceState.isSubDisplay(this.mContext)) ? this.mContext.getResources().getFloat(R.dimen.ongoing_activity_card_width) : this.mContext.getResources().getFloat(R.dimen.ongoing_activity_card_width_fold);
        if (layoutParams != null) {
            layoutParams.width = (int) (DeviceState.getDisplayWidth(this.mContext) * f);
        }
        if (findViewById != null) {
            findViewById.setLayoutParams(layoutParams);
        }
    }
}
