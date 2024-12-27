package com.android.server.wm;

import android.os.Looper;
import android.os.RemoteException;
import android.widget.Toast;

import com.android.server.policy.PhoneWindowManager;

import com.samsung.android.server.util.FullScreenAppsSupportUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class WindowManagerService$$ExternalSyntheticLambda19
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ WindowManagerService$$ExternalSyntheticLambda19(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                WindowManagerService windowManagerService = (WindowManagerService) obj;
                boolean isKeyguardShowing =
                        ((PhoneWindowManager) windowManagerService.mPolicy).isKeyguardShowing();
                if (windowManagerService.mDispatchedKeyguardLockedState == isKeyguardShowing) {
                    return;
                }
                int beginBroadcast =
                        windowManagerService.mKeyguardLockedStateListeners.beginBroadcast();
                for (int i2 = 0; i2 < beginBroadcast; i2++) {
                    try {
                        windowManagerService
                                .mKeyguardLockedStateListeners
                                .getBroadcastItem(i2)
                                .onKeyguardLockedStateChanged(isKeyguardShowing);
                    } catch (RemoteException unused) {
                    }
                }
                windowManagerService.mKeyguardLockedStateListeners.finishBroadcast();
                windowManagerService.mDispatchedKeyguardLockedState = isKeyguardShowing;
                return;
            case 1:
                WindowManagerService windowManagerService2 = (WindowManagerService) obj;
                WindowManagerGlobalLock windowManagerGlobalLock = windowManagerService2.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        windowManagerService2.mAtmService.deferWindowLayout();
                        windowManagerService2.mRoot.forAllDisplays(
                                new WindowManagerService$$ExternalSyntheticLambda7(2));
                        windowManagerService2.mAtmService.continueWindowLayout();
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                FullScreenAppsSupportUtils fullScreenAppsSupportUtils =
                        FullScreenAppsSupportUtils.LazyHolder.sUtils;
                synchronized (fullScreenAppsSupportUtils) {
                    fullScreenAppsSupportUtils.mCached = false;
                }
                return;
            case 2:
                WindowManagerService windowManagerService3 = (WindowManagerService) obj;
                Toast.makeText(
                                windowManagerService3.mContext,
                                Looper.getMainLooper(),
                                windowManagerService3.mContext.getString(17042724),
                                0)
                        .show();
                return;
            default:
                WindowManagerService.SettingsObserver settingsObserver =
                        (WindowManagerService.SettingsObserver) obj;
                settingsObserver.updateSystemUiSettings(false);
                settingsObserver.updateMaximumObscuringOpacityForTouch();
                settingsObserver.updateDisableSecureWindows();
                return;
        }
    }
}
