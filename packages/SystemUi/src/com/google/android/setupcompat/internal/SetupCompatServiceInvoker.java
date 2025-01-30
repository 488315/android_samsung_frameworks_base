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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SetupCompatServiceInvoker {
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
        SetupCompatServiceInvoker setupCompatServiceInvoker;
        synchronized (SetupCompatServiceInvoker.class) {
            if (instance == null) {
                instance = new SetupCompatServiceInvoker(context.getApplicationContext());
            }
            setupCompatServiceInvoker = instance;
        }
        return setupCompatServiceInvoker;
    }

    public static void setInstanceForTesting(SetupCompatServiceInvoker setupCompatServiceInvoker) {
        instance = setupCompatServiceInvoker;
    }

    public final void logMetricEvent(final int i, final Bundle bundle) {
        try {
            this.loggingExecutor.execute(new Runnable() { // from class: com.google.android.setupcompat.internal.SetupCompatServiceInvoker$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SetupCompatServiceInvoker setupCompatServiceInvoker = SetupCompatServiceInvoker.this;
                    int i2 = i;
                    Bundle bundle2 = bundle;
                    setupCompatServiceInvoker.getClass();
                    Logger logger = SetupCompatServiceInvoker.LOG;
                    try {
                        Context context = setupCompatServiceInvoker.context;
                        ISetupCompatService service = SetupCompatServiceProvider.getInstance(context).getService(setupCompatServiceInvoker.waitTimeInMillisForServiceConnection, TimeUnit.MILLISECONDS);
                        if (service != null) {
                            ((ISetupCompatService.Stub.Proxy) service).logMetric(i2, bundle2, Bundle.EMPTY);
                        } else {
                            logger.m239w("logMetric failed since service reference is null. Are the permissions valid?");
                        }
                    } catch (RemoteException | IllegalStateException | InterruptedException | TimeoutException e) {
                        logger.m238e(String.format("Exception occurred while trying to log metric = [%s]", bundle2), e);
                    }
                }
            });
        } catch (RejectedExecutionException e) {
            LOG.m238e(String.format("Metric of type %d dropped since queue is full.", Integer.valueOf(i)), e);
        }
    }
}
