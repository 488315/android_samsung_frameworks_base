package com.samsung.android.multicontrol;

import android.content.Context;
import android.os.RemoteException;
import android.view.IInputFilter;

/* loaded from: classes6.dex */
public final class SemMultiControlManager {
    private IMultiControlManager mService;
    public static final String TAG_PREFIX = "MultiControl@";
    private static final String TAG = TAG_PREFIX + SemMultiControlManager.class.getSimpleName();
    private static final Object sLock = new Object();

    public interface InputFilterInstallListener {
        void onInstalled();

        void onUninstalled();
    }

    public interface MultiControlDeathChecker {}

    public int getProtocolVersion() {
        try {
            return this.mService.getProtocolVersion();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return 1;
        }
    }

    public void setProtocolVersion(int version) {
        try {
            this.mService.setProtocolVersion(version);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
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

    public void setTriggerThreshold(int threshold) {
        try {
            this.mService.setTriggerThreshold(threshold);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void enableTriggerDetection(boolean enable) {
        try {
            this.mService.enableTriggerDetection(enable);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void forceHideCursor(boolean hide) {
        try {
            this.mService.forceHideCursor(hide);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setCursorPosition(int x, int y, int displayId) {
        try {
            this.mService.setCursorPosition(x, y, displayId);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setCursorPosition(int x, int y) {
        try {
            this.mService.setCursorPosition(x, y, -1);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setInteractive(boolean interactive) {
        try {
            this.mService.setInteractive(interactive);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setMultiControlOutOfFocus(boolean outOfFocus) {
        try {
            this.mService.setMultiControlOutOfFocus(outOfFocus);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setInputFilter(IInputFilter filter, InputFilterInstallListener listener) {
        try {
            this.mService.setInputFilter(filter, new InputFilterInstallListenerDelegate(listener));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void resetInputFilter() {
        try {
            this.mService.resetInputFilter();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void startDeathChecker() {
        try {
            this.mService.startDeathChecker(
                    new MultiControlDeathCheckerDelegate(
                            new MultiControlDeathChecker() { // from class:
                                                             // com.samsung.android.multicontrol.SemMultiControlManager.1
                            }));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void stopDeathChecker() {
        try {
            this.mService.stopDeathChecker();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public SemMultiControlManager(Context context, IMultiControlManager service) {
        this.mService = service;
    }

    private static class MultiControlDeathCheckerDelegate extends IMultiControlDeathChecker.Stub {
        private MultiControlDeathChecker mListener;

        MultiControlDeathCheckerDelegate(MultiControlDeathChecker listener) {
            this.mListener = listener;
        }

        public String toString() {
            String valueOf;
            synchronized (SemMultiControlManager.sLock) {
                valueOf = String.valueOf(this.mListener);
            }
            return valueOf;
        }

        void nullOutListenerLocked() {
            this.mListener = null;
        }
    }

    private static class InputFilterInstallListenerDelegate
            extends IInputFilterInstallListener.Stub {
        private InputFilterInstallListener mListener;

        InputFilterInstallListenerDelegate(InputFilterInstallListener listener) {
            this.mListener = listener;
        }

        @Override // com.samsung.android.multicontrol.IInputFilterInstallListener
        public void onInstalled() {
            InputFilterInstallListener listener;
            synchronized (SemMultiControlManager.sLock) {
                listener = this.mListener;
            }
            if (listener != null) {
                listener.onInstalled();
            }
        }

        @Override // com.samsung.android.multicontrol.IInputFilterInstallListener
        public void onUninstalled() {
            InputFilterInstallListener listener;
            synchronized (SemMultiControlManager.sLock) {
                listener = this.mListener;
            }
            if (listener != null) {
                listener.onUninstalled();
            }
        }

        public String toString() {
            String valueOf;
            synchronized (SemMultiControlManager.sLock) {
                valueOf = String.valueOf(this.mListener);
            }
            return valueOf;
        }

        void nullOutListenerLocked() {
            this.mListener = null;
        }
    }
}
