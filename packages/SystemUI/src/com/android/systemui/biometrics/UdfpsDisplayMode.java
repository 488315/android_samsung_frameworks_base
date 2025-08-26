package com.android.systemui.biometrics;

import android.content.Context;
import android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback;
import android.os.RemoteException;
import android.os.Trace;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda7;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.util.concurrency.Execution;

/* loaded from: classes.dex */
public final class UdfpsDisplayMode implements UdfpsDisplayModeProvider {
    public final AuthController authController;
    public final Context context;
    public Request currentRequest;
    public final Execution execution;
    public final UdfpsLogger logger;

    public UdfpsDisplayMode(Context context, Execution execution, AuthController authController, UdfpsLogger udfpsLogger) {
        this.context = context;
        this.execution = execution;
        this.authController = authController;
        this.logger = udfpsLogger;
    }

    public final void disable() {
        this.execution.isMainThread();
        UdfpsLogger udfpsLogger = this.logger;
        udfpsLogger.getClass();
        LogLevel logLevel = LogLevel.VERBOSE;
        LogBuffer logBuffer = udfpsLogger.logBuffer;
        LogBuffer.log$default(logBuffer, "UdfpsDisplayMode", logLevel, "disable");
        Request request = this.currentRequest;
        if (request == null) {
            LogBuffer.log$default(logBuffer, "UdfpsDisplayMode", LogLevel.WARNING, "disable | already disabled");
            return;
        }
        Trace.beginSection("UdfpsDisplayMode.disable");
        try {
            IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallback = this.authController.mUdfpsRefreshRateRequestCallback;
            iUdfpsRefreshRateRequestCallback.getClass();
            iUdfpsRefreshRateRequestCallback.onRequestDisabled(request.displayId);
            LogBuffer.log$default(logBuffer, "UdfpsDisplayMode", logLevel, "disable | removed the UDFPS refresh rate request");
        } catch (RemoteException e) {
            logBuffer.commit(logBuffer.obtain("UdfpsDisplayMode", LogLevel.ERROR, new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda7("disable"), e));
        }
        this.currentRequest = null;
        LogBuffer.log$default(logBuffer, "UdfpsDisplayMode", LogLevel.WARNING, "disable | onDisabled is null");
        Trace.endSection();
    }
}
