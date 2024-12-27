package com.android.systemui.volume.panel.component.spatial.domain.interactor;

import android.media.AudioDeviceAttributes;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

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

    /* JADX WARN: Removed duplicated region for block: B:11:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0064  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r5) {
        /*
            r4 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r4.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L20
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r5)
            goto L5c
        L10:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L18:
            java.lang.Object r1 = r4.L$0
            android.media.AudioDeviceAttributes r1 = (android.media.AudioDeviceAttributes) r1
            kotlin.ResultKt.throwOnFailure(r5)
            goto L40
        L20:
            kotlin.ResultKt.throwOnFailure(r5)
            java.lang.Object r5 = r4.L$0
            r1 = r5
            android.media.AudioDeviceAttributes r1 = (android.media.AudioDeviceAttributes) r1
            if (r1 != 0) goto L2d
            com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioAvailabilityModel$Unavailable r4 = com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioAvailabilityModel.Unavailable.INSTANCE
            return r4
        L2d:
            com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor r5 = r4.this$0
            com.android.settingslib.media.domain.interactor.SpatializerInteractor r5 = r5.spatializerInteractor
            r4.L$0 = r1
            r4.label = r3
            com.android.settingslib.media.data.repository.SpatializerRepository r5 = r5.repository
            com.android.settingslib.media.data.repository.SpatializerRepositoryImpl r5 = (com.android.settingslib.media.data.repository.SpatializerRepositoryImpl) r5
            java.lang.Object r5 = r5.isSpatialAudioAvailableForDevice(r1, r4)
            if (r5 != r0) goto L40
            return r0
        L40:
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 == 0) goto L6a
            com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor r5 = r4.this$0
            com.android.settingslib.media.domain.interactor.SpatializerInteractor r5 = r5.spatializerInteractor
            r3 = 0
            r4.L$0 = r3
            r4.label = r2
            com.android.settingslib.media.data.repository.SpatializerRepository r5 = r5.repository
            com.android.settingslib.media.data.repository.SpatializerRepositoryImpl r5 = (com.android.settingslib.media.data.repository.SpatializerRepositoryImpl) r5
            java.lang.Object r5 = r5.isHeadTrackingAvailableForDevice(r1, r4)
            if (r5 != r0) goto L5c
            return r0
        L5c:
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r4 = r5.booleanValue()
            if (r4 == 0) goto L67
            com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioAvailabilityModel$HeadTracking r4 = com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioAvailabilityModel.HeadTracking.INSTANCE
            return r4
        L67:
            com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioAvailabilityModel$SpatialAudio$Companion r4 = com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioAvailabilityModel.SpatialAudio.Companion
            return r4
        L6a:
            com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioAvailabilityModel$Unavailable r4 = com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioAvailabilityModel.Unavailable.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor$isAvailable$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
