package com.samsung.sesl.compose.template;

import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.material3.IconButtonColors;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.res.StringResources_androidKt;
import com.android.systemui.R;
import com.samsung.sesl.compose.component.IconButtonKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class SeslTopAppBarTemplate$NavigationScope {
    public static final Companion Companion = new Companion(null);
    public static final SeslTopAppBarTemplate$NavigationScope instance = new SeslTopAppBarTemplate$NavigationScope();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class SeslTopBarNavigationDefaults {
        public static final SeslTopBarNavigationDefaults INSTANCE = new SeslTopBarNavigationDefaults();

        private SeslTopBarNavigationDefaults() {
        }
    }

    private SeslTopAppBarTemplate$NavigationScope() {
    }

    public final void NavigationUp(Function0 function0, MutableInteractionSource mutableInteractionSource, String str, ComposableLambdaImpl composableLambdaImpl, Composer composer, int i) {
        int i2;
        Function0 function02;
        ComposableLambdaImpl composableLambdaImpl2;
        MutableInteractionSource mutableInteractionSource2;
        String str2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-461292708);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(function0) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        int i3 = i2 | 48;
        if ((i & 384) == 0) {
            i3 = i2 | 176;
        }
        if ((i & 3072) == 0) {
            i3 |= composerImpl.changedInstance(composableLambdaImpl) ? 2048 : 1024;
        }
        if ((i3 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            composableLambdaImpl2 = composableLambdaImpl;
            str2 = str;
            mutableInteractionSource2 = mutableInteractionSource;
            function02 = function0;
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                composerImpl.startReplaceGroup(-1706467278);
                Object objRememberedValue = composerImpl.rememberedValue();
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = InteractionSourceKt.MutableInteractionSource();
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                mutableInteractionSource = (MutableInteractionSource) objRememberedValue;
                composerImpl.end(false);
                SeslTopBarNavigationDefaults.INSTANCE.getClass();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.samsung.sesl.compose.template.SeslTopAppBarTemplate.NavigationScope.SeslTopBarNavigationDefaults.action_bar_up_description (AppBarTemplate.kt:271)");
                }
                str = StringResources_androidKt.stringResource(R.string.sesl_action_bar_up_description, composerImpl);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            } else {
                composerImpl.skipToGroupEnd();
            }
            int i4 = i3 & (-897);
            MutableInteractionSource mutableInteractionSource3 = mutableInteractionSource;
            String str3 = str;
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.template.SeslTopAppBarTemplate.NavigationScope.NavigationUp (AppBarTemplate.kt:255)");
            }
            int i5 = i4 << 9;
            IconButtonKt.SeslIconButton(function0, (Modifier.Companion) null, false, (IconButtonColors) null, mutableInteractionSource3, str3, composableLambdaImpl, (Composer) composerImpl, (i4 & 14) | (57344 & i5) | (i5 & 3670016));
            function02 = function0;
            composableLambdaImpl2 = composableLambdaImpl;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            mutableInteractionSource2 = mutableInteractionSource3;
            str2 = str3;
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new SeslTopAppBarTemplate$ActionScope$$ExternalSyntheticLambda0(this, function02, mutableInteractionSource2, str2, composableLambdaImpl2, i);
        }
    }
}
