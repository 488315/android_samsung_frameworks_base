package android.app.supervision;

import android.annotation.SystemApi;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;

@SystemApi
/* loaded from: classes.dex */
public class SupervisionManager {
    public static final String ACTION_DISABLE_SUPERVISION = "android.app.supervision.action.DISABLE_SUPERVISION";
    public static final String ACTION_ENABLE_SUPERVISION = "android.app.supervision.action.ENABLE_SUPERVISION";
    private final Context mContext;
    private final ISupervisionManager mService;

    public SupervisionManager(Context context, ISupervisionManager iSupervisionManager) {
        this.mContext = context;
        this.mService = iSupervisionManager;
    }

    @SystemApi
    public Intent createConfirmSupervisionCredentialsIntent() {
        ISupervisionManager iSupervisionManager = this.mService;
        if (iSupervisionManager == null) {
            return null;
        }
        try {
            Intent intentCreateConfirmSupervisionCredentialsIntent = iSupervisionManager.createConfirmSupervisionCredentialsIntent();
            if (intentCreateConfirmSupervisionCredentialsIntent != null) {
                intentCreateConfirmSupervisionCredentialsIntent.prepareToEnterProcess(32, this.mContext.getAttributionSource());
            }
            return intentCreateConfirmSupervisionCredentialsIntent;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isSupervisionEnabled() {
        return isSupervisionEnabledForUser(this.mContext.getUserId());
    }

    public boolean isSupervisionEnabledForUser(int i) {
        ISupervisionManager iSupervisionManager = this.mService;
        if (iSupervisionManager == null) {
            return false;
        }
        try {
            return iSupervisionManager.isSupervisionEnabledForUser(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSupervisionEnabled(boolean z) {
        setSupervisionEnabledForUser(this.mContext.getUserId(), z);
    }

    public void setSupervisionEnabledForUser(int i, boolean z) {
        ISupervisionManager iSupervisionManager = this.mService;
        if (iSupervisionManager != null) {
            try {
                iSupervisionManager.setSupervisionEnabledForUser(i, z);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public String getActiveSupervisionAppPackage() {
        ISupervisionManager iSupervisionManager = this.mService;
        if (iSupervisionManager == null) {
            return null;
        }
        try {
            return iSupervisionManager.getActiveSupervisionAppPackage(this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean shouldAllowBypassingSupervisionRoleQualification() {
        ISupervisionManager iSupervisionManager = this.mService;
        if (iSupervisionManager == null) {
            return false;
        }
        try {
            return iSupervisionManager.shouldAllowBypassingSupervisionRoleQualification();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
