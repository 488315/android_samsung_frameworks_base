package com.android.server.credentials;

import android.app.slice.Slice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.credentials.CredentialDescription;
import android.credentials.CredentialOption;
import android.credentials.GetCredentialException;
import android.credentials.GetCredentialResponse;
import android.credentials.selection.Entry;
import android.credentials.selection.GetCredentialProviderData;
import android.credentials.selection.ProviderData;
import android.credentials.selection.ProviderPendingIntentResponse;
import android.os.ICancellationSignal;
import android.service.credentials.CallingAppInfo;
import android.service.credentials.CredentialEntry;
import android.service.credentials.GetCredentialRequest;
import android.util.Slog;

import com.android.server.credentials.metrics.EntryEnum;
import com.android.server.credentials.metrics.ProviderSessionMetric;
import com.android.server.credentials.metrics.ProviderSessionMetric$$ExternalSyntheticLambda0;
import com.android.server.credentials.metrics.shared.ResponseCollective;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ProviderRegistryGetSession extends ProviderSession {
    static final String CREDENTIAL_ENTRY_KEY = "credential_key";
    public final CallingAppInfo mCallingAppInfo;
    public final CredentialDescriptionRegistry mCredentialDescriptionRegistry;
    List mCredentialEntries;
    public final String mCredentialProviderPackageName;
    public final Set mElementKeys;
    public final Map mUiCredentialEntries;

    public ProviderRegistryGetSession(
            Context context,
            int i,
            GetRequestSession getRequestSession,
            CallingAppInfo callingAppInfo,
            String str,
            CredentialOption credentialOption) {
        super(
                context,
                credentialOption,
                getRequestSession,
                new ComponentName(str, UUID.randomUUID().toString()),
                i,
                null);
        this.mUiCredentialEntries = new HashMap();
        this.mCredentialDescriptionRegistry = CredentialDescriptionRegistry.forUser(i);
        this.mCallingAppInfo = callingAppInfo;
        this.mCredentialProviderPackageName = str;
        this.mElementKeys =
                new HashSet(
                        credentialOption
                                .getCredentialRetrievalData()
                                .getStringArrayList(
                                        "android.credentials.GetCredentialOption.SUPPORTED_ELEMENT_KEYS"));
        this.mStatus = ProviderSession.Status.PENDING;
    }

    @Override // com.android.server.credentials.ProviderSession
    public final void invokeSession() {
        startCandidateMetrics();
        Set set = this.mElementKeys;
        CredentialDescriptionRegistry credentialDescriptionRegistry =
                this.mCredentialDescriptionRegistry;
        credentialDescriptionRegistry.getClass();
        HashSet hashSet = new HashSet();
        HashMap hashMap = (HashMap) credentialDescriptionRegistry.mCredentialDescriptions;
        String str = this.mCredentialProviderPackageName;
        if (hashMap.containsKey(str)) {
            for (CredentialDescription credentialDescription :
                    (Set)
                            ((HashMap) credentialDescriptionRegistry.mCredentialDescriptions)
                                    .get(str)) {
                if (credentialDescription.getSupportedElementKeys().containsAll(set)) {
                    hashSet.add(
                            new CredentialDescriptionRegistry.FilterResult(
                                    str,
                                    credentialDescription.getSupportedElementKeys(),
                                    credentialDescription.getCredentialEntries()));
                }
            }
        }
        this.mProviderResponse = hashSet;
        this.mCredentialEntries =
                (List)
                        hashSet.stream()
                                .flatMap(
                                        new ProviderRegistryGetSession$$ExternalSyntheticLambda0(0))
                                .collect(Collectors.toList());
        updateStatusAndInvokeCallback(
                ProviderSession.Status.CREDENTIALS_RECEIVED,
                ProviderSession.CredentialsSource.REGISTRY);
        List list = this.mCredentialEntries;
        ProviderSessionMetric providerSessionMetric = this.mProviderSessionMetric;
        providerSessionMetric.getClass();
        int size = list.size();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        linkedHashMap.put(EntryEnum.REMOTE_ENTRY, 0);
        linkedHashMap.put(EntryEnum.CREDENTIAL_ENTRY, Integer.valueOf(size));
        linkedHashMap.put(EntryEnum.ACTION_ENTRY, 0);
        linkedHashMap.put(EntryEnum.AUTHENTICATION_ENTRY, 0);
        list.forEach(new ProviderSessionMetric$$ExternalSyntheticLambda0(0, linkedHashMap2));
        providerSessionMetric.mCandidatePhasePerProviderMetric.mResponseCollective =
                new ResponseCollective(linkedHashMap2, linkedHashMap);
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public final void onProviderCancellable(ICancellationSignal iCancellationSignal) {}

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public final void onProviderResponseFailure(Exception exc) {}

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public final /* bridge */ /* synthetic */ void onProviderResponseSuccess(Object obj) {}

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public final void onProviderServiceDied(RemoteCredentialService remoteCredentialService) {}

    @Override // com.android.server.credentials.ProviderSession
    public final void onUiEntrySelected(
            String str, String str2, ProviderPendingIntentResponse providerPendingIntentResponse) {
        GetCredentialException getCredentialException;
        str.getClass();
        if (!str.equals(CREDENTIAL_ENTRY_KEY)) {
            Slog.i("CredentialManager", "Unsupported entry type selected");
            return;
        }
        if (((CredentialEntry) ((HashMap) this.mUiCredentialEntries).get(str2)) == null) {
            Slog.i("CredentialManager", "Unexpected credential entry key");
            return;
        }
        if (providerPendingIntentResponse != null) {
            if (providerPendingIntentResponse.getResultCode() == -1) {
                Intent resultData = providerPendingIntentResponse.getResultData();
                getCredentialException =
                        resultData == null
                                ? null
                                : (GetCredentialException)
                                        resultData.getSerializableExtra(
                                                "android.service.credentials.extra.GET_CREDENTIAL_EXCEPTION",
                                                GetCredentialException.class);
                if (getCredentialException != null) {
                    Slog.i("CredentialManager", "Pending intent contains provider exception");
                } else {
                    getCredentialException = null;
                }
            } else {
                getCredentialException =
                        providerPendingIntentResponse.getResultCode() == 0
                                ? new GetCredentialException(
                                        "android.credentials.GetCredentialException.TYPE_USER_CANCELED")
                                : new GetCredentialException(
                                        "android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL");
            }
            ProviderSession.ProviderInternalCallback providerInternalCallback = this.mCallbacks;
            if (getCredentialException != null) {
                providerInternalCallback.onFinalErrorReceived(
                        getCredentialException.getType(), getCredentialException.getMessage());
                return;
            }
            Intent resultData2 = providerPendingIntentResponse.getResultData();
            GetCredentialResponse getCredentialResponse =
                    resultData2 != null
                            ? (GetCredentialResponse)
                                    resultData2.getParcelableExtra(
                                            "android.service.credentials.extra.GET_CREDENTIAL_RESPONSE",
                                            GetCredentialResponse.class)
                            : null;
            if (getCredentialResponse != null) {
                if (providerInternalCallback != null) {
                    ((GetRequestSession) providerInternalCallback)
                            .onFinalResponseReceived(this.mComponentName, getCredentialResponse);
                    return;
                }
                return;
            }
        }
        Slog.w(
                "CredentialManager",
                "CredentialEntry does not have a credential or a pending intent result");
    }

    @Override // com.android.server.credentials.ProviderSession
    public final ProviderData prepareUiData() {
        if (!ProviderSession.isUiInvokingStatus(this.mStatus)) {
            Slog.i(
                    "CredentialManager",
                    "No date for UI coming from: " + this.mComponentName.flattenToString());
            return null;
        }
        if (this.mProviderResponse == null) {
            Slog.w(
                    "CredentialManager",
                    "response is null when preparing ui data. This is strange.");
            return null;
        }
        GetCredentialProviderData.Builder builder =
                new GetCredentialProviderData.Builder(this.mComponentName.flattenToString());
        List list = Collections.EMPTY_LIST;
        GetCredentialProviderData.Builder authenticationEntries =
                builder.setActionChips(list).setAuthenticationEntries(list);
        List<CredentialEntry> list2 =
                (List)
                        ((Set) this.mProviderResponse)
                                .stream()
                                        .flatMap(
                                                new ProviderRegistryGetSession$$ExternalSyntheticLambda0(
                                                        1))
                                        .collect(Collectors.toList());
        ArrayList arrayList = new ArrayList();
        for (CredentialEntry credentialEntry : list2) {
            String generateUniqueId = ProviderSession.generateUniqueId();
            ((HashMap) this.mUiCredentialEntries).put(generateUniqueId, credentialEntry);
            Slice slice = credentialEntry.getSlice();
            Intent intent = new Intent();
            intent.putExtra(
                    "android.service.credentials.extra.GET_CREDENTIAL_REQUEST",
                    new GetCredentialRequest(
                            this.mCallingAppInfo,
                            List.of((CredentialOption) this.mProviderRequest)));
            arrayList.add(new Entry(CREDENTIAL_ENTRY_KEY, generateUniqueId, slice, intent));
        }
        return authenticationEntries.setCredentialEntries(arrayList).build();
    }
}
