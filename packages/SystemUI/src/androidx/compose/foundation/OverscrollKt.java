package androidx.compose.foundation;

import android.content.Context;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalAccessorScope;
import androidx.compose.runtime.CompositionLocalMapKt;
import androidx.compose.runtime.ComputedProvidableCompositionLocal;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Density;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public abstract class OverscrollKt {
    public static final ComputedProvidableCompositionLocal LocalOverscrollFactory = new ComputedProvidableCompositionLocal(new Function1() { // from class: androidx.compose.foundation.OverscrollKt$LocalOverscrollFactory$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            PaddingValuesImpl paddingValuesImpl = AndroidOverscroll_androidKt.DefaultGlowPaddingValues;
            StaticProvidableCompositionLocal staticProvidableCompositionLocal = AndroidCompositionLocals_androidKt.LocalContext;
            PersistentCompositionLocalMap persistentCompositionLocalMap = (PersistentCompositionLocalMap) ((CompositionLocalAccessorScope) obj);
            persistentCompositionLocalMap.getClass();
            Context context = (Context) CompositionLocalMapKt.read(persistentCompositionLocalMap, staticProvidableCompositionLocal);
            Density density = (Density) CompositionLocalMapKt.read(persistentCompositionLocalMap, CompositionLocalsKt.LocalDensity);
            OverscrollConfiguration overscrollConfiguration = (OverscrollConfiguration) CompositionLocalMapKt.read(persistentCompositionLocalMap, OverscrollConfiguration_androidKt.LocalOverscrollConfiguration);
            if (overscrollConfiguration == null) {
                return null;
            }
            return new AndroidEdgeEffectOverscrollFactory(context, density, overscrollConfiguration.glowColor, overscrollConfiguration.drawPadding, null);
        }
    });

    public static final Modifier overscroll(Modifier modifier, OverscrollEffect overscrollEffect) {
        Modifier overscrollModifierElement;
        if (overscrollEffect == null || (overscrollModifierElement = overscrollEffect.getEffectModifier()) == null) {
            overscrollModifierElement = Modifier.Companion;
        }
        if (overscrollModifierElement == Modifier.Companion) {
            overscrollModifierElement = new OverscrollModifierElement(overscrollEffect);
        }
        return modifier.then(overscrollModifierElement);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x003f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final OverscrollEffect rememberOverscrollEffect(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(282942128);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.foundation.rememberOverscrollEffect (Overscroll.kt:344)");
        }
        OverscrollFactory overscrollFactory = (OverscrollFactory) composerImpl.consume(LocalOverscrollFactory);
        if (overscrollFactory == null) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
            return null;
        }
        boolean zChanged = composerImpl.changed(overscrollFactory);
        Object objRememberedValue = composerImpl.rememberedValue();
        if (!zChanged) {
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = overscrollFactory.createOverscrollEffect();
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        OverscrollEffect overscrollEffect = (OverscrollEffect) objRememberedValue;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return overscrollEffect;
    }
}
