package com.android.systemui.qs.tiles.impl.qr.domain.interactor;

import android.content.Intent;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.qs.tiles.base.domain.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.domain.actions.QSTileIntentUserInputHandlerImpl;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.base.domain.model.QSTileInput;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import com.android.systemui.qs.tiles.impl.qr.domain.model.QRCodeScannerTileModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* loaded from: classes2.dex */
public final class QRCodeScannerTileUserActionInteractor implements QSTileUserActionInteractor {
    public final QSTileIntentUserInputHandler qsTileIntentUserActionHandler;

    public QRCodeScannerTileUserActionInteractor(QSTileIntentUserInputHandler qSTileIntentUserInputHandler) {
        this.qsTileIntentUserActionHandler = qSTileIntentUserInputHandler;
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) {
        ActivityTransitionAnimator.Controller controllerActivityTransitionController;
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
                    controllerActivityTransitionController = expandable.activityTransitionController(32);
                } else {
                    controllerActivityTransitionController = null;
                }
                qSTileIntentUserInputHandlerImpl.activityStarter.startActivity(intent, true, controllerActivityTransitionController, true);
            } else if (!(qRCodeScannerTileModel instanceof QRCodeScannerTileModel.TemporarilyUnavailable)) {
                throw new NoWhenBranchMatchedException();
            }
        } else if (!(qSTileUserAction instanceof QSTileUserAction.LongClick) && !(qSTileUserAction instanceof QSTileUserAction.ToggleClick)) {
            throw new NoWhenBranchMatchedException();
        }
        return Unit.INSTANCE;
    }
}
