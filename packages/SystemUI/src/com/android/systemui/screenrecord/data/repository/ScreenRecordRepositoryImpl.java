package com.android.systemui.screenrecord.data.repository;

import android.app.PendingIntent;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* loaded from: classes2.dex */
public final class ScreenRecordRepositoryImpl implements ScreenRecordRepository {
    public final CoroutineContext bgCoroutineContext;
    public final RecordingController recordingController;
    public final Flow screenRecordState;

    /* renamed from: com.android.systemui.screenrecord.data.repository.ScreenRecordRepositoryImpl$stopRecording$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $stopReason;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(int i, Continuation continuation) {
            super(2, continuation);
            this.$stopReason = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ScreenRecordRepositoryImpl.this.new AnonymousClass2(this.$stopReason, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws PendingIntent.CanceledException {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ScreenRecordRepositoryImpl.this.recordingController.stopRecording(this.$stopReason);
            return Unit.INSTANCE;
        }
    }

    public ScreenRecordRepositoryImpl(CoroutineContext coroutineContext, RecordingController recordingController) {
        this.bgCoroutineContext = coroutineContext;
        this.recordingController = recordingController;
        this.screenRecordState = FlowKt.flowOn(FlowKt.distinctUntilChanged(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new ScreenRecordRepositoryImpl$screenRecordState$2(this, null), FlowConflatedKt.conflatedCallbackFlow(new ScreenRecordRepositoryImpl$screenRecordState$1(this, null)))), coroutineContext);
    }

    public final Object stopRecording(int i, SuspendLambda suspendLambda) throws Throwable {
        Object objWithContext = BuildersKt.withContext(this.bgCoroutineContext, new AnonymousClass2(i, null), suspendLambda);
        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
    }
}
