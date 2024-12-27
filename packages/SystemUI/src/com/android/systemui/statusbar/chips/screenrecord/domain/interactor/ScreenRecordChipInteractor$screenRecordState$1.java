package com.android.systemui.statusbar.chips.screenrecord.domain.interactor;

import com.android.systemui.mediaprojection.data.model.MediaProjectionState;
import com.android.systemui.screenrecord.data.model.ScreenRecordModel;
import com.android.systemui.statusbar.chips.screenrecord.domain.model.ScreenRecordChipModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class ScreenRecordChipInteractor$screenRecordState$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public ScreenRecordChipInteractor$screenRecordState$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ScreenRecordChipInteractor$screenRecordState$1 screenRecordChipInteractor$screenRecordState$1 = new ScreenRecordChipInteractor$screenRecordState$1((Continuation) obj3);
        screenRecordChipInteractor$screenRecordState$1.L$0 = (ScreenRecordModel) obj;
        screenRecordChipInteractor$screenRecordState$1.L$1 = (MediaProjectionState) obj2;
        return screenRecordChipInteractor$screenRecordState$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ScreenRecordModel screenRecordModel = (ScreenRecordModel) this.L$0;
        MediaProjectionState mediaProjectionState = (MediaProjectionState) this.L$1;
        if (screenRecordModel instanceof ScreenRecordModel.DoingNothing) {
            return ScreenRecordChipModel.DoingNothing.INSTANCE;
        }
        if (screenRecordModel instanceof ScreenRecordModel.Starting) {
            return new ScreenRecordChipModel.Starting(((ScreenRecordModel.Starting) screenRecordModel).millisUntilStarted);
        }
        if (screenRecordModel instanceof ScreenRecordModel.Recording) {
            return new ScreenRecordChipModel.Recording(mediaProjectionState instanceof MediaProjectionState.Projecting.SingleTask ? ((MediaProjectionState.Projecting.SingleTask) mediaProjectionState).task : null);
        }
        throw new NoWhenBranchMatchedException();
    }
}
