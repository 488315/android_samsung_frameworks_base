package com.android.text.flags;

/* loaded from: classes6.dex */
public interface FeatureFlags {
    boolean clearFontVariationSettings();

    boolean completeFontLoadInSystemServicesReady();

    boolean contextMenuHideUnavailableItems();

    boolean deprecateElegantTextHeightApi();

    boolean disableHandwritingInitiatorForIme();

    boolean escapeClearsFocus();

    boolean fixLineHeightForLocale();

    boolean fixNullTypefaceBolding();

    boolean handwritingCursorPosition();

    boolean handwritingEndOfLineTap();

    boolean handwritingGestureWithTransformation();

    boolean handwritingTrackDisabled();

    boolean handwritingUnsupportedMessage();

    boolean handwritingUnsupportedShowSoftInputFix();

    boolean insertModeCrashUpdateLayoutSpan();

    boolean insertModeCrashWhenDelete();

    boolean insertModeHighlightRange();

    boolean insertModeNotUpdateSelection();

    boolean letterSpacingJustification();

    boolean missingGetterApis();

    boolean newFontsFallbackXml();

    boolean noBreakNoHyphenationSpan();

    boolean rustHyphenator();

    boolean ttsSpanDuration();

    boolean typefaceCacheForVarSettings();

    boolean typefaceRedesignReadonly();

    boolean useBoundsForWidth();

    boolean useOptimizedBoottimeFontLoading();

    boolean verticalTextLayout();

    boolean wordStyleAuto();
}
