package com.android.settingslib.media.domain.interactor;

import android.media.AudioDeviceAttributes;
import com.android.settingslib.media.data.repository.SpatializerRepository;
import com.android.settingslib.media.data.repository.SpatializerRepositoryImpl;
import java.util.Collection;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes.dex */
public final class SpatializerInteractor {
    public final SpatializerRepository repository;

    /* renamed from: com.android.settingslib.media.domain.interactor.SpatializerInteractor$isSpatialAudioEnabled$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return SpatializerInteractor.this.isSpatialAudioEnabled(null, this);
        }
    }

    public SpatializerInteractor(SpatializerRepository spatializerRepository) {
        this.repository = spatializerRepository;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object isSpatialAudioEnabled(AudioDeviceAttributes audioDeviceAttributes, ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object spatialAudioCompatibleDevices = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(spatialAudioCompatibleDevices);
            anonymousClass1.L$0 = audioDeviceAttributes;
            anonymousClass1.label = 1;
            spatialAudioCompatibleDevices = ((SpatializerRepositoryImpl) this.repository).getSpatialAudioCompatibleDevices(anonymousClass1);
            if (spatialAudioCompatibleDevices == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            audioDeviceAttributes = (AudioDeviceAttributes) anonymousClass1.L$0;
            ResultKt.throwOnFailure(spatialAudioCompatibleDevices);
        }
        return Boolean.valueOf(((Collection) spatialAudioCompatibleDevices).contains(audioDeviceAttributes));
    }
}
