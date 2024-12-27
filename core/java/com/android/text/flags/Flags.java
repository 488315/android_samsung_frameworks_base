package com.android.text.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_COMPLETE_FONT_LOAD_IN_SYSTEM_SERVICES_READY = "com.android.text.flags.complete_font_load_in_system_services_ready";
    public static final String FLAG_DEPRECATE_UI_FONTS = "com.android.text.flags.deprecate_ui_fonts";
    public static final String FLAG_DISABLE_HANDWRITING_INITIATOR_FOR_IME = "com.android.text.flags.disable_handwriting_initiator_for_ime";
    public static final String FLAG_ESCAPE_CLEARS_FOCUS = "com.android.text.flags.escape_clears_focus";
    public static final String FLAG_FIX_DOUBLE_UNDERLINE = "com.android.text.flags.fix_double_underline";
    public static final String FLAG_FIX_FONT_UPDATE_FAILURE = "com.android.text.flags.fix_font_update_failure";
    public static final String FLAG_FIX_LINE_HEIGHT_FOR_LOCALE = "com.android.text.flags.fix_line_height_for_locale";
    public static final String FLAG_FIX_MISALIGNED_CONTEXT_MENU = "com.android.text.flags.fix_misaligned_context_menu";
    public static final String FLAG_FIX_NULL_TYPEFACE_BOLDING = "com.android.text.flags.fix_null_typeface_bolding";
    public static final String FLAG_HANDWRITING_CURSOR_POSITION = "com.android.text.flags.handwriting_cursor_position";
    public static final String FLAG_HANDWRITING_END_OF_LINE_TAP = "com.android.text.flags.handwriting_end_of_line_tap";
    public static final String FLAG_HANDWRITING_UNSUPPORTED_MESSAGE = "com.android.text.flags.handwriting_unsupported_message";
    public static final String FLAG_ICU_BIDI_MIGRATION = "com.android.text.flags.icu_bidi_migration";
    public static final String FLAG_INSERT_MODE_CRASH_WHEN_DELETE = "com.android.text.flags.insert_mode_crash_when_delete";
    public static final String FLAG_INSERT_MODE_NOT_UPDATE_SELECTION = "com.android.text.flags.insert_mode_not_update_selection";
    public static final String FLAG_LAZY_VARIATION_INSTANCE = "com.android.text.flags.lazy_variation_instance";
    public static final String FLAG_LETTER_SPACING_JUSTIFICATION = "com.android.text.flags.letter_spacing_justification";
    public static final String FLAG_MISSING_GETTER_APIS = "com.android.text.flags.missing_getter_apis";
    public static final String FLAG_NEW_FONTS_FALLBACK_XML = "com.android.text.flags.new_fonts_fallback_xml";
    public static final String FLAG_NO_BREAK_NO_HYPHENATION_SPAN = "com.android.text.flags.no_break_no_hyphenation_span";
    public static final String FLAG_PHRASE_STRICT_FALLBACK = "com.android.text.flags.phrase_strict_fallback";
    public static final String FLAG_RUST_HYPHENATOR = "com.android.text.flags.rust_hyphenator";
    public static final String FLAG_USE_BOUNDS_FOR_WIDTH = "com.android.text.flags.use_bounds_for_width";
    public static final String FLAG_USE_OPTIMIZED_BOOTTIME_FONT_LOADING = "com.android.text.flags.use_optimized_boottime_font_loading";
    public static final String FLAG_VENDOR_CUSTOM_LOCALE_FALLBACK = "com.android.text.flags.vendor_custom_locale_fallback";
    public static final String FLAG_WORD_STYLE_AUTO = "com.android.text.flags.word_style_auto";

    public static boolean completeFontLoadInSystemServicesReady() {
        return FEATURE_FLAGS.completeFontLoadInSystemServicesReady();
    }

    public static boolean deprecateUiFonts() {
        return FEATURE_FLAGS.deprecateUiFonts();
    }

    public static boolean disableHandwritingInitiatorForIme() {
        return FEATURE_FLAGS.disableHandwritingInitiatorForIme();
    }

    public static boolean escapeClearsFocus() {
        return FEATURE_FLAGS.escapeClearsFocus();
    }

    public static boolean fixDoubleUnderline() {
        return FEATURE_FLAGS.fixDoubleUnderline();
    }

    public static boolean fixFontUpdateFailure() {
        return FEATURE_FLAGS.fixFontUpdateFailure();
    }

    public static boolean fixLineHeightForLocale() {
        return FEATURE_FLAGS.fixLineHeightForLocale();
    }

    public static boolean fixMisalignedContextMenu() {
        return FEATURE_FLAGS.fixMisalignedContextMenu();
    }

    public static boolean fixNullTypefaceBolding() {
        return FEATURE_FLAGS.fixNullTypefaceBolding();
    }

    public static boolean handwritingCursorPosition() {
        return FEATURE_FLAGS.handwritingCursorPosition();
    }

    public static boolean handwritingEndOfLineTap() {
        return FEATURE_FLAGS.handwritingEndOfLineTap();
    }

    public static boolean handwritingUnsupportedMessage() {
        return FEATURE_FLAGS.handwritingUnsupportedMessage();
    }

    public static boolean icuBidiMigration() {
        return FEATURE_FLAGS.icuBidiMigration();
    }

    public static boolean insertModeCrashWhenDelete() {
        return FEATURE_FLAGS.insertModeCrashWhenDelete();
    }

    public static boolean insertModeNotUpdateSelection() {
        return FEATURE_FLAGS.insertModeNotUpdateSelection();
    }

    public static boolean lazyVariationInstance() {
        return FEATURE_FLAGS.lazyVariationInstance();
    }

    public static boolean letterSpacingJustification() {
        return FEATURE_FLAGS.letterSpacingJustification();
    }

    public static boolean missingGetterApis() {
        return FEATURE_FLAGS.missingGetterApis();
    }

    public static boolean newFontsFallbackXml() {
        return FEATURE_FLAGS.newFontsFallbackXml();
    }

    public static boolean noBreakNoHyphenationSpan() {
        return FEATURE_FLAGS.noBreakNoHyphenationSpan();
    }

    public static boolean phraseStrictFallback() {
        return FEATURE_FLAGS.phraseStrictFallback();
    }

    public static boolean rustHyphenator() {
        return FEATURE_FLAGS.rustHyphenator();
    }

    public static boolean useBoundsForWidth() {
        return FEATURE_FLAGS.useBoundsForWidth();
    }

    public static boolean useOptimizedBoottimeFontLoading() {
        return FEATURE_FLAGS.useOptimizedBoottimeFontLoading();
    }

    public static boolean vendorCustomLocaleFallback() {
        return FEATURE_FLAGS.vendorCustomLocaleFallback();
    }

    public static boolean wordStyleAuto() {
        return FEATURE_FLAGS.wordStyleAuto();
    }
}
