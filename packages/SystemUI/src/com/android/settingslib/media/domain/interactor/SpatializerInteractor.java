package com.android.settingslib.media.domain.interactor;

import com.android.settingslib.media.data.repository.SpatializerRepository;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SpatializerInteractor {
    public final SpatializerRepository repository;

    public SpatializerInteractor(SpatializerRepository spatializerRepository) {
        this.repository = spatializerRepository;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object isSpatialAudioEnabled(android.media.AudioDeviceAttributes r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.settingslib.media.domain.interactor.SpatializerInteractor$isSpatialAudioEnabled$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.settingslib.media.domain.interactor.SpatializerInteractor$isSpatialAudioEnabled$1 r0 = (com.android.settingslib.media.domain.interactor.SpatializerInteractor$isSpatialAudioEnabled$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settingslib.media.domain.interactor.SpatializerInteractor$isSpatialAudioEnabled$1 r0 = new com.android.settingslib.media.domain.interactor.SpatializerInteractor$isSpatialAudioEnabled$1
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L34
            if (r2 != r3) goto L2c
            java.lang.Object r4 = r0.L$0
            r5 = r4
            android.media.AudioDeviceAttributes r5 = (android.media.AudioDeviceAttributes) r5
            kotlin.ResultKt.throwOnFailure(r6)
            goto L46
        L2c:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L34:
            kotlin.ResultKt.throwOnFailure(r6)
            r0.L$0 = r5
            r0.label = r3
            com.android.settingslib.media.data.repository.SpatializerRepository r4 = r4.repository
            com.android.settingslib.media.data.repository.SpatializerRepositoryImpl r4 = (com.android.settingslib.media.data.repository.SpatializerRepositoryImpl) r4
            java.lang.Object r6 = r4.getSpatialAudioCompatibleDevices(r0)
            if (r6 != r1) goto L46
            return r1
        L46:
            java.util.Collection r6 = (java.util.Collection) r6
            boolean r4 = r6.contains(r5)
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.media.domain.interactor.SpatializerInteractor.isSpatialAudioEnabled(android.media.AudioDeviceAttributes, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
