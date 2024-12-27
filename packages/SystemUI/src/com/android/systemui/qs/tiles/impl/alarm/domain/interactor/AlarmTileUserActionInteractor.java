package com.android.systemui.qs.tiles.impl.alarm.domain.interactor;

import android.app.PendingIntent;
import android.content.Intent;
import com.android.systemui.animation.Expandable;
import com.android.systemui.qs.tiles.base.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.actions.QSTileIntentUserInputHandlerImpl;
import com.android.systemui.qs.tiles.base.interactor.QSTileInput;
import com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.impl.alarm.domain.model.AlarmTileModel;
import com.android.systemui.qs.tiles.viewmodel.QSTileUserAction;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;

public final class AlarmTileUserActionInteractor implements QSTileUserActionInteractor {
    public final QSTileIntentUserInputHandler inputHandler;

    public AlarmTileUserActionInteractor(QSTileIntentUserInputHandler qSTileIntentUserInputHandler) {
        this.inputHandler = qSTileIntentUserInputHandler;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) {
        QSTileUserAction qSTileUserAction = qSTileInput.action;
        if (qSTileUserAction instanceof QSTileUserAction.Click) {
            Object obj = qSTileInput.data;
            boolean z = obj instanceof AlarmTileModel.NextAlarmSet;
            QSTileIntentUserInputHandler qSTileIntentUserInputHandler = this.inputHandler;
            if (z) {
                AlarmTileModel.NextAlarmSet nextAlarmSet = (AlarmTileModel.NextAlarmSet) obj;
                if (nextAlarmSet.alarmClockInfo.getShowIntent() != null) {
                    PendingIntent showIntent = nextAlarmSet.alarmClockInfo.getShowIntent();
                    Expandable expandable = qSTileUserAction.getExpandable();
                    Intrinsics.checkNotNull(showIntent);
                    ((QSTileIntentUserInputHandlerImpl) qSTileIntentUserInputHandler).handle(expandable, showIntent, true);
                }
            }
            QSTileIntentUserInputHandler.handle$default(qSTileIntentUserInputHandler, qSTileUserAction.getExpandable(), new Intent("android.intent.action.SHOW_ALARMS"));
        } else {
            boolean z2 = qSTileUserAction instanceof QSTileUserAction.LongClick;
        }
        return Unit.INSTANCE;
    }
}
