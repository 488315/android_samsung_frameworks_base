package android.service.credentials;

import android.app.Service;
import android.content.Intent;
import android.credentials.ClearCredentialStateException;
import android.credentials.CreateCredentialException;
import android.credentials.GetCredentialException;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.Looper;
import android.os.OutcomeReceiver;
import android.os.RemoteException;
import android.service.credentials.CredentialProviderService;
import android.service.credentials.ICredentialProviderService;
import android.util.Slog;
import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.Objects;

/* loaded from: classes3.dex */
public abstract class CredentialProviderService extends Service {
    public static final String EXTRA_AUTOFILL_ID = "android.service.credentials.extra.AUTOFILL_ID";
    public static final String EXTRA_BEGIN_GET_CREDENTIAL_REQUEST = "android.service.credentials.extra.BEGIN_GET_CREDENTIAL_REQUEST";
    public static final String EXTRA_BEGIN_GET_CREDENTIAL_RESPONSE = "android.service.credentials.extra.BEGIN_GET_CREDENTIAL_RESPONSE";
    public static final String EXTRA_CREATE_CREDENTIAL_EXCEPTION = "android.service.credentials.extra.CREATE_CREDENTIAL_EXCEPTION";
    public static final String EXTRA_CREATE_CREDENTIAL_REQUEST = "android.service.credentials.extra.CREATE_CREDENTIAL_REQUEST";
    public static final String EXTRA_CREATE_CREDENTIAL_RESPONSE = "android.service.credentials.extra.CREATE_CREDENTIAL_RESPONSE";
    public static final String EXTRA_GET_CREDENTIAL_EXCEPTION = "android.service.credentials.extra.GET_CREDENTIAL_EXCEPTION";
    public static final String EXTRA_GET_CREDENTIAL_REQUEST = "android.service.credentials.extra.GET_CREDENTIAL_REQUEST";
    public static final String EXTRA_GET_CREDENTIAL_RESPONSE = "android.service.credentials.extra.GET_CREDENTIAL_RESPONSE";
    public static final String SERVICE_INTERFACE = "android.service.credentials.CredentialProviderService";
    public static final String SERVICE_META_DATA = "android.credentials.provider";
    public static final String SYSTEM_SERVICE_INTERFACE = "android.service.credentials.system.CredentialProviderService";
    private static final String TAG = "CredProviderService";
    public static final String TEST_SYSTEM_PROVIDER_META_DATA_KEY = "android.credentials.testsystemprovider";
    private Handler mHandler;
    private final ICredentialProviderService mInterface = new ICredentialProviderService.Stub() { // from class: android.service.credentials.CredentialProviderService.1
        @Override // android.service.credentials.ICredentialProviderService
        public void onBeginGetCredential(BeginGetCredentialRequest beginGetCredentialRequest, final IBeginGetCredentialCallback iBeginGetCredentialCallback) {
            Objects.requireNonNull(beginGetCredentialRequest);
            Objects.requireNonNull(iBeginGetCredentialCallback);
            ICancellationSignal createTransport = CancellationSignal.createTransport();
            try {
                iBeginGetCredentialCallback.onCancellable(createTransport);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            CredentialProviderService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.service.credentials.CredentialProviderService$1$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                    ((CredentialProviderService) obj).onBeginGetCredential((BeginGetCredentialRequest) obj2, (CancellationSignal) obj3, (CredentialProviderService.AnonymousClass1.C00091) obj4);
                }
            }, CredentialProviderService.this, beginGetCredentialRequest, CancellationSignal.fromTransport(createTransport), new OutcomeReceiver<BeginGetCredentialResponse, GetCredentialException>(this) { // from class: android.service.credentials.CredentialProviderService.1.1
                @Override // android.os.OutcomeReceiver
                public void onResult(BeginGetCredentialResponse beginGetCredentialResponse) {
                    try {
                        iBeginGetCredentialCallback.onSuccess(beginGetCredentialResponse);
                    } catch (RemoteException e2) {
                        e2.rethrowFromSystemServer();
                    }
                }

                @Override // android.os.OutcomeReceiver
                public void onError(GetCredentialException getCredentialException) {
                    try {
                        iBeginGetCredentialCallback.onFailure(getCredentialException.getType(), getCredentialException.getMessage());
                    } catch (RemoteException e2) {
                        e2.rethrowFromSystemServer();
                    }
                }
            }));
        }

        @Override // android.service.credentials.ICredentialProviderService
        public void onBeginCreateCredential(BeginCreateCredentialRequest beginCreateCredentialRequest, final IBeginCreateCredentialCallback iBeginCreateCredentialCallback) {
            Objects.requireNonNull(beginCreateCredentialRequest);
            Objects.requireNonNull(iBeginCreateCredentialCallback);
            ICancellationSignal createTransport = CancellationSignal.createTransport();
            try {
                iBeginCreateCredentialCallback.onCancellable(createTransport);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            CredentialProviderService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.service.credentials.CredentialProviderService$1$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                    ((CredentialProviderService) obj).onBeginCreateCredential((BeginCreateCredentialRequest) obj2, (CancellationSignal) obj3, (CredentialProviderService.AnonymousClass1.AnonymousClass2) obj4);
                }
            }, CredentialProviderService.this, beginCreateCredentialRequest, CancellationSignal.fromTransport(createTransport), new OutcomeReceiver<BeginCreateCredentialResponse, CreateCredentialException>(this) { // from class: android.service.credentials.CredentialProviderService.1.2
                @Override // android.os.OutcomeReceiver
                public void onResult(BeginCreateCredentialResponse beginCreateCredentialResponse) {
                    try {
                        iBeginCreateCredentialCallback.onSuccess(beginCreateCredentialResponse);
                    } catch (RemoteException e2) {
                        e2.rethrowFromSystemServer();
                    }
                }

                @Override // android.os.OutcomeReceiver
                public void onError(CreateCredentialException createCredentialException) {
                    try {
                        iBeginCreateCredentialCallback.onFailure(createCredentialException.getType(), createCredentialException.getMessage());
                    } catch (RemoteException e2) {
                        e2.rethrowFromSystemServer();
                    }
                }
            }));
        }

        @Override // android.service.credentials.ICredentialProviderService
        public void onClearCredentialState(ClearCredentialStateRequest clearCredentialStateRequest, final IClearCredentialStateCallback iClearCredentialStateCallback) {
            Objects.requireNonNull(clearCredentialStateRequest);
            Objects.requireNonNull(iClearCredentialStateCallback);
            ICancellationSignal createTransport = CancellationSignal.createTransport();
            try {
                iClearCredentialStateCallback.onCancellable(createTransport);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            CredentialProviderService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.service.credentials.CredentialProviderService$1$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                    ((CredentialProviderService) obj).onClearCredentialState((ClearCredentialStateRequest) obj2, (CancellationSignal) obj3, (CredentialProviderService.AnonymousClass1.AnonymousClass3) obj4);
                }
            }, CredentialProviderService.this, clearCredentialStateRequest, CancellationSignal.fromTransport(createTransport), new OutcomeReceiver<Void, ClearCredentialStateException>(this) { // from class: android.service.credentials.CredentialProviderService.1.3
                @Override // android.os.OutcomeReceiver
                public void onResult(Void r1) {
                    try {
                        iClearCredentialStateCallback.onSuccess();
                    } catch (RemoteException e2) {
                        e2.rethrowFromSystemServer();
                    }
                }

                @Override // android.os.OutcomeReceiver
                public void onError(ClearCredentialStateException clearCredentialStateException) {
                    try {
                        iClearCredentialStateCallback.onFailure(clearCredentialStateException.getType(), clearCredentialStateException.getMessage());
                    } catch (RemoteException e2) {
                        e2.rethrowFromSystemServer();
                    }
                }
            }));
        }
    };

    public abstract void onBeginCreateCredential(BeginCreateCredentialRequest beginCreateCredentialRequest, CancellationSignal cancellationSignal, OutcomeReceiver<BeginCreateCredentialResponse, CreateCredentialException> outcomeReceiver);

    public abstract void onBeginGetCredential(BeginGetCredentialRequest beginGetCredentialRequest, CancellationSignal cancellationSignal, OutcomeReceiver<BeginGetCredentialResponse, GetCredentialException> outcomeReceiver);

    public abstract void onClearCredentialState(ClearCredentialStateRequest clearCredentialStateRequest, CancellationSignal cancellationSignal, OutcomeReceiver<Void, ClearCredentialStateException> outcomeReceiver);

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mHandler = new Handler(Looper.getMainLooper(), null, true);
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mInterface.asBinder();
        }
        Slog.w(TAG, "Failed to bind with intent: " + intent);
        return null;
    }
}
