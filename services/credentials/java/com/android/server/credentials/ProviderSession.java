package com.android.server.credentials;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.credentials.CredentialProviderInfo;
import android.credentials.ui.ProviderData;
import android.credentials.ui.ProviderPendingIntentResponse;
import android.os.ICancellationSignal;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.credentials.RemoteCredentialService;
import com.android.server.credentials.metrics.ProviderSessionMetric;
import java.util.UUID;

/* loaded from: classes.dex */
public abstract class ProviderSession implements RemoteCredentialService.ProviderCallbacks {
    public final ProviderInternalCallback mCallbacks;
    public final ComponentName mComponentName;
    public final Context mContext;
    public ICancellationSignal mProviderCancellationSignal;
    public final Object mProviderRequest;
    public Object mProviderResponse;
    public final ProviderSessionMetric mProviderSessionMetric;
    public int mProviderSessionUid;
    public final RemoteCredentialService mRemoteCredentialService;
    public final int mUserId;
    public Status mStatus = Status.NOT_STARTED;
    public Boolean mProviderResponseSet = Boolean.FALSE;
    public final CredentialProviderInfo mProviderInfo = null;

    enum CredentialsSource {
        REMOTE_PROVIDER,
        REGISTRY,
        AUTH_ENTRY
    }

    public interface ProviderInternalCallback {
        void onFinalErrorReceived(ComponentName componentName, String str, String str2);

        void onFinalResponseReceived(ComponentName componentName, Object obj);

        void onProviderStatusChanged(Status status, ComponentName componentName, CredentialsSource credentialsSource);
    }

    enum Status {
        NOT_STARTED,
        PENDING,
        CREDENTIALS_RECEIVED,
        SERVICE_DEAD,
        SAVE_ENTRIES_RECEIVED,
        CANCELED,
        EMPTY_RESPONSE,
        NO_CREDENTIALS_FROM_AUTH_ENTRY,
        COMPLETE
    }

    public abstract void invokeSession();

    public abstract void onUiEntrySelected(String str, String str2, ProviderPendingIntentResponse providerPendingIntentResponse);

    /* renamed from: prepareUiData */
    public abstract ProviderData mo4615prepareUiData();

    public static boolean isUiInvokingStatus(Status status) {
        return status == Status.CREDENTIALS_RECEIVED || status == Status.SAVE_ENTRIES_RECEIVED || status == Status.NO_CREDENTIALS_FROM_AUTH_ENTRY;
    }

    public static boolean isStatusWaitingForRemoteResponse(Status status) {
        return status == Status.PENDING;
    }

    public static boolean isTerminatingStatus(Status status) {
        return status == Status.CANCELED || status == Status.SERVICE_DEAD;
    }

    public static boolean isCompletionStatus(Status status) {
        return status == Status.COMPLETE || status == Status.EMPTY_RESPONSE;
    }

    public ProviderSessionMetric getProviderSessionMetric() {
        return this.mProviderSessionMetric;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ProviderSession(Context context, Object obj, ProviderInternalCallback providerInternalCallback, ComponentName componentName, int i, RemoteCredentialService remoteCredentialService) {
        this.mContext = context;
        this.mProviderRequest = obj;
        this.mCallbacks = providerInternalCallback;
        this.mUserId = i;
        this.mComponentName = componentName;
        this.mRemoteCredentialService = remoteCredentialService;
        this.mProviderSessionUid = MetricUtilities.getPackageUid(context, componentName);
        this.mProviderSessionMetric = new ProviderSessionMetric(((RequestSession) providerInternalCallback).mRequestSessionMetric.getSessionIdTrackTwo());
    }

    public static String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

    public void cancelProviderRemoteSession() {
        try {
            ICancellationSignal iCancellationSignal = this.mProviderCancellationSignal;
            if (iCancellationSignal != null) {
                iCancellationSignal.cancel();
            }
            setStatus(Status.CANCELED);
        } catch (RemoteException e) {
            Slog.e("ProviderSession", "Issue while cancelling provider session: ", e);
        }
    }

    public void setStatus(Status status) {
        this.mStatus = status;
    }

    public Status getStatus() {
        return this.mStatus;
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public void updateStatusAndInvokeCallback(Status status, CredentialsSource credentialsSource) {
        setStatus(status);
        CredentialProviderInfo credentialProviderInfo = this.mProviderInfo;
        this.mProviderSessionMetric.collectCandidateMetricUpdate(isTerminatingStatus(status) || isStatusWaitingForRemoteResponse(status), isCompletionStatus(status) || isUiInvokingStatus(status), this.mProviderSessionUid, credentialsSource == CredentialsSource.AUTH_ENTRY, credentialProviderInfo != null && credentialProviderInfo.isPrimary());
        this.mCallbacks.onProviderStatusChanged(status, this.mComponentName, credentialsSource);
    }

    public void startCandidateMetrics() {
        this.mProviderSessionMetric.collectCandidateMetricSetupViaInitialMetric(((RequestSession) this.mCallbacks).mRequestSessionMetric.getInitialPhaseMetric());
    }

    public Boolean isProviderResponseSet() {
        return Boolean.valueOf(this.mProviderResponse != null || this.mProviderResponseSet.booleanValue());
    }

    public void invokeCallbackWithError(String str, String str2) {
        this.mCallbacks.onFinalErrorReceived(this.mComponentName, str, str2);
    }

    public boolean enforceRemoteEntryRestrictions(ComponentName componentName) {
        if (!this.mComponentName.equals(componentName)) {
            Slog.w("ProviderSession", "Remote entry being dropped as it is not from the service configured by the OEM.");
            return false;
        }
        try {
            ApplicationInfo applicationInfo = this.mContext.getPackageManager().getApplicationInfo(this.mComponentName.getPackageName(), PackageManager.ApplicationInfoFlags.of(1048576L));
            if (applicationInfo != null) {
                if (this.mContext.checkPermission("android.permission.PROVIDE_REMOTE_CREDENTIALS", -1, applicationInfo.uid) == 0) {
                    return true;
                }
            }
            return false;
        } catch (PackageManager.NameNotFoundException | SecurityException e) {
            Slog.e("ProviderSession", "Error getting info for " + this.mComponentName.flattenToString(), e);
            return false;
        }
    }
}
