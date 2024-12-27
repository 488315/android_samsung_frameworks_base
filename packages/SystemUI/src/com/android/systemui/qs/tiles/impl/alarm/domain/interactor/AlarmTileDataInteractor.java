package com.android.systemui.qs.tiles.impl.alarm.domain.interactor;

import android.os.UserHandle;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.qs.tiles.base.interactor.QSTileDataInteractor;
import com.android.systemui.statusbar.policy.NextAlarmController;
import com.android.systemui.util.time.DateFormatUtil;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AlarmTileDataInteractor implements QSTileDataInteractor {
    public final NextAlarmController alarmController;
    public final DateFormatUtil dateFormatUtil;

    public AlarmTileDataInteractor(NextAlarmController nextAlarmController, DateFormatUtil dateFormatUtil) {
        this.alarmController = nextAlarmController;
        this.dateFormatUtil = dateFormatUtil;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileAvailabilityInteractor
    public final Flow availability(UserHandle userHandle) {
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.TRUE);
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileDataInteractor
    public final Flow tileData(UserHandle userHandle, ReadonlySharedFlow readonlySharedFlow) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        AlarmTileDataInteractor$tileData$1 alarmTileDataInteractor$tileData$1 = new AlarmTileDataInteractor$tileData$1(this, null);
        conflatedCallbackFlow.getClass();
        return FlowConflatedKt.conflatedCallbackFlow(alarmTileDataInteractor$tileData$1);
    }
}
