package android.telecom;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import com.android.internal.os.SomeArgs;
import com.android.internal.telecom.ICallRedirectionAdapter;
import com.android.internal.telecom.ICallRedirectionService;

/* loaded from: classes4.dex */
public abstract class CallRedirectionService extends Service {
    private static final int MSG_PLACE_CALL = 1;
    private static final int MSG_TIMEOUT = 2;
    public static final String SERVICE_INTERFACE = "android.telecom.CallRedirectionService";
    private ICallRedirectionAdapter mCallRedirectionAdapter;
    private final Handler mHandler = new Handler(Looper.getMainLooper()) { // from class: android.telecom.CallRedirectionService.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i != 1) {
                if (i != 2) {
                    return;
                }
                CallRedirectionService.this.onRedirectionTimeout();
            } else {
                SomeArgs someArgs = (SomeArgs) message.obj;
                try {
                    CallRedirectionService.this.mCallRedirectionAdapter = (ICallRedirectionAdapter) someArgs.arg1;
                    CallRedirectionService.this.onPlaceCall((Uri) someArgs.arg2, (PhoneAccountHandle) someArgs.arg3, ((Boolean) someArgs.arg4).booleanValue());
                } finally {
                    someArgs.recycle();
                }
            }
        }
    };

    public abstract void onPlaceCall(Uri uri, PhoneAccountHandle phoneAccountHandle, boolean z);

    public void onRedirectionTimeout() {
    }

    @Override // android.app.Service
    public final boolean onUnbind(Intent intent) {
        return false;
    }

    public final void placeCallUnmodified() {
        try {
            ICallRedirectionAdapter iCallRedirectionAdapter = this.mCallRedirectionAdapter;
            if (iCallRedirectionAdapter == null) {
                throw new IllegalStateException("Can only be called from onPlaceCall.");
            }
            iCallRedirectionAdapter.placeCallUnmodified();
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public final void redirectCall(Uri uri, PhoneAccountHandle phoneAccountHandle, boolean z) {
        try {
            ICallRedirectionAdapter iCallRedirectionAdapter = this.mCallRedirectionAdapter;
            if (iCallRedirectionAdapter == null) {
                throw new IllegalStateException("Can only be called from onPlaceCall.");
            }
            iCallRedirectionAdapter.redirectCall(uri, phoneAccountHandle, z);
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public final void cancelCall() {
        try {
            ICallRedirectionAdapter iCallRedirectionAdapter = this.mCallRedirectionAdapter;
            if (iCallRedirectionAdapter == null) {
                throw new IllegalStateException("Can only be called from onPlaceCall.");
            }
            iCallRedirectionAdapter.cancelCall();
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    private final class CallRedirectionBinder extends ICallRedirectionService.Stub {
        private CallRedirectionBinder() {
        }

        @Override // com.android.internal.telecom.ICallRedirectionService
        public void placeCall(ICallRedirectionAdapter iCallRedirectionAdapter, Uri uri, PhoneAccountHandle phoneAccountHandle, boolean z) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = iCallRedirectionAdapter;
            obtain.arg2 = uri;
            obtain.arg3 = phoneAccountHandle;
            obtain.arg4 = Boolean.valueOf(z);
            CallRedirectionService.this.mHandler.obtainMessage(1, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.ICallRedirectionService
        public void notifyTimeout() {
            CallRedirectionService.this.mHandler.obtainMessage(2).sendToTarget();
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return new CallRedirectionBinder();
    }
}
