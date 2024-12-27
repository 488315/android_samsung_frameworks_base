package android.app.appfunctions;

import android.Manifest;
import android.app.Service;
import android.app.appfunctions.IAppFunctionService;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.CancellationSignal;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.OutcomeReceiver;
import android.os.RemoteException;

/* loaded from: classes.dex */
public abstract class AppFunctionService extends Service {
    public static final String SERVICE_INTERFACE = "android.app.appfunctions.AppFunctionService";
    private final Binder mBinder = createBinder(this, new OnExecuteFunction() { // from class: android.app.appfunctions.AppFunctionService$$ExternalSyntheticLambda0
        @Override // android.app.appfunctions.AppFunctionService.OnExecuteFunction
        public final void perform(ExecuteAppFunctionRequest executeAppFunctionRequest, String str, CancellationSignal cancellationSignal, OutcomeReceiver outcomeReceiver) {
            AppFunctionService.this.onExecuteFunction(executeAppFunctionRequest, str, cancellationSignal, outcomeReceiver);
        }
    });

    @FunctionalInterface
    public interface OnExecuteFunction {
        void perform(ExecuteAppFunctionRequest executeAppFunctionRequest, String str, CancellationSignal cancellationSignal, OutcomeReceiver<ExecuteAppFunctionResponse, AppFunctionException> outcomeReceiver);
    }

    public abstract void onExecuteFunction(ExecuteAppFunctionRequest executeAppFunctionRequest, String str, CancellationSignal cancellationSignal, OutcomeReceiver<ExecuteAppFunctionResponse, AppFunctionException> outcomeReceiver);

    public static Binder createBinder(final Context context, final OnExecuteFunction onExecuteFunction) {
        return new IAppFunctionService.Stub() { // from class: android.app.appfunctions.AppFunctionService.1
            @Override // android.app.appfunctions.IAppFunctionService
            public void executeAppFunction(ExecuteAppFunctionRequest request, String callingPackage, ICancellationCallback cancellationCallback, IExecuteAppFunctionCallback callback) {
                if (Context.this.checkCallingPermission(Manifest.permission.BIND_APP_FUNCTION_SERVICE) == -1) {
                    throw new SecurityException("Can only be called by the system server.");
                }
                final SafeOneTimeExecuteAppFunctionCallback safeCallback = new SafeOneTimeExecuteAppFunctionCallback(callback);
                try {
                    onExecuteFunction.perform(request, callingPackage, AppFunctionService.buildCancellationSignal(cancellationCallback), new OutcomeReceiver<ExecuteAppFunctionResponse, AppFunctionException>() { // from class: android.app.appfunctions.AppFunctionService.1.1
                        @Override // android.os.OutcomeReceiver
                        public void onResult(ExecuteAppFunctionResponse result) {
                            safeCallback.onResult(result);
                        }

                        @Override // android.os.OutcomeReceiver
                        public void onError(AppFunctionException exception) {
                            safeCallback.onError(exception);
                        }
                    });
                } catch (Exception ex) {
                    safeCallback.onError(new AppFunctionException(AppFunctionService.toErrorCode(ex), ex.getMessage()));
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static CancellationSignal buildCancellationSignal(ICancellationCallback cancellationCallback) {
        ICancellationSignal cancellationSignalTransport = CancellationSignal.createTransport();
        CancellationSignal cancellationSignal = CancellationSignal.fromTransport(cancellationSignalTransport);
        try {
            cancellationCallback.sendCancellationTransport(cancellationSignalTransport);
            return cancellationSignal;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int toErrorCode(Throwable t) {
        if (t instanceof IllegalArgumentException) {
            return 1001;
        }
        return 3000;
    }
}
