package androidx.compose.material3;

import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.material3.tokens.NavigationDrawerTokens;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.unit.Dp;

/* loaded from: classes.dex */
public final class NavigationDrawerItemDefaults {
    public static final NavigationDrawerItemDefaults INSTANCE = new NavigationDrawerItemDefaults();

    static {
        Dp.Companion companion = Dp.Companion;
        PaddingKt.m122PaddingValuesYgX7TsA$default(12, 2);
    }

    private NavigationDrawerItemDefaults() {
    }

    /* renamed from: colors-oq7We08, reason: not valid java name */
    public static NavigationDrawerItemColors m275colorsoq7We08(long j, Composer composer) {
        NavigationDrawerTokens navigationDrawerTokens = NavigationDrawerTokens.INSTANCE;
        navigationDrawerTokens.getClass();
        long value = ColorSchemeKt.getValue(NavigationDrawerTokens.ActiveIndicatorColor, composer);
        navigationDrawerTokens.getClass();
        long value2 = ColorSchemeKt.getValue(NavigationDrawerTokens.ActiveIconColor, composer);
        navigationDrawerTokens.getClass();
        long value3 = ColorSchemeKt.getValue(NavigationDrawerTokens.InactiveIconColor, composer);
        navigationDrawerTokens.getClass();
        long value4 = ColorSchemeKt.getValue(NavigationDrawerTokens.ActiveLabelTextColor, composer);
        navigationDrawerTokens.getClass();
        long value5 = ColorSchemeKt.getValue(NavigationDrawerTokens.InactiveLabelTextColor, composer);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.NavigationDrawerItemDefaults.colors (NavigationDrawer.kt:1182)");
        }
        DefaultDrawerItemsColor defaultDrawerItemsColor = new DefaultDrawerItemsColor(value2, value3, value4, value5, value, j, value4, value5, null);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return defaultDrawerItemsColor;
    }
}
