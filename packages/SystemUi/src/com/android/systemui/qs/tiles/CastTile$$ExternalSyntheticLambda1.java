package com.android.systemui.qs.tiles;

import android.app.Dialog;
import android.view.View;
import com.android.internal.app.MediaRouteDialogPresenter;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.animation.DialogLaunchAnimator$createActivityLaunchController$1;
import com.android.systemui.qs.tiles.CastTile;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class CastTile$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ CastTile f$0;
    public final /* synthetic */ View f$1;

    public /* synthetic */ CastTile$$ExternalSyntheticLambda1(CastTile castTile, View view) {
        this.f$0 = castTile;
        this.f$1 = view;
    }

    @Override // java.lang.Runnable
    public final void run() {
        final CastTile castTile = this.f$0;
        final View view = this.f$1;
        castTile.getClass();
        final CastTile.DialogHolder dialogHolder = new CastTile.DialogHolder(0);
        final Dialog createDialog = MediaRouteDialogPresenter.createDialog(castTile.mContext, 4, new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.CastTile$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                CastTile castTile2 = CastTile.this;
                CastTile.DialogHolder dialogHolder2 = dialogHolder;
                DialogLaunchAnimator dialogLaunchAnimator = castTile2.mDialogLaunchAnimator;
                dialogLaunchAnimator.getClass();
                DialogLaunchAnimator$createActivityLaunchController$1 createActivityLaunchController$default = DialogLaunchAnimator.createActivityLaunchController$default(dialogLaunchAnimator, view2);
                if (createActivityLaunchController$default == null) {
                    dialogHolder2.mDialog.dismiss();
                }
                castTile2.mActivityStarter.postStartActivityDismissingKeyguard(castTile2.getLongClickIntent(), 0, createActivityLaunchController$default);
            }
        }, 2132018529, false);
        dialogHolder.mDialog = createDialog;
        SystemUIDialog.setShowForAllUsers(createDialog);
        SystemUIDialog.registerDismissListener(createDialog, null);
        SystemUIDialog.setWindowOnTop(createDialog, ((KeyguardStateControllerImpl) castTile.mKeyguard).mShowing);
        SystemUIDialog.setDialogSize(createDialog);
        castTile.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.CastTile$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                CastTile castTile2 = CastTile.this;
                View view2 = view;
                Dialog dialog = createDialog;
                castTile2.getClass();
                if (view2 == null) {
                    dialog.show();
                    return;
                }
                DialogCuj dialogCuj = new DialogCuj(58, "cast");
                DialogLaunchAnimator dialogLaunchAnimator = castTile2.mDialogLaunchAnimator;
                dialogLaunchAnimator.getClass();
                DialogLaunchAnimator.showFromView$default(dialogLaunchAnimator, dialog, view2, dialogCuj, false, 8);
            }
        });
    }
}
