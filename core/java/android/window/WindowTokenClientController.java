package android.window;

import android.app.ActivityThread;
import android.app.IApplicationThread;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Log;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;

public class WindowTokenClientController {
    private static final String TAG = WindowTokenClientController.class.getSimpleName();
    private static WindowTokenClientController sController;
    private final Object mLock = new Object();
    private final IApplicationThread mAppThread =
            ActivityThread.currentActivityThread().getApplicationThread();
    private final ArrayMap<IBinder, WindowTokenClient> mWindowTokenClientMap = new ArrayMap<>();

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

    public static void overrideForTesting(WindowTokenClientController controller) {
        synchronized (WindowTokenClientController.class) {
            sController = controller;
        }
    }

    public static WindowTokenClientController createInstanceForTesting() {
        return new WindowTokenClientController();
    }

    private WindowTokenClientController() {}

    public Context getWindowContext(IBinder clientToken) {
        WindowTokenClient windowTokenClient;
        synchronized (this.mLock) {
            windowTokenClient = this.mWindowTokenClientMap.get(clientToken);
        }
        if (windowTokenClient != null) {
            return windowTokenClient.getContext();
        }
        return null;
    }

    public boolean attachToDisplayArea(
            WindowTokenClient client, int type, int displayId, Bundle options) {
        try {
            WindowContextInfo info =
                    getWindowManagerService()
                            .attachWindowContextToDisplayArea(
                                    this.mAppThread, client, type, displayId, options);
            if (info == null) {
                return false;
            }
            onWindowContextTokenAttached(client, info, false);
            return true;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean attachToDisplayContent(WindowTokenClient client, int displayId) {
        IWindowManager wms = getWindowManagerService();
        if (wms == null) {
            return false;
        }
        try {
            WindowContextInfo info =
                    wms.attachWindowContextToDisplayContent(this.mAppThread, client, displayId);
            if (info == null) {
                return false;
            }
            onWindowContextTokenAttached(client, info, false);
            return true;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean attachToWindowToken(WindowTokenClient client, IBinder windowToken) {
        try {
            WindowContextInfo info =
                    getWindowManagerService()
                            .attachWindowContextToWindowToken(this.mAppThread, client, windowToken);
            if (info == null) {
                return false;
            }
            onWindowContextTokenAttached(client, info, true);
            return true;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void detachIfNeeded(WindowTokenClient client) {
        synchronized (this.mLock) {
            if (this.mWindowTokenClientMap.remove(client) == null) {
                return;
            }
            try {
                getWindowManagerService().detachWindowContext(client);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    private void onWindowContextTokenAttached(
            WindowTokenClient client, WindowContextInfo info, boolean shouldReportConfigChange) {
        synchronized (this.mLock) {
            this.mWindowTokenClientMap.put(client, client);
        }
        if (shouldReportConfigChange) {
            client.postOnConfigurationChanged(info.getConfiguration(), info.getDisplayId());
        } else {
            client.onConfigurationChanged(info.getConfiguration(), info.getDisplayId(), false);
        }
    }

    public void onWindowContextInfoChanged(IBinder clientToken, WindowContextInfo info) {
        WindowTokenClient windowTokenClient = getWindowTokenClient(clientToken);
        if (windowTokenClient != null) {
            windowTokenClient.onConfigurationChanged(info.getConfiguration(), info.getDisplayId());
        }
    }

    public void onWindowContextWindowRemoved(IBinder clientToken) {
        WindowTokenClient windowTokenClient = getWindowTokenClient(clientToken);
        if (windowTokenClient != null) {
            windowTokenClient.onWindowTokenRemoved();
        }
    }

    private WindowTokenClient getWindowTokenClient(IBinder clientToken) {
        WindowTokenClient windowTokenClient;
        synchronized (this.mLock) {
            windowTokenClient = this.mWindowTokenClientMap.get(clientToken);
        }
        if (windowTokenClient == null) {
            Log.w(TAG, "Can't find attached WindowTokenClient for " + clientToken);
        }
        return windowTokenClient;
    }

    public IWindowManager getWindowManagerService() {
        return WindowManagerGlobal.getWindowManagerService();
    }
}
