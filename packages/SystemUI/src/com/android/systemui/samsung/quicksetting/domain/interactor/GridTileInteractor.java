package com.android.systemui.samsung.quicksetting.domain.interactor;

import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import com.android.systemui.R;
import com.android.systemui.pluginlock.component.PluginLockShortcutTask;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.panels.domain.interactor.EditTilesListInteractor;
import com.android.systemui.qs.panels.domain.model.EditTilesModel;
import com.android.systemui.qs.panels.shared.model.EditTileData;
import com.android.systemui.qs.panels.ui.viewmodel.TileGridViewModel;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.samsung.quicksetting.data.repository.GridTileRepositoryImpl;
import com.android.systemui.samsung.quicksetting.domain.model.GridTileData;
import com.android.systemui.samsung.quicksetting.domain.model.QSPanelItem;
import com.android.systemui.samsung.quicksetting.domain.model.items.BrightBar;
import com.android.systemui.samsung.quicksetting.domain.model.items.Collapser;
import com.android.systemui.samsung.quicksetting.domain.model.items.GridTileItem;
import com.android.systemui.samsung.quicksetting.domain.model.items.MediaPlayer;
import com.android.systemui.samsung.quicksetting.domain.model.items.QuickTile;
import com.android.systemui.samsung.quicksetting.domain.model.items.QuickTileDrawer;
import com.android.systemui.samsung.quicksetting.domain.model.items.QuickTileFolder;
import com.android.systemui.samsung.quicksetting.domain.model.items.VolumeBar;
import com.android.systemui.samsung.quicksetting.domain.repository.GridTileRepository;
import com.android.systemui.samsung.quicksetting.ui.panel.ScreenType;
import com.android.systemui.samsung.quicksetting.ui.panel.SecBrightBarViewModel;
import com.android.systemui.samsung.quicksetting.ui.panel.SecVolumeBarViewModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.inject.Provider;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes2.dex */
public final class GridTileInteractor {
    public final Provider brightBarViewModelProvider;
    public final CurrentTilesInteractor currentTilesInteractor;
    public final EditTilesListInteractor editTilesListInteractor;
    public final GridTileRepository gridTileRepository;
    public final TileGridViewModel tileGridViewModel;
    public final Provider volumeBarViewModelProvider;

    /* renamed from: com.android.systemui.samsung.quicksetting.domain.interactor.GridTileInteractor$loadAvailableTiles$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return GridTileInteractor.this.loadAvailableTiles(null, this);
        }
    }

    /* renamed from: com.android.systemui.samsung.quicksetting.domain.interactor.GridTileInteractor$loadTiles$1, reason: invalid class name and case insensitive filesystem */
    final class C10221 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C10221(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return GridTileInteractor.this.loadTiles(null, this);
        }
    }

    public GridTileInteractor(GridTileRepository gridTileRepository, EditTilesListInteractor editTilesListInteractor, CurrentTilesInteractor currentTilesInteractor, TileGridViewModel.Factory factory, Provider provider, Provider provider2) {
        this.gridTileRepository = gridTileRepository;
        this.editTilesListInteractor = editTilesListInteractor;
        this.currentTilesInteractor = currentTilesInteractor;
        this.brightBarViewModelProvider = provider;
        this.volumeBarViewModelProvider = provider2;
        this.tileGridViewModel = factory.create();
    }

    public static QSPanelItem buildPanelItem(GridTileData gridTileData, Function1 function1) {
        GridTileItem gridTileItem = (GridTileItem) function1.mo781invoke(gridTileData);
        long j = (gridTileData.spanX << 32) | (gridTileData.spanY & 4294967295L);
        IntOffset.Companion companion = IntOffset.Companion;
        return new QSPanelItem(gridTileItem, gridTileData.spanWidth, gridTileData.spanHeight, j, 0.0f, 0.0f, 0, null, null, null, false, 2032, null);
    }

    public static int getQuickTileResourceId(String str) {
        switch (str.hashCode()) {
            case -322116978:
                return !str.equals("Bluetooth") ? R.drawable.qs_airplane_icon_off : R.drawable.qs_bluetooth_icon_on;
            case 2695989:
                return !str.equals("Wifi") ? R.drawable.qs_airplane_icon_off : R.drawable.quick_panel_icon_wifi;
            case 191264562:
                return !str.equals("SoundMode") ? R.drawable.qs_airplane_icon_off : R.drawable.qs_data_saver_icon_off;
            case 810391366:
                return !str.equals(PluginLockShortcutTask.FLASH_LIGHT_TASK) ? R.drawable.qs_airplane_icon_off : R.drawable.qs_flashlight_icon_on;
            case 1278322741:
                return !str.equals("AirplaneMode") ? R.drawable.qs_airplane_icon_off : R.drawable.qs_airplane_icon_on;
            case 1834528233:
                return !str.equals("RotationLock") ? R.drawable.qs_airplane_icon_off : R.drawable.qs_location_icon_off;
            case 1965687765:
                return !str.equals("Location") ? R.drawable.qs_airplane_icon_off : R.drawable.qs_location_icon_off;
            default:
                return R.drawable.qs_airplane_icon_off;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x01b3 A[LOOP:0: B:39:0x01ad->B:41:0x01b3, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x01d6  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0208 A[LOOP:2: B:52:0x0206->B:53:0x0208, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0229  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0250  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0277 A[LOOP:5: B:66:0x0275->B:67:0x0277, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x029b  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x02be A[LOOP:7: B:75:0x02bc->B:76:0x02be, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x02e1 A[LOOP:8: B:78:0x02df->B:79:0x02e1, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0018  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0307  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object loadAvailableTiles(List list, ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        List list2;
        Object tilesToEdit;
        List list3;
        GridTileInteractor gridTileInteractor;
        List list4;
        QuickTile quickTile;
        long j;
        Iterator it;
        int size;
        int i;
        int size2;
        int i2;
        int size3;
        int i3;
        int size4;
        int i4;
        int size5;
        int i5;
        GridTileInteractor gridTileInteractor2 = this;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i6 = anonymousClass1.label;
            if ((i6 & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i6 - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = gridTileInteractor2.new AnonymousClass1(continuationImpl);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i7 = anonymousClass1.label;
        int i8 = 2;
        if (i7 == 0) {
            ResultKt.throwOnFailure(obj);
            anonymousClass1.L$0 = gridTileInteractor2;
            list2 = list;
            anonymousClass1.L$1 = list2;
            anonymousClass1.label = 1;
            tilesToEdit = gridTileInteractor2.editTilesListInteractor.getTilesToEdit(anonymousClass1);
            if (tilesToEdit != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i7 != 1) {
            if (i7 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            list4 = (List) anonymousClass1.L$2;
            list3 = (List) anonymousClass1.L$1;
            gridTileInteractor = (GridTileInteractor) anonymousClass1.L$0;
            ResultKt.throwOnFailure(obj);
            Iterable iterable = (Iterable) obj;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
            it = iterable.iterator();
            while (it.hasNext()) {
                arrayList.add(gridTileInteractor.toQSPanelItem((GridTileData) it.next()));
            }
            List list5 = list3;
            ArrayList arrayList2 = new ArrayList();
            for (Object obj2 : list5) {
                GridTileItem gridTileItem = ((QSPanelItem) obj2).gridTileItem;
                if (!Intrinsics.areEqual(gridTileItem.getType(), "QuickTile") && !Intrinsics.areEqual(gridTileItem.getType(), "QuickTileFolder")) {
                    arrayList2.add(obj2);
                }
            }
            ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList2, 10));
            size = arrayList2.size();
            i = 0;
            while (i < size) {
                Object obj3 = arrayList2.get(i);
                i++;
                arrayList3.add(((QSPanelItem) obj3).gridTileItem.getUniqueKey());
            }
            Set set = CollectionsKt___CollectionsKt.toSet(arrayList3);
            ArrayList arrayList4 = new ArrayList();
            size2 = arrayList.size();
            i2 = 0;
            while (i2 < size2) {
                Object obj4 = arrayList.get(i2);
                i2++;
                if (!set.contains(((QSPanelItem) obj4).gridTileItem.getUniqueKey())) {
                    arrayList4.add(obj4);
                }
            }
            ArrayList arrayList5 = new ArrayList();
            for (Object obj5 : list5) {
                if (Intrinsics.areEqual(((QSPanelItem) obj5).gridTileItem.getType(), "QuickTile")) {
                    arrayList5.add(obj5);
                }
            }
            ArrayList arrayList6 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList5, 10));
            size3 = arrayList5.size();
            i3 = 0;
            while (i3 < size3) {
                Object obj6 = arrayList5.get(i3);
                i3++;
                arrayList6.add(((QSPanelItem) obj6).gridTileItem.getUniqueKey());
            }
            Set set2 = CollectionsKt___CollectionsKt.toSet(arrayList6);
            ArrayList arrayList7 = new ArrayList();
            for (Object obj7 : list5) {
                if (Intrinsics.areEqual(((QSPanelItem) obj7).gridTileItem.getType(), "QuickTileFolder")) {
                    arrayList7.add(obj7);
                }
            }
            ArrayList arrayList8 = new ArrayList();
            size4 = arrayList7.size();
            i4 = 0;
            while (i4 < size4) {
                Object obj8 = arrayList7.get(i4);
                i4++;
                CollectionsKt__MutableCollectionsKt.addAll(((QuickTileFolder) ((QSPanelItem) obj8).gridTileItem).tiles, arrayList8);
            }
            ArrayList arrayList9 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList8, 10));
            size5 = arrayList8.size();
            i5 = 0;
            while (i5 < size5) {
                Object obj9 = arrayList8.get(i5);
                i5++;
                arrayList9.add(((QuickTile) obj9).spec);
            }
            Set set3 = CollectionsKt___CollectionsKt.toSet(SetsKt___SetsKt.plus(set2, (Iterable) arrayList9));
            ArrayList arrayList10 = new ArrayList();
            for (Object obj10 : list4) {
                if (!set3.contains(((QSPanelItem) obj10).gridTileItem.getUniqueKey())) {
                    arrayList10.add(obj10);
                }
            }
            return CollectionsKt___CollectionsKt.plus((Iterable) arrayList10, (Collection) arrayList4);
        }
        List list6 = (List) anonymousClass1.L$1;
        GridTileInteractor gridTileInteractor3 = (GridTileInteractor) anonymousClass1.L$0;
        ResultKt.throwOnFailure(obj);
        list2 = list6;
        gridTileInteractor2 = gridTileInteractor3;
        tilesToEdit = obj;
        EditTilesModel editTilesModel = (EditTilesModel) tilesToEdit;
        List listPlus = CollectionsKt___CollectionsKt.plus((Iterable) editTilesModel.customTiles, (Collection) editTilesModel.stockTiles);
        ArrayList arrayList11 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listPlus, 10));
        ArrayList arrayList12 = (ArrayList) listPlus;
        int size6 = arrayList12.size();
        int i9 = 0;
        while (i9 < size6) {
            Object obj11 = arrayList12.get(i9);
            i9++;
            EditTileData editTileData = (EditTileData) obj11;
            QSTile tileBySpecString = gridTileInteractor2.currentTilesInteractor.getTileBySpecString(editTileData.tileSpec.getSpec());
            TileSpec tileSpec = editTileData.tileSpec;
            if (tileBySpecString != null) {
                String spec = tileSpec.getSpec();
                if (Intrinsics.areEqual(spec, "Wifi") || Intrinsics.areEqual(spec, "Bluetooth")) {
                    j = (i8 << 32) | (1 & 4294967295L);
                    IntSize.Companion companion = IntSize.Companion;
                } else {
                    long j2 = 1;
                    j = (j2 << 32) | (j2 & 4294967295L);
                    IntSize.Companion companion2 = IntSize.Companion;
                }
                quickTile = new QuickTile(j, null, tileSpec.getSpec(), 0, tileBySpecString, 10, null);
            } else {
                quickTile = new QuickTile(0L, null, tileSpec.getSpec(), 0, null, 27, null);
            }
            arrayList11.add(new QSPanelItem(quickTile, 0, 0, 0L, 0.0f, 0.0f, 0, null, null, null, false, 2046, null));
            i8 = 2;
        }
        GridTileRepository gridTileRepository = gridTileInteractor2.gridTileRepository;
        anonymousClass1.L$0 = gridTileInteractor2;
        anonymousClass1.L$1 = list2;
        anonymousClass1.L$2 = arrayList11;
        anonymousClass1.label = 2;
        ((GridTileRepositoryImpl) gridTileRepository).getClass();
        List listAsList = Arrays.asList(new GridTileData("BrightBar", null, 0, 0, 4, 1, 14, null), new GridTileData("VolumeBar", null, 0, 0, 2, 2, 14, null), new GridTileData("MediaPlayer", null, 0, 0, 4, 1, 14, null), new GridTileData("QuickButton", "NearByDevices", 0, 0, 2, 1, 12, null), new GridTileData("QuickButton", "SmartThings", 0, 0, 2, 1, 12, null));
        if (listAsList != coroutineSingletons) {
            list3 = list2;
            obj = listAsList;
            gridTileInteractor = gridTileInteractor2;
            list4 = arrayList11;
            Iterable iterable2 = (Iterable) obj;
            ArrayList arrayList13 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable2, 10));
            it = iterable2.iterator();
            while (it.hasNext()) {
            }
            List list52 = list3;
            ArrayList arrayList22 = new ArrayList();
            while (r3.hasNext()) {
            }
            ArrayList arrayList32 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList22, 10));
            size = arrayList22.size();
            i = 0;
            while (i < size) {
            }
            Set set4 = CollectionsKt___CollectionsKt.toSet(arrayList32);
            ArrayList arrayList42 = new ArrayList();
            size2 = arrayList13.size();
            i2 = 0;
            while (i2 < size2) {
            }
            ArrayList arrayList52 = new ArrayList();
            while (r5.hasNext()) {
            }
            ArrayList arrayList62 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList52, 10));
            size3 = arrayList52.size();
            i3 = 0;
            while (i3 < size3) {
            }
            Set set22 = CollectionsKt___CollectionsKt.toSet(arrayList62);
            ArrayList arrayList72 = new ArrayList();
            while (r4.hasNext()) {
            }
            ArrayList arrayList82 = new ArrayList();
            size4 = arrayList72.size();
            i4 = 0;
            while (i4 < size4) {
            }
            ArrayList arrayList92 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList82, 10));
            size5 = arrayList82.size();
            i5 = 0;
            while (i5 < size5) {
            }
            Set set32 = CollectionsKt___CollectionsKt.toSet(SetsKt___SetsKt.plus(set22, (Iterable) arrayList92));
            ArrayList arrayList102 = new ArrayList();
            while (r0.hasNext()) {
            }
            return CollectionsKt___CollectionsKt.plus((Iterable) arrayList102, (Collection) arrayList42);
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object loadTiles(ScreenType screenType, ContinuationImpl continuationImpl) {
        C10221 c10221;
        if (continuationImpl instanceof C10221) {
            c10221 = (C10221) continuationImpl;
            int i = c10221.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c10221.label = i - Integer.MIN_VALUE;
            } else {
                c10221 = new C10221(continuationImpl);
            }
        }
        Object objLoadGridTiles = c10221.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c10221.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objLoadGridTiles);
            c10221.L$0 = this;
            c10221.label = 1;
            objLoadGridTiles = ((GridTileRepositoryImpl) this.gridTileRepository).loadGridTiles(screenType, c10221);
            if (objLoadGridTiles == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            this = (GridTileInteractor) c10221.L$0;
            ResultKt.throwOnFailure(objLoadGridTiles);
        }
        final Flow flow = (Flow) objLoadGridTiles;
        return new Flow() { // from class: com.android.systemui.samsung.quicksetting.domain.interactor.GridTileInteractor$loadTiles$$inlined$map$1

            /* renamed from: com.android.systemui.samsung.quicksetting.domain.interactor.GridTileInteractor$loadTiles$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ GridTileInteractor this$0;

                /* renamed from: com.android.systemui.samsung.quicksetting.domain.interactor.GridTileInteractor$loadTiles$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, GridTileInteractor gridTileInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = gridTileInteractor;
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
                        QSPanelItem qSPanelItem = this.this$0.toQSPanelItem((GridTileData) obj);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(qSPanelItem, anonymousClass1) == coroutineSingletons) {
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
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterative(DepthRegionTraversal.java:31)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visit(SwitchOverStringVisitor.java:60)
     */
    public final QSPanelItem toQSPanelItem(GridTileData gridTileData) {
        final int i = 1;
        String str = gridTileData.type;
        int iHashCode = str.hashCode();
        int i2 = gridTileData.spanY;
        int i3 = gridTileData.spanX;
        switch (iHashCode) {
            case -990228965:
                if (str.equals("QuickTile")) {
                    return buildPanelItem(gridTileData, new GridTileInteractor$$ExternalSyntheticLambda0(this, gridTileData));
                }
                break;
            case -699534612:
                if (str.equals("QuickTileDrawer")) {
                    IntOffset.Companion companion = IntOffset.Companion;
                    return new QSPanelItem(new QuickTileDrawer(0L, null, this.tileGridViewModel, 3, null), gridTileData.spanWidth, gridTileData.spanHeight, (i3 << 32) | (i2 & 4294967295L), 0.0f, 0.0f, 0, null, null, null, false, 2032, null);
                }
                break;
            case -644737431:
                if (str.equals("QuickTileFolder")) {
                    return buildPanelItem(gridTileData, new GridTileInteractor$$ExternalSyntheticLambda0(gridTileData, this, i));
                }
                break;
            case -469017959:
                if (str.equals("BrightBar")) {
                    final int i4 = 0;
                    return buildPanelItem(gridTileData, new Function1(this) { // from class: com.android.systemui.samsung.quicksetting.domain.interactor.GridTileInteractor$$ExternalSyntheticLambda3
                        public final /* synthetic */ GridTileInteractor f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            switch (i4) {
                                case 0:
                                    return new BrightBar((SecBrightBarViewModel) this.f$0.brightBarViewModelProvider.get(), 0L, null, 6, null);
                                default:
                                    return new VolumeBar((SecVolumeBarViewModel) this.f$0.volumeBarViewModelProvider.get(), 0L, null, 6, null);
                            }
                        }
                    });
                }
                break;
            case -410815931:
                if (str.equals("Collapser")) {
                    IntOffset.Companion companion2 = IntOffset.Companion;
                    return new QSPanelItem(new Collapser(0L, null, null, 0, null, null, 63, null), gridTileData.spanWidth, gridTileData.spanHeight, (i3 << 32) | (i2 & 4294967295L), 0.0f, 0.0f, 0, null, null, null, false, 2032, null);
                }
                break;
            case -124342663:
                if (str.equals("VolumeBar")) {
                    return buildPanelItem(gridTileData, new Function1(this) { // from class: com.android.systemui.samsung.quicksetting.domain.interactor.GridTileInteractor$$ExternalSyntheticLambda3
                        public final /* synthetic */ GridTileInteractor f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            switch (i) {
                                case 0:
                                    return new BrightBar((SecBrightBarViewModel) this.f$0.brightBarViewModelProvider.get(), 0L, null, 6, null);
                                default:
                                    return new VolumeBar((SecVolumeBarViewModel) this.f$0.volumeBarViewModelProvider.get(), 0L, null, 6, null);
                            }
                        }
                    });
                }
                break;
            case 1236935621:
                if (str.equals("MediaPlayer")) {
                    IntOffset.Companion companion3 = IntOffset.Companion;
                    return new QSPanelItem(new MediaPlayer(0L, null, 3, null), gridTileData.spanWidth, gridTileData.spanHeight, (i3 << 32) | (i2 & 4294967295L), 0.0f, 0.0f, 0, null, null, null, false, 2032, null);
                }
                break;
            case 1368718175:
                if (str.equals("QuickButton")) {
                    return buildPanelItem(gridTileData, new GridTileInteractor$$ExternalSyntheticLambda0(gridTileData, this, 2));
                }
                break;
        }
        IntOffset.Companion companion4 = IntOffset.Companion;
        return new QSPanelItem(new Collapser(0L, null, null, 0, null, null, 63, null), gridTileData.spanWidth, gridTileData.spanHeight, (i3 << 32) | (i2 & 4294967295L), 0.0f, 0.0f, 0, null, null, null, false, 2032, null);
    }
}
