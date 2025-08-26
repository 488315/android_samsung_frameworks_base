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
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.reflect.KFunction;

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

    /* JADX WARN: Removed duplicated region for block: B:32:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0134  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void Content(final SystemUIDialog systemUIDialog, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1777551269);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(systemUIDialog) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(this) ? 32 : 16;
        }
        if ((i2 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.anc.ui.composable.AncPopup.Content (AncPopup.kt:68)");
            }
            AncViewModel ancViewModel = this.viewModel;
            MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(ancViewModel.availabilityCriteria.isAvailable(), Boolean.TRUE, composerImpl, 48);
            composerImpl.startReplaceGroup(1721197196);
            boolean zBooleanValue = ((Boolean) mutableStateCollectAsStateWithLifecycle.getValue()).booleanValue();
            Composer.Companion companion = Composer.Companion;
            if (!zBooleanValue) {
                composerImpl.startReplaceGroup(1721198500);
                boolean zChangedInstance = composerImpl.changedInstance(systemUIDialog);
                Object objRememberedValue = composerImpl.rememberedValue();
                if (!zChangedInstance) {
                    companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        final int i3 = 0;
                        objRememberedValue = new Function0() { // from class: com.android.systemui.volume.panel.component.anc.ui.composable.AncPopup$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                switch (i3) {
                                    case 0:
                                        systemUIDialog.dismiss();
                                        break;
                                    default:
                                        systemUIDialog.dismiss();
                                        break;
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue);
                    }
                }
                composerImpl.end(false);
                EffectsKt.SideEffect((Function0) objRememberedValue, composerImpl);
                composerImpl.end(false);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    final int i4 = 0;
                    recomposeScopeImplEndRestartGroup.block = new Function2(this) { // from class: com.android.systemui.volume.panel.component.anc.ui.composable.AncPopup$$ExternalSyntheticLambda1
                        public final /* synthetic */ AncPopup f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            int i5 = i4;
                            Composer composer2 = (Composer) obj;
                            ((Integer) obj2).intValue();
                            switch (i5) {
                                case 0:
                                    this.f$0.Content(systemUIDialog, composer2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                    break;
                                case 1:
                                    this.f$0.Content(systemUIDialog, composer2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                    break;
                                default:
                                    this.f$0.Content(systemUIDialog, composer2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            composerImpl.end(false);
            MutableState mutableStateCollectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(ancViewModel.popupSlice, composerImpl);
            composerImpl.startReplaceGroup(1721202781);
            if (!AncViewModel.isClickable((Slice) mutableStateCollectAsStateWithLifecycle2.getValue())) {
                composerImpl.startReplaceGroup(1721204612);
                boolean zChangedInstance2 = composerImpl.changedInstance(systemUIDialog);
                Object objRememberedValue2 = composerImpl.rememberedValue();
                if (!zChangedInstance2) {
                    companion.getClass();
                    if (objRememberedValue2 == Composer.Companion.Empty) {
                        final int i5 = 1;
                        objRememberedValue2 = new Function0() { // from class: com.android.systemui.volume.panel.component.anc.ui.composable.AncPopup$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                switch (i5) {
                                    case 0:
                                        systemUIDialog.dismiss();
                                        break;
                                    default:
                                        systemUIDialog.dismiss();
                                        break;
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue2);
                    }
                }
                composerImpl.end(false);
                EffectsKt.SideEffect((Function0) objRememberedValue2, composerImpl);
                composerImpl.end(false);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                RecomposeScopeImpl recomposeScopeImplEndRestartGroup2 = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup2 != null) {
                    final int i6 = 1;
                    recomposeScopeImplEndRestartGroup2.block = new Function2(this) { // from class: com.android.systemui.volume.panel.component.anc.ui.composable.AncPopup$$ExternalSyntheticLambda1
                        public final /* synthetic */ AncPopup f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            int i52 = i6;
                            Composer composer2 = (Composer) obj;
                            ((Integer) obj2).intValue();
                            switch (i52) {
                                case 0:
                                    this.f$0.Content(systemUIDialog, composer2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                    break;
                                case 1:
                                    this.f$0.Content(systemUIDialog, composer2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                    break;
                                default:
                                    this.f$0.Content(systemUIDialog, composer2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            composerImpl.end(false);
            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(Modifier.Companion, 1.0f);
            Slice slice = (Slice) mutableStateCollectAsStateWithLifecycle2.getValue();
            composerImpl.startReplaceGroup(1721210419);
            boolean zChangedInstance3 = composerImpl.changedInstance(ancViewModel);
            Object objRememberedValue3 = composerImpl.rememberedValue();
            if (!zChangedInstance3) {
                companion.getClass();
                if (objRememberedValue3 == Composer.Companion.Empty) {
                    objRememberedValue3 = new AncPopup$Content$5$1(ancViewModel);
                    composerImpl.updateRememberedValue(objRememberedValue3);
                }
                composerImpl.end(false);
                SliceAndroidViewKt.SliceAndroidView(slice, modifierFillMaxWidth, (Function1) ((KFunction) objRememberedValue3), false, composerImpl, 48, 8);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup3 = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup3 != null) {
            final int i7 = 2;
            recomposeScopeImplEndRestartGroup3.block = new Function2(this) { // from class: com.android.systemui.volume.panel.component.anc.ui.composable.AncPopup$$ExternalSyntheticLambda1
                public final /* synthetic */ AncPopup f$0;

                {
                    this.f$0 = this;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    int i52 = i7;
                    Composer composer2 = (Composer) obj;
                    ((Integer) obj2).intValue();
                    switch (i52) {
                        case 0:
                            this.f$0.Content(systemUIDialog, composer2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            break;
                        case 1:
                            this.f$0.Content(systemUIDialog, composer2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            break;
                        default:
                            this.f$0.Content(systemUIDialog, composer2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            break;
                    }
                    return Unit.INSTANCE;
                }
            };
        }
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
            Modifier modifierM27basicMarquee1Mj1MLw$default = BasicMarqueeKt.m27basicMarquee1Mj1MLw$default(Modifier.Companion, 0, 63);
            String strStringResource = StringResources_androidKt.stringResource(R.string.volume_panel_noise_control_title, composerImpl2);
            MaterialTheme.INSTANCE.getClass();
            TextStyle textStyle = MaterialTheme.getTypography(composerImpl2).titleMedium;
            TextAlign.Companion.getClass();
            composerImpl = composerImpl2;
            TextKt.m317Text4IGK_g(strStringResource, modifierM27basicMarquee1Mj1MLw$default, 0L, 0L, null, null, null, 0L, null, TextAlign.m807boximpl(TextAlign.Center), 0L, 0, false, 1, 0, null, textStyle, composerImpl, 48, 3072, 56828);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(i) { // from class: com.android.systemui.volume.panel.component.anc.ui.composable.AncPopup$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    this.f$0.Title(iUpdateChangedFlags, (Composer) obj);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
