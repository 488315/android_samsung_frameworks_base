package com.android.systemui.volume.panel.component.spatialaudio.ui.composable;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class SpatialAudioComponent$Content$buttonComponent$1$1 extends FunctionReferenceImpl implements Function2 {
    public SpatialAudioComponent$Content$buttonComponent$1$1(Object obj) {
        super(2, obj, SpatialAudioPopup.class, "show", "show(Lcom/android/systemui/animation/Expandable;I)V", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Expandable expandable = (Expandable) obj;
        int intValue = ((Number) obj2).intValue();
        final SpatialAudioPopup spatialAudioPopup = (SpatialAudioPopup) this.receiver;
        UiEventLogger uiEventLogger = spatialAudioPopup.uiEventLogger;
        VolumePanelUiEvent volumePanelUiEvent = VolumePanelUiEvent.VOLUME_PANEL_SPATIAL_AUDIO_POP_UP_SHOWN;
        Iterator it = ((List) spatialAudioPopup.viewModel.spatialAudioButtons.$$delegate_0.getValue()).iterator();
        int i = 0;
        while (true) {
            if (!it.hasNext()) {
                i = -1;
                break;
            }
            if (((SpatialAudioButtonViewModel) it.next()).button.isActive) {
                break;
            }
            i++;
        }
        uiEventLogger.logWithPosition(volumePanelUiEvent, 0, (String) null, i);
        spatialAudioPopup.volumePanelPopup.show(expandable, intValue | 80, new ComposableLambdaImpl(1544071836, true, new Function3() { // from class: com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioPopup$show$2
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj3, Object obj4, Object obj5) {
                Composer composer = (Composer) obj4;
                ((Number) obj5).intValue();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioPopup.show.<anonymous> (SpatialAudioPopup.kt:60)");
                }
                SpatialAudioPopup.this.Title(0, composer);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                return Unit.INSTANCE;
            }
        }), new ComposableLambdaImpl(582446621, true, new Function3() { // from class: com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioPopup$show$3
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj3, Object obj4, Object obj5) {
                SystemUIDialog systemUIDialog = (SystemUIDialog) obj3;
                Composer composer = (Composer) obj4;
                int intValue2 = ((Number) obj5).intValue();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioPopup.show.<anonymous> (SpatialAudioPopup.kt:60)");
                }
                SpatialAudioPopup.this.Content(systemUIDialog, composer, intValue2 & 14);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                return Unit.INSTANCE;
            }
        }));
        return Unit.INSTANCE;
    }
}
