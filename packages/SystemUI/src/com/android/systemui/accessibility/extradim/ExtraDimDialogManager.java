package com.android.systemui.accessibility.extradim;

import android.os.Handler;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import javax.inject.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ExtraDimDialogManager {
    public SystemUIDialog dialog;
    public final DialogTransitionAnimator dialogTransitionAnimator;
    public final Provider extraDimDialogDelegateProvider;
    public final ActivityStarter mActivityStarter;
    public final Handler mainHandler;

    public ExtraDimDialogManager(Provider provider, ActivityStarter activityStarter, DialogTransitionAnimator dialogTransitionAnimator, Handler handler) {
        this.extraDimDialogDelegateProvider = provider;
        this.mActivityStarter = activityStarter;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
        this.mainHandler = handler;
    }

    public final void dismissKeyguardIfNeededAndShowDialog(final Expandable expandable) {
        this.mainHandler.post(new Runnable() { // from class: com.android.systemui.accessibility.extradim.ExtraDimDialogManager$dismissKeyguardIfNeededAndShowDialog$1
            @Override // java.lang.Runnable
            public final void run() {
                final ExtraDimDialogManager extraDimDialogManager = ExtraDimDialogManager.this;
                ActivityStarter activityStarter = extraDimDialogManager.mActivityStarter;
                final Expandable expandable2 = expandable;
                activityStarter.executeRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.accessibility.extradim.ExtraDimDialogManager$dismissKeyguardIfNeededAndShowDialog$1.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ExtraDimDialogManager extraDimDialogManager2 = ExtraDimDialogManager.this;
                        Expandable expandable3 = expandable2;
                        SystemUIDialog systemUIDialog = extraDimDialogManager2.dialog;
                        if (systemUIDialog != null) {
                            systemUIDialog.dismiss();
                        }
                        SystemUIDialog createDialog = ((ExtraDimDialogDelegate) extraDimDialogManager2.extraDimDialogDelegateProvider.get()).createDialog();
                        extraDimDialogManager2.dialog = createDialog;
                        DialogTransitionAnimator.Controller dialogTransitionController = expandable3 != null ? expandable3.dialogTransitionController(new DialogCuj(58, null, 2, null)) : null;
                        if (dialogTransitionController != null) {
                            extraDimDialogManager2.dialogTransitionAnimator.show(createDialog, dialogTransitionController, true);
                        } else {
                            createDialog.show();
                        }
                    }
                }, null, true, true, false);
            }
        });
    }
}
