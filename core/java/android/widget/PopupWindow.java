package android.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.StateListDrawable;
import android.hardware.display.DisplayManager;
import android.os.IBinder;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import android.window.WindowOnBackInvokedDispatcher;

import com.android.internal.R;

import com.samsung.android.app.SemMultiWindowManager;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.rune.ViewRune;
import com.samsung.android.view.SemWindowManager;

import java.lang.ref.WeakReference;
import java.util.List;

/* loaded from: classes4.dex */
public class PopupWindow {
    private static final int ANIMATION_STYLE_DEFAULT = -1;
    private static final int DEFAULT_ANCHORED_GRAVITY = 8388659;
    public static final int INPUT_METHOD_FROM_FOCUSABLE = 0;
    public static final int INPUT_METHOD_NEEDED = 1;
    public static final int INPUT_METHOD_NOT_NEEDED = 2;
    private boolean mAboveAnchor;
    private Drawable mAboveAnchorBackgroundDrawable;
    private boolean mAllowScrollingAnchorParent;
    private WeakReference<View> mAnchor;
    private WeakReference<View> mAnchorRoot;
    private int mAnchorXoff;
    private int mAnchorYoff;
    private int mAnchoredGravity;
    private int mAnimationStyle;
    private boolean mAttachedInDecor;
    private boolean mAttachedInDecorSet;
    private OnBackInvokedCallback mBackCallback;
    private Drawable mBackground;
    private View mBackgroundView;
    private Drawable mBelowAnchorBackgroundDrawable;
    private boolean mClipToScreen;
    private boolean mClippingEnabled;
    private View mContentView;
    private Context mContext;
    private PopupDecorView mDecorView;
    private float mElevation;
    private Transition mEnterTransition;
    private Rect mEpicenterBounds;
    private Transition mExitTransition;
    private boolean mFocusable;
    private int mGravity;
    private int mHeight;
    private int mHeightMode;
    private boolean mIgnoreCheekPress;
    private int mInputMethodMode;
    private boolean mIsAnchorRootAttached;
    private boolean mIsDeviceDefault;
    private boolean mIsDropdown;
    private boolean mIsReplacedPoupBackground;
    private boolean mIsShowing;
    private boolean mIsTransitioningToDismiss;
    private int mLastHeight;
    private int mLastWidth;
    private boolean mLayoutInScreen;
    private boolean mLayoutInsetDecor;
    private int mNavigationBarHeight;
    private boolean mNotTouchModal;
    private final View.OnAttachStateChangeListener mOnAnchorDetachedListener;
    private final View.OnAttachStateChangeListener mOnAnchorRootDetachedListener;
    private OnDismissListener mOnDismissListener;
    private final View.OnLayoutChangeListener mOnLayoutChangeListener;
    private final ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;
    private boolean mOutsideTouchable;
    private boolean mOverlapAnchor;
    private WeakReference<View> mParentRootView;
    private boolean mPopupViewInitialLayoutDirectionInherited;
    private boolean mShowWhenLocked;
    private int mSoftInputMode;
    private int mSplitTouchEnabled;
    private int mStatusBarHeight;
    private final Rect mTempRect;
    private final int[] mTmpAppLocation;
    private final int[] mTmpDrawingLocation;
    private final int[] mTmpScreenLocation;
    private View.OnTouchListener mTouchInterceptor;
    private boolean mTouchable;
    private int mWidth;
    private int mWidthMode;
    private int mWindowLayoutType;
    private WindowManager mWindowManager;
    private static final int[] ABOVE_ANCHOR_STATE_SET = {16842922};
    private static final int[] ONEUI_BLUR_POPUP_BACKGROUND_RES = {
        R.drawable.sem_popup_background_material_dark,
        R.drawable.sem_popup_background_material,
        R.drawable.sem_list_popup_background_material_dark,
        R.drawable.sem_list_popup_background_material,
        R.drawable.sem_search_popup_background_material_dark,
        R.drawable.sem_search_popup_background_material,
        R.drawable.sem_spinner_popup_background_material_dark,
        R.drawable.sem_spinner_popup_background_material
    };

    public interface OnDismissListener {
        void onDismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(
            View v,
            int left,
            int top,
            int right,
            int bottom,
            int oldLeft,
            int oldTop,
            int oldRight,
            int oldBottom) {
        alignToAnchor(left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom);
    }

    public PopupWindow(Context context) {
        this(context, (AttributeSet) null);
    }

    public PopupWindow(Context context, AttributeSet attrs) {
        this(context, attrs, 16842870);
    }

    public PopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        Transition exitTransition;
        this.mTmpDrawingLocation = new int[2];
        this.mTmpScreenLocation = new int[2];
        this.mTmpAppLocation = new int[2];
        this.mTempRect = new Rect();
        this.mInputMethodMode = 0;
        this.mSoftInputMode = 1;
        this.mTouchable = true;
        this.mOutsideTouchable = false;
        this.mClippingEnabled = true;
        this.mSplitTouchEnabled = -1;
        this.mAllowScrollingAnchorParent = true;
        this.mLayoutInsetDecor = false;
        this.mAttachedInDecor = true;
        this.mAttachedInDecorSet = false;
        this.mWidth = -2;
        this.mHeight = -2;
        this.mWindowLayoutType = 1000;
        this.mIgnoreCheekPress = false;
        this.mAnimationStyle = -1;
        this.mGravity = 0;
        this.mShowWhenLocked = false;
        this.mOnAnchorDetachedListener =
                new View.OnAttachStateChangeListener() { // from class: android.widget.PopupWindow.1
                    @Override // android.view.View.OnAttachStateChangeListener
                    public void onViewAttachedToWindow(View v) {
                        PopupWindow.this.alignToAnchor();
                    }

                    @Override // android.view.View.OnAttachStateChangeListener
                    public void onViewDetachedFromWindow(View v) {}
                };
        this.mOnAnchorRootDetachedListener =
                new View.OnAttachStateChangeListener() { // from class: android.widget.PopupWindow.2
                    @Override // android.view.View.OnAttachStateChangeListener
                    public void onViewAttachedToWindow(View v) {}

                    @Override // android.view.View.OnAttachStateChangeListener
                    public void onViewDetachedFromWindow(View v) {
                        PopupWindow.this.mIsAnchorRootAttached = false;
                    }
                };
        this.mOnScrollChangedListener =
                new ViewTreeObserver
                        .OnScrollChangedListener() { // from class:
                                                     // android.widget.PopupWindow$$ExternalSyntheticLambda0
                    @Override // android.view.ViewTreeObserver.OnScrollChangedListener
                    public final void onScrollChanged() {
                        PopupWindow.this.alignToAnchor();
                    }
                };
        this.mOnLayoutChangeListener =
                new View
                        .OnLayoutChangeListener() { // from class:
                                                    // android.widget.PopupWindow$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnLayoutChangeListener
                    public final void onLayoutChange(
                            View view,
                            int i,
                            int i2,
                            int i3,
                            int i4,
                            int i5,
                            int i6,
                            int i7,
                            int i8) {
                        PopupWindow.this.lambda$new$0(view, i, i2, i3, i4, i5, i6, i7, i8);
                    }
                };
        this.mIsDeviceDefault = false;
        this.mStatusBarHeight = 0;
        this.mNavigationBarHeight = 0;
        this.mContext = context;
        this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        TypedArray a =
                context.obtainStyledAttributes(
                        attrs, R.styleable.PopupWindow, defStyleAttr, defStyleRes);
        Drawable bg = a.getDrawable(0);
        this.mElevation = a.getDimension(3, 0.0f);
        this.mOverlapAnchor = a.getBoolean(2, false);
        if (a.hasValueOrEmpty(1)) {
            int animStyle = a.getResourceId(1, 0);
            if (animStyle == 16974614) {
                this.mAnimationStyle = -1;
            } else {
                this.mAnimationStyle = animStyle;
            }
        } else {
            this.mAnimationStyle = -1;
        }
        Transition enterTransition = getTransition(a.getResourceId(4, 0));
        if (a.hasValueOrEmpty(5)) {
            exitTransition = getTransition(a.getResourceId(5, 0));
        } else {
            exitTransition = enterTransition == null ? null : enterTransition.mo5201clone();
        }
        boolean isOneUIBlurBackground = false;
        int popupBackgroundResId = a.getResourceId(0, -1);
        for (int popupBgResIds : ONEUI_BLUR_POPUP_BACKGROUND_RES) {
            if (popupBgResIds == popupBackgroundResId) {
                isOneUIBlurBackground = true;
            }
        }
        a.recycle();
        setEnterTransition(enterTransition);
        setExitTransition(exitTransition);
        setBackgroundDrawable(bg);
        this.mIsReplacedPoupBackground = !isOneUIBlurBackground;
        TypedValue themeValue = new TypedValue();
        this.mContext.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, themeValue, false);
        this.mIsDeviceDefault = themeValue.data != 0;
        this.mStatusBarHeight =
                this.mContext.getResources().getDimensionPixelSize(R.dimen.status_bar_height);
        this.mNavigationBarHeight =
                this.mContext.getResources().getDimensionPixelSize(R.dimen.navigation_bar_height);
    }

    public PopupWindow() {
        this((View) null, 0, 0);
    }

    public PopupWindow(View contentView) {
        this(contentView, 0, 0);
    }

    public PopupWindow(int width, int height) {
        this((View) null, width, height);
    }

    public PopupWindow(View contentView, int width, int height) {
        this(contentView, width, height, false);
    }

    public PopupWindow(View contentView, int width, int height, boolean focusable) {
        this.mTmpDrawingLocation = new int[2];
        this.mTmpScreenLocation = new int[2];
        this.mTmpAppLocation = new int[2];
        this.mTempRect = new Rect();
        this.mInputMethodMode = 0;
        this.mSoftInputMode = 1;
        this.mTouchable = true;
        this.mOutsideTouchable = false;
        this.mClippingEnabled = true;
        this.mSplitTouchEnabled = -1;
        this.mAllowScrollingAnchorParent = true;
        this.mLayoutInsetDecor = false;
        this.mAttachedInDecor = true;
        this.mAttachedInDecorSet = false;
        this.mWidth = -2;
        this.mHeight = -2;
        this.mWindowLayoutType = 1000;
        this.mIgnoreCheekPress = false;
        this.mAnimationStyle = -1;
        this.mGravity = 0;
        this.mShowWhenLocked = false;
        this.mOnAnchorDetachedListener =
                new View.OnAttachStateChangeListener() { // from class: android.widget.PopupWindow.1
                    @Override // android.view.View.OnAttachStateChangeListener
                    public void onViewAttachedToWindow(View v) {
                        PopupWindow.this.alignToAnchor();
                    }

                    @Override // android.view.View.OnAttachStateChangeListener
                    public void onViewDetachedFromWindow(View v) {}
                };
        this.mOnAnchorRootDetachedListener =
                new View.OnAttachStateChangeListener() { // from class: android.widget.PopupWindow.2
                    @Override // android.view.View.OnAttachStateChangeListener
                    public void onViewAttachedToWindow(View v) {}

                    @Override // android.view.View.OnAttachStateChangeListener
                    public void onViewDetachedFromWindow(View v) {
                        PopupWindow.this.mIsAnchorRootAttached = false;
                    }
                };
        this.mOnScrollChangedListener =
                new ViewTreeObserver
                        .OnScrollChangedListener() { // from class:
                                                     // android.widget.PopupWindow$$ExternalSyntheticLambda0
                    @Override // android.view.ViewTreeObserver.OnScrollChangedListener
                    public final void onScrollChanged() {
                        PopupWindow.this.alignToAnchor();
                    }
                };
        this.mOnLayoutChangeListener =
                new View
                        .OnLayoutChangeListener() { // from class:
                                                    // android.widget.PopupWindow$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnLayoutChangeListener
                    public final void onLayoutChange(
                            View view,
                            int i,
                            int i2,
                            int i3,
                            int i4,
                            int i5,
                            int i6,
                            int i7,
                            int i8) {
                        PopupWindow.this.lambda$new$0(view, i, i2, i3, i4, i5, i6, i7, i8);
                    }
                };
        this.mIsDeviceDefault = false;
        this.mStatusBarHeight = 0;
        this.mNavigationBarHeight = 0;
        if (contentView != null) {
            this.mContext = contentView.getContext();
            this.mWindowManager =
                    (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
        }
        setContentView(contentView);
        setWidth(width);
        setHeight(height);
        setFocusable(focusable);
    }

    public void setEnterTransition(Transition enterTransition) {
        this.mEnterTransition = enterTransition;
    }

    public Transition getEnterTransition() {
        return this.mEnterTransition;
    }

    public void setExitTransition(Transition exitTransition) {
        this.mExitTransition = exitTransition;
    }

    public Transition getExitTransition() {
        return this.mExitTransition;
    }

    public Rect getEpicenterBounds() {
        if (this.mEpicenterBounds != null) {
            return new Rect(this.mEpicenterBounds);
        }
        return null;
    }

    public void setEpicenterBounds(Rect bounds) {
        this.mEpicenterBounds = bounds != null ? new Rect(bounds) : null;
    }

    private Transition getTransition(int resId) {
        if (resId != 0 && resId != 17760256) {
            TransitionInflater inflater = TransitionInflater.from(this.mContext);
            Transition transition = inflater.inflateTransition(resId);
            if (transition != null) {
                boolean isEmpty =
                        (transition instanceof TransitionSet)
                                && ((TransitionSet) transition).getTransitionCount() == 0;
                if (!isEmpty) {
                    return transition;
                }
                return null;
            }
            return null;
        }
        return null;
    }

    public Drawable getBackground() {
        return this.mBackground;
    }

    public void setBackgroundDrawable(Drawable background) {
        this.mIsReplacedPoupBackground = true;
        this.mBackground = background;
        if (this.mBackground instanceof StateListDrawable) {
            StateListDrawable stateList = (StateListDrawable) this.mBackground;
            int aboveAnchorStateIndex = stateList.findStateDrawableIndex(ABOVE_ANCHOR_STATE_SET);
            int count = stateList.getStateCount();
            int belowAnchorStateIndex = -1;
            int i = 0;
            while (true) {
                if (i >= count) {
                    break;
                }
                if (i == aboveAnchorStateIndex) {
                    i++;
                } else {
                    belowAnchorStateIndex = i;
                    break;
                }
            }
            if (aboveAnchorStateIndex != -1 && belowAnchorStateIndex != -1) {
                this.mAboveAnchorBackgroundDrawable =
                        stateList.getStateDrawable(aboveAnchorStateIndex);
                this.mBelowAnchorBackgroundDrawable =
                        stateList.getStateDrawable(belowAnchorStateIndex);
            } else {
                this.mBelowAnchorBackgroundDrawable = null;
                this.mAboveAnchorBackgroundDrawable = null;
            }
        }
    }

    public float getElevation() {
        return this.mElevation;
    }

    public void setElevation(float elevation) {
        this.mElevation = elevation;
    }

    public int getAnimationStyle() {
        return this.mAnimationStyle;
    }

    public void setIgnoreCheekPress() {
        this.mIgnoreCheekPress = true;
    }

    public void setAnimationStyle(int animationStyle) {
        this.mAnimationStyle = animationStyle;
    }

    public View getContentView() {
        return this.mContentView;
    }

    public void setContentView(View contentView) {
        if (isShowing()) {
            return;
        }
        this.mContentView = contentView;
        if (this.mContext == null && this.mContentView != null) {
            this.mContext = this.mContentView.getContext();
        }
        if (this.mWindowManager == null && this.mContentView != null) {
            this.mWindowManager =
                    (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
        }
        if (this.mContext != null && !this.mAttachedInDecorSet) {
            setAttachedInDecor(this.mContext.getApplicationInfo().targetSdkVersion >= 22);
        }
    }

    public void setTouchInterceptor(View.OnTouchListener l) {
        this.mTouchInterceptor = l;
    }

    public boolean isFocusable() {
        return this.mFocusable;
    }

    public void setFocusable(boolean focusable) {
        this.mFocusable = focusable;
    }

    public int getInputMethodMode() {
        return this.mInputMethodMode;
    }

    public void setInputMethodMode(int mode) {
        this.mInputMethodMode = mode;
    }

    public void setSoftInputMode(int mode) {
        this.mSoftInputMode = mode;
    }

    public int getSoftInputMode() {
        return this.mSoftInputMode;
    }

    public boolean isTouchable() {
        return this.mTouchable;
    }

    public void setTouchable(boolean touchable) {
        this.mTouchable = touchable;
    }

    public boolean isOutsideTouchable() {
        return this.mOutsideTouchable;
    }

    public void setOutsideTouchable(boolean touchable) {
        this.mOutsideTouchable = touchable;
    }

    public boolean isClippingEnabled() {
        return this.mClippingEnabled;
    }

    public void setClippingEnabled(boolean enabled) {
        this.mClippingEnabled = enabled;
    }

    @Deprecated
    public boolean isClipToScreenEnabled() {
        return this.mClipToScreen;
    }

    @Deprecated
    public void setClipToScreenEnabled(boolean enabled) {
        this.mClipToScreen = enabled;
    }

    public boolean isClippedToScreen() {
        return this.mClipToScreen;
    }

    public void setIsClippedToScreen(boolean enabled) {
        this.mClipToScreen = enabled;
    }

    void setAllowScrollingAnchorParent(boolean enabled) {
        this.mAllowScrollingAnchorParent = enabled;
    }

    protected final boolean getAllowScrollingAnchorParent() {
        return this.mAllowScrollingAnchorParent;
    }

    public boolean isSplitTouchEnabled() {
        return (this.mSplitTouchEnabled >= 0 || this.mContext == null)
                ? this.mSplitTouchEnabled == 1
                : this.mContext.getApplicationInfo().targetSdkVersion >= 11;
    }

    public void setSplitTouchEnabled(boolean z) {
        this.mSplitTouchEnabled = z ? 1 : 0;
    }

    @Deprecated
    public boolean isLayoutInScreenEnabled() {
        return this.mLayoutInScreen;
    }

    @Deprecated
    public void setLayoutInScreenEnabled(boolean enabled) {
        this.mLayoutInScreen = enabled;
    }

    public boolean isLaidOutInScreen() {
        return this.mLayoutInScreen;
    }

    public void setIsLaidOutInScreen(boolean enabled) {
        this.mLayoutInScreen = enabled;
    }

    public boolean isAttachedInDecor() {
        return this.mAttachedInDecor;
    }

    public void setAttachedInDecor(boolean enabled) {
        this.mAttachedInDecor = enabled;
        this.mAttachedInDecorSet = true;
    }

    public void setLayoutInsetDecor(boolean enabled) {
        this.mLayoutInsetDecor = enabled;
    }

    protected final boolean isLayoutInsetDecor() {
        return this.mLayoutInsetDecor;
    }

    public void setWindowLayoutType(int layoutType) {
        this.mWindowLayoutType = layoutType;
    }

    public int getWindowLayoutType() {
        return this.mWindowLayoutType;
    }

    public boolean isTouchModal() {
        return !this.mNotTouchModal;
    }

    public void setTouchModal(boolean touchModal) {
        this.mNotTouchModal = !touchModal;
    }

    @Deprecated
    public void setWindowLayoutMode(int widthSpec, int heightSpec) {
        this.mWidthMode = widthSpec;
        this.mHeightMode = heightSpec;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public void setHeight(int height) {
        this.mHeight = height;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public void setWidth(int width) {
        this.mWidth = width;
    }

    public void setOverlapAnchor(boolean overlapAnchor) {
        this.mOverlapAnchor = overlapAnchor;
    }

    public boolean getOverlapAnchor() {
        return this.mOverlapAnchor;
    }

    public boolean isShowing() {
        return this.mIsShowing;
    }

    protected final void setShowing(boolean isShowing) {
        this.mIsShowing = isShowing;
    }

    protected final void setDropDown(boolean isDropDown) {
        this.mIsDropdown = isDropDown;
    }

    protected final void setTransitioningToDismiss(boolean transitioningToDismiss) {
        this.mIsTransitioningToDismiss = transitioningToDismiss;
    }

    protected final boolean isTransitioningToDismiss() {
        return this.mIsTransitioningToDismiss;
    }

    public void showAtLocation(View parent, int gravity, int x, int y) {
        this.mParentRootView = new WeakReference<>(parent.getRootView());
        showAtLocation(parent.getWindowToken(), gravity, x, y);
    }

    public void showAtLocation(IBinder token, int gravity, int x, int y) {
        if (isShowing() || this.mContentView == null) {
            return;
        }
        TransitionManager.endTransitions(this.mDecorView);
        detachFromAnchor();
        this.mIsShowing = true;
        this.mIsDropdown = false;
        this.mGravity = gravity;
        WindowManager.LayoutParams p = createPopupLayoutParams(token);
        preparePopup(p);
        p.x = x;
        p.y = y;
        invokePopup(p);
    }

    public void showAsDropDown(View anchor) {
        showAsDropDown(anchor, 0, 0);
    }

    public void showAsDropDown(View anchor, int xoff, int yoff) {
        showAsDropDown(anchor, xoff, yoff, DEFAULT_ANCHORED_GRAVITY);
    }

    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        if (isShowing() || !hasContentView()) {
            return;
        }
        TransitionManager.endTransitions(this.mDecorView);
        attachToAnchor(anchor, xoff, yoff, gravity);
        this.mIsShowing = true;
        this.mIsDropdown = true;
        WindowManager.LayoutParams p = createPopupLayoutParams(anchor.getApplicationWindowToken());
        preparePopup(p);
        boolean aboveAnchor =
                findDropDownPosition(
                        anchor,
                        p,
                        xoff,
                        yoff,
                        p.width,
                        p.height,
                        gravity,
                        this.mAllowScrollingAnchorParent);
        updateAboveAnchor(aboveAnchor);
        p.accessibilityIdOfAnchor = anchor != null ? anchor.getAccessibilityViewId() : -1L;
        invokePopup(p);
    }

    public void semShowPopupWindow(WindowManager.LayoutParams params) {
        if (isShowing() || !hasContentView()) {
            return;
        }
        TransitionManager.endTransitions(this.mDecorView);
        detachFromAnchor();
        this.mIsShowing = true;
        this.mIsDropdown = false;
        preparePopup(params);
        invokePopup(params);
    }

    protected final void updateAboveAnchor(boolean aboveAnchor) {
        if (aboveAnchor != this.mAboveAnchor) {
            this.mAboveAnchor = aboveAnchor;
            if (this.mBackground != null && this.mBackgroundView != null) {
                if (this.mAboveAnchorBackgroundDrawable != null) {
                    if (this.mAboveAnchor) {
                        this.mBackgroundView.setBackground(this.mAboveAnchorBackgroundDrawable);
                        return;
                    } else {
                        this.mBackgroundView.setBackground(this.mBelowAnchorBackgroundDrawable);
                        return;
                    }
                }
                this.mBackgroundView.refreshDrawableState();
            }
        }
    }

    public boolean isAboveAnchor() {
        return this.mAboveAnchor;
    }

    private void preparePopup(WindowManager.LayoutParams p) {
        if (this.mContentView == null || this.mContext == null || this.mWindowManager == null) {
            throw new IllegalStateException(
                    "You must specify a valid content view by calling setContentView() before"
                        + " attempting to show the popup.");
        }
        if (p.accessibilityTitle == null) {
            p.accessibilityTitle = this.mContext.getString(R.string.popup_window_default_title);
        }
        if (this.mDecorView != null) {
            this.mDecorView.cancelTransitions();
        }
        if (this.mBackground != null) {
            this.mBackgroundView = createBackgroundView(this.mContentView);
            this.mBackgroundView.setBackground(this.mBackground);
            if (this.mIsDeviceDefault) {
                this.mBackgroundView.setClipToOutline(true);
            }
        } else {
            this.mBackgroundView = this.mContentView;
        }
        this.mDecorView = createDecorView(this.mBackgroundView);
        this.mDecorView.setIsRootNamespace(true);
        if (this.mIsDeviceDefault && (this.mBackground instanceof NinePatchDrawable)) {
            this.mBackgroundView.setElevation(0.0f);
            this.mBackgroundView.setClipToOutline(false);
        } else {
            this.mBackgroundView.setElevation(this.mElevation);
        }
        p.setSurfaceInsets(this.mBackgroundView, true, true);
        this.mPopupViewInitialLayoutDirectionInherited =
                this.mContentView.getRawLayoutDirection() == 2;
    }

    private PopupBackgroundView createBackgroundView(View contentView) {
        int height;
        ViewGroup.LayoutParams layoutParams = this.mContentView.getLayoutParams();
        if (layoutParams != null && layoutParams.height == -2) {
            height = -2;
        } else {
            height = -1;
        }
        PopupBackgroundView backgroundView = new PopupBackgroundView(this.mContext);
        FrameLayout.LayoutParams listParams = new FrameLayout.LayoutParams(-1, height);
        backgroundView.addView(contentView, listParams);
        return backgroundView;
    }

    private PopupDecorView createDecorView(View contentView) {
        int height;
        ViewGroup.LayoutParams layoutParams = this.mContentView.getLayoutParams();
        if (layoutParams != null && layoutParams.height == -2) {
            height = -2;
        } else {
            height = -1;
        }
        PopupDecorView decorView = new PopupDecorView(this.mContext);
        decorView.addView(contentView, -1, height);
        decorView.setClipChildren(false);
        decorView.setClipToPadding(false);
        return decorView;
    }

    private void invokePopup(WindowManager.LayoutParams p) {
        View parentRoot;
        WindowInsets windowInsets;
        if (this.mContext != null) {
            p.packageName = this.mContext.getPackageName();
        }
        PopupDecorView decorView = this.mDecorView;
        decorView.setFitsSystemWindows(this.mLayoutInsetDecor);
        setLayoutDirectionFromAnchor();
        if (CoreRune.MW_CAPTION_SHELL_DEX
                && this.mParentRootView != null
                && (parentRoot = this.mParentRootView.get()) != null) {
            Configuration config = parentRoot.getResources().getConfiguration();
            if ((config.isDexMode()
                            || (CoreRune.MT_NEW_DEX
                                    && config.isNewDexMode()
                                    && config.windowConfiguration.getWindowingMode() == 1))
                    && (windowInsets = parentRoot.getRootWindowInsets()) != null) {
                Insets insets = windowInsets.getInsets(WindowInsets.Type.captionBar());
                if (p.y < insets.top) {
                    p.y = insets.top;
                }
            }
        }
        this.mWindowManager.addView(decorView, p);
        if (this.mEnterTransition != null) {
            decorView.requestEnterTransition(this.mEnterTransition);
        }
    }

    private void setLayoutDirectionFromAnchor() {
        if (this.mAnchor != null) {
            View anchor = this.mAnchor.get();
            anchor.resolveLayoutDirection();
            if (anchor != null && this.mPopupViewInitialLayoutDirectionInherited) {
                this.mDecorView.setLayoutDirection(anchor.getLayoutDirection());
            }
        }
    }

    private int computeGravity() {
        int gravity = this.mGravity == 0 ? DEFAULT_ANCHORED_GRAVITY : this.mGravity;
        if (!this.mIsDropdown) {
            return gravity;
        }
        if (this.mClipToScreen || this.mClippingEnabled) {
            return gravity | 268435456;
        }
        return gravity;
    }

    protected WindowManager.LayoutParams createPopupLayoutParams(IBinder token) {
        WindowManager.LayoutParams p = new WindowManager.LayoutParams();
        p.gravity = computeGravity();
        p.flags = computeFlags(p.flags);
        p.type = this.mWindowLayoutType;
        p.token = token;
        p.softInputMode = this.mSoftInputMode;
        p.windowAnimations = computeAnimationResource();
        if (this.mBackground != null) {
            p.format = this.mBackground.getOpacity();
        } else {
            p.format = -3;
        }
        if (this.mHeightMode < 0) {
            int i = this.mHeightMode;
            this.mLastHeight = i;
            p.height = i;
        } else {
            int i2 = this.mHeight;
            this.mLastHeight = i2;
            p.height = i2;
        }
        if (this.mWidthMode < 0) {
            int i3 = this.mWidthMode;
            this.mLastWidth = i3;
            p.width = i3;
        } else {
            int i4 = this.mWidth;
            this.mLastWidth = i4;
            p.width = i4;
        }
        p.privateFlags = 16384;
        p.samsungFlags |= 131072;
        p.setTitle("PopupWindow:" + Integer.toHexString(hashCode()));
        return p;
    }

    private int computeFlags(int curFlags) {
        int curFlags2 = curFlags & (-9339417);
        if (this.mIgnoreCheekPress) {
            curFlags2 |= 32768;
        }
        if (!this.mFocusable) {
            curFlags2 |= 8;
            if (this.mInputMethodMode == 1) {
                curFlags2 |= 131072;
            }
        } else if (this.mInputMethodMode == 2) {
            curFlags2 |= 131072;
        }
        if (!this.mTouchable) {
            curFlags2 |= 16;
        }
        if (this.mOutsideTouchable) {
            curFlags2 |= 262144;
        }
        if (!this.mClippingEnabled || this.mClipToScreen) {
            curFlags2 |= 512;
        }
        if (isSplitTouchEnabled()) {
            curFlags2 |= 8388608;
        }
        if (this.mLayoutInScreen) {
            curFlags2 |= 256;
        }
        if (this.mLayoutInsetDecor) {
            curFlags2 |= 65536;
        }
        if (this.mNotTouchModal) {
            curFlags2 |= 32;
        }
        if (this.mAttachedInDecor) {
            curFlags2 |= 1073741824;
        }
        if (this.mShowWhenLocked) {
            return curFlags2 | 524288;
        }
        return curFlags2;
    }

    private int computeAnimationResource() {
        if (this.mAnimationStyle == -1) {
            if (this.mIsDropdown) {
                if (this.mAboveAnchor) {
                    return R.style.Animation_DropDownUp;
                }
                return R.style.Animation_DropDownDown;
            }
            return 0;
        }
        return this.mAnimationStyle;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r29v4 */
    /* JADX WARN: Type inference failed for: r29v5 */
    /* JADX WARN: Type inference failed for: r29v8 */
    protected boolean findDropDownPosition(
            View view,
            WindowManager.LayoutParams layoutParams,
            int i,
            int i2,
            int i3,
            int i4,
            int i5,
            boolean z) {
        int i6;
        int i7;
        boolean z2;
        int i8;
        WindowManager.LayoutParams layoutParams2;
        int i9;
        boolean z3;
        WindowManager.LayoutParams layoutParams3;
        boolean z4;
        int height = view.getHeight();
        int width = view.getWidth();
        if (!this.mOverlapAnchor) {
            i6 = i2;
        } else {
            i6 = i2 - height;
        }
        int[] iArr = this.mTmpAppLocation;
        View appRootView = getAppRootView(view);
        appRootView.getLocationOnScreen(iArr);
        int[] iArr2 = this.mTmpScreenLocation;
        view.getLocationOnScreen(iArr2);
        int[] iArr3 = this.mTmpDrawingLocation;
        iArr3[0] = iArr2[0] - iArr[0];
        iArr3[1] = iArr2[1] - iArr[1];
        layoutParams.x = iArr3[0] + i;
        layoutParams.y = iArr3[1] + height + i6;
        Rect rect = new Rect();
        getVisibleDisplayRect(appRootView, rect);
        if (i3 != -1) {
            i7 = i3;
        } else {
            i7 = rect.right - rect.left;
        }
        int semGetCenterPointForFoldable = semGetCenterPointForFoldable();
        int i10 =
                (semGetCenterPointForFoldable == 0 || iArr3[1] >= semGetCenterPointForFoldable)
                        ? rect.bottom
                        : semGetCenterPointForFoldable;
        int i11 =
                (semGetCenterPointForFoldable == 0 || iArr3[1] < semGetCenterPointForFoldable)
                        ? rect.top
                        : semGetCenterPointForFoldable;
        if (iArr[1] < semGetCenterPointForFoldable) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (i4 != -1) {
            i8 = i4;
        } else {
            i8 = z2 ? i10 - i11 : rect.bottom - rect.top;
        }
        layoutParams.gravity = computeGravity();
        layoutParams.width = i7;
        layoutParams.height = i8;
        int absoluteGravity = Gravity.getAbsoluteGravity(i5, view.getLayoutDirection()) & 7;
        if (absoluteGravity == 5) {
            layoutParams.x -= i7 - width;
        }
        int i12 = i7;
        int i13 = i8;
        boolean tryFitVertical =
                tryFitVertical(
                        layoutParams,
                        i6,
                        i8,
                        height,
                        iArr3[1],
                        iArr2[1],
                        z2 ? i11 : rect.top,
                        z2 ? i10 : rect.bottom,
                        false);
        boolean tryFitHorizontal =
                tryFitHorizontal(
                        layoutParams,
                        i,
                        i12,
                        width,
                        iArr3[0],
                        iArr2[0],
                        rect.left,
                        rect.right,
                        false);
        if (tryFitVertical && tryFitHorizontal) {
            z4 = 1;
            layoutParams3 = layoutParams;
        } else {
            int scrollX = view.getScrollX();
            int scrollY = view.getScrollY();
            Rect rect2 = new Rect(scrollX, scrollY, scrollX + i12 + i, scrollY + i13 + height + i6);
            if (!z) {
                layoutParams2 = layoutParams;
                i9 = absoluteGravity;
                z3 = true;
            } else if (!view.requestRectangleOnScreen(rect2, true)) {
                layoutParams2 = layoutParams;
                z3 = true;
                i9 = absoluteGravity;
            } else {
                view.getLocationOnScreen(iArr2);
                iArr3[0] = iArr2[0] - iArr[0];
                iArr3[1] = iArr2[1] - iArr[1];
                int i14 = iArr3[0] + i;
                layoutParams2 = layoutParams;
                boolean z5 = true;
                layoutParams2.x = i14;
                layoutParams2.y = iArr3[1] + height + i6;
                i9 = absoluteGravity;
                z3 = z5;
                if (i9 == 5) {
                    layoutParams2.x -= i12 - width;
                    z3 = z5;
                }
            }
            layoutParams3 = layoutParams2;
            tryFitVertical(
                    layoutParams,
                    i6,
                    i13,
                    height,
                    iArr3[z3 ? 1 : 0],
                    iArr2[z3 ? 1 : 0],
                    z2 ? i11 : rect.top,
                    z2 ? i10 : rect.bottom,
                    this.mClipToScreen);
            tryFitHorizontal(
                    layoutParams,
                    i,
                    i12,
                    width,
                    iArr3[0],
                    iArr2[0],
                    rect.left,
                    rect.right,
                    this.mClipToScreen);
            z4 = z3;
        }
        if (layoutParams3.y < iArr3[z4]) {
            return z4;
        }
        return false;
    }

    private void getVisibleDisplayRect(View rootView, Rect outRect) {
        if (rootView == null || outRect == null) {
            return;
        }
        boolean isWindowPopupOutsideBound = false;
        WindowManager.LayoutParams wlp = null;
        ViewGroup.LayoutParams vlp = rootView.getLayoutParams();
        if (this.mIsDeviceDefault && (vlp instanceof WindowManager.LayoutParams)) {
            wlp = (WindowManager.LayoutParams) vlp;
            isWindowPopupOutsideBound = (wlp.flags & 512) != 0;
        }
        boolean hasStatusBar = false;
        if (wlp != null && this.mContext != null) {
            int systemUiVisibility = wlp.systemUiVisibility | wlp.subtreeSystemUiVisibility;
            hasStatusBar = (wlp.flags & 1024) == 0 && (systemUiVisibility & 1028) == 0;
        }
        Rect visibleDisplayFrame = new Rect();
        if (isWindowPopupOutsideBound) {
            Display dm = this.mWindowManager.getDefaultDisplay();
            Point size = new Point();
            dm.getRealSize(size);
            visibleDisplayFrame.left = 0;
            visibleDisplayFrame.top = hasStatusBar ? this.mStatusBarHeight : 0;
            visibleDisplayFrame.right = size.x;
            visibleDisplayFrame.bottom = size.y;
            if (ViewRune.NAVIBAR_ENABLED && this.mContext != null) {
                int orientation = this.mContext.getResources().getConfiguration().orientation;
                if (orientation != 2) {
                    visibleDisplayFrame.bottom -= this.mNavigationBarHeight;
                }
            }
        } else {
            rootView.getWindowVisibleDisplayFrame(visibleDisplayFrame);
            if (this.mIsDeviceDefault && hasStatusBar && visibleDisplayFrame.top == 0) {
                visibleDisplayFrame.top = this.mStatusBarHeight;
            }
        }
        outRect.set(visibleDisplayFrame);
    }

    private boolean tryFitVertical(
            WindowManager.LayoutParams outParams,
            int yOffset,
            int height,
            int anchorHeight,
            int drawingLocationY,
            int screenLocationY,
            int displayFrameTop,
            int displayFrameBottom,
            boolean allowResize) {
        int yOffset2;
        int yOffset3;
        int winOffsetY = screenLocationY - drawingLocationY;
        int anchorTopInScreen = outParams.y + winOffsetY;
        int spaceBelow = displayFrameBottom - anchorTopInScreen;
        if (anchorTopInScreen >= displayFrameTop && height <= spaceBelow) {
            return true;
        }
        int spaceAbove = (anchorTopInScreen - anchorHeight) - displayFrameTop;
        if (height <= spaceAbove) {
            if (this.mIsDeviceDefault) {
                if (drawingLocationY <= displayFrameBottom) {
                    if (!this.mOverlapAnchor) {
                        yOffset3 = yOffset;
                    } else {
                        yOffset3 = yOffset + anchorHeight;
                    }
                    outParams.y = (drawingLocationY - height) + yOffset3;
                    return true;
                }
            } else {
                if (!this.mOverlapAnchor) {
                    yOffset2 = yOffset;
                } else {
                    yOffset2 = yOffset + anchorHeight;
                }
                outParams.y = (drawingLocationY - height) + yOffset2;
                return true;
            }
        }
        if (positionInDisplayVertical(
                outParams,
                height,
                drawingLocationY,
                screenLocationY,
                displayFrameTop,
                displayFrameBottom,
                allowResize)) {
            return true;
        }
        return false;
    }

    private boolean positionInDisplayVertical(
            WindowManager.LayoutParams outParams,
            int height,
            int drawingLocationY,
            int screenLocationY,
            int displayFrameTop,
            int displayFrameBottom,
            boolean canResize) {
        boolean fitsInDisplay = true;
        int winOffsetY = screenLocationY - drawingLocationY;
        outParams.y += winOffsetY;
        outParams.height = height;
        int bottom = outParams.y + height;
        if (bottom > displayFrameBottom) {
            outParams.y -= bottom - displayFrameBottom;
        }
        if (outParams.y < displayFrameTop) {
            outParams.y = displayFrameTop;
            int displayFrameHeight = displayFrameBottom - displayFrameTop;
            if (canResize && height > displayFrameHeight) {
                outParams.height = displayFrameHeight;
            } else {
                fitsInDisplay = false;
            }
        }
        outParams.y -= winOffsetY;
        return fitsInDisplay;
    }

    private boolean tryFitHorizontal(
            WindowManager.LayoutParams outParams,
            int xOffset,
            int width,
            int anchorWidth,
            int drawingLocationX,
            int screenLocationX,
            int displayFrameLeft,
            int displayFrameRight,
            boolean allowResize) {
        int winOffsetX = screenLocationX - drawingLocationX;
        int anchorLeftInScreen = outParams.x + winOffsetX;
        int spaceRight = displayFrameRight - anchorLeftInScreen;
        if (anchorLeftInScreen >= displayFrameLeft && width <= spaceRight) {
            return true;
        }
        if (positionInDisplayHorizontal(
                outParams,
                width,
                drawingLocationX,
                screenLocationX,
                displayFrameLeft,
                displayFrameRight,
                allowResize)) {
            return true;
        }
        return false;
    }

    private boolean positionInDisplayHorizontal(
            WindowManager.LayoutParams outParams,
            int width,
            int drawingLocationX,
            int screenLocationX,
            int displayFrameLeft,
            int displayFrameRight,
            boolean canResize) {
        boolean fitsInDisplay = true;
        int winOffsetX = screenLocationX - drawingLocationX;
        outParams.x += winOffsetX;
        int right = outParams.x + width;
        if (right > displayFrameRight) {
            outParams.x -= right - displayFrameRight;
        }
        if (outParams.x < displayFrameLeft) {
            outParams.x = displayFrameLeft;
            int displayFrameWidth = displayFrameRight - displayFrameLeft;
            if (canResize && width > displayFrameWidth) {
                outParams.width = displayFrameWidth;
            } else {
                fitsInDisplay = false;
            }
        }
        outParams.x -= winOffsetX;
        return fitsInDisplay;
    }

    public int getMaxAvailableHeight(View anchor) {
        return getMaxAvailableHeight(anchor, 0);
    }

    public int getMaxAvailableHeight(View anchor, int yOffset) {
        return getMaxAvailableHeight(anchor, yOffset, false);
    }

    public int getMaxAvailableHeight(View anchor, int yOffset, boolean ignoreBottomDecorations) {
        Rect displayFrame;
        int distanceToBottom;
        Rect visibleDisplayFrame = new Rect();
        View appView = getAppRootView(anchor);
        appView.getWindowVisibleDisplayFrame(visibleDisplayFrame);
        if (ignoreBottomDecorations) {
            displayFrame = new Rect();
            anchor.getWindowDisplayFrame(displayFrame);
            displayFrame.top = visibleDisplayFrame.top;
            displayFrame.right = visibleDisplayFrame.right;
            displayFrame.left = visibleDisplayFrame.left;
            if (this.mIsDeviceDefault && ViewRune.NAVIBAR_ENABLED) {
                int orientation = this.mContext.getResources().getConfiguration().orientation;
                if (orientation != 2) {
                    displayFrame.bottom -= this.mNavigationBarHeight;
                }
            }
        } else {
            displayFrame = visibleDisplayFrame;
            ViewGroup.LayoutParams vlp = appView.getLayoutParams();
            if (this.mIsDeviceDefault
                    && (vlp instanceof WindowManager.LayoutParams)
                    && this.mContext != null) {
                WindowManager.LayoutParams wlp = (WindowManager.LayoutParams) vlp;
                int systemUiVisibility = wlp.systemUiVisibility | wlp.subtreeSystemUiVisibility;
                boolean hasStatusBar = (wlp.flags & 1024) == 0 && (systemUiVisibility & 1028) == 0;
                if (hasStatusBar && displayFrame.top == 0) {
                    displayFrame.top = this.mStatusBarHeight;
                }
            }
        }
        int[] anchorPos = this.mTmpDrawingLocation;
        anchor.getLocationOnScreen(anchorPos);
        int centerPoint = semGetCenterPointForFoldable();
        int bottomEdge =
                (centerPoint == 0 || anchorPos[1] >= centerPoint)
                        ? displayFrame.bottom
                        : centerPoint;
        if (this.mOverlapAnchor) {
            distanceToBottom = (bottomEdge - anchorPos[1]) - yOffset;
        } else {
            int distanceToBottom2 = anchorPos[1];
            distanceToBottom = (bottomEdge - (distanceToBottom2 + anchor.getHeight())) - yOffset;
        }
        int distanceToTop = (anchorPos[1] - displayFrame.top) + yOffset;
        int returnedHeight = Math.max(distanceToBottom, distanceToTop);
        if (this.mBackground != null) {
            this.mBackground.getPadding(this.mTempRect);
            return returnedHeight - (this.mTempRect.top + this.mTempRect.bottom);
        }
        return returnedHeight;
    }

    public void dismiss() {
        final ViewGroup contentHolder;
        if (!isShowing() || isTransitioningToDismiss()) {
            return;
        }
        final PopupDecorView decorView = this.mDecorView;
        final View contentView = this.mContentView;
        unregisterBackCallback(decorView.findOnBackInvokedDispatcher());
        ViewParent contentParent = contentView.getParent();
        if (contentParent instanceof ViewGroup) {
            contentHolder = (ViewGroup) contentParent;
        } else {
            contentHolder = null;
        }
        if (decorView == null) {
            return;
        }
        decorView.cancelTransitions();
        this.mIsShowing = false;
        this.mIsTransitioningToDismiss = true;
        Transition exitTransition = this.mExitTransition;
        if (exitTransition != null
                && decorView.isLaidOut()
                && (this.mIsAnchorRootAttached || this.mAnchorRoot == null)) {
            WindowManager.LayoutParams p = (WindowManager.LayoutParams) decorView.getLayoutParams();
            p.flags |= 16;
            p.flags |= 8;
            p.flags &= -131073;
            this.mWindowManager.updateViewLayout(decorView, p);
            View anchorRoot = this.mAnchorRoot != null ? this.mAnchorRoot.get() : null;
            Rect epicenter = getTransitionEpicenter();
            decorView.startExitTransition(
                    exitTransition,
                    anchorRoot,
                    epicenter,
                    new TransitionListenerAdapter() { // from class: android.widget.PopupWindow.3
                        @Override // android.transition.TransitionListenerAdapter,
                                  // android.transition.Transition.TransitionListener
                        public void onTransitionEnd(Transition transition) {
                            PopupWindow.this.dismissImmediate(
                                    decorView, contentHolder, contentView);
                        }
                    });
        } else {
            dismissImmediate(decorView, contentHolder, contentView);
        }
        detachFromAnchor();
        if (this.mOnDismissListener != null) {
            this.mOnDismissListener.onDismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unregisterBackCallback(OnBackInvokedDispatcher onBackInvokedDispatcher) {
        OnBackInvokedCallback backCallback = this.mBackCallback;
        this.mBackCallback = null;
        if (onBackInvokedDispatcher != null && backCallback != null) {
            onBackInvokedDispatcher.unregisterOnBackInvokedCallback(backCallback);
        }
    }

    protected final Rect getTransitionEpicenter() {
        View anchor = this.mAnchor != null ? this.mAnchor.get() : null;
        View decor = this.mDecorView;
        if (anchor == null || decor == null) {
            return null;
        }
        int[] anchorLocation = anchor.getLocationOnScreen();
        int[] popupLocation = this.mDecorView.getLocationOnScreen();
        Rect bounds = new Rect(0, 0, anchor.getWidth(), anchor.getHeight());
        bounds.offset(anchorLocation[0] - popupLocation[0], anchorLocation[1] - popupLocation[1]);
        if (this.mEpicenterBounds != null) {
            int offsetX = bounds.left;
            int offsetY = bounds.top;
            bounds.set(this.mEpicenterBounds);
            bounds.offset(offsetX, offsetY);
        }
        return bounds;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissImmediate(View decorView, ViewGroup contentHolder, View contentView) {
        if (decorView.getParent() != null) {
            this.mWindowManager.removeViewImmediate(decorView);
        }
        if (contentHolder != null) {
            contentHolder.removeView(contentView);
        }
        this.mDecorView = null;
        this.mBackgroundView = null;
        this.mIsTransitioningToDismiss = false;
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    protected final OnDismissListener getOnDismissListener() {
        return this.mOnDismissListener;
    }

    public void update() {
        if (!isShowing() || !hasContentView()) {
            return;
        }
        WindowManager.LayoutParams p = getDecorViewLayoutParams();
        boolean update = false;
        int newAnim = computeAnimationResource();
        if (newAnim != p.windowAnimations) {
            p.windowAnimations = newAnim;
            update = true;
        }
        int newFlags = computeFlags(p.flags);
        if (newFlags != p.flags) {
            p.flags = newFlags;
            update = true;
        }
        int newGravity = computeGravity();
        if (newGravity != p.gravity) {
            p.gravity = newGravity;
            update = true;
        }
        if (update) {
            update(this.mAnchor != null ? this.mAnchor.get() : null, p);
        }
    }

    protected void update(View anchor, WindowManager.LayoutParams params) {
        setLayoutDirectionFromAnchor();
        this.mWindowManager.updateViewLayout(this.mDecorView, params);
    }

    public void update(int width, int height) {
        WindowManager.LayoutParams p = getDecorViewLayoutParams();
        update(p.x, p.y, width, height, false);
    }

    public void update(int x, int y, int width, int height) {
        update(x, y, width, height, false);
    }

    public void update(int x, int y, int width, int height, boolean force) {
        if (width >= 0) {
            this.mLastWidth = width;
            setWidth(width);
        }
        if (height >= 0) {
            this.mLastHeight = height;
            setHeight(height);
        }
        if (!isShowing() || !hasContentView()) {
            return;
        }
        WindowManager.LayoutParams p = getDecorViewLayoutParams();
        boolean update = force;
        int finalWidth = this.mWidthMode < 0 ? this.mWidthMode : this.mLastWidth;
        if (width != -1 && p.width != finalWidth) {
            this.mLastWidth = finalWidth;
            p.width = finalWidth;
            update = true;
        }
        int finalHeight = this.mHeightMode < 0 ? this.mHeightMode : this.mLastHeight;
        if (height != -1 && p.height != finalHeight) {
            this.mLastHeight = finalHeight;
            p.height = finalHeight;
            update = true;
        }
        if (p.x != x) {
            p.x = x;
            update = true;
        }
        if (p.y != y) {
            p.y = y;
            update = true;
        }
        int newAnim = computeAnimationResource();
        if (newAnim != p.windowAnimations) {
            p.windowAnimations = newAnim;
            update = true;
        }
        int newFlags = computeFlags(p.flags);
        if (newFlags != p.flags) {
            p.flags = newFlags;
            update = true;
        }
        int newGravity = computeGravity();
        if (newGravity != p.gravity) {
            p.gravity = newGravity;
            update = true;
        }
        View anchor = null;
        int newAccessibilityIdOfAnchor = -1;
        if (this.mAnchor != null && this.mAnchor.get() != null) {
            anchor = this.mAnchor.get();
            newAccessibilityIdOfAnchor = anchor.getAccessibilityViewId();
        }
        if (newAccessibilityIdOfAnchor != p.accessibilityIdOfAnchor) {
            p.accessibilityIdOfAnchor = newAccessibilityIdOfAnchor;
            update = true;
        }
        if (update) {
            update(anchor, p);
        }
    }

    protected boolean hasContentView() {
        return this.mContentView != null;
    }

    protected boolean hasDecorView() {
        return this.mDecorView != null;
    }

    protected WindowManager.LayoutParams getDecorViewLayoutParams() {
        return (WindowManager.LayoutParams) this.mDecorView.getLayoutParams();
    }

    public void update(View anchor, int width, int height) {
        update(anchor, false, 0, 0, width, height);
    }

    public void update(View anchor, int xoff, int yoff, int width, int height) {
        update(anchor, true, xoff, yoff, width, height);
    }

    private void update(
            View anchor, boolean updateLocation, int xoff, int yoff, int width, int height) {
        int width2;
        int height2;
        if (!isShowing() || !hasContentView()) {
            return;
        }
        WeakReference<View> oldAnchor = this.mAnchor;
        int gravity = this.mAnchoredGravity;
        boolean needsUpdate =
                updateLocation && !(this.mAnchorXoff == xoff && this.mAnchorYoff == yoff);
        if (oldAnchor == null || oldAnchor.get() != anchor || (needsUpdate && !this.mIsDropdown)) {
            attachToAnchor(anchor, xoff, yoff, gravity);
        } else if (needsUpdate) {
            this.mAnchorXoff = xoff;
            this.mAnchorYoff = yoff;
        }
        WindowManager.LayoutParams p = getDecorViewLayoutParams();
        int oldGravity = p.gravity;
        int oldWidth = p.width;
        int oldHeight = p.height;
        int oldX = p.x;
        int oldY = p.y;
        if (width >= 0) {
            width2 = width;
        } else {
            width2 = this.mWidth;
        }
        if (height >= 0) {
            height2 = height;
        } else {
            height2 = this.mHeight;
        }
        int oldHeight2 = width2;
        int oldWidth2 = height2;
        boolean aboveAnchor =
                findDropDownPosition(
                        anchor,
                        p,
                        this.mAnchorXoff,
                        this.mAnchorYoff,
                        oldHeight2,
                        oldWidth2,
                        gravity,
                        this.mAllowScrollingAnchorParent);
        updateAboveAnchor(aboveAnchor);
        boolean paramsChanged =
                (oldGravity == p.gravity
                                && oldX == p.x
                                && oldY == p.y
                                && oldWidth == p.width
                                && oldHeight == p.height)
                        ? false
                        : true;
        int newWidth = width2 < 0 ? width2 : p.width;
        int newHeight = height2 < 0 ? height2 : p.height;
        update(p.x, p.y, newWidth, newHeight, paramsChanged);
    }

    protected void detachFromAnchor() {
        View anchor = getAnchor();
        if (anchor != null) {
            ViewTreeObserver vto = anchor.getViewTreeObserver();
            vto.removeOnScrollChangedListener(this.mOnScrollChangedListener);
            anchor.removeOnAttachStateChangeListener(this.mOnAnchorDetachedListener);
        }
        View anchorRoot = this.mAnchorRoot != null ? this.mAnchorRoot.get() : null;
        if (anchorRoot != null) {
            anchorRoot.removeOnAttachStateChangeListener(this.mOnAnchorRootDetachedListener);
            anchorRoot.removeOnLayoutChangeListener(this.mOnLayoutChangeListener);
        }
        this.mAnchor = null;
        this.mAnchorRoot = null;
        this.mIsAnchorRootAttached = false;
    }

    protected void attachToAnchor(View anchor, int xoff, int yoff, int gravity) {
        detachFromAnchor();
        ViewTreeObserver vto = anchor.getViewTreeObserver();
        if (vto != null) {
            vto.addOnScrollChangedListener(this.mOnScrollChangedListener);
        }
        anchor.addOnAttachStateChangeListener(this.mOnAnchorDetachedListener);
        View anchorRoot = anchor.getRootView();
        anchorRoot.addOnAttachStateChangeListener(this.mOnAnchorRootDetachedListener);
        anchorRoot.addOnLayoutChangeListener(this.mOnLayoutChangeListener);
        this.mAnchor = new WeakReference<>(anchor);
        this.mAnchorRoot = new WeakReference<>(anchorRoot);
        this.mIsAnchorRootAttached = anchorRoot.isAttachedToWindow();
        this.mParentRootView = this.mAnchorRoot;
        this.mAnchorXoff = xoff;
        this.mAnchorYoff = yoff;
        this.mAnchoredGravity = gravity;
    }

    protected View getAnchor() {
        if (this.mAnchor != null) {
            return this.mAnchor.get();
        }
        return null;
    }

    private void alignToAnchor(
            int left,
            int top,
            int right,
            int bottom,
            int oldLeft,
            int oldTop,
            int oldRight,
            int oldBottom) {
        if (this.mIsDeviceDefault) {
            if (left != oldLeft || top != oldTop || right != oldRight || bottom != oldBottom) {
                alignToAnchor();
                return;
            }
            return;
        }
        alignToAnchor();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void alignToAnchor() {
        View anchor = this.mAnchor != null ? this.mAnchor.get() : null;
        if (anchor != null && anchor.isAttachedToWindow() && hasDecorView()) {
            WindowManager.LayoutParams p = getDecorViewLayoutParams();
            updateAboveAnchor(
                    findDropDownPosition(
                            anchor,
                            p,
                            this.mAnchorXoff,
                            this.mAnchorYoff,
                            p.width,
                            p.height,
                            this.mAnchoredGravity,
                            false));
            if (this.mIsDeviceDefault) {
                if (p.height != 0) {
                    update(p.x, p.y, -1, -1, true);
                    return;
                }
                return;
            }
            update(p.x, p.y, -1, -1, true);
            return;
        }
        if (this.mIsDeviceDefault) {
            dismiss();
        }
    }

    private View getAppRootView(View anchor) {
        View appWindowView =
                WindowManagerGlobal.getInstance().getWindowView(anchor.getApplicationWindowToken());
        if (appWindowView != null) {
            return appWindowView;
        }
        return anchor.getRootView();
    }

    public void semShowWhenLocked(boolean show) {
        if (isSystemApp()) {
            this.mShowWhenLocked = show;
        }
    }

    private boolean isSystemApp() {
        return (this.mContext.getApplicationInfo().flags & 1) != 0;
    }

    public class PopupDecorView extends FrameLayout {
        private Runnable mCleanupAfterExit;
        private boolean mIsPenSelectionMode;
        private final View.OnAttachStateChangeListener mOnAnchorRootDetachedListener;

        public PopupDecorView(Context context) {
            super(context);
            this.mIsPenSelectionMode = false;
            this.mOnAnchorRootDetachedListener =
                    new View
                            .OnAttachStateChangeListener() { // from class:
                                                             // android.widget.PopupWindow.PopupDecorView.4
                        @Override // android.view.View.OnAttachStateChangeListener
                        public void onViewAttachedToWindow(View v) {}

                        @Override // android.view.View.OnAttachStateChangeListener
                        public void onViewDetachedFromWindow(View v) {
                            v.removeOnAttachStateChangeListener(this);
                            if (PopupDecorView.this.isAttachedToWindow()) {
                                TransitionManager.endTransitions(PopupDecorView.this);
                            }
                        }
                    };
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchKeyEvent(KeyEvent event) {
            KeyEvent.DispatcherState state;
            if (event.getKeyCode() == 4 || event.getKeyCode() == 111) {
                if (getKeyDispatcherState() == null) {
                    return super.dispatchKeyEvent(event);
                }
                if (event.getAction() == 0 && event.getRepeatCount() == 0) {
                    KeyEvent.DispatcherState state2 = getKeyDispatcherState();
                    if (state2 != null) {
                        state2.startTracking(event, this);
                    }
                    return true;
                }
                if (event.getAction() == 1
                        && (state = getKeyDispatcherState()) != null
                        && state.isTracking(event)
                        && !event.isCanceled()) {
                    PopupWindow.this.dismiss();
                    return true;
                }
                return super.dispatchKeyEvent(event);
            }
            return super.dispatchKeyEvent(event);
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent ev) {
            if (ViewRune.WIDGET_PEN_SUPPORTED) {
                int action = ev.getAction();
                if (ev.getToolType(0) == 2) {
                    switch (action) {
                        case 0:
                            if ((ev.getButtonState() & 32) != 0) {
                                this.mIsPenSelectionMode = true;
                                ev.setAction(211);
                                break;
                            } else {
                                this.mIsPenSelectionMode = false;
                                break;
                            }
                        case 1:
                            if (this.mIsPenSelectionMode) {
                                ev.setAction(212);
                            }
                            this.mIsPenSelectionMode = false;
                            break;
                        case 2:
                            if (this.mIsPenSelectionMode) {
                                ev.setAction(213);
                                break;
                            }
                            break;
                    }
                }
            }
            if (PopupWindow.this.mTouchInterceptor == null
                    || !PopupWindow.this.mTouchInterceptor.onTouch(this, ev)) {
                return super.dispatchTouchEvent(ev);
            }
            return true;
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent event) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (event.getAction() == 0 && (x < 0 || x >= getWidth() || y < 0 || y >= getHeight())) {
                PopupWindow.this.dismiss();
                return true;
            }
            if (event.getAction() == 4) {
                PopupWindow.this.dismiss();
                return true;
            }
            return super.onTouchEvent(event);
        }

        public void requestEnterTransition(Transition transition) {
            ViewTreeObserver observer = getViewTreeObserver();
            if (observer != null && transition != null) {
                final Transition enterTransition = transition.mo5201clone();
                observer.addOnGlobalLayoutListener(
                        new ViewTreeObserver
                                .OnGlobalLayoutListener() { // from class:
                                                            // android.widget.PopupWindow.PopupDecorView.1
                            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                            public void onGlobalLayout() {
                                ViewTreeObserver observer2 =
                                        PopupDecorView.this.getViewTreeObserver();
                                if (observer2 != null) {
                                    observer2.removeOnGlobalLayoutListener(this);
                                }
                                final Rect epicenter = PopupWindow.this.getTransitionEpicenter();
                                enterTransition.setEpicenterCallback(
                                        new Transition
                                                .EpicenterCallback() { // from class:
                                                                       // android.widget.PopupWindow.PopupDecorView.1.1
                                            @Override // android.transition.Transition.EpicenterCallback
                                            public Rect onGetEpicenter(Transition transition2) {
                                                return epicenter;
                                            }
                                        });
                                PopupDecorView.this.startEnterTransition(enterTransition);
                            }
                        });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void startEnterTransition(Transition enterTransition) {
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                enterTransition.addTarget(child);
                child.setTransitionVisibility(4);
            }
            TransitionManager.beginDelayedTransition(this, enterTransition);
            for (int i2 = 0; i2 < count; i2++) {
                getChildAt(i2).setTransitionVisibility(0);
            }
        }

        public void startExitTransition(
                final Transition transition,
                final View anchorRoot,
                final Rect epicenter,
                final Transition.TransitionListener listener) {
            if (transition == null) {
                return;
            }
            if (anchorRoot != null) {
                anchorRoot.addOnAttachStateChangeListener(this.mOnAnchorRootDetachedListener);
            }
            this.mCleanupAfterExit =
                    new Runnable() { // from class:
                                     // android.widget.PopupWindow$PopupDecorView$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            PopupWindow.PopupDecorView.this.lambda$startExitTransition$0(
                                    listener, transition, anchorRoot);
                        }
                    };
            Transition exitTransition = transition.mo5201clone();
            exitTransition.addListener(
                    new TransitionListenerAdapter() { // from class:
                                                      // android.widget.PopupWindow.PopupDecorView.2
                        @Override // android.transition.TransitionListenerAdapter,
                                  // android.transition.Transition.TransitionListener
                        public void onTransitionEnd(Transition t) {
                            t.removeListener(this);
                            if (PopupDecorView.this.mCleanupAfterExit != null) {
                                PopupDecorView.this.mCleanupAfterExit.run();
                            }
                        }
                    });
            exitTransition.setEpicenterCallback(
                    new Transition
                            .EpicenterCallback() { // from class:
                                                   // android.widget.PopupWindow.PopupDecorView.3
                        @Override // android.transition.Transition.EpicenterCallback
                        public Rect onGetEpicenter(Transition transition2) {
                            return epicenter;
                        }
                    });
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                exitTransition.addTarget(child);
            }
            TransitionManager.beginDelayedTransition(this, exitTransition);
            for (int i2 = 0; i2 < count; i2++) {
                View child2 = getChildAt(i2);
                child2.setVisibility(4);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$startExitTransition$0(
                Transition.TransitionListener listener, Transition transition, View anchorRoot) {
            listener.onTransitionEnd(transition);
            if (anchorRoot != null) {
                anchorRoot.removeOnAttachStateChangeListener(this.mOnAnchorRootDetachedListener);
            }
            this.mCleanupAfterExit = null;
        }

        public void cancelTransitions() {
            TransitionManager.endTransitions(this);
            if (this.mCleanupAfterExit != null) {
                this.mCleanupAfterExit.run();
            }
        }

        @Override // android.view.View
        public void requestKeyboardShortcuts(List<KeyboardShortcutGroup> list, int deviceId) {
            View parentRoot;
            if (PopupWindow.this.mParentRootView != null
                    && (parentRoot = (View) PopupWindow.this.mParentRootView.get()) != null) {
                parentRoot.requestKeyboardShortcuts(list, deviceId);
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onAttachedToWindow() {
            OnBackInvokedDispatcher dispatcher;
            super.onAttachedToWindow();
            if (!WindowOnBackInvokedDispatcher.isOnBackInvokedCallbackEnabled(this.mContext)
                    || (dispatcher = findOnBackInvokedDispatcher()) == null) {
                return;
            }
            PopupWindow popupWindow = PopupWindow.this;
            final PopupWindow popupWindow2 = PopupWindow.this;
            popupWindow.mBackCallback =
                    new OnBackInvokedCallback() { // from class:
                                                  // android.widget.PopupWindow$PopupDecorView$$ExternalSyntheticLambda1
                        @Override // android.window.OnBackInvokedCallback
                        public final void onBackInvoked() {
                            PopupWindow.this.dismiss();
                        }
                    };
            dispatcher.registerOnBackInvokedCallback(0, PopupWindow.this.mBackCallback);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            PopupWindow.this.unregisterBackCallback(findOnBackInvokedDispatcher());
        }
    }

    private class PopupBackgroundView extends FrameLayout {
        public PopupBackgroundView(Context context) {
            super(context);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected int[] onCreateDrawableState(int extraSpace) {
            if (PopupWindow.this.mAboveAnchor) {
                int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
                View.mergeDrawableStates(drawableState, PopupWindow.ABOVE_ANCHOR_STATE_SET);
                return drawableState;
            }
            return super.onCreateDrawableState(extraSpace);
        }
    }

    boolean semIsAvailableBlurBackground() {
        return !this.mIsReplacedPoupBackground;
    }

    int semGetCenterPointForFoldable() {
        DisplayManager displayManager;
        Display display;
        if (this.mContext == null
                || (displayManager =
                                (DisplayManager)
                                        this.mContext.getSystemService(Context.DISPLAY_SERVICE))
                        == null
                || (display = displayManager.getDisplay(0)) == null
                || !SemWindowManager.getInstance().isTableMode()
                || !this.mIsDeviceDefault) {
            return 0;
        }
        try {
            SemMultiWindowManager multiWindowManager = new SemMultiWindowManager();
            if (multiWindowManager.getMode() != 0) {
                return 0;
            }
            Point displaySize = new Point();
            display.getRealSize(displaySize);
            if (ViewRune.supportFoldableDualDisplay()) {
                if (this.mContext.getResources().getConfiguration().orientation != 2) {
                    return 0;
                }
                int centerPoint =
                        (displaySize.y > displaySize.x ? displaySize.x : displaySize.y) / 2;
                return centerPoint;
            }
            if (!ViewRune.supportFoldableNoSubDisplay()
                    || this.mContext.getResources().getConfiguration().orientation != 1) {
                return 0;
            }
            int centerPoint2 = (displaySize.y > displaySize.x ? displaySize.y : displaySize.x) / 2;
            return centerPoint2;
        } catch (Exception e) {
            return 0;
        }
    }

    private View hidden_semGetBackgroundView() {
        return this.mBackgroundView;
    }
}
