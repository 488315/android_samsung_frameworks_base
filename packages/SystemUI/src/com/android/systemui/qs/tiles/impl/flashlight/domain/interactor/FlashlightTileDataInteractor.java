package com.android.systemui.qs.tiles.impl.flashlight.domain.interactor;

import android.os.UserHandle;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.qs.tiles.base.interactor.QSTileDataInteractor;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.FlashlightControllerImpl;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class FlashlightTileDataInteractor implements QSTileDataInteractor {
    public final FlashlightController flashlightController;

    public FlashlightTileDataInteractor(FlashlightController flashlightController) {
        this.flashlightController = flashlightController;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileAvailabilityInteractor
    public final Flow availability(UserHandle userHandle) {
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.valueOf(((FlashlightControllerImpl) this.flashlightController).mHasFlashlight));
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileDataInteractor
    public final Flow tileData(UserHandle userHandle, ReadonlySharedFlow readonlySharedFlow) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        FlashlightTileDataInteractor$tileData$1 flashlightTileDataInteractor$tileData$1 = new FlashlightTileDataInteractor$tileData$1(this, null);
        conflatedCallbackFlow.getClass();
        return FlowConflatedKt.conflatedCallbackFlow(flashlightTileDataInteractor$tileData$1);
    }
}
