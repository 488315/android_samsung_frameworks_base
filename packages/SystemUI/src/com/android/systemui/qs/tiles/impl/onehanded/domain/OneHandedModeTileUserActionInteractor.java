package com.android.systemui.qs.tiles.impl.onehanded.domain;

import android.content.Intent;
import com.android.systemui.accessibility.data.repository.OneHandedModeRepository;
import com.android.systemui.accessibility.data.repository.OneHandedModeRepositoryImpl;
import com.android.systemui.qs.tiles.base.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.interactor.QSTileInput;
import com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.impl.onehanded.domain.model.OneHandedModeTileModel;
import com.android.systemui.qs.tiles.viewmodel.QSTileUserAction;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class OneHandedModeTileUserActionInteractor implements QSTileUserActionInteractor {
    public final OneHandedModeRepository oneHandedModeRepository;
    public final QSTileIntentUserInputHandler qsTileIntentUserActionHandler;

    public OneHandedModeTileUserActionInteractor(OneHandedModeRepository oneHandedModeRepository, QSTileIntentUserInputHandler qSTileIntentUserInputHandler) {
        this.oneHandedModeRepository = oneHandedModeRepository;
        this.qsTileIntentUserActionHandler = qSTileIntentUserInputHandler;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) {
        QSTileUserAction qSTileUserAction = qSTileInput.action;
        if (qSTileUserAction instanceof QSTileUserAction.Click) {
            Object isEnabled = ((OneHandedModeRepositoryImpl) this.oneHandedModeRepository).setIsEnabled(qSTileInput.user, continuation, !((OneHandedModeTileModel) qSTileInput.data).isEnabled);
            if (isEnabled == CoroutineSingletons.COROUTINE_SUSPENDED) {
                return isEnabled;
            }
        } else if (qSTileUserAction instanceof QSTileUserAction.LongClick) {
            QSTileIntentUserInputHandler.handle$default(this.qsTileIntentUserActionHandler, ((QSTileUserAction.LongClick) qSTileUserAction).expandable, new Intent("android.settings.action.ONE_HANDED_SETTINGS"));
        }
        return Unit.INSTANCE;
    }
}
