package com.android.systemui.volume.dialog.ui.viewmodel;

import android.content.Context;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.ConfigurationControllerExtKt;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.statusbar.policy.DevicePostureControllerExtKt;
import com.android.systemui.volume.dialog.domain.interactor.VolumeDialogStateInteractor;
import com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor;
import com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor;
import java.util.Collection;
import java.util.LinkedHashSet;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeDialogViewModel {
    public final Context context;
    public final ReadonlyStateFlow dialogTitle;
    public final VolumeDialogVisibilityInteractor dialogVisibilityInteractor;
    public final ReadonlyStateFlow dialogVisibilityModel;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isHalfOpened;
    public final Collection touchableBoundsViews;
    public final VolumeDialogStateInteractor volumeDialogStateInteractor;

    public VolumeDialogViewModel(Context context, CoroutineScope coroutineScope, VolumeDialogVisibilityInteractor volumeDialogVisibilityInteractor, VolumeDialogSlidersInteractor volumeDialogSlidersInteractor, VolumeDialogStateInteractor volumeDialogStateInteractor, DevicePostureController devicePostureController, ConfigurationController configurationController) {
        this.context = context;
        this.dialogVisibilityInteractor = volumeDialogVisibilityInteractor;
        this.volumeDialogStateInteractor = volumeDialogStateInteractor;
        this.isHalfOpened = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(DevicePostureControllerExtKt.devicePosture(devicePostureController), new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new VolumeDialogViewModel$isHalfOpened$1(this, null), ConfigurationControllerExtKt.getOnConfigChanged(configurationController)), new VolumeDialogViewModel$isHalfOpened$2(this, null));
        this.dialogVisibilityModel = volumeDialogVisibilityInteractor.dialogVisibility;
        ReadonlyStateFlow readonlyStateFlow = volumeDialogStateInteractor.volumeDialogState;
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = volumeDialogSlidersInteractor.sliders;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow, new Flow() { // from class: com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L41
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSlidersModel r5 = (com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSlidersModel) r5
                        com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSliderType r5 = r5.slider
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new VolumeDialogViewModel$dialogTitle$2(this, null));
        SharingStarted.Companion.getClass();
        this.dialogTitle = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, SharingStarted.Companion.Eagerly, "");
        this.touchableBoundsViews = new LinkedHashSet();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final kotlin.coroutines.intrinsics.CoroutineSingletons addTouchableBounds(final android.view.View[] r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel$addTouchableBounds$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel$addTouchableBounds$1 r0 = (com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel$addTouchableBounds$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel$addTouchableBounds$1 r0 = new com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel$addTouchableBounds$1
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L37
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            java.lang.Object r4 = r0.L$1
            android.view.View[] r4 = (android.view.View[]) r4
            java.lang.Object r4 = r0.L$0
            com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel r4 = (com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel) r4
            kotlin.ResultKt.throwOnFailure(r6)
            goto L60
        L37:
            kotlin.ResultKt.throwOnFailure(r6)
            r0.L$0 = r4
            r0.L$1 = r5
            r0.label = r3
            kotlinx.coroutines.CancellableContinuationImpl r6 = new kotlinx.coroutines.CancellableContinuationImpl
            kotlin.coroutines.Continuation r0 = kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.intercepted(r0)
            r6.<init>(r0, r3)
            r6.initCancellability()
            java.util.Collection r0 = r4.touchableBoundsViews
            kotlin.collections.CollectionsKt__MutableCollectionsKt.addAll(r0, r5)
            com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel$addTouchableBounds$2$1 r0 = new com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel$addTouchableBounds$2$1
            r0.<init>()
            r6.invokeOnCancellation(r0)
            java.lang.Object r4 = r6.getResult()
            if (r4 != r1) goto L60
            return r1
        L60:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel.addTouchableBounds(android.view.View[], kotlin.coroutines.jvm.internal.ContinuationImpl):kotlin.coroutines.intrinsics.CoroutineSingletons");
    }
}
