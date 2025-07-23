package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.ColorKt;
import com.samsung.sesl.compose.component.CheckboxKt;
import com.samsung.sesl.compose.theme.SeslColorScheme;
import com.samsung.sesl.compose.theme.SeslTheme;
import com.samsung.sesl.compose.theme.ThemeKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class ControllersKt {
    public static final void MoCheckbox(final boolean z, final Function1 function1, final Modifier modifier, final boolean z2, MutableInteractionSource mutableInteractionSource, Composer composer, final int i) {
        final MutableInteractionSource mutableInteractionSource2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1618790467);
        if (((i | (composerImpl.changed(z) ? 4 : 2) | (composerImpl.changedInstance(function1) ? 32 : 16) | (composerImpl.changed(z2) ? 2048 : 1024) | 24576) & 9363) == 9362 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            mutableInteractionSource2 = mutableInteractionSource;
        } else {
            composerImpl.startReplaceGroup(1738266152);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = InteractionSourceKt.MutableInteractionSource();
                composerImpl.updateRememberedValue(rememberedValue);
            }
            mutableInteractionSource2 = (MutableInteractionSource) rememberedValue;
            composerImpl.end(false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.MoCheckbox (Controllers.kt:81)");
            }
            SeslTheme.INSTANCE.getClass();
            ThemeKt.SeslTheme(false, SeslColorScheme.m3340copyFD3wquc$default(SeslTheme.getColorScheme(composerImpl), ColorKt.Color(4294769919L), ColorKt.Color(4294769919L)), ComposableLambdaKt.rememberComposableLambda(1403220974, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ControllersKt$MoCheckbox$2
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
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.MoCheckbox.<anonymous> (Controllers.kt:88)");
                    }
                    CheckboxKt.SeslCheckbox(z, function1, modifier, z2, mutableInteractionSource2, composer2, 0);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 384, 1);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(z, function1, modifier, z2, mutableInteractionSource2, i) { // from class: com.android.systemui.media.mediaoutput.compose.widget.ControllersKt$$ExternalSyntheticLambda0
                public final /* synthetic */ boolean f$0;
                public final /* synthetic */ Function1 f$1;
                public final /* synthetic */ Modifier f$2;
                public final /* synthetic */ boolean f$3;
                public final /* synthetic */ MutableInteractionSource f$4;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(385);
                    boolean z3 = this.f$3;
                    MutableInteractionSource mutableInteractionSource3 = this.f$4;
                    ControllersKt.MoCheckbox(this.f$0, this.f$1, this.f$2, z3, mutableInteractionSource3, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:73:0x0292, code lost:
    
        if (r13.changed(r9) == false) goto L96;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void MoSlider(final float r45, final kotlin.jvm.functions.Function1 r46, final androidx.compose.ui.Modifier r47, boolean r48, final kotlin.jvm.functions.Function0 r49, androidx.compose.material3.SliderColors r50, androidx.compose.foundation.interaction.MutableInteractionSource r51, final kotlin.ranges.ClosedFloatRange r52, final float r53, final java.lang.String r54, androidx.compose.runtime.Composer r55, final int r56) {
        /*
            Method dump skipped, instructions count: 788
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.widget.ControllersKt.MoSlider(float, kotlin.jvm.functions.Function1, androidx.compose.ui.Modifier, boolean, kotlin.jvm.functions.Function0, androidx.compose.material3.SliderColors, androidx.compose.foundation.interaction.MutableInteractionSource, kotlin.ranges.ClosedFloatRange, float, java.lang.String, androidx.compose.runtime.Composer, int):void");
    }
}
