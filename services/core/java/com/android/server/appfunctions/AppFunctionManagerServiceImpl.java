package com.android.server.appfunctions;

import android.app.appfunctions.AppFunctionException;
import android.app.appfunctions.AppFunctionExecutionRecord;
import android.app.appfunctions.AppFunctionManagerHelper;
import android.app.appfunctions.AppFunctionRuntimeMetadata;
import android.app.appfunctions.ExecuteAppFunctionAidlRequest;
import android.app.appfunctions.IAppFunctionEnabledCallback;
import android.app.appfunctions.IAppFunctionManager;
import android.app.appfunctions.ICancellationCallback;
import android.app.appfunctions.IExecuteAppFunctionCallback;
import android.app.appfunctions.SafeOneTimeExecuteAppFunctionCallback;
import android.app.appsearch.AppSearchBatchResult;
import android.app.appsearch.AppSearchManager;
import android.app.appsearch.GenericDocument;
import android.app.appsearch.GetByDocumentIdRequest;
import android.app.appsearch.PutDocumentsRequest;
import android.app.appsearch.observer.DocumentChangeInfo;
import android.app.appsearch.observer.ObserverCallback;
import android.app.appsearch.observer.SchemaChangeInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.OutcomeReceiver;
import android.os.ParcelableException;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.text.TextUtils;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.appfunctions.AppFunctionManagerServiceImpl;
import com.android.server.appfunctions.RemoteServiceCallerImpl;
import com.android.server.appfunctions.RemoteServiceCallerImpl.OneOffServiceConnection;
import com.android.server.appfunctions.agentpolicy.AppFunctionAgentPolicyManager;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;
import java.util.function.Function;

public final class AppFunctionManagerServiceImpl extends IAppFunctionManager.Stub {
    public final AppFunctionAgentPolicyManager mAppFunctionAgentPolicyManager;
    public final AppFunctionLoggerHelper mAppFunctionLoggerHelper;
    public final CallerValidator mCallerValidator;
    public final Context mContext;
    public final ServiceHelper mInternalServiceHelper;
    public final Map mLocks = new WeakHashMap();
    public final RemoteServiceCaller mRemoteServiceCaller;
    public final ServiceConfig mServiceConfig;

    public final class AppFunctionMetadataObserver implements ObserverCallback {
        public final MetadataSyncAdapter mPerUserMetadataSyncAdapter;

        public AppFunctionMetadataObserver(UserHandle userHandle, Context context, AppFunctionAgentPolicyManager appFunctionAgentPolicyManager) {
            this.mPerUserMetadataSyncAdapter = MetadataSyncPerUser.getPerUserMetadataSyncAdapter(userHandle, context, appFunctionAgentPolicyManager);
        }

        @Override // android.app.appsearch.observer.ObserverCallback
        public final void onDocumentChanged(DocumentChangeInfo documentChangeInfo) {
            if (this.mPerUserMetadataSyncAdapter != null && documentChangeInfo.getDatabaseName().equals("apps-db") && documentChangeInfo.getNamespace().equals("app_functions")) {
                this.mPerUserMetadataSyncAdapter.submitSyncRequest(false);
            }
        }

        @Override // android.app.appsearch.observer.ObserverCallback
        public final void onSchemaChanged(SchemaChangeInfo schemaChangeInfo) {
            if (this.mPerUserMetadataSyncAdapter != null && schemaChangeInfo.getDatabaseName().equals("apps-db")) {
                Iterator<String> it = schemaChangeInfo.getChangedSchemaNames().iterator();
                while (it.hasNext()) {
                    if (it.next().startsWith("AppFunctionStaticMetadata")) {
                        this.mPerUserMetadataSyncAdapter.submitSyncRequest(false);
                        return;
                    }
                }
            }
        }
    }

    class DisabledAppFunctionException extends RuntimeException {
        public /* synthetic */ DisabledAppFunctionException() {
            this("The app function is disabled");
        }

        private DisabledAppFunctionException(String str) {
            super(str);
        }
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.server.appfunctions.AppFunctionManagerServiceImpl$$ExternalSyntheticLambda3] */
    public AppFunctionManagerServiceImpl(Context context, RemoteServiceCaller remoteServiceCaller, CallerValidator callerValidator, ServiceHelper serviceHelper, ServiceConfig serviceConfig) {
        Objects.requireNonNull(context);
        this.mContext = context;
        Objects.requireNonNull(remoteServiceCaller);
        this.mRemoteServiceCaller = remoteServiceCaller;
        Objects.requireNonNull(callerValidator);
        this.mCallerValidator = callerValidator;
        Objects.requireNonNull(serviceHelper);
        this.mInternalServiceHelper = serviceHelper;
        this.mServiceConfig = serviceConfig;
        this.mAppFunctionAgentPolicyManager = new AppFunctionAgentPolicyManager(context, new Runnable() { // from class: com.android.server.appfunctions.AppFunctionManagerServiceImpl$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                AppFunctionManagerServiceImpl appFunctionManagerServiceImpl = AppFunctionManagerServiceImpl.this;
                Iterator it = UserManager.get(appFunctionManagerServiceImpl.mContext).getUsers().iterator();
                while (it.hasNext()) {
                    appFunctionManagerServiceImpl.trySyncRuntimeMetadata(new SystemService.TargetUser((UserInfo) it.next()), true);
                }
            }
        });
        this.mAppFunctionLoggerHelper = new AppFunctionLoggerHelper(context);
    }

    public static AppFunctionRuntimeMetadata getRuntimeMetadataGenericDocument(String str, String str2, FutureAppSearchSessionImpl futureAppSearchSessionImpl) {
        String documentIdForAppFunction = AppFunctionRuntimeMetadata.getDocumentIdForAppFunction(str, str2);
        GetByDocumentIdRequest build = new GetByDocumentIdRequest.Builder("app_functions_runtime").addIds(documentIdForAppFunction).build();
        Objects.requireNonNull(build);
        AppSearchBatchResult appSearchBatchResult = (AppSearchBatchResult) futureAppSearchSessionImpl.getSessionAsync().thenCompose(new FutureAppSearchSessionImpl$$ExternalSyntheticLambda0(futureAppSearchSessionImpl, build, 4)).get();
        if (appSearchBatchResult.isSuccess()) {
            return new AppFunctionRuntimeMetadata((GenericDocument) appSearchBatchResult.getSuccesses().get(documentIdForAppFunction));
        }
        throw new IllegalArgumentException(XmlUtils$$ExternalSyntheticOutline0.m("Function ", str2, " does not exist"));
    }

    public static AppFunctionException mapExceptionToExecuteAppFunctionResponse(Throwable th) {
        if (th instanceof CompletionException) {
            th = th.getCause();
        }
        int i = 2000;
        if (th instanceof AppSearchException) {
            int resultCode = ((AppSearchException) th).getResultCode();
            if (resultCode == 0) {
                throw new IllegalArgumentException("This method can only be used to convert failure result codes.");
            }
            if (resultCode == 6) {
                i = 1003;
            }
        } else if (th instanceof SecurityException) {
            i = 1000;
        } else if (th instanceof DisabledAppFunctionException) {
            i = 1002;
        }
        return new AppFunctionException(i, th.getMessage());
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "AppFunctionManagerServiceImpl", printWriter)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
                AppFunctionDumpHelper.dumpAppFunctionsState(this.mContext, indentingPrintWriter);
                this.mAppFunctionLoggerHelper.dump(indentingPrintWriter, strArr);
                this.mAppFunctionAgentPolicyManager.dump(indentingPrintWriter);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final ICancellationSignal executeAppFunction(final ExecuteAppFunctionAidlRequest executeAppFunctionAidlRequest, final IExecuteAppFunctionCallback iExecuteAppFunctionCallback) {
        Objects.requireNonNull(executeAppFunctionAidlRequest);
        Objects.requireNonNull(iExecuteAppFunctionCallback);
        AppFunctionExecutionRecord appFunctionExecutionRecord = new AppFunctionExecutionRecord(executeAppFunctionAidlRequest);
        final AppFunctionLoggerHelper appFunctionLoggerHelper = this.mAppFunctionLoggerHelper;
        Objects.requireNonNull(appFunctionLoggerHelper);
        final SafeOneTimeExecuteAppFunctionCallback safeOneTimeExecuteAppFunctionCallback = new SafeOneTimeExecuteAppFunctionCallback(iExecuteAppFunctionCallback, new Consumer() { // from class: com.android.server.appfunctions.AppFunctionManagerServiceImpl$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                final AppFunctionLoggerHelper appFunctionLoggerHelper2 = AppFunctionLoggerHelper.this;
                final AppFunctionExecutionRecord appFunctionExecutionRecord2 = (AppFunctionExecutionRecord) obj;
                AppFunctionExecutionRecord[] appFunctionExecutionRecordArr = appFunctionLoggerHelper2.mAppFunctionExecutionHistory;
                int i = appFunctionLoggerHelper2.mAppFunctionExecutionHistoryIdx;
                appFunctionExecutionRecordArr[i] = appFunctionExecutionRecord2;
                appFunctionLoggerHelper2.mAppFunctionExecutionHistoryIdx = (i + 1) % 10;
                if (AppFunctionLoggerHelper.SA_LOG_ENABLED) {
                    BackgroundThread.getHandler().post(new Runnable() { // from class: com.android.server.appfunctions.AppFunctionLoggerHelper$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            AppFunctionLoggerHelper appFunctionLoggerHelper3 = AppFunctionLoggerHelper.this;
                            AppFunctionExecutionRecord appFunctionExecutionRecord3 = appFunctionExecutionRecord2;
                            appFunctionLoggerHelper3.getClass();
                            try {
                                Bundle bundle = new Bundle();
                                bundle.putString("tracking_id", "4AD-399-995651");
                                bundle.putString(LauncherConfigurationInternal.KEY_FEATURE_INT, "CRRA001");
                                bundle.putString("type", "ev");
                                bundle.putString("pkg_name", "com.android.server.appfunctions");
                                HashMap dimensionMap = appFunctionLoggerHelper3.getDimensionMap(appFunctionExecutionRecord3);
                                if (!AppFunctionLoggerHelper.USER_BUILD && !AppFunctionLoggerHelper.SHIP_BUILD) {
                                    for (String str : bundle.keySet()) {
                                        Slog.d("AppFunctionLoggerHelper", "sendSamsungAnalyticsLog key :" + str + " : " + bundle.get(str));
                                    }
                                    for (Map.Entry entry : dimensionMap.entrySet()) {
                                        Slog.d("AppFunctionLoggerHelper", "sendSamsungAnalyticsLog dimension key :" + ((String) entry.getKey()) + " : " + ((String) entry.getValue()));
                                    }
                                }
                                bundle.putSerializable("dimension", dimensionMap);
                                Intent intent = new Intent();
                                intent.setAction("com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY");
                                intent.setPackage("com.sec.android.diagmonagent");
                                intent.putExtras(bundle);
                                appFunctionLoggerHelper3.mContext.sendBroadcast(intent);
                            } catch (Exception e) {
                                Slog.e("AppFunctionLoggerHelper", "sendSamsungAnalyticsLog", e);
                            }
                        }
                    });
                }
            }
        }, appFunctionExecutionRecord);
        try {
            this.mCallerValidator.verifyTargetUserHandle(this.mCallerValidator.validateCallingPackage(executeAppFunctionAidlRequest.getCallingPackage()), executeAppFunctionAidlRequest.getUserHandle());
            final int callingUid = Binder.getCallingUid();
            final int callingPid = Binder.getCallingPid();
            final ICancellationSignal createTransport = CancellationSignal.createTransport();
            ((ThreadPoolExecutor) AppFunctionExecutors.THREAD_POOL_EXECUTOR).execute(new Runnable() { // from class: com.android.server.appfunctions.AppFunctionManagerServiceImpl$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    AppFunctionManagerServiceImpl appFunctionManagerServiceImpl = AppFunctionManagerServiceImpl.this;
                    ExecuteAppFunctionAidlRequest executeAppFunctionAidlRequest2 = executeAppFunctionAidlRequest;
                    int i = callingUid;
                    int i2 = callingPid;
                    ICancellationSignal iCancellationSignal = createTransport;
                    SafeOneTimeExecuteAppFunctionCallback safeOneTimeExecuteAppFunctionCallback2 = safeOneTimeExecuteAppFunctionCallback;
                    IExecuteAppFunctionCallback iExecuteAppFunctionCallback2 = iExecuteAppFunctionCallback;
                    appFunctionManagerServiceImpl.getClass();
                    try {
                        appFunctionManagerServiceImpl.executeAppFunctionInternal(executeAppFunctionAidlRequest2, i, i2, iCancellationSignal, safeOneTimeExecuteAppFunctionCallback2, iExecuteAppFunctionCallback2.asBinder());
                    } catch (Exception e) {
                        safeOneTimeExecuteAppFunctionCallback2.onError(AppFunctionManagerServiceImpl.mapExceptionToExecuteAppFunctionResponse(e));
                    }
                }
            });
            return createTransport;
        } catch (SecurityException e) {
            safeOneTimeExecuteAppFunctionCallback.onError(new AppFunctionException(1000, e.getMessage()));
            return null;
        }
    }

    public final void executeAppFunctionInternal(final ExecuteAppFunctionAidlRequest executeAppFunctionAidlRequest, int i, int i2, final ICancellationSignal iCancellationSignal, final SafeOneTimeExecuteAppFunctionCallback safeOneTimeExecuteAppFunctionCallback, final IBinder iBinder) {
        final UserHandle userHandle = executeAppFunctionAidlRequest.getUserHandle();
        if (this.mCallerValidator.isUserOrganizationManaged(userHandle)) {
            safeOneTimeExecuteAppFunctionCallback.onError(new AppFunctionException(2000, "Cannot run on a device with a device owner or from the managed profile."));
            return;
        }
        final String targetPackageName = executeAppFunctionAidlRequest.getClientRequest().getTargetPackageName();
        if (TextUtils.isEmpty(targetPackageName)) {
            safeOneTimeExecuteAppFunctionCallback.onError(new AppFunctionException(1001, "Target package name cannot be empty."));
            return;
        }
        final int i3 = 0;
        CompletableFuture thenAccept = this.mCallerValidator.verifyCallerCanExecuteAppFunction(i, i2, userHandle, executeAppFunctionAidlRequest.getCallingPackage(), targetPackageName, executeAppFunctionAidlRequest.getClientRequest().getFunctionIdentifier(), this.mAppFunctionAgentPolicyManager).thenAccept(new Consumer() { // from class: com.android.server.appfunctions.AppFunctionManagerServiceImpl$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Boolean bool = (Boolean) obj;
                switch (i3) {
                    case 0:
                        if (!bool.booleanValue()) {
                            throw new SecurityException("Caller does not have permission to execute the appfunction");
                        }
                        return;
                    default:
                        if (!bool.booleanValue()) {
                            throw new AppFunctionManagerServiceImpl.DisabledAppFunctionException();
                        }
                        return;
                }
            }
        });
        final int i4 = 0;
        CompletableFuture thenCompose = thenAccept.thenCompose(new Function(this) { // from class: com.android.server.appfunctions.AppFunctionManagerServiceImpl$$ExternalSyntheticLambda9
            public final /* synthetic */ AppFunctionManagerServiceImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                switch (i4) {
                    case 0:
                        AppFunctionManagerServiceImpl appFunctionManagerServiceImpl = this.f$0;
                        ExecuteAppFunctionAidlRequest executeAppFunctionAidlRequest2 = (ExecuteAppFunctionAidlRequest) executeAppFunctionAidlRequest;
                        appFunctionManagerServiceImpl.getClass();
                        String functionIdentifier = executeAppFunctionAidlRequest2.getClientRequest().getFunctionIdentifier();
                        String targetPackageName2 = executeAppFunctionAidlRequest2.getClientRequest().getTargetPackageName();
                        AppSearchManager appSearchManager = (AppSearchManager) appFunctionManagerServiceImpl.mContext.createContextAsUser(executeAppFunctionAidlRequest2.getUserHandle(), 0).getSystemService(AppSearchManager.class);
                        Executor executor = AppFunctionExecutors.THREAD_POOL_EXECUTOR;
                        final AndroidFuture androidFuture = new AndroidFuture();
                        AppFunctionManagerHelper.isAppFunctionEnabled(functionIdentifier, targetPackageName2, appSearchManager, executor, new OutcomeReceiver() { // from class: com.android.server.appfunctions.AppFunctionManagerServiceImpl.1
                            @Override // android.os.OutcomeReceiver
                            public final void onError(Throwable th) {
                                androidFuture.completeExceptionally((Exception) th);
                            }

                            @Override // android.os.OutcomeReceiver
                            public final void onResult(Object obj2) {
                                androidFuture.complete((Boolean) obj2);
                            }
                        });
                        return androidFuture;
                    default:
                        AppFunctionManagerServiceImpl appFunctionManagerServiceImpl2 = this.f$0;
                        SafeOneTimeExecuteAppFunctionCallback safeOneTimeExecuteAppFunctionCallback2 = (SafeOneTimeExecuteAppFunctionCallback) executeAppFunctionAidlRequest;
                        appFunctionManagerServiceImpl2.getClass();
                        safeOneTimeExecuteAppFunctionCallback2.onError(AppFunctionManagerServiceImpl.mapExceptionToExecuteAppFunctionResponse((Throwable) obj));
                        return null;
                }
            }
        });
        final int i5 = 1;
        CompletableFuture<Void> thenAccept2 = thenCompose.thenAccept(new Consumer() { // from class: com.android.server.appfunctions.AppFunctionManagerServiceImpl$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Boolean bool = (Boolean) obj;
                switch (i5) {
                    case 0:
                        if (!bool.booleanValue()) {
                            throw new SecurityException("Caller does not have permission to execute the appfunction");
                        }
                        return;
                    default:
                        if (!bool.booleanValue()) {
                            throw new AppFunctionManagerServiceImpl.DisabledAppFunctionException();
                        }
                        return;
                }
            }
        }).thenAccept(new Consumer() { // from class: com.android.server.appfunctions.AppFunctionManagerServiceImpl$$ExternalSyntheticLambda11
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r2v4, types: [android.os.IBinder$DeathRecipient, com.android.server.appfunctions.RemoteServiceCallerImpl$OneOffServiceConnection$$ExternalSyntheticLambda1] */
            /* JADX WARN: Type inference failed for: r4v1, types: [com.android.server.appfunctions.AppFunctionManagerServiceImpl$2] */
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AppFunctionManagerServiceImpl appFunctionManagerServiceImpl = AppFunctionManagerServiceImpl.this;
                String str = targetPackageName;
                UserHandle userHandle2 = userHandle;
                SafeOneTimeExecuteAppFunctionCallback safeOneTimeExecuteAppFunctionCallback2 = safeOneTimeExecuteAppFunctionCallback;
                ExecuteAppFunctionAidlRequest executeAppFunctionAidlRequest2 = executeAppFunctionAidlRequest;
                ICancellationSignal iCancellationSignal2 = iCancellationSignal;
                IBinder iBinder2 = iBinder;
                Intent resolveAppFunctionService = appFunctionManagerServiceImpl.mInternalServiceHelper.resolveAppFunctionService(str, userHandle2);
                if (resolveAppFunctionService == null) {
                    safeOneTimeExecuteAppFunctionCallback2.onError(new AppFunctionException(2000, "Cannot find the target service."));
                    return;
                }
                final CancellationSignal fromTransport = CancellationSignal.fromTransport(iCancellationSignal2);
                ?? r4 = new ICancellationCallback.Stub() { // from class: com.android.server.appfunctions.AppFunctionManagerServiceImpl.2
                    public final void sendCancellationTransport(ICancellationSignal iCancellationSignal3) {
                        fromTransport.setRemote(iCancellationSignal3);
                    }
                };
                RemoteServiceCaller remoteServiceCaller = appFunctionManagerServiceImpl.mRemoteServiceCaller;
                ((ServiceConfigImpl) appFunctionManagerServiceImpl.mServiceConfig).getClass();
                long j = DeviceConfig.getLong("appfunctions", "execute_app_function_cancellation_timeout_millis", 5000L);
                RunAppFunctionServiceCallback runAppFunctionServiceCallback = new RunAppFunctionServiceCallback(executeAppFunctionAidlRequest2, r4, safeOneTimeExecuteAppFunctionCallback2);
                RemoteServiceCallerImpl remoteServiceCallerImpl = (RemoteServiceCallerImpl) remoteServiceCaller;
                remoteServiceCallerImpl.getClass();
                final RemoteServiceCallerImpl.OneOffServiceConnection oneOffServiceConnection = remoteServiceCallerImpl.new OneOffServiceConnection(resolveAppFunctionService, userHandle2, j, fromTransport, runAppFunctionServiceCallback, iBinder2);
                boolean bindServiceAsUser = remoteServiceCallerImpl.mContext.bindServiceAsUser(resolveAppFunctionService, oneOffServiceConnection, 67108865, userHandle2);
                if (bindServiceAsUser) {
                    fromTransport.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: com.android.server.appfunctions.RemoteServiceCallerImpl$OneOffServiceConnection$$ExternalSyntheticLambda0
                        @Override // android.os.CancellationSignal.OnCancelListener
                        public final void onCancel() {
                            RemoteServiceCallerImpl.OneOffServiceConnection oneOffServiceConnection2 = RemoteServiceCallerImpl.OneOffServiceConnection.this;
                            oneOffServiceConnection2.mCallback.mSafeExecuteAppFunctionCallback.disable();
                            RemoteServiceCallerImpl.this.mHandler.postDelayed(oneOffServiceConnection2.mCancellationTimeoutRunnable, oneOffServiceConnection2.mCancellationTimeoutMillis);
                        }
                    });
                    ?? r2 = new IBinder.DeathRecipient() { // from class: com.android.server.appfunctions.RemoteServiceCallerImpl$OneOffServiceConnection$$ExternalSyntheticLambda1
                        @Override // android.os.IBinder.DeathRecipient
                        public final void binderDied() {
                            RemoteServiceCallerImpl.OneOffServiceConnection oneOffServiceConnection2 = RemoteServiceCallerImpl.OneOffServiceConnection.this;
                            oneOffServiceConnection2.getClass();
                            Slog.w("AppFunctionsServiceCall", "Caller process onDeath signal received");
                            oneOffServiceConnection2.mCancellationSignal.cancel();
                        }
                    };
                    oneOffServiceConnection.mDirectServiceVulture = r2;
                    try {
                        iBinder2.linkToDeath(r2, 0);
                    } catch (RemoteException e) {
                        Slog.w("AppFunctionsServiceCall", "Failed to link to death on " + oneOffServiceConnection.mCallerBinder + ": ", e);
                    }
                } else {
                    oneOffServiceConnection.safeUnbind();
                }
                if (bindServiceAsUser) {
                    return;
                }
                Slog.e("AppFunctionManagerServiceImpl", "Failed to bind to the AppFunctionService");
                safeOneTimeExecuteAppFunctionCallback2.onError(new AppFunctionException(2000, "Failed to bind the AppFunctionService."));
            }
        });
        final int i6 = 1;
        thenAccept2.exceptionally(new Function(this) { // from class: com.android.server.appfunctions.AppFunctionManagerServiceImpl$$ExternalSyntheticLambda9
            public final /* synthetic */ AppFunctionManagerServiceImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                switch (i6) {
                    case 0:
                        AppFunctionManagerServiceImpl appFunctionManagerServiceImpl = this.f$0;
                        ExecuteAppFunctionAidlRequest executeAppFunctionAidlRequest2 = (ExecuteAppFunctionAidlRequest) safeOneTimeExecuteAppFunctionCallback;
                        appFunctionManagerServiceImpl.getClass();
                        String functionIdentifier = executeAppFunctionAidlRequest2.getClientRequest().getFunctionIdentifier();
                        String targetPackageName2 = executeAppFunctionAidlRequest2.getClientRequest().getTargetPackageName();
                        AppSearchManager appSearchManager = (AppSearchManager) appFunctionManagerServiceImpl.mContext.createContextAsUser(executeAppFunctionAidlRequest2.getUserHandle(), 0).getSystemService(AppSearchManager.class);
                        Executor executor = AppFunctionExecutors.THREAD_POOL_EXECUTOR;
                        final AndroidFuture androidFuture = new AndroidFuture();
                        AppFunctionManagerHelper.isAppFunctionEnabled(functionIdentifier, targetPackageName2, appSearchManager, executor, new OutcomeReceiver() { // from class: com.android.server.appfunctions.AppFunctionManagerServiceImpl.1
                            @Override // android.os.OutcomeReceiver
                            public final void onError(Throwable th) {
                                androidFuture.completeExceptionally((Exception) th);
                            }

                            @Override // android.os.OutcomeReceiver
                            public final void onResult(Object obj2) {
                                androidFuture.complete((Boolean) obj2);
                            }
                        });
                        return androidFuture;
                    default:
                        AppFunctionManagerServiceImpl appFunctionManagerServiceImpl2 = this.f$0;
                        SafeOneTimeExecuteAppFunctionCallback safeOneTimeExecuteAppFunctionCallback2 = (SafeOneTimeExecuteAppFunctionCallback) safeOneTimeExecuteAppFunctionCallback;
                        appFunctionManagerServiceImpl2.getClass();
                        safeOneTimeExecuteAppFunctionCallback2.onError(AppFunctionManagerServiceImpl.mapExceptionToExecuteAppFunctionResponse((Throwable) obj));
                        return null;
                }
            }
        });
    }

    public Object getLockForPackage(String str) {
        Object computeIfAbsent;
        synchronized (this.mLocks) {
            ((WeakHashMap) this.mLocks).values().removeAll(Collections.singleton(null));
            computeIfAbsent = this.mLocks.computeIfAbsent(str, new AppFunctionManagerServiceImpl$$ExternalSyntheticLambda0(1));
        }
        return computeIfAbsent;
    }

    public final void setAppFunctionEnabled(final String str, final String str2, final UserHandle userHandle, final int i, final IAppFunctionEnabledCallback iAppFunctionEnabledCallback) {
        try {
            this.mCallerValidator.validateCallingPackage(str);
            ((ThreadPoolExecutor) AppFunctionExecutors.THREAD_POOL_EXECUTOR).execute(new Runnable() { // from class: com.android.server.appfunctions.AppFunctionManagerServiceImpl$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    AppFunctionManagerServiceImpl appFunctionManagerServiceImpl = AppFunctionManagerServiceImpl.this;
                    String str3 = str;
                    String str4 = str2;
                    UserHandle userHandle2 = userHandle;
                    int i2 = i;
                    IAppFunctionEnabledCallback iAppFunctionEnabledCallback2 = iAppFunctionEnabledCallback;
                    appFunctionManagerServiceImpl.getClass();
                    try {
                        synchronized (appFunctionManagerServiceImpl.getLockForPackage(str3)) {
                            appFunctionManagerServiceImpl.setAppFunctionEnabledInternalLocked(i2, userHandle2, str3, str4);
                        }
                        iAppFunctionEnabledCallback2.onSuccess();
                    } catch (Exception e) {
                        Slog.e("AppFunctionManagerServiceImpl", "Error in setAppFunctionEnabled: ", e);
                        try {
                            iAppFunctionEnabledCallback2.onError(new ParcelableException(e));
                        } catch (RemoteException e2) {
                            Slog.w("AppFunctionManagerServiceImpl", "Failed to report the exception", e2);
                        }
                    }
                }
            });
        } catch (SecurityException e) {
            try {
                iAppFunctionEnabledCallback.onError(new ParcelableException(e));
            } catch (RemoteException e2) {
                Slog.w("AppFunctionManagerServiceImpl", "Failed to report the exception", e2);
            }
        }
    }

    public final void setAppFunctionEnabledInternalLocked(int i, UserHandle userHandle, String str, String str2) {
        AppSearchManager appSearchManager = (AppSearchManager) this.mContext.createContextAsUser(userHandle, 0).getSystemService(AppSearchManager.class);
        if (appSearchManager == null) {
            throw new IllegalStateException("AppSearchManager not found for user:" + userHandle.getIdentifier());
        }
        FutureAppSearchSessionImpl futureAppSearchSessionImpl = new FutureAppSearchSessionImpl(appSearchManager, AppFunctionExecutors.THREAD_POOL_EXECUTOR, new AppSearchManager.SearchContext.Builder("appfunctions-db").build());
        try {
            PutDocumentsRequest build = new PutDocumentsRequest.Builder().addGenericDocuments(new AppFunctionRuntimeMetadata.Builder(new AppFunctionRuntimeMetadata(getRuntimeMetadataGenericDocument(str, str2, futureAppSearchSessionImpl))).setEnabled(i).build()).build();
            Objects.requireNonNull(build);
            AppSearchBatchResult appSearchBatchResult = (AppSearchBatchResult) futureAppSearchSessionImpl.getSessionAsync().thenCompose(new FutureAppSearchSessionImpl$$ExternalSyntheticLambda0(futureAppSearchSessionImpl, build, 1)).get();
            if (appSearchBatchResult.isSuccess()) {
                futureAppSearchSessionImpl.close();
            } else {
                throw new IllegalStateException("Failed writing updated doc to AppSearch due to " + appSearchBatchResult);
            }
        } catch (Throwable th) {
            try {
                futureAppSearchSessionImpl.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public final void trySyncRuntimeMetadata(SystemService.TargetUser targetUser, boolean z) {
        MetadataSyncAdapter perUserMetadataSyncAdapter = MetadataSyncPerUser.getPerUserMetadataSyncAdapter(targetUser.getUserHandle(), this.mContext.createContextAsUser(targetUser.getUserHandle(), 0), this.mAppFunctionAgentPolicyManager);
        if (perUserMetadataSyncAdapter != null) {
            perUserMetadataSyncAdapter.submitSyncRequest(z).whenComplete(new AppFunctionManagerServiceImpl$$ExternalSyntheticLambda1());
        }
    }
}
