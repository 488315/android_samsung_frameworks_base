package android.app.appfunctions;

import android.Manifest;
import android.app.Service;
import android.app.appfunctions.IAppFunctionService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.SigningInfo;
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
        public final void perform(ExecuteAppFunctionRequest executeAppFunctionRequest, String str, SigningInfo signingInfo, CancellationSignal cancellationSignal, OutcomeReceiver outcomeReceiver) {
            this.f$0.onExecuteFunction(executeAppFunctionRequest, str, signingInfo, cancellationSignal, outcomeReceiver);
        }
    });

    @FunctionalInterface
    public interface OnExecuteFunction {
        void perform(ExecuteAppFunctionRequest executeAppFunctionRequest, String str, SigningInfo signingInfo, CancellationSignal cancellationSignal, OutcomeReceiver<ExecuteAppFunctionResponse, AppFunctionException> outcomeReceiver);
    }

    public abstract void onExecuteFunction(ExecuteAppFunctionRequest executeAppFunctionRequest, String str, SigningInfo signingInfo, CancellationSignal cancellationSignal, OutcomeReceiver<ExecuteAppFunctionResponse, AppFunctionException> outcomeReceiver);

    public static Binder createBinder(final Context context, final OnExecuteFunction onExecuteFunction) {
        return new IAppFunctionService.Stub() { // from class: android.app.appfunctions.AppFunctionService.1
            @Override // android.app.appfunctions.IAppFunctionService
            public void executeAppFunction(ExecuteAppFunctionRequest executeAppFunctionRequest, String str, SigningInfo signingInfo, ICancellationCallback iCancellationCallback, IExecuteAppFunctionCallback iExecuteAppFunctionCallback) {
                if (context.checkCallingPermission(Manifest.permission.BIND_APP_FUNCTION_SERVICE) == -1) {
                    throw new SecurityException("Can only be called by the system server.");
                }
                final SafeOneTimeExecuteAppFunctionCallback safeOneTimeExecuteAppFunctionCallback = new SafeOneTimeExecuteAppFunctionCallback(iExecuteAppFunctionCallback);
                try {
                    onExecuteFunction.perform(executeAppFunctionRequest, str, signingInfo, AppFunctionService.buildCancellationSignal(iCancellationCallback), new OutcomeReceiver<ExecuteAppFunctionResponse, AppFunctionException>(this) { // from class: android.app.appfunctions.AppFunctionService.1.1
                        @Override // android.os.OutcomeReceiver
                        public void onResult(ExecuteAppFunctionResponse executeAppFunctionResponse) {
                            safeOneTimeExecuteAppFunctionCallback.onResult(executeAppFunctionResponse);
                        }

                        @Override // android.os.OutcomeReceiver
                        public void onError(AppFunctionException appFunctionException) {
                            safeOneTimeExecuteAppFunctionCallback.onError(appFunctionException);
                        }
                    });
                } catch (Exception e) {
                    safeOneTimeExecuteAppFunctionCallback.onError(new AppFunctionException(AppFunctionService.toErrorCode(e), e.getMessage()));
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static CancellationSignal buildCancellationSignal(ICancellationCallback iCancellationCallback) {
        ICancellationSignal iCancellationSignalCreateTransport = CancellationSignal.createTransport();
        CancellationSignal cancellationSignalFromTransport = CancellationSignal.fromTransport(iCancellationSignalCreateTransport);
        try {
            iCancellationCallback.sendCancellationTransport(iCancellationSignalCreateTransport);
            return cancellationSignalFromTransport;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int toErrorCode(Throwable th) {
        return th instanceof IllegalArgumentException ? 1001 : 3000;
    }
}
