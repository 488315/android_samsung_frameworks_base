package androidx.activity;

import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;
import androidx.activity.SystemBarStyle;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public abstract class EdgeToEdge {
    public static final int DefaultLightScrim = Color.argb(230, 255, 255, 255);
    public static final int DefaultDarkScrim = Color.argb(128, 27, 27, 27);

    public static void enable$default(ComponentActivity componentActivity) {
        SystemBarStyle.Companion companion = SystemBarStyle.Companion;
        SystemBarStyle$Companion$auto$1 systemBarStyle$Companion$auto$1 = new Function1() { // from class: androidx.activity.SystemBarStyle$Companion$auto$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return Boolean.valueOf((((Resources) obj).getConfiguration().uiMode & 48) == 32);
            }
        };
        companion.getClass();
        SystemBarStyle systemBarStyle = new SystemBarStyle(0, 0, 0, systemBarStyle$Companion$auto$1, null);
        companion.getClass();
        SystemBarStyle systemBarStyle2 = new SystemBarStyle(DefaultLightScrim, DefaultDarkScrim, 0, systemBarStyle$Companion$auto$1, null);
        View decorView = componentActivity.getWindow().getDecorView();
        boolean zBooleanValue = ((Boolean) systemBarStyle.detectDarkMode.mo781invoke(decorView.getResources())).booleanValue();
        boolean zBooleanValue2 = ((Boolean) systemBarStyle2.detectDarkMode.mo781invoke(decorView.getResources())).booleanValue();
        EdgeToEdgeApi30 edgeToEdgeApi30 = new EdgeToEdgeApi30();
        edgeToEdgeApi30.setUp(systemBarStyle, systemBarStyle2, componentActivity.getWindow(), decorView, zBooleanValue, zBooleanValue2);
        edgeToEdgeApi30.adjustLayoutInDisplayCutoutMode(componentActivity.getWindow());
    }
}
