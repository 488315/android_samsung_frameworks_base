package android.telecom;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import com.android.internal.telecom.ICallStreamingService;
import com.android.internal.telecom.IStreamingCallAdapter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes3.dex */
public abstract class CallStreamingService extends Service {
    private static final int MSG_CALL_STREAMING_STARTED = 2;
    private static final int MSG_CALL_STREAMING_STATE_CHANGED = 4;
    private static final int MSG_CALL_STREAMING_STOPPED = 3;
    private static final int MSG_SET_STREAMING_CALL_ADAPTER = 1;
    public static final String SERVICE_INTERFACE = "android.telecom.CallStreamingService";
    public static final int STREAMING_FAILED_ALREADY_STREAMING = 1;
    public static final int STREAMING_FAILED_NO_SENDER = 2;
    public static final int STREAMING_FAILED_SENDER_BINDING_ERROR = 3;
    public static final int STREAMING_FAILED_UNKNOWN = 0;
    private StreamingCall mCall;
    private final Handler mHandler = new Handler(Looper.getMainLooper()) { // from class: android.telecom.CallStreamingService.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (CallStreamingService.this.mStreamingCallAdapter == null && msg.what != 1) {
                Log.i(this, "handleMessage: null adapter!", new Object[0]);
            }
            switch (msg.what) {
                case 1:
                    if (msg.obj != null) {
                        Log.i(this, "MSG_SET_STREAMING_CALL_ADAPTER", new Object[0]);
                        CallStreamingService.this.mStreamingCallAdapter = new StreamingCallAdapter((IStreamingCallAdapter) msg.obj);
                        break;
                    }
                    break;
                case 2:
                    Log.i(this, "MSG_CALL_STREAMING_STARTED", new Object[0]);
                    CallStreamingService.this.mCall = (StreamingCall) msg.obj;
                    CallStreamingService.this.mCall.setAdapter(CallStreamingService.this.mStreamingCallAdapter);
                    CallStreamingService.this.onCallStreamingStarted(CallStreamingService.this.mCall);
                    break;
                case 3:
                    Log.i(this, "MSG_CALL_STREAMING_STOPPED", new Object[0]);
                    CallStreamingService.this.mCall = null;
                    CallStreamingService.this.mStreamingCallAdapter = null;
                    CallStreamingService.this.onCallStreamingStopped();
                    break;
                case 4:
                    int state = ((Integer) msg.obj).intValue();
                    if (CallStreamingService.this.mStreamingCallAdapter != null) {
                        CallStreamingService.this.mCall.requestStreamingState(state);
                        CallStreamingService.this.onCallStreamingStateChanged(state);
                        break;
                    }
                    break;
            }
        }
    };
    private StreamingCallAdapter mStreamingCallAdapter;

    @Retention(RetentionPolicy.SOURCE)
    public @interface StreamingFailedReason {
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        Log.i(this, "onBind", new Object[0]);
        return new CallStreamingServiceBinder();
    }

    private final class CallStreamingServiceBinder extends ICallStreamingService.Stub {
        private CallStreamingServiceBinder() {
        }

        @Override // com.android.internal.telecom.ICallStreamingService
        public void setStreamingCallAdapter(IStreamingCallAdapter streamingCallAdapter) throws RemoteException {
            Log.i(this, "setCallStreamingAdapter", new Object[0]);
            CallStreamingService.this.mHandler.obtainMessage(1, streamingCallAdapter).sendToTarget();
        }

        @Override // com.android.internal.telecom.ICallStreamingService
        public void onCallStreamingStarted(StreamingCall call) throws RemoteException {
            Log.i(this, "onCallStreamingStarted", new Object[0]);
            CallStreamingService.this.mHandler.obtainMessage(2, call).sendToTarget();
        }

        @Override // com.android.internal.telecom.ICallStreamingService
        public void onCallStreamingStopped() throws RemoteException {
            CallStreamingService.this.mHandler.obtainMessage(3).sendToTarget();
        }

        @Override // com.android.internal.telecom.ICallStreamingService
        public void onCallStreamingStateChanged(int state) throws RemoteException {
            CallStreamingService.this.mHandler.obtainMessage(4, Integer.valueOf(state)).sendToTarget();
        }
    }

    public void onCallStreamingStarted(StreamingCall call) {
    }

    public void onCallStreamingStopped() {
    }

    public void onCallStreamingStateChanged(int state) {
    }
}
