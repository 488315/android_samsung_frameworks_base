package com.android.systemui.qs.tiles.impl.uimodenight.domain.interactor;

import android.app.UiModeManager;
import android.content.Intent;
import com.android.systemui.qs.tiles.base.domain.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.base.domain.model.QSTileInput;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import com.android.systemui.qs.tiles.impl.uimodenight.domain.interactor.model.UiModeNightTileModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class UiModeNightTileUserActionInteractor implements QSTileUserActionInteractor {
    public final CoroutineContext backgroundContext;
    public final QSTileIntentUserInputHandler qsTileIntentUserActionHandler;
    public final UiModeManager uiModeManager;

    public UiModeNightTileUserActionInteractor(CoroutineContext coroutineContext, UiModeManager uiModeManager, QSTileIntentUserInputHandler qSTileIntentUserInputHandler) {
        this.backgroundContext = coroutineContext;
        this.uiModeManager = uiModeManager;
        this.qsTileIntentUserActionHandler = qSTileIntentUserInputHandler;
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor
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
        } else if (!(qSTileUserAction instanceof QSTileUserAction.ToggleClick)) {
            throw new NoWhenBranchMatchedException();
        }
        return Unit.INSTANCE;
    }
}
