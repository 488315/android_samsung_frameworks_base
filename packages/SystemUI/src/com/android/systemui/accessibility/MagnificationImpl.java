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
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.accessibility.MagnificationSettingsController;
import com.android.systemui.model.SysUiState;
import com.android.systemui.model.SysUiStateImpl;
import com.android.systemui.recents.LauncherProxyService;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.utils.windowmanager.WindowManagerProvider;
import com.android.systemui.utils.windowmanager.WindowManagerProviderImpl;
import com.android.systemui.utils.windowmanager.WindowManagerUtils;
import java.io.PrintWriter;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class MagnificationImpl implements Magnification, CommandQueue.Callbacks {
    static final int DELAY_SHOW_MAGNIFICATION_TIMEOUT_MS = 300;
    public final AccessibilityLogger mA11yLogger;
    public final AccessibilityManager mAccessibilityManager;
    public final CommandQueue mCommandQueue;
    public final DisplayTracker mDisplayTracker;
    public final Executor mExecutor;
    DisplayIdIndexSupplier mFullscreenMagnificationControllerSupplier;
    public final AnonymousClass1 mHandler;
    public final LauncherProxyService mLauncherProxyService;
    public MagnificationConnectionImpl mMagnificationConnectionImpl;
    final MagnificationSettingsController.Callback mMagnificationSettingsControllerCallback;
    DisplayIdIndexSupplier mMagnificationSettingsSupplier;
    public final ModeSwitchesController mModeSwitchesController;
    public final SysUiState mSysUiState;
    SparseArray<SparseArray<Float>> mUsersScales;
    DisplayIdIndexSupplier mWindowMagnificationControllerSupplier;
    final WindowMagnifierCallback mWindowMagnifierCallback;

    /* renamed from: com.android.systemui.accessibility.MagnificationImpl$3, reason: invalid class name */
    public class AnonymousClass3 implements WindowMagnifierCallback {
        public AnonymousClass3() {
        }
    }

    /* renamed from: com.android.systemui.accessibility.MagnificationImpl$4, reason: invalid class name */
    public class AnonymousClass4 implements MagnificationSettingsController.Callback {
        public AnonymousClass4() {
        }
    }

    public class FullscreenMagnificationControllerSupplier extends DisplayIdIndexSupplier {
        public final Context mContext;
        public final DisplayManager mDisplayManager;
        public final Executor mExecutor;
        public final Handler mHandler;
        public final IWindowManager mIWindowManager;
        public final WindowManagerProvider mWindowManagerProvider;

        public FullscreenMagnificationControllerSupplier(Context context, DisplayManager displayManager, Handler handler, Executor executor, IWindowManager iWindowManager, WindowManagerProvider windowManagerProvider) {
            super(displayManager);
            this.mContext = context;
            this.mHandler = handler;
            this.mExecutor = executor;
            this.mDisplayManager = displayManager;
            this.mIWindowManager = iWindowManager;
            this.mWindowManagerProvider = windowManagerProvider;
        }

        @Override // com.android.systemui.accessibility.DisplayIdIndexSupplier
        public final Object createInstance(Display display) {
            Context contextCreateWindowContext = this.mContext.createWindowContext(display, 2032, null);
            MagnificationImpl$WindowMagnificationControllerSupplier$$ExternalSyntheticLambda0 magnificationImpl$WindowMagnificationControllerSupplier$$ExternalSyntheticLambda0 = new MagnificationImpl$WindowMagnificationControllerSupplier$$ExternalSyntheticLambda0(this, display, 1);
            contextCreateWindowContext.setTheme(R.style.Theme_SystemUI);
            Executor executor = this.mExecutor;
            DisplayManager displayManager = this.mDisplayManager;
            AccessibilityManager accessibilityManager = (AccessibilityManager) contextCreateWindowContext.getSystemService(AccessibilityManager.class);
            ((WindowManagerProviderImpl) this.mWindowManagerProvider).getClass();
            return new FullscreenMagnificationController(contextCreateWindowContext, this.mHandler, executor, displayManager, accessibilityManager, WindowManagerUtils.getWindowManager(contextCreateWindowContext), this.mIWindowManager, magnificationImpl$WindowMagnificationControllerSupplier$$ExternalSyntheticLambda0);
        }
    }

    public class SettingsSupplier extends DisplayIdIndexSupplier {
        public final Context mContext;
        public final SecureSettings mSecureSettings;
        public final MagnificationSettingsController.Callback mSettingsControllerCallback;
        public final WindowManagerProvider mWindowManagerProvider;

        public SettingsSupplier(Context context, MagnificationSettingsController.Callback callback, DisplayManager displayManager, SecureSettings secureSettings, WindowManagerProvider windowManagerProvider) {
            super(displayManager);
            this.mContext = context;
            this.mSettingsControllerCallback = callback;
            this.mSecureSettings = secureSettings;
            this.mWindowManagerProvider = windowManagerProvider;
        }

        @Override // com.android.systemui.accessibility.DisplayIdIndexSupplier
        public final Object createInstance(Display display) {
            Context contextCreateWindowContext = this.mContext.createWindowContext(display, 2032, null);
            contextCreateWindowContext.setTheme(R.style.Theme_SystemUI);
            return new MagnificationSettingsController(contextCreateWindowContext, new SfVsyncFrameCallbackProvider(), this.mSettingsControllerCallback, this.mSecureSettings, this.mWindowManagerProvider);
        }
    }

    public class WindowMagnificationControllerSupplier extends DisplayIdIndexSupplier {
        public final Context mContext;
        public final Handler mHandler;
        public final SecureSettings mSecureSettings;
        public final SysUiState mSysUiState;
        public final WindowMagnifierCallback mWindowMagnifierCallback;
        public final WindowManagerProvider mWindowManagerProvider;

        public WindowMagnificationControllerSupplier(Context context, Handler handler, WindowMagnifierCallback windowMagnifierCallback, DisplayManager displayManager, SysUiState sysUiState, SecureSettings secureSettings, WindowManagerProvider windowManagerProvider) {
            super(displayManager);
            this.mContext = context;
            this.mHandler = handler;
            this.mWindowMagnifierCallback = windowMagnifierCallback;
            this.mSysUiState = sysUiState;
            this.mSecureSettings = secureSettings;
            this.mWindowManagerProvider = windowManagerProvider;
        }

        @Override // com.android.systemui.accessibility.DisplayIdIndexSupplier
        public final Object createInstance(Display display) {
            Context contextCreateWindowContext = this.mContext.createWindowContext(display, 2032, null);
            ((WindowManagerProviderImpl) this.mWindowManagerProvider).getClass();
            WindowManager windowManager = WindowManagerUtils.getWindowManager(contextCreateWindowContext);
            contextCreateWindowContext.setTheme(R.style.Theme_SystemUI);
            MagnificationImpl$WindowMagnificationControllerSupplier$$ExternalSyntheticLambda0 magnificationImpl$WindowMagnificationControllerSupplier$$ExternalSyntheticLambda0 = new MagnificationImpl$WindowMagnificationControllerSupplier$$ExternalSyntheticLambda0(this, display, 0);
            return new WindowMagnificationController(contextCreateWindowContext, this.mHandler, new WindowMagnificationAnimationController(contextCreateWindowContext), null, new SurfaceControl.Transaction(), this.mWindowMagnifierCallback, this.mSysUiState, this.mSecureSettings, magnificationImpl$WindowMagnificationControllerSupplier$$ExternalSyntheticLambda0, windowManager);
        }
    }

    public MagnificationImpl(Context context, Handler handler, Executor executor, CommandQueue commandQueue, ModeSwitchesController modeSwitchesController, SysUiState sysUiState, LauncherProxyService launcherProxyService, SecureSettings secureSettings, DisplayTracker displayTracker, DisplayManager displayManager, AccessibilityLogger accessibilityLogger, IWindowManager iWindowManager, AccessibilityManager accessibilityManager, WindowManagerProvider windowManagerProvider) {
        this(context, handler.getLooper(), executor, commandQueue, modeSwitchesController, sysUiState, launcherProxyService, secureSettings, displayTracker, displayManager, accessibilityLogger, iWindowManager, accessibilityManager, windowManagerProvider);
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(final PrintWriter printWriter, String[] strArr) {
        printWriter.println("Magnification");
        DisplayIdIndexSupplier displayIdIndexSupplier = this.mWindowMagnificationControllerSupplier;
        Consumer consumer = new Consumer() { // from class: com.android.systemui.accessibility.MagnificationImpl$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PrintWriter printWriter2 = printWriter;
                WindowMagnificationController windowMagnificationController = (WindowMagnificationController) obj;
                int i = MagnificationImpl.DELAY_SHOW_MAGNIFICATION_TIMEOUT_MS;
                printWriter2.println("WindowMagnificationController (displayId=" + windowMagnificationController.mDisplayId + "):");
                StringBuilder sbM = MagnificationImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("      mOverlapWithGestureInsets:"), windowMagnificationController.mOverlapWithGestureInsets, printWriter2, "      mScale:"), windowMagnificationController.mScale, printWriter2, "      mWindowBounds:");
                sbM.append(windowMagnificationController.mWindowBounds);
                printWriter2.println(sbM.toString());
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
                MagnificationImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("      mSystemGestureTop:"), windowMagnificationController.mSystemGestureTop, printWriter2, "      mMagnificationFrameOffsetX:"), windowMagnificationController.mMagnificationFrameOffsetX, printWriter2, "      mMagnificationFrameOffsetY:"), windowMagnificationController.mMagnificationFrameOffsetY, printWriter2);
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
        this.mLauncherProxyService.addCallback(new LauncherProxyService.LauncherProxyListener() { // from class: com.android.systemui.accessibility.MagnificationImpl.2
            @Override // com.android.systemui.recents.LauncherProxyService.LauncherProxyListener
            public final void onConnectionChanged(boolean z) {
                if (z) {
                    MagnificationImpl magnificationImpl = MagnificationImpl.this;
                    DisplayIdIndexSupplier displayIdIndexSupplier = magnificationImpl.mWindowMagnificationControllerSupplier;
                    magnificationImpl.mDisplayTracker.getClass();
                    WindowMagnificationController windowMagnificationController = (WindowMagnificationController) displayIdIndexSupplier.mSparseArray.get(0);
                    if (windowMagnificationController != null) {
                        windowMagnificationController.updateSysUIState(true);
                    } else {
                        ((SysUiStateImpl) magnificationImpl.mSysUiState.setFlag(524288L, false)).commitUpdate();
                    }
                }
            }
        });
    }

    /* JADX WARN: Type inference failed for: r4v0, types: [android.os.Handler, com.android.systemui.accessibility.MagnificationImpl$1] */
    public MagnificationImpl(Context context, Looper looper, Executor executor, CommandQueue commandQueue, ModeSwitchesController modeSwitchesController, SysUiState sysUiState, LauncherProxyService launcherProxyService, SecureSettings secureSettings, DisplayTracker displayTracker, DisplayManager displayManager, AccessibilityLogger accessibilityLogger, IWindowManager iWindowManager, AccessibilityManager accessibilityManager, WindowManagerProvider windowManagerProvider) {
        this.mUsersScales = new SparseArray<>();
        AnonymousClass3 anonymousClass3 = new AnonymousClass3();
        this.mWindowMagnifierCallback = anonymousClass3;
        AnonymousClass4 anonymousClass4 = new AnonymousClass4();
        this.mMagnificationSettingsControllerCallback = anonymousClass4;
        ?? r4 = new Handler(looper) { // from class: com.android.systemui.accessibility.MagnificationImpl.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                MagnificationModeSwitch magnificationModeSwitch;
                WindowMagnificationSettings windowMagnificationSettings;
                if (message.what == 1) {
                    int i = message.arg1;
                    int i2 = message.arg2;
                    MagnificationImpl magnificationImpl = MagnificationImpl.this;
                    MagnificationSettingsController magnificationSettingsController = (MagnificationSettingsController) magnificationImpl.mMagnificationSettingsSupplier.get(i);
                    boolean z = false;
                    if (magnificationSettingsController != null && (windowMagnificationSettings = magnificationSettingsController.mWindowMagnificationSettings) != null) {
                        z = windowMagnificationSettings.mIsVisible;
                    }
                    if (z || (magnificationModeSwitch = (MagnificationModeSwitch) magnificationImpl.mModeSwitchesController.mSwitchSupplier.get(i)) == null) {
                        return;
                    }
                    magnificationModeSwitch.showButton(i2, true);
                }
            }
        };
        this.mHandler = r4;
        this.mExecutor = executor;
        this.mAccessibilityManager = accessibilityManager;
        this.mCommandQueue = commandQueue;
        this.mModeSwitchesController = modeSwitchesController;
        this.mSysUiState = sysUiState;
        this.mLauncherProxyService = launcherProxyService;
        this.mDisplayTracker = displayTracker;
        this.mA11yLogger = accessibilityLogger;
        this.mWindowMagnificationControllerSupplier = new WindowMagnificationControllerSupplier(context, r4, anonymousClass3, displayManager, sysUiState, secureSettings, windowManagerProvider);
        this.mFullscreenMagnificationControllerSupplier = new FullscreenMagnificationControllerSupplier(context, displayManager, r4, executor, iWindowManager, windowManagerProvider);
        this.mMagnificationSettingsSupplier = new SettingsSupplier(context, anonymousClass4, displayManager, secureSettings, windowManagerProvider);
        modeSwitchesController.mClickListenerDelegate = new MagnificationImpl$$ExternalSyntheticLambda2(this);
    }
}
