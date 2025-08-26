package com.samsung.sesl.compose.foundation.theme;

import androidx.compose.runtime.CompositionLocalAccessorScope;
import androidx.compose.runtime.CompositionLocalMapKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.ui.platform.InspectionModeKt;
import com.samsung.sesl.compose.ui.platform.CompositionLocalsKt;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public final /* synthetic */ class TokenSchemeKt$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        StaticProvidableCompositionLocal staticProvidableCompositionLocal = InspectionModeKt.LocalInspectionMode;
        PersistentCompositionLocalMap persistentCompositionLocalMap = (PersistentCompositionLocalMap) ((CompositionLocalAccessorScope) obj);
        persistentCompositionLocalMap.getClass();
        if (!((Boolean) CompositionLocalMapKt.read(persistentCompositionLocalMap, staticProvidableCompositionLocal)).booleanValue()) {
            StaticProvidableCompositionLocal staticProvidableCompositionLocal2 = CompositionLocalsKt.LocalSeslSoundEffect;
            throw new IllegalStateException("CompositionLocal SeslTokenScheme, it must be wrapped with SeslTheme not present".toString());
        }
        SeslTokenSchemeImpl seslTokenSchemeImpl = TokenSchemeKt.SeslDarkTokenScheme;
        if (!((Boolean) CompositionLocalMapKt.read(persistentCompositionLocalMap, BasicThemeKt.LocalSeslInDarkTheme)).booleanValue()) {
            seslTokenSchemeImpl = null;
        }
        return seslTokenSchemeImpl == null ? TokenSchemeKt.SeslLightTokenScheme : seslTokenSchemeImpl;
    }
}
