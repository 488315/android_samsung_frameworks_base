package com.android.server.biometrics.log;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.biometrics.AuthenticationStatsCollector;
import com.android.server.biometrics.Utils;

import java.util.concurrent.TimeUnit;

public final class BiometricLogger {
    public static final boolean DEBUG = Utils.DEBUG;
    public final ALSProbe mALSProbe;
    public final AuthenticationStatsCollector mAuthenticationStatsCollector;
    public long mFirstAcquireTimeMs;
    public boolean mShouldLogMetrics;
    public final BiometricFrameworkStatsLogger mSink;
    public final int mStatsAction;
    public final int mStatsClient;
    public final int mStatsModality;

    public BiometricLogger(
            int i,
            int i2,
            int i3,
            BiometricFrameworkStatsLogger biometricFrameworkStatsLogger,
            AuthenticationStatsCollector authenticationStatsCollector,
            SensorManager sensorManager) {
        this.mShouldLogMetrics = true;
        this.mStatsModality = i;
        this.mStatsAction = i2;
        this.mStatsClient = i3;
        this.mSink = biometricFrameworkStatsLogger;
        this.mAuthenticationStatsCollector = authenticationStatsCollector;
        this.mALSProbe =
                new ALSProbe(
                        sensorManager,
                        new Handler(Looper.getMainLooper()),
                        TimeUnit.MINUTES.toMillis(1L));
    }

    public BiometricLogger(
            Context context,
            int i,
            int i2,
            int i3,
            AuthenticationStatsCollector authenticationStatsCollector) {
        this(
                i,
                i2,
                i3,
                BiometricFrameworkStatsLogger.sInstance,
                authenticationStatsCollector,
                (SensorManager) context.getSystemService(SensorManager.class));
    }

    public static BiometricLogger ofUnknown(Context context) {
        return new BiometricLogger(context, 0, 0, 0, null);
    }

    public final void logOnEnrolled(int i, int i2, boolean z, long j) {
        if (this.mShouldLogMetrics) {
            if (DEBUG) {
                StringBuilder sb = new StringBuilder("Enrolled! Modality: ");
                ServiceKeeper$$ExternalSyntheticOutline0.m(
                        this.mStatsModality, i, ", User: ", ", Client: ", sb);
                sb.append(this.mStatsClient);
                sb.append(", Latency: ");
                sb.append(j);
                sb.append(", Lux: ");
                sb.append(this.mALSProbe.mLastAmbientLux);
                sb.append(", Success: ");
                sb.append(z);
                Slog.v("BiometricLogger", sb.toString());
            } else {
                Slog.v("BiometricLogger", "Enroll latency: " + j);
            }
            if (shouldSkipLogging()) {
                return;
            }
            BiometricFrameworkStatsLogger biometricFrameworkStatsLogger = this.mSink;
            int i3 = this.mStatsModality;
            float f = this.mALSProbe.mLastAmbientLux;
            biometricFrameworkStatsLogger.getClass();
            FrameworkStatsLog.write(
                    184, i3, i, BiometricFrameworkStatsLogger.sanitizeLatency(j), z, -1, f, i2);
        }
    }

    public final void logOnError(
            Context context, OperationContextExt operationContextExt, int i, int i2, int i3) {
        if (this.mShouldLogMetrics) {
            long currentTimeMillis =
                    this.mFirstAcquireTimeMs != 0
                            ? System.currentTimeMillis() - this.mFirstAcquireTimeMs
                            : -1L;
            if (DEBUG) {
                StringBuilder sb = new StringBuilder("Error! Modality: ");
                ServiceKeeper$$ExternalSyntheticOutline0.m(
                        this.mStatsModality, i3, ", User: ", ", IsCrypto: ", sb);
                sb.append(operationContextExt.mAidlContext.isCrypto);
                sb.append(", Action: ");
                sb.append(this.mStatsAction);
                sb.append(", Client: ");
                ServiceKeeper$$ExternalSyntheticOutline0.m(
                        this.mStatsClient, i, ", Error: ", ", VendorCode: ", sb);
                sb.append(i2);
                sb.append(", Latency: ");
                sb.append(currentTimeMillis);
                Slog.v("BiometricLogger", sb.toString());
            } else {
                Slog.v("BiometricLogger", "Error latency: " + currentTimeMillis);
            }
            if (shouldSkipLogging()) {
                return;
            }
            boolean isDebugEnabled = Utils.isDebugEnabled(context, i3);
            this.mSink.getClass();
            BiometricFrameworkStatsLogger.error(
                    operationContextExt,
                    this.mStatsModality,
                    this.mStatsAction,
                    this.mStatsClient,
                    isDebugEnabled,
                    currentTimeMillis,
                    i,
                    i2,
                    i3);
        }
    }

    public final boolean shouldSkipLogging() {
        int i = this.mStatsAction;
        int i2 = this.mStatsModality;
        boolean z = i2 == 0 || i == 0;
        if (i2 == 0) {
            Slog.w(
                    "BiometricLogger",
                    "Unknown field detected: MODALITY_UNKNOWN, will not report metric");
        }
        if (i == 0) {
            Slog.w(
                    "BiometricLogger",
                    "Unknown field detected: ACTION_UNKNOWN, will not report metric");
        }
        if (this.mStatsClient == 0) {
            Slog.w("BiometricLogger", "Unknown field detected: CLIENT_UNKNOWN");
        }
        return z;
    }
}
