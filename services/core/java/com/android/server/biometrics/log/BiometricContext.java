package com.android.server.biometrics.log;

import android.content.Context;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.view.WindowManager;
import com.android.internal.statusbar.IStatusBarService;
import com.android.server.biometrics.BiometricHandlerProvider;
import com.android.server.biometrics.sensors.AuthSessionCoordinator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface BiometricContext {
    static BiometricContextProvider getInstance(Context context) {
        synchronized (BiometricContextProvider.class) {
            if (BiometricContextProvider.sInstance == null) {
                try {
                    BiometricContextProvider.sInstance = new BiometricContextProvider(context, (WindowManager) context.getSystemService("window"), IStatusBarService.Stub.asInterface(ServiceManager.getServiceOrThrow("statusbar")), BiometricHandlerProvider.sBiometricHandlerProvider.getBiometricCallbackHandler(), new AuthSessionCoordinator(SystemClock.elapsedRealtimeClock()));
                } catch (ServiceManager.ServiceNotFoundException e) {
                    throw new IllegalStateException("Failed to find required service", e);
                }
            }
        }
        return BiometricContextProvider.sInstance;
    }
}
