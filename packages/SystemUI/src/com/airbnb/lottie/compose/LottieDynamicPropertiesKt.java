package com.airbnb.lottie.compose;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import java.util.Arrays;
import kotlin.collections.ArraysKt___ArraysKt;

/* loaded from: classes.dex */
public abstract class LottieDynamicPropertiesKt {
    /* JADX WARN: Removed duplicated region for block: B:6:0x0029  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final LottieDynamicProperties rememberLottieDynamicProperties(LottieDynamicProperty[] lottieDynamicPropertyArr, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceableGroup(34467846);
        Integer numValueOf = Integer.valueOf(Arrays.hashCode(lottieDynamicPropertyArr));
        composerImpl.startReplaceableGroup(-3686930);
        boolean zChanged = composerImpl.changed(numValueOf);
        Object objRememberedValue = composerImpl.rememberedValue();
        if (!zChanged) {
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new LottieDynamicProperties(ArraysKt___ArraysKt.toList(lottieDynamicPropertyArr));
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        composerImpl.end(false);
        LottieDynamicProperties lottieDynamicProperties = (LottieDynamicProperties) objRememberedValue;
        composerImpl.end(false);
        return lottieDynamicProperties;
    }
}
