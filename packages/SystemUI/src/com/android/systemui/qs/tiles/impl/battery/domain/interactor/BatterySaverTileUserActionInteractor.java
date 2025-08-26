package com.android.systemui.qs.tiles.impl.battery.domain.interactor;

import android.content.Intent;
import com.android.systemui.qs.tiles.base.domain.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.base.domain.model.QSTileInput;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import com.android.systemui.qs.tiles.impl.battery.domain.model.BatterySaverTileModel;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* loaded from: classes2.dex */
public final class BatterySaverTileUserActionInteractor implements QSTileUserActionInteractor {
    public final BatteryController batteryController;
    public final QSTileIntentUserInputHandler qsTileIntentUserActionHandler;

    public BatterySaverTileUserActionInteractor(QSTileIntentUserInputHandler qSTileIntentUserInputHandler, BatteryController batteryController) {
        this.qsTileIntentUserActionHandler = qSTileIntentUserInputHandler;
        this.batteryController = batteryController;
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) {
        QSTileUserAction qSTileUserAction = qSTileInput.action;
        if (qSTileUserAction instanceof QSTileUserAction.Click) {
            if (!((BatterySaverTileModel) qSTileInput.data).isPluggedIn()) {
                ((BatteryControllerImpl) this.batteryController).setPowerSaveMode(!r3.isPowerSaving(), ((QSTileUserAction.Click) qSTileInput.action).expandable);
            }
        } else if (qSTileUserAction instanceof QSTileUserAction.LongClick) {
            QSTileIntentUserInputHandler.handle$default(this.qsTileIntentUserActionHandler, ((QSTileUserAction.LongClick) qSTileUserAction).expandable, new Intent("android.settings.BATTERY_SAVER_SETTINGS"));
        } else if (!(qSTileUserAction instanceof QSTileUserAction.ToggleClick)) {
            throw new NoWhenBranchMatchedException();
        }
        return Unit.INSTANCE;
    }
}
