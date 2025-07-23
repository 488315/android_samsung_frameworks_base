package android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.StateListDrawable;
import android.hardware.display.DisplayManager;
import android.media.MediaCodecInfo;
import android.os.IBinder;
import android.provider.Settings;
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
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.widget.PopupWindow;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import android.window.WindowOnBackInvokedDispatcher;
import com.android.internal.R;
import com.samsung.android.app.SemMultiWindowManager;
import com.samsung.android.rune.ViewRune;
import com.samsung.android.view.SemWindowManager;
import java.lang.ref.WeakReference;
import java.util.List;

/* loaded from: classes5.dex */
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
    private static final int[] ONEUI_BLUR_POPUP_BACKGROUND_RES = {R.drawable.sem_popup_background_material_dark, R.drawable.sem_popup_background_material, R.drawable.sem_list_popup_background_material_dark, R.drawable.sem_list_popup_background_material, R.drawable.sem_search_popup_background_material_dark, R.drawable.sem_search_popup_background_material, R.drawable.sem_spinner_popup_background_material_dark, R.drawable.sem_spinner_popup_background_material};

    public interface OnDismissListener {
        void onDismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        alignToAnchor(i, i2, i3, i4, i5, i6, i7, i8);
    }

    public PopupWindow(Context context) {
        this(context, (AttributeSet) null);
    }

    public PopupWindow(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842870);
    }

    public PopupWindow(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public PopupWindow(Context context, AttributeSet attributeSet, int i, int i2) {
        Transition mo5495clone;
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
        this.mOnAnchorDetachedListener = new View.OnAttachStateChangeListener() { // from class: android.widget.PopupWindow.1
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
                PopupWindow.this.alignToAnchor();
            }
        };
        this.mOnAnchorRootDetachedListener = new View.OnAttachStateChangeListener() { // from class: android.widget.PopupWindow.2
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
                PopupWindow.this.mIsAnchorRootAttached = false;
            }
        };
        this.mOnScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() { // from class: android.widget.PopupWindow$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public final void onScrollChanged() {
                PopupWindow.this.alignToAnchor();
            }
        };
        this.mOnLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: android.widget.PopupWindow$$ExternalSyntheticLambda1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
                PopupWindow.this.lambda$new$0(view, i3, i4, i5, i6, i7, i8, i9, i10);
            }
        };
        this.mIsDeviceDefault = false;
        this.mStatusBarHeight = 0;
        this.mNavigationBarHeight = 0;
        this.mContext = context;
        this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.PopupWindow, i, i2);
        Drawable drawable = obtainStyledAttributes.getDrawable(0);
        this.mElevation = obtainStyledAttributes.getDimension(3, 0.0f);
        this.mOverlapAnchor = obtainStyledAttributes.getBoolean(2, false);
        if (obtainStyledAttributes.hasValueOrEmpty(1)) {
            int resourceId = obtainStyledAttributes.getResourceId(1, 0);
            if (resourceId == 16974619) {
                this.mAnimationStyle = -1;
            } else {
                this.mAnimationStyle = resourceId;
            }
        } else {
            this.mAnimationStyle = -1;
        }
        Transition transition = getTransition(obtainStyledAttributes.getResourceId(4, 0));
        if (obtainStyledAttributes.hasValueOrEmpty(5)) {
            mo5495clone = getTransition(obtainStyledAttributes.getResourceId(5, 0));
        } else {
            mo5495clone = transition == null ? null : transition.mo5495clone();
        }
        int resourceId2 = obtainStyledAttributes.getResourceId(0, -1);
        boolean z = false;
        for (int i3 : ONEUI_BLUR_POPUP_BACKGROUND_RES) {
            if (i3 == resourceId2) {
                z = true;
            }
        }
        obtainStyledAttributes.recycle();
        setEnterTransition(transition);
        setExitTransition(mo5495clone);
        setBackgroundDrawable(drawable);
        this.mIsReplacedPoupBackground = !z;
        TypedValue typedValue = new TypedValue();
        this.mContext.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, typedValue, false);
        this.mIsDeviceDefault = typedValue.data != 0;
        this.mStatusBarHeight = this.mContext.getResources().getDimensionPixelSize(R.dimen.status_bar_height);
        this.mNavigationBarHeight = this.mContext.getResources().getDimensionPixelSize(R.dimen.navigation_bar_height);
    }

    public PopupWindow() {
        this((View) null, 0, 0);
    }

    public PopupWindow(View view) {
        this(view, 0, 0);
    }

    public PopupWindow(int i, int i2) {
        this((View) null, i, i2);
    }

    public PopupWindow(View view, int i, int i2) {
        this(view, i, i2, false);
    }

    public PopupWindow(View view, int i, int i2, boolean z) {
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
        this.mOnAnchorDetachedListener = new View.OnAttachStateChangeListener() { // from class: android.widget.PopupWindow.1
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view2) {
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view2) {
                PopupWindow.this.alignToAnchor();
            }
        };
        this.mOnAnchorRootDetachedListener = new View.OnAttachStateChangeListener() { // from class: android.widget.PopupWindow.2
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view2) {
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view2) {
                PopupWindow.this.mIsAnchorRootAttached = false;
            }
        };
        this.mOnScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() { // from class: android.widget.PopupWindow$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public final void onScrollChanged() {
                PopupWindow.this.alignToAnchor();
            }
        };
        this.mOnLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: android.widget.PopupWindow$$ExternalSyntheticLambda1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
                PopupWindow.this.lambda$new$0(view2, i3, i4, i5, i6, i7, i8, i9, i10);
            }
        };
        this.mIsDeviceDefault = false;
        this.mStatusBarHeight = 0;
        this.mNavigationBarHeight = 0;
        if (view != null) {
            Context context = view.getContext();
            this.mContext = context;
            this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        setContentView(view);
        setWidth(i);
        setHeight(i2);
        setFocusable(z);
    }

    public void setEnterTransition(Transition transition) {
        this.mEnterTransition = transition;
    }

    public Transition getEnterTransition() {
        return this.mEnterTransition;
    }

    public void setExitTransition(Transition transition) {
        this.mExitTransition = transition;
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

    public void setEpicenterBounds(Rect rect) {
        this.mEpicenterBounds = rect != null ? new Rect(rect) : null;
    }

    private Transition getTransition(int i) {
        Transition inflateTransition;
        if (i == 0 || i == 17760256 || (inflateTransition = TransitionInflater.from(this.mContext).inflateTransition(i)) == null) {
            return null;
        }
        if ((inflateTransition instanceof TransitionSet) && ((TransitionSet) inflateTransition).getTransitionCount() == 0) {
            return null;
        }
        return inflateTransition;
    }

    public Drawable getBackground() {
        return this.mBackground;
    }

    public void setBackgroundDrawable(Drawable drawable) {
        this.mIsReplacedPoupBackground = true;
        this.mBackground = drawable;
        if (drawable instanceof StateListDrawable) {
            StateListDrawable stateListDrawable = (StateListDrawable) drawable;
            int findStateDrawableIndex = stateListDrawable.findStateDrawableIndex(ABOVE_ANCHOR_STATE_SET);
            int stateCount = stateListDrawable.getStateCount();
            int i = 0;
            while (true) {
                if (i >= stateCount) {
                    i = -1;
                    break;
                } else if (i != findStateDrawableIndex) {
                    break;
                } else {
                    i++;
                }
            }
            if (findStateDrawableIndex != -1 && i != -1) {
                this.mAboveAnchorBackgroundDrawable = stateListDrawable.getStateDrawable(findStateDrawableIndex);
                this.mBelowAnchorBackgroundDrawable = stateListDrawable.getStateDrawable(i);
            } else {
                this.mBelowAnchorBackgroundDrawable = null;
                this.mAboveAnchorBackgroundDrawable = null;
            }
        }
    }

    public float getElevation() {
        return this.mElevation;
    }

    public void setElevation(float f) {
        this.mElevation = f;
    }

    public int getAnimationStyle() {
        return this.mAnimationStyle;
    }

    public void setIgnoreCheekPress() {
        this.mIgnoreCheekPress = true;
    }

    public void setAnimationStyle(int i) {
        this.mAnimationStyle = i;
    }

    public View getContentView() {
        return this.mContentView;
    }

    public void setContentView(View view) {
        if (isShowing()) {
            return;
        }
        this.mContentView = view;
        if (this.mContext == null && view != null) {
            this.mContext = view.getContext();
        }
        if (this.mWindowManager == null && this.mContentView != null) {
            this.mWindowManager = (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
        }
        Context context = this.mContext;
        if (context == null || this.mAttachedInDecorSet) {
            return;
        }
        setAttachedInDecor(context.getApplicationInfo().targetSdkVersion >= 22);
    }

    public void setTouchInterceptor(View.OnTouchListener onTouchListener) {
        this.mTouchInterceptor = onTouchListener;
    }

    public boolean isFocusable() {
        return this.mFocusable;
    }

    public void setFocusable(boolean z) {
        this.mFocusable = z;
    }

    public int getInputMethodMode() {
        return this.mInputMethodMode;
    }

    public void setInputMethodMode(int i) {
        this.mInputMethodMode = i;
    }

    public void setSoftInputMode(int i) {
        this.mSoftInputMode = i;
    }

    public int getSoftInputMode() {
        return this.mSoftInputMode;
    }

    public boolean isTouchable() {
        return this.mTouchable;
    }

    public void setTouchable(boolean z) {
        this.mTouchable = z;
    }

    public boolean isOutsideTouchable() {
        return this.mOutsideTouchable;
    }

    public void setOutsideTouchable(boolean z) {
        this.mOutsideTouchable = z;
    }

    public boolean isClippingEnabled() {
        return this.mClippingEnabled;
    }

    public void setClippingEnabled(boolean z) {
        this.mClippingEnabled = z;
    }

    @Deprecated
    public boolean isClipToScreenEnabled() {
        return this.mClipToScreen;
    }

    @Deprecated
    public void setClipToScreenEnabled(boolean z) {
        this.mClipToScreen = z;
    }

    public boolean isClippedToScreen() {
        return this.mClipToScreen;
    }

    public void setIsClippedToScreen(boolean z) {
        this.mClipToScreen = z;
    }

    void setAllowScrollingAnchorParent(boolean z) {
        this.mAllowScrollingAnchorParent = z;
    }

    protected final boolean getAllowScrollingAnchorParent() {
        return this.mAllowScrollingAnchorParent;
    }

    public boolean isSplitTouchEnabled() {
        Context context;
        int i = this.mSplitTouchEnabled;
        return (i >= 0 || (context = this.mContext) == null) ? i == 1 : context.getApplicationInfo().targetSdkVersion >= 11;
    }

    public void setSplitTouchEnabled(boolean z) {
        this.mSplitTouchEnabled = z ? 1 : 0;
    }

    @Deprecated
    public boolean isLayoutInScreenEnabled() {
        return this.mLayoutInScreen;
    }

    @Deprecated
    public void setLayoutInScreenEnabled(boolean z) {
        this.mLayoutInScreen = z;
    }

    public boolean isLaidOutInScreen() {
        return this.mLayoutInScreen;
    }

    public void setIsLaidOutInScreen(boolean z) {
        this.mLayoutInScreen = z;
    }

    public boolean isAttachedInDecor() {
        return this.mAttachedInDecor;
    }

    public void setAttachedInDecor(boolean z) {
        this.mAttachedInDecor = z;
        this.mAttachedInDecorSet = true;
    }

    public void setLayoutInsetDecor(boolean z) {
        this.mLayoutInsetDecor = z;
    }

    protected final boolean isLayoutInsetDecor() {
        return this.mLayoutInsetDecor;
    }

    public void setWindowLayoutType(int i) {
        this.mWindowLayoutType = i;
    }

    public int getWindowLayoutType() {
        return this.mWindowLayoutType;
    }

    public boolean isTouchModal() {
        return !this.mNotTouchModal;
    }

    public void setTouchModal(boolean z) {
        this.mNotTouchModal = !z;
    }

    @Deprecated
    public void setWindowLayoutMode(int i, int i2) {
        this.mWidthMode = i;
        this.mHeightMode = i2;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public void setHeight(int i) {
        this.mHeight = i;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public void setWidth(int i) {
        this.mWidth = i;
    }

    public void setOverlapAnchor(boolean z) {
        this.mOverlapAnchor = z;
    }

    public boolean getOverlapAnchor() {
        return this.mOverlapAnchor;
    }

    public boolean isShowing() {
        return this.mIsShowing;
    }

    protected final void setShowing(boolean z) {
        this.mIsShowing = z;
    }

    protected final void setDropDown(boolean z) {
        this.mIsDropdown = z;
    }

    protected final void setTransitioningToDismiss(boolean z) {
        this.mIsTransitioningToDismiss = z;
    }

    protected final boolean isTransitioningToDismiss() {
        return this.mIsTransitioningToDismiss;
    }

    public void showAtLocation(View view, int i, int i2, int i3) {
        this.mParentRootView = new WeakReference<>(view.getRootView());
        showAtLocation(view.getWindowToken(), i, i2, i3);
    }

    public void showAtLocation(IBinder iBinder, int i, int i2, int i3) {
        if (isShowing() || this.mContentView == null) {
            return;
        }
        TransitionManager.endTransitions(this.mDecorView);
        detachFromAnchor();
        this.mIsShowing = true;
        this.mIsDropdown = false;
        this.mGravity = i;
        WindowManager.LayoutParams createPopupLayoutParams = createPopupLayoutParams(iBinder);
        preparePopup(createPopupLayoutParams);
        createPopupLayoutParams.x = i2;
        createPopupLayoutParams.y = i3;
        invokePopup(createPopupLayoutParams);
    }

    public void showAsDropDown(View view) {
        showAsDropDown(view, 0, 0);
    }

    public void showAsDropDown(View view, int i, int i2) {
        showAsDropDown(view, i, i2, DEFAULT_ANCHORED_GRAVITY);
    }

    public void showAsDropDown(View view, int i, int i2, int i3) {
        if (isShowing() || !hasContentView()) {
            return;
        }
        TransitionManager.endTransitions(this.mDecorView);
        attachToAnchor(view, i, i2, i3);
        this.mIsShowing = true;
        this.mIsDropdown = true;
        WindowManager.LayoutParams createPopupLayoutParams = createPopupLayoutParams(view.getApplicationWindowToken());
        preparePopup(createPopupLayoutParams);
        updateAboveAnchor(findDropDownPosition(view, createPopupLayoutParams, i, i2, createPopupLayoutParams.width, createPopupLayoutParams.height, i3, this.mAllowScrollingAnchorParent));
        createPopupLayoutParams.accessibilityIdOfAnchor = view != null ? view.getAccessibilityViewId() : -1L;
        invokePopup(createPopupLayoutParams);
    }

    public void semShowPopupWindow(WindowManager.LayoutParams layoutParams) {
        if (isShowing() || !hasContentView()) {
            return;
        }
        TransitionManager.endTransitions(this.mDecorView);
        detachFromAnchor();
        this.mIsShowing = true;
        this.mIsDropdown = false;
        preparePopup(layoutParams);
        invokePopup(layoutParams);
    }

    protected final void updateAboveAnchor(boolean z) {
        View view;
        if (z != this.mAboveAnchor) {
            this.mAboveAnchor = z;
            if (this.mBackground == null || (view = this.mBackgroundView) == null) {
                return;
            }
            Drawable drawable = this.mAboveAnchorBackgroundDrawable;
            if (drawable == null) {
                view.refreshDrawableState();
            } else if (z) {
                view.setBackground(drawable);
            } else {
                view.setBackground(this.mBelowAnchorBackgroundDrawable);
            }
        }
    }

    public boolean isAboveAnchor() {
        return this.mAboveAnchor;
    }

    private void preparePopup(WindowManager.LayoutParams layoutParams) {
        if (this.mContentView == null || this.mContext == null || this.mWindowManager == null) {
            throw new IllegalStateException("You must specify a valid content view by calling setContentView() before attempting to show the popup.");
        }
        if (layoutParams.accessibilityTitle == null) {
            layoutParams.accessibilityTitle = this.mContext.getString(R.string.popup_window_default_title);
        }
        PopupDecorView popupDecorView = this.mDecorView;
        if (popupDecorView != null) {
            popupDecorView.cancelTransitions();
        }
        if (this.mBackground != null) {
            PopupBackgroundView createBackgroundView = createBackgroundView(this.mContentView);
            this.mBackgroundView = createBackgroundView;
            createBackgroundView.setBackground(this.mBackground);
            if (this.mIsDeviceDefault) {
                this.mBackgroundView.setClipToOutline(true);
            }
        } else {
            this.mBackgroundView = this.mContentView;
        }
        PopupDecorView createDecorView = createDecorView(this.mBackgroundView);
        this.mDecorView = createDecorView;
        createDecorView.setIsRootNamespace(true);
        if (this.mIsDeviceDefault && (this.mBackground instanceof NinePatchDrawable)) {
            this.mBackgroundView.setElevation(0.0f);
            this.mBackgroundView.setClipToOutline(false);
        } else {
            this.mBackgroundView.setElevation(this.mElevation);
        }
        layoutParams.setSurfaceInsets(this.mBackgroundView, true, true);
        this.mPopupViewInitialLayoutDirectionInherited = this.mContentView.getRawLayoutDirection() == 2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x000c, code lost:
    
        if (r0.height == (-2)) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.widget.PopupWindow.PopupBackgroundView createBackgroundView(android.view.View r5) {
        /*
            r4 = this;
            android.view.View r0 = r4.mContentView
            android.view.ViewGroup$LayoutParams r0 = r0.getLayoutParams()
            r1 = -1
            if (r0 == 0) goto Lf
            int r0 = r0.height
            r2 = -2
            if (r0 != r2) goto Lf
            goto L10
        Lf:
            r2 = r1
        L10:
            android.widget.PopupWindow$PopupBackgroundView r0 = new android.widget.PopupWindow$PopupBackgroundView
            android.content.Context r3 = r4.mContext
            r0.<init>(r3)
            android.widget.FrameLayout$LayoutParams r4 = new android.widget.FrameLayout$LayoutParams
            r4.<init>(r1, r2)
            r0.addView(r5, r4)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.PopupWindow.createBackgroundView(android.view.View):android.widget.PopupWindow$PopupBackgroundView");
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x000c, code lost:
    
        if (r0.height == (-2)) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.widget.PopupWindow.PopupDecorView createDecorView(android.view.View r5) {
        /*
            r4 = this;
            android.view.View r0 = r4.mContentView
            android.view.ViewGroup$LayoutParams r0 = r0.getLayoutParams()
            r1 = -1
            if (r0 == 0) goto Lf
            int r0 = r0.height
            r2 = -2
            if (r0 != r2) goto Lf
            goto L10
        Lf:
            r2 = r1
        L10:
            android.widget.PopupWindow$PopupDecorView r0 = new android.widget.PopupWindow$PopupDecorView
            android.content.Context r3 = r4.mContext
            r0.<init>(r3)
            r0.addView(r5, r1, r2)
            r4 = 0
            r0.setClipChildren(r4)
            r0.setClipToPadding(r4)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.PopupWindow.createDecorView(android.view.View):android.widget.PopupWindow$PopupDecorView");
    }

    private void invokePopup(WindowManager.LayoutParams layoutParams) {
        Context context = this.mContext;
        if (context != null) {
            layoutParams.packageName = context.getPackageName();
        }
        PopupDecorView popupDecorView = this.mDecorView;
        popupDecorView.setFitsSystemWindows(this.mLayoutInsetDecor);
        setLayoutDirectionFromAnchor();
        this.mWindowManager.addView(popupDecorView, layoutParams);
        Transition transition = this.mEnterTransition;
        if (transition != null) {
            popupDecorView.requestEnterTransition(transition);
        }
    }

    private void setLayoutDirectionFromAnchor() {
        WeakReference<View> weakReference = this.mAnchor;
        if (weakReference != null) {
            View view = weakReference.get();
            view.resolveLayoutDirection();
            if (view == null || !this.mPopupViewInitialLayoutDirectionInherited) {
                return;
            }
            this.mDecorView.setLayoutDirection(view.getLayoutDirection());
        }
    }

    private int computeGravity() {
        int i = this.mGravity;
        if (i == 0) {
            i = DEFAULT_ANCHORED_GRAVITY;
        }
        return (this.mIsDropdown && (this.mClipToScreen || this.mClippingEnabled)) ? 268435456 | i : i;
    }

    protected WindowManager.LayoutParams createPopupLayoutParams(IBinder iBinder) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.gravity = computeGravity();
        layoutParams.flags = computeFlags(layoutParams.flags);
        layoutParams.type = this.mWindowLayoutType;
        layoutParams.token = iBinder;
        layoutParams.softInputMode = this.mSoftInputMode;
        layoutParams.windowAnimations = computeAnimationResource();
        Drawable drawable = this.mBackground;
        if (drawable != null) {
            layoutParams.format = drawable.getOpacity();
        } else {
            layoutParams.format = -3;
        }
        int i = this.mHeightMode;
        if (i < 0) {
            this.mLastHeight = i;
            layoutParams.height = i;
        } else {
            int i2 = this.mHeight;
            this.mLastHeight = i2;
            layoutParams.height = i2;
        }
        int i3 = this.mWidthMode;
        if (i3 < 0) {
            this.mLastWidth = i3;
            layoutParams.width = i3;
        } else {
            int i4 = this.mWidth;
            this.mLastWidth = i4;
            layoutParams.width = i4;
        }
        layoutParams.privateFlags = 16384;
        layoutParams.samsungFlags |= 131072;
        layoutParams.setTitle("PopupWindow:" + Integer.toHexString(hashCode()));
        return layoutParams;
    }

    private int computeFlags(int i) {
        int i2;
        int i3 = i & (-9339417);
        if (this.mIgnoreCheekPress) {
            i3 |= 32768;
        }
        if (!this.mFocusable) {
            int i4 = i3 | 8;
            if (this.mInputMethodMode == 1) {
                i2 = MediaCodecInfo.CodecProfileLevel.APVLevel51Band3;
                i3 |= i2;
            } else {
                i3 = i4;
            }
        } else if (this.mInputMethodMode == 2) {
            i2 = 131072;
            i3 |= i2;
        }
        if (!this.mTouchable) {
            i3 |= 16;
        }
        if (this.mOutsideTouchable) {
            i3 |= 262144;
        }
        if (!this.mClippingEnabled || this.mClipToScreen) {
            i3 |= 512;
        }
        if (isSplitTouchEnabled()) {
            i3 |= 8388608;
        }
        if (this.mLayoutInScreen) {
            i3 |= 256;
        }
        if (this.mLayoutInsetDecor) {
            i3 |= 65536;
        }
        if (this.mNotTouchModal) {
            i3 |= 32;
        }
        if (this.mAttachedInDecor) {
            i3 |= 1073741824;
        }
        return this.mShowWhenLocked ? 524288 | i3 : i3;
    }

    private int computeAnimationResource() {
        int i = this.mAnimationStyle;
        if (i != -1) {
            return i;
        }
        if (this.mIsDropdown) {
            return this.mAboveAnchor ? R.style.Animation_DropDownUp : R.style.Animation_DropDownDown;
        }
        return 0;
    }

    protected boolean findDropDownPosition(View view, WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, boolean z) {
        int i6;
        int height = view.getHeight();
        int width = view.getWidth();
        int i7 = this.mOverlapAnchor ? i2 - height : i2;
        int[] iArr = this.mTmpAppLocation;
        View appRootView = getAppRootView(view);
        appRootView.getLocationOnScreen(iArr);
        int[] iArr2 = this.mTmpScreenLocation;
        view.getLocationOnScreen(iArr2);
        int[] iArr3 = this.mTmpDrawingLocation;
        int i8 = iArr2[0] - iArr[0];
        iArr3[0] = i8;
        iArr3[1] = iArr2[1] - iArr[1];
        layoutParams.x = i8 + i;
        layoutParams.y = iArr3[1] + height + i7;
        Rect rect = new Rect();
        getVisibleDisplayRect(appRootView, rect);
        int i9 = i3;
        if (i9 == -1) {
            i9 = rect.right - rect.left;
        }
        int semGetCenterPointForFoldable = semGetCenterPointForFoldable();
        int i10 = (semGetCenterPointForFoldable == 0 || iArr3[1] >= semGetCenterPointForFoldable) ? rect.bottom : semGetCenterPointForFoldable;
        int i11 = (semGetCenterPointForFoldable == 0 || iArr3[1] < semGetCenterPointForFoldable) ? rect.top : semGetCenterPointForFoldable;
        boolean z2 = iArr[1] < semGetCenterPointForFoldable;
        if (i4 == -1) {
            i6 = z2 ? i10 - i11 : rect.bottom - rect.top;
        } else {
            i6 = i4;
        }
        layoutParams.gravity = computeGravity();
        layoutParams.width = i9;
        layoutParams.height = i6;
        int absoluteGravity = Gravity.getAbsoluteGravity(i5, view.getLayoutDirection()) & 7;
        if (absoluteGravity == 5) {
            layoutParams.x -= i9 - width;
        }
        int i12 = i9;
        boolean tryFitVertical = tryFitVertical(layoutParams, i7, i6, height, iArr3[1], iArr2[1], z2 ? i11 : rect.top, z2 ? i10 : rect.bottom, false);
        int i13 = i7;
        int i14 = i6;
        WindowManager.LayoutParams layoutParams2 = layoutParams;
        boolean tryFitHorizontal = tryFitHorizontal(layoutParams2, i, i12, width, iArr3[0], iArr2[0], rect.left, rect.right, false);
        if (!tryFitVertical || !tryFitHorizontal) {
            int scrollX = view.getScrollX();
            int scrollY = view.getScrollY();
            Rect rect2 = new Rect(scrollX, scrollY, scrollX + i12 + i, scrollY + i14 + height + i13);
            if (z && view.requestRectangleOnScreen(rect2, true)) {
                view.getLocationOnScreen(iArr2);
                int i15 = iArr2[0] - iArr[0];
                iArr3[0] = i15;
                iArr3[1] = iArr2[1] - iArr[1];
                layoutParams2.x = i15 + i;
                layoutParams2.y = iArr3[1] + height + i13;
                if (absoluteGravity == 5) {
                    layoutParams2.x -= i12 - width;
                }
            }
            tryFitVertical(layoutParams2, i13, i14, height, iArr3[1], iArr2[1], z2 ? i11 : rect.top, z2 ? i10 : rect.bottom, this.mClipToScreen);
            layoutParams2 = layoutParams;
            tryFitHorizontal(layoutParams2, i, i12, width, iArr3[0], iArr2[0], rect.left, rect.right, this.mClipToScreen);
        }
        return layoutParams2.y < iArr3[1];
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00b1 A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void getVisibleDisplayRect(android.view.View r7, android.graphics.Rect r8) {
        /*
            r6 = this;
            if (r7 == 0) goto Lbe
            if (r8 != 0) goto L6
            goto Lbe
        L6:
            android.view.ViewGroup$LayoutParams r0 = r7.getLayoutParams()
            boolean r1 = r6.mIsDeviceDefault
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L1e
            boolean r1 = r0 instanceof android.view.WindowManager.LayoutParams
            if (r1 == 0) goto L1e
            android.view.WindowManager$LayoutParams r0 = (android.view.WindowManager.LayoutParams) r0
            int r1 = r0.flags
            r1 = r1 & 512(0x200, float:7.17E-43)
            if (r1 == 0) goto L1f
            r1 = r2
            goto L20
        L1e:
            r0 = 0
        L1f:
            r1 = r3
        L20:
            if (r0 == 0) goto L37
            android.content.Context r4 = r6.mContext
            if (r4 == 0) goto L37
            int r4 = r0.systemUiVisibility
            int r5 = r0.subtreeSystemUiVisibility
            r4 = r4 | r5
            int r0 = r0.flags
            r0 = r0 & 1024(0x400, float:1.435E-42)
            if (r0 != 0) goto L37
            r0 = r4 & 1028(0x404, float:1.44E-42)
            if (r0 != 0) goto L37
            r0 = r2
            goto L38
        L37:
            r0 = r3
        L38:
            android.graphics.Rect r4 = new android.graphics.Rect
            r4.<init>()
            if (r1 == 0) goto Laa
            android.content.Context r1 = r6.mContext
            if (r1 == 0) goto Laa
            android.content.res.Resources r1 = r1.getResources()
            android.content.res.Configuration r1 = r1.getConfiguration()
            android.app.WindowConfiguration r1 = r1.windowConfiguration
            int r1 = r1.getWindowingMode()
            if (r1 != r2) goto Laa
            android.view.WindowManager r7 = r6.mWindowManager
            android.view.Display r7 = r7.getDefaultDisplay()
            android.graphics.Point r1 = new android.graphics.Point
            r1.<init>()
            r7.getRealSize(r1)
            r4.left = r3
            if (r0 == 0) goto L67
            int r3 = r6.mStatusBarHeight
        L67:
            r4.top = r3
            int r7 = r1.x
            r4.right = r7
            int r7 = r1.y
            r4.bottom = r7
            android.content.Context r7 = r6.mContext
            android.content.res.Resources r7 = r7.getResources()
            android.content.res.Configuration r7 = r7.getConfiguration()
            int r7 = r7.orientation
            r0 = 2
            if (r7 == r0) goto Lbb
            java.lang.ref.WeakReference<android.view.View> r7 = r6.mParentRootView
            if (r7 == 0) goto La2
            java.lang.Object r7 = r7.get()
            android.view.View r7 = (android.view.View) r7
            if (r7 == 0) goto La2
            android.view.WindowInsets r7 = r7.getRootWindowInsets()
            if (r7 == 0) goto La2
            int r6 = android.view.WindowInsets.Type.navigationBars()
            android.graphics.Insets r6 = r7.getInsets(r6)
            int r7 = r4.bottom
            int r6 = r6.bottom
            int r7 = r7 - r6
            r4.bottom = r7
            goto Lbb
        La2:
            int r7 = r4.bottom
            int r6 = r6.mNavigationBarHeight
            int r7 = r7 - r6
            r4.bottom = r7
            goto Lbb
        Laa:
            r7.getWindowVisibleDisplayFrame(r4)
            boolean r7 = r6.mIsDeviceDefault
            if (r7 == 0) goto Lbb
            if (r0 == 0) goto Lbb
            int r7 = r4.top
            if (r7 != 0) goto Lbb
            int r6 = r6.mStatusBarHeight
            r4.top = r6
        Lbb:
            r8.set(r4)
        Lbe:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.PopupWindow.getVisibleDisplayRect(android.view.View, android.graphics.Rect):void");
    }

    private boolean tryFitVertical(WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, int i6, int i7, boolean z) {
        int i8 = layoutParams.y + (i5 - i4);
        int i9 = i7 - i8;
        if (i8 >= i6 && i2 <= i9) {
            return true;
        }
        if (i2 <= (i8 - i3) - i6) {
            if (!this.mIsDeviceDefault) {
                if (this.mOverlapAnchor) {
                    i += i3;
                }
                layoutParams.y = (i4 - i2) + i;
                return true;
            }
            if (i4 <= i7) {
                if (this.mOverlapAnchor) {
                    i += i3;
                }
                layoutParams.y = (i4 - i2) + i;
                return true;
            }
        }
        return positionInDisplayVertical(layoutParams, i2, i4, i5, i6, i7, z);
    }

    private boolean positionInDisplayVertical(WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, boolean z) {
        boolean z2;
        int i6 = i3 - i2;
        layoutParams.y += i6;
        layoutParams.height = i;
        int i7 = layoutParams.y + i;
        if (i7 > i5) {
            layoutParams.y -= i7 - i5;
        }
        if (layoutParams.y < i4) {
            layoutParams.y = i4;
            int i8 = i5 - i4;
            if (z && i > i8) {
                layoutParams.height = i8;
            } else {
                z2 = false;
                layoutParams.y -= i6;
                return z2;
            }
        }
        z2 = true;
        layoutParams.y -= i6;
        return z2;
    }

    private boolean tryFitHorizontal(WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, int i6, int i7, boolean z) {
        int i8 = layoutParams.x + (i5 - i4);
        return (i8 >= i6 && i2 <= i7 - i8) || positionInDisplayHorizontal(layoutParams, i2, i4, i5, i6, i7, z);
    }

    private boolean positionInDisplayHorizontal(WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, boolean z) {
        boolean z2;
        int i6 = i3 - i2;
        layoutParams.x += i6;
        int i7 = layoutParams.x + i;
        if (i7 > i5) {
            layoutParams.x -= i7 - i5;
        }
        if (layoutParams.x < i4) {
            layoutParams.x = i4;
            int i8 = i5 - i4;
            if (z && i > i8) {
                layoutParams.width = i8;
            } else {
                z2 = false;
                layoutParams.x -= i6;
                return z2;
            }
        }
        z2 = true;
        layoutParams.x -= i6;
        return z2;
    }

    public int getMaxAvailableHeight(View view) {
        return getMaxAvailableHeight(view, 0);
    }

    public int getMaxAvailableHeight(View view, int i) {
        return getMaxAvailableHeight(view, i, false);
    }

    public int getMaxAvailableHeight(View view, int i, boolean z) {
        int height;
        Rect rect = new Rect();
        View appRootView = getAppRootView(view);
        appRootView.getWindowVisibleDisplayFrame(rect);
        if (z) {
            Rect rect2 = new Rect();
            view.getWindowDisplayFrame(rect2);
            rect2.top = rect.top;
            rect2.right = rect.right;
            rect2.left = rect.left;
            if (this.mIsDeviceDefault && ViewRune.NAVIBAR_ENABLED && this.mContext.getResources().getConfiguration().orientation != 2) {
                rect2.bottom -= this.mNavigationBarHeight;
            }
            rect = rect2;
        } else {
            ViewGroup.LayoutParams layoutParams = appRootView.getLayoutParams();
            if (this.mIsDeviceDefault && (layoutParams instanceof WindowManager.LayoutParams) && this.mContext != null) {
                WindowManager.LayoutParams layoutParams2 = (WindowManager.LayoutParams) layoutParams;
                int i2 = layoutParams2.systemUiVisibility | layoutParams2.subtreeSystemUiVisibility;
                if ((layoutParams2.flags & 1024) == 0 && (i2 & 1028) == 0 && rect.top == 0) {
                    rect.top = this.mStatusBarHeight;
                }
            }
        }
        int[] iArr = this.mTmpDrawingLocation;
        view.getLocationOnScreen(iArr);
        int semGetCenterPointForFoldable = semGetCenterPointForFoldable();
        if (semGetCenterPointForFoldable == 0 || iArr[1] >= semGetCenterPointForFoldable) {
            semGetCenterPointForFoldable = rect.bottom;
        }
        if (this.mOverlapAnchor) {
            height = semGetCenterPointForFoldable - iArr[1];
        } else {
            height = semGetCenterPointForFoldable - (iArr[1] + view.getHeight());
        }
        int max = Math.max(height - i, (iArr[1] - rect.top) + i);
        Drawable drawable = this.mBackground;
        if (drawable == null) {
            return max;
        }
        drawable.getPadding(this.mTempRect);
        return max - (this.mTempRect.top + this.mTempRect.bottom);
    }

    public void dismiss() {
        if (!isShowing() || isTransitioningToDismiss()) {
            return;
        }
        final PopupDecorView popupDecorView = this.mDecorView;
        final View view = this.mContentView;
        unregisterBackCallback(popupDecorView.findOnBackInvokedDispatcher());
        ViewParent parent = view.getParent();
        final ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
        if (popupDecorView == null) {
            return;
        }
        popupDecorView.cancelTransitions();
        this.mIsShowing = false;
        this.mIsTransitioningToDismiss = true;
        Transition transition = this.mExitTransition;
        if (transition != null && popupDecorView.isLaidOut() && (this.mIsAnchorRootAttached || this.mAnchorRoot == null)) {
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) popupDecorView.getLayoutParams();
            layoutParams.flags |= 16;
            layoutParams.flags |= 8;
            layoutParams.flags &= -131073;
            this.mWindowManager.updateViewLayout(popupDecorView, layoutParams);
            WeakReference<View> weakReference = this.mAnchorRoot;
            popupDecorView.startExitTransition(transition, weakReference != null ? weakReference.get() : null, getTransitionEpicenter(), new TransitionListenerAdapter() { // from class: android.widget.PopupWindow.3
                @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                public void onTransitionEnd(Transition transition2) {
                    PopupWindow.this.dismissImmediate(popupDecorView, viewGroup, view);
                }
            });
        } else {
            dismissImmediate(popupDecorView, viewGroup, view);
        }
        detachFromAnchor();
        OnDismissListener onDismissListener = this.mOnDismissListener;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unregisterBackCallback(OnBackInvokedDispatcher onBackInvokedDispatcher) {
        OnBackInvokedCallback onBackInvokedCallback = this.mBackCallback;
        this.mBackCallback = null;
        if (onBackInvokedDispatcher == null || onBackInvokedCallback == null) {
            return;
        }
        onBackInvokedDispatcher.unregisterOnBackInvokedCallback(onBackInvokedCallback);
    }

    protected final Rect getTransitionEpicenter() {
        WeakReference<View> weakReference = this.mAnchor;
        View view = weakReference != null ? weakReference.get() : null;
        PopupDecorView popupDecorView = this.mDecorView;
        if (view == null || popupDecorView == null) {
            return null;
        }
        int[] locationOnScreen = view.getLocationOnScreen();
        int[] locationOnScreen2 = this.mDecorView.getLocationOnScreen();
        Rect rect = new Rect(0, 0, view.getWidth(), view.getHeight());
        rect.offset(locationOnScreen[0] - locationOnScreen2[0], locationOnScreen[1] - locationOnScreen2[1]);
        if (this.mEpicenterBounds != null) {
            int i = rect.left;
            int i2 = rect.top;
            rect.set(this.mEpicenterBounds);
            rect.offset(i, i2);
        }
        return rect;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissImmediate(View view, ViewGroup viewGroup, View view2) {
        if (view.getParent() != null) {
            this.mWindowManager.removeViewImmediate(view);
        }
        if (viewGroup != null) {
            viewGroup.removeView(view2);
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
        boolean z;
        if (isShowing() && hasContentView()) {
            WindowManager.LayoutParams decorViewLayoutParams = getDecorViewLayoutParams();
            int computeAnimationResource = computeAnimationResource();
            boolean z2 = true;
            if (computeAnimationResource != decorViewLayoutParams.windowAnimations) {
                decorViewLayoutParams.windowAnimations = computeAnimationResource;
                z = true;
            } else {
                z = false;
            }
            int computeFlags = computeFlags(decorViewLayoutParams.flags);
            if (computeFlags != decorViewLayoutParams.flags) {
                decorViewLayoutParams.flags = computeFlags;
                z = true;
            }
            int computeGravity = computeGravity();
            if (computeGravity != decorViewLayoutParams.gravity) {
                decorViewLayoutParams.gravity = computeGravity;
            } else {
                z2 = z;
            }
            if (z2) {
                WeakReference<View> weakReference = this.mAnchor;
                update(weakReference != null ? weakReference.get() : null, decorViewLayoutParams);
            }
        }
    }

    protected void update(View view, WindowManager.LayoutParams layoutParams) {
        setLayoutDirectionFromAnchor();
        this.mWindowManager.updateViewLayout(this.mDecorView, layoutParams);
    }

    public void update(int i, int i2) {
        WindowManager.LayoutParams decorViewLayoutParams = getDecorViewLayoutParams();
        update(decorViewLayoutParams.x, decorViewLayoutParams.y, i, i2, false);
    }

    public void update(int i, int i2, int i3, int i4) {
        update(i, i2, i3, i4, false);
    }

    public void update(int i, int i2, int i3, int i4, boolean z) {
        View view;
        if (i3 >= 0) {
            this.mLastWidth = i3;
            setWidth(i3);
        }
        if (i4 >= 0) {
            this.mLastHeight = i4;
            setHeight(i4);
        }
        if (isShowing() && hasContentView()) {
            WindowManager.LayoutParams decorViewLayoutParams = getDecorViewLayoutParams();
            int i5 = this.mWidthMode;
            if (i5 >= 0) {
                i5 = this.mLastWidth;
            }
            int i6 = -1;
            boolean z2 = true;
            if (i3 != -1 && decorViewLayoutParams.width != i5) {
                this.mLastWidth = i5;
                decorViewLayoutParams.width = i5;
                z = true;
            }
            int i7 = this.mHeightMode;
            if (i7 >= 0) {
                i7 = this.mLastHeight;
            }
            if (i4 != -1 && decorViewLayoutParams.height != i7) {
                this.mLastHeight = i7;
                decorViewLayoutParams.height = i7;
                z = true;
            }
            if (decorViewLayoutParams.x != i) {
                decorViewLayoutParams.x = i;
                z = true;
            }
            if (decorViewLayoutParams.y != i2) {
                decorViewLayoutParams.y = i2;
                z = true;
            }
            int computeAnimationResource = computeAnimationResource();
            if (computeAnimationResource != decorViewLayoutParams.windowAnimations) {
                decorViewLayoutParams.windowAnimations = computeAnimationResource;
                z = true;
            }
            int computeFlags = computeFlags(decorViewLayoutParams.flags);
            if (computeFlags != decorViewLayoutParams.flags) {
                decorViewLayoutParams.flags = computeFlags;
                z = true;
            }
            int computeGravity = computeGravity();
            if (computeGravity != decorViewLayoutParams.gravity) {
                decorViewLayoutParams.gravity = computeGravity;
                z = true;
            }
            WeakReference<View> weakReference = this.mAnchor;
            if (weakReference == null || weakReference.get() == null) {
                view = null;
            } else {
                view = this.mAnchor.get();
                i6 = view.getAccessibilityViewId();
            }
            long j = i6;
            if (j != decorViewLayoutParams.accessibilityIdOfAnchor) {
                decorViewLayoutParams.accessibilityIdOfAnchor = j;
            } else {
                z2 = z;
            }
            if (z2) {
                update(view, decorViewLayoutParams);
            }
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

    public void update(View view, int i, int i2) {
        update(view, false, 0, 0, i, i2);
    }

    public void update(View view, int i, int i2, int i3, int i4) {
        update(view, true, i, i2, i3, i4);
    }

    private void update(View view, boolean z, int i, int i2, int i3, int i4) {
        if (isShowing() && hasContentView()) {
            WeakReference<View> weakReference = this.mAnchor;
            int i5 = this.mAnchoredGravity;
            boolean z2 = true;
            boolean z3 = z && !(this.mAnchorXoff == i && this.mAnchorYoff == i2);
            if (weakReference == null || weakReference.get() != view || (z3 && !this.mIsDropdown)) {
                attachToAnchor(view, i, i2, i5);
            } else if (z3) {
                this.mAnchorXoff = i;
                this.mAnchorYoff = i2;
            }
            WindowManager.LayoutParams decorViewLayoutParams = getDecorViewLayoutParams();
            int i6 = decorViewLayoutParams.gravity;
            int i7 = decorViewLayoutParams.width;
            int i8 = decorViewLayoutParams.height;
            int i9 = decorViewLayoutParams.x;
            int i10 = decorViewLayoutParams.y;
            int i11 = i3 < 0 ? this.mWidth : i3;
            int i12 = i4 < 0 ? this.mHeight : i4;
            updateAboveAnchor(findDropDownPosition(view, decorViewLayoutParams, this.mAnchorXoff, this.mAnchorYoff, i11, i12, i5, this.mAllowScrollingAnchorParent));
            if (i6 == decorViewLayoutParams.gravity && i9 == decorViewLayoutParams.x && i10 == decorViewLayoutParams.y && i7 == decorViewLayoutParams.width && i8 == decorViewLayoutParams.height) {
                z2 = false;
            }
            if (i11 >= 0) {
                i11 = decorViewLayoutParams.width;
            }
            if (i12 >= 0) {
                i12 = decorViewLayoutParams.height;
            }
            update(decorViewLayoutParams.x, decorViewLayoutParams.y, i11, i12, z2);
        }
    }

    protected void detachFromAnchor() {
        View anchor = getAnchor();
        if (anchor != null) {
            anchor.getViewTreeObserver().removeOnScrollChangedListener(this.mOnScrollChangedListener);
            anchor.removeOnAttachStateChangeListener(this.mOnAnchorDetachedListener);
        }
        WeakReference<View> weakReference = this.mAnchorRoot;
        View view = weakReference != null ? weakReference.get() : null;
        if (view != null) {
            view.removeOnAttachStateChangeListener(this.mOnAnchorRootDetachedListener);
            view.removeOnLayoutChangeListener(this.mOnLayoutChangeListener);
        }
        this.mAnchor = null;
        this.mAnchorRoot = null;
        this.mIsAnchorRootAttached = false;
    }

    protected void attachToAnchor(View view, int i, int i2, int i3) {
        detachFromAnchor();
        ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        if (viewTreeObserver != null) {
            viewTreeObserver.addOnScrollChangedListener(this.mOnScrollChangedListener);
        }
        view.addOnAttachStateChangeListener(this.mOnAnchorDetachedListener);
        View rootView = view.getRootView();
        rootView.addOnAttachStateChangeListener(this.mOnAnchorRootDetachedListener);
        rootView.addOnLayoutChangeListener(this.mOnLayoutChangeListener);
        this.mAnchor = new WeakReference<>(view);
        this.mAnchorRoot = new WeakReference<>(rootView);
        this.mIsAnchorRootAttached = rootView.isAttachedToWindow();
        this.mParentRootView = this.mAnchorRoot;
        this.mAnchorXoff = i;
        this.mAnchorYoff = i2;
        this.mAnchoredGravity = i3;
    }

    protected View getAnchor() {
        WeakReference<View> weakReference = this.mAnchor;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    private void alignToAnchor(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        if (!this.mIsDeviceDefault) {
            alignToAnchor();
        } else {
            if (i == i5 && i2 == i6 && i3 == i7 && i4 == i8) {
                return;
            }
            alignToAnchor();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void alignToAnchor() {
        WeakReference<View> weakReference = this.mAnchor;
        View view = weakReference != null ? weakReference.get() : null;
        if (view != null && view.isAttachedToWindow() && hasDecorView()) {
            WindowManager.LayoutParams decorViewLayoutParams = getDecorViewLayoutParams();
            updateAboveAnchor(findDropDownPosition(view, decorViewLayoutParams, this.mAnchorXoff, this.mAnchorYoff, decorViewLayoutParams.width, decorViewLayoutParams.height, this.mAnchoredGravity, false));
            if (this.mIsDeviceDefault) {
                if (decorViewLayoutParams.height != 0) {
                    update(decorViewLayoutParams.x, decorViewLayoutParams.y, -1, -1, true);
                    return;
                }
                return;
            }
            update(decorViewLayoutParams.x, decorViewLayoutParams.y, -1, -1, true);
            return;
        }
        if (this.mIsDeviceDefault) {
            dismiss();
        }
    }

    private View getAppRootView(View view) {
        View windowView = WindowManagerGlobal.getInstance().getWindowView(view.getApplicationWindowToken());
        return windowView != null ? windowView : view.getRootView();
    }

    public void semShowWhenLocked(boolean z) {
        if (isSystemApp()) {
            this.mShowWhenLocked = z;
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
            this.mOnAnchorRootDetachedListener = new View.OnAttachStateChangeListener() { // from class: android.widget.PopupWindow.PopupDecorView.4
                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewAttachedToWindow(View view) {
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewDetachedFromWindow(View view) {
                    view.removeOnAttachStateChangeListener(this);
                    if (PopupDecorView.this.isAttachedToWindow()) {
                        TransitionManager.endTransitions(PopupDecorView.this);
                    }
                }
            };
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            KeyEvent.DispatcherState keyDispatcherState;
            if (keyEvent.getKeyCode() == 4 || keyEvent.getKeyCode() == 111) {
                if (getKeyDispatcherState() == null) {
                    return super.dispatchKeyEvent(keyEvent);
                }
                if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                    KeyEvent.DispatcherState keyDispatcherState2 = getKeyDispatcherState();
                    if (keyDispatcherState2 != null) {
                        keyDispatcherState2.startTracking(keyEvent, this);
                    }
                    return true;
                }
                if (keyEvent.getAction() == 1 && (keyDispatcherState = getKeyDispatcherState()) != null && keyDispatcherState.isTracking(keyEvent) && !keyEvent.isCanceled()) {
                    PopupWindow.this.dismiss();
                    return true;
                }
                return super.dispatchKeyEvent(keyEvent);
            }
            return super.dispatchKeyEvent(keyEvent);
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            if (ViewRune.WIDGET_PEN_SUPPORTED) {
                int action = motionEvent.getAction();
                if (motionEvent.getToolType(0) == 2) {
                    if (action != 0) {
                        if (action != 1) {
                            if (action == 2 && this.mIsPenSelectionMode) {
                                motionEvent.setAction(213);
                            }
                        } else {
                            if (this.mIsPenSelectionMode) {
                                motionEvent.setAction(212);
                            }
                            this.mIsPenSelectionMode = false;
                        }
                    } else if ((motionEvent.getButtonState() & 32) != 0) {
                        this.mIsPenSelectionMode = true;
                        motionEvent.setAction(211);
                    } else {
                        this.mIsPenSelectionMode = false;
                    }
                }
            }
            if (PopupWindow.this.mTouchInterceptor == null || !PopupWindow.this.mTouchInterceptor.onTouch(this, motionEvent)) {
                return super.dispatchTouchEvent(motionEvent);
            }
            return true;
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (motionEvent.getAction() == 0 && (x < 0 || x >= getWidth() || y < 0 || y >= getHeight())) {
                PopupWindow.this.dismiss();
                return true;
            }
            if (motionEvent.getAction() == 4) {
                PopupWindow.this.dismiss();
                return true;
            }
            return super.onTouchEvent(motionEvent);
        }

        public void requestEnterTransition(Transition transition) {
            ViewTreeObserver viewTreeObserver = getViewTreeObserver();
            if (viewTreeObserver == null || transition == null) {
                return;
            }
            final Transition mo5495clone = transition.mo5495clone();
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: android.widget.PopupWindow.PopupDecorView.1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    ViewTreeObserver viewTreeObserver2 = PopupDecorView.this.getViewTreeObserver();
                    if (viewTreeObserver2 != null) {
                        viewTreeObserver2.removeOnGlobalLayoutListener(this);
                    }
                    final Rect transitionEpicenter = PopupWindow.this.getTransitionEpicenter();
                    mo5495clone.setEpicenterCallback(new Transition.EpicenterCallback(this) { // from class: android.widget.PopupWindow.PopupDecorView.1.1
                        @Override // android.transition.Transition.EpicenterCallback
                        public Rect onGetEpicenter(Transition transition2) {
                            return transitionEpicenter;
                        }
                    });
                    PopupDecorView.this.startEnterTransition(mo5495clone);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void startEnterTransition(Transition transition) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                transition.addTarget(childAt);
                childAt.setTransitionVisibility(4);
            }
            TransitionManager.beginDelayedTransition(this, transition);
            for (int i2 = 0; i2 < childCount; i2++) {
                getChildAt(i2).setTransitionVisibility(0);
            }
        }

        public void startExitTransition(final Transition transition, final View view, final Rect rect, final Transition.TransitionListener transitionListener) {
            if (transition == null) {
                return;
            }
            if (view != null) {
                view.addOnAttachStateChangeListener(this.mOnAnchorRootDetachedListener);
            }
            this.mCleanupAfterExit = new Runnable() { // from class: android.widget.PopupWindow$PopupDecorView$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PopupWindow.PopupDecorView.this.lambda$startExitTransition$0(transitionListener, transition, view);
                }
            };
            Transition mo5495clone = transition.mo5495clone();
            mo5495clone.addListener(new TransitionListenerAdapter() { // from class: android.widget.PopupWindow.PopupDecorView.2
                @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                public void onTransitionEnd(Transition transition2) {
                    transition2.removeListener(this);
                    if (PopupDecorView.this.mCleanupAfterExit != null) {
                        PopupDecorView.this.mCleanupAfterExit.run();
                    }
                }
            });
            mo5495clone.setEpicenterCallback(new Transition.EpicenterCallback(this) { // from class: android.widget.PopupWindow.PopupDecorView.3
                @Override // android.transition.Transition.EpicenterCallback
                public Rect onGetEpicenter(Transition transition2) {
                    return rect;
                }
            });
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                mo5495clone.addTarget(getChildAt(i));
            }
            TransitionManager.beginDelayedTransition(this, mo5495clone);
            for (int i2 = 0; i2 < childCount; i2++) {
                getChildAt(i2).setVisibility(4);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$startExitTransition$0(Transition.TransitionListener transitionListener, Transition transition, View view) {
            transitionListener.onTransitionEnd(transition);
            if (view != null) {
                view.removeOnAttachStateChangeListener(this.mOnAnchorRootDetachedListener);
            }
            this.mCleanupAfterExit = null;
        }

        public void cancelTransitions() {
            TransitionManager.endTransitions(this);
            Runnable runnable = this.mCleanupAfterExit;
            if (runnable != null) {
                runnable.run();
            }
        }

        @Override // android.view.View
        public void requestKeyboardShortcuts(List<KeyboardShortcutGroup> list, int i) {
            View view;
            if (PopupWindow.this.mParentRootView == null || (view = (View) PopupWindow.this.mParentRootView.get()) == null) {
                return;
            }
            view.requestKeyboardShortcuts(list, i);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onAttachedToWindow() {
            OnBackInvokedDispatcher findOnBackInvokedDispatcher;
            super.onAttachedToWindow();
            if (WindowOnBackInvokedDispatcher.isOnBackInvokedCallbackEnabled(this.mContext) && (findOnBackInvokedDispatcher = findOnBackInvokedDispatcher()) != null) {
                final PopupWindow popupWindow = PopupWindow.this;
                popupWindow.mBackCallback = new OnBackInvokedCallback() { // from class: android.widget.PopupWindow$PopupDecorView$$ExternalSyntheticLambda1
                    @Override // android.window.OnBackInvokedCallback
                    public final void onBackInvoked() {
                        PopupWindow.this.dismiss();
                    }
                };
                findOnBackInvokedDispatcher.registerOnBackInvokedCallback(0, PopupWindow.this.mBackCallback);
            }
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
        protected int[] onCreateDrawableState(int i) {
            if (PopupWindow.this.mAboveAnchor) {
                int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
                View.mergeDrawableStates(onCreateDrawableState, PopupWindow.ABOVE_ANCHOR_STATE_SET);
                return onCreateDrawableState;
            }
            return super.onCreateDrawableState(i);
        }
    }

    boolean semIsAvailableBlurBackground() {
        return ((Settings.System.getString(this.mContext.getContentResolver(), "current_sec_active_themepackage") != null) || (Settings.System.getInt(this.mContext.getContentResolver(), "accessibility_reduce_transparency", 0) == 1) || this.mIsReplacedPoupBackground) ? false : true;
    }

    int semGetCenterPointForFoldable() {
        DisplayManager displayManager;
        Display display;
        Context context = this.mContext;
        if (context == null || (displayManager = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE)) == null || (display = displayManager.getDisplay(0)) == null || !SemWindowManager.getInstance().isTableMode() || !this.mIsDeviceDefault) {
            return 0;
        }
        if (new SemMultiWindowManager().getMode() != 0) {
            return 0;
        }
        Point point = new Point();
        display.getRealSize(point);
        if (ViewRune.supportFoldableDualDisplay()) {
            if (this.mContext.getResources().getConfiguration().orientation == 2) {
                return point.y > point.x ? point.x / 2 : point.y / 2;
            }
        } else if (ViewRune.supportFoldableNoSubDisplay() && this.mContext.getResources().getConfiguration().orientation == 1) {
            return point.y > point.x ? point.y / 2 : point.x / 2;
        }
        return 0;
    }

    private View hidden_semGetBackgroundView() {
        return this.mBackgroundView;
    }
}
