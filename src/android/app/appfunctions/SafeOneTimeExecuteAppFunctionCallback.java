package android.app.appfunctions;

import android.os.RemoteException;
import android.util.Log;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class SafeOneTimeExecuteAppFunctionCallback {
    private static final String TAG = "SafeOneTimeExecuteApp";
    private final AppFunctionExecutionRecord mAppFunctionExecutionRecord;
    private final IExecuteAppFunctionCallback mCallback;
    private final CompletionCallback mCompletionCallback;
    private final AtomicLong mExecutionStartTimeAfterBindMillis;
    private final AtomicBoolean mOnResultCalled;
    private final Consumer<AppFunctionExecutionRecord> mUpdateHistoryCallback;

    public interface CompletionCallback {
        void finalizeOnError(AppFunctionException appFunctionException, long j);

        void finalizeOnSuccess(ExecuteAppFunctionResponse executeAppFunctionResponse, long j);
    }

    public SafeOneTimeExecuteAppFunctionCallback(IExecuteAppFunctionCallback iExecuteAppFunctionCallback) {
        this(iExecuteAppFunctionCallback, null, null, null);
    }

    public SafeOneTimeExecuteAppFunctionCallback(IExecuteAppFunctionCallback iExecuteAppFunctionCallback, CompletionCallback completionCallback, Consumer<AppFunctionExecutionRecord> consumer, AppFunctionExecutionRecord appFunctionExecutionRecord) {
        this.mOnResultCalled = new AtomicBoolean(false);
        this.mExecutionStartTimeAfterBindMillis = new AtomicLong();
        this.mCallback = (IExecuteAppFunctionCallback) Objects.requireNonNull(iExecuteAppFunctionCallback);
        this.mCompletionCallback = completionCallback;
        this.mUpdateHistoryCallback = consumer;
        this.mAppFunctionExecutionRecord = appFunctionExecutionRecord;
    }

    public void onResult(ExecuteAppFunctionResponse executeAppFunctionResponse) {
        AppFunctionExecutionRecord appFunctionExecutionRecord;
        if (!this.mOnResultCalled.compareAndSet(false, true)) {
            Log.w(TAG, "Ignore subsequent calls to onResult/onError()");
            return;
        }
        try {
            if (this.mUpdateHistoryCallback != null && (appFunctionExecutionRecord = this.mAppFunctionExecutionRecord) != null) {
                appFunctionExecutionRecord.setResult(executeAppFunctionResponse);
                this.mUpdateHistoryCallback.accept(this.mAppFunctionExecutionRecord);
            }
            this.mCallback.onSuccess(executeAppFunctionResponse);
            CompletionCallback completionCallback = this.mCompletionCallback;
            if (completionCallback != null) {
                completionCallback.finalizeOnSuccess(executeAppFunctionResponse, this.mExecutionStartTimeAfterBindMillis.get());
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to invoke the callback", e);
        }
    }

    public void onError(AppFunctionException appFunctionException) {
        AppFunctionExecutionRecord appFunctionExecutionRecord;
        if (!this.mOnResultCalled.compareAndSet(false, true)) {
            Log.w(TAG, "Ignore subsequent calls to onResult/onError()");
            return;
        }
        try {
            if (this.mUpdateHistoryCallback != null && (appFunctionExecutionRecord = this.mAppFunctionExecutionRecord) != null) {
                appFunctionExecutionRecord.setError(appFunctionException);
                this.mUpdateHistoryCallback.accept(this.mAppFunctionExecutionRecord);
            }
            this.mCallback.onError(appFunctionException);
            CompletionCallback completionCallback = this.mCompletionCallback;
            if (completionCallback != null) {
                completionCallback.finalizeOnError(appFunctionException, this.mExecutionStartTimeAfterBindMillis.get());
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to invoke the callback", e);
        }
    }

    public void disable() {
        this.mOnResultCalled.set(true);
    }

    public void setExecutionStartTimeAfterBindMillis(long j) {
        if (this.mExecutionStartTimeAfterBindMillis.compareAndSet(0L, j)) {
            return;
        }
        Log.w(TAG, "Ignore subsequent calls to setExecutionStartTimeAfterBindMillis()");
    }
}
