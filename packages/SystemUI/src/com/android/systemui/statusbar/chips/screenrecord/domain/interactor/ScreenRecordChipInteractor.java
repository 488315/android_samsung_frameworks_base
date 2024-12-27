package com.android.systemui.statusbar.chips.screenrecord.domain.interactor;

import com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository;
import com.android.systemui.mediaprojection.data.repository.MediaProjectionRepository;
import com.android.systemui.screenrecord.data.repository.ScreenRecordRepository;
import com.android.systemui.screenrecord.data.repository.ScreenRecordRepositoryImpl;
import com.android.systemui.statusbar.chips.screenrecord.domain.model.ScreenRecordChipModel;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ScreenRecordChipInteractor {
    public final CoroutineScope scope;
    public final ScreenRecordRepository screenRecordRepository;
    public final ReadonlyStateFlow screenRecordState;

    public ScreenRecordChipInteractor(CoroutineScope coroutineScope, ScreenRecordRepository screenRecordRepository, MediaProjectionRepository mediaProjectionRepository) {
        this.scope = coroutineScope;
        this.screenRecordRepository = screenRecordRepository;
        this.screenRecordState = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(((ScreenRecordRepositoryImpl) screenRecordRepository).screenRecordState, ((MediaProjectionManagerRepository) mediaProjectionRepository).mediaProjectionState, new ScreenRecordChipInteractor$screenRecordState$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), ScreenRecordChipModel.DoingNothing.INSTANCE);
    }

    public final void stopRecording() {
        BuildersKt.launch$default(this.scope, null, null, new ScreenRecordChipInteractor$stopRecording$1(this, null), 3);
    }
}
