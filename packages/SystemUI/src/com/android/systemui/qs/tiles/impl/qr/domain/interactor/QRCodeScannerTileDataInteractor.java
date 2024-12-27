package com.android.systemui.qs.tiles.impl.qr.domain.interactor;

import android.os.UserHandle;
import com.android.systemui.qrcodescanner.controller.QRCodeScannerController;
import com.android.systemui.qs.tiles.base.interactor.QSTileDataInteractor;
import com.android.systemui.qs.tiles.impl.qr.domain.model.QRCodeScannerTileModel;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QRCodeScannerTileDataInteractor implements QSTileDataInteractor {
    public final CoroutineContext bgCoroutineContext;
    public final QRCodeScannerController qrController;
    public final CoroutineScope scope;

    public QRCodeScannerTileDataInteractor(CoroutineContext coroutineContext, CoroutineScope coroutineScope, QRCodeScannerController qRCodeScannerController) {
        this.bgCoroutineContext = coroutineContext;
        this.scope = coroutineScope;
        this.qrController = qRCodeScannerController;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileAvailabilityInteractor
    public final Flow availability(UserHandle userHandle) {
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.valueOf(this.qrController.isCameraAvailable()));
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileDataInteractor
    public final Flow tileData(UserHandle userHandle, ReadonlySharedFlow readonlySharedFlow) {
        return FlowKt.stateIn(FlowKt.flowOn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new QRCodeScannerTileDataInteractor$tileData$2(this, null), FlowConflatedKt.conflatedCallbackFlow(new QRCodeScannerTileDataInteractor$tileData$1(this, null))), this.bgCoroutineContext), this.scope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), QRCodeScannerTileModel.TemporarilyUnavailable.INSTANCE);
    }
}
