package com.android.systemui.volume.panel.component.spatialaudio.ui.composable;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.animation.Expandable;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.volume.panel.component.spatial.ui.viewmodel.SpatialAudioButtonViewModel;
import com.android.systemui.volume.panel.ui.VolumePanelUiEvent;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;

final /* synthetic */ class SpatialAudioComponent$Content$buttonComponent$1$1 extends FunctionReferenceImpl implements Function2 {
    public SpatialAudioComponent$Content$buttonComponent$1$1(Object obj) {
        super(2, obj, SpatialAudioPopup.class, "show", "show(Lcom/android/systemui/animation/Expandable;I)V", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        invoke((Expandable) obj, ((Number) obj2).intValue());
        return Unit.INSTANCE;
    }

    public final void invoke(Expandable expandable, int i) {
        final SpatialAudioPopup spatialAudioPopup = (SpatialAudioPopup) this.receiver;
        UiEventLogger uiEventLogger = spatialAudioPopup.uiEventLogger;
        VolumePanelUiEvent volumePanelUiEvent = VolumePanelUiEvent.VOLUME_PANEL_SPATIAL_AUDIO_POP_UP_SHOWN;
        Iterator it = ((List) spatialAudioPopup.viewModel.spatialAudioButtons.$$delegate_0.getValue()).iterator();
        int i2 = 0;
        while (true) {
            if (!it.hasNext()) {
                i2 = -1;
                break;
            } else if (((SpatialAudioButtonViewModel) it.next()).button.isActive) {
                break;
            } else {
                i2++;
            }
        }
        uiEventLogger.logWithPosition(volumePanelUiEvent, 0, (String) null, i2);
        spatialAudioPopup.volumePanelPopup.show(expandable, i | 80, new ComposableLambdaImpl(1544071836, true, new Function3() { // from class: com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioPopup$show$2
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                ((Number) obj3).intValue();
                OpaqueKey opaqueKey = ComposerKt.invocation;
                SpatialAudioPopup.access$Title(SpatialAudioPopup.this, (Composer) obj2, 8);
                return Unit.INSTANCE;
            }
        }), new ComposableLambdaImpl(582446621, true, new Function3() { // from class: com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioPopup$show$3
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                ((Number) obj3).intValue();
                OpaqueKey opaqueKey = ComposerKt.invocation;
                SpatialAudioPopup.this.Content((SystemUIDialog) obj, (Composer) obj2, 72);
                return Unit.INSTANCE;
            }
        }));
    }
}
