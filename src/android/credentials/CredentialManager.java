package android.credentials;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentSender;
import android.credentials.IClearCredentialStateCallback;
import android.credentials.ICreateCredentialCallback;
import android.credentials.IGetCandidateCredentialsCallback;
import android.credentials.IGetCredentialCallback;
import android.credentials.IPrepareGetCredentialCallback;
import android.credentials.ISetEnabledProvidersCallback;
import android.credentials.PrepareGetCredentialResponse;
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

/* loaded from: classes.dex */
public final class CredentialManager {
    private static final String DEVICE_CONFIG_ENABLE_CREDENTIAL_DESC_API = "enable_credential_description_api";
    public static final String DEVICE_CONFIG_ENABLE_CREDENTIAL_MANAGER = "enable_credential_manager";
    public static final String EXTRA_AUTOFILL_RESULT_RECEIVER = "android.credentials.AUTOFILL_RESULT_RECEIVER";
    private static final Bundle OPTIONS_SENDER_BAL_OPTIN = ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle();
    public static final int PROVIDER_FILTER_ALL_PROVIDERS = 0;
    public static final int PROVIDER_FILTER_SYSTEM_PROVIDERS_ONLY = 1;
    public static final int PROVIDER_FILTER_USER_PROVIDERS_INCLUDING_HIDDEN = 3;
    public static final int PROVIDER_FILTER_USER_PROVIDERS_ONLY = 2;
    public static final String TAG = "CredentialManager";
    private final Context mContext;
    private final ICredentialManager mService;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProviderFilter {
    }

    public CredentialManager(Context context, ICredentialManager iCredentialManager) {
        this.mContext = context;
        this.mService = iCredentialManager;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void getCandidateCredentials(GetCredentialRequest getCredentialRequest, String str, CancellationSignal cancellationSignal, Executor executor, OutcomeReceiver<GetCandidateCredentialsResponse, GetCandidateCredentialsException> outcomeReceiver, IBinder iBinder) {
        Objects.requireNonNull(getCredentialRequest, "request must not be null");
        Objects.requireNonNull(str, "callingPackage must not be null");
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(outcomeReceiver, "callback must not be null");
        if (cancellationSignal != null && cancellationSignal.isCanceled()) {
            Log.w(TAG, "getCandidateCredentials already canceled");
            return;
        }
        ICancellationSignal candidateCredentials = null;
        try {
            candidateCredentials = this.mService.getCandidateCredentials(getCredentialRequest, new GetCandidateCredentialsTransport(executor, outcomeReceiver), iBinder, str);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
        if (cancellationSignal == null || candidateCredentials == null) {
            return;
        }
        cancellationSignal.setRemote(candidateCredentials);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void getCredential(Context context, GetCredentialRequest getCredentialRequest, CancellationSignal cancellationSignal, Executor executor, OutcomeReceiver<GetCredentialResponse, GetCredentialException> outcomeReceiver) {
        Objects.requireNonNull(getCredentialRequest, "request must not be null");
        Objects.requireNonNull(context, "context must not be null");
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(outcomeReceiver, "callback must not be null");
        if (cancellationSignal != null && cancellationSignal.isCanceled()) {
            Log.w(TAG, "getCredential already canceled");
            return;
        }
        ICancellationSignal iCancellationSignalExecuteGetCredential = null;
        try {
            iCancellationSignalExecuteGetCredential = this.mService.executeGetCredential(getCredentialRequest, new GetCredentialTransport(context, executor, outcomeReceiver), this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
        if (cancellationSignal == null || iCancellationSignalExecuteGetCredential == null) {
            return;
        }
        cancellationSignal.setRemote(iCancellationSignalExecuteGetCredential);
    }

    public void getCredential(Context context, PrepareGetCredentialResponse.PendingGetCredentialHandle pendingGetCredentialHandle, CancellationSignal cancellationSignal, Executor executor, OutcomeReceiver<GetCredentialResponse, GetCredentialException> outcomeReceiver) {
        Objects.requireNonNull(pendingGetCredentialHandle, "pendingGetCredentialHandle must not be null");
        Objects.requireNonNull(context, "context must not be null");
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(outcomeReceiver, "callback must not be null");
        if (cancellationSignal != null && cancellationSignal.isCanceled()) {
            Log.w(TAG, "getCredential already canceled");
        } else {
            pendingGetCredentialHandle.show(context, cancellationSignal, executor, outcomeReceiver);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void prepareGetCredential(GetCredentialRequest getCredentialRequest, CancellationSignal cancellationSignal, Executor executor, OutcomeReceiver<PrepareGetCredentialResponse, GetCredentialException> outcomeReceiver) {
        Objects.requireNonNull(getCredentialRequest, "request must not be null");
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(outcomeReceiver, "callback must not be null");
        if (cancellationSignal != null && cancellationSignal.isCanceled()) {
            Log.w(TAG, "prepareGetCredential already canceled");
            return;
        }
        ICancellationSignal iCancellationSignalExecutePrepareGetCredential = null;
        Object[] objArr = 0;
        GetCredentialTransportPendingUseCase getCredentialTransportPendingUseCase = new GetCredentialTransportPendingUseCase();
        try {
            iCancellationSignalExecutePrepareGetCredential = this.mService.executePrepareGetCredential(getCredentialRequest, new PrepareGetCredentialTransport(executor, outcomeReceiver, getCredentialTransportPendingUseCase), getCredentialTransportPendingUseCase, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
        if (cancellationSignal == null || iCancellationSignalExecutePrepareGetCredential == null) {
            return;
        }
        cancellationSignal.setRemote(iCancellationSignalExecutePrepareGetCredential);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void createCredential(Context context, CreateCredentialRequest createCredentialRequest, CancellationSignal cancellationSignal, Executor executor, OutcomeReceiver<CreateCredentialResponse, CreateCredentialException> outcomeReceiver) {
        Objects.requireNonNull(createCredentialRequest, "request must not be null");
        Objects.requireNonNull(context, "context must not be null");
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(outcomeReceiver, "callback must not be null");
        if (cancellationSignal != null && cancellationSignal.isCanceled()) {
            Log.w(TAG, "createCredential already canceled");
            return;
        }
        ICancellationSignal iCancellationSignalExecuteCreateCredential = null;
        try {
            iCancellationSignalExecuteCreateCredential = this.mService.executeCreateCredential(createCredentialRequest, new CreateCredentialTransport(context, executor, outcomeReceiver), this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
        if (cancellationSignal == null || iCancellationSignalExecuteCreateCredential == null) {
            return;
        }
        cancellationSignal.setRemote(iCancellationSignalExecuteCreateCredential);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void clearCredentialState(ClearCredentialStateRequest clearCredentialStateRequest, CancellationSignal cancellationSignal, Executor executor, OutcomeReceiver<Void, ClearCredentialStateException> outcomeReceiver) {
        Objects.requireNonNull(clearCredentialStateRequest, "request must not be null");
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(outcomeReceiver, "callback must not be null");
        if (cancellationSignal != null && cancellationSignal.isCanceled()) {
            Log.w(TAG, "clearCredentialState already canceled");
            return;
        }
        ICancellationSignal iCancellationSignalClearCredentialState = null;
        try {
            iCancellationSignalClearCredentialState = this.mService.clearCredentialState(clearCredentialStateRequest, new ClearCredentialStateTransport(executor, outcomeReceiver), this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
        if (cancellationSignal == null || iCancellationSignalClearCredentialState == null) {
            return;
        }
        cancellationSignal.setRemote(iCancellationSignalClearCredentialState);
    }

    public void setEnabledProviders(List<String> list, List<String> list2, int i, Executor executor, OutcomeReceiver<Void, SetEnabledProvidersException> outcomeReceiver) {
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(outcomeReceiver, "callback must not be null");
        Objects.requireNonNull(list2, "providers must not be null");
        Objects.requireNonNull(list, "primaryProviders must not be null");
        try {
            this.mService.setEnabledProviders(list, list2, i, new SetEnabledProvidersTransport(executor, outcomeReceiver));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public boolean isEnabledCredentialProviderService(ComponentName componentName) {
        Objects.requireNonNull(componentName, "componentName must not be null");
        try {
            return this.mService.isEnabledCredentialProviderService(componentName, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<CredentialProviderInfo> getCredentialProviderServicesForTesting(int i) {
        try {
            return this.mService.getCredentialProviderServicesForTesting(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<CredentialProviderInfo> getCredentialProviderServices(int i, int i2) {
        try {
            return this.mService.getCredentialProviderServices(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean isServiceEnabled(Context context) {
        CredentialManager credentialManager;
        Objects.requireNonNull(context, "context must not be null");
        if (context == null || (credentialManager = (CredentialManager) context.getSystemService("credential")) == null) {
            return false;
        }
        return credentialManager.isServiceEnabled();
    }

    private boolean isServiceEnabled() {
        try {
            return this.mService.isServiceEnabled();
        } catch (RemoteException unused) {
            return false;
        }
    }

    public static boolean isCredentialDescriptionApiEnabled(Context context) {
        CredentialManager credentialManager;
        if (context == null || (credentialManager = (CredentialManager) context.getSystemService("credential")) == null) {
            return false;
        }
        return credentialManager.isCredentialDescriptionApiEnabled();
    }

    private boolean isCredentialDescriptionApiEnabled() {
        return DeviceConfig.getBoolean("credential_manager", DEVICE_CONFIG_ENABLE_CREDENTIAL_DESC_API, false);
    }

    public void registerCredentialDescription(RegisterCredentialDescriptionRequest registerCredentialDescriptionRequest) {
        Objects.requireNonNull(registerCredentialDescriptionRequest, "request must not be null");
        try {
            this.mService.registerCredentialDescription(registerCredentialDescriptionRequest, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void unregisterCredentialDescription(UnregisterCredentialDescriptionRequest unregisterCredentialDescriptionRequest) {
        Objects.requireNonNull(unregisterCredentialDescriptionRequest, "request must not be null");
        try {
            this.mService.unregisterCredentialDescription(unregisterCredentialDescriptionRequest, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class PrepareGetCredentialTransport extends IPrepareGetCredentialCallback.Stub {
        private final OutcomeReceiver<PrepareGetCredentialResponse, GetCredentialException> mCallback;
        private final Executor mExecutor;
        private final GetCredentialTransportPendingUseCase mGetCredentialTransport;

        private PrepareGetCredentialTransport(Executor executor, OutcomeReceiver<PrepareGetCredentialResponse, GetCredentialException> outcomeReceiver, GetCredentialTransportPendingUseCase getCredentialTransportPendingUseCase) {
            this.mExecutor = executor;
            this.mCallback = outcomeReceiver;
            this.mGetCredentialTransport = getCredentialTransportPendingUseCase;
        }

        @Override // android.credentials.IPrepareGetCredentialCallback
        public void onResponse(final PrepareGetCredentialResponseInternal prepareGetCredentialResponseInternal) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.credentials.CredentialManager$PrepareGetCredentialTransport$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onResponse$0(prepareGetCredentialResponseInternal);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResponse$0(PrepareGetCredentialResponseInternal prepareGetCredentialResponseInternal) {
            this.mCallback.onResult(new PrepareGetCredentialResponse(prepareGetCredentialResponseInternal, this.mGetCredentialTransport));
        }

        @Override // android.credentials.IPrepareGetCredentialCallback
        public void onError(final String str, final String str2) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.credentials.CredentialManager$PrepareGetCredentialTransport$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onError$1(str, str2);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$1(String str, String str2) {
            this.mCallback.onError(new GetCredentialException(str, str2));
        }
    }

    protected static class GetCredentialTransportPendingUseCase extends IGetCredentialCallback.Stub {
        private PrepareGetCredentialResponse.GetPendingCredentialInternalCallback mCallback;

        private GetCredentialTransportPendingUseCase() {
            this.mCallback = null;
        }

        public void setCallback(PrepareGetCredentialResponse.GetPendingCredentialInternalCallback getPendingCredentialInternalCallback) {
            if (this.mCallback == null) {
                this.mCallback = getPendingCredentialInternalCallback;
                return;
            }
            throw new IllegalStateException("callback has already been set once");
        }

        @Override // android.credentials.IGetCredentialCallback
        public void onPendingIntent(PendingIntent pendingIntent) {
            PrepareGetCredentialResponse.GetPendingCredentialInternalCallback getPendingCredentialInternalCallback = this.mCallback;
            if (getPendingCredentialInternalCallback != null) {
                getPendingCredentialInternalCallback.onPendingIntent(pendingIntent);
            } else {
                Log.d(CredentialManager.TAG, "Unexpected onPendingIntent call before the show invocation");
            }
        }

        @Override // android.credentials.IGetCredentialCallback
        public void onResponse(GetCredentialResponse getCredentialResponse) {
            if (this.mCallback != null) {
                long jClearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mCallback.onResponse(getCredentialResponse);
                    return;
                } finally {
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                }
            }
            Log.d(CredentialManager.TAG, "Unexpected onResponse call before the show invocation");
        }

        @Override // android.credentials.IGetCredentialCallback
        public void onError(String str, String str2) {
            if (this.mCallback != null) {
                long jClearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mCallback.onError(str, str2);
                    return;
                } finally {
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                }
            }
            Log.d(CredentialManager.TAG, "Unexpected onError call before the show invocation");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class GetCandidateCredentialsTransport extends IGetCandidateCredentialsCallback.Stub {
        private final OutcomeReceiver<GetCandidateCredentialsResponse, GetCandidateCredentialsException> mCallback;
        private final Executor mExecutor;

        private GetCandidateCredentialsTransport(Executor executor, OutcomeReceiver<GetCandidateCredentialsResponse, GetCandidateCredentialsException> outcomeReceiver) {
            this.mExecutor = executor;
            this.mCallback = outcomeReceiver;
        }

        @Override // android.credentials.IGetCandidateCredentialsCallback
        public void onResponse(final GetCandidateCredentialsResponse getCandidateCredentialsResponse) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.credentials.CredentialManager$GetCandidateCredentialsTransport$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onResponse$0(getCandidateCredentialsResponse);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResponse$0(GetCandidateCredentialsResponse getCandidateCredentialsResponse) {
            this.mCallback.onResult(getCandidateCredentialsResponse);
        }

        @Override // android.credentials.IGetCandidateCredentialsCallback
        public void onError(final String str, final String str2) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.credentials.CredentialManager$GetCandidateCredentialsTransport$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onError$1(str, str2);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$1(String str, String str2) {
            this.mCallback.onError(new GetCandidateCredentialsException(str, str2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class GetCredentialTransport extends IGetCredentialCallback.Stub {
        private final OutcomeReceiver<GetCredentialResponse, GetCredentialException> mCallback;
        private final Context mContext;
        private final Executor mExecutor;

        private GetCredentialTransport(Context context, Executor executor, OutcomeReceiver<GetCredentialResponse, GetCredentialException> outcomeReceiver) {
            this.mContext = context;
            this.mExecutor = executor;
            this.mCallback = outcomeReceiver;
        }

        @Override // android.credentials.IGetCredentialCallback
        public void onPendingIntent(PendingIntent pendingIntent) {
            try {
                this.mContext.startIntentSender(pendingIntent.getIntentSender(), null, 0, 0, 0, CredentialManager.OPTIONS_SENDER_BAL_OPTIN);
            } catch (IntentSender.SendIntentException e) {
                Log.e(CredentialManager.TAG, "startIntentSender() failed for intent:" + pendingIntent.getIntentSender(), e);
                long jClearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.credentials.CredentialManager$GetCredentialTransport$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onPendingIntent$0();
                        }
                    });
                } finally {
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPendingIntent$0() {
            this.mCallback.onError(new GetCredentialException(GetCredentialException.TYPE_UNKNOWN));
        }

        @Override // android.credentials.IGetCredentialCallback
        public void onResponse(final GetCredentialResponse getCredentialResponse) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.credentials.CredentialManager$GetCredentialTransport$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onResponse$1(getCredentialResponse);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResponse$1(GetCredentialResponse getCredentialResponse) {
            this.mCallback.onResult(getCredentialResponse);
        }

        @Override // android.credentials.IGetCredentialCallback
        public void onError(final String str, final String str2) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.credentials.CredentialManager$GetCredentialTransport$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onError$2(str, str2);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$2(String str, String str2) {
            this.mCallback.onError(new GetCredentialException(str, str2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class CreateCredentialTransport extends ICreateCredentialCallback.Stub {
        private final OutcomeReceiver<CreateCredentialResponse, CreateCredentialException> mCallback;
        private final Context mContext;
        private final Executor mExecutor;

        private CreateCredentialTransport(Context context, Executor executor, OutcomeReceiver<CreateCredentialResponse, CreateCredentialException> outcomeReceiver) {
            this.mContext = context;
            this.mExecutor = executor;
            this.mCallback = outcomeReceiver;
        }

        @Override // android.credentials.ICreateCredentialCallback
        public void onPendingIntent(PendingIntent pendingIntent) {
            try {
                this.mContext.startIntentSender(pendingIntent.getIntentSender(), null, 0, 0, 0, CredentialManager.OPTIONS_SENDER_BAL_OPTIN);
            } catch (IntentSender.SendIntentException e) {
                Log.e(CredentialManager.TAG, "startIntentSender() failed for intent:" + pendingIntent.getIntentSender(), e);
                long jClearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.credentials.CredentialManager$CreateCredentialTransport$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onPendingIntent$0();
                        }
                    });
                } finally {
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPendingIntent$0() {
            this.mCallback.onError(new CreateCredentialException(CreateCredentialException.TYPE_UNKNOWN));
        }

        @Override // android.credentials.ICreateCredentialCallback
        public void onResponse(final CreateCredentialResponse createCredentialResponse) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.credentials.CredentialManager$CreateCredentialTransport$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onResponse$1(createCredentialResponse);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResponse$1(CreateCredentialResponse createCredentialResponse) {
            this.mCallback.onResult(createCredentialResponse);
        }

        @Override // android.credentials.ICreateCredentialCallback
        public void onError(final String str, final String str2) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.credentials.CredentialManager$CreateCredentialTransport$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onError$2(str, str2);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$2(String str, String str2) {
            this.mCallback.onError(new CreateCredentialException(str, str2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ClearCredentialStateTransport extends IClearCredentialStateCallback.Stub {
        private final OutcomeReceiver<Void, ClearCredentialStateException> mCallback;
        private final Executor mExecutor;

        private ClearCredentialStateTransport(Executor executor, OutcomeReceiver<Void, ClearCredentialStateException> outcomeReceiver) {
            this.mExecutor = executor;
            this.mCallback = outcomeReceiver;
        }

        @Override // android.credentials.IClearCredentialStateCallback
        public void onSuccess() {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mCallback.onResult(null);
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        @Override // android.credentials.IClearCredentialStateCallback
        public void onError(final String str, final String str2) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.credentials.CredentialManager$ClearCredentialStateTransport$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onError$0(str, str2);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$0(String str, String str2) {
            this.mCallback.onError(new ClearCredentialStateException(str, str2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SetEnabledProvidersTransport extends ISetEnabledProvidersCallback.Stub {
        private final OutcomeReceiver<Void, SetEnabledProvidersException> mCallback;
        private final Executor mExecutor;

        private SetEnabledProvidersTransport(Executor executor, OutcomeReceiver<Void, SetEnabledProvidersException> outcomeReceiver) {
            this.mExecutor = executor;
            this.mCallback = outcomeReceiver;
        }

        public void onResponse(final Void r5) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.credentials.CredentialManager$SetEnabledProvidersTransport$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onResponse$0(r5);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResponse$0(Void r1) {
            this.mCallback.onResult(r1);
        }

        @Override // android.credentials.ISetEnabledProvidersCallback
        public void onResponse() {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.credentials.CredentialManager$SetEnabledProvidersTransport$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onResponse$1();
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResponse$1() {
            this.mCallback.onResult(null);
        }

        @Override // android.credentials.ISetEnabledProvidersCallback
        public void onError(final String str, final String str2) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.credentials.CredentialManager$SetEnabledProvidersTransport$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onError$2(str, str2);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$2(String str, String str2) {
            this.mCallback.onError(new SetEnabledProvidersException(str, str2));
        }
    }
}
