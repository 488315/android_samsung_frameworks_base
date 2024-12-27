package com.android.systemui.biometrics;

import android.content.Context;
import android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback;
import android.os.RemoteException;
import android.os.Trace;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.util.concurrency.Execution;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        logBuffer.log("UdfpsDisplayMode", logLevel, "disable", null);
        Request request = this.currentRequest;
        if (request == null) {
            logBuffer.log("UdfpsDisplayMode", LogLevel.WARNING, "disable | already disabled", null);
            return;
        }
        Trace.beginSection("UdfpsDisplayMode.disable");
        try {
            IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallback = this.authController.mUdfpsRefreshRateRequestCallback;
            Intrinsics.checkNotNull(iUdfpsRefreshRateRequestCallback);
            iUdfpsRefreshRateRequestCallback.onRequestDisabled(request.displayId);
            logBuffer.log("UdfpsDisplayMode", logLevel, "disable | removed the UDFPS refresh rate request", null);
        } catch (RemoteException e) {
            logBuffer.commit(logBuffer.obtain("UdfpsDisplayMode", LogLevel.ERROR, new UdfpsLogger$e$2("disable"), e));
        }
        this.currentRequest = null;
        logBuffer.log("UdfpsDisplayMode", LogLevel.WARNING, "disable | onDisabled is null", null);
        Trace.endSection();
    }
}
