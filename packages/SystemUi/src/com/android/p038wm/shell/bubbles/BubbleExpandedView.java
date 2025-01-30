package com.android.p038wm.shell.bubbles;

import android.R;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.PendingIntent;
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
import android.os.RemoteException;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.util.IntProperty;
import android.util.Log;
import android.util.Slog;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.p038wm.shell.QpShellRune;
import com.android.p038wm.shell.bubbles.BubbleController;
import com.android.p038wm.shell.bubbles.BubbleExpandedView;
import com.android.p038wm.shell.common.AlphaOptimizedButton;
import com.android.p038wm.shell.common.TriangleShape;
import com.android.p038wm.shell.taskview.TaskView;
import com.android.p038wm.shell.taskview.TaskViewTaskController;
import com.android.p038wm.shell.taskview.TaskViewTaskController$$ExternalSyntheticLambda0;
import com.android.p038wm.shell.transition.Transitions;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class BubbleExpandedView extends LinearLayout {
    public static final C37981 BOTTOM_CLIP_PROPERTY = new IntProperty("bottomClip") { // from class: com.android.wm.shell.bubbles.BubbleExpandedView.1
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
    public int mBackgroundColorFloating;
    public int mBottomClip;
    public Bubble mBubble;
    public BubbleController mController;
    public float mCornerRadius;
    public ShapeDrawable mCurrentPointer;
    public final FrameLayout mExpandedViewContainer;
    public int[] mExpandedViewContainerLocation;
    public boolean mIsAnimating;
    public boolean mIsClipping;
    public boolean mIsContentVisible;
    public boolean mIsOverflow;
    public ShapeDrawable mLeftPointer;
    public AlphaOptimizedButton mManageButton;
    public View.OnClickListener mManageClickListener;
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
    public final C38025 mTaskViewListener;
    public TaskViewTaskController mTaskViewTaskController;
    public int mTopClip;
    public ShapeDrawable mTopPointer;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.bubbles.BubbleExpandedView$5 */
    public final class C38025 implements TaskView.Listener {
        public boolean mInitialized = false;
        public boolean mDestroyed = false;

        public C38025() {
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
            StringBuilder sb = new StringBuilder("onInitialized: destroyed=");
            sb.append(this.mDestroyed);
            sb.append(" initialized=");
            sb.append(this.mInitialized);
            sb.append(" bubble=");
            BubbleExpandedView bubbleExpandedView = BubbleExpandedView.this;
            sb.append(bubbleExpandedView.getBubbleKey());
            Log.d("Bubbles", sb.toString());
            if (this.mDestroyed || this.mInitialized) {
                return;
            }
            final ActivityOptions makeCustomAnimation = ActivityOptions.makeCustomAnimation(bubbleExpandedView.getContext(), 0, 0);
            bubbleExpandedView.post(new Runnable() { // from class: com.android.wm.shell.bubbles.BubbleExpandedView$5$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Context context;
                    BubbleExpandedView.C38025 c38025 = BubbleExpandedView.C38025.this;
                    ActivityOptions activityOptions = makeCustomAnimation;
                    StringBuilder sb2 = new StringBuilder("onInitialized: calling startActivity, bubble=");
                    BubbleExpandedView bubbleExpandedView2 = BubbleExpandedView.this;
                    sb2.append(bubbleExpandedView2.getBubbleKey());
                    Log.d("Bubbles", sb2.toString());
                    try {
                        Rect rect = new Rect();
                        bubbleExpandedView2.mTaskView.getBoundsOnScreen(rect);
                        activityOptions.setTaskAlwaysOnTop(true);
                        activityOptions.setLaunchedFromBubble(true);
                        activityOptions.setPendingIntentBackgroundActivityStartMode(1);
                        activityOptions.setPendingIntentBackgroundActivityLaunchAllowedByPermission(true);
                        Intent intent = new Intent();
                        intent.addFlags(524288);
                        intent.addFlags(134217728);
                        Bubble bubble = bubbleExpandedView2.mBubble;
                        if (bubble.mIsAppBubble) {
                            context = ((LinearLayout) bubbleExpandedView2).mContext;
                            bubbleExpandedView2.mTaskView.startActivity(PendingIntent.getActivity(context.createContextAsUser(bubbleExpandedView2.mBubble.mUser, 4), 0, bubbleExpandedView2.mBubble.mAppIntent.addFlags(524288).addFlags(134217728), 201326592, null), null, activityOptions, rect);
                        } else if (!bubbleExpandedView2.mIsOverflow && bubble.hasMetadataShortcutId()) {
                            activityOptions.setApplyActivityFlagsForBubbles(true);
                            bubbleExpandedView2.mTaskView.startShortcutActivity(bubbleExpandedView2.mBubble.mShortcutInfo, activityOptions, rect);
                        } else {
                            Bubble bubble2 = bubbleExpandedView2.mBubble;
                            if (bubble2 != null) {
                                bubble2.mIntentActive = true;
                            }
                            bubbleExpandedView2.mTaskView.startActivity(bubbleExpandedView2.mPendingIntent, intent, activityOptions, rect);
                        }
                    } catch (RuntimeException e) {
                        Log.w("Bubbles", "Exception while displaying bubble: " + bubbleExpandedView2.getBubbleKey() + ", " + e.getMessage() + "; removing bubble");
                        bubbleExpandedView2.mController.removeBubble(bubbleExpandedView2.getBubbleKey(), 10);
                    }
                }
            });
            this.mInitialized = true;
        }

        @Override // com.android.wm.shell.taskview.TaskView.Listener
        public final void onReleased() {
            this.mDestroyed = true;
        }

        @Override // com.android.wm.shell.taskview.TaskView.Listener
        public final void onTaskCreated(int i) {
            StringBuilder m1m = AbstractC0000x2c234b15.m1m("onTaskCreated: taskId=", i, " bubble=");
            m1m.append(BubbleExpandedView.this.getBubbleKey());
            Log.d("Bubbles", m1m.toString());
            BubbleExpandedView bubbleExpandedView = BubbleExpandedView.this;
            bubbleExpandedView.mTaskId = i;
            Bubble bubble = bubbleExpandedView.mBubble;
            if (bubble != null && bubble.mIsAppBubble) {
                BubbleController bubbleController = bubbleExpandedView.mController;
                String str = bubble.mKey;
                BubbleController.BubblesImpl.CachedState cachedState = bubbleController.mImpl.mCachedState;
                synchronized (cachedState) {
                    cachedState.mAppBubbleTaskIds.put(str, Integer.valueOf(i));
                }
            }
            BubbleExpandedView.this.setContentVisibility(true);
        }

        @Override // com.android.wm.shell.taskview.TaskView.Listener
        public final void onTaskRemovalStarted(int i) {
            StringBuilder m1m = AbstractC0000x2c234b15.m1m("onTaskRemovalStarted: taskId=", i, " bubble=");
            BubbleExpandedView bubbleExpandedView = BubbleExpandedView.this;
            m1m.append(bubbleExpandedView.getBubbleKey());
            Log.d("Bubbles", m1m.toString());
            if (bubbleExpandedView.mBubble != null) {
                bubbleExpandedView.post(new BubbleExpandedView$$ExternalSyntheticLambda0(this, 1));
            }
        }

        @Override // com.android.wm.shell.taskview.TaskView.Listener
        public final void onTaskVisibilityChanged(boolean z) {
            BubbleExpandedView.this.setContentVisibility(z);
        }
    }

    public static void $r8$lambda$7mTlzeIDJmdNdaf6PVGZAuiv6Tk(BubbleExpandedView bubbleExpandedView, boolean z, boolean z2, float f, boolean z3, float f2, boolean z4) {
        float f3;
        float tabletSidePadding;
        int width;
        int i;
        bubbleExpandedView.mCurrentPointer = z ? z2 ? bubbleExpandedView.mLeftPointer : bubbleExpandedView.mRightPointer : bubbleExpandedView.mTopPointer;
        bubbleExpandedView.updatePointerView();
        if (z) {
            f3 = f - (bubbleExpandedView.mPointerWidth / 2.0f);
            if (z2) {
                width = -bubbleExpandedView.mPointerHeight;
                i = bubbleExpandedView.mPointerMargin;
            } else {
                width = bubbleExpandedView.getWidth() - ((LinearLayout) bubbleExpandedView).mPaddingRight;
                i = bubbleExpandedView.mPointerHeight;
            }
            tabletSidePadding = width - i;
        } else {
            f3 = bubbleExpandedView.mPointerOverlap;
            if (z3) {
                bubbleExpandedView.getWidth();
            }
            BubblePositioner bubblePositioner = bubbleExpandedView.mPositioner;
            float f4 = ((bubblePositioner.mBubbleSize / 2.0f) + f2) - bubbleExpandedView.mPointerWidth;
            tabletSidePadding = QpShellRune.NOTI_BUBBLE_STYLE_TABLET ? (bubbleExpandedView.mPointerWidth / 2.0f) + (f4 - bubblePositioner.getTabletSidePadding()) : f4;
        }
        if (z4) {
            bubbleExpandedView.mPointerView.animate().translationX(tabletSidePadding).translationY(f3).start();
            return;
        }
        bubbleExpandedView.mPointerView.setTranslationY(f3);
        bubbleExpandedView.mPointerView.setTranslationX(tabletSidePadding);
        bubbleExpandedView.mPointerView.setVisibility(0);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.wm.shell.bubbles.BubbleExpandedView$1] */
    static {
        new FloatProperty("contentAlpha") { // from class: com.android.wm.shell.bubbles.BubbleExpandedView.2
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
        new FloatProperty("backgroundAlpha") { // from class: com.android.wm.shell.bubbles.BubbleExpandedView.3
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
        new FloatProperty("manageButtonAlpha") { // from class: com.android.wm.shell.bubbles.BubbleExpandedView.4
            @Override // android.util.Property
            public final Float get(Object obj) {
                return Float.valueOf(((BubbleExpandedView) obj).mManageButton.getAlpha());
            }

            @Override // android.util.FloatProperty
            public final void setValue(Object obj, float f) {
                ((BubbleExpandedView) obj).mManageButton.setAlpha(f);
            }
        };
    }

    public BubbleExpandedView(Context context) {
        this(context, null);
    }

    public final void applyThemeAttrs() {
        TypedArray obtainStyledAttributes = ((LinearLayout) this).mContext.obtainStyledAttributes(new int[]{R.attr.dialogCornerRadius, R.^attr-private.materialColorOnSecondaryFixed, R.^attr-private.materialColorOnSurface});
        ScreenDecorationsUtils.supportsRoundedCornersOnWindows(((LinearLayout) this).mContext.getResources());
        float dimensionPixelSize = getContext().getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sec_noti_bubble_expand_view_radius);
        if (this.mCornerRadius != dimensionPixelSize) {
            this.mCornerRadius = dimensionPixelSize;
        }
        int color = obtainStyledAttributes.getColor(1, -1);
        this.mBackgroundColorFloating = color;
        this.mExpandedViewContainer.setBackgroundColor(color);
        int color2 = obtainStyledAttributes.getColor(2, -1);
        obtainStyledAttributes.recycle();
        AlphaOptimizedButton alphaOptimizedButton = this.mManageButton;
        if (alphaOptimizedButton != null) {
            alphaOptimizedButton.getBackground().setColorFilter(color2, PorterDuff.Mode.SRC_IN);
        }
        TaskView taskView = this.mTaskView;
        if (taskView != null) {
            taskView.setCornerRadius(this.mCornerRadius);
        }
        updatePointerView();
    }

    public final void cleanUpExpandedState() {
        StringBuilder sb = new StringBuilder("cleanUpExpandedState: bubble=");
        sb.append(getBubbleKey());
        sb.append(" task=");
        RecyclerView$$ExternalSyntheticOutline0.m46m(sb, this.mTaskId, "Bubbles");
        if (this.mTaskId != -1) {
            if (Transitions.ENABLE_SHELL_TRANSITIONS) {
                TaskView taskView = this.mTaskView;
                if (taskView != null) {
                    TaskViewTaskController taskViewTaskController = taskView.mTaskViewTaskController;
                    if (taskViewTaskController.mTaskToken == null) {
                        Slog.w("TaskViewTaskController", "Trying to remove a task that was never added? (no taskToken)");
                    } else {
                        taskViewTaskController.mShellExecutor.execute(new TaskViewTaskController$$ExternalSyntheticLambda0(taskViewTaskController, 4));
                    }
                }
            } else {
                try {
                    ActivityTaskManager.getService().removeTask(this.mTaskId);
                } catch (RemoteException e) {
                    Log.w("Bubbles", e.getMessage());
                }
            }
        }
        TaskView taskView2 = this.mTaskView;
        if (taskView2 != null) {
            taskView2.release();
            removeView(this.mTaskView);
            this.mTaskView = null;
        }
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

    public BubbleOverflowContainerView getOverflow() {
        return this.mOverflowView;
    }

    public final void initialize(BubbleController bubbleController, BubbleStackView bubbleStackView, boolean z) {
        this.mController = bubbleController;
        this.mStackView = bubbleStackView;
        this.mIsOverflow = z;
        this.mPositioner = bubbleController.getPositioner();
        if (this.mIsOverflow) {
            BubbleOverflowContainerView bubbleOverflowContainerView = (BubbleOverflowContainerView) LayoutInflater.from(getContext()).inflate(com.android.systemui.R.layout.sec_bubble_overflow_container, (ViewGroup) null);
            this.mOverflowView = bubbleOverflowContainerView;
            bubbleOverflowContainerView.mController = this.mController;
            this.mExpandedViewContainer.addView(this.mOverflowView, new FrameLayout.LayoutParams(-1, -1));
            this.mExpandedViewContainer.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
            bringChildToFront(this.mOverflowView);
            this.mManageButton.setVisibility(8);
            return;
        }
        Context context = ((LinearLayout) this).mContext;
        BubbleController bubbleController2 = this.mController;
        this.mTaskViewTaskController = new TaskViewTaskController(context, bubbleController2.mTaskOrganizer, bubbleController2.mTaskViewTransitions, bubbleController2.mSyncQueue);
        TaskView taskView = new TaskView(((LinearLayout) this).mContext, this.mTaskViewTaskController);
        this.mTaskView = taskView;
        taskView.setListener(this.mController.mMainExecutor, this.mTaskViewListener);
        this.mExpandedViewContainer.addView(this.mTaskView);
        bringChildToFront(this.mTaskView);
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
        TaskView taskView3 = this.mTaskView;
        if (taskView3 != null) {
            taskView3.setClipBounds(new Rect(0, this.mTopClip, this.mTaskView.getWidth(), this.mTaskView.getHeight() - this.mBottomClip));
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mNeedsNewHeight = false;
        Log.d("Bubbles", "onDetachedFromWindow: bubble=" + getBubbleKey());
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
                outline.setRoundRect(new Rect(0, BubbleExpandedView.this.mTopClip, view.getWidth(), view.getHeight() - BubbleExpandedView.this.mBottomClip), BubbleExpandedView.this.mCornerRadius);
            }
        });
        this.mExpandedViewContainer.setClipToOutline(true);
        this.mExpandedViewContainer.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        addView(this.mExpandedViewContainer);
        bringChildToFront(this.mManageButton);
        this.mSettingsButtonUpdated = false;
        applyThemeAttrs();
        setClipToPadding(false);
        setOnTouchListener(new View.OnTouchListener() { // from class: com.android.wm.shell.bubbles.BubbleExpandedView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                BubbleExpandedView bubbleExpandedView = BubbleExpandedView.this;
                if (bubbleExpandedView.mTaskView != null) {
                    bubbleExpandedView.mTaskView.getBoundsOnScreen(new Rect());
                    if (motionEvent.getRawY() >= r2.top && motionEvent.getRawY() <= r2.bottom && (motionEvent.getRawX() < r2.left || motionEvent.getRawX() > r2.right)) {
                        return true;
                    }
                }
                return false;
            }
        });
        setLayoutDirection(3);
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
        StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("setContentVisibility: visibility=", z, " bubble=");
        m49m.append(getBubbleKey());
        Log.d("Bubbles", m49m.toString());
        this.mIsContentVisible = z;
        TaskView taskView = this.mTaskView;
        if (taskView == null || this.mIsAnimating) {
            return;
        }
        taskView.setAlpha(z ? 1.0f : 0.0f);
        this.mPointerView.setAlpha(z ? 1.0f : 0.0f);
    }

    public final void setPointerPosition(final float f, final boolean z) {
        final boolean z2 = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m11m(((LinearLayout) this).mContext) == 1;
        final boolean showBubblesVertically = this.mPositioner.showBubblesVertically();
        setPadding(showBubblesVertically ? this.mPointerMargin + this.mPointerHeight : 0, (int) (showBubblesVertically ? 0.0f : this.mPointerHeight - this.mPointerOverlap), showBubblesVertically ? this.mPointerMargin + this.mPointerHeight : 0, 0);
        float pointerPosition = this.mPositioner.getPointerPosition(f);
        if (this.mPositioner.showBubblesVertically()) {
            pointerPosition -= this.mPositioner.getExpandedViewY(this.mBubble, f);
        }
        final float f2 = pointerPosition;
        post(new Runnable() { // from class: com.android.wm.shell.bubbles.BubbleExpandedView$$ExternalSyntheticLambda2
            public final /* synthetic */ boolean f$6 = false;

            @Override // java.lang.Runnable
            public final void run() {
                BubbleExpandedView.$r8$lambda$7mTlzeIDJmdNdaf6PVGZAuiv6Tk(BubbleExpandedView.this, showBubblesVertically, z, f2, z2, f, this.f$6);
            }
        });
    }

    public final void updateDimensions() {
        Resources resources = getResources();
        updateFontSize();
        this.mPointerMargin = resources.getDimensionPixelSize(com.android.systemui.R.dimen.bubble_pointer_margin);
        resources.getDimensionPixelSize(com.android.systemui.R.dimen.bubble_overflow_height);
        resources.getDimensionPixelSize(com.android.systemui.R.dimen.bubble_manage_button_total_height);
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
        if (this.mPointerView != null) {
            updatePointerView();
        }
        View view = this.mManageButton;
        if (view != null) {
            if (!this.mSettingsButtonUpdated) {
                removeView(view);
                AlphaOptimizedButton alphaOptimizedButton = (AlphaOptimizedButton) LayoutInflater.from(new ContextThemeWrapper(getContext(), R.style.Theme.DeviceDefault.DayNight)).inflate(com.android.systemui.R.layout.bubble_manage_button, (ViewGroup) this, false);
                this.mManageButton = alphaOptimizedButton;
                addView(alphaOptimizedButton);
                this.mSettingsButtonUpdated = true;
            }
            int visibility = this.mManageButton.getVisibility();
            this.mManageButton.getPaddingTop();
            this.mManageButton.getTextSize();
            View.OnClickListener onClickListener = this.mManageClickListener;
            if (onClickListener != null) {
                this.mManageButton.setOnClickListener(onClickListener);
            }
            if (this.mPositioner != null) {
                this.mManageButton.setVisibility(this.mIsOverflow ? 8 : 0);
            } else {
                this.mManageButton.setVisibility(visibility);
            }
        }
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

    public final void updatePointerView() {
        ShapeDrawable shapeDrawable;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mPointerView.getLayoutParams();
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

    public final void updateView(int[] iArr) {
        Bubble bubble;
        Log.d("Bubbles", "updateView: bubble=" + getBubbleKey());
        this.mExpandedViewContainerLocation = iArr;
        updateDimensions();
        int i = 0;
        if (this.mExpandedViewContainerLocation != null && (((bubble = this.mBubble) != null && this.mTaskView != null) || this.mIsOverflow)) {
            float expandedViewHeight = this.mPositioner.getExpandedViewHeight(bubble);
            int maxExpandedViewHeight = this.mPositioner.getMaxExpandedViewHeight();
            float min = expandedViewHeight == -1.0f ? maxExpandedViewHeight : Math.min(expandedViewHeight, maxExpandedViewHeight);
            Log.d("Bubbles", "updateHeight: GUI height=" + min);
            FrameLayout.LayoutParams layoutParams = this.mIsOverflow ? (FrameLayout.LayoutParams) this.mOverflowView.getLayoutParams() : (FrameLayout.LayoutParams) this.mTaskView.getLayoutParams();
            this.mNeedsNewHeight = ((float) layoutParams.height) != min;
            layoutParams.height = (int) min;
            if (this.mIsOverflow) {
                this.mOverflowView.setLayoutParams(layoutParams);
            } else {
                this.mTaskView.setLayoutParams(layoutParams);
            }
            this.mNeedsNewHeight = false;
            StringBuilder sb = new StringBuilder("updateHeight: bubble=");
            sb.append(getBubbleKey());
            sb.append(" height=");
            sb.append(min);
            sb.append(" mNeedsNewHeight=");
            ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, this.mNeedsNewHeight, "Bubbles");
        }
        TaskView taskView = this.mTaskView;
        if (taskView != null && taskView.getVisibility() == 0 && this.mTaskView.isAttachedToWindow()) {
            TaskView taskView2 = this.mTaskView;
            taskView2.getBoundsOnScreen(taskView2.mTmpRect);
            taskView2.mTaskViewTaskController.setWindowBounds(taskView2.mTmpRect);
        }
        if (this.mIsOverflow) {
            post(new BubbleExpandedView$$ExternalSyntheticLambda0(this, i));
        }
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
        this.mTaskViewListener = new C38025();
    }
}
