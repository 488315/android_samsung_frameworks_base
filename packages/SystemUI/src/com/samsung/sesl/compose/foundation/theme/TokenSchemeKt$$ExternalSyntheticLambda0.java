package com.samsung.sesl.compose.foundation.theme;

import androidx.compose.runtime.CompositionLocalAccessorScope;
import androidx.compose.runtime.CompositionLocalMapKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.ui.platform.InspectionModeKt;
import com.samsung.sesl.compose.ui.platform.CompositionLocalsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final /* synthetic */ class TokenSchemeKt$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        StaticProvidableCompositionLocal staticProvidableCompositionLocal = InspectionModeKt.LocalInspectionMode;
        PersistentCompositionLocalMap persistentCompositionLocalMap = (PersistentCompositionLocalMap) ((CompositionLocalAccessorScope) obj);
        persistentCompositionLocalMap.getClass();
        if (((Boolean) CompositionLocalMapKt.read(persistentCompositionLocalMap, staticProvidableCompositionLocal)).booleanValue()) {
            return ((Boolean) CompositionLocalMapKt.read(persistentCompositionLocalMap, BasicThemeKt.LocalSeslInDarkTheme)).booleanValue() ? TokenSchemeKt.SeslDarkTokenScheme : TokenSchemeKt.SeslLightTokenScheme;
        }
        StaticProvidableCompositionLocal staticProvidableCompositionLocal2 = CompositionLocalsKt.LocalSeslSoundEffect;
        throw new IllegalStateException("CompositionLocal SeslTokenScheme, it must be wrapped with SeslTheme not present".toString());
    }
}
