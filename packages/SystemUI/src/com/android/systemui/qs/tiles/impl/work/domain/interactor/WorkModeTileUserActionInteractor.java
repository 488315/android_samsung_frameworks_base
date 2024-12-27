package com.android.systemui.qs.tiles.impl.work.domain.interactor;

import android.content.Intent;
import com.android.systemui.qs.tiles.base.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.interactor.QSTileInput;
import com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.impl.work.domain.model.WorkModeTileModel;
import com.android.systemui.qs.tiles.viewmodel.QSTileUserAction;
import com.android.systemui.statusbar.phone.ManagedProfileController;
import com.android.systemui.statusbar.phone.ManagedProfileControllerImpl;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class WorkModeTileUserActionInteractor implements QSTileUserActionInteractor {
    public final ManagedProfileController profileController;
    public final QSTileIntentUserInputHandler qsTileIntentUserActionHandler;

    public WorkModeTileUserActionInteractor(ManagedProfileController managedProfileController, QSTileIntentUserInputHandler qSTileIntentUserInputHandler) {
        this.profileController = managedProfileController;
        this.qsTileIntentUserActionHandler = qSTileIntentUserInputHandler;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) {
        QSTileUserAction qSTileUserAction = qSTileInput.action;
        boolean z = qSTileUserAction instanceof QSTileUserAction.Click;
        Object obj = qSTileInput.data;
        if (z) {
            if (obj instanceof WorkModeTileModel.HasActiveProfile) {
                ((ManagedProfileControllerImpl) this.profileController).setWorkModeEnabled(!((WorkModeTileModel.HasActiveProfile) obj).isEnabled);
            }
        } else if ((qSTileUserAction instanceof QSTileUserAction.LongClick) && (obj instanceof WorkModeTileModel.HasActiveProfile)) {
            QSTileIntentUserInputHandler.handle$default(this.qsTileIntentUserActionHandler, ((QSTileUserAction.LongClick) qSTileUserAction).expandable, new Intent("android.settings.MANAGED_PROFILE_SETTINGS"));
        }
        return Unit.INSTANCE;
    }
}
