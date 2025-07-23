package com.android.systemui.volume.panel.component.spatialaudio.ui.composable;

import com.android.systemui.volume.panel.component.spatial.ui.viewmodel.SpatialAudioViewModel;
import com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SpatialAudioComponent implements ComposeVolumePanelUiComponent {
    public final SpatialAudioPopup popup;
    public final SpatialAudioViewModel viewModel;

    public SpatialAudioComponent(SpatialAudioViewModel spatialAudioViewModel, SpatialAudioPopup spatialAudioPopup) {
        this.viewModel = spatialAudioViewModel;
        this.popup = spatialAudioPopup;
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x003c, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
     */
    @Override // com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void Content(com.android.systemui.volume.panel.ui.composable.VolumePanelComposeScope r5, androidx.compose.ui.Modifier r6, androidx.compose.runtime.Composer r7, int r8) {
        /*
            r4 = this;
            androidx.compose.runtime.ComposerImpl r7 = (androidx.compose.runtime.ComposerImpl) r7
            r0 = -986021957(0xffffffffc53a7fbb, float:-2983.9832)
            r7.startReplaceGroup(r0)
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L13
            java.lang.String r0 = "com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioComponent.Content (SpatialAudioComponent.kt:40)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        L13:
            com.android.systemui.volume.panel.component.spatial.ui.viewmodel.SpatialAudioViewModel r0 = r4.viewModel
            kotlinx.coroutines.flow.ReadonlyStateFlow r1 = r0.shouldUsePopup
            androidx.compose.runtime.MutableState r1 = androidx.lifecycle.compose.FlowExtKt.collectAsStateWithLifecycle(r1, r7)
            java.lang.Object r2 = r1.getValue()
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            r3 = -1111527653(0xffffffffbdbf6f1b, float:-0.093473636)
            r7.startReplaceGroup(r3)
            boolean r2 = r7.changed(r2)
            java.lang.Object r3 = r7.rememberedValue()
            if (r2 != 0) goto L3e
            androidx.compose.runtime.Composer$Companion r2 = androidx.compose.runtime.Composer.Companion
            r2.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r2 = androidx.compose.runtime.Composer.Companion.Empty
            if (r3 != r2) goto L68
        L3e:
            java.lang.Object r1 = r1.getValue()
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            kotlinx.coroutines.flow.ReadonlyStateFlow r0 = r0.spatialAudioButton
            if (r1 == 0) goto L5a
            com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent r1 = new com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent
            com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioComponent$Content$buttonComponent$1$1 r2 = new com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioComponent$Content$buttonComponent$1$1
            com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioPopup r4 = r4.popup
            r2.<init>(r4)
            r1.<init>(r0, r2)
        L58:
            r3 = r1
            goto L65
        L5a:
            com.android.systemui.volume.panel.component.button.ui.composable.ToggleButtonComponent r1 = new com.android.systemui.volume.panel.component.button.ui.composable.ToggleButtonComponent
            com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioComponent$$ExternalSyntheticLambda0 r2 = new com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioComponent$$ExternalSyntheticLambda0
            r2.<init>()
            r1.<init>(r0, r2)
            goto L58
        L65:
            r7.updateRememberedValue(r3)
        L68:
            com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent r3 = (com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent) r3
            r4 = 0
            r7.end(r4)
            r8 = r8 & 126(0x7e, float:1.77E-43)
            r3.Content(r5, r6, r7, r8)
            boolean r5 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r5 == 0) goto L7c
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L7c:
            r7.end(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioComponent.Content(com.android.systemui.volume.panel.ui.composable.VolumePanelComposeScope, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int):void");
    }
}
