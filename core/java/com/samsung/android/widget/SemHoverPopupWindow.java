package com.samsung.android.widget;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.hardware.input.InputManager;
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
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import com.android.internal.C4337R;
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
                .getMainLooper()) { // from class: com.samsung.android.widget.SemHoverPopupWindow.1
          @Override // android.os.Handler
          public void handleMessage(Message msg) {
            if (SemHoverPopupWindow.this.mPopup != null
                && SemHoverPopupWindow.this.mPopup.isShowing()
                && msg.what == 1) {
              Log.m94d(SemHoverPopupWindow.TAG, "mDismissHandler handleMessage: Call dismiss");
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
    this.mAnimationStyle = C4337R.style.Animation_HoverPopup;
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
      ICoverManager asInterface =
          ICoverManager.Stub.asInterface(ServiceManager.getService(UnionConstants.SERVICE_COVER));
      this.mCoverManager = asInterface;
      if (asInterface == null) {
        Log.m96e(TAG, "warning: no COVER_MANAGER_SERVICE");
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
        this.mContext.getResources().getConfiguration().windowConfiguration.getWindowingMode();
    return windowMode == 5;
  }

  public boolean isFullscreenMode() {
    int windowMode =
        this.mContext.getResources().getConfiguration().windowConfiguration.getWindowingMode();
    return windowMode == 1;
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
      Log.m94d(TAG, "getApplicationContext() is null");
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
          Log.m94d(
              TAG,
              "getRealDisplayMetrics :sRealDisplayMetricsInDexMode width:" + displayInfo.appWidth);
          Log.m94d(
              TAG,
              "getRealDisplayMetrics :sRealDisplayMetricsInDexMode height:"
                  + displayInfo.appHeight);
        }
        DisplayMetrics displayMetrics = sRealDisplayMetricsInDexMode;
        displayMetrics.widthPixels = displayInfo.appWidth;
        displayMetrics.heightPixels = displayInfo.appHeight;
      }
      return sRealDisplayMetricsInDexMode;
    }
    if (localLOGV) {
      Log.m94d(TAG, "getRealDisplayMetrics :displaySize width:" + displaySize.widthPixels);
      Log.m94d(TAG, "getRealDisplayMetrics :displaySize height:" + displaySize.heightPixels);
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
    View view = this.mParentView;
    if (view != null) {
      return view.semIsDesktopMode();
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
      ICoverManager iCoverManager = this.mCoverManager;
      if (iCoverManager != null && (coverState = iCoverManager.getCoverState()) != null) {
        isCoverOpen = coverState.getSwitchState();
      }
    } catch (RemoteException e) {
      Log.m97e(TAG, "RemoteException in getCoverState: ", e);
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
    PopupWindow popupWindow = this.mPopup;
    return popupWindow != null && popupWindow.isShowing();
  }

  public void setHoverDetectTime(int ms) {
    this.mHoverDetectTimeMS = ms;
  }

  public void setHoverPaddingArea(int left, int top, int right, int bottom) {
    this.mHoverPaddingLeft = left;
    this.mHoverPaddingRight = right;
    this.mHoverPaddingTop = top;
    this.mHoverPaddingBottom = bottom;
    if (left != 0 || right != 0 || top != 0 || bottom != 0) {
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
      Log.m94d(TAG, "show :" + this.mParentView.toString());
    }
    View anchorView = this.mAnchorView;
    if (anchorView == null) {
      anchorView = this.mParentView;
    }
    int type = anchorView.semGetHoverPopupType();
    if (type != this.mPopupType) {
      this.mPopupType = type;
      setInstanceByType(type);
    }
    HoverPopupPreShowListener hoverPopupPreShowListener = this.mPreShowListener;
    if ((hoverPopupPreShowListener == null || hoverPopupPreShowListener.onHoverPopupPreShow())
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
      Runnable runnable =
          new Runnable() { // from class:
                           // com.samsung.android.widget.SemHoverPopupWindow$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              SemHoverPopupWindow.this.showPopup();
            }
          };
      this.mShowPopupRunnable = runnable;
      this.mParentView.postDelayed(runnable, this.mHoverDetectTimeMS);
      this.mIsShowMessageSent = true;
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void showPopup() {
    try {
      if (this.mHashCodeForViewState != getStateHashCode()) {
        Log.m94d(
            TAG,
            "showPopup() is cancelled : " + this.mHashCodeForViewState + " " + getStateHashCode());
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
      PopupWindow popupWindow = this.mPopup;
      if (popupWindow != null) {
        popupWindow.dismiss();
      }
      createPopupWindow();
      setPopupContent();
      update();
    } catch (Exception e) {
      Log.m98i(TAG, "Fail show hover popup :" + e);
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
      View anchorView = this.mAnchorView;
      if (anchorView == null) {
        anchorView = this.mParentView;
      }
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
    OnSetContentViewListener onSetContentViewListener = this.mListener;
    if (onSetContentViewListener != null) {
      onSetContentViewListener.onSetContentView(this.mParentView, this);
    }
  }

  protected void makeDefaultContentView() {}

  private void measureContentView(DisplayMetrics displayMetrics) {
    int widthMeasureSpec;
    int heightMeasureSpec;
    if (this.mContentView == null) {
      return;
    }
    ViewGroup.LayoutParams layoutParams = this.mContentLP;
    if (layoutParams == null) {
      widthMeasureSpec =
          View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, Integer.MIN_VALUE);
      heightMeasureSpec =
          View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels, Integer.MIN_VALUE);
    } else {
      int widthMeasureSpec2 = layoutParams.width;
      if (widthMeasureSpec2 < 0) {
        widthMeasureSpec =
            View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, Integer.MIN_VALUE);
      } else {
        widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mContentLP.width, 1073741824);
      }
      if (this.mContentLP.height < 0) {
        heightMeasureSpec =
            View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels, Integer.MIN_VALUE);
      } else {
        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mContentLP.height, 1073741824);
      }
    }
    this.mContentView.measure(widthMeasureSpec, heightMeasureSpec);
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

  /* JADX WARN: Code restructure failed: missing block: B:104:0x0229, code lost:

     if ((r6.top + r3[1]) != r2[1]) goto L69;
  */
  /* JADX WARN: Code restructure failed: missing block: B:92:0x01f3, code lost:

     if (r8.bottom == 10000) goto L60;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  private void computePopupPosition(View anchor, int gravity, int offX, int offY) {
    boolean isHasIdentityMatrix;
    boolean isHasIdentityMatrix2;
    String str;
    String str2;
    int[] anchorLocInWindow;
    Rect anchorRect;
    if (this.mContentView == null) {
      return;
    }
    if (this.mNavigationBarHeight != 0) {
      this.mDeviceRotation = getDeviceRotation();
    }
    int[] anchorLocOnScr = new int[2];
    int[] anchorLocInWindow2 = new int[2];
    DisplayMetrics displayMetrics = this.mResources.getDisplayMetrics();
    this.mAnchorView = anchor == null ? this.mParentView : anchor;
    View anchorView = this.mAnchorView;
    this.mPopupGravity = gravity;
    this.mPopupOffsetX = offX;
    this.mPopupOffsetY = offY;
    anchorView.getLocationOnScreen(anchorLocOnScr);
    anchorView.getLocationInWindow(anchorLocInWindow2);
    if (localLOGV) {
      Log.m94d(TAG, "computePopupPosition :anchorLocOnScr x:" + anchorLocOnScr[0]);
      Log.m94d(TAG, "computePopupPosition :anchorLocOnScr y:" + anchorLocOnScr[1]);
      Log.m94d(TAG, "computePopupPosition :anchorLocInWindow x:" + anchorLocInWindow2[0]);
      Log.m94d(TAG, "computePopupPosition :anchorLocInWindow y:" + anchorLocInWindow2[1]);
    }
    boolean isHasIdentityMatrix3 = true;
    Rect realRect = new Rect();
    if (!anchorView.updateDisplayListIfDirty().hasIdentityMatrix()) {
      isHasIdentityMatrix2 = false;
    } else {
      ViewParent viewParent = anchorView.getParent();
      while (true) {
        isHasIdentityMatrix = isHasIdentityMatrix3;
        boolean isHasIdentityMatrix4 = viewParent instanceof View;
        if (!isHasIdentityMatrix4) {
          break;
        }
        View view = (View) viewParent;
        if (!view.updateDisplayListIfDirty().hasIdentityMatrix()) {
          isHasIdentityMatrix = false;
        }
        viewParent = view.getParent();
        isHasIdentityMatrix3 = isHasIdentityMatrix;
      }
      isHasIdentityMatrix2 = isHasIdentityMatrix;
    }
    if (!isHasIdentityMatrix2) {
      anchorView.getBoundsOnScreen(realRect);
      anchorLocInWindow2[0] = anchorLocInWindow2[0] + (realRect.left - anchorLocOnScr[0]);
      anchorLocInWindow2[1] = anchorLocInWindow2[1] + (realRect.top - anchorLocOnScr[1]);
      anchorLocOnScr[0] = realRect.left;
      anchorLocOnScr[1] = realRect.top;
    }
    Rect displayFrame = new Rect();
    anchorView.getWindowVisibleDisplayFrame(displayFrame);
    boolean z = localLOGV;
    if (z) {
      Log.m94d(TAG, "computePopupPosition :displayFrame left:" + displayFrame.left);
      Log.m94d(TAG, "computePopupPosition :displayFrame right:" + displayFrame.right);
      Log.m94d(TAG, "computePopupPosition :displayFrame top:" + displayFrame.top);
      Log.m94d(TAG, "computePopupPosition :displayFrame bottom:" + displayFrame.bottom);
    }
    Rect rootViewDisplayRect = new Rect();
    View rootView = this.mAnchorView.getRootView();
    int rootViewWidth = rootView.getWidth();
    int rootViewHeight = rootView.getHeight();
    rootViewDisplayRect.left = anchorLocOnScr[0] - anchorLocInWindow2[0];
    rootViewDisplayRect.right = rootViewDisplayRect.left + rootViewWidth;
    rootViewDisplayRect.top = anchorLocOnScr[1] - anchorLocInWindow2[1];
    rootViewDisplayRect.bottom = rootViewDisplayRect.top + rootViewHeight;
    if (displayFrame.left == rootViewDisplayRect.left
        && displayFrame.right == rootViewDisplayRect.right
        && displayFrame.top == rootViewDisplayRect.top
        && displayFrame.bottom == rootViewDisplayRect.bottom) {
      str = "computePopupPosition :anchorLocInWindow x:";
      str2 = "computePopupPosition :anchorLocOnScr y:";
    } else {
      Rect tempDisplayFrame = new Rect();
      View tempAnchorView = this.mAnchorView;
      tempAnchorView.getWindowVisibleDisplayFrame(tempDisplayFrame);
      DisplayMetrics realDisplayMetrics = getRealDisplayMetrics();
      boolean isFreeForm = isFreeFormMode();
      if (isFreeForm) {
        str = "computePopupPosition :anchorLocInWindow x:";
        str2 = "computePopupPosition :anchorLocOnScr y:";
      } else {
        if (isFreeForm) {
          str = "computePopupPosition :anchorLocInWindow x:";
          str2 = "computePopupPosition :anchorLocOnScr y:";
        } else {
          str = "computePopupPosition :anchorLocInWindow x:";
          str2 = "computePopupPosition :anchorLocOnScr y:";
          if (tempDisplayFrame.left == -10000) {
            if (tempDisplayFrame.right == 10000) {
              if (tempDisplayFrame.top == -10000) {}
            }
          }
        }
        if (isFreeForm
            || (tempDisplayFrame.right - tempDisplayFrame.left <= realDisplayMetrics.widthPixels
                && tempDisplayFrame.bottom - tempDisplayFrame.top
                    <= realDisplayMetrics.heightPixels)) {
          if (!isFreeForm) {
            if (displayFrame.left + anchorLocInWindow2[0] == anchorLocOnScr[0]) {}
          }
        }
      }
      displayFrame.left = rootViewDisplayRect.left;
      displayFrame.right = rootViewDisplayRect.right;
      displayFrame.top = rootViewDisplayRect.top;
      displayFrame.bottom = rootViewDisplayRect.bottom;
    }
    if (anchorView.getApplicationWindowToken() == anchorView.getWindowToken()) {
      this.mWindowGapX = anchorLocOnScr[0] - anchorLocInWindow2[0];
      this.mCoordinatesOfAnchorView = 1;
      if (isHasIdentityMatrix2) {
        anchorRect =
            new Rect(
                anchorLocInWindow2[0],
                anchorLocInWindow2[1],
                anchorLocInWindow2[0] + anchorView.getWidth(),
                anchorLocInWindow2[1] + anchorView.getHeight());
        anchorLocInWindow = anchorLocInWindow2;
      } else {
        anchorLocInWindow = anchorLocInWindow2;
        anchorRect =
            new Rect(
                realRect.left - displayFrame.left,
                realRect.top - displayFrame.top,
                realRect.right - displayFrame.left,
                realRect.bottom - displayFrame.top);
      }
    } else {
      anchorLocInWindow = anchorLocInWindow2;
      this.mCoordinatesOfAnchorView = 2;
      this.mWindowGapX = 0;
      if (isHasIdentityMatrix2) {
        anchorRect =
            new Rect(
                anchorLocOnScr[0],
                anchorLocOnScr[1],
                anchorLocOnScr[0] + anchorView.getWidth(),
                anchorLocOnScr[1] + anchorView.getHeight());
      } else {
        anchorRect =
            new Rect(anchorLocOnScr[0], anchorLocOnScr[1], realRect.right, realRect.bottom);
      }
      if (displayFrame.left < 0 && displayFrame.top < 0) {
        displayFrame.left = 0;
        displayFrame.right = displayMetrics.widthPixels;
        displayFrame.top = 0;
        displayFrame.bottom = displayMetrics.heightPixels;
      }
    }
    if (displayFrame.left < 0 && displayFrame.top < 0) {
      View root = this.mParentView.getRootView();
      ViewGroup.LayoutParams rootLayoutParams = root.getLayoutParams();
      if (rootLayoutParams instanceof WindowManager.LayoutParams) {
        WindowManager.LayoutParams wlp = (WindowManager.LayoutParams) rootLayoutParams;
        boolean isSystemUiVisible =
            ((wlp.systemUiVisibility | wlp.subtreeSystemUiVisibility) & 1028) == 0;
        int statusBarHeight = 0;
        if ((wlp.flags & 512) == 1 && isSystemUiVisible) {
          statusBarHeight = this.mResources.getDimensionPixelSize(C4337R.dimen.status_bar_height);
        }
        displayFrame.left = 0;
        displayFrame.top = statusBarHeight;
        displayFrame.right = displayMetrics.widthPixels;
        displayFrame.bottom = displayMetrics.heightPixels;
      }
    }
    if (z) {
      Log.m94d(TAG, "computePopupPosition: displayMetrics" + displayMetrics);
      Log.m94d(TAG, "computePopupPosition :anchorLocOnScr x:" + anchorLocOnScr[0]);
      Log.m94d(TAG, str2 + anchorLocOnScr[1]);
      Log.m94d(TAG, str + anchorLocInWindow[0]);
      Log.m94d(TAG, "computePopupPosition :anchorLocInWindow y:" + anchorLocInWindow[1]);
      Log.m94d(TAG, "computePopupPosition :displayFrame:" + displayFrame);
    }
    measureContentView(displayMetrics);
    computePopupPositionInternal(anchorRect, displayFrame);
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
        Log.m94d(TAG, "computePopupPosition: Call resetTimeout()");
        this.mTouchableContainer.resetTimeout();
      }
    }
  }

  private void computePopupPositionInternal(Rect anchorRect, Rect displayFrame) {
    int posX;
    int posX2;
    int posY;
    int hGravity;
    int bottomBarHeight;
    int posX3;
    int posX4;
    this.mAnchorRect = anchorRect;
    this.mDisplayFrame = displayFrame;
    int posX5 = this.mPopupOffsetX;
    int posY2 = this.mPopupOffsetY;
    int i = this.mPopupGravity;
    int hGravity2 = i & Gravity.HORIZONTAL_GRAVITY_MASK;
    int vGravity = i & Gravity.VERTICAL_GRAVITY_MASK;
    DisplayMetrics displayMetrics = this.mResources.getDisplayMetrics();
    DisplayMetrics realDisplayMetrics = getRealDisplayMetrics();
    View root = this.mParentView.getRootView();
    ViewGroup.LayoutParams vlp = root.getLayoutParams();
    boolean isSystemUiVisible = false;
    int fullTextXShift =
        this.mResources.getDimensionPixelSize(
            C4337R.dimen.sem_hover_fulltext_popup_left_right_shift);
    int statusBarHeight = this.mResources.getDimensionPixelSize(C4337R.dimen.status_bar_height);
    int realStatusBarHeight = 0;
    if (vlp instanceof WindowManager.LayoutParams) {
      WindowManager.LayoutParams wlp = (WindowManager.LayoutParams) vlp;
      isSystemUiVisible = ((wlp.systemUiVisibility | wlp.subtreeSystemUiVisibility) & 1028) == 0;
      realStatusBarHeight = isSystemUiVisible ? statusBarHeight : 0;
    }
    int displayFrameWidth = displayFrame.right - displayFrame.left;
    int displayFrameHeight = displayFrame.bottom - displayFrame.top;
    if (this.mPopupGravity == 0) {
      int i2 = this.mCoordinatesOfAnchorView;
      if (i2 == 2) {
        int posX6 = this.mPopupOffsetX + displayFrame.left;
        int i3 = this.mPopupOffsetY;
        int posX7 = displayFrame.top;
        hGravity = posX7 + i3;
        posX2 = posX6;
      } else if (i2 != 1) {
        posX2 = posX5;
        hGravity = posY2;
      } else {
        posX2 = this.mPopupOffsetX;
        hGravity = this.mPopupOffsetY;
      }
    } else {
      switch (hGravity2) {
        case 1:
          int posX8 = anchorRect.centerX();
          posX = posX8 - (this.mContentWidth / 2);
          break;
        case 3:
          posX = anchorRect.left;
          break;
        case 5:
          int posX9 = anchorRect.right;
          posX = posX9 - this.mContentWidth;
          break;
        case 257:
          int posX10 = displayFrame.centerX();
          posX = posX10 - (this.mContentWidth / 2);
          break;
        case 259:
          int posX11 = anchorRect.centerX();
          posX = posX11 - this.mContentWidth;
          break;
        case 261:
          posX = anchorRect.centerX();
          break;
        case 513:
          if (isPopOver()) {
            posX = (this.mHoveringPointX - displayFrame.left) - (this.mContentWidth / 2);
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
          int posX12 = anchorRect.left;
          posX = posX12 - this.mContentWidth;
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
          int posY3 = anchorRect.top;
          int hGravity5 = this.mContentHeight;
          posY = (posY3 - hGravity5) - 0;
          break;
        case Gravity.BOTTOM_UNDER /* 20560 */:
          int posY4 = anchorRect.bottom;
          posY = posY4 + 0;
          break;
        default:
          posY = this.mPopupOffsetY;
          break;
      }
      hGravity = this.mPopupOffsetY + posY;
    }
    int posY5 = this.mCoordinatesOfAnchorView;
    if (posY5 == 2) {
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
            Math.min(posX2, (realDisplayMetrics.widthPixels - this.mContentWidth) - fullTextXShift);
      }
      if (!localLOGV) {
        posX4 = posX2;
      } else {
        Log.m94d(
            TAG,
            "computePopupPositionInternal :realDisplayMetrics width:"
                + realDisplayMetrics.widthPixels);
        posX4 = posX2;
        Log.m94d(
            TAG,
            "computePopupPositionInternal :realDisplayMetrics height:"
                + realDisplayMetrics.heightPixels);
      }
      if (hGravity >= realStatusBarHeight) {
        posX2 = posX4;
      } else if (vGravity == 12336) {
        if (displayMetrics.heightPixels - anchorRect.bottom >= this.mContentHeight) {
          hGravity = anchorRect.bottom + this.mPopupOffsetY;
          posX2 = posX4;
        } else if (displayMetrics.heightPixels - anchorRect.bottom
            > anchorRect.top - realStatusBarHeight) {
          hGravity = anchorRect.bottom + 0;
          posX2 = posX4;
        } else {
          hGravity = realStatusBarHeight;
          posX2 = posX4;
        }
      } else {
        hGravity = Math.max(displayFrame.top, hGravity);
        posX2 = posX4;
      }
    } else if (posY5 == 1) {
      if (displayFrame.left + posX2 <= 0) {
        int posX13 = Math.min(posX2, displayFrameWidth - this.mContentWidth);
        if (this.mDeviceRotation == 3 && this.mNavigationBarHeight != 0) {
          int i4 = displayFrame.left + posX13;
          int i5 = this.mNavigationBarHeight;
          if (i4 < i5) {
            posX2 = Math.max(i5 + fullTextXShift, posX13);
          }
        }
        if (this.mDeviceRotation == 1
            && statusBarHeight != 0
            && displayFrame.left + posX13 < statusBarHeight) {
          posX2 = Math.max(statusBarHeight + fullTextXShift, posX13);
        } else {
          posX2 = Math.max((-displayFrame.left) + fullTextXShift, posX13);
        }
      } else if (!isPopOver()
          && !isEmbeddedMode()
          && isAnchorViewInAppBounds(this.mAnchorRect.left, this.mAnchorRect.top)
          && displayFrame.left + posX2 + this.mContentWidth >= realDisplayMetrics.widthPixels) {
        posX2 =
            Math.min(
                posX2,
                ((realDisplayMetrics.widthPixels - displayFrame.left) - this.mContentWidth)
                    - fullTextXShift);
      } else if (displayFrame.left >= 0) {
        int i6 = this.mContentWidth;
        if (displayFrameWidth < i6) {
          int i7 = displayFrame.left + displayFrameWidth;
          int i8 = this.mContentWidth;
          if (i7 - i8 >= 0) {
            posX2 = Math.min(posX2, displayFrameWidth - i8);
          }
        } else if (posX2 + i6 > displayFrameWidth) {
          if (displayFrameWidth >= i6 + fullTextXShift) {
            posX2 = Math.min(posX2, (displayFrameWidth - i6) - fullTextXShift);
          } else if (displayFrameWidth >= i6) {
            posX2 = Math.min(posX2, displayFrameWidth - i6);
          }
        } else {
          if (this.mDeviceRotation == 3 && this.mNavigationBarHeight != 0) {
            int i9 = displayFrame.left + posX2;
            int i10 = this.mNavigationBarHeight;
            if (i9 < i10) {
              posX2 = Math.max(posX2, i10 + fullTextXShift);
            }
          }
          posX2 = Math.max(posX2, fullTextXShift);
        }
      }
      if (displayFrame.top + hGravity >= statusBarHeight) {
        int posX14 = posX2;
        int displayFrameWidth2 = this.mContentHeight;
        if (hGravity + displayFrameWidth2 > displayFrameHeight) {
          if (vGravity == 20560) {
            if (anchorRect.top >= this.mContentHeight
                && ((displayFrame.top != statusBarHeight
                        || this.mContentHeight + hGravity > displayFrame.bottom)
                    && displayFrame.top + hGravity + this.mContentHeight
                        > displayMetrics.heightPixels)) {
              hGravity = (anchorRect.top - this.mContentHeight) - this.mPopupOffsetY;
              posX2 = posX14;
            }
            posX2 = posX14;
          } else if (displayFrame.top == realStatusBarHeight) {
            hGravity = Math.min(displayFrame.bottom - this.mContentHeight, hGravity);
            posX2 = posX14;
          } else if (this.mDeviceRotation == 0 && this.mNavigationBarHeight != 0) {
            hGravity = Math.min(realDisplayMetrics.heightPixels - this.mContentHeight, hGravity);
            posX2 = posX14;
          } else {
            hGravity = Math.min(displayFrameHeight - this.mContentHeight, hGravity);
            posX2 = posX14;
          }
        } else {
          if (vGravity == 12336) {
            if (hGravity < statusBarHeight
                && displayFrameWidth2 + hGravity + statusBarHeight > anchorRect.top
                && displayFrame.top + anchorRect.bottom < realDisplayMetrics.heightPixels) {
              hGravity = anchorRect.bottom + 0;
            }
          } else if (hGravity < statusBarHeight && displayFrame.top == statusBarHeight) {
            hGravity = statusBarHeight;
          }
          if (isFullscreenMode()) {
            if (isMouseHoveringSettingsEnabled()) {
              bottomBarHeight = this.mResources.getDimensionPixelSize(C4337R.dimen.task_bar_height);
            } else {
              bottomBarHeight = this.mNavigationBarHeight;
            }
            if (bottomBarHeight != 0
                && displayFrame.top + hGravity + this.mContentHeight
                    > realDisplayMetrics.heightPixels) {
              hGravity = (realDisplayMetrics.heightPixels - displayFrame.top) - this.mContentHeight;
              posX2 = posX14;
            }
          }
          posX2 = posX14;
        }
      } else if (vGravity == 12336) {
        int comparingHeight = (displayFrameHeight - anchorRect.bottom) - statusBarHeight;
        if (comparingHeight < this.mContentHeight) {
          posX3 = posX2;
          if (comparingHeight > anchorRect.top
              || (displayMetrics.heightPixels - displayFrame.top) - anchorRect.bottom
                  > this.mContentHeight) {
            int posY6 = anchorRect.bottom;
            hGravity = posY6 + 0;
          } else {
            hGravity = statusBarHeight;
          }
        } else {
          int posY7 = anchorRect.bottom + 0;
          int posY8 = this.mPopupOffsetY;
          int displayFrameWidth3 = comparingHeight - posY8;
          posX3 = posX2;
          int posX15 = this.mContentHeight;
          if (displayFrameWidth3 < posX15) {
            hGravity = posY7;
          } else {
            hGravity = posY7 + posY8;
          }
        }
        posX2 = posX3;
      } else {
        hGravity = Math.max(statusBarHeight, hGravity);
      }
    }
    this.mPopupPosX = posX2;
    this.mPopupPosY = hGravity;
  }

  public void update() {
    PopupWindow popupWindow;
    if (!this.mNeedToMeasureContentView
        && (popupWindow = this.mPopup) != null
        && popupWindow.isShowing()) {
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

  private void updateHoverPopup(View anchor, int gravity, int offsetX, int offsetY) {
    TouchablePopupContainer touchablePopupContainer;
    if (this.mPopup == null) {
      Log.m94d(
          TAG,
          "updateHoverPopup(), returned due to mPopup == null  " + this.mParentView.toString());
      return;
    }
    computePopupPosition(anchor, gravity, offsetX, offsetY);
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
    IBinder binder = anchor.getApplicationWindowToken();
    if (binder == null || binder == anchor.getWindowToken()) {
      this.mPopup.showAtLocation(anchor, 0, this.mPopupPosX, this.mPopupPosY);
    } else {
      this.mPopup.showAtLocation(binder, 0, this.mPopupPosX, this.mPopupPosY);
    }
  }

  public void setAnimationStyle(int aniStyle) {
    this.mAnimationStyle = aniStyle;
    PopupWindow popupWindow = this.mPopup;
    if (popupWindow != null) {
      popupWindow.setAnimationStyle(aniStyle);
    }
  }

  public void setTouchable(boolean isTouchable) {
    this.mIsPopupTouchable = isTouchable;
    PopupWindow popupWindow = this.mPopup;
    if (popupWindow != null) {
      popupWindow.setTouchable(isTouchable);
    }
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
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
          if (!isPointInValidHoverArea && this.mIsTryingShowPopup && !this.mIsPopupTouchable) {
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
          Handler handler = this.mDismissHandler;
          if (handler != null && handler.hasMessages(1)) {
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
                         // com.samsung.android.widget.SemHoverPopupWindow$$ExternalSyntheticLambda1
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
    PopupWindow popupWindow = this.mPopup;
    if (popupWindow != null) {
      popupWindow.dismiss();
      this.mPopup = null;
      this.mIsCheckedRealDisplayMetricsInDexMode = false;
    }
  }

  protected void showPenPointEffect(boolean show) {
    if (this.mToolType == 2) {
      if (show) {
        InputManager.getInstance().setPointerIconType(20010);
        this.mIsSPenPointChanged = true;
      } else if (this.mIsSPenPointChanged) {
        InputManager.getInstance().setPointerIconType(20001);
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
    View view = this.mParentView;
    if (view != null) {
      int hashCode2 =
          hashCode
              | (view.getWindowVisibility() << 1)
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
    boolean hasNavigationBar = this.mResources.getBoolean(C4337R.bool.config_showNavigationBar);
    if (!hasNavigationBar) {
      return 0;
    }
    int navigationBarHeight =
        this.mResources.getDimensionPixelSize(C4337R.dimen.navigation_bar_height);
    return navigationBarHeight;
  }

  private int getDeviceRotation() {
    WindowManager wm = (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
    return wm.getDefaultDisplay().getRotation();
  }

  private boolean isPopOver() {
    boolean isPopOver =
        this.mContext.getResources().getConfiguration().windowConfiguration.isPopOver();
    return isPopOver;
  }

  private boolean isEmbeddedMode() {
    int isActivityInEmbeddingState =
        this.mContext.getResources().getConfiguration().windowConfiguration.getEmbedActivityMode();
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
              Log.m94d(
                  SemHoverPopupWindow.TAG,
                  "TouchablePopupContainer: ***** mContainerDismissHandler handleMessage *****");
              if (SemHoverPopupWindow.this.mPopup != null
                  && SemHoverPopupWindow.this.mPopup.isShowing()
                  && msg.what == 1) {
                Log.m94d(
                    SemHoverPopupWindow.TAG,
                    "TouchablePopupContainer: mContainerDismissHandler handleMessage: Call"
                        + " dismiss");
                SemHoverPopupWindow.this.dismiss();
              }
            }
          };
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent event) {
      Runnable runnable;
      if (this.mIsHoverExitCalled && (runnable = this.mDismissPopupRunnable) != null) {
        removeCallbacks(runnable);
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
            Runnable runnable =
                new Runnable() { // from class:
                                 // com.samsung.android.widget.SemHoverPopupWindow.TouchablePopupContainer.3
                  @Override // java.lang.Runnable
                  public void run() {
                    SemHoverPopupWindow.this.dismiss();
                  }
                };
            this.mDismissPopupRunnable = runnable;
            postDelayed(runnable, 100L);
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
