package com.samsung.android.hardware.secinputdev.taas;

import android.app.ActivityManager;
import android.content.Context;
import android.os.SystemClock;
import android.text.format.Time;
import android.util.Log;
import android.view.MotionEvent;

import com.samsung.android.hardware.secinputdev.SemInputDeviceManagerService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public class SemInputTaasTestCaseA implements SemInputTaasTestCase {
    private static final String TAG = "SemInputTaasTestCaseA";
    private static final int TIMEOUT_MILLISECONDS = 1000;
    private static final long criteria = 1000;
    private static final long idleTimeTotal = 12000;
    private static final long touchInterval = 2000;
    private static long beginTime = 0;
    private static long endTime = 0;
    private static int touchCount = 0;
    private ExecutorService threadPool = null;
    private ExternalApi mExternalApi = null;
    private Context mContext = null;
    private long currentTime = 0;
    private long deltaTime = 0;
    private boolean detected = false;
    private long oldSurfaceBeginTime = 0;
    private boolean addedLogsFlag = false;

    @Override // com.samsung.android.hardware.secinputdev.taas.SemInputTaasTestCase
    public void create(Context context, ExternalApi externalApi) {
        this.mContext = context;
        this.mExternalApi = externalApi;
        this.threadPool = Executors.newSingleThreadExecutor();
        this.mExternalApi.registerMotionEventListener(
                new Consumer() { // from class:
                                 // com.samsung.android.hardware.secinputdev.taas.SemInputTaasTestCaseA$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        SemInputTaasTestCaseA.this.runEvent((MotionEvent) obj);
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runEvent(MotionEvent mEvent) {
        final int action = mEvent.getAction();
        this.mExternalApi.post(
                new Runnable() { // from class:
                                 // com.samsung.android.hardware.secinputdev.taas.SemInputTaasTestCaseA$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SemInputTaasTestCaseA.this.lambda$runEvent$0(action);
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: runEvent, reason: merged with bridge method [inline-methods] */
    public void lambda$runEvent$0(int action) {
        Time time = new Time();
        time.setToNow();
        this.currentTime = time.toMillis(true);
        this.mExternalApi.setCurrentTime(this.currentTime);
        if (action == 0) {
            runEventActionDown();
        } else if (action != 1) {
            touchCount++;
            checkDetected();
        } else {
            runEventActionUp();
        }
    }

    private void runEventActionDown() {
        this.deltaTime = this.currentTime - endTime;
        if (touchCount == 0 || this.deltaTime > touchInterval) {
            beginTime = this.currentTime;
            touchCount = 1;
        }
    }

    private void runEventActionUp() {
        this.deltaTime = this.currentTime - beginTime;
        endTime = this.currentTime;
        try {
            if (this.detected) {
                ActivityManager activityManager =
                        (ActivityManager) this.mContext.getSystemService("activity");
                List<ActivityManager.RunningTaskInfo> runningTask =
                        activityManager.getRunningTasks(1);
                if (runningTask != null && !runningTask.isEmpty()) {
                    String topActivityName = runningTask.get(0).topActivity.getClassName();
                    String packageName = runningTask.get(0).topActivity.getPackageName();
                    String versionName =
                            this.mContext
                                    .getPackageManager()
                                    .getPackageInfo(packageName, 0)
                                    .versionName;
                    String logStr = topActivityName + " " + packageName + "(" + versionName + ")";
                    this.mExternalApi.writePerfLog(
                            "[sec_input] CASEA ended "
                                    + (this.deltaTime / criteria)
                                    + "s tc "
                                    + touchCount
                                    + " "
                                    + topActivityName);
                    Log.d(
                            TAG,
                            "[sec_input] CASEA ended "
                                    + (this.deltaTime / criteria)
                                    + "s tc "
                                    + touchCount
                                    + " "
                                    + topActivityName
                                    + "Top activity version: "
                                    + versionName);
                    this.detected = false;
                    beginTime = this.currentTime;
                    touchCount = 0;
                    increaseCount(logStr);
                    return;
                }
                this.detected = false;
                beginTime = this.currentTime;
                touchCount = 0;
            }
        } catch (Exception e) {
            this.detected = false;
            beginTime = this.currentTime;
            touchCount = 0;
            Log.d(TAG, "CaseAEventActionUp : " + e);
        }
    }

    private void checkDetected() {
        if (!this.detected) {
            this.deltaTime = this.currentTime - beginTime;
            if (this.deltaTime > criteria) {
                if (callSurfaceTimerStates(criteria)) {
                    try {
                        ActivityManager activityManager =
                                (ActivityManager) this.mContext.getSystemService("activity");
                        List<ActivityManager.RunningTaskInfo> runningTask =
                                activityManager.getRunningTasks(1);
                        if (runningTask != null && !runningTask.isEmpty()) {
                            String topActivityName = runningTask.get(0).topActivity.getClassName();
                            String packageName = runningTask.get(0).topActivity.getPackageName();
                            String versionName =
                                    this.mContext
                                            .getPackageManager()
                                            .getPackageInfo(packageName, 0)
                                            .versionName;
                            String logStr =
                                    topActivityName + " " + packageName + "(" + versionName + ")";
                            if (this.mExternalApi.getHwDefectCnt() != 0) {
                                this.mExternalApi.writePerfLogOfHwDefect(
                                        "[sec_input] CASEA "
                                                + (this.deltaTime / criteria)
                                                + "s tc "
                                                + touchCount
                                                + topActivityName);
                            } else {
                                this.mExternalApi.writePerfLog(
                                        "[sec_input] CASEA "
                                                + (this.deltaTime / criteria)
                                                + "s tc "
                                                + touchCount
                                                + " "
                                                + topActivityName);
                                Log.d(
                                        TAG,
                                        "[sec_input] CASEA ended "
                                                + (this.deltaTime / criteria)
                                                + "s tc "
                                                + touchCount
                                                + " "
                                                + topActivityName
                                                + "Top activity version: "
                                                + versionName);
                            }
                            this.detected = true;
                            increaseCount(logStr);
                            return;
                        }
                        this.detected = true;
                        return;
                    } catch (Exception e) {
                        Log.d(TAG, "CaseAEventActionUp : " + e);
                        return;
                    }
                }
                beginTime = this.currentTime;
                touchCount = 1;
            }
        }
    }

    private boolean callSurfaceTimerStates(long time) {
        FutureTask task =
                new FutureTask(
                        new Callable() { // from class:
                                         // com.samsung.android.hardware.secinputdev.taas.SemInputTaasTestCaseA.1
                            @Override // java.util.concurrent.Callable
                            public Boolean call() throws Exception {
                                return Boolean.valueOf(
                                        SemInputTaasTestCaseA.this.getSurfaceTimerStates());
                            }
                        });
        this.threadPool.execute(task);
        try {
            boolean result = ((Boolean) task.get(time, TimeUnit.MILLISECONDS)).booleanValue();
            return result;
        } catch (InterruptedException e) {
            SemInputDeviceManagerService.loggingException(TAG, "callSurfaceTimerStates:Taas", e);
            return false;
        } catch (ExecutionException e2) {
            SemInputDeviceManagerService.loggingException(TAG, "callSurfaceTimerStates:Taas", e2);
            return false;
        } catch (TimeoutException e3) {
            Log.d(TAG, "failed to get callSurfaceTimerStates for time out");
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean getSurfaceTimerStates() {
        long surfaceForCurrentTime = SystemClock.uptimeMillis();
        long surfaceBeginTime = this.mExternalApi.getSurfaceTimerStates();
        try {
            if (this.addedLogsFlag && surfaceBeginTime == this.oldSurfaceBeginTime) {
                return false;
            }
            this.addedLogsFlag = false;
            if (surfaceForCurrentTime - surfaceBeginTime <= idleTimeTotal || this.addedLogsFlag) {
                return false;
            }
            this.addedLogsFlag = true;
            this.oldSurfaceBeginTime = surfaceBeginTime;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void increaseCount(String buf) {
        long mNow = System.currentTimeMillis();
        Date mDate = new Date(mNow);
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy/MMdd/HH:mm:ss");
        String getTime = mFormat.format(mDate);
        String TaasLog = buf.replaceAll("\\([^)]*\\)", "").trim();
        String[] pakageName = buf.split(" ");
        if (TaasLog.length() > 160) {
            TaasLog = TaasLog.substring(TaasLog.length() - 160, TaasLog.length());
        }
        this.mExternalApi.writeForCaseToEfs(getTime, TaasLog, "A");
        this.mExternalApi.setCaseACount(pakageName[1]);
    }

    @Override // com.samsung.android.hardware.secinputdev.taas.SemInputTaasTestCase
    public void destroy() {}
}
