package com.android.systemui.accessibility;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.SparseArray;
import android.view.Display;
import android.view.IWindowManager;
import android.view.SurfaceControl;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.graphics.SfVsyncFrameCallbackProvider;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardStatusViewController$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.accessibility.MagnificationModeSwitch;
import com.android.systemui.accessibility.MagnificationSettingsController;
import com.android.systemui.model.SysUiState;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.util.settings.SecureSettings;
import java.io.PrintWriter;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public final class Magnification implements CoreStartable, CommandQueue.Callbacks {
    static final int DELAY_SHOW_MAGNIFICATION_TIMEOUT_MS = 300;
    public final AccessibilityLogger mA11yLogger;
    public final AccessibilityManager mAccessibilityManager;
    public final CommandQueue mCommandQueue;
    public final DisplayTracker mDisplayTracker;
    public final Executor mExecutor;
    DisplayIdIndexSupplier mFullscreenMagnificationControllerSupplier;
    public final AnonymousClass1 mHandler;
    public MagnificationConnectionImpl mMagnificationConnectionImpl;
    final MagnificationSettingsController.Callback mMagnificationSettingsControllerCallback;
    DisplayIdIndexSupplier mMagnificationSettingsSupplier;
    public final ModeSwitchesController mModeSwitchesController;
    public final OverviewProxyService mOverviewProxyService;
    public final SysUiState mSysUiState;
    SparseArray<SparseArray<Float>> mUsersScales;
    DisplayIdIndexSupplier mWindowMagnificationControllerSupplier;
    final WindowMagnifierCallback mWindowMagnifierCallback;

    /* renamed from: com.android.systemui.accessibility.Magnification$3, reason: invalid class name */
    public final class AnonymousClass3 implements WindowMagnifierCallback {
        public AnonymousClass3() {
        }
    }

    /* renamed from: com.android.systemui.accessibility.Magnification$4, reason: invalid class name */
    public final class AnonymousClass4 implements MagnificationSettingsController.Callback {
        public AnonymousClass4() {
        }
    }

    public final class FullscreenMagnificationControllerSupplier extends DisplayIdIndexSupplier {
        public final Context mContext;
        public final Executor mExecutor;
        public final Handler mHandler;
        public final IWindowManager mIWindowManager;

        public FullscreenMagnificationControllerSupplier(Context context, DisplayManager displayManager, Handler handler, Executor executor, IWindowManager iWindowManager) {
            super(displayManager);
            this.mContext = context;
            this.mHandler = handler;
            this.mExecutor = executor;
            this.mIWindowManager = iWindowManager;
        }

        @Override // com.android.systemui.accessibility.DisplayIdIndexSupplier
        public final Object createInstance(Display display) {
            Context createWindowContext = this.mContext.createWindowContext(display, 2032, null);
            Magnification$WindowMagnificationControllerSupplier$$ExternalSyntheticLambda0 magnification$WindowMagnificationControllerSupplier$$ExternalSyntheticLambda0 = new Magnification$WindowMagnificationControllerSupplier$$ExternalSyntheticLambda0(this, display, 1);
            createWindowContext.setTheme(R.style.Theme_SystemUI);
            return new FullscreenMagnificationController(createWindowContext, this.mHandler, this.mExecutor, (AccessibilityManager) createWindowContext.getSystemService(AccessibilityManager.class), (WindowManager) createWindowContext.getSystemService(WindowManager.class), this.mIWindowManager, magnification$WindowMagnificationControllerSupplier$$ExternalSyntheticLambda0);
        }
    }

    public final class SettingsSupplier extends DisplayIdIndexSupplier {
        public final Context mContext;
        public final SecureSettings mSecureSettings;
        public final MagnificationSettingsController.Callback mSettingsControllerCallback;

        public SettingsSupplier(Context context, MagnificationSettingsController.Callback callback, DisplayManager displayManager, SecureSettings secureSettings) {
            super(displayManager);
            this.mContext = context;
            this.mSettingsControllerCallback = callback;
            this.mSecureSettings = secureSettings;
        }

        @Override // com.android.systemui.accessibility.DisplayIdIndexSupplier
        public final Object createInstance(Display display) {
            Context createWindowContext = this.mContext.createWindowContext(display, 2032, null);
            createWindowContext.setTheme(R.style.Theme_SystemUI);
            return new MagnificationSettingsController(createWindowContext, new SfVsyncFrameCallbackProvider(), this.mSettingsControllerCallback, this.mSecureSettings);
        }
    }

    public final class WindowMagnificationControllerSupplier extends DisplayIdIndexSupplier {
        public final Context mContext;
        public final Handler mHandler;
        public final SecureSettings mSecureSettings;
        public final SysUiState mSysUiState;
        public final WindowMagnifierCallback mWindowMagnifierCallback;

        public WindowMagnificationControllerSupplier(Context context, Handler handler, WindowMagnifierCallback windowMagnifierCallback, DisplayManager displayManager, SysUiState sysUiState, SecureSettings secureSettings) {
            super(displayManager);
            this.mContext = context;
            this.mHandler = handler;
            this.mWindowMagnifierCallback = windowMagnifierCallback;
            this.mSysUiState = sysUiState;
            this.mSecureSettings = secureSettings;
        }

        @Override // com.android.systemui.accessibility.DisplayIdIndexSupplier
        public final Object createInstance(Display display) {
            Context context = this.mContext;
            Flags.createWindowlessWindowMagnifier();
            Context createWindowContext = context.createWindowContext(display, 2032, null);
            createWindowContext.setTheme(R.style.Theme_SystemUI);
            Magnification$WindowMagnificationControllerSupplier$$ExternalSyntheticLambda0 magnification$WindowMagnificationControllerSupplier$$ExternalSyntheticLambda0 = new Magnification$WindowMagnificationControllerSupplier$$ExternalSyntheticLambda0(this, display, 0);
            WindowMagnificationAnimationController windowMagnificationAnimationController = new WindowMagnificationAnimationController(createWindowContext);
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            SfVsyncFrameCallbackProvider sfVsyncFrameCallbackProvider = new SfVsyncFrameCallbackProvider();
            Magnification$WindowMagnificationControllerSupplier$$ExternalSyntheticLambda1 magnification$WindowMagnificationControllerSupplier$$ExternalSyntheticLambda1 = new Magnification$WindowMagnificationControllerSupplier$$ExternalSyntheticLambda1();
            return new WindowMagnificationController(createWindowContext, this.mHandler, windowMagnificationAnimationController, null, transaction, this.mWindowMagnifierCallback, this.mSysUiState, this.mSecureSettings, magnification$WindowMagnificationControllerSupplier$$ExternalSyntheticLambda0, sfVsyncFrameCallbackProvider, magnification$WindowMagnificationControllerSupplier$$ExternalSyntheticLambda1);
        }
    }

    public Magnification(Context context, Handler handler, Executor executor, CommandQueue commandQueue, ModeSwitchesController modeSwitchesController, SysUiState sysUiState, OverviewProxyService overviewProxyService, SecureSettings secureSettings, DisplayTracker displayTracker, DisplayManager displayManager, AccessibilityLogger accessibilityLogger, IWindowManager iWindowManager) {
        this(context, handler.getLooper(), executor, commandQueue, modeSwitchesController, sysUiState, overviewProxyService, secureSettings, displayTracker, displayManager, accessibilityLogger, iWindowManager);
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(final PrintWriter printWriter, String[] strArr) {
        printWriter.println("Magnification");
        DisplayIdIndexSupplier displayIdIndexSupplier = this.mWindowMagnificationControllerSupplier;
        Consumer consumer = new Consumer() { // from class: com.android.systemui.accessibility.Magnification$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PrintWriter printWriter2 = printWriter;
                WindowMagnificationController windowMagnificationController = (WindowMagnificationController) obj;
                printWriter2.println("WindowMagnificationController (displayId=" + windowMagnificationController.mDisplayId + "):");
                StringBuilder m = KeyguardStatusViewController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("      mOverlapWithGestureInsets:"), windowMagnificationController.mOverlapWithGestureInsets, printWriter2, "      mScale:"), windowMagnificationController.mScale, printWriter2, "      mWindowBounds:");
                m.append(windowMagnificationController.mWindowBounds);
                printWriter2.println(m.toString());
                StringBuilder sb = new StringBuilder("      mMirrorViewBounds:");
                sb.append(windowMagnificationController.isActivated() ? windowMagnificationController.mMirrorViewBounds : "empty");
                printWriter2.println(sb.toString());
                StringBuilder sb2 = new StringBuilder("      mMagnificationFrameBoundary:");
                sb2.append(windowMagnificationController.isActivated() ? windowMagnificationController.mMagnificationFrameBoundary : "empty");
                printWriter2.println(sb2.toString());
                StringBuilder sb3 = new StringBuilder("      mMagnificationFrame:");
                sb3.append(windowMagnificationController.isActivated() ? windowMagnificationController.mMagnificationFrame : "empty");
                printWriter2.println(sb3.toString());
                StringBuilder sb4 = new StringBuilder("      mSourceBounds:");
                sb4.append(windowMagnificationController.mSourceBounds.isEmpty() ? "empty" : windowMagnificationController.mSourceBounds);
                printWriter2.println(sb4.toString());
                StringBuilder m2 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("      mSystemGestureTop:"), windowMagnificationController.mSystemGestureTop, printWriter2, "      mMagnificationFrameOffsetX:"), windowMagnificationController.mMagnificationFrameOffsetX, printWriter2, "      mMagnificationFrameOffsetY:");
                m2.append(windowMagnificationController.mMagnificationFrameOffsetY);
                printWriter2.println(m2.toString());
            }
        };
        for (int i = 0; i < displayIdIndexSupplier.mSparseArray.size(); i++) {
            consumer.accept(displayIdIndexSupplier.mSparseArray.valueAt(i));
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void requestMagnificationConnection(boolean z) {
        if (!z) {
            this.mAccessibilityManager.setMagnificationConnection(null);
            return;
        }
        if (this.mMagnificationConnectionImpl == null) {
            this.mMagnificationConnectionImpl = new MagnificationConnectionImpl(this, this.mHandler);
        }
        this.mAccessibilityManager.setMagnificationConnection(this.mMagnificationConnectionImpl);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
        this.mOverviewProxyService.addCallback(new OverviewProxyService.OverviewProxyListener() { // from class: com.android.systemui.accessibility.Magnification.2
            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public final void onConnectionChanged(boolean z) {
                if (z) {
                    Magnification magnification = Magnification.this;
                    DisplayIdIndexSupplier displayIdIndexSupplier = magnification.mWindowMagnificationControllerSupplier;
                    magnification.mDisplayTracker.getClass();
                    WindowMagnificationController windowMagnificationController = (WindowMagnificationController) displayIdIndexSupplier.mSparseArray.get(0);
                    if (windowMagnificationController != null) {
                        windowMagnificationController.updateSysUIState(true);
                        return;
                    }
                    SysUiState sysUiState = magnification.mSysUiState;
                    sysUiState.setFlag(524288L, false);
                    sysUiState.commitUpdate(0);
                }
            }
        });
    }

    public Magnification(Context context, Looper looper, Executor executor, CommandQueue commandQueue, ModeSwitchesController modeSwitchesController, SysUiState sysUiState, OverviewProxyService overviewProxyService, SecureSettings secureSettings, DisplayTracker displayTracker, DisplayManager displayManager, AccessibilityLogger accessibilityLogger, IWindowManager iWindowManager) {
        this.mUsersScales = new SparseArray<>();
        AnonymousClass3 anonymousClass3 = new AnonymousClass3();
        this.mWindowMagnifierCallback = anonymousClass3;
        AnonymousClass4 anonymousClass4 = new AnonymousClass4();
        this.mMagnificationSettingsControllerCallback = anonymousClass4;
        ?? r11 = new Handler(looper) { // from class: com.android.systemui.accessibility.Magnification.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                MagnificationModeSwitch magnificationModeSwitch;
                WindowMagnificationSettings windowMagnificationSettings;
                if (message.what == 1) {
                    int i = message.arg1;
                    int i2 = message.arg2;
                    Magnification magnification = Magnification.this;
                    MagnificationSettingsController magnificationSettingsController = (MagnificationSettingsController) magnification.mMagnificationSettingsSupplier.get(i);
                    boolean z = false;
                    if (magnificationSettingsController != null && (windowMagnificationSettings = magnificationSettingsController.mWindowMagnificationSettings) != null) {
                        z = windowMagnificationSettings.mIsVisible;
                    }
                    if (z || (magnificationModeSwitch = (MagnificationModeSwitch) magnification.mModeSwitchesController.mSwitchSupplier.get(i)) == null) {
                        return;
                    }
                    magnificationModeSwitch.showButton(i2, true);
                }
            }
        };
        this.mHandler = r11;
        this.mExecutor = executor;
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        this.mCommandQueue = commandQueue;
        this.mModeSwitchesController = modeSwitchesController;
        this.mSysUiState = sysUiState;
        this.mOverviewProxyService = overviewProxyService;
        this.mDisplayTracker = displayTracker;
        this.mA11yLogger = accessibilityLogger;
        this.mWindowMagnificationControllerSupplier = new WindowMagnificationControllerSupplier(context, r11, anonymousClass3, displayManager, sysUiState, secureSettings);
        this.mFullscreenMagnificationControllerSupplier = new FullscreenMagnificationControllerSupplier(context, displayManager, r11, executor, iWindowManager);
        this.mMagnificationSettingsSupplier = new SettingsSupplier(context, anonymousClass4, displayManager, secureSettings);
        modeSwitchesController.mClickListenerDelegate = new MagnificationModeSwitch.ClickListener() { // from class: com.android.systemui.accessibility.Magnification$$ExternalSyntheticLambda1
            @Override // com.android.systemui.accessibility.MagnificationModeSwitch.ClickListener
            public final void onClick(int i) {
                Magnification magnification = Magnification.this;
                magnification.mHandler.post(new Magnification$$ExternalSyntheticLambda2(magnification, i, 0));
            }
        };
    }
}
