package com.android.systemui.qs.tiles.impl.reducebrightness.domain.interactor;

import android.R;
import android.content.Intent;
import android.content.res.Resources;
import com.android.systemui.accessibility.extradim.ExtraDimDialogManager;
import com.android.systemui.qs.ReduceBrightColorsController;
import com.android.systemui.qs.ReduceBrightColorsControllerImpl;
import com.android.systemui.qs.tiles.base.domain.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.base.domain.model.QSTileInput;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import com.android.systemui.qs.tiles.impl.reducebrightness.domain.model.ReduceBrightColorsTileModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* loaded from: classes2.dex */
public final class ReduceBrightColorsTileUserActionInteractor implements QSTileUserActionInteractor {
    public final ExtraDimDialogManager extraDimDialogManager;
    public final boolean isInUpgradeMode;
    public final QSTileIntentUserInputHandler qsTileIntentUserActionHandler;
    public final ReduceBrightColorsController reduceBrightColorsController;

    public ReduceBrightColorsTileUserActionInteractor(Resources resources, QSTileIntentUserInputHandler qSTileIntentUserInputHandler, ReduceBrightColorsController reduceBrightColorsController, ExtraDimDialogManager extraDimDialogManager) {
        this.qsTileIntentUserActionHandler = qSTileIntentUserInputHandler;
        this.reduceBrightColorsController = reduceBrightColorsController;
        this.extraDimDialogManager = extraDimDialogManager;
        ((ReduceBrightColorsControllerImpl) reduceBrightColorsController).getClass();
        this.isInUpgradeMode = resources.getBoolean(R.bool.config_isDesktopModeSupported);
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) {
        QSTileUserAction qSTileUserAction = qSTileInput.action;
        boolean z = qSTileUserAction instanceof QSTileUserAction.Click;
        ExtraDimDialogManager extraDimDialogManager = this.extraDimDialogManager;
        boolean z2 = this.isInUpgradeMode;
        if (z) {
            if (z2) {
                extraDimDialogManager.dismissKeyguardIfNeededAndShowDialog(((QSTileUserAction.Click) qSTileUserAction).expandable);
            } else {
                ((ReduceBrightColorsControllerImpl) this.reduceBrightColorsController).mManager.setReduceBrightColorsActivated(!((ReduceBrightColorsTileModel) qSTileInput.data).isEnabled);
            }
        } else if (qSTileUserAction instanceof QSTileUserAction.LongClick) {
            if (z2) {
                extraDimDialogManager.dismissKeyguardIfNeededAndShowDialog(((QSTileUserAction.LongClick) qSTileUserAction).expandable);
            } else {
                QSTileIntentUserInputHandler.handle$default(this.qsTileIntentUserActionHandler, ((QSTileUserAction.LongClick) qSTileUserAction).expandable, new Intent("android.settings.REDUCE_BRIGHT_COLORS_SETTINGS"));
            }
        } else if (!(qSTileUserAction instanceof QSTileUserAction.ToggleClick)) {
            throw new NoWhenBranchMatchedException();
        }
        return Unit.INSTANCE;
    }
}
