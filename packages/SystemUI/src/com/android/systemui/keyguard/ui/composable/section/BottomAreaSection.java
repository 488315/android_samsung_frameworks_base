package com.android.systemui.keyguard.ui.composable.section;

import android.content.Context;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.res.PrimitiveResources_androidKt;
import androidx.compose.ui.viewinterop.AndroidView_androidKt;
import com.android.compose.animation.scene.ContentScope;
import com.android.compose.animation.scene.ElementKey;
import com.android.systemui.R;
import com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder;
import com.android.systemui.keyguard.ui.binder.KeyguardQuickAffordanceViewBinder;
import com.android.systemui.keyguard.ui.view.KeyguardIndicationArea;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardIndicationAreaViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel;
import com.android.systemui.statusbar.KeyguardIndicationController;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.DisposableHandle;

/* loaded from: classes2.dex */
public final class BottomAreaSection {
    public final KeyguardIndicationAreaViewModel indicationAreaViewModel;
    public final KeyguardIndicationController indicationController;
    public final KeyguardQuickAffordanceViewBinder keyguardQuickAffordanceViewBinder;
    public final KeyguardQuickAffordancesCombinedViewModel viewModel;

    public BottomAreaSection(KeyguardQuickAffordancesCombinedViewModel keyguardQuickAffordancesCombinedViewModel, KeyguardIndicationController keyguardIndicationController, KeyguardIndicationAreaViewModel keyguardIndicationAreaViewModel, KeyguardQuickAffordanceViewBinder keyguardQuickAffordanceViewBinder) {
        this.viewModel = keyguardQuickAffordancesCombinedViewModel;
        this.indicationController = keyguardIndicationController;
        this.indicationAreaViewModel = keyguardIndicationAreaViewModel;
        this.keyguardQuickAffordanceViewBinder = keyguardQuickAffordanceViewBinder;
    }

    public final void IndicationArea(ContentScope contentScope, final Modifier modifier, Composer composer, final int i) {
        final ContentScope contentScope2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(400972694);
        int i2 = (composerImpl.changed(contentScope) ? 4 : 2) | i;
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(modifier) ? 32 : 16;
        }
        int i3 = i2 | (composerImpl.changedInstance(this) ? 256 : 128);
        if ((i3 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            contentScope2 = contentScope;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyguard.ui.composable.section.BottomAreaSection.IndicationArea (BottomAreaSection.kt:89)");
            }
            ElementKey elementKey = BottomAreaSectionKt.IndicationAreaElementKey;
            composerImpl.startReplaceGroup(2014498161);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyguard.ui.composable.section.BottomAreaSection.indicationAreaPadding (BottomAreaSection.kt:193)");
            }
            Modifier modifierM129paddingqDBjuR0$default = PaddingKt.m129paddingqDBjuR0$default(modifier, 0.0f, 0.0f, 0.0f, PrimitiveResources_androidKt.dimensionResource(R.dimen.keyguard_indication_margin_bottom, composerImpl), 7);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
            contentScope2 = contentScope;
            contentScope2.Element(elementKey, modifierM129paddingqDBjuR0$default, ComposableLambdaKt.rememberComposableLambda(2118934190, new Function3() { // from class: com.android.systemui.keyguard.ui.composable.section.BottomAreaSection.IndicationArea.1
                /* JADX WARN: Removed duplicated region for block: B:8:0x001f  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    if ((((Number) obj3).intValue() & 17) == 16) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.keyguard.ui.composable.section.BottomAreaSection.IndicationArea.<anonymous> (BottomAreaSection.kt:91)");
                            }
                            BottomAreaSection bottomAreaSection = BottomAreaSection.this;
                            bottomAreaSection.IndicationArea(bottomAreaSection.indicationAreaViewModel, bottomAreaSection.indicationController, null, composer2, 0);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, ((i3 << 9) & 7168) | 390);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.keyguard.ui.composable.section.BottomAreaSection$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    ContentScope contentScope3 = contentScope2;
                    Modifier modifier2 = modifier;
                    this.f$0.IndicationArea(contentScope3, modifier2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public final void IndicationArea(final KeyguardIndicationAreaViewModel keyguardIndicationAreaViewModel, final KeyguardIndicationController keyguardIndicationController, Modifier.Companion companion, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1804781866);
        if (((i | (composerImpl.changedInstance(keyguardIndicationAreaViewModel) ? 4 : 2) | (composerImpl.changedInstance(keyguardIndicationController) ? 32 : 16) | 384) & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            companion = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyguard.ui.composable.section.BottomAreaSection.IndicationArea (BottomAreaSection.kt:162)");
            }
            composerImpl.startReplaceGroup(-83975273);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Object obj = Composer.Companion.Empty;
            if (objRememberedValue == obj) {
                objRememberedValue = SnapshotStateKt.mutableStateOf$default(null);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            MutableState mutableState = (MutableState) objRememberedValue;
            composerImpl.end(false);
            final DisposableHandle disposableHandle = (DisposableHandle) mutableState.component1();
            final Function1 function1Component2 = mutableState.component2();
            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(companion, 1.0f);
            composerImpl.startReplaceGroup(-83971690);
            boolean zChanged = composerImpl.changed(function1Component2) | composerImpl.changedInstance(keyguardIndicationAreaViewModel) | composerImpl.changedInstance(keyguardIndicationController);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (zChanged || objRememberedValue2 == obj) {
                objRememberedValue2 = new Function1() { // from class: com.android.systemui.keyguard.ui.composable.section.BottomAreaSection$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        KeyguardIndicationArea keyguardIndicationArea = new KeyguardIndicationArea((Context) obj2, null);
                        keyguardIndicationArea.setFocusable(true);
                        keyguardIndicationArea.setImportantForAccessibility(1);
                        function1Component2.mo781invoke(KeyguardIndicationAreaBinder.bind(keyguardIndicationArea, keyguardIndicationAreaViewModel, keyguardIndicationController));
                        return keyguardIndicationArea;
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
            Function1 function1 = (Function1) objRememberedValue2;
            composerImpl.end(false);
            composerImpl.startReplaceGroup(-83954372);
            boolean zChangedInstance = composerImpl.changedInstance(disposableHandle);
            Object objRememberedValue3 = composerImpl.rememberedValue();
            if (zChangedInstance || objRememberedValue3 == obj) {
                objRememberedValue3 = new Function1() { // from class: com.android.systemui.keyguard.ui.composable.section.BottomAreaSection$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        DisposableHandle disposableHandle2 = disposableHandle;
                        if (disposableHandle2 != null) {
                            disposableHandle2.dispose();
                        }
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue3);
            }
            composerImpl.end(false);
            AndroidView_androidKt.AndroidView(function1, modifierFillMaxWidth, null, (Function1) objRememberedValue3, null, composerImpl, 0, 20);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        final Modifier.Companion companion2 = companion;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(keyguardIndicationAreaViewModel, keyguardIndicationController, companion2, i) { // from class: com.android.systemui.keyguard.ui.composable.section.BottomAreaSection$$ExternalSyntheticLambda3
                public final /* synthetic */ KeyguardIndicationAreaViewModel f$1;
                public final /* synthetic */ KeyguardIndicationController f$2;
                public final /* synthetic */ Modifier.Companion f$3;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Integer) obj3).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    KeyguardIndicationController keyguardIndicationController2 = this.f$2;
                    Modifier.Companion companion3 = this.f$3;
                    this.f$0.IndicationArea(this.f$1, keyguardIndicationController2, companion3, (Composer) obj2, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
