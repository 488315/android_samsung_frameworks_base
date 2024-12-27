package com.android.systemui.communal.ui.compose;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.FillElement;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.IntRect;
import com.android.compose.animation.scene.SceneScope;
import com.android.compose.theme.AndroidColorScheme;
import com.android.compose.theme.AndroidColorSchemeKt;
import com.android.systemui.communal.smartspace.SmartspaceInteractionHandler;
import com.android.systemui.communal.ui.compose.section.AmbientStatusBarSection;
import com.android.systemui.communal.ui.viewmodel.CommunalViewModel;
import com.android.systemui.keyguard.ui.composable.blueprint.BlueprintAlignmentLines$LockIcon;
import com.android.systemui.keyguard.ui.composable.section.LockSection;
import com.android.systemui.statusbar.phone.SystemUIDialogFactory;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

public final class CommunalContent {
    public final AmbientStatusBarSection ambientStatusBarSection;
    public final SystemUIDialogFactory dialogFactory;
    public final SmartspaceInteractionHandler interactionHandler;
    public final LockSection lockSection;
    public final CommunalViewModel viewModel;

    public CommunalContent(CommunalViewModel communalViewModel, SmartspaceInteractionHandler smartspaceInteractionHandler, SystemUIDialogFactory systemUIDialogFactory, LockSection lockSection, AmbientStatusBarSection ambientStatusBarSection) {
        this.viewModel = communalViewModel;
        this.interactionHandler = smartspaceInteractionHandler;
        this.dialogFactory = systemUIDialogFactory;
        this.lockSection = lockSection;
        this.ambientStatusBarSection = ambientStatusBarSection;
    }

    public final void Content(final SceneScope sceneScope, Modifier modifier, Composer composer, final int i, final int i2) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1442180163);
        Modifier modifier2 = (i2 & 1) != 0 ? Modifier.Companion : modifier;
        OpaqueKey opaqueKey = ComposerKt.invocation;
        FillElement fillElement = SizeKt.FillWholeMaxSize;
        Modifier then = modifier2.then(fillElement);
        int i3 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, then);
        ComposeUiNode.Companion.getClass();
        Function0 function0 = ComposeUiNode.Companion.Constructor;
        boolean z = composerImpl.applier instanceof Applier;
        if (!z) {
            ComposablesKt.invalidApplier();
            throw null;
        }
        composerImpl.startReusableNode();
        if (composerImpl.inserting) {
            composerImpl.createNode(function0);
        } else {
            composerImpl.useNode();
        }
        Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
        Updater.m276setimpl(composerImpl, new MeasurePolicy() { // from class: com.android.systemui.communal.ui.compose.CommunalContent$Content$1
            @Override // androidx.compose.ui.layout.MeasurePolicy
            /* renamed from: measure-3p2s80s */
            public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, long j) {
                MeasureResult layout$1;
                Measurable measurable = (Measurable) list.get(0);
                Measurable measurable2 = (Measurable) list.get(1);
                long m717copyZbe2FdA$default = Constraints.m717copyZbe2FdA$default(j, 0, 0, 0, 0, 10);
                final Placeable mo528measureBRTryo0 = measurable2.mo528measureBRTryo0(m717copyZbe2FdA$default);
                BlueprintAlignmentLines$LockIcon.INSTANCE.getClass();
                final IntRect intRect = new IntRect(mo528measureBRTryo0.get(BlueprintAlignmentLines$LockIcon.Left), mo528measureBRTryo0.get(BlueprintAlignmentLines$LockIcon.Top), mo528measureBRTryo0.get(BlueprintAlignmentLines$LockIcon.Right), mo528measureBRTryo0.get(BlueprintAlignmentLines$LockIcon.Bottom));
                final Placeable mo528measureBRTryo02 = measurable.mo528measureBRTryo0(Constraints.m717copyZbe2FdA$default(m717copyZbe2FdA$default, 0, 0, 0, intRect.top, 7));
                layout$1 = measureScope.layout$1(Constraints.m724getMaxWidthimpl(j), Constraints.m723getMaxHeightimpl(j), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalContent$Content$1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                        Placeable.PlacementScope.place$default(placementScope, Placeable.this, 0, 0);
                        Placeable placeable = mo528measureBRTryo0;
                        IntRect intRect2 = intRect;
                        Placeable.PlacementScope.place$default(placementScope, placeable, intRect2.left, intRect2.top);
                        return Unit.INSTANCE;
                    }
                });
                return layout$1;
            }
        }, function2);
        Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
        Updater.m276setimpl(composerImpl, currentCompositionLocalScope, function22);
        Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i3))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i3, composerImpl, i3, function23);
        }
        Function2 function24 = ComposeUiNode.Companion.SetModifier;
        Updater.m276setimpl(composerImpl, materializeModifier, function24);
        Modifier.Companion companion = Modifier.Companion;
        companion.then(fillElement);
        Alignment.Companion.getClass();
        MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
        int i4 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, fillElement);
        if (!z) {
            ComposablesKt.invalidApplier();
            throw null;
        }
        composerImpl.startReusableNode();
        if (composerImpl.inserting) {
            composerImpl.createNode(function0);
        } else {
            composerImpl.useNode();
        }
        Updater.m276setimpl(composerImpl, maybeCachedBoxMeasurePolicy, function2);
        Updater.m276setimpl(composerImpl, currentCompositionLocalScope2, function22);
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i4))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i4, composerImpl, i4, function23);
        }
        Updater.m276setimpl(composerImpl, materializeModifier2, function24);
        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
        composerImpl.startReplaceGroup(-632080020);
        int i5 = i & 14;
        this.ambientStatusBarSection.AmbientStatusBar(sceneScope, SizeKt.fillMaxWidth(companion, 1.0f), composerImpl, i5 | 48, 0);
        composerImpl.end(false);
        Communal$Elements.INSTANCE.getClass();
        final Modifier modifier3 = modifier2;
        CommunalHubKt.CommunalHub(sceneScope.element(companion, Communal$Elements.Grid), this.viewModel, this.interactionHandler, this.dialogFactory, null, null, null, composerImpl, 4672, 112);
        composerImpl.end(true);
        composerImpl.startReplaceGroup(-1625892487);
        this.lockSection.m1966LockIconBAq54LU(sceneScope, Color.m383boximpl(((AndroidColorScheme) composerImpl.consume(AndroidColorSchemeKt.LocalAndroidColorScheme)).onPrimaryContainer), sceneScope.element(companion, Communal$Elements.LockIcon), composerImpl, i5 | 4096, 0);
        composerImpl.end(false);
        composerImpl.end(true);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalContent$Content$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    CommunalContent.this.Content(sceneScope, modifier3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
