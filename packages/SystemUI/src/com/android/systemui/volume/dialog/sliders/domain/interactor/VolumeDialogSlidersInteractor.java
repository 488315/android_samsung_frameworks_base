package com.android.systemui.volume.dialog.sliders.domain.interactor;

import android.content.pm.PackageManager;
import com.android.systemui.volume.dialog.domain.interactor.VolumeDialogStateInteractor;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogStateModel;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogStreamModel;
import com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSliderType;
import com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSlidersModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
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

/* loaded from: classes3.dex */
public final class VolumeDialogSlidersInteractor {
    public final PackageManager packageManager;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 sliders;
    public final StreamsSorter streamsSorter = new StreamsSorter();

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
                public final Object mo781invoke(Object obj) {
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
                public final Object mo781invoke(Object obj) {
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
                public final Object mo781invoke(Object obj) {
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
                public final Object mo781invoke(Object obj) {
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
                public final Object mo781invoke(Object obj) {
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
                public final Object mo781invoke(Object obj) {
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
                public final Object mo781invoke(Object obj) {
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
                public final Object mo781invoke(Object obj) {
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
                if (((Boolean) ((Function1) it.next()).mo781invoke(volumeDialogStreamModel)).booleanValue()) {
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
                        if (!((VolumeDialogStateModel) obj).streamModels.isEmpty()) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        final Flow flow2 = new Flow() { // from class: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$special$$inlined$map$1

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

                /* JADX WARN: Removed duplicated region for block: B:29:0x0077  */
                /* JADX WARN: Removed duplicated region for block: B:30:0x0079  */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    VolumeDialogSlidersInteractor volumeDialogSlidersInteractor;
                    boolean z;
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
                        VolumeDialogStateModel volumeDialogStateModel = (VolumeDialogStateModel) obj;
                        Collection collectionValues = volumeDialogStateModel.streamModels.values();
                        ArrayList arrayList = new ArrayList();
                        Iterator it = collectionValues.iterator();
                        while (true) {
                            boolean zHasNext = it.hasNext();
                            volumeDialogSlidersInteractor = this.this$0;
                            if (!zHasNext) {
                                break;
                            }
                            Object next = it.next();
                            VolumeDialogStreamModel volumeDialogStreamModel = (VolumeDialogStreamModel) next;
                            volumeDialogSlidersInteractor.getClass();
                            if (!volumeDialogStreamModel.isActive) {
                                if (volumeDialogSlidersInteractor.packageManager.hasSystemFeature("android.software.leanback")) {
                                    z = false;
                                } else {
                                    int i3 = volumeDialogStreamModel.stream;
                                    if (i3 == 10) {
                                        z = volumeDialogStateModel.shouldShowA11ySlider;
                                    } else if (i3 == 3 || volumeDialogStreamModel.isDynamic) {
                                        z = true;
                                    }
                                }
                            }
                            if (z) {
                                arrayList.add(next);
                            }
                        }
                        List listSortedWith = CollectionsKt___CollectionsKt.sortedWith(arrayList, volumeDialogSlidersInteractor.streamsSorter);
                        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listSortedWith, 10));
                        Iterator it2 = listSortedWith.iterator();
                        while (it2.hasNext()) {
                            int i4 = ((VolumeDialogStreamModel) it2.next()).stream;
                            arrayList2.add(i4 == 99 ? new VolumeDialogSliderType.AudioSharingStream(i4) : i4 >= 100 ? new VolumeDialogSliderType.RemoteMediaStream(i4) : new VolumeDialogSliderType.Stream(i4));
                        }
                        LinkedHashSet linkedHashSet = new LinkedHashSet(arrayList2);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(linkedHashSet, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        final VolumeDialogSlidersInteractor$sliders$3 volumeDialogSlidersInteractor$sliders$3 = new VolumeDialogSlidersInteractor$sliders$3(null);
        final Flow flow3 = new Flow() { // from class: kotlinx.coroutines.flow.FlowKt__TransformKt$runningReduce$$inlined$unsafeFlow$1
            /* JADX WARN: Type inference failed for: r1v0, types: [T, kotlinx.coroutines.internal.Symbol] */
            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                ref$ObjectRef.element = NullSurrogateKt.NULL;
                Object objCollect = flow2.collect(new FlowKt__TransformKt$runningReduce$1$1(ref$ObjectRef, volumeDialogSlidersInteractor$sliders$3, flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        Flow flow4 = new Flow() { // from class: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor$special$$inlined$map$2

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
                        LinkedHashSet linkedHashSet = (LinkedHashSet) obj;
                        VolumeDialogSlidersModel volumeDialogSlidersModel = new VolumeDialogSlidersModel((VolumeDialogSliderType) CollectionsKt___CollectionsKt.first(linkedHashSet), CollectionsKt___CollectionsKt.drop(linkedHashSet, 1));
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(volumeDialogSlidersModel, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flow3.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        this.sliders = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(FlowKt.stateIn(flow4, coroutineScope, SharingStarted.Companion.Eagerly, null));
    }
}
