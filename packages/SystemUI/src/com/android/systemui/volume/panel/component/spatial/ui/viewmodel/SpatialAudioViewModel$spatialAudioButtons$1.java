package com.android.systemui.volume.panel.component.spatial.ui.viewmodel;

import com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioAvailabilityModel;
import com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioEnabledModel;
import java.util.ArrayList;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class SpatialAudioViewModel$spatialAudioButtons$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ SpatialAudioViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SpatialAudioViewModel$spatialAudioButtons$1(SpatialAudioViewModel spatialAudioViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = spatialAudioViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        SpatialAudioViewModel$spatialAudioButtons$1 spatialAudioViewModel$spatialAudioButtons$1 = new SpatialAudioViewModel$spatialAudioButtons$1(this.this$0, (Continuation) obj3);
        spatialAudioViewModel$spatialAudioButtons$1.L$0 = (SpatialAudioEnabledModel) obj;
        spatialAudioViewModel$spatialAudioButtons$1.L$1 = (SpatialAudioAvailabilityModel) obj2;
        return spatialAudioViewModel$spatialAudioButtons$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SpatialAudioEnabledModel spatialAudioEnabledModel = (SpatialAudioEnabledModel) this.L$0;
        SpatialAudioAvailabilityModel spatialAudioAvailabilityModel = (SpatialAudioAvailabilityModel) this.L$1;
        SpatialAudioEnabledModel.Companion.getClass();
        List list = SpatialAudioEnabledModel.Companion.values;
        ArrayList<SpatialAudioEnabledModel> arrayList = new ArrayList();
        for (Object obj2 : list) {
            if (((SpatialAudioEnabledModel) obj2) instanceof SpatialAudioEnabledModel.HeadTrackingEnabled ? spatialAudioAvailabilityModel instanceof SpatialAudioAvailabilityModel.HeadTracking : true) {
                arrayList.add(obj2);
            }
        }
        SpatialAudioViewModel spatialAudioViewModel = this.this$0;
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
        for (SpatialAudioEnabledModel spatialAudioEnabledModel2 : arrayList) {
            arrayList2.add(new SpatialAudioButtonViewModel(spatialAudioEnabledModel2, SpatialAudioViewModel.access$toViewModel(spatialAudioViewModel, spatialAudioEnabledModel2, Intrinsics.areEqual(spatialAudioEnabledModel2, spatialAudioEnabledModel), spatialAudioAvailabilityModel instanceof SpatialAudioAvailabilityModel.HeadTracking)));
        }
        return arrayList2;
    }
}
