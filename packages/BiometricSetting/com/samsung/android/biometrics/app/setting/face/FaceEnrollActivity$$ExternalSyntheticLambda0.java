package com.samsung.android.biometrics.app.setting.face;

import android.view.View;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class FaceEnrollActivity$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FaceEnrollActivity f$0;

    public /* synthetic */ FaceEnrollActivity$$ExternalSyntheticLambda0(FaceEnrollActivity faceEnrollActivity, int i) {
        this.$r8$classId = i;
        this.f$0 = faceEnrollActivity;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                FaceEnrollActivity faceEnrollActivity = this.f$0;
                int i = FaceEnrollActivity.$r8$clinit;
                faceEnrollActivity.setEnrollFragment();
                break;
            case 1:
                this.f$0.hideGlassesGuide(view);
                break;
            default:
                this.f$0.hideGlassesGuide(view);
                break;
        }
    }
}
