package com.android.systemui.qs.panels.ui.viewmodel;

import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.qs.panels.domain.interactor.EditTilesListInteractor;
import com.android.systemui.qs.panels.domain.interactor.TilesAvailabilityInteractor;
import com.android.systemui.qs.panels.domain.model.EditTilesModel;
import com.android.systemui.qs.panels.shared.model.EditTileData;
import com.android.systemui.qs.pipeline.data.repository.MinimumTilesResourceRepository;
import com.android.systemui.qs.pipeline.domain.model.TileModel;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.util.kotlin.FlowKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.builders.SetBuilder;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes2.dex */
public final class EditModeViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ EditModeViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EditModeViewModel$special$$inlined$flatMapLatest$1(Continuation continuation, EditModeViewModel editModeViewModel) {
        super(3, continuation);
        this.this$0 = editModeViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        EditModeViewModel$special$$inlined$flatMapLatest$1 editModeViewModel$special$$inlined$flatMapLatest$1 = new EditModeViewModel$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        editModeViewModel$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        editModeViewModel$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return editModeViewModel$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x00e3, code lost:
    
        if (kotlinx.coroutines.flow.FlowKt.emitAll(r11, r1, r10) == r0) goto L30;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        Flow flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        FlowCollector flowCollector2;
        final EditTilesModel editTilesModel;
        FlowCollector flowCollector3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                EditTilesListInteractor editTilesListInteractor = this.this$0.editTilesListInteractor;
                this.L$0 = flowCollector;
                this.label = 1;
                Object tilesToEdit = editTilesListInteractor.getTilesToEdit(this);
                if (tilesToEdit != coroutineSingletons) {
                    flowCollector2 = flowCollector;
                    obj = tilesToEdit;
                }
                return coroutineSingletons;
            }
            flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = EmptyFlow.INSTANCE;
            this.L$0 = null;
            this.L$1 = null;
            this.label = 3;
        } else if (i == 1) {
            flowCollector2 = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
        } else {
            if (i != 2) {
                if (i != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            editTilesModel = (EditTilesModel) this.L$1;
            flowCollector3 = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
            final Set set = (Set) obj;
            final StateFlow currentTiles = this.this$0.currentTilesInteractor.getCurrentTiles();
            final EditModeViewModel editModeViewModel = this.this$0;
            flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new Flow() { // from class: com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel$tiles$lambda$10$$inlined$map$1

                /* renamed from: com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel$tiles$lambda$10$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ EditTilesModel $editTilesData$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ Set $unavailable$inlined;
                    public final /* synthetic */ EditModeViewModel this$0;

                    /* renamed from: com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel$tiles$lambda$10$$inlined$map$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, EditModeViewModel editModeViewModel, EditTilesModel editTilesModel, Set set) {
                        this.$this_unsafeFlow = flowCollector;
                        this.this$0 = editModeViewModel;
                        this.$editTilesData$inlined = editTilesModel;
                        this.$unavailable$inlined = set;
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
                            List list = (List) obj;
                            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                            Iterator it = list.iterator();
                            while (it.hasNext()) {
                                arrayList.add(((TileModel) it.next()).spec);
                            }
                            int i3 = 0;
                            boolean z = arrayList.size() > ((MinimumTilesResourceRepository) this.this$0.minTilesInteractor.minimumTilesRepository).minNumberOfTiles;
                            EditTilesModel editTilesModel = this.$editTilesData$inlined;
                            List listPlus = CollectionsKt___CollectionsKt.plus((Iterable) editTilesModel.customTiles, (Collection) editTilesModel.stockTiles);
                            int iMapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(listPlus, 10));
                            if (iMapCapacity < 16) {
                                iMapCapacity = 16;
                            }
                            LinkedHashMap linkedHashMap = new LinkedHashMap(iMapCapacity);
                            ArrayList arrayList2 = (ArrayList) listPlus;
                            int size = arrayList2.size();
                            int i4 = 0;
                            while (i4 < size) {
                                Object obj3 = arrayList2.get(i4);
                                i4++;
                                linkedHashMap.put(((EditTileData) obj3).tileSpec, obj3);
                            }
                            ArrayList arrayList3 = new ArrayList();
                            int size2 = arrayList.size();
                            int i5 = 0;
                            while (i5 < size2) {
                                Object obj4 = arrayList.get(i5);
                                i5++;
                                EditTileData editTileData = (EditTileData) linkedHashMap.get((TileSpec) obj4);
                                if (editTileData != null) {
                                    arrayList3.add(editTileData);
                                }
                            }
                            ArrayList arrayList4 = new ArrayList();
                            int size3 = arrayList2.size();
                            int i6 = 0;
                            while (i6 < size3) {
                                Object obj5 = arrayList2.get(i6);
                                i6++;
                                if (!arrayList.contains(((EditTileData) obj5).tileSpec)) {
                                    arrayList4.add(obj5);
                                }
                            }
                            List listPlus2 = CollectionsKt___CollectionsKt.plus((Iterable) arrayList4, (Collection) arrayList3);
                            ArrayList arrayList5 = new ArrayList();
                            ArrayList arrayList6 = (ArrayList) listPlus2;
                            int size4 = arrayList6.size();
                            int i7 = 0;
                            while (i7 < size4) {
                                Object obj6 = arrayList6.get(i7);
                                i7++;
                                if (!this.$unavailable$inlined.contains(((EditTileData) obj6).tileSpec)) {
                                    arrayList5.add(obj6);
                                }
                            }
                            ArrayList arrayList7 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList5, 10));
                            int size5 = arrayList5.size();
                            while (i3 < size5) {
                                Object obj7 = arrayList5.get(i3);
                                i3++;
                                EditTileData editTileData2 = (EditTileData) obj7;
                                boolean zContains = arrayList.contains(editTileData2.tileSpec);
                                SetBuilder setBuilder = new SetBuilder();
                                if (zContains) {
                                    setBuilder.add(AvailableEditActions.MOVE);
                                    if (z) {
                                        setBuilder.add(AvailableEditActions.REMOVE);
                                    }
                                } else {
                                    setBuilder.add(AvailableEditActions.ADD);
                                }
                                arrayList7.add(new UnloadedEditTileViewModel(editTileData2.tileSpec, editTileData2.icon, editTileData2.label, editTileData2.appName, zContains, setBuilder.build(), editTileData2.category));
                            }
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(arrayList7, anonymousClass1) == coroutineSingletons) {
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
                public final Object collect(FlowCollector flowCollector4, Continuation continuation) {
                    Object objCollect = currentTiles.collect(new AnonymousClass2(flowCollector4, editModeViewModel, editTilesModel, set), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            }, new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt.AnonymousClass1(null), ((ConfigurationInteractorImpl) this.this$0.configurationInteractor).onAnyConfigurationChange), new EditModeViewModel$tiles$1$2(this.this$0, null));
            flowCollector = flowCollector3;
            this.L$0 = null;
            this.L$1 = null;
            this.label = 3;
        }
        EditTilesModel editTilesModel2 = (EditTilesModel) obj;
        TilesAvailabilityInteractor tilesAvailabilityInteractor = this.this$0.tilesAvailabilityInteractor;
        List list = editTilesModel2.stockTiles;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((EditTileData) it.next()).tileSpec);
        }
        List listMinus = CollectionsKt___CollectionsKt.minus((Iterable) arrayList, (Iterable) CollectionsKt___CollectionsKt.toSet(this.this$0.currentTilesInteractor.getCurrentTilesSpecs()));
        this.L$0 = flowCollector2;
        this.L$1 = editTilesModel2;
        this.label = 2;
        Object unavailableTiles = tilesAvailabilityInteractor.getUnavailableTiles(listMinus, this);
        if (unavailableTiles != coroutineSingletons) {
            FlowCollector flowCollector4 = flowCollector2;
            editTilesModel = editTilesModel2;
            obj = unavailableTiles;
            flowCollector3 = flowCollector4;
            final Set set2 = (Set) obj;
            final Flow currentTiles2 = this.this$0.currentTilesInteractor.getCurrentTiles();
            final EditModeViewModel editModeViewModel2 = this.this$0;
            flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new Flow() { // from class: com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel$tiles$lambda$10$$inlined$map$1

                /* renamed from: com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel$tiles$lambda$10$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ EditTilesModel $editTilesData$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ Set $unavailable$inlined;
                    public final /* synthetic */ EditModeViewModel this$0;

                    /* renamed from: com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel$tiles$lambda$10$$inlined$map$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, EditModeViewModel editModeViewModel, EditTilesModel editTilesModel, Set set) {
                        this.$this_unsafeFlow = flowCollector;
                        this.this$0 = editModeViewModel;
                        this.$editTilesData$inlined = editTilesModel;
                        this.$unavailable$inlined = set;
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
                            List list = (List) obj;
                            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                            Iterator it = list.iterator();
                            while (it.hasNext()) {
                                arrayList.add(((TileModel) it.next()).spec);
                            }
                            int i3 = 0;
                            boolean z = arrayList.size() > ((MinimumTilesResourceRepository) this.this$0.minTilesInteractor.minimumTilesRepository).minNumberOfTiles;
                            EditTilesModel editTilesModel = this.$editTilesData$inlined;
                            List listPlus = CollectionsKt___CollectionsKt.plus((Iterable) editTilesModel.customTiles, (Collection) editTilesModel.stockTiles);
                            int iMapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(listPlus, 10));
                            if (iMapCapacity < 16) {
                                iMapCapacity = 16;
                            }
                            LinkedHashMap linkedHashMap = new LinkedHashMap(iMapCapacity);
                            ArrayList arrayList2 = (ArrayList) listPlus;
                            int size = arrayList2.size();
                            int i4 = 0;
                            while (i4 < size) {
                                Object obj3 = arrayList2.get(i4);
                                i4++;
                                linkedHashMap.put(((EditTileData) obj3).tileSpec, obj3);
                            }
                            ArrayList arrayList3 = new ArrayList();
                            int size2 = arrayList.size();
                            int i5 = 0;
                            while (i5 < size2) {
                                Object obj4 = arrayList.get(i5);
                                i5++;
                                EditTileData editTileData = (EditTileData) linkedHashMap.get((TileSpec) obj4);
                                if (editTileData != null) {
                                    arrayList3.add(editTileData);
                                }
                            }
                            ArrayList arrayList4 = new ArrayList();
                            int size3 = arrayList2.size();
                            int i6 = 0;
                            while (i6 < size3) {
                                Object obj5 = arrayList2.get(i6);
                                i6++;
                                if (!arrayList.contains(((EditTileData) obj5).tileSpec)) {
                                    arrayList4.add(obj5);
                                }
                            }
                            List listPlus2 = CollectionsKt___CollectionsKt.plus((Iterable) arrayList4, (Collection) arrayList3);
                            ArrayList arrayList5 = new ArrayList();
                            ArrayList arrayList6 = (ArrayList) listPlus2;
                            int size4 = arrayList6.size();
                            int i7 = 0;
                            while (i7 < size4) {
                                Object obj6 = arrayList6.get(i7);
                                i7++;
                                if (!this.$unavailable$inlined.contains(((EditTileData) obj6).tileSpec)) {
                                    arrayList5.add(obj6);
                                }
                            }
                            ArrayList arrayList7 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList5, 10));
                            int size5 = arrayList5.size();
                            while (i3 < size5) {
                                Object obj7 = arrayList5.get(i3);
                                i3++;
                                EditTileData editTileData2 = (EditTileData) obj7;
                                boolean zContains = arrayList.contains(editTileData2.tileSpec);
                                SetBuilder setBuilder = new SetBuilder();
                                if (zContains) {
                                    setBuilder.add(AvailableEditActions.MOVE);
                                    if (z) {
                                        setBuilder.add(AvailableEditActions.REMOVE);
                                    }
                                } else {
                                    setBuilder.add(AvailableEditActions.ADD);
                                }
                                arrayList7.add(new UnloadedEditTileViewModel(editTileData2.tileSpec, editTileData2.icon, editTileData2.label, editTileData2.appName, zContains, setBuilder.build(), editTileData2.category));
                            }
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(arrayList7, anonymousClass1) == coroutineSingletons) {
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
                public final Object collect(FlowCollector flowCollector42, Continuation continuation) {
                    Object objCollect = currentTiles2.collect(new AnonymousClass2(flowCollector42, editModeViewModel2, editTilesModel, set2), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            }, new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt.AnonymousClass1(null), ((ConfigurationInteractorImpl) this.this$0.configurationInteractor).onAnyConfigurationChange), new EditModeViewModel$tiles$1$2(this.this$0, null));
            flowCollector = flowCollector3;
            this.L$0 = null;
            this.L$1 = null;
            this.label = 3;
        }
        return coroutineSingletons;
    }
}
