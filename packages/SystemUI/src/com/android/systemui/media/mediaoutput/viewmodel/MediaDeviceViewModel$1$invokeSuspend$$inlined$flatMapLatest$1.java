package com.android.systemui.media.mediaoutput.viewmodel;

import androidx.datastore.core.DataStore;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$9;
import com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel;
import com.samsung.android.smartthingsmediasdk.mediasdk.SmartThingsMediaSdkManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* loaded from: classes2.dex */
public final class MediaDeviceViewModel$1$invokeSuspend$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ MediaDeviceViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaDeviceViewModel$1$invokeSuspend$$inlined$flatMapLatest$1(Continuation continuation, MediaDeviceViewModel mediaDeviceViewModel) {
        super(3, continuation);
        this.this$0 = mediaDeviceViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MediaDeviceViewModel$1$invokeSuspend$$inlined$flatMapLatest$1 mediaDeviceViewModel$1$invokeSuspend$$inlined$flatMapLatest$1 = new MediaDeviceViewModel$1$invokeSuspend$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        mediaDeviceViewModel$1$invokeSuspend$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        mediaDeviceViewModel$1$invokeSuspend$$inlined$flatMapLatest$1.L$1 = obj2;
        return mediaDeviceViewModel$1$invokeSuspend$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                MediaDeviceViewModel.Companion companion = MediaDeviceViewModel.Companion;
                SmartThingsMediaSdkManager smartThingsMediaSdkManager = this.this$0.mediaSdkManager;
                companion.getClass();
                Flow flowBuffer$default = FlowKt.buffer$default(FlowKt.callbackFlow(new MediaDeviceViewModel$Companion$mediaChanged$1(smartThingsMediaSdkManager, null)), -1, 2);
                DataStoreDebugLabsExt dataStoreDebugLabsExt = DataStoreDebugLabsExt.INSTANCE;
                DataStore dataStore = this.this$0.dataStore;
                dataStoreDebugLabsExt.getClass();
                flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowBuffer$default, new DataStoreDebugLabsExt$special$$inlined$map$9(dataStore.getData()), new MediaDeviceViewModel$1$1$1(null));
            } else {
                flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = EmptyFlow.INSTANCE;
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowKt__ZipKt$combine$$inlined$unsafeFlow$1, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
