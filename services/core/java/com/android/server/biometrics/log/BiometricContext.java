package com.android.server.biometrics.log;

import android.content.Context;
import com.android.internal.statusbar.ISessionListener;
import com.android.server.biometrics.sensors.AuthSessionCoordinator;
import com.android.server.biometrics.sensors.fingerprint.SemBiometricDisplayStateMonitor;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public interface BiometricContext {
    void ensureBiometricContextListener(SemBiometricDisplayStateMonitor semBiometricDisplayStateMonitor);

    AuthSessionCoordinator getAuthSessionCoordinator();

    BiometricContextSessionInfo getBiometricPromptSessionInfo();

    int getCurrentRotation();

    int getDisplayState();

    int getDockedState();

    int getFoldState();

    ISessionListener getISessionListener();

    BiometricContextSessionInfo getKeyguardEntrySessionInfo();

    boolean isAod();

    boolean isAwake();

    boolean isDisplayOn();

    void subscribe(OperationContextExt operationContextExt, Consumer consumer);

    void unsubscribe(OperationContextExt operationContextExt);

    OperationContextExt updateContext(OperationContextExt operationContextExt, boolean z);

    static BiometricContext getInstance(Context context) {
        return BiometricContextProvider.defaultProvider(context);
    }
}
