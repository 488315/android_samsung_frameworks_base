package com.android.server.appfunctions;

import android.app.appfunctions.AppFunctionException;
import android.app.appfunctions.ExecuteAppFunctionResponse;
import android.app.appfunctions.IAppFunctionService;
import android.app.appfunctions.IExecuteAppFunctionCallback;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.UserHandle;
import android.util.Log;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RemoteServiceCallerImpl implements RemoteServiceCaller {
    public final Context mContext;
    public final Executor mExecutor;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public final Function mInterfaceConverter;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class OneOffServiceConnection implements ServiceConnection {
        public final RunAppFunctionServiceCallback mCallback;
        public final IBinder mCallerBinder;
        public final CancellationSignal mCancellationSignal;
        public final long mCancellationTimeoutMillis;
        public final RemoteServiceCallerImpl$OneOffServiceConnection$$ExternalSyntheticLambda2
                mCancellationTimeoutRunnable =
                        new RemoteServiceCallerImpl$OneOffServiceConnection$$ExternalSyntheticLambda2(
                                1, this);
        public RemoteServiceCallerImpl$OneOffServiceConnection$$ExternalSyntheticLambda1
                mDirectServiceVulture;

        public OneOffServiceConnection(
                Intent intent,
                UserHandle userHandle,
                long j,
                CancellationSignal cancellationSignal,
                RunAppFunctionServiceCallback runAppFunctionServiceCallback,
                IBinder iBinder) {
            this.mCallback = runAppFunctionServiceCallback;
            this.mCancellationTimeoutMillis = j;
            this.mCancellationSignal = cancellationSignal;
            this.mCallerBinder = iBinder;
        }

        @Override // android.content.ServiceConnection
        public final void onBindingDied(ComponentName componentName) {
            safeUnbind();
            Executor executor = RemoteServiceCallerImpl.this.mExecutor;
            RunAppFunctionServiceCallback runAppFunctionServiceCallback = this.mCallback;
            Objects.requireNonNull(runAppFunctionServiceCallback);
            executor.execute(
                    new RemoteServiceCallerImpl$OneOffServiceConnection$$ExternalSyntheticLambda2(
                            0, runAppFunctionServiceCallback));
        }

        @Override // android.content.ServiceConnection
        public final void onNullBinding(ComponentName componentName) {
            safeUnbind();
            Executor executor = RemoteServiceCallerImpl.this.mExecutor;
            RunAppFunctionServiceCallback runAppFunctionServiceCallback = this.mCallback;
            Objects.requireNonNull(runAppFunctionServiceCallback);
            executor.execute(
                    new RemoteServiceCallerImpl$OneOffServiceConnection$$ExternalSyntheticLambda2(
                            0, runAppFunctionServiceCallback));
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            final Object apply = RemoteServiceCallerImpl.this.mInterfaceConverter.apply(iBinder);
            RemoteServiceCallerImpl.this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // com.android.server.appfunctions.RemoteServiceCallerImpl$OneOffServiceConnection$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            RemoteServiceCallerImpl.OneOffServiceConnection
                                    oneOffServiceConnection =
                                            RemoteServiceCallerImpl.OneOffServiceConnection.this;
                            Object obj = apply;
                            RunAppFunctionServiceCallback runAppFunctionServiceCallback =
                                    oneOffServiceConnection.mCallback;
                            runAppFunctionServiceCallback.getClass();
                            try {
                                ((IAppFunctionService) obj)
                                        .executeAppFunction(
                                                runAppFunctionServiceCallback.mRequestInternal
                                                        .getClientRequest(),
                                                runAppFunctionServiceCallback.mRequestInternal
                                                        .getCallingPackage(),
                                                runAppFunctionServiceCallback.mCancellationCallback,
                                                new IExecuteAppFunctionCallback
                                                        .Stub() { // from class:
                                                                  // com.android.server.appfunctions.RunAppFunctionServiceCallback.1
                                                    public final /* synthetic */
                                                    RemoteServiceCallerImpl.OneOffServiceConnection
                                                            val$serviceUsageCompleteListener;

                                                    public AnonymousClass1(
                                                            RemoteServiceCallerImpl
                                                                            .OneOffServiceConnection
                                                                    oneOffServiceConnection2) {
                                                        r2 = oneOffServiceConnection2;
                                                    }

                                                    public final void onError(
                                                            AppFunctionException
                                                                    appFunctionException) {
                                                        RunAppFunctionServiceCallback.this
                                                                .mSafeExecuteAppFunctionCallback
                                                                .onError(appFunctionException);
                                                        r2.safeUnbind();
                                                    }

                                                    public final void onSuccess(
                                                            ExecuteAppFunctionResponse
                                                                    executeAppFunctionResponse) {
                                                        RunAppFunctionServiceCallback.this
                                                                .mSafeExecuteAppFunctionCallback
                                                                .onResult(
                                                                        executeAppFunctionResponse);
                                                        r2.safeUnbind();
                                                    }
                                                });
                            } catch (Exception e) {
                                runAppFunctionServiceCallback.mSafeExecuteAppFunctionCallback
                                        .onError(new AppFunctionException(3000, e.getMessage()));
                                oneOffServiceConnection2.safeUnbind();
                            }
                        }
                    });
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            safeUnbind();
            Executor executor = RemoteServiceCallerImpl.this.mExecutor;
            RunAppFunctionServiceCallback runAppFunctionServiceCallback = this.mCallback;
            Objects.requireNonNull(runAppFunctionServiceCallback);
            executor.execute(
                    new RemoteServiceCallerImpl$OneOffServiceConnection$$ExternalSyntheticLambda2(
                            0, runAppFunctionServiceCallback));
        }

        public final void safeUnbind() {
            try {
                RemoteServiceCallerImpl.this.mHandler.removeCallbacks(
                        this.mCancellationTimeoutRunnable);
                RemoteServiceCallerImpl.this.mContext.unbindService(this);
                RemoteServiceCallerImpl$OneOffServiceConnection$$ExternalSyntheticLambda1
                        remoteServiceCallerImpl$OneOffServiceConnection$$ExternalSyntheticLambda1 =
                                this.mDirectServiceVulture;
                if (remoteServiceCallerImpl$OneOffServiceConnection$$ExternalSyntheticLambda1
                        != null) {
                    this.mCallerBinder.unlinkToDeath(
                            remoteServiceCallerImpl$OneOffServiceConnection$$ExternalSyntheticLambda1,
                            0);
                }
            } catch (Exception e) {
                Log.w("AppFunctionsServiceCall", "Failed to unbind", e);
            }
        }
    }

    public RemoteServiceCallerImpl(
            Context context,
            AppFunctionManagerServiceImpl$$ExternalSyntheticLambda0
                    appFunctionManagerServiceImpl$$ExternalSyntheticLambda0,
            Executor executor) {
        this.mContext = context;
        this.mInterfaceConverter = appFunctionManagerServiceImpl$$ExternalSyntheticLambda0;
        this.mExecutor = executor;
    }
}
