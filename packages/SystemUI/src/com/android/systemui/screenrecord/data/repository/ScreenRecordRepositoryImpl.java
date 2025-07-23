package com.android.systemui.screenrecord.data.repository;

import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ScreenRecordRepositoryImpl implements ScreenRecordRepository {
    public final CoroutineContext bgCoroutineContext;
    public final RecordingController recordingController;
    public final Flow screenRecordState;

    public ScreenRecordRepositoryImpl(CoroutineContext coroutineContext, RecordingController recordingController) {
        this.bgCoroutineContext = coroutineContext;
        this.recordingController = recordingController;
        this.screenRecordState = FlowKt.flowOn(FlowKt.distinctUntilChanged(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new ScreenRecordRepositoryImpl$screenRecordState$2(this, null), FlowConflatedKt.conflatedCallbackFlow(new ScreenRecordRepositoryImpl$screenRecordState$1(this, null)))), coroutineContext);
    }

    public final Object stopRecording(int i, SuspendLambda suspendLambda) {
        Object withContext = BuildersKt.withContext(this.bgCoroutineContext, new ScreenRecordRepositoryImpl$stopRecording$2(this, i, null), suspendLambda);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }
}
