package com.android.systemui.volume.dialog.sliders.ui.viewmodel;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.volume.dialog.shared.VolumeDialogLogger;
import com.android.systemui.volume.dialog.shared.VolumeDialogLogger$$ExternalSyntheticLambda0;
import com.android.systemui.volume.dialog.sliders.dagger.VolumeDialogSliderComponent;
import com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor;
import com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSliderType;
import com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSlidersModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes3.dex */
public final class VolumeDialogSlidersViewModel {
    public final VolumeDialogSliderComponent.Factory sliderComponentFactory;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 sliders;
    public final VolumeDialogLogger volumeDialogLogger;

    public VolumeDialogSlidersViewModel(CoroutineScope coroutineScope, VolumeDialogSlidersInteractor volumeDialogSlidersInteractor, VolumeDialogSliderComponent.Factory factory, VolumeDialogLogger volumeDialogLogger) {
        this.sliderComponentFactory = factory;
        this.volumeDialogLogger = volumeDialogLogger;
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = volumeDialogSlidersInteractor.sliders;
        Flow flow = new Flow() { // from class: com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSlidersViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSlidersViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ VolumeDialogSlidersViewModel this$0;

                /* renamed from: com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSlidersViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
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
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, VolumeDialogSlidersViewModel volumeDialogSlidersViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = volumeDialogSlidersViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        VolumeDialogSlidersModel volumeDialogSlidersModel = (VolumeDialogSlidersModel) obj;
                        VolumeDialogSlidersViewModel volumeDialogSlidersViewModel = this.this$0;
                        VolumeDialogLogger volumeDialogLogger = volumeDialogSlidersViewModel.volumeDialogLogger;
                        int audioStream = volumeDialogSlidersModel.slider.getAudioStream();
                        List list = volumeDialogSlidersModel.floatingSliders;
                        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            arrayList.add(new Integer(((VolumeDialogSliderType) it.next()).getAudioStream()));
                        }
                        volumeDialogLogger.getClass();
                        LogLevel logLevel = LogLevel.DEBUG;
                        VolumeDialogLogger$$ExternalSyntheticLambda0 volumeDialogLogger$$ExternalSyntheticLambda0 = new VolumeDialogLogger$$ExternalSyntheticLambda0(0);
                        LogBuffer logBuffer = volumeDialogLogger.logBuffer;
                        LogMessage logMessageObtain = logBuffer.obtain("SysUI_VolumeDialog", logLevel, volumeDialogLogger$$ExternalSyntheticLambda0, null);
                        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                        logMessageImpl.int1 = audioStream;
                        logMessageImpl.str1 = CollectionsKt___CollectionsKt.joinToString$default(arrayList, ",", null, null, new VolumeDialogLogger$$ExternalSyntheticLambda0(1), 30);
                        logBuffer.commit(logMessageObtain);
                        VolumeDialogSliderType volumeDialogSliderType = volumeDialogSlidersModel.slider;
                        VolumeDialogSliderComponent.Factory factory = volumeDialogSlidersViewModel.sliderComponentFactory;
                        VolumeDialogSliderComponent volumeDialogSliderComponentCreate = factory.create(volumeDialogSliderType);
                        List list2 = volumeDialogSlidersModel.floatingSliders;
                        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                        Iterator it2 = list2.iterator();
                        while (it2.hasNext()) {
                            arrayList2.add(factory.create((VolumeDialogSliderType) it2.next()));
                        }
                        VolumeDialogSliderUiModel volumeDialogSliderUiModel = new VolumeDialogSliderUiModel(volumeDialogSliderComponentCreate, arrayList2);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(volumeDialogSliderUiModel, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        this.sliders = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.Eagerly, null));
    }
}
