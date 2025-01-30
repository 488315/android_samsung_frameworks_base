package com.android.server.credentials;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.credentials.CredentialOption;
import android.credentials.GetCredentialException;
import android.credentials.GetCredentialResponse;
import android.credentials.ui.Entry;
import android.credentials.ui.GetCredentialProviderData;
import android.credentials.ui.ProviderData;
import android.credentials.ui.ProviderPendingIntentResponse;
import android.os.ICancellationSignal;
import android.service.credentials.CallingAppInfo;
import android.service.credentials.CredentialEntry;
import android.service.credentials.GetCredentialRequest;
import android.util.Slog;
import com.android.server.credentials.CredentialDescriptionRegistry;
import com.android.server.credentials.ProviderSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes.dex */
public class ProviderRegistryGetSession extends ProviderSession {
    static final String CREDENTIAL_ENTRY_KEY = "credential_key";
    public final CallingAppInfo mCallingAppInfo;
    public final CredentialDescriptionRegistry mCredentialDescriptionRegistry;
    List mCredentialEntries;
    public final String mCredentialProviderPackageName;
    public final Set mElementKeys;
    public final Map mUiCredentialEntries;

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public void onProviderCancellable(ICancellationSignal iCancellationSignal) {
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public void onProviderResponseFailure(int i, Exception exc) {
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public void onProviderResponseSuccess(Set set) {
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public void onProviderServiceDied(RemoteCredentialService remoteCredentialService) {
    }

    public static ProviderRegistryGetSession createNewSession(Context context, int i, GetRequestSession getRequestSession, CallingAppInfo callingAppInfo, String str, CredentialOption credentialOption) {
        return new ProviderRegistryGetSession(context, i, getRequestSession, callingAppInfo, str, credentialOption);
    }

    public ProviderRegistryGetSession(Context context, int i, GetRequestSession getRequestSession, CallingAppInfo callingAppInfo, String str, CredentialOption credentialOption) {
        super(context, credentialOption, getRequestSession, new ComponentName(str, UUID.randomUUID().toString()), i, null);
        this.mUiCredentialEntries = new HashMap();
        this.mCredentialDescriptionRegistry = CredentialDescriptionRegistry.forUser(i);
        this.mCallingAppInfo = callingAppInfo;
        this.mCredentialProviderPackageName = str;
        this.mElementKeys = new HashSet(credentialOption.getCredentialRetrievalData().getStringArrayList("android.credentials.GetCredentialOption.SUPPORTED_ELEMENT_KEYS"));
        this.mStatus = ProviderSession.Status.PENDING;
    }

    public final List prepareUiCredentialEntries(List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            CredentialEntry credentialEntry = (CredentialEntry) it.next();
            String generateUniqueId = ProviderSession.generateUniqueId();
            this.mUiCredentialEntries.put(generateUniqueId, credentialEntry);
            arrayList.add(new Entry(CREDENTIAL_ENTRY_KEY, generateUniqueId, credentialEntry.getSlice(), setUpFillInIntent()));
        }
        return arrayList;
    }

    public final Intent setUpFillInIntent() {
        Intent intent = new Intent();
        intent.putExtra("android.service.credentials.extra.GET_CREDENTIAL_REQUEST", new GetCredentialRequest(this.mCallingAppInfo, List.of((CredentialOption) this.mProviderRequest)));
        return intent;
    }

    @Override // com.android.server.credentials.ProviderSession
    /* renamed from: prepareUiData */
    public ProviderData mo4615prepareUiData() {
        if (!ProviderSession.isUiInvokingStatus(getStatus())) {
            Slog.i("ProviderRegistryGetSession", "No date for UI coming from: " + this.mComponentName.flattenToString());
            return null;
        }
        if (this.mProviderResponse == null) {
            Slog.w("ProviderRegistryGetSession", "response is null when preparing ui data. This is strange.");
            return null;
        }
        GetCredentialProviderData.Builder builder = new GetCredentialProviderData.Builder(this.mComponentName.flattenToString());
        List list = Collections.EMPTY_LIST;
        return builder.setActionChips(list).setAuthenticationEntries(list).setCredentialEntries(prepareUiCredentialEntries((List) ((Set) this.mProviderResponse).stream().flatMap(new Function() { // from class: com.android.server.credentials.ProviderRegistryGetSession$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Stream lambda$prepareUiData$0;
                lambda$prepareUiData$0 = ProviderRegistryGetSession.lambda$prepareUiData$0((CredentialDescriptionRegistry.FilterResult) obj);
                return lambda$prepareUiData$0;
            }
        }).collect(Collectors.toList()))).build();
    }

    public static /* synthetic */ Stream lambda$prepareUiData$0(CredentialDescriptionRegistry.FilterResult filterResult) {
        return filterResult.mCredentialEntries.stream();
    }

    @Override // com.android.server.credentials.ProviderSession
    public void onUiEntrySelected(String str, String str2, ProviderPendingIntentResponse providerPendingIntentResponse) {
        str.hashCode();
        if (str.equals(CREDENTIAL_ENTRY_KEY)) {
            CredentialEntry credentialEntry = (CredentialEntry) this.mUiCredentialEntries.get(str2);
            if (credentialEntry == null) {
                Slog.i("ProviderRegistryGetSession", "Unexpected credential entry key");
                return;
            } else {
                onCredentialEntrySelected(credentialEntry, providerPendingIntentResponse);
                return;
            }
        }
        Slog.i("ProviderRegistryGetSession", "Unsupported entry type selected");
    }

    public final void onCredentialEntrySelected(CredentialEntry credentialEntry, ProviderPendingIntentResponse providerPendingIntentResponse) {
        if (providerPendingIntentResponse != null) {
            GetCredentialException maybeGetPendingIntentException = maybeGetPendingIntentException(providerPendingIntentResponse);
            if (maybeGetPendingIntentException != null) {
                invokeCallbackWithError(maybeGetPendingIntentException.getType(), maybeGetPendingIntentException.getMessage());
                return;
            }
            GetCredentialResponse extractGetCredentialResponse = PendingIntentResultHandler.extractGetCredentialResponse(providerPendingIntentResponse.getResultData());
            if (extractGetCredentialResponse != null) {
                ProviderSession.ProviderInternalCallback providerInternalCallback = this.mCallbacks;
                if (providerInternalCallback != null) {
                    ((GetRequestSession) providerInternalCallback).onFinalResponseReceived(this.mComponentName, extractGetCredentialResponse);
                    return;
                }
                return;
            }
        }
        Slog.w("ProviderRegistryGetSession", "CredentialEntry does not have a credential or a pending intent result");
    }

    @Override // com.android.server.credentials.ProviderSession
    public void invokeSession() {
        startCandidateMetrics();
        Set filteredResultForProvider = this.mCredentialDescriptionRegistry.getFilteredResultForProvider(this.mCredentialProviderPackageName, this.mElementKeys);
        this.mProviderResponse = filteredResultForProvider;
        this.mCredentialEntries = (List) filteredResultForProvider.stream().flatMap(new Function() { // from class: com.android.server.credentials.ProviderRegistryGetSession$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Stream lambda$invokeSession$1;
                lambda$invokeSession$1 = ProviderRegistryGetSession.lambda$invokeSession$1((CredentialDescriptionRegistry.FilterResult) obj);
                return lambda$invokeSession$1;
            }
        }).collect(Collectors.toList());
        updateStatusAndInvokeCallback(ProviderSession.Status.CREDENTIALS_RECEIVED, ProviderSession.CredentialsSource.REGISTRY);
        this.mProviderSessionMetric.collectCandidateEntryMetrics(this.mCredentialEntries);
    }

    public static /* synthetic */ Stream lambda$invokeSession$1(CredentialDescriptionRegistry.FilterResult filterResult) {
        return filterResult.mCredentialEntries.stream();
    }

    public GetCredentialException maybeGetPendingIntentException(ProviderPendingIntentResponse providerPendingIntentResponse) {
        if (providerPendingIntentResponse == null) {
            return null;
        }
        if (PendingIntentResultHandler.isValidResponse(providerPendingIntentResponse)) {
            GetCredentialException extractGetCredentialException = PendingIntentResultHandler.extractGetCredentialException(providerPendingIntentResponse.getResultData());
            if (extractGetCredentialException == null) {
                return null;
            }
            Slog.i("ProviderRegistryGetSession", "Pending intent contains provider exception");
            return extractGetCredentialException;
        }
        if (PendingIntentResultHandler.isCancelledResponse(providerPendingIntentResponse)) {
            return new GetCredentialException("android.credentials.GetCredentialException.TYPE_USER_CANCELED");
        }
        return new GetCredentialException("android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL");
    }
}
