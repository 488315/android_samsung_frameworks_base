package androidx.compose.foundation;

import android.content.Context;
import androidx.compose.foundation.layout.PaddingValuesImpl;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class OverscrollKt {
    public static final ComputedProvidableCompositionLocal LocalOverscrollFactory = new ComputedProvidableCompositionLocal(new Function1() { // from class: androidx.compose.foundation.OverscrollKt$LocalOverscrollFactory$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo779invoke(Object obj) {
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
        Modifier modifier2;
        if (overscrollEffect == null || (modifier2 = overscrollEffect.getEffectModifier()) == null) {
            modifier2 = Modifier.Companion;
        }
        if (modifier2 == Modifier.Companion) {
            modifier2 = new OverscrollModifierElement(overscrollEffect);
        }
        return modifier.then(modifier2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x003d, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.compose.foundation.OverscrollEffect rememberOverscrollEffect(androidx.compose.runtime.Composer r4) {
        /*
            androidx.compose.runtime.ComposerImpl r4 = (androidx.compose.runtime.ComposerImpl) r4
            r0 = 282942128(0x10dd5ab0, float:8.7308856E-29)
            r4.startReplaceGroup(r0)
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L13
            java.lang.String r0 = "androidx.compose.foundation.rememberOverscrollEffect (Overscroll.kt:344)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        L13:
            androidx.compose.runtime.ComputedProvidableCompositionLocal r0 = androidx.compose.foundation.OverscrollKt.LocalOverscrollFactory
            java.lang.Object r0 = r4.consume(r0)
            androidx.compose.foundation.OverscrollFactory r0 = (androidx.compose.foundation.OverscrollFactory) r0
            r1 = 0
            if (r0 != 0) goto L2c
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L27
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L27:
            r4.end(r1)
            r4 = 0
            return r4
        L2c:
            boolean r2 = r4.changed(r0)
            java.lang.Object r3 = r4.rememberedValue()
            if (r2 != 0) goto L3f
            androidx.compose.runtime.Composer$Companion r2 = androidx.compose.runtime.Composer.Companion
            r2.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r2 = androidx.compose.runtime.Composer.Companion.Empty
            if (r3 != r2) goto L46
        L3f:
            androidx.compose.foundation.OverscrollEffect r3 = r0.createOverscrollEffect()
            r4.updateRememberedValue(r3)
        L46:
            androidx.compose.foundation.OverscrollEffect r3 = (androidx.compose.foundation.OverscrollEffect) r3
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L51
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L51:
            r4.end(r1)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.OverscrollKt.rememberOverscrollEffect(androidx.compose.runtime.Composer):androidx.compose.foundation.OverscrollEffect");
    }
}
