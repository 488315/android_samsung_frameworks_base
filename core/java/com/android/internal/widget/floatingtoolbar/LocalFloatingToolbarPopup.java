package com.android.internal.widget.floatingtoolbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
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
import com.android.internal.C4337R;
import com.android.internal.util.Preconditions;
import com.samsung.android.app.SemMultiWindowManager;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class LocalFloatingToolbarPopup implements FloatingToolbarPopup {
  private static final int MAX_OVERFLOW_SIZE = 4;
  private static final int MIN_OVERFLOW_SIZE = 1;
  private static final int NEED_CHANGE_DIRECTION_ALL = 3;
  private static final int NEED_CHANGE_DIRECTION_HORIZONTAL = 2;
  private static final int NEED_CHANGE_DIRECTION_VERTICAL = 1;
  private static final int NEED_NOT_CHANGE_DIRECTION = 0;
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
  private static boolean sIsSemType = false;
  private static boolean sIsMovingStarted = false;
  private static boolean sIsDiscardTouch = false;
  private static boolean sIsScrolling = false;
  private static int mCutoutLeftMargin = 0;
  private static int mCutoutRightMargin = 0;
  private final Rect mViewPortOnScreen = new Rect();
  private final Point mCoordsOnWindow = new Point();
  private final int[] mTmpCoords = new int[2];
  private final Region mTouchableRegion = new Region();
  private final ViewTreeObserver.OnComputeInternalInsetsListener mInsetsComputer =
      new ViewTreeObserver
          .OnComputeInternalInsetsListener() { // from class:
                                               // com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup$$ExternalSyntheticLambda2
        @Override // android.view.ViewTreeObserver.OnComputeInternalInsetsListener
        public final void onComputeInternalInsets(
            ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
          LocalFloatingToolbarPopup.this.lambda$new$0(internalInsetsInfo);
        }
      };
  private final Runnable mPreparePopupContentRTLHelper =
      new Runnable() { // from class:
                       // com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.1
        @Override // java.lang.Runnable
        public void run() {
          LocalFloatingToolbarPopup.this.setPanelsStatesAtRestingPosition();
          LocalFloatingToolbarPopup.this.setContentAreaAsTouchableSurface();
          LocalFloatingToolbarPopup.this.mContentContainer.setAlpha(1.0f);
        }
      };
  private boolean mDismissed = true;
  private final Map<MenuItemRepr, MenuItem> mMenuItems = new LinkedHashMap();
  private final View.OnClickListener mMenuItemButtonOnClickListener =
      new View
          .OnClickListener() { // from class:
                               // com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.2
        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
          MenuItem menuItem;
          if (LocalFloatingToolbarPopup.this.mOnMenuItemClickListener == null) {
            return;
          }
          Object tag = v.getTag();
          if (!(tag instanceof MenuItemRepr)
              || (menuItem =
                      (MenuItem) LocalFloatingToolbarPopup.this.mMenuItems.get((MenuItemRepr) tag))
                  == null) {
            return;
          }
          LocalFloatingToolbarPopup.this.mOnMenuItemClickListener.onMenuItemClick(menuItem);
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
  private final View.OnAttachStateChangeListener mOnAnchorRootDetachedListener =
      new FloatingOnAttachStateChangeListener(this);

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$0(ViewTreeObserver.InternalInsetsInfo info) {
    info.contentInsets.setEmpty();
    info.visibleInsets.setEmpty();
    info.touchableRegion.set(this.mTouchableRegion);
    info.setTouchableInsets(3);
  }

  public LocalFloatingToolbarPopup(Context context, View parent, boolean isSemTypeFloating) {
    sIsSemType = isSemTypeFloating;
    View view = (View) Objects.requireNonNull(parent);
    this.mParent = view;
    Context applyDefaultTheme = applyDefaultTheme(context);
    this.mContext = applyDefaultTheme;
    ViewGroup createContentContainer = createContentContainer(applyDefaultTheme);
    this.mContentContainer = createContentContainer;
    PopupWindow createPopupWindow = createPopupWindow(createContentContainer);
    this.mPopupWindow = createPopupWindow;
    this.mMarginHorizontal =
        parent
            .getResources()
            .getDimensionPixelSize(C4337R.dimen.floating_toolbar_horizontal_margin);
    if (sIsSemType) {
      this.mMarginVertical =
          parent
              .getResources()
              .getDimensionPixelSize(C4337R.dimen.sem_floating_popup_vertical_margin);
      this.mLineHeight =
          context.getResources().getDimensionPixelSize(C4337R.dimen.sem_floating_popup_height);
    } else {
      this.mMarginVertical =
          parent
              .getResources()
              .getDimensionPixelSize(C4337R.dimen.floating_toolbar_vertical_margin);
      this.mLineHeight =
          context.getResources().getDimensionPixelSize(C4337R.dimen.floating_toolbar_height);
    }
    int dimensionPixelSize =
        context
            .getResources()
            .getDimensionPixelSize(C4337R.dimen.floating_toolbar_icon_text_spacing);
    this.mIconTextSpacing = dimensionPixelSize;
    this.mLogAccelerateInterpolator = new LogAccelerateInterpolator();
    this.mFastOutSlowInInterpolator = AnimationUtils.loadInterpolator(applyDefaultTheme, 17563661);
    this.mLinearOutSlowInInterpolator =
        AnimationUtils.loadInterpolator(applyDefaultTheme, 17563662);
    this.mFastOutLinearInInterpolator =
        AnimationUtils.loadInterpolator(applyDefaultTheme, 17563663);
    Drawable drawable =
        applyDefaultTheme
            .getResources()
            .getDrawable(C4337R.drawable.ft_avd_tooverflow, applyDefaultTheme.getTheme());
    this.mArrow = drawable;
    drawable.setAutoMirrored(true);
    Drawable drawable2 =
        applyDefaultTheme
            .getResources()
            .getDrawable(C4337R.drawable.ft_avd_toarrow, applyDefaultTheme.getTheme());
    this.mOverflow = drawable2;
    drawable2.setAutoMirrored(true);
    AnimatedVectorDrawable animatedVectorDrawable =
        (AnimatedVectorDrawable)
            applyDefaultTheme
                .getResources()
                .getDrawable(
                    C4337R.drawable.ft_avd_toarrow_animation, applyDefaultTheme.getTheme());
    this.mToArrow = animatedVectorDrawable;
    animatedVectorDrawable.setAutoMirrored(true);
    AnimatedVectorDrawable animatedVectorDrawable2 =
        (AnimatedVectorDrawable)
            applyDefaultTheme
                .getResources()
                .getDrawable(
                    C4337R.drawable.ft_avd_tooverflow_animation, applyDefaultTheme.getTheme());
    this.mToOverflow = animatedVectorDrawable2;
    animatedVectorDrawable2.setAutoMirrored(true);
    ImageButton createOverflowButton = createOverflowButton();
    this.mOverflowButton = createOverflowButton;
    this.mOverflowButtonSize = measure(createOverflowButton);
    this.mMainPanel = createMainPanel();
    this.mOverflowPanelViewHelper =
        new OverflowPanelViewHelper(applyDefaultTheme, dimensionPixelSize);
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
    this.mDismissAnimation =
        createExitAnimation(
            createContentContainer,
            150,
            new AnimatorListenerAdapter() { // from class:
                                            // com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.3
              @Override // android.animation.AnimatorListenerAdapter,
                        // android.animation.Animator.AnimatorListener
              public void onAnimationEnd(Animator animation) {
                LocalFloatingToolbarPopup.this.mPopupWindow.dismiss();
                LocalFloatingToolbarPopup.this.mContentContainer.removeAllViews();
                if (LocalFloatingToolbarPopup.this.mParentRoot != null) {
                  LocalFloatingToolbarPopup.this.mParentRoot.removeOnAttachStateChangeListener(
                      LocalFloatingToolbarPopup.this.mOnAnchorRootDetachedListener);
                }
              }
            });
    this.mHideAnimation =
        createExitAnimation(
            createContentContainer,
            0,
            new AnimatorListenerAdapter() { // from class:
                                            // com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.4
              @Override // android.animation.AnimatorListenerAdapter,
                        // android.animation.Animator.AnimatorListener
              public void onAnimationEnd(Animator animation) {
                LocalFloatingToolbarPopup.this.mPopupWindow.dismiss();
              }
            });
    this.mMenuFirstLastSidePadding =
        parent
            .getResources()
            .getDimensionPixelSize(C4337R.dimen.sem_floating_popup_menu_first_last_side_padding);
    this.mMenuFirstImageStartPadding =
        parent
            .getResources()
            .getDimensionPixelSize(
                C4337R.dimen.sem_floating_popup_menu_image_button_vertical_padding);
    this.mPopupTopMargin =
        parent.getResources().getDimensionPixelSize(C4337R.dimen.sem_floating_popup_top_margin);
    this.mPopupVerticalOffset =
        parent
            .getResources()
            .getDimensionPixelSize(C4337R.dimen.sem_floating_popup_vertical_offset);
    this.mArrowSem =
        applyDefaultTheme
            .getResources()
            .getDrawable(C4337R.drawable.tw_ic_ab_back_material, applyDefaultTheme.getTheme());
    createDividers();
    ViewConfiguration viewConfig = ViewConfiguration.get(applyDefaultTheme);
    this.mTouchSlop = viewConfig.getScaledTouchSlop();
    createPopupWindow.setTouchInterceptor(
        new View
            .OnTouchListener() { // from class:
                                 // com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.5
          /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
          /* JADX WARN: Code restructure failed: missing block: B:18:0x0147, code lost:

             return false;
          */
          @Override // android.view.View.OnTouchListener
          /*
              Code decompiled incorrectly, please refer to instructions dump.
          */
          public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            switch (action) {
              case 0:
                LocalFloatingToolbarPopup.this.mLastTouchDownX = event.getRawX();
                LocalFloatingToolbarPopup.this.mLastTouchDownY = event.getRawY();
                LocalFloatingToolbarPopup localFloatingToolbarPopup =
                    LocalFloatingToolbarPopup.this;
                localFloatingToolbarPopup.mPrevTouchX = localFloatingToolbarPopup.mLastTouchDownX;
                LocalFloatingToolbarPopup localFloatingToolbarPopup2 =
                    LocalFloatingToolbarPopup.this;
                localFloatingToolbarPopup2.mPrevTouchY = localFloatingToolbarPopup2.mLastTouchDownY;
                LocalFloatingToolbarPopup.sIsDiscardTouch = false;
                LocalFloatingToolbarPopup.sIsScrolling = false;
                LocalFloatingToolbarPopup.this.mIsMovingFirstTime = false;
                break;
              case 2:
                LocalFloatingToolbarPopup.this.mMoved = true;
                if (!LocalFloatingToolbarPopup.sIsScrolling) {
                  float x = event.getRawX();
                  float y = event.getRawY();
                  LocalFloatingToolbarPopup localFloatingToolbarPopup3 =
                      LocalFloatingToolbarPopup.this;
                  localFloatingToolbarPopup3.mDeltaX =
                      (int) (x - localFloatingToolbarPopup3.mPrevTouchX);
                  LocalFloatingToolbarPopup localFloatingToolbarPopup4 =
                      LocalFloatingToolbarPopup.this;
                  localFloatingToolbarPopup4.mDeltaY =
                      (int) (y - localFloatingToolbarPopup4.mPrevTouchY);
                  int deltaX = (int) (x - LocalFloatingToolbarPopup.this.mLastTouchDownX);
                  int deltaY = (int) (y - LocalFloatingToolbarPopup.this.mLastTouchDownY);
                  boolean prevIsMovingFirstTime = LocalFloatingToolbarPopup.this.mIsMovingFirstTime;
                  if ((deltaX * deltaX) + (deltaY * deltaY)
                      >= LocalFloatingToolbarPopup.this.mTouchSlop
                          * LocalFloatingToolbarPopup.this.mTouchSlop) {
                    LocalFloatingToolbarPopup.sIsDiscardTouch = true;
                    LocalFloatingToolbarPopup.sIsMovingStarted = true;
                    LocalFloatingToolbarPopup.this.mIsMovingFirstTime = true;
                  }
                  if (prevIsMovingFirstTime != LocalFloatingToolbarPopup.this.mIsMovingFirstTime) {
                    Log.m94d(
                        FloatingToolbar.FLOATING_TOOLBAR_TAG,
                        "FloatingToolbar will be start to move, moved deltaX, deltaY : "
                            + deltaX
                            + ", "
                            + deltaY
                            + "\nmTouchSlop = "
                            + LocalFloatingToolbarPopup.this.mTouchSlop);
                  }
                  if (LocalFloatingToolbarPopup.sIsDiscardTouch) {
                    if (LocalFloatingToolbarPopup.this.isInsideOfViewPortRect(x, y)) {
                      LocalFloatingToolbarPopup localFloatingToolbarPopup5 =
                          LocalFloatingToolbarPopup.this;
                      localFloatingToolbarPopup5.calculateCoords(
                          localFloatingToolbarPopup5.mCoordsOnWindow.f85x
                              + LocalFloatingToolbarPopup.this.mDeltaX,
                          LocalFloatingToolbarPopup.this.mCoordsOnWindow.f86y
                              + LocalFloatingToolbarPopup.this.mDeltaY);
                    }
                    LocalFloatingToolbarPopup.this.recalCoordsOnWindowX();
                    LocalFloatingToolbarPopup.this.mPopupWindow.update(
                        LocalFloatingToolbarPopup.this.mCoordsOnWindow.f85x,
                        LocalFloatingToolbarPopup.this.mCoordsOnWindow.f86y,
                        LocalFloatingToolbarPopup.this.mPopupWindow.getWidth(),
                        LocalFloatingToolbarPopup.this.mPopupWindow.getHeight());
                    LocalFloatingToolbarPopup.this.mPrevTouchX = x;
                    LocalFloatingToolbarPopup.this.mPrevTouchY = y;
                    break;
                  }
                }
                break;
            }
          }
        });
    this.mParentRoot = view.getRootView();
    this.mParentRootWindowInset = view.getRootWindowInsets();
  }

  @Override // com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup
  public boolean setOutsideTouchable(
      boolean outsideTouchable, PopupWindow.OnDismissListener onDismiss) {
    boolean ret = false;
    if (this.mPopupWindow.isOutsideTouchable() ^ outsideTouchable) {
      this.mPopupWindow.setOutsideTouchable(outsideTouchable);
      this.mPopupWindow.setFocusable(!outsideTouchable);
      this.mPopupWindow.update();
      ret = true;
    }
    this.mPopupWindow.setOnDismissListener(onDismiss);
    return ret;
  }

  private void layoutMenuItems(
      List<MenuItem> menuItems,
      MenuItem.OnMenuItemClickListener menuItemClickListener,
      int suggestedWidth) {
    cancelOverflowAnimations();
    clearPanels();
    updateMenuItems(menuItems, menuItemClickListener);
    List<MenuItem> menuItems2 =
        layoutMainPanelItems(menuItems, getAdjustedToolbarWidth(suggestedWidth));
    if (!menuItems2.isEmpty()) {
      layoutOverflowPanelItems(menuItems2);
    }
    updatePopupSize();
  }

  private void updateMenuItems(
      List<MenuItem> menuItems, MenuItem.OnMenuItemClickListener menuItemClickListener) {
    this.mMenuItems.clear();
    for (MenuItem menuItem : menuItems) {
      this.mMenuItems.put(MenuItemRepr.m230of(menuItem), menuItem);
    }
    this.mOnMenuItemClickListener = menuItemClickListener;
  }

  private boolean isLayoutRequired(List<MenuItem> menuItems) {
    return !MenuItemRepr.reprEquals(menuItems, this.mMenuItems.values());
  }

  @Override // com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup
  public void setWidthChanged(boolean widthChanged) {
    this.mWidthChanged = widthChanged;
  }

  @Override // com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup
  public void setSuggestedWidth(int suggestedWidth) {
    int difference = Math.abs(suggestedWidth - this.mSuggestedWidth);
    this.mWidthChanged = ((double) difference) > ((double) this.mSuggestedWidth) * 0.2d;
    this.mSuggestedWidth = suggestedWidth;
  }

  @Override // com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup
  public void show(
      List<MenuItem> menuItems,
      MenuItem.OnMenuItemClickListener menuItemClickListener,
      Rect contentRect) {
    if (isLayoutRequired(menuItems) || this.mWidthChanged) {
      dismiss();
      layoutMenuItems(menuItems, menuItemClickListener, this.mSuggestedWidth);
    } else {
      updateMenuItems(menuItems, menuItemClickListener);
    }
    if (!isShowing()) {
      show(contentRect);
    } else if (!this.mPreviousContentRect.equals(contentRect)) {
      updateCoordinates(contentRect);
    }
    this.mWidthChanged = false;
    this.mPreviousContentRect.set(contentRect);
  }

  private void show(Rect contentRectOnScreen) {
    Objects.requireNonNull(contentRectOnScreen);
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
    refreshCoordinatesAndOverflowDirection(contentRectOnScreen);
    preparePopupContent();
    recalCoordsOnWindowX();
    this.mPopupWindow.showAtLocation(
        this.mParent, 0, this.mCoordsOnWindow.f85x, this.mCoordsOnWindow.f86y);
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
    if (!isShowing()) {
      return;
    }
    this.mHidden = true;
    runHideAnimation();
    setZeroTouchableSurface();
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
  public void setIsMovingStarted(boolean isMovingStarted) {
    sIsMovingStarted = isMovingStarted;
  }

  @Override // com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup
  public boolean isMovingStarted() {
    return sIsMovingStarted;
  }

  private void updateCoordinates(Rect contentRectOnScreen) {
    Objects.requireNonNull(contentRectOnScreen);
    if (!isShowing() || !this.mPopupWindow.isShowing()) {
      return;
    }
    cancelOverflowAnimations();
    refreshCoordinatesAndOverflowDirection(contentRectOnScreen);
    preparePopupContent();
    recalCoordsOnWindowX();
    this.mPopupWindow.update(
        this.mCoordsOnWindow.f85x,
        this.mCoordsOnWindow.f86y,
        this.mPopupWindow.getWidth(),
        this.mPopupWindow.getHeight());
  }

  private void refreshCoordinatesAndOverflowDirection(Rect contentRectOnScreen) {
    int y;
    int minimumOverflowHeightWithMargin;
    refreshViewPort();
    if (isMovingStarted()) {
      return;
    }
    int x =
        Math.min(
            contentRectOnScreen.centerX() - (this.mPopupWindow.getWidth() / 2),
            this.mViewPortOnScreen.right - this.mPopupWindow.getWidth());
    if (hasOverflow()) {
      int halfPopupWindowWidth =
          (this.mPopupWindow.getWidth() + this.mOverflowPanelSize.getWidth()) / 2;
      x =
          Math.min(
              contentRectOnScreen.centerX() - (halfPopupWindowWidth / 2),
              (this.mViewPortOnScreen.right - halfPopupWindowWidth) - this.mMarginHorizontal);
    }
    int offsetY = this.mPopupVerticalOffset;
    int availableHeightAboveContent = contentRectOnScreen.top - this.mViewPortOnScreen.top;
    int availableHeightBelowContent = this.mViewPortOnScreen.bottom - contentRectOnScreen.bottom;
    int margin = this.mMarginVertical * 2;
    int toolbarHeightWithVerticalMargin = this.mLineHeight + margin;
    if (!hasOverflow()) {
      if (availableHeightAboveContent >= toolbarHeightWithVerticalMargin) {
        minimumOverflowHeightWithMargin = contentRectOnScreen.top - toolbarHeightWithVerticalMargin;
      } else if (availableHeightBelowContent >= toolbarHeightWithVerticalMargin) {
        minimumOverflowHeightWithMargin = contentRectOnScreen.bottom;
      } else {
        int y2 = this.mLineHeight;
        if (availableHeightBelowContent >= y2) {
          minimumOverflowHeightWithMargin = contentRectOnScreen.bottom - this.mMarginVertical;
        } else {
          minimumOverflowHeightWithMargin =
              Math.max(
                  this.mViewPortOnScreen.top,
                  contentRectOnScreen.top - toolbarHeightWithVerticalMargin);
        }
      }
    } else {
      int minimumOverflowHeightWithMargin2 = calculateOverflowHeight(1) + margin;
      int availableHeightThroughContentDown =
          (this.mViewPortOnScreen.bottom - contentRectOnScreen.top)
              + toolbarHeightWithVerticalMargin;
      int availableHeightThroughContentUp =
          (contentRectOnScreen.bottom - this.mViewPortOnScreen.top)
              + toolbarHeightWithVerticalMargin;
      int toolbarAreaHeight =
          this.mPopupWindow.getHeight()
              - (this.mOverflowPanelSize.getHeight() - this.mMainPanelSize.getHeight());
      if (availableHeightAboveContent >= minimumOverflowHeightWithMargin2) {
        if (availableHeightAboveContent >= availableHeightThroughContentDown) {
          updateOverflowHeight(availableHeightAboveContent - margin);
          y = contentRectOnScreen.top - toolbarAreaHeight;
          this.mOpenOverflowUpwards = true;
        } else {
          int y3 = availableHeightThroughContentDown - margin;
          updateOverflowHeight(y3);
          y = contentRectOnScreen.top - toolbarHeightWithVerticalMargin;
          this.mOpenOverflowUpwards = false;
        }
      } else if (availableHeightAboveContent >= toolbarHeightWithVerticalMargin
          && availableHeightThroughContentDown >= minimumOverflowHeightWithMargin2) {
        updateOverflowHeight(availableHeightThroughContentDown - margin);
        y = contentRectOnScreen.top - toolbarHeightWithVerticalMargin;
        this.mOpenOverflowUpwards = false;
      } else if (availableHeightBelowContent >= minimumOverflowHeightWithMargin2) {
        if (availableHeightBelowContent >= availableHeightThroughContentUp) {
          y = contentRectOnScreen.bottom;
          updateOverflowHeight(availableHeightBelowContent - margin);
          this.mOpenOverflowUpwards = false;
        } else {
          int y4 = availableHeightThroughContentUp - margin;
          updateOverflowHeight(y4);
          y = (contentRectOnScreen.bottom + toolbarHeightWithVerticalMargin) - toolbarAreaHeight;
          this.mOpenOverflowUpwards = true;
        }
      } else if (availableHeightBelowContent < toolbarHeightWithVerticalMargin
          || this.mViewPortOnScreen.height() < minimumOverflowHeightWithMargin2) {
        updateOverflowHeight(this.mViewPortOnScreen.height() - margin);
        y = this.mViewPortOnScreen.top;
        this.mOpenOverflowUpwards = false;
      } else {
        updateOverflowHeight(availableHeightThroughContentUp - margin);
        y = (contentRectOnScreen.bottom + toolbarHeightWithVerticalMargin) - toolbarAreaHeight;
        this.mOpenOverflowUpwards = true;
      }
      if (hasOverflow()) {
        offsetX =
            isInRTLMode()
                ? 0 - (this.mMainPanelSize.getWidth() - this.mOverflowPanelSize.getWidth())
                : 0;
        if (!this.mOpenOverflowUpwards) {
          offsetY -= this.mOverflowPanelSize.getHeight() - this.mMainPanelSize.getHeight();
          minimumOverflowHeightWithMargin = y;
        } else {
          minimumOverflowHeightWithMargin = y;
        }
      } else {
        minimumOverflowHeightWithMargin = y;
      }
    }
    this.mParent.getRootView().getLocationOnScreen(this.mTmpCoords);
    int[] iArr = this.mTmpCoords;
    int rootViewLeftOnScreen = iArr[0];
    int rootViewTopOnScreen = iArr[1];
    this.mParent.getRootView().getLocationInWindow(this.mTmpCoords);
    int[] iArr2 = this.mTmpCoords;
    int rootViewLeftOnWindow = iArr2[0];
    int rootViewTopOnWindow = iArr2[1];
    int windowLeftOnScreen = rootViewLeftOnScreen - rootViewLeftOnWindow;
    int windowTopOnScreen = rootViewTopOnScreen - rootViewTopOnWindow;
    this.mCoordsOnWindow.set(
        Math.max(
            Math.max(this.mViewPortOnScreen.left, 0) - windowLeftOnScreen, x - windowLeftOnScreen),
        Math.max(
            Math.max(this.mViewPortOnScreen.top, 0) - windowTopOnScreen,
            minimumOverflowHeightWithMargin - windowTopOnScreen));
    this.mCoordsOnWindow.offset(offsetX, offsetY);
    if (!this.mMoved) {
      this.mMovedPos.set(0, 0);
      this.mOriginalPos.set(this.mCoordsOnWindow.f85x, this.mCoordsOnWindow.f86y);
    }
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
    int needToChangeDirection = isNeedToChangeDirection();
    if (needToChangeDirection == 1 || needToChangeDirection == 3) {
      this.mOpenOverflowUpwards = !this.mOpenOverflowUpwards;
    }
    if (needToChangeDirection == 2 || needToChangeDirection == 3) {
      boolean isInRTLMode = isInRTLMode();
      boolean z = this.mIsClosedOpposites;
      if (isInRTLMode == z) {
        this.mIsClosedOpposites = !z;
        if (this.mCoordsOnWindow.f85x
                + this.mContentContainer.getX()
                + this.mOverflowPanelSize.getWidth()
            > this.mViewPortOnScreen.right) {
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
    final int targetWidth = this.mOverflowPanelSize.getWidth();
    final int targetHeight = this.mOverflowPanelSize.getHeight();
    final int startWidth = this.mContentContainer.getWidth();
    final int startHeight = this.mContentContainer.getHeight();
    final float startY = this.mContentContainer.getY();
    final float left = this.mContentContainer.getX();
    final float right = left + this.mContentContainer.getWidth();
    Animation widthAnimation =
        new Animation() { // from class:
                          // com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.6
          @Override // android.view.animation.Animation
          protected void applyTransformation(float interpolatedTime, Transformation t) {
            float x;
            int deltaWidth = (int) ((targetWidth - startWidth) * interpolatedTime);
            LocalFloatingToolbarPopup.setWidth(
                LocalFloatingToolbarPopup.this.mContentContainer, startWidth + deltaWidth);
            if (LocalFloatingToolbarPopup.this.isInRTLMode()
                != LocalFloatingToolbarPopup.this.mIsClosedOpposites) {
              x = left;
            } else {
              float x2 = right;
              x = x2 - LocalFloatingToolbarPopup.this.mContentContainer.getWidth();
            }
            LocalFloatingToolbarPopup.this.mContentContainer.setX(x);
            if (LocalFloatingToolbarPopup.this.isInRTLMode()) {
              LocalFloatingToolbarPopup.this.mMainPanel.setX(0.0f);
              LocalFloatingToolbarPopup.this.mOverflowPanel.setX(0.0f);
            } else {
              LocalFloatingToolbarPopup.this.mMainPanel.setX(
                  LocalFloatingToolbarPopup.this.mContentContainer.getWidth() - startWidth);
              LocalFloatingToolbarPopup.this.mOverflowPanel.setX(
                  LocalFloatingToolbarPopup.this.mContentContainer.getWidth() - targetWidth);
            }
          }
        };
    Animation heightAnimation =
        new Animation() { // from class:
                          // com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.7
          @Override // android.view.animation.Animation
          protected void applyTransformation(float interpolatedTime, Transformation t) {
            int deltaHeight = (int) ((targetHeight - startHeight) * interpolatedTime);
            LocalFloatingToolbarPopup.setHeight(
                LocalFloatingToolbarPopup.this.mContentContainer, startHeight + deltaHeight);
            if (LocalFloatingToolbarPopup.this.mOpenOverflowUpwards) {
              LocalFloatingToolbarPopup.this.mContentContainer.setY(
                  startY
                      - (LocalFloatingToolbarPopup.this.mContentContainer.getHeight()
                          - startHeight));
              LocalFloatingToolbarPopup.this.positionContentYCoordinatesIfOpeningOverflowUpwards();
            }
          }
        };
    final float overflowButtonStartX = this.mOverflowButton.getX();
    final float overflowButtonTargetX =
        isInRTLMode()
            ? (targetWidth + overflowButtonStartX) - this.mOverflowButton.getWidth()
            : (overflowButtonStartX - targetWidth) + this.mOverflowButton.getWidth();
    Animation overflowButtonAnimation =
        new Animation() { // from class:
                          // com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.8
          @Override // android.view.animation.Animation
          protected void applyTransformation(float interpolatedTime, Transformation t) {
            float f = overflowButtonStartX;
            float overflowButtonX = f + ((overflowButtonTargetX - f) * interpolatedTime);
            float deltaContainerWidth =
                LocalFloatingToolbarPopup.this.isInRTLMode()
                    ? 0.0f
                    : LocalFloatingToolbarPopup.this.mContentContainer.getWidth() - startWidth;
            float actualOverflowButtonX = overflowButtonX + deltaContainerWidth;
            LocalFloatingToolbarPopup.this.mOverflowButton.setX(actualOverflowButtonX);
          }
        };
    widthAnimation.setInterpolator(this.mLogAccelerateInterpolator);
    widthAnimation.setDuration(getAdjustedDuration(250));
    heightAnimation.setInterpolator(this.mFastOutSlowInInterpolator);
    heightAnimation.setDuration(getAdjustedDuration(250));
    overflowButtonAnimation.setInterpolator(this.mFastOutSlowInInterpolator);
    overflowButtonAnimation.setDuration(getAdjustedDuration(250));
    this.mOpenOverflowAnimation.getAnimations().clear();
    this.mOpenOverflowAnimation.getAnimations().clear();
    this.mOpenOverflowAnimation.addAnimation(widthAnimation);
    this.mOpenOverflowAnimation.addAnimation(heightAnimation);
    this.mOpenOverflowAnimation.addAnimation(overflowButtonAnimation);
    this.mContentContainer.startAnimation(this.mOpenOverflowAnimation);
    this.mIsOverflowOpen = true;
    this.mMainPanel
        .animate()
        .alpha(0.0f)
        .withLayer()
        .setInterpolator(this.mLinearOutSlowInInterpolator)
        .setDuration(250L)
        .start();
    this.mOverflowPanel.setAlpha(1.0f);
  }

  private void closeOverflow() {
    int needToChangeDirection = isNeedToChangeDirection();
    if (needToChangeDirection == 2) {
      boolean isInRTLMode = isInRTLMode();
      boolean z = this.mIsClosedOpposites;
      if (isInRTLMode == z) {
        this.mIsClosedOpposites = !z;
        if (this.mCoordsOnWindow.f85x
                + this.mContentContainer.getX()
                + this.mMainPanelSize.getWidth()
            > this.mViewPortOnScreen.right) {
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
    final int targetWidth = this.mMainPanelSize.getWidth();
    final int startWidth = this.mContentContainer.getWidth();
    final float left = this.mContentContainer.getX();
    final float right = left + this.mContentContainer.getWidth();
    Animation widthAnimation =
        new Animation() { // from class:
                          // com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.9
          @Override // android.view.animation.Animation
          protected void applyTransformation(float interpolatedTime, Transformation t) {
            int deltaWidth = (int) ((targetWidth - startWidth) * interpolatedTime);
            LocalFloatingToolbarPopup.setWidth(
                LocalFloatingToolbarPopup.this.mContentContainer, startWidth + deltaWidth);
            float x = right - LocalFloatingToolbarPopup.this.mContentContainer.getWidth();
            if (LocalFloatingToolbarPopup.this.isInRTLMode()
                != LocalFloatingToolbarPopup.this.mIsClosedOpposites) {
              x = left;
            }
            LocalFloatingToolbarPopup.this.mContentContainer.setX(x);
            if (LocalFloatingToolbarPopup.this.isInRTLMode()) {
              LocalFloatingToolbarPopup.this.mMainPanel.setX(0.0f);
              LocalFloatingToolbarPopup.this.mOverflowPanel.setX(0.0f);
            } else {
              LocalFloatingToolbarPopup.this.mMainPanel.setX(
                  LocalFloatingToolbarPopup.this.mContentContainer.getWidth() - targetWidth);
              LocalFloatingToolbarPopup.this.mOverflowPanel.setX(
                  LocalFloatingToolbarPopup.this.mContentContainer.getWidth() - startWidth);
            }
          }
        };
    final int targetHeight = this.mMainPanelSize.getHeight();
    final int startHeight = this.mContentContainer.getHeight();
    final float bottom = this.mContentContainer.getY() + this.mContentContainer.getHeight();
    Animation heightAnimation =
        new Animation() { // from class:
                          // com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.10
          @Override // android.view.animation.Animation
          protected void applyTransformation(float interpolatedTime, Transformation t) {
            int deltaHeight = (int) ((targetHeight - startHeight) * interpolatedTime);
            LocalFloatingToolbarPopup.setHeight(
                LocalFloatingToolbarPopup.this.mContentContainer, startHeight + deltaHeight);
            if (LocalFloatingToolbarPopup.this.mOpenOverflowUpwards) {
              LocalFloatingToolbarPopup.this.mContentContainer.setY(
                  bottom - LocalFloatingToolbarPopup.this.mContentContainer.getHeight());
              LocalFloatingToolbarPopup.this.positionContentYCoordinatesIfOpeningOverflowUpwards();
            }
          }
        };
    final float overflowButtonStartX = this.mOverflowButton.getX();
    final float overflowButtonTargetX =
        isInRTLMode()
            ? (overflowButtonStartX - startWidth) + this.mOverflowButton.getWidth()
            : (startWidth + overflowButtonStartX) - this.mOverflowButton.getWidth();
    Animation overflowButtonAnimation =
        new Animation() { // from class:
                          // com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.11
          @Override // android.view.animation.Animation
          protected void applyTransformation(float interpolatedTime, Transformation t) {
            float f = overflowButtonStartX;
            float overflowButtonX = f + ((overflowButtonTargetX - f) * interpolatedTime);
            float deltaContainerWidth =
                LocalFloatingToolbarPopup.this.isInRTLMode()
                    ? 0.0f
                    : LocalFloatingToolbarPopup.this.mContentContainer.getWidth() - startWidth;
            float actualOverflowButtonX = overflowButtonX + deltaContainerWidth;
            LocalFloatingToolbarPopup.this.mOverflowButton.setX(actualOverflowButtonX);
          }
        };
    widthAnimation.setInterpolator(this.mFastOutSlowInInterpolator);
    widthAnimation.setDuration(getAdjustedDuration(250));
    heightAnimation.setInterpolator(this.mLogAccelerateInterpolator);
    heightAnimation.setDuration(getAdjustedDuration(250));
    overflowButtonAnimation.setInterpolator(this.mFastOutSlowInInterpolator);
    overflowButtonAnimation.setDuration(getAdjustedDuration(250));
    this.mCloseOverflowAnimation.getAnimations().clear();
    this.mCloseOverflowAnimation.addAnimation(widthAnimation);
    this.mCloseOverflowAnimation.addAnimation(heightAnimation);
    this.mCloseOverflowAnimation.addAnimation(overflowButtonAnimation);
    this.mContentContainer.startAnimation(this.mCloseOverflowAnimation);
    this.mIsOverflowOpen = false;
    this.mMainPanel
        .animate()
        .alpha(1.0f)
        .withLayer()
        .setInterpolator(this.mFastOutLinearInInterpolator)
        .setDuration(100L)
        .start();
    this.mOverflowPanel
        .animate()
        .alpha(0.0f)
        .withLayer()
        .setInterpolator(this.mLinearOutSlowInInterpolator)
        .setDuration(150L)
        .start();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void setPanelsStatesAtRestingPosition() {
    int x;
    int x2;
    this.mOverflowButton.setEnabled(true);
    this.mOverflowPanel.awakenScrollBars();
    if (this.mIsOverflowOpen) {
      Size containerSize = this.mOverflowPanelSize;
      setSize(this.mContentContainer, containerSize);
      this.mMainPanel.setAlpha(0.0f);
      this.mMainPanel.setVisibility(4);
      this.mOverflowPanel.setAlpha(1.0f);
      this.mOverflowPanel.setVisibility(0);
      if (sIsSemType) {
        this.mDividerHorizontal.setVisibility(0);
        this.mOverflowButton.lambda$setImageURIAsync$2(this.mArrowSem);
      } else {
        this.mOverflowButton.lambda$setImageURIAsync$2(this.mArrow);
      }
      this.mOverflowButton.setContentDescription(
          this.mContext.getString(C4337R.string.floating_toolbar_close_overflow_description));
      int diff = this.mMainPanelSize.getWidth() - this.mOverflowPanelSize.getWidth();
      if (diff < 0) {
        int absDiff = Math.abs(diff);
        x2 =
            ((this.mPopupWindow.getWidth() - absDiff) - this.mOverflowPanelSize.getWidth())
                - this.mMarginHorizontal;
        if (isInRTLMode() != this.mIsClosedOpposites) {
          x2 =
              (this.mPopupWindow.getWidth() - this.mOverflowPanelSize.getWidth())
                  - this.mMarginHorizontal;
        }
      } else {
        x2 =
            (this.mPopupWindow.getWidth() - this.mMainPanelSize.getWidth())
                - this.mMarginHorizontal;
      }
      this.mContentContainer.setX(x2);
      if (sIsSemType) {
        ViewGroup.LayoutParams params = this.mDividerHorizontal.getLayoutParams();
        params.width = this.mOverflowPanelSize.getWidth();
        this.mDividerHorizontal.setLayoutParams(params);
      }
      if (isInRTLMode()) {
        this.mMainPanel.setX(0.0f);
        this.mOverflowButton.setX(containerSize.getWidth() - this.mOverflowButtonSize.getWidth());
        this.mOverflowPanel.setX(-5.0f);
        if (sIsSemType) {
          this.mDividerHorizontal.setX(0.0f);
        }
      } else {
        this.mMainPanel.setX(-this.mContentContainer.getX());
        this.mOverflowButton.setX(5.0f);
        this.mOverflowPanel.setX(0.0f);
        if (sIsSemType) {
          this.mDividerHorizontal.setX(0.0f);
        }
      }
      if (this.mOpenOverflowUpwards) {
        this.mContentContainer.setY(this.mPopupTopMargin);
        this.mMainPanel.setY(containerSize.getHeight() - this.mContentContainer.getHeight());
        this.mOverflowButton.setY(containerSize.getHeight() - this.mOverflowButtonSize.getHeight());
        this.mOverflowPanel.setY(0.0f);
        if (sIsSemType) {
          this.mDividerHorizontal.setY(
              containerSize.getHeight() - this.mOverflowButtonSize.getHeight());
          return;
        }
        return;
      }
      this.mContentContainer.setY(
          this.mPopupTopMargin
              + (this.mOverflowPanelSize.getHeight() - this.mMainPanelSize.getHeight()));
      this.mMainPanel.setY(0.0f);
      this.mOverflowButton.setY(0.0f);
      this.mOverflowPanel.setY(this.mOverflowButtonSize.getHeight());
      if (sIsSemType) {
        this.mDividerHorizontal.setY(this.mOverflowButtonSize.getHeight());
        return;
      }
      return;
    }
    Size containerSize2 = this.mMainPanelSize;
    setSize(this.mContentContainer, containerSize2);
    this.mMainPanel.setAlpha(1.0f);
    this.mMainPanel.setVisibility(0);
    this.mOverflowPanel.setAlpha(0.0f);
    this.mOverflowPanel.setVisibility(4);
    this.mOverflowButton.lambda$setImageURIAsync$2(this.mOverflow);
    this.mOverflowButton.setContentDescription(
        this.mContext.getString(C4337R.string.floating_toolbar_open_overflow_description));
    if (sIsSemType) {
      this.mDividerHorizontal.setVisibility(4);
    }
    if (hasOverflow()) {
      int diff2 = this.mMainPanelSize.getWidth() - this.mOverflowPanelSize.getWidth();
      if (diff2 < 0) {
        x =
            (this.mPopupWindow.getWidth() - this.mOverflowPanelSize.getWidth())
                - this.mMarginHorizontal;
      } else {
        x =
            ((this.mPopupWindow.getWidth() - diff2) - this.mMainPanelSize.getWidth())
                - this.mMarginHorizontal;
        if (isInRTLMode() != this.mIsClosedOpposites) {
          x =
              (this.mPopupWindow.getWidth() - this.mMainPanelSize.getWidth())
                  - this.mMarginHorizontal;
        }
      }
      this.mContentContainer.setX(x);
      if (isInRTLMode()) {
        this.mMainPanel.setX(0.0f);
        this.mOverflowButton.setX(0.0f);
        this.mOverflowPanel.setX(0.0f);
      } else {
        this.mMainPanel.setX(0.0f);
        this.mOverflowButton.setX(containerSize2.getWidth() - this.mOverflowButtonSize.getWidth());
        this.mOverflowPanel.setX(containerSize2.getWidth() - this.mOverflowPanelSize.getWidth());
      }
      this.mContentContainer.setY(
          (this.mPopupTopMargin + this.mOverflowPanelSize.getHeight())
              - containerSize2.getHeight());
      this.mMainPanel.setY(0.0f);
      this.mOverflowButton.setY(0.0f);
      if (this.mOpenOverflowUpwards) {
        this.mOverflowPanel.setY(containerSize2.getHeight() - this.mOverflowPanelSize.getHeight());
        return;
      } else {
        this.mOverflowPanel.setY(this.mOverflowButtonSize.getHeight());
        return;
      }
    }
    this.mContentContainer.setX(this.mMarginHorizontal);
    this.mContentContainer.setY(this.mPopupTopMargin);
    this.mMainPanel.setX(0.0f);
    this.mMainPanel.setY(0.0f);
  }

  private void updateOverflowHeight(int suggestedHeight) {
    if (hasOverflow()) {
      int maxItemSize = (suggestedHeight - this.mOverflowButtonSize.getHeight()) / this.mLineHeight;
      int newHeight = calculateOverflowHeight(maxItemSize);
      if (newHeight > suggestedHeight) {
        newHeight = suggestedHeight;
      }
      if (this.mOverflowPanelSize.getHeight() != newHeight) {
        this.mOverflowPanelSize = new Size(this.mOverflowPanelSize.getWidth(), newHeight);
      }
      setSize(this.mOverflowPanel, this.mOverflowPanelSize);
      if (this.mIsOverflowOpen) {
        setSize(this.mContentContainer, this.mOverflowPanelSize);
        if (this.mOpenOverflowUpwards) {
          int deltaHeight = this.mOverflowPanelSize.getHeight() - newHeight;
          ViewGroup viewGroup = this.mContentContainer;
          viewGroup.setY(viewGroup.getY() + deltaHeight);
          ImageButton imageButton = this.mOverflowButton;
          imageButton.setY(imageButton.getY() - deltaHeight);
        }
      } else {
        setSize(this.mContentContainer, this.mMainPanelSize);
      }
      updatePopupSize();
    }
  }

  private void updatePopupSize() {
    int width = 0;
    int height = 0;
    Size size = this.mMainPanelSize;
    if (size != null) {
      width = Math.max(0, size.getWidth());
      height = Math.max(0, this.mMainPanelSize.getHeight());
    }
    Size size2 = this.mOverflowPanelSize;
    if (size2 != null) {
      width = Math.max(width, size2.getWidth());
      height = Math.max(height, this.mOverflowPanelSize.getHeight());
      Size size3 = this.mMainPanelSize;
      if (size3 != null) {
        int adbDiff = Math.abs(size3.getWidth() - this.mOverflowPanelSize.getWidth());
        width += adbDiff;
        height = (height * 2) - this.mMainPanelSize.getHeight();
      }
    }
    this.mPopupWindow.setWidth((this.mMarginHorizontal * 2) + width);
    this.mPopupWindow.setHeight((this.mMarginVertical * 2) + height);
    maybeComputeTransitionDurationScale();
  }

  private void refreshViewPort() {
    this.mParent.getWindowVisibleDisplayFrame(this.mViewPortOnScreen);
    SemMultiWindowManager multiWindowManager = new SemMultiWindowManager();
    if (multiWindowManager.getMode() == 2) {
      int[] location = this.mParent.getLocationOnScreen();
      if (this.mViewPortOnScreen.top < location[1]) {
        this.mViewPortOnScreen.top = location[1];
      }
    }
  }

  private int getAdjustedToolbarWidth(int suggestedWidth) {
    int width = suggestedWidth;
    refreshViewPort();
    int maximumWidth =
        this.mViewPortOnScreen.width()
            - (this.mParent
                    .getResources()
                    .getDimensionPixelSize(C4337R.dimen.floating_toolbar_horizontal_margin)
                * 2);
    if (width <= 0) {
      width =
          this.mParent
              .getResources()
              .getDimensionPixelSize(C4337R.dimen.floating_toolbar_preferred_width);
    }
    return Math.min(width, maximumWidth);
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
    this.mTouchableRegion.set(
        (int) this.mContentContainer.getX(),
        (int) this.mContentContainer.getY(),
        ((int) this.mContentContainer.getX()) + width,
        ((int) this.mContentContainer.getY()) + height);
    Rect touchableRect = this.mTouchableRegion.getBounds();
    this.mToolbarHiddenArea[0] = this.mToolbarVisibleRect.left - touchableRect.left;
    this.mToolbarHiddenArea[1] = this.mToolbarVisibleRect.top - touchableRect.top;
  }

  private void setTouchableSurfaceInsetsComputer() {
    ViewTreeObserver viewTreeObserver =
        this.mPopupWindow.getContentView().getRootView().getViewTreeObserver();
    viewTreeObserver.removeOnComputeInternalInsetsListener(this.mInsetsComputer);
    viewTreeObserver.addOnComputeInternalInsetsListener(this.mInsetsComputer);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public boolean isInRTLMode() {
    return this.mContext.getApplicationInfo().hasRtlSupport()
        && this.mContext.getResources().getConfiguration().getLayoutDirection() == 1;
  }

  private boolean hasOverflow() {
    return this.mOverflowPanelSize != null;
  }

  /* JADX WARN: Multi-variable type inference failed */
  public List<MenuItem> layoutMainPanelItems(List<MenuItem> menuItems, int toolbarWidth) {
    int i;
    int paddingEnd;
    int itemMarginStart;
    Objects.requireNonNull(menuItems);
    int availableWidth = toolbarWidth;
    ArrayList<MenuItem> remainingMenuItems = new ArrayList<>();
    ArrayList<MenuItem> overflowMenuItems = new ArrayList<>();
    Iterator<MenuItem> it = menuItems.iterator();
    while (true) {
      i = 16908353;
      if (!it.hasNext()) {
        break;
      }
      MenuItem menuItem = it.next();
      if (menuItem.getItemId() != 16908353 && menuItem.requiresOverflow()) {
        overflowMenuItems.add(menuItem);
      } else {
        remainingMenuItems.add(menuItem);
      }
    }
    remainingMenuItems.addAll(overflowMenuItems);
    this.mMainPanel.removeAllViews();
    int i2 = 0;
    this.mMainPanel.setPaddingRelative(0, 0, 0, 0);
    boolean isFirstItem = true;
    while (!remainingMenuItems.isEmpty()) {
      MenuItem menuItem2 = remainingMenuItems.get(i2);
      if (menuItem2.getItemId() == 16909085) {
        remainingMenuItems.remove(i2);
      } else {
        if (!isFirstItem && menuItem2.requiresOverflow()) {
          break;
        }
        boolean z = (isFirstItem && menuItem2.getItemId() == i) ? 1 : i2;
        View menuItemButton =
            createMenuItemButton(this.mContext, menuItem2, this.mIconTextSpacing, z);
        if (z == 0 && (menuItemButton instanceof LinearLayout)) {
          ((LinearLayout) menuItemButton).setGravity(17);
        }
        if (isFirstItem) {
          if (sIsSemType) {
            if (z != 0) {
              itemMarginStart = this.mMenuFirstImageStartPadding;
            } else {
              itemMarginStart = this.mMenuFirstLastSidePadding;
            }
          } else {
            int itemMarginStart2 = menuItemButton.getPaddingStart();
            itemMarginStart = (int) (itemMarginStart2 * 1.5d);
          }
          menuItemButton.setPaddingRelative(
              itemMarginStart,
              menuItemButton.getPaddingTop(),
              menuItemButton.getPaddingEnd(),
              menuItemButton.getPaddingBottom());
        }
        boolean isLastItem = remainingMenuItems.size() == 1;
        if (isLastItem) {
          int paddingStart = menuItemButton.getPaddingStart();
          int paddingTop = menuItemButton.getPaddingTop();
          if (sIsSemType) {
            paddingEnd = this.mMenuFirstLastSidePadding;
          } else {
            paddingEnd = (int) (menuItemButton.getPaddingEnd() * 1.5d);
          }
          menuItemButton.setPaddingRelative(
              paddingStart, paddingTop, paddingEnd, menuItemButton.getPaddingBottom());
        }
        menuItemButton.measure(0, 0);
        int menuItemButtonWidth = Math.min(menuItemButton.getMeasuredWidth(), toolbarWidth);
        if (sIsSemType) {
          this.mOverflowButtonSize.getWidth();
        }
        boolean canFitWithOverflow =
            menuItemButtonWidth <= availableWidth - this.mOverflowButtonSize.getWidth();
        boolean canFitNoOverflow = isLastItem && menuItemButtonWidth <= availableWidth;
        if (!canFitWithOverflow && !canFitNoOverflow) {
          break;
        }
        setButtonTagAndClickListener(menuItemButton, menuItem2);
        menuItemButton.setTooltipText(menuItem2.getTooltipText());
        this.mMainPanel.addView(menuItemButton);
        ViewGroup.LayoutParams params = menuItemButton.getLayoutParams();
        params.width = menuItemButtonWidth;
        menuItemButton.setLayoutParams(params);
        availableWidth -= menuItemButtonWidth;
        remainingMenuItems.remove(0);
        menuItem2.getGroupId();
        isFirstItem = false;
        i2 = 0;
        i = 16908353;
      }
    }
    if (!remainingMenuItems.isEmpty()) {
      if (sIsSemType) {
        ViewGroup viewGroup = this.mMainPanel;
        View previousButton = viewGroup.getChildAt(viewGroup.getChildCount() - 1);
        if (previousButton != null) {
          int prevPaddingEnd = previousButton.getPaddingEnd();
          previousButton.setPaddingRelative(
              previousButton.getPaddingStart(),
              previousButton.getPaddingTop(),
              prevPaddingEnd * 2,
              previousButton.getPaddingBottom());
          ViewGroup.LayoutParams prevParams = previousButton.getLayoutParams();
          prevParams.width += prevPaddingEnd;
          previousButton.setLayoutParams(prevParams);
          this.mMainPanel.addView(this.mDividerVertical);
        }
      }
      this.mMainPanel.setPaddingRelative(0, 0, this.mOverflowButtonSize.getWidth(), 0);
    }
    this.mMainPanelSize = measure(this.mMainPanel);
    return remainingMenuItems;
  }

  private void layoutOverflowPanelItems(List<MenuItem> menuItems) {
    this.mOverflowMenuItems = menuItems;
    ArrayAdapter<MenuItem> overflowPanelAdapter = (ArrayAdapter) this.mOverflowPanel.getAdapter();
    overflowPanelAdapter.clear();
    int size = menuItems.size();
    for (int i = 0; i < size; i++) {
      overflowPanelAdapter.add(menuItems.get(i));
    }
    this.mOverflowPanel.setAdapter((ListAdapter) overflowPanelAdapter);
    if (this.mOpenOverflowUpwards) {
      this.mOverflowPanel.setY(0.0f);
    } else {
      this.mOverflowPanel.setY(this.mOverflowButtonSize.getHeight());
    }
    int width = Math.max(getOverflowWidth(), this.mOverflowButtonSize.getWidth());
    int height = calculateOverflowHeight(4);
    Size size2 = new Size(width, height);
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
    ArrayAdapter<MenuItem> overflowPanelAdapter = (ArrayAdapter) this.mOverflowPanel.getAdapter();
    overflowPanelAdapter.clear();
    this.mOverflowPanel.setAdapter((ListAdapter) overflowPanelAdapter);
    this.mContentContainer.removeAllViews();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void positionContentYCoordinatesIfOpeningOverflowUpwards() {
    if (this.mOpenOverflowUpwards) {
      this.mMainPanel.setY(this.mContentContainer.getHeight() - this.mMainPanelSize.getHeight());
      this.mOverflowButton.setY(
          this.mContentContainer.getHeight() - this.mOverflowButton.getHeight());
      this.mOverflowPanel.setY(
          this.mContentContainer.getHeight() - this.mOverflowPanelSize.getHeight());
    }
  }

  private int getOverflowWidth() {
    int overflowWidth = 0;
    int count = this.mOverflowPanel.getAdapter().getCount();
    for (int i = 0; i < count; i++) {
      MenuItem menuItem = (MenuItem) this.mOverflowPanel.getAdapter().getItem(i);
      overflowWidth =
          Math.max(this.mOverflowPanelViewHelper.calculateWidth(menuItem), overflowWidth);
    }
    return Math.min(overflowWidth, this.mViewPortOnScreen.width() - (this.mMarginHorizontal * 2));
  }

  private int calculateOverflowHeight(int maxItemSize) {
    int actualSize =
        Math.min(4, Math.min(Math.max(1, maxItemSize), this.mOverflowPanel.getCount()));
    int extension = 0;
    if (actualSize < this.mOverflowPanel.getCount()) {
      extension = (int) (this.mLineHeight * 0.5f);
    }
    return (this.mLineHeight * actualSize) + this.mOverflowButtonSize.getHeight() + extension;
  }

  private void setButtonTagAndClickListener(View menuItemButton, MenuItem menuItem) {
    menuItemButton.setTag(MenuItemRepr.m230of(menuItem));
    menuItemButton.setAccessibilityDelegate(getAccessibilityDelegate());
    menuItemButton.setOnClickListener(this.mMenuItemButtonOnClickListener);
  }

  private int getAdjustedDuration(int originalDuration) {
    int i = this.mTransitionDurationScale;
    if (i < 150) {
      return Math.max(originalDuration - 50, 0);
    }
    if (i > 300) {
      return originalDuration + 50;
    }
    return (int) (originalDuration * ValueAnimator.getDurationScale());
  }

  private void maybeComputeTransitionDurationScale() {
    Size size = this.mMainPanelSize;
    if (size != null && this.mOverflowPanelSize != null) {
      int w = size.getWidth() - this.mOverflowPanelSize.getWidth();
      int h = this.mOverflowPanelSize.getHeight() - this.mMainPanelSize.getHeight();
      this.mTransitionDurationScale =
          (int)
              (Math.sqrt((w * w) + (h * h))
                  / this.mContentContainer.getContext().getResources().getDisplayMetrics().density);
    }
  }

  private ViewGroup createMainPanel() {
    ViewGroup mainPanel =
        new LinearLayout(
            this
                .mContext) { // from class:
                             // com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.12
          @Override // android.widget.LinearLayout, android.view.View
          protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            if (LocalFloatingToolbarPopup.this.isOverflowAnimating()) {
              widthMeasureSpec =
                  View.MeasureSpec.makeMeasureSpec(
                      LocalFloatingToolbarPopup.this.mMainPanelSize.getWidth(), 1073741824);
            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
          }

          @Override // android.view.ViewGroup
          public boolean onInterceptTouchEvent(MotionEvent ev) {
            return LocalFloatingToolbarPopup.this.isOverflowAnimating();
          }
        };
    return mainPanel;
  }

  private ImageButton createOverflowButton() {
    int res =
        sIsSemType
            ? C4337R.layout.sem_floating_popup_overflow_button
            : C4337R.layout.floating_popup_overflow_button;
    final ImageButton overflowButton =
        (ImageButton) LayoutInflater.from(this.mContext).inflate(res, (ViewGroup) null);
    overflowButton.lambda$setImageURIAsync$2(this.mOverflow);
    overflowButton.semSetHoverPopupType(0);
    overflowButton.setAccessibilityDelegate(getAccessibilityDelegate());
    overflowButton.setOnClickListener(
        new View
            .OnClickListener() { // from class:
                                 // com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup$$ExternalSyntheticLambda0
          @Override // android.view.View.OnClickListener
          public final void onClick(View view) {
            LocalFloatingToolbarPopup.this.lambda$createOverflowButton$1(overflowButton, view);
          }
        });
    return overflowButton;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$createOverflowButton$1(ImageButton overflowButton, View v) {
    if (sIsDiscardTouch) {
      return;
    }
    if (this.mIsOverflowOpen) {
      if (sIsSemType) {
        overflowButton.lambda$setImageURIAsync$2(this.mOverflow);
      } else {
        overflowButton.lambda$setImageURIAsync$2(this.mToOverflow);
        this.mToOverflow.start();
      }
      closeOverflow();
      return;
    }
    if (sIsSemType) {
      overflowButton.lambda$setImageURIAsync$2(this.mArrowSem);
    } else {
      overflowButton.lambda$setImageURIAsync$2(this.mToArrow);
      this.mToArrow.start();
    }
    openOverflow();
  }

  private OverflowPanel createOverflowPanel() {
    final OverflowPanel overflowPanel = new OverflowPanel(this);
    overflowPanel.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
    overflowPanel.setDivider(null);
    overflowPanel.setDividerHeight(0);
    ArrayAdapter adapter =
        new ArrayAdapter<MenuItem>(
            this.mContext,
            0) { // from class:
                 // com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.13
          @Override // android.widget.ArrayAdapter, android.widget.Adapter
          public View getView(int position, View convertView, ViewGroup parent) {
            return LocalFloatingToolbarPopup.this.mOverflowPanelViewHelper.getView(
                getItem(position),
                LocalFloatingToolbarPopup.this.mOverflowPanelSize.getWidth(),
                convertView);
          }
        };
    overflowPanel.setAdapter((ListAdapter) adapter);
    overflowPanel.setOnItemClickListener(
        new AdapterView
            .OnItemClickListener() { // from class:
                                     // com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup$$ExternalSyntheticLambda1
          @Override // android.widget.AdapterView.OnItemClickListener
          public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
            LocalFloatingToolbarPopup.this.lambda$createOverflowPanel$2(
                overflowPanel, adapterView, view, i, j);
          }
        });
    return overflowPanel;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$createOverflowPanel$2(
      OverflowPanel overflowPanel, AdapterView parent, View view, int position, long id) {
    view.setAccessibilityDelegate(getAccessibilityDelegate());
    MenuItem menuItem = (MenuItem) overflowPanel.getAdapter().getItem(position);
    MenuItem.OnMenuItemClickListener onMenuItemClickListener = this.mOnMenuItemClickListener;
    if (onMenuItemClickListener != null) {
      onMenuItemClickListener.onMenuItemClick(menuItem);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public boolean isOverflowAnimating() {
    boolean overflowOpening =
        this.mOpenOverflowAnimation.hasStarted() && !this.mOpenOverflowAnimation.hasEnded();
    boolean overflowClosing =
        this.mCloseOverflowAnimation.hasStarted() && !this.mCloseOverflowAnimation.hasEnded();
    return overflowOpening || overflowClosing;
  }

  /* renamed from: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup$14 */
  class AnimationAnimationListenerC478814 implements Animation.AnimationListener {
    AnimationAnimationListenerC478814() {}

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationStart(Animation animation) {
      LocalFloatingToolbarPopup.this.mOverflowButton.setEnabled(false);
      LocalFloatingToolbarPopup.this.mMainPanel.setVisibility(0);
      LocalFloatingToolbarPopup.this.mOverflowPanel.setVisibility(0);
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationEnd(Animation animation) {
      LocalFloatingToolbarPopup.this.mContentContainer.post(
          new Runnable() { // from class:
                           // com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup$14$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              LocalFloatingToolbarPopup.AnimationAnimationListenerC478814.this
                  .lambda$onAnimationEnd$0();
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAnimationEnd$0() {
      LocalFloatingToolbarPopup.this.setPanelsStatesAtRestingPosition();
      LocalFloatingToolbarPopup.this.setContentAreaAsTouchableSurface();
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationRepeat(Animation animation) {}
  }

  private Animation.AnimationListener createOverflowAnimationListener() {
    Animation.AnimationListener listener = new AnimationAnimationListenerC478814();
    return listener;
  }

  private static Size measure(View view) {
    Preconditions.checkState(view.getParent() == null);
    view.measure(0, 0);
    return new Size(view.getMeasuredWidth(), view.getMeasuredHeight());
  }

  private static void setSize(View view, int width, int height) {
    view.setMinimumWidth(width);
    view.setMinimumHeight(height);
    ViewGroup.LayoutParams params = view.getLayoutParams();
    ViewGroup.LayoutParams params2 = params == null ? new ViewGroup.LayoutParams(0, 0) : params;
    params2.width = width;
    params2.height = height;
    view.setLayoutParams(params2);
  }

  private static void setSize(View view, Size size) {
    setSize(view, size.getWidth(), size.getHeight());
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static void setWidth(View view, int width) {
    ViewGroup.LayoutParams params = view.getLayoutParams();
    setSize(view, width, params.height);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static void setHeight(View view, int height) {
    ViewGroup.LayoutParams params = view.getLayoutParams();
    setSize(view, params.width, height);
  }

  /* JADX INFO: Access modifiers changed from: private */
  static final class OverflowPanel extends ListView {
    private final LocalFloatingToolbarPopup mPopup;

    OverflowPanel(LocalFloatingToolbarPopup popup) {
      super(((LocalFloatingToolbarPopup) Objects.requireNonNull(popup)).mContext);
      this.mPopup = popup;
      setScrollBarDefaultDelayBeforeFade(ViewConfiguration.getScrollDefaultDelay() * 3);
      if (!LocalFloatingToolbarPopup.sIsSemType) {
        setScrollIndicators(3);
      }
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      int height =
          this.mPopup.mOverflowPanelSize.getHeight() - this.mPopup.mOverflowButtonSize.getHeight();
      int heightMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(height, 1073741824);
      super.onMeasure(widthMeasureSpec, heightMeasureSpec2);
    }

    @Override // android.widget.AbsListView, android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
      if (canScrollVertically(1) || canScrollVertically(-1)) {
        LocalFloatingToolbarPopup.sIsScrolling = true;
      }
      if (this.mPopup.isOverflowAnimating()) {
        return true;
      }
      return super.dispatchTouchEvent(ev);
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

    private LogAccelerateInterpolator() {}

    private static float computeLog(float t, int base) {
      return (float) (1.0d - Math.pow(base, -t));
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float t) {
      return 1.0f - (computeLog(1.0f - t, 100) * LOGS_SCALE);
    }
  }

  private static final class OverflowPanelViewHelper {
    private final View mCalculator = createMenuButton(null);
    private final Context mContext;
    private final int mIconTextSpacing;
    private final int mSidePadding;

    OverflowPanelViewHelper(Context context, int iconTextSpacing) {
      this.mContext = (Context) Objects.requireNonNull(context);
      this.mIconTextSpacing = iconTextSpacing;
      this.mSidePadding =
          context
              .getResources()
              .getDimensionPixelSize(C4337R.dimen.floating_toolbar_overflow_side_padding);
    }

    public View getView(MenuItem menuItem, int minimumWidth, View convertView) {
      Objects.requireNonNull(menuItem);
      if (convertView != null) {
        LocalFloatingToolbarPopup.updateMenuItemButton(
            convertView, menuItem, this.mIconTextSpacing, shouldShowIcon(menuItem));
      } else {
        convertView = createMenuButton(menuItem);
      }
      convertView.setMinimumWidth(minimumWidth);
      return convertView;
    }

    public int calculateWidth(MenuItem menuItem) {
      LocalFloatingToolbarPopup.updateMenuItemButton(
          this.mCalculator, menuItem, this.mIconTextSpacing, shouldShowIcon(menuItem));
      this.mCalculator.measure(0, 0);
      return this.mCalculator.getMeasuredWidth();
    }

    private View createMenuButton(MenuItem menuItem) {
      View button =
          LocalFloatingToolbarPopup.createMenuItemButton(
              this.mContext, menuItem, this.mIconTextSpacing, shouldShowIcon(menuItem));
      int i = this.mSidePadding;
      button.setPadding(i, 0, i, 0);
      return button;
    }

    private boolean shouldShowIcon(MenuItem menuItem) {
      return menuItem != null && menuItem.getGroupId() == 16908353;
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

  private static final class FloatingOnAttachStateChangeListener
      implements View.OnAttachStateChangeListener {
    private final WeakReference<LocalFloatingToolbarPopup> mFloatingToolbarPopup;

    public FloatingOnAttachStateChangeListener(LocalFloatingToolbarPopup floatingToolbarPopup) {
      this.mFloatingToolbarPopup = new WeakReference<>(floatingToolbarPopup);
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewAttachedToWindow(View v) {}

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewDetachedFromWindow(View v) {
      if (this.mFloatingToolbarPopup.get() != null) {
        this.mFloatingToolbarPopup.get().onDetachFromWindow();
      }
      v.removeOnAttachStateChangeListener(this);
    }
  }

  private View.AccessibilityDelegate getAccessibilityDelegate() {
    if (this.mAccessibilityDelegate == null) {
      this.mAccessibilityDelegate =
          new View
              .AccessibilityDelegate() { // from class:
                                         // com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.15
            @Override // android.view.View.AccessibilityDelegate
            public boolean performAccessibilityAction(View host, int action, Bundle args) {
              if (action == 16) {
                return false;
              }
              return super.performAccessibilityAction(host, action, args);
            }
          };
    }
    return this.mAccessibilityDelegate;
  }

  private int getViewPortVisibleHeight() {
    SemMultiWindowManager multiWindowManager = new SemMultiWindowManager();
    if (multiWindowManager.getMode() == 2) {
      return this.mViewPortOnScreen.bottom;
    }
    DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
    int appHeight = displayMetrics.heightPixels;
    int sipHeight = getImeHeight();
    int viewPortVisibleHeight = appHeight - sipHeight;
    if (multiWindowManager.getMode() != 0) {
      int viewPortVisibleHeight2 = appHeight + this.mViewPortOnScreen.top;
      return viewPortVisibleHeight2;
    }
    return viewPortVisibleHeight;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public boolean isInsideOfViewPortRect(float x, float y) {
    refreshViewPort();
    return ((float) this.mViewPortOnScreen.left) <= x
        && ((float) this.mViewPortOnScreen.right) >= x
        && ((float) this.mViewPortOnScreen.top) <= y
        && ((float) this.mViewPortOnScreen.bottom) >= y;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void calculateCoords(int x, int y) {
    this.mParent.getRootView().getLocationOnScreen(this.mTmpCoords);
    int[] iArr = this.mTmpCoords;
    int rootViewLeftOnScreen = iArr[0];
    int rootViewTopOnScreen = iArr[1];
    this.mParent.getRootView().getLocationInWindow(this.mTmpCoords);
    int[] iArr2 = this.mTmpCoords;
    int rootViewLeftOnWindow = iArr2[0];
    int rootViewTopOnWindow = iArr2[1];
    int windowLeftOnScreen = rootViewLeftOnScreen - rootViewLeftOnWindow;
    int windowTopOnScreen = rootViewTopOnScreen - rootViewTopOnWindow;
    int i = this.mViewPortOnScreen.left;
    int i2 = this.mToolbarHiddenArea[0];
    int left = Math.max(Math.max(i + i2, i2) - windowLeftOnScreen, x);
    int i3 = this.mViewPortOnScreen.top;
    int i4 = this.mToolbarHiddenArea[1];
    int top = Math.max(Math.max(i3 + i4, i4) - windowTopOnScreen, y);
    int right =
        Math.min(
            (this.mToolbarVisibleRect.width() + left) - this.mToolbarHiddenArea[0],
            this.mViewPortOnScreen.right - windowLeftOnScreen);
    int bottom =
        Math.min(
            (this.mToolbarVisibleRect.height() + top) - this.mToolbarHiddenArea[1],
            getViewPortVisibleHeight() - windowTopOnScreen);
    this.mCoordsOnWindow.set(
        Math.min(left, (right - this.mToolbarVisibleRect.width()) + this.mToolbarHiddenArea[0]),
        Math.min(top, (bottom - this.mToolbarVisibleRect.height()) + this.mToolbarHiddenArea[1]));
    if (this.mMoved) {
      this.mMovedPos.set(
          this.mOriginalPos.f85x - this.mCoordsOnWindow.f85x,
          this.mOriginalPos.f86y - this.mCoordsOnWindow.f86y);
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
    Rect popupArea = new Rect(0, 0, this.mPopupWindow.getWidth(), this.mPopupWindow.getHeight());
    Rect popupAreaReverse =
        new Rect(0, 0, this.mPopupWindow.getWidth(), this.mPopupWindow.getHeight());
    int absOffsetX = Math.abs(this.mMainPanelSize.getWidth() - this.mOverflowPanelSize.getWidth());
    int offsetY = this.mOverflowPanelSize.getHeight() - this.mMainPanelSize.getHeight();
    if (this.mOpenOverflowUpwards) {
      popupArea.bottom -= offsetY;
      popupAreaReverse.top += offsetY;
    } else {
      popupArea.top += offsetY;
      popupAreaReverse.bottom -= offsetY;
    }
    popupArea.top += this.mMarginVertical;
    popupArea.bottom -= this.mMarginVertical;
    popupAreaReverse.top += this.mMarginVertical;
    popupAreaReverse.bottom -= this.mMarginVertical;
    if (isInRTLMode() != this.mIsClosedOpposites) {
      popupArea.left += this.mMarginHorizontal + absOffsetX;
      popupArea.right -= this.mMarginHorizontal;
      popupAreaReverse.left += this.mMarginHorizontal + absOffsetX;
      popupAreaReverse.right -= this.mMarginHorizontal;
    } else {
      popupArea.left += this.mMarginHorizontal;
      popupArea.right -= this.mMarginHorizontal + absOffsetX;
      popupAreaReverse.left += this.mMarginHorizontal;
      popupAreaReverse.right -= this.mMarginHorizontal + absOffsetX;
    }
    this.mParent.getRootView().getLocationOnScreen(this.mTmpCoords);
    int[] iArr = this.mTmpCoords;
    int rootViewLeftOnScreen = iArr[0];
    int rootViewTopOnScreen = iArr[1];
    this.mParent.getRootView().getLocationInWindow(this.mTmpCoords);
    int[] iArr2 = this.mTmpCoords;
    int rootViewLeftOnWindow = iArr2[0];
    int rootViewTopOnWindow = iArr2[1];
    int windowLeftOnScreen = rootViewLeftOnScreen - rootViewLeftOnWindow;
    int windowTopOnScreen = rootViewTopOnScreen - rootViewTopOnWindow;
    popupArea.offset(this.mCoordsOnWindow.f85x, this.mCoordsOnWindow.f86y);
    popupArea.offset(windowLeftOnScreen, windowTopOnScreen);
    popupAreaReverse.offset(this.mCoordsOnWindow.f85x, this.mCoordsOnWindow.f86y);
    popupAreaReverse.offset(windowLeftOnScreen, windowTopOnScreen);
    Rect viewArea = new Rect();
    viewArea.set(this.mViewPortOnScreen);
    viewArea.bottom = getViewPortVisibleHeight();
    if (viewArea.contains(popupArea)) {
      return 0;
    }
    if (viewArea.left <= popupArea.left && viewArea.right >= popupArea.right) {
      return !viewArea.contains(popupAreaReverse) ? 0 : 1;
    }
    if (viewArea.top <= popupArea.top && viewArea.bottom >= popupArea.bottom) {
      return 2;
    }
    return 3;
  }

  private void shiftPopup() {
    int startPopupX = this.mCoordsOnWindow.f85x;
    int targetPopupX = this.mViewPortOnScreen.left - this.mMarginHorizontal;
    ValueAnimator popupShiftAnimator = ValueAnimator.ofInt(startPopupX, targetPopupX);
    popupShiftAnimator.addUpdateListener(
        new ValueAnimator
            .AnimatorUpdateListener() { // from class:
                                        // com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.16
          @Override // android.animation.ValueAnimator.AnimatorUpdateListener
          public void onAnimationUpdate(ValueAnimator animation) {
            LocalFloatingToolbarPopup.this.mCoordsOnWindow.f85x =
                ((Integer) animation.getAnimatedValue()).intValue();
            LocalFloatingToolbarPopup.this.recalCoordsOnWindowX();
            LocalFloatingToolbarPopup.this.mPopupWindow.update(
                LocalFloatingToolbarPopup.this.mCoordsOnWindow.f85x,
                LocalFloatingToolbarPopup.this.mCoordsOnWindow.f86y,
                LocalFloatingToolbarPopup.this.mPopupWindow.getWidth(),
                LocalFloatingToolbarPopup.this.mPopupWindow.getHeight());
          }
        });
    popupShiftAnimator.setDuration(100L);
    popupShiftAnimator.start();
  }

  private void changeOverflowPanelAdapterOrder() {
    ArrayList arrayList = new ArrayList(this.mOverflowMenuItems);
    if (this.mOpenOverflowUpwards) {
      Collections.reverse(arrayList);
    }
    ArrayAdapter<MenuItem> overflowPanelAdapter = (ArrayAdapter) this.mOverflowPanel.getAdapter();
    overflowPanelAdapter.clear();
    overflowPanelAdapter.addAll(arrayList);
    this.mOverflowPanel.setAdapter((ListAdapter) overflowPanelAdapter);
    if (this.mOpenOverflowUpwards) {
      this.mOverflowPanel.setSelection(overflowPanelAdapter.getCount() - 1);
    }
  }

  private void createDividers() {
    ImageView imageView = new ImageView(this.mContext);
    this.mDividerVertical = imageView;
    imageView.setImageResource(C4337R.drawable.tw_floating_popup_divider);
    this.mDividerVertical.setLayoutParams(new ViewGroup.LayoutParams(-2, -1));
    this.mDividerVertical.setImportantForAccessibility(2);
    this.mDividerVertical.setEnabled(false);
    this.mDividerVertical.setFocusable(false);
    this.mDividerVertical.setContentDescription(null);
    ImageView imageView2 = new ImageView(this.mContext);
    this.mDividerHorizontal = imageView2;
    imageView2.setImageResource(C4337R.drawable.tw_floating_popup_divider_horizontal);
    this.mDividerHorizontal.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
    this.mDividerHorizontal.setImportantForAccessibility(2);
    this.mDividerHorizontal.setEnabled(false);
    this.mDividerHorizontal.setFocusable(false);
    this.mDividerHorizontal.setContentDescription(null);
  }

  private boolean isCutoutMarginSet() {
    if (mCutoutLeftMargin == 0 && mCutoutRightMargin == 0) {
      return false;
    }
    return true;
  }

  private DisplayCutout getDisplayCutout() {
    WindowInsets rootWindowInsets;
    DisplayCutout displayCutout;
    View view = this.mParentRoot;
    if (view != null
        && (rootWindowInsets = view.getRootWindowInsets()) != null
        && (displayCutout = rootWindowInsets.getDisplayCutout()) != null) {
      return displayCutout;
    }
    mCutoutRightMargin = 0;
    mCutoutLeftMargin = 0;
    return null;
  }

  private void setCutoutMarginValue(DisplayCutout displayCutout) {
    List<Rect> cutoutRectList = displayCutout.getBoundingRects();
    if (cutoutRectList != null && !cutoutRectList.isEmpty()) {
      Rect windowRect = new Rect();
      this.mParentRoot.getWindowDisplayFrame(windowRect);
      for (Rect rect : cutoutRectList) {
        int cutoutWidth = rect.right - rect.left;
        if (rect.left == 0) {
          mCutoutLeftMargin = cutoutWidth;
          mCutoutRightMargin = windowRect.right;
        } else if (rect.right == windowRect.right) {
          mCutoutLeftMargin = 0;
          mCutoutRightMargin = windowRect.right - cutoutWidth;
        } else {
          mCutoutRightMargin = 0;
          mCutoutLeftMargin = 0;
        }
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
      int rotation =
          ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE))
              .getDefaultDisplay()
              .getRotation();
      if (rotation == 1) {
        if (!isInRTLMode()) {
          Point point = this.mCoordsOnWindow;
          int i = point.f85x;
          int i2 = mCutoutLeftMargin;
          if (i >= i2) {
            i2 = this.mCoordsOnWindow.f85x;
          }
          point.f85x = i2;
          return;
        }
        return;
      }
      if (rotation == 3) {
        int currentPopupWindowWidth = this.mPopupWindow.getWidth();
        if (hasOverflow()) {
          currentPopupWindowWidth =
              (this.mOverflowPanelSize.getWidth() + currentPopupWindowWidth) / 2;
        }
        Point point2 = this.mCoordsOnWindow;
        int i3 = point2.f85x + currentPopupWindowWidth;
        int i4 = mCutoutRightMargin;
        point2.f85x = i3 > i4 ? i4 - currentPopupWindowWidth : this.mCoordsOnWindow.f85x;
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static View createMenuItemButton(
      Context context, MenuItem menuItem, int iconTextSpacing, boolean showIcon) {
    int res =
        sIsSemType
            ? C4337R.layout.sem_floating_popup_menu_button
            : C4337R.layout.floating_popup_menu_button;
    View menuItemButton = LayoutInflater.from(context).inflate(res, (ViewGroup) null);
    if (menuItem != null) {
      updateMenuItemButton(menuItemButton, menuItem, iconTextSpacing, showIcon);
    }
    menuItemButton.semSetHoverPopupType(0);
    return menuItemButton;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static void updateMenuItemButton(
      View menuItemButton, MenuItem menuItem, int iconTextSpacing, boolean showIcon) {
    TextView buttonText =
        (TextView) menuItemButton.findViewById(C4337R.id.floating_toolbar_menu_item_text);
    buttonText.setEllipsize(null);
    if (TextUtils.isEmpty(menuItem.getTitle())) {
      buttonText.setVisibility(8);
    } else {
      buttonText.setVisibility(0);
      buttonText.setText(menuItem.getTitle());
    }
    ImageView buttonIcon =
        (ImageView) menuItemButton.findViewById(C4337R.id.floating_toolbar_menu_item_image);
    if (menuItem.getIcon() == null || !showIcon) {
      buttonIcon.setVisibility(8);
      if (buttonText != null) {
        buttonText.setPaddingRelative(0, 0, 0, 0);
      }
    } else {
      buttonIcon.setVisibility(0);
      buttonIcon.lambda$setImageURIAsync$2(menuItem.getIcon());
      if (buttonText != null) {
        buttonText.setPaddingRelative(iconTextSpacing, 0, 0, 0);
      }
    }
    CharSequence contentDescription = menuItem.getContentDescription();
    if (TextUtils.isEmpty(contentDescription)) {
      menuItemButton.setContentDescription(menuItem.getTitle());
    } else {
      menuItemButton.setContentDescription(contentDescription);
    }
  }

  private static ViewGroup createContentContainer(Context context) {
    ViewGroup contentContainer =
        (ViewGroup)
            LayoutInflater.from(context)
                .inflate(
                    sIsSemType
                        ? C4337R.layout.sem_floating_popup_container
                        : C4337R.layout.floating_popup_container,
                    (ViewGroup) null);
    contentContainer.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
    contentContainer.setTag(FloatingToolbar.FLOATING_TOOLBAR_TAG);
    contentContainer.setClipToOutline(true);
    return contentContainer;
  }

  private static PopupWindow createPopupWindow(ViewGroup content) {
    ViewGroup popupContentHolder = new LinearLayout(content.getContext());
    ((LinearLayout) popupContentHolder).setGravity(0);
    PopupWindow popupWindow = new PopupWindow(popupContentHolder);
    popupWindow.setClippingEnabled(false);
    popupWindow.setWindowLayoutType(1005);
    popupWindow.setAnimationStyle(0);
    popupWindow.setBackgroundDrawable(new ColorDrawable(0));
    content.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
    popupContentHolder.addView(content);
    return popupWindow;
  }

  private static AnimatorSet createEnterAnimation(View view) {
    AnimatorSet animation = new AnimatorSet();
    animation.playTogether(ObjectAnimator.ofFloat(view, View.ALPHA, 0.0f, 1.0f).setDuration(150L));
    return animation;
  }

  private static AnimatorSet createExitAnimation(
      View view, int startDelay, Animator.AnimatorListener listener) {
    AnimatorSet animation = new AnimatorSet();
    animation.playTogether(ObjectAnimator.ofFloat(view, View.ALPHA, 1.0f, 0.0f).setDuration(100L));
    animation.setStartDelay(startDelay);
    animation.addListener(listener);
    return animation;
  }

  private static Context applyDefaultTheme(Context originalContext) {
    TypedArray a = originalContext.obtainStyledAttributes(new int[] {16844176});
    boolean isLightTheme = a.getBoolean(0, true);
    boolean isLightMode = (originalContext.getResources().getConfiguration().uiMode & 48) != 32;
    if (sIsSemType && isLightTheme != isLightMode) {
      isLightTheme = isLightMode;
    }
    int themeId = isLightTheme ? 16974123 : 16974120;
    a.recycle();
    return new ContextThemeWrapper(originalContext, themeId);
  }

  public static final class MenuItemRepr {
    public final int groupId;
    public final int itemId;
    private final Drawable mIcon;
    public final String title;

    private MenuItemRepr(int itemId, int groupId, CharSequence title, Drawable icon) {
      this.itemId = itemId;
      this.groupId = groupId;
      this.title = title == null ? null : title.toString();
      this.mIcon = icon;
    }

    /* renamed from: of */
    public static MenuItemRepr m230of(MenuItem menuItem) {
      return new MenuItemRepr(
          menuItem.getItemId(), menuItem.getGroupId(), menuItem.getTitle(), menuItem.getIcon());
    }

    public int hashCode() {
      return Objects.hash(
          Integer.valueOf(this.itemId), Integer.valueOf(this.groupId), this.title, this.mIcon);
    }

    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (!(o instanceof MenuItemRepr)) {
        return false;
      }
      MenuItemRepr other = (MenuItemRepr) o;
      return this.itemId == other.itemId
          && this.groupId == other.groupId
          && TextUtils.equals(this.title, other.title)
          && Objects.equals(this.mIcon, other.mIcon);
    }

    public static boolean reprEquals(
        Collection<MenuItem> menuItems1, Collection<MenuItem> menuItems2) {
      if (menuItems1.size() != menuItems2.size()) {
        return false;
      }
      Iterator<MenuItem> menuItems2Iter = menuItems2.iterator();
      for (MenuItem menuItem1 : menuItems1) {
        MenuItem menuItem2 = menuItems2Iter.next();
        if (!m230of(menuItem1).equals(m230of(menuItem2))) {
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
      Log.m102w(FloatingToolbar.FLOATING_TOOLBAR_TAG, "mParentRootWindowInset is null");
      return 0;
    }
    Insets imeInset = windowInsets.getInsets(WindowInsets.Type.ime());
    Insets navibarInset = this.mParentRootWindowInset.getInsets(WindowInsets.Type.navigationBars());
    return imeInset.bottom - navibarInset.bottom;
  }
}
