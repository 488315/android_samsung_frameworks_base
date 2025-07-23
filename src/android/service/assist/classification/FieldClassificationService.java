package android.service.assist.classification;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.BaseBundle;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.IBinder;
import android.os.OutcomeReceiver;
import android.os.RemoteException;
import android.service.assist.classification.IFieldClassificationService;
import android.util.Log;

@SystemApi
/* loaded from: classes3.dex */
public abstract class FieldClassificationService extends Service {
    public static final String SERVICE_INTERFACE = "android.service.assist.classification.FieldClassificationService";
    private static final String TAG = "FieldClassificationService";
    static boolean sDebug = !Build.IS_USER;
    static boolean sVerbose = false;
    private ComponentName mServiceComponentName;

    public abstract void onClassificationRequest(FieldClassificationRequest fieldClassificationRequest, CancellationSignal cancellationSignal, OutcomeReceiver<FieldClassificationResponse, Exception> outcomeReceiver);

    public void onConnected() {
    }

    public void onDisconnected() {
    }

    private final class FieldClassificationServiceImpl extends IFieldClassificationService.Stub {
        private FieldClassificationServiceImpl() {
        }

        @Override // android.service.assist.classification.IFieldClassificationService
        public void onConnected(boolean z, boolean z2) {
            FieldClassificationService.this.handleOnConnected(z, z2);
        }

        @Override // android.service.assist.classification.IFieldClassificationService
        public void onDisconnected() {
            FieldClassificationService.this.handleOnDisconnected();
        }

        @Override // android.service.assist.classification.IFieldClassificationService
        public void onFieldClassificationRequest(FieldClassificationRequest fieldClassificationRequest, IFieldClassificationCallback iFieldClassificationCallback) {
            FieldClassificationService.this.handleOnClassificationRequest(fieldClassificationRequest, iFieldClassificationCallback);
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        BaseBundle.setShouldDefuse(true);
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            this.mServiceComponentName = intent.getComponent();
            return new FieldClassificationServiceImpl().asBinder();
        }
        Log.w(TAG, "Tried to bind to wrong intent (should be android.service.assist.classification.FieldClassificationService: " + intent);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnConnected(boolean z, boolean z2) {
        if (sDebug || z) {
            Log.d(TAG, "handleOnConnected(): debug=" + z + ", verbose=" + z2);
        }
        sDebug = z;
        sVerbose = z2;
        onConnected();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnDisconnected() {
        onDisconnected();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnClassificationRequest(FieldClassificationRequest fieldClassificationRequest, final IFieldClassificationCallback iFieldClassificationCallback) {
        onClassificationRequest(fieldClassificationRequest, CancellationSignal.fromTransport(CancellationSignal.createTransport()), new OutcomeReceiver<FieldClassificationResponse, Exception>(this) { // from class: android.service.assist.classification.FieldClassificationService.1
            @Override // android.os.OutcomeReceiver
            public void onResult(FieldClassificationResponse fieldClassificationResponse) {
                try {
                    iFieldClassificationCallback.onSuccess(fieldClassificationResponse);
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }

            @Override // android.os.OutcomeReceiver
            public void onError(Exception exc) {
                try {
                    iFieldClassificationCallback.onFailure();
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        });
    }
}
