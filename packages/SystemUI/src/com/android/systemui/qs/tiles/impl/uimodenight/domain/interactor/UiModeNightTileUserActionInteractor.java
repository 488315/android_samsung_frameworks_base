package com.android.systemui.qs.tiles.impl.uimodenight.domain.interactor;

import android.app.UiModeManager;
import android.content.Intent;
import com.android.systemui.qs.tiles.base.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.interactor.QSTileInput;
import com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.impl.uimodenight.domain.model.UiModeNightTileModel;
import com.android.systemui.qs.tiles.viewmodel.QSTileUserAction;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class UiModeNightTileUserActionInteractor implements QSTileUserActionInteractor {
    public final CoroutineContext backgroundContext;
    public final QSTileIntentUserInputHandler qsTileIntentUserActionHandler;
    public final UiModeManager uiModeManager;

    public UiModeNightTileUserActionInteractor(CoroutineContext coroutineContext, UiModeManager uiModeManager, QSTileIntentUserInputHandler qSTileIntentUserInputHandler) {
        this.backgroundContext = coroutineContext;
        this.uiModeManager = uiModeManager;
        this.qsTileIntentUserActionHandler = qSTileIntentUserInputHandler;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) {
        QSTileUserAction qSTileUserAction = qSTileInput.action;
        if (qSTileUserAction instanceof QSTileUserAction.Click) {
            if (!((UiModeNightTileModel) qSTileInput.data).isPowerSave) {
                Object withContext = BuildersKt.withContext(this.backgroundContext, new UiModeNightTileUserActionInteractor$handleInput$2$1(this, qSTileInput, null), continuation);
                if (withContext == CoroutineSingletons.COROUTINE_SUSPENDED) {
                    return withContext;
                }
            }
        } else if (qSTileUserAction instanceof QSTileUserAction.LongClick) {
            QSTileIntentUserInputHandler.handle$default(this.qsTileIntentUserActionHandler, ((QSTileUserAction.LongClick) qSTileUserAction).expandable, new Intent("android.settings.DARK_THEME_SETTINGS"));
        }
        return Unit.INSTANCE;
    }
}
