package com.android.server.credentials;

import android.R;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.credentials.ClearCredentialStateRequest;
import android.credentials.CreateCredentialRequest;
import android.credentials.CredentialDescription;
import android.credentials.CredentialOption;
import android.credentials.CredentialProviderInfo;
import android.credentials.GetCredentialRequest;
import android.credentials.IClearCredentialStateCallback;
import android.credentials.ICreateCredentialCallback;
import android.credentials.ICredentialManager;
import android.credentials.IGetCandidateCredentialsCallback;
import android.credentials.IGetCredentialCallback;
import android.credentials.IPrepareGetCredentialCallback;
import android.credentials.ISetEnabledProvidersCallback;
import android.credentials.PrepareGetCredentialResponseInternal;
import android.credentials.RegisterCredentialDescriptionRequest;
import android.credentials.UnregisterCredentialDescriptionRequest;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.CancellationSignal;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.service.credentials.CallingAppInfo;
import android.service.credentials.CredentialProviderInfoFactory;
import android.service.credentials.PermissionUtils;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;

import com.android.server.AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.credentials.metrics.ApiName;
import com.android.server.credentials.metrics.ApiStatus;
import com.android.server.credentials.metrics.InitialPhaseMetric;
import com.android.server.credentials.metrics.RequestSessionMetric;
import com.android.server.infra.AbstractMasterSystemService;
import com.android.server.infra.AbstractPerUserSystemService;
import com.android.server.infra.SecureSettingsServiceNameResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CredentialManagerService extends AbstractMasterSystemService {
    public final Context mContext;
    public final SparseArray mRequestSessions;
    public final SessionManager mSessionManager;
    public final SparseArray mSystemServicesCacheList;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CredentialManagerServiceStub extends ICredentialManager.Stub {
        public CredentialManagerServiceStub() {}

        public static void finalizeAndEmitInitialPhaseMetric(RequestSession requestSession) {
            RequestSessionMetric requestSessionMetric = requestSession.mRequestSessionMetric;
            try {
                InitialPhaseMetric initialPhaseMetric = requestSessionMetric.mInitialPhaseMetric;
                System.nanoTime();
                initialPhaseMetric.getClass();
                int i = requestSessionMetric.mSequenceCounter + 1;
                requestSessionMetric.mSequenceCounter = i;
                MetricUtilities.logApiCalledInitialPhase(initialPhaseMetric, i);
            } catch (Exception e) {
                Slog.i("CredentialManager", "Unexpected error during metric logging: ", e);
            }
        }

        public final ICancellationSignal clearCredentialState(
                ClearCredentialStateRequest clearCredentialStateRequest,
                IClearCredentialStateCallback iClearCredentialStateCallback,
                String str) {
            long nanoTime = System.nanoTime();
            Slog.i(
                    "CredentialManager",
                    "starting clearCredentialState with callingPackage: " + str);
            int callingUserId = UserHandle.getCallingUserId();
            int callingUid = Binder.getCallingUid();
            CredentialManagerService.m390$$Nest$menforceCallingPackage(
                    CredentialManagerService.this, str, callingUid);
            ICancellationSignal createTransport = CancellationSignal.createTransport();
            Context context = CredentialManagerService.this.getContext();
            CredentialManagerService credentialManagerService = CredentialManagerService.this;
            ClearRequestSession clearRequestSession =
                    new ClearRequestSession(
                            context,
                            credentialManagerService.mSessionManager,
                            credentialManagerService.mLock,
                            callingUserId,
                            callingUid,
                            clearCredentialStateRequest,
                            iClearCredentialStateCallback,
                            "android.credentials.selection.TYPE_UNDEFINED",
                            CredentialManagerService.m389$$Nest$mconstructCallingAppInfo(
                                    credentialManagerService, str, callingUserId, null),
                            getEnabledProvidersForUser(callingUserId),
                            CancellationSignal.fromTransport(createTransport),
                            nanoTime,
                            true);
            CredentialManagerService.m388$$Nest$maddSessionLocked(
                    CredentialManagerService.this, callingUserId, clearRequestSession);
            ArrayList arrayList =
                    (ArrayList)
                            CredentialManagerService.m391$$Nest$minitiateProviderSessions(
                                    CredentialManagerService.this, clearRequestSession, List.of());
            if (arrayList.isEmpty()) {
                try {
                    iClearCredentialStateCallback.onError(
                            "UNKNOWN", "No credentials available on this device");
                } catch (RemoteException e) {
                    Slog.e(
                            "CredentialManager",
                            "Issue invoking onError on IClearCredentialStateCallback callback: ",
                            e);
                }
            }
            finalizeAndEmitInitialPhaseMetric(clearRequestSession);
            arrayList.forEach(
                    new CredentialManagerService$CredentialManagerServiceStub$$ExternalSyntheticLambda1());
            return createTransport;
        }

        public final ICancellationSignal executeCreateCredential(
                CreateCredentialRequest createCredentialRequest,
                ICreateCredentialCallback iCreateCredentialCallback,
                String str) {
            long nanoTime = System.nanoTime();
            Slog.i(
                    "CredentialManager",
                    "starting executeCreateCredential with callingPackage: " + str);
            ICancellationSignal createTransport = CancellationSignal.createTransport();
            if (createCredentialRequest.getOrigin() != null) {
                CredentialManagerService.this.mContext.enforceCallingPermission(
                        "android.permission.CREDENTIAL_MANAGER_SET_ORIGIN", null);
            }
            int callingUserId = UserHandle.getCallingUserId();
            int callingUid = Binder.getCallingUid();
            CredentialManagerService.m390$$Nest$menforceCallingPackage(
                    CredentialManagerService.this, str, callingUid);
            Context context = CredentialManagerService.this.getContext();
            CredentialManagerService credentialManagerService = CredentialManagerService.this;
            CreateRequestSession createRequestSession =
                    new CreateRequestSession(
                            context,
                            credentialManagerService.mSessionManager,
                            credentialManagerService.mLock,
                            callingUserId,
                            callingUid,
                            createCredentialRequest,
                            iCreateCredentialCallback,
                            CredentialManagerService.m389$$Nest$mconstructCallingAppInfo(
                                    credentialManagerService,
                                    str,
                                    callingUserId,
                                    createCredentialRequest.getOrigin()),
                            getEnabledProvidersForUser(callingUserId),
                            CredentialManagerService.getPrimaryProvidersForUserId(
                                    CredentialManagerService.this.getContext(), callingUserId),
                            CancellationSignal.fromTransport(createTransport),
                            nanoTime);
            CredentialManagerService.m388$$Nest$maddSessionLocked(
                    CredentialManagerService.this, callingUserId, createRequestSession);
            ArrayList arrayList =
                    (ArrayList)
                            CredentialManagerService.m391$$Nest$minitiateProviderSessions(
                                    CredentialManagerService.this,
                                    createRequestSession,
                                    List.of(createCredentialRequest.getType()));
            if (arrayList.isEmpty()) {
                try {
                    iCreateCredentialCallback.onError(
                            "android.credentials.CreateCredentialException.TYPE_NO_CREATE_OPTIONS",
                            "No create options available.");
                } catch (RemoteException e) {
                    Slog.e(
                            "CredentialManager",
                            "Issue invoking onError on ICreateCredentialCallback callback: ",
                            e);
                }
            }
            finalizeAndEmitInitialPhaseMetric(createRequestSession);
            arrayList.forEach(
                    new CredentialManagerService$CredentialManagerServiceStub$$ExternalSyntheticLambda1());
            return createTransport;
        }

        public final ICancellationSignal executeGetCredential(
                GetCredentialRequest getCredentialRequest,
                IGetCredentialCallback iGetCredentialCallback,
                String str) {
            long nanoTime = System.nanoTime();
            Slog.i(
                    "CredentialManager",
                    "starting executeGetCredential with callingPackage: " + str);
            ICancellationSignal createTransport = CancellationSignal.createTransport();
            int callingUserId = UserHandle.getCallingUserId();
            int callingUid = Binder.getCallingUid();
            CredentialManagerService.m390$$Nest$menforceCallingPackage(
                    CredentialManagerService.this, str, callingUid);
            CredentialManagerService credentialManagerService = CredentialManagerService.this;
            credentialManagerService.getClass();
            if (getCredentialRequest.getOrigin() != null) {
                credentialManagerService.mContext.enforceCallingPermission(
                        "android.permission.CREDENTIAL_MANAGER_SET_ORIGIN", null);
            }
            if (getCredentialRequest.getCredentialOptions().stream()
                    .anyMatch(new CredentialManagerService$$ExternalSyntheticLambda1(0))) {
                credentialManagerService.mContext.enforceCallingPermission(
                        "android.permission.CREDENTIAL_MANAGER_SET_ALLOWED_PROVIDERS", null);
            }
            Context context = CredentialManagerService.this.getContext();
            CredentialManagerService credentialManagerService2 = CredentialManagerService.this;
            GetRequestSession getRequestSession =
                    new GetRequestSession(
                            context,
                            credentialManagerService2.mSessionManager,
                            credentialManagerService2.mLock,
                            callingUserId,
                            callingUid,
                            iGetCredentialCallback,
                            getCredentialRequest,
                            CredentialManagerService.m389$$Nest$mconstructCallingAppInfo(
                                    credentialManagerService2,
                                    str,
                                    callingUserId,
                                    getCredentialRequest.getOrigin()),
                            getEnabledProvidersForUser(callingUserId),
                            CredentialManagerService.getPrimaryProvidersForUserId(
                                    CredentialManagerService.this.getContext(), callingUserId),
                            CancellationSignal.fromTransport(createTransport),
                            nanoTime);
            CredentialManagerService.m388$$Nest$maddSessionLocked(
                    CredentialManagerService.this, callingUserId, getRequestSession);
            List prepareProviderSessions =
                    prepareProviderSessions(getCredentialRequest, getRequestSession);
            if (prepareProviderSessions.isEmpty()) {
                try {
                    iGetCredentialCallback.onError(
                            "android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL",
                            "No credentials available on this device.");
                } catch (RemoteException e) {
                    Slog.e(
                            "CredentialManager",
                            "Issue invoking onError on IGetCredentialCallback callback: "
                                    + e.getMessage());
                }
            }
            prepareProviderSessions.forEach(
                    new CredentialManagerService$CredentialManagerServiceStub$$ExternalSyntheticLambda1());
            return createTransport;
        }

        public final ICancellationSignal executePrepareGetCredential(
                GetCredentialRequest getCredentialRequest,
                IPrepareGetCredentialCallback iPrepareGetCredentialCallback,
                IGetCredentialCallback iGetCredentialCallback,
                String str) {
            long nanoTime = System.nanoTime();
            ICancellationSignal createTransport = CancellationSignal.createTransport();
            if (getCredentialRequest.getOrigin() != null) {
                CredentialManagerService.this.mContext.enforceCallingPermission(
                        "android.permission.CREDENTIAL_MANAGER_SET_ORIGIN", null);
            }
            CredentialManagerService credentialManagerService = CredentialManagerService.this;
            credentialManagerService.getClass();
            if (getCredentialRequest.getCredentialOptions().stream()
                    .anyMatch(new CredentialManagerService$$ExternalSyntheticLambda1(0))) {
                credentialManagerService.mContext.enforceCallingPermission(
                        "android.permission.CREDENTIAL_MANAGER_SET_ALLOWED_PROVIDERS", null);
            }
            int callingUserId = UserHandle.getCallingUserId();
            int callingUid = Binder.getCallingUid();
            CredentialManagerService.m390$$Nest$menforceCallingPackage(
                    CredentialManagerService.this, str, callingUid);
            Context context = CredentialManagerService.this.getContext();
            CredentialManagerService credentialManagerService2 = CredentialManagerService.this;
            List prepareProviderSessions =
                    prepareProviderSessions(
                            getCredentialRequest,
                            new PrepareGetRequestSession(
                                    context,
                                    credentialManagerService2.mSessionManager,
                                    credentialManagerService2.mLock,
                                    callingUserId,
                                    callingUid,
                                    iGetCredentialCallback,
                                    getCredentialRequest,
                                    CredentialManagerService.m389$$Nest$mconstructCallingAppInfo(
                                            credentialManagerService2,
                                            str,
                                            callingUserId,
                                            getCredentialRequest.getOrigin()),
                                    getEnabledProvidersForUser(callingUserId),
                                    CredentialManagerService.getPrimaryProvidersForUserId(
                                            CredentialManagerService.this.getContext(),
                                            callingUserId),
                                    CancellationSignal.fromTransport(createTransport),
                                    nanoTime,
                                    iPrepareGetCredentialCallback));
            if (prepareProviderSessions.isEmpty()) {
                try {
                    iPrepareGetCredentialCallback.onResponse(
                            new PrepareGetCredentialResponseInternal(
                                    PermissionUtils.hasPermission(
                                            CredentialManagerService.this.mContext,
                                            str,
                                            "android.permission.CREDENTIAL_MANAGER_QUERY_CANDIDATE_CREDENTIALS"),
                                    (Set) null,
                                    false,
                                    false,
                                    (PendingIntent) null));
                } catch (RemoteException e) {
                    Slog.e(
                            "CredentialManager",
                            "Issue invoking onError on IGetCredentialCallback callback: "
                                    + e.getMessage());
                }
            }
            prepareProviderSessions.forEach(
                    new CredentialManagerService$CredentialManagerServiceStub$$ExternalSyntheticLambda1());
            return createTransport;
        }

        public final ICancellationSignal getCandidateCredentials(
                GetCredentialRequest getCredentialRequest,
                IGetCandidateCredentialsCallback iGetCandidateCredentialsCallback,
                IBinder iBinder,
                String str) {
            Slog.i(
                    "CredentialManager",
                    "starting getCandidateCredentials with callingPackage: " + str);
            ICancellationSignal createTransport = CancellationSignal.createTransport();
            int callingUserId = UserHandle.getCallingUserId();
            int callingUid = Binder.getCallingUid();
            Context context = CredentialManagerService.this.getContext();
            CredentialManagerService credentialManagerService = CredentialManagerService.this;
            GetCandidateRequestSession getCandidateRequestSession =
                    new GetCandidateRequestSession(
                            context,
                            credentialManagerService.mSessionManager,
                            credentialManagerService.mLock,
                            callingUserId,
                            callingUid,
                            iGetCandidateCredentialsCallback,
                            getCredentialRequest,
                            CredentialManagerService.m389$$Nest$mconstructCallingAppInfo(
                                    credentialManagerService,
                                    str,
                                    callingUserId,
                                    getCredentialRequest.getOrigin()),
                            getEnabledProvidersForUser(callingUserId),
                            CancellationSignal.fromTransport(createTransport),
                            iBinder);
            CredentialManagerService.m388$$Nest$maddSessionLocked(
                    CredentialManagerService.this, callingUserId, getCandidateRequestSession);
            List m391$$Nest$minitiateProviderSessions =
                    CredentialManagerService.m391$$Nest$minitiateProviderSessions(
                            CredentialManagerService.this,
                            getCandidateRequestSession,
                            (List)
                                    getCredentialRequest.getCredentialOptions().stream()
                                            .map(
                                                    new CredentialManagerService$$ExternalSyntheticLambda2(
                                                            1))
                                            .collect(Collectors.toList()));
            InitialPhaseMetric initialPhaseMetric =
                    getCandidateRequestSession.mRequestSessionMetric.mInitialPhaseMetric;
            initialPhaseMetric.mAutofillSessionId = getCandidateRequestSession.mAutofillSessionId;
            initialPhaseMetric.mAutofillRequestId = getCandidateRequestSession.mAutofillRequestId;
            finalizeAndEmitInitialPhaseMetric(getCandidateRequestSession);
            ArrayList arrayList = (ArrayList) m391$$Nest$minitiateProviderSessions;
            if (arrayList.isEmpty()) {
                try {
                    iGetCandidateCredentialsCallback.onError(
                            "android.credentials.GetCandidateCredentialsException.TYPE_NO_CREDENTIAL",
                            "No credentials available on this device.");
                } catch (RemoteException e) {
                    Slog.i(
                            "CredentialManager",
                            "Issue invoking onError on IGetCredentialCallback callback: "
                                    + e.getMessage());
                }
            }
            arrayList.forEach(
                    new CredentialManagerService$CredentialManagerServiceStub$$ExternalSyntheticLambda1());
            return createTransport;
        }

        public final List getCredentialProviderServices(int i, int i2) {
            CredentialManagerService credentialManagerService = CredentialManagerService.this;
            if (!credentialManagerService.hasPermission("android.permission.QUERY_ALL_PACKAGES")
                    && !credentialManagerService.hasPermission(
                            "android.permission.LIST_ENABLED_CREDENTIAL_PROVIDERS")) {
                throw new SecurityException(
                        "Caller is missing permission: QUERY_ALL_PACKAGES or"
                            + " LIST_ENABLED_CREDENTIAL_PROVIDERS");
            }
            MetricUtilities.logApiCalledSimpleV2(
                    ApiName.GET_CREDENTIAL_PROVIDER_SERVICES,
                    ApiStatus.SUCCESS,
                    Binder.getCallingUid());
            return CredentialProviderInfoFactory.getCredentialProviderServices(
                    CredentialManagerService.this.mContext,
                    i,
                    i2,
                    getEnabledProvidersForUser(i),
                    CredentialManagerService.getPrimaryProvidersForUserId(
                            CredentialManagerService.this.mContext, i));
        }

        public final List getCredentialProviderServicesForTesting(int i) {
            CredentialManagerService credentialManagerService = CredentialManagerService.this;
            if (!credentialManagerService.hasPermission("android.permission.QUERY_ALL_PACKAGES")
                    && !credentialManagerService.hasPermission(
                            "android.permission.LIST_ENABLED_CREDENTIAL_PROVIDERS")) {
                throw new SecurityException(
                        "Caller is missing permission: QUERY_ALL_PACKAGES or"
                            + " LIST_ENABLED_CREDENTIAL_PROVIDERS");
            }
            int callingUserId = UserHandle.getCallingUserId();
            return CredentialProviderInfoFactory.getCredentialProviderServicesForTesting(
                    CredentialManagerService.this.mContext,
                    callingUserId,
                    i,
                    getEnabledProvidersForUser(callingUserId),
                    CredentialManagerService.getPrimaryProvidersForUserId(
                            CredentialManagerService.this.mContext, callingUserId));
        }

        public final Set getEnabledProvidersForUser(int i) {
            int handleIncomingUser =
                    ActivityManager.handleIncomingUser(
                            Binder.getCallingPid(),
                            Binder.getCallingUid(),
                            i,
                            false,
                            false,
                            "getEnabledProvidersForUser",
                            null);
            HashSet hashSet = new HashSet();
            String stringForUser =
                    Settings.Secure.getStringForUser(
                            CredentialManagerService.this.mContext.getContentResolver(),
                            "credential_service",
                            handleIncomingUser);
            if (!TextUtils.isEmpty(stringForUser)) {
                for (String str : stringForUser.split(":")) {
                    ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
                    if (unflattenFromString != null) {
                        hashSet.add(unflattenFromString);
                    }
                }
            }
            return hashSet;
        }

        public final boolean isEnabledCredentialProviderService(
                ComponentName componentName, String str) {
            Slog.i(
                    "CredentialManager",
                    "isEnabledCredentialProviderService with componentName: "
                            + componentName.flattenToString());
            int callingUserId = UserHandle.getCallingUserId();
            int callingUid = Binder.getCallingUid();
            CredentialManagerService.m390$$Nest$menforceCallingPackage(
                    CredentialManagerService.this, str, callingUid);
            if (componentName.getPackageName().equals(str)) {
                Set enabledProvidersForUser = getEnabledProvidersForUser(callingUserId);
                MetricUtilities.logApiCalledSimpleV2(
                        ApiName.IS_ENABLED_CREDENTIAL_PROVIDER_SERVICE,
                        ApiStatus.SUCCESS,
                        callingUid);
                return ((HashSet) enabledProvidersForUser).contains(componentName);
            }
            Slog.w(
                    "CredentialManager",
                    "isEnabledCredentialProviderService component name does not match requested"
                        + " component");
            MetricUtilities.logApiCalledSimpleV2(
                    ApiName.IS_ENABLED_CREDENTIAL_PROVIDER_SERVICE, ApiStatus.FAILURE, callingUid);
            throw new IllegalArgumentException(
                    "provided component name does not match does not match requesting component");
        }

        public final boolean isServiceEnabled() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return DeviceConfig.getBoolean(
                        "credential_manager", "enable_credential_manager", true);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final List prepareProviderSessions(
                GetCredentialRequest getCredentialRequest, GetRequestSession getRequestSession) {
            List m391$$Nest$minitiateProviderSessions;
            if (CredentialManagerService.isCredentialDescriptionApiEnabled()) {
                List<CredentialOption> list =
                        getCredentialRequest.getCredentialOptions().stream()
                                .filter(new CredentialManagerService$$ExternalSyntheticLambda1(1))
                                .toList();
                List<CredentialOption> list2 =
                        getCredentialRequest.getCredentialOptions().stream()
                                .filter(new CredentialManagerService$$ExternalSyntheticLambda1(2))
                                .toList();
                CredentialManagerService credentialManagerService = CredentialManagerService.this;
                credentialManagerService.getClass();
                CredentialDescriptionRegistry forUser =
                        CredentialDescriptionRegistry.forUser(UserHandle.getCallingUserId());
                Set set =
                        (Set)
                                list.stream()
                                        .map(
                                                new CredentialManagerService$$ExternalSyntheticLambda2(
                                                        0))
                                        .collect(Collectors.toSet());
                HashSet hashSet = new HashSet();
                for (String str : ((HashMap) forUser.mCredentialDescriptions).keySet()) {
                    for (CredentialDescription credentialDescription :
                            (Set) ((HashMap) forUser.mCredentialDescriptions).get(str)) {
                        Set<String> supportedElementKeys =
                                credentialDescription.getSupportedElementKeys();
                        Iterator it = set.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            if (supportedElementKeys.containsAll((Set) it.next())) {
                                hashSet.add(
                                        new CredentialDescriptionRegistry.FilterResult(
                                                str,
                                                credentialDescription.getSupportedElementKeys(),
                                                credentialDescription.getCredentialEntries()));
                                break;
                            }
                        }
                    }
                }
                HashSet hashSet2 = new HashSet();
                Iterator it2 = hashSet.iterator();
                while (it2.hasNext()) {
                    CredentialDescriptionRegistry.FilterResult filterResult =
                            (CredentialDescriptionRegistry.FilterResult) it2.next();
                    for (CredentialOption credentialOption : list) {
                        if (filterResult.mElementKeys.containsAll(
                                new HashSet(
                                        credentialOption
                                                .getCredentialRetrievalData()
                                                .getStringArrayList(
                                                        "android.credentials.GetCredentialOption.SUPPORTED_ELEMENT_KEYS")))) {
                            hashSet2.add(new Pair(credentialOption, filterResult));
                        }
                    }
                }
                ArrayList arrayList = new ArrayList();
                Iterator it3 = hashSet2.iterator();
                while (it3.hasNext()) {
                    Pair pair = (Pair) it3.next();
                    ProviderRegistryGetSession providerRegistryGetSession =
                            new ProviderRegistryGetSession(
                                    credentialManagerService.mContext,
                                    UserHandle.getCallingUserId(),
                                    getRequestSession,
                                    getRequestSession.mClientAppInfo,
                                    ((CredentialDescriptionRegistry.FilterResult) pair.second)
                                            .mPackageName,
                                    (CredentialOption) pair.first);
                    arrayList.add(providerRegistryGetSession);
                    ComponentName componentName = providerRegistryGetSession.mComponentName;
                    ((ConcurrentHashMap) getRequestSession.mProviders)
                            .put(componentName.flattenToString(), providerRegistryGetSession);
                }
                List m391$$Nest$minitiateProviderSessions2 =
                        CredentialManagerService.m391$$Nest$minitiateProviderSessions(
                                CredentialManagerService.this,
                                getRequestSession,
                                (List)
                                        list2.stream()
                                                .map(
                                                        new CredentialManagerService$$ExternalSyntheticLambda2(
                                                                1))
                                                .collect(Collectors.toList()));
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                linkedHashSet.addAll(m391$$Nest$minitiateProviderSessions2);
                linkedHashSet.addAll(arrayList);
                m391$$Nest$minitiateProviderSessions = new ArrayList(linkedHashSet);
            } else {
                m391$$Nest$minitiateProviderSessions =
                        CredentialManagerService.m391$$Nest$minitiateProviderSessions(
                                CredentialManagerService.this,
                                getRequestSession,
                                (List)
                                        getCredentialRequest.getCredentialOptions().stream()
                                                .map(
                                                        new CredentialManagerService$$ExternalSyntheticLambda2(
                                                                1))
                                                .collect(Collectors.toList()));
            }
            finalizeAndEmitInitialPhaseMetric(getRequestSession);
            return m391$$Nest$minitiateProviderSessions;
        }

        public final void registerCredentialDescription(
                RegisterCredentialDescriptionRequest registerCredentialDescriptionRequest,
                String str) {
            Slog.i(
                    "CredentialManager",
                    "registerCredentialDescription with callingPackage: " + str);
            if (!CredentialManagerService.isCredentialDescriptionApiEnabled()) {
                throw new UnsupportedOperationException("Feature not supported");
            }
            CredentialManagerService.m390$$Nest$menforceCallingPackage(
                    CredentialManagerService.this, str, Binder.getCallingUid());
            CredentialDescriptionRegistry forUser =
                    CredentialDescriptionRegistry.forUser(UserHandle.getCallingUserId());
            if (!((HashMap) forUser.mCredentialDescriptions).containsKey(str)) {
                ((HashMap) forUser.mCredentialDescriptions).put(str, new HashSet());
            }
            if (forUser.mTotalDescriptionCount > 128
                    || ((Set) ((HashMap) forUser.mCredentialDescriptions).get(str)).size() > 16) {
                return;
            }
            Set<CredentialDescription> credentialDescriptions =
                    registerCredentialDescriptionRequest.getCredentialDescriptions();
            int size = ((Set) ((HashMap) forUser.mCredentialDescriptions).get(str)).size();
            ((Set) ((HashMap) forUser.mCredentialDescriptions).get(str))
                    .addAll(credentialDescriptions);
            forUser.mTotalDescriptionCount =
                    (((Set) ((HashMap) forUser.mCredentialDescriptions).get(str)).size() - size)
                            + forUser.mTotalDescriptionCount;
        }

        public final void setEnabledProviders(
                List list,
                List list2,
                int i,
                ISetEnabledProvidersCallback iSetEnabledProvidersCallback) {
            int callingUid = Binder.getCallingUid();
            boolean hasPermission =
                    CredentialManagerService.this.hasPermission(
                            "android.permission.WRITE_SECURE_SETTINGS");
            ApiStatus apiStatus = ApiStatus.FAILURE;
            if (!hasPermission) {
                try {
                    MetricUtilities.logApiCalledSimpleV2(
                            ApiName.SET_ENABLED_PROVIDERS, apiStatus, callingUid);
                    iSetEnabledProvidersCallback.onError(
                            "permission_denied",
                            "Caller is missing WRITE_SECURE_SETTINGS permission");
                    return;
                } catch (RemoteException e) {
                    MetricUtilities.logApiCalledSimpleV2(
                            ApiName.SET_ENABLED_PROVIDERS, apiStatus, callingUid);
                    Slog.e("CredentialManager", "Issue with invoking response: ", e);
                    return;
                }
            }
            int handleIncomingUser =
                    ActivityManager.handleIncomingUser(
                            Binder.getCallingPid(),
                            Binder.getCallingUid(),
                            i,
                            false,
                            false,
                            "setEnabledProviders",
                            null);
            HashSet hashSet = new HashSet(list2);
            hashSet.addAll(list);
            boolean putStringForUser =
                    Settings.Secure.putStringForUser(
                            CredentialManagerService.this.getContext().getContentResolver(),
                            "credential_service",
                            String.join(":", hashSet),
                            handleIncomingUser);
            boolean putStringForUser2 =
                    Settings.Secure.putStringForUser(
                            CredentialManagerService.this.getContext().getContentResolver(),
                            "credential_service_primary",
                            String.join(":", list),
                            handleIncomingUser);
            if (!putStringForUser || !putStringForUser2) {
                Slog.e(
                        "CredentialManager",
                        "Failed to store setting containing enabled or primary providers");
                try {
                    MetricUtilities.logApiCalledSimpleV2(
                            ApiName.SET_ENABLED_PROVIDERS, apiStatus, callingUid);
                    iSetEnabledProvidersCallback.onError(
                            "failed_setting_store",
                            "Failed to store setting containing enabled or primary providers");
                } catch (RemoteException e2) {
                    MetricUtilities.logApiCalledSimpleV2(
                            ApiName.SET_ENABLED_PROVIDERS, apiStatus, callingUid);
                    Slog.e("CredentialManager", "Issue with invoking error response: ", e2);
                    return;
                }
            }
            try {
                MetricUtilities.logApiCalledSimpleV2(
                        ApiName.SET_ENABLED_PROVIDERS, ApiStatus.SUCCESS, callingUid);
                iSetEnabledProvidersCallback.onResponse();
            } catch (RemoteException e3) {
                MetricUtilities.logApiCalledSimpleV2(
                        ApiName.SET_ENABLED_PROVIDERS, apiStatus, callingUid);
                Slog.e("CredentialManager", "Issue with invoking response: ", e3);
            }
        }

        public final void unregisterCredentialDescription(
                UnregisterCredentialDescriptionRequest unregisterCredentialDescriptionRequest,
                String str) {
            Slog.i(
                    "CredentialManager",
                    "unregisterCredentialDescription with callingPackage: " + str);
            if (!CredentialManagerService.isCredentialDescriptionApiEnabled()) {
                throw new UnsupportedOperationException("Feature not supported");
            }
            CredentialManagerService.m390$$Nest$menforceCallingPackage(
                    CredentialManagerService.this, str, Binder.getCallingUid());
            CredentialDescriptionRegistry forUser =
                    CredentialDescriptionRegistry.forUser(UserHandle.getCallingUserId());
            if (((HashMap) forUser.mCredentialDescriptions).containsKey(str)) {
                int size = ((Set) ((HashMap) forUser.mCredentialDescriptions).get(str)).size();
                ((Set) ((HashMap) forUser.mCredentialDescriptions).get(str))
                        .removeAll(
                                unregisterCredentialDescriptionRequest.getCredentialDescriptions());
                forUser.mTotalDescriptionCount -=
                        size - ((Set) ((HashMap) forUser.mCredentialDescriptions).get(str)).size();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SessionManager {
        public SessionManager() {}
    }

    /* renamed from: -$$Nest$maddSessionLocked, reason: not valid java name */
    public static void m388$$Nest$maddSessionLocked(
            CredentialManagerService credentialManagerService,
            int i,
            RequestSession requestSession) {
        synchronized (credentialManagerService.mLock) {
            SessionManager sessionManager = credentialManagerService.mSessionManager;
            IBinder iBinder = requestSession.mRequestId;
            CredentialManagerService credentialManagerService2 = CredentialManagerService.this;
            if (credentialManagerService2.mRequestSessions.get(i) == null) {
                credentialManagerService2.mRequestSessions.put(i, new HashMap());
            }
            ((Map) credentialManagerService2.mRequestSessions.get(i)).put(iBinder, requestSession);
        }
    }

    /* renamed from: -$$Nest$mconstructCallingAppInfo, reason: not valid java name */
    public static CallingAppInfo m389$$Nest$mconstructCallingAppInfo(
            CredentialManagerService credentialManagerService, String str, int i, String str2) {
        credentialManagerService.getClass();
        try {
            return new CallingAppInfo(
                    str,
                    credentialManagerService
                            .getContext()
                            .getPackageManager()
                            .getPackageInfoAsUser(
                                    str, PackageManager.PackageInfoFlags.of(134217728L), i)
                            .signingInfo,
                    str2);
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e("CredentialManager", "Issue while retrieving signatureInfo : ", e);
            return new CallingAppInfo(str, null, str2);
        }
    }

    /* renamed from: -$$Nest$menforceCallingPackage, reason: not valid java name */
    public static void m390$$Nest$menforceCallingPackage(
            CredentialManagerService credentialManagerService, String str, int i) {
        try {
            if (credentialManagerService
                            .mContext
                            .createContextAsUser(UserHandle.getUserHandleForUid(i), 0)
                            .getPackageManager()
                            .getPackageUid(str, PackageManager.PackageInfoFlags.of(0L))
                    != i) {
                throw new SecurityException(
                        VpnManagerService$$ExternalSyntheticOutline0.m(
                                i, str, " does not belong to uid "));
            }
        } catch (PackageManager.NameNotFoundException unused) {
            throw new SecurityException(
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, " not found"));
        }
    }

    /* renamed from: -$$Nest$minitiateProviderSessions, reason: not valid java name */
    public static List m391$$Nest$minitiateProviderSessions(
            CredentialManagerService credentialManagerService,
            RequestSession requestSession,
            List list) {
        credentialManagerService.getClass();
        ArrayList arrayList = new ArrayList();
        int callingUserId = UserHandle.getCallingUserId();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (credentialManagerService.mLock) {
                try {
                    ArrayList arrayList2 = new ArrayList();
                    List serviceListForUserLocked =
                            credentialManagerService.getServiceListForUserLocked(callingUserId);
                    if (serviceListForUserLocked != null && !serviceListForUserLocked.isEmpty()) {
                        arrayList2.addAll(serviceListForUserLocked);
                    }
                    arrayList2.addAll(
                            credentialManagerService.getOrConstructSystemServiceListLock(
                                    callingUserId));
                    Iterator it = arrayList2.iterator();
                    while (it.hasNext()) {
                        CredentialManagerServiceImpl credentialManagerServiceImpl =
                                (CredentialManagerServiceImpl) it.next();
                        synchronized (credentialManagerService.mLock) {
                            try {
                                ProviderSession initiateProviderSessionForRequestLocked =
                                        credentialManagerServiceImpl
                                                .initiateProviderSessionForRequestLocked(
                                                        requestSession, list);
                                if (initiateProviderSessionForRequestLocked != null) {
                                    arrayList.add(initiateProviderSessionForRequestLocked);
                                }
                            } finally {
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return arrayList;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public CredentialManagerService(Context context) {
        super(
                context,
                new SecureSettingsServiceNameResolver(context, "credential_service", true),
                null,
                4);
        this.mSystemServicesCacheList = new SparseArray();
        this.mRequestSessions = new SparseArray();
        this.mSessionManager = new SessionManager();
        this.mContext = context;
    }

    public static Set getPrimaryProvidersForUserId(Context context, int i) {
        int handleIncomingUser =
                ActivityManager.handleIncomingUser(
                        Binder.getCallingPid(),
                        Binder.getCallingUid(),
                        i,
                        false,
                        false,
                        "getPrimaryProvidersForUserId",
                        null);
        new SparseArray();
        new SparseBooleanArray();
        TextUtils.SimpleStringSplitter simpleStringSplitter =
                new TextUtils.SimpleStringSplitter(':');
        String stringForUser =
                Settings.Secure.getStringForUser(
                        context.getContentResolver(),
                        "credential_service_primary",
                        handleIncomingUser);
        ArraySet arraySet = new ArraySet();
        if (!TextUtils.isEmpty(stringForUser)) {
            simpleStringSplitter.setString(stringForUser);
            while (simpleStringSplitter.hasNext()) {
                String next = simpleStringSplitter.next();
                if (!TextUtils.isEmpty(next)) {
                    arraySet.add(next);
                }
            }
        }
        String[] strArr = (String[]) arraySet.toArray(new String[arraySet.size()]);
        if (strArr == null) {
            return new HashSet();
        }
        HashSet hashSet = new HashSet();
        for (String str : strArr) {
            ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
            if (unflattenFromString == null) {
                HeimdAllFsService$$ExternalSyntheticOutline0.m(
                        "Primary provider component name unflatten from string error: ",
                        str,
                        "CredentialManager");
            } else {
                hashSet.add(unflattenFromString);
            }
        }
        return hashSet;
    }

    public static Set getStoredProviders(String str, String str2) {
        HashSet hashSet = new HashSet();
        if (str != null && str2 != null) {
            for (String str3 : str.split(":")) {
                if (TextUtils.isEmpty(str3) || str3.equals("null")) {
                    Slog.d("CredentialManager", "provider component name is empty or null");
                } else {
                    ComponentName unflattenFromString = ComponentName.unflattenFromString(str3);
                    if (unflattenFromString != null
                            && !unflattenFromString.getPackageName().equals(str2)) {
                        hashSet.add(unflattenFromString.flattenToString());
                    }
                }
            }
        }
        return hashSet;
    }

    public static boolean isCredentialDescriptionApiEnabled() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return DeviceConfig.getBoolean(
                    "credential_manager", "enable_credential_description_api", false);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final List getOrConstructSystemServiceListLock(final int i) {
        List list = (List) this.mSystemServicesCacheList.get(i);
        if (list != null && list.size() != 0) {
            return list;
        }
        final ArrayList arrayList = new ArrayList();
        CredentialProviderInfoFactory.getAvailableSystemServices(
                        this.mContext, i, false, new HashSet())
                .forEach(
                        new Consumer() { // from class:
                                         // com.android.server.credentials.CredentialManagerService$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                CredentialManagerService credentialManagerService =
                                        CredentialManagerService.this;
                                List list2 = arrayList;
                                CredentialProviderInfo credentialProviderInfo =
                                        (CredentialProviderInfo) obj;
                                CredentialManagerServiceImpl credentialManagerServiceImpl =
                                        new CredentialManagerServiceImpl(
                                                credentialManagerService,
                                                credentialManagerService.mLock,
                                                i);
                                Slog.i(
                                        "CredentialManager",
                                        "CredentialManagerServiceImpl constructed for: "
                                                + credentialProviderInfo
                                                        .getServiceInfo()
                                                        .getComponentName()
                                                        .flattenToString());
                                credentialManagerServiceImpl.mInfo = credentialProviderInfo;
                                list2.add(credentialManagerServiceImpl);
                            }
                        });
        this.mSystemServicesCacheList.put(i, arrayList);
        return arrayList;
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final String getServiceSettingsProperty() {
        return "credential_service";
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void handlePackageRemovedMultiModeLocked(int i, String str) {
        ComponentName unflattenFromString;
        Context context = this.mContext;
        Slog.i(
                "CredentialManager",
                AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(
                        i, "updateProvidersWhenPackageRemoved)pkg=", str, " (", ")"));
        String stringForUser =
                Settings.Secure.getStringForUser(
                        context.getContentResolver(), "credential_service_primary", i);
        if (stringForUser == null) {
            Slog.w("CredentialManager", "settings key is null");
        } else {
            Set storedProviders = getStoredProviders(stringForUser, str);
            if (Settings.Secure.putStringForUser(
                    context.getContentResolver(),
                    "credential_service_primary",
                    String.join(":", storedProviders),
                    null,
                    false,
                    i,
                    true)) {
                String stringForUser2 =
                        Settings.Secure.getStringForUser(
                                context.getContentResolver(), "autofill_service", i);
                String string = context.getResources().getString(R.string.date_time);
                if (stringForUser2 != null
                        && ((HashSet) storedProviders).isEmpty()
                        && !TextUtils.equals(stringForUser2, string)
                        && (unflattenFromString = ComponentName.unflattenFromString(stringForUser2))
                                != null
                        && unflattenFromString.getPackageName().equals(str)
                        && !Settings.Secure.putStringForUser(
                                context.getContentResolver(),
                                "autofill_service",
                                "",
                                null,
                                false,
                                i,
                                true)) {
                    BootReceiver$$ExternalSyntheticOutline0.m(
                            "Failed to remove autofill package: ", str, "CredentialManager");
                }
                if (!Settings.Secure.putStringForUser(
                        context.getContentResolver(),
                        "credential_service",
                        String.join(
                                ":",
                                getStoredProviders(
                                        Settings.Secure.getStringForUser(
                                                context.getContentResolver(),
                                                "credential_service",
                                                i),
                                        str)),
                        null,
                        false,
                        i,
                        true)) {
                    BootReceiver$$ExternalSyntheticOutline0.m(
                            "Failed to remove secondary package: ", str, "CredentialManager");
                }
            } else {
                BootReceiver$$ExternalSyntheticOutline0.m(
                        "Failed to remove primary package: ", str, "CredentialManager");
            }
        }
        List<CredentialManagerServiceImpl> peekServiceListForUserLocked =
                peekServiceListForUserLocked(i);
        if (peekServiceListForUserLocked == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (CredentialManagerServiceImpl credentialManagerServiceImpl :
                peekServiceListForUserLocked) {
            if (credentialManagerServiceImpl != null
                    && str.equals(
                            credentialManagerServiceImpl
                                    .mInfo
                                    .getServiceInfo()
                                    .getComponentName()
                                    .getPackageName())) {
                arrayList.add(credentialManagerServiceImpl);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            CredentialManagerServiceImpl credentialManagerServiceImpl2 =
                    (CredentialManagerServiceImpl) it.next();
            if (this.mServicesCacheList.get(i) != null) {
                ((List) this.mServicesCacheList.get(i)).remove(credentialManagerServiceImpl2);
            }
            if (this.mSystemServicesCacheList.get(i) != null) {
                ((List) this.mSystemServicesCacheList.get(i)).remove(credentialManagerServiceImpl2);
            }
            CredentialDescriptionRegistry forUser = CredentialDescriptionRegistry.forUser(i);
            String servicePackageName = credentialManagerServiceImpl2.getServicePackageName();
            if (((HashMap) forUser.mCredentialDescriptions).containsKey(servicePackageName)) {
                ((HashMap) forUser.mCredentialDescriptions).remove(servicePackageName);
            }
        }
    }

    public final boolean hasPermission(String str) {
        boolean z = this.mContext.checkCallingOrSelfPermission(str) == 0;
        if (!z) {
            Slog.e("CredentialManager", "Caller does not have permission: ".concat(str));
        }
        return z;
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final List newServiceListLocked(int i, String[] strArr) {
        getOrConstructSystemServiceListLock(i);
        if (strArr == null || strArr.length == 0) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList(strArr.length);
        for (String str : strArr) {
            if (!TextUtils.isEmpty(str)) {
                try {
                    arrayList.add(new CredentialManagerServiceImpl(this, this.mLock, i, str));
                } catch (PackageManager.NameNotFoundException | SecurityException e) {
                    Slog.e("CredentialManager", "Unable to add serviceInfo : ", e);
                }
            }
        }
        return arrayList;
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final AbstractPerUserSystemService newServiceLocked(int i, boolean z) {
        Slog.w(
                "CredentialManager",
                "Should not be here - CredentialManagerService is configured to use multiple"
                    + " services");
        return null;
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("credential", new CredentialManagerServiceStub());
    }

    @Override // com.android.server.infra.AbstractMasterSystemService,
              // com.android.server.SystemService
    public final void onUserStopped(SystemService.TargetUser targetUser) {
        super.onUserStopped(targetUser);
        int userIdentifier = targetUser.getUserIdentifier();
        ReentrantLock reentrantLock = CredentialDescriptionRegistry.sLock;
        reentrantLock.lock();
        try {
            CredentialDescriptionRegistry.sCredentialDescriptionSessionPerUser.remove(
                    userIdentifier);
            reentrantLock.unlock();
        } catch (Throwable th) {
            CredentialDescriptionRegistry.sLock.unlock();
            throw th;
        }
    }
}
