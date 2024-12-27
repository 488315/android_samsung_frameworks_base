package com.samsung.android.hardware.secinputdev.taas;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.SemHqmManager;
import android.os.UserHandle;
import android.util.Log;
import android.util.PerfLog;
import android.view.MotionEvent;
import android.view.SemSurfaceControl;
import android.view.SurfaceControl;

import com.samsung.android.hardware.secinputdev.SemInputDeviceManagerService;
import com.samsung.android.hardware.secinputdev.SemInputFeatures;
import com.samsung.android.hardware.secinputdev.SemInputMotionEventDispatcher;
import com.samsung.android.hardware.secinputdev.external.SemInputExternal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class ExternalApi {
    static final String ACTION_ISSUE_TRACKER_ON_OFF = "com.sec.android.ISSUE_TRACKER_ONOFF";
    private static final String EXTRA_ERROR_CODE = "ERRCODE";
    private static final String EXTRA_ERROR_MSG = "ERRMSG";
    private static final String EXTRA_ERROR_NAME = "ERRNAME";
    private static final String EXTRA_ERROR_PACKAGE = "ERRPKG";
    static final String EXTRA_ON_OFF = "ONOFF";
    private static final String HQM_UPDATE_REQ = "com.sec.android.intent.action.HQM_UPDATE_REQ";
    private static final String ISSUE_TRACKER_ACTION_INTENT =
            "com.sec.android.ISSUE_TRACKER_ACTION";
    private Handler handler;
    private HandlerThread handlerThread;
    private boolean isIssueTrackerActive;
    private Context mContext;
    private SemInputMotionEventDispatcher mDispatcher;
    private SemInputDeviceHqmHelper mHqmHelper;
    private SemInputDeviceHqmData mHqmLoggingData;
    private SemHqmManager mSemHqmManager;
    private final String TAG = "SemInputExternalApi";
    private final String TSP_FEATURE = "TAAS";
    private final String COMPONENT_ID = "TSP";
    private final String HIT_TYPE = "sm";
    private final String COMONENT_VER = "0.0";
    private final String COM_MANUFACTURE = "sec";
    private final String DEV_CUSTOM_DATA_SET = "";
    private final String PRI_CUSTOM_DATA_SET = "";
    private List<Consumer<MotionEvent>> listeners = new ArrayList();
    private final StringBuilder bootingDump = new StringBuilder();
    private Runnable motionEventRunnable = null;
    private final int TAAS_START_DELAY_TIME_MS = 4000;
    private IntentFilter intentFilter = null;
    private long hwDefectTime = 0;
    private long hwDefectCnt = 0;
    private long currentTime = 0;
    private final BroadcastReceiver bigDataBroadcastReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.hardware.secinputdev.taas.ExternalApi.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    if (!ExternalApi.HQM_UPDATE_REQ.equals(intent.getAction())) {
                        return;
                    }
                    Log.d("SemInputExternalApi", "bigDataBroadcastReceiver");
                    ExternalApi.this.setLoggingData(
                            "CASA", ExternalApi.this.mHqmLoggingData.getCaseACountHqm());
                    ExternalApi.this.setLoggingData(
                            "CASB", ExternalApi.this.mHqmLoggingData.getCaseBCountHqm());
                    ExternalApi.this.sendHwParamToHqm(
                            ExternalApi.this.sendHqmTspData(ExternalApi.this.getmHqmLoggingData()));
                    ExternalApi.this.mHqmLoggingData.clear();
                }
            };
    private final BroadcastReceiver issueTrackerOnOffReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.hardware.secinputdev.taas.ExternalApi.2
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    Bundle extras = intent.getExtras();
                    if (extras != null
                            && extras.getSerializable(ExternalApi.EXTRA_ON_OFF) != null) {
                        ExternalApi.this.isIssueTrackerActive =
                                ((Boolean) extras.getSerializable(ExternalApi.EXTRA_ON_OFF))
                                        .booleanValue();
                    }
                }
            };
    private final SemInputExternal.IServiceListener displayStateListener = new AnonymousClass3();
    private SemInputMotionEventDispatcher.SemInputMotionEventListener motionEventListener =
            new SemInputMotionEventDispatcher
                    .SemInputMotionEventListener() { // from class:
                                                     // com.samsung.android.hardware.secinputdev.taas.ExternalApi.4
                @Override // com.samsung.android.hardware.secinputdev.SemInputMotionEventDispatcher.SemInputMotionEventListener
                public void onMotionEvent(MotionEvent event) {
                    ExternalApi.this.runEvent(event);
                }
            };

    public ExternalApi(
            Context context, SemInputExternal.IExternalEventRegister externalEventRegister) {
        this.mSemHqmManager = null;
        this.mDispatcher = null;
        this.handlerThread = null;
        this.handler = null;
        this.mHqmHelper = null;
        this.mHqmLoggingData = null;
        this.mContext = null;
        this.mHqmLoggingData = new SemInputDeviceHqmData();
        this.mHqmHelper = new SemInputDeviceHqmHelper();
        this.mSemHqmManager = (SemHqmManager) context.getSystemService("HqmManagerService");
        this.handlerThread = new HandlerThread("TaasHandlerThread", -8);
        this.handlerThread.start();
        this.handler = new Handler(this.handlerThread.getLooper());
        this.mContext = context;
        this.mContext.registerReceiver(
                this.bigDataBroadcastReceiver, new IntentFilter(HQM_UPDATE_REQ));
        this.mDispatcher = SemInputMotionEventDispatcher.getInstance(this.mContext);
        externalEventRegister.registerServiceListener(
                SemInputExternal.Event.LISTENER_DISPLAY_STATE, this.displayStateListener);
        Log.d("SemInputExternalApi", "start external api for taas");
    }

    public void SendBroadcastIssueTrackerTaasDump(Context context, String reason) {
        Log.d(
                "SemInputExternalApi",
                "SendBroadcastIssueTrackerTaasDump reason : "
                        + reason
                        + "isIssueTrackerActive : "
                        + this.isIssueTrackerActive);
        if (!this.isIssueTrackerActive) {
            return;
        }
        Intent intent = new Intent(ISSUE_TRACKER_ACTION_INTENT);
        intent.putExtra(EXTRA_ERROR_PACKAGE, "TAAS");
        intent.putExtra(EXTRA_ERROR_CODE, -141);
        intent.putExtra(EXTRA_ERROR_NAME, "TAAS-CASEA");
        intent.putExtra(
                EXTRA_ERROR_MSG, "[" + reason + "] Touch is okay, but Display is not working");
        context.sendBroadcastAsUser(intent, UserHandle.ALL);
    }

    public void sendHwParamToHqm(String basic_customDataSet) {
        if (this.mSemHqmManager != null) {
            Log.d(
                    "SemInputExternalApi",
                    "sendLoggingDataToHQM() Server update !!! basic_custom " + basic_customDataSet);
            this.mSemHqmManager.sendHWParamToHQM(
                    0, "TSP", "TAAS", "sm", "0.0", "sec", "", basic_customDataSet, "");
        }
    }

    /* renamed from: com.samsung.android.hardware.secinputdev.taas.ExternalApi$3, reason: invalid class name */
    class AnonymousClass3 implements SemInputExternal.IServiceListener {
        AnonymousClass3() {}

        @Override // com.samsung.android.hardware.secinputdev.external.SemInputExternal.IServiceListener
        public void onDisplayStateChanged(
                boolean isEarly, int stateLogical, int statePhysical, int displayType) {
            try {
                if (stateLogical == 2 && isEarly) {
                    if (ExternalApi.this.motionEventRunnable != null) {
                        ExternalApi.this.handler.removeCallbacks(
                                ExternalApi.this.motionEventRunnable);
                    }
                    ExternalApi.this.motionEventRunnable =
                            new Runnable() { // from class:
                                             // com.samsung.android.hardware.secinputdev.taas.ExternalApi$3$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ExternalApi.AnonymousClass3.this
                                            .lambda$onDisplayStateChanged$0();
                                }
                            };
                    ExternalApi.this.handler.postDelayed(
                            ExternalApi.this.motionEventRunnable, 4000L);
                    return;
                }
                if (stateLogical != 2 && isEarly) {
                    if (ExternalApi.this.motionEventRunnable != null) {
                        ExternalApi.this.handler.removeCallbacks(
                                ExternalApi.this.motionEventRunnable);
                    }
                    ExternalApi.this.handler.post(
                            new Runnable() { // from class:
                                             // com.samsung.android.hardware.secinputdev.taas.ExternalApi$3$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ExternalApi.AnonymousClass3.this
                                            .lambda$onDisplayStateChanged$1();
                                }
                            });
                }
            } catch (Exception e) {
                SemInputDeviceManagerService.loggingException("SemInputExternalApi", "register", e);
                ExternalApi.this.bootingDump.append(
                        "- ExternalApi registerMotionEvent about Taas exception: " + e + "\n");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDisplayStateChanged$0() {
            ExternalApi.this.mDispatcher.registerMotionEventListener(
                    ExternalApi.this.motionEventListener);
            ExternalApi.this.motionEventRunnable = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDisplayStateChanged$1() {
            ExternalApi.this.mDispatcher.unregisterMotionEventListener(
                    ExternalApi.this.motionEventListener);
        }
    }

    public void registerMotionEventListener(Consumer<MotionEvent> listener) {
        this.listeners.add(listener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runEvent(MotionEvent event) {
        if (!this.listeners.isEmpty()) {
            for (Consumer<MotionEvent> listener : this.listeners) {
                listener.accept(event);
            }
        }
    }

    public long getSurfaceTimerStates() {
        try {
            IBinder displayToken = SemSurfaceControl.getInternalDisplayToken();
            SurfaceControl.IdleBeginTime timerStates =
                    SurfaceControl.getIdleBeginTime(displayToken);
            return TimeUnit.NANOSECONDS.toMillis(timerStates.beginTimeIdle);
        } catch (Exception e) {
            Log.e("SemInputExternalApi", "getSurfaceTimerStates() Exception : " + e);
            throw new IllegalArgumentException("displayToken must not be null");
        }
    }

    public void writePerfLog(String str) {
        PerfLog.d(17, str);
    }

    public void writePerfLogOfHwDefect(String str) {
        PerfLog.d(
                17,
                str
                        + "hd "
                        + ((this.currentTime - this.hwDefectTime) / 1000)
                        + "s "
                        + this.hwDefectCnt);
        Log.d(
                "SemInputExternalApi",
                str
                        + "hd "
                        + ((this.currentTime - this.hwDefectTime) / 1000)
                        + "s "
                        + this.hwDefectCnt);
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public void setHwDefectTime(long hwDefectTime) {
        this.hwDefectTime = hwDefectTime;
    }

    public void setHwDefectCnt(long hwDefectCnt) {
        this.hwDefectCnt = hwDefectCnt;
    }

    public long getHwDefectCnt() {
        return this.hwDefectCnt;
    }

    public void setCaseACount(String pkgName) {
        this.mHqmLoggingData.setCaseACount(pkgName);
    }

    public void setCaseBCount() {
        this.mHqmLoggingData.setCaseBCount();
    }

    public void setLoggingData(String key, int value) {
        this.mHqmLoggingData.setLoggingData(key, value);
    }

    public void writeForCaseToEfs(String getTime, String TaasLog, String taasCase) {
        this.mHqmLoggingData.writeForCaseToEfs(getTime, TaasLog, taasCase);
    }

    public SemInputDeviceHqmData getmHqmLoggingData() {
        return this.mHqmLoggingData;
    }

    public String sendHqmTspData(SemInputDeviceHqmData data) {
        return this.mHqmHelper.sendHqmTspData(data);
    }

    public void post(Runnable runnable) {
        this.handler.post(runnable);
    }

    public boolean getInHouse() {
        return SemInputFeatures.IS_IN_HOUSE_PROJECT;
    }

    public void destroy() {
        if (this.motionEventRunnable != null) {
            this.handler.removeCallbacks(this.motionEventRunnable);
            Log.d("SemInputExternalApi", "removed callbacks handler");
        }
        if (this.mDispatcher != null) {
            try {
                this.mDispatcher.unregisterMotionEventListener(this.motionEventListener);
            } catch (Exception e) {
                SemInputDeviceManagerService.loggingException("SemInputExternalApi", "destroy", e);
            }
        }
        this.mHqmLoggingData.destroy();
    }
}
