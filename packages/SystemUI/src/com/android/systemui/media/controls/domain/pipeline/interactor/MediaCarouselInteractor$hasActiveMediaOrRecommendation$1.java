package com.android.systemui.media.controls.domain.pipeline.interactor;

import com.android.internal.logging.InstanceId;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.SmartspaceMediaData;
import java.util.Iterator;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class MediaCarouselInteractor$hasActiveMediaOrRecommendation$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    int label;

    public MediaCarouselInteractor$hasActiveMediaOrRecommendation$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        MediaCarouselInteractor$hasActiveMediaOrRecommendation$1 mediaCarouselInteractor$hasActiveMediaOrRecommendation$1 = new MediaCarouselInteractor$hasActiveMediaOrRecommendation$1((Continuation) obj4);
        mediaCarouselInteractor$hasActiveMediaOrRecommendation$1.L$0 = (Map) obj;
        mediaCarouselInteractor$hasActiveMediaOrRecommendation$1.L$1 = (SmartspaceMediaData) obj2;
        mediaCarouselInteractor$hasActiveMediaOrRecommendation$1.L$2 = (InstanceId) obj3;
        return mediaCarouselInteractor$hasActiveMediaOrRecommendation$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        boolean z;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Map map = (Map) this.L$0;
        SmartspaceMediaData smartspaceMediaData = (SmartspaceMediaData) this.L$1;
        InstanceId instanceId = (InstanceId) this.L$2;
        if (!map.isEmpty()) {
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                if (((MediaData) ((Map.Entry) it.next()).getValue()).active) {
                    break;
                }
            }
        }
        if (!smartspaceMediaData.isActive || (!smartspaceMediaData.isValid() && instanceId == null)) {
            z = false;
            return Boolean.valueOf(z);
        }
        z = true;
        return Boolean.valueOf(z);
    }
}
