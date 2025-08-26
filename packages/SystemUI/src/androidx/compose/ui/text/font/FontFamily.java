package androidx.compose.ui.text.font;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public abstract class FontFamily {
    public static final Companion Companion = new Companion(null);
    public static final DefaultFontFamily Default = new DefaultFontFamily();
    public static final GenericFontFamily SansSerif = new GenericFontFamily("sans-serif", "FontFamily.SansSerif");
    public static final GenericFontFamily Serif = new GenericFontFamily("serif", "FontFamily.Serif");
    public static final GenericFontFamily Monospace = new GenericFontFamily("monospace", "FontFamily.Monospace");
    public static final GenericFontFamily Cursive = new GenericFontFamily("cursive", "FontFamily.Cursive");

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Resolver {
    }

    public /* synthetic */ FontFamily(boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(z);
    }

    private FontFamily(boolean z) {
    }
}
