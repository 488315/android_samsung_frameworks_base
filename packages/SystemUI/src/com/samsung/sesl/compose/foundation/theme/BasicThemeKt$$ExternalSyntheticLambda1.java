package com.samsung.sesl.compose.foundation.theme;

import android.content.Context;
import androidx.compose.runtime.CompositionLocalAccessorScope;
import androidx.compose.runtime.CompositionLocalMapKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import com.samsung.sesl.compose.utils.ext.ContextExtKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final /* synthetic */ class BasicThemeKt$$ExternalSyntheticLambda1 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        StaticProvidableCompositionLocal staticProvidableCompositionLocal = AndroidCompositionLocals_androidKt.LocalContext;
        PersistentCompositionLocalMap persistentCompositionLocalMap = (PersistentCompositionLocalMap) ((CompositionLocalAccessorScope) obj);
        persistentCompositionLocalMap.getClass();
        return Boolean.valueOf(ContextExtKt.isSystemInDarkTheme((Context) CompositionLocalMapKt.read(persistentCompositionLocalMap, staticProvidableCompositionLocal)));
    }
}
