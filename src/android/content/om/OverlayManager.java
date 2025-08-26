package android.content.om;

import android.annotation.SystemApi;
import android.compat.Compatibility;
import android.content.Context;
import android.content.om.IOverlayManager;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import com.android.internal.content.om.OverlayManagerImpl;
import java.io.IOException;
import java.util.List;

/* loaded from: classes.dex */
public class OverlayManager {
    public static final long SELF_TARGETING_OVERLAY = 205919743;
    private static final long THROW_SECURITY_EXCEPTIONS = 147340954;
    private final OverlayManagerImpl mOverlayManagerImpl;
    private final IOverlayManager mService;

    public OverlayManager(Context context, IOverlayManager iOverlayManager) {
        this.mService = iOverlayManager;
        this.mOverlayManagerImpl = new OverlayManagerImpl(context);
    }

    public OverlayManager(Context context) {
        this(context, IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay")));
    }

    @SystemApi
    public void setEnabledExclusiveInCategory(String str, UserHandle userHandle) throws IllegalStateException, SecurityException {
        try {
            if (this.mService.setEnabledExclusiveInCategory(str, userHandle.getIdentifier())) {
            } else {
                throw new IllegalStateException("setEnabledExclusiveInCategory failed");
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (SecurityException e2) {
            rethrowSecurityException(e2);
        }
    }

    @SystemApi
    public void setEnabled(String str, boolean z, UserHandle userHandle) throws IllegalStateException, SecurityException {
        try {
            if (this.mService.setEnabled(str, z, userHandle.getIdentifier())) {
            } else {
                throw new IllegalStateException("setEnabled failed");
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (SecurityException e2) {
            rethrowSecurityException(e2);
        }
    }

    public void enableWithConstraints(String str, UserHandle userHandle, List<OverlayConstraint> list) throws IllegalStateException, SecurityException {
        try {
            if (this.mService.enableWithConstraints(str, userHandle.getIdentifier(), list)) {
            } else {
                throw new IllegalStateException("enableWithConstraints failed");
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (SecurityException e2) {
            rethrowSecurityException(e2);
        }
    }

    @SystemApi
    public OverlayInfo getOverlayInfo(String str, UserHandle userHandle) {
        try {
            return this.mService.getOverlayInfo(str, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public OverlayInfo getOverlayInfo(OverlayIdentifier overlayIdentifier, UserHandle userHandle) {
        try {
            return this.mService.getOverlayInfoByIdentifier(overlayIdentifier, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<OverlayInfo> getOverlayInfosForTarget(String str, UserHandle userHandle) {
        try {
            return this.mService.getOverlayInfosForTarget(str, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void invalidateCachesForOverlay(String str, UserHandle userHandle) {
        try {
            this.mService.invalidateCachesForOverlay(str, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void commitToSystemServer(OverlayManagerTransaction overlayManagerTransaction) {
        try {
            this.mService.commit(overlayManagerTransaction);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void commit(OverlayManagerTransaction overlayManagerTransaction) {
        IOverlayManager iOverlayManager;
        if (overlayManagerTransaction.isSelfTargeting() || (iOverlayManager = this.mService) == null || iOverlayManager.asBinder() == null) {
            try {
                commitSelfTarget(overlayManagerTransaction);
                return;
            } catch (PackageManager.NameNotFoundException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        commitToSystemServer(overlayManagerTransaction);
    }

    private void rethrowSecurityException(SecurityException securityException) {
        if (!Compatibility.isChangeEnabled(THROW_SECURITY_EXCEPTIONS)) {
            throw new IllegalStateException(securityException);
        }
        throw securityException;
    }

    void commitSelfTarget(OverlayManagerTransaction overlayManagerTransaction) throws PackageManager.NameNotFoundException, IOException {
        synchronized (this.mOverlayManagerImpl) {
            this.mOverlayManagerImpl.commit(overlayManagerTransaction);
        }
    }

    public List<OverlayInfo> getOverlayInfosForTarget(String str) {
        List<OverlayInfo> overlayInfosForTarget;
        synchronized (this.mOverlayManagerImpl) {
            overlayInfosForTarget = this.mOverlayManagerImpl.getOverlayInfosForTarget(str);
        }
        return overlayInfosForTarget;
    }
}
