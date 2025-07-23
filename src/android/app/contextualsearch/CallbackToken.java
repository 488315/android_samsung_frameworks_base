package android.app.contextualsearch;

import android.annotation.SystemApi;
import android.app.contextualsearch.CallbackToken;
import android.app.contextualsearch.IContextualSearchCallback;
import android.app.contextualsearch.IContextualSearchManager;
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
/* loaded from: classes.dex */
public final class CallbackToken implements Parcelable {
    public static final Parcelable.Creator<CallbackToken> CREATOR = new Parcelable.Creator<CallbackToken>() { // from class: android.app.contextualsearch.CallbackToken.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CallbackToken createFromParcel(Parcel parcel) {
            return new CallbackToken(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CallbackToken[] newArray(int i) {
            return new CallbackToken[i];
        }
    };
    private static final boolean DEBUG = true;
    private static final String TAG = "CallbackToken";
    private final Object mLock;
    private final IBinder mToken;
    private boolean mTokenUsed;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CallbackToken() {
        this.mLock = new Object();
        this.mTokenUsed = false;
        this.mToken = new Binder();
    }

    private CallbackToken(Parcel parcel) {
        this.mLock = new Object();
        this.mTokenUsed = false;
        this.mToken = parcel.readStrongBinder();
    }

    public void getContextualSearchState(Executor executor, OutcomeReceiver<ContextualSearchState, Throwable> outcomeReceiver) {
        boolean markUsedLocked;
        String str = TAG;
        Log.d(str, "getContextualSearchState for token:" + this.mToken);
        synchronized (this.mLock) {
            markUsedLocked = markUsedLocked();
        }
        if (markUsedLocked) {
            outcomeReceiver.onError(new IllegalAccessException("Token already used."));
            return;
        }
        try {
            IContextualSearchManager asInterface = IContextualSearchManager.Stub.asInterface(ServiceManager.getService(Context.CONTEXTUAL_SEARCH_SERVICE));
            CallbackWrapper callbackWrapper = new CallbackWrapper(executor, outcomeReceiver);
            if (asInterface != null) {
                asInterface.getContextualSearchState(this.mToken, callbackWrapper);
            } else {
                Log.w(str, "Failed to getContextualSearchState. Service null.");
            }
        } catch (RemoteException e) {
            Log.d(TAG, "Failed to call getContextualSearchState", e);
            e.rethrowFromSystemServer();
        }
    }

    private boolean markUsedLocked() {
        boolean z = this.mTokenUsed;
        this.mTokenUsed = true;
        return z;
    }

    public IBinder getToken() {
        return this.mToken;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mToken);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class CallbackWrapper extends IContextualSearchCallback.Stub {
        private final OutcomeReceiver<ContextualSearchState, Throwable> mCallback;
        private final Executor mExecutor;

        CallbackWrapper(Executor executor, OutcomeReceiver<ContextualSearchState, Throwable> outcomeReceiver) {
            this.mCallback = outcomeReceiver;
            this.mExecutor = executor;
        }

        @Override // android.app.contextualsearch.IContextualSearchCallback
        public void onResult(final ContextualSearchState contextualSearchState) {
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.app.contextualsearch.CallbackToken$CallbackWrapper$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    CallbackToken.CallbackWrapper.this.lambda$onResult$1(contextualSearchState);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResult$1(final ContextualSearchState contextualSearchState) throws Exception {
            Log.d(CallbackToken.TAG, "onResult state:" + contextualSearchState);
            this.mExecutor.execute(new Runnable() { // from class: android.app.contextualsearch.CallbackToken$CallbackWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CallbackToken.CallbackWrapper.this.lambda$onResult$0(contextualSearchState);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResult$0(ContextualSearchState contextualSearchState) {
            this.mCallback.onResult(contextualSearchState);
        }

        @Override // android.app.contextualsearch.IContextualSearchCallback
        public void onError(final ParcelableException parcelableException) {
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.app.contextualsearch.CallbackToken$CallbackWrapper$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    CallbackToken.CallbackWrapper.this.lambda$onError$3(parcelableException);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$3(final ParcelableException parcelableException) throws Exception {
            Log.w(CallbackToken.TAG, "onError", parcelableException);
            this.mExecutor.execute(new Runnable() { // from class: android.app.contextualsearch.CallbackToken$CallbackWrapper$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    CallbackToken.CallbackWrapper.this.lambda$onError$2(parcelableException);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$2(ParcelableException parcelableException) {
            this.mCallback.onError(parcelableException);
        }
    }
}
