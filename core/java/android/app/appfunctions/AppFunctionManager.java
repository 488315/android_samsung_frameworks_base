package android.app.appfunctions;

import android.app.appfunctions.AppFunctionManager;
import android.app.appfunctions.IAppFunctionEnabledCallback;
import android.app.appfunctions.IExecuteAppFunctionCallback;
import android.app.appsearch.AppSearchManager;
import android.content.Context;
import android.os.CancellationSignal;
import android.os.ICancellationSignal;
import android.os.OutcomeReceiver;
import android.os.ParcelableException;
import android.os.RemoteException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final class AppFunctionManager {
    public static final int APP_FUNCTION_STATE_DEFAULT = 0;
    public static final int APP_FUNCTION_STATE_DISABLED = 2;
    public static final int APP_FUNCTION_STATE_ENABLED = 1;
    private final Context mContext;
    private final IAppFunctionManager mService;

    @Retention(RetentionPolicy.SOURCE)
    public @interface EnabledState {
    }

    public AppFunctionManager(IAppFunctionManager service, Context context) {
        this.mService = service;
        this.mContext = context;
    }

    public void executeAppFunction(ExecuteAppFunctionRequest request, Executor executor, CancellationSignal cancellationSignal, OutcomeReceiver<ExecuteAppFunctionResponse, AppFunctionException> callback) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        ExecuteAppFunctionAidlRequest aidlRequest = new ExecuteAppFunctionAidlRequest(request, this.mContext.getUser(), this.mContext.getPackageName());
        try {
            ICancellationSignal cancellationTransport = this.mService.executeAppFunction(aidlRequest, new AnonymousClass1(executor, callback));
            if (cancellationTransport != null) {
                cancellationSignal.setRemote(cancellationTransport);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.app.appfunctions.AppFunctionManager$1, reason: invalid class name */
    class AnonymousClass1 extends IExecuteAppFunctionCallback.Stub {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass1(Executor executor, OutcomeReceiver outcomeReceiver) {
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.app.appfunctions.IExecuteAppFunctionCallback
        public void onSuccess(final ExecuteAppFunctionResponse result) {
            try {
                Executor executor = this.val$executor;
                final OutcomeReceiver outcomeReceiver = this.val$callback;
                executor.execute(new Runnable() { // from class: android.app.appfunctions.AppFunctionManager$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        OutcomeReceiver.this.onResult(result);
                    }
                });
            } catch (RuntimeException e) {
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new Runnable() { // from class: android.app.appfunctions.AppFunctionManager$1$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        OutcomeReceiver.this.onError(new AppFunctionException(2000, e.getMessage()));
                    }
                });
            }
        }

        @Override // android.app.appfunctions.IExecuteAppFunctionCallback
        public void onError(final AppFunctionException exception) {
            Executor executor = this.val$executor;
            final OutcomeReceiver outcomeReceiver = this.val$callback;
            executor.execute(new Runnable() { // from class: android.app.appfunctions.AppFunctionManager$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    OutcomeReceiver.this.onError(exception);
                }
            });
        }
    }

    public void isAppFunctionEnabled(String functionIdentifier, String targetPackage, Executor executor, OutcomeReceiver<Boolean, Exception> callback) {
        isAppFunctionEnabledInternal(functionIdentifier, targetPackage, executor, callback);
    }

    public void isAppFunctionEnabled(String functionIdentifier, Executor executor, OutcomeReceiver<Boolean, Exception> callback) {
        isAppFunctionEnabledInternal(functionIdentifier, this.mContext.getPackageName(), executor, callback);
    }

    public void setAppFunctionEnabled(String functionIdentifier, int newEnabledState, Executor executor, OutcomeReceiver<Void, Exception> callback) {
        Objects.requireNonNull(functionIdentifier);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        CallbackWrapper callbackWrapper = new CallbackWrapper(executor, callback);
        try {
            this.mService.setAppFunctionEnabled(this.mContext.getPackageName(), functionIdentifier, this.mContext.getUser(), newEnabledState, callbackWrapper);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void isAppFunctionEnabledInternal(String functionIdentifier, String targetPackage, Executor executor, OutcomeReceiver<Boolean, Exception> callback) {
        Objects.requireNonNull(functionIdentifier);
        Objects.requireNonNull(targetPackage);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        AppSearchManager appSearchManager = (AppSearchManager) this.mContext.getSystemService(AppSearchManager.class);
        if (appSearchManager == null) {
            callback.onError(new IllegalStateException("Failed to get AppSearchManager."));
        } else {
            AppFunctionManagerHelper.isAppFunctionEnabled(functionIdentifier, targetPackage, appSearchManager, executor, callback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class CallbackWrapper extends IAppFunctionEnabledCallback.Stub {
        private final OutcomeReceiver<Void, Exception> mCallback;
        private final Executor mExecutor;

        CallbackWrapper(Executor callbackExecutor, OutcomeReceiver<Void, Exception> callback) {
            this.mCallback = callback;
            this.mExecutor = callbackExecutor;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0() {
            this.mCallback.onResult(null);
        }

        @Override // android.app.appfunctions.IAppFunctionEnabledCallback
        public void onSuccess() {
            this.mExecutor.execute(new Runnable() { // from class: android.app.appfunctions.AppFunctionManager$CallbackWrapper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    AppFunctionManager.CallbackWrapper.this.lambda$onSuccess$0();
                }
            });
        }

        @Override // android.app.appfunctions.IAppFunctionEnabledCallback
        public void onError(final ParcelableException exception) {
            this.mExecutor.execute(new Runnable() { // from class: android.app.appfunctions.AppFunctionManager$CallbackWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AppFunctionManager.CallbackWrapper.this.lambda$onError$1(exception);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$1(ParcelableException exception) {
            if (IllegalArgumentException.class.isAssignableFrom(exception.getCause().getClass())) {
                this.mCallback.onError((IllegalArgumentException) exception.getCause());
            } else if (SecurityException.class.isAssignableFrom(exception.getCause().getClass())) {
                this.mCallback.onError((SecurityException) exception.getCause());
            } else {
                this.mCallback.onError(exception);
            }
        }
    }
}
