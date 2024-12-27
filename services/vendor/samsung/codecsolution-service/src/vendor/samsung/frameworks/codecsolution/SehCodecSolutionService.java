package vendor.samsung.frameworks.codecsolution;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Log;
import android.view.IWindowManager;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import vendor.samsung.frameworks.codecsolution.ISehCodecSolution;
import vendor.samsung.frameworks.codecsolution.Utils;

/* compiled from: qb/89523975 8dcf0dbe0f67a13a743f09df378775af1adb9bb1e787ba2899bc963e7059e86a */
/* loaded from: classes.dex */
public class SehCodecSolutionService extends ISehCodecSolution.Stub {
    private static final String ACTION_SCPM_UPDATE_BROADCAST = "com.samsung.android.scpm.policy.UPDATE.opus-offload-enable-list";
    private static final int HANDLER_MSG_LOGGING_EVENT = 900;
    private static final int HANDLER_MSG_SCPM_GET_OPUSOFFLOAD_ENABLE_PROPERTY = 1000;
    private static final int HANDLER_MSG_SCPM_V2_REGISTER = 210;
    private static final int HANDLER_MSG_SEND_BROADCAST = 800;
    private static final int HANDLER_MSG_SEND_BROADCAST_WITH_DATA = 801;
    private static final int HANDLER_MSG_SET_SMARTFITTING_PID = 202;
    private static final int HANDLER_MSG_START_SMART_FITTING_SERVICE = 200;
    private static final int HANDLER_MSG_STOP_SMART_FITTING_SERVICE = 201;
    private static final String INTENT_DOLBY_STATE_CHANGED = "com.samsung.intent.action.ACTION_DOLBY_CONTENT_PLAYBACK_STATE_CHANGED";
    private static final int MAX_PID_DUMP_COUNT = 256;
    private static final String MODEL_NAME = Build.MODEL;
    private static final int OPUS_VERSION = 101;
    public static final String SERVICE_VERSION = "2.3";
    private static final String TAG = "SehCodecSolutionService";
    private ActivityManager mActivityManager;
    private Context mContext;
    private Handler mHandler;
    private boolean mIsBootCompleted;
    private Logging mLogging;
    private OpusRunnable mOpusRunnable;
    private SCPMReceiver mSCPMReceiver;
    private SCPMV2Helper mSCPMV2Helper;
    private int mSmartFittingMode;
    private WindowManager mWindowManager;
    private IWindowManager mWindowManagerService;
    private int mPidDumpIndex = 0;
    private ArrayList<PidInfo> mPidDumpList = new ArrayList<>(MAX_PID_DUMP_COUNT);
    private ConcurrentHashMap<Long, SehVideoRecordingParameter> mVideoRecordingParameterMap = new ConcurrentHashMap<>();

    /* compiled from: qb/89523975 8dcf0dbe0f67a13a743f09df378775af1adb9bb1e787ba2899bc963e7059e86a */
    public class OpusRunnable implements Runnable {
        public OpusRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            Utils.QueryResult queryResult;
            Log.d(SehCodecSolutionService.TAG, "OpusRunnable  : device MODEL_NAME(" + SehCodecSolutionService.MODEL_NAME + ")");
            Utils.QueryResult queryResult2 = Utils.QueryResult.SERVER_IS_NOT_AVAILABLE;
            if (SehCodecSolutionService.this.mSCPMV2Helper == null || !SehCodecSolutionService.this.mSCPMV2Helper.isAvailable()) {
                Log.w(SehCodecSolutionService.TAG, "SCPMV2 is not available");
                return;
            }
            if (SehCodecSolutionService.this.mSCPMV2Helper.getToken() == null) {
                Log.i(SehCodecSolutionService.TAG, "v2 token is null, send msg to register");
                SehCodecSolutionService.this.mHandler.sendMessage(SehCodecSolutionService.this.mHandler.obtainMessage(SehCodecSolutionService.HANDLER_MSG_SCPM_V2_REGISTER));
                queryResult = Utils.QueryResult.NOT_FOUND;
            } else {
                Utils.QueryResult isInOpusOffloadEnabledList = SehCodecSolutionService.this.mSCPMV2Helper.isInOpusOffloadEnabledList(SehCodecSolutionService.MODEL_NAME, SehCodecSolutionService.OPUS_VERSION);
                SehCodecSolutionService.this.mSCPMV2Helper.setmOpusOffloadEnabledListToNull();
                queryResult = isInOpusOffloadEnabledList;
            }
            if (queryResult == Utils.QueryResult.FOUND) {
                Log.w(SehCodecSolutionService.TAG, "FOUND enabled_model in json list!# Set OpusOffload enabled");
                SystemProperties.set("persist.audio.opusoffload.disable", "0");
            } else {
                Log.w(SehCodecSolutionService.TAG, "NOT FOUND enabled_model in json list!# Set OpusOffload disabled");
                SystemProperties.set("persist.audio.opusoffload.disable", "1");
            }
        }
    }

    /* compiled from: qb/89523975 8dcf0dbe0f67a13a743f09df378775af1adb9bb1e787ba2899bc963e7059e86a */
    protected static class PidInfo {
        public Date date = null;
        public int pid = 0;
        public String packageName = null;
    }

    /* compiled from: qb/89523975 8dcf0dbe0f67a13a743f09df378775af1adb9bb1e787ba2899bc963e7059e86a */
    public class SCPMReceiver extends BroadcastReceiver {
        public SCPMReceiver() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(SehCodecSolutionService.ACTION_SCPM_UPDATE_BROADCAST);
            SehCodecSolutionService.this.mContext.registerReceiver(this, intentFilter, 2);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (SehCodecSolutionService.ACTION_SCPM_UPDATE_BROADCAST.equals(intent.getAction())) {
                Log.d(SehCodecSolutionService.TAG, "SCPM update broadcast received!");
                SehCodecSolutionService.this.mHandler.sendEmptyMessage(SehCodecSolutionService.HANDLER_MSG_SCPM_GET_OPUSOFFLOAD_ENABLE_PROPERTY);
            }
        }
    }

    public SehCodecSolutionService(Context context, IActivityManager iActivityManager) {
        Log.d(TAG, "create : 2.3");
        this.mContext = context;
        this.mLogging = new Logging(context);
        this.mActivityManager = (ActivityManager) this.mContext.getSystemService("activity");
        this.mWindowManager = (WindowManager) this.mContext.getSystemService("window");
        this.mWindowManagerService = WindowManagerGlobal.getWindowManagerService();
        this.mSCPMV2Helper = new SCPMV2Helper(this.mContext);
        this.mOpusRunnable = new OpusRunnable();
        Log.i(TAG, "SCPMReciever for OPUS is registered");
        this.mSCPMReceiver = new SCPMReceiver();
        for (int i = 0; i < MAX_PID_DUMP_COUNT; i++) {
            this.mPidDumpList.add(new PidInfo());
        }
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: vendor.samsung.frameworks.codecsolution.SehCodecSolutionService.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                Log.d(SehCodecSolutionService.TAG, "handleMessage : " + message.what);
                if (SehCodecSolutionService.this.mSCPMV2Helper != null && SehCodecSolutionService.this.mSCPMV2Helper.isAvailable() && SehCodecSolutionService.this.mSCPMV2Helper.getToken() == null && message.what != SehCodecSolutionService.HANDLER_MSG_SCPM_V2_REGISTER) {
                    SehCodecSolutionService.this.mSCPMV2Helper.registerToScpm();
                }
                int i2 = message.what;
                if (i2 == SehCodecSolutionService.HANDLER_MSG_SET_SMARTFITTING_PID) {
                    int i3 = message.arg1;
                    List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = SehCodecSolutionService.this.mActivityManager.getRunningAppProcesses();
                    if (runningAppProcesses == null) {
                        Log.w(SehCodecSolutionService.TAG, "runningAppProcesses is null.");
                        return;
                    }
                    for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                        try {
                            if (runningAppProcessInfo.pid == i3) {
                                Log.i(SehCodecSolutionService.TAG, "pid(" + i3 + ") pkg(" + runningAppProcessInfo.pkgList[0] + ")");
                                try {
                                    int i4 = SehCodecSolutionService.this.mPidDumpIndex - 1;
                                    if (i4 < 0) {
                                        i4 = 255;
                                    }
                                    if (((PidInfo) SehCodecSolutionService.this.mPidDumpList.get(i4)).pid != runningAppProcessInfo.pid) {
                                        ((PidInfo) SehCodecSolutionService.this.mPidDumpList.get(SehCodecSolutionService.this.mPidDumpIndex)).date = new Date();
                                        ((PidInfo) SehCodecSolutionService.this.mPidDumpList.get(SehCodecSolutionService.this.mPidDumpIndex)).pid = runningAppProcessInfo.pid;
                                        ((PidInfo) SehCodecSolutionService.this.mPidDumpList.get(SehCodecSolutionService.this.mPidDumpIndex)).packageName = runningAppProcessInfo.pkgList[0];
                                        SehCodecSolutionService.this.mPidDumpIndex++;
                                        SehCodecSolutionService.this.mPidDumpIndex %= SehCodecSolutionService.MAX_PID_DUMP_COUNT;
                                        return;
                                    }
                                    return;
                                } catch (Exception unused) {
                                    Log.w(SehCodecSolutionService.TAG, "Can't write dump info : " + i3);
                                    return;
                                }
                            }
                        } catch (Exception unused2) {
                            Log.w(SehCodecSolutionService.TAG, "Can't get the pkg of " + i3);
                            return;
                        }
                    }
                    Log.w(SehCodecSolutionService.TAG, "Can't find the pkg of " + i3);
                    return;
                }
                if (i2 == SehCodecSolutionService.HANDLER_MSG_SCPM_V2_REGISTER) {
                    if (SehCodecSolutionService.this.mSCPMV2Helper != null && SehCodecSolutionService.this.mSCPMV2Helper.isAvailable() && SehCodecSolutionService.this.mSCPMV2Helper.getToken() == null) {
                        SehCodecSolutionService.this.mSCPMV2Helper.registerToScpm();
                        return;
                    }
                    return;
                }
                if (i2 == SehCodecSolutionService.HANDLER_MSG_LOGGING_EVENT) {
                    if (!SehCodecSolutionService.this.isBootCompleted()) {
                        Log.d(SehCodecSolutionService.TAG, "ignore before boot completed");
                        return;
                    }
                    MediaStatisticsData mediaStatisticsData = (MediaStatisticsData) message.obj;
                    Log.d(SehCodecSolutionService.TAG, "event : " + mediaStatisticsData.getCategory());
                    SehCodecSolutionService.this.mLogging.insertLog(mediaStatisticsData.getCategory(), mediaStatisticsData.getLabel());
                    return;
                }
                if (i2 == SehCodecSolutionService.HANDLER_MSG_SCPM_GET_OPUSOFFLOAD_ENABLE_PROPERTY) {
                    Log.d(SehCodecSolutionService.TAG, "HANDLER_MSG_SCPM_GET_OPUSOFFLOAD_ENABLE_PROPERTY");
                    new Thread(SehCodecSolutionService.this.mOpusRunnable).start();
                    return;
                }
                if (i2 == SehCodecSolutionService.HANDLER_MSG_SEND_BROADCAST) {
                    String string = message.getData().getString("intent");
                    if (string == null) {
                        Log.w(SehCodecSolutionService.TAG, "intent is null.");
                        return;
                    } else {
                        Log.d(SehCodecSolutionService.TAG, "sendBroadcast ".concat(string));
                        SehCodecSolutionService.this.mContext.sendBroadcastAsUser(new Intent(string), UserHandle.ALL);
                        return;
                    }
                }
                if (i2 != SehCodecSolutionService.HANDLER_MSG_SEND_BROADCAST_WITH_DATA) {
                    return;
                }
                Bundle data = message.getData();
                String string2 = data.getString("intent");
                int i5 = data.getInt("PLAYBACK_STATE");
                if (string2 == null) {
                    Log.w(SehCodecSolutionService.TAG, "intent is null.");
                    return;
                }
                Intent intent = new Intent(string2);
                intent.putExtra("PLAYBACK_STATE", i5);
                Log.d(SehCodecSolutionService.TAG, "sendBroadcast " + string2 + " playbackState : " + i5);
                SehCodecSolutionService.this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isBootCompleted() {
        if (!this.mIsBootCompleted) {
            this.mIsBootCompleted = SystemProperties.getInt("sys.boot_completed", 0) == 1;
        }
        return this.mIsBootCompleted;
    }

    @Override // android.os.Binder
    protected void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            Log.d(TAG, "dump : Permission denial, pid = " + Binder.getCallingPid() + ", uid = " + Binder.getCallingUid());
            return;
        }
        SCPMV2Helper sCPMV2Helper = this.mSCPMV2Helper;
        if (sCPMV2Helper == null || !sCPMV2Helper.isAvailable()) {
            printWriter.println("SCPMv2 is not available");
        } else {
            printWriter.println("SCPMv2 is available");
            printWriter.println("H2SCAllowlistVersion : " + this.mSCPMV2Helper.getH2SCAllowlistVersion());
        }
        printWriter.println("");
        printWriter.println("List : ");
        ArrayList<PidInfo> arrayList = this.mPidDumpList;
        if (arrayList == null) {
            printWriter.println("List is null.");
            return;
        }
        if (arrayList.size() == 0) {
            printWriter.println("List size is 0.");
            return;
        }
        try {
            for (int i = this.mPidDumpIndex; i < MAX_PID_DUMP_COUNT; i++) {
                if (this.mPidDumpList.get(i).pid != 0) {
                    printWriter.println(this.mPidDumpList.get(i).date.toString() + " " + this.mPidDumpList.get(i).pid + " " + this.mPidDumpList.get(i).packageName);
                }
            }
            for (int i2 = 0; i2 < this.mPidDumpIndex; i2++) {
                if (this.mPidDumpList.get(i2).pid != 0) {
                    printWriter.println(this.mPidDumpList.get(i2).date.toString() + " " + this.mPidDumpList.get(i2).pid + " " + this.mPidDumpList.get(i2).packageName);
                }
            }
        } catch (Exception unused) {
            printWriter.println("Unexpected exception.");
        }
    }

    @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
    public SehDisplaySize getDisplaySize() {
        SehDisplaySize sehDisplaySize = new SehDisplaySize();
        sehDisplaySize.width = 1;
        sehDisplaySize.height = 1;
        Log.d(TAG, "w:" + sehDisplaySize.width + " h:" + sehDisplaySize.height);
        return sehDisplaySize;
    }

    @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
    public int getH2SCAllowlistStatus(String str, String str2) {
        Utils.QueryResult isInH2SCAllowlist;
        Utils.QueryResult queryResult = Utils.QueryResult.SERVER_IS_NOT_AVAILABLE;
        SCPMV2Helper sCPMV2Helper = this.mSCPMV2Helper;
        if (sCPMV2Helper == null || !sCPMV2Helper.isAvailable()) {
            Log.w(TAG, "SCPMV2 is not available");
            return 0;
        }
        Log.d(TAG, "getH2SCAllowlistStatus : procName(" + str + "), chipVendor(" + str2 + "), H2SCAllowlistVersion(" + this.mSCPMV2Helper.getH2SCAllowlistVersion() + ")");
        if (this.mSCPMV2Helper.getToken() == null) {
            Log.i(TAG, "v2 token is null, send msg to register");
            this.mHandler.sendMessage(this.mHandler.obtainMessage(HANDLER_MSG_SCPM_V2_REGISTER));
            isInH2SCAllowlist = Utils.QueryResult.NOT_FOUND;
        } else {
            isInH2SCAllowlist = this.mSCPMV2Helper.isInH2SCAllowlist(str, str2);
        }
        return isInH2SCAllowlist == Utils.QueryResult.FOUND ? 1 : 0;
    }

    @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
    public String getInterfaceHash() {
        return ISehCodecSolution.HASH;
    }

    @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
    public int getInterfaceVersion() {
        return 2;
    }

    @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
    public int getSmartFittingAllowlistStatus() {
        return 0;
    }

    @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
    public int getSmartFittingMode() {
        Log.d(TAG, "getSmartFittingMode : " + this.mSmartFittingMode);
        return this.mSmartFittingMode;
    }

    @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
    public SehVideoRecordingParameter getVideoRecordingParameter(long j) {
        SehVideoRecordingParameter remove = this.mVideoRecordingParameterMap.remove(Long.valueOf(j));
        Log.d(TAG, "getVideoRecordingParameter id : " + j);
        return remove;
    }

    @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
    public boolean isDesktopMode() {
        return false;
    }

    public boolean isSmartFittingSupportedRatio() {
        return false;
    }

    @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
    public void setSmartFittingMode(int i) {
        Log.d(TAG, "setSmartFittingMode : " + i);
        this.mSmartFittingMode = i;
    }

    @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
    public void setSmartFittingPid(int i) {
        Message obtainMessage = this.mHandler.obtainMessage(HANDLER_MSG_SET_SMARTFITTING_PID);
        obtainMessage.arg1 = i;
        this.mHandler.sendMessage(obtainMessage);
    }

    @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
    public void setVideoRecordingParameter(long j, SehVideoRecordingParameter sehVideoRecordingParameter) {
        Log.d(TAG, "setVideoRecordingParameter id : " + j);
        this.mVideoRecordingParameterMap.put(Long.valueOf(j), sehVideoRecordingParameter);
    }

    @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
    public synchronized void startSmartFittingService() {
    }

    @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
    public synchronized void stopSmartFittingService() {
    }

    @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
    public void updateMediaStatisticsData(String str) {
        if (str == null) {
            Log.d(TAG, "data is null");
            return;
        }
        Log.d(TAG, "updateMediaStatisticsData: (" + str + ")");
        if (str.equals("")) {
            return;
        }
        MediaStatisticsData mediaStatisticsData = new MediaStatisticsData(str);
        Message obtainMessage = this.mHandler.obtainMessage(HANDLER_MSG_LOGGING_EVENT);
        obtainMessage.obj = mediaStatisticsData;
        this.mHandler.sendMessage(obtainMessage);
    }

    @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
    public void updateStreamStatus(int i, boolean z, int i2) {
        if (i == 3 || i == 4) {
            Message obtainMessage = this.mHandler.obtainMessage(HANDLER_MSG_SEND_BROADCAST_WITH_DATA);
            Bundle bundle = new Bundle();
            bundle.putString("intent", INTENT_DOLBY_STATE_CHANGED);
            if (z) {
                bundle.putInt("PLAYBACK_STATE", 1);
            } else {
                bundle.putInt("PLAYBACK_STATE", 0);
            }
            obtainMessage.setData(bundle);
            this.mHandler.sendMessage(obtainMessage);
        }
    }

    @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
    public void setAutoFitMode(boolean z) {
    }

    @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
    public void setBlackbarState(boolean z) {
    }

    @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
    public void hideSmartFittingButton() {
    }

    @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
    public void showSmartFittingButton() {
    }
}
