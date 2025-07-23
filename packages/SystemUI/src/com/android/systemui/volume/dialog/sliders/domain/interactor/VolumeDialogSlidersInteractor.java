package com.android.systemui.volume.dialog.sliders.domain.interactor;

import android.content.pm.PackageManager;
import com.android.systemui.volume.dialog.domain.interactor.VolumeDialogStateInteractor;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogStreamModel;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeDialogSlidersInteractor {
    public final PackageManager packageManager;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 sliders;
    public final StreamsSorter streamsSorter = new StreamsSorter();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class StreamsSorter implements Comparator {
        public final List priorityPredicates;

        public StreamsSorter() {
            final int i = 0;
            final int i2 = 1;
            final int i3 = 2;
            final int i4 = 3;
            final int i5 = 4;
            final int i6 = 5;
            final int i7 = 6;
            final int i8 = 7;
            this.priorityPredicates = Arrays.asList(new Function1() { // from class: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$StreamsSorter$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    VolumeDialogStreamModel volumeDialogStreamModel = (VolumeDialogStreamModel) obj;
                    switch (i) {
                        case 0:
                            return Boolean.valueOf(volumeDialogStreamModel.isActive);
                        case 1:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 3);
                        case 2:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 10);
                        case 3:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 2);
                        case 4:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 5);
                        case 5:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 0);
                        case 6:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 1);
                        default:
                            return Boolean.valueOf(volumeDialogStreamModel.isDynamic);
                    }
                }
            }, new Function1() { // from class: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$StreamsSorter$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    VolumeDialogStreamModel volumeDialogStreamModel = (VolumeDialogStreamModel) obj;
                    switch (i2) {
                        case 0:
                            return Boolean.valueOf(volumeDialogStreamModel.isActive);
                        case 1:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 3);
                        case 2:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 10);
                        case 3:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 2);
                        case 4:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 5);
                        case 5:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 0);
                        case 6:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 1);
                        default:
                            return Boolean.valueOf(volumeDialogStreamModel.isDynamic);
                    }
                }
            }, new Function1() { // from class: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$StreamsSorter$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    VolumeDialogStreamModel volumeDialogStreamModel = (VolumeDialogStreamModel) obj;
                    switch (i3) {
                        case 0:
                            return Boolean.valueOf(volumeDialogStreamModel.isActive);
                        case 1:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 3);
                        case 2:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 10);
                        case 3:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 2);
                        case 4:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 5);
                        case 5:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 0);
                        case 6:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 1);
                        default:
                            return Boolean.valueOf(volumeDialogStreamModel.isDynamic);
                    }
                }
            }, new Function1() { // from class: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$StreamsSorter$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    VolumeDialogStreamModel volumeDialogStreamModel = (VolumeDialogStreamModel) obj;
                    switch (i4) {
                        case 0:
                            return Boolean.valueOf(volumeDialogStreamModel.isActive);
                        case 1:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 3);
                        case 2:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 10);
                        case 3:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 2);
                        case 4:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 5);
                        case 5:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 0);
                        case 6:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 1);
                        default:
                            return Boolean.valueOf(volumeDialogStreamModel.isDynamic);
                    }
                }
            }, new Function1() { // from class: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$StreamsSorter$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    VolumeDialogStreamModel volumeDialogStreamModel = (VolumeDialogStreamModel) obj;
                    switch (i5) {
                        case 0:
                            return Boolean.valueOf(volumeDialogStreamModel.isActive);
                        case 1:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 3);
                        case 2:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 10);
                        case 3:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 2);
                        case 4:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 5);
                        case 5:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 0);
                        case 6:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 1);
                        default:
                            return Boolean.valueOf(volumeDialogStreamModel.isDynamic);
                    }
                }
            }, new Function1() { // from class: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$StreamsSorter$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    VolumeDialogStreamModel volumeDialogStreamModel = (VolumeDialogStreamModel) obj;
                    switch (i6) {
                        case 0:
                            return Boolean.valueOf(volumeDialogStreamModel.isActive);
                        case 1:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 3);
                        case 2:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 10);
                        case 3:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 2);
                        case 4:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 5);
                        case 5:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 0);
                        case 6:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 1);
                        default:
                            return Boolean.valueOf(volumeDialogStreamModel.isDynamic);
                    }
                }
            }, new Function1() { // from class: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$StreamsSorter$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    VolumeDialogStreamModel volumeDialogStreamModel = (VolumeDialogStreamModel) obj;
                    switch (i7) {
                        case 0:
                            return Boolean.valueOf(volumeDialogStreamModel.isActive);
                        case 1:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 3);
                        case 2:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 10);
                        case 3:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 2);
                        case 4:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 5);
                        case 5:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 0);
                        case 6:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 1);
                        default:
                            return Boolean.valueOf(volumeDialogStreamModel.isDynamic);
                    }
                }
            }, new Function1() { // from class: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$StreamsSorter$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    VolumeDialogStreamModel volumeDialogStreamModel = (VolumeDialogStreamModel) obj;
                    switch (i8) {
                        case 0:
                            return Boolean.valueOf(volumeDialogStreamModel.isActive);
                        case 1:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 3);
                        case 2:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 10);
                        case 3:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 2);
                        case 4:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 5);
                        case 5:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 0);
                        case 6:
                            return Boolean.valueOf(volumeDialogStreamModel.stream == 1);
                        default:
                            return Boolean.valueOf(volumeDialogStreamModel.isDynamic);
                    }
                }
            });
        }

        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return getPriority((VolumeDialogStreamModel) obj) - getPriority((VolumeDialogStreamModel) obj2);
        }

        public final int getPriority(VolumeDialogStreamModel volumeDialogStreamModel) {
            Iterator it = this.priorityPredicates.iterator();
            int i = 0;
            while (true) {
                if (!it.hasNext()) {
                    i = -1;
                    break;
                }
                if (((Boolean) ((Function1) it.next()).mo779invoke(volumeDialogStreamModel)).booleanValue()) {
                    break;
                }
                i++;
            }
            return i >= 0 ? i : volumeDialogStreamModel.stream;
        }
    }

    public VolumeDialogSlidersInteractor(VolumeDialogStateInteractor volumeDialogStateInteractor, PackageManager packageManager, CoroutineScope coroutineScope) {
        this.packageManager = packageManager;
        final ReadonlyStateFlow readonlyStateFlow = volumeDialogStateInteractor.volumeDialogState;
        final Flow flow = new Flow() { // from class: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$special$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$special$$inlined$filter$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
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
                        boolean r0 = r6 instanceof com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$special$$inlined$filter$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L48
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        com.android.systemui.volume.dialog.shared.model.VolumeDialogStateModel r6 = (com.android.systemui.volume.dialog.shared.model.VolumeDialogStateModel) r6
                        java.util.Map r6 = r6.streamModels
                        boolean r6 = r6.isEmpty()
                        if (r6 != 0) goto L48
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final Flow flow2 = new Flow() { // from class: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ VolumeDialogSlidersInteractor this$0;

                /* renamed from: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, VolumeDialogSlidersInteractor volumeDialogSlidersInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = volumeDialogSlidersInteractor;
                }

                /* JADX WARN: Code restructure failed: missing block: B:34:0x0075, code lost:
                
                    if (r7.isDynamic == false) goto L30;
                 */
                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                /* JADX WARN: Removed duplicated region for block: B:26:0x007c A[SYNTHETIC] */
                /* JADX WARN: Removed duplicated region for block: B:30:0x0046 A[SYNTHETIC] */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r10, kotlin.coroutines.Continuation r11) {
                    /*
                        r9 = this;
                        boolean r0 = r11 instanceof com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r11
                        com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r11)
                    L18:
                        java.lang.Object r11 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L30
                        if (r2 != r3) goto L28
                        kotlin.ResultKt.throwOnFailure(r11)
                        goto Ld0
                    L28:
                        java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                        java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                        r9.<init>(r10)
                        throw r9
                    L30:
                        kotlin.ResultKt.throwOnFailure(r11)
                        com.android.systemui.volume.dialog.shared.model.VolumeDialogStateModel r10 = (com.android.systemui.volume.dialog.shared.model.VolumeDialogStateModel) r10
                        java.util.Map r11 = r10.streamModels
                        java.util.Collection r11 = r11.values()
                        java.lang.Iterable r11 = (java.lang.Iterable) r11
                        java.util.ArrayList r2 = new java.util.ArrayList
                        r2.<init>()
                        java.util.Iterator r11 = r11.iterator()
                    L46:
                        boolean r4 = r11.hasNext()
                        r5 = 10
                        com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor r6 = r9.this$0
                        if (r4 == 0) goto L80
                        java.lang.Object r4 = r11.next()
                        r7 = r4
                        com.android.systemui.volume.dialog.shared.model.VolumeDialogStreamModel r7 = (com.android.systemui.volume.dialog.shared.model.VolumeDialogStreamModel) r7
                        r6.getClass()
                        boolean r8 = r7.isActive
                        if (r8 == 0) goto L5f
                        goto L77
                    L5f:
                        android.content.pm.PackageManager r6 = r6.packageManager
                        java.lang.String r8 = "android.software.leanback"
                        boolean r6 = r6.hasSystemFeature(r8)
                        if (r6 != 0) goto L79
                        int r6 = r7.stream
                        if (r6 != r5) goto L70
                        boolean r5 = r10.shouldShowA11ySlider
                        goto L7a
                    L70:
                        r5 = 3
                        if (r6 == r5) goto L77
                        boolean r5 = r7.isDynamic
                        if (r5 == 0) goto L79
                    L77:
                        r5 = r3
                        goto L7a
                    L79:
                        r5 = 0
                    L7a:
                        if (r5 == 0) goto L46
                        r2.add(r4)
                        goto L46
                    L80:
                        com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$StreamsSorter r10 = r6.streamsSorter
                        java.util.List r10 = kotlin.collections.CollectionsKt___CollectionsKt.sortedWith(r2, r10)
                        java.lang.Iterable r10 = (java.lang.Iterable) r10
                        java.util.ArrayList r11 = new java.util.ArrayList
                        int r2 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r10, r5)
                        r11.<init>(r2)
                        java.util.Iterator r10 = r10.iterator()
                    L95:
                        boolean r2 = r10.hasNext()
                        if (r2 == 0) goto Lc0
                        java.lang.Object r2 = r10.next()
                        com.android.systemui.volume.dialog.shared.model.VolumeDialogStreamModel r2 = (com.android.systemui.volume.dialog.shared.model.VolumeDialogStreamModel) r2
                        int r2 = r2.stream
                        r4 = 99
                        if (r2 != r4) goto Lad
                        com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSliderType$AudioSharingStream r4 = new com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSliderType$AudioSharingStream
                        r4.<init>(r2)
                        goto Lbc
                    Lad:
                        r4 = 100
                        if (r2 < r4) goto Lb7
                        com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSliderType$RemoteMediaStream r4 = new com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSliderType$RemoteMediaStream
                        r4.<init>(r2)
                        goto Lbc
                    Lb7:
                        com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSliderType$Stream r4 = new com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSliderType$Stream
                        r4.<init>(r2)
                    Lbc:
                        r11.add(r4)
                        goto L95
                    Lc0:
                        java.util.LinkedHashSet r10 = new java.util.LinkedHashSet
                        r10.<init>(r11)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r9 = r9.$this_unsafeFlow
                        java.lang.Object r9 = r9.emit(r10, r0)
                        if (r9 != r1) goto Ld0
                        return r1
                    Ld0:
                        kotlin.Unit r9 = kotlin.Unit.INSTANCE
                        return r9
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final VolumeDialogSlidersInteractor$sliders$3 volumeDialogSlidersInteractor$sliders$3 = new VolumeDialogSlidersInteractor$sliders$3(null);
        final Flow flow3 = new Flow() { // from class: kotlinx.coroutines.flow.FlowKt__TransformKt$runningReduce$$inlined$unsafeFlow$1
            /* JADX WARN: Type inference failed for: r1v0, types: [T, kotlinx.coroutines.internal.Symbol] */
            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                ref$ObjectRef.element = NullSurrogateKt.NULL;
                Object collect = Flow.this.collect(new FlowKt__TransformKt$runningReduce$1$1(ref$ObjectRef, volumeDialogSlidersInteractor$sliders$3, flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        Flow flow4 = new Flow() { // from class: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$special$$inlined$map$2$2$1 r0 = (com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$special$$inlined$map$2$2$1 r0 = new com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4e
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.util.LinkedHashSet r5 = (java.util.LinkedHashSet) r5
                        com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSlidersModel r6 = new com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSlidersModel
                        java.lang.Object r2 = kotlin.collections.CollectionsKt___CollectionsKt.first(r5)
                        com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSliderType r2 = (com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSliderType) r2
                        java.util.List r5 = kotlin.collections.CollectionsKt___CollectionsKt.drop(r5, r3)
                        r6.<init>(r2, r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L4e
                        return r1
                    L4e:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        this.sliders = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(FlowKt.stateIn(flow4, coroutineScope, SharingStarted.Companion.Eagerly, null));
    }
}
