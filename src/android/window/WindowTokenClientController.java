package android.window;

import android.app.ActivityThread;
import android.app.IApplicationThread;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArraySet;
import android.util.Log;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags;

/* loaded from: classes5.dex */
public class WindowTokenClientController {
    private static final String TAG = "WindowTokenClientController";
    private static WindowTokenClientController sController;
    private final Object mLock = new Object();
    private final IApplicationThread mAppThread = ActivityThread.currentActivityThread().getApplicationThread();
    private final Handler mHandler = ActivityThread.currentActivityThread().getHandler();
    private final ArraySet<WindowTokenClient> mWindowTokenClients = new ArraySet<>();

    public static WindowTokenClientController getInstance() {
        WindowTokenClientController windowTokenClientController;
        synchronized (WindowTokenClientController.class) {
            if (sController == null) {
                sController = new WindowTokenClientController();
            }
            windowTokenClientController = sController;
        }
        return windowTokenClientController;
    }

    public static void overrideForTesting(WindowTokenClientController windowTokenClientController) {
        synchronized (WindowTokenClientController.class) {
            sController = windowTokenClientController;
        }
    }

    public static WindowTokenClientController createInstanceForTesting() {
        return new WindowTokenClientController();
    }

    private WindowTokenClientController() {
    }

    public Context getWindowContext(IBinder iBinder) {
        if (!(iBinder instanceof WindowTokenClient)) {
            return null;
        }
        WindowTokenClient windowTokenClient = (WindowTokenClient) iBinder;
        synchronized (this.mLock) {
            if (this.mWindowTokenClients.contains(windowTokenClient)) {
                return windowTokenClient.getContext();
            }
            return null;
        }
    }

    public boolean attachToDisplayArea(WindowTokenClient windowTokenClient, int i, int i2, Bundle bundle) {
        try {
            WindowContextInfo attachWindowContextToDisplayArea = getWindowManagerService().attachWindowContextToDisplayArea(this.mAppThread, windowTokenClient, i, i2, bundle);
            if (attachWindowContextToDisplayArea == null) {
                return false;
            }
            onWindowContextTokenAttached(windowTokenClient, attachWindowContextToDisplayArea, false);
            return true;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean attachToDisplayContent(WindowTokenClient windowTokenClient, int i) {
        IWindowManager windowManagerService = getWindowManagerService();
        if (windowManagerService == null) {
            if (Flags.trackSystemUiContextBeforeWms()) {
                recordWindowContextToken(windowTokenClient);
            }
            return false;
        }
        try {
            WindowContextInfo attachWindowContextToDisplayContent = windowManagerService.attachWindowContextToDisplayContent(this.mAppThread, windowTokenClient, i);
            if (attachWindowContextToDisplayContent == null) {
                return false;
            }
            onWindowContextTokenAttached(windowTokenClient, attachWindowContextToDisplayContent, false);
            return true;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (Exception e2) {
            Log.e(TAG, "Failed attachToDisplayContent", e2);
            return false;
        }
    }

    public boolean attachToWindowToken(WindowTokenClient windowTokenClient, IBinder iBinder) {
        try {
            WindowContextInfo attachWindowContextToWindowToken = getWindowManagerService().attachWindowContextToWindowToken(this.mAppThread, windowTokenClient, iBinder);
            if (attachWindowContextToWindowToken == null) {
                return false;
            }
            onWindowContextTokenAttached(windowTokenClient, attachWindowContextToWindowToken, true);
            return true;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void detachIfNeeded(WindowTokenClient windowTokenClient) {
        synchronized (this.mLock) {
            if (this.mWindowTokenClients.remove(windowTokenClient)) {
                IWindowManager windowManagerService = getWindowManagerService();
                if (windowManagerService == null) {
                    return;
                }
                try {
                    windowManagerService.detachWindowContext(windowTokenClient);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    public void reparentToDisplayArea(WindowTokenClient windowTokenClient, int i) {
        try {
            if (getWindowManagerService().reparentWindowContextToDisplayArea(this.mAppThread, windowTokenClient, i)) {
                return;
            }
            Log.e(TAG, "Didn't succeed reparenting of " + windowTokenClient + " to displayId=" + i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void onWindowContextTokenAttached(WindowTokenClient windowTokenClient, WindowContextInfo windowContextInfo, boolean z) {
        recordWindowContextToken(windowTokenClient);
        if (z) {
            windowTokenClient.postOnConfigurationChanged(windowContextInfo.getConfiguration(), windowContextInfo.getDisplayId());
        } else {
            windowTokenClient.onConfigurationChanged(windowContextInfo.getConfiguration(), windowContextInfo.getDisplayId(), false);
        }
    }

    private void recordWindowContextToken(WindowTokenClient windowTokenClient) {
        synchronized (this.mLock) {
            this.mWindowTokenClients.add(windowTokenClient);
        }
    }

    public void onWindowContextInfoChanged(IBinder iBinder, WindowContextInfo windowContextInfo) {
        WindowTokenClient windowTokenClientIfAttached = getWindowTokenClientIfAttached(iBinder);
        if (windowTokenClientIfAttached != null) {
            windowTokenClientIfAttached.onConfigurationChanged(windowContextInfo.getConfiguration(), windowContextInfo.getDisplayId());
        }
    }

    public void onWindowContextWindowRemoved(IBinder iBinder) {
        WindowTokenClient windowTokenClientIfAttached = getWindowTokenClientIfAttached(iBinder);
        if (windowTokenClientIfAttached != null) {
            windowTokenClientIfAttached.onWindowTokenRemoved();
        }
    }

    public void onWindowConfigurationChanged(IBinder iBinder, Configuration configuration, int i) {
        WindowTokenClient windowTokenClientIfAttached = getWindowTokenClientIfAttached(iBinder);
        if (windowTokenClientIfAttached != null) {
            if (this.mHandler.getLooper().isCurrentThread()) {
                windowTokenClientIfAttached.onConfigurationChanged(configuration, i);
            } else {
                windowTokenClientIfAttached.postOnConfigurationChanged(configuration, i);
            }
        }
    }

    private WindowTokenClient getWindowTokenClientIfAttached(IBinder iBinder) {
        if (!(iBinder instanceof WindowTokenClient)) {
            Log.e(TAG, "getWindowTokenClient failed for non-window token " + iBinder);
            return null;
        }
        WindowTokenClient windowTokenClient = (WindowTokenClient) iBinder;
        synchronized (this.mLock) {
            if (this.mWindowTokenClients.contains(windowTokenClient)) {
                return windowTokenClient;
            }
            Log.w(TAG, "Can't find attached WindowTokenClient for " + iBinder);
            return null;
        }
    }

    public IWindowManager getWindowManagerService() {
        return WindowManagerGlobal.getWindowManagerService();
    }
}
