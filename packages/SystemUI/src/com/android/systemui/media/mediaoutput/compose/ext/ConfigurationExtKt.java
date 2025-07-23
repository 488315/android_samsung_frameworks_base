package com.android.systemui.media.mediaoutput.compose.ext;

import android.content.res.Configuration;
import android.view.View;
import android.view.WindowInsets;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class ConfigurationExtKt {
    public static final MutableState CutoutInsets(Composer composer, Function1 function1) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-721397296);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ext.CutoutInsets (ConfigurationExt.kt:35)");
        }
        WindowInsets rootWindowInsets = ((View) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalView)).getRootView().getRootWindowInsets();
        composerImpl.startReplaceGroup(925847195);
        Object rememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (rememberedValue == composer$Companion$Empty$1) {
            rememberedValue = SnapshotStateKt.mutableStateOf$default(0);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        MutableState mutableState = (MutableState) rememberedValue;
        composerImpl.end(false);
        composerImpl.startReplaceGroup(925849556);
        boolean changedInstance = composerImpl.changedInstance(rootWindowInsets);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (changedInstance || rememberedValue2 == composer$Companion$Empty$1) {
            rememberedValue2 = new ConfigurationExtKt$CutoutInsets$1$1(rootWindowInsets, mutableState, function1, null);
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        composerImpl.end(false);
        EffectsKt.LaunchedEffect(composerImpl, rootWindowInsets, (Function2) rememberedValue2);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return mutableState;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x005a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final boolean isFold(androidx.compose.runtime.Composer r3) {
        /*
            androidx.compose.runtime.ComposerImpl r3 = (androidx.compose.runtime.ComposerImpl) r3
            r0 = -1004834635(0xffffffffc41b70b5, float:-621.76105)
            r3.startReplaceGroup(r0)
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L13
            java.lang.String r0 = "com.android.systemui.media.mediaoutput.compose.ext.isFold (ConfigurationExt.kt:32)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        L13:
            boolean r0 = com.android.systemui.BasicRune.BASIC_FOLDABLE_TYPE_FOLD
            r1 = 0
            if (r0 != 0) goto L1e
            boolean r0 = com.android.systemui.util.DeviceState.isTablet()
            if (r0 == 0) goto L53
        L1e:
            r0 = 386913985(0x170fd6c1, float:4.647685E-25)
            r3.startReplaceGroup(r0)
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L2f
            java.lang.String r0 = "com.android.systemui.media.mediaoutput.compose.ext.smallestScreenWidth (ConfigurationExt.kt:29)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        L2f:
            androidx.compose.runtime.DynamicProvidableCompositionLocal r0 = androidx.compose.ui.platform.AndroidCompositionLocals_androidKt.LocalConfiguration
            java.lang.Object r0 = r3.consume(r0)
            android.content.res.Configuration r0 = (android.content.res.Configuration) r0
            int r0 = r0.smallestScreenWidthDp
            float r0 = (float) r0
            androidx.compose.ui.unit.Dp$Companion r2 = androidx.compose.ui.unit.Dp.Companion
            boolean r2 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r2 == 0) goto L45
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L45:
            r3.end(r1)
            r2 = 411(0x19b, float:5.76E-43)
            float r2 = (float) r2
            int r0 = java.lang.Float.compare(r0, r2)
            if (r0 <= 0) goto L53
            r0 = 1
            goto L54
        L53:
            r0 = r1
        L54:
            boolean r2 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r2 == 0) goto L5d
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L5d:
            r3.end(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.ext.ConfigurationExtKt.isFold(androidx.compose.runtime.Composer):boolean");
    }

    public static final boolean isLandscape(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1466071787);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ext.isLandscape (ConfigurationExt.kt:23)");
        }
        boolean z = ((Configuration) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalConfiguration)).orientation == 2;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return z;
    }

    public static final boolean isPortrait(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-378085797);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ext.isPortrait (ConfigurationExt.kt:20)");
        }
        boolean z = !isLandscape(composerImpl);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return z;
    }
}
