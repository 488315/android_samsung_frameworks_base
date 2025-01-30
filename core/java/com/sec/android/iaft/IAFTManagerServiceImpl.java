package com.sec.android.iaft;

import android.content.Context;
import android.content.Intent;
import android.p009os.CountDownTimer;
import android.p009os.Handler;
import android.p009os.HandlerThread;
import android.p009os.Looper;
import android.p009os.Message;
import android.p009os.RemoteException;
import android.util.Log;
import com.android.internal.logging.nano.MetricsProto;
import com.samsung.android.core.p036pm.runtimemanifest.RuntimeManifestUtils;
import com.samsung.android.media.AudioParameter;
import com.sec.android.iaft.IIAFTManagerService;
import com.sec.android.iaft.callback.IIAFTCallback;

/* loaded from: classes6.dex */
class IAFTManagerServiceImpl extends IIAFTManagerService.Stub {
    private static final int MSG_START_ATRACE = 2;
    private static final int MSG_START_ATRACE_ANALYZE = 3;
    private static final int MSG_START_PERFETTO = 1;
    private static final int MSG_STOP_TRACE = 4;
    private static final String TAG = "IAFTManager";
    private Context mContext;
    private ServiceHandler mHandler;
    private Looper mLooper;
    private boolean mSystemReady = false;
    private static int mForegroundPid = 0;
    private static int mPolicy = -1;
    private static String mPackageName = "";
    private static IIAFTCallback mIAFTCallback = null;
    private static CountDownTimer mTraceTimer = null;
    private static int mTraceMaxTime = MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_ACCESS_FINE_LOCATION;

    IAFTManagerServiceImpl(Context context) {
        this.mContext = context;
        init();
    }

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override // android.p009os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.m94d(IAFTManagerServiceImpl.TAG, "Start perfetto in Handler thread");
                    break;
                case 2:
                    Log.m94d(IAFTManagerServiceImpl.TAG, "Start atrace in Handler thread");
                    Intent intent = new Intent();
                    intent.setAction("com.android.internal.intent.action.START_TRACE");
                    intent.setPackage("com.android.traceur");
                    IAFTManagerServiceImpl.this.mContext.sendBroadcast(intent);
                    IAFTManagerServiceImpl.mTraceTimer = new CountDownTimer(IAFTManagerServiceImpl.mTraceMaxTime * 1000, IAFTManagerServiceImpl.mTraceMaxTime * 1000) { // from class: com.sec.android.iaft.IAFTManagerServiceImpl.ServiceHandler.1
                        @Override // android.p009os.CountDownTimer
                        public void onTick(long duration) {
                        }

                        @Override // android.p009os.CountDownTimer
                        public void onFinish() {
                            Log.m94d(IAFTManagerServiceImpl.TAG, "traceTimer onfinish");
                            if (IAFTManagerServiceImpl.this.mSystemReady) {
                                IAFTManagerServiceImpl.this.mHandler.obtainMessage(4).sendToTarget();
                            }
                        }
                    }.start();
                    break;
                case 3:
                    Log.m94d(IAFTManagerServiceImpl.TAG, "Start atrace and analyze in Handler thread");
                    Intent intent2 = new Intent();
                    intent2.setAction("com.android.internal.intent.action.START_TRACE_ANALYZE");
                    intent2.setPackage("com.android.traceur");
                    intent2.putExtra(AudioParameter.SUBKEY_HIDDEN_SOUND_PID, IAFTManagerServiceImpl.mForegroundPid);
                    intent2.putExtra("package_name", IAFTManagerServiceImpl.mPackageName);
                    intent2.putExtra(RuntimeManifestUtils.TAG_POLICY, IAFTManagerServiceImpl.mPolicy);
                    IAFTManagerServiceImpl.this.mContext.sendBroadcast(intent2);
                    break;
                case 4:
                    Log.m94d(IAFTManagerServiceImpl.TAG, "Stop trace in Handler thread");
                    Intent intent3 = new Intent();
                    intent3.setAction("com.android.internal.intent.action.STOP_TRACE");
                    intent3.setPackage("com.android.traceur");
                    IAFTManagerServiceImpl.this.mContext.sendBroadcast(intent3);
                    break;
            }
        }
    }

    void init() {
        HandlerThread thread = new HandlerThread("MessageIAFTThread", 10);
        thread.start();
        this.mLooper = thread.getLooper();
        this.mHandler = new ServiceHandler(this.mLooper);
        if (!this.mSystemReady) {
            this.mSystemReady = true;
        }
    }

    public static void sendResult(int tid, int code, int freq) {
        Log.m94d(TAG, "sendResult back.");
        IIAFTCallback iIAFTCallback = mIAFTCallback;
        if (iIAFTCallback != null) {
            try {
                iIAFTCallback.traceResult(mPackageName, tid, code, freq, mPolicy);
            } catch (RemoteException e) {
                Log.m94d(TAG, "mIAFTCallback.traceResult exception!");
            }
        }
    }

    @Override // com.sec.android.iaft.IIAFTManagerService
    public void registerCallback(IIAFTCallback callback) throws RemoteException {
        Log.m94d(TAG, "Register callback.");
        mIAFTCallback = callback;
    }

    @Override // com.sec.android.iaft.IIAFTManagerService
    public void startAtraceAndAnalyze(int pid, String packageName, int policy) throws RemoteException {
        mForegroundPid = pid;
        mPackageName = packageName;
        mPolicy = policy;
        Log.m94d(TAG, "Send msg: MSG_START_ATRACE_ANALYZE. pid is " + pid);
        if (this.mSystemReady) {
            this.mHandler.obtainMessage(3).sendToTarget();
        }
    }

    @Override // com.sec.android.iaft.IIAFTManagerService
    public void startAtrace() throws RemoteException {
        Log.m94d(TAG, "Send msg: MSG_START_ATRACE.");
        if (this.mSystemReady) {
            this.mHandler.obtainMessage(2).sendToTarget();
        }
    }

    @Override // com.sec.android.iaft.IIAFTManagerService
    public void stopTrace() throws RemoteException {
        Log.m94d(TAG, "Remote call stopTrace.");
        CountDownTimer countDownTimer = mTraceTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (this.mSystemReady) {
            this.mHandler.obtainMessage(4).sendToTarget();
        }
    }

    @Override // com.sec.android.iaft.IIAFTManagerService
    public boolean traceLogSupported() {
        return false;
    }
}
