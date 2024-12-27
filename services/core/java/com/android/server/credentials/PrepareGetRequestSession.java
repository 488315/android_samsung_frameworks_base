package com.android.server.credentials;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.credentials.GetCredentialRequest;
import android.credentials.IGetCredentialCallback;
import android.credentials.IPrepareGetCredentialCallback;
import android.credentials.PrepareGetCredentialResponseInternal;
import android.credentials.selection.GetCredentialProviderData;
import android.credentials.selection.ProviderData;
import android.credentials.selection.RequestInfo;
import android.os.CancellationSignal;
import android.os.RemoteException;
import android.service.credentials.CallingAppInfo;
import android.service.credentials.PermissionUtils;
import android.util.Slog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class PrepareGetRequestSession extends GetRequestSession {
    public final IPrepareGetCredentialCallback mPrepareGetCredentialCallback;
    public final Set mPrimaryProviders;

    public PrepareGetRequestSession(
            Context context,
            CredentialManagerService.SessionManager sessionManager,
            Object obj,
            int i,
            int i2,
            IGetCredentialCallback iGetCredentialCallback,
            GetCredentialRequest getCredentialRequest,
            CallingAppInfo callingAppInfo,
            Set set,
            Set set2,
            CancellationSignal cancellationSignal,
            long j,
            IPrepareGetCredentialCallback iPrepareGetCredentialCallback) {
        super(
                context,
                sessionManager,
                obj,
                i,
                i2,
                iGetCredentialCallback,
                getCredentialRequest,
                callingAppInfo,
                set,
                set2,
                cancellationSignal,
                j);
        this.mRequestSessionMetric.collectGetFlowInitialMetricInfo(getCredentialRequest);
        this.mPrepareGetCredentialCallback = iPrepareGetCredentialCallback;
        this.mPrimaryProviders = set2;
    }

    @Override // com.android.server.credentials.GetRequestSession,
              // com.android.server.credentials.ProviderSession.ProviderInternalCallback
    public final void onProviderStatusChanged(
            ProviderSession.Status status,
            ComponentName componentName,
            ProviderSession.CredentialsSource credentialsSource) {
        Set set;
        boolean anyMatch;
        boolean anyMatch2;
        PendingIntent pendingIntent;
        Slog.i(
                "CredentialManager",
                "Provider Status changed with status: "
                        + status
                        + ", and source: "
                        + credentialsSource);
        int ordinal = credentialsSource.ordinal();
        if (ordinal != 0) {
            if (ordinal != 2) {
                Slog.w("CredentialManager", "Unexpected source");
                return;
            } else if (status == ProviderSession.Status.NO_CREDENTIALS_FROM_AUTH_ENTRY) {
                handleEmptyAuthenticationSelection(componentName);
                return;
            } else {
                if (status == ProviderSession.Status.CREDENTIALS_RECEIVED) {
                    getProviderDataAndInitiateUi();
                    return;
                }
                return;
            }
        }
        if (isAnyProviderPending()) {
            return;
        }
        boolean hasPermission =
                PermissionUtils.hasPermission(
                        this.mContext,
                        this.mClientAppInfo.getPackageName(),
                        "android.permission.CREDENTIAL_MANAGER_QUERY_CANDIDATE_CREDENTIALS");
        if (isUiInvocationNeeded()) {
            ArrayList providerDataForUi = getProviderDataForUi();
            if (!providerDataForUi.isEmpty()) {
                if (hasPermission) {
                    final int i = 0;
                    Stream map =
                            ((ConcurrentHashMap) this.mProviders)
                                    .values().stream()
                                            .map(
                                                    new Function() { // from class:
                                                                     // com.android.server.credentials.PrepareGetRequestSession$$ExternalSyntheticLambda0
                                                        @Override // java.util.function.Function
                                                        public final Object apply(Object obj) {
                                                            switch (i) {
                                                            }
                                                            return (ProviderData) obj;
                                                        }
                                                    });
                    final int i2 = 1;
                    set =
                            (Set)
                                    map.flatMap(
                                                    new Function() { // from class:
                                                                     // com.android.server.credentials.PrepareGetRequestSession$$ExternalSyntheticLambda0
                                                        @Override // java.util.function.Function
                                                        public final Object apply(Object obj) {
                                                            switch (i2) {
                                                            }
                                                            return (ProviderData) obj;
                                                        }
                                                    })
                                            .collect(Collectors.toSet());
                } else {
                    set = null;
                }
                if (hasPermission) {
                    final int i3 = 2;
                    Stream map2 =
                            providerDataForUi.stream()
                                    .map(
                                            new Function() { // from class:
                                                             // com.android.server.credentials.PrepareGetRequestSession$$ExternalSyntheticLambda0
                                                @Override // java.util.function.Function
                                                public final Object apply(Object obj) {
                                                    switch (i3) {
                                                    }
                                                    return (ProviderData) obj;
                                                }
                                            });
                    final int i4 = 0;
                    anyMatch =
                            map2.anyMatch(
                                    new Predicate() { // from class:
                                                      // com.android.server.credentials.PrepareGetRequestSession$$ExternalSyntheticLambda3
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            GetCredentialProviderData getCredentialProviderData =
                                                    (GetCredentialProviderData) obj;
                                            switch (i4) {
                                                case 0:
                                                    return !getCredentialProviderData
                                                            .getAuthenticationEntries()
                                                            .isEmpty();
                                                default:
                                                    return getCredentialProviderData
                                                                    .getRemoteEntry()
                                                            != null;
                                            }
                                        }
                                    });
                } else {
                    anyMatch = false;
                }
                if (hasPermission) {
                    final int i5 = 3;
                    Stream map3 =
                            providerDataForUi.stream()
                                    .map(
                                            new Function() { // from class:
                                                             // com.android.server.credentials.PrepareGetRequestSession$$ExternalSyntheticLambda0
                                                @Override // java.util.function.Function
                                                public final Object apply(Object obj) {
                                                    switch (i5) {
                                                    }
                                                    return (ProviderData) obj;
                                                }
                                            });
                    final int i6 = 1;
                    anyMatch2 =
                            map3.anyMatch(
                                    new Predicate() { // from class:
                                                      // com.android.server.credentials.PrepareGetRequestSession$$ExternalSyntheticLambda3
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            GetCredentialProviderData getCredentialProviderData =
                                                    (GetCredentialProviderData) obj;
                                            switch (i6) {
                                                case 0:
                                                    return !getCredentialProviderData
                                                            .getAuthenticationEntries()
                                                            .isEmpty();
                                                default:
                                                    return getCredentialProviderData
                                                                    .getRemoteEntry()
                                                            != null;
                                            }
                                        }
                                    });
                } else {
                    anyMatch2 = false;
                }
                ArrayList arrayList = new ArrayList();
                Iterator it = ((ConcurrentHashMap) this.mProviders).values().iterator();
                while (it.hasNext()) {
                    ProviderData prepareUiData = ((ProviderSession) it.next()).prepareUiData();
                    if (prepareUiData != null) {
                        arrayList.add(prepareUiData);
                    }
                }
                if (arrayList.isEmpty()) {
                    pendingIntent = null;
                } else {
                    ArrayList arrayList2 = new ArrayList();
                    Iterator it2 = this.mPrimaryProviders.iterator();
                    while (it2.hasNext()) {
                        arrayList2.add(((ComponentName) it2.next()).flattenToString());
                    }
                    pendingIntent =
                            this.mCredentialManagerUi.createPendingIntent(
                                    RequestInfo.newGetRequestInfo(
                                            this.mRequestId,
                                            (GetCredentialRequest) this.mClientRequest,
                                            this.mClientAppInfo.getPackageName(),
                                            PermissionUtils.hasPermission(
                                                    this.mContext,
                                                    this.mClientAppInfo.getPackageName(),
                                                    "android.permission.CREDENTIAL_MANAGER_SET_ALLOWED_PROVIDERS"),
                                            arrayList2,
                                            false),
                                    arrayList,
                                    this.mRequestSessionMetric);
                }
                try {
                    this.mPrepareGetCredentialCallback.onResponse(
                            new PrepareGetCredentialResponseInternal(
                                    hasPermission, set, anyMatch, anyMatch2, pendingIntent));
                    return;
                } catch (RemoteException e) {
                    Slog.e("CredentialManager", "EXCEPTION while mPendingCallback.onResponse", e);
                    return;
                }
            }
        }
        try {
            this.mPrepareGetCredentialCallback.onResponse(
                    new PrepareGetCredentialResponseInternal(
                            hasPermission, (Set) null, false, false, (PendingIntent) null));
        } catch (RemoteException e2) {
            Slog.e("CredentialManager", "EXCEPTION while mPendingCallback.onResponse", e2);
        }
    }
}
