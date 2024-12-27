package com.android.systemui.screenshot.sep;

import android.content.Context;
import android.media.AudioManager;
import android.os.SystemProperties;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.samsung.android.feature.SemCscFeature;
import com.sec.ims.presence.ServiceTuple;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ScreenshotFeedbackController {
    public final ScreenshotCaptureSound mCaptureSound;
    public final Context mContext;
    public final Vibrator mVibrator;

    public ScreenshotFeedbackController(Context context) {
        this.mContext = context;
        ScreenshotCaptureSound screenshotCaptureSound = new ScreenshotCaptureSound(context);
        this.mCaptureSound = screenshotCaptureSound;
        synchronized (screenshotCaptureSound) {
            try {
                String[] strArr = ScreenshotCaptureSound.SOUND_FILES;
                int[] iArr = screenshotCaptureSound.mSoundIds;
                if (iArr[0] == -1) {
                    iArr[0] = screenshotCaptureSound.mSoundPool.load(strArr[0], 1);
                }
                int[] iArr2 = screenshotCaptureSound.mForcedSoundIds;
                if (iArr2[0] == -1) {
                    iArr2[0] = screenshotCaptureSound.mForcedSoundPool.load(strArr[0], 1);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mVibrator = (Vibrator) context.getSystemService("vibrator");
    }

    public final void semPlayCameraSound() {
        boolean equals = SystemProperties.get("service.camera.running", "0").equals("1");
        boolean equals2 = SystemProperties.get("service.camera.rec.running", "0").equals("1");
        boolean equals3 = SystemProperties.get("service.camera.sfs.running", "0").equals("1");
        boolean equals4 = SystemProperties.get("service.bioface.authenticating", "0").equals("1");
        boolean z = Settings.Secure.getInt(this.mContext.getContentResolver(), "skip_adaptive_sound", 0) == 1;
        StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("isCameraRunning = ", ", isRecordRunning = ", ", isSmartStayRunning = ", equals, equals2);
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, equals3, ", isVtCallRunning = false, isBioFaceRunning = ", equals4, ", isAdaptiveBrightness = ");
        m.append(z);
        Log.i("Screenshot", m.toString());
        boolean z2 = SemCscFeature.getInstance().getBoolean("CscFeature_Framework_EnableScrCaptureSoundOnlyInCamera", false);
        ScreenshotCaptureSound screenshotCaptureSound = this.mCaptureSound;
        if (z2) {
            if (!equals || (!((!equals2) & (!equals3)) || !(!equals4)) || z) {
                return;
            }
            Log.i("Screenshot", "Camera is running. Play capture sound!");
            screenshotCaptureSound.play(true);
            return;
        }
        int ringerMode = ((AudioManager) this.mContext.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO)).getRingerMode();
        if (Settings.System.getInt(this.mContext.getContentResolver(), "csc_pref_camera_forced_shuttersound_key", 0) == 1 && equals && !equals3 && !equals4 && !z) {
            Log.i("Screenshot", "[forcedShutterSound] Camera is running!!!!");
            screenshotCaptureSound.play(true);
            return;
        }
        if (ringerMode == 2) {
            screenshotCaptureSound.play(false);
            return;
        }
        if (ringerMode == 1) {
            Log.i("Screenshot", "SupportedVibrationType() : " + this.mVibrator.semGetSupportedVibrationType());
            if (this.mVibrator.semGetSupportedVibrationType() > 1) {
                this.mVibrator.vibrate(VibrationEffect.semCreateHaptic(HapticFeedbackConstants.semGetVibrationIndex(4), -1, VibrationEffect.SemMagnitudeType.TYPE_MAX));
            } else if (this.mVibrator.semGetSupportedVibrationType() == 1) {
                this.mVibrator.vibrate(VibrationEffect.semCreateWaveform(HapticFeedbackConstants.semGetVibrationIndex(100), -1, VibrationEffect.SemMagnitudeType.TYPE_TOUCH));
            }
        }
    }
}
