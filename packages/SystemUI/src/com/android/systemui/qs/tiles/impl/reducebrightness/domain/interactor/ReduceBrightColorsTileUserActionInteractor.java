package com.android.systemui.qs.tiles.impl.reducebrightness.domain.interactor;

import android.content.Intent;
import com.android.systemui.qs.ReduceBrightColorsController;
import com.android.systemui.qs.ReduceBrightColorsControllerImpl;
import com.android.systemui.qs.tiles.base.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.interactor.QSTileInput;
import com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.impl.reducebrightness.domain.model.ReduceBrightColorsTileModel;
import com.android.systemui.qs.tiles.viewmodel.QSTileUserAction;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ReduceBrightColorsTileUserActionInteractor implements QSTileUserActionInteractor {
    public final QSTileIntentUserInputHandler qsTileIntentUserActionHandler;
    public final ReduceBrightColorsController reduceBrightColorsController;

    public ReduceBrightColorsTileUserActionInteractor(QSTileIntentUserInputHandler qSTileIntentUserInputHandler, ReduceBrightColorsController reduceBrightColorsController) {
        this.qsTileIntentUserActionHandler = qSTileIntentUserInputHandler;
        this.reduceBrightColorsController = reduceBrightColorsController;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) {
        QSTileUserAction qSTileUserAction = qSTileInput.action;
        if (qSTileUserAction instanceof QSTileUserAction.Click) {
            ((ReduceBrightColorsControllerImpl) this.reduceBrightColorsController).mManager.setReduceBrightColorsActivated(!((ReduceBrightColorsTileModel) qSTileInput.data).isEnabled);
        } else if (qSTileUserAction instanceof QSTileUserAction.LongClick) {
            QSTileIntentUserInputHandler.handle$default(this.qsTileIntentUserActionHandler, ((QSTileUserAction.LongClick) qSTileUserAction).expandable, new Intent("android.settings.REDUCE_BRIGHT_COLORS_SETTINGS"));
        }
        return Unit.INSTANCE;
    }
}
