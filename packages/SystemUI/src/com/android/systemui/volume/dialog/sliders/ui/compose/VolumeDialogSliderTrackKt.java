package com.android.systemui.volume.dialog.sliders.ui.compose;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.SliderColors;
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
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.layout.LayoutIdKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import com.android.systemui.volume.dialog.sliders.ui.compose.Contents;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class VolumeDialogSliderTrackKt {
    /* JADX WARN: Removed duplicated region for block: B:107:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x02b9  */
    /* renamed from: SliderTrack-q58E_xs, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m3202SliderTrackq58E_xs(final androidx.compose.material3.SliderState r29, final boolean r30, androidx.compose.ui.Modifier r31, final androidx.compose.material3.SliderColors r32, float r33, float r34, float r35, float r36, boolean r37, final androidx.compose.runtime.internal.ComposableLambdaImpl r38, final androidx.compose.runtime.internal.ComposableLambdaImpl r39, androidx.compose.runtime.Composer r40, final int r41, final int r42, final int r43) {
        /*
            Method dump skipped, instructions count: 702
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.dialog.sliders.ui.compose.VolumeDialogSliderTrackKt.m3202SliderTrackq58E_xs(androidx.compose.material3.SliderState, boolean, androidx.compose.ui.Modifier, androidx.compose.material3.SliderColors, float, float, float, float, boolean, androidx.compose.runtime.internal.ComposableLambdaImpl, androidx.compose.runtime.internal.ComposableLambdaImpl, androidx.compose.runtime.Composer, int, int, int):void");
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
                RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
                if (endRestartGroup != null) {
                    final int i3 = 0;
                    endRestartGroup.block = new Function2() { // from class: com.android.systemui.volume.dialog.sliders.ui.compose.VolumeDialogSliderTrackKt$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            switch (i3) {
                                case 0:
                                    ((Integer) obj2).getClass();
                                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                                    SliderColors sliderColors2 = sliderColors;
                                    Modifier modifier3 = modifier2;
                                    VolumeDialogSliderTrackKt.TrackIcon(composableLambdaImpl, z, contents, trackMeasurePolicy, sliderColors2, modifier3, (Composer) obj, updateChangedFlags);
                                    break;
                                default:
                                    ((Integer) obj2).getClass();
                                    int updateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                                    SliderColors sliderColors3 = sliderColors;
                                    Modifier modifier4 = modifier2;
                                    VolumeDialogSliderTrackKt.TrackIcon(composableLambdaImpl, z, contents, trackMeasurePolicy, sliderColors3, modifier4, (Composer) obj, updateChangedFlags2);
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
            Modifier fillMaxSize = SizeKt.fillMaxSize(LayoutIdKt.layoutId(modifier2, contents), 1.0f);
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, fillMaxSize);
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
            Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            final BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            CompositionLocalKt.CompositionLocalProvider(ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m454boximpl(j)), ComposableLambdaKt.rememberComposableLambda(2029817522, new Function2() { // from class: com.android.systemui.volume.dialog.sliders.ui.compose.VolumeDialogSliderTrackKt$TrackIcon$2$1
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
                        ComposerKt.traceEventStart("com.android.systemui.volume.dialog.sliders.ui.compose.TrackIcon.<anonymous>.<anonymous> (VolumeDialogSliderTrack.kt:166)");
                    }
                    Function4.this.invoke(boxScopeInstance, trackMeasurePolicy, composer2, 0);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 56);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            final int i4 = 1;
            endRestartGroup2.block = new Function2() { // from class: com.android.systemui.volume.dialog.sliders.ui.compose.VolumeDialogSliderTrackKt$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    switch (i4) {
                        case 0:
                            ((Integer) obj2).getClass();
                            int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                            SliderColors sliderColors2 = sliderColors;
                            Modifier modifier3 = modifier2;
                            VolumeDialogSliderTrackKt.TrackIcon(composableLambdaImpl, z, contents, trackMeasurePolicy, sliderColors2, modifier3, (Composer) obj, updateChangedFlags);
                            break;
                        default:
                            ((Integer) obj2).getClass();
                            int updateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                            SliderColors sliderColors3 = sliderColors;
                            Modifier modifier4 = modifier2;
                            VolumeDialogSliderTrackKt.TrackIcon(composableLambdaImpl, z, contents, trackMeasurePolicy, sliderColors3, modifier4, (Composer) obj, updateChangedFlags2);
                            break;
                    }
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
