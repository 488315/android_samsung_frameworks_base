package com.android.systemui.qs.tiles.impl.inversion.domain.interactor;

import android.content.Intent;
import com.android.systemui.accessibility.data.repository.ColorInversionRepository;
import com.android.systemui.accessibility.data.repository.ColorInversionRepositoryImpl;
import com.android.systemui.qs.tiles.base.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.interactor.QSTileInput;
import com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.impl.inversion.domain.model.ColorInversionTileModel;
import com.android.systemui.qs.tiles.viewmodel.QSTileUserAction;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ColorInversionUserActionInteractor implements QSTileUserActionInteractor {
    public final ColorInversionRepository colorInversionRepository;
    public final QSTileIntentUserInputHandler qsTileIntentUserActionHandler;

    public ColorInversionUserActionInteractor(ColorInversionRepository colorInversionRepository, QSTileIntentUserInputHandler qSTileIntentUserInputHandler) {
        this.colorInversionRepository = colorInversionRepository;
        this.qsTileIntentUserActionHandler = qSTileIntentUserInputHandler;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) {
        QSTileUserAction qSTileUserAction = qSTileInput.action;
        if (qSTileUserAction instanceof QSTileUserAction.Click) {
            Object isEnabled = ((ColorInversionRepositoryImpl) this.colorInversionRepository).setIsEnabled(qSTileInput.user, continuation, !((ColorInversionTileModel) qSTileInput.data).isEnabled);
            if (isEnabled == CoroutineSingletons.COROUTINE_SUSPENDED) {
                return isEnabled;
            }
        } else if (qSTileUserAction instanceof QSTileUserAction.LongClick) {
            QSTileIntentUserInputHandler.handle$default(this.qsTileIntentUserActionHandler, ((QSTileUserAction.LongClick) qSTileUserAction).expandable, new Intent("android.settings.COLOR_INVERSION_SETTINGS"));
        }
        return Unit.INSTANCE;
    }
}
