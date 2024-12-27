package com.android.server.ambientcontext;

import android.app.AppGlobals;
import android.app.ambientcontext.AmbientContextEventRequest;
import android.app.ambientcontext.IAmbientContextObserver;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.util.IndentingPrintWriter;
import android.util.Slog;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.infra.AbstractPerUserSystemService;

import java.io.PrintWriter;
import java.util.Arrays;

public abstract class AmbientContextManagerPerUserService extends AbstractPerUserSystemService {

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    final class ServiceType {
        public static final /* synthetic */ ServiceType[] $VALUES;
        public static final ServiceType DEFAULT;
        public static final ServiceType WEARABLE;

        static {
            ServiceType serviceType = new ServiceType("DEFAULT", 0);
            DEFAULT = serviceType;
            ServiceType serviceType2 = new ServiceType("WEARABLE", 1);
            WEARABLE = serviceType2;
            $VALUES = new ServiceType[] {serviceType, serviceType2};
        }

        public static ServiceType valueOf(String str) {
            return (ServiceType) Enum.valueOf(ServiceType.class, str);
        }

        public static ServiceType[] values() {
            return (ServiceType[]) $VALUES.clone();
        }
    }

    public static void completeRegistration(
            IAmbientContextObserver iAmbientContextObserver, int i) {
        try {
            iAmbientContextObserver.onRegistrationComplete(i);
        } catch (RemoteException e) {
            Slog.w(
                    "AmbientContextManagerPerUserService",
                    "Failed to call IAmbientContextObserver.onRegistrationComplete: "
                            + e.getMessage());
        }
    }

    public static void sendStatusCallback(int i, RemoteCallback remoteCallback) {
        Bundle bundle = new Bundle();
        bundle.putInt("android.app.ambientcontext.AmbientContextStatusBundleKey", i);
        remoteCallback.sendResult(bundle);
    }

    private boolean setUpServiceIfNeeded() {
        if (getComponentName() == null) {
            ComponentName[] updateServiceInfoListLocked = updateServiceInfoListLocked();
            if (updateServiceInfoListLocked == null || updateServiceInfoListLocked.length != 2) {
                Slog.d(
                        "AmbientContextManagerPerUserService",
                        "updateServiceInfoListLocked returned incorrect componentNames");
                return false;
            }
            int ordinal = getServiceType().ordinal();
            if (ordinal == 0) {
                setComponentName(updateServiceInfoListLocked[0]);
            } else {
                if (ordinal != 1) {
                    Slog.d(
                            "AmbientContextManagerPerUserService",
                            "updateServiceInfoListLocked returned unknown service types.");
                    return false;
                }
                setComponentName(updateServiceInfoListLocked[1]);
            }
        }
        if (getComponentName() == null) {
            return false;
        }
        try {
            return AppGlobals.getPackageManager()
                            .getServiceInfo(getComponentName(), 0L, this.mUserId)
                    != null;
        } catch (RemoteException unused) {
            Slog.w(
                    "AmbientContextManagerPerUserService",
                    "RemoteException while setting up service");
            return false;
        }
    }

    public abstract void clearRemoteService();

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public final void dumpLocked(PrintWriter printWriter) {
        synchronized (this.mLock) {
            super.dumpLocked(printWriter);
        }
        RemoteAmbientDetectionService remoteService = getRemoteService();
        if (remoteService != null) {
            remoteService.dump("", new IndentingPrintWriter(printWriter, "  "));
        }
    }

    public abstract void ensureRemoteServiceInitiated();

    public abstract int getAmbientContextEventArrayExtraKeyConfig();

    public abstract int getAmbientContextPackageNameExtraKeyConfig();

    public abstract ComponentName getComponentName();

    public abstract int getConsentComponentConfig();

    public abstract String getProtectedBindPermission();

    public abstract RemoteAmbientDetectionService getRemoteService();

    public abstract ServiceType getServiceType();

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public final ServiceInfo newServiceInfoLocked(ComponentName componentName) {
        Slog.d(
                "AmbientContextManagerPerUserService",
                "newServiceInfoLocked with component name: " + componentName.getClassName());
        if (getComponentName() == null
                || !componentName.getClassName().equals(getComponentName().getClassName())) {
            Slog.d(
                    "AmbientContextManagerPerUserService",
                    "service name does not match this per user, returning...");
            return null;
        }
        try {
            ServiceInfo serviceInfo =
                    AppGlobals.getPackageManager().getServiceInfo(componentName, 0L, this.mUserId);
            if (serviceInfo != null) {
                if (!getProtectedBindPermission().equals(serviceInfo.permission)) {
                    throw new SecurityException(
                            "Service "
                                    + serviceInfo.getComponentName()
                                    + " requires "
                                    + getProtectedBindPermission()
                                    + " permission. Found "
                                    + serviceInfo.permission
                                    + " permission");
                }
            }
            return serviceInfo;
        } catch (RemoteException unused) {
            throw new PackageManager.NameNotFoundException(
                    AmbientContextManagerPerUserService$$ExternalSyntheticOutline0.m(
                            componentName, "Could not get service for "));
        }
    }

    public final void onQueryServiceStatus(int[] iArr, String str, RemoteCallback remoteCallback) {
        Slog.d(
                "AmbientContextManagerPerUserService",
                "Query event status of " + Arrays.toString(iArr) + " for " + str);
        synchronized (this.mLock) {
            try {
                if (!setUpServiceIfNeeded()) {
                    Slog.w(
                            "AmbientContextManagerPerUserService",
                            "Detection service is not available at this moment.");
                    sendStatusCallback(3, remoteCallback);
                } else {
                    ensureRemoteServiceInitiated();
                    getRemoteService()
                            .queryServiceStatus(
                                    iArr,
                                    str,
                                    new RemoteCallback(
                                            new AmbientContextManagerPerUserService$$ExternalSyntheticLambda3(
                                                    1,
                                                    new AmbientContextManagerPerUserService$$ExternalSyntheticLambda1(
                                                            this, remoteCallback, 0))));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onRegisterObserver(
            AmbientContextEventRequest ambientContextEventRequest,
            String str,
            IAmbientContextObserver iAmbientContextObserver) {
        synchronized (this.mLock) {
            try {
                if (setUpServiceIfNeeded()) {
                    startDetection(ambientContextEventRequest, str, iAmbientContextObserver);
                    ((AmbientContextManagerService) this.mMaster)
                            .newClientAdded(
                                    this.mUserId,
                                    ambientContextEventRequest,
                                    str,
                                    iAmbientContextObserver);
                } else {
                    Slog.w(
                            "AmbientContextManagerPerUserService",
                            "Detection service is not available at this moment.");
                    completeRegistration(iAmbientContextObserver, 3);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public abstract void setComponentName(ComponentName componentName);

    public final void startDetection(
            AmbientContextEventRequest ambientContextEventRequest,
            String str,
            IAmbientContextObserver iAmbientContextObserver) {
        Slog.d(
                "AmbientContextManagerPerUserService",
                "Requested detection of " + ambientContextEventRequest.getEventTypes());
        synchronized (this.mLock) {
            try {
                if (setUpServiceIfNeeded()) {
                    ensureRemoteServiceInitiated();
                    getRemoteService()
                            .startDetection(
                                    ambientContextEventRequest,
                                    str,
                                    new RemoteCallback(
                                            new AmbientContextManagerPerUserService$$ExternalSyntheticLambda3(
                                                    0, this)),
                                    new RemoteCallback(
                                            new AmbientContextManagerPerUserService$$ExternalSyntheticLambda3(
                                                    1,
                                                    new AmbientContextManagerPerUserService$$ExternalSyntheticLambda1(
                                                            this, iAmbientContextObserver, 1))));
                } else {
                    Slog.w(
                            "AmbientContextManagerPerUserService",
                            "No valid component found for AmbientContextDetectionService");
                    completeRegistration(iAmbientContextObserver, 2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void stopDetection(String str) {
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                "Stop detection for ", str, "AmbientContextManagerPerUserService");
        synchronized (this.mLock) {
            try {
                if (getComponentName() != null) {
                    ensureRemoteServiceInitiated();
                    getRemoteService().stopDetection(str);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
