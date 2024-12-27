package com.samsung.android.hardware.secinputdev.taas;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;

import com.samsung.android.hardware.secinputdev.SemInputDeviceManagerService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public class SemInputTaasTestCaseB implements SemInputTaasTestCase {
    private static final String TAG = "SemInputTaasTestCaseB";
    private ExternalApi mExternalApi = null;
    private Context mContext = null;
    private int prv_tool_type = 1;

    @Override // com.samsung.android.hardware.secinputdev.taas.SemInputTaasTestCase
    public void create(Context context, ExternalApi externalApi) {
        this.mContext = context;
        this.mExternalApi = externalApi;
        this.mExternalApi.registerMotionEventListener(
                new Consumer() { // from class:
                                 // com.samsung.android.hardware.secinputdev.taas.SemInputTaasTestCaseB$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        SemInputTaasTestCaseB.this.runEvent((MotionEvent) obj);
                    }
                });
    }

    public void runEvent(MotionEvent mEvent) {
        try {
            final int toolType = mEvent.getToolType(0);
            final String deviceName = mEvent.getDevice().getName();
            this.mExternalApi.post(
                    new Runnable() { // from class:
                                     // com.samsung.android.hardware.secinputdev.taas.SemInputTaasTestCaseB$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            SemInputTaasTestCaseB.this.lambda$runEvent$0(toolType, deviceName);
                        }
                    });
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(TAG, "get Device Name fail", e);
        }
    }

    /* renamed from: runEvent, reason: merged with bridge method [inline-methods] */
    public void lambda$runEvent$0(int cur_tool_type, String deviceName) {
        if ((this.prv_tool_type == 1 || this.prv_tool_type == 2)
                && cur_tool_type != 1
                && cur_tool_type != 2) {
            ActivityManager activityManager =
                    (ActivityManager) this.mContext.getSystemService("activity");
            List<ActivityManager.RunningTaskInfo> runningTask = activityManager.getRunningTasks(1);
            if (runningTask == null || runningTask.isEmpty()) {
                this.prv_tool_type = cur_tool_type;
                return;
            }
            String topActivityName = runningTask.get(0).topActivity.getClassName();
            String packageName = runningTask.get(0).topActivity.getPackageName();
            String logStr = topActivityName + " " + packageName + " && " + deviceName;
            this.mExternalApi.writePerfLog(
                    "[sec_input] CASEB : "
                            + this.prv_tool_type
                            + "->"
                            + cur_tool_type
                            + " "
                            + deviceName
                            + " "
                            + topActivityName);
            Log.d(
                    TAG,
                    "[sec_input] CASEB : tool type change pre("
                            + this.prv_tool_type
                            + ") : cur("
                            + cur_tool_type
                            + ") : "
                            + deviceName
                            + " "
                            + MotionEvent.toolTypeToString(cur_tool_type)
                            + " "
                            + topActivityName);
            increaseCount(logStr);
        }
        this.prv_tool_type = cur_tool_type;
    }

    private void increaseCount(String buf) {
        String TaasLog;
        long mNow = System.currentTimeMillis();
        Date mDate = new Date(mNow);
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy/MMdd/HH:mm:ss");
        String getTime = mFormat.format(mDate);
        if (buf.length() > 160) {
            TaasLog = buf.substring(buf.length() - 160, buf.length());
        } else {
            TaasLog = buf;
        }
        this.mExternalApi.setCaseBCount();
        this.mExternalApi.writeForCaseToEfs(getTime, TaasLog, "B");
    }

    @Override // com.samsung.android.hardware.secinputdev.taas.SemInputTaasTestCase
    public void destroy() {}
}
