package androidx.compose.foundation.gestures;

import android.content.Context;
import androidx.compose.foundation.gestures.BringIntoViewSpec;
import androidx.compose.runtime.CompositionLocalAccessorScope;
import androidx.compose.runtime.CompositionLocalMapKt;
import androidx.compose.runtime.ComputedProvidableCompositionLocal;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public abstract class BringIntoViewSpec_androidKt {
    public static final ComputedProvidableCompositionLocal LocalBringIntoViewSpec = new ComputedProvidableCompositionLocal(new Function1() { // from class: androidx.compose.foundation.gestures.BringIntoViewSpec_androidKt$LocalBringIntoViewSpec$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            StaticProvidableCompositionLocal staticProvidableCompositionLocal = AndroidCompositionLocals_androidKt.LocalContext;
            PersistentCompositionLocalMap persistentCompositionLocalMap = (PersistentCompositionLocalMap) ((CompositionLocalAccessorScope) obj);
            persistentCompositionLocalMap.getClass();
            if (((Context) CompositionLocalMapKt.read(persistentCompositionLocalMap, staticProvidableCompositionLocal)).getPackageManager().hasSystemFeature("android.software.leanback")) {
                return BringIntoViewSpec_androidKt.PivotBringIntoViewSpec;
            }
            BringIntoViewSpec.Companion.getClass();
            return BringIntoViewSpec.Companion.DefaultBringIntoViewSpec;
        }
    });
    public static final BringIntoViewSpec_androidKt$PivotBringIntoViewSpec$1 PivotBringIntoViewSpec = new BringIntoViewSpec() { // from class: androidx.compose.foundation.gestures.BringIntoViewSpec_androidKt$PivotBringIntoViewSpec$1
        @Override // androidx.compose.foundation.gestures.BringIntoViewSpec
        public final float calculateScrollDistance(float f, float f2, float f3) {
            float fAbs = Math.abs((f2 + f) - f);
            float f4 = (0.3f * f3) - (0.0f * fAbs);
            float f5 = f3 - f4;
            if ((fAbs <= f3) && f5 < fAbs) {
                f4 = f3 - fAbs;
            }
            return f - f4;
        }
    };
}
