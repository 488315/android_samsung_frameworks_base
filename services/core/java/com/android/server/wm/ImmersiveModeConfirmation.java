package com.android.server.wm;

import android.R;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Region;
import android.graphics.drawable.ColorDrawable;
import android.os.Binder;
import android.os.Bundle;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserManager;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Slog;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.server.display.DisplayPowerController2;
import com.android.server.wm.ImmersiveModeConfirmation;
import com.samsung.android.cover.CoverManager;
import com.samsung.android.cover.CoverState;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public class ImmersiveModeConfirmation {
    public static boolean sConfirmed;
    public static boolean sConfirmedForGesture;
    public AccessibilityManager mAccessibilityManager;
    public boolean mCanSystemBarsBeShownByUser;
    public ClingWindowView mClingWindow;
    public final Context mContext;
    public CoverManager mCoverManager;
    public final List mDisablePackages;
    public final DisplayPolicy mDisplayPolicy;
    public final HandlerC2933H mHandler;
    public final long mPanicThresholdMs;
    public long mPanicTime;
    public final long mShowDelayMs;
    public boolean mVrModeEnabled;
    public Context mWindowContext;
    public WindowManager mWindowManager;
    public final IBinder mWindowToken = new Binder();
    public int mLockTaskState = 0;
    public final Runnable mConfirm = new Runnable() { // from class: com.android.server.wm.ImmersiveModeConfirmation.1
        @Override // java.lang.Runnable
        public void run() {
            if (ImmersiveModeConfirmation.this.mDisplayPolicy.mExt.isNavigationGestureMode()) {
                if (!ImmersiveModeConfirmation.sConfirmedForGesture) {
                    ImmersiveModeConfirmation.sConfirmedForGesture = true;
                    ImmersiveModeConfirmation.saveGestureSetting(ImmersiveModeConfirmation.this.mContext);
                }
            } else if (!ImmersiveModeConfirmation.sConfirmed) {
                ImmersiveModeConfirmation.sConfirmed = true;
                ImmersiveModeConfirmation.saveSetting(ImmersiveModeConfirmation.this.mContext);
            }
            if (ImmersiveModeConfirmation.this.mCoverManager != null && ImmersiveModeConfirmation.this.mListenerRegistered && ImmersiveModeConfirmation.sConfirmed && ImmersiveModeConfirmation.sConfirmedForGesture) {
                ImmersiveModeConfirmation.this.mCoverManager.unregisterListener(ImmersiveModeConfirmation.this.mCoverStateListener);
                ImmersiveModeConfirmation.this.mListenerRegistered = false;
            }
            ImmersiveModeConfirmation.this.handleHide();
        }
    };
    public boolean mCoverState = true;
    public boolean mListenerRegistered = false;
    public final CoverManager.StateListener mCoverStateListener = new CoverManager.StateListener() { // from class: com.android.server.wm.ImmersiveModeConfirmation.2
        public void onCoverStateChanged(CoverState coverState) {
            ImmersiveModeConfirmation.this.mCoverState = coverState.getSwitchState();
            ImmersiveModeConfirmation.this.mHandler.removeMessages(1);
            ImmersiveModeConfirmation.this.mHandler.sendEmptyMessage(2);
        }
    };

    public ImmersiveModeConfirmation(Context context, Looper looper, boolean z, boolean z2, DisplayPolicy displayPolicy) {
        Display display = context.getDisplay();
        Context systemUiContext = ActivityThread.currentActivityThread().getSystemUiContext();
        this.mContext = display.getDisplayId() != 0 ? systemUiContext.createDisplayContext(display) : systemUiContext;
        this.mHandler = new HandlerC2933H(looper);
        this.mShowDelayMs = context.getResources().getInteger(R.integer.leanback_setup_alpha_activity_out_bkg_duration) * 3;
        this.mPanicThresholdMs = context.getResources().getInteger(R.integer.config_lidNavigationAccessibility);
        this.mVrModeEnabled = z;
        this.mCanSystemBarsBeShownByUser = z2;
        this.mDisplayPolicy = displayPolicy;
        this.mDisablePackages = new ArrayList(Arrays.asList(context.getResources().getStringArray(17236437)));
    }

    public static boolean loadSetting(int i, Context context) {
        boolean z = sConfirmed;
        sConfirmed = false;
        String str = null;
        try {
            str = Settings.Secure.getStringForUser(context.getContentResolver(), "immersive_mode_confirmations", -2);
            sConfirmed = "confirmed".equals(str);
        } catch (Throwable th) {
            Slog.w("ImmersiveModeConfirmation", "Error loading confirmations, value=" + str, th);
        }
        return sConfirmed != z;
    }

    public static void saveSetting(Context context) {
        try {
            Settings.Secure.putStringForUser(context.getContentResolver(), "immersive_mode_confirmations", sConfirmed ? "confirmed" : null, -2);
        } catch (Throwable th) {
            Slog.w("ImmersiveModeConfirmation", "Error saving confirmations, sConfirmed=" + sConfirmed, th);
        }
    }

    public void release() {
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
    }

    public boolean onSettingChanged(int i) {
        boolean loadSetting = loadSetting(i, this.mContext);
        boolean loadGestureSetting = loadGestureSetting(i, this.mContext);
        if (this.mDisplayPolicy.mExt.isNavigationGestureMode()) {
            if (loadGestureSetting && sConfirmedForGesture) {
                this.mHandler.sendEmptyMessage(2);
            }
            return loadGestureSetting;
        }
        if (loadSetting && sConfirmed) {
            this.mHandler.sendEmptyMessage(2);
        }
        return loadSetting;
    }

    public void immersiveModeChangedLw(int i, boolean z, boolean z2, boolean z3, WindowState windowState) {
        this.mHandler.removeMessages(1);
        if (z) {
            if (!this.mDisplayPolicy.mExt.isNavigationGestureMode() ? !sConfirmed : !sConfirmedForGesture) {
                if (z2 && !this.mVrModeEnabled && this.mCanSystemBarsBeShownByUser && !z3 && !UserManager.isDeviceInDemoMode(this.mContext) && this.mLockTaskState != 1 && isImmersiveModeAvailable(windowState)) {
                    Message obtainMessage = this.mHandler.obtainMessage(1);
                    obtainMessage.arg1 = i;
                    this.mHandler.sendMessageDelayed(obtainMessage, this.mShowDelayMs);
                }
            }
            if (this.mCoverManager != null && this.mListenerRegistered && sConfirmed && sConfirmedForGesture) {
                this.mHandler.post(new Runnable() { // from class: com.android.server.wm.ImmersiveModeConfirmation$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ImmersiveModeConfirmation.this.lambda$immersiveModeChangedLw$0();
                    }
                });
                return;
            }
            return;
        }
        this.mHandler.sendEmptyMessage(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$immersiveModeChangedLw$0() {
        this.mCoverManager.unregisterListener(this.mCoverStateListener);
        this.mListenerRegistered = false;
    }

    public boolean onPowerKeyDown(boolean z, long j, boolean z2, boolean z3) {
        if (!z && j - this.mPanicTime < this.mPanicThresholdMs) {
            return this.mClingWindow == null;
        }
        if (z && z2 && !z3) {
            this.mPanicTime = j;
        } else {
            this.mPanicTime = 0L;
        }
        return false;
    }

    public final void handleHide() {
        if (this.mClingWindow != null) {
            try {
                getWindowManager(-1).removeView(this.mClingWindow);
                this.mClingWindow = null;
            } catch (WindowManager.InvalidDisplayException e) {
                Slog.w("ImmersiveModeConfirmation", "Fail to hide the immersive confirmation window because of " + e);
            }
        }
    }

    public final WindowManager.LayoutParams getClingWindowLayoutParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2017, 16777504, -3);
        layoutParams.setFitInsetsTypes(layoutParams.getFitInsetsTypes() & (~WindowInsets.Type.statusBars()));
        layoutParams.privateFlags |= 536870928;
        layoutParams.setTitle("ImmersiveModeConfirmation");
        layoutParams.windowAnimations = R.style.Animation.DeviceDefault.Dialog;
        layoutParams.token = getWindowToken();
        layoutParams.layoutInDisplayCutoutMode = 1;
        return layoutParams;
    }

    public final FrameLayout.LayoutParams getBubbleLayoutParams() {
        return new FrameLayout.LayoutParams(-1, -1, 49);
    }

    public IBinder getWindowToken() {
        return this.mWindowToken;
    }

    public class ClingWindowView extends FrameLayout {
        public final Interpolator SINE_IN_OUT_33;
        public final Interpolator SINE_IN_OUT_60;
        public Button mButtonOkLand;
        public Button mButtonOkPort;
        public Handler mClingHandler;
        public ViewGroup mClingLayout;
        public final ColorDrawable mColor;
        public ValueAnimator mColorAnim;
        public final Runnable mConfirm;
        public ImageView mImageArrow0;
        public ImageView mImageArrow270;
        public ImageView mImageArrow90;
        public ViewTreeObserver.OnComputeInternalInsetsListener mInsetsListener;
        public final Interpolator mInterpolator;
        public FrameLayout mLayoutLand;
        public FrameLayout mLayoutPort;
        public boolean mNavBarCanMove;
        public int mNavBarPosition;
        public BroadcastReceiver mReceiver;
        public boolean mShowOkButton;
        public TextView mTextLand;
        public TextView mTextPort;
        public Runnable mUpdateLayoutRunnable;

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            return true;
        }

        public ClingWindowView(Context context, Runnable runnable) {
            super(context);
            ColorDrawable colorDrawable = new ColorDrawable(0);
            this.mColor = colorDrawable;
            this.mUpdateLayoutRunnable = new Runnable() { // from class: com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.1
                @Override // java.lang.Runnable
                public void run() {
                    if (ClingWindowView.this.mClingLayout == null || ClingWindowView.this.mClingLayout.getParent() == null) {
                        return;
                    }
                    ClingWindowView.this.mClingLayout.setLayoutParams(ImmersiveModeConfirmation.this.getBubbleLayoutParams());
                }
            };
            this.mInsetsListener = new ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.2
                public final int[] mTmpInt2 = new int[2];

                public void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
                    ClingWindowView.this.mClingLayout.getLocationInWindow(this.mTmpInt2);
                    internalInsetsInfo.setTouchableInsets(3);
                    Region region = internalInsetsInfo.touchableRegion;
                    int[] iArr = this.mTmpInt2;
                    int i = iArr[0];
                    region.set(i, iArr[1], ClingWindowView.this.mClingLayout.getWidth() + i, this.mTmpInt2[1] + ClingWindowView.this.mClingLayout.getHeight());
                }
            };
            this.mReceiver = new BroadcastReceiver() { // from class: com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.3
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context2, Intent intent) {
                    if (intent.getAction().equals("android.intent.action.CONFIGURATION_CHANGED")) {
                        ClingWindowView clingWindowView = ClingWindowView.this;
                        clingWindowView.post(clingWindowView.mUpdateLayoutRunnable);
                    }
                }
            };
            this.SINE_IN_OUT_33 = new PathInterpolator(0.33f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 0.67f, 1.0f);
            this.SINE_IN_OUT_60 = new PathInterpolator(0.17f, 0.17f, 0.4f, 0.1f);
            this.mShowOkButton = false;
            this.mClingHandler = new Handler(Looper.myLooper());
            this.mConfirm = runnable;
            setBackground(colorDrawable);
            setImportantForAccessibility(2);
            this.mInterpolator = AnimationUtils.loadInterpolator(((FrameLayout) this).mContext, R.interpolator.linear_out_slow_in);
            this.mNavBarPosition = ImmersiveModeConfirmation.this.mDisplayPolicy.getNavBarPosition();
            this.mNavBarCanMove = ImmersiveModeConfirmation.this.mDisplayPolicy.navigationBarCanMove();
        }

        @Override // android.view.ViewGroup, android.view.View
        public void onAttachedToWindow() {
            super.onAttachedToWindow();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((FrameLayout) this).mContext.getDisplay().getMetrics(displayMetrics);
            float f = displayMetrics.density;
            getViewTreeObserver().addOnComputeInternalInsetsListener(this.mInsetsListener);
            ViewGroup viewGroup = (ViewGroup) View.inflate(getContext(), R.layout.language_picker_item, null);
            this.mClingLayout = viewGroup;
            this.mLayoutPort = (FrameLayout) viewGroup.findViewById(R.id.input_mode);
            this.mLayoutLand = (FrameLayout) this.mClingLayout.findViewById(R.id.input_minute);
            this.mTextPort = (TextView) this.mClingLayout.findViewById(R.id.runtime);
            this.mTextLand = (TextView) this.mClingLayout.findViewById(R.id.ltr);
            this.mButtonOkPort = (Button) this.mClingLayout.findViewById(R.id.sans);
            this.mButtonOkLand = (Button) this.mClingLayout.findViewById(R.id.map);
            this.mImageArrow0 = (ImageView) this.mClingLayout.findViewById(R.id.input_method_nav_center_group);
            this.mImageArrow90 = (ImageView) this.mClingLayout.findViewById(R.id.input_method_nav_home_handle);
            this.mImageArrow270 = (ImageView) this.mClingLayout.findViewById(R.id.input_method_nav_ends_group);
            this.mButtonOkPort.setOnClickListener(new View.OnClickListener() { // from class: com.android.server.wm.ImmersiveModeConfirmation$ClingWindowView$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ImmersiveModeConfirmation.ClingWindowView.this.lambda$onAttachedToWindow$0(view);
                }
            });
            this.mButtonOkLand.setOnClickListener(new View.OnClickListener() { // from class: com.android.server.wm.ImmersiveModeConfirmation$ClingWindowView$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ImmersiveModeConfirmation.ClingWindowView.this.lambda$onAttachedToWindow$1(view);
                }
            });
            updateTextSize();
            lambda$onConfigurationChanged$2();
            addView(this.mClingLayout, ImmersiveModeConfirmation.this.getBubbleLayoutParams());
            if (ActivityManager.isHighEndGfx()) {
                final ViewGroup viewGroup2 = this.mClingLayout;
                viewGroup2.setAlpha(DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
                viewGroup2.setTranslationY(f * (-96.0f));
                postOnAnimation(new Runnable() { // from class: com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.5
                    @Override // java.lang.Runnable
                    public void run() {
                        viewGroup2.animate().alpha(1.0f).translationY(DisplayPowerController2.RATE_FROM_DOZE_TO_ON).setDuration(250L).setInterpolator(ClingWindowView.this.mInterpolator).withLayer().start();
                        ClingWindowView.this.mColorAnim = ValueAnimator.ofObject(new ArgbEvaluator(), 0, Integer.MIN_VALUE);
                        ClingWindowView.this.mColorAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.5.1
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                ClingWindowView.this.mColor.setColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
                            }
                        });
                        ClingWindowView.this.mColorAnim.setDuration(250L);
                        ClingWindowView.this.mColorAnim.setInterpolator(ClingWindowView.this.mInterpolator);
                        ClingWindowView.this.mColorAnim.start();
                    }
                });
            } else {
                this.mColor.setColor(Integer.MIN_VALUE);
            }
            ((FrameLayout) this).mContext.registerReceiver(this.mReceiver, new IntentFilter("android.intent.action.CONFIGURATION_CHANGED"));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAttachedToWindow$0(View view) {
            this.mConfirm.run();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAttachedToWindow$1(View view) {
            this.mConfirm.run();
        }

        @Override // android.view.ViewGroup, android.view.View
        public void onDetachedFromWindow() {
            ((FrameLayout) this).mContext.unregisterReceiver(this.mReceiver);
        }

        @Override // android.view.View
        public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
            return new WindowInsets.Builder(windowInsets).setInsets(WindowInsets.Type.systemBars(), Insets.NONE).build();
        }

        public final void updateTextSize() {
            float dimensionPixelOffset = ((FrameLayout) this).mContext.getResources().getDimensionPixelOffset(17105752);
            float f = ((FrameLayout) this).mContext.getResources().getConfiguration().fontScale;
            if (f > 1.25f) {
                f = 1.25f;
            }
            float f2 = dimensionPixelOffset * f;
            this.mTextPort.setTextSize(0, f2);
            this.mTextLand.setTextSize(0, f2);
            this.mButtonOkPort.setTextSize(0, f2);
            this.mButtonOkLand.setTextSize(0, f2);
        }

        /* renamed from: updateLayout, reason: merged with bridge method [inline-methods] */
        public final void lambda$onConfigurationChanged$2() {
            Resources resources = ((FrameLayout) this).mContext.getResources();
            int i = this.mNavBarPosition;
            this.mLayoutPort.setVisibility(i == 4 ? 0 : 4);
            this.mLayoutLand.setVisibility(i == 4 ? 4 : 0);
            if (ImmersiveModeConfirmation.this.mDisplayPolicy.mExt.isNavigationGestureMode()) {
                String string = resources.getString(17042560);
                if (i == 4) {
                    this.mImageArrow0.setVisibility(4);
                    this.mTextPort.setText(string);
                } else {
                    this.mImageArrow90.setVisibility(4);
                    this.mImageArrow270.setVisibility(4);
                    this.mTextLand.setText(string);
                }
                startButtonAnimation(i);
                return;
            }
            String string2 = resources.getString(17042561);
            if (i == 4) {
                if (!ImmersiveModeConfirmation.this.mDisplayPolicy.navigationBarCanMove()) {
                    string2 = resources.getString(17042562);
                }
                this.mTextPort.setText(string2);
            } else {
                this.mImageArrow90.setVisibility(4);
                this.mImageArrow270.setVisibility(4);
                this.mTextLand.setText(string2);
            }
            startArrowAnimation(i);
        }

        public final void startArrowAnimation(int i) {
            final ImageView imageView;
            TranslateAnimation translateAnimation;
            if (i == 4) {
                imageView = this.mImageArrow0;
            } else {
                imageView = i == 2 ? this.mImageArrow90 : this.mImageArrow270;
            }
            if (imageView == null) {
                return;
            }
            imageView.setVisibility(0);
            imageView.clearAnimation();
            if (this.mShowOkButton) {
                startButtonAnimation(i);
                return;
            }
            int dimensionPixelOffset = ((FrameLayout) this).mContext.getResources().getDimensionPixelOffset(i == 4 ? 17105749 : 17105748);
            if (i == 1) {
                translateAnimation = new TranslateAnimation(-dimensionPixelOffset, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
            } else if (i != 2) {
                translateAnimation = i != 4 ? null : new TranslateAnimation(DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, dimensionPixelOffset, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
            } else {
                translateAnimation = new TranslateAnimation(dimensionPixelOffset, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
            }
            translateAnimation.setStartOffset(200L);
            translateAnimation.setDuration(600L);
            translateAnimation.setInterpolator(this.SINE_IN_OUT_60);
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
            alphaAnimation.setStartOffset(1800L);
            alphaAnimation.setDuration(300L);
            alphaAnimation.setInterpolator(this.SINE_IN_OUT_33);
            final AnimationSet animationSet = new AnimationSet(true);
            animationSet.addAnimation(translateAnimation);
            animationSet.addAnimation(alphaAnimation);
            animationSet.setAnimationListener(new Animation.AnimationListener() { // from class: com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.6
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                    imageView.setAlpha(1.0f);
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    imageView.setAlpha(DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
                    if (ClingWindowView.this.mShowOkButton || imageView.getVisibility() != 0) {
                        return;
                    }
                    imageView.startAnimation(animationSet);
                }
            });
            imageView.startAnimation(animationSet);
            imageView.invalidate();
        }

        public final void startButtonAnimation(int i) {
            Button button = i == 4 ? this.mButtonOkPort : this.mButtonOkLand;
            if (button == null || button.getVisibility() == 0) {
                return;
            }
            button.setVisibility(0);
            button.clearAnimation();
            AlphaAnimation alphaAnimation = new AlphaAnimation(DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 1.0f);
            alphaAnimation.setDuration(200L);
            alphaAnimation.setInterpolator(this.SINE_IN_OUT_33);
            button.startAnimation(alphaAnimation);
        }

        @Override // android.view.View
        public void onConfigurationChanged(Configuration configuration) {
            super.onConfigurationChanged(configuration);
            int navBarPosition = ImmersiveModeConfirmation.this.mDisplayPolicy.getNavBarPosition();
            boolean navigationBarCanMove = ImmersiveModeConfirmation.this.mDisplayPolicy.navigationBarCanMove();
            if (this.mNavBarPosition == navBarPosition && this.mNavBarCanMove == navigationBarCanMove) {
                return;
            }
            this.mNavBarPosition = navBarPosition;
            this.mNavBarCanMove = navigationBarCanMove;
            this.mClingHandler.post(new Runnable() { // from class: com.android.server.wm.ImmersiveModeConfirmation$ClingWindowView$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    ImmersiveModeConfirmation.ClingWindowView.this.lambda$onConfigurationChanged$2();
                }
            });
        }
    }

    public final WindowManager getWindowManager(int i) {
        if (this.mWindowManager == null || this.mWindowContext == null) {
            Context createWindowContext = this.mContext.createWindowContext(2017, getOptionsForWindowContext(i));
            this.mWindowContext = createWindowContext;
            WindowManager windowManager = (WindowManager) createWindowContext.getSystemService(WindowManager.class);
            this.mWindowManager = windowManager;
            return windowManager;
        }
        Bundle optionsForWindowContext = getOptionsForWindowContext(i);
        try {
            WindowManagerGlobal.getWindowManagerService().attachWindowContextToDisplayArea(this.mWindowContext.getWindowContextToken(), 2017, this.mContext.getDisplayId(), optionsForWindowContext);
            return this.mWindowManager;
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public final Bundle getOptionsForWindowContext(int i) {
        if (i == -1) {
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("root_display_area_id", i);
        return bundle;
    }

    public final void handleShow(int i) {
        this.mClingWindow = new ClingWindowView(this.mContext, this.mConfirm);
        try {
            getWindowManager(i).addView(this.mClingWindow, getClingWindowLayoutParams());
        } catch (WindowManager.InvalidDisplayException e) {
            Slog.w("ImmersiveModeConfirmation", "Fail to show the immersive confirmation window because of " + e);
        }
    }

    /* renamed from: com.android.server.wm.ImmersiveModeConfirmation$H */
    public final class HandlerC2933H extends Handler {
        public HandlerC2933H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                ImmersiveModeConfirmation.this.handleShow(message.arg1);
            } else {
                if (i != 2) {
                    return;
                }
                ImmersiveModeConfirmation.this.handleHide();
            }
        }
    }

    public void onVrStateChangedLw(boolean z) {
        this.mVrModeEnabled = z;
        if (z) {
            this.mHandler.removeMessages(1);
            this.mHandler.sendEmptyMessage(2);
        }
    }

    public void onLockTaskModeChangedLw(int i) {
        this.mLockTaskState = i;
    }

    public final boolean isImmersiveModeAvailable(WindowState windowState) {
        if (this.mClingWindow != null) {
            Slog.d("ImmersiveModeConfirmation", "Do nothing regarding immersiveModeConfirmation. clingWindow is not null.");
            return false;
        }
        if (this.mDisablePackages.contains(windowState.getOwningPackage())) {
            Slog.d("ImmersiveModeConfirmation", "Do nothing regarding immersiveModeConfirmation. disablePackage=" + windowState.getOwningPackage());
            return false;
        }
        if (!sConfirmedForGesture && sConfirmed && "android.server.wm.cts".equals(windowState.getOwningPackage())) {
            return false;
        }
        if (windowState.getAttrs().type < 1 || windowState.getAttrs().type > 99) {
            Slog.d("ImmersiveModeConfirmation", "Do nothing regarding immersiveModeConfirmation. appWindow type=" + windowState.getAttrs().type);
            return false;
        }
        if (!windowState.getAttrs().isFullscreen()) {
            Slog.d("ImmersiveModeConfirmation", "Do nothing regarding immersiveModeConfirmation. Not fullscreen.");
            return false;
        }
        if (CoreRune.IS_FACTORY_BINARY || FactoryTest.isAutomaticTestMode(this.mContext) || FactoryTest.isRunningFactoryApp()) {
            Slog.d("ImmersiveModeConfirmation", "Do nothing regarding immersiveModeConfirmation. Factory test mode.");
            return false;
        }
        if (!isPreloadInstallComplete()) {
            Slog.d("ImmersiveModeConfirmation", "Do nothing regarding immersiveModeConfirmation. Not preloadInstallComplete.");
            return false;
        }
        if (!this.mCoverState) {
            Slog.d("ImmersiveModeConfirmation", "Do nothing regarding immersiveModeConfirmation. coverClosed.");
            return false;
        }
        if (Settings.System.getInt(this.mContext.getContentResolver(), "ultra_powersaving_mode", 0) == 1) {
            Slog.d("ImmersiveModeConfirmation", "Do nothing regarding immersiveModeConfirmation. maxPowerSavingModeEnabled.");
            return false;
        }
        if (getAccessibilityManager().semIsAccessibilityServiceEnabled(16)) {
            Slog.d("ImmersiveModeConfirmation", "Do nothing regarding immersiveModeConfirmation. talkBackEnabled.");
            return false;
        }
        if (!getAccessibilityManager().semIsAccessibilityServiceEnabled(32)) {
            return true;
        }
        Slog.d("ImmersiveModeConfirmation", "Do nothing regarding immersiveModeConfirmation. voiceAssistantEnabled.");
        return false;
    }

    public final boolean isPreloadInstallComplete() {
        return SystemProperties.get("persist.sys.storage_preload", "2").equals("2");
    }

    public final AccessibilityManager getAccessibilityManager() {
        if (this.mAccessibilityManager == null) {
            this.mAccessibilityManager = (AccessibilityManager) this.mContext.getSystemService("accessibility");
        }
        return this.mAccessibilityManager;
    }

    public void showOkButton() {
        ClingWindowView clingWindowView = this.mClingWindow;
        if (clingWindowView != null) {
            clingWindowView.mShowOkButton = true;
            clingWindowView.mClingHandler.post(new Runnable() { // from class: com.android.server.wm.ImmersiveModeConfirmation$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ImmersiveModeConfirmation.this.lambda$showOkButton$1();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showOkButton$1() {
        ClingWindowView clingWindowView = this.mClingWindow;
        if (clingWindowView != null) {
            clingWindowView.lambda$onConfigurationChanged$2();
        }
    }

    public static boolean loadGestureSetting(int i, Context context) {
        boolean z = sConfirmedForGesture;
        sConfirmedForGesture = false;
        String str = null;
        try {
            str = Settings.Secure.getStringForUser(context.getContentResolver(), "gesture_immersive_mode_confirmations", -2);
            sConfirmedForGesture = "confirmed".equals(str);
        } catch (Throwable th) {
            Slog.w("ImmersiveModeConfirmation", "Error loading gesture confirmations, value=" + str, th);
        }
        return sConfirmedForGesture != z;
    }

    public static void saveGestureSetting(Context context) {
        try {
            Settings.Secure.putStringForUser(context.getContentResolver(), "gesture_immersive_mode_confirmations", sConfirmedForGesture ? "confirmed" : null, -2);
        } catch (Throwable th) {
            Slog.w("ImmersiveModeConfirmation", "Error saving gesture confirmations, sConfirmedForGesture=" + sConfirmedForGesture, th);
        }
    }

    public void systemReady() {
        if (this.mDisplayPolicy.mDisplayContent.isDefaultDisplay) {
            CoverManager coverManager = new CoverManager(this.mContext);
            this.mCoverManager = coverManager;
            coverManager.registerListener(this.mCoverStateListener);
            this.mListenerRegistered = true;
        }
    }

    public void hideImmersiveModeConfirmation() {
        if (sConfirmed) {
            return;
        }
        this.mHandler.removeMessages(1);
        this.mHandler.sendEmptyMessage(2);
    }
}
