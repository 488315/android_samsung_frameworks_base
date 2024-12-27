package com.android.server.appfunctions;

import android.app.appfunctions.ExecuteAppFunctionAidlRequest;
import android.app.appfunctions.ICancellationCallback;
import android.app.appfunctions.SafeOneTimeExecuteAppFunctionCallback;

public final class RunAppFunctionServiceCallback {
    public final ICancellationCallback mCancellationCallback;
    public final ExecuteAppFunctionAidlRequest mRequestInternal;
    public final SafeOneTimeExecuteAppFunctionCallback mSafeExecuteAppFunctionCallback;

    public RunAppFunctionServiceCallback(
            ExecuteAppFunctionAidlRequest executeAppFunctionAidlRequest,
            AppFunctionManagerServiceImpl.AnonymousClass2 anonymousClass2,
            SafeOneTimeExecuteAppFunctionCallback safeOneTimeExecuteAppFunctionCallback) {
        this.mRequestInternal = executeAppFunctionAidlRequest;
        this.mSafeExecuteAppFunctionCallback = safeOneTimeExecuteAppFunctionCallback;
        this.mCancellationCallback = anonymousClass2;
    }
}
