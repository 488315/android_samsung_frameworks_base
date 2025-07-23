package com.airbnb.lottie.compose;

import android.content.Context;
import android.provider.Settings;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.utils.Utils;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AnimateLottieCompositionAsStateKt {
    public static final LottieAnimatable animateLottieCompositionAsState(LottieComposition lottieComposition, boolean z, int i, Composer composer, int i2) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceableGroup(-180607952);
        if ((i2 & 2) != 0) {
            z = true;
        }
        boolean z2 = (i2 & 4) != 0;
        LottieCancellationBehavior lottieCancellationBehavior = LottieCancellationBehavior.Immediately;
        if (i <= 0) {
            throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "Iterations must be a positive number (", ").").toString());
        }
        if (Float.isInfinite(1.0f) || Float.isNaN(1.0f)) {
            throw new IllegalArgumentException(("Speed must be a finite number. It is 1.0.").toString());
        }
        composerImpl.startReplaceableGroup(-610207901);
        composerImpl.startReplaceableGroup(-3687241);
        Object rememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (rememberedValue == composer$Companion$Empty$1) {
            rememberedValue = new LottieAnimatableImpl();
            composerImpl.updateRememberedValue(rememberedValue);
        }
        composerImpl.end(false);
        LottieAnimatable lottieAnimatable = (LottieAnimatable) rememberedValue;
        composerImpl.end(false);
        composerImpl.startReplaceableGroup(-3687241);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (rememberedValue2 == composer$Companion$Empty$1) {
            rememberedValue2 = SnapshotStateKt.mutableStateOf$default(Boolean.valueOf(z));
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        composerImpl.end(false);
        MutableState mutableState = (MutableState) rememberedValue2;
        composerImpl.startReplaceableGroup(-180607189);
        Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        Utils.AnonymousClass1 anonymousClass1 = Utils.threadLocalPathMeasure;
        float f = 1.0f / Settings.Global.getFloat(context.getContentResolver(), SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE, 1.0f);
        composerImpl.end(false);
        EffectsKt.LaunchedEffect(new Object[]{lottieComposition, Boolean.valueOf(z), null, Float.valueOf(f), Integer.valueOf(i)}, new AnimateLottieCompositionAsStateKt$animateLottieCompositionAsState$3(z, z2, lottieAnimatable, lottieComposition, i, f, null, lottieCancellationBehavior, mutableState, null), composerImpl);
        composerImpl.end(false);
        return lottieAnimatable;
    }
}
