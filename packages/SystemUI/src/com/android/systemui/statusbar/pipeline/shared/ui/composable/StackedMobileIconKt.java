package com.android.systemui.statusbar.pipeline.shared.ui.composable;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.CanvasKt;
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
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.BlendMode;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.ui.compose.IconKt;
import com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModel;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

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
                RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    recomposeScopeImplEndRestartGroup.block = new Function2(stackedMobileIconViewModel, modifier, i, i3) { // from class: com.android.systemui.statusbar.pipeline.shared.ui.composable.StackedMobileIconKt$$ExternalSyntheticLambda0
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
            float fMo53toDpGaN1DYA = density.mo53toDpGaN1DYA(StackedMobileIconDimensions.IconPaddingSp);
            Density density2 = (Density) composerImpl.consume(staticProvidableCompositionLocal);
            Arrangement arrangement = Arrangement.INSTANCE;
            float fMo53toDpGaN1DYA2 = density2.mo53toDpGaN1DYA(StackedMobileIconDimensions.IconSpacingSp);
            arrangement.getClass();
            Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_4 = Arrangement.m92spacedBy0680j_4(fMo53toDpGaN1DYA2);
            Alignment.Companion.getClass();
            BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
            Modifier modifierM127paddingVpY3zN4$default = PaddingKt.m127paddingVpY3zN4$default(modifier, fMo53toDpGaN1DYA, 0.0f, 2);
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(spacedAlignedM92spacedBy0680j_4, vertical, composerImpl, 48);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM127paddingVpY3zN4$default);
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
            Updater.m337setimpl(composerImpl, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            Icon.Resource networkTypeIcon = stackedMobileIconViewModel.getNetworkTypeIcon();
            composerImpl.startReplaceGroup(2106096303);
            if (networkTypeIcon != null) {
                FillElement fillElement = SizeKt.FillWholeMaxHeight;
                modifier.then(fillElement);
                IconKt.m1074IconFNF3uiM(networkTypeIcon, fillElement, j, composerImpl, 48, 0);
            }
            composerImpl.end(false);
            m3096StackedMobileIcon3IgeMak(dualSim, j, null, composerImpl, 0);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup2 = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup2 != null) {
            recomposeScopeImplEndRestartGroup2.block = new Function2(stackedMobileIconViewModel, modifier, i, i2) { // from class: com.android.systemui.statusbar.pipeline.shared.ui.composable.StackedMobileIconKt$$ExternalSyntheticLambda0
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

    /* JADX WARN: Removed duplicated region for block: B:31:0x00c9  */
    /* renamed from: StackedMobileIcon-3IgeMak, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m3096StackedMobileIcon3IgeMak(final StackedMobileIconViewModel.DualSim dualSim, final long j, Modifier modifier, Composer composer, final int i) {
        final Modifier modifier2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-410697635);
        int i2 = i | (composerImpl.changedInstance(dualSim) ? 4 : 2) | (composerImpl.changed(j) ? 32 : 16) | 384;
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            modifier2 = modifier;
        } else {
            Modifier.Companion companion = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.statusbar.pipeline.shared.ui.composable.StackedMobileIcon (StackedMobileIcon.kt:89)");
            }
            final int iMax = Math.max(dualSim.primary.numberOfLevels, dualSim.secondary.numberOfLevels) - 1;
            BarsDependentDimensions barsDependentDimensions = iMax == 5 ? FiveBarsDimensions.INSTANCE : FourBarsDimensions.INSTANCE;
            Density density = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
            Dp dpM837boximpl = Dp.m837boximpl(density.mo53toDpGaN1DYA(barsDependentDimensions.totalWidth));
            StackedMobileIconDimensions.INSTANCE.getClass();
            Pair pair = new Pair(dpM837boximpl, Dp.m837boximpl(density.mo53toDpGaN1DYA(StackedMobileIconDimensions.IconHeightSp)));
            Modifier modifierM131height3ABfNKs = SizeKt.m131height3ABfNKs(SizeKt.m144width3ABfNKs(companion, ((Dp) pair.getFirst()).value), ((Dp) pair.getSecond()).value);
            composerImpl.startReplaceGroup(1513366563);
            boolean zChanged = composerImpl.changed(barsDependentDimensions) | composerImpl.changed(iMax) | composerImpl.changedInstance(dualSim) | ((i2 & 112) == 32);
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!zChanged) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    final BarsDependentDimensions barsDependentDimensions2 = barsDependentDimensions;
                    Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.composable.StackedMobileIconKt$$ExternalSyntheticLambda2
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            SignalIconModel.Cellular cellular;
                            char c;
                            long j2;
                            float f;
                            boolean z;
                            StackedMobileIconViewModel.DualSim dualSim2;
                            float f2;
                            int i3;
                            boolean z2 = true;
                            DrawScope drawScope = (DrawScope) obj;
                            StackedMobileIconDimensions.INSTANCE.getClass();
                            int iMo649roundToPxR2X_6o = drawScope.mo649roundToPxR2X_6o(StackedMobileIconDimensions.BarsVerticalPaddingSp);
                            BarsDependentDimensions barsDependentDimensions3 = barsDependentDimensions2;
                            int iMo649roundToPxR2X_6o2 = drawScope.mo649roundToPxR2X_6o(barsDependentDimensions3.barsHorizontalPadding);
                            int i4 = iMax;
                            char c2 = ' ';
                            float fIntBitsToFloat = (Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() >> 32)) - ((i4 - 1) * iMo649roundToPxR2X_6o2)) / i4;
                            float fMo57toPxR2X_6o = drawScope.mo57toPxR2X_6o(StackedMobileIconDimensions.SecondaryBarHeightSp);
                            float fMo57toPxR2X_6o2 = drawScope.mo57toPxR2X_6o(barsDependentDimensions3.barBaseHeight);
                            StackedMobileIconViewModel.DualSim dualSim3 = dualSim;
                            SignalIconModel.Cellular cellular2 = dualSim3.primary;
                            long j3 = j;
                            if (1 <= i4) {
                                float f3 = 0.0f;
                                SignalIconModel.Cellular cellular3 = cellular2;
                                int i5 = 1;
                                while (true) {
                                    float fIntBitsToFloat2 = Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() & 4294967295L)) - fMo57toPxR2X_6o;
                                    SignalIconModel.Cellular cellular4 = dualSim3.secondary;
                                    if (i5 <= cellular4.numberOfLevels) {
                                        z = z2;
                                        c = c2;
                                        f2 = fIntBitsToFloat;
                                        long jFloatToRawIntBits = (Float.floatToRawIntBits(f3) << c) | (Float.floatToRawIntBits(fIntBitsToFloat2) & 4294967295L);
                                        Offset.Companion companion2 = Offset.Companion;
                                        f = f3;
                                        long jFloatToRawIntBits2 = (Float.floatToRawIntBits(fMo57toPxR2X_6o) & 4294967295L) | (Float.floatToRawIntBits(f2) << c);
                                        Size.Companion companion3 = Size.Companion;
                                        dualSim2 = dualSim3;
                                        cellular = cellular3;
                                        StackedMobileIconKt.m3097drawMobileIconBarn3YQ8UE$default(drawScope, cellular4.level, i5, jFloatToRawIntBits, jFloatToRawIntBits2, j3);
                                    } else {
                                        f = f3;
                                        z = z2;
                                        dualSim2 = dualSim3;
                                        cellular = cellular3;
                                        c = c2;
                                        f2 = fIntBitsToFloat;
                                    }
                                    int i6 = i5;
                                    long j4 = j3;
                                    if (i6 <= cellular.numberOfLevels) {
                                        StackedMobileIconDimensions.INSTANCE.getClass();
                                        float fMo57toPxR2X_6o3 = (drawScope.mo57toPxR2X_6o(StackedMobileIconDimensions.BarsLevelIncrementSp) * (i6 - 1)) + fMo57toPxR2X_6o2;
                                        long jFloatToRawIntBits3 = (Float.floatToRawIntBits(f) << c) | (Float.floatToRawIntBits((fIntBitsToFloat2 - iMo649roundToPxR2X_6o) - fMo57toPxR2X_6o3) & 4294967295L);
                                        Offset.Companion companion4 = Offset.Companion;
                                        long jFloatToRawIntBits4 = (Float.floatToRawIntBits(f2) << c) | (Float.floatToRawIntBits(fMo57toPxR2X_6o3) & 4294967295L);
                                        Size.Companion companion5 = Size.Companion;
                                        i3 = i6;
                                        StackedMobileIconKt.m3097drawMobileIconBarn3YQ8UE$default(drawScope, cellular.level, i3, jFloatToRawIntBits3, jFloatToRawIntBits4, j4);
                                        j2 = j4;
                                    } else {
                                        j2 = j4;
                                        i3 = i6;
                                    }
                                    float f4 = f2 + iMo649roundToPxR2X_6o2 + f;
                                    if (i3 == i4) {
                                        break;
                                    }
                                    i5 = i3 + 1;
                                    dualSim3 = dualSim2;
                                    f3 = f4;
                                    cellular3 = cellular;
                                    j3 = j2;
                                    z2 = z;
                                    fIntBitsToFloat = f2;
                                    c2 = c;
                                }
                            } else {
                                cellular = cellular2;
                                c = ' ';
                                j2 = j3;
                            }
                            if (cellular.showExclamationMark) {
                                StackedMobileIconDimensions.INSTANCE.getClass();
                                long j5 = StackedMobileIconDimensions.ExclamationDiameterSp;
                                float fMo57toPxR2X_6o4 = drawScope.mo57toPxR2X_6o(j5);
                                float f5 = 2;
                                float fMo57toPxR2X_6o5 = drawScope.mo57toPxR2X_6o(j5) / f5;
                                long j6 = StackedMobileIconDimensions.ExclamationHeightSp;
                                float fMo57toPxR2X_6o6 = drawScope.mo57toPxR2X_6o(StackedMobileIconDimensions.ExclamationVerticalSpacing) + drawScope.mo57toPxR2X_6o(j6) + fMo57toPxR2X_6o4;
                                float fIntBitsToFloat3 = Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() >> c)) - drawScope.mo57toPxR2X_6o(StackedMobileIconDimensions.ExclamationHorizontalOffset);
                                float fIntBitsToFloat4 = Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() & 4294967295L)) - fMo57toPxR2X_6o5;
                                long jFloatToRawIntBits5 = (Float.floatToRawIntBits(fIntBitsToFloat3) << c) | (Float.floatToRawIntBits(fIntBitsToFloat4) & 4294967295L);
                                Offset.Companion companion6 = Offset.Companion;
                                int i7 = (int) (jFloatToRawIntBits5 >> c);
                                float fIntBitsToFloat5 = Float.intBitsToFloat(i7) - fMo57toPxR2X_6o5;
                                float fIntBitsToFloat6 = Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() & 4294967295L)) - fMo57toPxR2X_6o6;
                                long jFloatToRawIntBits6 = (Float.floatToRawIntBits(fIntBitsToFloat5) << c) | (Float.floatToRawIntBits(fIntBitsToFloat6) & 4294967295L);
                                long jFloatToRawIntBits7 = (Float.floatToRawIntBits(fMo57toPxR2X_6o5) << c) | (Float.floatToRawIntBits(fMo57toPxR2X_6o5) & 4294967295L);
                                CornerRadius.Companion companion7 = CornerRadius.Companion;
                                float fIntBitsToFloat7 = Float.intBitsToFloat(i7);
                                float fIntBitsToFloat8 = Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() & 4294967295L)) - (fMo57toPxR2X_6o6 / f5);
                                long jFloatToRawIntBits8 = (Float.floatToRawIntBits(fIntBitsToFloat7) << c) | (Float.floatToRawIntBits(fIntBitsToFloat8) & 4294967295L);
                                Color.Companion.getClass();
                                long j7 = Color.Transparent;
                                float fMo57toPxR2X_6o7 = drawScope.mo57toPxR2X_6o(StackedMobileIconDimensions.ExclamationCutoutRadiusSp);
                                BlendMode.Companion.getClass();
                                DrawScope.m534drawCircleVaOC9Bg$default(drawScope, j7, fMo57toPxR2X_6o7, jFloatToRawIntBits8, 0.0f, null, BlendMode.SrcIn, 56);
                                float fMo57toPxR2X_6o8 = drawScope.mo57toPxR2X_6o(j6);
                                long jFloatToRawIntBits9 = (Float.floatToRawIntBits(fMo57toPxR2X_6o4) << c) | (Float.floatToRawIntBits(fMo57toPxR2X_6o8) & 4294967295L);
                                Size.Companion companion8 = Size.Companion;
                                long j8 = j2;
                                DrawScope.m543drawRoundRectuAw5IA$default(drawScope, j8, jFloatToRawIntBits6, jFloatToRawIntBits9, jFloatToRawIntBits7, null, 0.0f, IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp);
                                DrawScope.m534drawCircleVaOC9Bg$default(drawScope, j8, fMo57toPxR2X_6o5, jFloatToRawIntBits5, 0.0f, null, 0, 120);
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(function1);
                    objRememberedValue = function1;
                }
                composerImpl.end(false);
                CanvasKt.Canvas(modifierM131height3ABfNKs, (Function1) objRememberedValue, composerImpl, 0);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                modifier2 = companion;
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(j, modifier2, i) { // from class: com.android.systemui.statusbar.pipeline.shared.ui.composable.StackedMobileIconKt$$ExternalSyntheticLambda3
                public final /* synthetic */ long f$1;
                public final /* synthetic */ Modifier f$2;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    StackedMobileIconKt.m3096StackedMobileIcon3IgeMak(this.f$0, this.f$1, this.f$2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* renamed from: drawMobileIconBar-n3YQ8UE$default, reason: not valid java name */
    public static void m3097drawMobileIconBarn3YQ8UE$default(DrawScope drawScope, int i, int i2, long j, long j2, long j3) {
        long jColor = ColorKt.Color(Color.m463getRedimpl(j3), Color.m462getGreenimpl(j3), Color.m460getBlueimpl(j3), 0.3f, Color.m461getColorSpaceimpl(j3));
        float fIntBitsToFloat = Float.intBitsToFloat((int) (j2 >> 32)) / 2;
        long jFloatToRawIntBits = (Float.floatToRawIntBits(fIntBitsToFloat) << 32) | (Float.floatToRawIntBits(fIntBitsToFloat) & 4294967295L);
        CornerRadius.Companion companion = CornerRadius.Companion;
        DrawScope.m543drawRoundRectuAw5IA$default(drawScope, i >= i2 ? j3 : jColor, j, j2, jFloatToRawIntBits, null, 0.0f, IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp);
    }
}
