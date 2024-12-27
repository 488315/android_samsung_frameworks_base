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
        Settings.Secure.putIntForUser(this.mContext.getContentResolver(), Settings.Secure.SCREENSAVER_ENABLED, z ? 1 : 0, -2);
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

    public void setActiveDream(ComponentName dreamComponent) {
        ComponentName[] dreams = {dreamComponent};
        try {
            this.mService.setDreamComponentsForUser(this.mContext.getUserId(), dreamComponent != null ? dreams : null);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setSystemDreamComponent(ComponentName dreamComponent) {
        try {
            this.mService.setSystemDreamComponent(dreamComponent);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setDreamOverlay(ComponentName dreamOverlayComponent) {
        try {
            this.mService.registerDreamOverlayService(dreamOverlayComponent);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public boolean canStartDreaming(boolean isScreenOn) {
        try {
            return this.mService.canStartDreaming(isScreenOn);
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

    public void setDreamIsObscured(boolean isObscured) {
        try {
            this.mService.setDreamIsObscured(isObscured);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
