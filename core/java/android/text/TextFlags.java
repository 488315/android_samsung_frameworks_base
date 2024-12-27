package android.text;

import android.app.AppGlobals;

import com.android.text.flags.Flags;

/* loaded from: classes4.dex */
public final class TextFlags {
    public static final String ENABLE_NEW_CONTEXT_MENU = "TextEditing__enable_new_context_menu";
    public static final boolean ENABLE_NEW_CONTEXT_MENU_DEFAULT = false;
    public static final String KEY_ENABLE_NEW_CONTEXT_MENU = "text__enable_new_context_menu";
    public static final String NAMESPACE = "text";
    public static final String[] TEXT_ACONFIGS_FLAGS = {
        Flags.FLAG_NO_BREAK_NO_HYPHENATION_SPAN,
        Flags.FLAG_PHRASE_STRICT_FALLBACK,
        Flags.FLAG_USE_BOUNDS_FOR_WIDTH,
        Flags.FLAG_FIX_LINE_HEIGHT_FOR_LOCALE,
        Flags.FLAG_ICU_BIDI_MIGRATION,
        Flags.FLAG_FIX_MISALIGNED_CONTEXT_MENU
    };
    public static final boolean[] TEXT_ACONFIG_DEFAULT_VALUE = {
        Flags.noBreakNoHyphenationSpan(),
        Flags.phraseStrictFallback(),
        Flags.useBoundsForWidth(),
        Flags.fixLineHeightForLocale(),
        Flags.icuBidiMigration(),
        Flags.fixMisalignedContextMenu()
    };

    public static String getKeyForFlag(String flag) {
        return "text__" + flag;
    }

    public static boolean isFeatureEnabled(String flag) {
        return AppGlobals.getIntCoreSetting(getKeyForFlag(flag), 0) != 0;
    }
}
