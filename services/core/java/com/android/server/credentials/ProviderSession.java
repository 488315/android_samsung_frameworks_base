package com.android.server.credentials;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.credentials.selection.ProviderData;
import android.credentials.selection.ProviderPendingIntentResponse;
import android.os.ICancellationSignal;
import android.util.Slog;
import com.android.server.PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0;
import com.android.server.credentials.RemoteCredentialService;
import com.android.server.credentials.metrics.BrowsedAuthenticationMetric;
import com.android.server.credentials.metrics.CandidatePhaseMetric;
import com.android.server.credentials.metrics.InitialPhaseMetric;
import com.android.server.credentials.metrics.ProviderSessionMetric;
import com.android.server.credentials.metrics.ProviderStatusForMetrics;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class ProviderSession implements RemoteCredentialService.ProviderCallbacks {
    public final ProviderInternalCallback mCallbacks;
    public final ComponentName mComponentName;
    public final Context mContext;
    public ICancellationSignal mProviderCancellationSignal;
    public final Object mProviderRequest;
    public Object mProviderResponse;
    public final ProviderSessionMetric mProviderSessionMetric;
    public final int mProviderSessionUid;
    public final RemoteCredentialService mRemoteCredentialService;
    public Status mStatus = Status.NOT_STARTED;
    public Boolean mProviderResponseSet = Boolean.FALSE;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class CredentialsSource {
        public static final /* synthetic */ CredentialsSource[] $VALUES;
        public static final CredentialsSource AUTH_ENTRY;
        public static final CredentialsSource REGISTRY;
        public static final CredentialsSource REMOTE_PROVIDER;

        static {
            CredentialsSource credentialsSource = new CredentialsSource("REMOTE_PROVIDER", 0);
            REMOTE_PROVIDER = credentialsSource;
            CredentialsSource credentialsSource2 = new CredentialsSource("REGISTRY", 1);
            REGISTRY = credentialsSource2;
            CredentialsSource credentialsSource3 = new CredentialsSource("AUTH_ENTRY", 2);
            AUTH_ENTRY = credentialsSource3;
            $VALUES = new CredentialsSource[]{credentialsSource, credentialsSource2, credentialsSource3};
        }

        public static CredentialsSource valueOf(String str) {
            return (CredentialsSource) Enum.valueOf(CredentialsSource.class, str);
        }

        public static CredentialsSource[] values() {
            return (CredentialsSource[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface ProviderInternalCallback {
        void onFinalErrorReceived(String str, String str2);

        void onFinalResponseReceived(ComponentName componentName, Object obj);

        void onProviderStatusChanged(Status status, ComponentName componentName, CredentialsSource credentialsSource);
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class Status {
        public static final /* synthetic */ Status[] $VALUES;
        public static final Status CANCELED;
        public static final Status COMPLETE;
        public static final Status CREDENTIALS_RECEIVED;
        public static final Status EMPTY_RESPONSE;
        public static final Status NOT_STARTED;
        public static final Status NO_CREDENTIALS_FROM_AUTH_ENTRY;
        public static final Status PENDING;
        public static final Status SAVE_ENTRIES_RECEIVED;
        public static final Status SERVICE_DEAD;

        static {
            Status status = new Status("NOT_STARTED", 0);
            NOT_STARTED = status;
            Status status2 = new Status("PENDING", 1);
            PENDING = status2;
            Status status3 = new Status("CREDENTIALS_RECEIVED", 2);
            CREDENTIALS_RECEIVED = status3;
            Status status4 = new Status("SERVICE_DEAD", 3);
            SERVICE_DEAD = status4;
            Status status5 = new Status("SAVE_ENTRIES_RECEIVED", 4);
            SAVE_ENTRIES_RECEIVED = status5;
            Status status6 = new Status("CANCELED", 5);
            CANCELED = status6;
            Status status7 = new Status("EMPTY_RESPONSE", 6);
            EMPTY_RESPONSE = status7;
            Status status8 = new Status("NO_CREDENTIALS_FROM_AUTH_ENTRY", 7);
            NO_CREDENTIALS_FROM_AUTH_ENTRY = status8;
            Status status9 = new Status("COMPLETE", 8);
            COMPLETE = status9;
            $VALUES = new Status[]{status, status2, status3, status4, status5, status6, status7, status8, status9};
        }

        public static Status valueOf(String str) {
            return (Status) Enum.valueOf(Status.class, str);
        }

        public static Status[] values() {
            return (Status[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ProviderSession(Context context, Object obj, ProviderInternalCallback providerInternalCallback, ComponentName componentName, int i, RemoteCredentialService remoteCredentialService) {
        this.mContext = context;
        this.mProviderRequest = obj;
        this.mCallbacks = providerInternalCallback;
        this.mComponentName = componentName;
        this.mRemoteCredentialService = remoteCredentialService;
        this.mProviderSessionUid = componentName == null ? -1 : MetricUtilities.getPackageUid(context, componentName.getPackageName(), i);
        this.mProviderSessionMetric = new ProviderSessionMetric(((RequestSession) providerInternalCallback).mRequestSessionMetric.mSessionIdTrackTwo);
    }

    public static String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

    public static boolean isUiInvokingStatus(Status status) {
        return status == Status.CREDENTIALS_RECEIVED || status == Status.SAVE_ENTRIES_RECEIVED || status == Status.NO_CREDENTIALS_FROM_AUTH_ENTRY;
    }

    public final boolean enforceRemoteEntryRestrictions(ComponentName componentName) {
        if (!this.mComponentName.equals(componentName)) {
            Slog.w("CredentialManager", "Remote entry being dropped as it is not from the service configured by the OEM.");
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
            Slog.e("CredentialManager", "Error getting info for " + this.mComponentName.flattenToString(), e);
            return false;
        }
    }

    public abstract void invokeSession();

    public abstract void onUiEntrySelected(String str, String str2, ProviderPendingIntentResponse providerPendingIntentResponse);

    public abstract ProviderData prepareUiData();

    public final void startCandidateMetrics() {
        InitialPhaseMetric initialPhaseMetric = ((RequestSession) this.mCallbacks).mRequestSessionMetric.mInitialPhaseMetric;
        ProviderSessionMetric providerSessionMetric = this.mProviderSessionMetric;
        providerSessionMetric.getClass();
        try {
            CandidatePhaseMetric candidatePhaseMetric = providerSessionMetric.mCandidatePhasePerProviderMetric;
            candidatePhaseMetric.mServiceBeganTimeNanoseconds = initialPhaseMetric.mCredentialServiceStartedTimeNanoseconds;
            candidatePhaseMetric.mStartQueryTimeNanoseconds = System.nanoTime();
        } catch (Exception e) {
            PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0.m(e, "Unexpected error during candidate setup metric logging: ", "ProviderSessionMetric");
        }
    }

    public final void updateStatusAndInvokeCallback(Status status, CredentialsSource credentialsSource) {
        this.mStatus = status;
        boolean z = (status == Status.CANCELED || status == Status.SERVICE_DEAD) || status == Status.PENDING;
        boolean z2 = status == Status.COMPLETE || status == Status.EMPTY_RESPONSE || isUiInvokingStatus(status);
        boolean z3 = credentialsSource == CredentialsSource.AUTH_ENTRY;
        ProviderSessionMetric providerSessionMetric = this.mProviderSessionMetric;
        CandidatePhaseMetric candidatePhaseMetric = providerSessionMetric.mCandidatePhasePerProviderMetric;
        ProviderStatusForMetrics providerStatusForMetrics = ProviderStatusForMetrics.QUERY_SUCCESS;
        ProviderStatusForMetrics providerStatusForMetrics2 = ProviderStatusForMetrics.QUERY_FAILURE;
        int i = this.mProviderSessionUid;
        try {
            if (z3) {
                List list = providerSessionMetric.mBrowsedAuthenticationMetric;
                BrowsedAuthenticationMetric browsedAuthenticationMetric = (BrowsedAuthenticationMetric) ((ArrayList) list).get(((ArrayList) list).size() - 1);
                browsedAuthenticationMetric.mProviderUid = i;
                if (z) {
                    browsedAuthenticationMetric.mAuthReturned = false;
                    browsedAuthenticationMetric.mProviderStatus = providerStatusForMetrics2.getMetricCode();
                } else if (z2) {
                    browsedAuthenticationMetric.mAuthReturned = true;
                    browsedAuthenticationMetric.mProviderStatus = providerStatusForMetrics.getMetricCode();
                }
            } else {
                candidatePhaseMetric.mIsPrimary = false;
                candidatePhaseMetric.mCandidateUid = i;
                candidatePhaseMetric.mQueryFinishTimeNanoseconds = System.nanoTime();
                if (z) {
                    candidatePhaseMetric.mQueryReturned = false;
                    candidatePhaseMetric.mProviderQueryStatus = providerStatusForMetrics2.getMetricCode();
                } else if (z2) {
                    candidatePhaseMetric.mQueryReturned = true;
                    candidatePhaseMetric.mProviderQueryStatus = providerStatusForMetrics.getMetricCode();
                }
            }
        } catch (Exception e) {
            PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0.m(e, "Unexpected error during candidate update metric logging: ", "ProviderSessionMetric");
        }
        this.mCallbacks.onProviderStatusChanged(status, this.mComponentName, credentialsSource);
    }
}
