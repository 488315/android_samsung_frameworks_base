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
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.ColorDrawable;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Slog;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.android.server.SystemUpdateManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

public final class ImmersiveModeConfirmation {
    public static boolean sConfirmed;
    public ClingWindowView mClingWindow;
    public final Context mContext;
    public final H mHandler;
    public final long mPanicThresholdMs;
    public long mPanicTime;
    public WindowManager mWindowManager;
    public final IBinder mWindowToken = new Binder();
    public int mWindowContextRootDisplayAreaId = -1;
    public final AnonymousClass1 mConfirm = new AnonymousClass1(0, this);

    /* renamed from: com.android.server.wm.ImmersiveModeConfirmation$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass1(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    if (!ImmersiveModeConfirmation.sConfirmed) {
                        ImmersiveModeConfirmation.sConfirmed = true;
                        try {
                            Settings.Secure.putStringForUser(
                                    ((ImmersiveModeConfirmation) this.this$0)
                                            .mContext.getContentResolver(),
                                    "immersive_mode_confirmations",
                                    "confirmed",
                                    -2);
                        } catch (Throwable th) {
                            Slog.w(
                                    "ImmersiveModeConfirmation",
                                    "Error saving confirmations, sConfirmed="
                                            + ImmersiveModeConfirmation.sConfirmed,
                                    th);
                        }
                    }
                    ((ImmersiveModeConfirmation) this.this$0).handleHide();
                    break;
                default:
                    ViewGroup viewGroup = ((ClingWindowView) this.this$0).mClingLayout;
                    if (viewGroup != null && viewGroup.getParent() != null) {
                        ClingWindowView clingWindowView = (ClingWindowView) this.this$0;
                        ViewGroup viewGroup2 = clingWindowView.mClingLayout;
                        ImmersiveModeConfirmation immersiveModeConfirmation =
                                ImmersiveModeConfirmation.this;
                        immersiveModeConfirmation.getClass();
                        viewGroup2.setLayoutParams(
                                new FrameLayout.LayoutParams(
                                        immersiveModeConfirmation
                                                .mContext
                                                .getResources()
                                                .getDimensionPixelSize(
                                                        R.dimen
                                                                .keyguard_avatar_frame_shadow_radius),
                                        -2,
                                        49));
                        break;
                    }
                    break;
            }
        }
    }

    public final class ClingWindowView extends FrameLayout {
        public ViewGroup mClingLayout;
        public final ColorDrawable mColor;
        public ValueAnimator mColorAnim;
        public final Runnable mConfirm;
        public final AnonymousClass2 mInsetsListener;
        public final Interpolator mInterpolator;
        public final AnonymousClass3 mReceiver;
        public final AnonymousClass1 mUpdateLayoutRunnable;

        public ClingWindowView(Context context, Runnable runnable) {
            super(context);
            ColorDrawable colorDrawable = new ColorDrawable(0);
            this.mColor = colorDrawable;
            this.mUpdateLayoutRunnable = new AnonymousClass1(1, this);
            this.mInsetsListener =
                    new ViewTreeObserver
                            .OnComputeInternalInsetsListener() { // from class:
                                                                 // com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.2
                        public final int[] mTmpInt2 = new int[2];

                        public final void onComputeInternalInsets(
                                ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
                            ClingWindowView.this.mClingLayout.getLocationInWindow(this.mTmpInt2);
                            internalInsetsInfo.setTouchableInsets(3);
                            Region region = internalInsetsInfo.touchableRegion;
                            int[] iArr = this.mTmpInt2;
                            int i = iArr[0];
                            region.set(
                                    i,
                                    iArr[1],
                                    ClingWindowView.this.mClingLayout.getWidth() + i,
                                    ClingWindowView.this.mClingLayout.getHeight()
                                            + this.mTmpInt2[1]);
                        }
                    };
            this.mReceiver =
                    new BroadcastReceiver() { // from class:
                                              // com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.3
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context2, Intent intent) {
                            if (intent.getAction()
                                    .equals("android.intent.action.CONFIGURATION_CHANGED")) {
                                ClingWindowView clingWindowView = ClingWindowView.this;
                                clingWindowView.post(clingWindowView.mUpdateLayoutRunnable);
                            }
                        }
                    };
            this.mConfirm = runnable;
            setBackground(colorDrawable);
            setImportantForAccessibility(2);
            this.mInterpolator =
                    AnimationUtils.loadInterpolator(
                            ((FrameLayout) this).mContext, R.interpolator.linear_out_slow_in);
        }

        @Override // android.view.View
        public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
            View findViewById;
            int width = getWidth();
            int dimensionPixelSize =
                    ImmersiveModeConfirmation.this
                            .mContext
                            .getResources()
                            .getDimensionPixelSize(R.dimen.keyguard_avatar_frame_shadow_radius);
            Rect boundingRectTop =
                    windowInsets.getDisplayCutout() != null
                            ? windowInsets.getDisplayCutout().getBoundingRectTop()
                            : new Rect();
            int i = dimensionPixelSize / 2;
            boolean intersects =
                    boundingRectTop.intersects(width - i, 0, i + width, boundingRectTop.bottom);
            ClingWindowView clingWindowView = ImmersiveModeConfirmation.this.mClingWindow;
            if (clingWindowView != null
                    && ((dimensionPixelSize < 0 || (width > 0 && intersects))
                            && (findViewById = clingWindowView.findViewById(R.id.insertion_handle))
                                    != null)) {
                RelativeLayout.LayoutParams layoutParams =
                        (RelativeLayout.LayoutParams) findViewById.getLayoutParams();
                layoutParams.topMargin = boundingRectTop.bottom;
                findViewById.setLayoutParams(layoutParams);
            }
            return new WindowInsets.Builder(windowInsets)
                    .setInsets(WindowInsets.Type.systemBars(), Insets.NONE)
                    .build();
        }

        @Override // android.view.ViewGroup, android.view.View
        public final void onAttachedToWindow() {
            super.onAttachedToWindow();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((FrameLayout) this).mContext.getDisplay().getMetrics(displayMetrics);
            float f = displayMetrics.density;
            getViewTreeObserver().addOnComputeInternalInsetsListener(this.mInsetsListener);
            ViewGroup viewGroup =
                    (ViewGroup)
                            View.inflate(
                                    getContext(), R.layout.language_picker_section_header, null);
            this.mClingLayout = viewGroup;
            ((Button) viewGroup.findViewById(R.id.qwertz))
                    .setOnClickListener(
                            new View
                                    .OnClickListener() { // from class:
                                                         // com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.4
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    ClingWindowView.this.mConfirm.run();
                                }
                            });
            ViewGroup viewGroup2 = this.mClingLayout;
            ImmersiveModeConfirmation immersiveModeConfirmation = ImmersiveModeConfirmation.this;
            immersiveModeConfirmation.getClass();
            addView(
                    viewGroup2,
                    new FrameLayout.LayoutParams(
                            immersiveModeConfirmation
                                    .mContext
                                    .getResources()
                                    .getDimensionPixelSize(
                                            R.dimen.keyguard_avatar_frame_shadow_radius),
                            -2,
                            49));
            if (ActivityManager.isHighEndGfx()) {
                final ViewGroup viewGroup3 = this.mClingLayout;
                viewGroup3.setAlpha(FullScreenMagnificationGestureHandler.MAX_SCALE);
                viewGroup3.setTranslationY(f * (-96.0f));
                postOnAnimation(
                        new Runnable() { // from class:
                                         // com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.5
                            @Override // java.lang.Runnable
                            public final void run() {
                                viewGroup3
                                        .animate()
                                        .alpha(1.0f)
                                        .translationY(
                                                FullScreenMagnificationGestureHandler.MAX_SCALE)
                                        .setDuration(250L)
                                        .setInterpolator(ClingWindowView.this.mInterpolator)
                                        .withLayer()
                                        .start();
                                ClingWindowView.this.mColorAnim =
                                        ValueAnimator.ofObject(
                                                new ArgbEvaluator(), 0, Integer.MIN_VALUE);
                                ClingWindowView.this.mColorAnim.addUpdateListener(
                                        new ValueAnimator
                                                .AnimatorUpdateListener() { // from class:
                                                                            // com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.5.1
                                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                            public final void onAnimationUpdate(
                                                    ValueAnimator valueAnimator) {
                                                ClingWindowView.this.mColor.setColor(
                                                        ((Integer) valueAnimator.getAnimatedValue())
                                                                .intValue());
                                            }
                                        });
                                ClingWindowView.this.mColorAnim.setDuration(250L);
                                ClingWindowView clingWindowView = ClingWindowView.this;
                                clingWindowView.mColorAnim.setInterpolator(
                                        clingWindowView.mInterpolator);
                                ClingWindowView.this.mColorAnim.start();
                            }
                        });
            } else {
                this.mColor.setColor(Integer.MIN_VALUE);
            }
            ((FrameLayout) this)
                    .mContext.registerReceiver(
                            this.mReceiver,
                            new IntentFilter("android.intent.action.CONFIGURATION_CHANGED"));
        }

        @Override // android.view.ViewGroup, android.view.View
        public final void onDetachedFromWindow() {
            ((FrameLayout) this).mContext.unregisterReceiver(this.mReceiver);
        }

        @Override // android.view.View
        public final boolean onTouchEvent(MotionEvent motionEvent) {
            return true;
        }
    }

    public final class H extends Handler {
        public H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (ViewRootImpl.CLIENT_TRANSIENT) {
                return;
            }
            int i = message.what;
            ImmersiveModeConfirmation immersiveModeConfirmation = ImmersiveModeConfirmation.this;
            if (i != 1) {
                if (i != 2) {
                    return;
                }
                immersiveModeConfirmation.handleHide();
                return;
            }
            int i2 = message.arg1;
            if (immersiveModeConfirmation.mClingWindow != null) {
                if (i2 == immersiveModeConfirmation.mWindowContextRootDisplayAreaId) {
                    return;
                } else {
                    immersiveModeConfirmation.handleHide();
                }
            }
            immersiveModeConfirmation.mClingWindow =
                    immersiveModeConfirmation
                    .new ClingWindowView(
                            immersiveModeConfirmation.mContext, immersiveModeConfirmation.mConfirm);
            WindowManager.LayoutParams layoutParams =
                    new WindowManager.LayoutParams(-1, -1, 2017, 16777504, -3);
            layoutParams.setFitInsetsTypes(
                    layoutParams.getFitInsetsTypes() & (~WindowInsets.Type.statusBars()));
            layoutParams.layoutInDisplayCutoutMode = 3;
            layoutParams.privateFlags |= 537002000;
            layoutParams.setTitle("ImmersiveModeConfirmation");
            layoutParams.windowAnimations = R.style.Animation.PopupWindow.ActionMode;
            layoutParams.token = immersiveModeConfirmation.mWindowToken;
            try {
                if (immersiveModeConfirmation.mWindowManager != null) {
                    throw new IllegalStateException(
                            "Must not create a new WindowManager while there is an existing one");
                }
                Bundle m =
                        i2 == -1
                                ? null
                                : SystemUpdateManagerService$$ExternalSyntheticOutline0.m(
                                        i2, "root_display_area_id");
                immersiveModeConfirmation.mWindowContextRootDisplayAreaId = i2;
                WindowManager windowManager =
                        (WindowManager)
                                immersiveModeConfirmation
                                        .mContext
                                        .createWindowContext(2017, m)
                                        .getSystemService(WindowManager.class);
                immersiveModeConfirmation.mWindowManager = windowManager;
                windowManager.addView(immersiveModeConfirmation.mClingWindow, layoutParams);
            } catch (WindowManager.InvalidDisplayException e) {
                Slog.w(
                        "ImmersiveModeConfirmation",
                        "Fail to show the immersive confirmation window because of " + e);
            }
        }
    }

    public ImmersiveModeConfirmation(Context context, Looper looper, boolean z) {
        Display display = context.getDisplay();
        Context systemUiContext = ActivityThread.currentActivityThread().getSystemUiContext();
        this.mContext =
                display.getDisplayId() != 0
                        ? systemUiContext.createDisplayContext(display)
                        : systemUiContext;
        this.mHandler = new H(looper);
        context.getResources().getInteger(17695145);
        this.mPanicThresholdMs =
                context.getResources().getInteger(R.integer.config_maxShortcutTargetsPerApp);
    }

    public static boolean loadSetting(Context context) {
        boolean z = sConfirmed;
        sConfirmed = false;
        String str = null;
        try {
            str =
                    Settings.Secure.getStringForUser(
                            context.getContentResolver(), "immersive_mode_confirmations", -2);
            sConfirmed = "confirmed".equals(str);
        } catch (Throwable th) {
            Slog.w("ImmersiveModeConfirmation", "Error loading confirmations, value=" + str, th);
        }
        return sConfirmed != z;
    }

    public final void handleHide() {
        ClingWindowView clingWindowView = this.mClingWindow;
        if (clingWindowView != null) {
            WindowManager windowManager = this.mWindowManager;
            if (windowManager != null) {
                try {
                    windowManager.removeView(clingWindowView);
                } catch (WindowManager.InvalidDisplayException e) {
                    Slog.w(
                            "ImmersiveModeConfirmation",
                            "Fail to hide the immersive confirmation window because of " + e);
                }
                this.mWindowManager = null;
            }
            this.mClingWindow = null;
        }
    }
}
