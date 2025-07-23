package com.android.systemui.statusbar.pipeline.shared.ui.composable;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.FillElement;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Density;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.ui.compose.IconKt;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModel;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class StackedMobileIconKt {
    public static final void StackedMobileIcon(final StackedMobileIconViewModel stackedMobileIconViewModel, final Modifier modifier, Composer composer, final int i) {
        final int i2 = 1;
        final int i3 = 0;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1902649125);
        if ((((composerImpl.changed(stackedMobileIconViewModel) ? 4 : 2) | i | 48) & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            modifier = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.statusbar.pipeline.shared.ui.composable.StackedMobileIcon (StackedMobileIcon.kt:64)");
            }
            StackedMobileIconViewModel.DualSim dualSim = stackedMobileIconViewModel.getDualSim();
            if (dualSim == null) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
                if (endRestartGroup != null) {
                    endRestartGroup.block = new Function2(stackedMobileIconViewModel, modifier, i, i3) { // from class: com.android.systemui.statusbar.pipeline.shared.ui.composable.StackedMobileIconKt$$ExternalSyntheticLambda0
                        public final /* synthetic */ int $r8$classId;
                        public final /* synthetic */ StackedMobileIconViewModel f$0;
                        public final /* synthetic */ Modifier f$1;

                        {
                            this.$r8$classId = i3;
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            int i4 = this.$r8$classId;
                            Composer composer2 = (Composer) obj;
                            ((Integer) obj2).getClass();
                            switch (i4) {
                                case 0:
                                    StackedMobileIconKt.StackedMobileIcon(this.f$0, this.f$1, composer2, RecomposeScopeImplKt.updateChangedFlags(1));
                                    break;
                                default:
                                    StackedMobileIconKt.StackedMobileIcon(this.f$0, this.f$1, composer2, RecomposeScopeImplKt.updateChangedFlags(1));
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            long j = ((Color) composerImpl.consume(ContentColorKt.LocalContentColor)).value;
            StaticProvidableCompositionLocal staticProvidableCompositionLocal = CompositionLocalsKt.LocalDensity;
            Density density = (Density) composerImpl.consume(staticProvidableCompositionLocal);
            StackedMobileIconDimensions.INSTANCE.getClass();
            float mo52toDpGaN1DYA = density.mo52toDpGaN1DYA(StackedMobileIconDimensions.IconPaddingSp);
            Density density2 = (Density) composerImpl.consume(staticProvidableCompositionLocal);
            Arrangement arrangement = Arrangement.INSTANCE;
            float mo52toDpGaN1DYA2 = density2.mo52toDpGaN1DYA(StackedMobileIconDimensions.IconSpacingSp);
            arrangement.getClass();
            Arrangement.SpacedAligned m91spacedBy0680j_4 = Arrangement.m91spacedBy0680j_4(mo52toDpGaN1DYA2);
            Alignment.Companion.getClass();
            BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
            Modifier m126paddingVpY3zN4$default = PaddingKt.m126paddingVpY3zN4$default(modifier, mo52toDpGaN1DYA, 0.0f, 2);
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(m91spacedBy0680j_4, vertical, composerImpl, 48);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m126paddingVpY3zN4$default);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
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
            Icon.Resource networkTypeIcon = stackedMobileIconViewModel.getNetworkTypeIcon();
            composerImpl.startReplaceGroup(2106096303);
            if (networkTypeIcon != null) {
                FillElement fillElement = SizeKt.FillWholeMaxHeight;
                modifier.then(fillElement);
                IconKt.m1072IconFNF3uiM(networkTypeIcon, fillElement, j, composerImpl, 48, 0);
            }
            composerImpl.end(false);
            m3081StackedMobileIcon3IgeMak(dualSim, j, null, composerImpl, 0);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.block = new Function2(stackedMobileIconViewModel, modifier, i, i2) { // from class: com.android.systemui.statusbar.pipeline.shared.ui.composable.StackedMobileIconKt$$ExternalSyntheticLambda0
                public final /* synthetic */ int $r8$classId;
                public final /* synthetic */ StackedMobileIconViewModel f$0;
                public final /* synthetic */ Modifier f$1;

                {
                    this.$r8$classId = i2;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    int i4 = this.$r8$classId;
                    Composer composer2 = (Composer) obj;
                    ((Integer) obj2).getClass();
                    switch (i4) {
                        case 0:
                            StackedMobileIconKt.StackedMobileIcon(this.f$0, this.f$1, composer2, RecomposeScopeImplKt.updateChangedFlags(1));
                            break;
                        default:
                            StackedMobileIconKt.StackedMobileIcon(this.f$0, this.f$1, composer2, RecomposeScopeImplKt.updateChangedFlags(1));
                            break;
                    }
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x00c7, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L31;
     */
    /* renamed from: StackedMobileIcon-3IgeMak, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m3081StackedMobileIcon3IgeMak(final com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModel.DualSim r16, final long r17, androidx.compose.ui.Modifier r19, androidx.compose.runtime.Composer r20, final int r21) {
        /*
            Method dump skipped, instructions count: 253
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.shared.ui.composable.StackedMobileIconKt.m3081StackedMobileIcon3IgeMak(com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModel$DualSim, long, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int):void");
    }

    /* renamed from: drawMobileIconBar-n3YQ8UE$default, reason: not valid java name */
    public static void m3082drawMobileIconBarn3YQ8UE$default(DrawScope drawScope, int i, int i2, long j, long j2, long j3) {
        long Color;
        Color = ColorKt.Color(Color.m461getRedimpl(j3), Color.m460getGreenimpl(j3), Color.m458getBlueimpl(j3), 0.3f, Color.m459getColorSpaceimpl(j3));
        float intBitsToFloat = Float.intBitsToFloat((int) (j2 >> 32)) / 2;
        long floatToRawIntBits = (Float.floatToRawIntBits(intBitsToFloat) << 32) | (Float.floatToRawIntBits(intBitsToFloat) & 4294967295L);
        CornerRadius.Companion companion = CornerRadius.Companion;
        DrawScope.m541drawRoundRectuAw5IA$default(drawScope, i >= i2 ? j3 : Color, j, j2, floatToRawIntBits, null, 0.0f, IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp);
    }
}
