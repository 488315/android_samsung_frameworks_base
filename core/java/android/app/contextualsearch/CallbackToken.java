package android.app.contextualsearch;

import android.annotation.SystemApi;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.OutcomeReceiver;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ParcelableException;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

import com.android.internal.util.FunctionalUtils;

import java.util.concurrent.Executor;

@SystemApi
public final class CallbackToken implements Parcelable {
    private static final boolean DEBUG = true;
    private final Object mLock;
    private final IBinder mToken;
    private boolean mTokenUsed;
    private static final String TAG = CallbackToken.class.getSimpleName();
    public static final Parcelable.Creator<CallbackToken> CREATOR =
            new Parcelable.Creator<
                    CallbackToken>() { // from class: android.app.contextualsearch.CallbackToken.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public CallbackToken createFromParcel(Parcel in) {
                    return new CallbackToken(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public CallbackToken[] newArray(int size) {
                    return new CallbackToken[size];
                }
            };

    public CallbackToken() {
        this.mLock = new Object();
        this.mTokenUsed = false;
        this.mToken = new Binder();
    }

    private CallbackToken(Parcel in) {
        this.mLock = new Object();
        this.mTokenUsed = false;
        this.mToken = in.readStrongBinder();
    }

    public void getContextualSearchState(
            Executor executor, OutcomeReceiver<ContextualSearchState, Throwable> callback) {
        boolean tokenUsed;
        Log.d(TAG, "getContextualSearchState for token:" + this.mToken);
        synchronized (this.mLock) {
            tokenUsed = markUsedLocked();
        }
        if (tokenUsed) {
            callback.onError(new IllegalAccessException("Token already used."));
            return;
        }
        try {
            IBinder b = ServiceManager.getService(Context.CONTEXTUAL_SEARCH_SERVICE);
            IContextualSearchManager service = IContextualSearchManager.Stub.asInterface(b);
            CallbackWrapper wrapper = new CallbackWrapper(executor, callback);
            if (service != null) {
                service.getContextualSearchState(this.mToken, wrapper);
            } else {
                Log.w(TAG, "Failed to getContextualSearchState. Service null.");
            }
        } catch (RemoteException e) {
            Log.d(TAG, "Failed to call getContextualSearchState", e);
            e.rethrowFromSystemServer();
        }
    }

    private boolean markUsedLocked() {
        boolean oldValue = this.mTokenUsed;
        this.mTokenUsed = true;
        return oldValue;
    }

    public IBinder getToken() {
        return this.mToken;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStrongBinder(this.mToken);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class CallbackWrapper extends IContextualSearchCallback.Stub {
        private final OutcomeReceiver<ContextualSearchState, Throwable> mCallback;
        private final Executor mExecutor;

        CallbackWrapper(
                Executor callbackExecutor,
                OutcomeReceiver<ContextualSearchState, Throwable> callback) {
            this.mCallback = callback;
            this.mExecutor = callbackExecutor;
        }

        @Override // android.app.contextualsearch.IContextualSearchCallback
        public void onResult(final ContextualSearchState state) {
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils.ThrowingRunnable() { // from class:
                        // android.app.contextualsearch.CallbackToken$CallbackWrapper$$ExternalSyntheticLambda2
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            CallbackToken.CallbackWrapper.this.lambda$onResult$1(state);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResult$1(final ContextualSearchState state)
                throws Exception {
            Log.d(CallbackToken.TAG, "onResult state:" + state);
            this.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.app.contextualsearch.CallbackToken$CallbackWrapper$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            CallbackToken.CallbackWrapper.this.lambda$onResult$0(state);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResult$0(ContextualSearchState state) {
            this.mCallback.onResult(state);
        }

        @Override // android.app.contextualsearch.IContextualSearchCallback
        public void onError(final ParcelableException error) {
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils.ThrowingRunnable() { // from class:
                        // android.app.contextualsearch.CallbackToken$CallbackWrapper$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            CallbackToken.CallbackWrapper.this.lambda$onError$3(error);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$3(final ParcelableException error)
                throws Exception {
            Log.w(CallbackToken.TAG, "onError", error);
            this.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.app.contextualsearch.CallbackToken$CallbackWrapper$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            CallbackToken.CallbackWrapper.this.lambda$onError$2(error);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$2(ParcelableException error) {
            this.mCallback.onError(error);
        }
    }
}
