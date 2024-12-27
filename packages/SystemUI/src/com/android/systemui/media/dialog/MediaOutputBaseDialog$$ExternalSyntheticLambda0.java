package com.android.systemui.media.dialog;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.DialogTransitionAnimator$createActivityTransitionController$1;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class MediaOutputBaseDialog$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ MediaOutputBaseDialog$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((MediaOutputBaseDialog) obj).onStopButtonClick();
                break;
            case 1:
                ((MediaOutputBaseDialog) obj).onBroadcastIconClick();
                break;
            case 2:
                ((MediaOutputBaseDialog) obj).dismiss();
                break;
            case 3:
                ((MediaOutputBaseDialog) obj).onStopButtonClick();
                break;
            default:
                MediaOutputController mediaOutputController = (MediaOutputController) obj;
                DialogTransitionAnimator dialogTransitionAnimator = mediaOutputController.mDialogTransitionAnimator;
                dialogTransitionAnimator.getClass();
                DialogTransitionAnimator$createActivityTransitionController$1 createActivityTransitionController$default = DialogTransitionAnimator.createActivityTransitionController$default(dialogTransitionAnimator, view);
                Intent launchIntentForPackage = TextUtils.isEmpty(mediaOutputController.mPackageName) ? null : mediaOutputController.mContext.getPackageManager().getLaunchIntentForPackage(mediaOutputController.mPackageName);
                if (launchIntentForPackage != null) {
                    launchIntentForPackage.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                    ((MediaOutputBaseDialog) mediaOutputController.mCallback).mBroadcastSender.closeSystemDialogs();
                    mediaOutputController.mActivityStarter.startActivity(launchIntentForPackage, true, (ActivityTransitionAnimator.Controller) createActivityTransitionController$default);
                    break;
                }
                break;
        }
    }
}
