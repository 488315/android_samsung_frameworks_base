package com.android.internal.widget.floatingtoolbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Size;
import android.view.ContextThemeWrapper;
import android.view.DisplayCutout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.util.Preconditions;
import com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup;
import com.samsung.android.app.SemMultiWindowManager;
import com.samsung.android.rune.CoreRune;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class LocalFloatingToolbarPopup implements FloatingToolbarPopup {
    private static final int MAX_OVERFLOW_SIZE = 4;
    private static final int MIN_OVERFLOW_SIZE = 1;
    private static final int NEED_CHANGE_DIRECTION_ALL = 3;
    private static final int NEED_CHANGE_DIRECTION_HORIZONTAL = 2;
    private static final int NEED_CHANGE_DIRECTION_VERTICAL = 1;
    private static final int NEED_NOT_CHANGE_DIRECTION = 0;
    private static int mCutoutLeftMargin = 0;
    private static int mCutoutRightMargin = 0;
    private static boolean mSpacingFirstButton = false;
    private static boolean sIsDiscardTouch = false;
    private static boolean sIsMovingStarted = false;
    private static boolean sIsScrolling = false;
    private static boolean sIsSemType = false;
    private View.AccessibilityDelegate mAccessibilityDelegate;
    private final Drawable mArrow;
    private final Drawable mArrowSem;
    private final AnimationSet mCloseOverflowAnimation;
    private final ViewGroup mContentContainer;
    private final Context mContext;
    private int mDeltaX;
    private int mDeltaY;
    private final AnimatorSet mDismissAnimation;
    private ImageView mDividerHorizontal;
    private ImageView mDividerVertical;
    private final Interpolator mFastOutLinearInInterpolator;
    private final Interpolator mFastOutSlowInInterpolator;
    private boolean mHidden;
    private final AnimatorSet mHideAnimation;
    private final int mIconTextSpacing;
    private boolean mIsOverflowOpen;
    private float mLastTouchDownX;
    private float mLastTouchDownY;
    private final int mLineHeight;
    private final Interpolator mLinearOutSlowInInterpolator;
    private final Interpolator mLogAccelerateInterpolator;
    private final ViewGroup mMainPanel;
    private Size mMainPanelSize;
    private final int mMarginHorizontal;
    private final int mMarginVertical;
    private final int mMenuFirstImageStartPadding;
    private final int mMenuFirstLastSidePadding;
    private final int mMenuIntelliFirstStartPadding;
    private boolean mMoved;
    private MenuItem.OnMenuItemClickListener mOnMenuItemClickListener;
    private final AnimationSet mOpenOverflowAnimation;
    private boolean mOpenOverflowUpwards;
    private final Drawable mOverflow;
    private final Animation.AnimationListener mOverflowAnimationListener;
    private final ImageButton mOverflowButton;
    private final Size mOverflowButtonSize;
    private List<MenuItem> mOverflowMenuItems;
    private final OverflowPanel mOverflowPanel;
    private Size mOverflowPanelSize;
    private final OverflowPanelViewHelper mOverflowPanelViewHelper;
    private final View mParent;
    private final View mParentRoot;
    private WindowInsets mParentRootWindowInset;
    private final int mPopupTopMargin;
    private final int mPopupVerticalOffset;
    private final PopupWindow mPopupWindow;
    private float mPrevTouchX;
    private float mPrevTouchY;
    private final AnimatorSet mShowAnimation;
    private int mSuggestedWidth;
    private final AnimatedVectorDrawable mToArrow;
    private final AnimatedVectorDrawable mToOverflow;
    private int mTouchSlop;
    private int mTransitionDurationScale;
    private final Rect mViewPortOnScreen = new Rect();
    private final Point mCoordsOnWindow = new Point();
    private final int[] mTmpCoords = new int[2];
    private final Region mTouchableRegion = new Region();
    private final ViewTreeObserver.OnComputeInternalInsetsListener mInsetsComputer = new ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup$$ExternalSyntheticLambda2
        @Override // android.view.ViewTreeObserver.OnComputeInternalInsetsListener
        public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
            LocalFloatingToolbarPopup.this.lambda$new$0(internalInsetsInfo);
        }
    };
    private final Runnable mPreparePopupContentRTLHelper = new Runnable() { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.1
        @Override // java.lang.Runnable
        public void run() {
            LocalFloatingToolbarPopup.this.setPanelsStatesAtRestingPosition();
            LocalFloatingToolbarPopup.this.setContentAreaAsTouchableSurface();
            LocalFloatingToolbarPopup.this.mContentContainer.setAlpha(1.0f);
        }
    };
    private boolean mDismissed = true;
    private final Map<MenuItemRepr, MenuItem> mMenuItems = new LinkedHashMap();
    private final View.OnClickListener mMenuItemButtonOnClickListener = new View.OnClickListener() { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.2
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            MenuItem menuItem;
            if (LocalFloatingToolbarPopup.this.mOnMenuItemClickListener == null) {
                return;
            }
            Object tag = view.getTag();
            if ((tag instanceof MenuItemRepr) && (menuItem = (MenuItem) LocalFloatingToolbarPopup.this.mMenuItems.get((MenuItemRepr) tag)) != null) {
                LocalFloatingToolbarPopup.this.mOnMenuItemClickListener.onMenuItemClick(menuItem);
            }
        }
    };
    private final Rect mPreviousContentRect = new Rect();
    private boolean mWidthChanged = true;
    private boolean mIsClosedOpposites = false;
    private boolean mIsMovingFirstTime = false;
    private Rect mToolbarVisibleRect = new Rect();
    private int[] mToolbarHiddenArea = new int[2];
    private Point mMovedPos = new Point();
    private Point mOriginalPos = new Point();
    private final View.OnAttachStateChangeListener mOnAnchorRootDetachedListener = new FloatingOnAttachStateChangeListener(this);

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
        internalInsetsInfo.contentInsets.setEmpty();
        internalInsetsInfo.visibleInsets.setEmpty();
        internalInsetsInfo.touchableRegion.set(this.mTouchableRegion);
        internalInsetsInfo.setTouchableInsets(3);
    }

    public LocalFloatingToolbarPopup(Context context, View view, boolean z) {
        sIsSemType = z;
        View view2 = (View) Objects.requireNonNull(view);
        this.mParent = view2;
        Context applyDefaultTheme = applyDefaultTheme(context);
        this.mContext = applyDefaultTheme;
        ViewGroup createContentContainer = createContentContainer(applyDefaultTheme);
        this.mContentContainer = createContentContainer;
        PopupWindow createPopupWindow = createPopupWindow(createContentContainer);
        this.mPopupWindow = createPopupWindow;
        this.mMarginHorizontal = view.getResources().getDimensionPixelSize(R.dimen.floating_toolbar_horizontal_margin);
        if (sIsSemType) {
            this.mMarginVertical = view.getResources().getDimensionPixelSize(R.dimen.sem_floating_popup_vertical_margin);
            this.mLineHeight = context.getResources().getDimensionPixelSize(R.dimen.sem_floating_popup_height);
        } else {
            this.mMarginVertical = view.getResources().getDimensionPixelSize(R.dimen.floating_toolbar_vertical_margin);
            this.mLineHeight = context.getResources().getDimensionPixelSize(R.dimen.floating_toolbar_height);
        }
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.floating_toolbar_icon_text_spacing);
        this.mIconTextSpacing = dimensionPixelSize;
        this.mLogAccelerateInterpolator = new LogAccelerateInterpolator();
        this.mFastOutSlowInInterpolator = AnimationUtils.loadInterpolator(applyDefaultTheme, 17563661);
        this.mLinearOutSlowInInterpolator = AnimationUtils.loadInterpolator(applyDefaultTheme, 17563662);
        this.mFastOutLinearInInterpolator = AnimationUtils.loadInterpolator(applyDefaultTheme, 17563663);
        Drawable drawable = applyDefaultTheme.getResources().getDrawable(R.drawable.ft_avd_tooverflow, applyDefaultTheme.getTheme());
        this.mArrow = drawable;
        drawable.setAutoMirrored(true);
        Drawable drawable2 = applyDefaultTheme.getResources().getDrawable(R.drawable.ft_avd_toarrow, applyDefaultTheme.getTheme());
        this.mOverflow = drawable2;
        drawable2.setAutoMirrored(true);
        AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) applyDefaultTheme.getResources().getDrawable(R.drawable.ft_avd_toarrow_animation, applyDefaultTheme.getTheme());
        this.mToArrow = animatedVectorDrawable;
        animatedVectorDrawable.setAutoMirrored(true);
        AnimatedVectorDrawable animatedVectorDrawable2 = (AnimatedVectorDrawable) applyDefaultTheme.getResources().getDrawable(R.drawable.ft_avd_tooverflow_animation, applyDefaultTheme.getTheme());
        this.mToOverflow = animatedVectorDrawable2;
        animatedVectorDrawable2.setAutoMirrored(true);
        ImageButton createOverflowButton = createOverflowButton();
        this.mOverflowButton = createOverflowButton;
        this.mOverflowButtonSize = measure(createOverflowButton);
        this.mMainPanel = createMainPanel();
        this.mOverflowPanelViewHelper = new OverflowPanelViewHelper(applyDefaultTheme, dimensionPixelSize);
        this.mOverflowPanel = createOverflowPanel();
        Animation.AnimationListener createOverflowAnimationListener = createOverflowAnimationListener();
        this.mOverflowAnimationListener = createOverflowAnimationListener;
        AnimationSet animationSet = new AnimationSet(true);
        this.mOpenOverflowAnimation = animationSet;
        animationSet.setAnimationListener(createOverflowAnimationListener);
        AnimationSet animationSet2 = new AnimationSet(true);
        this.mCloseOverflowAnimation = animationSet2;
        animationSet2.setAnimationListener(createOverflowAnimationListener);
        this.mShowAnimation = createEnterAnimation(createContentContainer);
        this.mDismissAnimation = createExitAnimation(createContentContainer, 150, new AnimatorListenerAdapter() { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                LocalFloatingToolbarPopup.this.mPopupWindow.dismiss();
                LocalFloatingToolbarPopup.this.mContentContainer.removeAllViews();
                if (LocalFloatingToolbarPopup.this.mParentRoot != null) {
                    LocalFloatingToolbarPopup.this.mParentRoot.removeOnAttachStateChangeListener(LocalFloatingToolbarPopup.this.mOnAnchorRootDetachedListener);
                }
            }
        });
        this.mHideAnimation = createExitAnimation(createContentContainer, 0, new AnimatorListenerAdapter() { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                LocalFloatingToolbarPopup.this.mPopupWindow.dismiss();
            }
        });
        this.mMenuFirstLastSidePadding = view.getResources().getDimensionPixelSize(R.dimen.sem_floating_popup_menu_first_last_side_padding);
        this.mMenuFirstImageStartPadding = view.getResources().getDimensionPixelSize(R.dimen.sem_floating_popup_menu_image_button_vertical_padding);
        this.mMenuIntelliFirstStartPadding = view.getResources().getDimensionPixelSize(R.dimen.sem_floating_popup_intelli_first_padding);
        this.mPopupTopMargin = view.getResources().getDimensionPixelSize(R.dimen.sem_floating_popup_top_margin);
        this.mPopupVerticalOffset = view.getResources().getDimensionPixelSize(R.dimen.sem_floating_popup_vertical_offset);
        this.mArrowSem = applyDefaultTheme.getResources().getDrawable(R.drawable.tw_ic_ab_back_material, applyDefaultTheme.getTheme());
        createDividers();
        this.mTouchSlop = ViewConfiguration.get(applyDefaultTheme).getScaledTouchSlop();
        createPopupWindow.setTouchInterceptor(new View.OnTouchListener() { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.5
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view3, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == 0) {
                    LocalFloatingToolbarPopup.this.mLastTouchDownX = motionEvent.getRawX();
                    LocalFloatingToolbarPopup.this.mLastTouchDownY = motionEvent.getRawY();
                    LocalFloatingToolbarPopup localFloatingToolbarPopup = LocalFloatingToolbarPopup.this;
                    localFloatingToolbarPopup.mPrevTouchX = localFloatingToolbarPopup.mLastTouchDownX;
                    LocalFloatingToolbarPopup localFloatingToolbarPopup2 = LocalFloatingToolbarPopup.this;
                    localFloatingToolbarPopup2.mPrevTouchY = localFloatingToolbarPopup2.mLastTouchDownY;
                    LocalFloatingToolbarPopup.sIsDiscardTouch = false;
                    LocalFloatingToolbarPopup.sIsScrolling = false;
                    LocalFloatingToolbarPopup.this.mIsMovingFirstTime = false;
                } else if (action == 2) {
                    LocalFloatingToolbarPopup.this.mMoved = true;
                    if (!LocalFloatingToolbarPopup.sIsScrolling) {
                        float rawX = motionEvent.getRawX();
                        float rawY = motionEvent.getRawY();
                        LocalFloatingToolbarPopup localFloatingToolbarPopup3 = LocalFloatingToolbarPopup.this;
                        localFloatingToolbarPopup3.mDeltaX = (int) (rawX - localFloatingToolbarPopup3.mPrevTouchX);
                        LocalFloatingToolbarPopup localFloatingToolbarPopup4 = LocalFloatingToolbarPopup.this;
                        localFloatingToolbarPopup4.mDeltaY = (int) (rawY - localFloatingToolbarPopup4.mPrevTouchY);
                        int i = (int) (rawX - LocalFloatingToolbarPopup.this.mLastTouchDownX);
                        int i2 = (int) (rawY - LocalFloatingToolbarPopup.this.mLastTouchDownY);
                        boolean z2 = LocalFloatingToolbarPopup.this.mIsMovingFirstTime;
                        if ((i * i) + (i2 * i2) >= LocalFloatingToolbarPopup.this.mTouchSlop * LocalFloatingToolbarPopup.this.mTouchSlop) {
                            LocalFloatingToolbarPopup.sIsDiscardTouch = true;
                            LocalFloatingToolbarPopup.sIsMovingStarted = true;
                            LocalFloatingToolbarPopup.this.mIsMovingFirstTime = true;
                        }
                        if (z2 != LocalFloatingToolbarPopup.this.mIsMovingFirstTime) {
                            Log.d(FloatingToolbar.FLOATING_TOOLBAR_TAG, "FloatingToolbar will be start to move, moved deltaX, deltaY : " + i + ", " + i2 + "\nmTouchSlop = " + LocalFloatingToolbarPopup.this.mTouchSlop);
                        }
                        if (LocalFloatingToolbarPopup.sIsDiscardTouch) {
                            if (LocalFloatingToolbarPopup.this.isInsideOfViewPortRect(rawX, rawY)) {
                                LocalFloatingToolbarPopup localFloatingToolbarPopup5 = LocalFloatingToolbarPopup.this;
                                localFloatingToolbarPopup5.calculateCoords(localFloatingToolbarPopup5.mCoordsOnWindow.x + LocalFloatingToolbarPopup.this.mDeltaX, LocalFloatingToolbarPopup.this.mCoordsOnWindow.y + LocalFloatingToolbarPopup.this.mDeltaY);
                            }
                            LocalFloatingToolbarPopup.this.recalCoordsOnWindowX();
                            LocalFloatingToolbarPopup.this.mPopupWindow.update(LocalFloatingToolbarPopup.this.mCoordsOnWindow.x, LocalFloatingToolbarPopup.this.mCoordsOnWindow.y, LocalFloatingToolbarPopup.this.mPopupWindow.getWidth(), LocalFloatingToolbarPopup.this.mPopupWindow.getHeight());
                            LocalFloatingToolbarPopup.this.mPrevTouchX = rawX;
                            LocalFloatingToolbarPopup.this.mPrevTouchY = rawY;
                        }
                    }
                }
                return false;
            }
        });
        this.mParentRoot = view2.getRootView();
        this.mParentRootWindowInset = view2.getRootWindowInsets();
    }

    @Override // com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup
    public boolean setOutsideTouchable(boolean z, PopupWindow.OnDismissListener onDismissListener) {
        boolean z2;
        if (this.mPopupWindow.isOutsideTouchable() ^ z) {
            this.mPopupWindow.setOutsideTouchable(z);
            z2 = true;
            this.mPopupWindow.setFocusable(!z);
            this.mPopupWindow.update();
        } else {
            z2 = false;
        }
        this.mPopupWindow.setOnDismissListener(onDismissListener);
        return z2;
    }

    private void layoutMenuItems(List<MenuItem> list, MenuItem.OnMenuItemClickListener onMenuItemClickListener, int i) {
        cancelOverflowAnimations();
        clearPanels();
        updateMenuItems(list, onMenuItemClickListener);
        List<MenuItem> layoutMainPanelItems = layoutMainPanelItems(list, getAdjustedToolbarWidth(i));
        if (!layoutMainPanelItems.isEmpty()) {
            layoutOverflowPanelItems(layoutMainPanelItems);
        }
        updatePopupSize();
    }

    private void updateMenuItems(List<MenuItem> list, MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        this.mMenuItems.clear();
        for (MenuItem menuItem : list) {
            this.mMenuItems.put(MenuItemRepr.of(menuItem), menuItem);
        }
        this.mOnMenuItemClickListener = onMenuItemClickListener;
    }

    private boolean isLayoutRequired(List<MenuItem> list) {
        return !MenuItemRepr.reprEquals(list, this.mMenuItems.values());
    }

    @Override // com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup
    public void setWidthChanged(boolean z) {
        this.mWidthChanged = z;
    }

    @Override // com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup
    public void setSuggestedWidth(int i) {
        this.mWidthChanged = ((double) Math.abs(i - this.mSuggestedWidth)) > ((double) this.mSuggestedWidth) * 0.2d;
        this.mSuggestedWidth = i;
    }

    @Override // com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup
    public void show(List<MenuItem> list, MenuItem.OnMenuItemClickListener onMenuItemClickListener, Rect rect) {
        if (isLayoutRequired(list) || this.mWidthChanged) {
            dismiss();
            layoutMenuItems(list, onMenuItemClickListener, this.mSuggestedWidth);
        } else {
            updateMenuItems(list, onMenuItemClickListener);
        }
        if (!isShowing()) {
            show(rect);
        } else if (!this.mPreviousContentRect.equals(rect)) {
            updateCoordinates(rect);
        }
        this.mWidthChanged = false;
        this.mPreviousContentRect.set(rect);
    }

    private void show(Rect rect) {
        Objects.requireNonNull(rect);
        if (isShowing()) {
            return;
        }
        View view = this.mParentRoot;
        if (view != null) {
            view.addOnAttachStateChangeListener(this.mOnAnchorRootDetachedListener);
        }
        this.mHidden = false;
        this.mDismissed = false;
        cancelDismissAndHideAnimations();
        cancelOverflowAnimations();
        refreshCoordinatesAndOverflowDirection(rect);
        preparePopupContent();
        recalCoordsOnWindowX();
        this.mPopupWindow.showAtLocation(this.mParent, 0, this.mCoordsOnWindow.x, this.mCoordsOnWindow.y);
        setTouchableSurfaceInsetsComputer();
        runShowAnimation();
    }

    @Override // com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup
    public void dismiss() {
        if (this.mDismissed) {
            return;
        }
        this.mHidden = false;
        this.mDismissed = true;
        this.mHideAnimation.cancel();
        runDismissAnimation();
        setZeroTouchableSurface();
    }

    @Override // com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup
    public void hide() {
        if (isShowing()) {
            this.mHidden = true;
            runHideAnimation();
            setZeroTouchableSurface();
        }
    }

    @Override // com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup
    public boolean isShowing() {
        return (this.mDismissed || this.mHidden) ? false : true;
    }

    @Override // com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup
    public boolean isHidden() {
        return this.mHidden;
    }

    @Override // com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup
    public void setIsMovingStarted(boolean z) {
        sIsMovingStarted = z;
    }

    @Override // com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup
    public boolean isMovingStarted() {
        return sIsMovingStarted;
    }

    private void updateCoordinates(Rect rect) {
        Objects.requireNonNull(rect);
        if (isShowing() && this.mPopupWindow.isShowing()) {
            cancelOverflowAnimations();
            refreshCoordinatesAndOverflowDirection(rect);
            preparePopupContent();
            recalCoordsOnWindowX();
            this.mPopupWindow.update(this.mCoordsOnWindow.x, this.mCoordsOnWindow.y, this.mPopupWindow.getWidth(), this.mPopupWindow.getHeight());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x01b2  */
    /* JADX WARN: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void refreshCoordinatesAndOverflowDirection(android.graphics.Rect r15) {
        /*
            Method dump skipped, instructions count: 453
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.refreshCoordinatesAndOverflowDirection(android.graphics.Rect):void");
    }

    private void runShowAnimation() {
        this.mShowAnimation.start();
    }

    private void runDismissAnimation() {
        this.mDismissAnimation.start();
    }

    private void runHideAnimation() {
        this.mHideAnimation.start();
    }

    private void cancelDismissAndHideAnimations() {
        this.mDismissAnimation.cancel();
        this.mHideAnimation.cancel();
    }

    private void cancelOverflowAnimations() {
        this.mContentContainer.clearAnimation();
        this.mMainPanel.animate().cancel();
        this.mOverflowPanel.animate().cancel();
        this.mToArrow.stop();
        this.mToOverflow.stop();
    }

    private void openOverflow() {
        int isNeedToChangeDirection = isNeedToChangeDirection();
        if (isNeedToChangeDirection == 1 || isNeedToChangeDirection == 3) {
            this.mOpenOverflowUpwards = !this.mOpenOverflowUpwards;
        }
        if (isNeedToChangeDirection == 2 || isNeedToChangeDirection == 3) {
            boolean isInRTLMode = isInRTLMode();
            boolean z = this.mIsClosedOpposites;
            if (isInRTLMode == z) {
                this.mIsClosedOpposites = !z;
                if (this.mCoordsOnWindow.x + this.mContentContainer.getX() + this.mOverflowPanelSize.getWidth() > this.mViewPortOnScreen.right) {
                    shiftPopup();
                    this.mIsClosedOpposites = !this.mIsClosedOpposites;
                }
            } else {
                shiftPopup();
                this.mIsClosedOpposites = !this.mIsClosedOpposites;
            }
        }
        if (sIsSemType) {
            changeOverflowPanelAdapterOrder();
        }
        final int width = this.mOverflowPanelSize.getWidth();
        final int height = this.mOverflowPanelSize.getHeight();
        final int width2 = this.mContentContainer.getWidth();
        final int height2 = this.mContentContainer.getHeight();
        final float y = this.mContentContainer.getY();
        final float x = this.mContentContainer.getX();
        final float width3 = x + this.mContentContainer.getWidth();
        Animation animation = new Animation() { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.6
            @Override // android.view.animation.Animation
            protected void applyTransformation(float f, Transformation transformation) {
                float width4;
                LocalFloatingToolbarPopup.setWidth(LocalFloatingToolbarPopup.this.mContentContainer, width2 + ((int) (f * (width - width2))));
                if (LocalFloatingToolbarPopup.this.isInRTLMode() != LocalFloatingToolbarPopup.this.mIsClosedOpposites) {
                    width4 = x;
                } else {
                    width4 = width3 - LocalFloatingToolbarPopup.this.mContentContainer.getWidth();
                }
                LocalFloatingToolbarPopup.this.mContentContainer.setX(width4);
                if (LocalFloatingToolbarPopup.this.isInRTLMode()) {
                    LocalFloatingToolbarPopup.this.mMainPanel.setX(0.0f);
                    LocalFloatingToolbarPopup.this.mOverflowPanel.setX(0.0f);
                } else {
                    LocalFloatingToolbarPopup.this.mMainPanel.setX(LocalFloatingToolbarPopup.this.mContentContainer.getWidth() - width2);
                    LocalFloatingToolbarPopup.this.mOverflowPanel.setX(LocalFloatingToolbarPopup.this.mContentContainer.getWidth() - width);
                }
            }
        };
        Animation animation2 = new Animation() { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.7
            @Override // android.view.animation.Animation
            protected void applyTransformation(float f, Transformation transformation) {
                LocalFloatingToolbarPopup.setHeight(LocalFloatingToolbarPopup.this.mContentContainer, height2 + ((int) (f * (height - height2))));
                if (LocalFloatingToolbarPopup.this.mOpenOverflowUpwards) {
                    LocalFloatingToolbarPopup.this.mContentContainer.setY(y - (LocalFloatingToolbarPopup.this.mContentContainer.getHeight() - height2));
                    LocalFloatingToolbarPopup.this.positionContentYCoordinatesIfOpeningOverflowUpwards();
                }
            }
        };
        final float x2 = this.mOverflowButton.getX();
        final float width4 = isInRTLMode() ? (width + x2) - this.mOverflowButton.getWidth() : (x2 - width) + this.mOverflowButton.getWidth();
        Animation animation3 = new Animation() { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.8
            @Override // android.view.animation.Animation
            protected void applyTransformation(float f, Transformation transformation) {
                float f2 = x2;
                LocalFloatingToolbarPopup.this.mOverflowButton.setX(f2 + (f * (width4 - f2)) + (LocalFloatingToolbarPopup.this.isInRTLMode() ? 0.0f : LocalFloatingToolbarPopup.this.mContentContainer.getWidth() - width2));
            }
        };
        animation.setInterpolator(this.mLogAccelerateInterpolator);
        animation.setDuration(getAdjustedDuration(250));
        animation2.setInterpolator(this.mFastOutSlowInInterpolator);
        animation2.setDuration(getAdjustedDuration(250));
        animation3.setInterpolator(this.mFastOutSlowInInterpolator);
        animation3.setDuration(getAdjustedDuration(250));
        this.mOpenOverflowAnimation.getAnimations().clear();
        this.mOpenOverflowAnimation.getAnimations().clear();
        this.mOpenOverflowAnimation.addAnimation(animation);
        this.mOpenOverflowAnimation.addAnimation(animation2);
        this.mOpenOverflowAnimation.addAnimation(animation3);
        this.mContentContainer.startAnimation(this.mOpenOverflowAnimation);
        this.mIsOverflowOpen = true;
        this.mMainPanel.animate().alpha(0.0f).withLayer().setInterpolator(this.mLinearOutSlowInInterpolator).setDuration(250L).start();
        this.mOverflowPanel.setAlpha(1.0f);
    }

    private void closeOverflow() {
        if (isNeedToChangeDirection() == 2) {
            boolean isInRTLMode = isInRTLMode();
            boolean z = this.mIsClosedOpposites;
            if (isInRTLMode == z) {
                this.mIsClosedOpposites = !z;
                if (this.mCoordsOnWindow.x + this.mContentContainer.getX() + this.mMainPanelSize.getWidth() > this.mViewPortOnScreen.right) {
                    shiftPopup();
                    this.mIsClosedOpposites = !this.mIsClosedOpposites;
                }
            } else {
                shiftPopup();
                this.mIsClosedOpposites = !this.mIsClosedOpposites;
            }
        }
        if (sIsSemType) {
            this.mDividerHorizontal.setVisibility(4);
        }
        final int width = this.mMainPanelSize.getWidth();
        final int width2 = this.mContentContainer.getWidth();
        final float x = this.mContentContainer.getX();
        final float width3 = x + this.mContentContainer.getWidth();
        Animation animation = new Animation() { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.9
            @Override // android.view.animation.Animation
            protected void applyTransformation(float f, Transformation transformation) {
                LocalFloatingToolbarPopup.setWidth(LocalFloatingToolbarPopup.this.mContentContainer, width2 + ((int) (f * (width - width2))));
                float width4 = width3 - LocalFloatingToolbarPopup.this.mContentContainer.getWidth();
                if (LocalFloatingToolbarPopup.this.isInRTLMode() != LocalFloatingToolbarPopup.this.mIsClosedOpposites) {
                    width4 = x;
                }
                LocalFloatingToolbarPopup.this.mContentContainer.setX(width4);
                if (LocalFloatingToolbarPopup.this.isInRTLMode()) {
                    LocalFloatingToolbarPopup.this.mMainPanel.setX(0.0f);
                    LocalFloatingToolbarPopup.this.mOverflowPanel.setX(0.0f);
                } else {
                    LocalFloatingToolbarPopup.this.mMainPanel.setX(LocalFloatingToolbarPopup.this.mContentContainer.getWidth() - width);
                    LocalFloatingToolbarPopup.this.mOverflowPanel.setX(LocalFloatingToolbarPopup.this.mContentContainer.getWidth() - width2);
                }
            }
        };
        final int height = this.mMainPanelSize.getHeight();
        final int height2 = this.mContentContainer.getHeight();
        final float y = this.mContentContainer.getY() + this.mContentContainer.getHeight();
        Animation animation2 = new Animation() { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.10
            @Override // android.view.animation.Animation
            protected void applyTransformation(float f, Transformation transformation) {
                LocalFloatingToolbarPopup.setHeight(LocalFloatingToolbarPopup.this.mContentContainer, height2 + ((int) (f * (height - height2))));
                if (LocalFloatingToolbarPopup.this.mOpenOverflowUpwards) {
                    LocalFloatingToolbarPopup.this.mContentContainer.setY(y - LocalFloatingToolbarPopup.this.mContentContainer.getHeight());
                    LocalFloatingToolbarPopup.this.positionContentYCoordinatesIfOpeningOverflowUpwards();
                }
            }
        };
        final float x2 = this.mOverflowButton.getX();
        final float width4 = isInRTLMode() ? (x2 - width2) + this.mOverflowButton.getWidth() : (width2 + x2) - this.mOverflowButton.getWidth();
        Animation animation3 = new Animation() { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.11
            @Override // android.view.animation.Animation
            protected void applyTransformation(float f, Transformation transformation) {
                float f2 = x2;
                LocalFloatingToolbarPopup.this.mOverflowButton.setX(f2 + (f * (width4 - f2)) + (LocalFloatingToolbarPopup.this.isInRTLMode() ? 0.0f : LocalFloatingToolbarPopup.this.mContentContainer.getWidth() - width2));
            }
        };
        animation.setInterpolator(this.mFastOutSlowInInterpolator);
        animation.setDuration(getAdjustedDuration(250));
        animation2.setInterpolator(this.mLogAccelerateInterpolator);
        animation2.setDuration(getAdjustedDuration(250));
        animation3.setInterpolator(this.mFastOutSlowInInterpolator);
        animation3.setDuration(getAdjustedDuration(250));
        this.mCloseOverflowAnimation.getAnimations().clear();
        this.mCloseOverflowAnimation.addAnimation(animation);
        this.mCloseOverflowAnimation.addAnimation(animation2);
        this.mCloseOverflowAnimation.addAnimation(animation3);
        this.mContentContainer.startAnimation(this.mCloseOverflowAnimation);
        this.mIsOverflowOpen = false;
        this.mMainPanel.animate().alpha(1.0f).withLayer().setInterpolator(this.mFastOutLinearInInterpolator).setDuration(100L).start();
        this.mOverflowPanel.animate().alpha(0.0f).withLayer().setInterpolator(this.mLinearOutSlowInInterpolator).setDuration(150L).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:14:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0228  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0281  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0293  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0238  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setPanelsStatesAtRestingPosition() {
        /*
            Method dump skipped, instructions count: 699
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.setPanelsStatesAtRestingPosition():void");
    }

    private void updateOverflowHeight(int i) {
        if (hasOverflow()) {
            int calculateOverflowHeight = calculateOverflowHeight((i - this.mOverflowButtonSize.getHeight()) / this.mLineHeight);
            if (calculateOverflowHeight <= i) {
                i = calculateOverflowHeight;
            }
            if (this.mOverflowPanelSize.getHeight() != i) {
                this.mOverflowPanelSize = new Size(this.mOverflowPanelSize.getWidth(), i);
            }
            setSize(this.mOverflowPanel, this.mOverflowPanelSize);
            if (this.mIsOverflowOpen) {
                setSize(this.mContentContainer, this.mOverflowPanelSize);
                if (this.mOpenOverflowUpwards) {
                    int height = this.mOverflowPanelSize.getHeight() - i;
                    ViewGroup viewGroup = this.mContentContainer;
                    float f = height;
                    viewGroup.setY(viewGroup.getY() + f);
                    ImageButton imageButton = this.mOverflowButton;
                    imageButton.setY(imageButton.getY() - f);
                }
            } else {
                setSize(this.mContentContainer, this.mMainPanelSize);
            }
            updatePopupSize();
        }
    }

    private void updatePopupSize() {
        int i;
        Size size = this.mMainPanelSize;
        int i2 = 0;
        if (size != null) {
            i2 = Math.max(0, size.getWidth());
            i = Math.max(0, this.mMainPanelSize.getHeight());
        } else {
            i = 0;
        }
        Size size2 = this.mOverflowPanelSize;
        if (size2 != null) {
            i2 = Math.max(i2, size2.getWidth());
            i = Math.max(i, this.mOverflowPanelSize.getHeight());
            Size size3 = this.mMainPanelSize;
            if (size3 != null) {
                i2 += Math.abs(size3.getWidth() - this.mOverflowPanelSize.getWidth());
                i = (i * 2) - this.mMainPanelSize.getHeight();
            }
        }
        this.mPopupWindow.setWidth(i2 + (this.mMarginHorizontal * 2));
        this.mPopupWindow.setHeight(i + (this.mMarginVertical * 2));
        maybeComputeTransitionDurationScale();
    }

    private void refreshViewPort() {
        this.mParent.getWindowVisibleDisplayFrame(this.mViewPortOnScreen);
        if (new SemMultiWindowManager().getMode() == 2) {
            int[] locationOnScreen = this.mParent.getLocationOnScreen();
            int i = this.mViewPortOnScreen.top;
            int i2 = locationOnScreen[1];
            if (i < i2) {
                this.mViewPortOnScreen.top = i2;
            }
        }
    }

    private int getAdjustedToolbarWidth(int i) {
        refreshViewPort();
        int width = this.mViewPortOnScreen.width() - (this.mParent.getResources().getDimensionPixelSize(R.dimen.floating_toolbar_horizontal_margin) * 2);
        if (i <= 0) {
            i = this.mParent.getResources().getDimensionPixelSize(R.dimen.floating_toolbar_preferred_width);
        }
        return Math.min(i, width);
    }

    private void setZeroTouchableSurface() {
        this.mTouchableRegion.setEmpty();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setContentAreaAsTouchableSurface() {
        int width;
        int height;
        Objects.requireNonNull(this.mMainPanelSize);
        if (this.mIsOverflowOpen) {
            Objects.requireNonNull(this.mOverflowPanelSize);
            width = this.mOverflowPanelSize.getWidth();
            height = this.mOverflowPanelSize.getHeight();
        } else {
            width = this.mMainPanelSize.getWidth();
            height = this.mMainPanelSize.getHeight();
        }
        this.mToolbarVisibleRect.set(0, 0, width, height);
        this.mTouchableRegion.set((int) this.mContentContainer.getX(), (int) this.mContentContainer.getY(), ((int) this.mContentContainer.getX()) + width, ((int) this.mContentContainer.getY()) + height);
        Rect bounds = this.mTouchableRegion.getBounds();
        this.mToolbarHiddenArea[0] = this.mToolbarVisibleRect.left - bounds.left;
        this.mToolbarHiddenArea[1] = this.mToolbarVisibleRect.top - bounds.top;
    }

    private void setTouchableSurfaceInsetsComputer() {
        ViewTreeObserver viewTreeObserver = this.mPopupWindow.getContentView().getRootView().getViewTreeObserver();
        viewTreeObserver.removeOnComputeInternalInsetsListener(this.mInsetsComputer);
        viewTreeObserver.addOnComputeInternalInsetsListener(this.mInsetsComputer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isInRTLMode() {
        return this.mContext.getApplicationInfo().hasRtlSupport() && this.mContext.getResources().getConfiguration().getLayoutDirection() == 1;
    }

    private boolean hasOverflow() {
        return this.mOverflowPanelSize != null;
    }

    public List<MenuItem> layoutMainPanelItems(List<MenuItem> list, int i) {
        double d;
        int paddingStart;
        int paddingStart2;
        Objects.requireNonNull(list);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (MenuItem menuItem : list) {
            if (menuItem.getItemId() != 16908353 && menuItem.requiresOverflow()) {
                arrayList2.add(menuItem);
            } else {
                arrayList.add(menuItem);
            }
        }
        arrayList.addAll(arrayList2);
        this.mMainPanel.removeAllViews();
        this.mMainPanel.setPaddingRelative(0, 0, 0, 0);
        int i2 = i;
        boolean z = true;
        char c = 0;
        while (!arrayList.isEmpty()) {
            MenuItem menuItem2 = (MenuItem) arrayList.get(0);
            if (menuItem2.getItemId() == 16909130) {
                arrayList.remove(0);
            } else {
                if (!z && menuItem2.requiresOverflow()) {
                    break;
                }
                boolean z2 = z && menuItem2.getItemId() == 16908353;
                View createMenuItemButton = createMenuItemButton(this.mContext, menuItem2, this.mIconTextSpacing, z2);
                if (!z2 && (createMenuItemButton instanceof LinearLayout)) {
                    ((LinearLayout) createMenuItemButton).setGravity(17);
                }
                if (menuItem2.getItemId() == 16910090) {
                    createMenuItemButton = LayoutInflater.from(this.mContext).inflate(sIsSemType ? R.layout.sem_floating_popup_menu_intelli : R.layout.sem_floating_popup_menu_intelli_default, (ViewGroup) null);
                    createMenuItemButton.semSetHoverPopupType(0);
                    createMenuItemButton.setContentDescription(menuItem2.getTitle());
                    createMenuItemButton.setPaddingRelative(createMenuItemButton.getPaddingStart(), createMenuItemButton.getPaddingTop(), createMenuItemButton.getPaddingEnd(), createMenuItemButton.getPaddingBottom());
                    c = 1;
                }
                if (!z || c > 0) {
                    d = 1.5d;
                } else if (c == 0) {
                    if (!sIsSemType) {
                        paddingStart = createMenuItemButton.getPaddingStart();
                        d = 1.5d;
                        paddingStart2 = (int) (paddingStart * d);
                        createMenuItemButton.setPaddingRelative(paddingStart2, createMenuItemButton.getPaddingTop(), createMenuItemButton.getPaddingEnd(), createMenuItemButton.getPaddingBottom());
                    } else {
                        if (z2) {
                            paddingStart2 = this.mMenuFirstImageStartPadding;
                        } else {
                            paddingStart2 = this.mMenuFirstLastSidePadding;
                        }
                        d = 1.5d;
                        createMenuItemButton.setPaddingRelative(paddingStart2, createMenuItemButton.getPaddingTop(), createMenuItemButton.getPaddingEnd(), createMenuItemButton.getPaddingBottom());
                    }
                } else {
                    d = 1.5d;
                    if (!sIsSemType) {
                        paddingStart = createMenuItemButton.getPaddingStart();
                        paddingStart2 = (int) (paddingStart * d);
                        createMenuItemButton.setPaddingRelative(paddingStart2, createMenuItemButton.getPaddingTop(), createMenuItemButton.getPaddingEnd(), createMenuItemButton.getPaddingBottom());
                    } else {
                        if (z2) {
                            paddingStart2 = this.mMenuIntelliFirstStartPadding;
                        } else {
                            paddingStart2 = createMenuItemButton.getPaddingStart();
                        }
                        createMenuItemButton.setPaddingRelative(paddingStart2, createMenuItemButton.getPaddingTop(), createMenuItemButton.getPaddingEnd(), createMenuItemButton.getPaddingBottom());
                    }
                }
                boolean z3 = arrayList.size() == 1;
                if (z3) {
                    int paddingEnd = sIsSemType ? this.mMenuFirstLastSidePadding : (int) (createMenuItemButton.getPaddingEnd() * d);
                    if (list.size() == 1 && menuItem2.getItemId() == 16910090) {
                        View findViewById = createMenuItemButton.findViewById(R.id.intelli_menu_divider);
                        if (findViewById != null) {
                            findViewById.setVisibility(8);
                        }
                        paddingEnd = this.mParent.getResources().getDimensionPixelSize(R.dimen.sem_floating_popup_intelli_menu_only_end_padding);
                    }
                    createMenuItemButton.setPaddingRelative(createMenuItemButton.getPaddingStart(), createMenuItemButton.getPaddingTop(), paddingEnd, createMenuItemButton.getPaddingBottom());
                }
                createMenuItemButton.measure(0, 0);
                int min = Math.min(createMenuItemButton.getMeasuredWidth(), i);
                if (sIsSemType) {
                    this.mOverflowButtonSize.getWidth();
                }
                boolean z4 = min <= i2 - this.mOverflowButtonSize.getWidth();
                boolean z5 = z3 && min <= i2;
                if (!z4 && !z5) {
                    break;
                }
                setButtonTagAndClickListener(createMenuItemButton, menuItem2);
                createMenuItemButton.setTooltipText(menuItem2.getTooltipText());
                this.mMainPanel.addView(createMenuItemButton);
                ViewGroup.LayoutParams layoutParams = createMenuItemButton.getLayoutParams();
                layoutParams.width = min;
                createMenuItemButton.setLayoutParams(layoutParams);
                i2 -= min;
                arrayList.remove(0);
                menuItem2.getGroupId();
                z = c == 1;
                c = 65535;
            }
        }
        if (!arrayList.isEmpty()) {
            if (sIsSemType) {
                ViewGroup viewGroup = this.mMainPanel;
                View childAt = viewGroup.getChildAt(viewGroup.getChildCount() - 1);
                if (childAt != null) {
                    int paddingEnd2 = childAt.getPaddingEnd();
                    childAt.setPaddingRelative(childAt.getPaddingStart(), childAt.getPaddingTop(), 0, childAt.getPaddingBottom());
                    ViewGroup.LayoutParams layoutParams2 = childAt.getLayoutParams();
                    layoutParams2.width -= paddingEnd2;
                    childAt.setLayoutParams(layoutParams2);
                }
                if (mSpacingFirstButton) {
                    View childAt2 = this.mMainPanel.getChildAt(0);
                    if (childAt2 != null) {
                        int paddingEnd3 = childAt2.getPaddingEnd();
                        childAt2.setPaddingRelative(childAt2.getPaddingStart(), childAt2.getPaddingTop(), 0, childAt2.getPaddingBottom());
                        ViewGroup.LayoutParams layoutParams3 = childAt2.getLayoutParams();
                        layoutParams3.width -= paddingEnd3;
                        childAt2.setLayoutParams(layoutParams3);
                    }
                    mSpacingFirstButton = false;
                }
            }
            this.mMainPanel.setPaddingRelative(0, 0, this.mOverflowButtonSize.getWidth(), 0);
        }
        this.mMainPanelSize = measure(this.mMainPanel);
        return arrayList;
    }

    private void layoutOverflowPanelItems(List<MenuItem> list) {
        this.mOverflowMenuItems = list;
        ArrayAdapter arrayAdapter = (ArrayAdapter) this.mOverflowPanel.getAdapter();
        arrayAdapter.clear();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            arrayAdapter.add(list.get(i));
        }
        this.mOverflowPanel.setAdapter((ListAdapter) arrayAdapter);
        if (this.mOpenOverflowUpwards) {
            this.mOverflowPanel.setY(0.0f);
        } else {
            this.mOverflowPanel.setY(this.mOverflowButtonSize.getHeight());
        }
        Size size2 = new Size(Math.max(getOverflowWidth(), this.mOverflowButtonSize.getWidth()), calculateOverflowHeight(4));
        this.mOverflowPanelSize = size2;
        setSize(this.mOverflowPanel, size2);
    }

    private void preparePopupContent() {
        this.mContentContainer.removeAllViews();
        if (hasOverflow()) {
            this.mContentContainer.addView(this.mOverflowPanel);
        }
        this.mContentContainer.addView(this.mMainPanel);
        if (hasOverflow()) {
            this.mContentContainer.addView(this.mOverflowButton);
            if (sIsSemType) {
                this.mContentContainer.addView(this.mDividerHorizontal);
            }
        }
        setPanelsStatesAtRestingPosition();
        setContentAreaAsTouchableSurface();
        if (isInRTLMode()) {
            this.mContentContainer.setAlpha(0.0f);
            this.mContentContainer.post(this.mPreparePopupContentRTLHelper);
        }
    }

    private void clearPanels() {
        this.mOverflowPanelSize = null;
        this.mMainPanelSize = null;
        this.mIsOverflowOpen = false;
        this.mMainPanel.removeAllViews();
        ArrayAdapter arrayAdapter = (ArrayAdapter) this.mOverflowPanel.getAdapter();
        arrayAdapter.clear();
        this.mOverflowPanel.setAdapter((ListAdapter) arrayAdapter);
        this.mContentContainer.removeAllViews();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void positionContentYCoordinatesIfOpeningOverflowUpwards() {
        if (this.mOpenOverflowUpwards) {
            this.mMainPanel.setY(this.mContentContainer.getHeight() - this.mMainPanelSize.getHeight());
            this.mOverflowButton.setY(this.mContentContainer.getHeight() - this.mOverflowButton.getHeight());
            this.mOverflowPanel.setY(this.mContentContainer.getHeight() - this.mOverflowPanelSize.getHeight());
        }
    }

    private int getOverflowWidth() {
        int count = this.mOverflowPanel.getAdapter().getCount();
        int i = 0;
        for (int i2 = 0; i2 < count; i2++) {
            i = Math.max(this.mOverflowPanelViewHelper.calculateWidth((MenuItem) this.mOverflowPanel.getAdapter().getItem(i2)), i);
        }
        return Math.min(i, this.mViewPortOnScreen.width() - (this.mMarginHorizontal * 2));
    }

    private int calculateOverflowHeight(int i) {
        int min = Math.min(4, Math.min(Math.max(1, i), this.mOverflowPanel.getCount()));
        return (min * this.mLineHeight) + this.mOverflowButtonSize.getHeight() + (min < this.mOverflowPanel.getCount() ? (int) (this.mLineHeight * 0.5f) : 0);
    }

    private void setButtonTagAndClickListener(View view, MenuItem menuItem) {
        view.setTag(MenuItemRepr.of(menuItem));
        view.setAccessibilityDelegate(getAccessibilityDelegate());
        view.setOnClickListener(this.mMenuItemButtonOnClickListener);
    }

    private int getAdjustedDuration(int i) {
        int i2 = this.mTransitionDurationScale;
        if (i2 < 150) {
            return Math.max(i - 50, 0);
        }
        return i2 > 300 ? i + 50 : (int) (i * ValueAnimator.getDurationScale());
    }

    private void maybeComputeTransitionDurationScale() {
        Size size = this.mMainPanelSize;
        if (size == null || this.mOverflowPanelSize == null) {
            return;
        }
        int width = size.getWidth() - this.mOverflowPanelSize.getWidth();
        int height = this.mOverflowPanelSize.getHeight() - this.mMainPanelSize.getHeight();
        this.mTransitionDurationScale = (int) (Math.sqrt((width * width) + (height * height)) / this.mContentContainer.getContext().getResources().getDisplayMetrics().density);
    }

    private ViewGroup createMainPanel() {
        return new LinearLayout(this.mContext) { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.12
            @Override // android.widget.LinearLayout, android.view.View
            protected void onMeasure(int i, int i2) {
                if (LocalFloatingToolbarPopup.this.isOverflowAnimating()) {
                    i = View.MeasureSpec.makeMeasureSpec(LocalFloatingToolbarPopup.this.mMainPanelSize.getWidth(), 1073741824);
                }
                super.onMeasure(i, i2);
            }

            @Override // android.view.ViewGroup
            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                return LocalFloatingToolbarPopup.this.isOverflowAnimating();
            }
        };
    }

    private ImageButton createOverflowButton() {
        final ImageButton imageButton = (ImageButton) LayoutInflater.from(this.mContext).inflate(sIsSemType ? R.layout.sem_floating_popup_overflow_button : R.layout.floating_popup_overflow_button, (ViewGroup) null);
        imageButton.lambda$setImageURIAsync$2(this.mOverflow);
        imageButton.semSetHoverPopupType(0);
        imageButton.setAccessibilityDelegate(getAccessibilityDelegate());
        imageButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LocalFloatingToolbarPopup.this.lambda$createOverflowButton$1(imageButton, view);
            }
        });
        return imageButton;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createOverflowButton$1(ImageButton imageButton, View view) {
        if (sIsDiscardTouch) {
            return;
        }
        if (this.mIsOverflowOpen) {
            if (sIsSemType) {
                imageButton.lambda$setImageURIAsync$2(this.mOverflow);
            } else {
                imageButton.lambda$setImageURIAsync$2(this.mToOverflow);
                this.mToOverflow.start();
            }
            closeOverflow();
            return;
        }
        if (sIsSemType) {
            imageButton.lambda$setImageURIAsync$2(this.mArrowSem);
        } else {
            imageButton.lambda$setImageURIAsync$2(this.mToArrow);
            this.mToArrow.start();
        }
        openOverflow();
    }

    private OverflowPanel createOverflowPanel() {
        final OverflowPanel overflowPanel = new OverflowPanel(this);
        overflowPanel.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        overflowPanel.setDivider(null);
        overflowPanel.setDividerHeight(0);
        overflowPanel.setAdapter((ListAdapter) new ArrayAdapter<MenuItem>(this.mContext, 0) { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.13
            @Override // android.widget.ArrayAdapter, android.widget.Adapter
            public View getView(int i, View view, ViewGroup viewGroup) {
                return LocalFloatingToolbarPopup.this.mOverflowPanelViewHelper.getView(getItem(i), LocalFloatingToolbarPopup.this.mOverflowPanelSize.getWidth(), view);
            }
        });
        overflowPanel.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup$$ExternalSyntheticLambda1
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                LocalFloatingToolbarPopup.this.lambda$createOverflowPanel$2(overflowPanel, adapterView, view, i, j);
            }
        });
        return overflowPanel;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createOverflowPanel$2(OverflowPanel overflowPanel, AdapterView adapterView, View view, int i, long j) {
        view.setAccessibilityDelegate(getAccessibilityDelegate());
        MenuItem menuItem = (MenuItem) overflowPanel.getAdapter().getItem(i);
        MenuItem.OnMenuItemClickListener onMenuItemClickListener = this.mOnMenuItemClickListener;
        if (onMenuItemClickListener != null) {
            onMenuItemClickListener.onMenuItemClick(menuItem);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isOverflowAnimating() {
        return (this.mOpenOverflowAnimation.hasStarted() && !this.mOpenOverflowAnimation.hasEnded()) || (this.mCloseOverflowAnimation.hasStarted() && !this.mCloseOverflowAnimation.hasEnded());
    }

    /* renamed from: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup$14, reason: invalid class name */
    class AnonymousClass14 implements Animation.AnimationListener {
        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        AnonymousClass14() {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
            LocalFloatingToolbarPopup.this.mOverflowButton.setEnabled(false);
            LocalFloatingToolbarPopup.this.mMainPanel.setVisibility(0);
            LocalFloatingToolbarPopup.this.mOverflowPanel.setVisibility(0);
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
            LocalFloatingToolbarPopup.this.mContentContainer.post(new Runnable() { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup$14$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    LocalFloatingToolbarPopup.AnonymousClass14.this.lambda$onAnimationEnd$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAnimationEnd$0() {
            LocalFloatingToolbarPopup.this.setPanelsStatesAtRestingPosition();
            LocalFloatingToolbarPopup.this.setContentAreaAsTouchableSurface();
        }
    }

    private Animation.AnimationListener createOverflowAnimationListener() {
        return new AnonymousClass14();
    }

    private static Size measure(View view) {
        Preconditions.checkState(view.getParent() == null);
        view.measure(0, 0);
        return new Size(view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    private static void setSize(View view, int i, int i2) {
        view.setMinimumWidth(i);
        view.setMinimumHeight(i2);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(0, 0);
        }
        layoutParams.width = i;
        layoutParams.height = i2;
        view.setLayoutParams(layoutParams);
    }

    private static void setSize(View view, Size size) {
        setSize(view, size.getWidth(), size.getHeight());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setWidth(View view, int i) {
        setSize(view, i, view.getLayoutParams().height);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setHeight(View view, int i) {
        setSize(view, view.getLayoutParams().width, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class OverflowPanel extends ListView {
        private final LocalFloatingToolbarPopup mPopup;

        OverflowPanel(LocalFloatingToolbarPopup localFloatingToolbarPopup) {
            super(((LocalFloatingToolbarPopup) Objects.requireNonNull(localFloatingToolbarPopup)).mContext);
            this.mPopup = localFloatingToolbarPopup;
            setScrollBarDefaultDelayBeforeFade(ViewConfiguration.getScrollDefaultDelay() * 3);
            if (LocalFloatingToolbarPopup.sIsSemType) {
                return;
            }
            setScrollIndicators(3);
        }

        @Override // android.widget.ListView, android.widget.AbsListView, android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(this.mPopup.mOverflowPanelSize.getHeight() - this.mPopup.mOverflowButtonSize.getHeight(), 1073741824));
        }

        @Override // android.widget.AbsListView, android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            if (canScrollVertically(1) || canScrollVertically(-1)) {
                LocalFloatingToolbarPopup.sIsScrolling = true;
            }
            if (this.mPopup.isOverflowAnimating()) {
                return true;
            }
            return super.dispatchTouchEvent(motionEvent);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.view.View
        public boolean awakenScrollBars() {
            return super.awakenScrollBars();
        }
    }

    private static final class LogAccelerateInterpolator implements Interpolator {
        private static final int BASE = 100;
        private static final float LOGS_SCALE = 1.0f / computeLog(1.0f, 100);

        private LogAccelerateInterpolator() {
        }

        private static float computeLog(float f, int i) {
            return (float) (1.0d - Math.pow(i, -f));
        }

        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            return 1.0f - (computeLog(1.0f - f, 100) * LOGS_SCALE);
        }
    }

    private static final class OverflowPanelViewHelper {
        private final View mCalculator = createMenuButton(null);
        private final Context mContext;
        private final int mIconTextSpacing;
        private final int mSidePadding;

        private boolean shouldShowIcon(MenuItem menuItem) {
            return false;
        }

        OverflowPanelViewHelper(Context context, int i) {
            this.mContext = (Context) Objects.requireNonNull(context);
            this.mIconTextSpacing = i;
            this.mSidePadding = context.getResources().getDimensionPixelSize(R.dimen.floating_toolbar_overflow_side_padding);
        }

        public View getView(MenuItem menuItem, int i, View view) {
            Objects.requireNonNull(menuItem);
            if (view != null) {
                LocalFloatingToolbarPopup.updateMenuItemButton(view, menuItem, this.mIconTextSpacing, shouldShowIcon(menuItem));
            } else {
                view = createMenuButton(menuItem);
            }
            view.setMinimumWidth(i);
            return view;
        }

        public int calculateWidth(MenuItem menuItem) {
            LocalFloatingToolbarPopup.updateMenuItemButton(this.mCalculator, menuItem, this.mIconTextSpacing, shouldShowIcon(menuItem));
            this.mCalculator.measure(0, 0);
            return this.mCalculator.getMeasuredWidth();
        }

        private View createMenuButton(MenuItem menuItem) {
            View createMenuItemButton = LocalFloatingToolbarPopup.createMenuItemButton(this.mContext, menuItem, this.mIconTextSpacing, shouldShowIcon(menuItem));
            int i = this.mSidePadding;
            createMenuItemButton.setPadding(i, 0, i, 0);
            return createMenuItemButton;
        }
    }

    @Override // com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup
    public void onDetachFromWindow() {
        this.mHideAnimation.cancel();
        this.mDismissAnimation.cancel();
        if (this.mPopupWindow.isShowing()) {
            this.mPopupWindow.dismiss();
        }
    }

    private static final class FloatingOnAttachStateChangeListener implements View.OnAttachStateChangeListener {
        private final WeakReference<LocalFloatingToolbarPopup> mFloatingToolbarPopup;

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view) {
        }

        public FloatingOnAttachStateChangeListener(LocalFloatingToolbarPopup localFloatingToolbarPopup) {
            this.mFloatingToolbarPopup = new WeakReference<>(localFloatingToolbarPopup);
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view) {
            if (this.mFloatingToolbarPopup.get() != null) {
                this.mFloatingToolbarPopup.get().onDetachFromWindow();
            }
            view.removeOnAttachStateChangeListener(this);
        }
    }

    private View.AccessibilityDelegate getAccessibilityDelegate() {
        if (this.mAccessibilityDelegate == null) {
            this.mAccessibilityDelegate = new View.AccessibilityDelegate(this) { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.15
                @Override // android.view.View.AccessibilityDelegate
                public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                    if (i == 16) {
                        return false;
                    }
                    return super.performAccessibilityAction(view, i, bundle);
                }
            };
        }
        return this.mAccessibilityDelegate;
    }

    private int getViewPortVisibleHeight() {
        SemMultiWindowManager semMultiWindowManager = new SemMultiWindowManager();
        if (semMultiWindowManager.getMode() == 2) {
            return this.mViewPortOnScreen.bottom;
        }
        int i = this.mContext.getResources().getDisplayMetrics().heightPixels;
        int imeHeight = i - getImeHeight();
        if (this.mContext.getResources().getConfiguration().windowConfiguration.isPopOver()) {
            imeHeight = i;
        }
        return semMultiWindowManager.getMode() != 0 ? i + this.mViewPortOnScreen.top : imeHeight;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isInsideOfViewPortRect(float f, float f2) {
        refreshViewPort();
        return ((float) this.mViewPortOnScreen.left) <= f && ((float) this.mViewPortOnScreen.right) >= f && ((float) this.mViewPortOnScreen.top) <= f2 && ((float) this.mViewPortOnScreen.bottom) >= f2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void calculateCoords(int i, int i2) {
        this.mParent.getRootView().getLocationOnScreen(this.mTmpCoords);
        int[] iArr = this.mTmpCoords;
        int i3 = iArr[0];
        int i4 = iArr[1];
        this.mParent.getRootView().getLocationInWindow(this.mTmpCoords);
        int[] iArr2 = this.mTmpCoords;
        int i5 = i3 - iArr2[0];
        int i6 = i4 - iArr2[1];
        int i7 = this.mViewPortOnScreen.left;
        int i8 = this.mToolbarHiddenArea[0];
        int max = Math.max(Math.max(i7 + i8, i8) - i5, i);
        int i9 = this.mViewPortOnScreen.top;
        int i10 = this.mToolbarHiddenArea[1];
        int max2 = Math.max(Math.max(i9 + i10, i10) - i6, i2);
        int min = Math.min((this.mToolbarVisibleRect.width() + max) - this.mToolbarHiddenArea[0], this.mViewPortOnScreen.right - i5);
        int min2 = Math.min((this.mToolbarVisibleRect.height() + max2) - this.mToolbarHiddenArea[1], getViewPortVisibleHeight() - i6);
        this.mCoordsOnWindow.set(Math.min(max, (min - this.mToolbarVisibleRect.width()) + this.mToolbarHiddenArea[0]), Math.min(max2, (min2 - this.mToolbarVisibleRect.height()) + this.mToolbarHiddenArea[1]));
        if (this.mMoved) {
            this.mMovedPos.set(this.mOriginalPos.x - this.mCoordsOnWindow.x, this.mOriginalPos.y - this.mCoordsOnWindow.y);
        }
    }

    @Override // com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup
    public Point getMovedPos() {
        return this.mMovedPos;
    }

    @Override // com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup
    public boolean isDismissed() {
        return this.mDismissed;
    }

    private int isNeedToChangeDirection() {
        Rect rect = new Rect(0, 0, this.mPopupWindow.getWidth(), this.mPopupWindow.getHeight());
        Rect rect2 = new Rect(0, 0, this.mPopupWindow.getWidth(), this.mPopupWindow.getHeight());
        int abs = Math.abs(this.mMainPanelSize.getWidth() - this.mOverflowPanelSize.getWidth());
        int height = this.mOverflowPanelSize.getHeight() - this.mMainPanelSize.getHeight();
        if (this.mOpenOverflowUpwards) {
            rect.bottom -= height;
            rect2.top += height;
        } else {
            rect.top += height;
            rect2.bottom -= height;
        }
        rect.top += this.mMarginVertical;
        rect.bottom -= this.mMarginVertical;
        rect2.top += this.mMarginVertical;
        rect2.bottom -= this.mMarginVertical;
        if (isInRTLMode() != this.mIsClosedOpposites) {
            rect.left += this.mMarginHorizontal + abs;
            rect.right -= this.mMarginHorizontal;
            rect2.left += abs + this.mMarginHorizontal;
            rect2.right -= this.mMarginHorizontal;
        } else {
            rect.left += this.mMarginHorizontal;
            rect.right -= this.mMarginHorizontal + abs;
            rect2.left += this.mMarginHorizontal;
            rect2.right -= abs + this.mMarginHorizontal;
        }
        this.mParent.getRootView().getLocationOnScreen(this.mTmpCoords);
        int[] iArr = this.mTmpCoords;
        int i = iArr[0];
        int i2 = iArr[1];
        this.mParent.getRootView().getLocationInWindow(this.mTmpCoords);
        int[] iArr2 = this.mTmpCoords;
        int i3 = i - iArr2[0];
        int i4 = i2 - iArr2[1];
        rect.offset(this.mCoordsOnWindow.x, this.mCoordsOnWindow.y);
        rect.offset(i3, i4);
        rect2.offset(this.mCoordsOnWindow.x, this.mCoordsOnWindow.y);
        rect2.offset(i3, i4);
        Rect rect3 = new Rect();
        rect3.set(this.mViewPortOnScreen);
        rect3.bottom = getViewPortVisibleHeight();
        if (rect3.contains(rect)) {
            return 0;
        }
        return (rect3.left > rect.left || rect3.right < rect.right) ? (rect3.top > rect.top || rect3.bottom < rect.bottom) ? 3 : 2 : !rect3.contains(rect2) ? 0 : 1;
    }

    private void shiftPopup() {
        ValueAnimator ofInt = ValueAnimator.ofInt(this.mCoordsOnWindow.x, this.mViewPortOnScreen.left - this.mMarginHorizontal);
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.16
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                LocalFloatingToolbarPopup.this.mCoordsOnWindow.x = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                LocalFloatingToolbarPopup.this.recalCoordsOnWindowX();
                LocalFloatingToolbarPopup.this.mPopupWindow.update(LocalFloatingToolbarPopup.this.mCoordsOnWindow.x, LocalFloatingToolbarPopup.this.mCoordsOnWindow.y, LocalFloatingToolbarPopup.this.mPopupWindow.getWidth(), LocalFloatingToolbarPopup.this.mPopupWindow.getHeight());
            }
        });
        ofInt.setDuration(100L);
        ofInt.start();
    }

    private void changeOverflowPanelAdapterOrder() {
        ArrayList arrayList = new ArrayList(this.mOverflowMenuItems);
        if (this.mOpenOverflowUpwards) {
            Collections.reverse(arrayList);
        }
        ArrayAdapter arrayAdapter = (ArrayAdapter) this.mOverflowPanel.getAdapter();
        arrayAdapter.clear();
        arrayAdapter.addAll(arrayList);
        this.mOverflowPanel.setAdapter((ListAdapter) arrayAdapter);
        if (this.mOpenOverflowUpwards) {
            this.mOverflowPanel.setSelection(arrayAdapter.getCount() - 1);
        }
    }

    private void createDividers() {
        ImageView imageView = new ImageView(this.mContext);
        this.mDividerVertical = imageView;
        imageView.setImageResource(R.drawable.tw_floating_popup_divider);
        this.mDividerVertical.setLayoutParams(new ViewGroup.LayoutParams(-2, -1));
        this.mDividerVertical.setImportantForAccessibility(2);
        this.mDividerVertical.setEnabled(false);
        this.mDividerVertical.setFocusable(false);
        this.mDividerVertical.setContentDescription(null);
        ImageView imageView2 = new ImageView(this.mContext);
        this.mDividerHorizontal = imageView2;
        imageView2.setImageResource(R.drawable.tw_floating_popup_divider_horizontal);
        this.mDividerHorizontal.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        this.mDividerHorizontal.setImportantForAccessibility(2);
        this.mDividerHorizontal.setEnabled(false);
        this.mDividerHorizontal.setFocusable(false);
        this.mDividerHorizontal.setContentDescription(null);
    }

    private boolean isCutoutMarginSet() {
        return (mCutoutLeftMargin == 0 && mCutoutRightMargin == 0) ? false : true;
    }

    private DisplayCutout getDisplayCutout() {
        WindowInsets rootWindowInsets;
        DisplayCutout displayCutout;
        if (!CoreRune.FW_CHANGE_DISPLAY_CUTOUT_MODE) {
            View view = this.mParentRoot;
            if (view != null && (rootWindowInsets = view.getRootWindowInsets()) != null && (displayCutout = rootWindowInsets.getDisplayCutout()) != null) {
                return displayCutout;
            }
        } else {
            Context context = this.mContext;
            if (context != null && context.getDisplay() != null) {
                return this.mContext.getDisplay().getCutout();
            }
        }
        mCutoutRightMargin = 0;
        mCutoutLeftMargin = 0;
        return null;
    }

    private void setCutoutMarginValue(DisplayCutout displayCutout) {
        List<Rect> boundingRects = displayCutout.getBoundingRects();
        if (boundingRects == null || boundingRects.isEmpty()) {
            return;
        }
        Rect rect = new Rect();
        this.mParentRoot.getWindowDisplayFrame(rect);
        for (Rect rect2 : boundingRects) {
            int i = rect2.right - rect2.left;
            if (rect2.left == 0) {
                mCutoutLeftMargin = i;
                mCutoutRightMargin = rect.right;
            } else if (rect2.right == rect.right) {
                mCutoutLeftMargin = 0;
                mCutoutRightMargin = rect.right - i;
            } else {
                mCutoutRightMargin = 0;
                mCutoutLeftMargin = 0;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recalCoordsOnWindowX() {
        DisplayCutout displayCutout = getDisplayCutout();
        if (displayCutout != null) {
            setCutoutMarginValue(displayCutout);
        }
        if (isCutoutMarginSet()) {
            int rotation = ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
            if (rotation == 1) {
                if (isInRTLMode()) {
                    return;
                }
                Point point = this.mCoordsOnWindow;
                int i = point.x;
                int i2 = mCutoutLeftMargin;
                if (i >= i2) {
                    i2 = this.mCoordsOnWindow.x;
                }
                point.x = i2;
                return;
            }
            if (rotation == 3) {
                int width = this.mPopupWindow.getWidth();
                if (hasOverflow()) {
                    width = (width + this.mOverflowPanelSize.getWidth()) / 2;
                }
                Point point2 = this.mCoordsOnWindow;
                int i3 = point2.x + width;
                int i4 = mCutoutRightMargin;
                point2.x = i3 > i4 ? i4 - width : this.mCoordsOnWindow.x;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static View createMenuItemButton(Context context, MenuItem menuItem, int i, boolean z) {
        View inflate = LayoutInflater.from(context).inflate(sIsSemType ? R.layout.sem_floating_popup_menu_button : R.layout.floating_popup_menu_button, (ViewGroup) null);
        if (menuItem != null) {
            updateMenuItemButton(inflate, menuItem, i, z);
        }
        inflate.semSetHoverPopupType(0);
        return inflate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void updateMenuItemButton(View view, MenuItem menuItem, int i, boolean z) {
        TextView textView = (TextView) view.findViewById(R.id.floating_toolbar_menu_item_text);
        textView.setEllipsize(null);
        if (TextUtils.isEmpty(menuItem.getTitle())) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
            textView.lambda$setTextAsync$0(menuItem.getTitle());
        }
        View findViewById = sIsSemType ? view.findViewById(R.id.intelli_menu_divider) : null;
        ImageView imageView = (ImageView) view.findViewById(R.id.floating_toolbar_menu_item_image);
        if (menuItem.getIcon() == null || !z) {
            imageView.setVisibility(8);
            if (textView != null) {
                textView.setPaddingRelative(0, 0, 0, 0);
            }
        } else {
            imageView.setVisibility(0);
            imageView.lambda$setImageURIAsync$2(menuItem.getIcon());
            if (textView != null) {
                if (sIsSemType) {
                    mSpacingFirstButton = true;
                    findViewById.setVisibility(0);
                    textView.setPaddingRelative(i, 0, i, 0);
                } else {
                    textView.setPaddingRelative(i, 0, 0, 0);
                }
            }
        }
        CharSequence contentDescription = menuItem.getContentDescription();
        if (TextUtils.isEmpty(contentDescription)) {
            view.setContentDescription(menuItem.getTitle());
        } else {
            view.setContentDescription(contentDescription);
        }
    }

    private static ViewGroup createContentContainer(Context context) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(sIsSemType ? R.layout.sem_floating_popup_container : R.layout.floating_popup_container, (ViewGroup) null);
        viewGroup.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        viewGroup.setTag(FloatingToolbar.FLOATING_TOOLBAR_TAG);
        viewGroup.setClipToOutline(true);
        return viewGroup;
    }

    private static PopupWindow createPopupWindow(ViewGroup viewGroup) {
        LinearLayout linearLayout = new LinearLayout(viewGroup.getContext());
        linearLayout.setGravity(0);
        PopupWindow popupWindow = new PopupWindow(linearLayout);
        popupWindow.setClippingEnabled(false);
        popupWindow.setWindowLayoutType(1005);
        popupWindow.setAnimationStyle(0);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        viewGroup.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        linearLayout.addView(viewGroup);
        return popupWindow;
    }

    private static AnimatorSet createEnterAnimation(View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(view, View.ALPHA, 0.0f, 1.0f).setDuration(150L));
        return animatorSet;
    }

    private static AnimatorSet createExitAnimation(View view, int i, Animator.AnimatorListener animatorListener) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(view, View.ALPHA, 1.0f, 0.0f).setDuration(100L));
        animatorSet.setStartDelay(i);
        animatorSet.addListener(animatorListener);
        return animatorSet;
    }

    private static Context applyDefaultTheme(Context context) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{16844176});
        boolean z = obtainStyledAttributes.getBoolean(0, true);
        boolean z2 = (context.getResources().getConfiguration().uiMode & 48) != 32;
        if (sIsSemType && z != z2) {
            z = z2;
        }
        int i = z ? 16974123 : 16974120;
        obtainStyledAttributes.recycle();
        return new ContextThemeWrapper(context, i);
    }

    public static final class MenuItemRepr {
        public final int groupId;
        public final int itemId;
        private final Drawable mIcon;
        public final String title;

        private MenuItemRepr(int i, int i2, CharSequence charSequence, Drawable drawable) {
            this.itemId = i;
            this.groupId = i2;
            this.title = charSequence == null ? null : charSequence.toString();
            this.mIcon = drawable;
        }

        public static MenuItemRepr of(MenuItem menuItem) {
            return new MenuItemRepr(menuItem.getItemId(), menuItem.getGroupId(), menuItem.getTitle(), menuItem.getIcon());
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.itemId), Integer.valueOf(this.groupId), this.title, this.mIcon);
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof MenuItemRepr)) {
                return false;
            }
            MenuItemRepr menuItemRepr = (MenuItemRepr) obj;
            return this.itemId == menuItemRepr.itemId && this.groupId == menuItemRepr.groupId && TextUtils.equals(this.title, menuItemRepr.title) && Objects.equals(this.mIcon, menuItemRepr.mIcon);
        }

        public static boolean reprEquals(Collection<MenuItem> collection, Collection<MenuItem> collection2) {
            if (collection.size() != collection2.size()) {
                return false;
            }
            Iterator<MenuItem> it = collection2.iterator();
            Iterator<MenuItem> it2 = collection.iterator();
            while (it2.hasNext()) {
                if (!of(it2.next()).equals(of(it.next()))) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override // com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup
    public boolean isDiscardTouch() {
        return sIsDiscardTouch;
    }

    private int getImeHeight() {
        WindowInsets windowInsets = this.mParentRootWindowInset;
        if (windowInsets == null) {
            Log.w(FloatingToolbar.FLOATING_TOOLBAR_TAG, "mParentRootWindowInset is null");
            return 0;
        }
        return windowInsets.getInsets(WindowInsets.Type.ime()).bottom - this.mParentRootWindowInset.getInsets(WindowInsets.Type.navigationBars()).bottom;
    }
}
