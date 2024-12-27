package com.android.systemui.volume.panel.component.spatial.domain.interactor;

import android.media.AudioDeviceAttributes;
import com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioAvailabilityModel;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* JADX WARN: Removed duplicated region for block: B:11:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x006b  */
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
            goto L63
        L10:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L18:
            java.lang.Object r1 = r4.L$0
            android.media.AudioDeviceAttributes r1 = (android.media.AudioDeviceAttributes) r1
            kotlin.ResultKt.throwOnFailure(r5)
            goto L47
        L20:
            kotlin.ResultKt.throwOnFailure(r5)
            java.lang.Object r5 = r4.L$0
            r1 = r5
            android.media.AudioDeviceAttributes r1 = (android.media.AudioDeviceAttributes) r1
            java.lang.Object r5 = r4.L$1
            com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioAvailabilityModel r5 = (com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioAvailabilityModel) r5
            boolean r5 = r5 instanceof com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioAvailabilityModel.Unavailable
            if (r5 == 0) goto L33
            com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioEnabledModel$Disabled r4 = com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioEnabledModel.Disabled.INSTANCE
            return r4
        L33:
            if (r1 != 0) goto L38
            com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioEnabledModel$Disabled r4 = com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioEnabledModel.Disabled.INSTANCE
            return r4
        L38:
            com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor r5 = r4.this$0
            com.android.settingslib.media.domain.interactor.SpatializerInteractor r5 = r5.spatializerInteractor
            r4.L$0 = r1
            r4.label = r3
            java.lang.Object r5 = r5.isSpatialAudioEnabled(r1, r4)
            if (r5 != r0) goto L47
            return r0
        L47:
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 == 0) goto L71
            com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor r5 = r4.this$0
            com.android.settingslib.media.domain.interactor.SpatializerInteractor r5 = r5.spatializerInteractor
            r3 = 0
            r4.L$0 = r3
            r4.label = r2
            com.android.settingslib.media.data.repository.SpatializerRepository r5 = r5.repository
            com.android.settingslib.media.data.repository.SpatializerRepositoryImpl r5 = (com.android.settingslib.media.data.repository.SpatializerRepositoryImpl) r5
            java.lang.Object r5 = r5.isHeadTrackingEnabled(r1, r4)
            if (r5 != r0) goto L63
            return r0
        L63:
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r4 = r5.booleanValue()
            if (r4 == 0) goto L6e
            com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioEnabledModel$HeadTrackingEnabled r4 = com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioEnabledModel.HeadTrackingEnabled.INSTANCE
            return r4
        L6e:
            com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioEnabledModel$SpatialAudioEnabled$Companion r4 = com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioEnabledModel.SpatialAudioEnabled.Companion
            return r4
        L71:
            com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioEnabledModel$Disabled r4 = com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioEnabledModel.Disabled.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor$isEnabled$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
