package android.app.appfunctions;

import android.os.RemoteException;
import android.util.Log;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class SafeOneTimeExecuteAppFunctionCallback {
    private static final String TAG = "SafeOneTimeExecuteApp";
    private final AppFunctionExecutionRecord mAppFunctionExecutionRecord;
    private final IExecuteAppFunctionCallback mCallback;
    private final AtomicBoolean mOnResultCalled;
    private final Consumer<AppFunctionExecutionRecord> mUpdateHistoryCallback;

    public SafeOneTimeExecuteAppFunctionCallback(IExecuteAppFunctionCallback callback) {
        this.mOnResultCalled = new AtomicBoolean(false);
        this.mCallback = (IExecuteAppFunctionCallback) Objects.requireNonNull(callback);
        this.mUpdateHistoryCallback = null;
        this.mAppFunctionExecutionRecord = null;
    }

    public SafeOneTimeExecuteAppFunctionCallback(
            IExecuteAppFunctionCallback callback,
            Consumer<AppFunctionExecutionRecord> updateHistoryCallback,
            AppFunctionExecutionRecord appFunctionExecutionRecord) {
        this.mOnResultCalled = new AtomicBoolean(false);
        this.mCallback = (IExecuteAppFunctionCallback) Objects.requireNonNull(callback);
        this.mUpdateHistoryCallback = (Consumer) Objects.requireNonNull(updateHistoryCallback);
        this.mAppFunctionExecutionRecord =
                (AppFunctionExecutionRecord) Objects.requireNonNull(appFunctionExecutionRecord);
    }

    public void onResult(ExecuteAppFunctionResponse result) {
        if (!this.mOnResultCalled.compareAndSet(false, true)) {
            Log.w(TAG, "Ignore subsequent calls to onResult/onError()");
            return;
        }
        try {
            if (this.mUpdateHistoryCallback != null) {
                this.mAppFunctionExecutionRecord.setResult(result);
                this.mUpdateHistoryCallback.accept(this.mAppFunctionExecutionRecord);
            }
            this.mCallback.onSuccess(result);
        } catch (RemoteException ex) {
            Log.w(TAG, "Failed to invoke the callback", ex);
        }
    }

    public void onError(AppFunctionException error) {
        if (!this.mOnResultCalled.compareAndSet(false, true)) {
            Log.w(TAG, "Ignore subsequent calls to onResult/onError()");
            return;
        }
        try {
            if (this.mUpdateHistoryCallback != null) {
                this.mAppFunctionExecutionRecord.setError(error);
                this.mUpdateHistoryCallback.accept(this.mAppFunctionExecutionRecord);
            }
            this.mCallback.onError(error);
        } catch (RemoteException ex) {
            Log.w(TAG, "Failed to invoke the callback", ex);
        }
    }

    public void disable() {
        this.mOnResultCalled.set(true);
    }
}
