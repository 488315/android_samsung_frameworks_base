package com.samsung.android.desktopmode;

import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Log;

import java.util.Map;

/* loaded from: classes6.dex */
public final class SemDesktopModeManager {
    public static final String LAUNCHER_PACKAGE = "com.sec.android.app.desktoplauncher";
    public static final String UI_SERVICE_PACKAGE = "com.sec.android.desktopmode.uiservice";
    private IDesktopMode mService;
    private static final String TAG = SemDesktopModeManager.class.getSimpleName();
    private static final Object sLock = new Object();
    private Map<DesktopModeListener, DesktopModeListenerDelegate> mDesktopModeListeners = null;
    private Map<DesktopModeBlocker, DesktopModeBlockerDelegate> mBlockers = null;

    public interface DesktopModeBlocker {
        String onBlocked();
    }

    public interface DesktopModeListener {
        void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState);
    }

    @Deprecated
    public interface EventListener {
        void onDesktopDockConnectionChanged(boolean z);

        void onDesktopModeChanged(boolean z);
    }

    private static class DesktopModeListenerDelegate extends IDesktopModeListener.Stub {
        private DesktopModeListener mListener;

        DesktopModeListenerDelegate(DesktopModeListener listener) {
            this.mListener = listener;
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeListener
        public void onDesktopModeStateChanged(SemDesktopModeState state) throws RemoteException {
            DesktopModeListener listener;
            synchronized (SemDesktopModeManager.sLock) {
                listener = this.mListener;
            }
            if (listener != null) {
                Log.d(
                        SemDesktopModeManager.TAG,
                        "onDesktopModeStateChanged() state=" + state + ", listener=" + listener);
                listener.onDesktopModeStateChanged(state);
            }
        }

        public String toString() {
            String valueOf;
            synchronized (SemDesktopModeManager.sLock) {
                valueOf = String.valueOf(this.mListener);
            }
            return valueOf;
        }

        void nullOutListenerLocked() {
            this.mListener = null;
        }
    }

    private static class DesktopModeBlockerDelegate extends IDesktopModeBlocker.Stub {
        private DesktopModeBlocker mBlocker;

        DesktopModeBlockerDelegate(DesktopModeBlocker blocker) {
            this.mBlocker = blocker;
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeBlocker
        public String onBlocked() {
            DesktopModeBlocker blocker;
            synchronized (SemDesktopModeManager.sLock) {
                blocker = this.mBlocker;
            }
            if (blocker != null) {
                return blocker.onBlocked();
            }
            return null;
        }

        public String toString() {
            String valueOf;
            synchronized (SemDesktopModeManager.sLock) {
                valueOf = String.valueOf(this.mBlocker);
            }
            return valueOf;
        }

        void nullOutBlockerLocked() {
            this.mBlocker = null;
        }
    }

    public SemDesktopModeManager(Context context, IDesktopMode service) {
        this.mService = service;
    }

    @Deprecated
    public static void registerListener(EventListener listener) {
        Log.w(
                TAG,
                "registerListener(EventListener) is removed.  Please use"
                    + " registerListener(DesktopModeListener) instead.");
    }

    public void registerListener(DesktopModeListener listener) {
        synchronized (sLock) {
            if (listener == null) {
                Log.w(TAG, "registerListener: Listener is null");
                return;
            }
            if (this.mDesktopModeListeners == null) {
                this.mDesktopModeListeners = new ArrayMap();
            }
            if (this.mDesktopModeListeners.containsKey(listener)) {
                Log.w(TAG, "registerListener: " + listener + " already registered");
                return;
            }
            DesktopModeListenerDelegate delegate = new DesktopModeListenerDelegate(listener);
            try {
                this.mService.registerDesktopModeListener(delegate, listener.toString());
                this.mDesktopModeListeners.put(listener, delegate);
                Log.i(TAG, "registerListener: " + listener);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public static void unregisterListener(EventListener listener) {
        Log.w(
                TAG,
                "unregisterListener(EventListener) is removed.  Please use"
                    + " unregisterListener(DesktopModeListener) instead.");
    }

    public void unregisterListener(DesktopModeListener listener) {
        synchronized (sLock) {
            if (listener == null) {
                Log.w(TAG, "unregisterListener: Listener is null");
                return;
            }
            if (this.mDesktopModeListeners == null) {
                return;
            }
            DesktopModeListenerDelegate delegate = this.mDesktopModeListeners.remove(listener);
            if (delegate == null) {
                Log.w(TAG, "unregisterListener: " + listener + " already unregistered");
                return;
            }
            if (this.mDesktopModeListeners.isEmpty()) {
                this.mDesktopModeListeners = null;
            }
            try {
                this.mService.unregisterDesktopModeListener(delegate);
                Log.i(TAG, "unregisterListener: " + listener);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            delegate.nullOutListenerLocked();
        }
    }

    @Deprecated
    public static boolean isDesktopDockConnected() {
        Log.w(
                TAG,
                "isDesktopDockConnected() is removed. Please use Intent.ACTION_DOCK_EVENT sticky"
                    + " broadcast instead");
        return false;
    }

    @Deprecated
    public static boolean isDesktopMode() {
        Log.w(
                TAG,
                "isDesktopMode() is removed. Please check Configuration.semDesktopModeEnabled"
                    + " instead, or consider using getDesktopModeState() instead.");
        return false;
    }

    public SemDesktopModeState getDesktopModeState() {
        try {
            return this.mService.getDesktopModeState();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }

    public boolean isDeviceConnected() {
        try {
            return this.mService.isDeviceConnected();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean isAllowed() {
        try {
            return this.mService.isAllowed();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public void forceSetHdmiSettings(boolean desktopMode) {
        try {
            this.mService.scheduleUpdateDesktopMode(desktopMode);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public Bundle sendMessage(Bundle message) {
        try {
            return this.mService.sendMessage(message);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }

    private static class IDesktopModeLauncherImpl extends IDesktopModeLauncher.Stub {
        OnMessageReceivedListener mLauncher;

        IDesktopModeLauncherImpl(OnMessageReceivedListener listener) {
            this.mLauncher = listener;
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeLauncher
        public Bundle sendMessage(Bundle message) throws RemoteException {
            if (this.mLauncher != null) {
                return this.mLauncher.onMessageReceived(message);
            }
            return null;
        }
    }

    public interface OnMessageReceivedListener {
        default Bundle onMessageReceived(Bundle message) {
            return null;
        }
    }

    public boolean setOnMessageReceivedListener(OnMessageReceivedListener listener) {
        try {
            if (listener == null) {
                this.mService.registerDesktopLauncher(null);
                return true;
            }
            IDesktopModeLauncherImpl iDesktopLauncher = new IDesktopModeLauncherImpl(listener);
            this.mService.registerDesktopLauncher(iDesktopLauncher);
            return true;
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public void registerBlocker(DesktopModeBlocker blocker) {
        synchronized (sLock) {
            if (Binder.getCallingUid() != 1000) {
                throw new SecurityException("Only the system may call registerBlocker()");
            }
            if (blocker == null) {
                Log.w(TAG, "registerBlocker: DesktopModeBlocker is null");
                return;
            }
            if (this.mBlockers == null) {
                this.mBlockers = new ArrayMap();
            }
            if (this.mBlockers.containsKey(blocker)) {
                Log.w(TAG, "registerBlocker: " + blocker + " already registered");
                return;
            }
            DesktopModeBlockerDelegate delegate = new DesktopModeBlockerDelegate(blocker);
            try {
                this.mService.registerBlocker(delegate, blocker.toString());
                this.mBlockers.put(blocker, delegate);
                Log.i(TAG, "registerBlocker: " + blocker);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterBlocker(DesktopModeBlocker blocker) {
        synchronized (sLock) {
            if (Binder.getCallingUid() != 1000) {
                throw new SecurityException("Only the system may call unregisterBlocker()");
            }
            if (blocker == null) {
                Log.w(TAG, "unregisterBlocker: DesktopModeBlocker is null");
                return;
            }
            if (this.mBlockers == null) {
                return;
            }
            DesktopModeBlockerDelegate delegate = this.mBlockers.remove(blocker);
            if (delegate == null) {
                Log.w(TAG, "unregisterBlocker: " + blocker + " already unregistered");
                return;
            }
            if (this.mBlockers.isEmpty()) {
                this.mBlockers = null;
            }
            try {
                this.mService.unregisterBlocker(delegate);
                Log.i(TAG, "unregisterBlocker: " + blocker);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            delegate.nullOutBlockerLocked();
        }
    }

    public void onSecuredAppLaunched(IBinder activityToken, String packageName) {
        try {
            this.mService.onSecuredAppLaunched(activityToken, packageName);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }
}
