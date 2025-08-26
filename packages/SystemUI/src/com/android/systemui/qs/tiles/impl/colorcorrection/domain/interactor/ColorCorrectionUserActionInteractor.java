package com.android.systemui.qs.tiles.impl.colorcorrection.domain.interactor;

import android.content.Intent;
import com.android.systemui.accessibility.data.repository.ColorCorrectionRepository;
import com.android.systemui.accessibility.data.repository.ColorCorrectionRepositoryImpl;
import com.android.systemui.qs.shared.QSSettingsPackageRepository;
import com.android.systemui.qs.tiles.base.domain.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.base.domain.model.QSTileInput;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import com.android.systemui.qs.tiles.impl.colorcorrection.domain.model.ColorCorrectionTileModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;

/* loaded from: classes2.dex */
public final class ColorCorrectionUserActionInteractor implements QSTileUserActionInteractor {
    public final ColorCorrectionRepository colorCorrectionRepository;
    public final QSTileIntentUserInputHandler qsTileIntentUserActionHandler;
    public final QSSettingsPackageRepository settingsPackageRepository;

    public ColorCorrectionUserActionInteractor(ColorCorrectionRepository colorCorrectionRepository, QSTileIntentUserInputHandler qSTileIntentUserInputHandler, QSSettingsPackageRepository qSSettingsPackageRepository) {
        this.colorCorrectionRepository = colorCorrectionRepository;
        this.qsTileIntentUserActionHandler = qSTileIntentUserInputHandler;
        this.settingsPackageRepository = qSSettingsPackageRepository;
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) {
        QSTileUserAction qSTileUserAction = qSTileInput.action;
        if (qSTileUserAction instanceof QSTileUserAction.Click) {
            Object isEnabled = ((ColorCorrectionRepositoryImpl) this.colorCorrectionRepository).setIsEnabled(qSTileInput.user, continuation, !((ColorCorrectionTileModel) qSTileInput.data).isEnabled);
            if (isEnabled == CoroutineSingletons.COROUTINE_SUSPENDED) {
                return isEnabled;
            }
        } else if (qSTileUserAction instanceof QSTileUserAction.LongClick) {
            QSTileIntentUserInputHandler.handle$default(this.qsTileIntentUserActionHandler, ((QSTileUserAction.LongClick) qSTileUserAction).expandable, new Intent("com.android.settings.ACCESSIBILITY_COLOR_SPACE_SETTINGS").setPackage(this.settingsPackageRepository.getSettingsPackageName()));
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
