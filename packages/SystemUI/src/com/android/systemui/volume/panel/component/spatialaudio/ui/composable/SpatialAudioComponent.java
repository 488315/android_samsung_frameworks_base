package com.android.systemui.volume.panel.component.spatialaudio.ui.composable;

import com.android.systemui.volume.panel.component.spatial.ui.viewmodel.SpatialAudioViewModel;
import com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class SpatialAudioComponent implements ComposeVolumePanelUiComponent {
    public final SpatialAudioPopup popup;
    public final SpatialAudioViewModel viewModel;

    public SpatialAudioComponent(SpatialAudioViewModel spatialAudioViewModel, SpatialAudioPopup spatialAudioPopup) {
        this.viewModel = spatialAudioViewModel;
        this.popup = spatialAudioPopup;
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0033, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L6;
     */
    @Override // com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void Content(final com.android.systemui.volume.panel.ui.composable.VolumePanelComposeScope r5, final androidx.compose.ui.Modifier r6, androidx.compose.runtime.Composer r7, final int r8) {
        /*
            r4 = this;
            androidx.compose.runtime.ComposerImpl r7 = (androidx.compose.runtime.ComposerImpl) r7
            r0 = -986021957(0xffffffffc53a7fbb, float:-2983.9832)
            r7.startRestartGroup(r0)
            androidx.compose.runtime.OpaqueKey r0 = androidx.compose.runtime.ComposerKt.invocation
            com.android.systemui.volume.panel.component.spatial.ui.viewmodel.SpatialAudioViewModel r0 = r4.viewModel
            kotlinx.coroutines.flow.ReadonlyStateFlow r1 = r0.shouldUsePopup
            androidx.compose.runtime.MutableState r1 = androidx.lifecycle.compose.FlowExtKt.collectAsStateWithLifecycle(r1, r7)
            java.lang.Object r2 = r1.getValue()
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            r3 = 933975511(0x37ab55d7, float:2.042476E-5)
            r7.startReplaceGroup(r3)
            boolean r2 = r7.changed(r2)
            java.lang.Object r3 = r7.rememberedValue()
            if (r2 != 0) goto L35
            androidx.compose.runtime.Composer$Companion r2 = androidx.compose.runtime.Composer.Companion
            r2.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r2 = androidx.compose.runtime.Composer.Companion.Empty
            if (r3 != r2) goto L5f
        L35:
            java.lang.Object r1 = r1.getValue()
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            kotlinx.coroutines.flow.ReadonlyStateFlow r0 = r0.spatialAudioButton
            if (r1 == 0) goto L51
            com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent r1 = new com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent
            com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioComponent$Content$buttonComponent$1$1 r2 = new com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioComponent$Content$buttonComponent$1$1
            com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioPopup r3 = r4.popup
            r2.<init>(r3)
            r1.<init>(r0, r2)
        L4f:
            r3 = r1
            goto L5c
        L51:
            com.android.systemui.volume.panel.component.button.ui.composable.ToggleButtonComponent r1 = new com.android.systemui.volume.panel.component.button.ui.composable.ToggleButtonComponent
            com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioComponent$Content$buttonComponent$1$2 r2 = new com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioComponent$Content$buttonComponent$1$2
            r2.<init>()
            r1.<init>(r0, r2)
            goto L4f
        L5c:
            r7.updateRememberedValue(r3)
        L5f:
            com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent r3 = (com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent) r3
            r0 = 0
            r7.end(r0)
            r0 = r8 & 126(0x7e, float:1.77E-43)
            r3.Content(r5, r6, r7, r0)
            androidx.compose.runtime.RecomposeScopeImpl r7 = r7.endRestartGroup()
            if (r7 == 0) goto L77
            com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioComponent$Content$2 r0 = new com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioComponent$Content$2
            r0.<init>()
            r7.block = r0
        L77:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioComponent.Content(com.android.systemui.volume.panel.ui.composable.VolumePanelComposeScope, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int):void");
    }
}
