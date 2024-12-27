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

    public SemHoverPopupWindow(View parentView, int type) {
        this.mPopupType = 0;
        this.mParentView = parentView;
        Context context = parentView.getContext();
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(16843945, outValue, false);
        if (outValue.data != 0) {
            this.mContext = new ContextThemeWrapper(context, outValue.data);
        } else {
            this.mContext = context;
        }
        this.mResources = this.mContext.getResources();
        this.mPopupType = type;
        initInstance();
        setInstanceByType(type);
        if (isMouseHoveringSettingsEnabled()) {
            this.mHoverDetectTimeMS = 750;
        }
        this.mDismissHandler =
                new Handler(
                        this.mContext
                                .getMainLooper()) { // from class:
                                                    // com.samsung.android.widget.SemHoverPopupWindow.1
                    @Override // android.os.Handler
                    public void handleMessage(Message msg) {
                        if (SemHoverPopupWindow.this.mPopup != null
                                && SemHoverPopupWindow.this.mPopup.isShowing()
                                && msg.what == 1) {
                            Log.d(
                                    SemHoverPopupWindow.TAG,
                                    "mDismissHandler handleMessage: Call dismiss");
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
            this.mCoverManager =
                    ICoverManager.Stub.asInterface(
                            ServiceManager.getService(UnionConstants.SERVICE_COVER));
            if (this.mCoverManager == null) {
                Log.e(TAG, "warning: no COVER_MANAGER_SERVICE");
            }
        }
    }

    protected void setInstanceByType(int type) {}

    public boolean isHoverPopupPossible() {
        switch (this.mPopupType) {
            case 1:
                if (this.mParentView == null || TextUtils.isEmpty(getTooltipText())) {}
                break;
        }
        return false;
    }

    protected boolean isUspFeature() {
        return this.mIsUspFeature;
    }

    private boolean isFreeFormMode() {
        int windowMode =
                this.mContext
                        .getResources()
                        .getConfiguration()
                        .windowConfiguration
                        .getWindowingMode();
        return windowMode == 5;
    }

    private DisplayMetrics getRealDisplayMetrics() {
        Display display =
                ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE))
                        .getDefaultDisplay();
        DisplayInfo displayInfo = new DisplayInfo();
        if (display != null) {
            display.getDisplayInfo(displayInfo);
        }
        DisplayMetrics displaySize = new DisplayMetrics();
        if (this.mContext.getApplicationContext() == null) {
            Log.d(TAG, "getApplicationContext() is null");
            displaySize.widthPixels = displayInfo.appWidth;
            displaySize.heightPixels = displayInfo.appHeight;
            displaySize.density = this.mResources.getDisplayMetrics().density;
        } else {
            displaySize = this.mContext.getApplicationContext().getResources().getDisplayMetrics();
        }
        if (isMouseHoveringSettingsEnabled()) {
            if (!this.mIsCheckedRealDisplayMetricsInDexMode) {
                this.mIsCheckedRealDisplayMetricsInDexMode = true;
                if (localLOGV) {
                    Log.d(
                            TAG,
                            "getRealDisplayMetrics :sRealDisplayMetricsInDexMode width:"
                                    + displayInfo.appWidth);
                    Log.d(
                            TAG,
                            "getRealDisplayMetrics :sRealDisplayMetricsInDexMode height:"
                                    + displayInfo.appHeight);
                }
                sRealDisplayMetricsInDexMode.widthPixels = displayInfo.appWidth;
                sRealDisplayMetricsInDexMode.heightPixels = displayInfo.appHeight;
            }
            return sRealDisplayMetricsInDexMode;
        }
        if (localLOGV) {
            Log.d(TAG, "getRealDisplayMetrics :displaySize width:" + displaySize.widthPixels);
            Log.d(TAG, "getRealDisplayMetrics :displaySize height:" + displaySize.heightPixels);
        }
        return displaySize;
    }

    private boolean isHoveringSettingEnabled() {
        switch (this.mToolType) {
            case 2:
                return isSPenHoveringSettingsEnabled();
            case 3:
                return isMouseHoveringSettingsEnabled();
            default:
                return false;
        }
    }

    private boolean isSPenHoveringSettingsEnabled() {
        return Settings.System.getIntForUser(
                        this.mContext.getContentResolver(), Settings.System.SEM_PEN_HOVERING, 0, -3)
                == 1;
    }

    private boolean isMouseHoveringSettingsEnabled() {
        if (this.mParentView != null) {
            return this.mParentView.semIsDesktopMode();
        }
        return false;
    }

    public void setHoverPopupToolType(int type) {
        this.mToolType = type;
    }

    private boolean isTalkBackEnabledForDeX() {
        AccessibilityManager accessibilityManager;
        return isMouseHoveringSettingsEnabled()
                && (accessibilityManager = AccessibilityManager.getInstance(this.mContext)) != null
                && accessibilityManager.semIsScreenReaderEnabled()
                && accessibilityManager.isTouchExplorationEnabled();
    }

    private boolean isLockScreenMode() {
        KeyguardManager keyguardManager =
                (KeyguardManager) this.mContext.getSystemService(Context.KEYGUARD_SERVICE);
        return keyguardManager.inKeyguardRestrictedInputMode();
    }

    private boolean isViewCoverClose() {
        CoverState coverState;
        boolean isCoverOpen = true;
        try {
            if (this.mCoverManager != null
                    && (coverState = this.mCoverManager.getCoverState()) != null) {
                isCoverOpen = coverState.getSwitchState();
            }
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in getCoverState: ", e);
        }
        return !isCoverOpen;
    }

    public void setDismissTouchableHPWOnActionUp(boolean bDismissTouchableHPWOnActionUp) {
        this.mDismissTouchableHPWOnActionUp = bDismissTouchableHPWOnActionUp;
    }

    public boolean getIsDismissTouchableHPWOnActionUp() {
        return this.mDismissTouchableHPWOnActionUp;
    }

    public View getParentView() {
        return this.mParentView;
    }

    public void setOnSetContentViewListener(OnSetContentViewListener l) {
        this.mListener = l;
    }

    public void setHoverPopupPreShowListener(HoverPopupPreShowListener l) {
        this.mPreShowListener = l;
    }

    public void setContent(View view) {
        setContent(view, view != null ? view.getLayoutParams() : null);
    }

    public void setContent(View view, ViewGroup.LayoutParams lp) {
        this.mContentView = view;
        this.mContentLP = lp;
        this.mNeedToMeasureContentView = true;
    }

    public void setContent(CharSequence text) {
        this.mContentText = text;
        this.mNeedToMeasureContentView = true;
    }

    public View getContentView() {
        return this.mContentView;
    }

    public boolean isShowing() {
        return this.mPopup != null && this.mPopup.isShowing();
    }

    public void setHoverDetectTime(int ms) {
        this.mHoverDetectTimeMS = ms;
    }

    public void setHoverPaddingArea(int left, int top, int right, int bottom) {
        this.mHoverPaddingLeft = left;
        this.mHoverPaddingRight = right;
        this.mHoverPaddingTop = top;
        this.mHoverPaddingBottom = bottom;
        if (this.mHoverPaddingLeft != 0
                || this.mHoverPaddingRight != 0
                || this.mHoverPaddingTop != 0
                || this.mHoverPaddingBottom != 0) {
            this.mIsHoverPaddingEnabled = true;
        }
    }

    public void setGravity(int gravity) {
        this.mPopupGravity = gravity;
    }

    public void setOffset(int x, int y) {
        this.mPopupOffsetX = x;
        this.mPopupOffsetY = y;
    }

    public void setHoveringPoint(int x, int y) {
        this.mHoveringPointX = x;
        this.mHoveringPointY = y;
    }

    public void setNeedNotWindowOffset(boolean needNotWindowOffset) {
        this.mNeedNotWindowOffset = needNotWindowOffset;
    }

    private CharSequence getTooltipText() {
        if (!TextUtils.isEmpty(this.mContentText)) {
            return this.mContentText;
        }
        if (!TextUtils.isEmpty(this.mParentView.getContentDescription())) {
            return this.mParentView.getContentDescription();
        }
        return null;
    }

    public void show() {
        if (localLOGV) {
            Log.d(TAG, "show :" + this.mParentView.toString());
        }
        Log.d(TAG, "Toolkit porting remove this log after all feature included");
        View anchorView = this.mAnchorView != null ? this.mAnchorView : this.mParentView;
        int type = anchorView.semGetHoverPopupType();
        if (type != this.mPopupType) {
            this.mPopupType = type;
            setInstanceByType(type);
        }
        if ((this.mPreShowListener == null || this.mPreShowListener.onHoverPopupPreShow())
                && this.mEnabled
                && type != 0
                && type != 1
                && !this.mIsShowMessageSent) {
            if ((this.mIsHoverPaddingEnabled && !this.mIsTryingShowPopup)
                    || !isHoverPopupPossible()
                    || !isHoveringSettingEnabled()
                    || isShowing()
                    || this.mParentView.getHandler() == null
                    || isViewCoverClose()
                    || isLockScreenMode()
                    || isTalkBackEnabledForDeX()) {
                return;
            }
            this.mHashCodeForViewState = getStateHashCode();
            if (!this.mIsSkipPenPointEffect) {
                showPenPointEffect(true);
            }
            this.mShowPopupRunnable =
                    new Runnable() { // from class:
                                     // com.samsung.android.widget.SemHoverPopupWindow$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            SemHoverPopupWindow.this.showPopup();
                        }
                    };
            this.mParentView.postDelayed(this.mShowPopupRunnable, this.mHoverDetectTimeMS);
            this.mIsShowMessageSent = true;
        }
    }

    public void showPopup() {
        try {
            if (this.mHashCodeForViewState != getStateHashCode()) {
                Log.d(
                        TAG,
                        "showPopup() is cancelled : "
                                + this.mHashCodeForViewState
                                + " "
                                + getStateHashCode());
                if (this.mIsUspFeature
                        && this.mParentView.getWindowVisibility() == 0
                        && this.mParentView.getVisibility() == 0) {
                    dismiss();
                    show();
                    return;
                } else {
                    dismiss();
                    return;
                }
            }
            if (!this.mIsSkipPenPointEffect) {
                showPenPointEffect(true);
            }
            this.mIsSkipPenPointEffect = false;
            if (this.mPopup != null) {
                this.mPopup.dismiss();
            }
            createPopupWindow();
            setPopupContent();
            update();
        } catch (Exception e) {
            Log.i(TAG, "Fail show hover popup :" + e);
        }
    }

    protected PopupWindow createPopupWindow() {
        if (this.mPopup == null) {
            this.mPopup = new PopupWindow(this.mParentView.getContext());
            this.mPopup.setWidth(-2);
            this.mPopup.setHeight(-2);
            this.mPopup.setTouchable(this.mIsPopupTouchable);
            this.mPopup.setClippingEnabled(false);
            this.mPopup.setBackgroundDrawable(null);
            this.mPopup.setWindowLayoutType(1005);
            View anchorView = this.mAnchorView != null ? this.mAnchorView : this.mParentView;
            if (anchorView.getApplicationWindowToken() != anchorView.getWindowToken()) {
                this.mPopup.setIsLaidOutInScreen(true);
            }
            this.mPopup.setAnimationStyle(this.mAnimationStyle);
        }
        return this.mPopup;
    }

    private void setPopupContent() {
        switch (this.mPopupType) {
            case 0:
                this.mContentView = null;
                break;
            case 1:
                this.mContentView = null;
                break;
            case 2:
                makeDefaultContentView();
                break;
            case 3:
                break;
            default:
                this.mContentView = null;
                break;
        }
        if (this.mListener != null) {
            this.mListener.onSetContentView(this.mParentView, this);
        }
    }

    protected void makeDefaultContentView() {}

    private void measureContentView(DisplayMetrics displayMetrics) {
        int widthMeasureSpec;
        int heightMeasureSpec;
        if (this.mContentView == null) {
            return;
        }
        if (this.mContentLP == null) {
            widthMeasureSpec =
                    View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, Integer.MIN_VALUE);
            heightMeasureSpec =
                    View.MeasureSpec.makeMeasureSpec(
                            displayMetrics.heightPixels, Integer.MIN_VALUE);
        } else {
            if (this.mContentLP.width < 0) {
                widthMeasureSpec =
                        View.MeasureSpec.makeMeasureSpec(
                                displayMetrics.widthPixels, Integer.MIN_VALUE);
            } else {
                widthMeasureSpec =
                        View.MeasureSpec.makeMeasureSpec(this.mContentLP.width, 1073741824);
            }
            if (this.mContentLP.height < 0) {
                heightMeasureSpec =
                        View.MeasureSpec.makeMeasureSpec(
                                displayMetrics.heightPixels, Integer.MIN_VALUE);
            } else {
                heightMeasureSpec =
                        View.MeasureSpec.makeMeasureSpec(this.mContentLP.height, 1073741824);
            }
        }
        this.mContentView.measure(widthMeasureSpec, heightMeasureSpec);
        this.mNeedToMeasureContentView = false;
        this.mContentWidth = this.mContentView.getMeasuredWidth();
        this.mContentHeight = this.mContentView.getMeasuredHeight();
        if (this.mPopup != null) {
            this.mPopup.setWidth(this.mContentWidth);
            this.mPopup.setHeight(this.mContentHeight);
            this.mPopup.setAnimationStyle(this.mAnimationStyle);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:105:0x0227, code lost:

       if ((r6.top + r3[1]) != r2[1]) goto L69;
    */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x01f1, code lost:

       if (r8.bottom == 10000) goto L60;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void computePopupPosition(android.view.View r28, int r29, int r30, int r31) {
        /*
            Method dump skipped, instructions count: 1046
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.widget.SemHoverPopupWindow.computePopupPosition(android.view.View,"
                    + " int, int, int):void");
    }

    private void computePopupPositionInternal(Rect anchorRect, Rect displayFrame) {
        int posX;
        int posX2;
        int posY;
        int hGravity;
        int bottomBarHeight;
        int posX3;
        this.mAnchorRect = anchorRect;
        this.mDisplayFrame = displayFrame;
        int posX4 = this.mPopupOffsetX;
        int posY2 = this.mPopupOffsetY;
        int hGravity2 = this.mPopupGravity & Gravity.HORIZONTAL_GRAVITY_MASK;
        int vGravity = this.mPopupGravity & Gravity.VERTICAL_GRAVITY_MASK;
        DisplayMetrics displayMetrics = this.mResources.getDisplayMetrics();
        DisplayMetrics realDisplayMetrics = getRealDisplayMetrics();
        View root = this.mParentView.getRootView();
        ViewGroup.LayoutParams vlp = root.getLayoutParams();
        boolean isSystemUiVisible = false;
        int fullTextXShift =
                this.mResources.getDimensionPixelSize(
                        R.dimen.sem_hover_fulltext_popup_left_right_shift);
        int statusBarHeight = this.mResources.getDimensionPixelSize(R.dimen.status_bar_height);
        int realStatusBarHeight = 0;
        if (vlp instanceof WindowManager.LayoutParams) {
            WindowManager.LayoutParams wlp = (WindowManager.LayoutParams) vlp;
            isSystemUiVisible =
                    ((wlp.systemUiVisibility | wlp.subtreeSystemUiVisibility) & 1028) == 0;
            realStatusBarHeight = isSystemUiVisible ? statusBarHeight : 0;
        }
        int displayFrameWidth = displayFrame.right - displayFrame.left;
        int displayFrameHeight = displayFrame.bottom - displayFrame.top;
        if (this.mPopupGravity == 0) {
            if (this.mCoordinatesOfAnchorView == 2) {
                int posX5 = this.mPopupOffsetX + displayFrame.left;
                int i = this.mPopupOffsetY;
                int posX6 = displayFrame.top;
                hGravity = posX6 + i;
                posX2 = posX5;
            } else {
                int posY3 = this.mCoordinatesOfAnchorView;
                if (posY3 != 1) {
                    posX2 = posX4;
                    hGravity = posY2;
                } else {
                    posX2 = this.mPopupOffsetX;
                    hGravity = this.mPopupOffsetY;
                }
            }
        } else {
            switch (hGravity2) {
                case 1:
                    int posX7 = anchorRect.centerX();
                    posX = posX7 - (this.mContentWidth / 2);
                    break;
                case 3:
                    posX = anchorRect.left;
                    break;
                case 5:
                    int posX8 = anchorRect.right;
                    posX = posX8 - this.mContentWidth;
                    break;
                case 257:
                    int posX9 = displayFrame.centerX();
                    posX = posX9 - (this.mContentWidth / 2);
                    break;
                case 259:
                    int posX10 = anchorRect.centerX();
                    posX = posX10 - this.mContentWidth;
                    break;
                case 261:
                    posX = anchorRect.centerX();
                    break;
                case 513:
                    if (isPopOver()) {
                        posX =
                                (this.mHoveringPointX - displayFrame.left)
                                        - (this.mContentWidth / 2);
                        this.mWindowGapX = 0;
                    } else {
                        posX = this.mHoveringPointX - (this.mContentWidth / 2);
                    }
                    if (!this.mNeedNotWindowOffset || !isMouseHoveringSettingsEnabled()) {
                        posX -= this.mWindowGapX;
                        break;
                    }
                    break;
                case 771:
                    int posX11 = anchorRect.left;
                    posX = posX11 - this.mContentWidth;
                    break;
                case 1285:
                    posX = anchorRect.right;
                    break;
                default:
                    posX = this.mPopupOffsetX;
                    break;
            }
            posX2 = posX + this.mPopupOffsetX;
            switch (vGravity) {
                case 16:
                    int hGravity3 = anchorRect.centerY();
                    posY = hGravity3 - (this.mContentHeight / 2);
                    break;
                case 48:
                    posY = anchorRect.top;
                    break;
                case 80:
                    int hGravity4 = anchorRect.bottom;
                    posY = hGravity4 - this.mContentHeight;
                    break;
                case Gravity.TOP_ABOVE /* 12336 */:
                    int posY4 = anchorRect.top;
                    int hGravity5 = this.mContentHeight;
                    posY = (posY4 - hGravity5) - 0;
                    break;
                case Gravity.BOTTOM_UNDER /* 20560 */:
                    int posY5 = anchorRect.bottom;
                    posY = posY5 + 0;
                    break;
                default:
                    posY = this.mPopupOffsetY;
                    break;
            }
            hGravity = this.mPopupOffsetY + posY;
        }
        int posY6 = this.mCoordinatesOfAnchorView;
        if (posY6 == 2) {
            if (this.mContentHeight + hGravity > displayMetrics.heightPixels) {
                if (vGravity == 20560) {
                    if (anchorRect.top >= this.mContentHeight) {
                        hGravity = (anchorRect.top - this.mContentHeight) - this.mPopupOffsetY;
                    }
                } else {
                    hGravity = anchorRect.top - this.mContentHeight;
                }
            }
            if (posX2 < 0) {
                posX2 = Math.max(fullTextXShift, posX2);
            } else if (this.mContentWidth + posX2 > realDisplayMetrics.widthPixels) {
                posX2 =
                        Math.min(
                                posX2,
                                (realDisplayMetrics.widthPixels - this.mContentWidth)
                                        - fullTextXShift);
            }
            if (!localLOGV) {
                posX3 = posX2;
            } else {
                Log.d(
                        TAG,
                        "computePopupPositionInternal :realDisplayMetrics width:"
                                + realDisplayMetrics.widthPixels);
                posX3 = posX2;
                Log.d(
                        TAG,
                        "computePopupPositionInternal :realDisplayMetrics height:"
                                + realDisplayMetrics.heightPixels);
            }
            if (hGravity >= realStatusBarHeight) {
                posX2 = posX3;
            } else if (vGravity == 12336) {
                if (displayMetrics.heightPixels - anchorRect.bottom >= this.mContentHeight) {
                    hGravity = anchorRect.bottom + this.mPopupOffsetY;
                    posX2 = posX3;
                } else if (displayMetrics.heightPixels - anchorRect.bottom
                        > anchorRect.top - realStatusBarHeight) {
                    hGravity = anchorRect.bottom + 0;
                    posX2 = posX3;
                } else {
                    hGravity = realStatusBarHeight;
                    posX2 = posX3;
                }
            } else {
                hGravity = Math.max(displayFrame.top, hGravity);
                posX2 = posX3;
            }
        } else if (this.mCoordinatesOfAnchorView == 1) {
            if (displayFrame.left + posX2 <= 0) {
                int posX12 = Math.min(posX2, displayFrameWidth - this.mContentWidth);
                if (this.mDeviceRotation == 3
                        && this.mNavigationBarHeight != 0
                        && displayFrame.left + posX12 < this.mNavigationBarHeight) {
                    posX2 = Math.max(this.mNavigationBarHeight + fullTextXShift, posX12);
                } else if (this.mDeviceRotation == 1
                        && statusBarHeight != 0
                        && displayFrame.left + posX12 < statusBarHeight) {
                    posX2 = Math.max(statusBarHeight + fullTextXShift, posX12);
                } else {
                    posX2 = Math.max((-displayFrame.left) + fullTextXShift, posX12);
                }
            } else if (!isPopOver()
                    && !isEmbeddedMode()
                    && isAnchorViewInAppBounds(this.mAnchorRect.left, this.mAnchorRect.top)
                    && displayFrame.left + posX2 + this.mContentWidth
                            >= realDisplayMetrics.widthPixels) {
                posX2 =
                        Math.min(
                                posX2,
                                ((realDisplayMetrics.widthPixels - displayFrame.left)
                                                - this.mContentWidth)
                                        - fullTextXShift);
            } else if (displayFrame.left >= 0) {
                if (displayFrameWidth < this.mContentWidth) {
                    if ((displayFrame.left + displayFrameWidth) - this.mContentWidth >= 0) {
                        posX2 = Math.min(posX2, displayFrameWidth - this.mContentWidth);
                    }
                } else if (this.mContentWidth + posX2 > displayFrameWidth) {
                    if (displayFrameWidth >= this.mContentWidth + fullTextXShift) {
                        posX2 =
                                Math.min(
                                        posX2,
                                        (displayFrameWidth - this.mContentWidth) - fullTextXShift);
                    } else if (displayFrameWidth >= this.mContentWidth) {
                        posX2 = Math.min(posX2, displayFrameWidth - this.mContentWidth);
                    }
                } else if (this.mDeviceRotation == 3
                        && this.mNavigationBarHeight != 0
                        && displayFrame.left + posX2 < this.mNavigationBarHeight) {
                    posX2 = Math.max(posX2, this.mNavigationBarHeight + fullTextXShift);
                } else {
                    posX2 = Math.max(posX2, fullTextXShift);
                }
            }
            if (displayFrame.top + hGravity >= statusBarHeight) {
                if (this.mContentHeight + hGravity > displayFrameHeight) {
                    if (vGravity == 20560) {
                        if (anchorRect.top >= this.mContentHeight
                                && ((displayFrame.top != statusBarHeight
                                                || this.mContentHeight + hGravity
                                                        > displayFrame.bottom)
                                        && displayFrame.top + hGravity + this.mContentHeight
                                                > displayMetrics.heightPixels)) {
                            hGravity = (anchorRect.top - this.mContentHeight) - this.mPopupOffsetY;
                        }
                    } else {
                        hGravity =
                                displayFrame.top != realStatusBarHeight
                                        ? (this.mDeviceRotation != 0
                                                        || this.mNavigationBarHeight == 0)
                                                ? Math.min(
                                                        displayFrameHeight - this.mContentHeight,
                                                        hGravity)
                                                : Math.min(
                                                        realDisplayMetrics.heightPixels
                                                                - this.mContentHeight,
                                                        hGravity)
                                        : Math.min(
                                                displayFrame.bottom - this.mContentHeight,
                                                hGravity);
                    }
                } else {
                    if (vGravity == 12336) {
                        if (hGravity < statusBarHeight
                                && this.mContentHeight + hGravity + statusBarHeight > anchorRect.top
                                && displayFrame.top + anchorRect.bottom
                                        < realDisplayMetrics.heightPixels) {
                            hGravity = anchorRect.bottom + 0;
                        }
                    } else if (hGravity < statusBarHeight && displayFrame.top == statusBarHeight) {
                        hGravity = statusBarHeight;
                    }
                    if (isMouseHoveringSettingsEnabled()) {
                        bottomBarHeight = 40;
                    } else {
                        bottomBarHeight = this.mNavigationBarHeight;
                    }
                    if (bottomBarHeight != 0
                            && displayFrame.top + hGravity + this.mContentHeight
                                    > realDisplayMetrics.heightPixels) {
                        hGravity =
                                (realDisplayMetrics.heightPixels - displayFrame.top)
                                        - this.mContentHeight;
                    }
                }
            } else if (vGravity == 12336) {
                int comparingHeight = (displayFrameHeight - anchorRect.bottom) - statusBarHeight;
                if (comparingHeight < this.mContentHeight) {
                    if (comparingHeight > anchorRect.top
                            || (displayMetrics.heightPixels - displayFrame.top) - anchorRect.bottom
                                    > this.mContentHeight) {
                        int posY7 = anchorRect.bottom;
                        hGravity = posY7 + 0;
                    } else {
                        hGravity = statusBarHeight;
                    }
                } else {
                    int posY8 = anchorRect.bottom + 0;
                    int posY9 = this.mPopupOffsetY;
                    hGravity =
                            comparingHeight - posY9 >= this.mContentHeight
                                    ? posY8 + this.mPopupOffsetY
                                    : posY8;
                }
            } else {
                hGravity = Math.max(statusBarHeight, hGravity);
            }
        }
        this.mPopupPosX = posX2;
        this.mPopupPosY = hGravity;
    }

    public void update() {
        if (!this.mNeedToMeasureContentView && this.mPopup != null && this.mPopup.isShowing()) {
            computePopupPositionInternal(this.mAnchorRect, this.mDisplayFrame);
            this.mPopup.update(this.mPopupPosX, this.mPopupPosY, -1, -1);
        } else {
            updateHoverPopup(
                    this.mAnchorView != null ? this.mAnchorView : this.mParentView,
                    this.mPopupGravity,
                    this.mPopupOffsetX,
                    this.mPopupOffsetY);
        }
    }

    private void updateHoverPopup(View anchor, int gravity, int offsetX, int offsetY) {
        if (this.mPopup == null) {
            Log.d(
                    TAG,
                    "updateHoverPopup(), returned due to mPopup == null  "
                            + this.mParentView.toString());
            return;
        }
        computePopupPosition(anchor, gravity, offsetX, offsetY);
        if (this.mContentWidth == 0 && this.mContentHeight == 0) {
            return;
        }
        if (this.mIsPopupTouchable && this.mTouchableContainer != null) {
            this.mPopup.setContentView(this.mTouchableContainer);
        } else {
            this.mPopup.setContentView(this.mContentView);
        }
        if (this.mPopup.getContentView() == null) {
            return;
        }
        if (this.mPopup.isShowing()) {
            this.mPopup.update(
                    this.mPopupPosX, this.mPopupPosY, this.mContentWidth, this.mContentHeight);
            return;
        }
        IBinder binder = anchor.getApplicationWindowToken();
        if (binder == null || binder == anchor.getWindowToken()) {
            this.mPopup.showAtLocation(anchor, 0, this.mPopupPosX, this.mPopupPosY);
        } else {
            this.mPopup.showAtLocation(binder, 0, this.mPopupPosX, this.mPopupPosY);
        }
    }

    public void setAnimationStyle(int aniStyle) {
        this.mAnimationStyle = aniStyle;
        if (this.mPopup != null) {
            this.mPopup.setAnimationStyle(this.mAnimationStyle);
        }
    }

    public void setTouchable(boolean isTouchable) {
        this.mIsPopupTouchable = isTouchable;
        if (this.mPopup != null) {
            this.mPopup.setTouchable(this.mIsPopupTouchable);
        }
    }

    public boolean onHoverEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        long timeDelay = SystemClock.uptimeMillis() - event.getEventTime();
        switch (action) {
            case 7:
                setHoveringPoint((int) event.getRawX(), (int) event.getRawY());
                if (this.mIsHoverPaddingEnabled) {
                    boolean isPointInValidHoverArea = pointInValidHoverArea(x, y);
                    if (isPointInValidHoverArea && !this.mIsTryingShowPopup) {
                        if (timeDelay > 1000) {
                            this.mIsTryingShowPopup = false;
                            return true;
                        }
                        this.mIsTryingShowPopup = true;
                        show();
                        return true;
                    }
                    if (!isPointInValidHoverArea
                            && this.mIsTryingShowPopup
                            && !this.mIsPopupTouchable) {
                        this.mIsTryingShowPopup = false;
                        dismiss();
                        return true;
                    }
                }
                if (this.mToolType != 3) {
                    resetTimeout();
                }
                return true;
            case 8:
            default:
                return false;
            case 9:
                if (timeDelay > 1000) {
                    return true;
                }
                if (this.mIsHoverPaddingEnabled) {
                    this.mIsTryingShowPopup = pointInValidHoverArea(x, y);
                }
                return false;
            case 10:
                if (this.mIsPopupTouchable) {
                    if (this.mDismissHandler != null && this.mDismissHandler.hasMessages(1)) {
                        this.mDismissHandler.removeMessages(1);
                    }
                    if (isShowing()) {
                        return true;
                    }
                }
                return false;
        }
    }

    protected void postDismiss(int ms) {
        this.mParentView.postDelayed(
                new Runnable() { // from class:
                                 // com.samsung.android.widget.SemHoverPopupWindow$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SemHoverPopupWindow.this.dismiss();
                    }
                },
                ms);
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
        if (this.mPopup != null) {
            this.mPopup.dismiss();
            this.mPopup = null;
            this.mIsCheckedRealDisplayMetricsInDexMode = false;
        }
    }

    protected void showPenPointEffect(boolean show) {
        if (this.mToolType == 2) {
            if (show) {
                PointerIcon pointerIcon = PointerIcon.getSystemIcon(this.mContext, 20010);
                this.mParentView.semSetPointerIcon(2, pointerIcon);
                this.mIsSPenPointChanged = true;
            } else if (this.mIsSPenPointChanged) {
                this.mParentView.semSetPointerIcon(2, null);
                this.mIsSPenPointChanged = false;
            }
        }
    }

    private boolean pointInValidHoverArea(float localX, float localY) {
        return localX >= ((float) this.mHoverPaddingLeft)
                && localX
                        < ((float)
                                ((this.mParentView.getRight() - this.mParentView.getLeft())
                                        - this.mHoverPaddingRight))
                && localY >= ((float) this.mHoverPaddingTop)
                && localY
                        < ((float)
                                ((this.mParentView.getBottom() - this.mParentView.getTop())
                                        - this.mHoverPaddingBottom));
    }

    private int getStateHashCode() {
        int hashCode = this.mPopupType;
        if (this.mParentView != null) {
            int hashCode2 =
                    hashCode
                            | (this.mParentView.getWindowVisibility() << 1)
                            | (this.mParentView.getVisibility() << 2)
                            | (this.mParentView.getLeft() << 4)
                            | (this.mParentView.getRight() << 8)
                            | (this.mParentView.getTop() << 12)
                            | (this.mParentView.getBottom() << 16);
            int[] location = new int[2];
            this.mParentView.getLocationOnScreen(location);
            return hashCode2 | (location[1] << 24) | (location[0] << 20);
        }
        return hashCode;
    }

    private void resetTimeout() {
        if (this.mDismissHandler != null) {
            if (this.mDismissHandler.hasMessages(1)) {
                this.mDismissHandler.removeMessages(1);
            }
            this.mDismissHandler.sendMessageDelayed(this.mDismissHandler.obtainMessage(1), 2000L);
        }
    }

    private int getNavigationBarHeight() {
        boolean hasNavigationBar = this.mResources.getBoolean(R.bool.config_showNavigationBar);
        if (!hasNavigationBar) {
            return 0;
        }
        int navigationBarHeight =
                this.mResources.getDimensionPixelSize(R.dimen.navigation_bar_height);
        return navigationBarHeight;
    }

    private int getDeviceRotation() {
        WindowManager wm = (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getRotation();
    }

    private boolean isPopOver() {
        boolean isPopOver = this.mContext.getResources().getConfiguration().semIsPopOver();
        return isPopOver;
    }

    private boolean isEmbeddedMode() {
        int isActivityInEmbeddingState =
                this.mContext
                        .getResources()
                        .getConfiguration()
                        .windowConfiguration
                        .getEmbedActivityMode();
        return isActivityInEmbeddingState != 0;
    }

    private boolean isAnchorViewInAppBounds(int x, int y) {
        Rect appBounds =
                this.mContext.getResources().getConfiguration().windowConfiguration.getAppBounds();
        return appBounds.contains(x, y);
    }

    public static class QuintEaseOut implements Interpolator {
        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float input) {
            float input2 = (input / 1.0f) - 1.0f;
            return (input2 * input2 * input2 * input2 * input2) + 1.0f;
        }
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
            this.mContainerDismissHandler =
                    new Handler(this.mContext.getMainLooper()) { // from class:
                        // com.samsung.android.widget.SemHoverPopupWindow.TouchablePopupContainer.1
                        @Override // android.os.Handler
                        public void handleMessage(Message msg) {
                            Log.d(
                                    SemHoverPopupWindow.TAG,
                                    "TouchablePopupContainer: ***** mContainerDismissHandler"
                                        + " handleMessage *****");
                            if (SemHoverPopupWindow.this.mPopup != null
                                    && SemHoverPopupWindow.this.mPopup.isShowing()
                                    && msg.what == 1) {
                                Log.d(
                                        SemHoverPopupWindow.TAG,
                                        "TouchablePopupContainer: mContainerDismissHandler"
                                            + " handleMessage: Call dismiss");
                                SemHoverPopupWindow.this.dismiss();
                            }
                        }
                    };
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent event) {
            if (this.mIsHoverExitCalled && this.mDismissPopupRunnable != null) {
                removeCallbacks(this.mDismissPopupRunnable);
                this.mDismissPopupRunnable = null;
                this.mIsHoverExitCalled = false;
            }
            boolean superRet = super.dispatchTouchEvent(event);
            if (event.getAction() == 1 && SemHoverPopupWindow.this.mDismissTouchableHPWOnActionUp) {
                postDelayed(
                        new Runnable() { // from class:
                                         // com.samsung.android.widget.SemHoverPopupWindow.TouchablePopupContainer.2
                            @Override // java.lang.Runnable
                            public void run() {
                                SemHoverPopupWindow.this.dismiss();
                            }
                        },
                        100L);
            }
            return superRet;
        }

        @Override // android.view.ViewGroup, android.view.View
        protected boolean dispatchHoverEvent(MotionEvent event) {
            int action = event.getAction();
            switch (action) {
                case 7:
                    if (SemHoverPopupWindow.this.mToolType != 3) {
                        resetTimeout();
                        break;
                    }
                    break;
                case 10:
                    if (pointInView(event.getX(), event.getY(), -2.0f)) {
                        this.mIsHoverExitCalled = true;
                        this.mDismissPopupRunnable =
                                new Runnable() { // from class:
                                                 // com.samsung.android.widget.SemHoverPopupWindow.TouchablePopupContainer.3
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        SemHoverPopupWindow.this.dismiss();
                                    }
                                };
                        postDelayed(this.mDismissPopupRunnable, 100L);
                        break;
                    } else {
                        boolean superRet = super.dispatchHoverEvent(event);
                        SemHoverPopupWindow.this.dismiss();
                        return superRet;
                    }
            }
            return super.dispatchHoverEvent(event);
        }

        public void resetTimeout() {
            if (this.mContainerDismissHandler != null) {
                if (this.mContainerDismissHandler.hasMessages(1)) {
                    this.mContainerDismissHandler.removeMessages(1);
                }
                this.mContainerDismissHandler.sendMessageDelayed(
                        this.mContainerDismissHandler.obtainMessage(1), 2000L);
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

        private Gravity() {}
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

    private void hidden_setGravity(int gravity) {
        setGravity(gravity);
    }

    private void hidden_setHoverDetectTime(int ms) {
        setHoverDetectTime(ms);
    }

    private void hidden_setOffset(int x, int y) {
        setOffset(x, y);
    }

    private void hidden_update() {
        update();
    }
}
