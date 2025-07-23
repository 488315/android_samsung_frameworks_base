package com.samsung.android.desktopmode;

import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Log;
import com.samsung.android.desktopmode.IDesktopModeBlocker;
import com.samsung.android.desktopmode.IDesktopModeLauncher;
import com.samsung.android.desktopmode.IDesktopModeListener;
import java.util.Map;

/* loaded from: classes6.dex */
public final class SemDesktopModeManager {
    public static final String LAUNCHER_PACKAGE = "com.sec.android.app.desktoplauncher";
    private static final String TAG = "SemDesktopModeManager";
    public static final String UI_SERVICE_PACKAGE = "com.sec.android.desktopmode.uiservice";
    private static final Object sLock = new Object();
    private IDesktopMode mService;
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

    public interface OnMessageReceivedListener {
        default Bundle onMessageReceived(Bundle bundle) {
            return null;
        }
    }

    private static class DesktopModeListenerDelegate extends IDesktopModeListener.Stub {
        private DesktopModeListener mListener;

        DesktopModeListenerDelegate(DesktopModeListener desktopModeListener) {
            this.mListener = desktopModeListener;
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeListener
        public void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) throws RemoteException {
            DesktopModeListener desktopModeListener;
            synchronized (SemDesktopModeManager.sLock) {
                desktopModeListener = this.mListener;
            }
            if (desktopModeListener != null) {
                Log.d(SemDesktopModeManager.TAG, "onDesktopModeStateChanged() state=" + semDesktopModeState + ", listener=" + desktopModeListener);
                desktopModeListener.onDesktopModeStateChanged(semDesktopModeState);
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

        DesktopModeBlockerDelegate(DesktopModeBlocker desktopModeBlocker) {
            this.mBlocker = desktopModeBlocker;
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeBlocker
        public String onBlocked() {
            DesktopModeBlocker desktopModeBlocker;
            synchronized (SemDesktopModeManager.sLock) {
                desktopModeBlocker = this.mBlocker;
            }
            if (desktopModeBlocker != null) {
                return desktopModeBlocker.onBlocked();
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

    public SemDesktopModeManager(Context context, IDesktopMode iDesktopMode) {
        this.mService = iDesktopMode;
    }

    @Deprecated
    public static void registerListener(EventListener eventListener) {
        Log.w(TAG, "registerListener(EventListener) is removed.  Please use registerListener(DesktopModeListener) instead.");
    }

    public void registerListener(DesktopModeListener desktopModeListener) {
        synchronized (sLock) {
            if (desktopModeListener == null) {
                Log.w(TAG, "registerListener: Listener is null");
                return;
            }
            if (this.mDesktopModeListeners == null) {
                this.mDesktopModeListeners = new ArrayMap();
            }
            if (this.mDesktopModeListeners.containsKey(desktopModeListener)) {
                Log.w(TAG, "registerListener: " + desktopModeListener + " already registered");
                return;
            }
            DesktopModeListenerDelegate desktopModeListenerDelegate = new DesktopModeListenerDelegate(desktopModeListener);
            try {
                this.mService.registerDesktopModeListener(desktopModeListenerDelegate, desktopModeListener.toString());
                this.mDesktopModeListeners.put(desktopModeListener, desktopModeListenerDelegate);
                Log.i(TAG, "registerListener: " + desktopModeListener);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public static void unregisterListener(EventListener eventListener) {
        Log.w(TAG, "unregisterListener(EventListener) is removed.  Please use unregisterListener(DesktopModeListener) instead.");
    }

    public void unregisterListener(DesktopModeListener desktopModeListener) {
        synchronized (sLock) {
            if (desktopModeListener == null) {
                Log.w(TAG, "unregisterListener: Listener is null");
                return;
            }
            Map<DesktopModeListener, DesktopModeListenerDelegate> map = this.mDesktopModeListeners;
            if (map == null) {
                return;
            }
            DesktopModeListenerDelegate remove = map.remove(desktopModeListener);
            if (remove == null) {
                Log.w(TAG, "unregisterListener: " + desktopModeListener + " already unregistered");
                return;
            }
            if (this.mDesktopModeListeners.isEmpty()) {
                this.mDesktopModeListeners = null;
            }
            try {
                this.mService.unregisterDesktopModeListener(remove);
                Log.i(TAG, "unregisterListener: " + desktopModeListener);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            remove.nullOutListenerLocked();
        }
    }

    @Deprecated
    public static boolean isDesktopDockConnected() {
        Log.w(TAG, "isDesktopDockConnected() is removed. Please use Intent.ACTION_DOCK_EVENT sticky broadcast instead");
        return false;
    }

    @Deprecated
    public static boolean isDesktopMode() {
        Log.w(TAG, "isDesktopMode() is removed. Please check Configuration.semDesktopModeEnabled instead, or consider using getDesktopModeState() instead.");
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

    public void forceSetHdmiSettings(boolean z) {
        try {
            this.mService.scheduleUpdateDesktopMode(z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public Bundle sendMessage(Bundle bundle) {
        try {
            return this.mService.sendMessage(bundle);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }

    private static class IDesktopModeLauncherImpl extends IDesktopModeLauncher.Stub {
        OnMessageReceivedListener mLauncher;

        IDesktopModeLauncherImpl(OnMessageReceivedListener onMessageReceivedListener) {
            this.mLauncher = onMessageReceivedListener;
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeLauncher
        public Bundle sendMessage(Bundle bundle) throws RemoteException {
            OnMessageReceivedListener onMessageReceivedListener = this.mLauncher;
            if (onMessageReceivedListener != null) {
                return onMessageReceivedListener.onMessageReceived(bundle);
            }
            return null;
        }
    }

    public boolean setOnMessageReceivedListener(OnMessageReceivedListener onMessageReceivedListener) {
        try {
            if (onMessageReceivedListener == null) {
                this.mService.registerDesktopLauncher(null);
                return true;
            }
            this.mService.registerDesktopLauncher(new IDesktopModeLauncherImpl(onMessageReceivedListener));
            return true;
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public void registerBlocker(DesktopModeBlocker desktopModeBlocker) {
        synchronized (sLock) {
            if (Binder.getCallingUid() != 1000) {
                throw new SecurityException("Only the system may call registerBlocker()");
            }
            if (desktopModeBlocker == null) {
                Log.w(TAG, "registerBlocker: DesktopModeBlocker is null");
                return;
            }
            if (this.mBlockers == null) {
                this.mBlockers = new ArrayMap();
            }
            if (this.mBlockers.containsKey(desktopModeBlocker)) {
                Log.w(TAG, "registerBlocker: " + desktopModeBlocker + " already registered");
                return;
            }
            DesktopModeBlockerDelegate desktopModeBlockerDelegate = new DesktopModeBlockerDelegate(desktopModeBlocker);
            try {
                this.mService.registerBlocker(desktopModeBlockerDelegate, desktopModeBlocker.toString());
                this.mBlockers.put(desktopModeBlocker, desktopModeBlockerDelegate);
                Log.i(TAG, "registerBlocker: " + desktopModeBlocker);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterBlocker(DesktopModeBlocker desktopModeBlocker) {
        synchronized (sLock) {
            if (Binder.getCallingUid() != 1000) {
                throw new SecurityException("Only the system may call unregisterBlocker()");
            }
            if (desktopModeBlocker == null) {
                Log.w(TAG, "unregisterBlocker: DesktopModeBlocker is null");
                return;
            }
            Map<DesktopModeBlocker, DesktopModeBlockerDelegate> map = this.mBlockers;
            if (map == null) {
                return;
            }
            DesktopModeBlockerDelegate remove = map.remove(desktopModeBlocker);
            if (remove == null) {
                Log.w(TAG, "unregisterBlocker: " + desktopModeBlocker + " already unregistered");
                return;
            }
            if (this.mBlockers.isEmpty()) {
                this.mBlockers = null;
            }
            try {
                this.mService.unregisterBlocker(remove);
                Log.i(TAG, "unregisterBlocker: " + desktopModeBlocker);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            remove.nullOutBlockerLocked();
        }
    }

    public void onSecuredAppLaunched(IBinder iBinder, String str) {
        try {
            this.mService.onSecuredAppLaunched(iBinder, str);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }
}
