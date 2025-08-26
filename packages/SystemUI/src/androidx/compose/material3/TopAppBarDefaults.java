package androidx.compose.material3;

import androidx.compose.foundation.layout.WindowInsets;
import androidx.compose.foundation.layout.WindowInsetsKt;
import androidx.compose.foundation.layout.WindowInsetsSides;
import androidx.compose.material3.internal.SystemBarsDefaultInsets_androidKt;
import androidx.compose.material3.tokens.AppBarLargeFlexibleTokens;
import androidx.compose.material3.tokens.AppBarLargeTokens;
import androidx.compose.material3.tokens.AppBarMediumFlexibleTokens;
import androidx.compose.material3.tokens.AppBarMediumTokens;
import androidx.compose.material3.tokens.AppBarSmallTokens;
import androidx.compose.material3.tokens.AppBarTokens;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public final class TopAppBarDefaults {
    public static final TopAppBarDefaults INSTANCE = new TopAppBarDefaults();
    public static final float TopAppBarExpandedHeight;

    static {
        AppBarSmallTokens appBarSmallTokens = AppBarSmallTokens.INSTANCE;
        appBarSmallTokens.getClass();
        TopAppBarExpandedHeight = AppBarSmallTokens.ContainerHeight;
        appBarSmallTokens.getClass();
        AppBarMediumTokens.INSTANCE.getClass();
        AppBarMediumFlexibleTokens appBarMediumFlexibleTokens = AppBarMediumFlexibleTokens.INSTANCE;
        appBarMediumFlexibleTokens.getClass();
        appBarMediumFlexibleTokens.getClass();
        appBarSmallTokens.getClass();
        AppBarLargeTokens.INSTANCE.getClass();
        AppBarLargeFlexibleTokens appBarLargeFlexibleTokens = AppBarLargeFlexibleTokens.INSTANCE;
        appBarLargeFlexibleTokens.getClass();
        appBarLargeFlexibleTokens.getClass();
    }

    private TopAppBarDefaults() {
    }

    public static TopAppBarColors getDefaultTopAppBarColors$material3_release(ColorScheme colorScheme) {
        TopAppBarColors topAppBarColors = colorScheme.defaultTopAppBarColorsCached;
        if (topAppBarColors != null) {
            return topAppBarColors;
        }
        AppBarTokens.INSTANCE.getClass();
        TopAppBarColors topAppBarColors2 = new TopAppBarColors(ColorSchemeKt.fromToken(colorScheme, AppBarTokens.ContainerColor), ColorSchemeKt.fromToken(colorScheme, AppBarTokens.OnScrollContainerColor), ColorSchemeKt.fromToken(colorScheme, AppBarTokens.LeadingIconColor), ColorSchemeKt.fromToken(colorScheme, AppBarTokens.TitleColor), ColorSchemeKt.fromToken(colorScheme, AppBarTokens.TrailingIconColor), ColorSchemeKt.fromToken(colorScheme, AppBarTokens.SubtitleColor), null);
        colorScheme.defaultTopAppBarColorsCached = topAppBarColors2;
        return topAppBarColors2;
    }

    public static WindowInsets getWindowInsets(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.TopAppBarDefaults.<get-windowInsets> (AppBar.kt:1589)");
        }
        WindowInsets.Companion companion = WindowInsets.Companion;
        WindowInsets systemBarsForVisualComponents = SystemBarsDefaultInsets_androidKt.getSystemBarsForVisualComponents(composer);
        WindowInsetsSides.Companion.getClass();
        WindowInsets windowInsetsM149onlybOOhFvg = WindowInsetsKt.m149onlybOOhFvg(systemBarsForVisualComponents, WindowInsetsSides.Horizontal | WindowInsetsSides.Top);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return windowInsetsM149onlybOOhFvg;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x002d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static TopAppBarScrollBehavior pinnedScrollBehavior(TopAppBarState topAppBarState, Composer composer) {
        AnonymousClass1 anonymousClass1 = new Function0() { // from class: androidx.compose.material3.TopAppBarDefaults.pinnedScrollBehavior.1
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Boolean.TRUE;
            }
        };
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.TopAppBarDefaults.pinnedScrollBehavior (AppBar.kt:1758)");
        }
        boolean zChanged = ((ComposerImpl) composer).changed(topAppBarState) | ((ComposerImpl) composer).changed(anonymousClass1);
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Object objRememberedValue = composerImpl.rememberedValue();
        if (!zChanged) {
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new PinnedScrollBehavior(topAppBarState, anonymousClass1);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        PinnedScrollBehavior pinnedScrollBehavior = (PinnedScrollBehavior) objRememberedValue;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return pinnedScrollBehavior;
    }

    public static TopAppBarColors topAppBarColors(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.TopAppBarDefaults.topAppBarColors (AppBar.kt:1507)");
        }
        MaterialTheme.INSTANCE.getClass();
        TopAppBarColors defaultTopAppBarColors$material3_release = getDefaultTopAppBarColors$material3_release(MaterialTheme.getColorScheme(composer));
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return defaultTopAppBarColors$material3_release;
    }
}
