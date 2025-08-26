package com.android.systemui.qs.tiles.impl.hearingdevices.domain.interactor;

import android.content.Intent;
import com.android.systemui.accessibility.hearingaid.HearingDevicesDialogManager;
import com.android.systemui.qs.shared.QSSettingsPackageRepository;
import com.android.systemui.qs.tiles.base.domain.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.base.domain.model.QSTileInput;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.BuildersKt;

/* loaded from: classes2.dex */
public final class HearingDevicesTileUserActionInteractor implements QSTileUserActionInteractor {
    public final HearingDevicesDialogManager hearingDevicesDialogManager;
    public final CoroutineContext mainContext;
    public final QSTileIntentUserInputHandler qsTileIntentUserActionHandler;
    public final QSSettingsPackageRepository settingsPackageRepository;

    public HearingDevicesTileUserActionInteractor(CoroutineContext coroutineContext, QSTileIntentUserInputHandler qSTileIntentUserInputHandler, HearingDevicesDialogManager hearingDevicesDialogManager, QSSettingsPackageRepository qSSettingsPackageRepository) {
        this.mainContext = coroutineContext;
        this.qsTileIntentUserActionHandler = qSTileIntentUserInputHandler;
        this.hearingDevicesDialogManager = hearingDevicesDialogManager;
        this.settingsPackageRepository = qSSettingsPackageRepository;
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) throws Throwable {
        QSTileUserAction qSTileUserAction = qSTileInput.action;
        if (qSTileUserAction instanceof QSTileUserAction.Click) {
            Object objWithContext = BuildersKt.withContext(this.mainContext, new HearingDevicesTileUserActionInteractor$handleInput$2$1(this, qSTileInput, null), continuation);
            if (objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED) {
                return objWithContext;
            }
        } else if (qSTileUserAction instanceof QSTileUserAction.LongClick) {
            QSTileIntentUserInputHandler.handle$default(this.qsTileIntentUserActionHandler, ((QSTileUserAction.LongClick) qSTileUserAction).expandable, new Intent("android.settings.HEARING_DEVICES_SETTINGS").setPackage(this.settingsPackageRepository.getSettingsPackageName()));
        } else if (!(qSTileUserAction instanceof QSTileUserAction.ToggleClick)) {
            throw new NoWhenBranchMatchedException();
        }
        return Unit.INSTANCE;
    }
}
