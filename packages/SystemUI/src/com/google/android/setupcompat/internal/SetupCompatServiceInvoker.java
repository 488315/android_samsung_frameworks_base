package com.google.android.setupcompat.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.setupcompat.ISetupCompatService;
import com.google.android.setupcompat.util.Logger;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes4.dex */
public class SetupCompatServiceInvoker {
    public static final Logger LOG = new Logger("SetupCompatServiceInvoker");
    public static final long MAX_WAIT_TIME_FOR_CONNECTION_MS = TimeUnit.SECONDS.toMillis(10);
    public static SetupCompatServiceInvoker instance;
    public final Context context;
    public final ExecutorService loggingExecutor;
    public final long waitTimeInMillisForServiceConnection;

    private SetupCompatServiceInvoker(Context context) {
        this.context = context;
        ExecutorProvider executorProvider = ExecutorProvider.setupCompatServiceInvoker;
        Executor executor = executorProvider.injectedExecutor;
        this.loggingExecutor = (ExecutorService) (executor == null ? executorProvider.executor : executor);
        this.waitTimeInMillisForServiceConnection = MAX_WAIT_TIME_FOR_CONNECTION_MS;
    }

    public static synchronized SetupCompatServiceInvoker get(Context context) {
        try {
            if (instance == null) {
                instance = new SetupCompatServiceInvoker(context.getApplicationContext());
            }
        } catch (Throwable th) {
            throw th;
        }
        return instance;
    }

    public static void setInstanceForTesting(SetupCompatServiceInvoker setupCompatServiceInvoker) {
        instance = setupCompatServiceInvoker;
    }

    public final void logMetricEvent(final int i, final Bundle bundle) {
        try {
            this.loggingExecutor.execute(new Runnable() { // from class: com.google.android.setupcompat.internal.SetupCompatServiceInvoker$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SetupCompatServiceInvoker setupCompatServiceInvoker = this.f$0;
                    int i2 = i;
                    Bundle bundle2 = bundle;
                    Logger logger = SetupCompatServiceInvoker.LOG;
                    setupCompatServiceInvoker.getClass();
                    Logger logger2 = SetupCompatServiceInvoker.LOG;
                    try {
                        ISetupCompatService service = SetupCompatServiceProvider.getInstance(setupCompatServiceInvoker.context).getService(setupCompatServiceInvoker.waitTimeInMillisForServiceConnection, TimeUnit.MILLISECONDS);
                        if (service == null) {
                            logger2.w("logMetric failed since service reference is null. Are the permissions valid?");
                        } else {
                            Bundle bundle3 = Bundle.EMPTY;
                            ((ISetupCompatService.Stub.Proxy) service).logMetric(i2, bundle2);
                        }
                    } catch (RemoteException | IllegalStateException | InterruptedException | TimeoutException e) {
                        logger2.e("Exception occurred while trying to log metric = [" + bundle2 + "]", e);
                    }
                }
            });
        } catch (RejectedExecutionException e) {
            LOG.e(String.format("Metric of type %d dropped since queue is full.", Integer.valueOf(i)), e);
        }
    }
}
