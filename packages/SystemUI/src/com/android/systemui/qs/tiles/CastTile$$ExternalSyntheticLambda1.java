package com.android.systemui.qs.tiles;

import android.app.Dialog;
import android.view.View;
import com.android.internal.app.MediaRouteDialogPresenter;
import com.android.systemui.R;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.qs.tiles.CastTile;
import com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractorImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;

/* loaded from: classes2.dex */
public final /* synthetic */ class CastTile$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CastTile f$0;
    public final /* synthetic */ Expandable f$1;

    public /* synthetic */ CastTile$$ExternalSyntheticLambda1(CastTile castTile, Expandable expandable, int i) {
        this.$r8$classId = i;
        this.f$0 = castTile;
        this.f$1 = expandable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = 1;
        switch (this.$r8$classId) {
            case 0:
                final CastTile castTile = this.f$0;
                Expandable expandable = this.f$1;
                if (!((KeyguardStateControllerImpl) castTile.mKeyguard).mShowing) {
                    castTile.mUiHandler.post(new CastTile$$ExternalSyntheticLambda1(castTile, expandable, i));
                    break;
                } else {
                    castTile.mActivityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.CastTile$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            CastTile castTile2 = castTile;
                            int i2 = CastTile.$r8$clinit;
                            castTile2.getClass();
                            castTile2.mUiHandler.post(new CastTile$$ExternalSyntheticLambda1(castTile2, null, 1));
                        }
                    });
                    break;
                }
            default:
                final CastTile castTile2 = this.f$0;
                Expandable expandable2 = this.f$1;
                int i2 = CastTile.$r8$clinit;
                castTile2.getClass();
                final CastTile.DialogHolder dialogHolder = new CastTile.DialogHolder(0);
                Dialog dialogCreateDialog = MediaRouteDialogPresenter.createDialog(((ShadeDialogContextInteractorImpl) castTile2.mShadeDialogContextInteractor).getContext(), 4, new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.CastTile$$ExternalSyntheticLambda6
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        CastTile castTile3 = castTile2;
                        CastTile.DialogHolder dialogHolder2 = dialogHolder;
                        DialogTransitionAnimator dialogTransitionAnimator = castTile3.mDialogTransitionAnimator;
                        dialogTransitionAnimator.getClass();
                        DialogTransitionAnimator.AnonymousClass1 anonymousClass1CreateActivityTransitionController$default = DialogTransitionAnimator.createActivityTransitionController$default(dialogTransitionAnimator, view);
                        if (anonymousClass1CreateActivityTransitionController$default == null) {
                            dialogHolder2.mDialog.dismiss();
                        }
                        castTile3.mActivityStarter.postStartActivityDismissingKeyguard(castTile3.getLongClickIntent(), 0, anonymousClass1CreateActivityTransitionController$default);
                    }
                }, R.style.Theme_SystemUI_Dialog_Cast, false);
                dialogHolder.mDialog = dialogCreateDialog;
                SystemUIDialog.setShowForAllUsers(dialogCreateDialog);
                SystemUIDialog.registerDismissListener(dialogCreateDialog);
                SystemUIDialog.setWindowOnTop(dialogCreateDialog, ((KeyguardStateControllerImpl) castTile2.mKeyguard).mShowing);
                SystemUIDialog.setDialogSize(dialogCreateDialog);
                castTile2.mUiHandler.post(new CastTile$$ExternalSyntheticLambda2(castTile2, expandable2, dialogCreateDialog, 1));
                break;
        }
    }
}
