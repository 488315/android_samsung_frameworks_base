package com.android.systemui.volume.dialog.sliders.ui.compose;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.text.BasicTextKt$$ExternalSyntheticOutline0;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.SliderColors;
import androidx.compose.material3.SliderDefaults;
import androidx.compose.material3.SliderState;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.layout.LayoutIdKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.LayoutDirection;
import com.android.systemui.volume.dialog.sliders.ui.compose.Contents;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public abstract class VolumeDialogSliderTrackKt {
    /* JADX WARN: Removed duplicated region for block: B:122:0x02a2  */
    /* JADX WARN: Removed duplicated region for block: B:126:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x013f  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0161  */
    /* renamed from: SliderTrack-q58E_xs, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m3219SliderTrackq58E_xs(final SliderState sliderState, final boolean z, Modifier modifier, final SliderColors sliderColors, float f, float f2, float f3, float f4, boolean z2, final ComposableLambdaImpl composableLambdaImpl, final ComposableLambdaImpl composableLambdaImpl2, Composer composer, final int i, final int i2, final int i3) {
        int i4;
        boolean z3;
        int i5;
        int i6;
        float f5;
        float f6;
        boolean z4;
        float f7;
        Modifier modifier2;
        float f8;
        boolean z5;
        float f9;
        Composer.Companion companion;
        int i7;
        float f10;
        boolean z6;
        float f11;
        boolean z7;
        final float f12;
        final float f13;
        final float f14;
        final boolean z8;
        final Modifier modifier3;
        final float f15;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1365209625);
        if ((i & 6) == 0) {
            i4 = ((i & 8) == 0 ? composerImpl.changed(sliderState) : composerImpl.changedInstance(sliderState) ? 4 : 2) | i;
        } else {
            i4 = i;
        }
        if ((i & 48) == 0) {
            i4 |= composerImpl.changed(z) ? 32 : 16;
        }
        int i8 = i4 | 384;
        if ((i & 3072) == 0) {
            i8 |= composerImpl.changed(sliderColors) ? 2048 : 1024;
        }
        int i9 = 14376960 | i8;
        int i10 = i3 & 256;
        if (i10 == 0) {
            if ((100663296 & i) == 0) {
                z3 = z2;
                i9 |= composerImpl.changed(z3) ? 67108864 : 33554432;
            }
            if ((i & 805306368) == 0) {
                i9 |= composerImpl.changedInstance(composableLambdaImpl) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
            }
            i5 = i9;
            int i11 = i2 | 6;
            if ((i2 & 48) != 0) {
                i6 = i10;
                i11 |= composerImpl.changedInstance(composableLambdaImpl2) ? 32 : 16;
            } else {
                i6 = i10;
            }
            int i12 = i11 | 384;
            if ((i5 & 306783379) != 306783378 && (i12 & 147) == 146 && composerImpl.getSkipping()) {
                composerImpl.skipToGroupEnd();
                modifier3 = modifier;
                f13 = f;
                f12 = f2;
                f14 = f3;
                f15 = f4;
                z8 = z3;
            } else {
                composerImpl.startDefaults();
                if ((i & 1) != 0 || composerImpl.getDefaultsInvalid()) {
                    Modifier.Companion companion2 = Modifier.Companion;
                    f5 = 6;
                    Dp.Companion companion3 = Dp.Companion;
                    f6 = 12;
                    float f16 = 2;
                    float f17 = 40;
                    if (i6 != 0) {
                        z3 = false;
                    }
                    z4 = z3;
                    f7 = f17;
                    modifier2 = companion2;
                    f8 = f16;
                } else {
                    composerImpl.skipToGroupEnd();
                    f5 = f;
                    f8 = f3;
                    z4 = z3;
                    modifier2 = modifier;
                    f6 = f2;
                    f7 = f4;
                }
                composerImpl.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.volume.dialog.sliders.ui.compose.SliderTrack (VolumeDialogSliderTrack.kt:65)");
                }
                boolean z9 = composerImpl.consume(CompositionLocalsKt.LocalLayoutDirection) != LayoutDirection.Rtl;
                composerImpl.startReplaceGroup(-1770498279);
                int i13 = i5 & 14;
                float f18 = f7;
                z5 = i13 != 4 || ((i5 & 8) != 0 && composerImpl.changed(sliderState));
                Object objRememberedValue = composerImpl.rememberedValue();
                float f19 = f5;
                Composer.Companion companion4 = Composer.Companion;
                if (z5) {
                    companion4.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        f9 = f18;
                        companion = companion4;
                        i7 = i13;
                        f10 = f6;
                        z6 = false;
                        f11 = f19;
                        objRememberedValue = new TrackMeasurePolicy(sliderState, (!z4 && z9) || z4, f11, z4, null);
                        z7 = z4;
                        composerImpl.updateRememberedValue(objRememberedValue);
                    } else {
                        f9 = f18;
                        i7 = i13;
                        z7 = z4;
                        companion = companion4;
                        f10 = f6;
                        z6 = false;
                        f11 = f19;
                    }
                    TrackMeasurePolicy trackMeasurePolicy = (TrackMeasurePolicy) objRememberedValue;
                    composerImpl.end(z6);
                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier2);
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
                    Updater.m337setimpl(composerImpl, trackMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                    Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                    Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
                    }
                    Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                    SliderDefaults sliderDefaults = SliderDefaults.INSTANCE;
                    Modifier.Companion companion5 = Modifier.Companion;
                    Modifier modifierM144width3ABfNKs = z7 ? SizeKt.m144width3ABfNKs(companion5, f9) : SizeKt.m131height3ABfNKs(companion5, f9);
                    companion5.getClass();
                    Modifier modifierLayoutId = LayoutIdKt.layoutId(modifierM144width3ABfNKs, Contents.Track.INSTANCE);
                    Object objM = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl, 810982059, companion);
                    if (objM == Composer.Companion.Empty) {
                        objM = new Function3() { // from class: com.android.systemui.volume.dialog.sliders.ui.compose.VolumeDialogSliderTrackKt$SliderTrack$1$1$1
                            @Override // kotlin.jvm.functions.Function3
                            public final /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
                                long j = ((Offset) obj2).packedValue;
                                long j2 = ((Color) obj3).value;
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl.updateRememberedValue(objM);
                    }
                    Function3 function3 = (Function3) objM;
                    composerImpl.end(false);
                    int i14 = i5 << 6;
                    int i15 = (i5 << 3) & 57344;
                    int i16 = (i14 & 7168) | 807075848 | i7 | ((i5 >> 12) & 112) | i15 | ((i5 << 9) & 29360128) | (234881024 & i14);
                    float f20 = f9;
                    Modifier modifier4 = modifier2;
                    float f21 = f11;
                    float f22 = f10;
                    sliderDefaults.m299TrackmnvyFg4(sliderState, f22, modifierLayoutId, z, sliderColors, null, function3, f21, f8, composerImpl, i16, 0);
                    int i17 = i5 & 112;
                    TrackIcon(composableLambdaImpl, z, Contents.Active.TrackStartIcon.INSTANCE, trackMeasurePolicy, sliderColors, null, composerImpl, ((i5 >> 27) & 14) | 384 | i17 | i15);
                    TrackIcon(null, z, Contents.Active.TrackEndIcon.INSTANCE, trackMeasurePolicy, sliderColors, null, composerImpl, 390 | i17 | i15);
                    TrackIcon(composableLambdaImpl2, z, Contents.Inactive.TrackStartIcon.INSTANCE, trackMeasurePolicy, sliderColors, null, composerImpl, ((i12 >> 3) & 14) | 384 | i17 | i15);
                    TrackIcon(null, z, Contents.Inactive.TrackEndIcon.INSTANCE, trackMeasurePolicy, sliderColors, null, composerImpl, 390 | i17 | i15);
                    composerImpl.end(true);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    f12 = f22;
                    f13 = f21;
                    f14 = f8;
                    z8 = z7;
                    modifier3 = modifier4;
                    f15 = f20;
                }
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.volume.dialog.sliders.ui.compose.VolumeDialogSliderTrackKt$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Integer) obj2).getClass();
                        int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                        int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(i2);
                        ComposableLambdaImpl composableLambdaImpl3 = composableLambdaImpl2;
                        int i18 = i3;
                        VolumeDialogSliderTrackKt.m3219SliderTrackq58E_xs(sliderState, z, modifier3, sliderColors, f13, f12, f14, f15, z8, composableLambdaImpl, composableLambdaImpl3, (Composer) obj, iUpdateChangedFlags, iUpdateChangedFlags2, i18);
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        i9 = 115040256 | i8;
        z3 = z2;
        if ((i & 805306368) == 0) {
        }
        i5 = i9;
        int i112 = i2 | 6;
        if ((i2 & 48) != 0) {
        }
        int i122 = i112 | 384;
        if ((i5 & 306783379) != 306783378) {
            composerImpl.startDefaults();
            if ((i & 1) != 0) {
                Modifier.Companion companion22 = Modifier.Companion;
                f5 = 6;
                Dp.Companion companion32 = Dp.Companion;
                f6 = 12;
                float f162 = 2;
                float f172 = 40;
                if (i6 != 0) {
                }
                z4 = z3;
                f7 = f172;
                modifier2 = companion22;
                f8 = f162;
                composerImpl.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                }
                if (composerImpl.consume(CompositionLocalsKt.LocalLayoutDirection) != LayoutDirection.Rtl) {
                }
                composerImpl.startReplaceGroup(-1770498279);
                int i132 = i5 & 14;
                float f182 = f7;
                if (i132 != 4) {
                    Object objRememberedValue2 = composerImpl.rememberedValue();
                    float f192 = f5;
                    Composer.Companion companion42 = Composer.Companion;
                    if (z5) {
                    }
                }
            }
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    public static final void TrackIcon(final ComposableLambdaImpl composableLambdaImpl, final boolean z, final Contents contents, final TrackMeasurePolicy trackMeasurePolicy, final SliderColors sliderColors, Modifier modifier, Composer composer, final int i) {
        int i2;
        final Modifier modifier2;
        long j;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-943965896);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(composableLambdaImpl) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(z) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= (i & 512) == 0 ? composerImpl.changed(contents) : composerImpl.changedInstance(contents) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= (i & 4096) == 0 ? composerImpl.changed(trackMeasurePolicy) : composerImpl.changedInstance(trackMeasurePolicy) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changed(sliderColors) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if (((i2 | 196608) & 74899) == 74898 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            modifier2 = modifier;
        } else {
            modifier2 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.dialog.sliders.ui.compose.TrackIcon (VolumeDialogSliderTrack.kt:140)");
            }
            if (composableLambdaImpl == null) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    final int i3 = 0;
                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.volume.dialog.sliders.ui.compose.VolumeDialogSliderTrackKt$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            switch (i3) {
                                case 0:
                                    ((Integer) obj2).getClass();
                                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                                    SliderColors sliderColors2 = sliderColors;
                                    Modifier modifier3 = modifier2;
                                    VolumeDialogSliderTrackKt.TrackIcon(composableLambdaImpl, z, contents, trackMeasurePolicy, sliderColors2, modifier3, (Composer) obj, iUpdateChangedFlags);
                                    break;
                                default:
                                    ((Integer) obj2).getClass();
                                    int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                                    SliderColors sliderColors3 = sliderColors;
                                    Modifier modifier4 = modifier2;
                                    VolumeDialogSliderTrackKt.TrackIcon(composableLambdaImpl, z, contents, trackMeasurePolicy, sliderColors3, modifier4, (Composer) obj, iUpdateChangedFlags2);
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            if (contents instanceof Contents.Inactive) {
                j = z ? sliderColors.inactiveTickColor : sliderColors.disabledInactiveTickColor;
            } else {
                if (!(contents instanceof Contents.Active)) {
                    if (!(contents instanceof Contents.Track)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    throw new IllegalStateException((contents + " is unsupported by the TrackIcon").toString());
                }
                j = z ? sliderColors.activeTickColor : sliderColors.disabledActiveTickColor;
            }
            Modifier modifierFillMaxSize = SizeKt.fillMaxSize(LayoutIdKt.layoutId(modifier2, contents), 1.0f);
            Alignment.Companion.getClass();
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierFillMaxSize);
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
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            final BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            CompositionLocalKt.CompositionLocalProvider(ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m456boximpl(j)), ComposableLambdaKt.rememberComposableLambda(2029817522, new Function2() { // from class: com.android.systemui.volume.dialog.sliders.ui.compose.VolumeDialogSliderTrackKt$TrackIcon$2$1
                /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
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
                                ComposerKt.traceEventStart("com.android.systemui.volume.dialog.sliders.ui.compose.TrackIcon.<anonymous>.<anonymous> (VolumeDialogSliderTrack.kt:166)");
                            }
                            composableLambdaImpl.invoke(boxScopeInstance, trackMeasurePolicy, composer2, 0);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 56);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup2 = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup2 != null) {
            final int i4 = 1;
            recomposeScopeImplEndRestartGroup2.block = new Function2() { // from class: com.android.systemui.volume.dialog.sliders.ui.compose.VolumeDialogSliderTrackKt$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    switch (i4) {
                        case 0:
                            ((Integer) obj2).getClass();
                            int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                            SliderColors sliderColors2 = sliderColors;
                            Modifier modifier3 = modifier2;
                            VolumeDialogSliderTrackKt.TrackIcon(composableLambdaImpl, z, contents, trackMeasurePolicy, sliderColors2, modifier3, (Composer) obj, iUpdateChangedFlags);
                            break;
                        default:
                            ((Integer) obj2).getClass();
                            int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                            SliderColors sliderColors3 = sliderColors;
                            Modifier modifier4 = modifier2;
                            VolumeDialogSliderTrackKt.TrackIcon(composableLambdaImpl, z, contents, trackMeasurePolicy, sliderColors3, modifier4, (Composer) obj, iUpdateChangedFlags2);
                            break;
                    }
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
