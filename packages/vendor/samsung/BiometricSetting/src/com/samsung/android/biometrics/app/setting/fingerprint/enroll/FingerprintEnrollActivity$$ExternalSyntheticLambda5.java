package com.samsung.android.biometrics.app.setting.fingerprint.enroll;

import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.Utils;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final /* synthetic */ class FingerprintEnrollActivity$$ExternalSyntheticLambda5
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ FingerprintEnrollActivity$$ExternalSyntheticLambda5(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        TextView textView;
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                FingerprintEnrollActivity fingerprintEnrollActivity =
                        (FingerprintEnrollActivity) obj;
                if (fingerprintEnrollActivity.mEnrollGuideFrame != null) {
                    fingerprintEnrollActivity.startEnrollment();
                    fingerprintEnrollActivity.mEnrollGuideFrame.show();
                    boolean z = fingerprintEnrollActivity.mIsTalkbackEnabled;
                    if (!z && fingerprintEnrollActivity.mEnrollGuideFrame != null && !z) {
                        fingerprintEnrollActivity.mTouchGuideHandler =
                                new Handler(fingerprintEnrollActivity.getMainLooper());
                        fingerprintEnrollActivity.mTouchGuideRunnable =
                                new FingerprintEnrollActivity$$ExternalSyntheticLambda5(
                                        2, fingerprintEnrollActivity);
                        FingerprintEnrollGuideFrame fingerprintEnrollGuideFrame =
                                fingerprintEnrollActivity.mEnrollGuideFrame;
                        FingerprintEnrollActivity.AnonymousClass21 anonymousClass21 =
                                fingerprintEnrollActivity.mTouchGuideViewListener;
                        Rect semGetFingerIconRectInDisplay =
                                fingerprintEnrollActivity.mFingerprintManager
                                        .semGetFingerIconRectInDisplay();
                        fingerprintEnrollGuideFrame.mTouchViewListener = anonymousClass21;
                        fingerprintEnrollGuideFrame.mRectFingerIcon = semGetFingerIconRectInDisplay;
                        break;
                    }
                }
                break;
            case 1:
                FingerprintEnrollActivity fingerprintEnrollActivity2 =
                        (FingerprintEnrollActivity) obj;
                fingerprintEnrollActivity2.mIsShownLiftMsg = false;
                fingerprintEnrollActivity2.setFingerGuideTitle(400);
                if (!fingerprintEnrollActivity2.mIsShowErrorMsg) {
                    fingerprintEnrollActivity2.startViewAnimation(
                            fingerprintEnrollActivity2.mGuideTitle, 204);
                    fingerprintEnrollActivity2.startViewAnimation(
                            fingerprintEnrollActivity2.mTxtViewProgress, 204);
                }
                if (!Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                    fingerprintEnrollActivity2.runTextToSpeech(
                            1, String.valueOf(fingerprintEnrollActivity2.mGuideTitle.getText()));
                    break;
                }
                break;
            case 2:
                FingerprintEnrollActivity fingerprintEnrollActivity3 =
                        (FingerprintEnrollActivity) obj;
                if (fingerprintEnrollActivity3.mIsTouchedFingerFrame
                        && (textView = fingerprintEnrollActivity3.mGuideTitle) != null) {
                    textView.setText(
                            fingerprintEnrollActivity3.mIsTalkbackEnabled
                                    ? R.string.fingerprint_enroll_partial_tts
                                    : R.string.fingerprint_enroll_partial);
                    fingerprintEnrollActivity3.startViewAnimation(
                            fingerprintEnrollActivity3.mGuideTitle, 210);
                    fingerprintEnrollActivity3.mLogging_Partial++;
                    fingerprintEnrollActivity3.tuneTouchGuideFrame();
                    if (fingerprintEnrollActivity3.mEnrollGuideFrame != null) {
                        Log.d("BSS_FingerprintEnrollActivity", "TouchGuideRunnable - finishScan");
                        fingerprintEnrollActivity3.mEnrollGuideFrame.finishScan();
                        break;
                    }
                }
                break;
            default:
                ((FingerprintEnrollActivity)
                                ((FingerprintEnrollActivity.AnonymousClass5) obj).this$0)
                        .mNextButtonArea.setVisibility(0);
                break;
        }
    }
}
