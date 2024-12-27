package com.android.server.credentials;

import android.app.slice.Slice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.credentials.CredentialOption;
import android.credentials.CredentialProviderInfo;
import android.credentials.GetCredentialException;
import android.credentials.GetCredentialRequest;
import android.credentials.GetCredentialResponse;
import android.credentials.selection.AuthenticationEntry;
import android.credentials.selection.Entry;
import android.credentials.selection.GetCredentialProviderData;
import android.credentials.selection.ProviderData;
import android.credentials.selection.ProviderPendingIntentResponse;
import android.os.ICancellationSignal;
import android.os.Parcelable;
import android.service.credentials.Action;
import android.service.credentials.BeginGetCredentialRequest;
import android.service.credentials.BeginGetCredentialResponse;
import android.service.credentials.CallingAppInfo;
import android.service.credentials.CredentialEntry;
import android.service.credentials.RemoteEntry;
import android.util.Pair;
import android.util.Slog;

import com.android.internal.hidden_from_bootclasspath.android.credentials.flags.Flags;
import com.android.server.PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0;
import com.android.server.credentials.metrics.BrowsedAuthenticationMetric;
import com.android.server.credentials.metrics.ProviderSessionMetric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public final class ProviderGetSession extends ProviderSession {
    public final Map mBeginGetOptionToCredentialOptionMap;
    public final CallingAppInfo mCallingAppInfo;
    public final GetCredentialRequest mCompleteRequest;
    public final ProviderResponseDataHandler mProviderResponseDataHandler;

    public final class ProviderResponseDataHandler {
        public final ComponentName mExpectedRemoteEntryProviderService;
        public final Map mUiCredentialEntries = new HashMap();
        public final Map mUiActionsEntries = new HashMap();
        public final Map mUiAuthenticationEntries = new HashMap();
        public final Set mCredentialEntryTypes = new HashSet();
        public Pair mUiRemoteEntry = null;

        /* renamed from: -$$Nest$misEmptyResponse, reason: not valid java name */
        public static boolean m392$$Nest$misEmptyResponse(
                ProviderResponseDataHandler providerResponseDataHandler) {
            return ((HashMap) providerResponseDataHandler.mUiCredentialEntries).isEmpty()
                    && ((HashMap) providerResponseDataHandler.mUiActionsEntries).isEmpty()
                    && ((HashMap) providerResponseDataHandler.mUiAuthenticationEntries).isEmpty()
                    && providerResponseDataHandler.mUiRemoteEntry == null;
        }

        /* renamed from: -$$Nest$misEmptyResponse, reason: not valid java name */
        public static boolean m393$$Nest$misEmptyResponse(
                ProviderResponseDataHandler providerResponseDataHandler,
                BeginGetCredentialResponse beginGetCredentialResponse) {
            providerResponseDataHandler.getClass();
            return beginGetCredentialResponse.getCredentialEntries().isEmpty()
                    && beginGetCredentialResponse.getActions().isEmpty()
                    && beginGetCredentialResponse.getAuthenticationActions().isEmpty()
                    && beginGetCredentialResponse.getRemoteCredentialEntry() == null;
        }

        public ProviderResponseDataHandler(ComponentName componentName) {
            this.mExpectedRemoteEntryProviderService = componentName;
        }

        public final void updatePreviousMostRecentAuthEntry() {
            Optional findFirst =
                    ((HashMap) this.mUiAuthenticationEntries)
                            .entrySet().stream()
                                    .filter(new ProviderGetSession$$ExternalSyntheticLambda1(1))
                                    .findFirst();
            if (findFirst.isEmpty()) {
                return;
            }
            String str = (String) ((Map.Entry) findFirst.get()).getKey();
            ((HashMap) this.mUiAuthenticationEntries).remove(str);
            Map map = this.mUiAuthenticationEntries;
            Action action = (Action) ((Pair) ((Map.Entry) findFirst.get()).getValue()).first;
            AuthenticationEntry authenticationEntry =
                    (AuthenticationEntry) ((Pair) ((Map.Entry) findFirst.get()).getValue()).second;
            ((HashMap) map)
                    .put(
                            str,
                            new Pair(
                                    action,
                                    new AuthenticationEntry(
                                            "authentication_action_key",
                                            authenticationEntry.getSubkey(),
                                            authenticationEntry.getSlice(),
                                            1,
                                            authenticationEntry.getFrameworkExtrasIntent())));
        }
    }

    public ProviderGetSession(
            Context context,
            CredentialProviderInfo credentialProviderInfo,
            ProviderSession.ProviderInternalCallback providerInternalCallback,
            int i,
            RemoteCredentialService remoteCredentialService,
            BeginGetCredentialRequest beginGetCredentialRequest,
            GetCredentialRequest getCredentialRequest,
            CallingAppInfo callingAppInfo,
            Map map,
            String str) {
        super(
                context,
                beginGetCredentialRequest,
                providerInternalCallback,
                credentialProviderInfo.getComponentName(),
                i,
                remoteCredentialService);
        this.mCompleteRequest = getCredentialRequest;
        this.mCallingAppInfo = callingAppInfo;
        this.mStatus = ProviderSession.Status.PENDING;
        this.mBeginGetOptionToCredentialOptionMap = new HashMap(map);
        this.mProviderResponseDataHandler =
                new ProviderResponseDataHandler(ComponentName.unflattenFromString(str));
    }

    public static GetCredentialRequest filterOptions(
            List list,
            GetCredentialRequest getCredentialRequest,
            CredentialProviderInfo credentialProviderInfo,
            String str) {
        Slog.i(
                "CredentialManager",
                "Filtering request options for: " + credentialProviderInfo.getComponentName());
        if (Flags.hybridFilterOptFixEnabled()) {
            ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
            if (unflattenFromString != null
                    && unflattenFromString.equals(credentialProviderInfo.getComponentName())) {
                Slog.i("CredentialManager", "Skipping filtering of options for hybrid service");
                return getCredentialRequest;
            }
            Slog.w("CredentialManager", "Could not parse hybrid service while filtering options");
        }
        ArrayList arrayList = new ArrayList();
        for (CredentialOption credentialOption : getCredentialRequest.getCredentialOptions()) {
            if (list.contains(credentialOption.getType())) {
                if (credentialProviderInfo.isSystemProvider()
                        || credentialOption.getAllowedProviders().isEmpty()
                        || credentialOption
                                .getAllowedProviders()
                                .contains(credentialProviderInfo.getComponentName())) {
                    boolean isSystemProvider = credentialProviderInfo.isSystemProvider();
                    if (!credentialOption.isSystemProviderRequired() || isSystemProvider) {
                        Slog.i(
                                "CredentialManager",
                                "Option of type: "
                                        + credentialOption.getType()
                                        + " meets all filteringconditions");
                        arrayList.add(credentialOption);
                    } else {
                        Slog.i(
                                "CredentialManager",
                                "System provider required, but this service is not a system"
                                    + " provider");
                    }
                } else {
                    Slog.i(
                            "CredentialManager",
                            "Provider allow list specified but does not contain this provider");
                }
            }
        }
        if (!arrayList.isEmpty()) {
            return new GetCredentialRequest.Builder(getCredentialRequest.getData())
                    .setCredentialOptions(arrayList)
                    .build();
        }
        Slog.i("CredentialManager", "No options filtered");
        return null;
    }

    public static GetCredentialException maybeGetPendingIntentException(
            ProviderPendingIntentResponse providerPendingIntentResponse) {
        if (providerPendingIntentResponse == null) {
            return null;
        }
        if (providerPendingIntentResponse.getResultCode() != -1) {
            return providerPendingIntentResponse.getResultCode() == 0
                    ? new GetCredentialException(
                            "android.credentials.GetCredentialException.TYPE_USER_CANCELED")
                    : new GetCredentialException(
                            "android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL");
        }
        Intent resultData = providerPendingIntentResponse.getResultData();
        GetCredentialException getCredentialException =
                resultData == null
                        ? null
                        : (GetCredentialException)
                                resultData.getSerializableExtra(
                                        "android.service.credentials.extra.GET_CREDENTIAL_EXCEPTION",
                                        GetCredentialException.class);
        if (getCredentialException != null) {
            return getCredentialException;
        }
        return null;
    }

    public final void addToInitialRemoteResponse(
            BeginGetCredentialResponse beginGetCredentialResponse, boolean z) {
        if (beginGetCredentialResponse == null) {
            return;
        }
        List<CredentialEntry> credentialEntries = beginGetCredentialResponse.getCredentialEntries();
        List<Action> actions = beginGetCredentialResponse.getActions();
        List<Action> authenticationActions = beginGetCredentialResponse.getAuthenticationActions();
        RemoteEntry remoteCredentialEntry = beginGetCredentialResponse.getRemoteCredentialEntry();
        final ProviderResponseDataHandler providerResponseDataHandler =
                this.mProviderResponseDataHandler;
        providerResponseDataHandler.getClass();
        final int i = 0;
        credentialEntries.forEach(
                new Consumer() { // from class:
                    // com.android.server.credentials.ProviderGetSession$ProviderResponseDataHandler$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i2 = i;
                        ProviderGetSession.ProviderResponseDataHandler
                                providerResponseDataHandler2 = providerResponseDataHandler;
                        switch (i2) {
                            case 0:
                                CredentialEntry credentialEntry = (CredentialEntry) obj;
                                providerResponseDataHandler2.getClass();
                                String generateUniqueId = ProviderSession.generateUniqueId();
                                Slice slice = credentialEntry.getSlice();
                                String beginGetCredentialOptionId =
                                        credentialEntry.getBeginGetCredentialOptionId();
                                ProviderGetSession providerGetSession = ProviderGetSession.this;
                                providerGetSession.getClass();
                                Intent intent = new Intent();
                                CredentialOption credentialOption =
                                        (CredentialOption)
                                                ((HashMap)
                                                                providerGetSession
                                                                        .mBeginGetOptionToCredentialOptionMap)
                                                        .get(beginGetCredentialOptionId);
                                if (credentialOption == null) {
                                    Slog.w(
                                            "CredentialManager",
                                            "Id from Credential Entry does not resolve to a valid"
                                                + " option");
                                } else {
                                    intent =
                                            intent.putExtra(
                                                    "android.service.credentials.extra.GET_CREDENTIAL_REQUEST",
                                                    new android.service.credentials
                                                            .GetCredentialRequest(
                                                            providerGetSession.mCallingAppInfo,
                                                            List.of(credentialOption)));
                                }
                                Entry entry =
                                        new Entry(
                                                "credential_key", generateUniqueId, slice, intent);
                                ((HashMap) providerResponseDataHandler2.mUiCredentialEntries)
                                        .put(generateUniqueId, new Pair(credentialEntry, entry));
                                ((HashSet) providerResponseDataHandler2.mCredentialEntryTypes)
                                        .add(credentialEntry.getType());
                                break;
                            case 1:
                                Action action = (Action) obj;
                                providerResponseDataHandler2.getClass();
                                String generateUniqueId2 = ProviderSession.generateUniqueId();
                                Slice slice2 = action.getSlice();
                                ProviderGetSession providerGetSession2 = ProviderGetSession.this;
                                providerGetSession2.getClass();
                                Intent intent2 = new Intent();
                                intent2.putExtra(
                                        "android.service.credentials.extra.BEGIN_GET_CREDENTIAL_REQUEST",
                                        (Parcelable) providerGetSession2.mProviderRequest);
                                Entry entry2 =
                                        new Entry("action_key", generateUniqueId2, slice2, intent2);
                                ((HashMap) providerResponseDataHandler2.mUiActionsEntries)
                                        .put(generateUniqueId2, new Pair(action, entry2));
                                break;
                            default:
                                Action action2 = (Action) obj;
                                providerResponseDataHandler2.getClass();
                                String generateUniqueId3 = ProviderSession.generateUniqueId();
                                Slice slice3 = action2.getSlice();
                                ProviderGetSession providerGetSession3 = ProviderGetSession.this;
                                providerGetSession3.getClass();
                                Intent intent3 = new Intent();
                                intent3.putExtra(
                                        "android.service.credentials.extra.BEGIN_GET_CREDENTIAL_REQUEST",
                                        (Parcelable) providerGetSession3.mProviderRequest);
                                AuthenticationEntry authenticationEntry =
                                        new AuthenticationEntry(
                                                "authentication_action_key",
                                                generateUniqueId3,
                                                slice3,
                                                0,
                                                intent3);
                                ((HashMap) providerResponseDataHandler2.mUiAuthenticationEntries)
                                        .put(
                                                generateUniqueId3,
                                                new Pair(action2, authenticationEntry));
                                break;
                        }
                    }
                });
        final int i2 = 1;
        actions.forEach(
                new Consumer() { // from class:
                    // com.android.server.credentials.ProviderGetSession$ProviderResponseDataHandler$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i22 = i2;
                        ProviderGetSession.ProviderResponseDataHandler
                                providerResponseDataHandler2 = providerResponseDataHandler;
                        switch (i22) {
                            case 0:
                                CredentialEntry credentialEntry = (CredentialEntry) obj;
                                providerResponseDataHandler2.getClass();
                                String generateUniqueId = ProviderSession.generateUniqueId();
                                Slice slice = credentialEntry.getSlice();
                                String beginGetCredentialOptionId =
                                        credentialEntry.getBeginGetCredentialOptionId();
                                ProviderGetSession providerGetSession = ProviderGetSession.this;
                                providerGetSession.getClass();
                                Intent intent = new Intent();
                                CredentialOption credentialOption =
                                        (CredentialOption)
                                                ((HashMap)
                                                                providerGetSession
                                                                        .mBeginGetOptionToCredentialOptionMap)
                                                        .get(beginGetCredentialOptionId);
                                if (credentialOption == null) {
                                    Slog.w(
                                            "CredentialManager",
                                            "Id from Credential Entry does not resolve to a valid"
                                                + " option");
                                } else {
                                    intent =
                                            intent.putExtra(
                                                    "android.service.credentials.extra.GET_CREDENTIAL_REQUEST",
                                                    new android.service.credentials
                                                            .GetCredentialRequest(
                                                            providerGetSession.mCallingAppInfo,
                                                            List.of(credentialOption)));
                                }
                                Entry entry =
                                        new Entry(
                                                "credential_key", generateUniqueId, slice, intent);
                                ((HashMap) providerResponseDataHandler2.mUiCredentialEntries)
                                        .put(generateUniqueId, new Pair(credentialEntry, entry));
                                ((HashSet) providerResponseDataHandler2.mCredentialEntryTypes)
                                        .add(credentialEntry.getType());
                                break;
                            case 1:
                                Action action = (Action) obj;
                                providerResponseDataHandler2.getClass();
                                String generateUniqueId2 = ProviderSession.generateUniqueId();
                                Slice slice2 = action.getSlice();
                                ProviderGetSession providerGetSession2 = ProviderGetSession.this;
                                providerGetSession2.getClass();
                                Intent intent2 = new Intent();
                                intent2.putExtra(
                                        "android.service.credentials.extra.BEGIN_GET_CREDENTIAL_REQUEST",
                                        (Parcelable) providerGetSession2.mProviderRequest);
                                Entry entry2 =
                                        new Entry("action_key", generateUniqueId2, slice2, intent2);
                                ((HashMap) providerResponseDataHandler2.mUiActionsEntries)
                                        .put(generateUniqueId2, new Pair(action, entry2));
                                break;
                            default:
                                Action action2 = (Action) obj;
                                providerResponseDataHandler2.getClass();
                                String generateUniqueId3 = ProviderSession.generateUniqueId();
                                Slice slice3 = action2.getSlice();
                                ProviderGetSession providerGetSession3 = ProviderGetSession.this;
                                providerGetSession3.getClass();
                                Intent intent3 = new Intent();
                                intent3.putExtra(
                                        "android.service.credentials.extra.BEGIN_GET_CREDENTIAL_REQUEST",
                                        (Parcelable) providerGetSession3.mProviderRequest);
                                AuthenticationEntry authenticationEntry =
                                        new AuthenticationEntry(
                                                "authentication_action_key",
                                                generateUniqueId3,
                                                slice3,
                                                0,
                                                intent3);
                                ((HashMap) providerResponseDataHandler2.mUiAuthenticationEntries)
                                        .put(
                                                generateUniqueId3,
                                                new Pair(action2, authenticationEntry));
                                break;
                        }
                    }
                });
        final int i3 = 2;
        authenticationActions.forEach(
                new Consumer() { // from class:
                    // com.android.server.credentials.ProviderGetSession$ProviderResponseDataHandler$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i22 = i3;
                        ProviderGetSession.ProviderResponseDataHandler
                                providerResponseDataHandler2 = providerResponseDataHandler;
                        switch (i22) {
                            case 0:
                                CredentialEntry credentialEntry = (CredentialEntry) obj;
                                providerResponseDataHandler2.getClass();
                                String generateUniqueId = ProviderSession.generateUniqueId();
                                Slice slice = credentialEntry.getSlice();
                                String beginGetCredentialOptionId =
                                        credentialEntry.getBeginGetCredentialOptionId();
                                ProviderGetSession providerGetSession = ProviderGetSession.this;
                                providerGetSession.getClass();
                                Intent intent = new Intent();
                                CredentialOption credentialOption =
                                        (CredentialOption)
                                                ((HashMap)
                                                                providerGetSession
                                                                        .mBeginGetOptionToCredentialOptionMap)
                                                        .get(beginGetCredentialOptionId);
                                if (credentialOption == null) {
                                    Slog.w(
                                            "CredentialManager",
                                            "Id from Credential Entry does not resolve to a valid"
                                                + " option");
                                } else {
                                    intent =
                                            intent.putExtra(
                                                    "android.service.credentials.extra.GET_CREDENTIAL_REQUEST",
                                                    new android.service.credentials
                                                            .GetCredentialRequest(
                                                            providerGetSession.mCallingAppInfo,
                                                            List.of(credentialOption)));
                                }
                                Entry entry =
                                        new Entry(
                                                "credential_key", generateUniqueId, slice, intent);
                                ((HashMap) providerResponseDataHandler2.mUiCredentialEntries)
                                        .put(generateUniqueId, new Pair(credentialEntry, entry));
                                ((HashSet) providerResponseDataHandler2.mCredentialEntryTypes)
                                        .add(credentialEntry.getType());
                                break;
                            case 1:
                                Action action = (Action) obj;
                                providerResponseDataHandler2.getClass();
                                String generateUniqueId2 = ProviderSession.generateUniqueId();
                                Slice slice2 = action.getSlice();
                                ProviderGetSession providerGetSession2 = ProviderGetSession.this;
                                providerGetSession2.getClass();
                                Intent intent2 = new Intent();
                                intent2.putExtra(
                                        "android.service.credentials.extra.BEGIN_GET_CREDENTIAL_REQUEST",
                                        (Parcelable) providerGetSession2.mProviderRequest);
                                Entry entry2 =
                                        new Entry("action_key", generateUniqueId2, slice2, intent2);
                                ((HashMap) providerResponseDataHandler2.mUiActionsEntries)
                                        .put(generateUniqueId2, new Pair(action, entry2));
                                break;
                            default:
                                Action action2 = (Action) obj;
                                providerResponseDataHandler2.getClass();
                                String generateUniqueId3 = ProviderSession.generateUniqueId();
                                Slice slice3 = action2.getSlice();
                                ProviderGetSession providerGetSession3 = ProviderGetSession.this;
                                providerGetSession3.getClass();
                                Intent intent3 = new Intent();
                                intent3.putExtra(
                                        "android.service.credentials.extra.BEGIN_GET_CREDENTIAL_REQUEST",
                                        (Parcelable) providerGetSession3.mProviderRequest);
                                AuthenticationEntry authenticationEntry =
                                        new AuthenticationEntry(
                                                "authentication_action_key",
                                                generateUniqueId3,
                                                slice3,
                                                0,
                                                intent3);
                                ((HashMap) providerResponseDataHandler2.mUiAuthenticationEntries)
                                        .put(
                                                generateUniqueId3,
                                                new Pair(action2, authenticationEntry));
                                break;
                        }
                    }
                });
        if (remoteCredentialEntry == null && z) {
            return;
        }
        ComponentName componentName =
                providerResponseDataHandler.mExpectedRemoteEntryProviderService;
        ProviderGetSession providerGetSession = ProviderGetSession.this;
        if (!providerGetSession.enforceRemoteEntryRestrictions(componentName)) {
            Slog.w(
                    "CredentialManager",
                    "Remote entry being dropped as it does not meet the restriction checks.");
        } else if (remoteCredentialEntry == null) {
            providerResponseDataHandler.mUiRemoteEntry = null;
        } else {
            String generateUniqueId = ProviderSession.generateUniqueId();
            providerResponseDataHandler.mUiRemoteEntry =
                    new Pair(
                            generateUniqueId,
                            new Pair(
                                    remoteCredentialEntry,
                                    new Entry(
                                            "remote_entry_key",
                                            generateUniqueId,
                                            remoteCredentialEntry.getSlice(),
                                            new Intent()
                                                    .putExtra(
                                                            "android.service.credentials.extra.GET_CREDENTIAL_REQUEST",
                                                            new android.service.credentials
                                                                    .GetCredentialRequest(
                                                                    providerGetSession
                                                                            .mCallingAppInfo,
                                                                    providerGetSession
                                                                            .mCompleteRequest
                                                                            .getCredentialOptions())))));
        }
    }

    public final void invokeCallbackOnInternalInvalidState$1() {
        this.mCallbacks.onFinalErrorReceived(
                "android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL", null);
    }

    @Override // com.android.server.credentials.ProviderSession
    public final void invokeSession() {
        RemoteCredentialService remoteCredentialService = this.mRemoteCredentialService;
        if (remoteCredentialService != null) {
            startCandidateMetrics();
            remoteCredentialService.setCallback(this);
            remoteCredentialService.onBeginGetCredential(
                    (BeginGetCredentialRequest) this.mProviderRequest);
        }
    }

    public final void onCredentialEntrySelected(
            ProviderPendingIntentResponse providerPendingIntentResponse) {
        if (providerPendingIntentResponse == null) {
            invokeCallbackOnInternalInvalidState$1();
            return;
        }
        GetCredentialException maybeGetPendingIntentException =
                maybeGetPendingIntentException(providerPendingIntentResponse);
        ProviderSession.ProviderInternalCallback providerInternalCallback = this.mCallbacks;
        if (maybeGetPendingIntentException != null) {
            providerInternalCallback.onFinalErrorReceived(
                    maybeGetPendingIntentException.getType(),
                    maybeGetPendingIntentException.getMessage());
            return;
        }
        Intent resultData = providerPendingIntentResponse.getResultData();
        GetCredentialResponse getCredentialResponse =
                resultData == null
                        ? null
                        : (GetCredentialResponse)
                                resultData.getParcelableExtra(
                                        "android.service.credentials.extra.GET_CREDENTIAL_RESPONSE",
                                        GetCredentialResponse.class);
        if (getCredentialResponse != null) {
            providerInternalCallback.onFinalResponseReceived(
                    this.mComponentName, getCredentialResponse);
        } else {
            Slog.i(
                    "CredentialManager",
                    "Pending intent response contains no credential, or error for a credential"
                        + " entry");
            invokeCallbackOnInternalInvalidState$1();
        }
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public final void onProviderCancellable(ICancellationSignal iCancellationSignal) {
        this.mProviderCancellationSignal = iCancellationSignal;
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public final void onProviderResponseFailure(Exception exc) {
        boolean z = exc instanceof GetCredentialException;
        ProviderSessionMetric providerSessionMetric = this.mProviderSessionMetric;
        if (z) {
            providerSessionMetric.collectCandidateFrameworkException(
                    ((GetCredentialException) exc).getType());
        }
        providerSessionMetric.collectCandidateExceptionStatus();
        updateStatusAndInvokeCallback(
                ProviderSession.Status.CANCELED, ProviderSession.CredentialsSource.REMOTE_PROVIDER);
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public final void onProviderResponseSuccess(Object obj) {
        BeginGetCredentialResponse beginGetCredentialResponse = (BeginGetCredentialResponse) obj;
        Slog.i(
                "CredentialManager",
                "Remote provider responded with a valid response: " + this.mComponentName);
        this.mProviderResponse = beginGetCredentialResponse;
        addToInitialRemoteResponse(beginGetCredentialResponse, true);
        boolean m393$$Nest$misEmptyResponse =
                ProviderResponseDataHandler.m393$$Nest$misEmptyResponse(
                        this.mProviderResponseDataHandler, beginGetCredentialResponse);
        ProviderSession.CredentialsSource credentialsSource =
                ProviderSession.CredentialsSource.REMOTE_PROVIDER;
        ProviderSessionMetric providerSessionMetric = this.mProviderSessionMetric;
        if (m393$$Nest$misEmptyResponse) {
            providerSessionMetric.collectCandidateEntryMetrics(
                    beginGetCredentialResponse, false, null);
            updateStatusAndInvokeCallback(ProviderSession.Status.EMPTY_RESPONSE, credentialsSource);
        } else {
            providerSessionMetric.collectCandidateEntryMetrics(
                    beginGetCredentialResponse, false, null);
            updateStatusAndInvokeCallback(
                    ProviderSession.Status.CREDENTIALS_RECEIVED, credentialsSource);
        }
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public final void onProviderServiceDied(RemoteCredentialService remoteCredentialService) {
        if (remoteCredentialService.getComponentName().equals(this.mComponentName)) {
            updateStatusAndInvokeCallback(
                    ProviderSession.Status.SERVICE_DEAD,
                    ProviderSession.CredentialsSource.REMOTE_PROVIDER);
        } else {
            Slog.w(
                    "CredentialManager",
                    "Component names different in onProviderServiceDied - this should not happen");
        }
    }

    @Override // com.android.server.credentials.ProviderSession
    public final void onUiEntrySelected(
            String str, String str2, ProviderPendingIntentResponse providerPendingIntentResponse) {
        Parcelable parcelable;
        ProviderResponseDataHandler providerResponseDataHandler;
        Object obj;
        Slog.i(
                "CredentialManager",
                "onUiEntrySelected with entryType: " + str + ", and entryKey: " + str2);
        str.getClass();
        providerResponseDataHandler = this.mProviderResponseDataHandler;
        switch (str) {
            case "remote_entry_key":
                if (((String) providerResponseDataHandler.mUiRemoteEntry.first).equals(str2)
                        && (obj = providerResponseDataHandler.mUiRemoteEntry.second) != null) {
                    parcelable = (RemoteEntry) ((Pair) obj).first;
                }
                if (parcelable == null) {
                    Slog.i("CredentialManager", "Unexpected remote entry key");
                    invokeCallbackOnInternalInvalidState$1();
                    break;
                } else {
                    onCredentialEntrySelected(providerPendingIntentResponse);
                    break;
                }
                break;
            case "authentication_action_key":
                Action action =
                        ((HashMap) providerResponseDataHandler.mUiAuthenticationEntries).get(str2)
                                        == null
                                ? null
                                : (Action)
                                        ((Pair)
                                                        ((HashMap)
                                                                        providerResponseDataHandler
                                                                                .mUiAuthenticationEntries)
                                                                .get(str2))
                                                .first;
                ProviderSessionMetric providerSessionMetric = this.mProviderSessionMetric;
                ((ArrayList) providerSessionMetric.mBrowsedAuthenticationMetric)
                        .add(
                                new BrowsedAuthenticationMetric(
                                        providerSessionMetric
                                                .mCandidatePhasePerProviderMetric
                                                .mSessionIdProvider));
                if (action != null) {
                    ProviderSession.CredentialsSource credentialsSource =
                            ProviderSession.CredentialsSource.AUTH_ENTRY;
                    if (providerPendingIntentResponse != null) {
                        GetCredentialException maybeGetPendingIntentException =
                                maybeGetPendingIntentException(providerPendingIntentResponse);
                        if (maybeGetPendingIntentException != null) {
                            try {
                                List list = providerSessionMetric.mBrowsedAuthenticationMetric;
                                ((BrowsedAuthenticationMetric)
                                                        ((ArrayList) list)
                                                                .get(((ArrayList) list).size() - 1))
                                                .mHasException =
                                        true;
                            } catch (Exception e) {
                                PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0.m(
                                        e,
                                        "Error while setting authentication metric exception ",
                                        "ProviderSessionMetric");
                            }
                            this.mCallbacks.onFinalErrorReceived(
                                    maybeGetPendingIntentException.getType(),
                                    maybeGetPendingIntentException.getMessage());
                        } else {
                            Intent resultData = providerPendingIntentResponse.getResultData();
                            BeginGetCredentialResponse beginGetCredentialResponse =
                                    resultData == null
                                            ? null
                                            : (BeginGetCredentialResponse)
                                                    resultData.getParcelableExtra(
                                                            "android.service.credentials.extra.BEGIN_GET_CREDENTIAL_RESPONSE",
                                                            BeginGetCredentialResponse.class);
                            providerSessionMetric.collectCandidateEntryMetrics(
                                    beginGetCredentialResponse, true, null);
                            if (beginGetCredentialResponse != null
                                    && !ProviderResponseDataHandler.m393$$Nest$misEmptyResponse(
                                            providerResponseDataHandler,
                                            beginGetCredentialResponse)) {
                                addToInitialRemoteResponse(beginGetCredentialResponse, false);
                            }
                        }
                        Slog.i(
                                "CredentialManager",
                                "Additional content received - removing authentication entry");
                        ((HashMap) providerResponseDataHandler.mUiAuthenticationEntries)
                                .remove(str2);
                        if (!ProviderResponseDataHandler.m392$$Nest$misEmptyResponse(
                                providerResponseDataHandler)) {
                            updateStatusAndInvokeCallback(
                                    ProviderSession.Status.CREDENTIALS_RECEIVED, credentialsSource);
                            break;
                        }
                    }
                    Slog.i(
                            "CredentialManager",
                            "Additional content not received from authentication entry");
                    if (str2 == null) {
                        providerResponseDataHandler.updatePreviousMostRecentAuthEntry();
                    } else {
                        providerResponseDataHandler.updatePreviousMostRecentAuthEntry();
                        AuthenticationEntry authenticationEntry =
                                (AuthenticationEntry)
                                        ((Pair)
                                                        ((HashMap)
                                                                        providerResponseDataHandler
                                                                                .mUiAuthenticationEntries)
                                                                .get(str2))
                                                .second;
                        ((HashMap) providerResponseDataHandler.mUiAuthenticationEntries)
                                .put(
                                        str2,
                                        new Pair(
                                                (Action)
                                                        ((Pair)
                                                                        ((HashMap)
                                                                                        providerResponseDataHandler
                                                                                                .mUiAuthenticationEntries)
                                                                                .get(str2))
                                                                .first,
                                                new AuthenticationEntry(
                                                        "authentication_action_key",
                                                        authenticationEntry.getSubkey(),
                                                        authenticationEntry.getSlice(),
                                                        2,
                                                        authenticationEntry
                                                                .getFrameworkExtrasIntent())));
                    }
                    updateStatusAndInvokeCallback(
                            ProviderSession.Status.NO_CREDENTIALS_FROM_AUTH_ENTRY,
                            credentialsSource);
                    break;
                } else {
                    Slog.i("CredentialManager", "Unexpected authenticationEntry key");
                    invokeCallbackOnInternalInvalidState$1();
                    break;
                }
                break;
            case "credential_key":
                if ((((HashMap) providerResponseDataHandler.mUiCredentialEntries).get(str2) != null
                                ? (CredentialEntry)
                                        ((Pair)
                                                        ((HashMap)
                                                                        providerResponseDataHandler
                                                                                .mUiCredentialEntries)
                                                                .get(str2))
                                                .first
                                : null)
                        != null) {
                    onCredentialEntrySelected(providerPendingIntentResponse);
                    break;
                } else {
                    Slog.i("CredentialManager", "Unexpected credential entry key");
                    invokeCallbackOnInternalInvalidState$1();
                    break;
                }
            case "action_key":
                if ((((HashMap) providerResponseDataHandler.mUiActionsEntries).get(str2) != null
                                ? (Action)
                                        ((Pair)
                                                        ((HashMap)
                                                                        providerResponseDataHandler
                                                                                .mUiActionsEntries)
                                                                .get(str2))
                                                .first
                                : null)
                        != null) {
                    Slog.i("CredentialManager", "onActionEntrySelected");
                    onCredentialEntrySelected(providerPendingIntentResponse);
                    break;
                } else {
                    Slog.i("CredentialManager", "Unexpected action entry key");
                    invokeCallbackOnInternalInvalidState$1();
                    break;
                }
            default:
                Slog.i("CredentialManager", "Unsupported entry type selected");
                invokeCallbackOnInternalInvalidState$1();
                break;
        }
    }

    @Override // com.android.server.credentials.ProviderSession
    public final ProviderData prepareUiData() {
        Object obj;
        Entry entry = null;
        if (!ProviderSession.isUiInvokingStatus(this.mStatus)) {
            Slog.i(
                    "CredentialManager",
                    "No data for UI from: " + this.mComponentName.flattenToString());
            return null;
        }
        if (this.mProviderResponse == null) {
            return null;
        }
        ProviderResponseDataHandler providerResponseDataHandler = this.mProviderResponseDataHandler;
        if (ProviderResponseDataHandler.m392$$Nest$misEmptyResponse(providerResponseDataHandler)) {
            return null;
        }
        GetCredentialProviderData.Builder builder =
                new GetCredentialProviderData.Builder(
                        ProviderGetSession.this.mComponentName.flattenToString());
        ArrayList arrayList = new ArrayList();
        Iterator it = ((HashMap) providerResponseDataHandler.mUiActionsEntries).keySet().iterator();
        while (it.hasNext()) {
            arrayList.add(
                    (Entry)
                            ((Pair)
                                            ((HashMap)
                                                            providerResponseDataHandler
                                                                    .mUiActionsEntries)
                                                    .get((String) it.next()))
                                    .second);
        }
        GetCredentialProviderData.Builder actionChips = builder.setActionChips(arrayList);
        ArrayList arrayList2 = new ArrayList();
        Iterator it2 =
                ((HashMap) providerResponseDataHandler.mUiCredentialEntries).keySet().iterator();
        while (it2.hasNext()) {
            arrayList2.add(
                    (Entry)
                            ((Pair)
                                            ((HashMap)
                                                            providerResponseDataHandler
                                                                    .mUiCredentialEntries)
                                                    .get((String) it2.next()))
                                    .second);
        }
        GetCredentialProviderData.Builder credentialEntries =
                actionChips.setCredentialEntries(arrayList2);
        ArrayList arrayList3 = new ArrayList();
        Iterator it3 =
                ((HashMap) providerResponseDataHandler.mUiAuthenticationEntries)
                        .keySet()
                        .iterator();
        while (it3.hasNext()) {
            arrayList3.add(
                    (AuthenticationEntry)
                            ((Pair)
                                            ((HashMap)
                                                            providerResponseDataHandler
                                                                    .mUiAuthenticationEntries)
                                                    .get((String) it3.next()))
                                    .second);
        }
        GetCredentialProviderData.Builder authenticationEntries =
                credentialEntries.setAuthenticationEntries(arrayList3);
        Pair pair = providerResponseDataHandler.mUiRemoteEntry;
        if (pair != null && pair.first != null && (obj = pair.second) != null) {
            entry = (Entry) ((Pair) obj).second;
        }
        return authenticationEntries.setRemoteEntry(entry).build();
    }
}
