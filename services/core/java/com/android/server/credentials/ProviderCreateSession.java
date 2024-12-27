package com.android.server.credentials;

import android.app.slice.Slice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.credentials.CreateCredentialException;
import android.credentials.CreateCredentialResponse;
import android.credentials.CredentialProviderInfo;
import android.credentials.selection.CreateCredentialProviderData;
import android.credentials.selection.Entry;
import android.credentials.selection.ProviderData;
import android.credentials.selection.ProviderPendingIntentResponse;
import android.os.ICancellationSignal;
import android.service.credentials.BeginCreateCredentialRequest;
import android.service.credentials.BeginCreateCredentialResponse;
import android.service.credentials.CreateCredentialRequest;
import android.service.credentials.CreateEntry;
import android.service.credentials.RemoteEntry;
import android.util.Pair;
import android.util.Slog;
import com.android.server.credentials.ProviderCreateSession;
import com.android.server.credentials.ProviderSession;
import com.android.server.credentials.metrics.ProviderSessionMetric;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ProviderCreateSession extends ProviderSession {
    public final CreateCredentialRequest mCompleteRequest;
    public final ProviderResponseDataHandler mProviderResponseDataHandler;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ProviderResponseDataHandler {
        public final ComponentName mExpectedRemoteEntryProviderService;
        public final Map mUiCreateEntries = new HashMap();
        public Pair mUiRemoteEntry = null;

        public ProviderResponseDataHandler(ComponentName componentName) {
            this.mExpectedRemoteEntryProviderService = componentName;
        }
    }

    public ProviderCreateSession(Context context, CredentialProviderInfo credentialProviderInfo, ProviderSession.ProviderInternalCallback providerInternalCallback, int i, RemoteCredentialService remoteCredentialService, BeginCreateCredentialRequest beginCreateCredentialRequest, CreateCredentialRequest createCredentialRequest, String str) {
        super(context, beginCreateCredentialRequest, providerInternalCallback, credentialProviderInfo.getComponentName(), i, remoteCredentialService);
        this.mCompleteRequest = createCredentialRequest;
        this.mStatus = ProviderSession.Status.PENDING;
        this.mProviderResponseDataHandler = new ProviderResponseDataHandler(ComponentName.unflattenFromString(str));
    }

    @Override // com.android.server.credentials.ProviderSession
    public final void invokeSession() {
        RemoteCredentialService remoteCredentialService = this.mRemoteCredentialService;
        if (remoteCredentialService != null) {
            startCandidateMetrics();
            remoteCredentialService.setCallback(this);
            remoteCredentialService.onBeginCreateCredential((BeginCreateCredentialRequest) this.mProviderRequest);
        }
    }

    public final void onCreateEntrySelected(ProviderPendingIntentResponse providerPendingIntentResponse) {
        CreateCredentialException createCredentialException;
        if (providerPendingIntentResponse == null) {
            Slog.i("CredentialManager", "pendingIntentResponse is null");
            createCredentialException = new CreateCredentialException("android.credentials.CreateCredentialException.TYPE_NO_CREATE_OPTIONS");
        } else if (providerPendingIntentResponse.getResultCode() == -1) {
            Intent resultData = providerPendingIntentResponse.getResultData();
            createCredentialException = resultData == null ? null : (CreateCredentialException) resultData.getSerializableExtra("android.service.credentials.extra.CREATE_CREDENTIAL_EXCEPTION", CreateCredentialException.class);
            if (createCredentialException != null) {
                Slog.i("CredentialManager", "Pending intent contains provider exception");
            } else {
                createCredentialException = null;
            }
        } else {
            createCredentialException = providerPendingIntentResponse.getResultCode() == 0 ? new CreateCredentialException("android.credentials.CreateCredentialException.TYPE_USER_CANCELED") : new CreateCredentialException("android.credentials.CreateCredentialException.TYPE_NO_CREATE_OPTIONS");
        }
        ProviderSession.ProviderInternalCallback providerInternalCallback = this.mCallbacks;
        if (createCredentialException != null) {
            providerInternalCallback.onFinalErrorReceived(createCredentialException.getType(), createCredentialException.getMessage());
            return;
        }
        Intent resultData2 = providerPendingIntentResponse.getResultData();
        CreateCredentialResponse createCredentialResponse = resultData2 == null ? null : (CreateCredentialResponse) resultData2.getParcelableExtra("android.service.credentials.extra.CREATE_CREDENTIAL_RESPONSE", CreateCredentialResponse.class);
        if (createCredentialResponse != null) {
            providerInternalCallback.onFinalResponseReceived(this.mComponentName, createCredentialResponse);
        } else {
            Slog.i("CredentialManager", "onSaveEntrySelected - no response or error found in pending intent response");
            providerInternalCallback.onFinalErrorReceived("android.credentials.CreateCredentialException.TYPE_UNKNOWN", null);
        }
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public final void onProviderCancellable(ICancellationSignal iCancellationSignal) {
        this.mProviderCancellationSignal = iCancellationSignal;
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public final void onProviderResponseFailure(Exception exc) {
        boolean z = exc instanceof CreateCredentialException;
        ProviderSessionMetric providerSessionMetric = this.mProviderSessionMetric;
        if (z) {
            providerSessionMetric.collectCandidateFrameworkException(((CreateCredentialException) exc).getType());
        }
        providerSessionMetric.collectCandidateExceptionStatus();
        updateStatusAndInvokeCallback(ProviderSession.Status.CANCELED, ProviderSession.CredentialsSource.REMOTE_PROVIDER);
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public final void onProviderResponseSuccess(Object obj) {
        BeginCreateCredentialResponse beginCreateCredentialResponse = (BeginCreateCredentialResponse) obj;
        Slog.i("CredentialManager", "Remote provider responded with a valid response: " + this.mComponentName);
        this.mProviderResponse = beginCreateCredentialResponse;
        List<CreateEntry> createEntries = beginCreateCredentialResponse.getCreateEntries();
        RemoteEntry remoteCreateEntry = beginCreateCredentialResponse.getRemoteCreateEntry();
        final ProviderResponseDataHandler providerResponseDataHandler = this.mProviderResponseDataHandler;
        providerResponseDataHandler.getClass();
        createEntries.forEach(new Consumer() { // from class: com.android.server.credentials.ProviderCreateSession$ProviderResponseDataHandler$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj2) {
                ProviderCreateSession.ProviderResponseDataHandler providerResponseDataHandler2 = ProviderCreateSession.ProviderResponseDataHandler.this;
                CreateEntry createEntry = (CreateEntry) obj2;
                providerResponseDataHandler2.getClass();
                String generateUniqueId = ProviderSession.generateUniqueId();
                Slice slice = createEntry.getSlice();
                ProviderCreateSession providerCreateSession = ProviderCreateSession.this;
                providerCreateSession.getClass();
                Intent intent = new Intent();
                intent.putExtra("android.service.credentials.extra.CREATE_CREDENTIAL_REQUEST", providerCreateSession.mCompleteRequest);
                Entry entry = new Entry("save_entry_key", generateUniqueId, slice, intent);
                ((HashMap) providerResponseDataHandler2.mUiCreateEntries).put(generateUniqueId, new Pair(createEntry, entry));
            }
        });
        if (remoteCreateEntry != null) {
            ComponentName componentName = providerResponseDataHandler.mExpectedRemoteEntryProviderService;
            ProviderCreateSession providerCreateSession = ProviderCreateSession.this;
            if (providerCreateSession.enforceRemoteEntryRestrictions(componentName)) {
                String generateUniqueId = ProviderSession.generateUniqueId();
                Slice slice = remoteCreateEntry.getSlice();
                Intent intent = new Intent();
                intent.putExtra("android.service.credentials.extra.CREATE_CREDENTIAL_REQUEST", providerCreateSession.mCompleteRequest);
                providerResponseDataHandler.mUiRemoteEntry = new Pair(generateUniqueId, new Pair(remoteCreateEntry, new Entry("remote_entry_key", generateUniqueId, slice, intent)));
            } else {
                Slog.w("CredentialManager", "Remote entry being dropped as it does not meet the restrictionchecks.");
            }
        }
        List<CreateEntry> createEntries2 = beginCreateCredentialResponse.getCreateEntries();
        ProviderSession.CredentialsSource credentialsSource = ProviderSession.CredentialsSource.REMOTE_PROVIDER;
        Object obj2 = this.mCallbacks;
        ProviderSessionMetric providerSessionMetric = this.mProviderSessionMetric;
        if ((createEntries2 == null || beginCreateCredentialResponse.getCreateEntries().isEmpty()) && beginCreateCredentialResponse.getRemoteCreateEntry() == null) {
            providerSessionMetric.collectCandidateEntryMetrics(beginCreateCredentialResponse, false, ((RequestSession) obj2).mRequestSessionMetric.mInitialPhaseMetric);
            updateStatusAndInvokeCallback(ProviderSession.Status.EMPTY_RESPONSE, credentialsSource);
        } else {
            providerSessionMetric.collectCandidateEntryMetrics(beginCreateCredentialResponse, false, ((RequestSession) obj2).mRequestSessionMetric.mInitialPhaseMetric);
            updateStatusAndInvokeCallback(ProviderSession.Status.SAVE_ENTRIES_RECEIVED, credentialsSource);
        }
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public final void onProviderServiceDied(RemoteCredentialService remoteCredentialService) {
        if (remoteCredentialService.getComponentName().equals(this.mComponentName)) {
            updateStatusAndInvokeCallback(ProviderSession.Status.SERVICE_DEAD, ProviderSession.CredentialsSource.REMOTE_PROVIDER);
        } else {
            Slog.w("CredentialManager", "Component names different in onProviderServiceDied - this should not happen");
        }
    }

    @Override // com.android.server.credentials.ProviderSession
    public final void onUiEntrySelected(String str, String str2, ProviderPendingIntentResponse providerPendingIntentResponse) {
        Object obj;
        Object obj2;
        str.getClass();
        ProviderSession.ProviderInternalCallback providerInternalCallback = this.mCallbacks;
        ProviderResponseDataHandler providerResponseDataHandler = this.mProviderResponseDataHandler;
        if (str.equals("save_entry_key")) {
            if ((((HashMap) providerResponseDataHandler.mUiCreateEntries).get(str2) == null ? null : (CreateEntry) ((Pair) ((HashMap) providerResponseDataHandler.mUiCreateEntries).get(str2)).first) != null) {
                onCreateEntrySelected(providerPendingIntentResponse);
                return;
            } else {
                Slog.i("CredentialManager", "Unexpected save entry key");
                providerInternalCallback.onFinalErrorReceived("android.credentials.CreateCredentialException.TYPE_UNKNOWN", null);
                return;
            }
        }
        if (!str.equals("remote_entry_key")) {
            Slog.i("CredentialManager", "Unsupported entry type selected");
            providerInternalCallback.onFinalErrorReceived("android.credentials.CreateCredentialException.TYPE_UNKNOWN", null);
            return;
        }
        Pair pair = providerResponseDataHandler.mUiRemoteEntry;
        if (((pair == null || (obj = pair.first) == null || !((String) obj).equals(str2) || (obj2 = providerResponseDataHandler.mUiRemoteEntry.second) == null) ? null : (RemoteEntry) ((Pair) obj2).first) != null) {
            onCreateEntrySelected(providerPendingIntentResponse);
        } else {
            Slog.i("CredentialManager", "Unexpected remote entry key");
            providerInternalCallback.onFinalErrorReceived("android.credentials.CreateCredentialException.TYPE_UNKNOWN", null);
        }
    }

    @Override // com.android.server.credentials.ProviderSession
    public final ProviderData prepareUiData() {
        Object obj;
        Entry entry = null;
        if (!ProviderSession.isUiInvokingStatus(this.mStatus)) {
            Slog.i("CredentialManager", "No data for UI from: " + this.mComponentName.flattenToString());
            return null;
        }
        if (this.mProviderResponse == null) {
            return null;
        }
        ProviderResponseDataHandler providerResponseDataHandler = this.mProviderResponseDataHandler;
        if (((HashMap) providerResponseDataHandler.mUiCreateEntries).isEmpty() && providerResponseDataHandler.mUiRemoteEntry == null) {
            return null;
        }
        CreateCredentialProviderData.Builder builder = new CreateCredentialProviderData.Builder(ProviderCreateSession.this.mComponentName.flattenToString());
        ArrayList arrayList = new ArrayList();
        Iterator it = ((HashMap) providerResponseDataHandler.mUiCreateEntries).keySet().iterator();
        while (it.hasNext()) {
            arrayList.add((Entry) ((Pair) ((HashMap) providerResponseDataHandler.mUiCreateEntries).get((String) it.next())).second);
        }
        CreateCredentialProviderData.Builder saveEntries = builder.setSaveEntries(arrayList);
        Pair pair = providerResponseDataHandler.mUiRemoteEntry;
        if (pair != null && pair.first != null && (obj = pair.second) != null) {
            entry = (Entry) ((Pair) obj).second;
        }
        return saveEntries.setRemoteEntry(entry).build();
    }
}
