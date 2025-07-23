package com.samsung.android.multicontrol;

import android.content.Context;
import android.os.RemoteException;
import android.view.IInputFilter;
import com.samsung.android.multicontrol.IInputFilterInstallListener;
import com.samsung.android.multicontrol.IMultiControlDeathChecker;

/* loaded from: classes6.dex */
public final class SemMultiControlManager {
    private static final String TAG = "MultiControl@SemMultiControlManager";
    public static final String TAG_PREFIX = "MultiControl@";
    private static final Object sLock = new Object();
    private IMultiControlManager mService;

    public interface InputFilterInstallListener {
        void onInstalled();

        void onUninstalled();
    }

    public interface MultiControlDeathChecker {
    }

    public int getProtocolVersion() {
        try {
            return this.mService.getProtocolVersion();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return 1;
        }
    }

    public void setProtocolVersion(int i) {
        try {
            this.mService.setProtocolVersion(i);
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

    public void setTriggerThreshold(int i) {
        try {
            this.mService.setTriggerThreshold(i);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void enableTriggerDetection(boolean z) {
        try {
            this.mService.enableTriggerDetection(z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void forceHideCursor(boolean z) {
        try {
            this.mService.forceHideCursor(z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setCursorPosition(int i, int i2, int i3) {
        try {
            this.mService.setCursorPosition(i, i2, i3);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setCursorPosition(int i, int i2) {
        try {
            this.mService.setCursorPosition(i, i2, -1);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setInteractive(boolean z) {
        try {
            this.mService.setInteractive(z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setMultiControlOutOfFocus(boolean z) {
        try {
            this.mService.setMultiControlOutOfFocus(z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setInputFilter(IInputFilter iInputFilter, InputFilterInstallListener inputFilterInstallListener) {
        try {
            this.mService.setInputFilter(iInputFilter, new InputFilterInstallListenerDelegate(inputFilterInstallListener));
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
            this.mService.startDeathChecker(new MultiControlDeathCheckerDelegate(new MultiControlDeathChecker(this) { // from class: com.samsung.android.multicontrol.SemMultiControlManager.1
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

    public void observeDesktopMode(boolean z) {
        try {
            this.mService.observeDesktopMode(z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public SemMultiControlManager(Context context, IMultiControlManager iMultiControlManager) {
        this.mService = iMultiControlManager;
    }

    private static class MultiControlDeathCheckerDelegate extends IMultiControlDeathChecker.Stub {
        private MultiControlDeathChecker mListener;

        MultiControlDeathCheckerDelegate(MultiControlDeathChecker multiControlDeathChecker) {
            this.mListener = multiControlDeathChecker;
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

    private static class InputFilterInstallListenerDelegate extends IInputFilterInstallListener.Stub {
        private InputFilterInstallListener mListener;

        InputFilterInstallListenerDelegate(InputFilterInstallListener inputFilterInstallListener) {
            this.mListener = inputFilterInstallListener;
        }

        @Override // com.samsung.android.multicontrol.IInputFilterInstallListener
        public void onInstalled() {
            InputFilterInstallListener inputFilterInstallListener;
            synchronized (SemMultiControlManager.sLock) {
                inputFilterInstallListener = this.mListener;
            }
            if (inputFilterInstallListener != null) {
                inputFilterInstallListener.onInstalled();
            }
        }

        @Override // com.samsung.android.multicontrol.IInputFilterInstallListener
        public void onUninstalled() {
            InputFilterInstallListener inputFilterInstallListener;
            synchronized (SemMultiControlManager.sLock) {
                inputFilterInstallListener = this.mListener;
            }
            if (inputFilterInstallListener != null) {
                inputFilterInstallListener.onUninstalled();
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
