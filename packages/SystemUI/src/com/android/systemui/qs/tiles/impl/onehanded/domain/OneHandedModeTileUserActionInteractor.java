package com.android.systemui.qs.tiles.impl.onehanded.domain;

import android.content.Intent;
import com.android.systemui.accessibility.data.repository.OneHandedModeRepository;
import com.android.systemui.accessibility.data.repository.OneHandedModeRepositoryImpl;
import com.android.systemui.qs.tiles.base.domain.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.base.domain.model.QSTileInput;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import com.android.systemui.qs.tiles.impl.onehanded.domain.model.OneHandedModeTileModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;

/* loaded from: classes2.dex */
public final class OneHandedModeTileUserActionInteractor implements QSTileUserActionInteractor {
    public final OneHandedModeRepository oneHandedModeRepository;
    public final QSTileIntentUserInputHandler qsTileIntentUserActionHandler;

    public OneHandedModeTileUserActionInteractor(OneHandedModeRepository oneHandedModeRepository, QSTileIntentUserInputHandler qSTileIntentUserInputHandler) {
        this.oneHandedModeRepository = oneHandedModeRepository;
        this.qsTileIntentUserActionHandler = qSTileIntentUserInputHandler;
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) {
        QSTileUserAction qSTileUserAction = qSTileInput.action;
        if (qSTileUserAction instanceof QSTileUserAction.Click) {
            Object isEnabled = ((OneHandedModeRepositoryImpl) this.oneHandedModeRepository).setIsEnabled(qSTileInput.user, continuation, !((OneHandedModeTileModel) qSTileInput.data).isEnabled);
            if (isEnabled == CoroutineSingletons.COROUTINE_SUSPENDED) {
                return isEnabled;
            }
        } else if (qSTileUserAction instanceof QSTileUserAction.LongClick) {
            QSTileIntentUserInputHandler.handle$default(this.qsTileIntentUserActionHandler, ((QSTileUserAction.LongClick) qSTileUserAction).expandable, new Intent("android.settings.action.ONE_HANDED_SETTINGS"));
            Unit unit = Unit.INSTANCE;
        } else {
            if (!(qSTileUserAction instanceof QSTileUserAction.ToggleClick)) {
                throw new NoWhenBranchMatchedException();
            }
            Unit unit2 = Unit.INSTANCE;
        }
        return Unit.INSTANCE;
    }
}
