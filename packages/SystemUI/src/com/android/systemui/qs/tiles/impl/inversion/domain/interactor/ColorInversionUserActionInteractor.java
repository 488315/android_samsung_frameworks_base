package com.android.systemui.qs.tiles.impl.inversion.domain.interactor;

import android.content.Intent;
import com.android.systemui.accessibility.data.repository.ColorInversionRepository;
import com.android.systemui.accessibility.data.repository.ColorInversionRepositoryImpl;
import com.android.systemui.qs.shared.QSSettingsPackageRepository;
import com.android.systemui.qs.tiles.base.domain.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.base.domain.model.QSTileInput;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import com.android.systemui.qs.tiles.impl.inversion.domain.model.ColorInversionTileModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ColorInversionUserActionInteractor implements QSTileUserActionInteractor {
    public final ColorInversionRepository colorInversionRepository;
    public final QSTileIntentUserInputHandler qsTileIntentUserActionHandler;
    public final QSSettingsPackageRepository settingsPackageRepository;

    public ColorInversionUserActionInteractor(ColorInversionRepository colorInversionRepository, QSTileIntentUserInputHandler qSTileIntentUserInputHandler, QSSettingsPackageRepository qSSettingsPackageRepository) {
        this.colorInversionRepository = colorInversionRepository;
        this.qsTileIntentUserActionHandler = qSTileIntentUserInputHandler;
        this.settingsPackageRepository = qSSettingsPackageRepository;
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) {
        QSTileUserAction qSTileUserAction = qSTileInput.action;
        if (qSTileUserAction instanceof QSTileUserAction.Click) {
            Object isEnabled = ((ColorInversionRepositoryImpl) this.colorInversionRepository).setIsEnabled(qSTileInput.user, continuation, !((ColorInversionTileModel) qSTileInput.data).isEnabled);
            if (isEnabled == CoroutineSingletons.COROUTINE_SUSPENDED) {
                return isEnabled;
            }
        } else if (qSTileUserAction instanceof QSTileUserAction.LongClick) {
            QSTileIntentUserInputHandler.handle$default(this.qsTileIntentUserActionHandler, ((QSTileUserAction.LongClick) qSTileUserAction).expandable, new Intent("android.settings.COLOR_INVERSION_SETTINGS").setPackage(this.settingsPackageRepository.getSettingsPackageName()));
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
