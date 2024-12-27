package com.android.server.wm;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import android.view.IWindow;
import android.view.SurfaceControl;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.samsung.android.rune.CoreRune;

public final class ShellRoot {
    public IWindow mAccessibilityWindow;
    public ShellRoot$$ExternalSyntheticLambda1 mAccessibilityWindowDeath;
    public IWindow mClient;
    public final ShellRoot$$ExternalSyntheticLambda0 mDeathRecipient;
    public final DisplayContent mDisplayContent;
    public final SurfaceControl mSurfaceControl;
    public WindowToken mToken;
    public final int mWindowType;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [android.os.IBinder$DeathRecipient, com.android.server.wm.ShellRoot$$ExternalSyntheticLambda0] */
    public ShellRoot(IWindow iWindow, DisplayContent displayContent, final int i) {
        WindowToken windowToken;
        this.mSurfaceControl = null;
        this.mDisplayContent = displayContent;
        ?? r2 = new IBinder.DeathRecipient() { // from class: com.android.server.wm.ShellRoot$$ExternalSyntheticLambda0
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                ShellRoot shellRoot = ShellRoot.this;
                int i2 = i;
                DisplayContent displayContent2 = shellRoot.mDisplayContent;
                WindowManagerGlobalLock windowManagerGlobalLock = displayContent2.mWmService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        ShellRoot shellRoot2 = (ShellRoot) displayContent2.mShellRoots.get(i2);
                        if (shellRoot2 == null) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        shellRoot2.clear();
                        displayContent2.mShellRoots.remove(i2);
                        WindowManagerService.resetPriorityAfterLockedSection();
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
            }
        };
        this.mDeathRecipient = r2;
        try {
            iWindow.asBinder().linkToDeath(r2, 0);
            this.mClient = iWindow;
            if (i == 0) {
                this.mWindowType = 2034;
            } else {
                if (i != 1) {
                    throw new IllegalArgumentException(NandswapManager$$ExternalSyntheticOutline0.m(i, " is not an acceptable shell root layer."));
                }
                this.mWindowType = 2038;
            }
            WindowManagerService windowManagerService = displayContent.mWmService;
            IBinder asBinder = iWindow.asBinder();
            int i2 = this.mWindowType;
            if (CoreRune.FW_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY && i2 == 2632) {
                windowToken = new TransientLaunchOverlayToken(windowManagerService, asBinder, 2632, true, displayContent, true, false, false, null);
                displayContent.mTransientLaunchOverlayTokens.add(windowToken);
            } else {
                windowToken = new WindowToken(windowManagerService, asBinder, i2, true, displayContent, true, false, false, null);
            }
            this.mToken = windowToken;
            SurfaceControl build = windowToken.makeChildSurface(null).setContainerLayer().setName("Shell Root Leash " + displayContent.mDisplayId).setCallsite("ShellRoot").build();
            this.mSurfaceControl = build;
            this.mToken.getPendingTransaction().show(build);
        } catch (RemoteException e) {
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "Unable to add shell root layer ", " on display ");
            m.append(displayContent.mDisplayId);
            Slog.e("ShellRoot", m.toString(), e);
        }
    }

    public final void clear() {
        IWindow iWindow = this.mClient;
        if (iWindow != null) {
            iWindow.asBinder().unlinkToDeath(this.mDeathRecipient, 0);
            this.mClient = null;
        }
        WindowToken windowToken = this.mToken;
        if (windowToken != null) {
            windowToken.removeImmediately();
            this.mToken = null;
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.wm.ShellRoot$$ExternalSyntheticLambda1] */
    public final void setAccessibilityWindow(IWindow iWindow) {
        IWindow iWindow2 = this.mAccessibilityWindow;
        if (iWindow2 != null) {
            iWindow2.asBinder().unlinkToDeath(this.mAccessibilityWindowDeath, 0);
        }
        this.mAccessibilityWindow = iWindow;
        if (iWindow != null) {
            try {
                this.mAccessibilityWindowDeath = new IBinder.DeathRecipient() { // from class: com.android.server.wm.ShellRoot$$ExternalSyntheticLambda1
                    @Override // android.os.IBinder.DeathRecipient
                    public final void binderDied() {
                        ShellRoot shellRoot = ShellRoot.this;
                        WindowManagerGlobalLock windowManagerGlobalLock = shellRoot.mDisplayContent.mWmService.mGlobalLock;
                        WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock) {
                            try {
                                shellRoot.mAccessibilityWindow = null;
                                shellRoot.setAccessibilityWindow(null);
                            } catch (Throwable th) {
                                WindowManagerService.resetPriorityAfterLockedSection();
                                throw th;
                            }
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                };
                iWindow.asBinder().linkToDeath(this.mAccessibilityWindowDeath, 0);
            } catch (RemoteException unused) {
                this.mAccessibilityWindow = null;
            }
        }
    }
}
