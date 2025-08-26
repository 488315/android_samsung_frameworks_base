package android.service.autofill;

import android.app.Service;
import android.content.Intent;
import android.os.BaseBundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.Looper;
import android.os.RemoteException;
import android.service.autofill.IAutoFillService;
import android.util.Log;
import android.view.autofill.AutofillManager;
import com.android.internal.os.IResultReceiver;
import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.QuintConsumer;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public abstract class AutofillService extends Service {
    public static final String EXTRA_ERROR = "error";
    public static final String EXTRA_FILL_RESPONSE = "android.service.autofill.extra.FILL_RESPONSE";
    public static final String EXTRA_RESULT = "result";
    public static final String SERVICE_INTERFACE = "android.service.autofill.AutofillService";
    public static final String SERVICE_META_DATA = "android.autofill";
    private static final String TAG = "AutofillService";
    public static final String WEBVIEW_REQUESTED_CREDENTIAL_KEY = "webview_requested_credential";
    private Handler mHandler;
    private final IAutoFillService mInterface = new IAutoFillService.Stub() { // from class: android.service.autofill.AutofillService.1
        @Override // android.service.autofill.IAutoFillService
        public void onConnectedStateChanged(boolean z) {
            AutofillService.this.mHandler.sendMessage(PooledLambda.obtainMessage(z ? new Consumer() { // from class: android.service.autofill.AutofillService$1$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((AutofillService) obj).onConnected();
                }
            } : new Consumer() { // from class: android.service.autofill.AutofillService$1$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((AutofillService) obj).onDisconnected();
                }
            }, AutofillService.this));
        }

        @Override // android.service.autofill.IAutoFillService
        public void onFillRequest(FillRequest fillRequest, IFillCallback iFillCallback) {
            ICancellationSignal iCancellationSignalCreateTransport = CancellationSignal.createTransport();
            try {
                iFillCallback.onCancellable(iCancellationSignalCreateTransport);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            AutofillService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.service.autofill.AutofillService$1$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                    ((AutofillService) obj).onFillRequest((FillRequest) obj2, (CancellationSignal) obj3, (FillCallback) obj4);
                }
            }, AutofillService.this, fillRequest, CancellationSignal.fromTransport(iCancellationSignalCreateTransport), new FillCallback(iFillCallback, fillRequest.getId())));
        }

        @Override // android.service.autofill.IAutoFillService
        public void onConvertCredentialRequest(ConvertCredentialRequest convertCredentialRequest, IConvertCredentialCallback iConvertCredentialCallback) {
            AutofillService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.service.autofill.AutofillService$1$$ExternalSyntheticLambda5
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((AutofillService) obj).onConvertCredentialRequest((ConvertCredentialRequest) obj2, (ConvertCredentialCallback) obj3);
                }
            }, AutofillService.this, convertCredentialRequest, new ConvertCredentialCallback(iConvertCredentialCallback)));
        }

        @Override // android.service.autofill.IAutoFillService
        public void onFillCredentialRequest(FillRequest fillRequest, IFillCallback iFillCallback, IBinder iBinder) {
            ICancellationSignal iCancellationSignalCreateTransport = CancellationSignal.createTransport();
            try {
                iFillCallback.onCancellable(iCancellationSignalCreateTransport);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            AutofillService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuintConsumer() { // from class: android.service.autofill.AutofillService$1$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.QuintConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                    ((AutofillService) obj).onFillCredentialRequest((FillRequest) obj2, (CancellationSignal) obj3, (FillCallback) obj4, (IBinder) obj5);
                }
            }, AutofillService.this, fillRequest, CancellationSignal.fromTransport(iCancellationSignalCreateTransport), new FillCallback(iFillCallback, fillRequest.getId()), iBinder));
        }

        @Override // android.service.autofill.IAutoFillService
        public void onSaveRequest(SaveRequest saveRequest, ISaveCallback iSaveCallback) {
            AutofillService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.service.autofill.AutofillService$1$$ExternalSyntheticLambda4
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((AutofillService) obj).onSaveRequest((SaveRequest) obj2, (SaveCallback) obj3);
                }
            }, AutofillService.this, saveRequest, new SaveCallback(iSaveCallback)));
        }

        @Override // android.service.autofill.IAutoFillService
        public void onSavedPasswordCountRequest(IResultReceiver iResultReceiver) {
            AutofillService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.service.autofill.AutofillService$1$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((AutofillService) obj).onSavedDatasetsInfoRequest((SavedDatasetsInfoCallbackImpl) obj2);
                }
            }, AutofillService.this, new SavedDatasetsInfoCallbackImpl(iResultReceiver, SavedDatasetsInfo.TYPE_PASSWORDS)));
        }

        @Override // android.service.autofill.IAutoFillService
        public void onSessionDestroyed(FillEventHistory fillEventHistory) {
            AutofillService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.service.autofill.AutofillService$1$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((AutofillService) obj).onSessionDestroyed((FillEventHistory) obj2);
                }
            }, AutofillService.this, fillEventHistory));
        }
    };

    public void onConnected() {
    }

    public void onConvertCredentialRequest(ConvertCredentialRequest convertCredentialRequest, ConvertCredentialCallback convertCredentialCallback) {
    }

    public void onDisconnected() {
    }

    public void onFillCredentialRequest(FillRequest fillRequest, CancellationSignal cancellationSignal, FillCallback fillCallback, IBinder iBinder) {
    }

    public abstract void onFillRequest(FillRequest fillRequest, CancellationSignal cancellationSignal, FillCallback fillCallback);

    public abstract void onSaveRequest(SaveRequest saveRequest, SaveCallback saveCallback);

    public void onSessionDestroyed(FillEventHistory fillEventHistory) {
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mHandler = new Handler(Looper.getMainLooper(), null, true);
        BaseBundle.setShouldDefuse(true);
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mInterface.asBinder();
        }
        Log.w(TAG, "Tried to bind to wrong intent (should be android.service.autofill.AutofillService: " + intent);
        return null;
    }

    public void onSavedDatasetsInfoRequest(SavedDatasetsInfoCallback savedDatasetsInfoCallback) {
        savedDatasetsInfoCallback.onError(1);
    }

    @Deprecated
    public final FillEventHistory getFillEventHistory() {
        AutofillManager autofillManager = (AutofillManager) getSystemService(AutofillManager.class);
        if (autofillManager == null) {
            return null;
        }
        return autofillManager.getFillEventHistory();
    }
}
