package com.android.systemui.volume.panel.component.spatial.domain.interactor;

import android.media.AudioDeviceAttributes;
import com.android.settingslib.media.data.repository.SpatializerRepositoryImpl;
import com.android.settingslib.media.domain.interactor.SpatializerInteractor;
import com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioAvailabilityModel;
import com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioEnabledModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* loaded from: classes3.dex */
final class SpatialAudioComponentInteractor$isEnabled$2 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ SpatialAudioComponentInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SpatialAudioComponentInteractor$isEnabled$2(SpatialAudioComponentInteractor spatialAudioComponentInteractor, Continuation continuation) {
        super(4, continuation);
        this.this$0 = spatialAudioComponentInteractor;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        SpatialAudioComponentInteractor$isEnabled$2 spatialAudioComponentInteractor$isEnabled$2 = new SpatialAudioComponentInteractor$isEnabled$2(this.this$0, (Continuation) obj4);
        spatialAudioComponentInteractor$isEnabled$2.L$0 = (AudioDeviceAttributes) obj2;
        spatialAudioComponentInteractor$isEnabled$2.L$1 = (SpatialAudioAvailabilityModel) obj3;
        return spatialAudioComponentInteractor$isEnabled$2.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0060, code lost:
    
        if (r5 == r0) goto L24;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        AudioDeviceAttributes audioDeviceAttributes;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            audioDeviceAttributes = (AudioDeviceAttributes) this.L$0;
            if (((SpatialAudioAvailabilityModel) this.L$1) instanceof SpatialAudioAvailabilityModel.Unavailable) {
                return SpatialAudioEnabledModel.Disabled.INSTANCE;
            }
            if (audioDeviceAttributes == null) {
                return SpatialAudioEnabledModel.Disabled.INSTANCE;
            }
            SpatializerInteractor spatializerInteractor = this.this$0.spatializerInteractor;
            this.L$0 = audioDeviceAttributes;
            this.label = 1;
            obj = spatializerInteractor.isSpatialAudioEnabled(audioDeviceAttributes, this);
            if (obj != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return ((Boolean) obj).booleanValue() ? SpatialAudioEnabledModel.HeadTrackingEnabled.INSTANCE : SpatialAudioEnabledModel.SpatialAudioEnabled.Companion;
        }
        audioDeviceAttributes = (AudioDeviceAttributes) this.L$0;
        ResultKt.throwOnFailure(obj);
        if (!((Boolean) obj).booleanValue()) {
            return SpatialAudioEnabledModel.Disabled.INSTANCE;
        }
        SpatializerInteractor spatializerInteractor2 = this.this$0.spatializerInteractor;
        this.L$0 = null;
        this.label = 2;
        obj = ((SpatializerRepositoryImpl) spatializerInteractor2.repository).isHeadTrackingEnabled(audioDeviceAttributes, this);
    }
}
