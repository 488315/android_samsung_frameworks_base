package com.android.server.biometrics.sensors;

import android.hardware.biometrics.AuthenticationStateListener;
import android.hardware.biometrics.events.AuthenticationAcquiredInfo;
import android.hardware.biometrics.events.AuthenticationErrorInfo;
import android.hardware.biometrics.events.AuthenticationFailedInfo;
import android.hardware.biometrics.events.AuthenticationHelpInfo;
import android.hardware.biometrics.events.AuthenticationStartedInfo;
import android.hardware.biometrics.events.AuthenticationStoppedInfo;
import android.hardware.biometrics.events.AuthenticationSucceededInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

public final class AuthenticationStateListeners implements IBinder.DeathRecipient {
    public final CopyOnWriteArrayList mAuthenticationStateListeners = new CopyOnWriteArrayList();

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {}

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied(final IBinder iBinder) {
        Slog.w("AuthenticationStateListeners", "Callback binder died: " + iBinder);
        if (!this.mAuthenticationStateListeners.removeIf(
                new Predicate() { // from class:
                                  // com.android.server.biometrics.sensors.AuthenticationStateListeners$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((AuthenticationStateListener) obj).asBinder().equals(iBinder);
                    }
                })) {
            Slog.w("AuthenticationStateListeners", "No dead listeners found");
            return;
        }
        Slog.w("AuthenticationStateListeners", "Removed dead listener for " + iBinder);
    }

    public final void onAuthenticationAcquired(
            AuthenticationAcquiredInfo authenticationAcquiredInfo) {
        Iterator it = this.mAuthenticationStateListeners.iterator();
        while (it.hasNext()) {
            try {
                ((AuthenticationStateListener) it.next())
                        .onAuthenticationAcquired(authenticationAcquiredInfo);
            } catch (RemoteException e) {
                Slog.e(
                        "AuthenticationStateListeners",
                        "Remote exception in notifying listener that authentication acquired",
                        e);
            }
        }
    }

    public final void onAuthenticationError(AuthenticationErrorInfo authenticationErrorInfo) {
        Iterator it = this.mAuthenticationStateListeners.iterator();
        while (it.hasNext()) {
            try {
                ((AuthenticationStateListener) it.next())
                        .onAuthenticationError(authenticationErrorInfo);
            } catch (RemoteException e) {
                Slog.e(
                        "AuthenticationStateListeners",
                        "Remote exception in notifying listener of unrecoverable authentication"
                            + " error",
                        e);
            }
        }
    }

    public final void onAuthenticationFailed(AuthenticationFailedInfo authenticationFailedInfo) {
        Iterator it = this.mAuthenticationStateListeners.iterator();
        while (it.hasNext()) {
            try {
                ((AuthenticationStateListener) it.next())
                        .onAuthenticationFailed(authenticationFailedInfo);
            } catch (RemoteException e) {
                Slog.e(
                        "AuthenticationStateListeners",
                        "Remote exception in notifying listener that authentication failed",
                        e);
            }
        }
    }

    public final void onAuthenticationHelp(AuthenticationHelpInfo authenticationHelpInfo) {
        Iterator it = this.mAuthenticationStateListeners.iterator();
        while (it.hasNext()) {
            try {
                ((AuthenticationStateListener) it.next())
                        .onAuthenticationHelp(authenticationHelpInfo);
            } catch (RemoteException e) {
                Slog.e(
                        "AuthenticationStateListeners",
                        "Remote exception in notifying listener of recoverable authentication"
                            + " error",
                        e);
            }
        }
    }

    public final void onAuthenticationStarted(AuthenticationStartedInfo authenticationStartedInfo) {
        Iterator it = this.mAuthenticationStateListeners.iterator();
        while (it.hasNext()) {
            try {
                ((AuthenticationStateListener) it.next())
                        .onAuthenticationStarted(authenticationStartedInfo);
            } catch (RemoteException e) {
                Slog.e(
                        "AuthenticationStateListeners",
                        "Remote exception in notifying listener that authentication started",
                        e);
            }
        }
    }

    public final void onAuthenticationStopped(AuthenticationStoppedInfo authenticationStoppedInfo) {
        Iterator it = this.mAuthenticationStateListeners.iterator();
        while (it.hasNext()) {
            try {
                ((AuthenticationStateListener) it.next())
                        .onAuthenticationStopped(authenticationStoppedInfo);
            } catch (RemoteException e) {
                Slog.e(
                        "AuthenticationStateListeners",
                        "Remote exception in notifying listener that authentication stopped",
                        e);
            }
        }
    }

    public final void onAuthenticationSucceeded(
            AuthenticationSucceededInfo authenticationSucceededInfo) {
        Iterator it = this.mAuthenticationStateListeners.iterator();
        while (it.hasNext()) {
            try {
                ((AuthenticationStateListener) it.next())
                        .onAuthenticationSucceeded(authenticationSucceededInfo);
            } catch (RemoteException e) {
                Slog.e(
                        "AuthenticationStateListeners",
                        "Remote exception in notifying listener that authentication succeeded",
                        e);
            }
        }
    }
}
