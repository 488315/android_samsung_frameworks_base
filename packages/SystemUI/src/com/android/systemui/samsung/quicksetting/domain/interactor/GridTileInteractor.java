package com.android.systemui.samsung.quicksetting.domain.interactor;

import androidx.compose.ui.unit.IntOffset;
import com.android.systemui.R;
import com.android.systemui.pluginlock.component.PluginLockShortcutTask;
import com.android.systemui.qs.panels.domain.interactor.EditTilesListInteractor;
import com.android.systemui.qs.panels.ui.viewmodel.TileGridViewModel;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor;
import com.android.systemui.samsung.quicksetting.domain.model.GridTileData;
import com.android.systemui.samsung.quicksetting.domain.model.QSPanelItem;
import com.android.systemui.samsung.quicksetting.domain.model.items.BrightBar;
import com.android.systemui.samsung.quicksetting.domain.model.items.Collapser;
import com.android.systemui.samsung.quicksetting.domain.model.items.GridTileItem;
import com.android.systemui.samsung.quicksetting.domain.model.items.MediaPlayer;
import com.android.systemui.samsung.quicksetting.domain.model.items.QuickTileDrawer;
import com.android.systemui.samsung.quicksetting.domain.model.items.VolumeBar;
import com.android.systemui.samsung.quicksetting.domain.repository.GridTileRepository;
import com.android.systemui.samsung.quicksetting.ui.panel.SecBrightBarViewModel;
import com.android.systemui.samsung.quicksetting.ui.panel.SecVolumeBarViewModel;
import javax.inject.Provider;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class GridTileInteractor {
    public final Provider brightBarViewModelProvider;
    public final CurrentTilesInteractor currentTilesInteractor;
    public final EditTilesListInteractor editTilesListInteractor;
    public final GridTileRepository gridTileRepository;
    public final TileGridViewModel tileGridViewModel;
    public final Provider volumeBarViewModelProvider;

    public GridTileInteractor(GridTileRepository gridTileRepository, EditTilesListInteractor editTilesListInteractor, CurrentTilesInteractor currentTilesInteractor, TileGridViewModel.Factory factory, Provider provider, Provider provider2) {
        this.gridTileRepository = gridTileRepository;
        this.editTilesListInteractor = editTilesListInteractor;
        this.currentTilesInteractor = currentTilesInteractor;
        this.brightBarViewModelProvider = provider;
        this.volumeBarViewModelProvider = provider2;
        this.tileGridViewModel = factory.create();
    }

    public static QSPanelItem buildPanelItem(GridTileData gridTileData, Function1 function1) {
        GridTileItem gridTileItem = (GridTileItem) function1.mo779invoke(gridTileData);
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

    /* JADX WARN: Code restructure failed: missing block: B:118:0x0068, code lost:
    
        if (r5 == r4) goto L36;
     */
    /* JADX WARN: Removed duplicated region for block: B:116:0x019a  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x01b3 A[LOOP:0: B:12:0x01ad->B:14:0x01b3, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x01d6  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0208 A[LOOP:2: B:32:0x0206->B:33:0x0208, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0229  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0250  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0277 A[LOOP:5: B:57:0x0275->B:58:0x0277, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x029b  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x02be A[LOOP:7: B:72:0x02bc->B:73:0x02be, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x02e1 A[LOOP:8: B:76:0x02df->B:77:0x02e1, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0307  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0028  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x008c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object loadAvailableTiles(java.util.List r42, kotlin.coroutines.jvm.internal.ContinuationImpl r43) {
        /*
            Method dump skipped, instructions count: 803
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.samsung.quicksetting.domain.interactor.GridTileInteractor.loadAvailableTiles(java.util.List, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object loadTiles(com.android.systemui.samsung.quicksetting.ui.panel.ScreenType r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.systemui.samsung.quicksetting.domain.interactor.GridTileInteractor$loadTiles$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.samsung.quicksetting.domain.interactor.GridTileInteractor$loadTiles$1 r0 = (com.android.systemui.samsung.quicksetting.domain.interactor.GridTileInteractor$loadTiles$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.samsung.quicksetting.domain.interactor.GridTileInteractor$loadTiles$1 r0 = new com.android.systemui.samsung.quicksetting.domain.interactor.GridTileInteractor$loadTiles$1
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r4 = r0.L$0
            com.android.systemui.samsung.quicksetting.domain.interactor.GridTileInteractor r4 = (com.android.systemui.samsung.quicksetting.domain.interactor.GridTileInteractor) r4
            kotlin.ResultKt.throwOnFailure(r6)
            goto L45
        L2b:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L33:
            kotlin.ResultKt.throwOnFailure(r6)
            r0.L$0 = r4
            r0.label = r3
            com.android.systemui.samsung.quicksetting.domain.repository.GridTileRepository r6 = r4.gridTileRepository
            com.android.systemui.samsung.quicksetting.data.repository.GridTileRepositoryImpl r6 = (com.android.systemui.samsung.quicksetting.data.repository.GridTileRepositoryImpl) r6
            java.lang.Object r6 = r6.loadGridTiles(r5, r0)
            if (r6 != r1) goto L45
            return r1
        L45:
            kotlinx.coroutines.flow.Flow r6 = (kotlinx.coroutines.flow.Flow) r6
            com.android.systemui.samsung.quicksetting.domain.interactor.GridTileInteractor$loadTiles$$inlined$map$1 r5 = new com.android.systemui.samsung.quicksetting.domain.interactor.GridTileInteractor$loadTiles$$inlined$map$1
            r5.<init>()
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.samsung.quicksetting.domain.interactor.GridTileInteractor.loadTiles(com.android.systemui.samsung.quicksetting.ui.panel.ScreenType, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
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
        int hashCode = str.hashCode();
        int i2 = gridTileData.spanY;
        int i3 = gridTileData.spanX;
        switch (hashCode) {
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
                        public final Object mo779invoke(Object obj) {
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
                        public final Object mo779invoke(Object obj) {
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
