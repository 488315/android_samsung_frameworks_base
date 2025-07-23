package com.android.systemui.qs.panels.ui.viewmodel;

import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import com.android.systemui.haptics.msdl.qs.TileHapticsViewModelFactoryProvider;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.panels.domain.interactor.QuickQuickSettingsRowInteractor;
import com.android.systemui.qs.panels.shared.model.SizedTileImpl;
import com.android.systemui.qs.panels.shared.model.TileRowKt;
import com.android.systemui.qs.panels.ui.viewmodel.MediaInRowInLandscapeViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.QSColumnsViewModel;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor;
import com.android.systemui.qs.pipeline.domain.model.TileModel;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function0;
import kotlin.sequences.DropTakeSequence;
import kotlin.sequences.EmptySequence;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TakeSequence;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QuickQuickSettingsViewModel extends ExclusiveActivatable {
    public final State currentTiles$delegate;
    public final Hydrator hydrator;
    public final State largeTiles$delegate;
    public final State largeTilesSpan$delegate;
    public final MediaInRowInLandscapeViewModel mediaInRowViewModel;
    public final QSColumnsViewModel qsColumnsViewModel;
    public final State rowsWithoutMedia$delegate;
    public final TileSquishinessViewModel squishinessViewModel;
    public final TileHapticsViewModelFactoryProvider tileHapticsViewModelFactoryProvider;
    public final State tileViewModels$delegate;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        QuickQuickSettingsViewModel create();
    }

    public QuickQuickSettingsViewModel(CurrentTilesInteractor currentTilesInteractor, QSColumnsViewModel.Factory factory, QuickQuickSettingsRowInteractor quickQuickSettingsRowInteractor, MediaInRowInLandscapeViewModel.Factory factory2, TileSquishinessViewModel tileSquishinessViewModel, IconTilesViewModel iconTilesViewModel, TileHapticsViewModelFactoryProvider tileHapticsViewModelFactoryProvider) {
        this.squishinessViewModel = tileSquishinessViewModel;
        this.tileHapticsViewModelFactoryProvider = tileHapticsViewModelFactoryProvider;
        Hydrator hydrator = new Hydrator("QuickQuickSettingsViewModel", null, 2, null);
        this.hydrator = hydrator;
        this.qsColumnsViewModel = factory.create(1);
        this.mediaInRowViewModel = factory2.create(1);
        this.largeTiles$delegate = hydrator.hydratedStateOf(iconTilesViewModel.getLargeTiles(), "largeTiles");
        quickQuickSettingsRowInteractor.getClass();
        this.rowsWithoutMedia$delegate = hydrator.hydratedStateOf("rowsWithoutMedia", 2, quickQuickSettingsRowInteractor.rows);
        this.largeTilesSpan$delegate = hydrator.hydratedStateOf(iconTilesViewModel.getLargeTilesSpan(), "largeTilesSpan");
        this.currentTiles$delegate = hydrator.hydratedStateOf(currentTilesInteractor.getCurrentTiles(), "currentTiles");
        this.tileViewModels$delegate = SnapshotStateKt.derivedStateOf(new Function0() { // from class: com.android.systemui.qs.panels.ui.viewmodel.QuickQuickSettingsViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                QuickQuickSettingsViewModel quickQuickSettingsViewModel = QuickQuickSettingsViewModel.this;
                List<TileModel> list = (List) ((SnapshotMutableStateImpl) quickQuickSettingsViewModel.currentTiles$delegate).getValue();
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                for (TileModel tileModel : list) {
                    QSTile qSTile = tileModel.tile;
                    TileSpec tileSpec = tileModel.spec;
                    arrayList.add(new SizedTileImpl(new TileViewModel(qSTile, tileSpec), ((Set) ((SnapshotMutableStateImpl) quickQuickSettingsViewModel.largeTiles$delegate).getValue()).contains(tileSpec) ? ((Number) ((SnapshotMutableStateImpl) quickQuickSettingsViewModel.largeTilesSpan$delegate).getValue()).intValue() : 1));
                }
                Sequence splitInRowsSequence = TileRowKt.splitInRowsSequence(quickQuickSettingsViewModel.qsColumnsViewModel.getColumns(), arrayList);
                boolean shouldMediaShowInRow = quickQuickSettingsViewModel.mediaInRowViewModel.getShouldMediaShowInRow();
                State state = quickQuickSettingsViewModel.rowsWithoutMedia$delegate;
                int intValue = shouldMediaShowInRow ? ((Number) ((SnapshotMutableStateImpl) state).getValue()).intValue() * 2 : ((Number) ((SnapshotMutableStateImpl) state).getValue()).intValue();
                if (intValue >= 0) {
                    return CollectionsKt__IterablesKt.flatten(SequencesKt___SequencesKt.toList(intValue == 0 ? EmptySequence.INSTANCE : splitInRowsSequence instanceof DropTakeSequence ? ((DropTakeSequence) splitInRowsSequence).take(intValue) : new TakeSequence(splitInRowsSequence, intValue)));
                }
                throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(intValue, "Requested element count ", " is less than zero.").toString());
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.qs.panels.ui.viewmodel.QuickQuickSettingsViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.qs.panels.ui.viewmodel.QuickQuickSettingsViewModel$onActivated$1 r0 = (com.android.systemui.qs.panels.ui.viewmodel.QuickQuickSettingsViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.qs.panels.ui.viewmodel.QuickQuickSettingsViewModel$onActivated$1 r0 = new com.android.systemui.qs.panels.ui.viewmodel.QuickQuickSettingsViewModel$onActivated$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L41
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.qs.panels.ui.viewmodel.QuickQuickSettingsViewModel$onActivated$2 r5 = new com.android.systemui.qs.panels.ui.viewmodel.QuickQuickSettingsViewModel$onActivated$2
            r2 = 0
            r5.<init>(r4, r2)
            r0.label = r3
            java.lang.Object r4 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r5, r0)
            if (r4 != r1) goto L41
            return r1
        L41:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.ui.viewmodel.QuickQuickSettingsViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
