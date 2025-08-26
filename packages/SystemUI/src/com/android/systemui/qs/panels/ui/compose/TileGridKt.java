package com.android.systemui.qs.panels.ui.compose;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.Modifier;
import com.android.compose.animation.scene.ContentScope;
import com.android.compose.ui.graphics.DrawInContainerNode$$ExternalSyntheticLambda1;
import com.android.systemui.qs.panels.ui.viewmodel.TileGridViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.TileViewModel;
import com.android.systemui.qs.pipeline.domain.model.TileModel;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public abstract class TileGridKt {
    public static final void TileGrid(ContentScope contentScope, final TileGridViewModel tileGridViewModel, final Modifier modifier, Function0 function0, Composer composer, final int i, final int i2) {
        ContentScope contentScope2;
        final Function0 function02;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(705899080);
        int i3 = (i & 6) == 0 ? (composerImpl.changed(contentScope) ? 4 : 2) | i : i;
        if ((i & 48) == 0) {
            i3 |= composerImpl.changedInstance(tileGridViewModel) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i3 |= composerImpl.changed(modifier) ? 256 : 128;
        }
        int i4 = i2 & 4;
        if (i4 != 0) {
            i3 |= 3072;
        } else if ((i & 3072) == 0) {
            i3 |= composerImpl.changedInstance(function0) ? 2048 : 1024;
        }
        if ((i3 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            contentScope2 = contentScope;
            function02 = function0;
        } else {
            if (i4 != 0) {
                composerImpl.startReplaceGroup(-1940010048);
                Object objRememberedValue = composerImpl.rememberedValue();
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new DrawInContainerNode$$ExternalSyntheticLambda1();
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                function0 = (Function0) objRememberedValue;
                composerImpl.end(false);
            }
            Function0 function03 = function0;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.TileGrid (TileGrid.kt:28)");
            }
            GridLayout gridLayout = (GridLayout) ((SnapshotMutableStateImpl) tileGridViewModel.gridLayout$delegate).getValue();
            List<TileModel> list = (List) ((SnapshotMutableStateImpl) tileGridViewModel.tileModels$delegate).getValue();
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
            for (TileModel tileModel : list) {
                arrayList.add(new TileViewModel(tileModel.tile, tileModel.spec));
            }
            contentScope2 = contentScope;
            gridLayout.TileGrid(contentScope2, arrayList, function03, composerImpl, i3 & 8078);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            function02 = function03;
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            final ContentScope contentScope3 = contentScope2;
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.qs.panels.ui.compose.TileGridKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    Function0 function04 = function02;
                    TileGridKt.TileGrid(contentScope3, tileGridViewModel, modifier, function04, (Composer) obj, iUpdateChangedFlags, i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
