package com.android.systemui.media.dialog;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import com.android.systemui.animation.DialogTransitionAnimator;

/* loaded from: classes2.dex */
public final /* synthetic */ class MediaOutputBaseDialog$$ExternalSyntheticLambda1 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ MediaOutputBaseDialog$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                int i2 = MediaOutputBaseDialog.$r8$clinit;
                ((MediaOutputBaseDialog) obj).onStopButtonClick();
                break;
            case 1:
                int i3 = MediaOutputBaseDialog.$r8$clinit;
                ((MediaOutputBaseDialog) obj).onBroadcastIconClick();
                break;
            case 2:
                int i4 = MediaOutputBaseDialog.$r8$clinit;
                ((MediaOutputBaseDialog) obj).dismiss();
                break;
            case 3:
                int i5 = MediaOutputBaseDialog.$r8$clinit;
                ((MediaOutputBaseDialog) obj).onStopButtonClick();
                break;
            default:
                MediaSwitchingController mediaSwitchingController = (MediaSwitchingController) obj;
                DialogTransitionAnimator dialogTransitionAnimator = mediaSwitchingController.mDialogTransitionAnimator;
                dialogTransitionAnimator.getClass();
                DialogTransitionAnimator.AnonymousClass1 anonymousClass1CreateActivityTransitionController$default = DialogTransitionAnimator.createActivityTransitionController$default(dialogTransitionAnimator, view);
                Intent launchIntentForPackage = TextUtils.isEmpty(mediaSwitchingController.mPackageName) ? null : mediaSwitchingController.mContext.getPackageManager().getLaunchIntentForPackage(mediaSwitchingController.mPackageName);
                if (launchIntentForPackage != null) {
                    launchIntentForPackage.addFlags(268435456);
                    mediaSwitchingController.mCallback.dismissDialog();
                    mediaSwitchingController.startActivity(launchIntentForPackage, anonymousClass1CreateActivityTransitionController$default);
                    break;
                }
                break;
        }
    }
}
