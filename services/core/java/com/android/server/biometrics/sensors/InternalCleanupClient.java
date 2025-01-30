package com.android.server.biometrics.sensors;

import android.content.Context;
import android.hardware.biometrics.BiometricAuthenticator;
import android.os.Build;
import android.os.IBinder;
import android.util.Slog;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public abstract class InternalCleanupClient extends HalClientMonitor implements EnumerateConsumer, RemovalConsumer, EnrollmentModifier {
    public final Map mAuthenticatorIds;
    public final BiometricUtils mBiometricUtils;
    public BaseClientMonitor mCurrentTask;
    public final ClientMonitorCallback mEnumerateCallback;
    public boolean mFavorHalEnrollments;
    public final boolean mHasEnrollmentsBeforeStarting;
    public final ClientMonitorCallback mRemoveCallback;
    public final ArrayList mUnknownHALTemplates;

    public abstract InternalEnumerateClient getEnumerateClient(Context context, Supplier supplier, IBinder iBinder, int i, String str, List list, BiometricUtils biometricUtils, int i2, BiometricLogger biometricLogger, BiometricContext biometricContext);

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public int getProtoEnum() {
        return 7;
    }

    public abstract RemovalClient getRemovalClient(Context context, Supplier supplier, IBinder iBinder, int i, int i2, String str, BiometricUtils biometricUtils, int i3, BiometricLogger biometricLogger, BiometricContext biometricContext, Map map);

    public void onAddUnknownTemplate(int i, BiometricAuthenticator.Identifier identifier) {
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public void unableToStart() {
    }

    public final class UserTemplate {
        public final BiometricAuthenticator.Identifier mIdentifier;
        public final int mUserId;

        public UserTemplate(BiometricAuthenticator.Identifier identifier, int i) {
            this.mIdentifier = identifier;
            this.mUserId = i;
        }
    }

    public InternalCleanupClient(Context context, Supplier supplier, int i, String str, int i2, BiometricLogger biometricLogger, BiometricContext biometricContext, BiometricUtils biometricUtils, Map map) {
        super(context, supplier, null, null, i, str, 0, i2, biometricLogger, biometricContext);
        this.mUnknownHALTemplates = new ArrayList();
        this.mFavorHalEnrollments = false;
        this.mEnumerateCallback = new ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.InternalCleanupClient.1
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
                List unknownHALTemplates = ((InternalEnumerateClient) InternalCleanupClient.this.mCurrentTask).getUnknownHALTemplates();
                Slog.d("Biometrics/InternalCleanupClient", "Enumerate onClientFinished: " + baseClientMonitor + ", success: " + z);
                if (!unknownHALTemplates.isEmpty()) {
                    Slog.w("Biometrics/InternalCleanupClient", "Adding " + unknownHALTemplates.size() + " templates for deletion");
                }
                Iterator it = unknownHALTemplates.iterator();
                while (it.hasNext()) {
                    InternalCleanupClient.this.mUnknownHALTemplates.add(new UserTemplate((BiometricAuthenticator.Identifier) it.next(), InternalCleanupClient.this.mCurrentTask.getTargetUserId()));
                }
                if (InternalCleanupClient.this.mUnknownHALTemplates.isEmpty()) {
                    return;
                }
                if (InternalCleanupClient.this.mFavorHalEnrollments) {
                    Build.isDebuggable();
                }
                try {
                    Iterator it2 = InternalCleanupClient.this.mUnknownHALTemplates.iterator();
                    while (it2.hasNext()) {
                        UserTemplate userTemplate = (UserTemplate) it2.next();
                        Slog.i("Biometrics/InternalCleanupClient", "Adding unknown HAL template: " + userTemplate.mIdentifier.getBiometricId());
                        InternalCleanupClient.this.onAddUnknownTemplate(userTemplate.mUserId, userTemplate.mIdentifier);
                    }
                } finally {
                    InternalCleanupClient internalCleanupClient = InternalCleanupClient.this;
                    internalCleanupClient.mCallback.onClientFinished(internalCleanupClient, z);
                }
            }
        };
        this.mRemoveCallback = new ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.InternalCleanupClient.2
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
                Slog.d("Biometrics/InternalCleanupClient", "Remove onClientFinished: " + baseClientMonitor + ", success: " + z);
                if (InternalCleanupClient.this.mUnknownHALTemplates.isEmpty()) {
                    InternalCleanupClient internalCleanupClient = InternalCleanupClient.this;
                    internalCleanupClient.mCallback.onClientFinished(internalCleanupClient, z);
                } else {
                    InternalCleanupClient.this.startCleanupUnknownHalTemplates();
                }
            }
        };
        this.mBiometricUtils = biometricUtils;
        this.mAuthenticatorIds = map;
        this.mHasEnrollmentsBeforeStarting = !biometricUtils.getBiometricsForUser(context, i).isEmpty();
    }

    public final void startCleanupUnknownHalTemplates() {
        Slog.d("Biometrics/InternalCleanupClient", "startCleanupUnknownHalTemplates, size: " + this.mUnknownHALTemplates.size());
        UserTemplate userTemplate = (UserTemplate) this.mUnknownHALTemplates.get(0);
        this.mUnknownHALTemplates.remove(userTemplate);
        this.mCurrentTask = getRemovalClient(getContext(), this.mLazyDaemon, getToken(), userTemplate.mIdentifier.getBiometricId(), userTemplate.mUserId, getContext().getPackageName(), this.mBiometricUtils, getSensorId(), getLogger(), getBiometricContext(), this.mAuthenticatorIds);
        getLogger().logUnknownEnrollmentInHal();
        this.mCurrentTask.start(this.mRemoveCallback);
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public void start(ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        List biometricsForUser = this.mBiometricUtils.getBiometricsForUser(getContext(), getTargetUserId());
        this.mCurrentTask = getEnumerateClient(getContext(), this.mLazyDaemon, getToken(), getTargetUserId(), getOwnerString(), biometricsForUser, this.mBiometricUtils, getSensorId(), getLogger(), getBiometricContext());
        Slog.d("Biometrics/InternalCleanupClient", "Starting enumerate: " + this.mCurrentTask + " enrolledList size:" + biometricsForUser.size());
        this.mCurrentTask.start(this.mEnumerateCallback);
    }

    @Override // com.android.server.biometrics.sensors.RemovalConsumer
    public void onRemoved(BiometricAuthenticator.Identifier identifier, int i) {
        BaseClientMonitor baseClientMonitor = this.mCurrentTask;
        if (!(baseClientMonitor instanceof RemovalClient)) {
            Slog.e("Biometrics/InternalCleanupClient", "onRemoved received during client: " + this.mCurrentTask.getClass().getSimpleName());
            return;
        }
        ((RemovalClient) baseClientMonitor).onRemoved(identifier, i);
    }

    @Override // com.android.server.biometrics.sensors.EnrollmentModifier
    public boolean hasEnrollmentStateChanged() {
        return (this.mBiometricUtils.getBiometricsForUser(getContext(), getTargetUserId()).isEmpty() ^ true) != this.mHasEnrollmentsBeforeStarting;
    }

    @Override // com.android.server.biometrics.sensors.EnrollmentModifier
    public boolean hasEnrollments() {
        return !this.mBiometricUtils.getBiometricsForUser(getContext(), getTargetUserId()).isEmpty();
    }

    @Override // com.android.server.biometrics.sensors.EnumerateConsumer
    public void onEnumerationResult(BiometricAuthenticator.Identifier identifier, int i) {
        if (!(this.mCurrentTask instanceof InternalEnumerateClient)) {
            Slog.e("Biometrics/InternalCleanupClient", "onEnumerationResult received during client: " + this.mCurrentTask.getClass().getSimpleName());
            return;
        }
        Slog.d("Biometrics/InternalCleanupClient", "onEnumerated, remaining: " + i);
        ((EnumerateConsumer) this.mCurrentTask).onEnumerationResult(identifier, i);
    }

    public void setFavorHalEnrollments() {
        this.mFavorHalEnrollments = true;
    }

    public InternalEnumerateClient getCurrentEnumerateClient() {
        return (InternalEnumerateClient) this.mCurrentTask;
    }

    public RemovalClient getCurrentRemoveClient() {
        return (RemovalClient) this.mCurrentTask;
    }

    public ArrayList getUnknownHALTemplates() {
        return this.mUnknownHALTemplates;
    }
}
