package com.android.server.biometrics.log;

import android.content.Context;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.view.WindowManager;

import com.android.internal.statusbar.IStatusBarService;
import com.android.server.biometrics.BiometricHandlerProvider;
import com.android.server.biometrics.sensors.AuthSessionCoordinator;

public interface BiometricContext {
    static BiometricContextProvider getInstance(Context context) {
        synchronized (BiometricContextProvider.class) {
            if (BiometricContextProvider.sInstance == null) {
                try {
                    BiometricContextProvider.sInstance =
                            new BiometricContextProvider(
                                    context,
                                    (WindowManager) context.getSystemService("window"),
                                    IStatusBarService.Stub.asInterface(
                                            ServiceManager.getServiceOrThrow("statusbar")),
                                    BiometricHandlerProvider.sBiometricHandlerProvider
                                            .getBiometricCallbackHandler(),
                                    new AuthSessionCoordinator(SystemClock.elapsedRealtimeClock()));
                } catch (ServiceManager.ServiceNotFoundException e) {
                    throw new IllegalStateException("Failed to find required service", e);
                }
            }
        }
        return BiometricContextProvider.sInstance;
    }
}
