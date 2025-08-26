package com.android.settingslib.media.data.repository;

import android.media.AudioDeviceAttributes;
import android.media.Spatializer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
public final class SpatializerRepositoryImpl implements SpatializerRepository {
    public final CoroutineContext backgroundContext;
    public final Spatializer spatializer;

    /* renamed from: com.android.settingslib.media.data.repository.SpatializerRepositoryImpl$addSpatialAudioCompatibleDevice$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ AudioDeviceAttributes $audioDeviceAttributes;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(AudioDeviceAttributes audioDeviceAttributes, Continuation continuation) {
            super(2, continuation);
            this.$audioDeviceAttributes = audioDeviceAttributes;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SpatializerRepositoryImpl.this.new AnonymousClass2(this.$audioDeviceAttributes, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            SpatializerRepositoryImpl.this.spatializer.addCompatibleAudioDevice(this.$audioDeviceAttributes);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.settingslib.media.data.repository.SpatializerRepositoryImpl$getSpatialAudioCompatibleDevices$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return SpatializerRepositoryImpl.this.getSpatialAudioCompatibleDevices(this);
        }
    }

    /* renamed from: com.android.settingslib.media.data.repository.SpatializerRepositoryImpl$getSpatialAudioCompatibleDevices$2, reason: invalid class name and case insensitive filesystem */
    final class C07742 extends SuspendLambda implements Function2 {
        int label;

        public C07742(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SpatializerRepositoryImpl.this.new C07742(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C07742) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return SpatializerRepositoryImpl.this.spatializer.getCompatibleAudioDevices();
        }
    }

    /* renamed from: com.android.settingslib.media.data.repository.SpatializerRepositoryImpl$isHeadTrackingAvailableForDevice$2, reason: invalid class name and case insensitive filesystem */
    final class C07752 extends SuspendLambda implements Function2 {
        final /* synthetic */ AudioDeviceAttributes $audioDeviceAttributes;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C07752(AudioDeviceAttributes audioDeviceAttributes, Continuation continuation) {
            super(2, continuation);
            this.$audioDeviceAttributes = audioDeviceAttributes;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SpatializerRepositoryImpl.this.new C07752(this.$audioDeviceAttributes, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C07752) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boolean.valueOf(SpatializerRepositoryImpl.this.spatializer.hasHeadTracker(this.$audioDeviceAttributes));
        }
    }

    /* renamed from: com.android.settingslib.media.data.repository.SpatializerRepositoryImpl$isHeadTrackingEnabled$2, reason: invalid class name and case insensitive filesystem */
    final class C07762 extends SuspendLambda implements Function2 {
        final /* synthetic */ AudioDeviceAttributes $audioDeviceAttributes;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C07762(AudioDeviceAttributes audioDeviceAttributes, Continuation continuation) {
            super(2, continuation);
            this.$audioDeviceAttributes = audioDeviceAttributes;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SpatializerRepositoryImpl.this.new C07762(this.$audioDeviceAttributes, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C07762) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boolean.valueOf(SpatializerRepositoryImpl.this.spatializer.isHeadTrackerEnabled(this.$audioDeviceAttributes));
        }
    }

    /* renamed from: com.android.settingslib.media.data.repository.SpatializerRepositoryImpl$isSpatialAudioAvailableForDevice$2, reason: invalid class name and case insensitive filesystem */
    final class C07772 extends SuspendLambda implements Function2 {
        final /* synthetic */ AudioDeviceAttributes $audioDeviceAttributes;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C07772(AudioDeviceAttributes audioDeviceAttributes, Continuation continuation) {
            super(2, continuation);
            this.$audioDeviceAttributes = audioDeviceAttributes;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SpatializerRepositoryImpl.this.new C07772(this.$audioDeviceAttributes, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C07772) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boolean.valueOf(SpatializerRepositoryImpl.this.spatializer.isAvailableForDevice(this.$audioDeviceAttributes));
        }
    }

    /* renamed from: com.android.settingslib.media.data.repository.SpatializerRepositoryImpl$removeSpatialAudioCompatibleDevice$2, reason: invalid class name and case insensitive filesystem */
    final class C07782 extends SuspendLambda implements Function2 {
        final /* synthetic */ AudioDeviceAttributes $audioDeviceAttributes;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C07782(AudioDeviceAttributes audioDeviceAttributes, Continuation continuation) {
            super(2, continuation);
            this.$audioDeviceAttributes = audioDeviceAttributes;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SpatializerRepositoryImpl.this.new C07782(this.$audioDeviceAttributes, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C07782) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            SpatializerRepositoryImpl.this.spatializer.removeCompatibleAudioDevice(this.$audioDeviceAttributes);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.settingslib.media.data.repository.SpatializerRepositoryImpl$setHeadTrackingEnabled$2, reason: invalid class name and case insensitive filesystem */
    final class C07792 extends SuspendLambda implements Function2 {
        final /* synthetic */ AudioDeviceAttributes $audioDeviceAttributes;
        final /* synthetic */ boolean $isEnabled;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C07792(boolean z, AudioDeviceAttributes audioDeviceAttributes, Continuation continuation) {
            super(2, continuation);
            this.$isEnabled = z;
            this.$audioDeviceAttributes = audioDeviceAttributes;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SpatializerRepositoryImpl.this.new C07792(this.$isEnabled, this.$audioDeviceAttributes, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C07792) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            SpatializerRepositoryImpl.this.spatializer.setHeadTrackerEnabled(this.$isEnabled, this.$audioDeviceAttributes);
            return Unit.INSTANCE;
        }
    }

    public SpatializerRepositoryImpl(Spatializer spatializer, CoroutineContext coroutineContext) {
        this.spatializer = spatializer;
        this.backgroundContext = coroutineContext;
    }

    public final Object addSpatialAudioCompatibleDevice(AudioDeviceAttributes audioDeviceAttributes, Continuation continuation) throws Throwable {
        Object objWithContext = BuildersKt.withContext(this.backgroundContext, new AnonymousClass2(audioDeviceAttributes, null), continuation);
        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getSpatialAudioCompatibleDevices(ContinuationImpl continuationImpl) throws Throwable {
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
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 != 0) {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        ResultKt.throwOnFailure(obj);
        C07742 c07742 = new C07742(null);
        anonymousClass1.label = 1;
        Object objWithContext = BuildersKt.withContext(this.backgroundContext, c07742, anonymousClass1);
        return objWithContext == coroutineSingletons ? coroutineSingletons : objWithContext;
    }

    public final Object isHeadTrackingAvailableForDevice(AudioDeviceAttributes audioDeviceAttributes, Continuation continuation) {
        return BuildersKt.withContext(this.backgroundContext, new C07752(audioDeviceAttributes, null), continuation);
    }

    public final Object isHeadTrackingEnabled(AudioDeviceAttributes audioDeviceAttributes, Continuation continuation) {
        return BuildersKt.withContext(this.backgroundContext, new C07762(audioDeviceAttributes, null), continuation);
    }

    public final Object isSpatialAudioAvailableForDevice(AudioDeviceAttributes audioDeviceAttributes, ContinuationImpl continuationImpl) {
        return BuildersKt.withContext(this.backgroundContext, new C07772(audioDeviceAttributes, null), continuationImpl);
    }

    public final Object removeSpatialAudioCompatibleDevice(AudioDeviceAttributes audioDeviceAttributes, Continuation continuation) throws Throwable {
        Object objWithContext = BuildersKt.withContext(this.backgroundContext, new C07782(audioDeviceAttributes, null), continuation);
        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
    }

    public final Object setHeadTrackingEnabled(AudioDeviceAttributes audioDeviceAttributes, boolean z, Continuation continuation) throws Throwable {
        Object objWithContext = BuildersKt.withContext(this.backgroundContext, new C07792(z, audioDeviceAttributes, null), continuation);
        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
    }
}
