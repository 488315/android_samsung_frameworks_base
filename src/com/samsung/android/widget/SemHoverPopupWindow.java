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

    private boolean isMouseHoveringSettingsEnabled() {
        return false;
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
            ICoverManager asInterface = ICoverManager.Stub.asInterface(ServiceManager.getService(UnionConstants.SERVICE_COVER));
            this.mCoverManager = asInterface;
            if (asInterface == null) {
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
        boolean z;
        ICoverManager iCoverManager;
        CoverState coverState;
        try {
            iCoverManager = this.mCoverManager;
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in getCoverState: ", e);
        }
        if (iCoverManager != null && (coverState = iCoverManager.getCoverState()) != null) {
            z = coverState.getSwitchState();
            return !z;
        }
        z = true;
        return !z;
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
        int semGetHoverPopupType = view.semGetHoverPopupType();
        if (semGetHoverPopupType != this.mPopupType) {
            this.mPopupType = semGetHoverPopupType;
            setInstanceByType(semGetHoverPopupType);
        }
        HoverPopupPreShowListener hoverPopupPreShowListener = this.mPreShowListener;
        if ((hoverPopupPreShowListener != null && !hoverPopupPreShowListener.onHoverPopupPreShow()) || !this.mEnabled || semGetHoverPopupType == 0 || semGetHoverPopupType == 1 || this.mIsShowMessageSent) {
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
                SemHoverPopupWindow.this.showPopup();
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
        if (i == 0) {
            this.mContentView = null;
        } else if (i == 1) {
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
        int makeMeasureSpec;
        int makeMeasureSpec2;
        if (this.mContentView == null) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = this.mContentLP;
        if (layoutParams == null) {
            makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, Integer.MIN_VALUE);
            makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels, Integer.MIN_VALUE);
        } else {
            if (layoutParams.width < 0) {
                makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, Integer.MIN_VALUE);
            } else {
                makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mContentLP.width, 1073741824);
            }
            if (this.mContentLP.height < 0) {
                makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels, Integer.MIN_VALUE);
            } else {
                makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(this.mContentLP.height, 1073741824);
            }
        }
        this.mContentView.measure(makeMeasureSpec, makeMeasureSpec2);
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

    /* JADX WARN: Code restructure failed: missing block: B:101:0x01e8, code lost:
    
        if ((r13.top + r18[r23]) != r2[r23]) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x01bd, code lost:
    
        if (r2.bottom == 10000) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x01d4, code lost:
    
        if ((r2.bottom - r2.top) > r12.heightPixels) goto L66;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void computePopupPosition(android.view.View r22, int r23, int r24, int r25) {
        /*
            Method dump skipped, instructions count: 906
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.widget.SemHoverPopupWindow.computePopupPosition(android.view.View, int, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:114:0x031c, code lost:
    
        if (r18.top == r11) goto L202;
     */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0326  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x032d  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0329  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x01b5  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x005d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void computePopupPositionInternal(android.graphics.Rect r17, android.graphics.Rect r18) {
        /*
            Method dump skipped, instructions count: 837
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.widget.SemHoverPopupWindow.computePopupPositionInternal(android.graphics.Rect, android.graphics.Rect):void");
    }

    public void update() {
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

    private void updateHoverPopup(View view, int i, int i2, int i3) {
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
        long uptimeMillis = SystemClock.uptimeMillis() - motionEvent.getEventTime();
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
                if (uptimeMillis > 1000) {
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
            boolean pointInValidHoverArea = pointInValidHoverArea(x, y);
            if (pointInValidHoverArea && !this.mIsTryingShowPopup) {
                if (uptimeMillis > 1000) {
                    this.mIsTryingShowPopup = false;
                    return true;
                }
                this.mIsTryingShowPopup = true;
                show();
                return true;
            }
            if (!pointInValidHoverArea && this.mIsTryingShowPopup && !this.mIsPopupTouchable) {
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
                SemHoverPopupWindow.this.dismiss();
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
            boolean dispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
            if (motionEvent.getAction() == 1 && SemHoverPopupWindow.this.mDismissTouchableHPWOnActionUp) {
                postDelayed(new Runnable() { // from class: com.samsung.android.widget.SemHoverPopupWindow.TouchablePopupContainer.2
                    @Override // java.lang.Runnable
                    public void run() {
                        SemHoverPopupWindow.this.dismiss();
                    }
                }, 100L);
            }
            return dispatchTouchEvent;
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
                        boolean dispatchHoverEvent = super.dispatchHoverEvent(motionEvent);
                        SemHoverPopupWindow.this.dismiss();
                        return dispatchHoverEvent;
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

    private void hidden_update() {
        update();
    }
}
