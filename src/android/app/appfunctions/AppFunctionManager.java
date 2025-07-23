package android.app.appfunctions;

import android.app.appfunctions.AppFunctionManager;
import android.app.appfunctions.AppFunctionManagerHelper;
import android.app.appfunctions.IAppFunctionEnabledCallback;
import android.app.appfunctions.IExecuteAppFunctionCallback;
import android.app.appsearch.AppSearchManager;
import android.content.Context;
import android.os.CancellationSignal;
import android.os.ICancellationSignal;
import android.os.OutcomeReceiver;
import android.os.ParcelableException;
import android.os.RemoteException;
import android.os.SystemClock;
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

    public AppFunctionManager(IAppFunctionManager iAppFunctionManager, Context context) {
        this.mService = iAppFunctionManager;
        this.mContext = context;
    }

    public void executeAppFunction(ExecuteAppFunctionRequest executeAppFunctionRequest, Executor executor, CancellationSignal cancellationSignal, OutcomeReceiver<ExecuteAppFunctionResponse, AppFunctionException> outcomeReceiver) {
        Objects.requireNonNull(executeAppFunctionRequest);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        try {
            ICancellationSignal executeAppFunction = this.mService.executeAppFunction(new ExecuteAppFunctionAidlRequest(executeAppFunctionRequest, this.mContext.getUser(), this.mContext.getPackageName(), SystemClock.elapsedRealtime()), new AnonymousClass1(this, executor, outcomeReceiver));
            if (executeAppFunction != null) {
                cancellationSignal.setRemote(executeAppFunction);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.app.appfunctions.AppFunctionManager$1, reason: invalid class name */
    class AnonymousClass1 extends IExecuteAppFunctionCallback.Stub {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass1(AppFunctionManager appFunctionManager, Executor executor, OutcomeReceiver outcomeReceiver) {
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.app.appfunctions.IExecuteAppFunctionCallback
        public void onSuccess(final ExecuteAppFunctionResponse executeAppFunctionResponse) {
            try {
                Executor executor = this.val$executor;
                final OutcomeReceiver outcomeReceiver = this.val$callback;
                executor.execute(new Runnable() { // from class: android.app.appfunctions.AppFunctionManager$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        OutcomeReceiver.this.onResult(executeAppFunctionResponse);
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
        public void onError(final AppFunctionException appFunctionException) {
            Executor executor = this.val$executor;
            final OutcomeReceiver outcomeReceiver = this.val$callback;
            executor.execute(new Runnable() { // from class: android.app.appfunctions.AppFunctionManager$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    OutcomeReceiver.this.onError(appFunctionException);
                }
            });
        }
    }

    public void isAppFunctionEnabled(String str, String str2, Executor executor, OutcomeReceiver<Boolean, Exception> outcomeReceiver) {
        isAppFunctionEnabledInternal(str, str2, executor, outcomeReceiver);
    }

    public void isAppFunctionEnabled(String str, Executor executor, OutcomeReceiver<Boolean, Exception> outcomeReceiver) {
        isAppFunctionEnabledInternal(str, this.mContext.getPackageName(), executor, outcomeReceiver);
    }

    public void setAppFunctionEnabled(String str, int i, Executor executor, OutcomeReceiver<Void, Exception> outcomeReceiver) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        try {
            this.mService.setAppFunctionEnabled(this.mContext.getPackageName(), str, this.mContext.getUser(), i, new CallbackWrapper(executor, outcomeReceiver));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void isAppFunctionEnabledInternal(String str, String str2, Executor executor, final OutcomeReceiver<Boolean, Exception> outcomeReceiver) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(str2);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        AppSearchManager appSearchManager = (AppSearchManager) this.mContext.getSystemService(AppSearchManager.class);
        if (appSearchManager == null) {
            outcomeReceiver.onError(new IllegalStateException("Failed to get AppSearchManager."));
        } else {
            AppFunctionManagerHelper.isAppFunctionEnabled(str, str2, appSearchManager, executor, new OutcomeReceiver<Boolean, Exception>(this) { // from class: android.app.appfunctions.AppFunctionManager.2
                @Override // android.os.OutcomeReceiver
                public void onResult(Boolean bool) {
                    outcomeReceiver.onResult(bool);
                }

                @Override // android.os.OutcomeReceiver
                public void onError(Exception exc) {
                    if (exc instanceof AppFunctionManagerHelper.AppFunctionNotFoundException) {
                        exc = new IllegalArgumentException(exc);
                    }
                    outcomeReceiver.onError(exc);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class CallbackWrapper extends IAppFunctionEnabledCallback.Stub {
        private final OutcomeReceiver<Void, Exception> mCallback;
        private final Executor mExecutor;

        CallbackWrapper(Executor executor, OutcomeReceiver<Void, Exception> outcomeReceiver) {
            this.mCallback = outcomeReceiver;
            this.mExecutor = executor;
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
        public void onError(final ParcelableException parcelableException) {
            this.mExecutor.execute(new Runnable() { // from class: android.app.appfunctions.AppFunctionManager$CallbackWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AppFunctionManager.CallbackWrapper.this.lambda$onError$1(parcelableException);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$1(ParcelableException parcelableException) {
            if (IllegalArgumentException.class.isAssignableFrom(parcelableException.getCause().getClass())) {
                this.mCallback.onError((IllegalArgumentException) parcelableException.getCause());
            } else if (SecurityException.class.isAssignableFrom(parcelableException.getCause().getClass())) {
                this.mCallback.onError((SecurityException) parcelableException.getCause());
            } else {
                this.mCallback.onError(parcelableException);
            }
        }
    }
}
