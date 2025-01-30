package android.credentials;

import android.app.PendingIntent;
import android.content.Context;
import android.content.IntentSender;
import android.credentials.CredentialManager;
import android.p009os.CancellationSignal;
import android.p009os.OutcomeReceiver;
import android.util.Log;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final class PrepareGetCredentialResponse {
    private static final String TAG = "CredentialManager";
    private final PendingGetCredentialHandle mPendingGetCredentialHandle;
    private final PrepareGetCredentialResponseInternal mResponseInternal;

    protected interface GetPendingCredentialInternalCallback {
        void onError(String str, String str2);

        void onPendingIntent(PendingIntent pendingIntent);

        void onResponse(GetCredentialResponse getCredentialResponse);
    }

    public static final class PendingGetCredentialHandle {
        private final CredentialManager.GetCredentialTransportPendingUseCase mGetCredentialTransport;
        private final PendingIntent mPendingIntent;

        PendingGetCredentialHandle(CredentialManager.GetCredentialTransportPendingUseCase transport, PendingIntent pendingIntent) {
            this.mGetCredentialTransport = transport;
            this.mPendingIntent = pendingIntent;
        }

        void show(Context context, CancellationSignal cancellationSignal, Executor executor, final OutcomeReceiver<GetCredentialResponse, GetCredentialException> callback) {
            if (this.mPendingIntent == null) {
                executor.execute(new Runnable() { // from class: android.credentials.PrepareGetCredentialResponse$PendingGetCredentialHandle$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        OutcomeReceiver.this.onError(new GetCredentialException(GetCredentialException.TYPE_NO_CREDENTIAL));
                    }
                });
                return;
            }
            this.mGetCredentialTransport.setCallback(new C07811(context, executor, callback));
            try {
                context.startIntentSender(this.mPendingIntent.getIntentSender(), null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                Log.m97e(PrepareGetCredentialResponse.TAG, "startIntentSender() failed for intent for show()", e);
                executor.execute(new Runnable() { // from class: android.credentials.PrepareGetCredentialResponse$PendingGetCredentialHandle$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        OutcomeReceiver.this.onError(new GetCredentialException(GetCredentialException.TYPE_UNKNOWN));
                    }
                });
            }
        }

        /* renamed from: android.credentials.PrepareGetCredentialResponse$PendingGetCredentialHandle$1 */
        class C07811 implements GetPendingCredentialInternalCallback {
            final /* synthetic */ OutcomeReceiver val$callback;
            final /* synthetic */ Context val$context;
            final /* synthetic */ Executor val$executor;

            C07811(Context context, Executor executor, OutcomeReceiver outcomeReceiver) {
                this.val$context = context;
                this.val$executor = executor;
                this.val$callback = outcomeReceiver;
            }

            @Override // android.credentials.PrepareGetCredentialResponse.GetPendingCredentialInternalCallback
            public void onPendingIntent(PendingIntent pendingIntent) {
                try {
                    this.val$context.startIntentSender(pendingIntent.getIntentSender(), null, 0, 0, 0);
                } catch (IntentSender.SendIntentException e) {
                    Log.m97e(PrepareGetCredentialResponse.TAG, "startIntentSender() failed for intent for show()", e);
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(new Runnable() { // from class: android.credentials.PrepareGetCredentialResponse$PendingGetCredentialHandle$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            OutcomeReceiver.this.onError(new GetCredentialException(GetCredentialException.TYPE_UNKNOWN));
                        }
                    });
                }
            }

            @Override // android.credentials.PrepareGetCredentialResponse.GetPendingCredentialInternalCallback
            public void onResponse(final GetCredentialResponse response) {
                Executor executor = this.val$executor;
                final OutcomeReceiver outcomeReceiver = this.val$callback;
                executor.execute(new Runnable() { // from class: android.credentials.PrepareGetCredentialResponse$PendingGetCredentialHandle$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        OutcomeReceiver.this.onResult(response);
                    }
                });
            }

            @Override // android.credentials.PrepareGetCredentialResponse.GetPendingCredentialInternalCallback
            public void onError(final String errorType, final String message) {
                Executor executor = this.val$executor;
                final OutcomeReceiver outcomeReceiver = this.val$callback;
                executor.execute(new Runnable() { // from class: android.credentials.PrepareGetCredentialResponse$PendingGetCredentialHandle$1$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        OutcomeReceiver.this.onError(new GetCredentialException(errorType, message));
                    }
                });
            }
        }
    }

    public boolean hasCredentialResults(String credentialType) {
        return this.mResponseInternal.hasCredentialResults(credentialType);
    }

    public boolean hasAuthenticationResults() {
        return this.mResponseInternal.hasAuthenticationResults();
    }

    public boolean hasRemoteResults() {
        return this.mResponseInternal.hasRemoteResults();
    }

    public PendingGetCredentialHandle getPendingGetCredentialHandle() {
        return this.mPendingGetCredentialHandle;
    }

    protected PrepareGetCredentialResponse(PrepareGetCredentialResponseInternal responseInternal, CredentialManager.GetCredentialTransportPendingUseCase getCredentialTransport) {
        this.mResponseInternal = responseInternal;
        this.mPendingGetCredentialHandle = new PendingGetCredentialHandle(getCredentialTransport, responseInternal.getPendingIntent());
    }
}
