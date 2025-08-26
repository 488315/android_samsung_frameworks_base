package com.android.systemui.qs.tiles.impl.flashlight.domain.interactor;

import android.app.ActivityManager;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.base.domain.model.QSTileInput;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import com.android.systemui.qs.tiles.impl.flashlight.domain.model.FlashlightTileModel;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.FlashlightControllerImpl;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* loaded from: classes2.dex */
public final class FlashlightTileUserActionInteractor implements QSTileUserActionInteractor {
    public final FlashlightController flashlightController;

    public FlashlightTileUserActionInteractor(FlashlightController flashlightController) {
        this.flashlightController = flashlightController;
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) {
        QSTileUserAction qSTileUserAction = qSTileInput.action;
        if (!(qSTileUserAction instanceof QSTileUserAction.Click)) {
            boolean z = qSTileUserAction instanceof QSTileUserAction.ToggleClick;
        } else if (!ActivityManager.isUserAMonkey()) {
            if (qSTileInput.data instanceof FlashlightTileModel.FlashlightAvailable) {
                ((FlashlightControllerImpl) this.flashlightController).setFlashlight(!((FlashlightTileModel.FlashlightAvailable) r2).isEnabled);
            }
        }
        return Unit.INSTANCE;
    }
}
