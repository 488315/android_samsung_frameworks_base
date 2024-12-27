package com.android.systemui.qs.tiles;

import android.app.Dialog;
import android.view.View;
import com.android.internal.app.MediaRouteDialogPresenter;
import com.android.systemui.R;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.DialogTransitionAnimator$createActivityTransitionController$1;
import com.android.systemui.animation.Expandable;
import com.android.systemui.qs.tiles.CastTile;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DialogKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class CastTile$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ CastTile f$0;
    public final /* synthetic */ Expandable f$1;

    public /* synthetic */ CastTile$$ExternalSyntheticLambda2(CastTile castTile, Expandable expandable) {
        this.f$0 = castTile;
        this.f$1 = expandable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        final CastTile castTile = this.f$0;
        final Expandable expandable = this.f$1;
        castTile.getClass();
        final CastTile.DialogHolder dialogHolder = new CastTile.DialogHolder(0);
        final Dialog createDialog = MediaRouteDialogPresenter.createDialog(castTile.mContext, 4, new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.CastTile$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CastTile castTile2 = CastTile.this;
                CastTile.DialogHolder dialogHolder2 = dialogHolder;
                DialogTransitionAnimator dialogTransitionAnimator = castTile2.mDialogTransitionAnimator;
                dialogTransitionAnimator.getClass();
                DialogTransitionAnimator$createActivityTransitionController$1 createActivityTransitionController$default = DialogTransitionAnimator.createActivityTransitionController$default(dialogTransitionAnimator, view);
                if (createActivityTransitionController$default == null) {
                    dialogHolder2.mDialog.dismiss();
                }
                castTile2.mActivityStarter.postStartActivityDismissingKeyguard(castTile2.getLongClickIntent(), 0, createActivityTransitionController$default);
            }
        }, R.style.Theme_SystemUI_Dialog_Cast, false);
        dialogHolder.mDialog = createDialog;
        SystemUIDialog.setShowForAllUsers(createDialog);
        SystemUIDialog.registerDismissListener(createDialog);
        SystemUIDialog.setWindowOnTop(createDialog, ((KeyguardStateControllerImpl) castTile.mKeyguard).mShowing);
        SystemUIDialog.setDialogSize(createDialog);
        castTile.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.CastTile$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                DialogTransitionAnimator.Controller dialogTransitionController;
                CastTile castTile2 = CastTile.this;
                Expandable expandable2 = expandable;
                Dialog dialog = createDialog;
                castTile2.getClass();
                if (expandable2 != null && (dialogTransitionController = expandable2.dialogTransitionController(new DialogCuj(58, "cast"))) != null) {
                    castTile2.mDialogTransitionAnimator.show(dialog, dialogTransitionController, false);
                    return;
                }
                if (dialog.getWindow() != null) {
                    DialogKt.registerAnimationOnBackInvoked(dialog, dialog.getWindow().getDecorView());
                }
                dialog.show();
            }
        });
    }
}
