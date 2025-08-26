package com.android.systemui.inputdevice.tutorial.ui.composable;

import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.graphics.ColorKt;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.compose.LottieDynamicProperty;
import com.airbnb.lottie.model.KeyPath;
import java.util.Arrays;

/* loaded from: classes2.dex */
public abstract class LottieHelpersKt {
    /* JADX WARN: Removed duplicated region for block: B:14:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0045  */
    /* renamed from: rememberColorFilterProperty-RPmYEkk, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final LottieDynamicProperty m2585rememberColorFilterPropertyRPmYEkk(String str, long j, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-886109170);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.inputdevice.tutorial.ui.composable.rememberColorFilterProperty (LottieHelpers.kt:32)");
        }
        ColorFilter colorFilter = LottieProperty.COLOR_FILTER;
        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(ColorKt.m469toArgb8_81llA(j), PorterDuff.Mode.SRC_ATOP);
        String[] strArr = {"**", str, "**"};
        composerImpl.startReplaceableGroup(1613443783);
        composerImpl.startReplaceableGroup(-3686930);
        boolean zChanged = composerImpl.changed(strArr);
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion companion = Composer.Companion;
        if (!zChanged) {
            companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new KeyPath((String[]) Arrays.copyOf(strArr, 3));
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        composerImpl.end(false);
        KeyPath keyPath = (KeyPath) objRememberedValue;
        composerImpl.startReplaceableGroup(-3686095);
        boolean zChanged2 = composerImpl.changed(keyPath) | composerImpl.changed(colorFilter) | composerImpl.changed(porterDuffColorFilter);
        Object objRememberedValue2 = composerImpl.rememberedValue();
        if (!zChanged2) {
            companion.getClass();
            if (objRememberedValue2 == Composer.Companion.Empty) {
                objRememberedValue2 = new LottieDynamicProperty(colorFilter, keyPath, porterDuffColorFilter);
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
        }
        composerImpl.end(false);
        LottieDynamicProperty lottieDynamicProperty = (LottieDynamicProperty) objRememberedValue2;
        composerImpl.end(false);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return lottieDynamicProperty;
    }
}
