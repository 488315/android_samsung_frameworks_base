package com.android.server.biometrics.sensors;

import android.content.pm.UserInfo;
import android.hardware.biometrics.IBiometricStateListener;
import android.hardware.biometrics.SensorPropertiesInternal;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserManager;
import android.util.Slog;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.biometrics.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BiometricStateCallback implements ClientMonitorCallback, IBinder.DeathRecipient {
    public final UserManager mUserManager;
    public final CopyOnWriteArrayList mBiometricStateListeners = new CopyOnWriteArrayList();
    public List mProviders = List.of();
    public int mBiometricState = 0;

    public BiometricStateCallback(UserManager userManager) {
        this.mUserManager = userManager;
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {}

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied(final IBinder iBinder) {
        Slog.w("BiometricStateCallback", "Callback binder died: " + iBinder);
        if (!this.mBiometricStateListeners.removeIf(
                new Predicate() { // from class:
                                  // com.android.server.biometrics.sensors.BiometricStateCallback$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((IBiometricStateListener) obj).asBinder().equals(iBinder);
                    }
                })) {
            Slog.w("BiometricStateCallback", "No dead listeners found");
            return;
        }
        Slog.w("BiometricStateCallback", "Removed dead listener for " + iBinder);
    }

    public final synchronized void broadcastCurrentEnrollmentState(
            IBiometricStateListener iBiometricStateListener) {
        try {
        } catch (Throwable th) {
            throw th;
        }
        for (BiometricServiceProvider biometricServiceProvider : this.mProviders) {
            Iterator it = ((ArrayList) biometricServiceProvider.getSensorProperties()).iterator();
            while (it.hasNext()) {
                SensorPropertiesInternal sensorPropertiesInternal =
                        (SensorPropertiesInternal) it.next();
                for (UserInfo userInfo : this.mUserManager.getAliveUsers()) {
                    boolean hasEnrollments =
                            biometricServiceProvider.hasEnrollments(
                                    sensorPropertiesInternal.sensorId, userInfo.id);
                    if (iBiometricStateListener != null) {
                        try {
                            iBiometricStateListener.onEnrollmentsChanged(
                                    userInfo.id, sensorPropertiesInternal.sensorId, hasEnrollments);
                        } catch (RemoteException e) {
                            Slog.e("BiometricStateCallback", "Remote exception", e);
                        }
                    } else {
                        int i = userInfo.id;
                        int i2 = sensorPropertiesInternal.sensorId;
                        Iterator it2 = this.mBiometricStateListeners.iterator();
                        while (it2.hasNext()) {
                            try {
                                ((IBiometricStateListener) it2.next())
                                        .onEnrollmentsChanged(i, i2, hasEnrollments);
                            } catch (RemoteException e2) {
                                Slog.e("BiometricStateCallback", "Remote exception", e2);
                            }
                        }
                    }
                    throw th;
                }
            }
        }
    }

    public final void notifyBiometricStateListeners(int i) {
        Iterator it = this.mBiometricStateListeners.iterator();
        while (it.hasNext()) {
            try {
                ((IBiometricStateListener) it.next()).onStateChanged(i);
            } catch (RemoteException e) {
                Slog.e("BiometricStateCallback", "Remote exception in biometric state change", e);
            }
        }
    }

    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
    public final void onBiometricAction() {
        Iterator it = this.mBiometricStateListeners.iterator();
        while (it.hasNext()) {
            try {
                ((IBiometricStateListener) it.next()).onBiometricAction(0);
            } catch (RemoteException e) {
                Slog.e("BiometricStateCallback", "Remote exception in onBiometricAction", e);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
    public final void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
        this.mBiometricState = 0;
        Slog.d(
                "BiometricStateCallback",
                "Client finished, state updated to "
                        + this.mBiometricState
                        + ", client "
                        + baseClientMonitor);
        if (baseClientMonitor instanceof EnrollmentModifier) {
            EnrollmentModifier enrollmentModifier = (EnrollmentModifier) baseClientMonitor;
            boolean hasEnrollmentStateChanged = enrollmentModifier.hasEnrollmentStateChanged();
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    "Enrollment state changed: ",
                    "BiometricStateCallback",
                    hasEnrollmentStateChanged);
            if (hasEnrollmentStateChanged) {
                int i = baseClientMonitor.mTargetUserId;
                int i2 = baseClientMonitor.mSensorId;
                boolean hasEnrollments = enrollmentModifier.hasEnrollments();
                Iterator it = this.mBiometricStateListeners.iterator();
                while (it.hasNext()) {
                    try {
                        ((IBiometricStateListener) it.next())
                                .onEnrollmentsChanged(i, i2, hasEnrollments);
                    } catch (RemoteException e) {
                        Slog.e("BiometricStateCallback", "Remote exception", e);
                    }
                }
            }
        }
        notifyBiometricStateListeners(this.mBiometricState);
    }

    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
    public final void onClientStarted(BaseClientMonitor baseClientMonitor) {
        int i = this.mBiometricState;
        if (baseClientMonitor instanceof AuthenticationClient) {
            AuthenticationClient authenticationClient = (AuthenticationClient) baseClientMonitor;
            if (authenticationClient.isKeyguard()) {
                this.mBiometricState = 2;
            } else if (authenticationClient.isBiometricPrompt()) {
                this.mBiometricState = 3;
            } else {
                this.mBiometricState = 4;
            }
        } else if (baseClientMonitor instanceof EnrollClient) {
            this.mBiometricState = 1;
        } else {
            Slog.w(
                    "BiometricStateCallback",
                    "Other authentication client: ".concat(Utils.getClientName(baseClientMonitor)));
            this.mBiometricState = 0;
        }
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m(i, "State updated from ", " to ");
        m.append(this.mBiometricState);
        m.append(", client ");
        m.append(baseClientMonitor);
        Slog.d("BiometricStateCallback", m.toString());
        notifyBiometricStateListeners(this.mBiometricState);
    }

    public final synchronized void registerBiometricStateListener(
            IBiometricStateListener iBiometricStateListener) {
        this.mBiometricStateListeners.add(iBiometricStateListener);
        broadcastCurrentEnrollmentState(iBiometricStateListener);
        try {
            iBiometricStateListener.asBinder().linkToDeath(this, 0);
        } catch (RemoteException e) {
            Slog.e("BiometricStateCallback", "Failed to link to death", e);
        }
    }
}
