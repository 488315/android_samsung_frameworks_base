package com.android.systemui.volume.panel.component.anc.ui.composable;

import androidx.compose.foundation.BasicMarqueeKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.style.TextAlign;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.volume.panel.component.anc.ui.viewmodel.AncViewModel;
import com.android.systemui.volume.panel.component.popup.ui.composable.VolumePanelPopup;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AncPopup {
    public final UiEventLogger uiEventLogger;
    public final AncViewModel viewModel;
    public final VolumePanelPopup volumePanelPopup;

    public AncPopup(VolumePanelPopup volumePanelPopup, AncViewModel ancViewModel, UiEventLogger uiEventLogger) {
        this.volumePanelPopup = volumePanelPopup;
        this.viewModel = ancViewModel;
        this.uiEventLogger = uiEventLogger;
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x0080, code lost:
    
        if (r0 == androidx.compose.runtime.Composer.Companion.Empty) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00de, code lost:
    
        if (r0 == androidx.compose.runtime.Composer.Companion.Empty) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0132, code lost:
    
        if (r6 == androidx.compose.runtime.Composer.Companion.Empty) goto L58;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void Content(final com.android.systemui.statusbar.phone.SystemUIDialog r8, androidx.compose.runtime.Composer r9, final int r10) {
        /*
            Method dump skipped, instructions count: 357
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.anc.ui.composable.AncPopup.Content(com.android.systemui.statusbar.phone.SystemUIDialog, androidx.compose.runtime.Composer, int):void");
    }

    public final void Title(final int i, Composer composer) {
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1967543205);
        if ((i & 1) == 0 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.anc.ui.composable.AncPopup.Title (AncPopup.kt:57)");
            }
            Modifier m27basicMarquee1Mj1MLw$default = BasicMarqueeKt.m27basicMarquee1Mj1MLw$default(Modifier.Companion, 0, 63);
            String stringResource = StringResources_androidKt.stringResource(R.string.volume_panel_noise_control_title, composerImpl2);
            MaterialTheme.INSTANCE.getClass();
            TextStyle textStyle = MaterialTheme.getTypography(composerImpl2).titleMedium;
            TextAlign.Companion.getClass();
            composerImpl = composerImpl2;
            TextKt.m316Text4IGK_g(stringResource, m27basicMarquee1Mj1MLw$default, 0L, 0L, null, null, null, 0L, null, TextAlign.m805boximpl(TextAlign.Center), 0L, 0, false, 1, 0, null, textStyle, composerImpl, 48, 3072, 56828);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(i) { // from class: com.android.systemui.volume.panel.component.anc.ui.composable.AncPopup$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    AncPopup.this.Title(updateChangedFlags, (Composer) obj);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
