package com.android.wm.shell.bubbles.bar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Insets;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import com.android.systemui.R;
import com.android.systemui.SystemUIApplication;
import com.android.wm.shell.bubbles.Bubble;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleEducationController;
import com.android.wm.shell.bubbles.BubbleExpandedViewManager;
import com.android.wm.shell.bubbles.BubbleExpandedViewManager$Companion$fromBubbleController$1;
import com.android.wm.shell.bubbles.BubbleLogger;
import com.android.wm.shell.bubbles.BubbleOverflowContainerView;
import com.android.wm.shell.bubbles.BubblePositioner;
import com.android.wm.shell.bubbles.BubbleTaskView;
import com.android.wm.shell.bubbles.BubbleTaskViewListener;
import com.android.wm.shell.bubbles.BubbleViewProvider;
import com.android.wm.shell.bubbles.RegionSamplingProvider;
import com.android.wm.shell.bubbles.bar.BubbleBarExpandedView;
import com.android.wm.shell.bubbles.bar.BubbleBarLayerView;
import com.android.wm.shell.bubbles.bar.BubbleBarMenuView;
import com.android.wm.shell.dagger.HasWMComponent;
import com.android.wm.shell.shared.TypefaceUtils;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import com.android.wm.shell.shared.bubbles.BubbleBarLocation;
import com.android.wm.shell.shared.bubbles.BubblePopupDrawable;
import com.android.wm.shell.shared.bubbles.BubblePopupView;
import com.android.wm.shell.shared.handles.RegionSamplingHelper;
import com.android.wm.shell.taskview.TaskView;
import com.android.wm.shell.taskview.TaskViewTaskController;
import java.util.ArrayList;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class BubbleBarExpandedView extends FrameLayout implements BubbleTaskViewListener.Callback {
    public static final AnonymousClass1 CORNER_RADIUS = new FloatProperty("cornerRadius") { // from class: com.android.wm.shell.bubbles.bar.BubbleBarExpandedView.1
        @Override // android.util.Property
        public final Float get(Object obj) {
            return Float.valueOf(((BubbleBarExpandedView) obj).mCurrentCornerRadius);
        }

        @Override // android.util.FloatProperty
        public final void setValue(Object obj, float f) {
            BubbleBarExpandedView bubbleBarExpandedView = (BubbleBarExpandedView) obj;
            if (bubbleBarExpandedView.mCurrentCornerRadius != f) {
                bubbleBarExpandedView.mCurrentCornerRadius = f;
                TaskView taskView = bubbleBarExpandedView.mTaskView;
                if (taskView != null) {
                    taskView.setCornerRadius(f);
                }
                bubbleBarExpandedView.invalidateOutline();
            }
        }
    };
    public static final AnonymousClass2 TASK_VIEW_ALPHA = null;
    public BubbleLogger bubbleLogger;
    public Runnable mAnimateExpansion;
    public Executor mBackgroundExecutor;
    public final int mBottomClip;
    public Bubble mBubble;
    public BubbleTaskViewListener mBubbleTaskViewListener;
    public int mCaptionHeight;
    public float mCurrentCornerRadius;
    public float mDraggedCornerRadius;
    public BubbleBarHandleView mHandleView;
    public boolean mIsAnimating;
    public boolean mIsContentVisible;
    public boolean mIsDragging;
    public BubbleBarLayerView$$ExternalSyntheticLambda2 mLayerBoundsSupplier;
    public BubbleBarLayerView.AnonymousClass4 mListener;
    public final int[] mLoc;
    public Executor mMainExecutor;
    public BubbleExpandedViewManager mManager;
    public BubbleBarMenuViewController mMenuViewController;
    public BubbleOverflowContainerView mOverflowView;
    public BubblePositioner mPositioner;
    public RegionSamplingHelper mRegionSamplingHelper;
    public RegionSamplingProvider mRegionSamplingProvider;
    public float mRestingCornerRadius;
    public final Rect mSampleRect;
    public TaskView mTaskView;
    public final Rect mTempBounds;
    public TaskViewVisibilityState mVisibilityState;

    /* renamed from: com.android.wm.shell.bubbles.bar.BubbleBarExpandedView$4, reason: invalid class name */
    public class AnonymousClass4 {
        public AnonymousClass4() {
        }

        public final void onMenuVisibilityChanged(boolean z) {
            BubbleBarLayerView$$ExternalSyntheticLambda2 bubbleBarLayerView$$ExternalSyntheticLambda2;
            BubbleBarExpandedView bubbleBarExpandedView = BubbleBarExpandedView.this;
            TaskView taskView = bubbleBarExpandedView.mTaskView;
            if (taskView != null && (bubbleBarLayerView$$ExternalSyntheticLambda2 = bubbleBarExpandedView.mLayerBoundsSupplier) != null) {
                Rect rect = z ? (Rect) bubbleBarLayerView$$ExternalSyntheticLambda2.get() : null;
                taskView.mObscuredTouchRegion = rect != null ? new Region(rect) : null;
                taskView.invalidate();
            }
            if (z) {
                bubbleBarExpandedView.mHandleView.setFocusable(false);
                bubbleBarExpandedView.mHandleView.setImportantForAccessibility(2);
            } else {
                bubbleBarExpandedView.mHandleView.setFocusable(true);
                bubbleBarExpandedView.mHandleView.setImportantForAccessibility(0);
            }
        }
    }

    public class HandleViewAccessibilityDelegate extends View.AccessibilityDelegate {
        public /* synthetic */ HandleViewAccessibilityDelegate(BubbleBarExpandedView bubbleBarExpandedView, int i) {
            this();
        }

        @Override // android.view.View.AccessibilityDelegate
        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, BubbleBarExpandedView.this.getResources().getString(R.string.bubble_accessibility_action_expand_menu)));
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_COLLAPSE);
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_DISMISS);
            if (BubbleBarExpandedView.this.mPositioner.isBubbleBarOnLeft()) {
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_bubble_bar_right, BubbleBarExpandedView.this.getResources().getString(R.string.bubble_accessibility_action_move_bar_right)));
            } else {
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_bubble_bar_left, BubbleBarExpandedView.this.getResources().getString(R.string.bubble_accessibility_action_move_bar_left)));
            }
        }

        @Override // android.view.View.AccessibilityDelegate
        public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            if (super.performAccessibilityAction(view, i, bundle)) {
                return true;
            }
            if (i == 524288) {
                ((BubbleExpandedViewManager$Companion$fromBubbleController$1) BubbleBarExpandedView.this.mManager).$controller.collapseStack();
                return true;
            }
            if (i == 1048576) {
                BubbleBarExpandedView bubbleBarExpandedView = BubbleBarExpandedView.this;
                BubbleExpandedViewManager bubbleExpandedViewManager = bubbleBarExpandedView.mManager;
                Bubble bubble = bubbleBarExpandedView.mBubble;
                BubbleController bubbleController = ((BubbleExpandedViewManager$Companion$fromBubbleController$1) bubbleExpandedViewManager).$controller;
                bubbleController.getClass();
                bubbleController.mBubbleData.dismissBubbleWithKey(1, bubble.mKey);
                return true;
            }
            if (i == R.id.action_move_bubble_bar_left) {
                BubbleExpandedViewManager bubbleExpandedViewManager2 = BubbleBarExpandedView.this.mManager;
                Parcelable.Creator<BubbleBarLocation> creator = BubbleBarLocation.CREATOR;
                ((BubbleExpandedViewManager$Companion$fromBubbleController$1) bubbleExpandedViewManager2).$controller.getClass();
                return true;
            }
            if (i != R.id.action_move_bubble_bar_right) {
                return false;
            }
            BubbleExpandedViewManager bubbleExpandedViewManager3 = BubbleBarExpandedView.this.mManager;
            Parcelable.Creator<BubbleBarLocation> creator2 = BubbleBarLocation.CREATOR;
            ((BubbleExpandedViewManager$Companion$fromBubbleController$1) bubbleExpandedViewManager3).$controller.getClass();
            return true;
        }

        private HandleViewAccessibilityDelegate() {
        }
    }

    enum TaskViewVisibilityState {
        PENDING_INVISIBLE,
        INVISIBLE,
        PENDING_VISIBLE,
        VISIBLE
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.wm.shell.bubbles.bar.BubbleBarExpandedView$1] */
    static {
        new FloatProperty("taskViewAlpha") { // from class: com.android.wm.shell.bubbles.bar.BubbleBarExpandedView.2
            @Override // android.util.Property
            public final Float get(Object obj) {
                BubbleBarExpandedView bubbleBarExpandedView = (BubbleBarExpandedView) obj;
                TaskView taskView = bubbleBarExpandedView.mTaskView;
                return Float.valueOf(taskView != null ? taskView.getAlpha() : bubbleBarExpandedView.getAlpha());
            }

            @Override // android.util.FloatProperty
            public final void setValue(Object obj, float f) {
                BubbleBarExpandedView bubbleBarExpandedView = (BubbleBarExpandedView) obj;
                TaskView taskView = bubbleBarExpandedView.mTaskView;
                if (taskView != null) {
                    taskView.setAlpha(f);
                }
                bubbleBarExpandedView.setAlpha(f);
            }
        };
    }

    public BubbleBarExpandedView(Context context) {
        this(context, null);
    }

    public final void applyThemeAttrs() {
        this.mCaptionHeight = getResources().getDimensionPixelSize(R.dimen.bubble_bar_expanded_view_caption_height);
        this.mRestingCornerRadius = getResources().getDimensionPixelSize(R.dimen.bubble_bar_expanded_view_corner_radius);
        this.mDraggedCornerRadius = getResources().getDimensionPixelSize(R.dimen.bubble_bar_expanded_view_corner_radius_dragged);
        float f = this.mRestingCornerRadius;
        this.mCurrentCornerRadius = f;
        TaskView taskView = this.mTaskView;
        if (taskView != null) {
            taskView.setCornerRadius(f);
            TaskView taskView2 = this.mTaskView;
            Insets insetsOf = Insets.of(0, this.mCaptionHeight, 0, 0);
            taskView2.mCaptionInsets = insetsOf;
            if (insetsOf == null) {
                TaskViewTaskController taskViewTaskController = taskView2.mTaskViewTaskController;
                Rect rect = taskViewTaskController.mCaptionInsets;
                if (rect == null || !rect.equals(null)) {
                    taskViewTaskController.mCaptionInsets = null;
                    taskViewTaskController.applyCaptionInsetsIfNeeded();
                }
            }
        }
    }

    public final Rect getCaptionSampleRect() {
        TaskView taskView = this.mTaskView;
        if (taskView == null) {
            return null;
        }
        taskView.getLocationOnScreen(this.mLoc);
        Rect rect = this.mSampleRect;
        int[] iArr = this.mLoc;
        int i = iArr[0];
        rect.set(i, iArr[1], this.mTaskView.getWidth() + i, this.mLoc[1] + this.mCaptionHeight);
        return this.mSampleRect;
    }

    public RegionSamplingHelper getRegionSamplingHelper() {
        return this.mRegionSamplingHelper;
    }

    public final void initialize(BubbleExpandedViewManager bubbleExpandedViewManager, BubblePositioner bubblePositioner, boolean z, BubbleTaskView bubbleTaskView, Executor executor, Executor executor2, RegionSamplingProvider regionSamplingProvider) {
        final BubbleBarExpandedView bubbleBarExpandedView;
        this.mManager = bubbleExpandedViewManager;
        this.mPositioner = bubblePositioner;
        this.mMainExecutor = executor;
        this.mBackgroundExecutor = executor2;
        this.mRegionSamplingProvider = regionSamplingProvider;
        if (z) {
            BubbleOverflowContainerView bubbleOverflowContainerView = (BubbleOverflowContainerView) LayoutInflater.from(getContext()).inflate(R.layout.bubble_overflow_container, (ViewGroup) null);
            this.mOverflowView = bubbleOverflowContainerView;
            bubbleOverflowContainerView.mExpandedViewManager = bubbleExpandedViewManager;
            bubbleOverflowContainerView.mPositioner = bubblePositioner;
            addView(bubbleOverflowContainerView);
            this.mHandleView.setVisibility(8);
            bubbleBarExpandedView = this;
        } else {
            this.mTaskView = bubbleTaskView.taskView;
            bubbleBarExpandedView = this;
            bubbleBarExpandedView.mBubbleTaskViewListener = new BubbleTaskViewListener(((FrameLayout) this).mContext, bubbleTaskView, bubbleBarExpandedView, bubbleExpandedViewManager, this);
            if (bubbleBarExpandedView.mTaskView.getParent() != null) {
                if (bubbleTaskView.isVisible) {
                    bubbleBarExpandedView.mVisibilityState = TaskViewVisibilityState.PENDING_INVISIBLE;
                }
                ((ViewGroup) bubbleBarExpandedView.mTaskView.getParent()).removeView(bubbleBarExpandedView.mTaskView);
            }
            if (bubbleBarExpandedView.mVisibilityState == TaskViewVisibilityState.INVISIBLE) {
                bubbleBarExpandedView.mVisibilityState = TaskViewVisibilityState.PENDING_VISIBLE;
                bubbleBarExpandedView.setupTaskView();
            }
            bubbleBarExpandedView.bringChildToFront(bubbleBarExpandedView.mHandleView);
            bubbleBarExpandedView.mHandleView.setAccessibilityDelegate(new HandleViewAccessibilityDelegate(bubbleBarExpandedView, 0));
        }
        BubbleBarMenuViewController bubbleBarMenuViewController = new BubbleBarMenuViewController(((FrameLayout) bubbleBarExpandedView).mContext, bubbleBarExpandedView.mHandleView, bubbleBarExpandedView);
        bubbleBarExpandedView.mMenuViewController = bubbleBarMenuViewController;
        bubbleBarMenuViewController.mListener = bubbleBarExpandedView.new AnonymousClass4();
        bubbleBarExpandedView.mHandleView.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.bubbles.bar.BubbleBarExpandedView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                final BubbleBarMenuViewController bubbleBarMenuViewController2 = this.f$0.mMenuViewController;
                if (bubbleBarMenuViewController2.mMenuView == null || bubbleBarMenuViewController2.mScrimView == null) {
                    BubbleBarMenuView bubbleBarMenuView = (BubbleBarMenuView) LayoutInflater.from(bubbleBarMenuViewController2.mContext).inflate(R.layout.bubble_bar_menu_view, bubbleBarMenuViewController2.mRootView, false);
                    bubbleBarMenuViewController2.mMenuView = bubbleBarMenuView;
                    final BubbleBarMenuViewController$$ExternalSyntheticLambda1 bubbleBarMenuViewController$$ExternalSyntheticLambda1 = new BubbleBarMenuViewController$$ExternalSyntheticLambda1(bubbleBarMenuViewController2, 3);
                    bubbleBarMenuView.mBubbleSectionView.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.bubbles.bar.BubbleBarMenuView$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            BubbleBarMenuViewController$$ExternalSyntheticLambda1 bubbleBarMenuViewController$$ExternalSyntheticLambda12 = bubbleBarMenuViewController$$ExternalSyntheticLambda1;
                            int i = BubbleBarMenuView.$r8$clinit;
                            bubbleBarMenuViewController$$ExternalSyntheticLambda12.run();
                        }
                    });
                    Bubble bubble = bubbleBarMenuViewController2.mBubble;
                    if (bubble != null) {
                        BubbleBarMenuView bubbleBarMenuView2 = bubbleBarMenuViewController2.mMenuView;
                        bubbleBarMenuView2.getClass();
                        Icon icon = bubble.mIcon;
                        if (icon != null) {
                            bubbleBarMenuView2.mBubbleIconView.setImageIcon(icon);
                        } else {
                            bubbleBarMenuView2.mBubbleIconView.setImageBitmap(bubble.mBubbleBitmap);
                        }
                        bubbleBarMenuView2.mBubbleTitleView.setText(bubble.mTitle);
                        BubbleBarMenuView bubbleBarMenuView3 = bubbleBarMenuViewController2.mMenuView;
                        final Bubble bubble2 = bubbleBarMenuViewController2.mBubble;
                        ArrayList arrayList = new ArrayList();
                        Resources resources = bubbleBarMenuViewController2.mContext.getResources();
                        int color = bubbleBarMenuViewController2.mContext.getColor(android.R.color.search_url_text_material_light);
                        if (bubble2.isChat()) {
                            final int i = 0;
                            arrayList.add(new BubbleBarMenuView.MenuAction(Icon.createWithResource(bubbleBarMenuViewController2.mContext, R.drawable.bubble_ic_stop_bubble), resources.getString(R.string.bubbles_dont_bubble_conversation), color, new View.OnClickListener() { // from class: com.android.wm.shell.bubbles.bar.BubbleBarMenuViewController$$ExternalSyntheticLambda8
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view2) {
                                    switch (i) {
                                        case 0:
                                            BubbleBarMenuViewController bubbleBarMenuViewController3 = bubbleBarMenuViewController2;
                                            Bubble bubble3 = bubble2;
                                            bubbleBarMenuViewController3.hideMenu(true);
                                            BubbleBarExpandedView.AnonymousClass4 anonymousClass4 = bubbleBarMenuViewController3.mListener;
                                            if (anonymousClass4 != null) {
                                                BubbleBarExpandedView bubbleBarExpandedView2 = BubbleBarExpandedView.this;
                                                BubbleBarLayerView.AnonymousClass4 anonymousClass42 = bubbleBarExpandedView2.mListener;
                                                if (anonymousClass42 != null) {
                                                    String str = bubble3.mKey;
                                                    BubbleBarLayerView.this.getClass();
                                                }
                                                bubbleBarExpandedView2.bubbleLogger.log(bubble3, BubbleLogger.Event.BUBBLE_BAR_APP_MENU_OPT_OUT);
                                                break;
                                            }
                                            break;
                                        case 1:
                                            BubbleBarMenuViewController bubbleBarMenuViewController4 = bubbleBarMenuViewController2;
                                            Bubble bubble4 = bubble2;
                                            bubbleBarMenuViewController4.hideMenu(true);
                                            BubbleBarExpandedView.AnonymousClass4 anonymousClass43 = bubbleBarMenuViewController4.mListener;
                                            if (anonymousClass43 != null) {
                                                BubbleBarExpandedView bubbleBarExpandedView3 = BubbleBarExpandedView.this;
                                                BubbleController bubbleController = ((BubbleExpandedViewManager$Companion$fromBubbleController$1) bubbleBarExpandedView3.mManager).$controller;
                                                bubbleController.getClass();
                                                bubbleController.mBubbleData.dismissBubbleWithKey(1, bubble4.mKey);
                                                bubbleBarExpandedView3.bubbleLogger.log(bubble4, BubbleLogger.Event.BUBBLE_BAR_BUBBLE_DISMISSED_APP_MENU);
                                                break;
                                            }
                                            break;
                                        default:
                                            BubbleBarMenuViewController bubbleBarMenuViewController5 = bubbleBarMenuViewController2;
                                            Bubble bubble5 = bubble2;
                                            bubbleBarMenuViewController5.hideMenu(true);
                                            BubbleBarExpandedView.AnonymousClass4 anonymousClass44 = bubbleBarMenuViewController5.mListener;
                                            if (anonymousClass44 != null) {
                                                BubbleBarExpandedView bubbleBarExpandedView4 = BubbleBarExpandedView.this;
                                                ((BubbleExpandedViewManager$Companion$fromBubbleController$1) bubbleBarExpandedView4.mManager).$controller.collapseStack();
                                                ((FrameLayout) bubbleBarExpandedView4).mContext.startActivityAsUser(bubble5.getSettingsIntent(((FrameLayout) bubbleBarExpandedView4).mContext), bubble5.mUser);
                                                bubbleBarExpandedView4.bubbleLogger.log(bubble5, BubbleLogger.Event.BUBBLE_BAR_APP_MENU_GO_TO_SETTINGS);
                                                break;
                                            }
                                            break;
                                    }
                                }
                            }));
                            Bitmap bitmap = bubble2.mRawBadgeBitmap;
                            final int i2 = 2;
                            arrayList.add(new BubbleBarMenuView.MenuAction(bitmap != null ? Icon.createWithBitmap(bitmap) : null, resources.getString(R.string.bubbles_app_settings, bubble2.mAppName), new View.OnClickListener() { // from class: com.android.wm.shell.bubbles.bar.BubbleBarMenuViewController$$ExternalSyntheticLambda8
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view2) {
                                    switch (i2) {
                                        case 0:
                                            BubbleBarMenuViewController bubbleBarMenuViewController3 = bubbleBarMenuViewController2;
                                            Bubble bubble3 = bubble2;
                                            bubbleBarMenuViewController3.hideMenu(true);
                                            BubbleBarExpandedView.AnonymousClass4 anonymousClass4 = bubbleBarMenuViewController3.mListener;
                                            if (anonymousClass4 != null) {
                                                BubbleBarExpandedView bubbleBarExpandedView2 = BubbleBarExpandedView.this;
                                                BubbleBarLayerView.AnonymousClass4 anonymousClass42 = bubbleBarExpandedView2.mListener;
                                                if (anonymousClass42 != null) {
                                                    String str = bubble3.mKey;
                                                    BubbleBarLayerView.this.getClass();
                                                }
                                                bubbleBarExpandedView2.bubbleLogger.log(bubble3, BubbleLogger.Event.BUBBLE_BAR_APP_MENU_OPT_OUT);
                                                break;
                                            }
                                            break;
                                        case 1:
                                            BubbleBarMenuViewController bubbleBarMenuViewController4 = bubbleBarMenuViewController2;
                                            Bubble bubble4 = bubble2;
                                            bubbleBarMenuViewController4.hideMenu(true);
                                            BubbleBarExpandedView.AnonymousClass4 anonymousClass43 = bubbleBarMenuViewController4.mListener;
                                            if (anonymousClass43 != null) {
                                                BubbleBarExpandedView bubbleBarExpandedView3 = BubbleBarExpandedView.this;
                                                BubbleController bubbleController = ((BubbleExpandedViewManager$Companion$fromBubbleController$1) bubbleBarExpandedView3.mManager).$controller;
                                                bubbleController.getClass();
                                                bubbleController.mBubbleData.dismissBubbleWithKey(1, bubble4.mKey);
                                                bubbleBarExpandedView3.bubbleLogger.log(bubble4, BubbleLogger.Event.BUBBLE_BAR_BUBBLE_DISMISSED_APP_MENU);
                                                break;
                                            }
                                            break;
                                        default:
                                            BubbleBarMenuViewController bubbleBarMenuViewController5 = bubbleBarMenuViewController2;
                                            Bubble bubble5 = bubble2;
                                            bubbleBarMenuViewController5.hideMenu(true);
                                            BubbleBarExpandedView.AnonymousClass4 anonymousClass44 = bubbleBarMenuViewController5.mListener;
                                            if (anonymousClass44 != null) {
                                                BubbleBarExpandedView bubbleBarExpandedView4 = BubbleBarExpandedView.this;
                                                ((BubbleExpandedViewManager$Companion$fromBubbleController$1) bubbleBarExpandedView4.mManager).$controller.collapseStack();
                                                ((FrameLayout) bubbleBarExpandedView4).mContext.startActivityAsUser(bubble5.getSettingsIntent(((FrameLayout) bubbleBarExpandedView4).mContext), bubble5.mUser);
                                                bubbleBarExpandedView4.bubbleLogger.log(bubble5, BubbleLogger.Event.BUBBLE_BAR_APP_MENU_GO_TO_SETTINGS);
                                                break;
                                            }
                                            break;
                                    }
                                }
                            }));
                        }
                        final int i3 = 1;
                        arrayList.add(new BubbleBarMenuView.MenuAction(Icon.createWithResource(resources, R.drawable.ic_remove_no_shadow), resources.getString(R.string.bubble_dismiss_text), color, new View.OnClickListener() { // from class: com.android.wm.shell.bubbles.bar.BubbleBarMenuViewController$$ExternalSyntheticLambda8
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view2) {
                                switch (i3) {
                                    case 0:
                                        BubbleBarMenuViewController bubbleBarMenuViewController3 = bubbleBarMenuViewController2;
                                        Bubble bubble3 = bubble2;
                                        bubbleBarMenuViewController3.hideMenu(true);
                                        BubbleBarExpandedView.AnonymousClass4 anonymousClass4 = bubbleBarMenuViewController3.mListener;
                                        if (anonymousClass4 != null) {
                                            BubbleBarExpandedView bubbleBarExpandedView2 = BubbleBarExpandedView.this;
                                            BubbleBarLayerView.AnonymousClass4 anonymousClass42 = bubbleBarExpandedView2.mListener;
                                            if (anonymousClass42 != null) {
                                                String str = bubble3.mKey;
                                                BubbleBarLayerView.this.getClass();
                                            }
                                            bubbleBarExpandedView2.bubbleLogger.log(bubble3, BubbleLogger.Event.BUBBLE_BAR_APP_MENU_OPT_OUT);
                                            break;
                                        }
                                        break;
                                    case 1:
                                        BubbleBarMenuViewController bubbleBarMenuViewController4 = bubbleBarMenuViewController2;
                                        Bubble bubble4 = bubble2;
                                        bubbleBarMenuViewController4.hideMenu(true);
                                        BubbleBarExpandedView.AnonymousClass4 anonymousClass43 = bubbleBarMenuViewController4.mListener;
                                        if (anonymousClass43 != null) {
                                            BubbleBarExpandedView bubbleBarExpandedView3 = BubbleBarExpandedView.this;
                                            BubbleController bubbleController = ((BubbleExpandedViewManager$Companion$fromBubbleController$1) bubbleBarExpandedView3.mManager).$controller;
                                            bubbleController.getClass();
                                            bubbleController.mBubbleData.dismissBubbleWithKey(1, bubble4.mKey);
                                            bubbleBarExpandedView3.bubbleLogger.log(bubble4, BubbleLogger.Event.BUBBLE_BAR_BUBBLE_DISMISSED_APP_MENU);
                                            break;
                                        }
                                        break;
                                    default:
                                        BubbleBarMenuViewController bubbleBarMenuViewController5 = bubbleBarMenuViewController2;
                                        Bubble bubble5 = bubble2;
                                        bubbleBarMenuViewController5.hideMenu(true);
                                        BubbleBarExpandedView.AnonymousClass4 anonymousClass44 = bubbleBarMenuViewController5.mListener;
                                        if (anonymousClass44 != null) {
                                            BubbleBarExpandedView bubbleBarExpandedView4 = BubbleBarExpandedView.this;
                                            ((BubbleExpandedViewManager$Companion$fromBubbleController$1) bubbleBarExpandedView4.mManager).$controller.collapseStack();
                                            ((FrameLayout) bubbleBarExpandedView4).mContext.startActivityAsUser(bubble5.getSettingsIntent(((FrameLayout) bubbleBarExpandedView4).mContext), bubble5.mUser);
                                            bubbleBarExpandedView4.bubbleLogger.log(bubble5, BubbleLogger.Event.BUBBLE_BAR_APP_MENU_GO_TO_SETTINGS);
                                            break;
                                        }
                                        break;
                                }
                            }
                        }));
                        bubbleBarMenuView3.updateActions(arrayList);
                    }
                    View view2 = new View(bubbleBarMenuViewController2.mContext);
                    bubbleBarMenuViewController2.mScrimView = view2;
                    view2.setImportantForAccessibility(2);
                    bubbleBarMenuViewController2.mScrimView.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.bubbles.bar.BubbleBarMenuViewController$$ExternalSyntheticLambda7
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view3) {
                            bubbleBarMenuViewController2.hideMenu(true);
                        }
                    });
                    bubbleBarMenuViewController2.mRootView.addView(bubbleBarMenuViewController2.mScrimView);
                    bubbleBarMenuViewController2.mRootView.addView(bubbleBarMenuViewController2.mMenuView);
                }
                bubbleBarMenuViewController2.runOnMenuIsMeasured(new BubbleBarMenuViewController$$ExternalSyntheticLambda1(bubbleBarMenuViewController2, 1));
            }
        });
    }

    public boolean isSurfaceZOrderedOnTop() {
        TaskView taskView = this.mTaskView;
        return taskView != null && taskView.isZOrderedOnTop();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.wm.shell.bubbles.bar.BubbleBarExpandedView$5] */
    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        RegionSamplingProvider regionSamplingProvider;
        super.onAttachedToWindow();
        RegionSamplingHelper regionSamplingHelper = this.mRegionSamplingHelper;
        if (regionSamplingHelper != null) {
            regionSamplingHelper.stopAndDestroy();
        }
        if (this.mMainExecutor == null || this.mBackgroundExecutor == null || (regionSamplingProvider = this.mRegionSamplingProvider) == 0) {
            return;
        }
        this.mRegionSamplingHelper = regionSamplingProvider.createHelper(this, new RegionSamplingHelper.SamplingCallback() { // from class: com.android.wm.shell.bubbles.bar.BubbleBarExpandedView.5
            @Override // com.android.wm.shell.shared.handles.RegionSamplingHelper.SamplingCallback
            public final Rect getSampledRegion() {
                return BubbleBarExpandedView.this.getCaptionSampleRect();
            }

            @Override // com.android.wm.shell.shared.handles.RegionSamplingHelper.SamplingCallback
            public final boolean isSamplingEnabled() {
                AnonymousClass1 anonymousClass1 = BubbleBarExpandedView.CORNER_RADIUS;
                BubbleBarExpandedView bubbleBarExpandedView = BubbleBarExpandedView.this;
                TaskView taskView = bubbleBarExpandedView.mTaskView;
                return (taskView == null || taskView.mTaskViewTaskController.mTaskInfo == null || bubbleBarExpandedView.mIsDragging || bubbleBarExpandedView.mIsAnimating || !bubbleBarExpandedView.mIsContentVisible) ? false : true;
            }

            @Override // com.android.wm.shell.shared.handles.RegionSamplingHelper.SamplingCallback
            public final void onRegionDarknessChanged(boolean z) {
                BubbleBarHandleView bubbleBarHandleView = BubbleBarExpandedView.this.mHandleView;
                if (bubbleBarHandleView != null) {
                    int i = z ? bubbleBarHandleView.mHandleLightColor : bubbleBarHandleView.mHandleDarkColor;
                    if (i == bubbleBarHandleView.mRegionSamplerColor) {
                        return;
                    }
                    bubbleBarHandleView.mRegionSamplerColor = i;
                    ObjectAnimator objectAnimator = bubbleBarHandleView.mColorChangeAnim;
                    if (objectAnimator != null) {
                        objectAnimator.cancel();
                    }
                    ObjectAnimator objectAnimatorOfArgb = ObjectAnimator.ofArgb(bubbleBarHandleView, BubbleBarHandleView.HANDLE_COLOR, i);
                    bubbleBarHandleView.mColorChangeAnim = objectAnimatorOfArgb;
                    objectAnimatorOfArgb.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.bubbles.bar.BubbleBarHandleView.2
                        public AnonymousClass2() {
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            BubbleBarHandleView.this.mColorChangeAnim = null;
                        }
                    });
                    bubbleBarHandleView.mColorChangeAnim.setDuration(120L);
                    bubbleBarHandleView.mColorChangeAnim.start();
                }
            }
        }, this.mMainExecutor, this.mBackgroundExecutor);
    }

    @Override // com.android.wm.shell.bubbles.BubbleTaskViewListener.Callback
    public final void onBackPressed() {
        BubbleBarLayerView.AnonymousClass4 anonymousClass4 = this.mListener;
        if (anonymousClass4 == null) {
            return;
        }
        int i = BubbleBarLayerView.$r8$clinit;
        BubbleBarLayerView.this.hideModalOrCollapse();
    }

    @Override // com.android.wm.shell.bubbles.BubbleTaskViewListener.Callback
    public final void onContentVisibilityChanged(boolean z) {
        TaskViewVisibilityState taskViewVisibilityState = this.mVisibilityState;
        if (taskViewVisibilityState == TaskViewVisibilityState.PENDING_INVISIBLE && !z) {
            this.mVisibilityState = TaskViewVisibilityState.PENDING_VISIBLE;
            setupTaskView();
        } else if (z && taskViewVisibilityState == TaskViewVisibilityState.PENDING_VISIBLE) {
            this.mVisibilityState = TaskViewVisibilityState.VISIBLE;
            Runnable runnable = this.mAnimateExpansion;
            if (runnable != null) {
                runnable.run();
                this.mAnimateExpansion = null;
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mMenuViewController.hideMenu(false);
        RegionSamplingHelper regionSamplingHelper = this.mRegionSamplingHelper;
        if (regionSamplingHelper != null) {
            regionSamplingHelper.stopAndDestroy();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        Context context = getContext();
        if (context instanceof HasWMComponent) {
            ((SystemUIApplication) ((HasWMComponent) context)).mInitializer.getWMComponent().inject(this);
        }
        setElevation(getResources().getDimensionPixelSize(R.dimen.bubble_elevation));
        this.mCaptionHeight = context.getResources().getDimensionPixelSize(R.dimen.bubble_bar_expanded_view_caption_height);
        this.mHandleView = (BubbleBarHandleView) findViewById(R.id.bubble_bar_handle_view);
        applyThemeAttrs();
        setClipToOutline(true);
        setOutlineProvider(new ViewOutlineProvider() { // from class: com.android.wm.shell.bubbles.bar.BubbleBarExpandedView.3
            @Override // android.view.ViewOutlineProvider
            public final void getOutline(View view, Outline outline) {
                int width = view.getWidth();
                int height = view.getHeight();
                BubbleBarExpandedView bubbleBarExpandedView = BubbleBarExpandedView.this;
                outline.setRoundRect(0, 0, width, height - bubbleBarExpandedView.mBottomClip, bubbleBarExpandedView.mCurrentCornerRadius);
            }
        });
        setOnTouchListener(new BubbleBarExpandedView$$ExternalSyntheticLambda0());
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        TaskView taskView = this.mTaskView;
        if (taskView != null) {
            taskView.layout(i, i2, i3, taskView.getMeasuredHeight() + i2);
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mTaskView != null) {
            measureChild(this.mTaskView, i, View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2), View.MeasureSpec.getMode(i2)));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x005f  */
    @Override // com.android.wm.shell.bubbles.BubbleTaskViewListener.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onTaskCreated() {
        BubbleBarLayerView bubbleBarLayerView;
        final BubbleEducationViewController bubbleEducationViewController;
        BubbleBarExpandedView bubbleBarExpandedView;
        BubbleViewProvider bubbleViewProvider;
        TaskView taskView = this.mTaskView;
        if (taskView != null) {
            taskView.setAlpha(0.0f);
        }
        BubbleBarLayerView.AnonymousClass4 anonymousClass4 = this.mListener;
        if (anonymousClass4 != null && (bubbleEducationViewController = (bubbleBarLayerView = BubbleBarLayerView.this).mEducationViewController) != null && (bubbleBarExpandedView = bubbleBarLayerView.mExpandedView) != null) {
            BubbleEducationController bubbleEducationController = (BubbleEducationController) bubbleEducationViewController.controller$delegate.getValue();
            if (Settings.Secure.getInt(bubbleEducationController.context.getContentResolver(), "force_hide_bubbles_user_education", 0) == 0 && (bubbleViewProvider = anonymousClass4.val$b) != null) {
                if (bubbleViewProvider instanceof Bubble ? ((Bubble) bubbleViewProvider).isChat() : false) {
                    if (bubbleEducationController.prefs.getBoolean("HasSeenBubblesManageOnboarding", false)) {
                        if (Settings.Secure.getInt(bubbleEducationController.context.getContentResolver(), "force_show_bubbles_user_education", 0) != 0) {
                        }
                    } else {
                        BubbleEducationViewController.hideEducation$default(bubbleEducationViewController, false);
                        BubblePopupView bubblePopupView = (BubblePopupView) LayoutInflater.from(bubbleEducationViewController.context).inflate(R.layout.bubble_bar_manage_education, (ViewGroup) bubbleBarExpandedView, false);
                        TypedArray typedArrayObtainStyledAttributes = bubblePopupView.getContext().obtainStyledAttributes(new int[]{android.R.attr.dialogCornerRadius});
                        Resources resources = bubblePopupView.getContext().getResources();
                        BubblePopupDrawable.Config config = new BubblePopupDrawable.Config(bubblePopupView.getContext().getColor(android.R.color.sliding_tab_text_color_active), typedArrayObtainStyledAttributes.getDimension(0, 0.0f), resources.getDimensionPixelSize(R.dimen.bubble_popup_padding), resources.getDimension(R.dimen.bubble_popup_arrow_width), resources.getDimension(R.dimen.bubble_popup_arrow_height), resources.getDimension(R.dimen.bubble_popup_arrow_corner_radius));
                        typedArrayObtainStyledAttributes.recycle();
                        BubblePopupDrawable bubblePopupDrawable = new BubblePopupDrawable(config);
                        bubblePopupView.getClass();
                        bubblePopupView.setBackground(bubblePopupDrawable);
                        bubblePopupView.forceLayout();
                        bubblePopupView.setAlpha(0.0f);
                        bubblePopupView.setScaleX(0.5f);
                        bubblePopupView.setScaleY(0.5f);
                        TypefaceUtils.Companion companion = TypefaceUtils.Companion;
                        TypefaceUtils.FontFamily fontFamily = TypefaceUtils.FontFamily.GSF_TITLE_MEDIUM;
                        companion.getClass();
                        bubblePopupView.setPivotY(0.0f);
                        if (!bubblePopupView.isLaidOut() || bubblePopupView.isLayoutRequested()) {
                            bubblePopupView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.wm.shell.bubbles.bar.BubbleEducationViewController$showManageEducation$lambda$14$$inlined$doOnLayout$1
                                @Override // android.view.View.OnLayoutChangeListener
                                public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                                    view.removeOnLayoutChangeListener(this);
                                    view.setPivotX(view.getWidth() / 2.0f);
                                }
                            });
                        } else {
                            bubblePopupView.setPivotX(bubblePopupView.getWidth() / 2.0f);
                        }
                        bubblePopupView.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.bubbles.bar.BubbleEducationViewController$showManageEducation$2$2
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                BubbleEducationViewController.hideEducation$default(bubbleEducationViewController, true);
                            }
                        });
                        bubbleEducationViewController.educationView = bubblePopupView;
                        bubbleEducationViewController.rootView = bubbleBarExpandedView;
                        PhysicsAnimator.Companion.getClass();
                        PhysicsAnimator companion2 = PhysicsAnimator.Companion.getInstance(bubblePopupView);
                        companion2.defaultSpring = (PhysicsAnimator.SpringConfig) bubbleEducationViewController.springConfig$delegate.getValue();
                        bubbleEducationViewController.animator = companion2;
                        bubbleBarExpandedView.addView((View) bubbleEducationViewController.scrimView$delegate.getValue());
                        bubbleBarExpandedView.addView(bubbleEducationViewController.educationView);
                        bubbleEducationViewController.animateTransition(true, new BubbleEducationViewController$$ExternalSyntheticLambda2(bubbleEducationViewController, 2));
                    }
                }
            }
        }
        if (this.mVisibilityState == TaskViewVisibilityState.PENDING_VISIBLE) {
            this.mVisibilityState = TaskViewVisibilityState.VISIBLE;
            Runnable runnable = this.mAnimateExpansion;
            if (runnable != null) {
                runnable.run();
                this.mAnimateExpansion = null;
            }
        }
    }

    @Override // com.android.wm.shell.bubbles.BubbleTaskViewListener.Callback
    public final void onTaskRemovalStarted() {
        RegionSamplingHelper regionSamplingHelper = this.mRegionSamplingHelper;
        if (regionSamplingHelper != null) {
            regionSamplingHelper.stopAndDestroy();
        }
    }

    public final void setAnimating(boolean z) {
        this.mIsAnimating = z;
        if (z) {
            updateSamplingState();
        }
        if (z) {
            return;
        }
        setContentVisibility(this.mIsContentVisible);
    }

    public final void setContentVisibility(boolean z) {
        this.mIsContentVisible = z;
        TaskView taskView = this.mTaskView;
        if (taskView == null || this.mIsAnimating) {
            return;
        }
        taskView.setAlpha(z ? 1.0f : 0.0f);
        RegionSamplingHelper regionSamplingHelper = this.mRegionSamplingHelper;
        if (regionSamplingHelper != null) {
            regionSamplingHelper.mWindowVisible = z;
            regionSamplingHelper.updateSamplingListener();
        }
        updateSamplingState();
    }

    public final void setSurfaceZOrderedOnTop(boolean z) {
        TaskView taskView = this.mTaskView;
        if (taskView == null) {
            return;
        }
        taskView.setZOrderedOnTop(z, true);
    }

    public final void setupTaskView() {
        addView(this.mTaskView, new FrameLayout.LayoutParams(-1, -1));
        this.mTaskView.setEnableSurfaceClipping(true);
        this.mTaskView.setCornerRadius(this.mCurrentCornerRadius);
        this.mTaskView.setVisibility(0);
        TaskView taskView = this.mTaskView;
        Insets insetsOf = Insets.of(0, this.mCaptionHeight, 0, 0);
        taskView.mCaptionInsets = insetsOf;
        if (insetsOf == null) {
            TaskViewTaskController taskViewTaskController = taskView.mTaskViewTaskController;
            Rect rect = taskViewTaskController.mCaptionInsets;
            if (rect == null || !rect.equals(null)) {
                taskViewTaskController.mCaptionInsets = null;
                taskViewTaskController.applyCaptionInsetsIfNeeded();
            }
        }
    }

    public final void updateSamplingState() {
        RegionSamplingHelper regionSamplingHelper = this.mRegionSamplingHelper;
        if (regionSamplingHelper == null) {
            return;
        }
        TaskView taskView = this.mTaskView;
        if (taskView == null || taskView.mTaskViewTaskController.mTaskInfo == null || this.mIsDragging || this.mIsAnimating || !this.mIsContentVisible) {
            regionSamplingHelper.stop();
        } else {
            regionSamplingHelper.start(getCaptionSampleRect());
        }
    }

    public BubbleBarExpandedView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BubbleBarExpandedView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public BubbleBarExpandedView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mSampleRect = new Rect();
        this.mLoc = new int[2];
        this.mTempBounds = new Rect();
        this.mRestingCornerRadius = 0.0f;
        this.mDraggedCornerRadius = 0.0f;
        this.mCurrentCornerRadius = 0.0f;
        this.mAnimateExpansion = null;
        this.mVisibilityState = TaskViewVisibilityState.INVISIBLE;
        this.mIsContentVisible = false;
        this.mBottomClip = 0;
    }
}
