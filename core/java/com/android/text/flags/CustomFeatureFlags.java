package com.android.text.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_COMPLETE_FONT_LOAD_IN_SYSTEM_SERVICES_READY, Flags.FLAG_DEPRECATE_UI_FONTS, Flags.FLAG_DISABLE_HANDWRITING_INITIATOR_FOR_IME, Flags.FLAG_ESCAPE_CLEARS_FOCUS, Flags.FLAG_FIX_DOUBLE_UNDERLINE, Flags.FLAG_FIX_FONT_UPDATE_FAILURE, Flags.FLAG_FIX_LINE_HEIGHT_FOR_LOCALE, Flags.FLAG_FIX_MISALIGNED_CONTEXT_MENU, Flags.FLAG_FIX_NULL_TYPEFACE_BOLDING, Flags.FLAG_HANDWRITING_CURSOR_POSITION, Flags.FLAG_HANDWRITING_END_OF_LINE_TAP, Flags.FLAG_HANDWRITING_UNSUPPORTED_MESSAGE, Flags.FLAG_ICU_BIDI_MIGRATION, Flags.FLAG_INSERT_MODE_CRASH_WHEN_DELETE, Flags.FLAG_INSERT_MODE_NOT_UPDATE_SELECTION, Flags.FLAG_LAZY_VARIATION_INSTANCE, Flags.FLAG_LETTER_SPACING_JUSTIFICATION, Flags.FLAG_MISSING_GETTER_APIS, Flags.FLAG_NEW_FONTS_FALLBACK_XML, Flags.FLAG_NO_BREAK_NO_HYPHENATION_SPAN, Flags.FLAG_PHRASE_STRICT_FALLBACK, Flags.FLAG_RUST_HYPHENATOR, Flags.FLAG_USE_BOUNDS_FOR_WIDTH, Flags.FLAG_USE_OPTIMIZED_BOOTTIME_FONT_LOADING, Flags.FLAG_VENDOR_CUSTOM_LOCALE_FALLBACK, Flags.FLAG_WORD_STYLE_AUTO, ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean completeFontLoadInSystemServicesReady() {
        return getValue(Flags.FLAG_COMPLETE_FONT_LOAD_IN_SYSTEM_SERVICES_READY, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).completeFontLoadInSystemServicesReady();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean deprecateUiFonts() {
        return getValue(Flags.FLAG_DEPRECATE_UI_FONTS, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deprecateUiFonts();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean disableHandwritingInitiatorForIme() {
        return getValue(Flags.FLAG_DISABLE_HANDWRITING_INITIATOR_FOR_IME, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).disableHandwritingInitiatorForIme();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean escapeClearsFocus() {
        return getValue(Flags.FLAG_ESCAPE_CLEARS_FOCUS, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).escapeClearsFocus();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean fixDoubleUnderline() {
        return getValue(Flags.FLAG_FIX_DOUBLE_UNDERLINE, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixDoubleUnderline();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean fixFontUpdateFailure() {
        return getValue(Flags.FLAG_FIX_FONT_UPDATE_FAILURE, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixFontUpdateFailure();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean fixLineHeightForLocale() {
        return getValue(Flags.FLAG_FIX_LINE_HEIGHT_FOR_LOCALE, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixLineHeightForLocale();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean fixMisalignedContextMenu() {
        return getValue(Flags.FLAG_FIX_MISALIGNED_CONTEXT_MENU, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixMisalignedContextMenu();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean fixNullTypefaceBolding() {
        return getValue(Flags.FLAG_FIX_NULL_TYPEFACE_BOLDING, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixNullTypefaceBolding();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean handwritingCursorPosition() {
        return getValue(Flags.FLAG_HANDWRITING_CURSOR_POSITION, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).handwritingCursorPosition();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean handwritingEndOfLineTap() {
        return getValue(Flags.FLAG_HANDWRITING_END_OF_LINE_TAP, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).handwritingEndOfLineTap();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean handwritingUnsupportedMessage() {
        return getValue(Flags.FLAG_HANDWRITING_UNSUPPORTED_MESSAGE, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).handwritingUnsupportedMessage();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean icuBidiMigration() {
        return getValue(Flags.FLAG_ICU_BIDI_MIGRATION, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).icuBidiMigration();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean insertModeCrashWhenDelete() {
        return getValue(Flags.FLAG_INSERT_MODE_CRASH_WHEN_DELETE, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).insertModeCrashWhenDelete();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean insertModeNotUpdateSelection() {
        return getValue(Flags.FLAG_INSERT_MODE_NOT_UPDATE_SELECTION, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).insertModeNotUpdateSelection();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean lazyVariationInstance() {
        return getValue(Flags.FLAG_LAZY_VARIATION_INSTANCE, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).lazyVariationInstance();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean letterSpacingJustification() {
        return getValue(Flags.FLAG_LETTER_SPACING_JUSTIFICATION, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).letterSpacingJustification();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean missingGetterApis() {
        return getValue(Flags.FLAG_MISSING_GETTER_APIS, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).missingGetterApis();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean newFontsFallbackXml() {
        return getValue(Flags.FLAG_NEW_FONTS_FALLBACK_XML, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).newFontsFallbackXml();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean noBreakNoHyphenationSpan() {
        return getValue(Flags.FLAG_NO_BREAK_NO_HYPHENATION_SPAN, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).noBreakNoHyphenationSpan();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean phraseStrictFallback() {
        return getValue(Flags.FLAG_PHRASE_STRICT_FALLBACK, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).phraseStrictFallback();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean rustHyphenator() {
        return getValue(Flags.FLAG_RUST_HYPHENATOR, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).rustHyphenator();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean useBoundsForWidth() {
        return getValue(Flags.FLAG_USE_BOUNDS_FOR_WIDTH, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useBoundsForWidth();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean useOptimizedBoottimeFontLoading() {
        return getValue(Flags.FLAG_USE_OPTIMIZED_BOOTTIME_FONT_LOADING, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useOptimizedBoottimeFontLoading();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean vendorCustomLocaleFallback() {
        return getValue(Flags.FLAG_VENDOR_CUSTOM_LOCALE_FALLBACK, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).vendorCustomLocaleFallback();
            }
        });
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean wordStyleAuto() {
        return getValue(Flags.FLAG_WORD_STYLE_AUTO, new Predicate() { // from class: com.android.text.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).wordStyleAuto();
            }
        });
    }

    public boolean isFlagReadOnlyOptimized(String flagName) {
        if (this.mReadOnlyFlagsSet.contains(flagName) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean isOptimizationEnabled() {
        return false;
    }

    protected boolean getValue(String flagName, Predicate<FeatureFlags> getter) {
        return this.mGetValueImpl.test(flagName, getter);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_COMPLETE_FONT_LOAD_IN_SYSTEM_SERVICES_READY, Flags.FLAG_DEPRECATE_UI_FONTS, Flags.FLAG_DISABLE_HANDWRITING_INITIATOR_FOR_IME, Flags.FLAG_ESCAPE_CLEARS_FOCUS, Flags.FLAG_FIX_DOUBLE_UNDERLINE, Flags.FLAG_FIX_FONT_UPDATE_FAILURE, Flags.FLAG_FIX_LINE_HEIGHT_FOR_LOCALE, Flags.FLAG_FIX_MISALIGNED_CONTEXT_MENU, Flags.FLAG_FIX_NULL_TYPEFACE_BOLDING, Flags.FLAG_HANDWRITING_CURSOR_POSITION, Flags.FLAG_HANDWRITING_END_OF_LINE_TAP, Flags.FLAG_HANDWRITING_UNSUPPORTED_MESSAGE, Flags.FLAG_ICU_BIDI_MIGRATION, Flags.FLAG_INSERT_MODE_CRASH_WHEN_DELETE, Flags.FLAG_INSERT_MODE_NOT_UPDATE_SELECTION, Flags.FLAG_LAZY_VARIATION_INSTANCE, Flags.FLAG_LETTER_SPACING_JUSTIFICATION, Flags.FLAG_MISSING_GETTER_APIS, Flags.FLAG_NEW_FONTS_FALLBACK_XML, Flags.FLAG_NO_BREAK_NO_HYPHENATION_SPAN, Flags.FLAG_PHRASE_STRICT_FALLBACK, Flags.FLAG_RUST_HYPHENATOR, Flags.FLAG_USE_BOUNDS_FOR_WIDTH, Flags.FLAG_USE_OPTIMIZED_BOOTTIME_FONT_LOADING, Flags.FLAG_VENDOR_CUSTOM_LOCALE_FALLBACK, Flags.FLAG_WORD_STYLE_AUTO);
    }
}
