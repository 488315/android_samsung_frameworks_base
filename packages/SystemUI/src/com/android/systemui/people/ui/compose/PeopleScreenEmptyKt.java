package com.android.systemui.people.ui.compose;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.layout.WindowInsetsPadding_androidKt$safeDrawingPadding$$inlined$windowInsetsPadding$1;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.ButtonKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.SurfaceKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.InspectableValueKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.R;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class PeopleScreenEmptyKt {
    public static final void ExampleTile(final Modifier.Companion companion, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-2044616169);
        if (((i | 6) & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            Modifier.Companion companion2 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.people.ui.compose.ExampleTile (PeopleScreenEmpty.kt:76)");
            }
            Dp.Companion companion3 = Dp.Companion;
            RoundedCornerShape m186RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m186RoundedCornerShape0680j_4(28);
            MaterialTheme.INSTANCE.getClass();
            long j = MaterialTheme.getColorScheme(composerImpl).secondaryContainer;
            ComposableSingletons$PeopleScreenEmptyKt.INSTANCE.getClass();
            SurfaceKt.m303SurfaceT9BRK9s(companion2, m186RoundedCornerShape0680j_4, j, 0L, 0.0f, 0.0f, null, ComposableSingletons$PeopleScreenEmptyKt.f91lambda2, composerImpl, 12582918, 120);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            companion = companion2;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(i) { // from class: com.android.systemui.people.ui.compose.PeopleScreenEmptyKt$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    PeopleScreenEmptyKt.ExampleTile(Modifier.Companion.this, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void PeopleScreenEmpty(final Function0 function0, Modifier.Companion companion, Composer composer, final int i) {
        final Modifier.Companion companion2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(442618961);
        int i2 = (composerImpl.changedInstance(function0) ? 4 : 2) | i | 48;
        if ((i2 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            companion2 = companion;
        } else {
            Modifier.Companion companion3 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.people.ui.compose.PeopleScreenEmpty (PeopleScreenEmpty.kt:46)");
            }
            Modifier m124padding3ABfNKs = PaddingKt.m124padding3ABfNKs(ComposedModifierKt.composed(SizeKt.fillMaxSize(companion3, 1.0f), InspectableValueKt.NoInspectorInfo, new WindowInsetsPadding_androidKt$safeDrawingPadding$$inlined$windowInsetsPadding$1()), PeopleScreenKt.PeopleSpacePadding);
            Alignment.Companion.getClass();
            BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
            Arrangement.INSTANCE.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(Arrangement.Top, horizontal, composerImpl, 48);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m124padding3ABfNKs);
            ComposeUiNode.Companion.getClass();
            Function0 function02 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function02);
            } else {
                composerImpl.useNode();
            }
            Updater.m336setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            String stringResource = StringResources_androidKt.stringResource(R.string.select_conversation_title, composerImpl);
            MaterialTheme.INSTANCE.getClass();
            TextStyle textStyle = MaterialTheme.getTypography(composerImpl).headlineSmall;
            TextAlign.Companion.getClass();
            int i3 = TextAlign.Center;
            TextKt.m316Text4IGK_g(stringResource, null, 0L, 0L, null, null, null, 0L, null, TextAlign.m805boximpl(i3), 0L, 0, false, 0, 0, null, textStyle, composerImpl, 0, 0, 65022);
            Dp.Companion companion4 = Dp.Companion;
            SpacerKt.Spacer(composerImpl, SizeKt.m130height3ABfNKs(companion3, 50));
            TextKt.m316Text4IGK_g(StringResources_androidKt.stringResource(R.string.no_conversations_text, composerImpl), null, 0L, 0L, null, null, null, 0L, null, TextAlign.m805boximpl(i3), 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composerImpl).bodyLarge, composerImpl, 0, 0, 65022);
            SpacerKt.Spacer(composerImpl, columnScopeInstance.weight(companion3, 1.0f, true));
            ExampleTile(null, composerImpl, 0);
            SpacerKt.Spacer(composerImpl, columnScopeInstance.weight(companion3, 1.0f, true));
            Modifier fillMaxWidth = SizeKt.fillMaxWidth(companion3, 1.0f);
            float f = 56;
            float f2 = 0.0f;
            if (true & true) {
                Dp.Companion.getClass();
                f2 = Dp.Unspecified;
            }
            if ((2 & 1) != 0) {
                Dp.Companion.getClass();
                f = Dp.Unspecified;
            }
            Modifier m129defaultMinSizeVpY3zN4 = SizeKt.m129defaultMinSizeVpY3zN4(fillMaxWidth, f2, f);
            ComposableSingletons$PeopleScreenEmptyKt.INSTANCE.getClass();
            companion2 = companion3;
            ButtonKt.Button(function0, m129defaultMinSizeVpY3zN4, false, null, null, null, null, null, null, ComposableSingletons$PeopleScreenEmptyKt.f90lambda1, composerImpl, (i2 & 14) | 805306416, 508);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(companion2, i) { // from class: com.android.systemui.people.ui.compose.PeopleScreenEmptyKt$$ExternalSyntheticLambda0
                public final /* synthetic */ Modifier.Companion f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    PeopleScreenEmptyKt.PeopleScreenEmpty(Function0.this, this.f$1, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
