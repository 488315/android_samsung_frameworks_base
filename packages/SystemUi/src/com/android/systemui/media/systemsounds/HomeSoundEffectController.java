package com.android.systemui.media.systemsounds;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.util.Slog;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class HomeSoundEffectController implements CoreStartable {
    public final ActivityManagerWrapper mActivityManagerWrapper;
    public final AudioManager mAudioManager;
    public boolean mIsLastTaskHome = true;
    public boolean mLastActivityHasNoHomeSound = false;
    public int mLastActivityType;
    public String mLastHomePackageName;
    public int mLastTaskId;
    public final boolean mPlayHomeSoundAfterAssistant;
    public final boolean mPlayHomeSoundAfterDream;
    public final PackageManager mPm;
    public final TaskStackChangeListeners mTaskStackChangeListeners;

    public HomeSoundEffectController(Context context, AudioManager audioManager, TaskStackChangeListeners taskStackChangeListeners, ActivityManagerWrapper activityManagerWrapper, PackageManager packageManager) {
        this.mAudioManager = audioManager;
        this.mTaskStackChangeListeners = taskStackChangeListeners;
        this.mActivityManagerWrapper = activityManagerWrapper;
        this.mPm = packageManager;
        this.mPlayHomeSoundAfterAssistant = context.getResources().getBoolean(R.bool.config_playHomeSoundAfterAssistant);
        this.mPlayHomeSoundAfterDream = context.getResources().getBoolean(R.bool.config_playHomeSoundAfterDream);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        if (this.mAudioManager.isHomeSoundEffectEnabled()) {
            this.mTaskStackChangeListeners.registerTaskStackListener(new TaskStackChangeListener() { // from class: com.android.systemui.media.systemsounds.HomeSoundEffectController.1
                /* JADX WARN: Removed duplicated region for block: B:33:0x0079  */
                /* JADX WARN: Removed duplicated region for block: B:46:0x007b  */
                @Override // com.android.systemui.shared.system.TaskStackChangeListener
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void onTaskStackChanged() {
                    boolean z;
                    boolean z2;
                    int i;
                    HomeSoundEffectController homeSoundEffectController = HomeSoundEffectController.this;
                    ActivityManager.RunningTaskInfo runningTask = homeSoundEffectController.mActivityManagerWrapper.getRunningTask();
                    if (runningTask == null || runningTask.topActivityInfo == null) {
                        return;
                    }
                    homeSoundEffectController.getClass();
                    if ((runningTask.taskId == homeSoundEffectController.mLastTaskId || homeSoundEffectController.mIsLastTaskHome || !(runningTask.topActivityType == 2) || homeSoundEffectController.mLastActivityHasNoHomeSound || ((i = homeSoundEffectController.mLastActivityType) == 4 && !homeSoundEffectController.mPlayHomeSoundAfterAssistant) || (i == 5 && !homeSoundEffectController.mPlayHomeSoundAfterDream)) ? false : true) {
                        homeSoundEffectController.mAudioManager.playSoundEffect(11);
                    }
                    homeSoundEffectController.mLastTaskId = runningTask.taskId;
                    homeSoundEffectController.mLastActivityType = runningTask.topActivityType;
                    ActivityInfo activityInfo = runningTask.topActivityInfo;
                    if ((activityInfo.privateFlags & 2) == 0) {
                        if (homeSoundEffectController.mPm.checkPermission("android.permission.DISABLE_SYSTEM_SOUND_EFFECTS", activityInfo.packageName) == 0) {
                            z = true;
                            homeSoundEffectController.mLastActivityHasNoHomeSound = z;
                            z2 = runningTask.topActivityType != 2;
                            boolean equals = runningTask.topActivityInfo.packageName.equals(homeSoundEffectController.mLastHomePackageName);
                            homeSoundEffectController.mIsLastTaskHome = !z2 || equals;
                            if (z2 || equals) {
                            }
                            homeSoundEffectController.mLastHomePackageName = runningTask.topActivityInfo.packageName;
                            return;
                        }
                        Slog.w("HomeSoundEffectController", "Activity has flag playHomeTransition set to false but doesn't hold required permission android.permission.DISABLE_SYSTEM_SOUND_EFFECTS");
                    }
                    z = false;
                    homeSoundEffectController.mLastActivityHasNoHomeSound = z;
                    if (runningTask.topActivityType != 2) {
                    }
                    boolean equals2 = runningTask.topActivityInfo.packageName.equals(homeSoundEffectController.mLastHomePackageName);
                    homeSoundEffectController.mIsLastTaskHome = !z2 || equals2;
                    if (z2) {
                    }
                }
            });
        }
    }
}
