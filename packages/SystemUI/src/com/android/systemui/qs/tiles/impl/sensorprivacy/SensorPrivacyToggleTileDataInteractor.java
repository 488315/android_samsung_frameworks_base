package com.android.systemui.qs.tiles.impl.sensorprivacy;

import android.os.UserHandle;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.qs.tiles.base.interactor.QSTileDataInteractor;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SensorPrivacyToggleTileDataInteractor implements QSTileDataInteractor {
    public final CoroutineContext bgCoroutineContext;
    public final IndividualSensorPrivacyController privacyController;
    public final int sensorId;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public SensorPrivacyToggleTileDataInteractor(CoroutineContext coroutineContext, IndividualSensorPrivacyController individualSensorPrivacyController, int i) {
        this.bgCoroutineContext = coroutineContext;
        this.privacyController = individualSensorPrivacyController;
        this.sensorId = i;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileAvailabilityInteractor
    public final Flow availability(UserHandle userHandle) {
        return FlowKt.flowOn(new SafeFlow(new SensorPrivacyToggleTileDataInteractor$availability$1(this, null)), this.bgCoroutineContext);
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileDataInteractor
    public final Flow tileData(UserHandle userHandle, ReadonlySharedFlow readonlySharedFlow) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        SensorPrivacyToggleTileDataInteractor$tileData$1 sensorPrivacyToggleTileDataInteractor$tileData$1 = new SensorPrivacyToggleTileDataInteractor$tileData$1(this, null);
        conflatedCallbackFlow.getClass();
        return FlowKt.flowOn(FlowKt.distinctUntilChanged(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new SensorPrivacyToggleTileDataInteractor$tileData$2(this, null), FlowConflatedKt.conflatedCallbackFlow(sensorPrivacyToggleTileDataInteractor$tileData$1))), this.bgCoroutineContext);
    }
}
