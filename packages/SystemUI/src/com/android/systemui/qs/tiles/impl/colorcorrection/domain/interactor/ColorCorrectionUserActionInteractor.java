package com.android.systemui.qs.tiles.impl.colorcorrection.domain.interactor;

import android.content.Intent;
import com.android.systemui.accessibility.data.repository.ColorCorrectionRepository;
import com.android.systemui.accessibility.data.repository.ColorCorrectionRepositoryImpl;
import com.android.systemui.qs.tiles.base.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.interactor.QSTileInput;
import com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.impl.colorcorrection.domain.model.ColorCorrectionTileModel;
import com.android.systemui.qs.tiles.viewmodel.QSTileUserAction;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ColorCorrectionUserActionInteractor implements QSTileUserActionInteractor {
    public final ColorCorrectionRepository colorCorrectionRepository;
    public final QSTileIntentUserInputHandler qsTileIntentUserActionHandler;

    public ColorCorrectionUserActionInteractor(ColorCorrectionRepository colorCorrectionRepository, QSTileIntentUserInputHandler qSTileIntentUserInputHandler) {
        this.colorCorrectionRepository = colorCorrectionRepository;
        this.qsTileIntentUserActionHandler = qSTileIntentUserInputHandler;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) {
        QSTileUserAction qSTileUserAction = qSTileInput.action;
        if (qSTileUserAction instanceof QSTileUserAction.Click) {
            Object isEnabled = ((ColorCorrectionRepositoryImpl) this.colorCorrectionRepository).setIsEnabled(qSTileInput.user, continuation, !((ColorCorrectionTileModel) qSTileInput.data).isEnabled);
            if (isEnabled == CoroutineSingletons.COROUTINE_SUSPENDED) {
                return isEnabled;
            }
        } else if (qSTileUserAction instanceof QSTileUserAction.LongClick) {
            QSTileIntentUserInputHandler.handle$default(this.qsTileIntentUserActionHandler, ((QSTileUserAction.LongClick) qSTileUserAction).expandable, new Intent("com.android.settings.ACCESSIBILITY_COLOR_SPACE_SETTINGS"));
        }
        return Unit.INSTANCE;
    }
}
