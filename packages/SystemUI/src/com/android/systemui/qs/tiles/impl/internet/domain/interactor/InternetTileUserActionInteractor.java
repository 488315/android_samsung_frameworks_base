package com.android.systemui.qs.tiles.impl.internet.domain.interactor;

import android.content.Intent;
import com.android.systemui.qs.tiles.base.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.interactor.QSTileInput;
import com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.dialog.InternetDialogManager;
import com.android.systemui.qs.tiles.viewmodel.QSTileUserAction;
import com.android.systemui.statusbar.connectivity.AccessPointController;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.BuildersKt;

public final class InternetTileUserActionInteractor implements QSTileUserActionInteractor {
    public final AccessPointController accessPointController;
    public final InternetDialogManager internetDialogManager;
    public final CoroutineContext mainContext;
    public final QSTileIntentUserInputHandler qsTileIntentUserActionHandler;

    public InternetTileUserActionInteractor(CoroutineContext coroutineContext, InternetDialogManager internetDialogManager, AccessPointController accessPointController, QSTileIntentUserInputHandler qSTileIntentUserInputHandler) {
        this.mainContext = coroutineContext;
        this.internetDialogManager = internetDialogManager;
        this.accessPointController = accessPointController;
        this.qsTileIntentUserActionHandler = qSTileIntentUserInputHandler;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) {
        QSTileUserAction qSTileUserAction = qSTileInput.action;
        if (qSTileUserAction instanceof QSTileUserAction.Click) {
            Object withContext = BuildersKt.withContext(this.mainContext, new InternetTileUserActionInteractor$handleInput$2$1(this, qSTileInput, null), continuation);
            if (withContext == CoroutineSingletons.COROUTINE_SUSPENDED) {
                return withContext;
            }
        } else if (qSTileUserAction instanceof QSTileUserAction.LongClick) {
            QSTileIntentUserInputHandler.handle$default(this.qsTileIntentUserActionHandler, ((QSTileUserAction.LongClick) qSTileUserAction).expandable, new Intent("android.settings.WIFI_SETTINGS"));
        }
        return Unit.INSTANCE;
    }
}
