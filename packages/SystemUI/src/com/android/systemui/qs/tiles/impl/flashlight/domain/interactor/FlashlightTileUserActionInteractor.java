package com.android.systemui.qs.tiles.impl.flashlight.domain.interactor;

import android.app.ActivityManager;
import com.android.systemui.qs.tiles.base.interactor.QSTileInput;
import com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.impl.flashlight.domain.model.FlashlightTileModel;
import com.android.systemui.qs.tiles.viewmodel.QSTileUserAction;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.FlashlightControllerImpl;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

public final class FlashlightTileUserActionInteractor implements QSTileUserActionInteractor {
    public final FlashlightController flashlightController;

    public FlashlightTileUserActionInteractor(FlashlightController flashlightController) {
        this.flashlightController = flashlightController;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) {
        if ((qSTileInput.action instanceof QSTileUserAction.Click) && !ActivityManager.isUserAMonkey()) {
            if (qSTileInput.data instanceof FlashlightTileModel.FlashlightAvailable) {
                ((FlashlightControllerImpl) this.flashlightController).setFlashlight(!((FlashlightTileModel.FlashlightAvailable) r1).isEnabled);
            }
        }
        return Unit.INSTANCE;
    }
}
