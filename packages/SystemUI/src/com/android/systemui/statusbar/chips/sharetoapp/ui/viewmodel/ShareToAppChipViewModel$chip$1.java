package com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.statusbar.chips.mediaprojection.domain.model.MediaProjectionStopDialogModel;
import com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class ShareToAppChipViewModel$chip$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ ShareToAppChipViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShareToAppChipViewModel$chip$1(ShareToAppChipViewModel shareToAppChipViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = shareToAppChipViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ShareToAppChipViewModel$chip$1 shareToAppChipViewModel$chip$1 = new ShareToAppChipViewModel$chip$1(this.this$0, (Continuation) obj3);
        shareToAppChipViewModel$chip$1.L$0 = (OngoingActivityChipModel) obj;
        shareToAppChipViewModel$chip$1.L$1 = (MediaProjectionStopDialogModel) obj2;
        return shareToAppChipViewModel$chip$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        OngoingActivityChipModel ongoingActivityChipModel = (OngoingActivityChipModel) this.L$0;
        if (!(((MediaProjectionStopDialogModel) this.L$1) instanceof MediaProjectionStopDialogModel.Shown)) {
            return ongoingActivityChipModel;
        }
        LogBuffer logBuffer = this.this$0.logger;
        logBuffer.commit(logBuffer.obtain(ShareToAppChipViewModel.TAG, LogLevel.INFO, new ShareToAppChipViewModel$$ExternalSyntheticLambda0(2), null));
        return new OngoingActivityChipModel.Inactive(false, null, 3, null);
    }
}
