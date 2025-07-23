package android.credentials;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.content.IntentSender;
import android.credentials.CredentialManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.OutcomeReceiver;
import android.util.Log;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final class PrepareGetCredentialResponse {
    private static final Bundle OPTIONS_SENDER_BAL_OPTIN = ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle();
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

        PendingGetCredentialHandle(CredentialManager.GetCredentialTransportPendingUseCase getCredentialTransportPendingUseCase, PendingIntent pendingIntent) {
            this.mGetCredentialTransport = getCredentialTransportPendingUseCase;
            this.mPendingIntent = pendingIntent;
        }

        void show(Context context, CancellationSignal cancellationSignal, Executor executor, final OutcomeReceiver<GetCredentialResponse, GetCredentialException> outcomeReceiver) {
            if (this.mPendingIntent == null) {
                executor.execute(new Runnable() { // from class: android.credentials.PrepareGetCredentialResponse$PendingGetCredentialHandle$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        OutcomeReceiver.this.onError(new GetCredentialException(GetCredentialException.TYPE_NO_CREDENTIAL));
                    }
                });
                return;
            }
            this.mGetCredentialTransport.setCallback(new AnonymousClass1(this, context, executor, outcomeReceiver));
            try {
                context.startIntentSender(this.mPendingIntent.getIntentSender(), null, 0, 0, 0, PrepareGetCredentialResponse.OPTIONS_SENDER_BAL_OPTIN);
            } catch (IntentSender.SendIntentException e) {
                Log.e("CredentialManager", "startIntentSender() failed for intent for show()", e);
                executor.execute(new Runnable() { // from class: android.credentials.PrepareGetCredentialResponse$PendingGetCredentialHandle$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        OutcomeReceiver.this.onError(new GetCredentialException(GetCredentialException.TYPE_UNKNOWN));
                    }
                });
            }
        }

        /* renamed from: android.credentials.PrepareGetCredentialResponse$PendingGetCredentialHandle$1, reason: invalid class name */
        class AnonymousClass1 implements GetPendingCredentialInternalCallback {
            final /* synthetic */ OutcomeReceiver val$callback;
            final /* synthetic */ Context val$context;
            final /* synthetic */ Executor val$executor;

            AnonymousClass1(PendingGetCredentialHandle pendingGetCredentialHandle, Context context, Executor executor, OutcomeReceiver outcomeReceiver) {
                this.val$context = context;
                this.val$executor = executor;
                this.val$callback = outcomeReceiver;
            }

            @Override // android.credentials.PrepareGetCredentialResponse.GetPendingCredentialInternalCallback
            public void onPendingIntent(PendingIntent pendingIntent) {
                try {
                    this.val$context.startIntentSender(pendingIntent.getIntentSender(), null, 0, 0, 0, PrepareGetCredentialResponse.OPTIONS_SENDER_BAL_OPTIN);
                } catch (IntentSender.SendIntentException e) {
                    Log.e("CredentialManager", "startIntentSender() failed for intent for show()", e);
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(new Runnable() { // from class: android.credentials.PrepareGetCredentialResponse$PendingGetCredentialHandle$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            OutcomeReceiver.this.onError(new GetCredentialException(GetCredentialException.TYPE_UNKNOWN));
                        }
                    });
                }
            }

            @Override // android.credentials.PrepareGetCredentialResponse.GetPendingCredentialInternalCallback
            public void onResponse(final GetCredentialResponse getCredentialResponse) {
                Executor executor = this.val$executor;
                final OutcomeReceiver outcomeReceiver = this.val$callback;
                executor.execute(new Runnable() { // from class: android.credentials.PrepareGetCredentialResponse$PendingGetCredentialHandle$1$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        OutcomeReceiver.this.onResult(getCredentialResponse);
                    }
                });
            }

            @Override // android.credentials.PrepareGetCredentialResponse.GetPendingCredentialInternalCallback
            public void onError(final String str, final String str2) {
                Executor executor = this.val$executor;
                final OutcomeReceiver outcomeReceiver = this.val$callback;
                executor.execute(new Runnable() { // from class: android.credentials.PrepareGetCredentialResponse$PendingGetCredentialHandle$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        OutcomeReceiver.this.onError(new GetCredentialException(str, str2));
                    }
                });
            }
        }
    }

    public boolean hasCredentialResults(String str) {
        return this.mResponseInternal.hasCredentialResults(str);
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

    protected PrepareGetCredentialResponse(PrepareGetCredentialResponseInternal prepareGetCredentialResponseInternal, CredentialManager.GetCredentialTransportPendingUseCase getCredentialTransportPendingUseCase) {
        this.mResponseInternal = prepareGetCredentialResponseInternal;
        this.mPendingGetCredentialHandle = new PendingGetCredentialHandle(getCredentialTransportPendingUseCase, prepareGetCredentialResponseInternal.getPendingIntent());
    }
}
