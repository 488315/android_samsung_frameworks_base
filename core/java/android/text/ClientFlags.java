package android.text;

import com.android.text.flags.Flags;

/* loaded from: classes4.dex */
public class ClientFlags {
    public static boolean noBreakNoHyphenationSpan() {
        return TextFlags.isFeatureEnabled(Flags.FLAG_NO_BREAK_NO_HYPHENATION_SPAN);
    }

    public static boolean phraseStrictFallback() {
        return TextFlags.isFeatureEnabled(Flags.FLAG_PHRASE_STRICT_FALLBACK);
    }

    public static boolean useBoundsForWidth() {
        return TextFlags.isFeatureEnabled(Flags.FLAG_USE_BOUNDS_FOR_WIDTH);
    }

    public static boolean fixLineHeightForLocale() {
        return TextFlags.isFeatureEnabled(Flags.FLAG_FIX_LINE_HEIGHT_FOR_LOCALE);
    }

    public static boolean icuBidiMigration() {
        return TextFlags.isFeatureEnabled(Flags.FLAG_ICU_BIDI_MIGRATION);
    }

    public static boolean fixMisalignedContextMenu() {
        return TextFlags.isFeatureEnabled(Flags.FLAG_FIX_MISALIGNED_CONTEXT_MENU);
    }
}
