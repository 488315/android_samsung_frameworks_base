package com.android.systemui.statusbar.policy.ui.dialog.composable;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.SingleValueAnimationKt;
import androidx.compose.foundation.BasicMarqueeKt;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.SurfaceKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.TestTagKt;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.common.ui.compose.IconKt;
import com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModeTileViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public abstract class ModeTileKt {
    public static final void ModeTile(final ModeTileViewModel modeTileViewModel, final Modifier modifier, Composer composer, final int i, final int i2) {
        long j;
        long j2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1313864703);
        int i3 = (composerImpl.changed(modeTileViewModel) ? 4 : 2) | i;
        int i4 = i2 & 2;
        if (i4 != 0) {
            i3 |= 48;
        } else if ((i & 48) == 0) {
            i3 |= composerImpl.changed(modifier) ? 32 : 16;
        }
        if ((i3 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (i4 != 0) {
                modifier = Modifier.Companion;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.statusbar.policy.ui.dialog.composable.ModeTile (ModeTile.kt:48)");
            }
            if (modeTileViewModel.enabled) {
                composerImpl.startReplaceGroup(-358683747);
                MaterialTheme.INSTANCE.getClass();
                j = MaterialTheme.getColorScheme(composerImpl).primary;
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(-358682108);
                MaterialTheme.INSTANCE.getClass();
                j = MaterialTheme.getColorScheme(composerImpl).surfaceVariant;
                composerImpl.end(false);
            }
            final State stateM7animateColorAsStateeuL9pac = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(j, null, null, composerImpl, 0, 14);
            if (modeTileViewModel.enabled) {
                composerImpl.startReplaceGroup(-358677441);
                MaterialTheme.INSTANCE.getClass();
                j2 = MaterialTheme.getColorScheme(composerImpl).onPrimary;
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(-358675738);
                MaterialTheme.INSTANCE.getClass();
                j2 = MaterialTheme.getColorScheme(composerImpl).onSurfaceVariant;
                composerImpl.end(false);
            }
            CompositionLocalKt.CompositionLocalProvider(ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m456boximpl(((Color) SingleValueAnimationKt.m7animateColorAsStateeuL9pac(j2, null, null, composerImpl, 0, 14).getValue()).value)), ComposableLambdaKt.rememberComposableLambda(1382141759, new Function2() { // from class: com.android.systemui.statusbar.policy.ui.dialog.composable.ModeTileKt.ModeTile.1
                /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.statusbar.policy.ui.dialog.composable.ModeTile.<anonymous> (ModeTile.kt:61)");
                            }
                            long j3 = ((Color) stateM7animateColorAsStateeuL9pac.getValue()).value;
                            Dp.Companion companion = Dp.Companion;
                            RoundedCornerShape roundedCornerShapeM187RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(16);
                            final ModeTileViewModel modeTileViewModel2 = modeTileViewModel;
                            SurfaceKt.m304SurfaceT9BRK9s(modifier, roundedCornerShapeM187RoundedCornerShape0680j_4, j3, 0L, 0.0f, 0.0f, null, ComposableLambdaKt.rememberComposableLambda(-327673852, new Function2() { // from class: com.android.systemui.statusbar.policy.ui.dialog.composable.ModeTileKt.ModeTile.1.1
                                /* JADX WARN: Removed duplicated region for block: B:15:0x0064  */
                                /* JADX WARN: Removed duplicated region for block: B:44:0x01a2  */
                                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                                @Override // kotlin.jvm.functions.Function2
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final Object invoke(Object obj3, Object obj4) {
                                    final int i5 = 0;
                                    Composer composer3 = (Composer) obj3;
                                    if ((((Number) obj4).intValue() & 3) == 2) {
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                        if (composerImpl3.getSkipping()) {
                                            composerImpl3.skipToGroupEnd();
                                        } else {
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("com.android.systemui.statusbar.policy.ui.dialog.composable.ModeTile.<anonymous>.<anonymous> (ModeTile.kt:62)");
                                            }
                                            Modifier.Companion companion2 = Modifier.Companion;
                                            final ModeTileViewModel modeTileViewModel3 = modeTileViewModel2;
                                            Function0 function0 = modeTileViewModel3.onClick;
                                            Function0 function02 = modeTileViewModel3.onLongClick;
                                            Dp.Companion companion3 = Dp.Companion;
                                            Modifier modifierM125padding3ABfNKs = PaddingKt.m125padding3ABfNKs(ClickableKt.m39combinedClickablef5TDLPQ$default(companion2, null, modeTileViewModel3.onLongClickLabel, function02, function0, 103), 16);
                                            ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                            composerImpl4.startReplaceGroup(-1044435099);
                                            boolean zChanged = composerImpl4.changed(modeTileViewModel3);
                                            Object objRememberedValue = composerImpl4.rememberedValue();
                                            Composer.Companion companion4 = Composer.Companion;
                                            if (!zChanged) {
                                                companion4.getClass();
                                                if (objRememberedValue == Composer.Companion.Empty) {
                                                    objRememberedValue = new Function1() { // from class: com.android.systemui.statusbar.policy.ui.dialog.composable.ModeTileKt$ModeTile$1$1$$ExternalSyntheticLambda0
                                                        @Override // kotlin.jvm.functions.Function1
                                                        /* renamed from: invoke */
                                                        public final Object mo781invoke(Object obj5) {
                                                            SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj5;
                                                            switch (i5) {
                                                                case 0:
                                                                    SemanticsPropertiesKt.setStateDescription(semanticsPropertyReceiver, modeTileViewModel3.stateDescription);
                                                                    break;
                                                                default:
                                                                    SemanticsPropertiesKt.setContentDescription(semanticsPropertyReceiver, modeTileViewModel3.subtextDescription);
                                                                    break;
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    };
                                                    composerImpl4.updateRememberedValue(objRememberedValue);
                                                }
                                                composerImpl4.end(false);
                                                Modifier modifierSemantics = SemanticsModifierKt.semantics(modifierM125padding3ABfNKs, false, (Function1) objRememberedValue);
                                                Alignment.Companion.getClass();
                                                BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                                                Arrangement arrangement = Arrangement.INSTANCE;
                                                BiasAlignment.Horizontal horizontal = Alignment.Companion.Start;
                                                arrangement.getClass();
                                                RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.m93spacedByD5KLDUw(12, horizontal), vertical, composerImpl4, 54);
                                                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl4);
                                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl4.currentCompositionLocalScope();
                                                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl4, modifierSemantics);
                                                ComposeUiNode.Companion.getClass();
                                                Function0 function03 = ComposeUiNode.Companion.Constructor;
                                                if (composerImpl4.applier == null) {
                                                    ComposablesKt.invalidApplier();
                                                    throw null;
                                                }
                                                composerImpl4.startReusableNode();
                                                if (composerImpl4.inserting) {
                                                    composerImpl4.createNode(function03);
                                                } else {
                                                    composerImpl4.useNode();
                                                }
                                                Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
                                                Updater.m337setimpl(composerImpl4, rowMeasurePolicy, function2);
                                                Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                                                Updater.m337setimpl(composerImpl4, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
                                                Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                if (composerImpl4.inserting || !Intrinsics.areEqual(composerImpl4.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl4, currentCompositeKeyHash, function23);
                                                }
                                                Function2 function24 = ComposeUiNode.Companion.SetModifier;
                                                Updater.m337setimpl(composerImpl4, modifierMaterializeModifier, function24);
                                                RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                                                IconKt.m1074IconFNF3uiM(modeTileViewModel3.icon, SizeKt.m140size3ABfNKs(companion2, 24), 0L, composerImpl4, 48, 4);
                                                ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(Arrangement.Top, horizontal, composerImpl4, 0);
                                                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl4);
                                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl4.currentCompositionLocalScope();
                                                Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl4, companion2);
                                                composerImpl4.startReusableNode();
                                                if (composerImpl4.inserting) {
                                                    composerImpl4.createNode(function03);
                                                } else {
                                                    composerImpl4.useNode();
                                                }
                                                Updater.m337setimpl(composerImpl4, columnMeasurePolicy, function2);
                                                Updater.m337setimpl(composerImpl4, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
                                                if (composerImpl4.inserting || !Intrinsics.areEqual(composerImpl4.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl4, currentCompositeKeyHash2, function23);
                                                }
                                                Updater.m337setimpl(composerImpl4, modifierMaterializeModifier2, function24);
                                                ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                                                FontWeight.Companion.getClass();
                                                TextKt.m317Text4IGK_g(modeTileViewModel3.text, TestTagKt.testTag(BasicMarqueeKt.m27basicMarquee1Mj1MLw$default(companion2, 1, 62), "name"), 0L, 0L, null, FontWeight.W500, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composerImpl4, 196608, 0, 131036);
                                                FontWeight fontWeight = FontWeight.W400;
                                                Modifier modifierTestTag = TestTagKt.testTag(BasicMarqueeKt.m27basicMarquee1Mj1MLw$default(companion2, 1, 62), modeTileViewModel3.enabled ? "stateOn" : "stateOff");
                                                composerImpl4.startReplaceGroup(1300522347);
                                                boolean zChanged2 = composerImpl4.changed(modeTileViewModel3);
                                                Object objRememberedValue2 = composerImpl4.rememberedValue();
                                                if (!zChanged2) {
                                                    companion4.getClass();
                                                    if (objRememberedValue2 == Composer.Companion.Empty) {
                                                        final int i6 = 1;
                                                        objRememberedValue2 = new Function1() { // from class: com.android.systemui.statusbar.policy.ui.dialog.composable.ModeTileKt$ModeTile$1$1$$ExternalSyntheticLambda0
                                                            @Override // kotlin.jvm.functions.Function1
                                                            /* renamed from: invoke */
                                                            public final Object mo781invoke(Object obj5) {
                                                                SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj5;
                                                                switch (i6) {
                                                                    case 0:
                                                                        SemanticsPropertiesKt.setStateDescription(semanticsPropertyReceiver, modeTileViewModel3.stateDescription);
                                                                        break;
                                                                    default:
                                                                        SemanticsPropertiesKt.setContentDescription(semanticsPropertyReceiver, modeTileViewModel3.subtextDescription);
                                                                        break;
                                                                }
                                                                return Unit.INSTANCE;
                                                            }
                                                        };
                                                        composerImpl4.updateRememberedValue(objRememberedValue2);
                                                    }
                                                    composerImpl4.end(false);
                                                    TextKt.m317Text4IGK_g(modeTileViewModel3.subtext, SemanticsModifierKt.clearAndSetSemantics(modifierTestTag, (Function1) objRememberedValue2), 0L, 0L, null, fontWeight, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composerImpl4, 196608, 0, 131036);
                                                    if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl4, true, true)) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            }, composer2), composer2, 12582912, 120);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 56);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.statusbar.policy.ui.dialog.composable.ModeTileKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    Modifier modifier2 = modifier;
                    int i5 = i2;
                    ModeTileKt.ModeTile(modeTileViewModel, modifier2, (Composer) obj, iUpdateChangedFlags, i5);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
