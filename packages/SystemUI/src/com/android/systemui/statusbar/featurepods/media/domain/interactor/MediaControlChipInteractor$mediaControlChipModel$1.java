package com.android.systemui.statusbar.featurepods.media.domain.interactor;

import com.android.systemui.statusbar.featurepods.media.shared.model.MediaControlChipModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class MediaControlChipInteractor$mediaControlChipModel$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public MediaControlChipInteractor$mediaControlChipModel$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        MediaControlChipInteractor$mediaControlChipModel$1 mediaControlChipInteractor$mediaControlChipModel$1 = new MediaControlChipInteractor$mediaControlChipModel$1((Continuation) obj3);
        mediaControlChipInteractor$mediaControlChipModel$1.L$0 = (MediaControlChipModel) obj;
        mediaControlChipInteractor$mediaControlChipModel$1.Z$0 = booleanValue;
        return mediaControlChipInteractor$mediaControlChipModel$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        MediaControlChipModel mediaControlChipModel = (MediaControlChipModel) this.L$0;
        if (this.Z$0) {
            return mediaControlChipModel;
        }
        return null;
    }
}
