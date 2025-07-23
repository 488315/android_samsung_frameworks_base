package com.android.systemui.qs.tiles.impl.fontscaling.domain.interactor;

import android.content.Intent;
import com.android.systemui.accessibility.fontscaling.FontScalingDialogDelegate;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.shared.QSSettingsPackageRepository;
import com.android.systemui.qs.tiles.base.domain.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.base.domain.model.QSTileInput;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import javax.inject.Provider;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class FontScalingTileUserActionInteractor implements QSTileUserActionInteractor {
    public final ActivityStarter activityStarter;
    public final CoroutineContext coroutineContext;
    public final DialogTransitionAnimator dialogTransitionAnimator;
    public final Provider fontScalingDialogDelegateProvider;
    public final KeyguardStateController keyguardStateController;
    public final QSTileIntentUserInputHandler qsTileIntentUserActionHandler;
    public final QSSettingsPackageRepository settingsPackageRepository;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public FontScalingTileUserActionInteractor(CoroutineContext coroutineContext, QSTileIntentUserInputHandler qSTileIntentUserInputHandler, Provider provider, KeyguardStateController keyguardStateController, DialogTransitionAnimator dialogTransitionAnimator, ActivityStarter activityStarter, QSSettingsPackageRepository qSSettingsPackageRepository) {
        this.coroutineContext = coroutineContext;
        this.qsTileIntentUserActionHandler = qSTileIntentUserInputHandler;
        this.fontScalingDialogDelegateProvider = provider;
        this.keyguardStateController = keyguardStateController;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
        this.activityStarter = activityStarter;
        this.settingsPackageRepository = qSSettingsPackageRepository;
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor
    public final Object handleInput(final QSTileInput qSTileInput, Continuation continuation) {
        QSTileUserAction qSTileUserAction = qSTileInput.action;
        if (qSTileUserAction instanceof QSTileUserAction.Click) {
            final boolean z = (((QSTileUserAction.Click) qSTileUserAction).expandable == null || ((KeyguardStateControllerImpl) this.keyguardStateController).mShowing) ? false : true;
            Object withContext = BuildersKt.withContext(this.coroutineContext, new FontScalingTileUserActionInteractor$handleInput$2$1(this, new Runnable() { // from class: com.android.systemui.qs.tiles.impl.fontscaling.domain.interactor.FontScalingTileUserActionInteractor$handleInput$2$runnable$1
                @Override // java.lang.Runnable
                public final void run() {
                    DialogTransitionAnimator.Controller dialogTransitionController;
                    SystemUIDialog createDialog = ((FontScalingDialogDelegate) FontScalingTileUserActionInteractor.this.fontScalingDialogDelegateProvider.get()).createDialog();
                    if (!z) {
                        createDialog.show();
                        return;
                    }
                    Expandable expandable = ((QSTileUserAction.Click) qSTileInput.action).expandable;
                    if (expandable == null || (dialogTransitionController = expandable.dialogTransitionController(new DialogCuj(58, "font_scaling"))) == null) {
                        createDialog.show();
                        return;
                    }
                    DialogTransitionAnimator dialogTransitionAnimator = FontScalingTileUserActionInteractor.this.dialogTransitionAnimator;
                    TransitionAnimator.Timings timings = DialogTransitionAnimator.TIMINGS;
                    dialogTransitionAnimator.show(createDialog, dialogTransitionController, false);
                }
            }, null), continuation);
            if (withContext == CoroutineSingletons.COROUTINE_SUSPENDED) {
                return withContext;
            }
        } else if (qSTileUserAction instanceof QSTileUserAction.LongClick) {
            QSTileIntentUserInputHandler.handle$default(this.qsTileIntentUserActionHandler, ((QSTileUserAction.LongClick) qSTileUserAction).expandable, new Intent("android.settings.TEXT_READING_SETTINGS").setPackage(this.settingsPackageRepository.getSettingsPackageName()));
        } else if (!(qSTileUserAction instanceof QSTileUserAction.ToggleClick)) {
            throw new NoWhenBranchMatchedException();
        }
        return Unit.INSTANCE;
    }
}
