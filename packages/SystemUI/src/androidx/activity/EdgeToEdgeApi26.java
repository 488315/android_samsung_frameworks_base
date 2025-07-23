package androidx.activity;

import android.view.View;
import android.view.Window;
import androidx.core.view.WindowInsetsControllerCompat;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class EdgeToEdgeApi26 extends EdgeToEdgeBase {
    public void setUp(SystemBarStyle systemBarStyle, SystemBarStyle systemBarStyle2, Window window, View view, boolean z, boolean z2) {
        window.setDecorFitsSystemWindows(false);
        window.setStatusBarColor(z ? systemBarStyle.darkScrim : systemBarStyle.lightScrim);
        window.setNavigationBarColor(z2 ? systemBarStyle2.darkScrim : systemBarStyle2.lightScrim);
        WindowInsetsControllerCompat windowInsetsControllerCompat = new WindowInsetsControllerCompat(window, view);
        windowInsetsControllerCompat.setAppearanceLightStatusBars(!z);
        windowInsetsControllerCompat.setAppearanceLightNavigationBars(!z2);
    }
}
