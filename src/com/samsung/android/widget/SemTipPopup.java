package com.samsung.android.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
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
import com.android.internal.R;
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
    private static Interpolator INTERPOLATOR_ELASTIC_50 = null;
    private static Interpolator INTERPOLATOR_ELASTIC_CUSTOM = null;
    private static Interpolator INTERPOLATOR_SINE_IN_OUT_33 = null;
    private static Interpolator INTERPOLATOR_SINE_IN_OUT_70 = null;
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

    public interface OnDismissListener {
        void onDismiss();
    }

    public interface OnStateChangeListener {
        void onStateChanged(int i);
    }

    public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener) {
        this.mOnStateChangeListener = onStateChangeListener;
    }

    public SemTipPopup(View view) {
        this(view, 0);
    }

    public SemTipPopup(View view, int i) {
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
        if (i < 0 || i > 1) {
            throw new IllegalArgumentException("Invalid SmartTip mode : " + i + " ,mode can either be 0 (MODE_NORMAL) or 1 (MODE_TRANSLUCENT)");
        }
        Context context = view.getContext();
        this.mContext = context;
        Resources resources = context.getResources();
        this.mResources = resources;
        this.mParentView = view;
        this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        this.mDisplayMetrics = resources.getDisplayMetrics();
        debugLog("mDisplayMetrics = " + this.mDisplayMetrics);
        this.mState = 1;
        this.mType = 0;
        this.mMode = i;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes((AttributeSet) null, R.styleable.SemTipPopup);
        this.mBackgroundColor = obtainStyledAttributes.getColor(0, -16777216);
        obtainStyledAttributes.recycle();
        initInterpolator();
        LayoutInflater from = LayoutInflater.from(context);
        this.mBubbleView = from.inflate(R.layout.sem_tip_popup_bubble, (ViewGroup) null);
        View inflate = from.inflate(R.layout.sem_tip_popup_balloon, (ViewGroup) null);
        this.mBalloonView = inflate;
        initBubblePopup(i);
        initBalloonPopup(i);
        TextView textView = (TextView) inflate.findViewById(R.id.sem_tip_popup_message);
        this.mMessageView = textView;
        Button button = (Button) inflate.findViewById(R.id.sem_tip_popup_action);
        this.mActionView = button;
        textView.setVisibility(8);
        button.setVisibility(8);
        this.mArrowPositionX = -1;
        this.mArrowPositionY = -1;
        this.mArrowDirection = -1;
        this.mBalloonX = -1;
        if (i == 1) {
            textView.setTextColor(resources.getColor(R.color.sem_tip_popup_text_color_translucent, null));
            button.setTextColor(resources.getColor(R.color.sem_tip_popup_text_color_translucent, null));
        }
        this.mScaleMargin = resources.getDimensionPixelSize(R.dimen.sem_tip_popup_scale_margin);
        this.mSideMargin = resources.getDimensionPixelSize(R.dimen.sem_tip_popup_side_margin);
        this.mArrowHeight = resources.getDimensionPixelSize(R.dimen.sem_tip_popup_balloon_arrow_height);
        this.mArrowWidth = resources.getDimensionPixelSize(R.dimen.sem_tip_popup_balloon_arrow_width);
        this.mHorizontalTextMargin = resources.getDimensionPixelSize(R.dimen.sem_tip_popup_balloon_message_margin_horizontal);
        this.mVerticalTextMargin = resources.getDimensionPixelSize(R.dimen.sem_tip_popup_balloon_message_margin_vertical);
        this.mDisplayFrame = new Rect();
        this.mBubblePopup.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.samsung.android.widget.SemTipPopup.1
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
        this.mBalloonPopup.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.samsung.android.widget.SemTipPopup.2
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
        inflate.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.samsung.android.widget.SemTipPopup.3
            @Override // android.view.View.AccessibilityDelegate
            public void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfo);
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, SemTipPopup.this.mContext.getString(R.string.smart_tip_action_click_hint_text)));
            }
        });
    }

    private void initInterpolator() {
        if (INTERPOLATOR_SINE_IN_OUT_33 == null) {
            INTERPOLATOR_SINE_IN_OUT_33 = AnimationUtils.loadInterpolator(this.mContext, R.interpolator.sine_in_out_33);
        }
        if (INTERPOLATOR_SINE_IN_OUT_70 == null) {
            INTERPOLATOR_SINE_IN_OUT_70 = AnimationUtils.loadInterpolator(this.mContext, R.interpolator.sine_in_out_70);
        }
        if (INTERPOLATOR_ELASTIC_50 == null) {
            INTERPOLATOR_ELASTIC_50 = new ElasticCustom(1.0f, 0.7f);
        }
        if (INTERPOLATOR_ELASTIC_CUSTOM == null) {
            INTERPOLATOR_ELASTIC_CUSTOM = new ElasticCustom(1.0f, 1.3f);
        }
    }

    private void initBubblePopup(int i) {
        this.mBubbleBackground = (ImageView) this.mBubbleView.findViewById(R.id.sem_tip_popup_bubble_bg);
        this.mBubbleIcon = (ImageView) this.mBubbleView.findViewById(R.id.sem_tip_popup_bubble_icon);
        if (i == 1) {
            this.mBubbleBackground.setImageResource(R.drawable.sem_tip_popup_hint_background_translucent);
            this.mBubbleBackground.setImageTintList(null);
            if (isRTL() && isMirroringSupportedInRTL()) {
                this.mBubbleIcon.setImageResource(R.drawable.sem_tip_popup_hint_icon_translucent_rtl);
            } else {
                this.mBubbleIcon.setImageResource(R.drawable.sem_tip_popup_hint_icon_translucent);
            }
            this.mBubbleIcon.setImageTintList(null);
            this.mBubbleWidth = this.mResources.getDimensionPixelSize(R.dimen.sem_tip_popup_bubble_width_translucent);
            this.mBubbleHeight = this.mResources.getDimensionPixelSize(R.dimen.sem_tip_popup_bubble_height_translucent);
        } else {
            this.mBubbleWidth = this.mResources.getDimensionPixelSize(R.dimen.sem_tip_popup_bubble_width);
            this.mBubbleHeight = this.mResources.getDimensionPixelSize(R.dimen.sem_tip_popup_bubble_height);
        }
        TipWindowBubble tipWindowBubble = new TipWindowBubble(this.mBubbleView, this.mBubbleWidth, this.mBubbleHeight, false);
        this.mBubblePopup = tipWindowBubble;
        tipWindowBubble.setTouchable(true);
        this.mBubblePopup.setOutsideTouchable(true);
        this.mBubblePopup.setAttachedInDecor(false);
    }

    private void initBalloonPopup(int i) {
        this.mBalloonBubble = (FrameLayout) this.mBalloonView.findViewById(R.id.sem_tip_popup_balloon_bubble);
        this.mBalloonBubbleHint = (ImageView) this.mBalloonView.findViewById(R.id.sem_tip_popup_balloon_bubble_hint);
        this.mBalloonBubbleIcon = (ImageView) this.mBalloonView.findViewById(R.id.sem_tip_popup_balloon_bubble_icon);
        this.mBalloonPanel = (FrameLayout) this.mBalloonView.findViewById(R.id.sem_tip_popup_balloon_panel);
        this.mBalloonContent = (FrameLayout) this.mBalloonView.findViewById(R.id.sem_tip_popup_balloon_content);
        this.mBalloonBg1 = (ImageView) this.mBalloonView.findViewById(R.id.sem_tip_popup_balloon_bg_01);
        this.mBalloonBg2 = (ImageView) this.mBalloonView.findViewById(R.id.sem_tip_popup_balloon_bg_02);
        if (i == 1) {
            this.mBalloonBg1.setBackgroundResource(R.drawable.sem_tip_popup_balloon_background_left_translucent);
            this.mBalloonBg1.setBackgroundTintList(null);
            this.mBalloonBg2.setBackgroundResource(R.drawable.sem_tip_popup_balloon_background_right_translucent);
            this.mBalloonBg2.setBackgroundTintList(null);
        }
        this.mBalloonBubble.setVisibility(0);
        this.mBalloonPanel.setVisibility(8);
        TipWindowBalloon tipWindowBalloon = new TipWindowBalloon(this.mBalloonView, this.mBalloonWidth, this.mBalloonHeight, true);
        this.mBalloonPopup = tipWindowBalloon;
        tipWindowBalloon.setFocusable(true);
        this.mBalloonPopup.setTouchable(true);
        this.mBalloonPopup.setOutsideTouchable(true);
        this.mBalloonPopup.setAttachedInDecor(false);
        this.mBalloonPopup.setTouchInterceptor(new View.OnTouchListener() { // from class: com.samsung.android.widget.SemTipPopup.4
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (SemTipPopup.this.mNeedToCallParentViewsOnClick && SemTipPopup.this.mParentView.hasOnClickListeners() && (motionEvent.getAction() == 0 || motionEvent.getAction() == 4)) {
                    Rect rect = new Rect();
                    int[] iArr = new int[2];
                    SemTipPopup.this.mParentView.getLocationOnScreen(iArr);
                    int i2 = iArr[0];
                    rect.set(i2, iArr[1], SemTipPopup.this.mParentView.getWidth() + i2, iArr[1] + SemTipPopup.this.mParentView.getHeight());
                    if (rect.contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY())) {
                        SemTipPopup.this.debugLog("callOnClick for parent view");
                        SemTipPopup.this.mParentView.callOnClick();
                    }
                }
                return false;
            }
        });
    }

    public void show(int i) {
        setInternal();
        if (this.mArrowPositionX == -1 || this.mArrowPositionY == -1) {
            calculateArrowPosition();
        }
        if (i == -1) {
            calculateArrowDirection(this.mArrowPositionX, this.mArrowPositionY);
        } else {
            this.mArrowDirection = i;
        }
        calculatePopupSize();
        calculatePopupPosition();
        setBubblePanel();
        setBalloonPanel();
        showInternal();
    }

    public void setMessage(CharSequence charSequence) {
        this.mMessageText = charSequence;
    }

    public void setAction(CharSequence charSequence, View.OnClickListener onClickListener) {
        this.mActionText = charSequence;
        this.mActionClickListener = onClickListener;
    }

    public void semCallParentViewsOnClick(boolean z) {
        this.mNeedToCallParentViewsOnClick = z;
    }

    public boolean isShowing() {
        TipWindow tipWindow = this.mBubblePopup;
        boolean isShowing = tipWindow != null ? tipWindow.isShowing() : false;
        TipWindow tipWindow2 = this.mBalloonPopup;
        return isShowing || (tipWindow2 != null ? tipWindow2.isShowing() : false);
    }

    public void dismiss(boolean z) {
        TipWindow tipWindow = this.mBubblePopup;
        if (tipWindow != null) {
            tipWindow.setUseDismissAnimation(z);
            debugLog("mBubblePopup.mIsDismissing = " + this.mBubblePopup.mIsDismissing);
            this.mBubblePopup.dismiss();
        }
        TipWindow tipWindow2 = this.mBalloonPopup;
        if (tipWindow2 != null) {
            tipWindow2.setUseDismissAnimation(z);
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

    public void setExpanded(boolean z) {
        if (z) {
            this.mState = 2;
            this.mScaleMargin = 0;
        } else {
            this.mScaleMargin = this.mResources.getDimensionPixelSize(R.dimen.sem_tip_popup_scale_margin);
        }
    }

    public void setTargetPosition(int i, int i2) {
        if (i < 0 || i2 < 0) {
            return;
        }
        this.mIsDefaultPosition = false;
        this.mArrowPositionX = i;
        this.mArrowPositionY = i2;
    }

    public void setHintDescription(CharSequence charSequence) {
        this.mHintDescription = charSequence;
    }

    public void update() {
        update(this.mArrowDirection, false);
    }

    public void update(int i, boolean z) {
        TipWindow tipWindow;
        TipWindow tipWindow2;
        if (!isShowing() || this.mParentView == null) {
            return;
        }
        this.mDisplayMetrics = this.mResources.getDisplayMetrics();
        debugLog("update - mDisplayMetrics = " + this.mDisplayMetrics);
        setInternal();
        this.mBalloonX = -1;
        this.mBalloonY = -1;
        if (this.mIsDefaultPosition) {
            debugLog("update - default position");
            calculateArrowPosition();
        }
        if (i == -1) {
            calculateArrowDirection(this.mArrowPositionX, this.mArrowPositionY);
        } else {
            this.mArrowDirection = i;
        }
        calculatePopupSize();
        calculatePopupPosition();
        setBubblePanel();
        setBalloonPanel();
        int i2 = this.mState;
        if (i2 != 1 || (tipWindow2 = this.mBubblePopup) == null) {
            if (i2 != 2 || (tipWindow = this.mBalloonPopup) == null) {
                return;
            }
            tipWindow.update(this.mBalloonPopupX, this.mBalloonPopupY, tipWindow.getWidth(), this.mBalloonPopup.getHeight());
            return;
        }
        tipWindow2.update(this.mBubblePopupX, this.mBubblePopupY, tipWindow2.getWidth(), this.mBubblePopup.getHeight());
        if (z) {
            debugLog("Timer Reset!");
            scheduleTimeout();
        }
    }

    public void setMessageTextColor(int i) {
        this.mMessageTextColor = Integer.valueOf(i | (-16777216));
    }

    public void setActionTextColor(int i) {
        this.mActionTextColor = Integer.valueOf(i | (-16777216));
    }

    public void setBackgroundColor(int i) {
        this.mBackgroundColor = i | (-16777216);
    }

    public void setBackgroundColorWithAlpha(int i) {
        this.mBackgroundColor = i;
    }

    public void setBorderColor(int i) {
        this.mBorderColor = Integer.valueOf(i | (-16777216));
    }

    public void setOutsideTouchEnabled(boolean z) {
        this.mBubblePopup.setFocusable(z);
        this.mBubblePopup.setOutsideTouchable(z);
        this.mBalloonPopup.setFocusable(z);
        this.mBalloonPopup.setOutsideTouchable(z);
        debugLog("outside enabled : " + z);
    }

    public void setPopupWindowClippingEnabled(boolean z) {
        this.mBubblePopup.setClippingEnabled(z);
        this.mBalloonPopup.setClippingEnabled(z);
        this.mForceRealDisplay = !z;
        this.mSideMargin = z ? this.mResources.getDimensionPixelSize(R.dimen.sem_tip_popup_side_margin) : 0;
        debugLog("clipping enabled : " + z);
    }

    private void setInternal() {
        CharSequence charSequence;
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper()) { // from class: com.samsung.android.widget.SemTipPopup.5
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    int i = message.what;
                    if (i == 0) {
                        SemTipPopup.this.dismissBubble(true);
                    } else if (i == 1) {
                        SemTipPopup.this.dismissBubble(false);
                    } else {
                        if (i != 2) {
                            return;
                        }
                        SemTipPopup.this.animateScaleUp();
                    }
                }
            };
        }
        if (this.mMessageView == null || this.mActionView == null) {
            return;
        }
        float f = this.mResources.getConfiguration().fontScale;
        int dimensionPixelOffset = this.mResources.getDimensionPixelOffset(R.dimen.sem_tip_popup_balloon_message_text_size);
        int dimensionPixelOffset2 = this.mResources.getDimensionPixelOffset(R.dimen.sem_tip_popup_balloon_action_text_size);
        if (f > 1.2f) {
            double d = 1.2f;
            this.mMessageView.setTextSize(0, (float) Math.floor(Math.ceil(dimensionPixelOffset / f) * d));
            this.mActionView.setTextSize(0, (float) Math.floor(Math.ceil(dimensionPixelOffset2 / f) * d));
        }
        this.mMessageView.lambda$setTextAsync$0(this.mMessageText);
        if (TextUtils.isEmpty(this.mActionText) || this.mActionClickListener == null) {
            this.mActionView.setVisibility(8);
            this.mActionView.setOnClickListener(null);
            this.mType = 0;
        } else {
            this.mActionView.setVisibility(0);
            this.mActionView.semSetButtonShapeEnabled(true, this.mBackgroundColor);
            this.mActionView.lambda$setTextAsync$0(this.mActionText);
            this.mActionView.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.widget.SemTipPopup.6
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
        if (this.mMode == 1 || this.mBubbleIcon == null || this.mBubbleBackground == null || this.mBalloonBubble == null || this.mBalloonBg1 == null || this.mBalloonBg2 == null) {
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
            this.mBubbleView.setOnTouchListener(new View.OnTouchListener() { // from class: com.samsung.android.widget.SemTipPopup.7
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    SemTipPopup.this.mState = 2;
                    if (SemTipPopup.this.mOnStateChangeListener != null) {
                        SemTipPopup.this.mOnStateChangeListener.onStateChanged(SemTipPopup.this.mState);
                    }
                    if (SemTipPopup.this.mBalloonPopup != null) {
                        SemTipPopup.this.mBalloonPopup.showAtLocation(SemTipPopup.this.mParentView, 0, SemTipPopup.this.mBalloonPopupX, SemTipPopup.this.mBalloonPopupY);
                    }
                    if (SemTipPopup.mHandler != null) {
                        SemTipPopup.mHandler.removeMessages(0);
                        SemTipPopup.mHandler.sendMessageDelayed(Message.obtain(SemTipPopup.mHandler, 1), 10L);
                        SemTipPopup.mHandler.sendMessageDelayed(Message.obtain(SemTipPopup.mHandler, 2), 20L);
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
        this.mBalloonView.setOnTouchListener(new View.OnTouchListener() { // from class: com.samsung.android.widget.SemTipPopup.8
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (SemTipPopup.this.mType != 0) {
                    return false;
                }
                SemTipPopup.this.dismiss(true);
                return false;
            }
        });
    }

    private void setBubblePanel() {
        if (this.mBubblePopup == null) {
            return;
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mBubbleBackground.getLayoutParams();
        if (this.mMode == 1) {
            layoutParams.width = this.mResources.getDimensionPixelSize(R.dimen.sem_tip_popup_bubble_width_translucent);
            layoutParams.height = this.mResources.getDimensionPixelSize(R.dimen.sem_tip_popup_bubble_height_translucent);
        }
        int i = this.mArrowDirection;
        if (i == 0) {
            this.mBubblePopup.setPivot(r1.getWidth(), this.mBubblePopup.getHeight());
            layoutParams.gravity = 85;
            int i2 = this.mBubbleX;
            int i3 = this.mScaleMargin;
            this.mBubblePopupX = i2 - (i3 * 2);
            this.mBubblePopupY = this.mBubbleY - (i3 * 2);
            if (this.mMode == 0) {
                this.mBubbleBackground.setImageResource(R.drawable.sem_tip_popup_hint_background_03);
                if (isRTL() && isMirroringSupportedInRTL()) {
                    this.mBubbleIcon.setImageResource(R.drawable.sem_tip_popup_hint_icon_rtl);
                } else {
                    this.mBubbleIcon.setImageResource(R.drawable.sem_tip_popup_hint_icon);
                }
            } else {
                this.mBubbleBackground.setRotationX(180.0f);
            }
        } else if (i == 1) {
            this.mBubblePopup.setPivot(0.0f, r1.getHeight());
            layoutParams.gravity = 83;
            this.mBubblePopupX = this.mBubbleX;
            this.mBubblePopupY = this.mBubbleY - (this.mScaleMargin * 2);
            if (this.mMode == 0) {
                this.mBubbleBackground.setImageResource(R.drawable.sem_tip_popup_hint_background_04);
                if (isRTL() && isMirroringSupportedInRTL()) {
                    this.mBubbleIcon.setImageResource(R.drawable.sem_tip_popup_hint_icon_rtl);
                } else {
                    this.mBubbleIcon.setImageResource(R.drawable.sem_tip_popup_hint_icon);
                }
            } else {
                this.mBubbleBackground.setRotation(180.0f);
            }
        } else if (i == 2) {
            this.mBubblePopup.setPivot(r1.getWidth(), 0.0f);
            layoutParams.gravity = 53;
            this.mBubblePopupX = this.mBubbleX - (this.mScaleMargin * 2);
            this.mBubblePopupY = this.mBubbleY;
            if (this.mMode == 0) {
                this.mBubbleBackground.setImageResource(R.drawable.sem_tip_popup_hint_background_01);
                if (isRTL() && isMirroringSupportedInRTL()) {
                    this.mBubbleIcon.setImageResource(R.drawable.sem_tip_popup_hint_icon_rtl);
                } else {
                    this.mBubbleIcon.setImageResource(R.drawable.sem_tip_popup_hint_icon);
                }
            }
        } else if (i == 3) {
            this.mBubblePopup.setPivot(0.0f, 0.0f);
            layoutParams.gravity = 51;
            this.mBubblePopupX = this.mBubbleX;
            this.mBubblePopupY = this.mBubbleY;
            if (this.mMode == 0) {
                this.mBubbleBackground.setImageResource(R.drawable.sem_tip_popup_hint_background_02);
                if (isRTL() && isMirroringSupportedInRTL()) {
                    this.mBubbleIcon.setImageResource(R.drawable.sem_tip_popup_hint_icon_rtl);
                } else {
                    this.mBubbleIcon.setImageResource(R.drawable.sem_tip_popup_hint_icon);
                }
            } else {
                this.mBubbleBackground.setRotationY(180.0f);
            }
        }
        this.mBubbleBackground.setLayoutParams(layoutParams);
        this.mBubbleIcon.setLayoutParams(layoutParams);
        this.mBubblePopup.setWidth(this.mBubbleWidth + (this.mScaleMargin * 2));
        this.mBubblePopup.setHeight(this.mBubbleHeight + (this.mScaleMargin * 2));
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0130  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0260  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void setBalloonPanel() {
        /*
            Method dump skipped, instructions count: 835
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.widget.SemTipPopup.setBalloonPanel():void");
    }

    private void calculateArrowDirection(int i, int i2) {
        View view = this.mParentView;
        if (view != null && this.mIsDefaultPosition) {
            int[] iArr = new int[2];
            view.getLocationInWindow(iArr);
            int height = iArr[1] + (this.mParentView.getHeight() / 2);
            if (i * 2 <= this.mDisplayMetrics.widthPixels) {
                if (i2 <= height) {
                    this.mArrowDirection = 1;
                } else {
                    this.mArrowDirection = 3;
                }
            } else if (i2 <= height) {
                this.mArrowDirection = 0;
            } else {
                this.mArrowDirection = 2;
            }
        } else {
            int i3 = i * 2;
            if (i3 <= this.mDisplayMetrics.widthPixels && i2 * 2 <= this.mDisplayMetrics.heightPixels) {
                this.mArrowDirection = 3;
            } else if (i3 > this.mDisplayMetrics.widthPixels && i2 * 2 <= this.mDisplayMetrics.heightPixels) {
                this.mArrowDirection = 2;
            } else if (i3 <= this.mDisplayMetrics.widthPixels && i2 * 2 > this.mDisplayMetrics.heightPixels) {
                this.mArrowDirection = 1;
            } else if (i3 > this.mDisplayMetrics.widthPixels && i2 * 2 > this.mDisplayMetrics.heightPixels) {
                this.mArrowDirection = 0;
            }
        }
        debugLog("calculateArrowDirection : arrow position (" + i + ", " + i2 + ") / mArrowDirection = " + this.mArrowDirection);
    }

    private void calculateArrowPosition() {
        View view = this.mParentView;
        if (view == null) {
            this.mArrowPositionX = 0;
            this.mArrowPositionY = 0;
            return;
        }
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        debugLog("calculateArrowPosition anchor location : " + iArr[0] + ", " + iArr[1]);
        int width = iArr[0] + (this.mParentView.getWidth() / 2);
        int height = iArr[1] + (this.mParentView.getHeight() / 2);
        if (height * 2 <= this.mDisplayMetrics.heightPixels) {
            this.mArrowPositionY = height + (this.mParentView.getHeight() / 2);
        } else {
            this.mArrowPositionY = height - (this.mParentView.getHeight() / 2);
        }
        this.mArrowPositionX = width;
        debugLog("calculateArrowPosition mArrowPosition : " + this.mArrowPositionX + ", " + this.mArrowPositionY);
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0069, code lost:
    
        if (r0 <= 1280) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void calculatePopupSize() {
        /*
            Method dump skipped, instructions count: 284
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.widget.SemTipPopup.calculatePopupSize():void");
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
            if (this.mArrowPositionX < this.mDisplayFrame.left + this.mSideMargin + this.mHorizontalTextMargin) {
                debugLog("Target position is too far to the left!");
                this.mArrowPositionX = this.mDisplayFrame.left + this.mSideMargin + this.mHorizontalTextMargin;
            } else if (this.mArrowPositionX > ((this.mDisplayFrame.right - this.mSideMargin) - this.mHorizontalTextMargin) - this.mArrowWidth) {
                debugLog("Target position is too far to the right!");
                this.mArrowPositionX = ((this.mDisplayFrame.right - this.mSideMargin) - this.mHorizontalTextMargin) - this.mArrowWidth;
            }
        } else if (this.mArrowPositionX < this.mDisplayFrame.left + this.mSideMargin + this.mHorizontalTextMargin + this.mArrowWidth) {
            debugLog("Target position is too far to the left!");
            this.mArrowPositionX = this.mDisplayFrame.left + this.mSideMargin + this.mHorizontalTextMargin + this.mArrowWidth;
        } else if (this.mArrowPositionX > (this.mDisplayFrame.right - this.mSideMargin) - this.mHorizontalTextMargin) {
            debugLog("Target position is too far to the right!");
            this.mArrowPositionX = (this.mDisplayFrame.right - this.mSideMargin) - this.mHorizontalTextMargin;
        }
        if (this.mContext.getResources().getConfiguration().semDesktopModeEnabled == 1) {
            int measuredWidth = this.mParentView.getRootView().getMeasuredWidth();
            int[] iArr = new int[2];
            this.mParentView.getRootView().getLocationOnScreen(iArr);
            int i3 = iArr[0];
            if (i3 < 0) {
                measuredWidth += i3;
            }
            int i4 = this.mBalloonX;
            int i5 = this.mDisplayFrame.left;
            int i6 = this.mSideMargin;
            if (i4 < i5 + i6) {
                this.mBalloonX = this.mDisplayFrame.left + this.mSideMargin;
            } else {
                int i7 = this.mBalloonX;
                int i8 = this.mBalloonWidth;
                if (i7 + i8 > measuredWidth - i6) {
                    int i9 = (measuredWidth - i6) - i8;
                    this.mBalloonX = i9;
                    int i10 = iArr[0];
                    if (i10 < 0) {
                        this.mBalloonX = i9 - i10;
                    }
                }
            }
        } else if (this.mBalloonX < this.mDisplayFrame.left + this.mSideMargin) {
            this.mBalloonX = this.mDisplayFrame.left + this.mSideMargin;
        } else if (this.mBalloonX + this.mBalloonWidth > this.mDisplayFrame.right - this.mSideMargin) {
            this.mBalloonX = (this.mDisplayFrame.right - this.mSideMargin) - this.mBalloonWidth;
        }
        int i11 = this.mArrowDirection;
        if (i11 == 0) {
            this.mBubbleX = this.mArrowPositionX - this.mBubbleWidth;
            int i12 = this.mArrowPositionY;
            this.mBubbleY = i12 - this.mBubbleHeight;
            this.mBalloonY = i12 - this.mBalloonHeight;
        } else if (i11 == 1) {
            this.mBubbleX = this.mArrowPositionX;
            int i13 = this.mArrowPositionY;
            this.mBubbleY = i13 - this.mBubbleHeight;
            this.mBalloonY = i13 - this.mBalloonHeight;
        } else if (i11 == 2) {
            this.mBubbleX = this.mArrowPositionX - this.mBubbleWidth;
            int i14 = this.mArrowPositionY;
            this.mBubbleY = i14;
            this.mBalloonY = i14;
        } else if (i11 == 3) {
            this.mBubbleX = this.mArrowPositionX;
            int i15 = this.mArrowPositionY;
            this.mBubbleY = i15;
            this.mBalloonY = i15;
        }
        debugLog("QuestionPopup : " + this.mBubbleX + ", " + this.mBubbleY + ", " + this.mBubbleWidth + ", " + this.mBubbleHeight);
        debugLog("BalloonPopup : " + this.mBalloonX + ", " + this.mBalloonY + ", " + this.mBalloonWidth + ", " + this.mBalloonHeight);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissBubble(boolean z) {
        TipWindow tipWindow = this.mBubblePopup;
        if (tipWindow != null) {
            tipWindow.setUseDismissAnimation(z);
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
        float f;
        float f2;
        int i = this.mArrowDirection;
        if (i != 0) {
            if (i == 1) {
                f2 = 1.0f;
                f = 0.0f;
            } else if (i != 2) {
                f = 0.0f;
            } else {
                f = 1.0f;
                f2 = 0.0f;
            }
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 1, f, 1, f2);
            scaleAnimation.setInterpolator(INTERPOLATOR_ELASTIC_50);
            scaleAnimation.setDuration(500L);
            scaleAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.samsung.android.widget.SemTipPopup.9
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    SemTipPopup.this.scheduleTimeout();
                    SemTipPopup.this.animateBounce();
                }
            });
            this.mBubbleView.startAnimation(scaleAnimation);
        }
        f = 1.0f;
        f2 = f;
        ScaleAnimation scaleAnimation2 = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 1, f, 1, f2);
        scaleAnimation2.setInterpolator(INTERPOLATOR_ELASTIC_50);
        scaleAnimation2.setDuration(500L);
        scaleAnimation2.setAnimationListener(new Animation.AnimationListener() { // from class: com.samsung.android.widget.SemTipPopup.9
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                SemTipPopup.this.scheduleTimeout();
                SemTipPopup.this.animateBounce();
            }
        });
        this.mBubbleView.startAnimation(scaleAnimation2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animateBounce() {
        float width;
        int height;
        float f;
        float f2;
        int i = this.mArrowDirection;
        if (i != 0) {
            width = 0.0f;
            if (i == 1) {
                height = this.mBubblePopup.getHeight();
            } else {
                if (i != 2) {
                    f2 = 0.0f;
                    f = 0.0f;
                } else {
                    f2 = this.mBubblePopup.getWidth();
                    f = 0.0f;
                }
                final AnimationSet animationSet = new AnimationSet(false);
                ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, 0, f2, 0, f);
                scaleAnimation.setDuration(167L);
                scaleAnimation.setInterpolator(INTERPOLATOR_SINE_IN_OUT_70);
                ScaleAnimation scaleAnimation2 = new ScaleAnimation(1.0f, 0.833f, 1.0f, 0.833f, 0, f2, 0, f);
                scaleAnimation2.setStartOffset(167L);
                scaleAnimation2.setDuration(250L);
                scaleAnimation2.setInterpolator(INTERPOLATOR_SINE_IN_OUT_33);
                scaleAnimation2.setAnimationListener(new Animation.AnimationListener() { // from class: com.samsung.android.widget.SemTipPopup.10
                    int count = 0;

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationStart(Animation animation) {
                        this.count++;
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationEnd(Animation animation) {
                        SemTipPopup.this.debugLog("repeat count " + this.count);
                        SemTipPopup.this.mBubbleView.startAnimation(animationSet);
                    }
                });
                animationSet.addAnimation(scaleAnimation);
                animationSet.addAnimation(scaleAnimation2);
                animationSet.setStartOffset(3000L);
                this.mBubbleView.startAnimation(animationSet);
            }
        } else {
            width = this.mBubblePopup.getWidth();
            height = this.mBubblePopup.getHeight();
        }
        f = height;
        f2 = width;
        final AnimationSet animationSet2 = new AnimationSet(false);
        ScaleAnimation scaleAnimation3 = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, 0, f2, 0, f);
        scaleAnimation3.setDuration(167L);
        scaleAnimation3.setInterpolator(INTERPOLATOR_SINE_IN_OUT_70);
        ScaleAnimation scaleAnimation22 = new ScaleAnimation(1.0f, 0.833f, 1.0f, 0.833f, 0, f2, 0, f);
        scaleAnimation22.setStartOffset(167L);
        scaleAnimation22.setDuration(250L);
        scaleAnimation22.setInterpolator(INTERPOLATOR_SINE_IN_OUT_33);
        scaleAnimation22.setAnimationListener(new Animation.AnimationListener() { // from class: com.samsung.android.widget.SemTipPopup.10
            int count = 0;

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
                this.count++;
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                SemTipPopup.this.debugLog("repeat count " + this.count);
                SemTipPopup.this.mBubbleView.startAnimation(animationSet2);
            }
        });
        animationSet2.addAnimation(scaleAnimation3);
        animationSet2.addAnimation(scaleAnimation22);
        animationSet2.setStartOffset(3000L);
        this.mBubbleView.startAnimation(animationSet2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animateScaleUp() {
        float width;
        float height;
        float f;
        int i = this.mArrowDirection;
        if (i == 0) {
            width = this.mBalloonBubble.getWidth();
            height = this.mBalloonBubble.getHeight();
            f = 0.0f - (this.mArrowHeight / 2.0f);
        } else if (i == 1) {
            height = this.mBalloonBubble.getHeight();
            f = 0.0f - (this.mArrowHeight / 2.0f);
            width = 0.0f;
        } else if (i == 2) {
            width = this.mBalloonBubble.getWidth();
            f = this.mArrowHeight / 2.0f;
            height = 0.0f;
        } else if (i != 3) {
            width = 0.0f;
            height = 0.0f;
            f = 0.0f;
        } else {
            f = this.mArrowHeight / 2.0f;
            width = 0.0f;
            height = 0.0f;
        }
        AnimationSet animationSet = new AnimationSet(false);
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0.0f, 0, 0.0f, 0, 0.0f, 0, f);
        translateAnimation.setDuration(500L);
        translateAnimation.setInterpolator(INTERPOLATOR_ELASTIC_CUSTOM);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f, 0, width, 0, height);
        scaleAnimation.setDuration(500L);
        scaleAnimation.setInterpolator(INTERPOLATOR_ELASTIC_50);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(167L);
        alphaAnimation.setInterpolator(INTERPOLATOR_SINE_IN_OUT_70);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setAnimationListener(new Animation.AnimationListener() { // from class: com.samsung.android.widget.SemTipPopup.11
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
                SemTipPopup.this.mBalloonPanel.setVisibility(0);
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                SemTipPopup.this.mBalloonBubble.setVisibility(8);
            }
        });
        this.mBalloonBubble.startAnimation(animationSet);
        animateBaloonScaleUp();
    }

    private void animateBaloonScaleUp() {
        float f;
        float f2;
        int i;
        int i2;
        float dimensionPixelSize = this.mResources.getDimensionPixelSize(R.dimen.sem_tip_popup_bubble_height);
        int i3 = this.mBalloonHeight;
        float f3 = dimensionPixelSize / i3;
        int i4 = this.mArrowDirection;
        if (i4 != 1) {
            if (i4 == 2) {
                i = this.mArrowPositionX;
                i2 = this.mBalloonX;
            } else if (i4 != 3) {
                f = 0.0f;
                f2 = 0.0f;
            } else {
                i = this.mBubbleX;
                i2 = this.mBalloonX;
            }
            f = i - i2;
            f2 = 0.0f;
        } else {
            f = this.mArrowPositionX - this.mBalloonX;
            f2 = i3;
        }
        AnimationSet animationSet = new AnimationSet(false);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.32f, 1.0f, f3, 1.0f, 0, f, 0, f2);
        scaleAnimation.setInterpolator(INTERPOLATOR_ELASTIC_CUSTOM);
        scaleAnimation.setDuration(500L);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setInterpolator(INTERPOLATOR_SINE_IN_OUT_70);
        alphaAnimation.setDuration(83L);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        this.mBalloonPanel.startAnimation(animationSet);
        AlphaAnimation alphaAnimation2 = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation2.setInterpolator(INTERPOLATOR_SINE_IN_OUT_33);
        alphaAnimation2.setStartOffset(333L);
        alphaAnimation2.setDuration(167L);
        alphaAnimation2.setAnimationListener(new Animation.AnimationListener() { // from class: com.samsung.android.widget.SemTipPopup.12
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
                SemTipPopup.this.mMessageView.setVisibility(0);
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                SemTipPopup.this.dismissBubble(false);
            }
        });
        this.mMessageView.startAnimation(alphaAnimation2);
        this.mActionView.startAnimation(alphaAnimation2);
    }

    private boolean isNavigationbarHide() {
        Context context = this.mContext;
        return context != null && Settings.Global.getInt(context.getContentResolver(), "navigationbar_hide_bar_enabled", 0) == 1;
    }

    private int getNavagationbarHeight() {
        int identifier = this.mResources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (identifier > 0) {
            return this.mResources.getDimensionPixelSize(identifier);
        }
        return 0;
    }

    private boolean isTablet() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mWindowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
        int i = ((displayMetrics.widthPixels > displayMetrics.heightPixels ? displayMetrics.heightPixels : displayMetrics.widthPixels) * 160) / displayMetrics.densityDpi;
        debugLog("short size dp  = " + i);
        return i >= 600;
    }

    private void getDisplayFrame(Rect rect) {
        DisplayCutout displayCutout;
        int navagationbarHeight = getNavagationbarHeight();
        boolean isNavigationbarHide = isNavigationbarHide();
        int rotation = this.mWindowManager.getDefaultDisplay().getRotation();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mWindowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
        debugLog("realMetrics = " + displayMetrics);
        debugLog("is tablet? = " + isTablet());
        if (this.mForceRealDisplay) {
            rect.left = 0;
            rect.top = 0;
            rect.right = displayMetrics.widthPixels;
            rect.bottom = displayMetrics.heightPixels;
            debugLog("Screen Rect = " + rect + " mForceRealDisplay = " + this.mForceRealDisplay);
            return;
        }
        rect.left = 0;
        rect.top = 0;
        rect.right = this.mDisplayMetrics.widthPixels;
        rect.bottom = this.mDisplayMetrics.heightPixels;
        Rect rect2 = new Rect();
        View windowView = WindowManagerGlobal.getInstance().getWindowView(this.mParentView.getApplicationWindowToken());
        if (windowView == null) {
            windowView = this.mParentView.getRootView();
        }
        windowView.getWindowVisibleDisplayFrame(rect2);
        debugLog("Bounds = " + rect2);
        if (isTablet()) {
            debugLog(BnRConstants.DEVICETYPE_TABLET);
            if (displayMetrics.widthPixels == this.mDisplayMetrics.widthPixels && displayMetrics.heightPixels - this.mDisplayMetrics.heightPixels == navagationbarHeight && isNavigationbarHide) {
                rect.bottom += navagationbarHeight;
            }
        } else {
            debugLog("phone");
            if (rotation != 0) {
                if (rotation == 1) {
                    if (displayMetrics.heightPixels == this.mDisplayMetrics.heightPixels && displayMetrics.widthPixels - this.mDisplayMetrics.widthPixels == navagationbarHeight && isNavigationbarHide) {
                        rect.right += navagationbarHeight;
                    }
                    WindowInsets rootWindowInsets = this.mParentView.getRootWindowInsets();
                    if (rootWindowInsets != null && (displayCutout = rootWindowInsets.getDisplayCutout()) != null) {
                        rect.left += displayCutout.getSafeInsetLeft();
                        rect.right += displayCutout.getSafeInsetLeft();
                        debugLog("displayCutout.getSafeInsetLeft() :  " + displayCutout.getSafeInsetLeft());
                    }
                } else if (rotation != 2) {
                    if (rotation == 3) {
                        if (displayMetrics.heightPixels == this.mDisplayMetrics.heightPixels && displayMetrics.widthPixels - this.mDisplayMetrics.widthPixels == navagationbarHeight) {
                            if (isNavigationbarHide) {
                                rect.right += navagationbarHeight;
                            } else {
                                rect.left += navagationbarHeight;
                                rect.right += navagationbarHeight;
                            }
                        } else if (displayMetrics.heightPixels == this.mDisplayMetrics.heightPixels && rect2.left == navagationbarHeight) {
                            debugLog("Left Docked");
                            rect.left += navagationbarHeight;
                            rect.right += navagationbarHeight;
                        }
                    }
                } else if (displayMetrics.widthPixels == this.mDisplayMetrics.widthPixels && displayMetrics.heightPixels - this.mDisplayMetrics.heightPixels == navagationbarHeight) {
                    if (isNavigationbarHide) {
                        rect.bottom += navagationbarHeight;
                    } else {
                        rect.top += navagationbarHeight;
                        rect.bottom += navagationbarHeight;
                    }
                } else if (displayMetrics.widthPixels == this.mDisplayMetrics.widthPixels && rect2.top == navagationbarHeight) {
                    debugLog("Top Docked");
                    rect.top += navagationbarHeight;
                    rect.bottom += navagationbarHeight;
                }
            } else if (displayMetrics.widthPixels == this.mDisplayMetrics.widthPixels && displayMetrics.heightPixels - this.mDisplayMetrics.heightPixels == navagationbarHeight && isNavigationbarHide) {
                rect.bottom += navagationbarHeight;
            }
        }
        debugLog("Screen Rect = " + rect);
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    private static class TipWindow extends PopupWindow {
        protected boolean mIsDismissing;
        private boolean mIsUsingDismissAnimation;
        protected float mPivotX;
        protected float mPivotY;

        void animateViewOut() {
        }

        private TipWindow(View view, int i, int i2, boolean z) {
            super(view, i, i2, z);
            this.mIsUsingDismissAnimation = true;
            this.mIsDismissing = false;
            this.mPivotX = 0.0f;
            this.mPivotY = 0.0f;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setUseDismissAnimation(boolean z) {
            this.mIsUsingDismissAnimation = z;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setPivot(float f, float f2) {
            this.mPivotX = f;
            this.mPivotY = f2;
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
    }

    private static class TipWindowBubble extends TipWindow {
        private TipWindowBubble(View view, int i, int i2, boolean z) {
            super(view, i, i2, z);
        }

        @Override // com.samsung.android.widget.SemTipPopup.TipWindow
        protected void animateViewOut() {
            AnimationSet animationSet = new AnimationSet(true);
            ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.81f, 1.0f, 0.81f, 0, this.mPivotX, 0, this.mPivotY);
            scaleAnimation.setInterpolator(SemTipPopup.INTERPOLATOR_ELASTIC_CUSTOM);
            scaleAnimation.setDuration(167L);
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setInterpolator(SemTipPopup.INTERPOLATOR_SINE_IN_OUT_33);
            alphaAnimation.setDuration(167L);
            animationSet.addAnimation(scaleAnimation);
            animationSet.addAnimation(alphaAnimation);
            animationSet.setAnimationListener(new Animation.AnimationListener() { // from class: com.samsung.android.widget.SemTipPopup.TipWindowBubble.1
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                    TipWindowBubble.this.mIsDismissing = true;
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    TipWindowBubble.this.dismissFinal();
                }
            });
            getContentView().startAnimation(animationSet);
        }
    }

    private static class TipWindowBalloon extends TipWindow {
        private TipWindowBalloon(View view, int i, int i2, boolean z) {
            super(view, i, i2, z);
        }

        @Override // com.samsung.android.widget.SemTipPopup.TipWindow
        protected void animateViewOut() {
            View contentView = getContentView();
            View findViewById = contentView.findViewById(R.id.sem_tip_popup_message);
            ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.32f, 1.0f, 0.32f, 0, this.mPivotX, 0, this.mPivotY);
            scaleAnimation.setInterpolator(SemTipPopup.INTERPOLATOR_ELASTIC_CUSTOM);
            scaleAnimation.setDuration(500L);
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setDuration(500L);
            AnimationSet animationSet = new AnimationSet(true);
            animationSet.addAnimation(alphaAnimation);
            animationSet.addAnimation(scaleAnimation);
            animationSet.setAnimationListener(new Animation.AnimationListener() { // from class: com.samsung.android.widget.SemTipPopup.TipWindowBalloon.1
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                    TipWindowBalloon.this.mIsDismissing = true;
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    TipWindowBalloon.this.dismissFinal();
                }
            });
            contentView.startAnimation(animationSet);
            findViewById.startAnimation(alphaAnimation);
        }
    }

    private boolean isRTL() {
        return this.mContext.getResources().getConfiguration().getLayoutDirection() == 1;
    }

    private String getLocale() {
        return this.mContext.getResources().getConfiguration().getLocales().get(0).toString();
    }

    private boolean isMirroringSupportedInRTL() {
        return (getLocale().equals("iw_IL") || getLocale().equals("he_IL")) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void debugLog(String str) {
        Log.d(TAG, " #### " + str);
    }

    public PopupWindow semGetBubblePopupWindow() {
        return this.mBubblePopup;
    }

    public PopupWindow semGetBalloonPopupWindow() {
        return this.mBalloonPopup;
    }
}
