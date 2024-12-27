package com.android.systemui.volume.panel.component.anc.ui.composable;

import androidx.compose.foundation.BasicMarqueeKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.style.TextAlign;
import androidx.lifecycle.compose.FlowExtKt;
import androidx.slice.Slice;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.volume.panel.component.anc.ui.viewmodel.AncViewModel;
import com.android.systemui.volume.panel.component.popup.ui.composable.VolumePanelPopup;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AncPopup {
    public final UiEventLogger uiEventLogger;
    public final AncViewModel viewModel;
    public final VolumePanelPopup volumePanelPopup;

    public AncPopup(VolumePanelPopup volumePanelPopup, AncViewModel ancViewModel, UiEventLogger uiEventLogger) {
        this.volumePanelPopup = volumePanelPopup;
        this.viewModel = ancViewModel;
        this.uiEventLogger = uiEventLogger;
    }

    public static final void access$Content(final AncPopup ancPopup, final SystemUIDialog systemUIDialog, Composer composer, final int i) {
        ancPopup.getClass();
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1777551269);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        AncViewModel ancViewModel = ancPopup.viewModel;
        MutableState collectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(ancViewModel.availabilityCriteria.isAvailable(), Boolean.TRUE, composerImpl, 56);
        composerImpl.startReplaceGroup(1440995719);
        if (!((Boolean) collectAsStateWithLifecycle.getValue()).booleanValue()) {
            EffectsKt.SideEffect(new Function0() { // from class: com.android.systemui.volume.panel.component.anc.ui.composable.AncPopup$Content$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    SystemUIDialog.this.dismiss();
                    return Unit.INSTANCE;
                }
            }, composerImpl);
            composerImpl.end(false);
            RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
            if (endRestartGroup != null) {
                endRestartGroup.block = new Function2() { // from class: com.android.systemui.volume.panel.component.anc.ui.composable.AncPopup$Content$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Number) obj2).intValue();
                        AncPopup.access$Content(AncPopup.this, systemUIDialog, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        composerImpl.end(false);
        MutableState collectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(ancViewModel.popupSlice, composerImpl);
        composerImpl.startReplaceGroup(1440995893);
        if (!AncViewModel.isClickable((Slice) collectAsStateWithLifecycle2.getValue())) {
            EffectsKt.SideEffect(new Function0() { // from class: com.android.systemui.volume.panel.component.anc.ui.composable.AncPopup$Content$3
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    SystemUIDialog.this.dismiss();
                    return Unit.INSTANCE;
                }
            }, composerImpl);
            composerImpl.end(false);
            RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
            if (endRestartGroup2 != null) {
                endRestartGroup2.block = new Function2() { // from class: com.android.systemui.volume.panel.component.anc.ui.composable.AncPopup$Content$4
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Number) obj2).intValue();
                        AncPopup.access$Content(AncPopup.this, systemUIDialog, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        composerImpl.end(false);
        SliceAndroidViewKt.SliceAndroidView((Slice) collectAsStateWithLifecycle2.getValue(), SizeKt.fillMaxWidth(Modifier.Companion, 1.0f), new AncPopup$Content$5(ancViewModel), false, composerImpl, 56, 8);
        RecomposeScopeImpl endRestartGroup3 = composerImpl.endRestartGroup();
        if (endRestartGroup3 != null) {
            endRestartGroup3.block = new Function2() { // from class: com.android.systemui.volume.panel.component.anc.ui.composable.AncPopup$Content$6
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    AncPopup.access$Content(AncPopup.this, systemUIDialog, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void access$Title(final AncPopup ancPopup, Composer composer, final int i) {
        ComposerImpl composerImpl;
        ancPopup.getClass();
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1967543205);
        if ((i & 1) == 0 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Modifier m25basicMarquee1Mj1MLw$default = BasicMarqueeKt.m25basicMarquee1Mj1MLw$default(Modifier.Companion);
            String stringResource = StringResources_androidKt.stringResource(R.string.volume_panel_noise_control_title, composerImpl2);
            MaterialTheme.INSTANCE.getClass();
            TextStyle textStyle = MaterialTheme.getTypography(composerImpl2).titleMedium;
            TextAlign.Companion.getClass();
            composerImpl = composerImpl2;
            TextKt.m257Text4IGK_g(stringResource, m25basicMarquee1Mj1MLw$default, 0L, 0L, null, null, null, 0L, null, TextAlign.m705boximpl(TextAlign.Center), 0L, 0, false, 1, 0, null, textStyle, composerImpl, 48, 3072, 56828);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.volume.panel.component.anc.ui.composable.AncPopup$Title$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    AncPopup.access$Title(AncPopup.this, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
