package com.android.systemui.qs.tiles.impl.rotation.domain.interactor;

import android.content.Intent;
import com.android.systemui.qs.tiles.base.domain.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.base.domain.model.QSTileInput;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import com.android.systemui.qs.tiles.impl.rotation.domain.model.RotationLockTileModel;
import com.android.systemui.statusbar.policy.RotationLockController;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class RotationLockTileUserActionInteractor implements QSTileUserActionInteractor {
    public final RotationLockController controller;
    public final QSTileIntentUserInputHandler qsTileIntentUserActionHandler;

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

    public RotationLockTileUserActionInteractor(RotationLockController rotationLockController, QSTileIntentUserInputHandler qSTileIntentUserInputHandler) {
        this.controller = rotationLockController;
        this.qsTileIntentUserActionHandler = qSTileIntentUserInputHandler;
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) {
        QSTileUserAction qSTileUserAction = qSTileInput.action;
        if (qSTileUserAction instanceof QSTileUserAction.Click) {
            this.controller.setRotationLocked("QSTileUserActionInteractor#handleInput", !((RotationLockTileModel) qSTileInput.data).isRotationLocked);
        } else if (qSTileUserAction instanceof QSTileUserAction.LongClick) {
            QSTileIntentUserInputHandler.handle$default(this.qsTileIntentUserActionHandler, ((QSTileUserAction.LongClick) qSTileUserAction).expandable, new Intent("android.settings.AUTO_ROTATE_SETTINGS"));
        } else if (!(qSTileUserAction instanceof QSTileUserAction.ToggleClick)) {
            throw new NoWhenBranchMatchedException();
        }
        return Unit.INSTANCE;
    }
}
