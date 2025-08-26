package com.android.systemui.volume.panel.component.spatialaudio.ui.composable;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.ui.Modifier;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent;
import com.android.systemui.volume.panel.component.button.ui.composable.ToggleButtonComponent;
import com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioEnabledModel;
import com.android.systemui.volume.panel.component.spatial.ui.viewmodel.SpatialAudioViewModel;
import com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent;
import com.android.systemui.volume.panel.ui.composable.VolumePanelComposeScope;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes3.dex */
public final class SpatialAudioComponent implements ComposeVolumePanelUiComponent {
    public final SpatialAudioPopup popup;
    public final SpatialAudioViewModel viewModel;

    public SpatialAudioComponent(SpatialAudioViewModel spatialAudioViewModel, SpatialAudioPopup spatialAudioPopup) {
        this.viewModel = spatialAudioViewModel;
        this.popup = spatialAudioPopup;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x003e  */
    @Override // com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void Content(VolumePanelComposeScope volumePanelComposeScope, Modifier modifier, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-986021957);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioComponent.Content (SpatialAudioComponent.kt:40)");
        }
        SpatialAudioViewModel spatialAudioViewModel = this.viewModel;
        MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(spatialAudioViewModel.shouldUsePopup, composerImpl);
        boolean zBooleanValue = ((Boolean) mutableStateCollectAsStateWithLifecycle.getValue()).booleanValue();
        composerImpl.startReplaceGroup(-1111527653);
        boolean zChanged = composerImpl.changed(zBooleanValue);
        Object objRememberedValue = composerImpl.rememberedValue();
        if (!zChanged) {
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                boolean zBooleanValue2 = ((Boolean) mutableStateCollectAsStateWithLifecycle.getValue()).booleanValue();
                ReadonlyStateFlow readonlyStateFlow = spatialAudioViewModel.spatialAudioButton;
                objRememberedValue = zBooleanValue2 ? new ButtonComponent(readonlyStateFlow, new SpatialAudioComponent$Content$buttonComponent$1$1(this.popup)) : new ToggleButtonComponent(readonlyStateFlow, new Function1() { // from class: com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioComponent$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        boolean zBooleanValue3 = ((Boolean) obj).booleanValue();
                        SpatialAudioComponent spatialAudioComponent = this.f$0;
                        if (zBooleanValue3) {
                            spatialAudioComponent.viewModel.setEnabled(SpatialAudioEnabledModel.SpatialAudioEnabled.Companion);
                        } else {
                            spatialAudioComponent.viewModel.setEnabled(SpatialAudioEnabledModel.Disabled.INSTANCE);
                        }
                        return Unit.INSTANCE;
                    }
                });
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        composerImpl.end(false);
        ((ComposeVolumePanelUiComponent) objRememberedValue).Content(volumePanelComposeScope, modifier, composerImpl, i & 126);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
    }
}
