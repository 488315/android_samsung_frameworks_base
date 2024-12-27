package com.android.server.biometrics.sensors;

import android.content.Context;
import android.hardware.biometrics.BiometricAuthenticator;
import android.os.IBinder;
import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class InternalCleanupClient extends HalClientMonitor
        implements EnumerateConsumer, RemovalConsumer, EnrollmentModifier {
    public final Map mAuthenticatorIds;
    public final BiometricUtils mBiometricUtils;
    public HalClientMonitor mCurrentTask;
    public final AnonymousClass1 mEnumerateCallback;
    public boolean mFavorHalEnrollments;
    public final boolean mHasEnrollmentsBeforeStarting;
    public final AnonymousClass1 mRemoveCallback;
    public final ArrayList mUnknownHALTemplates;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UserTemplate {
        public final BiometricAuthenticator.Identifier mIdentifier;
        public final int mUserId;

        public UserTemplate(BiometricAuthenticator.Identifier identifier, int i) {
            this.mIdentifier = identifier;
            this.mUserId = i;
        }
    }

    /* renamed from: -$$Nest$mstartCleanupUnknownHalTemplates, reason: not valid java name */
    public static void m314$$Nest$mstartCleanupUnknownHalTemplates(
            InternalCleanupClient internalCleanupClient) {
        Slog.d(
                "Biometrics/InternalCleanupClient",
                "startCleanupUnknownHalTemplates, size: "
                        + internalCleanupClient.mUnknownHALTemplates.size());
        UserTemplate userTemplate =
                (UserTemplate) internalCleanupClient.mUnknownHALTemplates.get(0);
        internalCleanupClient.mUnknownHALTemplates.remove(userTemplate);
        internalCleanupClient.mCurrentTask =
                internalCleanupClient.getRemovalClient(
                        internalCleanupClient.mContext,
                        internalCleanupClient.mLazyDaemon,
                        internalCleanupClient.mToken,
                        userTemplate.mIdentifier.getBiometricId(),
                        userTemplate.mUserId,
                        internalCleanupClient.mContext.getPackageName(),
                        internalCleanupClient.mBiometricUtils,
                        internalCleanupClient.mSensorId,
                        internalCleanupClient.mLogger,
                        internalCleanupClient.mBiometricContext,
                        internalCleanupClient.mAuthenticatorIds);
        BiometricLogger biometricLogger = internalCleanupClient.mLogger;
        if (!biometricLogger.shouldSkipLogging()) {
            biometricLogger.mSink.getClass();
            FrameworkStatsLog.write(148, biometricLogger.mStatsModality, 3, -1);
        }
        internalCleanupClient.mCurrentTask.start(internalCleanupClient.mRemoveCallback);
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.biometrics.sensors.InternalCleanupClient$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.server.biometrics.sensors.InternalCleanupClient$1] */
    public InternalCleanupClient(
            Context context,
            Supplier supplier,
            int i,
            String str,
            int i2,
            BiometricLogger biometricLogger,
            BiometricContext biometricContext,
            BiometricUtils biometricUtils,
            Map map) {
        super(
                context,
                supplier,
                null,
                null,
                i,
                str,
                0,
                i2,
                biometricLogger,
                biometricContext,
                false);
        this.mUnknownHALTemplates = new ArrayList();
        this.mFavorHalEnrollments = false;
        final int i3 = 0;
        this.mEnumerateCallback =
                new ClientMonitorCallback(
                        this) { // from class:
                                // com.android.server.biometrics.sensors.InternalCleanupClient.1
                    public final /* synthetic */ InternalCleanupClient this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
                    public final void onClientFinished(
                            BaseClientMonitor baseClientMonitor, boolean z) {
                        InternalCleanupClient internalCleanupClient;
                        switch (i3) {
                            case 0:
                                internalCleanupClient = this.this$0;
                                List list =
                                        ((InternalEnumerateClient)
                                                        internalCleanupClient.mCurrentTask)
                                                .mUnknownHALTemplates;
                                Slog.d(
                                        "Biometrics/InternalCleanupClient",
                                        "Enumerate onClientFinished: "
                                                + baseClientMonitor
                                                + ", success: "
                                                + z);
                                ArrayList arrayList = (ArrayList) list;
                                if (!arrayList.isEmpty()) {
                                    Slog.w(
                                            "Biometrics/InternalCleanupClient",
                                            "Adding "
                                                    + arrayList.size()
                                                    + " templates for deletion");
                                }
                                Iterator it = arrayList.iterator();
                                while (it.hasNext()) {
                                    internalCleanupClient.mUnknownHALTemplates.add(
                                            new UserTemplate(
                                                    (BiometricAuthenticator.Identifier) it.next(),
                                                    internalCleanupClient
                                                            .mCurrentTask
                                                            .mTargetUserId));
                                }
                                if (internalCleanupClient.mUnknownHALTemplates.isEmpty()) {
                                    return;
                                }
                                if (!internalCleanupClient.mFavorHalEnrollments) {
                                    InternalCleanupClient
                                            .m314$$Nest$mstartCleanupUnknownHalTemplates(
                                                    internalCleanupClient);
                                    return;
                                }
                                try {
                                    Iterator it2 =
                                            internalCleanupClient.mUnknownHALTemplates.iterator();
                                    while (it2.hasNext()) {
                                        UserTemplate userTemplate = (UserTemplate) it2.next();
                                        Slog.i(
                                                "Biometrics/InternalCleanupClient",
                                                "Adding unknown HAL template: "
                                                        + userTemplate.mIdentifier
                                                                .getBiometricId());
                                        internalCleanupClient.onAddUnknownTemplate(
                                                userTemplate.mIdentifier);
                                    }
                                    return;
                                } finally {
                                    internalCleanupClient.mCallback.onClientFinished(
                                            internalCleanupClient, z);
                                }
                            default:
                                Slog.d(
                                        "Biometrics/InternalCleanupClient",
                                        "Remove onClientFinished: "
                                                + baseClientMonitor
                                                + ", success: "
                                                + z);
                                internalCleanupClient = this.this$0;
                                if (internalCleanupClient.mUnknownHALTemplates.isEmpty()) {
                                    return;
                                }
                                InternalCleanupClient.m314$$Nest$mstartCleanupUnknownHalTemplates(
                                        internalCleanupClient);
                                return;
                        }
                    }
                };
        final int i4 = 1;
        this.mRemoveCallback =
                new ClientMonitorCallback(
                        this) { // from class:
                                // com.android.server.biometrics.sensors.InternalCleanupClient.1
                    public final /* synthetic */ InternalCleanupClient this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
                    public final void onClientFinished(
                            BaseClientMonitor baseClientMonitor, boolean z) {
                        InternalCleanupClient internalCleanupClient;
                        switch (i4) {
                            case 0:
                                internalCleanupClient = this.this$0;
                                List list =
                                        ((InternalEnumerateClient)
                                                        internalCleanupClient.mCurrentTask)
                                                .mUnknownHALTemplates;
                                Slog.d(
                                        "Biometrics/InternalCleanupClient",
                                        "Enumerate onClientFinished: "
                                                + baseClientMonitor
                                                + ", success: "
                                                + z);
                                ArrayList arrayList = (ArrayList) list;
                                if (!arrayList.isEmpty()) {
                                    Slog.w(
                                            "Biometrics/InternalCleanupClient",
                                            "Adding "
                                                    + arrayList.size()
                                                    + " templates for deletion");
                                }
                                Iterator it = arrayList.iterator();
                                while (it.hasNext()) {
                                    internalCleanupClient.mUnknownHALTemplates.add(
                                            new UserTemplate(
                                                    (BiometricAuthenticator.Identifier) it.next(),
                                                    internalCleanupClient
                                                            .mCurrentTask
                                                            .mTargetUserId));
                                }
                                if (internalCleanupClient.mUnknownHALTemplates.isEmpty()) {
                                    return;
                                }
                                if (!internalCleanupClient.mFavorHalEnrollments) {
                                    InternalCleanupClient
                                            .m314$$Nest$mstartCleanupUnknownHalTemplates(
                                                    internalCleanupClient);
                                    return;
                                }
                                try {
                                    Iterator it2 =
                                            internalCleanupClient.mUnknownHALTemplates.iterator();
                                    while (it2.hasNext()) {
                                        UserTemplate userTemplate = (UserTemplate) it2.next();
                                        Slog.i(
                                                "Biometrics/InternalCleanupClient",
                                                "Adding unknown HAL template: "
                                                        + userTemplate.mIdentifier
                                                                .getBiometricId());
                                        internalCleanupClient.onAddUnknownTemplate(
                                                userTemplate.mIdentifier);
                                    }
                                    return;
                                } finally {
                                    internalCleanupClient.mCallback.onClientFinished(
                                            internalCleanupClient, z);
                                }
                            default:
                                Slog.d(
                                        "Biometrics/InternalCleanupClient",
                                        "Remove onClientFinished: "
                                                + baseClientMonitor
                                                + ", success: "
                                                + z);
                                internalCleanupClient = this.this$0;
                                if (internalCleanupClient.mUnknownHALTemplates.isEmpty()) {
                                    return;
                                }
                                InternalCleanupClient.m314$$Nest$mstartCleanupUnknownHalTemplates(
                                        internalCleanupClient);
                                return;
                        }
                    }
                };
        this.mBiometricUtils = biometricUtils;
        this.mAuthenticatorIds = map;
        this.mHasEnrollmentsBeforeStarting =
                !biometricUtils.getBiometricsForUser(context, i).isEmpty();
    }

    public InternalEnumerateClient getCurrentEnumerateClient() {
        return (InternalEnumerateClient) this.mCurrentTask;
    }

    public RemovalClient getCurrentRemoveClient() {
        return (RemovalClient) this.mCurrentTask;
    }

    public abstract InternalEnumerateClient getEnumerateClient(
            Context context,
            Supplier supplier,
            IBinder iBinder,
            int i,
            String str,
            List list,
            BiometricUtils biometricUtils,
            int i2,
            BiometricLogger biometricLogger,
            BiometricContext biometricContext);

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final int getProtoEnum() {
        return 7;
    }

    public abstract RemovalClient getRemovalClient(
            Context context,
            Supplier supplier,
            IBinder iBinder,
            int i,
            int i2,
            String str,
            BiometricUtils biometricUtils,
            int i3,
            BiometricLogger biometricLogger,
            BiometricContext biometricContext,
            Map map);

    public ArrayList getUnknownHALTemplates() {
        return this.mUnknownHALTemplates;
    }

    @Override // com.android.server.biometrics.sensors.EnrollmentModifier
    public final boolean hasEnrollmentStateChanged() {
        return (this.mBiometricUtils
                                .getBiometricsForUser(this.mContext, this.mTargetUserId)
                                .isEmpty()
                        ^ true)
                != this.mHasEnrollmentsBeforeStarting;
    }

    @Override // com.android.server.biometrics.sensors.EnrollmentModifier
    public final boolean hasEnrollments() {
        return !this.mBiometricUtils
                .getBiometricsForUser(this.mContext, this.mTargetUserId)
                .isEmpty();
    }

    public abstract void onAddUnknownTemplate(BiometricAuthenticator.Identifier identifier);

    @Override // com.android.server.biometrics.sensors.EnumerateConsumer
    public final void onEnumerationResult(BiometricAuthenticator.Identifier identifier, int i) {
        HalClientMonitor halClientMonitor = this.mCurrentTask;
        if (!(halClientMonitor instanceof InternalEnumerateClient)) {
            Slog.e(
                    "Biometrics/InternalCleanupClient",
                    "onEnumerationResult received during client: "
                            .concat(halClientMonitor.getClass().getSimpleName()));
        } else {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    i, "onEnumerated, remaining: ", "Biometrics/InternalCleanupClient");
            ((EnumerateConsumer) this.mCurrentTask).onEnumerationResult(identifier, i);
        }
    }

    @Override // com.android.server.biometrics.sensors.RemovalConsumer
    public final void onRemoved(BiometricAuthenticator.Identifier identifier, int i) {
        HalClientMonitor halClientMonitor = this.mCurrentTask;
        if (halClientMonitor instanceof RemovalClient) {
            ((RemovalClient) halClientMonitor).onRemoved(identifier, i);
        } else {
            Slog.e(
                    "Biometrics/InternalCleanupClient",
                    "onRemoved received during client: "
                            .concat(halClientMonitor.getClass().getSimpleName()));
        }
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final void start(ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        List biometricsForUser =
                this.mBiometricUtils.getBiometricsForUser(this.mContext, this.mTargetUserId);
        this.mCurrentTask =
                getEnumerateClient(
                        this.mContext,
                        this.mLazyDaemon,
                        this.mToken,
                        this.mTargetUserId,
                        this.mOwner,
                        biometricsForUser,
                        this.mBiometricUtils,
                        this.mSensorId,
                        this.mLogger,
                        this.mBiometricContext);
        Slog.d(
                "Biometrics/InternalCleanupClient",
                "Starting enumerate: "
                        + this.mCurrentTask
                        + " enrolledList size:"
                        + biometricsForUser.size());
        this.mCurrentTask.start(this.mEnumerateCallback);
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void unableToStart() {}
}
