package com.android.systemui.volume.dialog.domain.interactor;

import com.android.systemui.volume.dialog.shared.model.VolumeDialogVisibilityModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
final class VolumeDialogVisibilityInteractor$dialogVisibility$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ VolumeDialogVisibilityInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogVisibilityInteractor$dialogVisibility$1(VolumeDialogVisibilityInteractor volumeDialogVisibilityInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = volumeDialogVisibilityInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        VolumeDialogVisibilityInteractor$dialogVisibility$1 volumeDialogVisibilityInteractor$dialogVisibility$1 = new VolumeDialogVisibilityInteractor$dialogVisibility$1(this.this$0, continuation);
        volumeDialogVisibilityInteractor$dialogVisibility$1.L$0 = obj;
        return volumeDialogVisibilityInteractor$dialogVisibility$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumeDialogVisibilityInteractor$dialogVisibility$1) create((VolumeDialogVisibilityModel) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.this$0.controller.notifyVisible(((VolumeDialogVisibilityModel) this.L$0) instanceof VolumeDialogVisibilityModel.Visible);
        return Unit.INSTANCE;
    }
}
