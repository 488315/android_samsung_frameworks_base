package com.android.systemui.screenshot.sep;

import android.content.Context;
import android.media.AudioManager;
import android.os.SystemProperties;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.samsung.android.feature.SemCscFeature;
import com.sec.ims.configuration.DATA;
import com.sec.ims.presence.ServiceTuple;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScreenshotFeedbackController {
    public final ScreenshotCaptureSound mCaptureSound;
    public final Context mContext;
    public final Vibrator mVibrator;

    public ScreenshotFeedbackController(Context context) {
        this.mContext = context;
        ScreenshotCaptureSound screenshotCaptureSound = new ScreenshotCaptureSound(context);
        this.mCaptureSound = screenshotCaptureSound;
        synchronized (screenshotCaptureSound) {
            String[] strArr = ScreenshotCaptureSound.SOUND_FILES;
            int[] iArr = screenshotCaptureSound.mSoundIds;
            if (iArr[0] == -1) {
                iArr[0] = screenshotCaptureSound.mSoundPool.load(strArr[0], 1);
            }
            int[] iArr2 = screenshotCaptureSound.mForcedSoundIds;
            if (iArr2[0] == -1) {
                iArr2[0] = screenshotCaptureSound.mForcedSoundPool.load(strArr[0], 1);
            }
        }
        this.mVibrator = (Vibrator) context.getSystemService("vibrator");
    }

    public final void semPlayCameraSound() {
        boolean equals = SystemProperties.get("service.camera.running", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN).equals("1");
        boolean equals2 = SystemProperties.get("service.camera.rec.running", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN).equals("1");
        boolean equals3 = SystemProperties.get("service.camera.sfs.running", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN).equals("1");
        String str = ScreenshotUtils.VALUE_SUB_DISPLAY_POLICY;
        boolean equals4 = SystemProperties.get("service.bioface.authenticating", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN).equals("1");
        Context context = this.mContext;
        boolean z = Settings.Secure.getInt(context.getContentResolver(), "skip_adaptive_sound", 0) == 1;
        StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("isCameraRunning = ", equals, ", isRecordRunning = ", equals2, ", isSmartStayRunning = ");
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m69m, equals3, ", isVtCallRunning = false, isBioFaceRunning = ", equals4, ", isAdaptiveBrightness = ");
        m69m.append(z);
        Log.i("Screenshot", m69m.toString());
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
        int ringerMode = ((AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO)).getRingerMode();
        if ((Settings.System.getInt(context.getContentResolver(), "csc_pref_camera_forced_shuttersound_key", 0) == 1) && equals && !equals3 && !equals4 && !z) {
            Log.i("Screenshot", "[forcedShutterSound] Camera is running!!!!");
            screenshotCaptureSound.play(true);
            return;
        }
        if (ringerMode == 2) {
            screenshotCaptureSound.play(false);
            return;
        }
        if (ringerMode == 1) {
            StringBuilder sb = new StringBuilder("SupportedVibrationType() : ");
            Vibrator vibrator = this.mVibrator;
            sb.append(vibrator.semGetSupportedVibrationType());
            Log.i("Screenshot", sb.toString());
            if (vibrator.semGetSupportedVibrationType() > 1) {
                vibrator.vibrate(VibrationEffect.semCreateHaptic(HapticFeedbackConstants.semGetVibrationIndex(4), -1, VibrationEffect.SemMagnitudeType.TYPE_MAX));
            } else if (vibrator.semGetSupportedVibrationType() == 1) {
                vibrator.vibrate(VibrationEffect.semCreateWaveform(HapticFeedbackConstants.semGetVibrationIndex(100), -1, VibrationEffect.SemMagnitudeType.TYPE_TOUCH));
            }
        }
    }
}
