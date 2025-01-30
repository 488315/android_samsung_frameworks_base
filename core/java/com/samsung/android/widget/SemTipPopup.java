package com.samsung.android.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.telecom.TelecomManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ElasticCustom;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.android.internal.C4337R;
import com.samsung.android.wallpaperbackup.BnRConstants;

/* loaded from: classes6.dex */
public class SemTipPopup {
  private static final int ANIMATION_DURATION_BOUNCE_SCALE1 = 167;
  private static final int ANIMATION_DURATION_BOUNCE_SCALE2 = 250;
  private static final int ANIMATION_DURATION_DISMISS_ALPHA = 167;
  private static final int ANIMATION_DURATION_DISMISS_SCALE = 167;
  private static final int ANIMATION_DURATION_EXPAND_ALPHA = 83;
  private static final int ANIMATION_DURATION_EXPAND_SCALE = 500;
  private static final int ANIMATION_DURATION_EXPAND_TEXT = 167;
  private static final int ANIMATION_DURATION_SHOW_SCALE = 500;
  private static final int ANIMATION_OFFSET_BOUNCE_SCALE = 3000;
  private static final int ANIMATION_OFFSET_EXPAND_TEXT = 333;
  public static final int DIRECTION_BOTTOM_LEFT = 2;
  public static final int DIRECTION_BOTTOM_RIGHT = 3;
  public static final int DIRECTION_DEFAULT = -1;
  public static final int DIRECTION_TOP_LEFT = 0;
  public static final int DIRECTION_TOP_RIGHT = 1;
  public static final int MODE_NORMAL = 0;
  public static final int MODE_TRANSLUCENT = 1;
  private static final int MSG_DISMISS = 1;
  private static final int MSG_SCALE_UP = 2;
  private static final int MSG_TIMEOUT = 0;
  public static final int STATE_DISMISSED = 0;
  public static final int STATE_EXPANDED = 2;
  public static final int STATE_HINT = 1;
  private static final String TAG = "SemTipPopup";
  private static final int TIMEOUT_DURATION_MS = 7100;
  private static final int TYPE_BALLOON_ACTION = 1;
  private static final int TYPE_BALLOON_CUSTOM = 2;
  private static final int TYPE_BALLOON_SIMPLE = 0;
  private static final boolean localLOGD = true;
  private static Handler mHandler;
  private View.OnClickListener mActionClickListener;
  private CharSequence mActionText;
  private Integer mActionTextColor;
  private final Button mActionView;
  private int mArrowDirection;
  private final int mArrowHeight;
  private int mArrowPositionX;
  private int mArrowPositionY;
  private final int mArrowWidth;
  private int mBackgroundColor;
  private ImageView mBalloonBg1;
  private ImageView mBalloonBg2;
  private FrameLayout mBalloonBubble;
  private ImageView mBalloonBubbleHint;
  private ImageView mBalloonBubbleIcon;
  private FrameLayout mBalloonContent;
  private int mBalloonHeight;
  private FrameLayout mBalloonPanel;
  private TipWindow mBalloonPopup;
  private int mBalloonPopupX;
  private int mBalloonPopupY;
  private final View mBalloonView;
  private int mBalloonWidth;
  private int mBalloonX;
  private int mBalloonY;
  private Integer mBorderColor;
  private ImageView mBubbleBackground;
  private int mBubbleHeight;
  private ImageView mBubbleIcon;
  private TipWindow mBubblePopup;
  private int mBubblePopupX;
  private int mBubblePopupY;
  private final View mBubbleView;
  private int mBubbleWidth;
  private int mBubbleX;
  private int mBubbleY;
  private final Context mContext;
  private final Rect mDisplayFrame;
  private DisplayMetrics mDisplayMetrics;
  private boolean mForceRealDisplay;
  private CharSequence mHintDescription;
  private final int mHorizontalTextMargin;
  private int mInitialmMessageViewWidth;
  private boolean mIsDefaultPosition;
  private boolean mIsMessageViewMeasured;
  private CharSequence mMessageText;
  private Integer mMessageTextColor;
  private final TextView mMessageView;
  private final int mMode;
  private boolean mNeedToCallParentViewsOnClick;
  private OnDismissListener mOnDismissListener;
  private OnStateChangeListener mOnStateChangeListener;
  private final View mParentView;
  private final Resources mResources;
  private int mScaleMargin;
  private int mSideMargin;
  private int mState;
  private int mType;
  private final int mVerticalTextMargin;
  private final WindowManager mWindowManager;
  private static Interpolator INTERPOLATOR_SINE_IN_OUT_33 = null;
  private static Interpolator INTERPOLATOR_SINE_IN_OUT_70 = null;
  private static Interpolator INTERPOLATOR_ELASTIC_50 = null;
  private static Interpolator INTERPOLATOR_ELASTIC_CUSTOM = null;

  public interface OnDismissListener {
    void onDismiss();
  }

  public interface OnStateChangeListener {
    void onStateChanged(int i);
  }

  public void setOnStateChangeListener(OnStateChangeListener changeListener) {
    this.mOnStateChangeListener = changeListener;
  }

  public SemTipPopup(View parentView) {
    this(parentView, 0);
  }

  public SemTipPopup(View parentView, int mode) {
    this.mIsDefaultPosition = true;
    this.mMessageText = null;
    this.mActionText = null;
    this.mHintDescription = null;
    this.mActionClickListener = null;
    this.mMessageTextColor = null;
    this.mActionTextColor = null;
    this.mBorderColor = null;
    this.mInitialmMessageViewWidth = 0;
    this.mIsMessageViewMeasured = false;
    this.mForceRealDisplay = false;
    this.mNeedToCallParentViewsOnClick = false;
    if (mode < 0 || mode > 1) {
      throw new IllegalArgumentException(
          "Invalid SmartTip mode : "
              + mode
              + " ,mode can either be 0 (MODE_NORMAL) or 1 (MODE_TRANSLUCENT)");
    }
    Context context = parentView.getContext();
    this.mContext = context;
    Resources resources = context.getResources();
    this.mResources = resources;
    this.mParentView = parentView;
    this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    this.mDisplayMetrics = resources.getDisplayMetrics();
    debugLog("mDisplayMetrics = " + this.mDisplayMetrics);
    this.mState = 1;
    this.mType = 0;
    this.mMode = mode;
    TypedArray a =
        context.obtainStyledAttributes((AttributeSet) null, C4337R.styleable.SemTipPopup);
    this.mBackgroundColor = a.getColor(0, -16777216);
    a.recycle();
    initInterpolator();
    LayoutInflater inflater = LayoutInflater.from(context);
    this.mBubbleView = inflater.inflate(C4337R.layout.sem_tip_popup_bubble, (ViewGroup) null);
    View inflate = inflater.inflate(C4337R.layout.sem_tip_popup_balloon, (ViewGroup) null);
    this.mBalloonView = inflate;
    initBubblePopup(mode);
    initBalloonPopup(mode);
    TextView textView = (TextView) inflate.findViewById(C4337R.id.sem_tip_popup_message);
    this.mMessageView = textView;
    Button button = (Button) inflate.findViewById(C4337R.id.sem_tip_popup_action);
    this.mActionView = button;
    textView.setVisibility(8);
    button.setVisibility(8);
    this.mArrowPositionX = -1;
    this.mArrowPositionY = -1;
    this.mArrowDirection = -1;
    this.mBalloonX = -1;
    if (mode == 1) {
      textView.setTextColor(
          resources.getColor(C4337R.color.sem_tip_popup_text_color_translucent, null));
      button.setTextColor(
          resources.getColor(C4337R.color.sem_tip_popup_text_color_translucent, null));
    }
    this.mScaleMargin = resources.getDimensionPixelSize(C4337R.dimen.sem_tip_popup_scale_margin);
    this.mSideMargin = resources.getDimensionPixelSize(C4337R.dimen.sem_tip_popup_side_margin);
    this.mArrowHeight =
        resources.getDimensionPixelSize(C4337R.dimen.sem_tip_popup_balloon_arrow_height);
    this.mArrowWidth =
        resources.getDimensionPixelSize(C4337R.dimen.sem_tip_popup_balloon_arrow_width);
    this.mHorizontalTextMargin =
        resources.getDimensionPixelSize(
            C4337R.dimen.sem_tip_popup_balloon_message_margin_horizontal);
    this.mVerticalTextMargin =
        resources.getDimensionPixelSize(C4337R.dimen.sem_tip_popup_balloon_message_margin_vertical);
    this.mDisplayFrame = new Rect();
    this.mBubblePopup.setOnDismissListener(
        new PopupWindow
            .OnDismissListener() { // from class: com.samsung.android.widget.SemTipPopup.1
          @Override // android.widget.PopupWindow.OnDismissListener
          public void onDismiss() {
            if (SemTipPopup.this.mState == 1) {
              SemTipPopup.this.mState = 0;
              if (SemTipPopup.this.mOnStateChangeListener != null) {
                SemTipPopup.this.mOnStateChangeListener.onStateChanged(SemTipPopup.this.mState);
                SemTipPopup.this.debugLog("mIsShowing : " + SemTipPopup.this.isShowing());
              }
              if (SemTipPopup.mHandler != null) {
                SemTipPopup.mHandler.removeCallbacksAndMessages(null);
                SemTipPopup.mHandler = null;
              }
              SemTipPopup.this.debugLog("onDismiss - BubblePopup");
            }
          }
        });
    this.mBalloonPopup.setOnDismissListener(
        new PopupWindow
            .OnDismissListener() { // from class: com.samsung.android.widget.SemTipPopup.2
          @Override // android.widget.PopupWindow.OnDismissListener
          public void onDismiss() {
            SemTipPopup.this.mState = 0;
            if (SemTipPopup.this.mOnStateChangeListener != null) {
              SemTipPopup.this.mOnStateChangeListener.onStateChanged(SemTipPopup.this.mState);
              SemTipPopup.this.debugLog("mIsShowing : " + SemTipPopup.this.isShowing());
            }
            SemTipPopup.this.debugLog("onDismiss - BalloonPopup");
            SemTipPopup.this.dismissBubble(false);
            if (SemTipPopup.mHandler != null) {
              SemTipPopup.mHandler.removeCallbacksAndMessages(null);
              SemTipPopup.mHandler = null;
            }
          }
        });
    inflate.setAccessibilityDelegate(
        new View.AccessibilityDelegate() { // from class: com.samsung.android.widget.SemTipPopup.3
          @Override // android.view.View.AccessibilityDelegate
          public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            info.addAction(
                new AccessibilityNodeInfo.AccessibilityAction(
                    16, SemTipPopup.this.mContext.getString(C4337R.string.close)));
          }
        });
  }

  private void initInterpolator() {
    if (INTERPOLATOR_SINE_IN_OUT_33 == null) {
      INTERPOLATOR_SINE_IN_OUT_33 =
          AnimationUtils.loadInterpolator(this.mContext, C4337R.interpolator.sine_in_out_33);
    }
    if (INTERPOLATOR_SINE_IN_OUT_70 == null) {
      INTERPOLATOR_SINE_IN_OUT_70 =
          AnimationUtils.loadInterpolator(this.mContext, C4337R.interpolator.sine_in_out_70);
    }
    if (INTERPOLATOR_ELASTIC_50 == null) {
      INTERPOLATOR_ELASTIC_50 = new ElasticCustom(1.0f, 0.7f);
    }
    if (INTERPOLATOR_ELASTIC_CUSTOM == null) {
      INTERPOLATOR_ELASTIC_CUSTOM = new ElasticCustom(1.0f, 1.3f);
    }
  }

  private void initBubblePopup(int mode) {
    this.mBubbleBackground =
        (ImageView) this.mBubbleView.findViewById(C4337R.id.sem_tip_popup_bubble_bg);
    this.mBubbleIcon =
        (ImageView) this.mBubbleView.findViewById(C4337R.id.sem_tip_popup_bubble_icon);
    if (mode == 1) {
      this.mBubbleBackground.setImageResource(
          C4337R.drawable.sem_tip_popup_hint_background_translucent);
      this.mBubbleBackground.setImageTintList(null);
      if (isRTL() && !getLocale().equals("iw_IL")) {
        this.mBubbleIcon.setImageResource(C4337R.drawable.sem_tip_popup_hint_icon_translucent_rtl);
      } else {
        this.mBubbleIcon.setImageResource(C4337R.drawable.sem_tip_popup_hint_icon_translucent);
      }
      this.mBubbleIcon.setImageTintList(null);
      this.mBubbleWidth =
          this.mResources.getDimensionPixelSize(
              C4337R.dimen.sem_tip_popup_bubble_width_translucent);
      this.mBubbleHeight =
          this.mResources.getDimensionPixelSize(
              C4337R.dimen.sem_tip_popup_bubble_height_translucent);
    } else {
      this.mBubbleWidth =
          this.mResources.getDimensionPixelSize(C4337R.dimen.sem_tip_popup_bubble_width);
      this.mBubbleHeight =
          this.mResources.getDimensionPixelSize(C4337R.dimen.sem_tip_popup_bubble_height);
    }
    TipWindowBubble tipWindowBubble =
        new TipWindowBubble(this.mBubbleView, this.mBubbleWidth, this.mBubbleHeight, false);
    this.mBubblePopup = tipWindowBubble;
    tipWindowBubble.setTouchable(true);
    this.mBubblePopup.setOutsideTouchable(true);
    this.mBubblePopup.setAttachedInDecor(false);
  }

  private void initBalloonPopup(int mode) {
    this.mBalloonBubble =
        (FrameLayout) this.mBalloonView.findViewById(C4337R.id.sem_tip_popup_balloon_bubble);
    this.mBalloonBubbleHint =
        (ImageView) this.mBalloonView.findViewById(C4337R.id.sem_tip_popup_balloon_bubble_hint);
    this.mBalloonBubbleIcon =
        (ImageView) this.mBalloonView.findViewById(C4337R.id.sem_tip_popup_balloon_bubble_icon);
    this.mBalloonPanel =
        (FrameLayout) this.mBalloonView.findViewById(C4337R.id.sem_tip_popup_balloon_panel);
    this.mBalloonContent =
        (FrameLayout) this.mBalloonView.findViewById(C4337R.id.sem_tip_popup_balloon_content);
    this.mBalloonBg1 =
        (ImageView) this.mBalloonView.findViewById(C4337R.id.sem_tip_popup_balloon_bg_01);
    this.mBalloonBg2 =
        (ImageView) this.mBalloonView.findViewById(C4337R.id.sem_tip_popup_balloon_bg_02);
    if (mode == 1) {
      this.mBalloonBg1.setBackgroundResource(
          C4337R.drawable.sem_tip_popup_balloon_background_left_translucent);
      this.mBalloonBg1.setBackgroundTintList(null);
      this.mBalloonBg2.setBackgroundResource(
          C4337R.drawable.sem_tip_popup_balloon_background_right_translucent);
      this.mBalloonBg2.setBackgroundTintList(null);
    }
    this.mBalloonBubble.setVisibility(0);
    this.mBalloonPanel.setVisibility(8);
    TipWindowBalloon tipWindowBalloon =
        new TipWindowBalloon(this.mBalloonView, this.mBalloonWidth, this.mBalloonHeight, true);
    this.mBalloonPopup = tipWindowBalloon;
    tipWindowBalloon.setFocusable(true);
    this.mBalloonPopup.setTouchable(true);
    this.mBalloonPopup.setOutsideTouchable(true);
    this.mBalloonPopup.setAttachedInDecor(false);
    this.mBalloonPopup.setTouchInterceptor(
        new View.OnTouchListener() { // from class: com.samsung.android.widget.SemTipPopup.4
          @Override // android.view.View.OnTouchListener
          public boolean onTouch(View v, MotionEvent event) {
            if (SemTipPopup.this.mNeedToCallParentViewsOnClick
                && SemTipPopup.this.mParentView.hasOnClickListeners()
                && (event.getAction() == 0 || event.getAction() == 4)) {
              Rect parentViewBounds = new Rect();
              int[] outLocation = new int[2];
              SemTipPopup.this.mParentView.getLocationOnScreen(outLocation);
              parentViewBounds.set(
                  outLocation[0],
                  outLocation[1],
                  outLocation[0] + SemTipPopup.this.mParentView.getWidth(),
                  outLocation[1] + SemTipPopup.this.mParentView.getHeight());
              boolean isTouchContainedInParentView =
                  parentViewBounds.contains((int) event.getRawX(), (int) event.getRawY());
              if (isTouchContainedInParentView) {
                SemTipPopup.this.debugLog("callOnClick for parent view");
                SemTipPopup.this.mParentView.callOnClick();
              }
            }
            return false;
          }
        });
  }

  public void show(int direction) {
    setInternal();
    if (this.mArrowPositionX == -1 || this.mArrowPositionY == -1) {
      calculateArrowPosition();
    }
    if (direction == -1) {
      calculateArrowDirection(this.mArrowPositionX, this.mArrowPositionY);
    } else {
      this.mArrowDirection = direction;
    }
    calculatePopupSize();
    calculatePopupPosition();
    setBubblePanel();
    setBalloonPanel();
    showInternal();
  }

  public void setMessage(CharSequence message) {
    this.mMessageText = message;
  }

  public void setAction(CharSequence actionText, View.OnClickListener listener) {
    this.mActionText = actionText;
    this.mActionClickListener = listener;
  }

  public void semCallParentViewsOnClick(boolean needToCall) {
    this.mNeedToCallParentViewsOnClick = needToCall;
  }

  public boolean isShowing() {
    boolean isBubbleShowing = false;
    boolean isBalloonShowing = false;
    TipWindow tipWindow = this.mBubblePopup;
    if (tipWindow != null) {
      isBubbleShowing = tipWindow.isShowing();
    }
    TipWindow tipWindow2 = this.mBalloonPopup;
    if (tipWindow2 != null) {
      isBalloonShowing = tipWindow2.isShowing();
    }
    return isBubbleShowing || isBalloonShowing;
  }

  public void dismiss(boolean withAnimation) {
    TipWindow tipWindow = this.mBubblePopup;
    if (tipWindow != null) {
      tipWindow.setUseDismissAnimation(withAnimation);
      debugLog("mBubblePopup.mIsDismissing = " + this.mBubblePopup.mIsDismissing);
      this.mBubblePopup.dismiss();
    }
    TipWindow tipWindow2 = this.mBalloonPopup;
    if (tipWindow2 != null) {
      tipWindow2.setUseDismissAnimation(withAnimation);
      debugLog("mBalloonPopup.mIsDismissing = " + this.mBalloonPopup.mIsDismissing);
      this.mBalloonPopup.dismiss();
    }
    OnDismissListener onDismissListener = this.mOnDismissListener;
    if (onDismissListener != null) {
      onDismissListener.onDismiss();
    }
    Handler handler = mHandler;
    if (handler != null) {
      handler.removeCallbacksAndMessages(null);
      mHandler = null;
    }
  }

  public void setExpanded(boolean expanded) {
    if (expanded) {
      this.mState = 2;
      this.mScaleMargin = 0;
    } else {
      this.mScaleMargin =
          this.mResources.getDimensionPixelSize(C4337R.dimen.sem_tip_popup_scale_margin);
    }
  }

  public void setTargetPosition(int x, int y) {
    if (x < 0 || y < 0) {
      return;
    }
    this.mIsDefaultPosition = false;
    this.mArrowPositionX = x;
    this.mArrowPositionY = y;
  }

  public void setHintDescription(CharSequence hintDescription) {
    this.mHintDescription = hintDescription;
  }

  public void update() {
    update(this.mArrowDirection, false);
  }

  public void update(int direction, boolean resetHintTimer) {
    TipWindow tipWindow;
    TipWindow tipWindow2;
    if (!isShowing() || this.mParentView == null) {
      return;
    }
    setInternal();
    this.mBalloonX = -1;
    this.mBalloonY = -1;
    if (this.mIsDefaultPosition) {
      debugLog("update - default position");
      calculateArrowPosition();
    }
    if (direction == -1) {
      calculateArrowDirection(this.mArrowPositionX, this.mArrowPositionY);
    } else {
      this.mArrowDirection = direction;
    }
    calculatePopupSize();
    calculatePopupPosition();
    setBubblePanel();
    setBalloonPanel();
    int i = this.mState;
    if (i == 1 && (tipWindow2 = this.mBubblePopup) != null) {
      tipWindow2.update(
          this.mBubblePopupX,
          this.mBubblePopupY,
          tipWindow2.getWidth(),
          this.mBubblePopup.getHeight());
      if (resetHintTimer) {
        debugLog("Timer Reset!");
        scheduleTimeout();
        return;
      }
      return;
    }
    if (i == 2 && (tipWindow = this.mBalloonPopup) != null) {
      tipWindow.update(
          this.mBalloonPopupX,
          this.mBalloonPopupY,
          tipWindow.getWidth(),
          this.mBalloonPopup.getHeight());
    }
  }

  public void setMessageTextColor(int color) {
    this.mMessageTextColor = Integer.valueOf((-16777216) | color);
  }

  public void setActionTextColor(int color) {
    this.mActionTextColor = Integer.valueOf((-16777216) | color);
  }

  public void setBackgroundColor(int color) {
    this.mBackgroundColor = (-16777216) | color;
  }

  public void setBackgroundColorWithAlpha(int color) {
    this.mBackgroundColor = color;
  }

  public void setBorderColor(int color) {
    this.mBorderColor = Integer.valueOf((-16777216) | color);
  }

  public void setOutsideTouchEnabled(boolean enabled) {
    this.mBubblePopup.setFocusable(enabled);
    this.mBubblePopup.setOutsideTouchable(enabled);
    this.mBalloonPopup.setFocusable(enabled);
    this.mBalloonPopup.setOutsideTouchable(enabled);
    debugLog("outside enabled : " + enabled);
  }

  public void setPopupWindowClippingEnabled(boolean enabled) {
    this.mBubblePopup.setClippingEnabled(enabled);
    this.mBalloonPopup.setClippingEnabled(enabled);
    this.mForceRealDisplay = !enabled;
    this.mSideMargin =
        enabled ? this.mResources.getDimensionPixelSize(C4337R.dimen.sem_tip_popup_side_margin) : 0;
    debugLog("clipping enabled : " + enabled);
  }

  private void setInternal() {
    CharSequence charSequence;
    if (mHandler == null) {
      mHandler =
          new Handler(
              Looper.getMainLooper()) { // from class: com.samsung.android.widget.SemTipPopup.5
            @Override // android.os.Handler
            public void handleMessage(Message message) {
              switch (message.what) {
                case 0:
                  SemTipPopup.this.dismissBubble(true);
                  break;
                case 1:
                  SemTipPopup.this.dismissBubble(false);
                  break;
                case 2:
                  SemTipPopup.this.animateScaleUp();
                  break;
              }
            }
          };
    }
    if (this.mMessageView == null || this.mActionView == null) {
      return;
    }
    float currentFontScale = this.mResources.getConfiguration().fontScale;
    int messageTextSize =
        this.mResources.getDimensionPixelOffset(
            C4337R.dimen.sem_tip_popup_balloon_message_text_size);
    int actionTextSize =
        this.mResources.getDimensionPixelOffset(
            C4337R.dimen.sem_tip_popup_balloon_action_text_size);
    if (currentFontScale > 1.2f) {
      this.mMessageView.setTextSize(
          0, (float) Math.floor(Math.ceil(messageTextSize / currentFontScale) * 1.2f));
      this.mActionView.setTextSize(
          0, (float) Math.floor(Math.ceil(actionTextSize / currentFontScale) * 1.2f));
    }
    this.mMessageView.setText(this.mMessageText);
    if (TextUtils.isEmpty(this.mActionText) || this.mActionClickListener == null) {
      this.mActionView.setVisibility(8);
      this.mActionView.setOnClickListener(null);
      this.mType = 0;
    } else {
      this.mActionView.setVisibility(0);
      this.mActionView.semSetButtonShapeEnabled(true, this.mBackgroundColor);
      this.mActionView.setText(this.mActionText);
      this.mActionView.setOnClickListener(
          new View.OnClickListener() { // from class: com.samsung.android.widget.SemTipPopup.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
              if (SemTipPopup.this.mActionClickListener != null) {
                SemTipPopup.this.mActionClickListener.onClick(view);
              }
              SemTipPopup.this.dismiss(true);
            }
          });
      this.mType = 1;
    }
    ImageView imageView = this.mBubbleIcon;
    if (imageView != null && (charSequence = this.mHintDescription) != null) {
      imageView.setContentDescription(charSequence);
    }
    if (this.mMode == 1
        || this.mBubbleIcon == null
        || this.mBubbleBackground == null
        || this.mBalloonBubble == null
        || this.mBalloonBg1 == null
        || this.mBalloonBg2 == null) {
      return;
    }
    Integer num = this.mMessageTextColor;
    if (num != null) {
      this.mMessageView.setTextColor(num.intValue());
    }
    Integer num2 = this.mActionTextColor;
    if (num2 != null) {
      this.mActionView.setTextColor(num2.intValue());
    }
    this.mBubbleBackground.setColorFilter(this.mBackgroundColor);
    this.mBalloonBubbleHint.setColorFilter(this.mBackgroundColor);
    this.mBalloonBg1.setBackgroundTintList(ColorStateList.valueOf(this.mBackgroundColor));
    this.mBalloonBg2.setBackgroundTintList(ColorStateList.valueOf(this.mBackgroundColor));
    Integer num3 = this.mBorderColor;
    if (num3 != null) {
      this.mBubbleIcon.setColorFilter(num3.intValue());
      this.mBalloonBubbleIcon.setColorFilter(this.mBorderColor.intValue());
    }
  }

  private void showInternal() {
    if (this.mState != 2) {
      this.mState = 1;
      OnStateChangeListener onStateChangeListener = this.mOnStateChangeListener;
      if (onStateChangeListener != null) {
        onStateChangeListener.onStateChanged(1);
        debugLog("mIsShowing : " + isShowing());
      }
      TipWindow tipWindow = this.mBubblePopup;
      if (tipWindow != null) {
        tipWindow.showAtLocation(this.mParentView, 0, this.mBubblePopupX, this.mBubblePopupY);
        animateViewIn();
      }
      this.mBubbleView.setOnTouchListener(
          new View.OnTouchListener() { // from class: com.samsung.android.widget.SemTipPopup.7
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View v, MotionEvent event) {
              SemTipPopup.this.mState = 2;
              if (SemTipPopup.this.mOnStateChangeListener != null) {
                SemTipPopup.this.mOnStateChangeListener.onStateChanged(SemTipPopup.this.mState);
              }
              if (SemTipPopup.this.mBalloonPopup != null) {
                SemTipPopup.this.mBalloonPopup.showAtLocation(
                    SemTipPopup.this.mParentView,
                    0,
                    SemTipPopup.this.mBalloonPopupX,
                    SemTipPopup.this.mBalloonPopupY);
              }
              if (SemTipPopup.mHandler != null) {
                SemTipPopup.mHandler.removeMessages(0);
                SemTipPopup.mHandler.sendMessageDelayed(
                    Message.obtain(SemTipPopup.mHandler, 1), 10L);
                SemTipPopup.mHandler.sendMessageDelayed(
                    Message.obtain(SemTipPopup.mHandler, 2), 20L);
              }
              return false;
            }
          });
    } else {
      this.mBalloonBubble.setVisibility(8);
      this.mBalloonPanel.setVisibility(0);
      this.mMessageView.setVisibility(0);
      OnStateChangeListener onStateChangeListener2 = this.mOnStateChangeListener;
      if (onStateChangeListener2 != null) {
        onStateChangeListener2.onStateChanged(this.mState);
      }
      TipWindow tipWindow2 = this.mBalloonPopup;
      if (tipWindow2 != null) {
        tipWindow2.showAtLocation(this.mParentView, 0, this.mBalloonPopupX, this.mBalloonPopupY);
      }
      animateBaloonScaleUp();
    }
    this.mBalloonView.setOnTouchListener(
        new View.OnTouchListener() { // from class: com.samsung.android.widget.SemTipPopup.8
          @Override // android.view.View.OnTouchListener
          public boolean onTouch(View v, MotionEvent event) {
            if (SemTipPopup.this.mType == 0) {
              SemTipPopup.this.dismiss(true);
              return false;
            }
            return false;
          }
        });
  }

  private void setBubblePanel() {
    if (this.mBubblePopup == null) {
      return;
    }
    FrameLayout.LayoutParams paramBubblePanel =
        (FrameLayout.LayoutParams) this.mBubbleBackground.getLayoutParams();
    if (this.mMode == 1) {
      paramBubblePanel.width =
          this.mResources.getDimensionPixelSize(
              C4337R.dimen.sem_tip_popup_bubble_width_translucent);
      paramBubblePanel.height =
          this.mResources.getDimensionPixelSize(
              C4337R.dimen.sem_tip_popup_bubble_height_translucent);
    }
    switch (this.mArrowDirection) {
      case 0:
        this.mBubblePopup.setPivot(r1.getWidth(), this.mBubblePopup.getHeight());
        paramBubblePanel.gravity = 85;
        int i = this.mBubbleX;
        int i2 = this.mScaleMargin;
        this.mBubblePopupX = i - (i2 * 2);
        this.mBubblePopupY = this.mBubbleY - (i2 * 2);
        if (this.mMode == 0) {
          this.mBubbleBackground.setImageResource(C4337R.drawable.sem_tip_popup_hint_background_03);
          if (isRTL() && !getLocale().equals("iw_IL")) {
            this.mBubbleIcon.setImageResource(C4337R.drawable.sem_tip_popup_hint_icon_rtl);
            break;
          } else {
            this.mBubbleIcon.setImageResource(C4337R.drawable.sem_tip_popup_hint_icon);
            break;
          }
        } else {
          this.mBubbleBackground.setRotationX(180.0f);
          break;
        }
      case 1:
        this.mBubblePopup.setPivot(0.0f, r1.getHeight());
        paramBubblePanel.gravity = 83;
        this.mBubblePopupX = this.mBubbleX;
        this.mBubblePopupY = this.mBubbleY - (this.mScaleMargin * 2);
        if (this.mMode == 0) {
          this.mBubbleBackground.setImageResource(C4337R.drawable.sem_tip_popup_hint_background_04);
          if (isRTL() && !getLocale().equals("iw_IL")) {
            this.mBubbleIcon.setImageResource(C4337R.drawable.sem_tip_popup_hint_icon_rtl);
            break;
          } else {
            this.mBubbleIcon.setImageResource(C4337R.drawable.sem_tip_popup_hint_icon);
            break;
          }
        } else {
          this.mBubbleBackground.setRotation(180.0f);
          break;
        }
        break;
      case 2:
        this.mBubblePopup.setPivot(r1.getWidth(), 0.0f);
        paramBubblePanel.gravity = 53;
        this.mBubblePopupX = this.mBubbleX - (this.mScaleMargin * 2);
        this.mBubblePopupY = this.mBubbleY;
        if (this.mMode == 0) {
          this.mBubbleBackground.setImageResource(C4337R.drawable.sem_tip_popup_hint_background_01);
          if (isRTL() && !getLocale().equals("iw_IL")) {
            this.mBubbleIcon.setImageResource(C4337R.drawable.sem_tip_popup_hint_icon_rtl);
            break;
          } else {
            this.mBubbleIcon.setImageResource(C4337R.drawable.sem_tip_popup_hint_icon);
            break;
          }
        }
        break;
      case 3:
        this.mBubblePopup.setPivot(0.0f, 0.0f);
        paramBubblePanel.gravity = 51;
        this.mBubblePopupX = this.mBubbleX;
        this.mBubblePopupY = this.mBubbleY;
        if (this.mMode == 0) {
          this.mBubbleBackground.setImageResource(C4337R.drawable.sem_tip_popup_hint_background_02);
          if (isRTL() && !getLocale().equals("iw_IL")) {
            this.mBubbleIcon.setImageResource(C4337R.drawable.sem_tip_popup_hint_icon_rtl);
            break;
          } else {
            this.mBubbleIcon.setImageResource(C4337R.drawable.sem_tip_popup_hint_icon);
            break;
          }
        } else {
          this.mBubbleBackground.setRotationY(180.0f);
          break;
        }
        break;
    }
    this.mBubbleBackground.setLayoutParams(paramBubblePanel);
    this.mBubbleIcon.setLayoutParams(paramBubblePanel);
    this.mBubblePopup.setWidth(this.mBubbleWidth + (this.mScaleMargin * 2));
    this.mBubblePopup.setHeight(this.mBubbleHeight + (this.mScaleMargin * 2));
  }

  private void setBalloonPanel() {
    int scaleFactor;
    float f;
    FrameLayout.LayoutParams paramBalloonContent;
    float f2;
    if (this.mBalloonPopup != null) {
      debugLog("setBalloonPanel()");
      int i = this.mBubbleX;
      int i2 = this.mBalloonX;
      int leftMargin = i - i2;
      int rightMargin = (i2 + this.mBalloonWidth) - i;
      int i3 = this.mBubbleY;
      int i4 = this.mBalloonY;
      int topMargin = i3 - i4;
      int bottomMargin = (i4 + this.mBalloonHeight) - (i3 + this.mBubbleHeight);
      DisplayMetrics realMetrics = new DisplayMetrics();
      this.mWindowManager.getDefaultDisplay().getRealMetrics(realMetrics);
      int scaleFactor2 = (int) Math.ceil(realMetrics.density);
      int minBackgroundWidth =
          this.mResources.getDimensionPixelSize(
              C4337R.dimen.sem_tip_popup_balloon_background_minwidth);
      debugLog("leftMargin[" + leftMargin + NavigationBarInflaterView.SIZE_MOD_END);
      debugLog(
          "rightMargin["
              + rightMargin
              + "] mBalloonWidth["
              + this.mBalloonWidth
              + NavigationBarInflaterView.SIZE_MOD_END);
      int horizontalContentMargin =
          this.mHorizontalTextMargin
              - this.mResources.getDimensionPixelSize(
                  C4337R.dimen.sem_tip_popup_button_padding_horizontal);
      int verticalButtonPadding =
          this.mActionView.getVisibility() == 0
              ? this.mResources.getDimensionPixelSize(
                  C4337R.dimen.sem_tip_popup_button_padding_vertical)
              : 0;
      FrameLayout.LayoutParams paramBalloonBubble =
          (FrameLayout.LayoutParams) this.mBalloonBubble.getLayoutParams();
      FrameLayout.LayoutParams paramBalloonPanel =
          (FrameLayout.LayoutParams) this.mBalloonPanel.getLayoutParams();
      FrameLayout.LayoutParams paramBalloonContent2 =
          (FrameLayout.LayoutParams) this.mBalloonContent.getLayoutParams();
      FrameLayout.LayoutParams paramBalloonBg1 =
          (FrameLayout.LayoutParams) this.mBalloonBg1.getLayoutParams();
      FrameLayout.LayoutParams paramBalloonBg2 =
          (FrameLayout.LayoutParams) this.mBalloonBg2.getLayoutParams();
      if (this.mMode == 1) {
        this.mBalloonBubbleHint.setImageResource(
            C4337R.drawable.sem_tip_popup_hint_background_translucent);
        this.mBalloonBubbleHint.setImageTintList(null);
        if (isRTL() && !getLocale().equals("iw_IL")) {
          this.mBalloonBubbleIcon.setImageResource(
              C4337R.drawable.sem_tip_popup_hint_icon_translucent_rtl);
        } else {
          this.mBalloonBubbleIcon.setImageResource(
              C4337R.drawable.sem_tip_popup_hint_icon_translucent);
        }
        this.mBalloonBubbleIcon.setImageTintList(null);
        paramBalloonBubble.width =
            this.mResources.getDimensionPixelSize(
                C4337R.dimen.sem_tip_popup_bubble_width_translucent);
        paramBalloonBubble.height =
            this.mResources.getDimensionPixelSize(
                C4337R.dimen.sem_tip_popup_bubble_height_translucent);
        scaleFactor = 0;
      } else if (Color.alpha(this.mBackgroundColor) < 255) {
        debugLog("Updating scaleFactor to 0 because transparency is applied to background.");
        scaleFactor = 0;
      } else {
        scaleFactor = scaleFactor2;
      }
      switch (this.mArrowDirection) {
        case 0:
          TipWindow tipWindow = this.mBalloonPopup;
          int i5 = this.mArrowPositionX - this.mBalloonX;
          int i6 = this.mScaleMargin;
          tipWindow.setPivot(i5 + i6, this.mBalloonHeight + i6);
          if (this.mMode == 0) {
            this.mBalloonBubbleHint.setImageResource(
                C4337R.drawable.sem_tip_popup_hint_background_03);
            this.mBalloonBubbleIcon.setImageResource(C4337R.drawable.sem_tip_popup_hint_icon);
            f = 180.0f;
          } else {
            f = 180.0f;
            this.mBalloonBubbleHint.setRotationX(180.0f);
          }
          this.mBalloonBg1.setRotationX(f);
          this.mBalloonBg2.setRotationX(f);
          paramBalloonBg2.gravity = 85;
          paramBalloonBg1.gravity = 85;
          paramBalloonBubble.gravity = 85;
          int i7 = this.mBubbleWidth;
          if (rightMargin - i7 < minBackgroundWidth) {
            int scaledLeftMargin = this.mBalloonWidth - minBackgroundWidth;
            paramBalloonBg1.setMargins(0, 0, minBackgroundWidth, 0);
            paramBalloonBg2.setMargins(scaledLeftMargin - scaleFactor, 0, 0, 0);
            debugLog("Right Margin is less then minimum background width!");
            debugLog(
                "updated !! leftMargin["
                    + scaledLeftMargin
                    + "],  rightMargin["
                    + minBackgroundWidth
                    + NavigationBarInflaterView.SIZE_MOD_END);
          } else {
            paramBalloonBg1.setMargins(0, 0, rightMargin - i7, 0);
            paramBalloonBg2.setMargins((this.mBubbleWidth + leftMargin) - scaleFactor, 0, 0, 0);
          }
          int i8 = this.mVerticalTextMargin;
          paramBalloonContent = paramBalloonContent2;
          paramBalloonContent.setMargins(
              horizontalContentMargin,
              i8,
              horizontalContentMargin,
              (this.mArrowHeight + i8) - verticalButtonPadding);
          break;
        case 1:
          TipWindow tipWindow2 = this.mBalloonPopup;
          int i9 = this.mArrowPositionX - this.mBalloonX;
          int i10 = this.mScaleMargin;
          tipWindow2.setPivot(i9 + i10, this.mBalloonHeight + i10);
          if (this.mMode == 0) {
            this.mBalloonBubbleHint.setImageResource(
                C4337R.drawable.sem_tip_popup_hint_background_04);
            this.mBalloonBubbleIcon.setImageResource(C4337R.drawable.sem_tip_popup_hint_icon);
            f2 = 180.0f;
          } else {
            f2 = 180.0f;
            this.mBalloonBubbleHint.setRotation(180.0f);
          }
          this.mBalloonBg1.setRotation(f2);
          this.mBalloonBg2.setRotation(f2);
          paramBalloonBg2.gravity = 83;
          paramBalloonBg1.gravity = 83;
          paramBalloonBubble.gravity = 83;
          if (leftMargin < minBackgroundWidth) {
            int scaledRightMargin = this.mBalloonWidth - minBackgroundWidth;
            paramBalloonBg1.setMargins(minBackgroundWidth, 0, 0, 0);
            paramBalloonBg2.setMargins(0, 0, scaledRightMargin - scaleFactor, 0);
            debugLog("Left Margin is less then minimum background width!");
            debugLog(
                "updated !! leftMargin["
                    + minBackgroundWidth
                    + "],  rightMargin["
                    + scaledRightMargin
                    + NavigationBarInflaterView.SIZE_MOD_END);
          } else {
            paramBalloonBg1.setMargins(leftMargin, 0, 0, 0);
            paramBalloonBg2.setMargins(0, 0, rightMargin - scaleFactor, 0);
          }
          int i11 = this.mVerticalTextMargin;
          paramBalloonContent = paramBalloonContent2;
          paramBalloonContent.setMargins(
              horizontalContentMargin,
              i11,
              horizontalContentMargin,
              (this.mArrowHeight + i11) - verticalButtonPadding);
          break;
        case 2:
          TipWindow tipWindow3 = this.mBalloonPopup;
          int i12 = this.mArrowPositionX - this.mBalloonX;
          tipWindow3.setPivot(i12 + r5, this.mScaleMargin);
          if (this.mMode == 0) {
            this.mBalloonBubbleHint.setImageResource(
                C4337R.drawable.sem_tip_popup_hint_background_01);
            this.mBalloonBubbleIcon.setImageResource(C4337R.drawable.sem_tip_popup_hint_icon);
          }
          paramBalloonBg2.gravity = 53;
          paramBalloonBg1.gravity = 53;
          paramBalloonBubble.gravity = 53;
          paramBalloonBg1.setMargins(0, 0, rightMargin - this.mBubbleWidth, 0);
          paramBalloonBg2.setMargins((this.mBubbleWidth + leftMargin) - scaleFactor, 0, 0, 0);
          int i13 = this.mArrowHeight;
          int i14 = this.mVerticalTextMargin;
          paramBalloonContent2.setMargins(
              horizontalContentMargin,
              i13 + i14,
              horizontalContentMargin,
              i14 - verticalButtonPadding);
          paramBalloonContent = paramBalloonContent2;
          break;
        case 3:
          TipWindow tipWindow4 = this.mBalloonPopup;
          int i15 = this.mArrowPositionX - this.mBalloonX;
          tipWindow4.setPivot(i15 + r9, this.mScaleMargin);
          if (this.mMode == 0) {
            this.mBalloonBubbleHint.setImageResource(
                C4337R.drawable.sem_tip_popup_hint_background_02);
            this.mBalloonBubbleIcon.setImageResource(C4337R.drawable.sem_tip_popup_hint_icon);
          } else {
            this.mBalloonBubbleHint.setRotationY(180.0f);
          }
          this.mBalloonBg1.setRotationY(180.0f);
          this.mBalloonBg2.setRotationY(180.0f);
          paramBalloonBg2.gravity = 51;
          paramBalloonBg1.gravity = 51;
          paramBalloonBubble.gravity = 51;
          paramBalloonBg1.setMargins(leftMargin, 0, 0, 0);
          paramBalloonBg2.setMargins(0, 0, rightMargin - scaleFactor, 0);
          int i16 = this.mArrowHeight;
          int i17 = this.mVerticalTextMargin;
          paramBalloonContent2.setMargins(
              horizontalContentMargin,
              i16 + i17,
              horizontalContentMargin,
              i17 - verticalButtonPadding);
          paramBalloonContent = paramBalloonContent2;
          break;
        default:
          paramBalloonContent = paramBalloonContent2;
          break;
      }
      int i18 = this.mScaleMargin;
      paramBalloonBubble.setMargins(
          leftMargin + i18,
          topMargin + i18,
          (rightMargin - this.mBubbleWidth) + i18,
          bottomMargin + i18);
      int balloonPanelMargin = this.mScaleMargin;
      paramBalloonPanel.setMargins(
          balloonPanelMargin, balloonPanelMargin, balloonPanelMargin, balloonPanelMargin);
      int i19 = this.mBalloonX;
      int i20 = this.mScaleMargin;
      this.mBalloonPopupX = i19 - i20;
      this.mBalloonPopupY = this.mBalloonY - i20;
      this.mBalloonBubble.setLayoutParams(paramBalloonBubble);
      this.mBalloonPanel.setLayoutParams(paramBalloonPanel);
      this.mBalloonBg1.setLayoutParams(paramBalloonBg1);
      this.mBalloonBg2.setLayoutParams(paramBalloonBg2);
      this.mBalloonContent.setLayoutParams(paramBalloonContent);
      this.mBalloonPopup.setWidth(this.mBalloonWidth + (this.mScaleMargin * 2));
      this.mBalloonPopup.setHeight(this.mBalloonHeight + (this.mScaleMargin * 2));
    }
  }

  private void calculateArrowDirection(int arrowX, int arrowY) {
    View view = this.mParentView;
    if (view != null && this.mIsDefaultPosition) {
      int[] location = new int[2];
      view.getLocationInWindow(location);
      int parentY = location[1] + (this.mParentView.getHeight() / 2);
      if (arrowX * 2 <= this.mDisplayMetrics.widthPixels) {
        if (arrowY <= parentY) {
          this.mArrowDirection = 1;
        } else {
          this.mArrowDirection = 3;
        }
      } else if (arrowY <= parentY) {
        this.mArrowDirection = 0;
      } else {
        this.mArrowDirection = 2;
      }
    } else if (arrowX * 2 <= this.mDisplayMetrics.widthPixels
        && arrowY * 2 <= this.mDisplayMetrics.heightPixels) {
      this.mArrowDirection = 3;
    } else if (arrowX * 2 > this.mDisplayMetrics.widthPixels
        && arrowY * 2 <= this.mDisplayMetrics.heightPixels) {
      this.mArrowDirection = 2;
    } else if (arrowX * 2 <= this.mDisplayMetrics.widthPixels
        && arrowY * 2 > this.mDisplayMetrics.heightPixels) {
      this.mArrowDirection = 1;
    } else if (arrowX * 2 > this.mDisplayMetrics.widthPixels
        && arrowY * 2 > this.mDisplayMetrics.heightPixels) {
      this.mArrowDirection = 0;
    }
    debugLog(
        "calculateArrowDirection : arrow position ("
            + arrowX
            + ", "
            + arrowY
            + ") / mArrowDirection = "
            + this.mArrowDirection);
  }

  private void calculateArrowPosition() {
    View view = this.mParentView;
    if (view == null) {
      this.mArrowPositionX = 0;
      this.mArrowPositionY = 0;
      return;
    }
    int[] location = new int[2];
    view.getLocationInWindow(location);
    debugLog("calculateArrowPosition anchor location : " + location[0] + ", " + location[1]);
    int x = location[0] + (this.mParentView.getWidth() / 2);
    int y = location[1] + (this.mParentView.getHeight() / 2);
    if (y * 2 <= this.mDisplayMetrics.heightPixels) {
      this.mArrowPositionY = (this.mParentView.getHeight() / 2) + y;
    } else {
      this.mArrowPositionY = y - (this.mParentView.getHeight() / 2);
    }
    this.mArrowPositionX = x;
    debugLog(
        "calculateArrowPosition mArrowPosition : "
            + this.mArrowPositionX
            + ", "
            + this.mArrowPositionY);
  }

  private void calculatePopupSize() {
    int balloonMaxWidth;
    this.mDisplayMetrics = this.mResources.getDisplayMetrics();
    int screenWidthDp = this.mResources.getConfiguration().screenWidthDp;
    int balloonMinWidth = this.mArrowWidth + (this.mHorizontalTextMargin * 2);
    if (this.mContext.getResources().getConfiguration().semDesktopModeEnabled != 1) {
      debugLog("screen width DP " + screenWidthDp);
      if (screenWidthDp <= 480) {
        balloonMaxWidth = (int) (this.mDisplayMetrics.widthPixels * 0.83f);
      } else if (screenWidthDp <= 960) {
        balloonMaxWidth = (int) (this.mDisplayMetrics.widthPixels * 0.6f);
      } else if (screenWidthDp <= 1280) {
        balloonMaxWidth = (int) (this.mDisplayMetrics.widthPixels * 0.45f);
      } else {
        balloonMaxWidth = (int) (this.mDisplayMetrics.widthPixels * 0.25f);
      }
    } else {
      int windowWidthInDexMode = this.mParentView.getRootView().getMeasuredWidth();
      int[] windowLocation = new int[2];
      this.mParentView.getRootView().getLocationOnScreen(windowLocation);
      if (windowLocation[0] < 0) {
        windowWidthInDexMode += windowLocation[0];
      }
      debugLog("Window width in DexMode " + windowWidthInDexMode);
      if (windowWidthInDexMode <= 480) {
        balloonMaxWidth = (int) (windowWidthInDexMode * 0.83f);
      } else if (windowWidthInDexMode <= 960) {
        balloonMaxWidth = (int) (windowWidthInDexMode * 0.6f);
      } else if (windowWidthInDexMode <= 1280) {
        balloonMaxWidth = (int) (windowWidthInDexMode * 0.45f);
      } else {
        balloonMaxWidth = (int) (windowWidthInDexMode * 0.25f);
      }
    }
    if (!this.mIsMessageViewMeasured) {
      this.mMessageView.measure(0, 0);
      this.mInitialmMessageViewWidth = this.mMessageView.getMeasuredWidth();
      this.mIsMessageViewMeasured = true;
    }
    int i = this.mInitialmMessageViewWidth;
    int i2 = this.mHorizontalTextMargin;
    int balloonWidth = i + (i2 * 2);
    if (balloonWidth < balloonMinWidth) {
      balloonWidth = balloonMinWidth;
    } else if (balloonWidth > balloonMaxWidth) {
      balloonWidth = balloonMaxWidth;
    }
    this.mBalloonWidth = balloonWidth;
    this.mMessageView.setWidth(balloonWidth - (i2 * 2));
    this.mMessageView.measure(0, 0);
    this.mBalloonHeight =
        this.mMessageView.getMeasuredHeight() + (this.mVerticalTextMargin * 2) + this.mArrowHeight;
    if (this.mType == 1) {
      this.mActionView.measure(0, 0);
      if (this.mBalloonWidth < this.mActionView.getMeasuredWidth()) {
        this.mBalloonWidth =
            this.mActionView.getMeasuredWidth()
                + (this.mResources.getDimensionPixelSize(
                        C4337R.dimen.sem_tip_popup_button_padding_horizontal)
                    * 2);
      }
      this.mBalloonHeight +=
          this.mActionView.getMeasuredHeight()
              - this.mResources.getDimensionPixelSize(
                  C4337R.dimen.sem_tip_popup_button_padding_vertical);
    }
  }

  private void calculatePopupPosition() {
    getDisplayFrame(this.mDisplayFrame);
    if (this.mBalloonX < 0) {
      int i = this.mArrowDirection;
      if (i == 3 || i == 1) {
        this.mBalloonX = (this.mArrowPositionX + this.mArrowWidth) - (this.mBalloonWidth / 2);
      } else {
        this.mBalloonX = (this.mArrowPositionX - this.mArrowWidth) - (this.mBalloonWidth / 2);
      }
    }
    int i2 = this.mArrowDirection;
    if (i2 == 3 || i2 == 1) {
      if (this.mArrowPositionX
          < this.mDisplayFrame.left + this.mSideMargin + this.mHorizontalTextMargin) {
        debugLog("Target position is too far to the left!");
        this.mArrowPositionX =
            this.mDisplayFrame.left + this.mSideMargin + this.mHorizontalTextMargin;
      } else if (this.mArrowPositionX
          > ((this.mDisplayFrame.right - this.mSideMargin) - this.mHorizontalTextMargin)
              - this.mArrowWidth) {
        debugLog("Target position is too far to the right!");
        this.mArrowPositionX =
            ((this.mDisplayFrame.right - this.mSideMargin) - this.mHorizontalTextMargin)
                - this.mArrowWidth;
      }
    } else if (this.mArrowPositionX
        < this.mDisplayFrame.left
            + this.mSideMargin
            + this.mHorizontalTextMargin
            + this.mArrowWidth) {
      debugLog("Target position is too far to the left!");
      this.mArrowPositionX =
          this.mDisplayFrame.left
              + this.mSideMargin
              + this.mHorizontalTextMargin
              + this.mArrowWidth;
    } else if (this.mArrowPositionX
        > (this.mDisplayFrame.right - this.mSideMargin) - this.mHorizontalTextMargin) {
      debugLog("Target position is too far to the right!");
      this.mArrowPositionX =
          (this.mDisplayFrame.right - this.mSideMargin) - this.mHorizontalTextMargin;
    }
    if (this.mContext.getResources().getConfiguration().semDesktopModeEnabled == 1) {
      int windowWidthInDexMode = this.mParentView.getRootView().getMeasuredWidth();
      int[] windowLocation = new int[2];
      this.mParentView.getRootView().getLocationOnScreen(windowLocation);
      if (windowLocation[0] < 0) {
        windowWidthInDexMode += windowLocation[0];
      }
      int i3 = this.mBalloonX;
      int i4 = this.mDisplayFrame.left;
      int i5 = this.mSideMargin;
      if (i3 < i4 + i5) {
        this.mBalloonX = this.mDisplayFrame.left + this.mSideMargin;
      } else {
        int i6 = this.mBalloonX;
        int i7 = this.mBalloonWidth;
        if (i6 + i7 > windowWidthInDexMode - i5) {
          int i8 = (windowWidthInDexMode - i5) - i7;
          this.mBalloonX = i8;
          if (windowLocation[0] < 0) {
            this.mBalloonX = i8 - windowLocation[0];
          }
        }
      }
    } else if (this.mBalloonX < this.mDisplayFrame.left + this.mSideMargin) {
      this.mBalloonX = this.mDisplayFrame.left + this.mSideMargin;
    } else if (this.mBalloonX + this.mBalloonWidth > this.mDisplayFrame.right - this.mSideMargin) {
      this.mBalloonX = (this.mDisplayFrame.right - this.mSideMargin) - this.mBalloonWidth;
    }
    switch (this.mArrowDirection) {
      case 0:
        this.mBubbleX = this.mArrowPositionX - this.mBubbleWidth;
        int i9 = this.mArrowPositionY;
        this.mBubbleY = i9 - this.mBubbleHeight;
        this.mBalloonY = i9 - this.mBalloonHeight;
        break;
      case 1:
        this.mBubbleX = this.mArrowPositionX;
        int i10 = this.mArrowPositionY;
        this.mBubbleY = i10 - this.mBubbleHeight;
        this.mBalloonY = i10 - this.mBalloonHeight;
        break;
      case 2:
        this.mBubbleX = this.mArrowPositionX - this.mBubbleWidth;
        int i11 = this.mArrowPositionY;
        this.mBubbleY = i11;
        this.mBalloonY = i11;
        break;
      case 3:
        this.mBubbleX = this.mArrowPositionX;
        int i12 = this.mArrowPositionY;
        this.mBubbleY = i12;
        this.mBalloonY = i12;
        break;
    }
    debugLog(
        "QuestionPopup : "
            + this.mBubbleX
            + ", "
            + this.mBubbleY
            + ", "
            + this.mBubbleWidth
            + ", "
            + this.mBubbleHeight);
    debugLog(
        "BalloonPopup : "
            + this.mBalloonX
            + ", "
            + this.mBalloonY
            + ", "
            + this.mBalloonWidth
            + ", "
            + this.mBalloonHeight);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void dismissBubble(boolean withAnimation) {
    TipWindow tipWindow = this.mBubblePopup;
    if (tipWindow != null) {
      tipWindow.setUseDismissAnimation(withAnimation);
      this.mBubblePopup.dismiss();
    }
    OnDismissListener onDismissListener = this.mOnDismissListener;
    if (onDismissListener != null) {
      onDismissListener.onDismiss();
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void scheduleTimeout() {
    Handler handler = mHandler;
    if (handler != null) {
      handler.removeMessages(0);
      Handler handler2 = mHandler;
      handler2.sendMessageDelayed(Message.obtain(handler2, 0), 7100L);
    }
  }

  private void animateViewIn() {
    float pivotX = 0.0f;
    float pivotY = 0.0f;
    switch (this.mArrowDirection) {
      case 0:
        pivotX = 1.0f;
        pivotY = 1.0f;
        break;
      case 1:
        pivotX = 0.0f;
        pivotY = 1.0f;
        break;
      case 2:
        pivotX = 1.0f;
        pivotY = 0.0f;
        break;
      case 3:
        pivotX = 0.0f;
        pivotY = 0.0f;
        break;
    }
    Animation animScale = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 1, pivotX, 1, pivotY);
    animScale.setInterpolator(INTERPOLATOR_ELASTIC_50);
    animScale.setDuration(500L);
    animScale.setAnimationListener(
        new Animation.AnimationListener() { // from class: com.samsung.android.widget.SemTipPopup.9
          @Override // android.view.animation.Animation.AnimationListener
          public void onAnimationStart(Animation animation) {}

          @Override // android.view.animation.Animation.AnimationListener
          public void onAnimationEnd(Animation animation) {
            SemTipPopup.this.scheduleTimeout();
            SemTipPopup.this.animateBounce();
          }

          @Override // android.view.animation.Animation.AnimationListener
          public void onAnimationRepeat(Animation animation) {}
        });
    this.mBubbleView.startAnimation(animScale);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void animateBounce() {
    float pivotX = 0.0f;
    float pivotY = 0.0f;
    switch (this.mArrowDirection) {
      case 0:
        pivotX = this.mBubblePopup.getWidth();
        pivotY = this.mBubblePopup.getHeight();
        break;
      case 1:
        pivotX = 0.0f;
        pivotY = this.mBubblePopup.getHeight();
        break;
      case 2:
        pivotX = this.mBubblePopup.getWidth();
        pivotY = 0.0f;
        break;
      case 3:
        pivotX = 0.0f;
        pivotY = 0.0f;
        break;
    }
    final AnimationSet animationSet = new AnimationSet(false);
    float f = pivotX;
    float f2 = pivotY;
    Animation anim1 = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, 0, f, 0, f2);
    anim1.setDuration(167L);
    anim1.setInterpolator(INTERPOLATOR_SINE_IN_OUT_70);
    Animation anim2 = new ScaleAnimation(1.0f, 0.833f, 1.0f, 0.833f, 0, f, 0, f2);
    anim2.setStartOffset(167L);
    anim2.setDuration(250L);
    anim2.setInterpolator(INTERPOLATOR_SINE_IN_OUT_33);
    anim2.setAnimationListener(
        new Animation.AnimationListener() { // from class: com.samsung.android.widget.SemTipPopup.10
          int count = 0;

          @Override // android.view.animation.Animation.AnimationListener
          public void onAnimationStart(Animation animation) {
            this.count++;
          }

          @Override // android.view.animation.Animation.AnimationListener
          public void onAnimationEnd(Animation animation) {
            SemTipPopup.this.debugLog("repeat count " + this.count);
            SemTipPopup.this.mBubbleView.startAnimation(animationSet);
          }

          @Override // android.view.animation.Animation.AnimationListener
          public void onAnimationRepeat(Animation animation) {}
        });
    animationSet.addAnimation(anim1);
    animationSet.addAnimation(anim2);
    animationSet.setStartOffset(TelecomManager.VERY_SHORT_CALL_TIME_MS);
    this.mBubbleView.startAnimation(animationSet);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void animateScaleUp() {
    float deltaHintY = 0.0f;
    float pivotHintX = 0.0f;
    float pivotHintY = 0.0f;
    switch (this.mArrowDirection) {
      case 0:
        pivotHintX = this.mBalloonBubble.getWidth();
        pivotHintY = this.mBalloonBubble.getHeight();
        deltaHintY = 0.0f - (this.mArrowHeight / 2.0f);
        break;
      case 1:
        pivotHintX = 0.0f;
        pivotHintY = this.mBalloonBubble.getHeight();
        deltaHintY = 0.0f - (this.mArrowHeight / 2.0f);
        break;
      case 2:
        pivotHintX = this.mBalloonBubble.getWidth();
        pivotHintY = 0.0f;
        deltaHintY = this.mArrowHeight / 2.0f;
        break;
      case 3:
        pivotHintX = 0.0f;
        pivotHintY = 0.0f;
        deltaHintY = this.mArrowHeight / 2.0f;
        break;
    }
    AnimationSet animationBubble = new AnimationSet(false);
    TranslateAnimation animationBubbleMove =
        new TranslateAnimation(0, 0.0f, 0, 0.0f, 0, 0.0f, 0, deltaHintY);
    animationBubbleMove.setDuration(500L);
    animationBubbleMove.setInterpolator(INTERPOLATOR_ELASTIC_CUSTOM);
    Animation animationBubbleScale =
        new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f, 0, pivotHintX, 0, pivotHintY);
    animationBubbleScale.setDuration(500L);
    animationBubbleScale.setInterpolator(INTERPOLATOR_ELASTIC_50);
    Animation animationBubbleAlpha = new AlphaAnimation(1.0f, 0.0f);
    animationBubbleAlpha.setDuration(167L);
    animationBubbleAlpha.setInterpolator(INTERPOLATOR_SINE_IN_OUT_70);
    animationBubble.addAnimation(animationBubbleMove);
    animationBubble.addAnimation(animationBubbleScale);
    animationBubble.addAnimation(animationBubbleAlpha);
    animationBubble.setAnimationListener(
        new Animation.AnimationListener() { // from class: com.samsung.android.widget.SemTipPopup.11
          @Override // android.view.animation.Animation.AnimationListener
          public void onAnimationStart(Animation animation) {
            SemTipPopup.this.mBalloonPanel.setVisibility(0);
          }

          @Override // android.view.animation.Animation.AnimationListener
          public void onAnimationEnd(Animation animation) {
            SemTipPopup.this.mBalloonBubble.setVisibility(8);
          }

          @Override // android.view.animation.Animation.AnimationListener
          public void onAnimationRepeat(Animation animation) {}
        });
    this.mBalloonBubble.startAnimation(animationBubble);
    animateBaloonScaleUp();
  }

  private void animateBaloonScaleUp() {
    float pivotPanelX = 0.0f;
    float pivotPanelY = 0.0f;
    int questionHeight =
        this.mResources.getDimensionPixelSize(C4337R.dimen.sem_tip_popup_bubble_height);
    int i = this.mBalloonHeight;
    float panelScale = questionHeight / i;
    switch (this.mArrowDirection) {
      case 1:
        pivotPanelX = this.mArrowPositionX - this.mBalloonX;
        pivotPanelY = i;
        break;
      case 2:
        pivotPanelX = this.mArrowPositionX - this.mBalloonX;
        pivotPanelY = 0.0f;
        break;
      case 3:
        pivotPanelX = this.mBubbleX - this.mBalloonX;
        pivotPanelY = 0.0f;
        break;
    }
    AnimationSet animationPanel = new AnimationSet(false);
    Animation animationPanelScale =
        new ScaleAnimation(0.32f, 1.0f, panelScale, 1.0f, 0, pivotPanelX, 0, pivotPanelY);
    animationPanelScale.setInterpolator(INTERPOLATOR_ELASTIC_CUSTOM);
    animationPanelScale.setDuration(500L);
    Animation animationPanelAlpha = new AlphaAnimation(0.0f, 1.0f);
    animationPanelAlpha.setInterpolator(INTERPOLATOR_SINE_IN_OUT_70);
    animationPanelAlpha.setDuration(83L);
    animationPanel.addAnimation(animationPanelScale);
    animationPanel.addAnimation(animationPanelAlpha);
    this.mBalloonPanel.startAnimation(animationPanel);
    Animation animationText = new AlphaAnimation(0.0f, 1.0f);
    animationText.setInterpolator(INTERPOLATOR_SINE_IN_OUT_33);
    animationText.setStartOffset(333L);
    animationText.setDuration(167L);
    animationText.setAnimationListener(
        new Animation.AnimationListener() { // from class: com.samsung.android.widget.SemTipPopup.12
          @Override // android.view.animation.Animation.AnimationListener
          public void onAnimationStart(Animation animation) {
            SemTipPopup.this.mMessageView.setVisibility(0);
          }

          @Override // android.view.animation.Animation.AnimationListener
          public void onAnimationEnd(Animation animation) {
            SemTipPopup.this.dismissBubble(false);
          }

          @Override // android.view.animation.Animation.AnimationListener
          public void onAnimationRepeat(Animation animation) {}
        });
    this.mMessageView.startAnimation(animationText);
    this.mActionView.startAnimation(animationText);
  }

  private boolean isNavigationbarHide() {
    Context context = this.mContext;
    return context != null
        && Settings.Global.getInt(context.getContentResolver(), "navigationbar_hide_bar_enabled", 0)
            == 1;
  }

  private int getNavagationbarHeight() {
    int resourceId = this.mResources.getIdentifier("navigation_bar_height", "dimen", "android");
    if (resourceId > 0) {
      return this.mResources.getDimensionPixelSize(resourceId);
    }
    return 0;
  }

  private boolean isTablet() {
    DisplayMetrics realMetrics = new DisplayMetrics();
    this.mWindowManager.getDefaultDisplay().getRealMetrics(realMetrics);
    int shortSize =
        realMetrics.widthPixels > realMetrics.heightPixels
            ? realMetrics.heightPixels
            : realMetrics.widthPixels;
    int shortSizeDp = (shortSize * 160) / realMetrics.densityDpi;
    debugLog("short size dp  = " + shortSizeDp);
    return shortSizeDp >= 600;
  }

  private void getDisplayFrame(Rect screenRect) {
    DisplayCutout displayCutout;
    int navigationbarHeight = getNavagationbarHeight();
    boolean navigationbarHide = isNavigationbarHide();
    int displayRotation = this.mWindowManager.getDefaultDisplay().getRotation();
    DisplayMetrics realMetrics = new DisplayMetrics();
    this.mWindowManager.getDefaultDisplay().getRealMetrics(realMetrics);
    debugLog("realMetrics = " + realMetrics);
    debugLog("is tablet? = " + isTablet());
    if (this.mForceRealDisplay) {
      screenRect.left = 0;
      screenRect.top = 0;
      screenRect.right = realMetrics.widthPixels;
      screenRect.bottom = realMetrics.heightPixels;
      debugLog("Screen Rect = " + screenRect + " mForceRealDisplay = " + this.mForceRealDisplay);
      return;
    }
    screenRect.left = 0;
    screenRect.top = 0;
    screenRect.right = this.mDisplayMetrics.widthPixels;
    screenRect.bottom = this.mDisplayMetrics.heightPixels;
    Rect bounds = new Rect();
    View appRootView =
        WindowManagerGlobal.getInstance()
            .getWindowView(this.mParentView.getApplicationWindowToken());
    if (appRootView == null) {
      appRootView = this.mParentView.getRootView();
    }
    appRootView.getWindowVisibleDisplayFrame(bounds);
    debugLog("Bounds = " + bounds);
    if (isTablet()) {
      debugLog(BnRConstants.DEVICETYPE_TABLET);
      if (realMetrics.widthPixels == this.mDisplayMetrics.widthPixels
          && realMetrics.heightPixels - this.mDisplayMetrics.heightPixels == navigationbarHeight
          && navigationbarHide) {
        screenRect.bottom += navigationbarHeight;
      }
    } else {
      debugLog("phone");
      switch (displayRotation) {
        case 0:
          if (realMetrics.widthPixels == this.mDisplayMetrics.widthPixels
              && realMetrics.heightPixels - this.mDisplayMetrics.heightPixels == navigationbarHeight
              && navigationbarHide) {
            screenRect.bottom += navigationbarHeight;
            break;
          }
          break;
        case 1:
          if (realMetrics.heightPixels == this.mDisplayMetrics.heightPixels
              && realMetrics.widthPixels - this.mDisplayMetrics.widthPixels == navigationbarHeight
              && navigationbarHide) {
            screenRect.right += navigationbarHeight;
          }
          WindowInsets windowInsets = this.mParentView.getRootWindowInsets();
          if (windowInsets != null && (displayCutout = windowInsets.getDisplayCutout()) != null) {
            screenRect.left += displayCutout.getSafeInsetLeft();
            screenRect.right += displayCutout.getSafeInsetLeft();
            debugLog("displayCutout.getSafeInsetLeft() :  " + displayCutout.getSafeInsetLeft());
            break;
          }
          break;
        case 2:
          if (realMetrics.widthPixels == this.mDisplayMetrics.widthPixels
              && realMetrics.heightPixels - this.mDisplayMetrics.heightPixels
                  == navigationbarHeight) {
            if (navigationbarHide) {
              screenRect.bottom += navigationbarHeight;
              break;
            } else {
              screenRect.top += navigationbarHeight;
              screenRect.bottom += navigationbarHeight;
              break;
            }
          } else if (realMetrics.widthPixels == this.mDisplayMetrics.widthPixels
              && bounds.top == navigationbarHeight) {
            debugLog("Top Docked");
            screenRect.top += navigationbarHeight;
            screenRect.bottom += navigationbarHeight;
            break;
          }
          break;
        case 3:
          if (realMetrics.heightPixels == this.mDisplayMetrics.heightPixels
              && realMetrics.widthPixels - this.mDisplayMetrics.widthPixels
                  == navigationbarHeight) {
            if (navigationbarHide) {
              screenRect.right += navigationbarHeight;
              break;
            } else {
              screenRect.left += navigationbarHeight;
              screenRect.right += navigationbarHeight;
              break;
            }
          } else if (realMetrics.heightPixels == this.mDisplayMetrics.heightPixels
              && bounds.left == navigationbarHeight) {
            debugLog("Left Docked");
            screenRect.left += navigationbarHeight;
            screenRect.right += navigationbarHeight;
            break;
          }
          break;
      }
    }
    debugLog("Screen Rect = " + screenRect);
  }

  public void setOnDismissListener(OnDismissListener onDismissListener) {
    this.mOnDismissListener = onDismissListener;
  }

  private static class TipWindow extends PopupWindow {
    protected boolean mIsDismissing;
    private boolean mIsUsingDismissAnimation;
    protected float mPivotX;
    protected float mPivotY;

    private TipWindow(View contentView, int width, int height, boolean focusable) {
      super(contentView, width, height, focusable);
      this.mIsUsingDismissAnimation = true;
      this.mIsDismissing = false;
      this.mPivotX = 0.0f;
      this.mPivotY = 0.0f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setUseDismissAnimation(boolean useAnimation) {
      this.mIsUsingDismissAnimation = useAnimation;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPivot(float pivotX, float pivotY) {
      this.mPivotX = pivotX;
      this.mPivotY = pivotY;
    }

    @Override // android.widget.PopupWindow
    public void dismiss() {
      if (this.mIsUsingDismissAnimation && !this.mIsDismissing) {
        animateViewOut();
      } else {
        super.dismiss();
      }
    }

    void dismissFinal() {
      super.dismiss();
    }

    void animateViewOut() {}
  }

  private static class TipWindowBubble extends TipWindow {
    private TipWindowBubble(View contentView, int width, int height, boolean focusable) {
      super(contentView, width, height, focusable);
    }

    @Override // com.samsung.android.widget.SemTipPopup.TipWindow
    protected void animateViewOut() {
      AnimationSet animationSet = new AnimationSet(true);
      Animation animScale =
          new ScaleAnimation(1.0f, 0.81f, 1.0f, 0.81f, 0, this.mPivotX, 0, this.mPivotY);
      animScale.setInterpolator(SemTipPopup.INTERPOLATOR_ELASTIC_CUSTOM);
      animScale.setDuration(167L);
      Animation animAlpha = new AlphaAnimation(1.0f, 0.0f);
      animAlpha.setInterpolator(SemTipPopup.INTERPOLATOR_SINE_IN_OUT_33);
      animAlpha.setDuration(167L);
      animationSet.addAnimation(animScale);
      animationSet.addAnimation(animAlpha);
      animationSet.setAnimationListener(
          new Animation
              .AnimationListener() { // from class:
                                     // com.samsung.android.widget.SemTipPopup.TipWindowBubble.1
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
              TipWindowBubble.this.mIsDismissing = true;
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
              TipWindowBubble.this.dismissFinal();
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {}
          });
      getContentView().startAnimation(animationSet);
    }
  }

  private static class TipWindowBalloon extends TipWindow {
    private TipWindowBalloon(View contentView, int width, int height, boolean focusable) {
      super(contentView, width, height, focusable);
    }

    @Override // com.samsung.android.widget.SemTipPopup.TipWindow
    protected void animateViewOut() {
      View bubbleView = getContentView();
      View messageView = bubbleView.findViewById(C4337R.id.sem_tip_popup_message);
      Animation animScale =
          new ScaleAnimation(1.0f, 0.32f, 1.0f, 0.32f, 0, this.mPivotX, 0, this.mPivotY);
      animScale.setInterpolator(SemTipPopup.INTERPOLATOR_ELASTIC_CUSTOM);
      animScale.setDuration(500L);
      Animation animAlpha = new AlphaAnimation(1.0f, 0.0f);
      animAlpha.setDuration(500L);
      AnimationSet animationSet = new AnimationSet(true);
      animationSet.addAnimation(animAlpha);
      animationSet.addAnimation(animScale);
      animationSet.setAnimationListener(
          new Animation
              .AnimationListener() { // from class:
                                     // com.samsung.android.widget.SemTipPopup.TipWindowBalloon.1
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
              TipWindowBalloon.this.mIsDismissing = true;
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
              TipWindowBalloon.this.dismissFinal();
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {}
          });
      bubbleView.startAnimation(animationSet);
      messageView.startAnimation(animAlpha);
    }
  }

  private boolean isRTL() {
    return this.mContext.getResources().getConfiguration().getLayoutDirection() == 1;
  }

  private String getLocale() {
    return this.mContext.getResources().getConfiguration().getLocales().get(0).toString();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void debugLog(String msg) {
    Log.m94d(TAG, " #### " + msg);
  }

  public PopupWindow semGetBubblePopupWindow() {
    return this.mBubblePopup;
  }

  public PopupWindow semGetBalloonPopupWindow() {
    return this.mBalloonPopup;
  }
}
