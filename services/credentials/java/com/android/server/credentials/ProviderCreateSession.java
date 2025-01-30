package com.android.server.credentials;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.credentials.CreateCredentialException;
import android.credentials.CreateCredentialResponse;
import android.credentials.CredentialProviderInfo;
import android.credentials.ui.CreateCredentialProviderData;
import android.credentials.ui.Entry;
import android.credentials.ui.ProviderPendingIntentResponse;
import android.os.Bundle;
import android.os.ICancellationSignal;
import android.service.credentials.BeginCreateCredentialRequest;
import android.service.credentials.BeginCreateCredentialResponse;
import android.service.credentials.CallingAppInfo;
import android.service.credentials.CreateCredentialRequest;
import android.service.credentials.CreateEntry;
import android.service.credentials.RemoteEntry;
import android.util.Pair;
import android.util.Slog;
import com.android.server.credentials.ProviderCreateSession;
import com.android.server.credentials.ProviderSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final class ProviderCreateSession extends ProviderSession {
    public final CreateCredentialRequest mCompleteRequest;
    public CreateCredentialException mProviderException;
    public final ProviderResponseDataHandler mProviderResponseDataHandler;

    public static ProviderCreateSession createNewSession(Context context, int i, CredentialProviderInfo credentialProviderInfo, CreateRequestSession createRequestSession, RemoteCredentialService remoteCredentialService) {
        CreateCredentialRequest createProviderRequest = createProviderRequest(credentialProviderInfo.getCapabilities(), (android.credentials.CreateCredentialRequest) createRequestSession.mClientRequest, createRequestSession.mClientAppInfo, credentialProviderInfo.isSystemProvider());
        if (createProviderRequest != null) {
            return new ProviderCreateSession(context, credentialProviderInfo, createRequestSession, i, remoteCredentialService, constructQueryPhaseRequest(((android.credentials.CreateCredentialRequest) createRequestSession.mClientRequest).getType(), ((android.credentials.CreateCredentialRequest) createRequestSession.mClientRequest).getCandidateQueryData(), createRequestSession.mClientAppInfo, ((android.credentials.CreateCredentialRequest) createRequestSession.mClientRequest).alwaysSendAppInfoToProvider()), createProviderRequest, createRequestSession.mHybridService);
        }
        Slog.i("ProviderCreateSession", "Unable to create provider session for: " + credentialProviderInfo.getComponentName());
        return null;
    }

    public static BeginCreateCredentialRequest constructQueryPhaseRequest(String str, Bundle bundle, CallingAppInfo callingAppInfo, boolean z) {
        if (z) {
            return new BeginCreateCredentialRequest(str, bundle, callingAppInfo);
        }
        return new BeginCreateCredentialRequest(str, bundle);
    }

    public static CreateCredentialRequest createProviderRequest(List list, android.credentials.CreateCredentialRequest createCredentialRequest, CallingAppInfo callingAppInfo, boolean z) {
        if (createCredentialRequest.isSystemProviderRequired() && !z) {
            return null;
        }
        String type = createCredentialRequest.getType();
        if (list.contains(type)) {
            return new CreateCredentialRequest(callingAppInfo, type, createCredentialRequest.getCredentialData());
        }
        return null;
    }

    public ProviderCreateSession(Context context, CredentialProviderInfo credentialProviderInfo, ProviderSession.ProviderInternalCallback providerInternalCallback, int i, RemoteCredentialService remoteCredentialService, BeginCreateCredentialRequest beginCreateCredentialRequest, CreateCredentialRequest createCredentialRequest, String str) {
        super(context, beginCreateCredentialRequest, providerInternalCallback, credentialProviderInfo.getComponentName(), i, remoteCredentialService);
        this.mCompleteRequest = createCredentialRequest;
        setStatus(ProviderSession.Status.PENDING);
        this.mProviderResponseDataHandler = new ProviderResponseDataHandler(ComponentName.unflattenFromString(str));
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public void onProviderResponseSuccess(BeginCreateCredentialResponse beginCreateCredentialResponse) {
        Slog.i("ProviderCreateSession", "Remote provider responded with a valid response: " + this.mComponentName);
        onSetInitialRemoteResponse(beginCreateCredentialResponse);
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public void onProviderResponseFailure(int i, Exception exc) {
        if (exc instanceof CreateCredentialException) {
            CreateCredentialException createCredentialException = (CreateCredentialException) exc;
            this.mProviderException = createCredentialException;
            this.mProviderSessionMetric.collectCandidateFrameworkException(createCredentialException.getType());
        }
        this.mProviderSessionMetric.collectCandidateExceptionStatus(true);
        updateStatusAndInvokeCallback(ProviderSession.Status.CANCELED, ProviderSession.CredentialsSource.REMOTE_PROVIDER);
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public void onProviderServiceDied(RemoteCredentialService remoteCredentialService) {
        if (remoteCredentialService.getComponentName().equals(this.mComponentName)) {
            updateStatusAndInvokeCallback(ProviderSession.Status.SERVICE_DEAD, ProviderSession.CredentialsSource.REMOTE_PROVIDER);
        } else {
            Slog.w("ProviderCreateSession", "Component names different in onProviderServiceDied - this should not happen");
        }
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public void onProviderCancellable(ICancellationSignal iCancellationSignal) {
        this.mProviderCancellationSignal = iCancellationSignal;
    }

    public final void onSetInitialRemoteResponse(BeginCreateCredentialResponse beginCreateCredentialResponse) {
        this.mProviderResponse = beginCreateCredentialResponse;
        this.mProviderResponseDataHandler.addResponseContent(beginCreateCredentialResponse.getCreateEntries(), beginCreateCredentialResponse.getRemoteCreateEntry());
        if (this.mProviderResponseDataHandler.isEmptyResponse(beginCreateCredentialResponse)) {
            this.mProviderSessionMetric.collectCandidateEntryMetrics(beginCreateCredentialResponse, false, ((RequestSession) this.mCallbacks).mRequestSessionMetric.getInitialPhaseMetric());
            updateStatusAndInvokeCallback(ProviderSession.Status.EMPTY_RESPONSE, ProviderSession.CredentialsSource.REMOTE_PROVIDER);
        } else {
            this.mProviderSessionMetric.collectCandidateEntryMetrics(beginCreateCredentialResponse, false, ((RequestSession) this.mCallbacks).mRequestSessionMetric.getInitialPhaseMetric());
            updateStatusAndInvokeCallback(ProviderSession.Status.SAVE_ENTRIES_RECEIVED, ProviderSession.CredentialsSource.REMOTE_PROVIDER);
        }
    }

    @Override // com.android.server.credentials.ProviderSession
    /* renamed from: prepareUiData, reason: merged with bridge method [inline-methods] */
    public CreateCredentialProviderData mo4615prepareUiData() {
        if (!ProviderSession.isUiInvokingStatus(getStatus())) {
            Slog.i("ProviderCreateSession", "No data for UI from: " + this.mComponentName.flattenToString());
            return null;
        }
        if (this.mProviderResponse == null || this.mProviderResponseDataHandler.isEmptyResponse()) {
            return null;
        }
        return this.mProviderResponseDataHandler.toCreateCredentialProviderData();
    }

    @Override // com.android.server.credentials.ProviderSession
    public void onUiEntrySelected(String str, String str2, ProviderPendingIntentResponse providerPendingIntentResponse) {
        str.hashCode();
        if (str.equals("save_entry_key")) {
            if (this.mProviderResponseDataHandler.getCreateEntry(str2) == null) {
                Slog.i("ProviderCreateSession", "Unexpected save entry key");
                invokeCallbackOnInternalInvalidState();
                return;
            } else {
                onCreateEntrySelected(providerPendingIntentResponse);
                return;
            }
        }
        if (str.equals("remote_entry_key")) {
            if (this.mProviderResponseDataHandler.getRemoteEntry(str2) == null) {
                Slog.i("ProviderCreateSession", "Unexpected remote entry key");
                invokeCallbackOnInternalInvalidState();
                return;
            } else {
                onRemoteEntrySelected(providerPendingIntentResponse);
                return;
            }
        }
        Slog.i("ProviderCreateSession", "Unsupported entry type selected");
        invokeCallbackOnInternalInvalidState();
    }

    @Override // com.android.server.credentials.ProviderSession
    public void invokeSession() {
        if (this.mRemoteCredentialService != null) {
            startCandidateMetrics();
            this.mRemoteCredentialService.setCallback(this);
            this.mRemoteCredentialService.onBeginCreateCredential((BeginCreateCredentialRequest) this.mProviderRequest);
        }
    }

    public final Intent setUpFillInIntent() {
        Intent intent = new Intent();
        intent.putExtra("android.service.credentials.extra.CREATE_CREDENTIAL_REQUEST", this.mCompleteRequest);
        return intent;
    }

    public final void onCreateEntrySelected(ProviderPendingIntentResponse providerPendingIntentResponse) {
        CreateCredentialException maybeGetPendingIntentException = maybeGetPendingIntentException(providerPendingIntentResponse);
        if (maybeGetPendingIntentException != null) {
            invokeCallbackWithError(maybeGetPendingIntentException.getType(), maybeGetPendingIntentException.getMessage());
            return;
        }
        CreateCredentialResponse extractCreateCredentialResponse = PendingIntentResultHandler.extractCreateCredentialResponse(providerPendingIntentResponse.getResultData());
        if (extractCreateCredentialResponse != null) {
            this.mCallbacks.onFinalResponseReceived(this.mComponentName, extractCreateCredentialResponse);
        } else {
            Slog.i("ProviderCreateSession", "onSaveEntrySelected - no response or error found in pending intent response");
            invokeCallbackOnInternalInvalidState();
        }
    }

    public final void onRemoteEntrySelected(ProviderPendingIntentResponse providerPendingIntentResponse) {
        onCreateEntrySelected(providerPendingIntentResponse);
    }

    public final CreateCredentialException maybeGetPendingIntentException(ProviderPendingIntentResponse providerPendingIntentResponse) {
        if (providerPendingIntentResponse == null) {
            Slog.i("ProviderCreateSession", "pendingIntentResponse is null");
            return new CreateCredentialException("android.credentials.CreateCredentialException.TYPE_NO_CREATE_OPTIONS");
        }
        if (PendingIntentResultHandler.isValidResponse(providerPendingIntentResponse)) {
            CreateCredentialException extractCreateCredentialException = PendingIntentResultHandler.extractCreateCredentialException(providerPendingIntentResponse.getResultData());
            if (extractCreateCredentialException == null) {
                return null;
            }
            Slog.i("ProviderCreateSession", "Pending intent contains provider exception");
            return extractCreateCredentialException;
        }
        if (PendingIntentResultHandler.isCancelledResponse(providerPendingIntentResponse)) {
            return new CreateCredentialException("android.credentials.CreateCredentialException.TYPE_USER_CANCELED");
        }
        return new CreateCredentialException("android.credentials.CreateCredentialException.TYPE_NO_CREATE_OPTIONS");
    }

    public final void invokeCallbackOnInternalInvalidState() {
        this.mCallbacks.onFinalErrorReceived(this.mComponentName, "android.credentials.CreateCredentialException.TYPE_UNKNOWN", null);
    }

    public class ProviderResponseDataHandler {
        public final ComponentName mExpectedRemoteEntryProviderService;
        public final Map mUiCreateEntries = new HashMap();
        public Pair mUiRemoteEntry = null;

        public ProviderResponseDataHandler(ComponentName componentName) {
            this.mExpectedRemoteEntryProviderService = componentName;
        }

        public void addResponseContent(List list, RemoteEntry remoteEntry) {
            list.forEach(new Consumer() { // from class: com.android.server.credentials.ProviderCreateSession$ProviderResponseDataHandler$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ProviderCreateSession.ProviderResponseDataHandler.this.addCreateEntry((CreateEntry) obj);
                }
            });
            if (remoteEntry != null) {
                setRemoteEntry(remoteEntry);
            }
        }

        public void addCreateEntry(CreateEntry createEntry) {
            String generateUniqueId = ProviderSession.generateUniqueId();
            this.mUiCreateEntries.put(generateUniqueId, new Pair(createEntry, new Entry("save_entry_key", generateUniqueId, createEntry.getSlice(), ProviderCreateSession.this.setUpFillInIntent())));
        }

        public void setRemoteEntry(RemoteEntry remoteEntry) {
            if (!ProviderCreateSession.this.enforceRemoteEntryRestrictions(this.mExpectedRemoteEntryProviderService)) {
                Slog.w("ProviderCreateSession", "Remote entry being dropped as it does not meet the restrictionchecks.");
            } else if (remoteEntry == null) {
                this.mUiRemoteEntry = null;
            } else {
                String generateUniqueId = ProviderSession.generateUniqueId();
                this.mUiRemoteEntry = new Pair(generateUniqueId, new Pair(remoteEntry, new Entry("remote_entry_key", generateUniqueId, remoteEntry.getSlice(), ProviderCreateSession.this.setUpFillInIntent())));
            }
        }

        public CreateCredentialProviderData toCreateCredentialProviderData() {
            return new CreateCredentialProviderData.Builder(ProviderCreateSession.this.mComponentName.flattenToString()).setSaveEntries(prepareUiCreateEntries()).setRemoteEntry(prepareRemoteEntry()).build();
        }

        public final List prepareUiCreateEntries() {
            ArrayList arrayList = new ArrayList();
            Iterator it = this.mUiCreateEntries.keySet().iterator();
            while (it.hasNext()) {
                arrayList.add((Entry) ((Pair) this.mUiCreateEntries.get((String) it.next())).second);
            }
            return arrayList;
        }

        public final Entry prepareRemoteEntry() {
            Object obj;
            Pair pair = this.mUiRemoteEntry;
            if (pair == null || pair.first == null || (obj = pair.second) == null) {
                return null;
            }
            return (Entry) ((Pair) obj).second;
        }

        public final boolean isEmptyResponse() {
            return this.mUiCreateEntries.isEmpty() && this.mUiRemoteEntry == null;
        }

        public RemoteEntry getRemoteEntry(String str) {
            Object obj;
            Object obj2;
            Pair pair = this.mUiRemoteEntry;
            if (pair == null || (obj = pair.first) == null || !((String) obj).equals(str) || (obj2 = this.mUiRemoteEntry.second) == null) {
                return null;
            }
            return (RemoteEntry) ((Pair) obj2).first;
        }

        public CreateEntry getCreateEntry(String str) {
            if (this.mUiCreateEntries.get(str) == null) {
                return null;
            }
            return (CreateEntry) ((Pair) this.mUiCreateEntries.get(str)).first;
        }

        public boolean isEmptyResponse(BeginCreateCredentialResponse beginCreateCredentialResponse) {
            return (beginCreateCredentialResponse.getCreateEntries() == null || beginCreateCredentialResponse.getCreateEntries().isEmpty()) && beginCreateCredentialResponse.getRemoteCreateEntry() == null;
        }
    }
}
