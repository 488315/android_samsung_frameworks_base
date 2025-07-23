package android.app;

import android.content.ComponentName;
import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.service.dreams.DreamService;
import android.service.dreams.IDreamManager;
import com.android.internal.R;

/* loaded from: classes.dex */
public class DreamManager {
    private final Context mContext;
    private final IDreamManager mService = IDreamManager.Stub.asInterface(ServiceManager.getServiceOrThrow(DreamService.DREAM_SERVICE));

    public DreamManager(Context context) throws ServiceManager.ServiceNotFoundException {
        this.mContext = context;
    }

    public boolean isScreensaverEnabled() {
        return Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.SCREENSAVER_ENABLED, 0, -2) != 0;
    }

    public void setScreensaverEnabled(boolean z) {
        try {
            this.mService.setScreensaverEnabled(z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public boolean areDreamsSupported() {
        return this.mContext.getResources().getBoolean(R.bool.config_dreamsSupported);
    }

    public void startDream() {
        try {
            this.mService.dream();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void stopDream() {
        try {
            this.mService.awaken();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setActiveDream(ComponentName componentName) {
        ComponentName[] componentNameArr = {componentName};
        try {
            IDreamManager iDreamManager = this.mService;
            int userId = this.mContext.getUserId();
            if (componentName == null) {
                componentNameArr = null;
            }
            iDreamManager.setDreamComponentsForUser(userId, componentNameArr);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setSystemDreamComponent(ComponentName componentName) {
        try {
            this.mService.setSystemDreamComponent(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setDreamOverlay(ComponentName componentName) {
        try {
            this.mService.registerDreamOverlayService(componentName);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public boolean canStartDreaming(boolean z) {
        try {
            return this.mService.canStartDreaming(z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean isDreaming() {
        try {
            return this.mService.isDreaming();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public void setDreamIsObscured(boolean z) {
        try {
            this.mService.setDreamIsObscured(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setDevicePostured(boolean z) {
        try {
            this.mService.setDevicePostured(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
