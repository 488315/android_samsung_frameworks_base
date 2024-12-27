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
        public void onBeginGetCredential(BeginGetCredentialRequest request, final IBeginGetCredentialCallback callback) {
            Objects.requireNonNull(request);
            Objects.requireNonNull(callback);
            ICancellationSignal transport = CancellationSignal.createTransport();
            try {
                callback.onCancellable(transport);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            CredentialProviderService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.service.credentials.CredentialProviderService$1$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                    ((CredentialProviderService) obj).onBeginGetCredential((BeginGetCredentialRequest) obj2, (CancellationSignal) obj3, (CredentialProviderService.AnonymousClass1.C00091) obj4);
                }
            }, CredentialProviderService.this, request, CancellationSignal.fromTransport(transport), new OutcomeReceiver<BeginGetCredentialResponse, GetCredentialException>() { // from class: android.service.credentials.CredentialProviderService.1.1
                @Override // android.os.OutcomeReceiver
                public void onResult(BeginGetCredentialResponse result) {
                    try {
                        callback.onSuccess(result);
                    } catch (RemoteException e2) {
                        e2.rethrowFromSystemServer();
                    }
                }

                @Override // android.os.OutcomeReceiver
                public void onError(GetCredentialException e2) {
                    try {
                        callback.onFailure(e2.getType(), e2.getMessage());
                    } catch (RemoteException ex) {
                        ex.rethrowFromSystemServer();
                    }
                }
            }));
        }

        @Override // android.service.credentials.ICredentialProviderService
        public void onBeginCreateCredential(BeginCreateCredentialRequest request, final IBeginCreateCredentialCallback callback) {
            Objects.requireNonNull(request);
            Objects.requireNonNull(callback);
            ICancellationSignal transport = CancellationSignal.createTransport();
            try {
                callback.onCancellable(transport);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            CredentialProviderService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.service.credentials.CredentialProviderService$1$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                    ((CredentialProviderService) obj).onBeginCreateCredential((BeginCreateCredentialRequest) obj2, (CancellationSignal) obj3, (CredentialProviderService.AnonymousClass1.AnonymousClass2) obj4);
                }
            }, CredentialProviderService.this, request, CancellationSignal.fromTransport(transport), new OutcomeReceiver<BeginCreateCredentialResponse, CreateCredentialException>() { // from class: android.service.credentials.CredentialProviderService.1.2
                @Override // android.os.OutcomeReceiver
                public void onResult(BeginCreateCredentialResponse result) {
                    try {
                        callback.onSuccess(result);
                    } catch (RemoteException e2) {
                        e2.rethrowFromSystemServer();
                    }
                }

                @Override // android.os.OutcomeReceiver
                public void onError(CreateCredentialException e2) {
                    try {
                        callback.onFailure(e2.getType(), e2.getMessage());
                    } catch (RemoteException ex) {
                        ex.rethrowFromSystemServer();
                    }
                }
            }));
        }

        @Override // android.service.credentials.ICredentialProviderService
        public void onClearCredentialState(ClearCredentialStateRequest request, final IClearCredentialStateCallback callback) {
            Objects.requireNonNull(request);
            Objects.requireNonNull(callback);
            ICancellationSignal transport = CancellationSignal.createTransport();
            try {
                callback.onCancellable(transport);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            CredentialProviderService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.service.credentials.CredentialProviderService$1$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                    ((CredentialProviderService) obj).onClearCredentialState((ClearCredentialStateRequest) obj2, (CancellationSignal) obj3, (CredentialProviderService.AnonymousClass1.AnonymousClass3) obj4);
                }
            }, CredentialProviderService.this, request, CancellationSignal.fromTransport(transport), new OutcomeReceiver<Void, ClearCredentialStateException>() { // from class: android.service.credentials.CredentialProviderService.1.3
                @Override // android.os.OutcomeReceiver
                public void onResult(Void result) {
                    try {
                        callback.onSuccess();
                    } catch (RemoteException e2) {
                        e2.rethrowFromSystemServer();
                    }
                }

                @Override // android.os.OutcomeReceiver
                public void onError(ClearCredentialStateException e2) {
                    try {
                        callback.onFailure(e2.getType(), e2.getMessage());
                    } catch (RemoteException ex) {
                        ex.rethrowFromSystemServer();
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
