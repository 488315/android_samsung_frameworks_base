package com.samsung.android.biometrics.app.setting.face;

import android.os.SystemClock;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final /* synthetic */ class FaceEnrollActivity$6$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FaceEnrollActivity.AnonymousClass6 f$0;

    public /* synthetic */ FaceEnrollActivity$6$$ExternalSyntheticLambda0(
            FaceEnrollActivity.AnonymousClass6 anonymousClass6, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass6;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        FaceEnrollActivity.AnonymousClass6 anonymousClass6 = this.f$0;
        switch (i) {
            case 0:
                anonymousClass6.onEnrollmentHelp(2001, "");
                break;
            case 1:
                anonymousClass6.this$0.setEnrollResult(-1);
                break;
            default:
                FaceEnrollActivity faceEnrollActivity = anonymousClass6.this$0;
                if (!faceEnrollActivity.mIsPreviewStart) {
                    FaceRegisterEffectView faceRegisterEffectView =
                            faceEnrollActivity.mEnrollAnimationView;
                    if (faceRegisterEffectView != null) {
                        if (faceRegisterEffectView.mIsPreviewMask) {
                            faceRegisterEffectView.mIsPreviewStarting = true;
                            if (faceRegisterEffectView.mState != 1) {
                                faceRegisterEffectView.mIsPreviewMask = false;
                                faceRegisterEffectView.mPreviewMaskTimestamp =
                                        SystemClock.elapsedRealtime();
                            }
                        }
                        faceRegisterEffectView.invalidate();
                    }
                    anonymousClass6.this$0.mIsPreviewStart = true;
                }
                FaceEnrollActivity faceEnrollActivity2 = anonymousClass6.this$0;
                faceEnrollActivity2.mCameraPreview.setImageBitmap(
                        faceEnrollActivity2.mPreviewImage);
                FaceEnrollActivity faceEnrollActivity3 = anonymousClass6.this$0;
                faceEnrollActivity3.mPreviewImage = null;
                faceEnrollActivity3.mLastDecodingTimestamp = SystemClock.elapsedRealtime();
                break;
        }
    }
}
