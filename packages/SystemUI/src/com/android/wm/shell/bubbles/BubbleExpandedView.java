package com.android.wm.shell.bubbles;

import android.R;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.CornerPathEffect;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.util.IntProperty;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.QpShellRune;
import com.android.wm.shell.bubbles.Bubble;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleExpandedView;
import com.android.wm.shell.common.AlphaOptimizedButton;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.TriangleShape;
import com.android.wm.shell.shared.TypefaceUtils;
import com.android.wm.shell.taskview.TaskView;
import com.android.wm.shell.taskview.TaskViewTaskController;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class BubbleExpandedView extends LinearLayout {
    public int mBackgroundColorFloating;
    public int mBottomClip;
    public Bubble mBubble;
    public float mCornerRadius;
    public ShapeDrawable mCurrentPointer;
    public AnonymousClass5 mCurrentTaskViewListener;
    public final FrameLayout mExpandedViewContainer;
    public int[] mExpandedViewContainerLocation;
    public boolean mImeVisible;
    public boolean mIsAnimating;
    public boolean mIsClipping;
    public boolean mIsContentVisible;
    public boolean mIsOverflow;
    public ShapeDrawable mLeftPointer;
    public AlphaOptimizedButton mManageButton;
    public BubbleStackView$$ExternalSyntheticLambda6 mManageClickListener;
    public BubbleExpandedViewManager mManager;
    public boolean mNeedsNewHeight;
    public BubbleOverflowContainerView mOverflowView;
    public PendingIntent mPendingIntent;
    public CornerPathEffect mPointerEffect;
    public int mPointerHeight;
    public int mPointerMargin;
    public float mPointerOverlap;
    public final PointF mPointerPos;
    public float mPointerRadius;
    public View mPointerView;
    public int mPointerWidth;
    public BubblePositioner mPositioner;
    public ShapeDrawable mRightPointer;
    public boolean mSettingsButtonUpdated;
    public BubbleStackView mStackView;
    public int mTaskId;
    public TaskView mTaskView;
    public final AnonymousClass5 mTaskViewListener;
    public int mTopClip;
    public ShapeDrawable mTopPointer;
    public boolean mUsingMaxHeight;
    public static final AnonymousClass1 BOTTOM_CLIP_PROPERTY = new IntProperty("bottomClip") { // from class: com.android.wm.shell.bubbles.BubbleExpandedView.1
        @Override // android.util.Property
        public final Integer get(Object obj) {
            return Integer.valueOf(((BubbleExpandedView) obj).mBottomClip);
        }

        @Override // android.util.IntProperty
        public final void setValue(Object obj, int i) {
            BubbleExpandedView bubbleExpandedView = (BubbleExpandedView) obj;
            bubbleExpandedView.mBottomClip = i;
            bubbleExpandedView.onContainerClipUpdate();
        }
    };
    public static final AnonymousClass2 CONTENT_ALPHA = new FloatProperty("contentAlpha") { // from class: com.android.wm.shell.bubbles.BubbleExpandedView.2
        @Override // android.util.Property
        public final Float get(Object obj) {
            float alpha;
            BubbleExpandedView bubbleExpandedView = (BubbleExpandedView) obj;
            if (bubbleExpandedView.mIsOverflow) {
                alpha = bubbleExpandedView.mOverflowView.getAlpha();
            } else {
                TaskView taskView = bubbleExpandedView.mTaskView;
                alpha = taskView != null ? taskView.getAlpha() : 1.0f;
            }
            return Float.valueOf(alpha);
        }

        @Override // android.util.FloatProperty
        public final void setValue(Object obj, float f) {
            ((BubbleExpandedView) obj).setContentAlpha(f);
        }
    };
    public static final AnonymousClass3 BACKGROUND_ALPHA = new FloatProperty("backgroundAlpha") { // from class: com.android.wm.shell.bubbles.BubbleExpandedView.3
        @Override // android.util.Property
        public final Float get(Object obj) {
            return Float.valueOf(((BubbleExpandedView) obj).getAlpha());
        }

        @Override // android.util.FloatProperty
        public final void setValue(Object obj, float f) {
            BubbleExpandedView bubbleExpandedView = (BubbleExpandedView) obj;
            bubbleExpandedView.mPointerView.setAlpha(f);
            bubbleExpandedView.setAlpha(f);
        }
    };
    public static final AnonymousClass4 MANAGE_BUTTON_ALPHA = new FloatProperty("manageButtonAlpha") { // from class: com.android.wm.shell.bubbles.BubbleExpandedView.4
        @Override // android.util.Property
        public final Float get(Object obj) {
            return Float.valueOf(((BubbleExpandedView) obj).mManageButton.getAlpha());
        }

        @Override // android.util.FloatProperty
        public final void setValue(Object obj, float f) {
            ((BubbleExpandedView) obj).mManageButton.setAlpha(f);
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.bubbles.BubbleExpandedView$5, reason: invalid class name */
    public class AnonymousClass5 implements TaskView.Listener {
        public boolean mInitialized = false;
        public boolean mDestroyed = false;

        public AnonymousClass5() {
        }

        @Override // com.android.wm.shell.taskview.TaskView.Listener
        public final void onBackPressedOnTaskRoot(int i) {
            BubbleStackView bubbleStackView;
            boolean z;
            BubbleExpandedView bubbleExpandedView = BubbleExpandedView.this;
            if (bubbleExpandedView.mTaskId == i && (z = (bubbleStackView = bubbleExpandedView.mStackView).mIsExpanded) && z) {
                if (bubbleStackView.isManageEduVisible()) {
                    bubbleStackView.mManageEduView.hide();
                } else {
                    bubbleStackView.mBubbleData.setExpanded(false);
                }
            }
        }

        @Override // com.android.wm.shell.taskview.TaskView.Listener
        public final void onInitialized() {
            boolean z = this.mDestroyed;
            BubbleExpandedView bubbleExpandedView = BubbleExpandedView.this;
            if (!z && !this.mInitialized) {
                final ActivityOptions makeCustomAnimation = ActivityOptions.makeCustomAnimation(bubbleExpandedView.getContext(), 0, 0);
                bubbleExpandedView.post(new Runnable() { // from class: com.android.wm.shell.bubbles.BubbleExpandedView$5$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        boolean z2;
                        Context context;
                        BubbleExpandedView.AnonymousClass5 anonymousClass5 = BubbleExpandedView.AnonymousClass5.this;
                        ActivityOptions activityOptions = makeCustomAnimation;
                        anonymousClass5.getClass();
                        boolean z3 = ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0];
                        BubbleExpandedView bubbleExpandedView2 = BubbleExpandedView.this;
                        if (z3) {
                            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, 1533690492202595624L, 0, String.valueOf(bubbleExpandedView2.getBubbleKey()));
                        }
                        try {
                            Rect rect = new Rect();
                            bubbleExpandedView2.mTaskView.getBoundsOnScreen(rect);
                            activityOptions.setTaskAlwaysOnTop(true);
                            activityOptions.setPendingIntentBackgroundActivityStartMode(3);
                            if (bubbleExpandedView2.mBubble.hasMetadataShortcutId()) {
                                z2 = true;
                            } else {
                                Bubble.BubbleType bubbleType = bubbleExpandedView2.mBubble.mType;
                                Bubble.BubbleType bubbleType2 = Bubble.BubbleType.TYPE_CHAT;
                                z2 = false;
                            }
                            Bubble bubble = bubbleExpandedView2.mBubble;
                            if (!(bubble.mType == Bubble.BubbleType.TYPE_APP) && !bubble.isNote()) {
                                if (bubbleExpandedView2.mIsOverflow || !z2) {
                                    activityOptions.setLaunchedFromBubble(true);
                                    Bubble bubble2 = bubbleExpandedView2.mBubble;
                                    if (bubble2 != null) {
                                        bubble2.mPendingIntentActive = true;
                                    }
                                    Intent intent = new Intent();
                                    intent.addFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
                                    intent.addFlags(134217728);
                                    bubbleExpandedView2.mTaskView.startActivity(bubbleExpandedView2.mPendingIntent, intent, activityOptions, rect);
                                    return;
                                }
                                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[1]) {
                                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_BUBBLES, -7259845944232954472L, 0, String.valueOf(bubbleExpandedView2.getBubbleKey()));
                                }
                                if (bubbleExpandedView2.mBubble.isChat()) {
                                    activityOptions.setLaunchedFromBubble(true);
                                    activityOptions.setApplyActivityFlagsForBubbles(true);
                                } else {
                                    activityOptions.setApplyMultipleTaskFlagForShortcut(true);
                                }
                                TaskView taskView = bubbleExpandedView2.mTaskView;
                                taskView.mTaskViewController.startShortcutActivity(taskView.mTaskViewTaskController, bubbleExpandedView2.mBubble.mShortcutInfo, activityOptions, rect);
                                return;
                            }
                            context = ((LinearLayout) bubbleExpandedView2).mContext;
                            bubbleExpandedView2.mTaskView.startActivity(PendingIntent.getActivity(context.createContextAsUser(bubbleExpandedView2.mBubble.mUser, 4), 0, bubbleExpandedView2.mBubble.mIntent, 167772160, null), new Intent(), activityOptions, rect);
                        } catch (RuntimeException e) {
                            Log.w("Bubbles", "Exception while displaying bubble: " + bubbleExpandedView2.getBubbleKey() + ", " + e.getMessage() + "; removing bubble");
                            ((BubbleExpandedViewManager$Companion$fromBubbleController$1) bubbleExpandedView2.mManager).$controller.removeBubble(10, bubbleExpandedView2.getBubbleKey());
                        }
                    }
                });
                this.mInitialized = true;
            } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                boolean z2 = this.mInitialized;
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, 3773084945322816131L, 15, Boolean.valueOf(z), Boolean.valueOf(z2), String.valueOf(bubbleExpandedView.getBubbleKey()));
            }
        }

        @Override // com.android.wm.shell.taskview.TaskView.Listener
        public final void onReleased() {
            this.mDestroyed = true;
        }

        @Override // com.android.wm.shell.taskview.TaskView.Listener
        public final void onTaskCreated(int i, ComponentName componentName) {
            boolean z = ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0];
            BubbleExpandedView bubbleExpandedView = BubbleExpandedView.this;
            if (z) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -3890427939905127709L, 1, Long.valueOf(i), String.valueOf(bubbleExpandedView.getBubbleKey()));
            }
            bubbleExpandedView.mTaskId = i;
            Bubble bubble = bubbleExpandedView.mBubble;
            if (bubble != null && bubble.isNote()) {
                BubbleExpandedViewManager bubbleExpandedViewManager = bubbleExpandedView.mManager;
                String str = bubbleExpandedView.mBubble.mKey;
                int i2 = bubbleExpandedView.mTaskId;
                BubbleController.BubblesImpl.CachedState cachedState = ((BubbleExpandedViewManager$Companion$fromBubbleController$1) bubbleExpandedViewManager).$controller.mImpl.mCachedState;
                synchronized (cachedState) {
                    cachedState.mNoteBubbleTaskIds.put(str, Integer.valueOf(i2));
                }
            }
            bubbleExpandedView.setContentVisibility(true);
        }

        @Override // com.android.wm.shell.taskview.TaskView.Listener
        public final void onTaskRemovalStarted(int i) {
            boolean z = ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0];
            BubbleExpandedView bubbleExpandedView = BubbleExpandedView.this;
            if (z) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -1943811537155114558L, 1, Long.valueOf(i), String.valueOf(bubbleExpandedView.getBubbleKey()));
            }
            Bubble bubble = bubbleExpandedView.mBubble;
            if (bubble != null) {
                ((BubbleExpandedViewManager$Companion$fromBubbleController$1) bubbleExpandedView.mManager).$controller.removeBubble(3, bubble.mKey);
            }
            TaskView taskView = bubbleExpandedView.mTaskView;
            if (taskView != null) {
                taskView.getHolder().removeCallback(taskView);
                taskView.mTaskViewTaskController.performRelease();
                bubbleExpandedView.removeView(bubbleExpandedView.mTaskView);
                bubbleExpandedView.mTaskView = null;
            }
        }

        @Override // com.android.wm.shell.taskview.TaskView.Listener
        public final void onTaskVisibilityChanged(int i, boolean z) {
            boolean z2 = ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0];
            BubbleExpandedView bubbleExpandedView = BubbleExpandedView.this;
            if (z2) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -1456133808905439828L, 19, Boolean.valueOf(z), String.valueOf(bubbleExpandedView.getBubbleKey()), Long.valueOf(i));
            }
            bubbleExpandedView.setContentVisibility(z);
        }
    }

    public static void $r8$lambda$ccTaI9r7P2VOoMubLXq7uktr0E8(BubbleExpandedView bubbleExpandedView, boolean z, boolean z2, float f, boolean z3, float f2, boolean z4) {
        int width;
        int i;
        bubbleExpandedView.mCurrentPointer = z ? z2 ? bubbleExpandedView.mLeftPointer : bubbleExpandedView.mRightPointer : bubbleExpandedView.mTopPointer;
        bubbleExpandedView.updatePointerViewIfExists();
        if (z) {
            PointF pointF = bubbleExpandedView.mPointerPos;
            pointF.y = f - (bubbleExpandedView.mPointerWidth / 2.0f);
            if (z2) {
                width = -bubbleExpandedView.mPointerHeight;
                i = bubbleExpandedView.mPointerMargin;
            } else {
                width = bubbleExpandedView.getWidth() - ((LinearLayout) bubbleExpandedView).mPaddingRight;
                i = bubbleExpandedView.mPointerHeight;
            }
            pointF.x = width - i;
        } else {
            PointF pointF2 = bubbleExpandedView.mPointerPos;
            pointF2.y = bubbleExpandedView.mPointerOverlap;
            if (z3) {
                BubblePositioner bubblePositioner = bubbleExpandedView.mPositioner;
                float f3 = ((bubblePositioner.mBubbleSize / 2.0f) + f2) - bubbleExpandedView.mPointerWidth;
                pointF2.x = f3;
                if (QpShellRune.NOTI_BUBBLE_STYLE_TABLET) {
                    pointF2.x = (bubbleExpandedView.mPointerWidth / 2.0f) + (f3 - bubblePositioner.getTabletSidePadding());
                }
            } else {
                BubblePositioner bubblePositioner2 = bubbleExpandedView.mPositioner;
                float f4 = ((bubblePositioner2.mBubbleSize / 2.0f) + f2) - bubbleExpandedView.mPointerWidth;
                pointF2.x = f4;
                if (QpShellRune.NOTI_BUBBLE_STYLE_TABLET) {
                    pointF2.x = (bubbleExpandedView.mPointerWidth / 2.0f) + (f4 - bubblePositioner2.getTabletSidePadding());
                }
            }
        }
        if (z4) {
            bubbleExpandedView.mPointerView.animate().translationX(bubbleExpandedView.mPointerPos.x).translationY(bubbleExpandedView.mPointerPos.y).start();
            return;
        }
        bubbleExpandedView.mPointerView.setTranslationY(bubbleExpandedView.mPointerPos.y);
        bubbleExpandedView.mPointerView.setTranslationX(bubbleExpandedView.mPointerPos.x);
        bubbleExpandedView.mPointerView.setVisibility(0);
    }

    public BubbleExpandedView(Context context) {
        this(context, null);
    }

    public final void applyThemeAttrs() {
        TypedArray obtainStyledAttributes = ((LinearLayout) this).mContext.obtainStyledAttributes(new int[]{R.attr.dialogCornerRadius});
        this.mCornerRadius = ScreenDecorationsUtils.supportsRoundedCornersOnWindows(((LinearLayout) this).mContext.getResources()) ? getContext().getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sec_noti_bubble_expand_view_radius) : 0.0f;
        int color = ((LinearLayout) this).mContext.getColor(R.color.side_fps_toast_background);
        this.mBackgroundColorFloating = color;
        this.mExpandedViewContainer.setBackgroundColor(color);
        int color2 = ((LinearLayout) this).mContext.getColor(R.color.sliding_tab_text_color_shadow);
        obtainStyledAttributes.recycle();
        AlphaOptimizedButton alphaOptimizedButton = this.mManageButton;
        if (alphaOptimizedButton != null) {
            alphaOptimizedButton.getBackground().setColorFilter(color2, PorterDuff.Mode.SRC_IN);
        }
        TaskView taskView = this.mTaskView;
        if (taskView != null) {
            taskView.setCornerRadius(this.mCornerRadius);
        }
        updatePointerViewIfExists();
        updateManageButtonIfExists();
    }

    public String getBubbleKey() {
        Bubble bubble = this.mBubble;
        if (bubble != null) {
            return bubble.mKey;
        }
        if (this.mIsOverflow) {
            return "Overflow";
        }
        return null;
    }

    public final int getContentHeight() {
        int height;
        int i;
        if (this.mIsOverflow) {
            height = this.mOverflowView.getHeight() - this.mTopClip;
            i = this.mBottomClip;
        } else {
            TaskView taskView = this.mTaskView;
            if (taskView == null) {
                return 0;
            }
            height = taskView.getHeight() - this.mTopClip;
            i = this.mBottomClip;
        }
        return height - i;
    }

    public BubbleOverflowContainerView getOverflow() {
        return this.mOverflowView;
    }

    public final void initialize(BubbleExpandedViewManager bubbleExpandedViewManager, BubbleStackView bubbleStackView, BubblePositioner bubblePositioner, boolean z, BubbleTaskView bubbleTaskView) {
        this.mManager = bubbleExpandedViewManager;
        this.mStackView = bubbleStackView;
        this.mIsOverflow = z;
        this.mPositioner = bubblePositioner;
        if (z) {
            BubbleOverflowContainerView bubbleOverflowContainerView = (BubbleOverflowContainerView) LayoutInflater.from(getContext()).inflate(com.android.systemui.R.layout.bubble_overflow_container, (ViewGroup) null);
            this.mOverflowView = bubbleOverflowContainerView;
            bubbleOverflowContainerView.mExpandedViewManager = bubbleExpandedViewManager;
            bubbleOverflowContainerView.mPositioner = bubblePositioner;
            this.mExpandedViewContainer.addView(this.mOverflowView, new FrameLayout.LayoutParams(-1, -1));
            this.mExpandedViewContainer.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
            bringChildToFront(this.mOverflowView);
            this.mManageButton.setVisibility(8);
            return;
        }
        TaskView taskView = bubbleTaskView.taskView;
        this.mTaskView = taskView;
        taskView.mCaptionInsets = null;
        TaskViewTaskController taskViewTaskController = taskView.mTaskViewTaskController;
        Rect rect = taskViewTaskController.mCaptionInsets;
        if (rect == null || !rect.equals(null)) {
            taskViewTaskController.mCaptionInsets = null;
            taskViewTaskController.applyCaptionInsetsIfNeeded();
        }
        AnonymousClass5 anonymousClass5 = this.mTaskViewListener;
        this.mCurrentTaskViewListener = anonymousClass5;
        bubbleTaskView.delegateListener = anonymousClass5;
        boolean isStackOnLeft = this.mPositioner.isStackOnLeft(this.mStackView.mStackAnimationController.mStackPosition);
        BubblePositioner bubblePositioner2 = this.mPositioner;
        int[] expandedViewContainerPadding = bubblePositioner2.getExpandedViewContainerPadding(isStackOnLeft, false);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(((bubblePositioner2.mScreenRect.width() - expandedViewContainerPadding[0]) - expandedViewContainerPadding[2]) - (bubblePositioner2.showBubblesVertically() ? bubblePositioner2.mPointerHeight - bubblePositioner2.mPointerOverlap : 0), -1);
        if (this.mTaskView.getParent() != null) {
            ((ViewGroup) this.mTaskView.getParent()).removeView(this.mTaskView);
        }
        this.mExpandedViewContainer.addView(this.mTaskView, layoutParams);
        bringChildToFront(this.mTaskView);
        if (bubbleTaskView.isCreated) {
            this.mCurrentTaskViewListener.onTaskCreated(bubbleTaskView.taskId, bubbleTaskView.componentName);
        }
    }

    public final void movePointerBy(float f) {
        this.mPointerView.setTranslationX(this.mPointerPos.x + f);
        this.mPointerView.setTranslationY(this.mPointerPos.y + 0.0f);
    }

    public final void onContainerClipUpdate() {
        if (this.mTopClip == 0 && this.mBottomClip == 0) {
            if (this.mIsClipping) {
                this.mIsClipping = false;
                TaskView taskView = this.mTaskView;
                if (taskView != null) {
                    taskView.setClipBounds(null);
                    this.mTaskView.setEnableSurfaceClipping(false);
                }
                this.mExpandedViewContainer.invalidateOutline();
                return;
            }
            return;
        }
        if (!this.mIsClipping) {
            this.mIsClipping = true;
            TaskView taskView2 = this.mTaskView;
            if (taskView2 != null) {
                taskView2.setEnableSurfaceClipping(true);
            }
        }
        this.mExpandedViewContainer.invalidateOutline();
        if (this.mTaskView != null) {
            this.mTaskView.setClipBounds(new Rect(0, this.mTopClip, this.mTaskView.getWidth(), this.mTaskView.getHeight() - this.mBottomClip));
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mImeVisible = false;
        this.mNeedsNewHeight = false;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mManageButton = (AlphaOptimizedButton) LayoutInflater.from(getContext()).inflate(com.android.systemui.R.layout.bubble_manage_button, (ViewGroup) this, false);
        updateDimensions();
        View findViewById = findViewById(com.android.systemui.R.id.pointer_view);
        this.mPointerView = findViewById;
        this.mCurrentPointer = this.mTopPointer;
        findViewById.setVisibility(4);
        setContentVisibility(false);
        this.mExpandedViewContainer.setOutlineProvider(new ViewOutlineProvider() { // from class: com.android.wm.shell.bubbles.BubbleExpandedView.6
            @Override // android.view.ViewOutlineProvider
            public final void getOutline(View view, Outline outline) {
                BubbleExpandedView bubbleExpandedView = BubbleExpandedView.this;
                AnonymousClass1 anonymousClass1 = BubbleExpandedView.BOTTOM_CLIP_PROPERTY;
                bubbleExpandedView.getClass();
                int i = BubbleExpandedView.this.mTopClip;
                int width = view.getWidth();
                BubbleExpandedView.this.getClass();
                outline.setRoundRect(new Rect(0, i, width, view.getHeight() - BubbleExpandedView.this.mBottomClip), BubbleExpandedView.this.mCornerRadius);
            }
        });
        this.mExpandedViewContainer.setClipToOutline(true);
        this.mExpandedViewContainer.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        addView(this.mExpandedViewContainer);
        bringChildToFront(this.mManageButton);
        BubbleStackView$$ExternalSyntheticLambda6 bubbleStackView$$ExternalSyntheticLambda6 = this.mManageClickListener;
        this.mManageClickListener = bubbleStackView$$ExternalSyntheticLambda6;
        this.mManageButton.setOnClickListener(bubbleStackView$$ExternalSyntheticLambda6);
        this.mSettingsButtonUpdated = false;
        applyThemeAttrs();
        setClipToPadding(false);
        setOnTouchListener(new View.OnTouchListener() { // from class: com.android.wm.shell.bubbles.BubbleExpandedView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                BubbleExpandedView bubbleExpandedView = BubbleExpandedView.this;
                if (bubbleExpandedView.mTaskView == null) {
                    return false;
                }
                Rect rect = new Rect();
                bubbleExpandedView.mTaskView.getBoundsOnScreen(rect);
                if (motionEvent.getRawY() < rect.top || motionEvent.getRawY() > rect.bottom) {
                    return false;
                }
                return motionEvent.getRawX() < ((float) rect.left) || motionEvent.getRawX() > ((float) rect.right);
            }
        });
        setLayoutDirection(3);
    }

    public final void setAnimating(boolean z) {
        this.mIsAnimating = z;
        if (z) {
            return;
        }
        setContentVisibility(this.mIsContentVisible);
    }

    public final void setContentAlpha(float f) {
        if (this.mIsOverflow) {
            this.mOverflowView.setAlpha(f);
            return;
        }
        TaskView taskView = this.mTaskView;
        if (taskView != null) {
            taskView.setAlpha(f);
        }
    }

    public final void setContentVisibility(boolean z) {
        this.mIsContentVisible = z;
        TaskView taskView = this.mTaskView;
        if (taskView == null || this.mIsAnimating) {
            return;
        }
        taskView.setAlpha(z ? 1.0f : 0.0f);
        this.mPointerView.setAlpha(z ? 1.0f : 0.0f);
    }

    public final void setPointerPosition(final float f, final boolean z, final boolean z2) {
        final boolean z3 = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(((LinearLayout) this).mContext) == 1;
        final boolean showBubblesVertically = this.mPositioner.showBubblesVertically();
        setPadding(showBubblesVertically ? this.mPointerMargin + this.mPointerHeight : 0, (int) (showBubblesVertically ? 0.0f : this.mPointerHeight - this.mPointerOverlap), showBubblesVertically ? this.mPointerMargin + this.mPointerHeight : 0, 0);
        float pointerPosition = this.mPositioner.getPointerPosition(f);
        if (this.mPositioner.showBubblesVertically()) {
            pointerPosition -= this.mPositioner.getExpandedViewY(this.mBubble, f);
        }
        final float f2 = pointerPosition;
        post(new Runnable() { // from class: com.android.wm.shell.bubbles.BubbleExpandedView$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                BubbleExpandedView.$r8$lambda$ccTaI9r7P2VOoMubLXq7uktr0E8(BubbleExpandedView.this, showBubblesVertically, z, f2, z3, f, z2);
            }
        });
    }

    public final void setSurfaceZOrderedOnTop(boolean z) {
        TaskView taskView = this.mTaskView;
        if (taskView == null) {
            return;
        }
        taskView.setZOrderedOnTop(z, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00af  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void update(com.android.wm.shell.bubbles.Bubble r10) {
        /*
            r9 = this;
            com.android.wm.shell.bubbles.BubbleStackView r0 = r9.mStackView
            java.lang.String r1 = "Bubbles"
            if (r0 != 0) goto L18
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r0 = "Stack is null for bubble: "
            r9.<init>(r0)
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            android.util.Log.w(r1, r9)
            return
        L18:
            r0 = 1
            r2 = 0
            com.android.wm.shell.bubbles.Bubble r3 = r9.mBubble
            if (r3 == 0) goto L31
            android.app.PendingIntent r3 = r9.mPendingIntent
            if (r3 == 0) goto L24
            r3 = r0
            goto L25
        L24:
            r3 = r2
        L25:
            android.app.PendingIntent r4 = r10.mPendingIntent
            if (r4 == 0) goto L2b
            r4 = r0
            goto L2c
        L2b:
            r4 = r2
        L2c:
            if (r3 == r4) goto L2f
            goto L31
        L2f:
            r3 = r2
            goto L32
        L31:
            r3 = r0
        L32:
            if (r10 == 0) goto L43
            com.android.wm.shell.bubbles.Bubble r4 = r9.mBubble
            if (r4 == 0) goto L43
            java.lang.String r5 = r10.mKey
            java.lang.String r4 = r4.mKey
            boolean r4 = r5.equals(r4)
            if (r4 == 0) goto L43
            goto L44
        L43:
            r0 = r2
        L44:
            boolean[] r4 = com.android.internal.protolog.ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled
            boolean r4 = r4[r2]
            if (r4 == 0) goto L68
            java.lang.String r4 = r10.mKey
            java.lang.String r4 = java.lang.String.valueOf(r4)
            com.android.wm.shell.protolog.ShellProtoLogGroup r5 = com.android.wm.shell.protolog.ShellProtoLogGroup.WM_SHELL_BUBBLES
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r3)
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r0)
            java.lang.Object[] r4 = new java.lang.Object[]{r4, r6, r7}
            r6 = 7607960991883866700(0x6994e836a244324c, double:4.0008344980493066E200)
            r8 = 60
            com.android.internal.protolog.ProtoLogImpl_1771455215.d(r5, r6, r8, r4)
        L68:
            if (r3 != 0) goto L8b
            if (r0 == 0) goto L6d
            goto L8b
        L6d:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r0 = "Trying to update entry with different key, new bubble: "
            r9.<init>(r0)
            java.lang.String r0 = r10.mKey
            r9.append(r0)
            java.lang.String r0 = " old bubble: "
            r9.append(r0)
            java.lang.String r10 = r10.mKey
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            android.util.Log.w(r1, r9)
            return
        L8b:
            r9.mBubble = r10
            com.android.wm.shell.common.AlphaOptimizedButton r0 = r9.mManageButton
            android.content.res.Resources r1 = r9.getResources()
            java.lang.String r10 = r10.mAppName
            java.lang.Object[] r10 = new java.lang.Object[]{r10}
            r4 = 2131952495(0x7f13036f, float:1.9541434E38)
            java.lang.String r10 = r1.getString(r4, r10)
            r0.setContentDescription(r10)
            com.android.wm.shell.common.AlphaOptimizedButton r10 = r9.mManageButton
            com.android.wm.shell.bubbles.BubbleExpandedView$8 r0 = new com.android.wm.shell.bubbles.BubbleExpandedView$8
            r0.<init>()
            r10.setAccessibilityDelegate(r0)
            if (r3 == 0) goto Lc9
            com.android.wm.shell.bubbles.Bubble r10 = r9.mBubble
            android.app.PendingIntent r0 = r10.mPendingIntent
            r9.mPendingIntent = r0
            if (r0 != 0) goto Lbd
            boolean r10 = r10.hasMetadataShortcutId()
            if (r10 == 0) goto Lc9
        Lbd:
            com.android.wm.shell.taskview.TaskView r10 = r9.mTaskView
            if (r10 == 0) goto Lc9
            r9.setContentVisibility(r2)
            com.android.wm.shell.taskview.TaskView r10 = r9.mTaskView
            r10.setVisibility(r2)
        Lc9:
            r9.applyThemeAttrs()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.bubbles.BubbleExpandedView.update(com.android.wm.shell.bubbles.Bubble):void");
    }

    public final void updateDimensions() {
        Resources resources = getResources();
        updateFontSize();
        this.mPointerMargin = resources.getDimensionPixelSize(com.android.systemui.R.dimen.bubble_pointer_margin);
        this.mPointerWidth = resources.getDimensionPixelSize(com.android.systemui.R.dimen.bubble_pointer_width);
        this.mPointerHeight = resources.getDimensionPixelSize(com.android.systemui.R.dimen.bubble_pointer_height);
        this.mPointerRadius = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.bubble_pointer_radius);
        this.mPointerEffect = new CornerPathEffect(this.mPointerRadius);
        this.mPointerOverlap = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.bubble_pointer_overlap);
        float f = this.mPointerWidth;
        float f2 = this.mPointerHeight;
        int i = TriangleShape.$r8$clinit;
        Path path = new Path();
        path.moveTo(0.0f, f2);
        path.lineTo(f, f2);
        path.lineTo(f / 2.0f, 0.0f);
        path.close();
        this.mTopPointer = new ShapeDrawable(new TriangleShape(path, f, f2));
        this.mLeftPointer = new ShapeDrawable(TriangleShape.createHorizontal(this.mPointerWidth, this.mPointerHeight, true));
        this.mRightPointer = new ShapeDrawable(TriangleShape.createHorizontal(this.mPointerWidth, this.mPointerHeight, false));
        updatePointerViewIfExists();
        updateManageButtonIfExists();
    }

    public final void updateFontSize() {
        float dimensionPixelSize = ((LinearLayout) this).mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sec_noti_bubble_settings_text_size);
        AlphaOptimizedButton alphaOptimizedButton = this.mManageButton;
        if (alphaOptimizedButton != null) {
            alphaOptimizedButton.setTextSize(0, dimensionPixelSize);
        }
        BubbleOverflowContainerView bubbleOverflowContainerView = this.mOverflowView;
        if (bubbleOverflowContainerView != null) {
            bubbleOverflowContainerView.updateFontSize();
        }
    }

    public final void updateHeight() {
        if (this.mExpandedViewContainerLocation == null) {
            return;
        }
        Bubble bubble = this.mBubble;
        if ((bubble == null || this.mTaskView == null) && !this.mIsOverflow) {
            return;
        }
        float expandedViewHeight = this.mPositioner.getExpandedViewHeight(bubble);
        int maxExpandedViewHeight = this.mPositioner.getMaxExpandedViewHeight(this.mIsOverflow);
        float min = expandedViewHeight == -1.0f ? maxExpandedViewHeight : Math.min(expandedViewHeight, maxExpandedViewHeight);
        this.mUsingMaxHeight = min == ((float) maxExpandedViewHeight);
        FrameLayout.LayoutParams layoutParams = this.mIsOverflow ? (FrameLayout.LayoutParams) this.mOverflowView.getLayoutParams() : (FrameLayout.LayoutParams) this.mTaskView.getLayoutParams();
        this.mNeedsNewHeight = ((float) layoutParams.height) != min;
        if (this.mImeVisible) {
            return;
        }
        layoutParams.height = (int) min;
        if (this.mIsOverflow) {
            this.mOverflowView.setLayoutParams(layoutParams);
        } else {
            this.mTaskView.setLayoutParams(layoutParams);
        }
        this.mNeedsNewHeight = false;
    }

    public final void updateLocale() {
        AlphaOptimizedButton alphaOptimizedButton = this.mManageButton;
        if (alphaOptimizedButton != null) {
            alphaOptimizedButton.setText(((LinearLayout) this).mContext.getString(com.android.systemui.R.string.setting_bubbles_text));
        }
        BubbleOverflowContainerView bubbleOverflowContainerView = this.mOverflowView;
        if (bubbleOverflowContainerView != null) {
            bubbleOverflowContainerView.updateLocale();
        }
    }

    public final void updateManageButtonIfExists() {
        int i = 0;
        AlphaOptimizedButton alphaOptimizedButton = this.mManageButton;
        if (alphaOptimizedButton == null) {
            return;
        }
        if (!this.mSettingsButtonUpdated) {
            int visibility = alphaOptimizedButton.getVisibility();
            removeView(this.mManageButton);
            AlphaOptimizedButton alphaOptimizedButton2 = (AlphaOptimizedButton) LayoutInflater.from(new ContextThemeWrapper(getContext(), R.style.Theme.DeviceDefault.DayNight)).inflate(com.android.systemui.R.layout.bubble_manage_button, (ViewGroup) this, false);
            this.mManageButton = alphaOptimizedButton2;
            addView(alphaOptimizedButton2);
            TypefaceUtils.FontFamily fontFamily = TypefaceUtils.FontFamily.GSF_TITLE_MEDIUM;
            TypefaceUtils.setTypeface();
            this.mSettingsButtonUpdated = true;
            BubbleStackView$$ExternalSyntheticLambda6 bubbleStackView$$ExternalSyntheticLambda6 = this.mManageClickListener;
            if (bubbleStackView$$ExternalSyntheticLambda6 != null) {
                this.mManageButton.setOnClickListener(bubbleStackView$$ExternalSyntheticLambda6);
            }
            BubblePositioner bubblePositioner = this.mPositioner;
            if (bubblePositioner != null) {
                this.mManageButton.setVisibility((bubblePositioner.mDeviceConfig.isLandscape || this.mIsOverflow) ? 8 : 0);
            } else {
                this.mManageButton.setVisibility(visibility);
            }
        }
        post(new BubbleExpandedView$$ExternalSyntheticLambda1(this, i));
    }

    public final void updatePointerViewIfExists() {
        ShapeDrawable shapeDrawable;
        View view = this.mPointerView;
        if (view == null) {
            return;
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        BubblePositioner bubblePositioner = this.mPositioner;
        if ((bubblePositioner != null && bubblePositioner.showBubblesVertically()) || (shapeDrawable = this.mCurrentPointer) == this.mLeftPointer || shapeDrawable == this.mRightPointer) {
            layoutParams.width = this.mPointerHeight;
            layoutParams.height = this.mPointerWidth;
            layoutParams.bottomMargin = 0;
            layoutParams.topMargin = 0;
        } else {
            layoutParams.width = this.mPointerWidth;
            layoutParams.height = this.mPointerHeight;
            int i = this.mPointerMargin;
            layoutParams.bottomMargin = i;
            layoutParams.topMargin = i;
        }
        this.mCurrentPointer.setTint(this.mBackgroundColorFloating);
        Paint paint = this.mCurrentPointer.getPaint();
        paint.setColor(this.mBackgroundColorFloating);
        paint.setPathEffect(this.mPointerEffect);
        this.mPointerView.setLayoutParams(layoutParams);
    }

    public BubbleExpandedView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BubbleExpandedView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public BubbleExpandedView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTaskId = -1;
        this.mIsContentVisible = false;
        this.mIsAnimating = false;
        this.mPointerPos = new PointF();
        this.mCornerRadius = 0.0f;
        this.mTopClip = 0;
        this.mBottomClip = 0;
        this.mSettingsButtonUpdated = false;
        this.mExpandedViewContainer = new FrameLayout(getContext());
        this.mTaskViewListener = new AnonymousClass5();
    }
}
