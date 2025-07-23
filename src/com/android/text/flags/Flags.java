package com.android.text.flags;

/* loaded from: classes6.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_CLEAR_FONT_VARIATION_SETTINGS = "com.android.text.flags.clear_font_variation_settings";
    public static final String FLAG_COMPLETE_FONT_LOAD_IN_SYSTEM_SERVICES_READY = "com.android.text.flags.complete_font_load_in_system_services_ready";
    public static final String FLAG_CONTEXT_MENU_HIDE_UNAVAILABLE_ITEMS = "com.android.text.flags.context_menu_hide_unavailable_items";
    public static final String FLAG_DEPRECATE_ELEGANT_TEXT_HEIGHT_API = "com.android.text.flags.deprecate_elegant_text_height_api";
    public static final String FLAG_DISABLE_HANDWRITING_INITIATOR_FOR_IME = "com.android.text.flags.disable_handwriting_initiator_for_ime";
    public static final String FLAG_ESCAPE_CLEARS_FOCUS = "com.android.text.flags.escape_clears_focus";
    public static final String FLAG_FIX_LINE_HEIGHT_FOR_LOCALE = "com.android.text.flags.fix_line_height_for_locale";
    public static final String FLAG_FIX_NULL_TYPEFACE_BOLDING = "com.android.text.flags.fix_null_typeface_bolding";
    public static final String FLAG_HANDWRITING_CURSOR_POSITION = "com.android.text.flags.handwriting_cursor_position";
    public static final String FLAG_HANDWRITING_END_OF_LINE_TAP = "com.android.text.flags.handwriting_end_of_line_tap";
    public static final String FLAG_HANDWRITING_GESTURE_WITH_TRANSFORMATION = "com.android.text.flags.handwriting_gesture_with_transformation";
    public static final String FLAG_HANDWRITING_TRACK_DISABLED = "com.android.text.flags.handwriting_track_disabled";
    public static final String FLAG_HANDWRITING_UNSUPPORTED_MESSAGE = "com.android.text.flags.handwriting_unsupported_message";
    public static final String FLAG_HANDWRITING_UNSUPPORTED_SHOW_SOFT_INPUT_FIX = "com.android.text.flags.handwriting_unsupported_show_soft_input_fix";
    public static final String FLAG_INSERT_MODE_CRASH_UPDATE_LAYOUT_SPAN = "com.android.text.flags.insert_mode_crash_update_layout_span";
    public static final String FLAG_INSERT_MODE_CRASH_WHEN_DELETE = "com.android.text.flags.insert_mode_crash_when_delete";
    public static final String FLAG_INSERT_MODE_HIGHLIGHT_RANGE = "com.android.text.flags.insert_mode_highlight_range";
    public static final String FLAG_INSERT_MODE_NOT_UPDATE_SELECTION = "com.android.text.flags.insert_mode_not_update_selection";
    public static final String FLAG_LETTER_SPACING_JUSTIFICATION = "com.android.text.flags.letter_spacing_justification";
    public static final String FLAG_MISSING_GETTER_APIS = "com.android.text.flags.missing_getter_apis";
    public static final String FLAG_NEW_FONTS_FALLBACK_XML = "com.android.text.flags.new_fonts_fallback_xml";
    public static final String FLAG_NO_BREAK_NO_HYPHENATION_SPAN = "com.android.text.flags.no_break_no_hyphenation_span";
    public static final String FLAG_RUST_HYPHENATOR = "com.android.text.flags.rust_hyphenator";
    public static final String FLAG_TTS_SPAN_DURATION = "com.android.text.flags.tts_span_duration";
    public static final String FLAG_TYPEFACE_CACHE_FOR_VAR_SETTINGS = "com.android.text.flags.typeface_cache_for_var_settings";
    public static final String FLAG_TYPEFACE_REDESIGN_READONLY = "com.android.text.flags.typeface_redesign_readonly";
    public static final String FLAG_USE_BOUNDS_FOR_WIDTH = "com.android.text.flags.use_bounds_for_width";
    public static final String FLAG_USE_OPTIMIZED_BOOTTIME_FONT_LOADING = "com.android.text.flags.use_optimized_boottime_font_loading";
    public static final String FLAG_VERTICAL_TEXT_LAYOUT = "com.android.text.flags.vertical_text_layout";
    public static final String FLAG_WORD_STYLE_AUTO = "com.android.text.flags.word_style_auto";

    public static boolean clearFontVariationSettings() {
        return FEATURE_FLAGS.clearFontVariationSettings();
    }

    public static boolean completeFontLoadInSystemServicesReady() {
        return FEATURE_FLAGS.completeFontLoadInSystemServicesReady();
    }

    public static boolean contextMenuHideUnavailableItems() {
        return FEATURE_FLAGS.contextMenuHideUnavailableItems();
    }

    public static boolean deprecateElegantTextHeightApi() {
        return FEATURE_FLAGS.deprecateElegantTextHeightApi();
    }

    public static boolean disableHandwritingInitiatorForIme() {
        return FEATURE_FLAGS.disableHandwritingInitiatorForIme();
    }

    public static boolean escapeClearsFocus() {
        return FEATURE_FLAGS.escapeClearsFocus();
    }

    public static boolean fixLineHeightForLocale() {
        return FEATURE_FLAGS.fixLineHeightForLocale();
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

    public static boolean handwritingGestureWithTransformation() {
        return FEATURE_FLAGS.handwritingGestureWithTransformation();
    }

    public static boolean handwritingTrackDisabled() {
        return FEATURE_FLAGS.handwritingTrackDisabled();
    }

    public static boolean handwritingUnsupportedMessage() {
        return FEATURE_FLAGS.handwritingUnsupportedMessage();
    }

    public static boolean handwritingUnsupportedShowSoftInputFix() {
        return FEATURE_FLAGS.handwritingUnsupportedShowSoftInputFix();
    }

    public static boolean insertModeCrashUpdateLayoutSpan() {
        return FEATURE_FLAGS.insertModeCrashUpdateLayoutSpan();
    }

    public static boolean insertModeCrashWhenDelete() {
        return FEATURE_FLAGS.insertModeCrashWhenDelete();
    }

    public static boolean insertModeHighlightRange() {
        return FEATURE_FLAGS.insertModeHighlightRange();
    }

    public static boolean insertModeNotUpdateSelection() {
        return FEATURE_FLAGS.insertModeNotUpdateSelection();
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

    public static boolean rustHyphenator() {
        return FEATURE_FLAGS.rustHyphenator();
    }

    public static boolean ttsSpanDuration() {
        return FEATURE_FLAGS.ttsSpanDuration();
    }

    public static boolean typefaceCacheForVarSettings() {
        return FEATURE_FLAGS.typefaceCacheForVarSettings();
    }

    public static boolean typefaceRedesignReadonly() {
        return FEATURE_FLAGS.typefaceRedesignReadonly();
    }

    public static boolean useBoundsForWidth() {
        return FEATURE_FLAGS.useBoundsForWidth();
    }

    public static boolean useOptimizedBoottimeFontLoading() {
        return FEATURE_FLAGS.useOptimizedBoottimeFontLoading();
    }

    public static boolean verticalTextLayout() {
        return FEATURE_FLAGS.verticalTextLayout();
    }

    public static boolean wordStyleAuto() {
        return FEATURE_FLAGS.wordStyleAuto();
    }
}
