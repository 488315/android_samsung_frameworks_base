package com.android.systemui.volume.panel.component.spatialaudio.ui.composable;

import androidx.compose.foundation.BasicMarqueeKt;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.common.ui.compose.IconKt;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.volume.panel.component.popup.ui.composable.VolumePanelPopup;
import com.android.systemui.volume.panel.component.selector.ui.composable.Item;
import com.android.systemui.volume.panel.component.selector.ui.composable.VolumePanelRadioButtonBarScope;
import com.android.systemui.volume.panel.component.selector.ui.composable.VolumePanelRadioButtonBarScopeImpl;
import com.android.systemui.volume.panel.component.selector.ui.composable.VolumePanelRadioButtonsKt;
import com.android.systemui.volume.panel.component.spatial.ui.viewmodel.SpatialAudioButtonViewModel;
import com.android.systemui.volume.panel.component.spatial.ui.viewmodel.SpatialAudioViewModel;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
public final class SpatialAudioPopup {
    public final UiEventLogger uiEventLogger;
    public final SpatialAudioViewModel viewModel;
    public final VolumePanelPopup volumePanelPopup;

    public SpatialAudioPopup(SpatialAudioViewModel spatialAudioViewModel, VolumePanelPopup volumePanelPopup, UiEventLogger uiEventLogger) {
        this.viewModel = spatialAudioViewModel;
        this.volumePanelPopup = volumePanelPopup;
        this.uiEventLogger = uiEventLogger;
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00ee  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void Content(final SystemUIDialog systemUIDialog, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1509869231);
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
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioPopup.Content (SpatialAudioPopup.kt:76)");
            }
            SpatialAudioViewModel spatialAudioViewModel = this.viewModel;
            MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(spatialAudioViewModel.isAvailable, composerImpl);
            composerImpl.startReplaceGroup(2029557134);
            boolean zBooleanValue = ((Boolean) mutableStateCollectAsStateWithLifecycle.getValue()).booleanValue();
            Composer.Companion companion = Composer.Companion;
            if (!zBooleanValue) {
                composerImpl.startReplaceGroup(2029558438);
                boolean zChangedInstance = composerImpl.changedInstance(systemUIDialog);
                Object objRememberedValue = composerImpl.rememberedValue();
                if (!zChangedInstance) {
                    companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = new Function0() { // from class: com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioPopup$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                systemUIDialog.dismiss();
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
                    final int i3 = 0;
                    recomposeScopeImplEndRestartGroup.block = new Function2(this) { // from class: com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioPopup$$ExternalSyntheticLambda1
                        public final /* synthetic */ SpatialAudioPopup f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            int i4 = i3;
                            Composer composer2 = (Composer) obj;
                            ((Integer) obj2).intValue();
                            switch (i4) {
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
            final MutableState mutableStateCollectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(spatialAudioViewModel.spatialAudioButtons, composerImpl);
            if (((List) mutableStateCollectAsStateWithLifecycle2.getValue()).isEmpty()) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                RecomposeScopeImpl recomposeScopeImplEndRestartGroup2 = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup2 != null) {
                    final int i4 = 1;
                    recomposeScopeImplEndRestartGroup2.block = new Function2(this) { // from class: com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioPopup$$ExternalSyntheticLambda1
                        public final /* synthetic */ SpatialAudioPopup f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            int i42 = i4;
                            Composer composer2 = (Composer) obj;
                            ((Integer) obj2).intValue();
                            switch (i42) {
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
            composerImpl.startReplaceGroup(2029567396);
            boolean zChanged = composerImpl.changed(mutableStateCollectAsStateWithLifecycle2) | composerImpl.changedInstance(this);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (!zChanged) {
                companion.getClass();
                if (objRememberedValue2 == Composer.Companion.Empty) {
                    objRememberedValue2 = new Function1() { // from class: com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioPopup$$ExternalSyntheticLambda3
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            VolumePanelRadioButtonBarScope volumePanelRadioButtonBarScope = (VolumePanelRadioButtonBarScope) obj;
                            for (final SpatialAudioButtonViewModel spatialAudioButtonViewModel : (List) mutableStateCollectAsStateWithLifecycle2.getValue()) {
                                final String string = spatialAudioButtonViewModel.button.label.toString();
                                boolean z = spatialAudioButtonViewModel.button.isActive;
                                final SpatialAudioPopup spatialAudioPopup = this;
                                Function0 function0 = new Function0() { // from class: com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioPopup$$ExternalSyntheticLambda5
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        spatialAudioPopup.viewModel.setEnabled(spatialAudioButtonViewModel.model);
                                        return Unit.INSTANCE;
                                    }
                                };
                                ComposableLambdaImpl composableLambdaImpl = new ComposableLambdaImpl(666734551, true, new Function3() { // from class: com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioPopup$Content$4$1$2
                                    /* JADX WARN: Removed duplicated region for block: B:8:0x001f  */
                                    @Override // kotlin.jvm.functions.Function3
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final Object invoke(Object obj2, Object obj3, Object obj4) {
                                        Composer composer2 = (Composer) obj3;
                                        if ((((Number) obj4).intValue() & 17) == 16) {
                                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                            if (composerImpl2.getSkipping()) {
                                                composerImpl2.skipToGroupEnd();
                                            } else {
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioPopup.Content.<anonymous>.<anonymous>.<anonymous> (SpatialAudioPopup.kt:95)");
                                                }
                                                IconKt.m1074IconFNF3uiM(spatialAudioButtonViewModel.button.icon, null, 0L, composer2, 0, 6);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                            }
                                        }
                                        return Unit.INSTANCE;
                                    }
                                });
                                ComposableLambdaImpl composableLambdaImpl2 = new ComposableLambdaImpl(2005226806, true, new Function3() { // from class: com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioPopup$Content$4$1$3
                                    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
                                    @Override // kotlin.jvm.functions.Function3
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final Object invoke(Object obj2, Object obj3, Object obj4) {
                                        Composer composer2 = (Composer) obj3;
                                        if ((((Number) obj4).intValue() & 17) == 16) {
                                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                            if (composerImpl2.getSkipping()) {
                                                composerImpl2.skipToGroupEnd();
                                            } else {
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioPopup.Content.<anonymous>.<anonymous>.<anonymous> (SpatialAudioPopup.kt:97)");
                                                }
                                                MaterialTheme.INSTANCE.getClass();
                                                TextStyle textStyle = MaterialTheme.getTypography(composer2).labelMedium;
                                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                                long j = ((Color) composerImpl3.consume(ContentColorKt.LocalContentColor)).value;
                                                TextAlign.Companion.getClass();
                                                int i5 = TextAlign.Center;
                                                TextOverflow.Companion.getClass();
                                                int i6 = TextOverflow.Ellipsis;
                                                TextKt.m317Text4IGK_g(string, null, j, 0L, null, null, null, 0L, null, TextAlign.m807boximpl(i5), 0L, i6, false, 1, 0, null, textStyle, composerImpl3, 0, 3120, 54778);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                            }
                                        }
                                        return Unit.INSTANCE;
                                    }
                                });
                                VolumePanelRadioButtonBarScopeImpl volumePanelRadioButtonBarScopeImpl = (VolumePanelRadioButtonBarScopeImpl) volumePanelRadioButtonBarScope;
                                if (z && volumePanelRadioButtonBarScopeImpl.selectedIndex != -1) {
                                    throw new IllegalArgumentException("Only one item should be selected at a time");
                                }
                                if (z) {
                                    volumePanelRadioButtonBarScopeImpl.selectedIndex = ((ArrayList) volumePanelRadioButtonBarScopeImpl.mutableItems).size();
                                }
                                ((ArrayList) volumePanelRadioButtonBarScopeImpl.mutableItems).add(new Item(function0, composableLambdaImpl, composableLambdaImpl2, string));
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue2);
                }
                composerImpl.end(false);
                VolumePanelRadioButtonsKt.m3220VolumePanelRadioButtonBarcjTkxnM(null, 0.0f, 0.0f, 0.0f, null, null, null, (Function1) objRememberedValue2, composerImpl, 0);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup3 = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup3 != null) {
            final int i5 = 2;
            recomposeScopeImplEndRestartGroup3.block = new Function2(this) { // from class: com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioPopup$$ExternalSyntheticLambda1
                public final /* synthetic */ SpatialAudioPopup f$0;

                {
                    this.f$0 = this;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    int i42 = i5;
                    Composer composer2 = (Composer) obj;
                    ((Integer) obj2).intValue();
                    switch (i42) {
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
        composerImpl2.startRestartGroup(-351769307);
        if ((i & 1) == 0 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioPopup.Title (SpatialAudioPopup.kt:64)");
            }
            Modifier modifierM27basicMarquee1Mj1MLw$default = BasicMarqueeKt.m27basicMarquee1Mj1MLw$default(Modifier.Companion, 0, 63);
            String strStringResource = StringResources_androidKt.stringResource(R.string.volume_panel_spatial_audio_title, composerImpl2);
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
            recomposeScopeImplEndRestartGroup.block = new Function2(i) { // from class: com.android.systemui.volume.panel.component.spatialaudio.ui.composable.SpatialAudioPopup$$ExternalSyntheticLambda6
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
