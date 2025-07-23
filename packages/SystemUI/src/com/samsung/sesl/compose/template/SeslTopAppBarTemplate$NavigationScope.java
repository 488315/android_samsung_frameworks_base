package com.samsung.sesl.compose.template;

import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import com.samsung.sesl.compose.component.IconButtonKt;
import com.samsung.sesl.compose.template.SeslTopAppBarTemplate$NavigationScope;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslTopAppBarTemplate$NavigationScope {
    public static final Companion Companion = new Companion(null);
    public static final SeslTopAppBarTemplate$NavigationScope instance = new SeslTopAppBarTemplate$NavigationScope();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private SeslTopAppBarTemplate$NavigationScope() {
    }

    public final void NavigationUp(Function0 function0, MutableInteractionSource mutableInteractionSource, ComposableLambdaImpl composableLambdaImpl, Composer composer, final int i) {
        int i2;
        final Function0 function02;
        final ComposableLambdaImpl composableLambdaImpl2;
        final MutableInteractionSource mutableInteractionSource2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1049201781);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(function0) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        int i3 = i2 | 48;
        if ((i & 384) == 0) {
            i3 |= composerImpl.changedInstance(composableLambdaImpl) ? 256 : 128;
        }
        if ((i3 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            composableLambdaImpl2 = composableLambdaImpl;
            mutableInteractionSource2 = mutableInteractionSource;
            function02 = function0;
        } else {
            composerImpl.startReplaceGroup(-1706486862);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = InteractionSourceKt.MutableInteractionSource();
                composerImpl.updateRememberedValue(rememberedValue);
            }
            MutableInteractionSource mutableInteractionSource3 = (MutableInteractionSource) rememberedValue;
            composerImpl.end(false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.template.SeslTopAppBarTemplate.NavigationScope.NavigationUp (AppBarTemplate.kt:235)");
            }
            int i4 = i3 & 14;
            int i5 = i3 << 9;
            IconButtonKt.SeslIconButton(function0, null, false, null, mutableInteractionSource3, composableLambdaImpl, composerImpl, i4 | (57344 & i5) | (i5 & 458752), 14);
            function02 = function0;
            composableLambdaImpl2 = composableLambdaImpl;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            mutableInteractionSource2 = mutableInteractionSource3;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.template.SeslTopAppBarTemplate$NavigationScope$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    ((Integer) obj2).getClass();
                    SeslTopAppBarTemplate$NavigationScope.Companion companion = SeslTopAppBarTemplate$NavigationScope.Companion;
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    SeslTopAppBarTemplate$NavigationScope.this.NavigationUp(function02, mutableInteractionSource2, composableLambdaImpl2, composer2, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
