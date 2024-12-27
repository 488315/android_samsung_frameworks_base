package android.credentials;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentSender;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.OutcomeReceiver;
import android.os.RemoteException;
import android.provider.DeviceConfig;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

public final class CredentialManager {
    private static final String DEVICE_CONFIG_ENABLE_CREDENTIAL_DESC_API =
            "enable_credential_description_api";
    public static final String DEVICE_CONFIG_ENABLE_CREDENTIAL_MANAGER =
            "enable_credential_manager";
    public static final String EXTRA_AUTOFILL_RESULT_RECEIVER =
            "android.credentials.AUTOFILL_RESULT_RECEIVER";
    private static final Bundle OPTIONS_SENDER_BAL_OPTIN =
            ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle();
    public static final int PROVIDER_FILTER_ALL_PROVIDERS = 0;
    public static final int PROVIDER_FILTER_SYSTEM_PROVIDERS_ONLY = 1;
    public static final int PROVIDER_FILTER_USER_PROVIDERS_INCLUDING_HIDDEN = 3;
    public static final int PROVIDER_FILTER_USER_PROVIDERS_ONLY = 2;
    public static final String TAG = "CredentialManager";
    private final Context mContext;
    private final ICredentialManager mService;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProviderFilter {}

    public CredentialManager(Context context, ICredentialManager service) {
        this.mContext = context;
        this.mService = service;
    }

    public void getCandidateCredentials(
            GetCredentialRequest request,
            String callingPackage,
            CancellationSignal cancellationSignal,
            Executor executor,
            OutcomeReceiver<GetCandidateCredentialsResponse, GetCandidateCredentialsException>
                    callback,
            IBinder clientCallback) {
        Objects.requireNonNull(request, "request must not be null");
        Objects.requireNonNull(callingPackage, "callingPackage must not be null");
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(callback, "callback must not be null");
        if (cancellationSignal != null && cancellationSignal.isCanceled()) {
            Log.w(TAG, "getCandidateCredentials already canceled");
            return;
        }
        ICancellationSignal cancelRemote = null;
        try {
            cancelRemote =
                    this.mService.getCandidateCredentials(
                            request,
                            new GetCandidateCredentialsTransport(executor, callback),
                            clientCallback,
                            callingPackage);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
        if (cancellationSignal != null && cancelRemote != null) {
            cancellationSignal.setRemote(cancelRemote);
        }
    }

    public void getCredential(
            Context context,
            GetCredentialRequest request,
            CancellationSignal cancellationSignal,
            Executor executor,
            OutcomeReceiver<GetCredentialResponse, GetCredentialException> callback) {
        Objects.requireNonNull(request, "request must not be null");
        Objects.requireNonNull(context, "context must not be null");
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(callback, "callback must not be null");
        if (cancellationSignal != null && cancellationSignal.isCanceled()) {
            Log.w(TAG, "getCredential already canceled");
            return;
        }
        ICancellationSignal cancelRemote = null;
        try {
            cancelRemote =
                    this.mService.executeGetCredential(
                            request,
                            new GetCredentialTransport(context, executor, callback),
                            this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
        if (cancellationSignal != null && cancelRemote != null) {
            cancellationSignal.setRemote(cancelRemote);
        }
    }

    public void getCredential(
            Context context,
            PrepareGetCredentialResponse.PendingGetCredentialHandle pendingGetCredentialHandle,
            CancellationSignal cancellationSignal,
            Executor executor,
            OutcomeReceiver<GetCredentialResponse, GetCredentialException> callback) {
        Objects.requireNonNull(
                pendingGetCredentialHandle, "pendingGetCredentialHandle must not be null");
        Objects.requireNonNull(context, "context must not be null");
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(callback, "callback must not be null");
        if (cancellationSignal != null && cancellationSignal.isCanceled()) {
            Log.w(TAG, "getCredential already canceled");
        } else {
            pendingGetCredentialHandle.show(context, cancellationSignal, executor, callback);
        }
    }

    public void prepareGetCredential(
            GetCredentialRequest getCredentialRequest,
            CancellationSignal cancellationSignal,
            Executor executor,
            OutcomeReceiver<PrepareGetCredentialResponse, GetCredentialException> outcomeReceiver) {
        Objects.requireNonNull(getCredentialRequest, "request must not be null");
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(outcomeReceiver, "callback must not be null");
        if (cancellationSignal != null && cancellationSignal.isCanceled()) {
            Log.w(TAG, "prepareGetCredential already canceled");
            return;
        }
        ICancellationSignal iCancellationSignal = null;
        byte b = 0;
        GetCredentialTransportPendingUseCase getCredentialTransportPendingUseCase =
                new GetCredentialTransportPendingUseCase();
        try {
            iCancellationSignal =
                    this.mService.executePrepareGetCredential(
                            getCredentialRequest,
                            new PrepareGetCredentialTransport(
                                    executor,
                                    outcomeReceiver,
                                    getCredentialTransportPendingUseCase),
                            getCredentialTransportPendingUseCase,
                            this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
        if (cancellationSignal != null && iCancellationSignal != null) {
            cancellationSignal.setRemote(iCancellationSignal);
        }
    }

    public void createCredential(
            Context context,
            CreateCredentialRequest request,
            CancellationSignal cancellationSignal,
            Executor executor,
            OutcomeReceiver<CreateCredentialResponse, CreateCredentialException> callback) {
        Objects.requireNonNull(request, "request must not be null");
        Objects.requireNonNull(context, "context must not be null");
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(callback, "callback must not be null");
        if (cancellationSignal != null && cancellationSignal.isCanceled()) {
            Log.w(TAG, "createCredential already canceled");
            return;
        }
        ICancellationSignal cancelRemote = null;
        try {
            cancelRemote =
                    this.mService.executeCreateCredential(
                            request,
                            new CreateCredentialTransport(context, executor, callback),
                            this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
        if (cancellationSignal != null && cancelRemote != null) {
            cancellationSignal.setRemote(cancelRemote);
        }
    }

    public void clearCredentialState(
            ClearCredentialStateRequest request,
            CancellationSignal cancellationSignal,
            Executor executor,
            OutcomeReceiver<Void, ClearCredentialStateException> callback) {
        Objects.requireNonNull(request, "request must not be null");
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(callback, "callback must not be null");
        if (cancellationSignal != null && cancellationSignal.isCanceled()) {
            Log.w(TAG, "clearCredentialState already canceled");
            return;
        }
        ICancellationSignal cancelRemote = null;
        try {
            cancelRemote =
                    this.mService.clearCredentialState(
                            request,
                            new ClearCredentialStateTransport(executor, callback),
                            this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
        if (cancellationSignal != null && cancelRemote != null) {
            cancellationSignal.setRemote(cancelRemote);
        }
    }

    public void setEnabledProviders(
            List<String> primaryProviders,
            List<String> providers,
            int userId,
            Executor executor,
            OutcomeReceiver<Void, SetEnabledProvidersException> callback) {
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(callback, "callback must not be null");
        Objects.requireNonNull(providers, "providers must not be null");
        Objects.requireNonNull(primaryProviders, "primaryProviders must not be null");
        try {
            this.mService.setEnabledProviders(
                    primaryProviders,
                    providers,
                    userId,
                    new SetEnabledProvidersTransport(executor, callback));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public boolean isEnabledCredentialProviderService(ComponentName componentName) {
        Objects.requireNonNull(componentName, "componentName must not be null");
        try {
            return this.mService.isEnabledCredentialProviderService(
                    componentName, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<CredentialProviderInfo> getCredentialProviderServicesForTesting(
            int providerFilter) {
        try {
            return this.mService.getCredentialProviderServicesForTesting(providerFilter);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<CredentialProviderInfo> getCredentialProviderServices(
            int userId, int providerFilter) {
        try {
            return this.mService.getCredentialProviderServices(userId, providerFilter);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean isServiceEnabled(Context context) {
        CredentialManager credentialManager;
        Objects.requireNonNull(context, "context must not be null");
        if (context == null
                || (credentialManager = (CredentialManager) context.getSystemService("credential"))
                        == null) {
            return false;
        }
        return credentialManager.isServiceEnabled();
    }

    private boolean isServiceEnabled() {
        try {
            return this.mService.isServiceEnabled();
        } catch (RemoteException e) {
            return false;
        }
    }

    public static boolean isCredentialDescriptionApiEnabled(Context context) {
        CredentialManager credentialManager;
        if (context == null
                || (credentialManager = (CredentialManager) context.getSystemService("credential"))
                        == null) {
            return false;
        }
        return credentialManager.isCredentialDescriptionApiEnabled();
    }

    private boolean isCredentialDescriptionApiEnabled() {
        return DeviceConfig.getBoolean(
                "credential_manager", DEVICE_CONFIG_ENABLE_CREDENTIAL_DESC_API, false);
    }

    public void registerCredentialDescription(RegisterCredentialDescriptionRequest request) {
        Objects.requireNonNull(request, "request must not be null");
        try {
            this.mService.registerCredentialDescription(request, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void unregisterCredentialDescription(UnregisterCredentialDescriptionRequest request) {
        Objects.requireNonNull(request, "request must not be null");
        try {
            this.mService.unregisterCredentialDescription(
                    request, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class PrepareGetCredentialTransport extends IPrepareGetCredentialCallback.Stub {
        private final OutcomeReceiver<PrepareGetCredentialResponse, GetCredentialException>
                mCallback;
        private final Executor mExecutor;
        private final GetCredentialTransportPendingUseCase mGetCredentialTransport;

        private PrepareGetCredentialTransport(
                Executor executor,
                OutcomeReceiver<PrepareGetCredentialResponse, GetCredentialException> callback,
                GetCredentialTransportPendingUseCase getCredentialTransport) {
            this.mExecutor = executor;
            this.mCallback = callback;
            this.mGetCredentialTransport = getCredentialTransport;
        }

        @Override // android.credentials.IPrepareGetCredentialCallback
        public void onResponse(final PrepareGetCredentialResponseInternal response) {
            long identity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.credentials.CredentialManager$PrepareGetCredentialTransport$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                CredentialManager.PrepareGetCredentialTransport.this
                                        .lambda$onResponse$0(response);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResponse$0(
                PrepareGetCredentialResponseInternal response) {
            this.mCallback.onResult(
                    new PrepareGetCredentialResponse(response, this.mGetCredentialTransport));
        }

        @Override // android.credentials.IPrepareGetCredentialCallback
        public void onError(final String errorType, final String message) {
            long identity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.credentials.CredentialManager$PrepareGetCredentialTransport$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                CredentialManager.PrepareGetCredentialTransport.this
                                        .lambda$onError$1(errorType, message);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$1(String errorType, String message) {
            this.mCallback.onError(new GetCredentialException(errorType, message));
        }
    }

    protected static class GetCredentialTransportPendingUseCase
            extends IGetCredentialCallback.Stub {
        private PrepareGetCredentialResponse.GetPendingCredentialInternalCallback mCallback;

        private GetCredentialTransportPendingUseCase() {
            this.mCallback = null;
        }

        public void setCallback(
                PrepareGetCredentialResponse.GetPendingCredentialInternalCallback callback) {
            if (this.mCallback == null) {
                this.mCallback = callback;
                return;
            }
            throw new IllegalStateException("callback has already been set once");
        }

        @Override // android.credentials.IGetCredentialCallback
        public void onPendingIntent(PendingIntent pendingIntent) {
            if (this.mCallback != null) {
                this.mCallback.onPendingIntent(pendingIntent);
            } else {
                Log.d(
                        CredentialManager.TAG,
                        "Unexpected onPendingIntent call before the show invocation");
            }
        }

        @Override // android.credentials.IGetCredentialCallback
        public void onResponse(GetCredentialResponse response) {
            if (this.mCallback != null) {
                long identity = Binder.clearCallingIdentity();
                try {
                    this.mCallback.onResponse(response);
                    return;
                } finally {
                    Binder.restoreCallingIdentity(identity);
                }
            }
            Log.d(CredentialManager.TAG, "Unexpected onResponse call before the show invocation");
        }

        @Override // android.credentials.IGetCredentialCallback
        public void onError(String errorType, String message) {
            if (this.mCallback != null) {
                long identity = Binder.clearCallingIdentity();
                try {
                    this.mCallback.onError(errorType, message);
                    return;
                } finally {
                    Binder.restoreCallingIdentity(identity);
                }
            }
            Log.d(CredentialManager.TAG, "Unexpected onError call before the show invocation");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class GetCandidateCredentialsTransport extends IGetCandidateCredentialsCallback.Stub {
        private final OutcomeReceiver<
                        GetCandidateCredentialsResponse, GetCandidateCredentialsException>
                mCallback;
        private final Executor mExecutor;

        private GetCandidateCredentialsTransport(
                Executor executor,
                OutcomeReceiver<GetCandidateCredentialsResponse, GetCandidateCredentialsException>
                        callback) {
            this.mExecutor = executor;
            this.mCallback = callback;
        }

        @Override // android.credentials.IGetCandidateCredentialsCallback
        public void onResponse(final GetCandidateCredentialsResponse response) {
            long identity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.credentials.CredentialManager$GetCandidateCredentialsTransport$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                CredentialManager.GetCandidateCredentialsTransport.this
                                        .lambda$onResponse$0(response);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResponse$0(GetCandidateCredentialsResponse response) {
            this.mCallback.onResult(response);
        }

        @Override // android.credentials.IGetCandidateCredentialsCallback
        public void onError(final String errorType, final String message) {
            long identity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.credentials.CredentialManager$GetCandidateCredentialsTransport$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                CredentialManager.GetCandidateCredentialsTransport.this
                                        .lambda$onError$1(errorType, message);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$1(String errorType, String message) {
            this.mCallback.onError(new GetCandidateCredentialsException(errorType, message));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class GetCredentialTransport extends IGetCredentialCallback.Stub {
        private final OutcomeReceiver<GetCredentialResponse, GetCredentialException> mCallback;
        private final Context mContext;
        private final Executor mExecutor;

        private GetCredentialTransport(
                Context context,
                Executor executor,
                OutcomeReceiver<GetCredentialResponse, GetCredentialException> callback) {
            this.mContext = context;
            this.mExecutor = executor;
            this.mCallback = callback;
        }

        @Override // android.credentials.IGetCredentialCallback
        public void onPendingIntent(PendingIntent pendingIntent) {
            try {
                this.mContext.startIntentSender(
                        pendingIntent.getIntentSender(),
                        null,
                        0,
                        0,
                        0,
                        CredentialManager.OPTIONS_SENDER_BAL_OPTIN);
            } catch (IntentSender.SendIntentException e) {
                Log.e(
                        CredentialManager.TAG,
                        "startIntentSender() failed for intent:" + pendingIntent.getIntentSender(),
                        e);
                long identity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(
                            new Runnable() { // from class:
                                // android.credentials.CredentialManager$GetCredentialTransport$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    CredentialManager.GetCredentialTransport.this
                                            .lambda$onPendingIntent$0();
                                }
                            });
                } finally {
                    Binder.restoreCallingIdentity(identity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPendingIntent$0() {
            this.mCallback.onError(new GetCredentialException(GetCredentialException.TYPE_UNKNOWN));
        }

        @Override // android.credentials.IGetCredentialCallback
        public void onResponse(final GetCredentialResponse response) {
            long identity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.credentials.CredentialManager$GetCredentialTransport$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                CredentialManager.GetCredentialTransport.this.lambda$onResponse$1(
                                        response);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResponse$1(GetCredentialResponse response) {
            this.mCallback.onResult(response);
        }

        @Override // android.credentials.IGetCredentialCallback
        public void onError(final String errorType, final String message) {
            long identity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.credentials.CredentialManager$GetCredentialTransport$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                CredentialManager.GetCredentialTransport.this.lambda$onError$2(
                                        errorType, message);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$2(String errorType, String message) {
            this.mCallback.onError(new GetCredentialException(errorType, message));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class CreateCredentialTransport extends ICreateCredentialCallback.Stub {
        private final OutcomeReceiver<CreateCredentialResponse, CreateCredentialException>
                mCallback;
        private final Context mContext;
        private final Executor mExecutor;

        private CreateCredentialTransport(
                Context context,
                Executor executor,
                OutcomeReceiver<CreateCredentialResponse, CreateCredentialException> callback) {
            this.mContext = context;
            this.mExecutor = executor;
            this.mCallback = callback;
        }

        @Override // android.credentials.ICreateCredentialCallback
        public void onPendingIntent(PendingIntent pendingIntent) {
            try {
                this.mContext.startIntentSender(
                        pendingIntent.getIntentSender(),
                        null,
                        0,
                        0,
                        0,
                        CredentialManager.OPTIONS_SENDER_BAL_OPTIN);
            } catch (IntentSender.SendIntentException e) {
                Log.e(
                        CredentialManager.TAG,
                        "startIntentSender() failed for intent:" + pendingIntent.getIntentSender(),
                        e);
                long identity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(
                            new Runnable() { // from class:
                                // android.credentials.CredentialManager$CreateCredentialTransport$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    CredentialManager.CreateCredentialTransport.this
                                            .lambda$onPendingIntent$0();
                                }
                            });
                } finally {
                    Binder.restoreCallingIdentity(identity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPendingIntent$0() {
            this.mCallback.onError(
                    new CreateCredentialException(CreateCredentialException.TYPE_UNKNOWN));
        }

        @Override // android.credentials.ICreateCredentialCallback
        public void onResponse(final CreateCredentialResponse response) {
            long identity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.credentials.CredentialManager$CreateCredentialTransport$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                CredentialManager.CreateCredentialTransport.this
                                        .lambda$onResponse$1(response);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResponse$1(CreateCredentialResponse response) {
            this.mCallback.onResult(response);
        }

        @Override // android.credentials.ICreateCredentialCallback
        public void onError(final String errorType, final String message) {
            long identity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.credentials.CredentialManager$CreateCredentialTransport$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                CredentialManager.CreateCredentialTransport.this.lambda$onError$2(
                                        errorType, message);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$2(String errorType, String message) {
            this.mCallback.onError(new CreateCredentialException(errorType, message));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ClearCredentialStateTransport extends IClearCredentialStateCallback.Stub {
        private final OutcomeReceiver<Void, ClearCredentialStateException> mCallback;
        private final Executor mExecutor;

        private ClearCredentialStateTransport(
                Executor executor, OutcomeReceiver<Void, ClearCredentialStateException> callback) {
            this.mExecutor = executor;
            this.mCallback = callback;
        }

        @Override // android.credentials.IClearCredentialStateCallback
        public void onSuccess() {
            long identity = Binder.clearCallingIdentity();
            try {
                this.mCallback.onResult(null);
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        @Override // android.credentials.IClearCredentialStateCallback
        public void onError(final String errorType, final String message) {
            long identity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.credentials.CredentialManager$ClearCredentialStateTransport$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                CredentialManager.ClearCredentialStateTransport.this
                                        .lambda$onError$0(errorType, message);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$0(String errorType, String message) {
            this.mCallback.onError(new ClearCredentialStateException(errorType, message));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SetEnabledProvidersTransport extends ISetEnabledProvidersCallback.Stub {
        private final OutcomeReceiver<Void, SetEnabledProvidersException> mCallback;
        private final Executor mExecutor;

        private SetEnabledProvidersTransport(
                Executor executor, OutcomeReceiver<Void, SetEnabledProvidersException> callback) {
            this.mExecutor = executor;
            this.mCallback = callback;
        }

        public void onResponse(final Void result) {
            long identity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.credentials.CredentialManager$SetEnabledProvidersTransport$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                CredentialManager.SetEnabledProvidersTransport.this
                                        .lambda$onResponse$0(result);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResponse$0(Void result) {
            this.mCallback.onResult(result);
        }

        @Override // android.credentials.ISetEnabledProvidersCallback
        public void onResponse() {
            long identity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.credentials.CredentialManager$SetEnabledProvidersTransport$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                CredentialManager.SetEnabledProvidersTransport.this
                                        .lambda$onResponse$1();
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResponse$1() {
            this.mCallback.onResult(null);
        }

        @Override // android.credentials.ISetEnabledProvidersCallback
        public void onError(final String errorType, final String message) {
            long identity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.credentials.CredentialManager$SetEnabledProvidersTransport$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                CredentialManager.SetEnabledProvidersTransport.this
                                        .lambda$onError$2(errorType, message);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$2(String errorType, String message) {
            this.mCallback.onError(new SetEnabledProvidersException(errorType, message));
        }
    }
}
