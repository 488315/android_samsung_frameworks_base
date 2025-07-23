package android.telephony.gba;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.telephony.IBootstrapAuthenticationCallback;
import android.telephony.gba.IGbaService;
import android.util.Log;
import android.util.SparseArray;

@SystemApi
/* loaded from: classes4.dex */
public class GbaService extends Service {
    private static final boolean DBG = Build.IS_DEBUGGABLE;
    private static final int EVENT_GBA_AUTH_REQUEST = 1;
    public static final String SERVICE_INTERFACE = "android.telephony.gba.GbaService";
    private static final String TAG = "GbaService";
    private final GbaServiceHandler mHandler;
    private final HandlerThread mHandlerThread;
    private final SparseArray<IBootstrapAuthenticationCallback> mCallbacks = new SparseArray<>();
    private final IGbaServiceWrapper mBinder = new IGbaServiceWrapper();

    public GbaService() {
        HandlerThread handlerThread = new HandlerThread(TAG);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new GbaServiceHandler(handlerThread.getLooper());
        Log.d(TAG, "GBA service created");
    }

    private class GbaServiceHandler extends Handler {
        GbaServiceHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            GbaAuthRequest gbaAuthRequest = (GbaAuthRequest) message.obj;
            synchronized (GbaService.this.mCallbacks) {
                GbaService.this.mCallbacks.put(gbaAuthRequest.getToken(), gbaAuthRequest.getCallback());
            }
            GbaService.this.onAuthenticationRequest(gbaAuthRequest.getSubId(), gbaAuthRequest.getToken(), gbaAuthRequest.getAppType(), gbaAuthRequest.getNafUrl(), gbaAuthRequest.getSecurityProtocol(), gbaAuthRequest.isForceBootStrapping());
        }
    }

    public void onAuthenticationRequest(int i, int i2, int i3, Uri uri, byte[] bArr, boolean z) {
        reportAuthenticationFailure(i2, 1);
    }

    public final void reportKeysAvailable(int i, byte[] bArr, String str) throws RuntimeException {
        IBootstrapAuthenticationCallback iBootstrapAuthenticationCallback;
        synchronized (this.mCallbacks) {
            iBootstrapAuthenticationCallback = this.mCallbacks.get(i);
            this.mCallbacks.remove(i);
        }
        if (iBootstrapAuthenticationCallback != null) {
            try {
                iBootstrapAuthenticationCallback.onKeysAvailable(i, bArr, str);
            } catch (RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }
    }

    public final void reportAuthenticationFailure(int i, int i2) throws RuntimeException {
        IBootstrapAuthenticationCallback iBootstrapAuthenticationCallback;
        synchronized (this.mCallbacks) {
            iBootstrapAuthenticationCallback = this.mCallbacks.get(i);
            this.mCallbacks.remove(i);
        }
        if (iBootstrapAuthenticationCallback != null) {
            try {
                iBootstrapAuthenticationCallback.onAuthenticationFailure(i, i2);
            } catch (RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (!SERVICE_INTERFACE.equals(intent.getAction())) {
            return null;
        }
        Log.d(TAG, "GbaService Bound.");
        return this.mBinder;
    }

    @Override // android.app.Service
    public void onDestroy() {
        this.mHandlerThread.quit();
        super.onDestroy();
    }

    private class IGbaServiceWrapper extends IGbaService.Stub {
        private IGbaServiceWrapper() {
        }

        @Override // android.telephony.gba.IGbaService
        public void authenticationRequest(GbaAuthRequest gbaAuthRequest) {
            if (GbaService.DBG) {
                Log.d(GbaService.TAG, "receive request: " + gbaAuthRequest);
            }
            GbaService.this.mHandler.obtainMessage(1, gbaAuthRequest).sendToTarget();
        }
    }
}
