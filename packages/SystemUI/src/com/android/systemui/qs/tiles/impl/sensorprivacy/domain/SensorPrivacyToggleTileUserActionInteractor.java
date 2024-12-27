package com.android.systemui.qs.tiles.impl.sensorprivacy.domain;

import android.content.Intent;
import android.safetycenter.SafetyCenterManager;
import com.android.systemui.animation.Expandable;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.tiles.base.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.interactor.QSTileInput;
import com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.impl.sensorprivacy.domain.model.SensorPrivacyToggleTileModel;
import com.android.systemui.qs.tiles.viewmodel.QSTileUserAction;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyControllerImpl;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SensorPrivacyToggleTileUserActionInteractor implements QSTileUserActionInteractor {
    public final ActivityStarter activityStarter;
    public final KeyguardInteractor keyguardInteractor;
    public Intent longClickIntent;
    public final QSTileIntentUserInputHandler qsTileIntentUserActionHandler;
    public final SafetyCenterManager safetyCenterManager;
    public final int sensorId;
    public final IndividualSensorPrivacyController sensorPrivacyController;

    public SensorPrivacyToggleTileUserActionInteractor(QSTileIntentUserInputHandler qSTileIntentUserInputHandler, KeyguardInteractor keyguardInteractor, ActivityStarter activityStarter, IndividualSensorPrivacyController individualSensorPrivacyController, SafetyCenterManager safetyCenterManager, int i) {
        this.qsTileIntentUserActionHandler = qSTileIntentUserInputHandler;
        this.keyguardInteractor = keyguardInteractor;
        this.activityStarter = activityStarter;
        this.sensorPrivacyController = individualSensorPrivacyController;
        this.safetyCenterManager = safetyCenterManager;
        this.sensorId = i;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) {
        QSTileUserAction qSTileUserAction = qSTileInput.action;
        if (qSTileUserAction instanceof QSTileUserAction.Click) {
            final boolean z = ((SensorPrivacyToggleTileModel) qSTileInput.data).isBlocked;
            IndividualSensorPrivacyControllerImpl individualSensorPrivacyControllerImpl = (IndividualSensorPrivacyControllerImpl) this.sensorPrivacyController;
            if (individualSensorPrivacyControllerImpl.mSensorPrivacyManager.requiresAuthentication()) {
                KeyguardInteractor keyguardInteractor = this.keyguardInteractor;
                if (((Boolean) keyguardInteractor.isKeyguardDismissible.getValue()).booleanValue() && keyguardInteractor.isKeyguardShowing()) {
                    this.activityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.impl.sensorprivacy.domain.SensorPrivacyToggleTileUserActionInteractor$handleInput$2$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            SensorPrivacyToggleTileUserActionInteractor sensorPrivacyToggleTileUserActionInteractor = SensorPrivacyToggleTileUserActionInteractor.this;
                            ((IndividualSensorPrivacyControllerImpl) sensorPrivacyToggleTileUserActionInteractor.sensorPrivacyController).setSensorBlocked(1, sensorPrivacyToggleTileUserActionInteractor.sensorId, !z);
                        }
                    });
                    return Unit.INSTANCE;
                }
            }
            individualSensorPrivacyControllerImpl.setSensorBlocked(1, this.sensorId, !z);
        } else if (qSTileUserAction instanceof QSTileUserAction.LongClick) {
            if (this.longClickIntent == null) {
                this.longClickIntent = new Intent(this.safetyCenterManager.isSafetyCenterEnabled() ? "android.settings.PRIVACY_CONTROLS" : "android.settings.PRIVACY_SETTINGS");
            }
            Expandable expandable = qSTileInput.action.getExpandable();
            Intent intent = this.longClickIntent;
            if (intent == null) {
                intent = null;
            }
            QSTileIntentUserInputHandler.handle$default(this.qsTileIntentUserActionHandler, expandable, intent);
        }
        return Unit.INSTANCE;
    }
}
