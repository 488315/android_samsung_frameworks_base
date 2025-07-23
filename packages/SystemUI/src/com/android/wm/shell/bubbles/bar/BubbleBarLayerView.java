package com.android.wm.shell.bubbles.bar;

import android.R;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.systemui.wmshell.BubblesManager;
import com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda3;
import com.android.wm.shell.bubbles.Bubble;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleData;
import com.android.wm.shell.bubbles.BubbleExpandedViewManager$Companion$fromBubbleController$1;
import com.android.wm.shell.bubbles.BubbleLogger;
import com.android.wm.shell.bubbles.BubblePositioner;
import com.android.wm.shell.bubbles.BubbleViewProvider;
import com.android.wm.shell.bubbles.DismissViewUtils;
import com.android.wm.shell.shared.animation.Interpolators;
import com.android.wm.shell.shared.bubbles.BubbleBarLocation;
import com.android.wm.shell.shared.bubbles.DeviceConfig;
import com.android.wm.shell.shared.bubbles.DismissView;
import com.android.wm.shell.shared.bubbles.DragZone;
import com.android.wm.shell.shared.bubbles.DragZoneFactory;
import com.android.wm.shell.shared.bubbles.DropTargetManager;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class BubbleBarLayerView extends FrameLayout implements ViewTreeObserver.OnComputeInternalInsetsListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final BubbleBarAnimationHelper mAnimationHelper;
    public final BubbleController mBubbleController;
    public final BubbleData mBubbleData;
    public final BubbleExpandedViewPinController mBubbleExpandedViewPinController;
    public final BubbleLogger mBubbleLogger;
    public final DismissView mDismissView;
    public BubbleBarExpandedViewDragController mDragController;
    public final BubbleEducationViewController mEducationViewController;
    public BubbleViewProvider mExpandedBubble;
    public BubbleBarExpandedView mExpandedView;
    public final Rect mHandleTouchBounds;
    public boolean mIsExpanded;
    public final BubblePositioner mPositioner;
    public final View mScrimView;
    public final Rect mTempRect;
    public final Region mTouchableRegion;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.bubbles.bar.BubbleBarLayerView$1, reason: invalid class name */
    public class AnonymousClass1 implements DropTargetManager.DragZoneChangedListener {
        public final /* synthetic */ LocationChangeListener val$locationChangeListener;
        public DragZone mLastBubbleLocationDragZone = null;
        public BubbleBarLocation mInitialLocation = null;

        public AnonymousClass1(LocationChangeListener locationChangeListener) {
            this.val$locationChangeListener = locationChangeListener;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.bubbles.bar.BubbleBarLayerView$2, reason: invalid class name */
    public class AnonymousClass2 implements DragZoneFactory.SplitScreenModeChecker {
        public AnonymousClass2(BubbleBarLayerView bubbleBarLayerView) {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.bubbles.bar.BubbleBarLayerView$4, reason: invalid class name */
    public class AnonymousClass4 {
        public final /* synthetic */ BubbleViewProvider val$b;

        public AnonymousClass4(BubbleViewProvider bubbleViewProvider) {
            this.val$b = bubbleViewProvider;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class LocationChangeListener {
        public BubbleBarLocation mInitialLocation;

        public /* synthetic */ LocationChangeListener(BubbleBarLayerView bubbleBarLayerView, int i) {
            this();
        }

        public final void onRelease(BubbleBarLocation bubbleBarLocation) {
            BubbleBarLayerView bubbleBarLayerView = BubbleBarLayerView.this;
            bubbleBarLayerView.mBubbleController.getClass();
            if (bubbleBarLocation != this.mInitialLocation) {
                BubbleLogger.Event event = bubbleBarLocation.isOnLeft(bubbleBarLayerView.isLayoutRtl()) ? BubbleLogger.Event.BUBBLE_BAR_MOVED_LEFT_DRAG_EXP_VIEW : BubbleLogger.Event.BUBBLE_BAR_MOVED_RIGHT_DRAG_EXP_VIEW;
                BubbleViewProvider bubbleViewProvider = bubbleBarLayerView.mExpandedBubble;
                if (bubbleViewProvider == null || !(bubbleViewProvider instanceof Bubble)) {
                    return;
                }
                bubbleBarLayerView.mBubbleLogger.log((Bubble) bubbleViewProvider, event);
            }
        }

        private LocationChangeListener() {
        }
    }

    public BubbleBarLayerView(Context context, BubbleController bubbleController, BubbleData bubbleData, BubbleLogger bubbleLogger) {
        super(context);
        int i = 0;
        this.mIsExpanded = false;
        this.mTouchableRegion = new Region();
        this.mTempRect = new Rect();
        this.mHandleTouchBounds = new Rect();
        this.mBubbleController = bubbleController;
        this.mBubbleData = bubbleData;
        BubblePositioner positioner = bubbleController.getPositioner();
        this.mPositioner = positioner;
        this.mBubbleLogger = bubbleLogger;
        this.mAnimationHelper = new BubbleBarAnimationHelper(context, positioner);
        this.mEducationViewController = new BubbleEducationViewController(context, new BubbleBarLayerView$$ExternalSyntheticLambda0(this));
        View view = new View(getContext());
        this.mScrimView = view;
        view.setImportantForAccessibility(2);
        view.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.system_neutral1_1000)));
        addView(view);
        view.setAlpha(0.0f);
        view.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.system_neutral1_1000)));
        DismissView dismissView = this.mDismissView;
        if (dismissView != null) {
            removeView(dismissView);
        }
        DismissView dismissView2 = new DismissView(getContext());
        this.mDismissView = dismissView2;
        DismissViewUtils.setup(dismissView2);
        addView(this.mDismissView);
        BubbleExpandedViewPinController bubbleExpandedViewPinController = new BubbleExpandedViewPinController(context, this, positioner);
        this.mBubbleExpandedViewPinController = bubbleExpandedViewPinController;
        bubbleExpandedViewPinController.listener = new LocationChangeListener(this, i);
        setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.bubbles.bar.BubbleBarLayerView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                BubbleBarLayerView bubbleBarLayerView = BubbleBarLayerView.this;
                int i2 = BubbleBarLayerView.$r8$clinit;
                bubbleBarLayerView.hideModalOrCollapse();
            }
        });
    }

    public final boolean canExpandView(BubbleViewProvider bubbleViewProvider) {
        if (bubbleViewProvider.getBubbleBarExpandedView() == null) {
            return false;
        }
        return (this.mExpandedBubble != null && this.mIsExpanded && bubbleViewProvider.getKey().equals(this.mExpandedBubble.getKey())) ? false : true;
    }

    public BubbleBarExpandedViewDragController getDragController() {
        return this.mDragController;
    }

    public final void hideModalOrCollapse() {
        BubbleBarExpandedView bubbleBarExpandedView;
        BubbleEducationViewController bubbleEducationViewController = this.mEducationViewController;
        if ((bubbleEducationViewController.educationView == null || bubbleEducationViewController.rootView == null) ? false : true) {
            bubbleEducationViewController.getClass();
            BubbleEducationViewController.hideEducation$default(bubbleEducationViewController, true);
            return;
        }
        if (this.mIsExpanded && (bubbleBarExpandedView = this.mExpandedView) != null) {
            BubbleBarMenuView bubbleBarMenuView = bubbleBarExpandedView.mMenuViewController.mMenuView;
            if (bubbleBarMenuView != null && bubbleBarMenuView.getVisibility() == 0) {
                bubbleBarExpandedView.mMenuViewController.hideMenu(true);
                return;
            }
            BubbleBarExpandedView bubbleBarExpandedView2 = this.mExpandedView;
            if (bubbleBarExpandedView2.mPositioner.mImeVisible) {
                ((BubbleExpandedViewManager$Companion$fromBubbleController$1) bubbleBarExpandedView2.mManager).$controller.hideCurrentInputMethod(null);
                return;
            }
        }
        this.mBubbleController.collapseStack();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        WindowManager windowManager = (WindowManager) ((FrameLayout) this).mContext.getSystemService(WindowManager.class);
        BubblePositioner bubblePositioner = this.mPositioner;
        Context context = ((FrameLayout) this).mContext;
        Objects.requireNonNull(windowManager);
        bubblePositioner.update(DeviceConfig.create(context, windowManager));
        getViewTreeObserver().addOnComputeInternalInsetsListener(this);
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x001c, code lost:
    
        if (r1.rootView != null) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onComputeInternalInsets(android.view.ViewTreeObserver.InternalInsetsInfo r4) {
        /*
            r3 = this;
            r0 = 3
            r4.setTouchableInsets(r0)
            android.graphics.Region r0 = r3.mTouchableRegion
            r0.setEmpty()
            android.graphics.Region r0 = r3.mTouchableRegion
            android.graphics.Rect r1 = r3.mTempRect
            r1.setEmpty()
            boolean r1 = r3.mIsExpanded
            if (r1 != 0) goto L1e
            com.android.wm.shell.bubbles.bar.BubbleEducationViewController r1 = r3.mEducationViewController
            com.android.wm.shell.shared.bubbles.BubblePopupView r2 = r1.educationView
            if (r2 == 0) goto L2a
            android.view.ViewGroup r1 = r1.rootView
            if (r1 == 0) goto L2a
        L1e:
            android.graphics.Rect r1 = r3.mTempRect
            r3.getBoundsOnScreen(r1)
            android.graphics.Rect r1 = r3.mTempRect
            android.graphics.Region$Op r2 = android.graphics.Region.Op.UNION
            r0.op(r1, r2)
        L2a:
            android.graphics.Region r4 = r4.touchableRegion
            android.graphics.Region r3 = r3.mTouchableRegion
            r4.set(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.bubbles.bar.BubbleBarLayerView.onComputeInternalInsets(android.view.ViewTreeObserver$InternalInsetsInfo):void");
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnComputeInternalInsetsListener(this);
        if (this.mExpandedView != null) {
            BubbleEducationViewController bubbleEducationViewController = this.mEducationViewController;
            bubbleEducationViewController.getClass();
            BubbleEducationViewController.hideEducation$default(bubbleEducationViewController, false);
            removeView(this.mExpandedView);
            this.mExpandedView = null;
        }
    }

    public final BubbleViewProvider prepareExpandedView(BubbleViewProvider bubbleViewProvider) {
        BubbleViewProvider bubbleViewProvider2;
        if (!canExpandView(bubbleViewProvider)) {
            throw new IllegalStateException("Can't prepare expand. Check canExpandView(b) first.");
        }
        BubbleBarExpandedView bubbleBarExpandedView = bubbleViewProvider.getBubbleBarExpandedView();
        BubbleViewProvider bubbleViewProvider3 = null;
        if (this.mExpandedBubble != null && !bubbleViewProvider.getKey().equals(this.mExpandedBubble.getKey())) {
            if (!this.mIsExpanded || this.mExpandedBubble.getBubbleBarExpandedView() == null) {
                removeView(this.mExpandedView);
                bubbleViewProvider2 = null;
            } else {
                bubbleViewProvider2 = this.mExpandedBubble;
            }
            this.mExpandedView = null;
            bubbleViewProvider3 = bubbleViewProvider2;
        }
        if (this.mExpandedView == null) {
            if (bubbleBarExpandedView.getParent() != null) {
                this.mAnimationHelper.cancelAnimations();
                removeView(bubbleBarExpandedView);
            }
            this.mExpandedBubble = bubbleViewProvider;
            this.mExpandedView = bubbleBarExpandedView;
            boolean equals = bubbleViewProvider.getKey().equals("Overflow");
            BubblePositioner bubblePositioner = this.mPositioner;
            int i = equals ? bubblePositioner.mOverflowWidth : bubblePositioner.mExpandedViewBubbleBarWidth;
            int expandedViewHeightForBubbleBar = bubblePositioner.getExpandedViewHeightForBubbleBar(equals);
            this.mExpandedView.setVisibility(8);
            BubbleBarExpandedView bubbleBarExpandedView2 = this.mExpandedView;
            BubblePositioner bubblePositioner2 = this.mPositioner;
            bubbleBarExpandedView2.setY((bubblePositioner2.mBubbleBarTopOnScreen - bubblePositioner2.mExpandedViewPadding) - expandedViewHeightForBubbleBar);
            BubbleBarExpandedView bubbleBarExpandedView3 = this.mExpandedView;
            bubbleBarExpandedView3.mLayerBoundsSupplier = new BubbleBarLayerView$$ExternalSyntheticLambda2(this);
            bubbleBarExpandedView3.mListener = new AnonymousClass4(bubbleViewProvider);
            this.mDragController = new BubbleBarExpandedViewDragController(((FrameLayout) this).mContext, this.mExpandedView, this.mDismissView, this.mAnimationHelper, this.mPositioner, this.mBubbleExpandedViewPinController, null, null, new BubbleBarLayerView$$ExternalSyntheticLambda0(this));
            addView(this.mExpandedView, new FrameLayout.LayoutParams(i, expandedViewHeightForBubbleBar, 3));
        }
        BubbleEducationViewController bubbleEducationViewController = this.mEducationViewController;
        boolean z = true;
        if ((bubbleEducationViewController.educationView == null || bubbleEducationViewController.rootView == null) ? false : true) {
            bubbleEducationViewController.getClass();
            BubbleEducationViewController.hideEducation$default(bubbleEducationViewController, true);
        }
        this.mIsExpanded = true;
        BubblesManager.AnonymousClass5 anonymousClass5 = this.mBubbleController.mSysuiProxy;
        anonymousClass5.val$sysuiMainExecutor.execute(new BubblesManager$5$$ExternalSyntheticLambda3(anonymousClass5, anonymousClass5.val$sysUiState, z));
        this.mScrimView.animate().setInterpolator(Interpolators.ALPHA_IN).alpha(0.32f).start();
        return bubbleViewProvider3;
    }
}
