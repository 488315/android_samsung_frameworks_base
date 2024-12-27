package com.android.server.connectivity;

import android.util.Log;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public final /* synthetic */ class Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda0
        implements RejectedExecutionHandler {
    @Override // java.util.concurrent.RejectedExecutionHandler
    public final void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
        Log.d("IkeV2VpnRunner", "Runnable " + runnable + " rejected by the mExecutor");
    }
}
