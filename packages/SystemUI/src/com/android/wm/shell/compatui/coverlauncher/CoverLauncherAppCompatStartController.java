package com.android.wm.shell.compatui.coverlauncher;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.TaskStackListener;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.view.SemWindowManager;
import java.util.Iterator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class CoverLauncherAppCompatStartController {
    public final Context mContext;
    public String mCoverLauncherAppCompatMode;
    public ActivityManager.RunningTaskInfo mTargetTask = null;
    public boolean mIsFolded = false;
    public final CoverLauncherHandler mHandler = new CoverLauncherHandler(this, 0);
    public final SemWindowManager mWindowManager = SemWindowManager.getInstance();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class CoverLauncherHandler extends Handler {
        public /* synthetic */ CoverLauncherHandler(CoverLauncherAppCompatStartController coverLauncherAppCompatStartController, int i) {
            this();
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            CoverLauncherAppCompatStartController coverLauncherAppCompatStartController = CoverLauncherAppCompatStartController.this;
            coverLauncherAppCompatStartController.getClass();
            try {
                coverLauncherAppCompatStartController.mTargetTask = null;
                Iterator it = ActivityManager.getService().getTasks(20).iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) it.next();
                    if (runningTaskInfo.displayId == 1 && runningTaskInfo.getActivityType() != 2 && runningTaskInfo.getActivityType() != 3 && runningTaskInfo.isFocused && runningTaskInfo.getWindowingMode() == 1) {
                        coverLauncherAppCompatStartController.mTargetTask = runningTaskInfo;
                        break;
                    }
                }
                if (coverLauncherAppCompatStartController.checkValidTargetTask()) {
                    coverLauncherAppCompatStartController.processCompatMode();
                }
            } catch (Exception e) {
                Log.e("CoverLauncherAppCompatStartController", "failed to call getTasks in onFlipStateChanged", e);
            }
        }

        private CoverLauncherHandler() {
        }
    }

    public CoverLauncherAppCompatStartController(Context context) {
        this.mContext = context;
        if (CoreRune.FW_FLIP_FULL_COVER_SCREEN_APP_COMPAT) {
            String string = Settings.System.getString(context.getContentResolver(), "cover_launcher_app_compat_mode");
            this.mCoverLauncherAppCompatMode = string;
            if (TextUtils.isEmpty(string)) {
                this.mCoverLauncherAppCompatMode = "fullscreen";
                Settings.System.putString(context.getContentResolver(), "cover_launcher_app_compat_mode", "fullscreen");
            }
            try {
                ActivityTaskManager.getService().registerTaskStackListener(new TaskStackListener() { // from class: com.android.wm.shell.compatui.coverlauncher.CoverLauncherAppCompatStartController.1
                    public final void onActivityRestartAttempt(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2, boolean z3) {
                        CoverLauncherAppCompatStartController coverLauncherAppCompatStartController = CoverLauncherAppCompatStartController.this;
                        if (coverLauncherAppCompatStartController.mWindowManager.isFolded()) {
                            coverLauncherAppCompatStartController.mIsFolded = true;
                            CoverLauncherAppCompatStartController.this.mHandler.removeMessages(1);
                            CoverLauncherHandler coverLauncherHandler = CoverLauncherAppCompatStartController.this.mHandler;
                            coverLauncherHandler.sendMessageDelayed(coverLauncherHandler.obtainMessage(1), 100L);
                            return;
                        }
                        if (coverLauncherAppCompatStartController.mIsFolded) {
                            coverLauncherAppCompatStartController.startFloatingIcon("clear");
                            coverLauncherAppCompatStartController.mIsFolded = false;
                        }
                    }

                    public final void onTaskFocusChanged(int i, boolean z) {
                        CoverLauncherAppCompatStartController coverLauncherAppCompatStartController = CoverLauncherAppCompatStartController.this;
                        if (coverLauncherAppCompatStartController.mWindowManager.isFolded()) {
                            coverLauncherAppCompatStartController.mIsFolded = true;
                            CoverLauncherAppCompatStartController.this.mHandler.removeMessages(1);
                            CoverLauncherHandler coverLauncherHandler = CoverLauncherAppCompatStartController.this.mHandler;
                            coverLauncherHandler.sendMessageDelayed(coverLauncherHandler.obtainMessage(1), 100L);
                            return;
                        }
                        if (coverLauncherAppCompatStartController.mIsFolded) {
                            coverLauncherAppCompatStartController.startFloatingIcon("clear");
                            coverLauncherAppCompatStartController.mIsFolded = false;
                        }
                    }
                });
            } catch (Exception e) {
                Log.e("CoverLauncherAppCompatStartController", "failed to call registerTaskStackListener", e);
            }
            String str = this.mCoverLauncherAppCompatMode;
            if (!CoreRune.FW_FLIP_FULL_COVER_SCREEN_APP_COMPAT || str == null) {
                return;
            }
            Log.d("CoverLauncherAppCompatStartController", "Init CoverLauncherAppCompatMode StatusPreferences");
            Intent intent = new Intent();
            intent.setComponent(ComponentName.unflattenFromString("com.android.systemui/com.android.wm.shell.compatui.coverlauncher.CoverLauncherAppCompatService"));
            intent.putExtra("compatModeType", "clear");
            intent.putExtra("compatStart", true);
            this.mContext.startServiceAsUser(intent, UserHandle.CURRENT);
        }
    }

    public final boolean checkValidTargetTask() {
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mTargetTask;
        if (runningTaskInfo != null && runningTaskInfo.baseActivity != null && runningTaskInfo.isLaunchedFromMultistarCoverLauncher) {
            return true;
        }
        StringBuilder sb = new StringBuilder("mTargetTask is Not Available, mTargetTask");
        if (this.mTargetTask != null) {
            sb.append(", baseActivity : " + this.mTargetTask.baseActivity);
            sb.append(", isLaunchedFromMultistarCoverLauncher : " + this.mTargetTask.isLaunchedFromMultistarCoverLauncher);
        }
        Log.d("CoverLauncherAppCompatStartController", sb.toString());
        startFloatingIcon("clear");
        return false;
    }

    public final void processCompatMode() {
        String string = Settings.System.getString(this.mContext.getContentResolver(), "cover_launcher_app_compat_mode");
        this.mCoverLauncherAppCompatMode = string;
        if ("fullscreen".equals(string)) {
            startFloatingIcon("landscape");
            return;
        }
        if ("landscape".equals(this.mCoverLauncherAppCompatMode)) {
            startFloatingIcon("portrait");
        } else if ("portrait".equals(this.mCoverLauncherAppCompatMode)) {
            startFloatingIcon("fullscreen");
        } else {
            startFloatingIcon("clear");
            this.mCoverLauncherAppCompatMode = "fullscreen";
        }
    }

    public final void startFloatingIcon(String str) {
        if (CoreRune.FW_FLIP_FULL_COVER_SCREEN_APP_COMPAT_FLOATING_ICON) {
            Log.d("CoverLauncherAppCompatStartController", "startFloatingIcon: extra=".concat(str));
            Intent intent = new Intent();
            intent.setComponent(ComponentName.unflattenFromString("com.android.systemui/com.android.wm.shell.compatui.coverlauncher.CoverLauncherAppCompatService"));
            intent.putExtra("compatModeType", str);
            intent.putExtra("compatStart", true);
            this.mContext.startServiceAsUser(intent, Process.myUserHandle());
        }
    }
}
