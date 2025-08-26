package com.android.systemui.volume.panel.component.spatial.domain;

import com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor;
import com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioAvailabilityModel;
import com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioEnabledModel;
import com.android.systemui.volume.panel.domain.ComponentAvailabilityCriteria;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* loaded from: classes3.dex */
public final class SpatialAudioAvailabilityCriteria implements ComponentAvailabilityCriteria {
    public final SpatialAudioComponentInteractor interactor;

    /* renamed from: com.android.systemui.volume.panel.component.spatial.domain.SpatialAudioAvailabilityCriteria$isAvailable$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1((Continuation) obj3);
            anonymousClass1.L$0 = (SpatialAudioAvailabilityModel) obj;
            anonymousClass1.L$1 = (SpatialAudioEnabledModel) obj2;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            SpatialAudioAvailabilityModel spatialAudioAvailabilityModel = (SpatialAudioAvailabilityModel) this.L$0;
            SpatialAudioEnabledModel spatialAudioEnabledModel = (SpatialAudioEnabledModel) this.L$1;
            boolean z = false;
            if ((spatialAudioAvailabilityModel instanceof SpatialAudioAvailabilityModel.SpatialAudio) && !(spatialAudioEnabledModel instanceof SpatialAudioEnabledModel.Unknown)) {
                z = true;
            }
            return Boolean.valueOf(z);
        }
    }

    public SpatialAudioAvailabilityCriteria(SpatialAudioComponentInteractor spatialAudioComponentInteractor) {
        this.interactor = spatialAudioComponentInteractor;
    }

    @Override // com.android.systemui.volume.panel.domain.ComponentAvailabilityCriteria
    public final Flow isAvailable() {
        SpatialAudioComponentInteractor spatialAudioComponentInteractor = this.interactor;
        return new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(spatialAudioComponentInteractor.isAvailable, spatialAudioComponentInteractor.isEnabled, new AnonymousClass1(null));
    }
}
