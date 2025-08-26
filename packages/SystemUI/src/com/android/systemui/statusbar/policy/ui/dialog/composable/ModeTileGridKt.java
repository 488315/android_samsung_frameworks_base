package com.android.systemui.statusbar.policy.ui.dialog.composable;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.lazy.grid.GridCells;
import androidx.compose.foundation.lazy.grid.LazyGridDslKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.Dp;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModesDialogViewModel;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
public abstract class ModeTileGridKt {
    /* JADX WARN: Removed duplicated region for block: B:19:0x0086  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void ModeTileGrid(final ModesDialogViewModel modesDialogViewModel, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(708429859);
        if ((((composerImpl.changedInstance(modesDialogViewModel) ? 4 : 2) | i) & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.statusbar.policy.ui.dialog.composable.ModeTileGrid (ModeTileGrid.kt:40)");
            }
            MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(modesDialogViewModel.tiles, EmptyList.INSTANCE, composerImpl, 48);
            composerImpl.startReplaceGroup(1281722002);
            GridCells.Fixed fixed = new GridCells.Fixed(1);
            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(Modifier.Companion, 1.0f);
            float f = IKnoxCustomManager.Stub.TRANSACTION_getForceSingleView;
            Dp.Companion companion = Dp.Companion;
            Modifier modifierM133heightInVpY3zN4$default = SizeKt.m133heightInVpY3zN4$default(modifierFillMaxWidth, 0.0f, f, 1);
            float f2 = 8;
            Arrangement.INSTANCE.getClass();
            Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_4 = Arrangement.m92spacedBy0680j_4(f2);
            Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_42 = Arrangement.m92spacedBy0680j_4(f2);
            composerImpl.startReplaceGroup(1149733109);
            boolean zChanged = composerImpl.changed(mutableStateCollectAsStateWithLifecycle);
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!zChanged) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new ModeTileGridKt$$ExternalSyntheticLambda0(mutableStateCollectAsStateWithLifecycle, 0);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                LazyGridDslKt.LazyVerticalGrid(1769520, 0, 924, null, null, spacedAlignedM92spacedBy0680j_42, spacedAlignedM92spacedBy0680j_4, null, fixed, null, composerImpl, modifierM133heightInVpY3zN4$default, (Function1) objRememberedValue, false, false);
                composerImpl.end(false);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(i) { // from class: com.android.systemui.statusbar.policy.ui.dialog.composable.ModeTileGridKt$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    ModeTileGridKt.ModeTileGrid(this.f$0, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
