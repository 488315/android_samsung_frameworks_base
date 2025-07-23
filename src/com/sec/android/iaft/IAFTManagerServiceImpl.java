package com.sec.android.iaft;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.samsung.android.core.pm.runtimemanifest.RuntimeManifestUtils;
import com.sec.android.iaft.IIAFTManagerService;
import com.sec.android.iaft.callback.IIAFTCallback;

/* loaded from: classes6.dex */
class IAFTManagerServiceImpl extends IIAFTManagerService.Stub {
    private static final int MSG_START_ATRACE = 2;
    private static final int MSG_START_ATRACE_ANALYZE = 3;
    private static final int MSG_START_PERFETTO = 1;
    private static final int MSG_STOP_TRACE = 4;
    private static final String TAG = "IAFTManager";
    private static int mForegroundPid = 0;
    private static IIAFTCallback mIAFTCallback = null;
    private static String mPackageName = "";
    private static int mPolicy = -1;
    private static int mTraceMaxTime = 660;
    private static CountDownTimer mTraceTimer;
    private Context mContext;
    private ServiceHandler mHandler;
    private Looper mLooper;
    private boolean mSystemReady = false;

    @Override // com.sec.android.iaft.IIAFTManagerService
    public boolean traceLogSupported() {
        return false;
    }

    IAFTManagerServiceImpl(Context context) {
        this.mContext = context;
        init();
    }

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                Log.d(IAFTManagerServiceImpl.TAG, "Start perfetto in Handler thread");
                return;
            }
            if (i == 2) {
                Log.d(IAFTManagerServiceImpl.TAG, "Start atrace in Handler thread");
                Intent intent = new Intent();
                intent.setAction("com.android.internal.intent.action.START_TRACE");
                intent.setPackage("com.android.traceur");
                IAFTManagerServiceImpl.this.mContext.sendBroadcast(intent);
                IAFTManagerServiceImpl.mTraceTimer = new CountDownTimer(IAFTManagerServiceImpl.mTraceMaxTime * 1000, IAFTManagerServiceImpl.mTraceMaxTime * 1000) { // from class: com.sec.android.iaft.IAFTManagerServiceImpl.ServiceHandler.1
                    @Override // android.os.CountDownTimer
                    public void onTick(long j) {
                    }

                    @Override // android.os.CountDownTimer
                    public void onFinish() {
                        Log.d(IAFTManagerServiceImpl.TAG, "traceTimer onfinish");
                        if (IAFTManagerServiceImpl.this.mSystemReady) {
                            IAFTManagerServiceImpl.this.mHandler.obtainMessage(4).sendToTarget();
                        }
                    }
                }.start();
                return;
            }
            if (i != 3) {
                if (i != 4) {
                    return;
                }
                Log.d(IAFTManagerServiceImpl.TAG, "Stop trace in Handler thread");
                Intent intent2 = new Intent();
                intent2.setAction("com.android.internal.intent.action.STOP_TRACE");
                intent2.setPackage("com.android.traceur");
                IAFTManagerServiceImpl.this.mContext.sendBroadcast(intent2);
                return;
            }
            Log.d(IAFTManagerServiceImpl.TAG, "Start atrace and analyze in Handler thread");
            Intent intent3 = new Intent();
            intent3.setAction("com.android.internal.intent.action.START_TRACE_ANALYZE");
            intent3.setPackage("com.android.traceur");
            intent3.putExtra("pid", IAFTManagerServiceImpl.mForegroundPid);
            intent3.putExtra("package_name", IAFTManagerServiceImpl.mPackageName);
            intent3.putExtra(RuntimeManifestUtils.TAG_POLICY, IAFTManagerServiceImpl.mPolicy);
            IAFTManagerServiceImpl.this.mContext.sendBroadcast(intent3);
        }
    }

    void init() {
        HandlerThread handlerThread = new HandlerThread("MessageIAFTThread", 10);
        handlerThread.start();
        this.mLooper = handlerThread.getLooper();
        this.mHandler = new ServiceHandler(this.mLooper);
        if (this.mSystemReady) {
            return;
        }
        this.mSystemReady = true;
    }

    public static void sendResult(int i, int i2, int i3) {
        Log.d(TAG, "sendResult back.");
        IIAFTCallback iIAFTCallback = mIAFTCallback;
        if (iIAFTCallback != null) {
            try {
                iIAFTCallback.traceResult(mPackageName, i, i2, i3, mPolicy);
            } catch (RemoteException unused) {
                Log.d(TAG, "mIAFTCallback.traceResult exception!");
            }
        }
    }

    @Override // com.sec.android.iaft.IIAFTManagerService
    public void registerCallback(IIAFTCallback iIAFTCallback) throws RemoteException {
        Log.d(TAG, "Register callback.");
        mIAFTCallback = iIAFTCallback;
    }

    @Override // com.sec.android.iaft.IIAFTManagerService
    public void startAtraceAndAnalyze(int i, String str, int i2) throws RemoteException {
        mForegroundPid = i;
        mPackageName = str;
        mPolicy = i2;
        Log.d(TAG, "Send msg: MSG_START_ATRACE_ANALYZE. pid is " + i);
        if (this.mSystemReady) {
            this.mHandler.obtainMessage(3).sendToTarget();
        }
    }

    @Override // com.sec.android.iaft.IIAFTManagerService
    public void startAtrace() throws RemoteException {
        Log.d(TAG, "Send msg: MSG_START_ATRACE.");
        if (this.mSystemReady) {
            this.mHandler.obtainMessage(2).sendToTarget();
        }
    }

    @Override // com.sec.android.iaft.IIAFTManagerService
    public void stopTrace() throws RemoteException {
        Log.d(TAG, "Remote call stopTrace.");
        CountDownTimer countDownTimer = mTraceTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (this.mSystemReady) {
            this.mHandler.obtainMessage(4).sendToTarget();
        }
    }
}
