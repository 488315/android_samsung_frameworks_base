package com.android.systemui.qs.tiles.impl.modes.domain.interactor;

import com.android.systemui.animation.Expandable;
import com.android.systemui.qs.shared.QSSettingsPackageRepository;
import com.android.systemui.qs.tiles.base.domain.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.base.domain.model.QSTileInput;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor;
import com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate;
import com.android.systemui.statusbar.policy.ui.dialog.ModesDialogEventLogger;
import kotlin.NoWhenBranchMatchedException;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ModesDndTileUserActionInteractor implements QSTileUserActionInteractor {
    public final ModesDialogDelegate dialogDelegate;
    public final ZenModeInteractor zenModeInteractor;

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

    public ModesDndTileUserActionInteractor(CoroutineContext coroutineContext, QSTileIntentUserInputHandler qSTileIntentUserInputHandler, ModesDialogDelegate modesDialogDelegate, ZenModeInteractor zenModeInteractor, ModesDialogEventLogger modesDialogEventLogger, QSSettingsPackageRepository qSSettingsPackageRepository) {
        this.dialogDelegate = modesDialogDelegate;
        this.zenModeInteractor = zenModeInteractor;
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) {
        QSTileUserAction qSTileUserAction = qSTileInput.action;
        boolean z = qSTileUserAction instanceof QSTileUserAction.Click;
        ZenModeInteractor zenModeInteractor = this.zenModeInteractor;
        if (z || (qSTileUserAction instanceof QSTileUserAction.ToggleClick)) {
            zenModeInteractor.getClass();
            ZenModeInteractor.getDndMode();
            throw null;
        }
        if (!(qSTileUserAction instanceof QSTileUserAction.LongClick)) {
            throw new NoWhenBranchMatchedException();
        }
        Expandable expandable = ((QSTileUserAction.LongClick) qSTileUserAction).expandable;
        zenModeInteractor.getClass();
        ZenModeInteractor.getDndMode();
        throw null;
    }
}
