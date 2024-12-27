package com.android.systemui.qs.tiles.impl.qr.domain.interactor;

import android.content.Intent;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.qs.tiles.base.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.actions.QSTileIntentUserInputHandlerImpl;
import com.android.systemui.qs.tiles.base.interactor.QSTileInput;
import com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.impl.qr.domain.model.QRCodeScannerTileModel;
import com.android.systemui.qs.tiles.viewmodel.QSTileUserAction;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QRCodeScannerTileUserActionInteractor implements QSTileUserActionInteractor {
    public final QSTileIntentUserInputHandler qsTileIntentUserActionHandler;

    public QRCodeScannerTileUserActionInteractor(QSTileIntentUserInputHandler qSTileIntentUserInputHandler) {
        this.qsTileIntentUserActionHandler = qSTileIntentUserInputHandler;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) {
        ActivityTransitionAnimator.Controller controller;
        QSTileUserAction qSTileUserAction = qSTileInput.action;
        if (qSTileUserAction instanceof QSTileUserAction.Click) {
            Object obj = qSTileInput.data;
            QRCodeScannerTileModel qRCodeScannerTileModel = (QRCodeScannerTileModel) obj;
            if (qRCodeScannerTileModel instanceof QRCodeScannerTileModel.Available) {
                Expandable expandable = ((QSTileUserAction.Click) qSTileUserAction).expandable;
                Intent intent = ((QRCodeScannerTileModel.Available) obj).intent;
                QSTileIntentUserInputHandlerImpl qSTileIntentUserInputHandlerImpl = (QSTileIntentUserInputHandlerImpl) this.qsTileIntentUserActionHandler;
                if (expandable != null) {
                    qSTileIntentUserInputHandlerImpl.getClass();
                    controller = expandable.activityTransitionController(32);
                } else {
                    controller = null;
                }
                qSTileIntentUserInputHandlerImpl.activityStarter.startActivity(intent, true, controller, true);
            } else {
                boolean z = qRCodeScannerTileModel instanceof QRCodeScannerTileModel.TemporarilyUnavailable;
            }
        } else {
            boolean z2 = qSTileUserAction instanceof QSTileUserAction.LongClick;
        }
        return Unit.INSTANCE;
    }
}
