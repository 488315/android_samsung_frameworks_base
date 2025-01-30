package com.android.wm.shell.splitscreen;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ServiceInfo;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Slog;
import android.view.BatchedInputEventReceiver;
import android.view.Choreographer;
import android.view.ISystemGestureExclusionListener;
import android.view.IWindowManager;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputMonitor;
import android.view.TwoFingerSwipeGestureDetector;
import android.view.accessibility.AccessibilityManager;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.splitscreen.EnterSplitGestureHandler;
import com.android.wm.shell.sysui.ShellInit;
import com.samsung.android.rune.CoreRune;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class EnterSplitGestureHandler {
    public static final boolean DEBUG;
    public static final String TAG;
    public final Context mContext;
    public final DisplayController mDisplayController;
    public final int mDisplayId;
    public final TwoFingerSwipeGestureDetector mGestureDetector;
    public final Handler mHandler;
    public EnterSplitGestureEventListener mInputEventReceiver;
    public InputMonitor mInputMonitor;
    public boolean mIsA11yButtonEnabled;
    public boolean mIsCommonEnabled;
    public boolean mIsDeviceProvisioned;
    public boolean mIsEnabled;
    public boolean mIsLockTaskMode;
    public boolean mIsSettingEnabled;
    public boolean mIsStandAlone;
    public boolean mIsSupportSplitScreen;
    public boolean mIsSystemUiStateValid;
    public boolean mIsTalkbackEnabled;
    public boolean mIsUserSetupComplete;
    public final ShellExecutor mMainExecutor;
    public int mNavMode;
    public C40965 mObserver;
    public final Optional mSplitScreenController;
    public final IWindowManager mWindowManagerService;
    public final C40921 mGestureExclusionListener = new C40921();
    public final Rect mTmpBounds = new Rect();
    public int mDisplayDeviceType = -1;
    public final IActivityTaskManager mAtm = ActivityTaskManager.getService();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.splitscreen.EnterSplitGestureHandler$1 */
    public final class C40921 extends ISystemGestureExclusionListener.Stub {
        public C40921() {
        }

        public final void onSystemGestureExclusionChanged(final int i, final Region region, Region region2) {
            ((HandlerExecutor) EnterSplitGestureHandler.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.splitscreen.EnterSplitGestureHandler$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    EnterSplitGestureHandler.C40921 c40921 = EnterSplitGestureHandler.C40921.this;
                    int i2 = i;
                    Region region3 = region;
                    if (i2 != 0) {
                        c40921.getClass();
                        return;
                    }
                    TwoFingerSwipeGestureDetector twoFingerSwipeGestureDetector = EnterSplitGestureHandler.this.mGestureDetector;
                    if (twoFingerSwipeGestureDetector != null) {
                        twoFingerSwipeGestureDetector.setGestureExclusionRegion(region3);
                    }
                }
            });
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class EnterSplitGestureEventListener extends BatchedInputEventReceiver {
        public EnterSplitGestureEventListener(InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper, Choreographer.getSfInstance());
        }

        public final void onInputEvent(InputEvent inputEvent) {
            EnterSplitGestureHandler.this.mGestureDetector.onInputEvent(inputEvent);
            finishInputEvent(inputEvent, true);
        }
    }

    static {
        DEBUG = CoreRune.SAFE_DEBUG || CoreRune.IS_DEBUG_LEVEL_MID;
        TAG = "EnterSplitGestureHandler";
    }

    public EnterSplitGestureHandler(Context context, ShellInit shellInit, Handler handler, DisplayController displayController, ShellExecutor shellExecutor, IWindowManager iWindowManager, Optional<SplitScreenController> optional) {
        this.mContext = context;
        this.mHandler = handler;
        this.mDisplayId = context.getDisplayId();
        this.mDisplayController = displayController;
        this.mMainExecutor = shellExecutor;
        this.mWindowManagerService = iWindowManager;
        this.mSplitScreenController = optional;
        this.mGestureDetector = new TwoFingerSwipeGestureDetector(context, new Function() { // from class: com.android.wm.shell.splitscreen.EnterSplitGestureHandler$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                final EnterSplitGestureHandler enterSplitGestureHandler = EnterSplitGestureHandler.this;
                final TwoFingerSwipeGestureDetector twoFingerSwipeGestureDetector = (TwoFingerSwipeGestureDetector) obj;
                enterSplitGestureHandler.getClass();
                return new TwoFingerSwipeGestureDetector.GestureListener() { // from class: com.android.wm.shell.splitscreen.EnterSplitGestureHandler.2
                    public final void onCommitted(final int i) {
                        EnterSplitGestureHandler.this.mSplitScreenController.ifPresentOrElse(new Consumer() { // from class: com.android.wm.shell.splitscreen.EnterSplitGestureHandler$2$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj2) {
                                ((SplitScreenController) obj2).mImpl.startSplitByTwoTouchSwipeIfPossible(i);
                            }
                        }, new EnterSplitGestureHandler$2$$ExternalSyntheticLambda1());
                    }

                    public final void onDetected() {
                        InputMonitor inputMonitor = EnterSplitGestureHandler.this.mInputMonitor;
                        if (inputMonitor != null) {
                            inputMonitor.pilferPointers();
                        } else {
                            Slog.e(EnterSplitGestureHandler.TAG, "gesture detected but input monitor is null.");
                        }
                    }

                    public final void onDetecting() {
                        EnterSplitGestureHandler enterSplitGestureHandler2 = EnterSplitGestureHandler.this;
                        DisplayLayout displayLayout = enterSplitGestureHandler2.mDisplayController.getDisplayLayout(enterSplitGestureHandler2.mDisplayId);
                        if (displayLayout == null) {
                            Slog.e(EnterSplitGestureHandler.TAG, "gesture detecting but display frame is null");
                            return;
                        }
                        displayLayout.getDisplayBounds(EnterSplitGestureHandler.this.mTmpBounds);
                        TwoFingerSwipeGestureDetector twoFingerSwipeGestureDetector2 = twoFingerSwipeGestureDetector;
                        Rect rect = EnterSplitGestureHandler.this.mTmpBounds;
                        float density = displayLayout.density();
                        EnterSplitGestureHandler enterSplitGestureHandler3 = EnterSplitGestureHandler.this;
                        char c = displayLayout.mWidth > displayLayout.mHeight ? (char) 2 : (char) 1;
                        boolean z = enterSplitGestureHandler3.mDisplayDeviceType == 5;
                        enterSplitGestureHandler3.getClass();
                        boolean z2 = (CoreRune.MW_MULTI_SPLIT_FULL_TO_SPLIT_BY_GESTURE && !z) || c == 2;
                        boolean z3 = enterSplitGestureHandler3.mNavMode != 3;
                        int i = z2 ? 5 : 0;
                        if (z3) {
                            i |= 8;
                        }
                        twoFingerSwipeGestureDetector2.init(rect, density, i, EnterSplitGestureHandler.this.mIsTalkbackEnabled);
                    }
                };
            }
        }, "EnterSplit");
        shellInit.addInitCallback(new EnterSplitGestureHandler$$ExternalSyntheticLambda1(this, 0), this);
    }

    public final ComponentName getTalkbackComponent() {
        Context context = this.mContext;
        Iterator<AccessibilityServiceInfo> it = ((AccessibilityManager) context.getSystemService(AccessibilityManager.class)).getInstalledAccessibilityServiceList().iterator();
        while (it.hasNext()) {
            ServiceInfo serviceInfo = it.next().getResolveInfo().serviceInfo;
            if (serviceInfo.loadLabel(context.getPackageManager()).toString().equals("TalkBack")) {
                return new ComponentName(serviceInfo.packageName, serviceInfo.name);
            }
        }
        return null;
    }

    public final void updateEnableState(String str) {
        String str2 = TAG;
        boolean z = DEBUG;
        if (z) {
            Slog.d(str2, "updateEnableState caller=" + str);
        }
        boolean z2 = false;
        boolean z3 = this.mNavMode == 2;
        final boolean z4 = this.mIsSupportSplitScreen && this.mIsSettingEnabled && !this.mIsStandAlone && this.mIsDeviceProvisioned && this.mIsUserSetupComplete && !this.mIsLockTaskMode && this.mIsSystemUiStateValid;
        if (!z3 && z4) {
            z2 = true;
        }
        if (z) {
            Slog.d(str2, "updateEnableState state.\n  mIsSupportSplitScreen = " + this.mIsSupportSplitScreen + "\n  mIsSettingEnabled = " + this.mIsSettingEnabled + "\n  mIsStandAlone(need false) = " + this.mIsStandAlone + "\n  mNavMode(need 0) = " + this.mNavMode + "\n  mIsDeviceProvisioned = " + this.mIsDeviceProvisioned + "\n  mIsUserSetupComplete = " + this.mIsUserSetupComplete + "\n  mIsLockTaskMode = " + this.mIsLockTaskMode + "\n  mIsSystemUIStateOk = " + this.mIsSystemUiStateValid + "\n  isEnabled = " + z2);
        }
        if (this.mIsCommonEnabled != z4) {
            this.mIsCommonEnabled = z4;
            this.mSplitScreenController.ifPresent(new Consumer() { // from class: com.android.wm.shell.splitscreen.EnterSplitGestureHandler$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    boolean z5 = z4;
                    SplitScreenController splitScreenController = (SplitScreenController) obj;
                    splitScreenController.getClass();
                    splitScreenController.mGestureStarter.ifPresent(new SplitScreenController$$ExternalSyntheticLambda8(z5, 0));
                }
            });
        }
        if (z2 == this.mIsEnabled) {
            if (z) {
                Slog.d(str2, "enabled same in past.");
                return;
            }
            return;
        }
        int i = this.mDisplayId;
        if (i != 0) {
            Slog.d(str2, "updateEnableState. now default display is supported.");
            return;
        }
        this.mIsEnabled = z2;
        C40921 c40921 = this.mGestureExclusionListener;
        IWindowManager iWindowManager = this.mWindowManagerService;
        if (z2) {
            this.mInputMonitor = InputManager.getInstance().monitorGestureInput("enter-split", i, 1);
            try {
                this.mInputEventReceiver = new EnterSplitGestureEventListener(this.mInputMonitor.getInputChannel(), Looper.myLooper());
                try {
                    iWindowManager.registerSystemGestureExclusionListener(c40921, i);
                    return;
                } catch (RemoteException | IllegalArgumentException e) {
                    Slog.e(str2, "Failed to register window manager callbacks", e);
                    return;
                }
            } catch (Exception e2) {
                throw new RuntimeException("Failed to create input event receiver", e2);
            }
        }
        EnterSplitGestureEventListener enterSplitGestureEventListener = this.mInputEventReceiver;
        if (enterSplitGestureEventListener != null) {
            enterSplitGestureEventListener.dispose();
            this.mInputEventReceiver = null;
        }
        InputMonitor inputMonitor = this.mInputMonitor;
        if (inputMonitor != null) {
            inputMonitor.dispose();
            this.mInputMonitor = null;
        }
        try {
            iWindowManager.unregisterSystemGestureExclusionListener(c40921, i);
        } catch (RemoteException | IllegalArgumentException e3) {
            Slog.e(str2, "Failed to unregister window manager callbacks", e3);
        }
    }
}
