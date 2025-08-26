package com.android.systemui.volume.dialog.ui.viewmodel;

import android.content.Context;
import android.view.View;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.ConfigurationControllerExtKt;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.statusbar.policy.DevicePostureControllerExtKt;
import com.android.systemui.volume.dialog.domain.interactor.VolumeDialogStateInteractor;
import com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor;
import com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor;
import com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSliderType;
import com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSlidersModel;
import java.util.Collection;
import java.util.LinkedHashSet;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes3.dex */
public final class VolumeDialogViewModel {
    public final Context context;
    public final ReadonlyStateFlow dialogTitle;
    public final VolumeDialogVisibilityInteractor dialogVisibilityInteractor;
    public final ReadonlyStateFlow dialogVisibilityModel;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isHalfOpened;
    public final Collection touchableBoundsViews;
    public final VolumeDialogStateInteractor volumeDialogStateInteractor;

    /* renamed from: com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel$addTouchableBounds$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return VolumeDialogViewModel.this.addTouchableBounds(null, this);
        }
    }

    public VolumeDialogViewModel(Context context, CoroutineScope coroutineScope, VolumeDialogVisibilityInteractor volumeDialogVisibilityInteractor, VolumeDialogSlidersInteractor volumeDialogSlidersInteractor, VolumeDialogStateInteractor volumeDialogStateInteractor, DevicePostureController devicePostureController, ConfigurationController configurationController) {
        this.context = context;
        this.dialogVisibilityInteractor = volumeDialogVisibilityInteractor;
        this.volumeDialogStateInteractor = volumeDialogStateInteractor;
        this.isHalfOpened = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(DevicePostureControllerExtKt.devicePosture(devicePostureController), new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new VolumeDialogViewModel$isHalfOpened$1(this, null), ConfigurationControllerExtKt.getOnConfigChanged(configurationController)), new VolumeDialogViewModel$isHalfOpened$2(this, null));
        this.dialogVisibilityModel = volumeDialogVisibilityInteractor.dialogVisibility;
        ReadonlyStateFlow readonlyStateFlow = volumeDialogStateInteractor.volumeDialogState;
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = volumeDialogSlidersInteractor.sliders;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow, new Flow() { // from class: com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
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
                        VolumeDialogSliderType volumeDialogSliderType = ((VolumeDialogSlidersModel) obj).slider;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(volumeDialogSliderType, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, new VolumeDialogViewModel$dialogTitle$2(this, null));
        SharingStarted.Companion.getClass();
        this.dialogTitle = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, SharingStarted.Companion.Eagerly, "");
        this.touchableBoundsViews = new LinkedHashSet();
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final CoroutineSingletons addTouchableBounds(final View[] viewArr, ContinuationImpl continuationImpl) {
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
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            anonymousClass1.L$0 = this;
            anonymousClass1.L$1 = viewArr;
            anonymousClass1.label = 1;
            CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(anonymousClass1), 1);
            cancellableContinuationImpl.initCancellability();
            CollectionsKt__MutableCollectionsKt.addAll(this.touchableBoundsViews, viewArr);
            cancellableContinuationImpl.invokeOnCancellation(new Function1() { // from class: com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel$addTouchableBounds$2$1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    this.this$0.touchableBoundsViews.removeAll(ArraysKt___ArraysKt.toSet(viewArr));
                    return Unit.INSTANCE;
                }
            });
            if (cancellableContinuationImpl.getResult() == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
