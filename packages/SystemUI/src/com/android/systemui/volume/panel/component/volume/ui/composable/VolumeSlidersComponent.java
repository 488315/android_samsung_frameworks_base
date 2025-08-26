package com.android.systemui.volume.panel.component.volume.ui.composable;

import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.ui.Modifier;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.compose.PlatformSliderDefaults;
import com.android.systemui.volume.panel.component.volume.ui.viewmodel.AudioVolumeComponentViewModel;
import com.android.systemui.volume.panel.component.volume.ui.viewmodel.SlidersExpandableViewModel;
import com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent;
import com.android.systemui.volume.panel.ui.composable.VolumePanelComposeScope;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.KFunction;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* loaded from: classes3.dex */
public final class VolumeSlidersComponent implements ComposeVolumePanelUiComponent {
    public final AudioVolumeComponentViewModel viewModel;

    public VolumeSlidersComponent(AudioVolumeComponentViewModel audioVolumeComponentViewModel) {
        this.viewModel = audioVolumeComponentViewModel;
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x00cb  */
    @Override // com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void Content(VolumePanelComposeScope volumePanelComposeScope, Modifier modifier, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1711119471);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSlidersComponent.Content (VolumeSlidersComponent.kt:39)");
        }
        AudioVolumeComponentViewModel audioVolumeComponentViewModel = this.viewModel;
        MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(audioVolumeComponentViewModel.sliderViewModels, composerImpl);
        if (((List) mutableStateCollectAsStateWithLifecycle.getValue()).isEmpty()) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
            return;
        }
        if (volumePanelComposeScope.state.isLargeScreen) {
            composerImpl.startReplaceGroup(-18787593);
            List list = (List) mutableStateCollectAsStateWithLifecycle.getValue();
            PlatformSliderDefaults.INSTANCE.getClass();
            GridVolumeSlidersKt.GridVolumeSliders(list, PlatformSliderDefaults.defaultPlatformSliderColors(composerImpl), SizeKt.fillMaxWidth(modifier, 1.0f), composerImpl, 0);
            composerImpl.end(false);
        } else {
            composerImpl.startReplaceGroup(-18522574);
            MutableState mutableStateCollectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(volumePanelComposeScope.state.orientation == 1 ? audioVolumeComponentViewModel.portraitExpandable : new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(SlidersExpandableViewModel.Fixed.INSTANCE), SlidersExpandableViewModel.Unavailable.INSTANCE, composerImpl, 48);
            if (((SlidersExpandableViewModel) mutableStateCollectAsStateWithLifecycle2.getValue()) instanceof SlidersExpandableViewModel.Unavailable) {
                composerImpl.end(false);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                return;
            }
            SlidersExpandableViewModel slidersExpandableViewModel = (SlidersExpandableViewModel) mutableStateCollectAsStateWithLifecycle2.getValue();
            SlidersExpandableViewModel.Expandable expandable = slidersExpandableViewModel instanceof SlidersExpandableViewModel.Expandable ? (SlidersExpandableViewModel.Expandable) slidersExpandableViewModel : null;
            boolean z = expandable != null ? expandable.isExpanded : true;
            List list2 = (List) mutableStateCollectAsStateWithLifecycle.getValue();
            composerImpl.startReplaceGroup(-1663146089);
            boolean zChangedInstance = composerImpl.changedInstance(audioVolumeComponentViewModel);
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!zChangedInstance) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new VolumeSlidersComponent$Content$1$1(audioVolumeComponentViewModel);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                PlatformSliderDefaults.INSTANCE.getClass();
                ColumnVolumeSlidersKt.ColumnVolumeSliders(list2, z, (Function1) ((KFunction) objRememberedValue), PlatformSliderDefaults.defaultPlatformSliderColors(composerImpl), ((SlidersExpandableViewModel) mutableStateCollectAsStateWithLifecycle2.getValue()) instanceof SlidersExpandableViewModel.Expandable, SizeKt.fillMaxWidth(modifier, 1.0f), composerImpl, 0);
                composerImpl.end(false);
            }
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
    }
}
