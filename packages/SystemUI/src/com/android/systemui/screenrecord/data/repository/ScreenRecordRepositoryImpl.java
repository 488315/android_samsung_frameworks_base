package com.android.systemui.screenrecord.data.repository;

import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ScreenRecordRepositoryImpl implements ScreenRecordRepository {
    public final CoroutineContext bgCoroutineContext;
    public final RecordingController recordingController;
    public final Flow screenRecordState;

    public ScreenRecordRepositoryImpl(CoroutineContext coroutineContext, RecordingController recordingController) {
        this.bgCoroutineContext = coroutineContext;
        this.recordingController = recordingController;
        this.screenRecordState = FlowKt.flowOn(FlowKt.distinctUntilChanged(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new ScreenRecordRepositoryImpl$screenRecordState$2(this, null), FlowConflatedKt.conflatedCallbackFlow(new ScreenRecordRepositoryImpl$screenRecordState$1(this, null)))), coroutineContext);
    }

    public final Object stopRecording(Continuation continuation) {
        Object withContext = BuildersKt.withContext(this.bgCoroutineContext, new ScreenRecordRepositoryImpl$stopRecording$2(this, null), continuation);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }
}
