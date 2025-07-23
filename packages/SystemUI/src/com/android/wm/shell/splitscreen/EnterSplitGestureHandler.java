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
import android.os.Debug;
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
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.splitscreen.EnterSplitGestureHandler;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.sysui.ShellInit;
import com.samsung.android.rune.CoreRune;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class EnterSplitGestureHandler {
    public static final boolean DEBUG = CoreRune.IS_DEBUG_LEVEL_MID;
    public static final String TAG = "EnterSplitGestureHandler";
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
    public AnonymousClass5 mObserver;
    public final Optional mSplitScreenController;
    public final IWindowManager mWindowManagerService;
    public final AnonymousClass1 mGestureExclusionListener = new AnonymousClass1();
    public final Rect mTmpBounds = new Rect();
    public int mDisplayDeviceType = -1;
    public final IActivityTaskManager mAtm = ActivityTaskManager.getService();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.splitscreen.EnterSplitGestureHandler$1, reason: invalid class name */
    public class AnonymousClass1 extends ISystemGestureExclusionListener.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;

        public AnonymousClass1() {
        }

        public final void onSystemGestureExclusionChanged(final int i, final Region region, Region region2) {
            EnterSplitGestureHandler.this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.splitscreen.EnterSplitGestureHandler$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    EnterSplitGestureHandler.AnonymousClass1 anonymousClass1 = EnterSplitGestureHandler.AnonymousClass1.this;
                    int i2 = i;
                    Region region3 = region;
                    if (i2 != 0) {
                        int i3 = EnterSplitGestureHandler.AnonymousClass1.$r8$clinit;
                        anonymousClass1.getClass();
                    } else {
                        TwoFingerSwipeGestureDetector twoFingerSwipeGestureDetector = EnterSplitGestureHandler.this.mGestureDetector;
                        if (twoFingerSwipeGestureDetector != null) {
                            twoFingerSwipeGestureDetector.setGestureExclusionRegion(region3);
                        }
                    }
                }
            });
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class EnterSplitGestureEventListener extends BatchedInputEventReceiver {
        public EnterSplitGestureEventListener(InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper, Choreographer.getSfInstance());
        }

        public final void onInputEvent(InputEvent inputEvent) {
            EnterSplitGestureHandler.this.mGestureDetector.onInputEvent(inputEvent);
            finishInputEvent(inputEvent, true);
        }
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
                boolean z = EnterSplitGestureHandler.DEBUG;
                enterSplitGestureHandler.getClass();
                return new TwoFingerSwipeGestureDetector.GestureListener() { // from class: com.android.wm.shell.splitscreen.EnterSplitGestureHandler.2
                    public final void onCommitted(final int i) {
                        EnterSplitGestureHandler.this.mSplitScreenController.ifPresentOrElse(new Consumer() { // from class: com.android.wm.shell.splitscreen.EnterSplitGestureHandler$2$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj2) {
                                int i2 = i;
                                SplitScreenController.SplitScreenImpl splitScreenImpl = ((SplitScreenController) obj2).mImpl;
                                splitScreenImpl.getClass();
                                SplitScreenController.this.mMainExecutor.execute(new SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda9(splitScreenImpl, i2, Debug.getCaller()));
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
                        boolean z2 = enterSplitGestureHandler3.mDisplayDeviceType == 5;
                        enterSplitGestureHandler3.getClass();
                        boolean z3 = (CoreRune.MW_MULTI_SPLIT_FULL_TO_SPLIT_BY_GESTURE && !z2) || c == 2;
                        boolean z4 = enterSplitGestureHandler3.mNavMode != 3;
                        int i = z3 ? 5 : 0;
                        if (z4) {
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
        Iterator<AccessibilityServiceInfo> it = ((AccessibilityManager) this.mContext.getSystemService(AccessibilityManager.class)).getInstalledAccessibilityServiceList().iterator();
        while (it.hasNext()) {
            ServiceInfo serviceInfo = it.next().getResolveInfo().serviceInfo;
            if (serviceInfo.loadLabel(this.mContext.getPackageManager()).toString().equals("TalkBack")) {
                return new ComponentName(serviceInfo.packageName, serviceInfo.name);
            }
        }
        return null;
    }

    public final void updateEnableState(String str) {
        boolean z = DEBUG;
        String str2 = TAG;
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
                    boolean z6 = EnterSplitGestureHandler.DEBUG;
                    ((SplitScreenController) obj).mGestureStarter.ifPresent(new SplitScreenController$$ExternalSyntheticLambda10(z5, 0));
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
        if (z2) {
            this.mInputMonitor = InputManager.getInstance().monitorGestureInput("enter-split", i, 1);
            try {
                this.mInputEventReceiver = new EnterSplitGestureEventListener(this.mInputMonitor.getInputChannel(), Looper.myLooper());
                try {
                    this.mWindowManagerService.registerSystemGestureExclusionListener(this.mGestureExclusionListener, i);
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
            this.mWindowManagerService.unregisterSystemGestureExclusionListener(this.mGestureExclusionListener, i);
        } catch (RemoteException | IllegalArgumentException e3) {
            Slog.e(str2, "Failed to unregister window manager callbacks", e3);
        }
    }
}
