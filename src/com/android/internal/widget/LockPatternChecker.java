package com.android.internal.widget;

import android.os.AsyncTask;
import android.util.Log;
import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockPatternUtils;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class LockPatternChecker {

    public interface OnCheckCallback {
        default void onCancelled() {
        }

        void onChecked(boolean z, int i);

        default void onEarlyMatched() {
        }
    }

    public interface OnCheckCallbackForDualDarDo extends LockPatternUtils.DualDarAuthProgressCallback {
        default void onCancelled() {
        }

        void onChecked(boolean z, int i);
    }

    public interface OnVerifyCallback {
        void onVerified(VerifyCredentialResponse verifyCredentialResponse, int i);
    }

    public static AsyncTask<?, ?, ?> verifyCredential(final LockPatternUtils lockPatternUtils, LockscreenCredential lockscreenCredential, final int i, final int i2, final OnVerifyCallback onVerifyCallback) {
        final LockscreenCredential duplicate = lockscreenCredential.duplicate();
        AsyncTask<Void, Void, VerifyCredentialResponse> asyncTask = new AsyncTask<Void, Void, VerifyCredentialResponse>() { // from class: com.android.internal.widget.LockPatternChecker.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public VerifyCredentialResponse doInBackground(Void... voidArr) {
                return LockPatternUtils.this.verifyCredential(duplicate, i, i2);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(VerifyCredentialResponse verifyCredentialResponse) {
                onVerifyCallback.onVerified(verifyCredentialResponse, verifyCredentialResponse.getTimeout());
                duplicate.zeroize();
            }

            @Override // android.os.AsyncTask
            protected void onCancelled() {
                duplicate.zeroize();
            }
        };
        asyncTask.execute(new Void[0]);
        return asyncTask;
    }

    public static AsyncTask<?, ?, ?> checkCredential(final LockPatternUtils lockPatternUtils, LockscreenCredential lockscreenCredential, final int i, final OnCheckCallback onCheckCallback) {
        final LockscreenCredential duplicate = lockscreenCredential.duplicate();
        AsyncTask<Void, Void, Boolean> asyncTask = new AsyncTask<Void, Void, Boolean>() { // from class: com.android.internal.widget.LockPatternChecker.2
            private int mThrottleTimeout;

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public Boolean doInBackground(Void... voidArr) {
                try {
                    LockPatternUtils lockPatternUtils2 = LockPatternUtils.this;
                    LockscreenCredential lockscreenCredential2 = duplicate;
                    int i2 = i;
                    final OnCheckCallback onCheckCallback2 = onCheckCallback;
                    Objects.requireNonNull(onCheckCallback2);
                    return Boolean.valueOf(lockPatternUtils2.checkCredential(lockscreenCredential2, i2, new LockPatternUtils.CheckCredentialProgressCallback() { // from class: com.android.internal.widget.LockPatternChecker$2$$ExternalSyntheticLambda0
                        @Override // com.android.internal.widget.LockPatternUtils.CheckCredentialProgressCallback
                        public final void onEarlyMatched() {
                            LockPatternChecker.OnCheckCallback.this.onEarlyMatched();
                        }
                    }));
                } catch (LockPatternUtils.RequestThrottledException e) {
                    this.mThrottleTimeout = e.getTimeoutMs();
                    Log.w("LockPatternChecker", "checkCredential : RequestThrottledException! ThrottleTimeout = " + this.mThrottleTimeout);
                    return false;
                }
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(Boolean bool) {
                Log.w("LockPatternChecker", "checkCredential : onChecked called!");
                onCheckCallback.onChecked(bool.booleanValue(), this.mThrottleTimeout);
                duplicate.zeroize();
            }

            @Override // android.os.AsyncTask
            protected void onCancelled() {
                onCheckCallback.onCancelled();
                duplicate.zeroize();
            }
        };
        asyncTask.execute(new Void[0]);
        return asyncTask;
    }

    public static AsyncTask<?, ?, ?> verifyTiedProfileChallenge(final LockPatternUtils lockPatternUtils, LockscreenCredential lockscreenCredential, final int i, final int i2, final OnVerifyCallback onVerifyCallback) {
        final LockscreenCredential duplicate = lockscreenCredential.duplicate();
        AsyncTask<Void, Void, VerifyCredentialResponse> asyncTask = new AsyncTask<Void, Void, VerifyCredentialResponse>() { // from class: com.android.internal.widget.LockPatternChecker.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public VerifyCredentialResponse doInBackground(Void... voidArr) {
                return LockPatternUtils.this.verifyTiedProfileChallenge(duplicate, i, i2);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(VerifyCredentialResponse verifyCredentialResponse) {
                onVerifyCallback.onVerified(verifyCredentialResponse, verifyCredentialResponse.getTimeout());
                duplicate.zeroize();
            }

            @Override // android.os.AsyncTask
            protected void onCancelled() {
                duplicate.zeroize();
            }
        };
        asyncTask.execute(new Void[0]);
        return asyncTask;
    }

    public static AsyncTask<?, ?, ?> checkRemoteLockPassword(final LockPatternUtils lockPatternUtils, final int i, final byte[] bArr, final int i2, final OnCheckCallback onCheckCallback) {
        AsyncTask<Void, Void, Boolean> asyncTask = new AsyncTask<Void, Void, Boolean>() { // from class: com.android.internal.widget.LockPatternChecker.4
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public Boolean doInBackground(Void... voidArr) {
                return Boolean.valueOf(LockPatternUtils.this.checkRemoteLockPassword(i, bArr, i2));
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(Boolean bool) {
                onCheckCallback.onChecked(bool.booleanValue(), 0);
            }
        };
        asyncTask.execute(new Void[0]);
        return asyncTask;
    }

    public static AsyncTask<?, ?, ?> checkCredential(final LockPatternUtils lockPatternUtils, LockscreenCredential lockscreenCredential, final int i, final int i2, final OnCheckCallbackForDualDarDo onCheckCallbackForDualDarDo) {
        final LockscreenCredential duplicate = lockscreenCredential.duplicate();
        AsyncTask<Void, Void, Boolean> asyncTask = new AsyncTask<Void, Void, Boolean>() { // from class: com.android.internal.widget.LockPatternChecker.5
            private int mThrottleTimeout;

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public Boolean doInBackground(Void... voidArr) {
                try {
                    return Boolean.valueOf(LockPatternUtils.this.getLockPatternUtilForDualDarDo().checkCredential(duplicate, i, i2, onCheckCallbackForDualDarDo));
                } catch (LockPatternUtils.RequestThrottledException e) {
                    this.mThrottleTimeout = e.getTimeoutMs();
                    return false;
                }
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(Boolean bool) {
                onCheckCallbackForDualDarDo.onChecked(bool.booleanValue(), this.mThrottleTimeout);
                duplicate.zeroize();
            }

            @Override // android.os.AsyncTask
            protected void onCancelled() {
                onCheckCallbackForDualDarDo.onCancelled();
                duplicate.zeroize();
            }
        };
        asyncTask.execute(new Void[0]);
        return asyncTask;
    }
}
