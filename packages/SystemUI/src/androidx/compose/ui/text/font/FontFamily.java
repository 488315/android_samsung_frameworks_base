package androidx.compose.ui.text.font;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class FontFamily {
    public static final Companion Companion = new Companion(null);
    public static final DefaultFontFamily Default = new DefaultFontFamily();
    public static final GenericFontFamily SansSerif = new GenericFontFamily("sans-serif", "FontFamily.SansSerif");
    public static final GenericFontFamily Serif = new GenericFontFamily("serif", "FontFamily.Serif");
    public static final GenericFontFamily Monospace = new GenericFontFamily("monospace", "FontFamily.Monospace");
    public static final GenericFontFamily Cursive = new GenericFontFamily("cursive", "FontFamily.Cursive");

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Resolver {
    }

    public /* synthetic */ FontFamily(boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(z);
    }

    private FontFamily(boolean z) {
    }
}
