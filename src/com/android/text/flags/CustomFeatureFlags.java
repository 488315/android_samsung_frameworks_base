package com.android.text.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes6.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_CLEAR_FONT_VARIATION_SETTINGS, Flags.FLAG_COMPLETE_FONT_LOAD_IN_SYSTEM_SERVICES_READY, Flags.FLAG_CONTEXT_MENU_HIDE_UNAVAILABLE_ITEMS, Flags.FLAG_DEPRECATE_ELEGANT_TEXT_HEIGHT_API, Flags.FLAG_DISABLE_HANDWRITING_INITIATOR_FOR_IME, Flags.FLAG_ESCAPE_CLEARS_FOCUS, Flags.FLAG_FIX_LINE_HEIGHT_FOR_LOCALE, Flags.FLAG_FIX_NULL_TYPEFACE_BOLDING, Flags.FLAG_HANDWRITING_CURSOR_POSITION, Flags.FLAG_HANDWRITING_END_OF_LINE_TAP, Flags.FLAG_HANDWRITING_GESTURE_WITH_TRANSFORMATION, Flags.FLAG_HANDWRITING_TRACK_DISABLED, Flags.FLAG_HANDWRITING_UNSUPPORTED_MESSAGE, Flags.FLAG_HANDWRITING_UNSUPPORTED_SHOW_SOFT_INPUT_FIX, Flags.FLAG_INSERT_MODE_CRASH_UPDATE_LAYOUT_SPAN, Flags.FLAG_INSERT_MODE_CRASH_WHEN_DELETE, Flags.FLAG_INSERT_MODE_HIGHLIGHT_RANGE, Flags.FLAG_INSERT_MODE_NOT_UPDATE_SELECTION, Flags.FLAG_LETTER_SPACING_JUSTIFICATION, Flags.FLAG_MISSING_GETTER_APIS, Flags.FLAG_NEW_FONTS_FALLBACK_XML, Flags.FLAG_NO_BREAK_NO_HYPHENATION_SPAN, Flags.FLAG_RUST_HYPHENATOR, Flags.FLAG_TTS_SPAN_DURATION, Flags.FLAG_TYPEFACE_CACHE_FOR_VAR_SETTINGS, Flags.FLAG_TYPEFACE_REDESIGN_READONLY, Flags.FLAG_USE_BOUNDS_FOR_WIDTH, Flags.FLAG_USE_OPTIMIZED_BOOTTIME_FONT_LOADING, Flags.FLAG_VERTICAL_TEXT_LAYOUT, Flags.FLAG_WORD_STYLE_AUTO, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean clearFontVariationSettings() {
        return getValue(Flags.FLAG_CLEAR_FONT_VARIATION_SETTINGS, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).clearFontVariationSettings();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean completeFontLoadInSystemServicesReady() {
        return getValue(Flags.FLAG_COMPLETE_FONT_LOAD_IN_SYSTEM_SERVICES_READY, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).completeFontLoadInSystemServicesReady();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean contextMenuHideUnavailableItems() {
        return getValue(Flags.FLAG_CONTEXT_MENU_HIDE_UNAVAILABLE_ITEMS, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda29
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).contextMenuHideUnavailableItems();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean deprecateElegantTextHeightApi() {
        return getValue(Flags.FLAG_DEPRECATE_ELEGANT_TEXT_HEIGHT_API, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deprecateElegantTextHeightApi();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean disableHandwritingInitiatorForIme() {
        return getValue(Flags.FLAG_DISABLE_HANDWRITING_INITIATOR_FOR_IME, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).disableHandwritingInitiatorForIme();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean escapeClearsFocus() {
        return getValue(Flags.FLAG_ESCAPE_CLEARS_FOCUS, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).escapeClearsFocus();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean fixLineHeightForLocale() {
        return getValue(Flags.FLAG_FIX_LINE_HEIGHT_FOR_LOCALE, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixLineHeightForLocale();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean fixNullTypefaceBolding() {
        return getValue(Flags.FLAG_FIX_NULL_TYPEFACE_BOLDING, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixNullTypefaceBolding();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean handwritingCursorPosition() {
        return getValue(Flags.FLAG_HANDWRITING_CURSOR_POSITION, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).handwritingCursorPosition();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean handwritingEndOfLineTap() {
        return getValue(Flags.FLAG_HANDWRITING_END_OF_LINE_TAP, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).handwritingEndOfLineTap();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean handwritingGestureWithTransformation() {
        return getValue(Flags.FLAG_HANDWRITING_GESTURE_WITH_TRANSFORMATION, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).handwritingGestureWithTransformation();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean handwritingTrackDisabled() {
        return getValue(Flags.FLAG_HANDWRITING_TRACK_DISABLED, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).handwritingTrackDisabled();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean handwritingUnsupportedMessage() {
        return getValue(Flags.FLAG_HANDWRITING_UNSUPPORTED_MESSAGE, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).handwritingUnsupportedMessage();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean handwritingUnsupportedShowSoftInputFix() {
        return getValue(Flags.FLAG_HANDWRITING_UNSUPPORTED_SHOW_SOFT_INPUT_FIX, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).handwritingUnsupportedShowSoftInputFix();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean insertModeCrashUpdateLayoutSpan() {
        return getValue(Flags.FLAG_INSERT_MODE_CRASH_UPDATE_LAYOUT_SPAN, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda27
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).insertModeCrashUpdateLayoutSpan();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean insertModeCrashWhenDelete() {
        return getValue(Flags.FLAG_INSERT_MODE_CRASH_WHEN_DELETE, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda28
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).insertModeCrashWhenDelete();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean insertModeHighlightRange() {
        return getValue(Flags.FLAG_INSERT_MODE_HIGHLIGHT_RANGE, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).insertModeHighlightRange();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean insertModeNotUpdateSelection() {
        return getValue(Flags.FLAG_INSERT_MODE_NOT_UPDATE_SELECTION, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).insertModeNotUpdateSelection();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean letterSpacingJustification() {
        return getValue(Flags.FLAG_LETTER_SPACING_JUSTIFICATION, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).letterSpacingJustification();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean missingGetterApis() {
        return getValue(Flags.FLAG_MISSING_GETTER_APIS, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).missingGetterApis();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean newFontsFallbackXml() {
        return getValue(Flags.FLAG_NEW_FONTS_FALLBACK_XML, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).newFontsFallbackXml();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean noBreakNoHyphenationSpan() {
        return getValue(Flags.FLAG_NO_BREAK_NO_HYPHENATION_SPAN, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).noBreakNoHyphenationSpan();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean rustHyphenator() {
        return getValue(Flags.FLAG_RUST_HYPHENATOR, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).rustHyphenator();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean ttsSpanDuration() {
        return getValue(Flags.FLAG_TTS_SPAN_DURATION, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ttsSpanDuration();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean typefaceCacheForVarSettings() {
        return getValue(Flags.FLAG_TYPEFACE_CACHE_FOR_VAR_SETTINGS, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda26
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).typefaceCacheForVarSettings();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean typefaceRedesignReadonly() {
        return getValue(Flags.FLAG_TYPEFACE_REDESIGN_READONLY, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).typefaceRedesignReadonly();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean useBoundsForWidth() {
        return getValue(Flags.FLAG_USE_BOUNDS_FOR_WIDTH, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useBoundsForWidth();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean useOptimizedBoottimeFontLoading() {
        return getValue(Flags.FLAG_USE_OPTIMIZED_BOOTTIME_FONT_LOADING, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useOptimizedBoottimeFontLoading();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean verticalTextLayout() {
        return getValue(Flags.FLAG_VERTICAL_TEXT_LAYOUT, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).verticalTextLayout();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean wordStyleAuto() {
        return getValue(Flags.FLAG_WORD_STYLE_AUTO, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).wordStyleAuto();
            }
        });
    }

    public boolean isFlagReadOnlyOptimized(String str) {
        return this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled();
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_CLEAR_FONT_VARIATION_SETTINGS, Flags.FLAG_COMPLETE_FONT_LOAD_IN_SYSTEM_SERVICES_READY, Flags.FLAG_CONTEXT_MENU_HIDE_UNAVAILABLE_ITEMS, Flags.FLAG_DEPRECATE_ELEGANT_TEXT_HEIGHT_API, Flags.FLAG_DISABLE_HANDWRITING_INITIATOR_FOR_IME, Flags.FLAG_ESCAPE_CLEARS_FOCUS, Flags.FLAG_FIX_LINE_HEIGHT_FOR_LOCALE, Flags.FLAG_FIX_NULL_TYPEFACE_BOLDING, Flags.FLAG_HANDWRITING_CURSOR_POSITION, Flags.FLAG_HANDWRITING_END_OF_LINE_TAP, Flags.FLAG_HANDWRITING_GESTURE_WITH_TRANSFORMATION, Flags.FLAG_HANDWRITING_TRACK_DISABLED, Flags.FLAG_HANDWRITING_UNSUPPORTED_MESSAGE, Flags.FLAG_HANDWRITING_UNSUPPORTED_SHOW_SOFT_INPUT_FIX, Flags.FLAG_INSERT_MODE_CRASH_UPDATE_LAYOUT_SPAN, Flags.FLAG_INSERT_MODE_CRASH_WHEN_DELETE, Flags.FLAG_INSERT_MODE_HIGHLIGHT_RANGE, Flags.FLAG_INSERT_MODE_NOT_UPDATE_SELECTION, Flags.FLAG_LETTER_SPACING_JUSTIFICATION, Flags.FLAG_MISSING_GETTER_APIS, Flags.FLAG_NEW_FONTS_FALLBACK_XML, Flags.FLAG_NO_BREAK_NO_HYPHENATION_SPAN, Flags.FLAG_RUST_HYPHENATOR, Flags.FLAG_TTS_SPAN_DURATION, Flags.FLAG_TYPEFACE_CACHE_FOR_VAR_SETTINGS, Flags.FLAG_TYPEFACE_REDESIGN_READONLY, Flags.FLAG_USE_BOUNDS_FOR_WIDTH, Flags.FLAG_USE_OPTIMIZED_BOOTTIME_FONT_LOADING, Flags.FLAG_VERTICAL_TEXT_LAYOUT, Flags.FLAG_WORD_STYLE_AUTO);
    }
}
