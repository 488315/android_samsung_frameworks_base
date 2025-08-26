package com.android.systemui.volume.panel.component.spatial.domain.interactor;

import android.media.AudioDeviceAttributes;
import com.android.settingslib.media.data.repository.SpatializerRepositoryImpl;
import com.android.settingslib.media.domain.interactor.SpatializerInteractor;
import com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioAvailabilityModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
final class SpatialAudioComponentInteractor$isAvailable$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ SpatialAudioComponentInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SpatialAudioComponentInteractor$isAvailable$2(SpatialAudioComponentInteractor spatialAudioComponentInteractor, Continuation continuation) {
        super(3, continuation);
        this.this$0 = spatialAudioComponentInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        SpatialAudioComponentInteractor$isAvailable$2 spatialAudioComponentInteractor$isAvailable$2 = new SpatialAudioComponentInteractor$isAvailable$2(this.this$0, (Continuation) obj3);
        spatialAudioComponentInteractor$isAvailable$2.L$0 = (AudioDeviceAttributes) obj;
        return spatialAudioComponentInteractor$isAvailable$2.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0059, code lost:
    
        if (r5 == r0) goto L21;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        AudioDeviceAttributes audioDeviceAttributes;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            audioDeviceAttributes = (AudioDeviceAttributes) this.L$0;
            if (audioDeviceAttributes == null) {
                return SpatialAudioAvailabilityModel.Unavailable.INSTANCE;
            }
            SpatializerInteractor spatializerInteractor = this.this$0.spatializerInteractor;
            this.L$0 = audioDeviceAttributes;
            this.label = 1;
            obj = ((SpatializerRepositoryImpl) spatializerInteractor.repository).isSpatialAudioAvailableForDevice(audioDeviceAttributes, this);
            if (obj != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return ((Boolean) obj).booleanValue() ? SpatialAudioAvailabilityModel.HeadTracking.INSTANCE : SpatialAudioAvailabilityModel.SpatialAudio.Companion;
        }
        audioDeviceAttributes = (AudioDeviceAttributes) this.L$0;
        ResultKt.throwOnFailure(obj);
        if (!((Boolean) obj).booleanValue()) {
            return SpatialAudioAvailabilityModel.Unavailable.INSTANCE;
        }
        SpatializerInteractor spatializerInteractor2 = this.this$0.spatializerInteractor;
        this.L$0 = null;
        this.label = 2;
        obj = ((SpatializerRepositoryImpl) spatializerInteractor2.repository).isHeadTrackingAvailableForDevice(audioDeviceAttributes, this);
    }
}
