package com.android.settingslib.media.data.repository;

import android.media.AudioDeviceAttributes;
import android.media.Spatializer;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SpatializerRepositoryImpl implements SpatializerRepository {
    public final CoroutineContext backgroundContext;
    public final Spatializer spatializer;

    public SpatializerRepositoryImpl(Spatializer spatializer, CoroutineContext coroutineContext) {
        this.spatializer = spatializer;
        this.backgroundContext = coroutineContext;
    }

    public final Object addSpatialAudioCompatibleDevice(AudioDeviceAttributes audioDeviceAttributes, Continuation continuation) {
        Object withContext = BuildersKt.withContext(this.backgroundContext, new SpatializerRepositoryImpl$addSpatialAudioCompatibleDevice$2(this, audioDeviceAttributes, null), continuation);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getSpatialAudioCompatibleDevices(kotlin.coroutines.jvm.internal.ContinuationImpl r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.settingslib.media.data.repository.SpatializerRepositoryImpl$getSpatialAudioCompatibleDevices$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.settingslib.media.data.repository.SpatializerRepositoryImpl$getSpatialAudioCompatibleDevices$1 r0 = (com.android.settingslib.media.data.repository.SpatializerRepositoryImpl$getSpatialAudioCompatibleDevices$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settingslib.media.data.repository.SpatializerRepositoryImpl$getSpatialAudioCompatibleDevices$1 r0 = new com.android.settingslib.media.data.repository.SpatializerRepositoryImpl$getSpatialAudioCompatibleDevices$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r5)
            return r5
        L27:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.settingslib.media.data.repository.SpatializerRepositoryImpl$getSpatialAudioCompatibleDevices$2 r5 = new com.android.settingslib.media.data.repository.SpatializerRepositoryImpl$getSpatialAudioCompatibleDevices$2
            r2 = 0
            r5.<init>(r4, r2)
            r0.label = r3
            kotlin.coroutines.CoroutineContext r4 = r4.backgroundContext
            java.lang.Object r4 = kotlinx.coroutines.BuildersKt.withContext(r4, r5, r0)
            if (r4 != r1) goto L43
            return r1
        L43:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.media.data.repository.SpatializerRepositoryImpl.getSpatialAudioCompatibleDevices(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final Object isHeadTrackingAvailableForDevice(AudioDeviceAttributes audioDeviceAttributes, Continuation continuation) {
        return BuildersKt.withContext(this.backgroundContext, new SpatializerRepositoryImpl$isHeadTrackingAvailableForDevice$2(this, audioDeviceAttributes, null), continuation);
    }

    public final Object isHeadTrackingEnabled(AudioDeviceAttributes audioDeviceAttributes, Continuation continuation) {
        return BuildersKt.withContext(this.backgroundContext, new SpatializerRepositoryImpl$isHeadTrackingEnabled$2(this, audioDeviceAttributes, null), continuation);
    }

    public final Object isSpatialAudioAvailableForDevice(AudioDeviceAttributes audioDeviceAttributes, ContinuationImpl continuationImpl) {
        return BuildersKt.withContext(this.backgroundContext, new SpatializerRepositoryImpl$isSpatialAudioAvailableForDevice$2(this, audioDeviceAttributes, null), continuationImpl);
    }

    public final Object removeSpatialAudioCompatibleDevice(AudioDeviceAttributes audioDeviceAttributes, Continuation continuation) {
        Object withContext = BuildersKt.withContext(this.backgroundContext, new SpatializerRepositoryImpl$removeSpatialAudioCompatibleDevice$2(this, audioDeviceAttributes, null), continuation);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }

    public final Object setHeadTrackingEnabled(AudioDeviceAttributes audioDeviceAttributes, boolean z, Continuation continuation) {
        Object withContext = BuildersKt.withContext(this.backgroundContext, new SpatializerRepositoryImpl$setHeadTrackingEnabled$2(this, z, audioDeviceAttributes, null), continuation);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }
}
