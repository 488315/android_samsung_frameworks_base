package com.android.systemui.qs.tiles;

import android.app.Dialog;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.tiles.dialog.CastDetailsViewModel;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DialogKt;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public final /* synthetic */ class CastTile$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CastTile f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ CastTile$$ExternalSyntheticLambda2(CastTile castTile, Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = castTile;
        this.f$1 = obj;
        this.f$2 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        DialogTransitionAnimator.Controller controllerDialogTransitionController;
        switch (this.$r8$classId) {
            case 0:
                CastTile castTile = this.f$0;
                final Consumer consumer = (Consumer) this.f$1;
                final CastDetailsViewModel castDetailsViewModel = (CastDetailsViewModel) this.f$2;
                if (!((KeyguardStateControllerImpl) castTile.mKeyguard).mShowing) {
                    consumer.accept(castDetailsViewModel);
                    break;
                } else {
                    castTile.mActivityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.qs.tiles.CastTile$$ExternalSyntheticLambda4
                        @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                        public final boolean onDismiss() {
                            Consumer consumer2 = consumer;
                            int i = CastTile.$r8$clinit;
                            consumer2.accept(castDetailsViewModel);
                            return false;
                        }
                    }, null, true);
                    break;
                }
            default:
                CastTile castTile2 = this.f$0;
                Expandable expandable = (Expandable) this.f$1;
                Dialog dialog = (Dialog) this.f$2;
                int i = CastTile.$r8$clinit;
                castTile2.getClass();
                if (expandable != null && (controllerDialogTransitionController = expandable.dialogTransitionController(new DialogCuj(58, "cast"))) != null) {
                    castTile2.mDialogTransitionAnimator.show(dialog, controllerDialogTransitionController, false);
                    break;
                } else {
                    if (dialog.getWindow() != null) {
                        DialogKt.registerAnimationOnBackInvoked(dialog, dialog.getWindow().getDecorView());
                    }
                    dialog.show();
                    break;
                }
                break;
        }
    }
}
