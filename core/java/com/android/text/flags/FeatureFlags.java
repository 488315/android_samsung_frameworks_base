package com.android.text.flags;

/* loaded from: classes5.dex */
public interface FeatureFlags {
    boolean completeFontLoadInSystemServicesReady();

    boolean deprecateUiFonts();

    boolean disableHandwritingInitiatorForIme();

    boolean escapeClearsFocus();

    boolean fixDoubleUnderline();

    boolean fixFontUpdateFailure();

    boolean fixLineHeightForLocale();

    boolean fixMisalignedContextMenu();

    boolean fixNullTypefaceBolding();

    boolean handwritingCursorPosition();

    boolean handwritingEndOfLineTap();

    boolean handwritingUnsupportedMessage();

    boolean icuBidiMigration();

    boolean insertModeCrashWhenDelete();

    boolean insertModeNotUpdateSelection();

    boolean lazyVariationInstance();

    boolean letterSpacingJustification();

    boolean missingGetterApis();

    boolean newFontsFallbackXml();

    boolean noBreakNoHyphenationSpan();

    boolean phraseStrictFallback();

    boolean rustHyphenator();

    boolean useBoundsForWidth();

    boolean useOptimizedBoottimeFontLoading();

    boolean vendorCustomLocaleFallback();

    boolean wordStyleAuto();
}
