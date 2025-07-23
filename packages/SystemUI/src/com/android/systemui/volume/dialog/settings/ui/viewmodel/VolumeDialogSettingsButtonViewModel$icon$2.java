package com.android.systemui.volume.dialog.settings.ui.viewmodel;

import android.media.session.PlaybackState;
import com.android.systemui.volume.dialog.settings.ui.viewmodel.VolumeDialogSettingsButtonViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class VolumeDialogSettingsButtonViewModel$icon$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public VolumeDialogSettingsButtonViewModel$icon$2(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        VolumeDialogSettingsButtonViewModel$icon$2 volumeDialogSettingsButtonViewModel$icon$2 = new VolumeDialogSettingsButtonViewModel$icon$2((Continuation) obj3);
        volumeDialogSettingsButtonViewModel$icon$2.L$0 = (VolumeDialogSettingsButtonViewModel.PlaybackStates) obj;
        volumeDialogSettingsButtonViewModel$icon$2.L$1 = (PlaybackState) obj2;
        return volumeDialogSettingsButtonViewModel$icon$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        VolumeDialogSettingsButtonViewModel.PlaybackStates playbackStates = (VolumeDialogSettingsButtonViewModel.PlaybackStates) this.L$0;
        PlaybackState playbackState = (PlaybackState) this.L$1;
        boolean isActive = playbackState != null ? playbackState.isActive() : false;
        return (playbackStates == null || playbackState == null || isActive != playbackState.isActive()) ? playbackStates != null ? new VolumeDialogSettingsButtonViewModel.PlaybackStates(Boolean.valueOf(playbackStates.isCurrentActive), isActive) : new VolumeDialogSettingsButtonViewModel.PlaybackStates(null, isActive) : playbackStates;
    }
}
