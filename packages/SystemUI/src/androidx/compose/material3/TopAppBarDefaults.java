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
import androidx.compose.runtime.ComposerKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        WindowInsets m148onlybOOhFvg = WindowInsetsKt.m148onlybOOhFvg(systemBarsForVisualComponents, WindowInsetsSides.Horizontal | WindowInsetsSides.Top);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return m148onlybOOhFvg;
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x002b, code lost:
    
        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static androidx.compose.material3.TopAppBarScrollBehavior pinnedScrollBehavior(androidx.compose.material3.TopAppBarState r3, androidx.compose.runtime.Composer r4) {
        /*
            androidx.compose.material3.TopAppBarDefaults$pinnedScrollBehavior$1 r0 = new kotlin.jvm.functions.Function0() { // from class: androidx.compose.material3.TopAppBarDefaults$pinnedScrollBehavior$1
                static {
                    /*
                        androidx.compose.material3.TopAppBarDefaults$pinnedScrollBehavior$1 r0 = new androidx.compose.material3.TopAppBarDefaults$pinnedScrollBehavior$1
                        r0.<init>()
                        
                        // error: 0x0005: SPUT (r0 I:androidx.compose.material3.TopAppBarDefaults$pinnedScrollBehavior$1) androidx.compose.material3.TopAppBarDefaults$pinnedScrollBehavior$1.INSTANCE androidx.compose.material3.TopAppBarDefaults$pinnedScrollBehavior$1
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.TopAppBarDefaults$pinnedScrollBehavior$1.<clinit>():void");
                }

                {
                    /*
                        r1 = this;
                        r0 = 0
                        r1.<init>(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.TopAppBarDefaults$pinnedScrollBehavior$1.<init>():void");
                }

                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ java.lang.Object invoke() {
                    /*
                        r0 = this;
                        java.lang.Boolean r0 = java.lang.Boolean.TRUE
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.TopAppBarDefaults$pinnedScrollBehavior$1.invoke():java.lang.Object");
                }
            }
            boolean r1 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r1 == 0) goto Ld
            java.lang.String r1 = "androidx.compose.material3.TopAppBarDefaults.pinnedScrollBehavior (AppBar.kt:1758)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r1)
        Ld:
            r1 = r4
            androidx.compose.runtime.ComposerImpl r1 = (androidx.compose.runtime.ComposerImpl) r1
            boolean r1 = r1.changed(r3)
            r2 = r4
            androidx.compose.runtime.ComposerImpl r2 = (androidx.compose.runtime.ComposerImpl) r2
            boolean r2 = r2.changed(r0)
            r1 = r1 | r2
            androidx.compose.runtime.ComposerImpl r4 = (androidx.compose.runtime.ComposerImpl) r4
            java.lang.Object r2 = r4.rememberedValue()
            if (r1 != 0) goto L2d
            androidx.compose.runtime.Composer$Companion r1 = androidx.compose.runtime.Composer.Companion
            r1.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r1 = androidx.compose.runtime.Composer.Companion.Empty
            if (r2 != r1) goto L35
        L2d:
            androidx.compose.material3.PinnedScrollBehavior r2 = new androidx.compose.material3.PinnedScrollBehavior
            r2.<init>(r3, r0)
            r4.updateRememberedValue(r2)
        L35:
            androidx.compose.material3.PinnedScrollBehavior r2 = (androidx.compose.material3.PinnedScrollBehavior) r2
            boolean r3 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r3 == 0) goto L40
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L40:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.TopAppBarDefaults.pinnedScrollBehavior(androidx.compose.material3.TopAppBarState, androidx.compose.runtime.Composer):androidx.compose.material3.TopAppBarScrollBehavior");
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
