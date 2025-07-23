package com.android.systemui.statusbar;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.ContentObserver;
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
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserManager;
import android.provider.Settings;
import android.service.vr.IVrManager;
import android.service.vr.IVrStateCallbacks;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.utils.windowmanager.WindowManagerProvider;
import com.android.systemui.utils.windowmanager.WindowManagerProviderImpl;
import com.android.systemui.utils.windowmanager.WindowManagerUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ImmersiveModeConfirmation implements CoreStartable, CommandQueue.Callbacks, TaskStackChangeListener {
    public static boolean sConfirmed;
    public final Handler mBackgroundHandler;
    public ClingWindowView mClingWindow;
    public final CommandQueue mCommandQueue;
    public AnonymousClass1 mContentObserver;
    public final Context mDisplayContext;
    public boolean mNavBarEmpty;
    public final SecureSettings mSecureSettings;
    public final Context mSysUiContext;
    public WindowManager mWindowManager;
    public final WindowManagerProvider mWindowManagerProvider;
    public final H mHandler = new H(Looper.getMainLooper());
    public long mShowDelayMs = 0;
    public final IBinder mWindowToken = new Binder();
    public int mWindowContextRootDisplayAreaId = -1;
    public boolean mVrModeEnabled = false;
    public boolean mCanSystemBarsBeShownByUser = true;
    public int mLockTaskState = 0;
    public final AnonymousClass2 mVrStateCallbacks = new AnonymousClass2();
    public final AnonymousClass3 mConfirm = new Runnable() { // from class: com.android.systemui.statusbar.ImmersiveModeConfirmation.3
        @Override // java.lang.Runnable
        public final void run() {
            if (!ImmersiveModeConfirmation.sConfirmed) {
                ImmersiveModeConfirmation.sConfirmed = true;
                try {
                    Settings.Secure.putStringForUser(ImmersiveModeConfirmation.this.mDisplayContext.getContentResolver(), "immersive_mode_confirmations", "confirmed", -2);
                } catch (Throwable th) {
                    Log.w("ImmersiveModeConfirm", "Error saving confirmations, sConfirmed=" + ImmersiveModeConfirmation.sConfirmed, th);
                }
            }
            ImmersiveModeConfirmation.this.handleHide$2();
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.ImmersiveModeConfirmation$2, reason: invalid class name */
    public class AnonymousClass2 extends IVrStateCallbacks.Stub {
        public AnonymousClass2() {
        }

        public final void onVrStateChanged(boolean z) {
            ImmersiveModeConfirmation immersiveModeConfirmation = ImmersiveModeConfirmation.this;
            immersiveModeConfirmation.mVrModeEnabled = z;
            if (z) {
                immersiveModeConfirmation.mHandler.removeMessages(1);
                ImmersiveModeConfirmation.this.mHandler.sendEmptyMessage(2);
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ClingWindowView extends FrameLayout {
        public ViewGroup mClingLayout;
        public final ColorDrawable mColor;
        public ValueAnimator mColorAnim;
        public final Runnable mConfirm;
        public final AnonymousClass2 mInsetsListener;
        public final Interpolator mInterpolator;
        public final AnonymousClass3 mReceiver;
        public final AnonymousClass1 mUpdateLayoutRunnable;

        /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.statusbar.ImmersiveModeConfirmation$ClingWindowView$1] */
        /* JADX WARN: Type inference failed for: r2v3, types: [com.android.systemui.statusbar.ImmersiveModeConfirmation$ClingWindowView$2] */
        /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.statusbar.ImmersiveModeConfirmation$ClingWindowView$3] */
        public ClingWindowView(Context context, Runnable runnable) {
            super(context);
            ColorDrawable colorDrawable = new ColorDrawable(0);
            this.mColor = colorDrawable;
            this.mUpdateLayoutRunnable = new Runnable() { // from class: com.android.systemui.statusbar.ImmersiveModeConfirmation.ClingWindowView.1
                @Override // java.lang.Runnable
                public final void run() {
                    ViewGroup viewGroup = ClingWindowView.this.mClingLayout;
                    if (viewGroup == null || viewGroup.getParent() == null) {
                        return;
                    }
                    ClingWindowView clingWindowView = ClingWindowView.this;
                    ViewGroup viewGroup2 = clingWindowView.mClingLayout;
                    ImmersiveModeConfirmation immersiveModeConfirmation = ImmersiveModeConfirmation.this;
                    boolean z = ImmersiveModeConfirmation.sConfirmed;
                    immersiveModeConfirmation.getClass();
                    viewGroup2.setLayoutParams(new FrameLayout.LayoutParams(immersiveModeConfirmation.mSysUiContext.getResources().getDimensionPixelSize(R.dimen.immersive_mode_cling_width), -2, 49));
                }
            };
            this.mInsetsListener = new ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: com.android.systemui.statusbar.ImmersiveModeConfirmation.ClingWindowView.2
                public final int[] mTmpInt2 = new int[2];

                public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
                    ClingWindowView.this.mClingLayout.getLocationInWindow(this.mTmpInt2);
                    internalInsetsInfo.setTouchableInsets(3);
                    Region region = internalInsetsInfo.touchableRegion;
                    int[] iArr = this.mTmpInt2;
                    int i = iArr[0];
                    region.set(i, iArr[1], ClingWindowView.this.mClingLayout.getWidth() + i, ClingWindowView.this.mClingLayout.getHeight() + this.mTmpInt2[1]);
                }
            };
            this.mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.ImmersiveModeConfirmation.ClingWindowView.3
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context2, Intent intent) {
                    if (intent.getAction().equals("android.intent.action.CONFIGURATION_CHANGED")) {
                        ClingWindowView clingWindowView = ClingWindowView.this;
                        clingWindowView.post(clingWindowView.mUpdateLayoutRunnable);
                    }
                }
            };
            this.mConfirm = runnable;
            setBackground(colorDrawable);
            setImportantForAccessibility(2);
            this.mInterpolator = AnimationUtils.loadInterpolator(((FrameLayout) this).mContext, android.R.interpolator.linear_out_slow_in);
        }

        @Override // android.view.View
        public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
            int width = getWidth();
            ImmersiveModeConfirmation immersiveModeConfirmation = ImmersiveModeConfirmation.this;
            boolean z = ImmersiveModeConfirmation.sConfirmed;
            int dimensionPixelSize = immersiveModeConfirmation.mSysUiContext.getResources().getDimensionPixelSize(R.dimen.immersive_mode_cling_width);
            Rect boundingRectTop = windowInsets.getDisplayCutout() != null ? windowInsets.getDisplayCutout().getBoundingRectTop() : new Rect();
            int i = dimensionPixelSize / 2;
            boolean intersects = boundingRectTop.intersects(width - i, 0, i + width, boundingRectTop.bottom);
            if (dimensionPixelSize < 0 || (width > 0 && intersects)) {
                View findViewById = findViewById(R.id.immersive_cling_icon);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) findViewById.getLayoutParams();
                layoutParams.topMargin = boundingRectTop.bottom;
                findViewById.setLayoutParams(layoutParams);
            }
            return new WindowInsets.Builder(windowInsets).setInsets(WindowInsets.Type.systemBars(), Insets.NONE).build();
        }

        @Override // android.view.ViewGroup, android.view.View
        public final void onAttachedToWindow() {
            super.onAttachedToWindow();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((FrameLayout) this).mContext.getDisplay().getMetrics(displayMetrics);
            float f = displayMetrics.density;
            getViewTreeObserver().addOnComputeInternalInsetsListener(this.mInsetsListener);
            ViewGroup viewGroup = (ViewGroup) View.inflate(ImmersiveModeConfirmation.this.mSysUiContext, R.layout.immersive_mode_cling, null);
            this.mClingLayout = viewGroup;
            ((Button) viewGroup.findViewById(R.id.ok)).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.ImmersiveModeConfirmation.ClingWindowView.4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ClingWindowView.this.mConfirm.run();
                }
            });
            ViewGroup viewGroup2 = this.mClingLayout;
            ImmersiveModeConfirmation immersiveModeConfirmation = ImmersiveModeConfirmation.this;
            immersiveModeConfirmation.getClass();
            addView(viewGroup2, new FrameLayout.LayoutParams(immersiveModeConfirmation.mSysUiContext.getResources().getDimensionPixelSize(R.dimen.immersive_mode_cling_width), -2, 49));
            if (ActivityManager.isHighEndGfx()) {
                final ViewGroup viewGroup3 = this.mClingLayout;
                viewGroup3.setAlpha(0.0f);
                viewGroup3.setTranslationY(f * (-96.0f));
                postOnAnimation(new Runnable() { // from class: com.android.systemui.statusbar.ImmersiveModeConfirmation.ClingWindowView.5
                    @Override // java.lang.Runnable
                    public final void run() {
                        viewGroup3.animate().alpha(1.0f).translationY(0.0f).setDuration(250L).setInterpolator(ClingWindowView.this.mInterpolator).withLayer().start();
                        ClingWindowView.this.mColorAnim = ValueAnimator.ofObject(new ArgbEvaluator(), 0, Integer.MIN_VALUE);
                        ClingWindowView.this.mColorAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.ImmersiveModeConfirmation.ClingWindowView.5.1
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                ClingWindowView.this.mColor.setColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
                            }
                        });
                        ClingWindowView.this.mColorAnim.setDuration(250L);
                        ClingWindowView clingWindowView = ClingWindowView.this;
                        clingWindowView.mColorAnim.setInterpolator(clingWindowView.mInterpolator);
                        ClingWindowView.this.mColorAnim.start();
                    }
                });
            } else {
                this.mColor.setColor(Integer.MIN_VALUE);
            }
            ((FrameLayout) this).mContext.registerReceiver(this.mReceiver, new IntentFilter("android.intent.action.CONFIGURATION_CHANGED"));
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class H extends Handler {
        public H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            ImmersiveModeConfirmation immersiveModeConfirmation = ImmersiveModeConfirmation.this;
            if (i != 1) {
                if (i != 2) {
                    return;
                }
                boolean z = ImmersiveModeConfirmation.sConfirmed;
                immersiveModeConfirmation.handleHide$2();
                return;
            }
            int i2 = message.arg1;
            if (immersiveModeConfirmation.mClingWindow != null) {
                if (i2 == immersiveModeConfirmation.mWindowContextRootDisplayAreaId) {
                    return;
                } else {
                    immersiveModeConfirmation.handleHide$2();
                }
            }
            immersiveModeConfirmation.mClingWindow = immersiveModeConfirmation.new ClingWindowView(immersiveModeConfirmation.mDisplayContext, immersiveModeConfirmation.mConfirm);
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2017, 16777504, -3);
            layoutParams.setFitInsetsTypes(layoutParams.getFitInsetsTypes() & (~WindowInsets.Type.statusBars()));
            layoutParams.layoutInDisplayCutoutMode = 3;
            layoutParams.privateFlags |= 537002000;
            layoutParams.setTitle("ImmersiveModeConfirmation");
            layoutParams.accessibilityTitle = immersiveModeConfirmation.mSysUiContext.getString(R.string.immersive_cling_title);
            layoutParams.windowAnimations = android.R.style.Animation.Tooltip;
            layoutParams.token = immersiveModeConfirmation.mWindowToken;
            try {
                immersiveModeConfirmation.createWindowManager(i2).addView(immersiveModeConfirmation.mClingWindow, layoutParams);
            } catch (WindowManager.InvalidDisplayException e) {
                Log.w("ImmersiveModeConfirm", "Fail to show the immersive confirmation window because of " + e);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.systemui.statusbar.ImmersiveModeConfirmation$3] */
    public ImmersiveModeConfirmation(Context context, CommandQueue commandQueue, SecureSettings secureSettings, Handler handler, WindowManagerProvider windowManagerProvider) {
        this.mSysUiContext = context;
        Display display = context.getDisplay();
        this.mDisplayContext = display.getDisplayId() != 0 ? context.createDisplayContext(display) : context;
        this.mCommandQueue = commandQueue;
        this.mSecureSettings = secureSettings;
        this.mBackgroundHandler = handler;
        this.mWindowManagerProvider = windowManagerProvider;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void confirmImmersivePrompt() {
        if (this.mClingWindow != null) {
            this.mHandler.post(this.mConfirm);
        }
    }

    public final WindowManager createWindowManager(int i) {
        Bundle bundle;
        if (this.mWindowManager != null) {
            throw new IllegalStateException("Must not create a new WindowManager while there is an existing one");
        }
        if (i == -1) {
            bundle = null;
        } else {
            bundle = new Bundle();
            bundle.putInt("root_display_area_id", i);
        }
        this.mWindowContextRootDisplayAreaId = i;
        Context createWindowContext = this.mDisplayContext.createWindowContext(2017, bundle);
        ((WindowManagerProviderImpl) this.mWindowManagerProvider).getClass();
        WindowManager windowManager = WindowManagerUtils.getWindowManager(createWindowContext);
        this.mWindowManager = windowManager;
        return windowManager;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void disable(int i, int i2, int i3, boolean z) {
        if (this.mSysUiContext.getDisplayId() != i) {
            return;
        }
        this.mNavBarEmpty = (i2 & 23068672) == 23068672;
    }

    public final void handleHide$2() {
        ClingWindowView clingWindowView = this.mClingWindow;
        if (clingWindowView != null) {
            WindowManager windowManager = this.mWindowManager;
            if (windowManager != null) {
                try {
                    windowManager.removeView(clingWindowView);
                } catch (WindowManager.InvalidDisplayException e) {
                    Log.w("ImmersiveModeConfirm", "Fail to hide the immersive confirmation window because of " + e);
                }
                this.mWindowManager = null;
            }
            this.mClingWindow = null;
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void immersiveModeChanged(int i, boolean z, int i2) {
        H h = this.mHandler;
        h.removeMessages(1);
        if (!z) {
            h.sendEmptyMessage(2);
            return;
        }
        boolean z2 = this.mSecureSettings.getIntForUser(SettingsHelper.INDEX_USER_SETUP_COMPLETE, 0, -2) != 0;
        if (sConfirmed || !z2 || this.mVrModeEnabled || !this.mCanSystemBarsBeShownByUser || this.mNavBarEmpty || UserManager.isDeviceInDemoMode(this.mDisplayContext) || this.mLockTaskState == 1 || i2 == 2037 || i2 == 2030) {
            return;
        }
        Message obtainMessage = h.obtainMessage(1);
        obtainMessage.arg1 = i;
        h.sendMessageDelayed(obtainMessage, this.mShowDelayMs);
    }

    public final boolean loadSetting() {
        boolean z = sConfirmed;
        sConfirmed = false;
        String str = null;
        try {
            str = this.mSecureSettings.getStringForUser("immersive_mode_confirmations", -2);
            sConfirmed = "confirmed".equals(str);
        } catch (Throwable th) {
            Log.w("ImmersiveModeConfirm", "Error loading confirmations, value=" + str, th);
        }
        return sConfirmed != z;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onDisplayRemoved(int i) {
        if (i != this.mSysUiContext.getDisplayId()) {
            return;
        }
        H h = this.mHandler;
        h.removeMessages(1);
        h.removeMessages(2);
        IVrManager asInterface = IVrManager.Stub.asInterface(ServiceManager.getService("vrmanager"));
        if (asInterface != null) {
            try {
                asInterface.unregisterListener(this.mVrStateCallbacks);
            } catch (RemoteException unused) {
            }
        }
        this.mCommandQueue.removeCallback((CommandQueue.Callbacks) this);
    }

    @Override // com.android.systemui.shared.system.TaskStackChangeListener
    public final void onLockTaskModeChanged(int i) {
        this.mLockTaskState = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10, types: [android.database.ContentObserver, com.android.systemui.statusbar.ImmersiveModeConfirmation$1] */
    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
        Resources resources = this.mSysUiContext.getResources();
        this.mShowDelayMs = resources.getInteger(R.integer.dock_enter_exit_duration) * 3;
        this.mCanSystemBarsBeShownByUser = !resources.getBoolean(R.bool.config_remoteInsetsControllerControlsSystemBars) || resources.getBoolean(R.bool.config_remoteInsetsControllerSystemBarsCanBeShownByUserAction);
        IVrManager asInterface = IVrManager.Stub.asInterface(ServiceManager.getService("vrmanager"));
        if (asInterface != null) {
            try {
                this.mVrModeEnabled = asInterface.getVrModeState();
                AnonymousClass2 anonymousClass2 = this.mVrStateCallbacks;
                asInterface.registerListener(anonymousClass2);
                anonymousClass2.onVrStateChanged(this.mVrModeEnabled);
            } catch (RemoteException unused) {
            }
        }
        TaskStackChangeListeners.INSTANCE.registerTaskStackListener(this);
        Handler handler = this.mBackgroundHandler;
        ?? r0 = new ContentObserver(handler) { // from class: com.android.systemui.statusbar.ImmersiveModeConfirmation.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                ImmersiveModeConfirmation immersiveModeConfirmation = ImmersiveModeConfirmation.this;
                immersiveModeConfirmation.mSysUiContext.getUserId();
                if (immersiveModeConfirmation.loadSetting() && ImmersiveModeConfirmation.sConfirmed) {
                    immersiveModeConfirmation.mHandler.sendEmptyMessage(2);
                }
            }
        };
        this.mContentObserver = r0;
        SecureSettings secureSettings = this.mSecureSettings;
        secureSettings.registerContentObserverForUserSync("immersive_mode_confirmations", (ContentObserver) r0, -2);
        secureSettings.registerContentObserverForUserSync(SettingsHelper.INDEX_USER_SETUP_COMPLETE, this.mContentObserver, -2);
        handler.post(new Runnable() { // from class: com.android.systemui.statusbar.ImmersiveModeConfirmation$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ImmersiveModeConfirmation immersiveModeConfirmation = ImmersiveModeConfirmation.this;
                boolean z = ImmersiveModeConfirmation.sConfirmed;
                immersiveModeConfirmation.loadSetting();
            }
        });
    }
}
