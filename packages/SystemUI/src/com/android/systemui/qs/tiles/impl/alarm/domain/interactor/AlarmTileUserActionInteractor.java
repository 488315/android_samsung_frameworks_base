package com.android.systemui.qs.tiles.impl.alarm.domain.interactor;

import android.app.PendingIntent;
import android.content.Intent;
import com.android.systemui.animation.Expandable;
import com.android.systemui.qs.tiles.base.domain.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.domain.actions.QSTileIntentUserInputHandlerImpl;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.base.domain.model.QSTileInput;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import com.android.systemui.qs.tiles.impl.alarm.domain.model.AlarmTileModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* loaded from: classes2.dex */
public final class AlarmTileUserActionInteractor implements QSTileUserActionInteractor {
    public final QSTileIntentUserInputHandler inputHandler;

    public AlarmTileUserActionInteractor(QSTileIntentUserInputHandler qSTileIntentUserInputHandler) {
        this.inputHandler = qSTileIntentUserInputHandler;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x002c  */
    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
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
                    Expandable expandable = ((QSTileUserAction.Click) qSTileUserAction).expandable;
                    showIntent.getClass();
                    ((QSTileIntentUserInputHandlerImpl) qSTileIntentUserInputHandler).handle(expandable, showIntent, true);
                } else {
                    QSTileIntentUserInputHandler.handle$default(qSTileIntentUserInputHandler, ((QSTileUserAction.Click) qSTileUserAction).expandable, new Intent("android.intent.action.SHOW_ALARMS"));
                }
            }
        } else if (!(qSTileUserAction instanceof QSTileUserAction.LongClick) && !(qSTileUserAction instanceof QSTileUserAction.ToggleClick)) {
            throw new NoWhenBranchMatchedException();
        }
        return Unit.INSTANCE;
    }
}
