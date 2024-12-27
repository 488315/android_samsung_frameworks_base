package com.android.systemui.qs.tiles.impl.rotation.domain.interactor;

import android.content.Intent;
import com.android.systemui.qs.tiles.base.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.interactor.QSTileInput;
import com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.impl.rotation.domain.model.RotationLockTileModel;
import com.android.systemui.qs.tiles.viewmodel.QSTileUserAction;
import com.android.systemui.statusbar.policy.RotationLockController;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class RotationLockTileUserActionInteractor implements QSTileUserActionInteractor {
    public final RotationLockController controller;
    public final QSTileIntentUserInputHandler qsTileIntentUserActionHandler;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public RotationLockTileUserActionInteractor(RotationLockController rotationLockController, QSTileIntentUserInputHandler qSTileIntentUserInputHandler) {
        this.controller = rotationLockController;
        this.qsTileIntentUserActionHandler = qSTileIntentUserInputHandler;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) {
        QSTileUserAction qSTileUserAction = qSTileInput.action;
        if (qSTileUserAction instanceof QSTileUserAction.Click) {
            this.controller.setRotationLocked("QSTileUserActionInteractor#handleInput", !((RotationLockTileModel) qSTileInput.data).isRotationLocked);
        } else if (qSTileUserAction instanceof QSTileUserAction.LongClick) {
            QSTileIntentUserInputHandler.handle$default(this.qsTileIntentUserActionHandler, ((QSTileUserAction.LongClick) qSTileUserAction).expandable, new Intent("android.settings.AUTO_ROTATE_SETTINGS"));
        }
        return Unit.INSTANCE;
    }
}
