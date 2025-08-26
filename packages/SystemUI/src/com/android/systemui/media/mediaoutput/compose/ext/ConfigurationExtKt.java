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
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpKt;
import androidx.compose.ui.unit.DpSize;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.util.DeviceState;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public abstract class ConfigurationExtKt {
    public static final MutableState CutoutInsets(Composer composer, Function1 function1) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-721397296);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ext.CutoutInsets (ConfigurationExt.kt:46)");
        }
        WindowInsets rootWindowInsets = ((View) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalView)).getRootView().getRootWindowInsets();
        composerImpl.startReplaceGroup(925863323);
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (objRememberedValue == composer$Companion$Empty$1) {
            objRememberedValue = SnapshotStateKt.mutableStateOf$default(0);
            composerImpl.updateRememberedValue(objRememberedValue);
        }
        MutableState mutableState = (MutableState) objRememberedValue;
        composerImpl.end(false);
        composerImpl.startReplaceGroup(925865684);
        boolean zChangedInstance = composerImpl.changedInstance(rootWindowInsets);
        Object objRememberedValue2 = composerImpl.rememberedValue();
        if (zChangedInstance || objRememberedValue2 == composer$Companion$Empty$1) {
            objRememberedValue2 = new ConfigurationExtKt$CutoutInsets$1$1(rootWindowInsets, mutableState, function1, null);
            composerImpl.updateRememberedValue(objRememberedValue2);
        }
        composerImpl.end(false);
        EffectsKt.LaunchedEffect(composerImpl, rootWindowInsets, (Function2) objRememberedValue2);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return mutableState;
    }

    public static final boolean isFolderOpened(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1536839423);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ext.isFolderOpened (ConfigurationExt.kt:43)");
        }
        boolean z = BasicRune.BASIC_FOLDABLE_TYPE_FOLD && ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return z;
    }

    public static final boolean isLandscape(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1466071787);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ext.isLandscape (ConfigurationExt.kt:25)");
        }
        boolean z = ((Configuration) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalConfiguration)).orientation == 2;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return z;
    }

    public static final boolean isPopupMode(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-67815497);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ext.isPopupMode (ConfigurationExt.kt:37)");
        }
        Pair pair = new Pair(composerImpl.consume(CompositionExtKt.LocalRootSize), DpSize.m844boximpl(screenSizeDp(composerImpl)));
        long j = ((DpSize) pair.component1()).packedValue;
        long j2 = ((DpSize) pair.component2()).packedValue;
        boolean z = DpSize.m847getWidthD9Ej5fM(j) / DpSize.m847getWidthD9Ej5fM(j2) < 0.8f || DpSize.m846getHeightD9Ej5fM(j) / DpSize.m846getHeightD9Ej5fM(j2) < 0.8f;
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
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ext.isPortrait (ConfigurationExt.kt:22)");
        }
        boolean z = !isLandscape(composerImpl);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return z;
    }

    public static final boolean isTablet(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1212409840);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ext.isTablet (ConfigurationExt.kt:40)");
        }
        boolean zIsTablet = DeviceState.isTablet();
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return zIsTablet;
    }

    public static final long screenSizeDp(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-575072100);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ext.screenSizeDp (ConfigurationExt.kt:28)");
        }
        float f = ((Configuration) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalConfiguration)).screenWidthDp;
        Dp.Companion companion = Dp.Companion;
        long jM840DpSizeYgX7TsA = DpKt.m840DpSizeYgX7TsA(f, r0.screenHeightDp);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return jM840DpSizeYgX7TsA;
    }
}
