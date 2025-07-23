package com.android.systemui.keyboard.stickykeys.ui.view;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Center$1;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
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
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.systemui.keyboard.stickykeys.shared.model.Locked;
import com.android.systemui.keyboard.stickykeys.shared.model.ModifierKey;
import com.android.systemui.keyboard.stickykeys.ui.viewmodel.StickyKeysIndicatorViewModel;
import com.android.systemui.media.mediaoutput.compose.theme.TypeKt;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class StickyKeysIndicatorKt {
    /* renamed from: StickyKeyText-GLLOLKI, reason: not valid java name */
    public static final void m2584StickyKeyTextGLLOLKI(final ModifierKey modifierKey, final boolean z, Modifier.Companion companion, Composer composer, final int i) {
        final Modifier.Companion companion2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1454168994);
        if ((((composerImpl.changed(modifierKey) ? 4 : 2) | i | (composerImpl.changed(z) ? 32 : 16) | 384) & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            companion2 = companion;
        } else {
            companion2 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.stickykeys.ui.view.StickyKeyText (StickyKeysIndicator.kt:121)");
            }
            composerImpl.startReplaceGroup(-1303368290);
            String displayedText = modifierKey.getDisplayedText();
            FontWeight.Companion.getClass();
            FontWeight fontWeight = FontWeight.Normal;
            TextStyle.Companion companion3 = TextStyle.Companion;
            TextKt.m316Text4IGK_g(displayedText, companion2, ColorKt.Color(4294638330L), 0L, null, fontWeight, null, 0L, null, null, 0L, 0, false, 0, 0, null, TextStyle.m754copyp1EtxEg$default(TypeKt.getSecRegular(), 0L, TextUnitKt.getSp(15), null, null, 0L, 0, 0L, null, null, 0, 16777213), composerImpl, 197040, 0, 65496);
            composerImpl = composerImpl;
            composerImpl.end(false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(z, companion2, i) { // from class: com.android.systemui.keyboard.stickykeys.ui.view.StickyKeysIndicatorKt$$ExternalSyntheticLambda2
                public final /* synthetic */ boolean f$1;
                public final /* synthetic */ Modifier.Companion f$2;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    boolean z2 = this.f$1;
                    Modifier.Companion companion4 = this.f$2;
                    StickyKeysIndicatorKt.m2584StickyKeyTextGLLOLKI(ModifierKey.this, z2, companion4, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void StickyKeysIndicator(final StickyKeysIndicatorViewModel stickyKeysIndicatorViewModel, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-322293386);
        if ((((composerImpl.changedInstance(stickyKeysIndicatorViewModel) ? 4 : 2) | i) & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.stickykeys.ui.view.StickyKeysIndicator (StickyKeysIndicator.kt:67)");
            }
            StickyKeysIndicator((Map) FlowExtKt.collectAsStateWithLifecycle(stickyKeysIndicatorViewModel.indicatorContent, MapsKt__MapsKt.emptyMap(), composerImpl, 48).getValue(), null, composerImpl, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(i) { // from class: com.android.systemui.keyboard.stickykeys.ui.view.StickyKeysIndicatorKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    StickyKeysIndicatorKt.StickyKeysIndicator(StickyKeysIndicatorViewModel.this, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void StickyKeysIndicator(final Map map, Modifier.Companion companion, Composer composer, final int i) {
        final Modifier.Companion companion2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(371615851);
        int i2 = 4;
        int i3 = 2;
        int i4 = 48;
        if ((((composerImpl.changedInstance(map) ? 4 : 2) | i | 48) & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            companion2 = companion;
        } else {
            Modifier.Companion companion3 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.stickykeys.ui.view.StickyKeysIndicator (StickyKeysIndicator.kt:73)");
            }
            composerImpl.startReplaceGroup(1030679632);
            Alignment.Companion.getClass();
            BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
            Arrangement.INSTANCE.getClass();
            Arrangement$Center$1 arrangement$Center$1 = Arrangement.Center;
            Dp.Companion companion4 = Dp.Companion;
            Modifier m124padding3ABfNKs = PaddingKt.m124padding3ABfNKs(companion3, 16);
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(arrangement$Center$1, vertical, composerImpl, 54);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m124padding3ABfNKs);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier != null) {
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m336setimpl(composerImpl, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
                }
                Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
                RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                composerImpl.startReplaceGroup(390846155);
                for (Map.Entry entry : map.entrySet()) {
                    final ModifierKey modifierKey = (ModifierKey) entry.getKey();
                    final boolean z = ((Locked) entry.getValue()).locked;
                    composerImpl.startMovableGroup(-1982807765, modifierKey);
                    SurfaceKt.m303SurfaceT9BRK9s(PaddingKt.m124padding3ABfNKs(SizeKt.m145widthInVpY3zN4$default(SizeKt.m132heightInVpY3zN4$default(companion3, 42, 0.0f, i3), i4, 0.0f, i3), i2), RoundedCornerShapeKt.m186RoundedCornerShape0680j_4(8), z ? ColorKt.Color(Color.m461getRedimpl(r9), Color.m460getGreenimpl(r9), Color.m458getBlueimpl(r9), 0.9f, Color.m459getColorSpaceimpl(ColorKt.Color(29406))) : ColorKt.Color(Color.m461getRedimpl(r9), Color.m460getGreenimpl(r9), Color.m458getBlueimpl(r9), 0.9f, Color.m459getColorSpaceimpl(ColorKt.Color(7566195))), 0L, 0.0f, 0.0f, null, ComposableLambdaKt.rememberComposableLambda(-1839735975, new Function2() { // from class: com.android.systemui.keyboard.stickykeys.ui.view.StickyKeysIndicatorKt$StickyKeysIndicator$2$1$1
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            Composer composer2 = (Composer) obj;
                            if ((((Number) obj2).intValue() & 3) == 2) {
                                ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                if (composerImpl2.getSkipping()) {
                                    composerImpl2.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.keyboard.stickykeys.ui.view.StickyKeysIndicator.<anonymous>.<anonymous>.<anonymous>.<anonymous> (StickyKeysIndicator.kt:92)");
                            }
                            Alignment.Companion.getClass();
                            BiasAlignment.Vertical vertical2 = Alignment.Companion.CenterVertically;
                            Arrangement.INSTANCE.getClass();
                            Arrangement$Center$1 arrangement$Center$12 = Arrangement.Center;
                            Dp.Companion companion5 = Dp.Companion;
                            Modifier m124padding3ABfNKs2 = PaddingKt.m124padding3ABfNKs(Modifier.Companion, 8);
                            RowMeasurePolicy rowMeasurePolicy2 = RowKt.rowMeasurePolicy(arrangement$Center$12, vertical2, composer2, 54);
                            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl3.currentCompositionLocalScope();
                            Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composer2, m124padding3ABfNKs2);
                            ComposeUiNode.Companion.getClass();
                            Function0 function02 = ComposeUiNode.Companion.Constructor;
                            if (composerImpl3.applier == null) {
                                ComposablesKt.invalidApplier();
                                throw null;
                            }
                            composerImpl3.startReusableNode();
                            if (composerImpl3.inserting) {
                                composerImpl3.createNode(function02);
                            } else {
                                composerImpl3.useNode();
                            }
                            Updater.m336setimpl(composer2, rowMeasurePolicy2, ComposeUiNode.Companion.SetMeasurePolicy);
                            Updater.m336setimpl(composer2, currentCompositionLocalScope2, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                            Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
                            if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl3, currentCompositeKeyHash2, function22);
                            }
                            Updater.m336setimpl(composer2, materializeModifier2, ComposeUiNode.Companion.SetModifier);
                            RowScopeInstance rowScopeInstance2 = RowScopeInstance.INSTANCE;
                            StickyKeysIndicatorKt.m2584StickyKeyTextGLLOLKI(ModifierKey.this, z, null, composer2, 0);
                            composerImpl3.end(true);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            return Unit.INSTANCE;
                        }
                    }, composerImpl), composerImpl, 12582912, 120);
                    composerImpl.end(false);
                    companion3 = companion3;
                    i3 = i3;
                    i4 = i4;
                    i2 = 4;
                }
                Modifier.Companion companion5 = companion3;
                composerImpl.end(false);
                composerImpl.end(true);
                composerImpl.end(false);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                companion2 = companion5;
            } else {
                ComposablesKt.invalidApplier();
                throw null;
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(map, companion2, i) { // from class: com.android.systemui.keyboard.stickykeys.ui.view.StickyKeysIndicatorKt$$ExternalSyntheticLambda1
                public final /* synthetic */ Map f$0;
                public final /* synthetic */ Modifier.Companion f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    StickyKeysIndicatorKt.StickyKeysIndicator(this.f$0, this.f$1, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
