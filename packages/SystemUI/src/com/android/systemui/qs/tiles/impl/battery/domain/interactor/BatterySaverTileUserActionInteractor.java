package com.android.systemui.qs.tiles.impl.battery.domain.interactor;

import android.content.Intent;
import com.android.systemui.qs.tiles.base.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.interactor.QSTileInput;
import com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.impl.battery.domain.model.BatterySaverTileModel;
import com.android.systemui.qs.tiles.viewmodel.QSTileUserAction;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BatterySaverTileUserActionInteractor implements QSTileUserActionInteractor {
    public final BatteryController batteryController;
    public final QSTileIntentUserInputHandler qsTileIntentUserActionHandler;

    public BatterySaverTileUserActionInteractor(QSTileIntentUserInputHandler qSTileIntentUserInputHandler, BatteryController batteryController) {
        this.qsTileIntentUserActionHandler = qSTileIntentUserInputHandler;
        this.batteryController = batteryController;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) {
        QSTileUserAction qSTileUserAction = qSTileInput.action;
        if (qSTileUserAction instanceof QSTileUserAction.Click) {
            if (!((BatterySaverTileModel) qSTileInput.data).isPluggedIn()) {
                ((BatteryControllerImpl) this.batteryController).setPowerSaveMode(!r3.isPowerSaving(), qSTileInput.action.getExpandable());
            }
        } else if (qSTileUserAction instanceof QSTileUserAction.LongClick) {
            QSTileIntentUserInputHandler.handle$default(this.qsTileIntentUserActionHandler, ((QSTileUserAction.LongClick) qSTileUserAction).expandable, new Intent("android.settings.BATTERY_SAVER_SETTINGS"));
        }
        return Unit.INSTANCE;
    }
}
