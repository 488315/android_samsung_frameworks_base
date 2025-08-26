package com.samsung.android.widget;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import com.android.internal.R;
import com.samsung.android.cover.CoverState;
import com.samsung.android.cover.ICoverManager;
import com.samsung.android.rune.ViewRune;
import com.samsung.android.sepunion.UnionConstants;

/* loaded from: classes6.dex */
public class SemHoverPopupWindow {
    private static final int ANCHOR_VIEW_COORDINATES_TYPE_SCREEN = 2;
    private static final int ANCHOR_VIEW_COORDINATES_TYPE_WINDOW = 1;
    private static final boolean DEBUG = false;
    private static final int HOVER_DETECT_TIME_MS = 300;
    private static final int HOVER_DETECT_TIME_MS_DEX = 750;
    private static final int MSG_TIMEOUT = 1;
    private static final int POPUP_TIMEOUT_MS = 5000;
    private static final String TAG = "SemHoverPopupWindow";
    private static final int TIMEOUT_DELAY = 2000;
    public static final int TYPE_NONE = 0;
    public static final int TYPE_TOOLTIP = 1;
    public static final int TYPE_USER_CUSTOM = 3;
    public static final int TYPE_WIDGET_DEFAULT = 2;
    private static final int UI_THREAD_BUSY_TIME_MS = 1000;
    private static final boolean localLOGV = Debug.semIsProductDev();
    private static final DisplayMetrics sRealDisplayMetricsInDexMode = new DisplayMetrics();
    private View mAnchorView;
    protected int mAnimationStyle;
    private ViewGroup.LayoutParams mContentLP;
    protected CharSequence mContentText;
    protected View mContentView;
    private final Context mContext;
    private int mCoordinatesOfAnchorView;
    private ICoverManager mCoverManager;
    private int mDeviceRotation;
    private Handler mDismissHandler;
    private boolean mEnabled;
    private int mHashCodeForViewState;
    protected int mHoverDetectTimeMS;
    private int mHoverPaddingBottom;
    private int mHoverPaddingLeft;
    private int mHoverPaddingRight;
    private int mHoverPaddingTop;
    private int mHoveringPointX;
    private int mHoveringPointY;
    private boolean mIsPopupTouchable;
    private boolean mIsSPenPointChanged;
    private boolean mIsSkipPenPointEffect;
    private boolean mIsTryingShowPopup;
    private OnSetContentViewListener mListener;
    private int mNavigationBarHeight;
    protected final View mParentView;
    private PopupWindow mPopup;
    protected int mPopupGravity;
    private int mPopupOffsetX;
    private int mPopupOffsetY;
    private int mPopupPosX;
    private int mPopupPosY;
    protected int mPopupType;
    private HoverPopupPreShowListener mPreShowListener;
    private final Resources mResources;
    private TouchablePopupContainer mTouchableContainer;
    private int mWindowGapX;
    private boolean mDismissTouchableHPWOnActionUp = true;
    private boolean mNeedNotWindowOffset = false;
    private boolean mNeedToMeasureContentView = false;
    private boolean mIsCheckedRealDisplayMetricsInDexMode = false;
    private boolean mIsHoverPaddingEnabled = false;
    private boolean mIsShowMessageSent = false;
    private boolean mIsUspFeature = false;
    private int mContentWidth = 0;
    private int mContentHeight = 0;
    private int mToolType = 0;
    private Rect mAnchorRect = null;
    private Rect mDisplayFrame = null;
    private Runnable mShowPopupRunnable = null;

    public interface HoverPopupPreShowListener {
        boolean onHoverPopupPreShow();
    }

    public interface OnSetContentViewListener {
        boolean onSetContentView(View view, SemHoverPopupWindow semHoverPopupWindow);
    }

    public static class QuintEaseOut implements Interpolator {
        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            float f2 = (f / 1.0f) - 1.0f;
            return (f2 * f2 * f2 * f2 * f2) + 1.0f;
        }
    }

    private static final int hidden_TYPE_NONE() {
        return 0;
    }

    private static final int hidden_TYPE_TOOLTIP() {
        return 1;
    }

    private static final int hidden_TYPE_USER_CUSTOM() {
        return 3;
    }

    protected void makeDefaultContentView() {
    }

    protected void setInstanceByType(int i) {
    }

    public SemHoverPopupWindow(View view, int i) {
        this.mPopupType = 0;
        this.mParentView = view;
        Context context = view.getContext();
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16843945, typedValue, false);
        if (typedValue.data != 0) {
            this.mContext = new ContextThemeWrapper(context, typedValue.data);
        } else {
            this.mContext = context;
        }
        this.mResources = this.mContext.getResources();
        this.mPopupType = i;
        initInstance();
        setInstanceByType(i);
        if (isMouseHoveringSettingsEnabled()) {
            this.mHoverDetectTimeMS = 750;
        }
        this.mDismissHandler = new Handler(this.mContext.getMainLooper()) { // from class: com.samsung.android.widget.SemHoverPopupWindow.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (SemHoverPopupWindow.this.mPopup != null && SemHoverPopupWindow.this.mPopup.isShowing() && message.what == 1) {
                    Log.d(SemHoverPopupWindow.TAG, "mDismissHandler handleMessage: Call dismiss");
                    SemHoverPopupWindow.this.dismiss();
                }
            }
        };
    }

    private void initInstance() {
        this.mPopup = null;
        this.mEnabled = true;
        this.mHoverDetectTimeMS = 300;
        this.mPopupGravity = 12849;
        this.mPopupPosX = 0;
        this.mPopupPosY = 0;
        this.mHoveringPointX = 0;
        this.mHoveringPointY = 0;
        this.mPopupOffsetX = 0;
        this.mPopupOffsetY = 0;
        this.mWindowGapX = 0;
        this.mHoverPaddingLeft = 0;
        this.mHoverPaddingRight = 0;
        this.mHoverPaddingTop = 0;
        this.mHoverPaddingBottom = 0;
        this.mNavigationBarHeight = getNavigationBarHeight();
        this.mListener = null;
        this.mContentText = null;
        this.mAnimationStyle = R.style.Animation_HoverPopup;
        this.mCoordinatesOfAnchorView = 0;
        this.mContentView = null;
        this.mTouchableContainer = null;
        this.mAnchorView = null;
        this.mIsSPenPointChanged = false;
        this.mIsPopupTouchable = false;
        this.mIsTryingShowPopup = false;
        this.mIsSkipPenPointEffect = false;
        initCoverManager();
        this.mIsUspFeature = ViewRune.WIDGET_PEN_SUPPORTED;
    }

    private void initCoverManager() {
        if (this.mCoverManager == null) {
            ICoverManager iCoverManagerAsInterface = ICoverManager.Stub.asInterface(ServiceManager.getService(UnionConstants.SERVICE_COVER));
            this.mCoverManager = iCoverManagerAsInterface;
            if (iCoverManagerAsInterface == null) {
                Log.e(TAG, "warning: no COVER_MANAGER_SERVICE");
            }
        }
    }

    public boolean isHoverPopupPossible() {
        int i = this.mPopupType;
        if (i != 0) {
            if (i != 1) {
                if (i == 2) {
                    return false;
                }
            } else if (this.mParentView == null || TextUtils.isEmpty(getTooltipText())) {
            }
            return true;
        }
        return false;
    }

    protected boolean isUspFeature() {
        return this.mIsUspFeature;
    }

    private boolean isFreeFormMode() {
        return this.mContext.getResources().getConfiguration().windowConfiguration.getWindowingMode() == 5;
    }

    private DisplayMetrics getRealDisplayMetrics() {
        Display defaultDisplay = ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayInfo displayInfo = new DisplayInfo();
        if (defaultDisplay != null) {
            defaultDisplay.getDisplayInfo(displayInfo);
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (this.mContext.getApplicationContext() == null) {
            Log.d(TAG, "getApplicationContext() is null");
            displayMetrics.widthPixels = displayInfo.appWidth;
            displayMetrics.heightPixels = displayInfo.appHeight;
            displayMetrics.density = this.mResources.getDisplayMetrics().density;
        } else {
            displayMetrics = this.mContext.getApplicationContext().getResources().getDisplayMetrics();
        }
        if (isMouseHoveringSettingsEnabled()) {
            if (!this.mIsCheckedRealDisplayMetricsInDexMode) {
                this.mIsCheckedRealDisplayMetricsInDexMode = true;
                if (localLOGV) {
                    Log.d(TAG, "getRealDisplayMetrics :sRealDisplayMetricsInDexMode width:" + displayInfo.appWidth);
                    Log.d(TAG, "getRealDisplayMetrics :sRealDisplayMetricsInDexMode height:" + displayInfo.appHeight);
                }
                DisplayMetrics displayMetrics2 = sRealDisplayMetricsInDexMode;
                displayMetrics2.widthPixels = displayInfo.appWidth;
                displayMetrics2.heightPixels = displayInfo.appHeight;
            }
            return sRealDisplayMetricsInDexMode;
        }
        if (localLOGV) {
            Log.d(TAG, "getRealDisplayMetrics :displaySize width:" + displayMetrics.widthPixels);
            Log.d(TAG, "getRealDisplayMetrics :displaySize height:" + displayMetrics.heightPixels);
        }
        return displayMetrics;
    }

    private boolean isHoveringSettingEnabled() {
        int i = this.mToolType;
        if (i == 2) {
            return isSPenHoveringSettingsEnabled();
        }
        if (i != 3) {
            return false;
        }
        return isMouseHoveringSettingsEnabled();
    }

    private boolean isSPenHoveringSettingsEnabled() {
        return Settings.System.getIntForUser(this.mContext.getContentResolver(), Settings.System.SEM_PEN_HOVERING, 0, -3) == 1;
    }

    private boolean isMouseHoveringSettingsEnabled() {
        View view = this.mParentView;
        if (view != null) {
            return view.semIsDesktopMode();
        }
        return false;
    }

    public void setHoverPopupToolType(int i) {
        this.mToolType = i;
    }

    private boolean isTalkBackEnabledForDeX() {
        AccessibilityManager accessibilityManager;
        return isMouseHoveringSettingsEnabled() && (accessibilityManager = AccessibilityManager.getInstance(this.mContext)) != null && accessibilityManager.semIsScreenReaderEnabled() && accessibilityManager.isTouchExplorationEnabled();
    }

    private boolean isLockScreenMode() {
        return ((KeyguardManager) this.mContext.getSystemService(Context.KEYGUARD_SERVICE)).inKeyguardRestrictedInputMode();
    }

    private boolean isViewCoverClose() {
        ICoverManager iCoverManager;
        CoverState coverState;
        try {
            iCoverManager = this.mCoverManager;
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in getCoverState: ", e);
        }
        boolean switchState = (iCoverManager == null || (coverState = iCoverManager.getCoverState()) == null) ? true : coverState.getSwitchState();
        return !switchState;
    }

    public void setDismissTouchableHPWOnActionUp(boolean z) {
        this.mDismissTouchableHPWOnActionUp = z;
    }

    public boolean getIsDismissTouchableHPWOnActionUp() {
        return this.mDismissTouchableHPWOnActionUp;
    }

    public View getParentView() {
        return this.mParentView;
    }

    public void setOnSetContentViewListener(OnSetContentViewListener onSetContentViewListener) {
        this.mListener = onSetContentViewListener;
    }

    public void setHoverPopupPreShowListener(HoverPopupPreShowListener hoverPopupPreShowListener) {
        this.mPreShowListener = hoverPopupPreShowListener;
    }

    public void setContent(View view) {
        setContent(view, view != null ? view.getLayoutParams() : null);
    }

    public void setContent(View view, ViewGroup.LayoutParams layoutParams) {
        this.mContentView = view;
        this.mContentLP = layoutParams;
        this.mNeedToMeasureContentView = true;
    }

    public void setContent(CharSequence charSequence) {
        this.mContentText = charSequence;
        this.mNeedToMeasureContentView = true;
    }

    public View getContentView() {
        return this.mContentView;
    }

    public boolean isShowing() {
        PopupWindow popupWindow = this.mPopup;
        return popupWindow != null && popupWindow.isShowing();
    }

    public void setHoverDetectTime(int i) {
        this.mHoverDetectTimeMS = i;
    }

    public void setHoverPaddingArea(int i, int i2, int i3, int i4) {
        this.mHoverPaddingLeft = i;
        this.mHoverPaddingRight = i3;
        this.mHoverPaddingTop = i2;
        this.mHoverPaddingBottom = i4;
        if (i == 0 && i3 == 0 && i2 == 0 && i4 == 0) {
            return;
        }
        this.mIsHoverPaddingEnabled = true;
    }

    public void setGravity(int i) {
        this.mPopupGravity = i;
    }

    public void setOffset(int i, int i2) {
        this.mPopupOffsetX = i;
        this.mPopupOffsetY = i2;
    }

    public void setHoveringPoint(int i, int i2) {
        this.mHoveringPointX = i;
        this.mHoveringPointY = i2;
    }

    public void setNeedNotWindowOffset(boolean z) {
        this.mNeedNotWindowOffset = z;
    }

    private CharSequence getTooltipText() {
        if (!TextUtils.isEmpty(this.mContentText)) {
            return this.mContentText;
        }
        if (TextUtils.isEmpty(this.mParentView.getContentDescription())) {
            return null;
        }
        return this.mParentView.getContentDescription();
    }

    public void show() {
        if (localLOGV) {
            Log.d(TAG, "show :" + this.mParentView.toString());
        }
        Log.d(TAG, "Toolkit porting remove this log after all feature included");
        View view = this.mAnchorView;
        if (view == null) {
            view = this.mParentView;
        }
        int iSemGetHoverPopupType = view.semGetHoverPopupType();
        if (iSemGetHoverPopupType != this.mPopupType) {
            this.mPopupType = iSemGetHoverPopupType;
            setInstanceByType(iSemGetHoverPopupType);
        }
        HoverPopupPreShowListener hoverPopupPreShowListener = this.mPreShowListener;
        if ((hoverPopupPreShowListener != null && !hoverPopupPreShowListener.onHoverPopupPreShow()) || !this.mEnabled || iSemGetHoverPopupType == 0 || iSemGetHoverPopupType == 1 || this.mIsShowMessageSent) {
            return;
        }
        if ((this.mIsHoverPaddingEnabled && !this.mIsTryingShowPopup) || !isHoverPopupPossible() || !isHoveringSettingEnabled() || isShowing() || this.mParentView.getHandler() == null || isViewCoverClose() || isLockScreenMode() || isTalkBackEnabledForDeX()) {
            return;
        }
        this.mHashCodeForViewState = getStateHashCode();
        if (!this.mIsSkipPenPointEffect) {
            showPenPointEffect(true);
        }
        Runnable runnable = new Runnable() { // from class: com.samsung.android.widget.SemHoverPopupWindow$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.showPopup();
            }
        };
        this.mShowPopupRunnable = runnable;
        this.mParentView.postDelayed(runnable, this.mHoverDetectTimeMS);
        this.mIsShowMessageSent = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPopup() {
        try {
            if (this.mHashCodeForViewState == getStateHashCode()) {
                if (!this.mIsSkipPenPointEffect) {
                    showPenPointEffect(true);
                }
                this.mIsSkipPenPointEffect = false;
                PopupWindow popupWindow = this.mPopup;
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                createPopupWindow();
                setPopupContent();
                update();
                return;
            }
            Log.d(TAG, "showPopup() is cancelled : " + this.mHashCodeForViewState + " " + getStateHashCode());
            if (!this.mIsUspFeature || this.mParentView.getWindowVisibility() != 0 || this.mParentView.getVisibility() != 0) {
                dismiss();
            } else {
                dismiss();
                show();
            }
        } catch (Exception e) {
            Log.i(TAG, "Fail show hover popup :" + e);
        }
    }

    protected PopupWindow createPopupWindow() {
        if (this.mPopup == null) {
            PopupWindow popupWindow = new PopupWindow(this.mParentView.getContext());
            this.mPopup = popupWindow;
            popupWindow.setWidth(-2);
            this.mPopup.setHeight(-2);
            this.mPopup.setTouchable(this.mIsPopupTouchable);
            this.mPopup.setClippingEnabled(false);
            this.mPopup.setBackgroundDrawable(null);
            this.mPopup.setWindowLayoutType(1005);
            View view = this.mAnchorView;
            if (view == null) {
                view = this.mParentView;
            }
            if (view.getApplicationWindowToken() != view.getWindowToken()) {
                this.mPopup.setIsLaidOutInScreen(true);
            }
            this.mPopup.setAnimationStyle(this.mAnimationStyle);
        }
        return this.mPopup;
    }

    private void setPopupContent() {
        int i = this.mPopupType;
        if (i == 0 || i == 1) {
            this.mContentView = null;
        } else if (i == 2) {
            makeDefaultContentView();
        } else if (i != 3) {
            this.mContentView = null;
        }
        OnSetContentViewListener onSetContentViewListener = this.mListener;
        if (onSetContentViewListener != null) {
            onSetContentViewListener.onSetContentView(this.mParentView, this);
        }
    }

    private void measureContentView(DisplayMetrics displayMetrics) {
        int iMakeMeasureSpec;
        int iMakeMeasureSpec2;
        if (this.mContentView == null) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = this.mContentLP;
        if (layoutParams == null) {
            iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, Integer.MIN_VALUE);
            iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels, Integer.MIN_VALUE);
        } else {
            if (layoutParams.width < 0) {
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, Integer.MIN_VALUE);
            } else {
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mContentLP.width, 1073741824);
            }
            if (this.mContentLP.height < 0) {
                iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels, Integer.MIN_VALUE);
            } else {
                iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(this.mContentLP.height, 1073741824);
            }
        }
        this.mContentView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mNeedToMeasureContentView = false;
        this.mContentWidth = this.mContentView.getMeasuredWidth();
        this.mContentHeight = this.mContentView.getMeasuredHeight();
        PopupWindow popupWindow = this.mPopup;
        if (popupWindow != null) {
            popupWindow.setWidth(this.mContentWidth);
            this.mPopup.setHeight(this.mContentHeight);
            this.mPopup.setAnimationStyle(this.mAnimationStyle);
        }
    }

    private void computePopupPosition(View view, int i, int i2, int i3) throws Resources.NotFoundException {
        boolean z;
        int i4;
        int i5;
        int[] iArr;
        View view2;
        Rect rect;
        if (this.mContentView == null) {
            return;
        }
        if (this.mNavigationBarHeight != 0) {
            this.mDeviceRotation = getDeviceRotation();
        }
        int[] iArr2 = new int[2];
        int[] iArr3 = new int[2];
        DisplayMetrics displayMetrics = this.mResources.getDisplayMetrics();
        View view3 = view == null ? this.mParentView : view;
        this.mAnchorView = view3;
        this.mPopupGravity = i;
        this.mPopupOffsetX = i2;
        this.mPopupOffsetY = i3;
        view3.getLocationOnScreen(iArr2);
        view3.getLocationInWindow(iArr3);
        if (localLOGV) {
            Log.d(TAG, "computePopupPosition :anchorLocOnScr x:" + iArr2[0]);
            Log.d(TAG, "computePopupPosition :anchorLocOnScr y:" + iArr2[1]);
            Log.d(TAG, "computePopupPosition :anchorLocInWindow x:" + iArr3[0]);
            Log.d(TAG, "computePopupPosition :anchorLocInWindow y:" + iArr3[1]);
        }
        Rect rect2 = new Rect();
        if (view3.updateDisplayListIfDirty().hasIdentityMatrix()) {
            Object parent = view3.getParent();
            z = true;
            i4 = 0;
            while (parent instanceof View) {
                View view4 = (View) parent;
                if (!view4.updateDisplayListIfDirty().hasIdentityMatrix()) {
                    z = false;
                }
                parent = view4.getParent();
            }
        } else {
            i4 = 0;
            z = false;
        }
        if (!z) {
            view3.getBoundsOnScreen(rect2);
            iArr3[i4] = iArr3[i4] + (rect2.left - iArr2[i4]);
            iArr3[1] = iArr3[1] + (rect2.top - iArr2[1]);
            iArr2[i4] = rect2.left;
            iArr2[1] = rect2.top;
        }
        Rect rect3 = new Rect();
        view3.getWindowVisibleDisplayFrame(rect3);
        boolean z2 = localLOGV;
        if (z2) {
            i5 = 1;
            Log.d(TAG, "computePopupPosition :displayFrame left:" + rect3.left);
            Log.d(TAG, "computePopupPosition :displayFrame right:" + rect3.right);
            Log.d(TAG, "computePopupPosition :displayFrame top:" + rect3.top);
            Log.d(TAG, "computePopupPosition :displayFrame bottom:" + rect3.bottom);
        } else {
            i5 = 1;
        }
        Rect rect4 = new Rect();
        View rootView = this.mAnchorView.getRootView();
        int width = rootView.getWidth();
        int height = rootView.getHeight();
        rect4.left = iArr2[i4] - iArr3[i4];
        rect4.right = rect4.left + width;
        rect4.top = iArr2[i5] - iArr3[i5];
        rect4.bottom = rect4.top + height;
        if (rect3.left == rect4.left && rect3.right == rect4.right && rect3.top == rect4.top && rect3.bottom == rect4.bottom) {
            iArr = iArr3;
            view2 = view3;
        } else {
            Rect rect5 = new Rect();
            this.mAnchorView.getWindowVisibleDisplayFrame(rect5);
            DisplayMetrics realDisplayMetrics = getRealDisplayMetrics();
            boolean zIsFreeFormMode = isFreeFormMode();
            iArr = iArr3;
            if (zIsFreeFormMode) {
                view2 = view3;
            } else {
                if (zIsFreeFormMode) {
                    view2 = view3;
                } else {
                    view2 = view3;
                    if (rect5.left != -10000 || rect5.right != 10000 || rect5.top != -10000 || rect5.bottom != 10000) {
                    }
                }
                if ((!zIsFreeFormMode && (rect5.right - rect5.left > realDisplayMetrics.widthPixels || rect5.bottom - rect5.top > realDisplayMetrics.heightPixels)) || (!zIsFreeFormMode && (rect3.left + iArr[i4] != iArr2[i4] || rect3.top + iArr[i5] != iArr2[i5]))) {
                }
            }
            rect3.left = rect4.left;
            rect3.right = rect4.right;
            rect3.top = rect4.top;
            rect3.bottom = rect4.bottom;
        }
        if (view2.getApplicationWindowToken() == view2.getWindowToken()) {
            this.mWindowGapX = iArr2[i4] - iArr[i4];
            int i6 = i5;
            this.mCoordinatesOfAnchorView = i6;
            if (z) {
                int i7 = iArr[i4];
                rect = new Rect(i7, iArr[i6], view2.getWidth() + i7, iArr[i6] + view2.getHeight());
            } else {
                rect = new Rect(rect2.left - rect3.left, rect2.top - rect3.top, rect2.right - rect3.left, rect2.bottom - rect3.top);
            }
        } else {
            this.mCoordinatesOfAnchorView = 2;
            int i8 = i4;
            this.mWindowGapX = i8;
            if (z) {
                int i9 = iArr2[i8];
                rect = new Rect(i9, iArr2[1], view2.getWidth() + i9, iArr2[1] + view2.getHeight());
            } else {
                rect = new Rect(iArr2[i8], iArr2[1], rect2.right, rect2.bottom);
            }
            if (rect3.left < 0 && rect3.top < 0) {
                rect3.left = i8;
                rect3.right = displayMetrics.widthPixels;
                rect3.top = i8;
                rect3.bottom = displayMetrics.heightPixels;
            }
        }
        if (rect3.left < 0 && rect3.top < 0) {
            ViewGroup.LayoutParams layoutParams = this.mParentView.getRootView().getLayoutParams();
            if (layoutParams instanceof WindowManager.LayoutParams) {
                WindowManager.LayoutParams layoutParams2 = (WindowManager.LayoutParams) layoutParams;
                int dimensionPixelSize = ((layoutParams2.flags & 512) == 1 && (((layoutParams2.systemUiVisibility | layoutParams2.subtreeSystemUiVisibility) & 1028) == 0)) ? this.mResources.getDimensionPixelSize(R.dimen.status_bar_height) : 0;
                rect3.left = 0;
                rect3.top = dimensionPixelSize;
                rect3.right = displayMetrics.widthPixels;
                rect3.bottom = displayMetrics.heightPixels;
            }
        }
        if (z2) {
            Log.d(TAG, "computePopupPosition: displayMetrics" + displayMetrics);
            Log.d(TAG, "computePopupPosition :anchorLocOnScr x:" + iArr2[0]);
            Log.d(TAG, "computePopupPosition :anchorLocOnScr y:" + iArr2[1]);
            Log.d(TAG, "computePopupPosition :anchorLocInWindow x:" + iArr[0]);
            Log.d(TAG, "computePopupPosition :anchorLocInWindow y:" + iArr[1]);
            Log.d(TAG, "computePopupPosition :displayFrame:" + rect3);
        }
        measureContentView(displayMetrics);
        computePopupPositionInternal(rect, rect3);
        if (this.mIsPopupTouchable) {
            if (this.mTouchableContainer == null) {
                this.mTouchableContainer = new TouchablePopupContainer(this.mContext);
            }
            if (this.mTouchableContainer.getChildCount() == 0) {
                this.mTouchableContainer.addView(this.mContentView);
            } else if (!this.mTouchableContainer.getChildAt(0).equals(this.mContentView)) {
                this.mTouchableContainer.removeAllViews();
                this.mTouchableContainer.addView(this.mContentView);
            }
            if (this.mToolType != 3) {
                Log.d(TAG, "computePopupPosition: Call resetTimeout()");
                this.mTouchableContainer.resetTimeout();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x01b5  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x01db  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x026d  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x031f  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x0326  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x0329  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x033f A[PHI: r3 r4
      0x033f: PHI (r3v41 int) = (r3v29 int), (r3v36 int), (r3v36 int), (r3v36 int), (r3v44 int) binds: [B:102:0x01b6, B:171:0x02b6, B:177:0x02cb, B:175:0x02c1, B:91:0x0188] A[DONT_GENERATE, DONT_INLINE]
      0x033f: PHI (r4v42 int) = (r4v33 int), (r4v33 int), (r4v33 int), (r4v33 int), (r4v43 int) binds: [B:102:0x01b6, B:171:0x02b6, B:177:0x02cb, B:175:0x02c1, B:91:0x0188] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x004d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void computePopupPositionInternal(Rect rect, Rect rect2) throws Resources.NotFoundException {
        int iMin;
        int iCenterX;
        int i;
        int iCenterY;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        this.mAnchorRect = rect;
        this.mDisplayFrame = rect2;
        int iMax = this.mPopupOffsetX;
        int i10 = this.mPopupOffsetY;
        int i11 = this.mPopupGravity;
        int i12 = i11 & Gravity.HORIZONTAL_GRAVITY_MASK;
        int i13 = i11 & Gravity.VERTICAL_GRAVITY_MASK;
        DisplayMetrics displayMetrics = this.mResources.getDisplayMetrics();
        DisplayMetrics realDisplayMetrics = getRealDisplayMetrics();
        ViewGroup.LayoutParams layoutParams = this.mParentView.getRootView().getLayoutParams();
        int dimensionPixelSize = this.mResources.getDimensionPixelSize(R.dimen.sem_hover_fulltext_popup_left_right_shift);
        int dimensionPixelSize2 = this.mResources.getDimensionPixelSize(R.dimen.status_bar_height);
        if (layoutParams instanceof WindowManager.LayoutParams) {
            WindowManager.LayoutParams layoutParams2 = (WindowManager.LayoutParams) layoutParams;
            iMin = ((layoutParams2.subtreeSystemUiVisibility | layoutParams2.systemUiVisibility) & 1028) == 0 ? dimensionPixelSize2 : 0;
        }
        int i14 = rect2.right - rect2.left;
        int i15 = rect2.bottom - rect2.top;
        if (this.mPopupGravity == 0) {
            int i16 = this.mCoordinatesOfAnchorView;
            if (i16 == 2) {
                iMax = this.mPopupOffsetX + rect2.left;
                i3 = this.mPopupOffsetY;
                i4 = rect2.top;
            } else {
                if (i16 == 1) {
                    iMax = this.mPopupOffsetX;
                    i10 = this.mPopupOffsetY;
                }
                i5 = this.mCoordinatesOfAnchorView;
                if (i5 == 2) {
                    if (this.mContentHeight + i10 > displayMetrics.heightPixels) {
                        if (i13 == 20560) {
                            if (rect.top >= this.mContentHeight) {
                                i8 = rect.top - this.mContentHeight;
                                i9 = this.mPopupOffsetY;
                            }
                        } else {
                            i8 = rect.top;
                            i9 = this.mContentHeight;
                        }
                        i10 = i8 - i9;
                    }
                    if (iMax < 0) {
                        iMax = Math.max(dimensionPixelSize, iMax);
                    } else if (this.mContentWidth + iMax > realDisplayMetrics.widthPixels) {
                        iMax = Math.min(iMax, (realDisplayMetrics.widthPixels - this.mContentWidth) - dimensionPixelSize);
                    }
                    if (localLOGV) {
                        Log.d(TAG, "computePopupPositionInternal :realDisplayMetrics width:" + realDisplayMetrics.widthPixels);
                        Log.d(TAG, "computePopupPositionInternal :realDisplayMetrics height:" + realDisplayMetrics.heightPixels);
                    }
                    if (i10 >= iMin) {
                        iMin = i10;
                    } else if (i13 == 12336) {
                        if (displayMetrics.heightPixels - rect.bottom >= this.mContentHeight) {
                            iMin = rect.bottom + this.mPopupOffsetY;
                        } else if (displayMetrics.heightPixels - rect.bottom > rect.top - iMin) {
                            iMin = rect.bottom;
                        }
                    } else {
                        iMin = Math.max(rect2.top, i10);
                    }
                } else if (i5 == 1) {
                    if (rect2.left + iMax <= 0) {
                        int iMin2 = Math.min(iMax, i14 - this.mContentWidth);
                        if (this.mDeviceRotation == 3 && this.mNavigationBarHeight != 0) {
                            int i17 = rect2.left + iMin2;
                            int i18 = this.mNavigationBarHeight;
                            if (i17 < i18) {
                                iMax = Math.max(i18 + dimensionPixelSize, iMin2);
                            }
                        } else if (this.mDeviceRotation == 1 && dimensionPixelSize2 != 0 && rect2.left + iMin2 < dimensionPixelSize2) {
                            iMax = Math.max(dimensionPixelSize + dimensionPixelSize2, iMin2);
                        } else {
                            iMax = Math.max((-rect2.left) + dimensionPixelSize, iMin2);
                        }
                    } else if (!isPopOver() && !isEmbeddedMode() && isAnchorViewInAppBounds(this.mAnchorRect.left, this.mAnchorRect.top) && rect2.left + iMax + this.mContentWidth >= realDisplayMetrics.widthPixels) {
                        iMax = Math.min(iMax, ((realDisplayMetrics.widthPixels - rect2.left) - this.mContentWidth) - dimensionPixelSize);
                    } else if (rect2.left >= 0) {
                        int i19 = this.mContentWidth;
                        if (i14 < i19) {
                            int i20 = rect2.left + i14;
                            int i21 = this.mContentWidth;
                            if (i20 - i21 >= 0) {
                                iMax = Math.min(iMax, i14 - i21);
                            }
                        } else if (iMax + i19 > i14) {
                            if (i14 >= i19 + dimensionPixelSize) {
                                iMax = Math.min(iMax, (i14 - i19) - dimensionPixelSize);
                            } else if (i14 >= i19) {
                                iMax = Math.min(iMax, i14 - i19);
                            }
                        } else if (this.mDeviceRotation != 3 || this.mNavigationBarHeight == 0) {
                            iMax = Math.max(iMax, dimensionPixelSize);
                        } else {
                            int i22 = rect2.left + iMax;
                            int i23 = this.mNavigationBarHeight;
                            if (i22 < i23) {
                                iMax = Math.max(iMax, i23 + dimensionPixelSize);
                            }
                        }
                    }
                    if (rect2.top + i10 >= dimensionPixelSize2) {
                        int i24 = this.mContentHeight;
                        if (i10 + i24 > i15) {
                            if (i13 == 20560) {
                                if (rect.top >= this.mContentHeight && ((rect2.top != dimensionPixelSize2 || this.mContentHeight + i10 > rect2.bottom) && rect2.top + i10 + this.mContentHeight > displayMetrics.heightPixels)) {
                                    i6 = rect.top - this.mContentHeight;
                                    i7 = this.mPopupOffsetY;
                                }
                                iMin = i10;
                            } else if (rect2.top != iMin) {
                                if (this.mDeviceRotation == 0 && this.mNavigationBarHeight != 0) {
                                    iMin = Math.min(realDisplayMetrics.heightPixels - this.mContentHeight, i10);
                                } else {
                                    iMin = Math.min(i15 - this.mContentHeight, i10);
                                }
                            } else {
                                iMin = Math.min(rect2.bottom - this.mContentHeight, i10);
                            }
                        } else if (i13 == 12336) {
                            dimensionPixelSize2 = (i10 >= dimensionPixelSize2 || (i24 + i10) + dimensionPixelSize2 <= rect.top || rect2.top + rect.bottom >= realDisplayMetrics.heightPixels) ? i10 : rect.bottom;
                            if ((!isMouseHoveringSettingsEnabled() ? 40 : this.mNavigationBarHeight) != 0 && rect2.top + dimensionPixelSize2 + this.mContentHeight > realDisplayMetrics.heightPixels) {
                                i6 = realDisplayMetrics.heightPixels - rect2.top;
                                i7 = this.mContentHeight;
                            }
                            iMin = dimensionPixelSize2;
                        } else {
                            if (i10 >= dimensionPixelSize2 || rect2.top != dimensionPixelSize2) {
                            }
                            if ((!isMouseHoveringSettingsEnabled() ? 40 : this.mNavigationBarHeight) != 0) {
                                i6 = realDisplayMetrics.heightPixels - rect2.top;
                                i7 = this.mContentHeight;
                            }
                            iMin = dimensionPixelSize2;
                        }
                        iMin = i6 - i7;
                    } else if (i13 == 12336) {
                        int i25 = (i15 - rect.bottom) - dimensionPixelSize2;
                        if (i25 >= this.mContentHeight) {
                            dimensionPixelSize2 = rect.bottom;
                            int i26 = this.mPopupOffsetY;
                            if (i25 - i26 >= this.mContentHeight) {
                                dimensionPixelSize2 += i26;
                            }
                        } else if (i25 > rect.top || (displayMetrics.heightPixels - rect2.top) - rect.bottom > this.mContentHeight) {
                            dimensionPixelSize2 = rect.bottom;
                        }
                        iMin = dimensionPixelSize2;
                    } else {
                        iMin = Math.max(dimensionPixelSize2, i10);
                    }
                }
                this.mPopupPosX = iMax;
                this.mPopupPosY = iMin;
            }
        } else {
            if (i12 == 1) {
                iCenterX = rect.centerX();
                i = this.mContentWidth / 2;
            } else {
                if (i12 == 3) {
                    iCenterX = rect.left;
                } else if (i12 == 5) {
                    iCenterX = rect.right;
                    i = this.mContentWidth;
                } else if (i12 == 257) {
                    iCenterX = rect2.centerX();
                    i = this.mContentWidth / 2;
                } else if (i12 == 259) {
                    iCenterX = rect.centerX();
                    i = this.mContentWidth;
                } else if (i12 == 261) {
                    iCenterX = rect.centerX();
                } else {
                    if (i12 == 513) {
                        if (isPopOver()) {
                            iCenterX = (this.mHoveringPointX - rect2.left) - (this.mContentWidth / 2);
                            this.mWindowGapX = 0;
                        } else {
                            iCenterX = this.mHoveringPointX - (this.mContentWidth / 2);
                        }
                        if (!this.mNeedNotWindowOffset || !isMouseHoveringSettingsEnabled()) {
                            i = this.mWindowGapX;
                        }
                    } else if (i12 == 771) {
                        iCenterX = rect.left;
                        i = this.mContentWidth;
                    } else if (i12 == 1285) {
                        iCenterX = rect.right;
                    } else {
                        iCenterX = this.mPopupOffsetX;
                    }
                    i5 = this.mCoordinatesOfAnchorView;
                    if (i5 == 2) {
                    }
                    this.mPopupPosX = iMax;
                    this.mPopupPosY = iMin;
                }
                iMax = iCenterX + this.mPopupOffsetX;
                if (i13 != 16) {
                    iCenterY = rect.centerY();
                    i2 = this.mContentHeight / 2;
                } else {
                    if (i13 == 48) {
                        i3 = rect.top;
                    } else if (i13 == 80) {
                        iCenterY = rect.bottom;
                        i2 = this.mContentHeight;
                    } else if (i13 == 12336) {
                        iCenterY = rect.top;
                        i2 = this.mContentHeight;
                    } else if (i13 == 20560) {
                        i3 = rect.bottom;
                    } else {
                        i3 = this.mPopupOffsetY;
                    }
                    i4 = this.mPopupOffsetY;
                }
                i3 = iCenterY - i2;
                i4 = this.mPopupOffsetY;
            }
            iCenterX -= i;
            iMax = iCenterX + this.mPopupOffsetX;
            if (i13 != 16) {
            }
            i3 = iCenterY - i2;
            i4 = this.mPopupOffsetY;
        }
        i10 = i3 + i4;
        i5 = this.mCoordinatesOfAnchorView;
        if (i5 == 2) {
        }
        this.mPopupPosX = iMax;
        this.mPopupPosY = iMin;
    }

    public void update() throws Resources.NotFoundException {
        PopupWindow popupWindow;
        if (!this.mNeedToMeasureContentView && (popupWindow = this.mPopup) != null && popupWindow.isShowing()) {
            computePopupPositionInternal(this.mAnchorRect, this.mDisplayFrame);
            this.mPopup.update(this.mPopupPosX, this.mPopupPosY, -1, -1);
        } else {
            View view = this.mAnchorView;
            if (view == null) {
                view = this.mParentView;
            }
            updateHoverPopup(view, this.mPopupGravity, this.mPopupOffsetX, this.mPopupOffsetY);
        }
    }

    private void updateHoverPopup(View view, int i, int i2, int i3) throws Resources.NotFoundException {
        TouchablePopupContainer touchablePopupContainer;
        if (this.mPopup == null) {
            Log.d(TAG, "updateHoverPopup(), returned due to mPopup == null  " + this.mParentView.toString());
            return;
        }
        computePopupPosition(view, i, i2, i3);
        if (this.mContentWidth == 0 && this.mContentHeight == 0) {
            return;
        }
        if (this.mIsPopupTouchable && (touchablePopupContainer = this.mTouchableContainer) != null) {
            this.mPopup.setContentView(touchablePopupContainer);
        } else {
            this.mPopup.setContentView(this.mContentView);
        }
        if (this.mPopup.getContentView() == null) {
            return;
        }
        if (this.mPopup.isShowing()) {
            this.mPopup.update(this.mPopupPosX, this.mPopupPosY, this.mContentWidth, this.mContentHeight);
            return;
        }
        IBinder applicationWindowToken = view.getApplicationWindowToken();
        if (applicationWindowToken != null && applicationWindowToken != view.getWindowToken()) {
            this.mPopup.showAtLocation(applicationWindowToken, 0, this.mPopupPosX, this.mPopupPosY);
        } else {
            this.mPopup.showAtLocation(view, 0, this.mPopupPosX, this.mPopupPosY);
        }
    }

    public void setAnimationStyle(int i) {
        this.mAnimationStyle = i;
        PopupWindow popupWindow = this.mPopup;
        if (popupWindow != null) {
            popupWindow.setAnimationStyle(i);
        }
    }

    public void setTouchable(boolean z) {
        this.mIsPopupTouchable = z;
        PopupWindow popupWindow = this.mPopup;
        if (popupWindow != null) {
            popupWindow.setTouchable(z);
        }
    }

    public boolean onHoverEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        long jUptimeMillis = SystemClock.uptimeMillis() - motionEvent.getEventTime();
        if (action != 7) {
            if (action != 9) {
                if (action == 10 && this.mIsPopupTouchable) {
                    Handler handler = this.mDismissHandler;
                    if (handler != null && handler.hasMessages(1)) {
                        this.mDismissHandler.removeMessages(1);
                    }
                    if (isShowing()) {
                        return true;
                    }
                }
            } else {
                if (jUptimeMillis > 1000) {
                    return true;
                }
                if (this.mIsHoverPaddingEnabled) {
                    this.mIsTryingShowPopup = pointInValidHoverArea(x, y);
                }
            }
            return false;
        }
        setHoveringPoint((int) motionEvent.getRawX(), (int) motionEvent.getRawY());
        if (this.mIsHoverPaddingEnabled) {
            boolean zPointInValidHoverArea = pointInValidHoverArea(x, y);
            if (zPointInValidHoverArea && !this.mIsTryingShowPopup) {
                if (jUptimeMillis > 1000) {
                    this.mIsTryingShowPopup = false;
                    return true;
                }
                this.mIsTryingShowPopup = true;
                show();
                return true;
            }
            if (!zPointInValidHoverArea && this.mIsTryingShowPopup && !this.mIsPopupTouchable) {
                this.mIsTryingShowPopup = false;
                dismiss();
                return true;
            }
        }
        if (this.mToolType != 3) {
            resetTimeout();
        }
        return true;
    }

    protected void postDismiss(int i) {
        this.mParentView.postDelayed(new Runnable() { // from class: com.samsung.android.widget.SemHoverPopupWindow$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.dismiss();
            }
        }, i);
    }

    public void dismiss() {
        if (!this.mIsSkipPenPointEffect) {
            showPenPointEffect(false);
        }
        dismissPopup();
    }

    private void dismissPopup() {
        if (this.mIsShowMessageSent || this.mShowPopupRunnable != null) {
            this.mParentView.removeCallbacks(this.mShowPopupRunnable);
            this.mShowPopupRunnable = null;
            this.mIsShowMessageSent = false;
        }
        PopupWindow popupWindow = this.mPopup;
        if (popupWindow != null) {
            popupWindow.dismiss();
            this.mPopup = null;
            this.mIsCheckedRealDisplayMetricsInDexMode = false;
        }
    }

    protected void showPenPointEffect(boolean z) {
        if (this.mToolType == 2) {
            if (z) {
                this.mParentView.semSetPointerIcon(2, PointerIcon.getSystemIcon(this.mContext, 20010));
                this.mIsSPenPointChanged = true;
            } else if (this.mIsSPenPointChanged) {
                this.mParentView.semSetPointerIcon(2, null);
                this.mIsSPenPointChanged = false;
            }
        }
    }

    private boolean pointInValidHoverArea(float f, float f2) {
        return f >= ((float) this.mHoverPaddingLeft) && f < ((float) ((this.mParentView.getRight() - this.mParentView.getLeft()) - this.mHoverPaddingRight)) && f2 >= ((float) this.mHoverPaddingTop) && f2 < ((float) ((this.mParentView.getBottom() - this.mParentView.getTop()) - this.mHoverPaddingBottom));
    }

    private int getStateHashCode() {
        int i = this.mPopupType;
        View view = this.mParentView;
        if (view == null) {
            return i;
        }
        int windowVisibility = i | (view.getWindowVisibility() << 1) | (this.mParentView.getVisibility() << 2) | (this.mParentView.getLeft() << 4) | (this.mParentView.getRight() << 8) | (this.mParentView.getTop() << 12) | (this.mParentView.getBottom() << 16);
        int[] iArr = new int[2];
        this.mParentView.getLocationOnScreen(iArr);
        return (iArr[0] << 20) | (iArr[1] << 24) | windowVisibility;
    }

    private void resetTimeout() {
        Handler handler = this.mDismissHandler;
        if (handler != null) {
            if (handler.hasMessages(1)) {
                this.mDismissHandler.removeMessages(1);
            }
            Handler handler2 = this.mDismissHandler;
            handler2.sendMessageDelayed(handler2.obtainMessage(1), 2000L);
        }
    }

    private int getNavigationBarHeight() {
        if (this.mResources.getBoolean(R.bool.config_showNavigationBar)) {
            return this.mResources.getDimensionPixelSize(R.dimen.navigation_bar_height);
        }
        return 0;
    }

    private int getDeviceRotation() {
        return ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
    }

    private boolean isPopOver() {
        return this.mContext.getResources().getConfiguration().semIsPopOver();
    }

    private boolean isEmbeddedMode() {
        return this.mContext.getResources().getConfiguration().windowConfiguration.getEmbedActivityMode() != 0;
    }

    private boolean isAnchorViewInAppBounds(int i, int i2) {
        return this.mContext.getResources().getConfiguration().windowConfiguration.getAppBounds().contains(i, i2);
    }

    protected class TouchablePopupContainer extends FrameLayout {
        private static final int MSG_TIMEOUT = 1;
        private static final int SLOP_FACTOR_POINT_IN_VIEW = -2;
        private static final int TIMEOUT_DELAY = 2000;
        private static final int TIMEOUT_DISMISS_DELAY = 100;
        protected Handler mContainerDismissHandler;
        private Runnable mDismissPopupRunnable;
        private boolean mIsHoverExitCalled;

        public TouchablePopupContainer(Context context) {
            super(context);
            this.mIsHoverExitCalled = false;
            this.mDismissPopupRunnable = null;
            this.mContainerDismissHandler = null;
            this.mContainerDismissHandler = new Handler(this.mContext.getMainLooper()) { // from class: com.samsung.android.widget.SemHoverPopupWindow.TouchablePopupContainer.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    Log.d(SemHoverPopupWindow.TAG, "TouchablePopupContainer: ***** mContainerDismissHandler handleMessage *****");
                    if (SemHoverPopupWindow.this.mPopup != null && SemHoverPopupWindow.this.mPopup.isShowing() && message.what == 1) {
                        Log.d(SemHoverPopupWindow.TAG, "TouchablePopupContainer: mContainerDismissHandler handleMessage: Call dismiss");
                        SemHoverPopupWindow.this.dismiss();
                    }
                }
            };
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            Runnable runnable;
            if (this.mIsHoverExitCalled && (runnable = this.mDismissPopupRunnable) != null) {
                removeCallbacks(runnable);
                this.mDismissPopupRunnable = null;
                this.mIsHoverExitCalled = false;
            }
            boolean zDispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
            if (motionEvent.getAction() == 1 && SemHoverPopupWindow.this.mDismissTouchableHPWOnActionUp) {
                postDelayed(new Runnable() { // from class: com.samsung.android.widget.SemHoverPopupWindow.TouchablePopupContainer.2
                    @Override // java.lang.Runnable
                    public void run() {
                        SemHoverPopupWindow.this.dismiss();
                    }
                }, 100L);
            }
            return zDispatchTouchEvent;
        }

        @Override // android.view.ViewGroup, android.view.View
        protected boolean dispatchHoverEvent(MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            if (action != 7) {
                if (action == 10) {
                    if (pointInView(motionEvent.getX(), motionEvent.getY(), -2.0f)) {
                        this.mIsHoverExitCalled = true;
                        Runnable runnable = new Runnable() { // from class: com.samsung.android.widget.SemHoverPopupWindow.TouchablePopupContainer.3
                            @Override // java.lang.Runnable
                            public void run() {
                                SemHoverPopupWindow.this.dismiss();
                            }
                        };
                        this.mDismissPopupRunnable = runnable;
                        postDelayed(runnable, 100L);
                    } else {
                        boolean zDispatchHoverEvent = super.dispatchHoverEvent(motionEvent);
                        SemHoverPopupWindow.this.dismiss();
                        return zDispatchHoverEvent;
                    }
                }
            } else if (SemHoverPopupWindow.this.mToolType != 3) {
                resetTimeout();
            }
            return super.dispatchHoverEvent(motionEvent);
        }

        public void resetTimeout() {
            Handler handler = this.mContainerDismissHandler;
            if (handler != null) {
                if (handler.hasMessages(1)) {
                    this.mContainerDismissHandler.removeMessages(1);
                }
                Handler handler2 = this.mContainerDismissHandler;
                handler2.sendMessageDelayed(handler2.obtainMessage(1), 2000L);
            }
        }
    }

    public static final class Gravity {
        public static final int BOTTOM = 80;
        public static final int BOTTOM_UNDER = 20560;
        public static final int CENTER = 17;
        public static final int CENTER_HORIZONTAL = 1;
        public static final int CENTER_HORIZONTAL_ON_POINT = 513;
        public static final int CENTER_HORIZONTAL_ON_WINDOW = 257;
        public static final int CENTER_VERTICAL = 16;
        public static final int HORIZONTAL_GRAVITY_MASK = 3855;
        public static final int LEFT = 3;
        public static final int LEFT_CENTER_AXIS = 259;
        public static final int LEFT_OUTSIDE = 771;
        public static final int NO_GRAVITY = 0;
        public static final int RIGHT = 5;
        public static final int RIGHT_CENTER_AXIS = 261;
        public static final int RIGHT_OUTSIDE = 1285;
        public static final int TOP = 48;
        public static final int TOP_ABOVE = 12336;
        public static final int VERTICAL_GRAVITY_MASK = 61680;

        private Gravity() {
        }
    }

    private void hidden_setGravity(int i) {
        setGravity(i);
    }

    private void hidden_setHoverDetectTime(int i) {
        setHoverDetectTime(i);
    }

    private void hidden_setOffset(int i, int i2) {
        setOffset(i, i2);
    }

    private void hidden_update() throws Resources.NotFoundException {
        update();
    }
}
