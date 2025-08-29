package com.android.systemui.communal.ui.compose;

import android.content.res.Configuration;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.ZIndexModifierKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.IntRect;
import com.android.compose.animation.scene.ContentScope;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.communal.smartspace.SmartspaceInteractionHandler;
import com.android.systemui.communal.ui.compose.section.AmbientStatusBarSection;
import com.android.systemui.communal.ui.compose.section.CommunalLockSection;
import com.android.systemui.communal.ui.compose.section.CommunalPopupSection;
import com.android.systemui.communal.ui.compose.section.HubOnboardingSection;
import com.android.systemui.communal.ui.view.layout.sections.CommunalAppWidgetSection;
import com.android.systemui.communal.ui.viewmodel.CommunalViewModel;
import com.android.systemui.keyguard.ui.composable.blueprint.BlueprintAlignmentLines$LockIcon;
import com.android.systemui.keyguard.ui.composable.section.BottomAreaSection;
import com.android.systemui.keyguard.ui.composable.section.LockSection;
import com.android.systemui.statusbar.phone.SystemUIDialogFactory;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class CommunalContent {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AmbientStatusBarSection ambientStatusBarSection;
    public final BottomAreaSection bottomAreaSection;
    public final CommunalPopupSection communalPopupSection;
    public final CommunalSettingsInteractor communalSettingsInteractor;
    public final SystemUIDialogFactory dialogFactory;
    public final HubOnboardingSection hubOnboardingSection;
    public final SmartspaceInteractionHandler interactionHandler;
    public final LockSection lockSection;
    public final CommunalViewModel viewModel;
    public final CommunalAppWidgetSection widgetSection;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        Dp.Companion companion = Dp.Companion;
    }

    public CommunalContent(CommunalViewModel communalViewModel, SmartspaceInteractionHandler smartspaceInteractionHandler, CommunalSettingsInteractor communalSettingsInteractor, SystemUIDialogFactory systemUIDialogFactory, LockSection lockSection, CommunalLockSection communalLockSection, BottomAreaSection bottomAreaSection, AmbientStatusBarSection ambientStatusBarSection, CommunalPopupSection communalPopupSection, CommunalAppWidgetSection communalAppWidgetSection, HubOnboardingSection hubOnboardingSection) {
        this.viewModel = communalViewModel;
        this.interactionHandler = smartspaceInteractionHandler;
        this.communalSettingsInteractor = communalSettingsInteractor;
        this.dialogFactory = systemUIDialogFactory;
        this.lockSection = lockSection;
        this.bottomAreaSection = bottomAreaSection;
        this.ambientStatusBarSection = ambientStatusBarSection;
        this.communalPopupSection = communalPopupSection;
        this.widgetSection = communalAppWidgetSection;
        this.hubOnboardingSection = hubOnboardingSection;
    }

    public final void Content(final ContentScope contentScope, final Modifier modifier, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1994133520);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(contentScope) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(modifier) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(this) ? 256 : 128;
        }
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.CommunalContent.Content (CommunalContent.kt:68)");
            }
            CommunalTouchableSurfaceKt.CommunalTouchableSurface(this.viewModel, modifier, ComposableLambdaKt.rememberComposableLambda(781207450, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalContent.Content.1
                /* JADX WARN: Removed duplicated region for block: B:15:0x0067  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
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
                                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.CommunalContent.Content.<anonymous> (CommunalContent.kt:70)");
                            }
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            final int i3 = ((Configuration) composerImpl3.consume(AndroidCompositionLocals_androidKt.LocalConfiguration)).orientation;
                            Modifier.Companion companion = Modifier.Companion;
                            Modifier modifierFillMaxSize = SizeKt.fillMaxSize(companion, 1.0f);
                            composerImpl3.startReplaceGroup(-1309671223);
                            final CommunalContent communalContent = CommunalContent.this;
                            boolean zChangedInstance = composerImpl3.changedInstance(communalContent) | composerImpl3.changed(i3);
                            Object objRememberedValue = composerImpl3.rememberedValue();
                            if (!zChangedInstance) {
                                Composer.Companion.getClass();
                                if (objRememberedValue == Composer.Companion.Empty) {
                                    objRememberedValue = new MeasurePolicy(i3) { // from class: com.android.systemui.communal.ui.compose.CommunalContent$Content$1$1$1
                                        @Override // androidx.compose.ui.layout.MeasurePolicy
                                        /* renamed from: measure-3p2s80s */
                                        public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, final long j) {
                                            Measurable measurable = (Measurable) list.get(0);
                                            Measurable measurable2 = (Measurable) list.get(1);
                                            Measurable measurable3 = (Measurable) list.get(2);
                                            long jM816copyZbe2FdA$default = Constraints.m816copyZbe2FdA$default(j, 0, 0, 0, 0, 10);
                                            CommunalContent communalContent2 = this.this$0;
                                            communalContent2.communalSettingsInteractor.isV2FlagEnabled();
                                            final Placeable placeableMo610measureBRTryo0 = measurable2.mo610measureBRTryo0(jM816copyZbe2FdA$default);
                                            communalContent2.communalSettingsInteractor.isV2FlagEnabled();
                                            BlueprintAlignmentLines$LockIcon.INSTANCE.getClass();
                                            final IntRect intRect = new IntRect(placeableMo610measureBRTryo0.get(BlueprintAlignmentLines$LockIcon.Left), placeableMo610measureBRTryo0.get(BlueprintAlignmentLines$LockIcon.Top), placeableMo610measureBRTryo0.get(BlueprintAlignmentLines$LockIcon.Right), placeableMo610measureBRTryo0.get(BlueprintAlignmentLines$LockIcon.Bottom));
                                            final Placeable placeableMo610measureBRTryo02 = measurable3.mo610measureBRTryo0(jM816copyZbe2FdA$default);
                                            final Placeable placeableMo610measureBRTryo03 = measurable.mo610measureBRTryo0(Constraints.m816copyZbe2FdA$default(jM816copyZbe2FdA$default, 0, 0, 0, intRect.top, 7));
                                            return measureScope.layout$1(Constraints.m823getMaxWidthimpl(j), Constraints.m822getMaxHeightimpl(j), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalContent$Content$1$1$1$$ExternalSyntheticLambda0
                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo781invoke(Object obj4) {
                                                    Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj4;
                                                    placementScope.place(placeableMo610measureBRTryo03, 0, 0, 0.0f);
                                                    IntRect intRect2 = intRect;
                                                    placementScope.place(placeableMo610measureBRTryo0, intRect2.left, intRect2.top, 0.0f);
                                                    int iM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(j);
                                                    Placeable placeable = placeableMo610measureBRTryo02;
                                                    placementScope.place(placeable, 0, iM822getMaxHeightimpl - placeable.height, 0.0f);
                                                    return Unit.INSTANCE;
                                                }
                                            });
                                        }
                                    };
                                    composerImpl3.updateRememberedValue(objRememberedValue);
                                }
                                MeasurePolicy measurePolicy = (MeasurePolicy) objRememberedValue;
                                composerImpl3.end(false);
                                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl3, modifierFillMaxSize);
                                ComposeUiNode.Companion.getClass();
                                Function0 function0 = ComposeUiNode.Companion.Constructor;
                                if (composerImpl3.applier == null) {
                                    ComposablesKt.invalidApplier();
                                    throw null;
                                }
                                composerImpl3.startReusableNode();
                                if (composerImpl3.inserting) {
                                    composerImpl3.createNode(function0);
                                } else {
                                    composerImpl3.useNode();
                                }
                                Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
                                Updater.m337setimpl(composerImpl3, measurePolicy, function2);
                                Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                                Updater.m337setimpl(composerImpl3, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
                                Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function23);
                                }
                                Function2 function24 = ComposeUiNode.Companion.SetModifier;
                                Updater.m337setimpl(composerImpl3, modifierMaterializeModifier, function24);
                                Modifier modifierFillMaxSize2 = SizeKt.fillMaxSize(companion, 1.0f);
                                Alignment.Companion.getClass();
                                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl3.currentCompositionLocalScope();
                                Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl3, modifierFillMaxSize2);
                                composerImpl3.startReusableNode();
                                if (composerImpl3.inserting) {
                                    composerImpl3.createNode(function0);
                                } else {
                                    composerImpl3.useNode();
                                }
                                Updater.m337setimpl(composerImpl3, measurePolicyMaybeCachedBoxMeasurePolicy, function2);
                                Updater.m337setimpl(composerImpl3, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
                                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl3, currentCompositeKeyHash2, function23);
                                }
                                Updater.m337setimpl(composerImpl3, modifierMaterializeModifier2, function24);
                                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                composerImpl3.startReplaceGroup(-877976416);
                                communalContent.communalPopupSection.Popup(0, composerImpl3);
                                composerImpl3.end(false);
                                composerImpl3.startReplaceGroup(-877974195);
                                Modifier modifierZIndex = ZIndexModifierKt.zIndex(SizeKt.fillMaxWidth(companion, 1.0f), 1.0f);
                                AmbientStatusBarSection ambientStatusBarSection = communalContent.ambientStatusBarSection;
                                ContentScope contentScope2 = contentScope;
                                ambientStatusBarSection.AmbientStatusBar(contentScope2, modifierZIndex, composerImpl3, 48);
                                composerImpl3.end(false);
                                Communal$Elements.INSTANCE.getClass();
                                CommunalHubKt.CommunalHub(contentScope2.element(companion, Communal$Elements.Grid), communalContent.viewModel, communalContent.widgetSection, communalContent.interactionHandler, communalContent.dialogFactory, null, null, null, contentScope2, composerImpl3, 0, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType);
                                composerImpl3.startReplaceGroup(-877951770);
                                communalContent.hubOnboardingSection.BottomSheet(0, composerImpl3);
                                composerImpl3.end(false);
                                composerImpl3.end(true);
                                communalContent.communalSettingsInteractor.isV2FlagEnabled();
                                composerImpl3.startReplaceGroup(-1836012657);
                                MaterialTheme.INSTANCE.getClass();
                                communalContent.lockSection.m2618LockIconBAq54LU(contentScope2, Color.m456boximpl(MaterialTheme.getColorScheme(composerImpl3).onPrimaryContainer), contentScope2.element(companion, Communal$Elements.LockIcon), composerImpl3, 0, 0);
                                composerImpl3.end(false);
                                composerImpl3.startReplaceGroup(1880447978);
                                communalContent.bottomAreaSection.IndicationArea(contentScope2, SizeKt.fillMaxWidth(contentScope2.element(companion, Communal$Elements.IndicationArea), 1.0f), composerImpl3, 0);
                                if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl3, false, true)) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, (i2 & 112) | 384);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalContent$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int i3 = CommunalContent.$r8$clinit;
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    ContentScope contentScope2 = contentScope;
                    Modifier modifier2 = modifier;
                    this.f$0.Content(contentScope2, modifier2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
