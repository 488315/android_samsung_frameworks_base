package com.android.server.wm;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.media.AudioManager;
import android.os.AsyncTask;

import com.android.server.policy.PhoneWindowManager;

import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.server.util.SafetySystemService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class WindowManagerServiceExt$$ExternalSyntheticLambda2
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WindowManagerServiceExt f$0;

    public /* synthetic */ WindowManagerServiceExt$$ExternalSyntheticLambda2(
            WindowManagerServiceExt windowManagerServiceExt, int i) {
        this.$r8$classId = i;
        this.f$0 = windowManagerServiceExt;
    }

    @Override // java.lang.Runnable
    public final void run() {
        final AudioManager audioManager;
        int defaultDisplayRotation;
        int i = this.$r8$classId;
        WindowManagerServiceExt windowManagerServiceExt = this.f$0;
        switch (i) {
            case 0:
                if (((PhoneWindowManager) windowManagerServiceExt.mService.mPolicy)
                                .mDefaultDisplayPolicy
                                .mScreenOnEarly
                        && (audioManager =
                                        (AudioManager)
                                                SafetySystemService.getSystemService(
                                                        AudioManager.class))
                                != null
                        && windowManagerServiceExt.mLastReportedRotationToAudioManager
                                != (defaultDisplayRotation =
                                        windowManagerServiceExt.mService
                                                .getDefaultDisplayRotation())) {
                    windowManagerServiceExt.mLastReportedRotationToAudioManager =
                            defaultDisplayRotation;
                    final String m =
                            VibrationParam$1$$ExternalSyntheticOutline0.m(
                                    defaultDisplayRotation, "g_hw_display_rotation=");
                    AsyncTask.execute(
                            new Runnable() { // from class:
                                             // com.android.server.wm.WindowManagerServiceExt$$ExternalSyntheticLambda6
                                @Override // java.lang.Runnable
                                public final void run() {
                                    audioManager.setParameters(m);
                                }
                            });
                    break;
                }
                break;
            default:
                windowManagerServiceExt.getClass();
                CoreSaLogger.logForBasic("W010", (String) null);
                break;
        }
    }
}
