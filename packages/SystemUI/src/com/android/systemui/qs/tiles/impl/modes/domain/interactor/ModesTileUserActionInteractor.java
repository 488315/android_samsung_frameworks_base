package com.android.systemui.qs.tiles.impl.modes.domain.interactor;

import android.content.Intent;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.qs.flags.QSComposeFragment;
import com.android.systemui.qs.tiles.base.domain.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.base.domain.model.QSTileInput;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor;
import com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate;
import com.android.systemui.statusbar.policy.ui.dialog.ModesDialogEventLogger;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ModesTileUserActionInteractor implements QSTileUserActionInteractor {
    public final ModesDialogDelegate dialogDelegate;
    public final Intent longClickIntent = new Intent("android.settings.ZEN_MODE_SETTINGS");
    public final QSTileIntentUserInputHandler qsTileIntentUserInputHandler;

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

    public ModesTileUserActionInteractor(CoroutineContext coroutineContext, QSTileIntentUserInputHandler qSTileIntentUserInputHandler, ModesDialogDelegate modesDialogDelegate, ZenModeInteractor zenModeInteractor, ModesDialogEventLogger modesDialogEventLogger) {
        this.qsTileIntentUserInputHandler = qSTileIntentUserInputHandler;
        this.dialogDelegate = modesDialogDelegate;
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) {
        QSTileUserAction qSTileUserAction = qSTileInput.action;
        if (qSTileUserAction instanceof QSTileUserAction.Click) {
            Object showDialog = this.dialogDelegate.showDialog(((QSTileUserAction.Click) qSTileUserAction).expandable, (SuspendLambda) continuation);
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (showDialog != coroutineSingletons) {
                showDialog = Unit.INSTANCE;
            }
            if (showDialog == coroutineSingletons) {
                return showDialog;
            }
        } else if (qSTileUserAction instanceof QSTileUserAction.ToggleClick) {
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            int i = QSComposeFragment.$r8$clinit;
            refactorFlagUtils.getClass();
            RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.qs_ui_refactor_compose_fragment to be enabled.");
            Unit unit = Unit.INSTANCE;
            if (unit == CoroutineSingletons.COROUTINE_SUSPENDED) {
                return unit;
            }
        } else {
            if (!(qSTileUserAction instanceof QSTileUserAction.LongClick)) {
                throw new NoWhenBranchMatchedException();
            }
            QSTileIntentUserInputHandler.handle$default(this.qsTileIntentUserInputHandler, ((QSTileUserAction.LongClick) qSTileUserAction).expandable, this.longClickIntent);
        }
        return Unit.INSTANCE;
    }
}
